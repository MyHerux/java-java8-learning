package com.xu.java8.Function;


import java.util.function.BiFunction;

public class BiFunctionTest {

    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
        System.out.println(add.apply(10,20));
    }
}
