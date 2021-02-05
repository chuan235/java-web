package com.gc.leetcode.dp.singleLC;

/**
 * @description: https://leetcode-cn.com/problems/maximum-product-subarray/
 * 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积
 */
public class LC152 {

    public static void main(String[] args) {
        // 连续至少一个的子数组   最大乘积
        int[] nums = new int[]{-2, 5, -2, -4, 4};
        // dp(i) = max{dp(i-1)*Ai, dp(i-1)}

        int maxF = nums[0], minF = nums[0], res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 会出现 2,3,4 的错误结果
//            if (maxF * nums[i] < maxF) { // 判断的问题在于对于负数的判断  可能后面乘上负数会变为正数
//                // 重置maxF的值
//                maxF = nums[i];
//            } else {
//                maxF = Math.max(maxF, maxF * nums[i]);
//            }
//            System.out.println("maxF="+maxF);
//            res = Math.max(res, maxF);
            // 将正负数分开
            int mx = maxF, mn = minF;
            // maxF = 5 minF = -10 res = 5      nums[i]=5
            // maxF = 20 minF = -10 res = 20    nums[i]=-2
            // maxF = 40 minF = -80 res = 40    nums[i]=-4
            // maxF = 160 minF = -320 res = 160 nums[i]=4
            maxF = Math.max(mx * nums[i], Math.max(mn * nums[i], nums[i]));
            minF = Math.min(mn * nums[i], Math.min(mx * nums[i], nums[i]));
            res = Math.max(maxF, res);
            System.out.println("maxF=" + maxF + ",minF=" + minF + ",res=" + res);
        }
        System.out.println("最大乘积：" + res);
    }

}
