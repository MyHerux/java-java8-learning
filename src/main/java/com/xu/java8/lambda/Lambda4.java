package com.xu.java8.lambda;

/**
 * Class Lambda作用域。
 *
 * @author hua xu
 * @version 1.0.0
 * @date 16/08/30
 */
public class Lambda4 {
    /**
     * 在lambda表达式中访问外层作用域和老版本的匿名对象中的方式很相似。
     * 你可以直接访问标记了final的外层局部变量，或者实例的字段以及静态变量。
     */
    static int outerStaticNum;

    int outerNum;

    void testScopes() {
        int num = 1;

        Lambda2.Converter<Integer, String> stringConverter =
                (from) -> String.valueOf(from + num);

        String convert = stringConverter.convert(2);
        System.out.println(convert);    // 3

        Lambda2.Converter<Integer, String> stringConverter2 = (from) -> {
            outerNum = 13;
            return String.valueOf(from);
        };

        String[] array = new String[1];
        Lambda2.Converter<Integer, String> stringConverter3 = (from) -> {
            array[0] = "Hi there";
            return String.valueOf(from);
        };

        stringConverter3.convert(23);

        System.out.println(array[0]);
    }
    public static void main(String[] args) {
        new Lambda4().testScopes();
    }
}


