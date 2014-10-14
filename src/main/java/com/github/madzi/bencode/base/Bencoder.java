package com.github.madzi.bencode.base;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Base implementation of Bencode encoder.
 */
public class Bencoder {

    /**
     * Encode integer value.
     * 
     * @param val integer value
     * @return encoded string
     */
    public static String writeInt(final Long val) {
        StringBuilder sb = new StringBuilder();
        sb.append(BConst.INT_START);
        sb.append(val);
        sb.append(BConst.COMMON_END);
        return sb.toString();
    }

    /**
     * Encode array of bytes.
     * 
     * @param bytes array of bytes
     * @return encoded string
     */
    public static String writeByteArray(final byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        sb.append(bytes == null ? 0 : bytes.length);
        sb.append(BConst.COLON);
        if (bytes != null) {
            for (int idx = 0; idx < bytes.length; ++idx) {
                sb.append((char) bytes[idx]);
            }
        }
        return sb.toString();
    }

    /**
     * Encode iterable entity.
     * 
     * @param iterable iterable entity
     * @return encoded string
     */
    public static String writeList(final Iterable<? extends Object> iterable) {
        StringBuilder sb = new StringBuilder();
        sb.append(BConst.LIST_START);
        if (iterable != null) {
            Iterator<? extends Object> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                sb.append(writeObj(iterator.next()));
            }
        }
        sb.append(BConst.COMMON_END);
        return sb.toString();
    }

    /**
     * Encode dictionary.
     * 
     * @param map list of pair (key, value)
     * @return encoded string
     */
    public static String writeDict(final Map<String, ? extends Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(BConst.DICT_START);
        if (map != null) {
            TreeMap<String, Object> tree = new TreeMap<String, Object>(map);
            for(String key : tree.keySet()) {
                sb.append(writeByteArray(key.getBytes(BConst.ASCII)));
                sb.append(writeObj(tree.get(key)));
            }
        }
        sb.append(BConst.COMMON_END);
        return sb.toString();
    }

    /**
     * Encode ojbect.
     * 
     * @param object some object
     * @return encoded string
     */
    public static String writeObj(final Object object) {
        String result = null;
        if (object instanceof Long) {
            Long longObj = (Long) object;
            result = writeInt(longObj);
        } else if (object instanceof Integer) {
            Integer intObj = (Integer) object;
            result = writeInt(intObj.longValue());
        } else if (object instanceof Short) {
            Short shObj = (Short) object;
            result = writeInt(shObj.longValue());
        } else if (object instanceof Byte) {
            Byte bObj = (Byte) object;
            result = writeInt(bObj.longValue());
        } else if (object instanceof String) {
            String strObj = (String) object;
            result = writeByteArray(strObj.getBytes(BConst.ASCII));
        } else if (object instanceof byte[]) {
            byte[] bytes = (byte[]) object;
            result = writeByteArray(bytes);
        } else if (object instanceof Iterable) {
            Iterable itObj = (Iterable) object;
            result = writeList(itObj);
        } else if (object instanceof Map) {
            Map mObj = (Map) object;
            result = writeDict(mObj);
        } else {
            throw new IllegalArgumentException(String
                    .format("Unsupport class for serialization: %s.", object.getClass().getName()));
        }
        return result;
    }

}
