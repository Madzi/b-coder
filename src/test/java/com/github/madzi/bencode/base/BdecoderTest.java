package com.github.madzi.bencode.base;

import com.github.madzi.bencode.utils.BStringStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for Bdecoder class.
 */
public class BdecoderTest {

    /**
     * Test of readInt method, of class Bdecoder.
     */
    @Test
    public void testReadInt() {
        System.out.println("readInt");
        // Positive value
        BStringStream stream = new BStringStream("i42e");
        Long expected = 42L;
        Long result = Bdecoder.readInt(stream);
        Assert.assertEquals(expected, result);
        // Zerro value
        stream = new BStringStream("i0e");
        expected = 0L;
        result = Bdecoder.readInt(stream);
        Assert.assertEquals(expected, result);
        // Negative value
        stream = new BStringStream("i-42e");
        expected = -42L;
        result = Bdecoder.readInt(stream);
        Assert.assertEquals(expected, result);
    }

    /**
     * Test for readInt method when only (negative) sign is in scope.
     */
    @Test(expected = IllegalStateException.class)
    public void testReadIntOnlySign() {
        BStringStream stream = new BStringStream("i-e");
        Long result = Bdecoder.readInt(stream);
    }

    /**
     * Test for readInt method when value does not have tokens.
     */
    @Test(expected = IllegalStateException.class)
    public void testReadIntOnlyValue() {
        BStringStream stream = new BStringStream("42");
        Long result = Bdecoder.readInt(stream);
    }

    /**
     * Test for readInt method when value has a lead zero.
     */
    @Test(expected = IllegalStateException.class)
    public void testReadIntLeadZero() {
        BStringStream stream = new BStringStream("i042e");
        Long result = Bdecoder.readInt(stream);
    }

    /**
     * Test for readInt method when value is absent.
     */
    @Test
    public void testReadIntEmptyValue() {
        BStringStream stream = new BStringStream("ie");
        Long result = Bdecoder.readInt(stream);
        Assert.assertEquals(result, null);
    }

    /**
     * Test for readInt method when (negative) sign is in the middle of the value.
     */
    @Test(expected = IllegalStateException.class)
    public void testReadIntSingInMiddle() {
        BStringStream stream = new BStringStream("i10-5e");
        Long result = Bdecoder.readInt(stream);
    }

    /**
     * Test for readByteArray method, of class Bdecoder.
     */
    @Test
    public void testReadByteArray() {
        BStringStream stream = new BStringStream("4:spam");
        byte[] bytes = Bdecoder.readByteArray(stream);
        String str = new String(bytes, BConst.ASCII);
        String expResult = "spam";
        Assert.assertEquals(expResult, str);
    }

    /**
     * Test for readList method, of class Bdecoder.
     */
    @Test
    public void testReadList() {
        BStringStream stream = new BStringStream("li1ei2ei3ee");
        List<Object> list = (List) Bdecoder.readList(stream);
        Assert.assertEquals(3, list.size());
        for (int idx = 0; idx < list.size(); ++idx) {
            Long val = (Long) list.get(idx);
            Long expVal = (long) (idx + 1);
            Assert.assertEquals(expVal, val);
        }
    }

    /**
     * Test for readDictionary method, of class Bdecoder.
     */
    @Test
    public void testReadDictionary() {
        String expKey = "spam";
        BStringStream stream = new BStringStream("d4:spami1ee");
        Map<String, Object> map = (Map) Bdecoder.readObj(stream);
        Set<String> set = map.keySet();
        Assert.assertEquals(1, set.size());
        Assert.assertTrue(set.contains(expKey));
        Long val = (Long) map.get(expKey);
        Long expVal = 1L;
        Assert.assertEquals(expVal, val);
    }

    /**
     * Test for readObj to decode an object.
     */
    @Test
    public void testReadObj() {
        String expKey = "spam";
        BStringStream stream = new BStringStream("d4:spamli1ei2ei3eee");
        Map<String, Object> map = (Map) Bdecoder.readObj(stream);
        Set<String> set = map.keySet();
        Assert.assertEquals(1, set.size());
        Assert.assertTrue(set.contains(expKey));
        List<Object> list = (List) map.get(expKey);
        Assert.assertEquals(3, list.size());
        for (int idx = 0; idx < list.size(); ++idx) {
            Long val = (Long) list.get(idx);
            Long expVal = (long) (idx + 1);
            Assert.assertEquals(expVal, val);
        }
    }

}
