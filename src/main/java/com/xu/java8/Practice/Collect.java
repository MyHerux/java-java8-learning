package com.xu.java8.Practice;


import com.xu.java8.Stream.Person;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;

public class Collect {

    public static void main(String[] args) {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa1");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");

        System.out.println(stringCollection);

        System.out.println(stringCollection.stream().filter(x -> x.startsWith("a")).collect(Collectors.toList()));

        System.out.println(stringCollection.stream().filter(x -> x.startsWith("a")).collect(Collectors.toSet()));

        List<String> list = stringCollection.stream()
                .filter(x -> x.startsWith("a"))
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(list);

        Function<String, String> xu = x -> x + "_xu";
        Map<String, String> map = stringCollection.stream()
                .filter(x -> x.startsWith("a"))
                .distinct()
                .collect(Collectors.toMap(Function.identity(), xu));
        System.out.println(map);

        Predicate<String> startA = x -> x.startsWith("a");
        Map<Boolean, List<String>> map2 = stringCollection.stream()
                .collect(Collectors.partitioningBy(startA));
        System.out.println(map2);

        Predicate<String> end1 = x -> x.endsWith("1");
        Map<Boolean, Map<Boolean, List<String>>> map3 = stringCollection.stream()
                .collect(Collectors.partitioningBy(startA, Collectors.partitioningBy(end1)));
        System.out.println("map3:" + map3);

        Function<String, String> stringStart = x -> String.valueOf(x.charAt(0));
        Map<String, List<String>> map4 = stringCollection.stream()
                .collect(Collectors.groupingBy(stringStart));
        System.out.println("map4:" + map4);

        Map<String, Long> map5 = stringCollection.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("map5:" + map5);

        Map<String, Long> map6 = stringCollection.stream()
                .collect(Collectors.groupingBy(stringStart, LinkedHashMap::new, Collectors.counting()));
        System.out.println("map6:" + map6);

        Supplier<List<String>> supplier = ArrayList::new;
        BiConsumer<List<String>, String> accumulator = List::add;
        BiConsumer<List<String>, List<String>> combiner = List::addAll;
        List<String> list1 = stringCollection.stream()
                .filter(x -> x.startsWith("a"))
                .collect(supplier, accumulator, combiner);
        System.out.println("list1:" + list1);

        BinaryOperator<String> binaryOperator = (x, y) -> x + y;
        String reduceStr1 = stringCollection.stream().reduce(binaryOperator).orElse("");
        System.out.println("reduceStr1: " + reduceStr1);

        String reduceStr2 = stringCollection.stream().reduce("start:", binaryOperator);
        System.out.println("reduceStr1: " + reduceStr2);

        List<Person> personList = new ArrayList<>();
        personList.add(new Person(10, 20));
        personList.add(new Person(20, 30));
        personList.add(new Person(30, 50));

        BiFunction<Person, Person, Person> biFunction = (x, y) -> new Person(x.getAge() + y.getAge(), x.getRate() + y.getRate());
        BinaryOperator<Person> binaryOperator1 = (x, y) -> new Person(x.getAge() + y.getAge(), x.getRate() + y.getRate());
        Person total = personList.parallelStream().reduce(new Person(0, 0), biFunction, binaryOperator1);
        System.out.println("total:" + total);


    }
}
