package com.xu.java8.Stream;

import net.sf.json.JSONObject;

import java.util.*;

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

        /**
         * 对list里面的json处理
         */
        List<Object> list = new ArrayList<>();
        JSONObject data1 = new JSONObject();
        data1.put("type", "支出");
        data1.put("money", 500);
        JSONObject data2 = new JSONObject();
        data2.put("type", "收入");
        data2.put("money", 1000);
        JSONObject data3 = new JSONObject();
        data3.put("type", "借贷");
        data3.put("money", 100);
        list.add(data1);
        list.add(data2);
        list.add(data3);
        /**
         * 按money的值来排列json
         */
        list.stream()
                .filter(x -> JSONObject.fromObject(x).containsKey("money"))
                .sorted((b, a) -> Integer.valueOf(JSONObject.fromObject(a).getInt("money")).compareTo(JSONObject.fromObject(b)
                        .getInt("money")))
                .forEach(System.out::println);
        /**
         * 找到最小的money
         */
        Integer min=list.stream()
                .filter(x -> JSONObject.fromObject(x).containsKey("money"))
                .map(x->JSONObject.fromObject(x).getInt("money"))
                .sorted()
                .findFirst()
                .get();
        System.out.println(min);
        /**
         * 计算type的数目
         */
        Map<String, Integer> type_count = new HashMap<>();
        list.stream()
                .filter(x -> JSONObject.fromObject(x).containsKey("type"))
                .map(x -> JSONObject.fromObject(x).getString("type"))
                .forEach(x -> {
                    if (type_count.containsKey(x)) type_count.put(x, type_count.get(x) + 1);
                    else type_count.put(x, 1);
                });
        System.out.println(type_count.toString());
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
