package com.github.madzi.bencode.base;

import com.github.madzi.bencode.utils.IBStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Base implementation of Bencode decoder.
 */
public class Bdecoder {

    /**
     * Decode integer value.
     * 
     * @param stream stream with encoded bytes
     * @return decoded integer value
     */
    public static Long readInt(final IBStream stream) {
        char ch = stream.getChar();
        checkChar(BConst.INT_START, ch);

        long val = 0;
        boolean negative = false;
        boolean leadZero = false;

        ch = stream.getChar();
        if (BConst.SIGN == ch) {
            negative = true;
            ch = stream.getChar();
        } else if (BConst.ZERO == ch) {
            leadZero = true;
            ch = stream.getChar();
        }

        if (BConst.COMMON_END == ch) {
            if (leadZero) {
                return 0L;
            } else if (negative) {
                throw new IllegalStateException("Sign without digets not allowed.");
            }
            return null;
        }

        while (isDigit(ch)) {
            if (leadZero && BConst.ZERO != ch) {
                throw new IllegalStateException("Lead zero not allowed.");
            }
            val = val * 10 + (int) ch - (int) BConst.ZERO;
            ch = stream.getChar();
        }

        checkChar(BConst.COMMON_END, ch);
        return negative ? -val : val;
    }

    /**
     * Decode array of bytes.
     * 
     * @param stream stream with encoded bytes
     * @return array of bytes
     */
    public static byte[] readByteArray(final IBStream stream) {
        char ch = stream.getChar();
        if (!isDigit(ch)) {
            throw new IllegalStateException(String.format("Expected array length but found '%s'.", ch));
        }
        long len = 0;
        while (isDigit(ch)) {
            len = len + (int) ch - (int) BConst.ZERO;
            ch = stream.getChar();
        }
        checkChar(BConst.COLON, ch);
        return stream.getByteArray((int) len);
    }

    /**
     * Decode list of objects.
     * 
     * @param stream stream with encoded bytes
     * @return the list of objects
     */
    public static List<Object> readList(final IBStream stream) {
        char ch = stream.getChar();
        checkChar(BConst.LIST_START, ch);
        List<Object> list = new LinkedList<Object>();
        ch = stream.peekChar();
        while (BConst.COMMON_END != ch) {
            list.add(readObj(stream));
            ch = stream.peekChar();
        }
        ch = stream.getChar();
        checkChar(BConst.COMMON_END, ch);
        return list;
    }

    /**
     * Decode dictionary.
     * 
     * @param stream stream with encoded bytes
     * @return map (dictionary) with a key-value pairs
     */
    public static Map<String, Object> readDictionary(final IBStream stream) {
        char ch = stream.getChar();
        checkChar(BConst.DICT_START, ch);
        Map<String, Object> dict = new TreeMap<String, Object>();

        ch = stream.peekChar();
        while (BConst.COMMON_END != ch) {
            byte[] key = readByteArray(stream);
            Object obj = readObj(stream);
            dict.put(new String(key, BConst.ASCII), obj);
            ch = stream.peekChar();
        }

        ch = stream.getChar();
        checkChar(BConst.COMMON_END, ch);
        return dict;
    }

    /**
     * Decode object.
     * 
     * @param stream stream with encoded bytes
     * @return some object
     */
    public static Object readObj(final IBStream stream) {
        Object result;
        char ch = stream.peekChar();
        if (BConst.EOT == ch) {
            result = null;
        } else if (isDigit(ch)) {
            result = readByteArray(stream);
        } else if (BConst.INT_START == ch) {
            result = readInt(stream);
        } else if (BConst.LIST_START == ch) {
            result = readList(stream);
        } else if (BConst.DICT_START == ch) {
            result = readDictionary(stream);
        } else {
            throw new IllegalStateException(String.format("Unexpected symbol: %s.", ch));
        }
        return result;
    }

    /**
     * Throw exception if checked char is not equal to expected char.
     * 
     * @param exp expected char
     * @param ch checkded char
     */
    private static void checkChar(final char exp, final char ch) {
        if (ch != exp) {
            throw new IllegalStateException(String.format("Expected '%s' but found '%s'.", exp, ch));
        }
    }

    /**
     * Check ASCII symbol belongs to the set of digits [0x30..0x39].
     * 
     * @param ch char for check
     * @return result of check
     */
    private static boolean isDigit(char ch) {
        return '0' <= ch && ch <= '9';
    }

}
