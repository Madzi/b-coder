package com.github.madzi.bencode.base;

import java.nio.charset.Charset;

/**
 * Constants for encoding and decoding.
 */
public final class BConst {

    /** The charset of Bencode - ASCII. */
    public static final Charset ASCII = Charset.forName("US-ASCII");

    /** The prefix for dictionary. */
    public static final char DICT_START = 'd';
    /** The prefix for integer. */
    public static final char INT_START = 'i';
    /** The prefix for list. */
    public static final char LIST_START = 'l';
    /** The common suffix for dictionary, integer and list. */
    public static final char COMMON_END = 'e';
    /** The array length and data delimiter. */
    public static final char COLON = ':';
    /** The negative sign. */
    public static final char SIGN = '-';
    /** Zero digit. */
    public static final char ZERO = '0';
    /** End of text. */
    public static final char EOT = (char) -1;

    /**
     * Empty private constructor for utilites class.
     */
    private BConst() {
    }

}
