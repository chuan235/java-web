package com.gc.leetcode.dp;

import java.util.*;

/**
 * @description: https://leetcode-cn.com/problems/ones-and-zeroes/
 * 找到存在m的0,n个1 以内的所有子元素和
 * m,n 依赖 m-1,n-1
 * dp(m,n) = max(dp(m,n), dp(m-i,n-j))  0<i<m, 0<j<n
 */
public class SolutionZeroAndOne01 {
    public static void main(String[] args) {

        String[] strs = {"10", "0001", "111001", "1", "0"};

        // 1的个数
        int m = 5;
        // 0的个数
        int n = 3;

    }

    public static int[] countZeroAndOne(String str){
        int[] res = new int[2];
        for (char c : str.toCharArray()) {
            // c = '0' 或者 '1'
            res[c - '0']++;
        }
        return res;
    }


    public static void arrayMethod(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (String s : strs) {
            int zeroCount = 0, oneCount = 0;
            for (char c : s.toCharArray()) {
                if (c == '1') {
                    oneCount++;
                } else if (c == '0') {
                    zeroCount++;
                }
            }
            // 递归计算 dp[m][n]
            for (int i = m; i >= zeroCount; i--) {
                for (int j = n; j >= oneCount; j--) {
                    // 对于小于m和小于n的dp值都要+1
                    // zero=1 one=1
                    // dp[3,3] => i>=1,j>=1的所有可能都要+1  => dp[3,3] = max[dp[3,3], dp[3-i,3-j]]
                    // i,j 回循环1~3之间，找到这之间的最大值  在加上当前的值
                    dp[zeroCount][oneCount] = Math.max(dp[zeroCount][oneCount], dp[zeroCount - i][oneCount - j] + 1);
                }
            }
        }


    }

    public static void forMethod(String[] strs, int m, int n) {
        Map<String, List<String>> cache = new HashMap<>();
        // 统计数组中数字出现的频率
        for (String s : strs) {
            int zeroCount = 0;
            int oneCount = 0;
            for (char c : s.toCharArray()) {
                if (c == '1') {
                    oneCount++;
                } else if (c == '0') {
                    zeroCount++;
                } else {
                    continue;
                }
            }
            String key = zeroCount + "-" + oneCount;
            cache.compute(key, (k, v) -> {
                if (v == null) {
                    v = new ArrayList<>();
                }
                v.add(s);
                return v;
            });
        }
        // 根据 m n 计算最大的子集
        List<String> result = new LinkedList<>();
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                String key = i + "-" + j;
                List<String> list = cache.get(key);
                if (list != null && list.size() > 0) {
                    result.addAll(list);
                }
            }
        }
        for (String s : result) {
            System.out.println(s);
        }
    }

}
