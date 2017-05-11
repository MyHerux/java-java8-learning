package com.xu.java8.Function;

import java.util.function.Predicate;

/**
 * Created by xu on 2017/5/10.
 */
public class PredicateTest {

    public static void main(String[] args) {
        Predicate<String> predicate=x->x.startsWith("a");
        System.out.println(predicate.test("abc"));
    }
}
