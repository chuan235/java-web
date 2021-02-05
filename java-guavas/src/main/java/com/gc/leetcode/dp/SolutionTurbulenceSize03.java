package com.gc.leetcode.dp;

/**
 * @description: https://leetcode-cn.com/problems/longest-turbulent-subarray/
 * 求数组中的具有最大和的连续子序列，返回其最大和
 * <p>
 * <p>
 * 动态规划的核心思想：穷举法
 */
public class SolutionTurbulenceSize03 {

    public static void main(String[] args) {
        int[] nums = {9, 4, 2, 10, 7, 8, 8, 1, 9};
        // 只要求计算出最大端流子数组的长度
        // 之和上一次的状态有关
        int up = 1, down = 1, ans = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                up = down + 1;
                down = 1;
            } else if (nums[i] < nums[i - 1]) {
                down = up + 1;
                up = 1;
            } else {
                up = down = 1;
            }
            ans = Math.max(ans, Math.max(up, down));
        }

        /*boolean[] flags = new boolean[nums.length];
        int size = 1;
        int maxSize = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            flags[i] = nums[i] - nums[i + 1] > 0;
            if (i > 1) {
                if (flags[i] != flags[i - 1]) {
                    System.out.println("第" + i + "个值：" + nums[i]);
                    maxSize = Math.max(++size, maxSize);
                } else {
                    size = 1;
                }
            }
        }
        System.out.println(Arrays.toString(flags));
        System.out.println(maxSize + 1);*/
    }

    public static void wiggleMaxLength() {
        int[] nums = new int[]{1, 7, 4, 9, 2, 5};
        // 正数为true  负数为false
        boolean flag = true;
        for (int i = 1; i < nums.length; i++) {
            int prev = nums[i - 1];
            boolean tr = prev - nums[i] > 0;
            if (i == 1) {
                flag = tr;
            }
            if (i > 1 && flag == tr) {
                System.out.println("不是摆动序列");
            }
        }


    }

    public static void maxSubArray() {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        // dp(i) = max{dp(i-1) + Ai, dp(i-1)}
        int maxSum = nums[0];
        int res = 0;
        for (int i = 1; i < nums.length; i++) {
            // -2 1 -2 4 3 5 6 1 5
            maxSum = Math.max(maxSum + nums[i], nums[i]);
            // 缓存最大值
            res = Math.max(maxSum, res);
        }
    }

    // 分冶思想  先分离在合并
    public static void maxSubArray2() {
        // get(a,l,r) 表示查询a数组的[l,r]序列的最大值
        // 拆分 get(a,l,(l+r)/2)   get(a,(l+r)/2,r)
        /**
         * 维护的参数
         * lSum: 以l为左端的最大子段和  [l, m]
         * rSum：以r为右端的最大子段和  [m+1, r]
         * mSum: 表示[l,r]内的最大子段和
         * iSum: 表示[l,r]的区间和
         * 合并逻辑：
         * 左子区间的最大子段和 mSum1
         * 右子区间的最大子段和  mSum2
         * 左子区间的右端和+右子区间的左端和 rSum1+lSum2
         *   mSum = max(mSum1,mSum2,rSum1+lSum2)
         */
    }

}
