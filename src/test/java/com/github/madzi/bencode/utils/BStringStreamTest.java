package com.github.madzi.bencode.utils;

import com.github.madzi.bencode.base.BConst;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test for BStringStream implementation.
 */
public class BStringStreamTest {
    
    /**
     * Test of peekChar method, of class BStringStream.
     */
    @Test
    public void testPeekChar() {
        System.out.println("peekChar");
        BStringStream sStream = new BStringStream("i42e");
        char expResult = 'i';
        char result = sStream.peekChar();
        int expPos = 0;
        int pos = sStream.getPos();
        assertEquals(expResult, result);
        assertEquals(expPos, pos);
    }

    /**
     * Test of getChar method, of class BStringStream.
     */
    @Test
    public void testGetChar() {
        System.out.println("getChar");
        BStringStream sStream = new BStringStream("i43e");
        char expResult = 'i';
        char result = sStream.getChar();
        int expPos = 1;
        int pos = sStream.getPos();
        assertEquals(expResult, result);
        assertEquals(expPos, pos);
    }

    /**
     * Test of reset method, of class BStringStream.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        BStringStream sStream = new BStringStream("i44e");
        char result = sStream.getChar();
        result = sStream.getChar();
        result = sStream.getChar();
        result = sStream.getChar();
        sStream.reset();
        int expPos = 0;
        int pos = sStream.getPos();
        assertEquals(expPos, pos);
    }

    /**
     * Test of getPos method, of class BStringStream.
     */
    @Test
    public void testGetPos() {
        System.out.println("getPos");
        BStringStream sStream = new BStringStream("i45e");
        char result = sStream.getChar();
        int expPos = 1;
        int pos = sStream.getPos();
        assertEquals(expPos, pos);
    }

    /**
     * Test of getByteArray method, of class BStringStream.
     */
    @Test
    public void testGetByteArray() {
        System.out.println("getByteArray");
        String expResult = "i46e";
        BStringStream sStream = new BStringStream(expResult);
        byte[] bytes = sStream.getByteArray(expResult.length());
        String result = new String(bytes, BConst.ASCII);
        assertEquals(expResult, result);
    }

}
