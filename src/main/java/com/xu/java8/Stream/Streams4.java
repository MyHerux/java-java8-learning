package com.xu.java8.Stream;

import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * Class IntStream接口
 *
 * @author hua xu
 * @version 1.0.0
 * @date 16/08/31
 */
public class Streams4 {
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 1) {
                System.out.print(i+" ");
            }
        }

        System.out.println();
        IntStream.range(0, 10)
                .forEach(i -> {
                    if (i % 2 == 1) System.out.print(i+" ");
                });
        System.out.println();
        System.out.println(IntStream.range(0, 10).sum());
        System.out.println();
        IntStream.range(0, 10)
                .filter(i -> i % 2 == 1)
                .forEach((i)-> System.out.print(i+" "));

        System.out.println();
        OptionalInt reduced1 =
                IntStream.range(0, 10)
                        .reduce((a, b) -> a + b);
        System.out.println(reduced1.getAsInt());

        int reduced2 =
                IntStream.range(0, 10)
                        .reduce(7, (a, b) -> a + b);
        System.out.println(reduced2);
    }
}


