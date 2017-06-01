# Java8-进阶

## 函数式接口

> 只包含一个抽象方法的接口

- `Function<T, R>`

    > 接受一个输入参数，返回一个结果。

    Function接口包含以下方法：

    定义两个比较简单的函数：`times2` 和 `squared`

    ```
    Function<Integer, Integer> times2 = e -> e * 2;
    Function<Integer, Integer> squared = e -> e * e;
    ```

    - `R apply(T t)`

        执行函数

        ```
        //return 8 - > 4 * 2
        Integer a = times2.apply(4);
        System.out.println(a);
        ```

    - `compose`

        先执行参数里面的操作，然后执行调用者
        ```
        //return 32 - > 4 ^ 2 * 2
        Integer b = times2.compose(squared).apply(4);
        System.out.println(b);
        ```
    - `andThen`

        先执行调用者操作，再执行参数操作
        ```
        //return 64 - > (4 * 2) ^ 2
        Integer c = times2.andThen(squared).apply(4);
        System.out.println(c);
        ```
    - `identity`

        总是返回传入的参数本身
        ```
        //return 4
        Integer d = identity.apply(4);
        System.out.println(d);
        ```

- `BiFunction<T, U, R>`

    > 接受输入两个参数，返回一个结果

    - `R apply(T t, U u)`

        ```
        BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
        //return 30
        System.out.println(add.apply(10,20));
        ```
- `Supplier<T>`

    > 无参数，返回一个结果。

    - `T get()`

        ```
        Supplier<Integer> get= () -> 10;
        //return 10
        Integer a=get.get();
        ```
- `Consumer<T>`

    > 代表了接受一个输入参数并且无返回的操作

    - `void accept(T t)`

        ```
        //return void
        Consumer<Integer> accept=x->{};
        ```
- `BiConsumer<T, U>`

    > 代表了一个接受两个输入参数的操作，并且不返回任何结果

    - `void accept(T t, U u)`

        ```
        //return void
        BiConsumer<Integer,Integer> accept=(x,y)->{};
        ```
- `BinaryOperator<T> extends BiFunction<T,T,T>`

    > 一个作用于于两个同类型操作符的操作，并且返回了操作符同类型的结果

    定义了两个静态方法：`minBy`,`maxBy`

- `Predicate<T>`

    > 接受一个输入参数，返回一个布尔值结果。

    - `test`

        ```
        Predicate<String> predicate=x->x.startsWith("a");
        //return ture
        System.out.println(predicate.test("abc"));
        ```
    
## Stream接口

- `Collector接口`

    > Collector是Stream的可变减少操作接口

    Collector<T, A, R>接受三个泛型参数，对可变减少操作的数据类型作相应限制：

    T：输入元素类型

    A：缩减操作的可变累积类型（通常隐藏为实现细节）

    R：可变减少操作的结果类型

    Collector接口声明了4个函数，这四个函数一起协调执行以将元素目累积到可变结果容器中，并且可以选择地对结果进行最终的变换:
    - `Supplier<A> supplier()`: 创建新的结果结
    - `BiConsumer<A, T> accumulator()`: 将元素添加到结果容器
    - `BinaryOperator<A> combiner()`: 将两个结果容器合并为一个结果容器
    - `Function<A, R> finisher()`: 对结果容器作相应的变换

    在Collector接口的characteristics方法内，可以对Collector声明相关约束:
    - `Set<Characteristics> characteristics()`:

        Characteristics是Collector内的一个枚举类，声明了CONCURRENT、UNORDERED、IDENTITY_FINISH等三个属性，用来约束Collector的属性:

        - CONCURRENT：表示此收集器支持并发，意味着允许在多个线程中，累加器可以调用结果容器
        - UNORDERED：表示收集器并不按照Stream中的元素输入顺序执行
        - IDENTITY_FINISH：表示finisher实现的是识别功能，可忽略。

        > 如果一个容器仅声明CONCURRENT属性，而不是UNORDERED属性，那么该容器仅仅支持无序的Stream在多线程中执行。

## 定义自己的Stream

