package com.xu.java8.lambda;

import java.util.*;

/**
 * Class lambda表达式
 *
 * @author hua xu
 * @version 1.0.0
 * @date 16/08/30
 */
public class Lambda1 {
    public static void main(String args[]){
        List<String> names= Arrays.asList("peter", "anna", "mike", "xenia");

        /**java8以前的字符串排列，创建一个匿名的比较器对象Comparator然后将其传递给sort方法*/
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });
        /**java8使用lambda表达式就不需要匿名对象了*/
        Collections.sort(names,(String a,String b)->{return b.compareTo(a);});

        /**对于函数体只有一行代码的，你可以去掉大括号{}以及return关键字*/
        Collections.sort(names,(String a,String b)->b.compareTo(a));

        /**Java编译器可以自动推导出参数类型，所以你可以不用再写一次类型*/
        Collections.sort(names, (a, b) -> b.compareTo(a));

        System.out.println(names);

        names.sort(Collections.reverseOrder());

        System.out.println(names);

        List<String> names2 = Arrays.asList("peter", null, "anna", "mike", "xenia");
        names2.sort(Comparator.nullsLast(String::compareTo));
        System.out.println(names2);

        List<String> names3 = null;

        Optional.ofNullable(names3).ifPresent(list -> list.sort(Comparator.naturalOrder()));

        System.out.println(names3);
    }
}
