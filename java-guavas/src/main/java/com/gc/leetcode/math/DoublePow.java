package com.gc.leetcode.math;

/**
 * @description: https://leetcode-cn.com/problems/powx-n/
 */
public class DoublePow {
    /**
     * 计算 x^n
     *
     * @param x
     * @param n
     * @return
     */
    public static double pow(double x, int n) {
        if (n == 0) {
            return 1.0;
        }
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }
        //return powFor(x, n);
        //return fastPow(x, n);
        return binaryPow(x, n);
    }


    public static double powFor(double x, int n) {
        double result = 1;
        for (int i = 0; i < n; i++) {
            result *= x;
        }
        return result;
    }

    // 2.000 5  二分+递归
    public static double fastPow(double x, int n) {
        if (n == 0) {
            return 1.0;
        }
        double half = fastPow(x, n / 2);
        if (n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }

    public static double binaryPow(double x, int n) {
        double result = 1;
        double current = x;
        for (int i = n; i > 0; i = n / 2) {
            if (i % 2 == 1) {
                result = result * current;
            }
            current *= current;
        }
        return result;
    }
}
