package com.gc.leetcode.dp.singleLC;

/**
 * @description: https://leetcode-cn.com/problems/maximum-sum-circular-subarray/
 * 环形子数组的最大和
 * 给定一个由整数数组 A 表示的环形数组 C，求 C 的非空子数组的最大可能和。
 * C[i] = A[i]          0 <= i < A.length
 * C[i+A.length] = C[i] i>=0
 */
public class LC918 {
    public static void main(String[] args) {
        int[] nums = new int[]{5, -3, 5};
        // 一种是没有边界  最大的子数组和
        // 一种是右边界    对整个数组求和-无边界的最小和
        // 另外需要考虑全部为负数的情况
        int curMax, max, curMin, min, sum;
        curMax = max = curMin = min = sum = nums[0];
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            curMax = Math.max(curMax + nums[i], curMax);
            max = Math.max(max, curMax);
            curMin = Math.min(curMin + nums[i], curMin);
            min = Math.min(min, curMin);
        }
        if (max < 0) {
            System.out.println("max = " + max);
        } else {
            int res = Math.max(sum - min, max);
            System.out.println("res = " + res);
        }

    }

}
