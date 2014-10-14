package com.github.madzi.bencode.utils;

/**
 * The Bencode stream interface.
 */
public interface IBStream {

    /**
     * Returns the char in current position without changing position in the stream.
     * 
     * @return char in current position
     */
    char peekChar();

    /**
     * Return the char in current postition and move the position to a next char.
     * 
     * @return char in current position
     */
    char getChar();

    /**
     * Reset position in the stream.
     */
    void reset();

    /**
     * Returns the current position in the stream.
     * 
     * @return current position in the stream
     */
    int getPos();

    /**
     * Returns bytes array from current position with the given length.
     * 
     * @param length length of bytes array
     * @return bytes array
     */
    byte[] getByteArray(int length);

}
