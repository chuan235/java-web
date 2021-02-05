package com.gc.leetcode.dp.singleLC;

import java.util.Arrays;

/**
 * @description: https://leetcode-cn.com/problems/largest-submatrix-with-rearrangements/
 * <p>
 * 给你一个二进制矩阵 matrix ，它的大小为 m x n ，你可以将 matrix 中的 列 按任意顺序重新排列。
 * 请你返回最优方案下将 matrix 重新排列后，全是 1 的子矩阵面积。
 * <p>
 * 0, 0, 1
 * 1, 1, 1
 * 1, 0, 1
 * <p>
 * 统计连续1的个数
 * 0 0 1
 * 1 1 2
 * 2 0 3
 * 针对每一行进行升序排列
 * 0 0 1    length-index  (3-2)*1 (3-1)*0
 * 1 1 2     (3-2)*2  (3-1)*1  (3-0)*1
 * 0 2 3     (3-2)*3  (3-1)*2  (3-0)*0
 */
public class LC1727 {
    public static void main(String[] args) {

        int[][] nums = new int[][]{{0, 0, 1}, {1, 1, 1}, {1, 0, 1}};
        int res = 0;
        // 统计连续1的个数  将二维转化为一维
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                if (nums[i][j] == 1) {
                    nums[i][j] = nums[i - 1][j] + 1;
                }
            }
        }
        // 对子数组进行升序排列
        for (int i = 0; i < nums.length; i++) {
            Arrays.sort(nums[i]);
            // 从后向前遍历  最大的都在后面
            for (int j = nums[i].length - 1; j > 0; j--) {
                // 当前行的连续高度
                int height = nums[i][j];
                // 缓存最大的面积
                res = Math.max(res, height * (nums[i].length - j));
            }
        }
    }
}
