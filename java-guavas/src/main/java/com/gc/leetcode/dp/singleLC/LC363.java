package com.gc.leetcode.dp.singleLC;

import java.util.Arrays;

/**
 * @description: https://leetcode-cn.com/problems/max-sum-of-rectangle-no-larger-than-k/
 * <p>
 * 给定一个非空二维矩阵 matrix 和一个整数 k，找到这个矩阵内部不大于 k 的最大矩形和。
 *
 */
public class LC363 {

    public static void main(String[] args) {
        char[] s = new char[]{'h','e','l','l','o'};
        int n = s.length-1;
        for (int i = n >> 2; i >= 0; i--) {
            // i k 交换位置
            int k = n - i;
            char cj = s[i];
            char ck = s[k];
            s[i] = ck;
            s[k] = cj;
        }
    }


    // 暴力解法
    // dp(i1,j1,i2,j2) = dp(i1,j1,i2 - 1,j2) + dp(i1,j1,i2,j2 - 1) - dp(i1,j1,i2 - 1,j2 - 1) + matrix[i2 - 1][j2 - 1];
    public static void force() {
        int[][] matrix = new int[][]{{1, 0, 1}, {0, -2, 3}};
        int k = 5;
        int rows = matrix.length, cols = matrix[0].length, max = Integer.MIN_VALUE;
        for (int i1 = 0; i1 < rows; i1++) {
            for (int j1 = 0; j1 < cols; j1++) {
                int[][] dp = new int[rows + 1][cols + 1];
                dp[i1][j1] = matrix[i1-1][j1-1];
                for (int i2 = i1; i2 < rows; i2++) {
                    for (int j2 = j1; j2 < cols; j2++) {
                        dp[i2][j2] = dp[i2-1][j2]+dp[i2][j2-1] -dp[i2-1][j2-1]+matrix[i2-1][j2-1];
                        if (dp[i2][j2] <= k && dp[i2][j2] > max){
                            max = dp[i2][j2];
                        }
                    }
                }
            }
        }
    }
}
