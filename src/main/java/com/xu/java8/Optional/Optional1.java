package com.xu.java8.Optional;

import com.xu.java8.lambda.Person;

import java.util.Optional;

/**
 * Class java8-Optional类:解决空引用的问题
 *
 * @author hua xu
 * @version 1.0.0
 * @date 16/08/31
 */
public class Optional1 {
    public static void main(String[] args) {

        /**创建一个空Optional对象*/
        Optional<String> optional = Optional.empty();

        /**创建Optional对象有一个非空值*/
        Person person = new Person("xu","hua");
        Optional<Person> optional2 = Optional.of(person);//如果person是null，将会立即抛出，而不是访问person的属性时获得一个潜在的错误
        System.out.println(optional2);

        /**判断对象是否存在*/
        System.out.println(optional.isPresent());
        System.out.println(optional2.isPresent());

        /**获取Optional里面的值*/
        System.out.println(optional2.get());

        /**如果Optional为空返回默认值*/
        System.out.println(optional.orElse("fallback"));
        optional.ifPresent(System.out::println);
        optional2.ifPresent(System.out::println);

        System.out.println(Optional.ofNullable(null).isPresent());
    }
}



