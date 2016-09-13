package com.xu.java8.Stream;

import java.util.*;

/**
 * Class java8-Stream接口
 *
 * @author hua xu
 * @version 1.0.0
 * @date 16/08/31
 */
public class Streams1 {

    public static void main(String[] args) {

        /**
         * java.util.Stream 表示能应用在一组元素上一次执行的操作序列。
         * Stream 操作分为中间操作或者最终操作两种，最终操作返回一特定类型的计算结果，
         * 而中间操作返回Stream本身，这样你就可以将多个操作依次串起来。Stream 的
         * 创建需要指定一个数据源，比如 java.util.Collection的子类，List或者Set，
         * Map不支持。Stream的操作可以串行执行或者并行执行。
         */
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

        /**
         * Filter 过滤.
         * 过滤通过一个predicate接口来过滤并只保留符合条件的元素，该操作属于中间操作，
         * 所以我们可以在过滤后的结果来应用其他Stream操作（比如forEach）。forEach
         * 需要一个函数来对过滤后的元素依次执行。forEach是一个最终操作，所以我们不
         * 能在forEach之后来执行其他Stream操作。
         */
        System.out.println("--------------filtering---------------");
        stringCollection
                .stream()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);

        /**
         * sorted 排序.
         * 是一个中间操作，返回的是排序好后的Stream。如果你不指定一个自定义的Comparator则会使用默认排序。
         */
        System.out.println("--------------sorting-----------------");
        stringCollection
                .stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println(stringCollection);//原数据源不会被改变

        /**
         * 中间操作map会将元素根据指定的Function接口来依次将元素转成另外的对象
         */
        System.out.println("--------------mapping-----------------");
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .map((s)->s+" space")
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);

        /**
         * Stream提供了多种匹配操作，允许检测指定的Predicate是否匹配整个Stream。
         * 所有的匹配操作都是最终操作，并返回一个boolean类型的值。
         */
        System.out.println("--------------matching----------------");
        boolean anyStartsWithA = stringCollection
                .stream()
                .anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);      // true

        boolean allStartsWithA = stringCollection
                .stream()
                .allMatch((s) -> s.startsWith("a"));
        System.out.println(allStartsWithA);      // false

        boolean noneStartsWithZ = stringCollection
                .stream()
                .noneMatch((s) -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);      // true

        /**
         * 计数是一个最终操作，返回Stream中元素的个数，返回值类型是long。
         */
        System.out.println("--------------counting----------------");
        long startsWithB = stringCollection
                .stream()
                .filter((s) -> s.startsWith("b"))
                .count();
        System.out.println(startsWithB);    // 3

        /**
         * 这是一个最终操作，允许通过指定的函数来讲stream中的多个元素规约为一个元素，规越后的结果是通过Optional接口表示的
         */
        System.out.println("--------------reducing----------------");
        Optional<String> reduced =
                stringCollection
                        .stream()
                        .sorted()
                        .reduce((s1, s2) -> s1 + "#" + s2);

        reduced.ifPresent(System.out::println);
        // "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"
    }

}
