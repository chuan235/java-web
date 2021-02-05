package com.gc.leetcode.dp.singleLC;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @description: https://leetcode-cn.com/problems/russian-doll-envelopes/
 * 俄罗斯套娃信封问题
 * 信封的宽和高  (w,h)
 * 宽高小的信封 可以无限装入比他宽高都大的信封
 * 计算最多能有多少个信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）
 * <p>
 * envelopes = [[5,4],[6,4],[6,7],[2,3]]
 * 最多信封的个数为 3, 组合为: [2,3] => [5,4] => [6,7]
 */
public class LC354 {

    public static void main(String[] args) {

        int[][] envelopes = new int[][]{{5, 4}, {6, 4}, {6, 7}, {2, 3}};
        // 方法一： 找出的最大的信封，在找出比他小的信封
        // 方法二： 在找最大信封的时候，同时存储小信封(排除长或者高与最大的相等的信封)
        // 最大的信封怎么找？
        // 根据宽度进行序 (2,3),(5,4),(6,7),(6,4) 宽度相等的就根据高度降序排列
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] i1, int[] i2) {
                if (i1[0] == i2[0]) {
                    // 按高度降序
                    return i2[1] - i1[1];
                }
                return i1[0] - i2[0];
            }
        });
        // 对宽度使用 LIS 算法
        int[] width = new int[envelopes.length];
        int[] dp = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            width[i] = envelopes[i][1];
        }
        // LIS算法
        int len = 0;
        for (int i = 0; i < width.length; i++) {
            int index = Arrays.binarySearch(dp, 0, dp.length, width[i]);
            index = index < 0 ? -index - 1 : index;
            dp[index] = width[i];
            if (i == len) {
                len++;
            }
        }
        System.out.println("最大套娃数：" + len);
    }
}
