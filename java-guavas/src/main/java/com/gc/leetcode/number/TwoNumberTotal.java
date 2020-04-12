package com.gc.leetcode.number;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标
 * 给定 nums = [2, 7, 11, 15], target = 9     返回索引 [0, 1]
 * 1、数组一定存在某种组合满足条件
 * 2、数组中的数字不能重复使用
 */
public class TwoNumberTotal {

    public static void main(String[] args) {
        int[] nums = new int[]{12, 2, 11, 15, 7, 3};
        int target = 9;
        long time1 = System.nanoTime();
        solution(nums, target, 0);
        long time2 = System.nanoTime();
        solution1(nums, target);
        long time3 = System.nanoTime();
        //方案一：395000
        System.out.println("方案一：" + (time2 - time1));
        //方案二：120500
        System.out.println("方案二：" + (time3 - time2));
    }

    /**
     * 暴力循环迭代法
     */
    public static void solution(int[] nums, int target, int start) {
        int len = nums.length;
        if (start >= len) {
            System.out.println("未找到满足target的组合...");
            return;
        }
        for (int i = start; i < len - 1; i++) {
            if (nums[start] + nums[i + 1] == target) {
                System.out.println("成功找到组合：[" + nums[start] + "," + nums[i + 1] + "]");
                System.out.println("索引：[" + start + "," + (i + 1) + "]");
                return;
            }
        }
        solution(nums, target, ++start);
    }

    /**
     * 一遍hash算法
     *
     * @param nums
     * @param target
     */
    public static void solution1(int[] nums, int target) {
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int result = target - nums[i];
            if (hashMap.containsKey(result)) {
                System.out.println("索引：[" + hashMap.get(result) + "," + i + "]");
                return;
            }
            hashMap.put(nums[i], i);
        }
    }

}
