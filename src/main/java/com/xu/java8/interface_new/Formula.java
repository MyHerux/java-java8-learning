package com.xu.java8.interface_new;

/**
 * 接口的默认方法
 */
interface Formula {
    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}

