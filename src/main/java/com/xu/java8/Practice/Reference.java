package com.xu.java8.Practice;

import java.util.function.Predicate;
import java.util.stream.Stream;


public class Reference {
    public static <T> Predicate<T> as(Predicate<T> predicate) {
        return predicate;
    }
    public static void main(String[] args) {
        long count = Stream.of("A", "", "B").filter(s -> !s.isEmpty()).count();
        System.out.println(count);
        long count1 = Stream.of("A", "", "B").filter(as(String::isEmpty).negate()).count();
        System.out.println(count1);
        long count2 = Stream.of("A", "", "B").filter(as(String::isEmpty).negate().and("A"::equals)).count();
        System.out.println(count2);
    }
}
