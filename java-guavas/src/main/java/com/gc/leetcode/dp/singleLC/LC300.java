package com.gc.leetcode.dp.singleLC;

import java.util.Arrays;

/**
 * 单串问题
 *
 * @description: https://leetcode-cn.com/problems/longest-increasing-subsequence/
 * id=300
 * 最长递增子序列  保证顺序和元素组相同即可   不要求结果的元素在原数组中连续
 */
public class LC300 {

    public static void main(String[] args) {
        int[] nums = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        // 动态规划
        // dp(i) = dp(i)+1 || max(dp(i-1), dp(i)+1)
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int res = dp[0];
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    // 2, 5, 3, 7
                    // dp[i] = dp[i] + 1;
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
            res = Math.max(res, dp[i]);
        }
        System.out.println("动态规划结果：res=" + res);
        // 动态规划+二分法
        int[] dp2 = new int[nums.length];
        int len = 0;
        for (int i = 0; i < nums.length; i++) {
            int index = Arrays.binarySearch(dp2, 0, len, nums[i]);
            index = index < 0 ? -index - 1 : index;
            dp2[index] = nums[i];
            if (index == len) {
                len++;
            }
        }
        System.out.println("动态规划结果：" + dp2.length);
    }
}
