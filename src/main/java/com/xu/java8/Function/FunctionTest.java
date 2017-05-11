package com.xu.java8.Function;


import java.util.function.Function;

public class FunctionTest {


    public static void main(String[] args) {

        Function<Integer, Integer> times2 = e -> e * 2;
        Function<Integer, Integer> squared = e -> e * e;
        Function<Integer, Integer> identity = Function.identity();

        //return 8 - > 4 * 2
        Integer a = times2.apply(4);
        System.out.println(a);
        //return 32 - > 4 ^ 2 * 2
        Integer b = times2.compose(squared).apply(4);
        System.out.println(b);
        //return 64 - > (4 * 2) ^ 2
        Integer c = times2.andThen(squared).apply(4);
        System.out.println(c);
        //return 4
        Integer d = identity.apply(4);
        System.out.println(d);
    }
}
