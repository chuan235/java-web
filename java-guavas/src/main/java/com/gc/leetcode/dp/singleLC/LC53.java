package com.gc.leetcode.dp.singleLC;

/**
 * @description: https://leetcode-cn.com/problems/maximum-subarray/
 * 最大连续子数组的和
 */
public class LC53 {

    public static void main(String[] args) {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        // dp(i) = max{dp(i-1)+Ai, dp(i-1)}
        int sum = 0;
        int res = nums[0];
        for (int i = 0; i < nums.length; i++) {
            // 如果只有这一步会出现的问题？ 出现sum并不是计算过程中的最大值  因为他在max函数中参与了计算
            sum = Math.max(sum + nums[i], nums[i]);
            // 关键
            res = Math.max(res, sum);
        }
        System.out.println(res);
    }

}
