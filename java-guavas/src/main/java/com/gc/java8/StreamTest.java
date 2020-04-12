package com.gc.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * JAVA8 Stream API:
 * Stream是对集合Collection对象功能的增强，专注于对集合元素的聚合、批量操作。在形式上更像是一个Iterator的的升级版
 * 1、Iterator中，用户只能一个一个的遍历对象并对其执行某些操作；Stream中，只需指定对集合中的元素操作，比如根据颜色分组或者年龄小于20
 * Stream会在内部进行遍历，做出响应的数转换
 * 2、Stream就如同一个Iterator，单向不可重复，数据只能遍历一次，遍历过一次后即用尽了，就像水流，流过就没有了
 * 3、Stream可以并行化操作，Iterator只能显示的、串行式的操作。
 * 使用并行操作的时候，数据会被分成多段，其中每一段都在不同的线程中处理，然后将结果一起输出，Stream的并行操作依赖于java7中的Fork/Join框架来拆分任务和加速处理过程
 * 使用的流程
 * 1、获取一个数据源
 * 2、数据转换
 * 3、执行操作获取想要到的值
 */
public class StreamTest {

    public static void buildStream() {
        // 根据罗列的数据生成
        Stream<String> stream = Stream.of("a", "b", "c");
        // 根据数组生成
        Stream<String> arrayStream = Stream.of(new String[]{"a", "b", "c"});
        // 根据集合生成
        Stream<Object> listStream = Collections.emptyList().stream();
        // 基本数值对应的流
        // IntStream LongStream DoubleStream
        IntStream.of(new int[]{1, 2, 3, 4}).forEach(System.out::println);
        // range 前后相等会返回一个空
        IntStream.range(0, 10).forEach(System.out::println);
        // rangeClosed 前后相等会返回一个元素，并且会自动关闭流
        IntStream.rangeClosed(0, 10).forEach(System.out::println);
        // 流转为集合
        List<String> streamToList = stream.collect(Collectors.toList());
        ArrayList<String> streamToArrayList = arrayStream.collect(Collectors.toCollection(ArrayList::new));
        Stream<String> stringStream = Stream.of(new String[]{"1", "2", "3"});
    }
}
