package com.xu.java8.lambda;

/**
 * Class 函数式接口,方法，构造器
 *
 * @author hua xu
 * @version 1.0.0
 * @date 16/08/30
 */
public class Lambda2 {

    /**
     * 每一个lambda表达式都对应一个类型，通常是接口类型。而“函数式接口”是指仅仅只包含一个抽象方法的接口，
     * 每一个该类型的lambda表达式都会被匹配到这个抽象方法。
     * 因为 默认方法 不算抽象方法，所以你也可以给你的函数式接口添加默认方法。我们可以将lambda表达式当作任意只包含一个抽象方法的接口类型，
     * 确保你的接口一定达到这个要求，你只需要给你的接口添加 @FunctionalInterface 注解，
     * 编译器如果发现你标注了这个注解的接口有多于一个抽象方法的时候会报错的。
     */

    @FunctionalInterface
    public static interface Converter<F, T> {
        T convert(F from);
    }

    static class Something {
        String startsWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }

    interface PersonFactory<P extends Person> {
        P create(String firstName, String lastName);
    }

    public static void main(String[] args) {

        /**基本的接口实现*/
        Converter<String, Integer> integerConverter1 = new Converter<String, Integer>() {
            @Override
            public Integer convert(String from) {
                return Integer.valueOf(from);
            }
        };

        /**使用lambda表达式*/
        Converter<String, Integer> integerConverter2 = (from) -> Integer.valueOf(from);

        Integer converted1 = integerConverter1.convert("123");
        Integer converted2 = integerConverter2.convert("123");
        System.out.println(converted1);   // result: 123
        System.out.println(converted2);   // result: 123

        /**简写的lambda表达式*/
        Converter<String, Integer> integerConverter3 = Integer::valueOf;
        Integer converted3 = integerConverter3.convert("123");
        System.out.println(converted3);   // result: 123

        /**简写的lambda表达式:method*/
        Something something = new Something();
        Converter<String, String> stringConverter = something::startsWith;
        String converted4 = stringConverter.convert("Java");
        System.out.println(converted4);    // result J

        /**简写的lambda表达式:constructor*/
        Person p=new Person("xu","hua");
        Person p2=new Person("xu","hua","");
        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("xu", "hua");//Java编译器会自动根据PersonFactory.create方法的签名来选择合适的构造函数。
        System.out.println(person.toString());
    }
}


