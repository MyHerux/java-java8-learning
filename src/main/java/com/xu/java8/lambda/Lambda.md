##Lambda表达式
- Lambda表达式的使用

java8以前的字符串排列，创建一个匿名的比较器对象Comparator然后将其传递给sort方法
```
List<String> names= Arrays.asList("peter", "anna", "mike", "xenia");
Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });
```
java8使用lambda表达式就不需要匿名对象了
```
Collections.sort(names,(String a,String b)->{return b.compareTo(a);});
```
简化一下：对于函数体只有一行代码的，你可以去掉大括号{}以及return关键字
```
Collections.sort(names,(String a,String b)->b.compareTo(a));
```
Java编译器可以自动推导出参数类型，所以你可以不用再写一次类型
````
Collections.sort(names, (a, b) -> b.compareTo(a));
````
```
##:[xenia, peter, mike, anna]
```
对于null的处理
```
List<String> names2 = Arrays.asList("peter", null, "anna", "mike", "xenia");
names2.sort(Comparator.nullsLast(String::compareTo));
System.out.println(names2);
##:[anna, mike, peter, xenia, null]
```
- 函数式接口,方法，构造器

每一个lambda表达式都对应一个类型，通常是接口类型。而“函数式接口”是指仅仅只包含一个抽象方法的接口，
每一个该类型的lambda表达式都会被匹配到这个抽象方法。
因为默认方法不算抽象方法，所以你也可以给你的函数式接口添加默认方法。
我们可以将lambda表达式当作任意只包含一个抽象方法的接口类型，确保你的接口一定达到这个要求，你只需要给你的接口添加 @FunctionalInterface 注解，
编译器如果发现你标注了这个注解的接口有多于一个抽象方法的时候会报错的。

函数式接口

```
    @FunctionalInterface
    public static interface Converter<F, T> {
        T convert(F from);
    }
    /**原始的接口实现*/
    Converter<String, Integer> integerConverter1 = new Converter<String, Integer>() {
        @Override
        public Integer convert(String from) {
            return Integer.valueOf(from);
        }
    };

    /**使用lambda表达式实现接口*/
    Converter<String, Integer> integerConverter2 = (from) -> Integer.valueOf(from);

    Integer converted1 = integerConverter1.convert("123");
    Integer converted2 = integerConverter2.convert("123");
    System.out.println(converted1);   
    System.out.println(converted2);   
    ##:123
       123
    /**简写的lambda表达式*/
    Converter<String, Integer> integerConverter3 = Integer::valueOf;
    Integer converted3 = integerConverter3.convert("123");
    System.out.println(converted3);   
    ##:123
```
函数式方法
```
    static class Something {
        String startsWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }
    Something something = new Something();
    Converter<String, String> stringConverter = something::startsWith;
    String converted4 = stringConverter.convert("Java");
    System.out.println(converted4);    
    ##:j
```
函数式构造器
Java编译器会自动根据PersonFactory.create方法的签名来选择合适的构造函数。
```
    public class Person {
        public String firstName;
        public String lastName;

        public Person() {
        }

        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String toString(){
            return firstName+lastName;
        }
    }
```
```
    interface PersonFactory<P extends Person> {
        P create(String firstName, String lastName);
    }
     PersonFactory<Person> personFactory = Person::new;
     Person person = personFactory.create("xu", "hua");
     System.out.println(person.toString());
    ##:xuhua
```
- Lambda作用域
在lambda表达式中访问外层作用域和老版本的匿名对象中的方式很相似。
你可以直接访问标记了final的外层局部变量，或者实例的字段以及静态变量。
```
    static int outerStaticNum = 10;

    int outerNum;

    void testScopes() {
        
        /**变量num可以不用声明为final*/
        int num = 1;

        /**可以直接在lambda表达式中访问外层的局部变量*/
        Lambda2.Converter<Integer, String> stringConverter =
                        (from) -> String.valueOf(from + outerStaticNum+num);
        String convert = stringConverter.convert(2);
        System.out.println(convert);   
        ##:13
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
        ##:Before:outerNum-->13
           After:outerNum-->15
        
        String[] array = new String[1];
        Lambda2.Converter<Integer, String> stringConverter3 = (from) -> {
            array[0] = "Hi here";
            return String.valueOf(from);
        };
        stringConverter3.convert(23);
        System.out.println("\nBefore:array[0]-->" + array[0]);
        array[0] = "Hi there";
        System.out.println("After:array[0]-->" + array[0]);
        ##:Before:array[0]-->Hi here
           After:array[0]-->Hi there
    }
```