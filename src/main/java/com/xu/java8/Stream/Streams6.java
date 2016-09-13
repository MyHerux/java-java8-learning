package com.xu.java8.Stream;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xu on 2016/9/13.
 */
public class Streams6 {
    public static void main(String args[]) {
        Map<String, Integer> unsortMap = new HashMap<>();
        unsortMap.put("z", 10);
        unsortMap.put("b", 5);
        unsortMap.put("a", 6);
        unsortMap.put("c", 20);
        unsortMap.put("d", 1);
        unsortMap.put("e", 7);
        unsortMap.put("y", 8);
        unsortMap.put("n", 99);
        unsortMap.put("j", 50);
        unsortMap.put("m", 2);
        unsortMap.put("f", 9);
        System.out.println(sortByValue(unsortMap));
    }

    /**
     * 使用stream类来对map的value排序(并行排序,逆序)
     */
    public static <K, V extends Comparable<? super V>> Map<K, V>
    sortByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet().parallelStream().sorted((o1, o2) -> (o2.getValue()).compareTo(o1.getValue())).forEachOrdered(x ->
                result.put(x.getKey(), x
                        .getValue()));
        return result;
    }
}
