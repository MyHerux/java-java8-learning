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
