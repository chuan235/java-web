package com.gc.leetcode.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: https://leetcode-cn.com/problems/longest-increasing-subsequence/
 * 最大严格递增子序列
 * 1、dp(i) = max(dp(j) + 1, dp(i))  j<i and nums[j]<nums[i]
 * 2、维护一个结果数组，遍历已知数组，将第一个元素放入数组，如果后进来的数比结果数组的值都大，那么放入结果数据末尾
 * 否则的话用当前元素覆盖掉结果数组中第一个比他大的元素
 */
public class SolutionOfLIS02 {

    // 时间复杂度：O(N^2)，这里 N 是输入数组的长度； 空间复杂度：O(N)
    public static void main(String[] args) {


        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        // dp[i] = max(dp[i-1], dp[i]+1)    nums[i] < nums[i-1]
        // dp[i] = dp[i]+1                  nums[i] > nums[i-1]

        int[] dp = new int[nums.length];
        // 单独的一个数就是一个数量为1的子序列  表示一个值
        Arrays.fill(dp, 1);
        int res = dp[0];
        for (int i = 1; i < nums.length; i++) {
            // 计算前i个值的最大序列
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
            // 获取最大的序列
            res = Math.max(res, dp[i]);
        }
    }

    public static void nlogN() {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        int[] res = new int[nums.length];
        int len = 0;
        for (int i = 0; i < nums.length; i++) {
            // 二分法查找，找不到返回它应该在的位置 -1 或者是  -endIndex  就是该元素该插入的位置
            int index = Arrays.binarySearch(res, 0, len, nums[i]);
            index = index < 0 ? -index - 1 : index;
            // 找不到说明比结果数组的数据都小  或者比所有的数据都大
            res[index] = nums[i];
            if (index == len) {
                len++;
            }
        }
        System.out.println(Arrays.toString(res));
        System.out.println("最大子序列长度为：" + res.length);
    }

    public static int binarySearch(int[] arr, int fromIndex, int endIndex, int value) {
        // 检查索引
        // .....
        while (fromIndex < endIndex) {
            int mid = (fromIndex + endIndex) >>> 2;
            if (arr[mid] < value) {
                fromIndex = mid + 1;
            } else if (arr[mid] > value) {
                endIndex = mid;
            } else {
                return mid;
            }
        }
        return -(fromIndex + 1);
    }
}