- `collect`

    `collect`有两个接口:

    ```
    <R> R collect(Supplier<R> supplier,
                  BiConsumer<R, ? super T> accumulator,
                  BiConsumer<R, R> combiner);
    <R, A> R collect(Collector<? super T, A, R> collector);              
    ```              
    - `<1> <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner)`

        > Supplier supplier是一个工厂函数，用来生成一个新的容器；

        > BiConsumer accumulator也是一个函数，用来把Stream中的元素添加到结果容器中；

        > BiConsumer combiner还是一个函数，用来把中间状态的多个结果容器合并成为一个（并发的时候会用到）

        ```
        Supplier<List<String>> supplier = ArrayList::new;
        BiConsumer<List<String>, String> accumulator = List::add;
        BiConsumer<List<String>, List<String>> combiner = List::addAll;

        //return [aaa1, aaa1],实现了Collectors.toCollection
        List<String> list1 = stringCollection.stream()
                .filter(x -> x.startsWith("a"))
                .collect(supplier, accumulator, combiner);
        ```

    - `<2> <R, A> R collect(Collector<? super T, A, R> collector)`

        `Collectors`是Java已经提供好的一些工具方法：

        ```
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa1");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        ```

        转换成其他集合:
        - `toList`

            ```
            //return [aaa1, aaa1]
            stringCollection.stream()
                .filter(x -> x.startsWith("a")).collect(Collectors.toList())
            ```    
        - `toSet`

            ```
            //return [aaa1]
            stringCollection.stream()
                .filter(x -> x.startsWith("a")).collect(Collectors.toSet())
            ```
        - `toCollection`

            接口：
            ```
            public static <T, C extends Collection<T>> Collector<T, ?, C> toCollection(Supplier<C> collectionFactory)
            ```
            实现：
            ```
            //return [aaa1, aaa1]
            List<String> list = stringCollection.stream()
                .filter(x -> x.startsWith("a"))
                .collect(Collectors.toCollection(ArrayList::new));
            ```
        - `toMap`
            
            接口：
            ```
            public static <T, K, U>
            Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends U> valueMapper) 
            ```
            实现：
            ```
            //return {aaa1=aaa1_xu}
            Function<String, String> xu = x -> x + "_xu";
            Map<String, String> map = stringCollection.stream()
                .filter(x -> x.startsWith("a"))
                .distinct()
                .collect(Collectors.toMap(Function.identity(), xu));
            ```
        
        转成值:    
        - `averagingDouble`:求平均值，Stream的元素类型为double
        - `averagingInt`:求平均值，Stream的元素类型为int
        - `averagingLong`:求平均值，Stream的元素类型为long
        - `counting`:Stream的元素个数
        - `maxBy`:在指定条件下的，Stream的最大元素
        - `minBy`:在指定条件下的，Stream的最小元素
        - `reducing`: reduce操作
        - `summarizingDouble`:统计Stream的数据(double)状态，其中包括count，min，max，sum和平均。
        - `summarizingInt`:统计Stream的数据(int)状态，其中包括count，min，max，sum和平均。
        - `summarizingLong`:统计Stream的数据(long)状态，其中包括count，min，max，sum和平均。
        - `summingDouble`:求和，Stream的元素类型为double
        - `summingInt`:求和，Stream的元素类型为int
        - `summingLong`:求和，Stream的元素类型为long

        数据分区:

        - `partitioningBy`

            接口：
            ```
            public static <T> Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate)

            public static <T, D, A>
            Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate,
                                                               Collector<? super T, A, D> downstream)
            ```
            实现：
            ```
            Predicate<String> startA = x -> x.startsWith("a");
            //return {false=[ddd2, bbb1], true=[aaa1, aaa1]}
            Map<Boolean, List<String>> map2 = stringCollection.stream()
                    .collect(Collectors.partitioningBy(startA));
            
            //return {false={false=[ddd2], true=[bbb1]}, true={false=[], true=[aaa1, aaa1]}}
            Predicate<String> end1 = x -> x.endsWith("1");
            Map<Boolean, Map<Boolean, List<String>>> map3 = stringCollection.stream()
                    .collect(Collectors.partitioningBy(startA, Collectors.partitioningBy(end1)));
            ```
        
        数据分组：

        - `groupingBy`

            接口：
            ```
            public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier)

            public static <T, K, A, D>
            Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier,
                                            Collector<? super T, A, D> downstream)

            public static <T, K, D, A, M extends Map<K, D>>
            Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier,Supplier<M> mapFactory,
                                    Collector<? super T, A, D> downstream)
            ```
            
            实现：
            ```
            //rerurn {a=[aaa1, aaa1], b=[bbb1], d=[ddd2]}
            Function<String, String> stringStart = x -> String.valueOf(x.charAt(0));
            Map<String, List<String>> map4 = stringCollection.stream()
                    .collect(Collectors.groupingBy(stringStart));

            //rerurn {ddd2=1, bbb1=1, aaa1=2}
            Map<String, Long> map5 = stringCollection.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            
            //rerurn {d=1, a=2, b=1}
            Map<String, Long> map6 = stringCollection.stream()
                    .collect(Collectors.groupingBy(stringStart, LinkedHashMap::new, Collectors.counting()));
            ```

- `reduce`

    `reduce`有三个接口：

    ```
    Optional<T> reduce(BinaryOperator<T> accumulator);

    <U> U reduce(U identity,
                 BiFunction<U, ? super T, U> accumulator,
                 BinaryOperator<U> combiner);

    T reduce(T identity, BinaryOperator<T> accumulator);
    ```

    - `<1> Optional<T> reduce(BinaryOperator<T> accumulator)`

        ```
        BinaryOperator<String> binaryOperator = (x, y) -> x + y;
        //rerurn ddd2aaa1bbb1aaa1
        String reduceStr1 = stringCollection.stream().reduce(binaryOperator).orElse("");
        ```

    - `<2> T reduce(T identity, BinaryOperator<T> accumulator)`

        ```
        //return start:ddd2aaa1bbb1aaa1
        String reduceStr2=stringCollection.stream().reduce("start:",binaryOperator);
        ```

    - `<3> <U> U reduce(U identity,BiFunction<U, ? super T, U> accumulator,BinaryOperator<U> combiner)`

        > 第一个参数返回实例u，传递你要返回的U类型对象的初始化实例u

        > BiFunction accumulator,负责数据的累加

        > BinaryOperator combiner,负责在并行情况下最后合并每个reduce线程的结果

        ```
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(10, 20));
        personList.add(new Person(20, 30));
        personList.add(new Person(30, 50));
        
        BiFunction<Person, Person, Person> biFunction = (x, y) -> new Person(x.getAge() + y.getAge(), x.getRate() + y.getRate());
        BinaryOperator<Person> binaryOperator1 = (x, y) -> new Person(x.getAge() + y.getAge(), x.getRate() + y.getRate());
        Person total = personList.stream().reduce(new Person(0, 0), biFunction, binaryOperator1);
        System.out.println("total:"+total);
        ```