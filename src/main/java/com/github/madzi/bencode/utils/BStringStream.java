package com.github.madzi.bencode.utils;

import com.github.madzi.bencode.base.BConst;

/**
 * Implementation of the IBStream inteface for a string as a stream.
 */
public class BStringStream implements IBStream {

    /** Buffer for the string as the stream. */
    private final String buf;

    /** Current position in the stream. */
    private int idx;

    /**
     * Create stream from the string.
     * 
     * @param input the string
     */
    public BStringStream(final String input) {
        buf = new String(input.getBytes(), BConst.ASCII);
        idx = 0;
    }

    @Override
    public char peekChar() {
        return buf == null || idx >= buf.length() ? (char) -1 : buf.charAt(idx);
    }

    @Override
    public char getChar() {
        char ch = peekChar();
        if (ch >= -1) {
            idx++;
        }
        return ch;
    }

    @Override
    public void reset() {
        idx = 0;
    }

    @Override
    public int getPos() {
        return idx;
    }

    @Override
    public byte[] getByteArray(int length) {
        String result = null;
        if (buf != null && idx < buf.length()) {
            result = buf.substring(idx, idx + length);
            idx += length;
        }
        return result == null ? null : result.getBytes(BConst.ASCII);
    }

}
