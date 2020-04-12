package com.gc.leetcode.math;

/**
 * @description: 求正整数x的平方根  返回整数部分
 */
public class SqrtX {

    private static int count = 0;

    public int mySqrt(int x) {
        if (x == 0) return 0;
        long left = 0;
        long right = x / 2 + 1;
        while (left < right) {
            count++;
            long middle = (left + right + 1) >>> 1;
            long sqrt = middle * middle;
            if (sqrt > x) {
                right = middle - 1;
            } else {
                left = middle;
            }
        }
        return (int) left;
    }

    // 牛顿法
    public int mySqrt1(int a) {
        if (a < 0) throw new IllegalArgumentException("不能输入负数");
        long x = a;
        while (x * x > a) {
            x = (x + a / x) / 2;
        }
        return (int) x;
    }

}
