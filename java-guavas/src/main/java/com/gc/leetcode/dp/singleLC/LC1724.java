package com.gc.leetcode.dp.singleLC;

import java.util.Arrays;

/**
 * @description: https://leetcode-cn.com/problems/max-submatrix-lcci/
 * 给定一个正整数、负整数和 0 组成的 N × M 矩阵，编写代码找出元素总和最大的子矩阵。
 */
public class LC1724 {

    public static void main(String[] args) {
        /**
         -1 4  -2
         2  0  3
         3 -1 -2
         */
        int[][] nums = new int[][]{{-1, 4, -2}, {2, 0, 3}, {3, -1, -2}};
        // 在暴力for循环的基础上剪支

        // 1、优化连续矩阵的和  ==>  将二维问题转化为一维问题
        // i,j 行 -> []  每次计算出 i，j行的最大连续和
        // 记录最大值
        int res = 0;
        // 记录 i到j行的计算结果
        int[] rows = new int[nums[0].length];
        // 记录 最大值的坐标
        int[] resPoint = new int[4];
        for (int i = 0; i < nums.length; i++) {
            // 起始行  每次起始的时候 需要将rows记录 清零
            Arrays.fill(rows, 0);

            for (int j = i; j < nums.length; j++) {
                // 结束行  第一行  1-2行  1-3行  2-2 2-3 3-3 ...
                // 计算行中对应列的结果 nums[0][0]+nums[1][0]
                for (int k = 0; k < rows.length; k++) {
                    rows[k] = rows[k] + nums[j][k];
                }
                int tmpSum = 0;
                // 计算完成之后  求数组的最大连续和   LIS算法
                for (int k = 0; k < rows.length; k++) {
                    if (tmpSum + rows[k] > rows[k]) {
                        tmpSum = tmpSum + rows[k];
                    } else {
                        // 跟新起始列坐标
                        tmpSum = rows[k];
                        resPoint[1] = k;
                    }
                    if (tmpSum > res) {
                        // 更新坐标
                        res = tmpSum;
                        // 行坐标 第i行 到 第j行
                        // 列坐标
                        resPoint[0] = i;
                        resPoint[2] = j;
                        resPoint[3] = k;
                    }
                }
            }
        }
        System.out.println("res=" + res);
        System.out.println(Arrays.toString(resPoint));

    }

    public static void foreFor() {
        int[][] nums = new int[][]{{-1, 4, -2}, {2, 0, 3}, {3, -1, -2}};
        // 暴力循环解法
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++) {
                // i j 表示起点
                for (int k = 0; k < nums.length; k++) {
                    for (int l = 0; l < nums[i].length; l++) {
                        // k l 表示终点
                        int tmpSum = 0;
                        // 计算矩阵的和
                        for (int m = i; m <= k; m++) {
                            for (int n = j; n <= l; n++) {
                                tmpSum += nums[m][n];
                            }
                        }
//                        System.out.println("tmpSum=" + tmpSum);
                        if (tmpSum > res) {
                            res = Math.max(res, tmpSum);
                            System.out.println("(" + i + "," + j + "," + k + "," + l + ") ");
                            System.out.println("maxSum = " + res);
                        }

                    }
                }

            }
        }
    }
}
