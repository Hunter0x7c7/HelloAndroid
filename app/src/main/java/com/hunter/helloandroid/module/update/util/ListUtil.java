package com.hunter.helloandroid.module.update.util;


import com.hunter.helloandroid.module.update.bean.TupleTwo;

import org.json.JSONArray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/26 16:50
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class ListUtil {

    /**
     * 判断是否为空
     *
     * @param list 判断的集合
     * @return true为空，false为非空
     */
    public static boolean isEmpty(List list) {
        boolean isEmpty = true;
        if (list != null) {
            isEmpty = list.size() <= 0;
        }
        return isEmpty;
    }

    /**
     * 判断是否为空
     *
     * @param list 判断的集合
     * @return true为空，false为非空
     */
    public static boolean isEmpty(Map list) {
        boolean isEmpty = true;
        if (list != null) {
            isEmpty = list.size() <= 0;
        }
        return isEmpty;
    }

    /**
     * 判断是否为空
     *
     * @param list 判断的集合
     * @return true为空，false为非空
     */
    public static boolean isEmpty(Set list) {
        boolean isEmpty = true;
        if (list != null) {
            isEmpty = list.size() <= 0;
        }
        return isEmpty;
    }

    /**
     * 判断是否为空
     *
     * @param obj 判断的数组对象
     * @return true为空，false为非空
     */
    public static boolean isEmpty(Object[] obj) {
        boolean isEmpty = true;
        if (obj != null) {
            isEmpty = obj.length <= 0;
        }
        return isEmpty;
    }

    /**
     * 判断是否为空
     *
     * @param obj 判断的数组对象
     * @return true为空，false为非空
     */
    public static boolean isEmpty(int[] obj) {
        boolean isEmpty = true;
        if (obj != null) {
            isEmpty = obj.length <= 0;
        }
        return isEmpty;
    }

    /**
     * 判断是否为空
     *
     * @param obj 判断的数组对象
     * @return true为空，false为非空
     */
    public static boolean isEmpty(byte[] obj) {
        boolean isEmpty = true;
        if (obj != null) {
            isEmpty = obj.length <= 0;
        }
        return isEmpty;
    }

    /**
     * 判断是否为空
     *
     * @param obj 判断的数组对象
     * @return true为空，false为非空
     */
    public static boolean isEmpty(JSONArray obj) {
        boolean isEmpty = true;
        if (obj != null) {
            isEmpty = obj.length() <= 0;
        }
        return isEmpty;
    }

    /**
     * 深度复制集合
     */
    public static <T extends Serializable> Map<String, T> deepCopy(Map<String, T> src) throws Exception {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        Map<String, T> dest = (Map<String, T>) in.readObject();

//        Set<Map.Entry<String, T>> entries = dest.entrySet();
//        for (Map.Entry<String, T> map : entries) {
//            T value = map.getValue();
//            if (value instanceof List) {
//                List list = deepCopy((List) value);
//                map.setValue((T) list);
//            }
//        }
        return dest;
    }

    /**
     * 深度复制集合
     */
    public static <T extends Serializable> List<T> deepCopy(List<T> src) throws Exception {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

    /**
     * 深度复制集合
     */
    public static <T extends Serializable> T deepCopy(T obj) {
        T result = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(obj);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            result = (T) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取Map集合中position的Key和Value
     */
    public static <K, V> TupleTwo<K, V> getMapKeyValue(Map<K, V> map, int position) {
        K key = null;
        V value = null;
        if (map != null && position >= 0) {
            int index = 0;
            Set<Map.Entry<K, V>> entrySet = map.entrySet();
            for (Map.Entry<K, V> entry : entrySet) {
                if (index == position) {
                    key = entry.getKey();
                    value = entry.getValue();
                    break;
                }
                index++;
            }
        }
        return new TupleTwo<>(key, value);
    }

    /**
     * 获取Map集合中position的Key
     */
    public static <T> T getMapKey(Map<T, ?> map, int position) {
        Set<? extends Map.Entry<T, ?>> entrySet = map.entrySet();
        T key = null;
        int index = 0;
        for (Map.Entry<T, ?> entry : entrySet) {
            if (index == position) {
                key = entry.getKey();
                break;
            }
            index++;
        }
        return key;
    }

    public static <T> T getMapKey(Class<T> t, Map map, int position) {
        Set<Map.Entry> entrySet = map.entrySet();
        T key = null;
        int index = 0;
        for (Map.Entry entry : entrySet) {
            if (index == position) {
                key = (T) entry.getKey();
                break;
            }
            index++;
        }
        return key;
    }

    public static <T> List<T> toList(Set<T> set) {
        return Arrays.asList((T[]) set.toArray());
    }


    /**
     * 获取Map集合中指定Key的position
     */
    public static <T> int getMapIndex(Map<T, ?> map, T key) {
        Set<? extends Map.Entry<T, ?>> entrySet = map.entrySet();
        int index = -1;
        if (key != null) {
            index++;
            for (Map.Entry<T, ?> entry : entrySet) {
                if (key.equals(entry.getKey())) {
                    break;
                }
                index++;
            }
        }
        return index;
    }

}
