package com.gc.leetcode.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description: 对大List进行分组
 */
public class ListUtil {

    public static void main(String[] args) {
//        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13);
//        List<List<Integer>>  listList = Lists.partition(list,4);
//        List<List<Integer>> listList = partitionByGroupNum(list, 5);

        //System.out.println(listList);

        System.out.println(Math.floorDiv(13, 5));
        System.out.println(Math.floorMod(4, 3));
    }

    /**
     * @param list 需要切分的list
     * @param num  每num个分成一组
     */
    public static <T> List<List<T>> partitionByNum(List<T> list, int num) {
        int group = calculateGroup(list.size(), num);
        List<List<T>> result = new ArrayList<>();
        Stream.iterate(0, n -> n + 1).limit(group).forEach(i -> {
            result.add(list.stream().skip(i * num).limit(num).collect(Collectors.toList()));
        });

        List<List<T>> splitList = Stream.iterate(0, n -> n + 1)
                .limit(group)
                .parallel()
                .map(a -> list.stream().skip(a * num).limit(num).parallel()
                        .collect(Collectors.toList())).collect(Collectors.toList());


        return result;
    }

    /**
     * @param list     需要切分的list
     * @param groupNum 需要分成多少个list
     */
    public static <T> List<List<T>> partitionByGroupNum(List<T> list, int groupNum) {
        List<List<T>> result = new ArrayList<>();
        int num = calculateGroup(list.size(), groupNum);
        Stream.iterate(0, n -> n + 1).limit(groupNum).forEach(i -> {
            result.add(list.stream().skip(i * num).limit(num).collect(Collectors.toList()));
        });

        return result;
    }

    public static int calculateGroup(int size, int groupNum) {
        if (Math.floorMod(size, groupNum) == 0) {
            return Math.floorDiv(size, groupNum);
        } else {
            return Math.floorDiv(size, groupNum) + 1;
        }
    }

}
