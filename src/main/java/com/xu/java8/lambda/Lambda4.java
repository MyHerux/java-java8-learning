package com.xu.java8.lambda;

/**
 * Class Lambda作用域。访问局部变量,对象字段与静态变量
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
    static int outerStaticNum = 10;

    int outerNum;

    void testScopes() {

        /**变量num可以不用声明为final*/
        int num = 1;

        /**可以直接在lambda表达式中访问外层的局部变量*/
        Lambda2.Converter<Integer, String> stringConverter =
                (from) -> String.valueOf(from + outerStaticNum+num);
        String convert = stringConverter.convert(2);
        System.out.println(convert);    // 3
        /**但是num必须不可被后面的代码修改（即隐性的具有final的语义），否则编译出错*/
        //num=3;

        /**lambda内部对于实例的字段以及静态变量是即可读又可写*/
        Lambda2.Converter<Integer, String> stringConverter2 = (from) -> {
            outerNum = 13;
            return String.valueOf(from + outerNum);
        };
        System.out.println(stringConverter2.convert(2));
        System.out.println("\nBefore:outerNum-->" + outerNum);
        outerNum = 15;
        System.out.println("After:outerNum-->" + outerNum);

        String[] array = new String[1];
        Lambda2.Converter<Integer, String> stringConverter3 = (from) -> {
            array[0] = "Hi here";
            return String.valueOf(from);
        };
        stringConverter3.convert(23);
        System.out.println("\nBefore:array[0]-->" + array[0]);
        array[0] = "Hi there";
        System.out.println("After:array[0]-->" + array[0]);
    }

    public static void main(String[] args) {
        new Lambda4().testScopes();
    }
}


