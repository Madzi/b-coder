package com.github.madzi.bencode.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test for Bencoder class.
 */
public class BencoderTest {
    
    /**
     * Test of writeInt method, of class Bencoder.
     */
    @Test
    public void testWriteInt() {
        System.out.println("writeInt");
        /** Positive testing. */
        // Positive value
        Long val = 42L;
        String expResult = "i42e";
        String result = Bencoder.writeInt(val);
        assertEquals(expResult, result);
        // Zerro value
        val = 0L;
        expResult = "i0e";
        result = Bencoder.writeInt(val);
        assertEquals(expResult, result);
        // Negative value
        val = -42L;
        expResult = "i-42e";
        result = Bencoder.writeInt(val);
        assertEquals(expResult, result);
    }

    /**
     * Test of writeArray method, of class Bencoder.
     */
    @Test
    public void testWriteByteArray() {
        System.out.println("writeByteArray");
        /** Positive testing. */
        String bytes = "spam";
        String expResult = "4:spam";
        String result = Bencoder.writeByteArray(bytes.getBytes(BConst.ASCII));
        assertEquals(expResult, result);
        bytes = "";
        expResult = "0:";
        result = Bencoder.writeByteArray(bytes.getBytes(BConst.ASCII));
        assertEquals(expResult, result);
    }

    /**
     * Test of writeList method, of class Bencoder.
     */
    @Test
    public void testWriteList() {
        System.out.println("writeList");
        /** Positive testing. */
        // List of long values
        List<Long> list = new LinkedList(Arrays.asList(0L, 1L, 2L, 3L));
        String expResult = "li0ei1ei2ei3ee";
        String result = Bencoder.writeList(list);
        assertEquals(expResult, result);
        // List of string values
        List<String> strs = new LinkedList<String>(Arrays.asList("spam", "moo", "test"));
        expResult = "l4:spam3:moo4:teste";
        result = Bencoder.writeList(strs);
        assertEquals(expResult, result);
        // Empty list
        list = Collections.emptyList();
        expResult = "le";
        result = Bencoder.writeList(list);
        assertEquals(expResult, result);
        // Combine list
        List<Object> objs = new LinkedList();
        objs.add(42L);
        objs.add("spam");
        expResult = "li42e4:spame";
        result = Bencoder.writeList(objs);
        assertEquals(expResult, result);
    }

    /**
     * Test of writeList method, of class Bencoder.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWriteListUnsupportedType() {
        System.out.println("writeListUnsupportedType");
        /** Negative testing. */
        List<Date> dates = new LinkedList(Arrays.asList(new Date(), new Date()));
        Bencoder.writeList(dates);
    }

    /**
     * Test of writeDict method, of class Bencoder.
     */
    @Test
    public void testWriteDict() {
        System.out.println("writeList");
        /** Positive testing. */
        Map<String, Object> map = new HashMap();
        map.put("spam", -3L);
        map.put("test", 3L);
        map.put("book", 0L);
        String expResult = "d4:booki0e4:spami-3e4:testi3ee";
        String result = Bencoder.writeDict(map);
        assertEquals(expResult, result);
        map.clear();
        map.put("abc", new LinkedList<Long>(Arrays.asList(-1L, 0L, 1L)));
        map.put("cba", "moo");
        expResult = "d3:abcli-1ei0ei1ee3:cba3:mooe";
        result = Bencoder.writeDict(map);
        assertEquals(expResult, result);
    }

}
