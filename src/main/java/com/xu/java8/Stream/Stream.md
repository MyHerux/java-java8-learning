##Stream类

    java.util.Stream 表示能应用在一组元素上一次执行的操作序列。
    Stream 操作分为中间操作或者最终操作两种，最终操作返回一特定类型的计算结果，
    而中间操作返回Stream本身，这样你就可以将多个操作依次串起来。Stream 的
    创建需要指定一个数据源，比如 java.util.Collection的子类，List或者Set，
    Map不支持。Stream的操作可以串行执行或者并行执行。

- **Stream的基本接口**

    ```
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
    ```

    ####Filter 过滤.

    Filter通过一个predicate接口来过滤并只保留符合条件的元素，该操作属于中间操作，
    所以我们可以在过滤后的结果来应用其他Stream操作（比如forEach）。forEach
    需要一个函数来对过滤后的元素依次执行。forEach是一个最终操作，所以我们不
    能在forEach之后来执行其他Stream操作。
    ```
        stringCollection
            .stream()
            .filter((s) -> s.startsWith("a"))
            .forEach(System.out::println);
        ##:aaa2
           aaa1
    ```

    ####Sorted 排序.

    Sorted是一个中间操作，返回的是排序好后的Stream。
    如果你不指定一个自定义的Comparator则会使用默认排序.
    ```
        stringCollection
            .stream()
            .sorted()
            .forEach(System.out::println);
        System.out.println(stringCollection);//原数据源不会被改变
        ##:aaa1
           aaa2
           bbb1
           bbb2
           bbb3
           ccc
           ddd1
           ddd2
           [ddd2, aaa2, bbb1, aaa1, bbb3, ccc, bbb2, ddd1]
    ```

    ####Map

    中间操作map会将元素根据指定的Function接口来依次将元素转成另外的对象.
    ```
        stringCollection
           .stream()
           .map(String::toUpperCase)
           .map((s)->s+" space")
           .sorted((a, b) -> b.compareTo(a))
           .forEach(System.out::println);
        ##:DDD2 space
           DDD1 space
           CCC space
           BBB3 space
           BBB2 space
           BBB1 space
           AAA2 space
           AAA1 space

    ```

    ####Match

    Stream提供了多种匹配操作，允许检测指定的Predicate是否匹配整个Stream。
    所有的匹配操作都是最终操作，并返回一个boolean类型的值。
    ```
        boolean anyStartsWithA = stringCollection
            .stream()
            .anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);      // true

        boolean allStartsWithA = stringCollection
            .stream()
            .allMatch((s) -> s.startsWith("a"));
        System.out.println(allStartsWithA);      // false

        boolean noneStartsWithZ = stringCollection
            .stream()
            .noneMatch((s) -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);      // true
        ##:true
           false
           true
    ```

    ####Count

    计数是一个最终操作，返回Stream中元素的个数，返回值类型是long。

    ```
        long startsWithB = stringCollection
            .stream()
            .filter((s) -> s.startsWith("b"))
            .count();
        System.out.println(startsWithB);
        ##:3
    ```
    ####Reduce

    Reduce是一个最终操作，允许通过指定的函数来讲stream中的多个元素规约为一个元素，规约后的结果是通过Optional接口表示的。
    ```
        Optional<String> reduced =
                stringCollection
                    .stream()
                    .sorted()
                    .reduce((s1, s2) -> s1 + "#" + s2);

        reduced.ifPresent(System.out::println);
        ##:aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2
    ```

- **并行stream和串行stream**

    ####并行stream

    ```
        List<String> values = new ArrayList<>(MAX);
        for (int i = 0; i < MAX; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));
    ```

    ####串行stream

    串行stream是在运行时将数据划分成了多个块，然后将数据块分配给合适的处理器去处理。
    只有当所有块都处理完成了，才会执行之后的代码。

    ```
        List<String> values = new ArrayList<>(MAX);
        for (int i = 0; i < MAX; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        
        long t0 = System.nanoTime();
        long count = values.parallelStream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
    ```

    时间结果比较：
    ```
    1000000
    sequential sort took: 717 ms
    1000000
    parallel sort took: 303 ms
    ```

- **IntStream接口**

    IntStream接口是stream的一种，继承了BaseStream接口。
    同理还有DoubleStream和LongStream。

    ####range
    ```
        IntStream.range(0, 10)
                .forEach(i -> {
                    if (i % 2 == 1) System.out.print(i+" ");
                });
        ##:1 3 5 7 9
    
        OptionalInt reduced1 =
                IntStream.range(0, 10)
                    .reduce((a, b) -> a + b);
        System.out.println(reduced1.getAsInt());
    
        int reduced2 =
            IntStream.range(0, 10)
                    .reduce(7, (a, b) -> a + b);
        System.out.println(reduced2);
        ##:45
           52
    ```

    ####sum
    ```
        System.out.println(IntStream.range(0, 10).sum());
    ```

- **Stream的应用**

    使用stream类来对map的value排序:
    ```
        Map<String, Integer> unsortMap = new HashMap<>();
        unsortMap.put("z", 10);
        unsortMap.put("b", 5);
        unsortMap.put("a", 6);
        unsortMap.put("c", 20);
        unsortMap.put("d", 1);
        unsortMap.put("e", 7);
        unsortMap.put("y", 8);
        unsortMap.put("n", 99);
        unsortMap.put("j", 50);
        unsortMap.put("m", 2);
        unsortMap.put("f", 9);
    ```

    ```
        public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
            Map<K, V> result = new LinkedHashMap<>();
            map.entrySet().parallelStream()
                .sorted((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()))
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
            return result;
        }
        System.out.println(sortByValue(unsortMap));
        ##:{n=99, j=50, c=20, z=10, f=9, y=8, e=7, a=6, b=5, m=2, d=1}
    ```
    使用stream类来处理list里面的json数据:
    ```
        List<Object> list = new ArrayList<>();
        JSONObject data1 = new JSONObject();
        data1.put("type", "支出");
        data1.put("money", 500);
        JSONObject data2 = new JSONObject();
        data2.put("type", "收入");
        data2.put("money", 1000);
        JSONObject data3 = new JSONObject();
        data3.put("type", "借贷");
        data3.put("money", 100);
        list.add(data1);
        list.add(data2);
        list.add(data3);
    ```

    ```
        /**
         * 按money的值来排列json
         */
        list.stream()
                .filter(x -> JSONObject.fromObject(x).containsKey("money"))
                .sorted((b, a) -> Integer.valueOf(JSONObject.fromObject(a).getInt("money")).compareTo(JSONObject.fromObject(b)
                        .getInt("money")))
                .forEach(System.out::println);
        /**
         * 找到最小的money
         */
        Integer min = list.stream()
                .filter(x -> JSONObject.fromObject(x).containsKey("money"))
                .map(x -> JSONObject.fromObject(x).getInt("money"))
                .sorted()
                .findFirst()
                .get();
        System.out.println(min);
        /**
         * 计算type的数目
         */
        Map<String, Integer> type_count = new HashMap<>();
        list.stream()
                .filter(x -> JSONObject.fromObject(x).containsKey("type"))
                .map(x -> JSONObject.fromObject(x).getString("type"))
                .forEach(x -> {
                    if (type_count.containsKey(x)) type_count.put(x, type_count.get(x) + 1);
                    else type_count.put(x, 1);
                });
        System.out.println(type_count.toString());
        ##:
        {"type":"收入","money":1000}
        {"type":"支出","money":500}
        {"type":"借贷","money":100}
        100
        {借贷=1, 收入=1, 支出=1}
    ```