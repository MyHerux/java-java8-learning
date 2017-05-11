package com.xu.java8.Function;

import java.util.function.Supplier;

/**
 * Created by xu on 2017/5/10.
 */
public class SupplierTest {
    public static void main(String[] args) {
        Supplier<Integer> get= () -> 10;
        Integer a=get.get();
        System.out.println(a);
    }
}
