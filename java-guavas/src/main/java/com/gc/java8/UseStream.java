package com.gc.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 用法：
 * 1、map/flatMap
 */
public class UseStream {


    public static void streamMapTest() {
        // 将一个集合或者数组中的数据映射为另一种数据
        Stream<String> stream = Stream.of(new String[]{"a", "b", "c"});
        stream.map(String::toUpperCase).forEach(System.out::println);
        // 集合转换
        Stream<List> listStream = Stream.of(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );
        Stream<Integer> mapStream = listStream.map(List::size);
        mapStream.forEach(System.out::println);
//        Stream<Object> flatMapStream = listStream.flatMap(Collection::stream);
    }

    public static void filterTest() {
        Integer[] nums = new Integer[]{12, 54, 78, 98, 58, 89};
        // forEach是一个终止操作
        Arrays.stream(nums).filter(n -> n >= 60).forEach(System.out::println);
    }

    public static void reduceTest() {
        // reduce 就是将stream中的元素组合起来 比如字符串拼接、sum、min、max、average
        // 指定了初始值
        System.out.println(Stream.of(new Integer[]{100, 200, 300}).reduce(0, (arg1, arg2) -> arg1 + arg2));
        // 不指定初始值  -> 返回一个Optional
        System.out.println(Stream.of(new Integer[]{100, 200, 300}).reduce(Integer::sum).get());
    }

    public static void limitTest() {
        // limit返回stream中的前n个元素
        Integer[] array = {1, 2, 2, 3, 3, 4, 5, 6, 7};
        Stream.of(array).limit(3).forEach(System.out::println);
        // 1 2 3
        System.out.println("--------------------");
        // skip则是扔掉前n个元素
        // 4 5 6 7
        Stream.of(array).skip(3).forEach(System.out::println);
        System.out.println("--------------------");
        Stream.of(array).sorted((n1, n2) -> n2.compareTo(n1)).limit(3).forEach(System.out::println);
        // 倒序 7 6 5
        System.out.println("--------------------");
        // 顺序 1 2 3
        Stream.of(array).sorted((n1, n2) -> n1.compareTo(n2)).limit(3).forEach(System.out::println);

        System.out.println("min=" + Arrays.stream(array).min(Comparator.naturalOrder()).get());
        System.out.println("max=" + Arrays.stream(array).max(Comparator.naturalOrder()).get());
        // 去重
        Arrays.stream(array).distinct().forEach(System.out::println);

    }
}
