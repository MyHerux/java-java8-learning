# Java8-进阶


- Map的双重循环

    ```
        //对map的entry对象来做stream操作，使用两次forEach
        Map<String, Long> map = new HashMap<>();
        crowdMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .forEach(x -> x.entrySet().forEach(y -> {
                    if (map.containsKey(y.getKey()))
                        map.put(y.getKey(), map.get(y.getKey()) + y.getValue());
                    else map.put(y.getKey(), y.getValue());
                }));

        //对map的entry对象来做stream操作，使用flatMap将stream合并
        Map<String, Long> map = new HashMap<>();
        crowdMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .flatMap(x -> x.entrySet().stream())
                .forEach(y -> {
                    if (map.containsKey(y.getKey()))
                        map.put(y.getKey(), map.get(y.getKey()) + y.getValue());
                    else map.put(y.getKey(), y.getValue());
                });

        //使用map本身的foreach语句            
        Map<String, Long> map = new HashMap<>();
        crowdMap.forEach((key, value) -> value.forEach((x, y) -> {
            if (map.containsKey(x))
                map.put(x, map.get(x) + y);
            map.put(x, y);
        }));
    ```

- List的多次分组

    ```
        //使用groupingBy将ApproveRuleDetail对象分别按照item和detail分组，并计次
        Map<String, Map<String, Long>> detailMap = approveRuleDetailList.stream()
                .collect(Collectors
                        .groupingBy(ApproveRuleDetail::getItem, Collectors.
                                groupingBy(ApproveRuleDetail::getDetail, Collectors.counting())));
    ```

- List按照自定义条件分组

    ```
        //使用自定义的Function函数，将年龄按照每10岁分组
        Function<Integer, Integer> ageGroup = x -> x / 10;
        Map<Integer, List<StatisticsPipeline>> ageMap = statisticsPipelineList
                .stream()
                .collect(Collectors.groupingBy(y -> ageGroup.apply(y.getAge())));
    ```

    ```
        //将年龄按不同方式分组
        Function<Integer, Integer> ageCredit = x -> {
            if (x <= 18)
                return 18;
            else if (x >= 40)
                return 40;
            else return x;
        };

        //将StatisticsPipeline转化为suggestion
        ToDoubleFunction<StatisticsPipeline> mapper = StatisticsPipeline::getSuggestion;

        //将人群按照不同年龄分组，并计算每个年龄段的suggestion的平均值
        Map<Integer, Double> ageCreditMap = statisticsPipelineList
                .stream()
                .collect(Collectors.groupingBy(y -> ageCredit.apply(y.getAge()), Collectors.averagingDouble(mapper)));
    ```

- 多个数据求集合

    ```
        //合并数据
        private BiFunction<Integer[], ApprovePipeline, Integer[]> accumulator = (x, y) -> new Integer[]{
                x[0] + y.getAuth(), x[1] + y.getAntiFraud(), x[2] + y.getCreditRule(), x[3] + y.getModelReject(), x[4] + y.getSuggestion()
        };

        //合并集合
        private BinaryOperator<Integer[]> combiner = (x, y) -> new Integer[]{x[0] + y[0], x[1] + y[1], x[2] + y[2], x[3] + y[3], x[4] + y[4]};

        //将ApprovePipeline对象的不同数据相加
        Integer[] detail = approvePipelineList.stream().reduce(new Integer[]{0, 0, 0, 0, 0}, accumulator, combiner);
    ```

- 多个数据求集合-多重合并

    ```
        private BiFunction<Integer[], ApprovePipeline, Integer[]> accumulator = (x, y) -> new Integer[]{
            x[0] += y.getAuth(), x[1] += y.getAntiFraud(), x[2] += y.getCreditRule(), x[3] += y.getModelReject(), x[4] += y.getSuggestion()
        };
        //合并数据
        BiFunction<Integer[], Map.Entry<String, List<ApprovePipeline>>, Integer[]> newAccumulator = (x, y) -> {
            List<ApprovePipeline> pipelineList = y.getValue();
            Integer[] data = pipelineList.stream().reduce(new Integer[]{0, 0, 0, 0, 0}, accumulator, combiner);
            return new Integer[]{
                    x[0] += data[0], x[1] += data[1], x[2] += data[2], x[3] += data[3], x[4] += data[4]
            };
        };
        //最终reduce
        Integer[] total = channelMap.entrySet().stream().reduce(new Integer[]{0, 0, 0, 0, 0}, newAccumulator, combiner);
    ```

- map最大多项和

    ```
    Long hourC3 = hourMap.entrySet().stream().mapToLong(Map.Entry::getValue).sorted().limit(3).sum();
    ```