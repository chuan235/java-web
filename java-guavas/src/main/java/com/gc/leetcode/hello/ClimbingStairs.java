package com.gc.leetcode.hello;

/**
 * @description: https://blog.csdn.net/zgpeace/article/details/88382121
 */
public class ClimbingStairs {


    // 递归
    // f(n) = f(n-1) + f(n-2)
    public int climb1(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("请输入一个大于0的参数");
        }
        if (n <= 2) {
            return n;
        }
        return climb1(n - 1) + climb1(n - 2);
    }

    // 缓存递归 存储历史记录
    public int climb2(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("请输入一个大于0的参数");
        }
        if (n <= 2) {
            return n;
        }
        int[] cache = new int[n + 1];
        return subRecursion(n - 1, cache) + subRecursion(n - 2, cache);
    }

    private int subRecursion(int i, int[] cache) {
        if (i <= 2) return i;
        if (cache[i] != 0) return cache[i];
        cache[i] = subRecursion(i - 1, cache) + subRecursion(i - 2, cache);
        return cache[i];
    }

    // 动态规划解法  f(n) = f(n-1) + f(n-2)
    // 时间复杂度降低为O(n), 内存复杂度为O(n)
    public int dynamic(int n) {
        // check...
        // 从底层向上计算
        int[] cache = new int[n + 1];
        cache[1] = 1;
        cache[2] = 2;
        for (int i = 3; i < cache.length; i++) {
            cache[i] = cache[i - 1] + cache[i - 2];
        }
        return cache[n];
    }

    // 使用Fibonacci  直接使用三个变量进行递推
    // 时间复杂度降低为O(n), 内存复杂度为O(1)
    public int fibonacci(int n) {
        int first = 1;
        int second = 2;
        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }
        return second;
    }

    //  Fibonacci 优化变量
    public int fibonacciVar(int n) {
        int a = 1;
        int b = 2;
        while (--n >= 2) {
            b = a + b;
            a = b - a;
        }
        return b;
    }

    //  优化底层的计算公式
    public int fibonacciFormula(int n) {
        double sqrt5 = Math.sqrt(5);
        double fibn = Math.pow((1 + sqrt5) / 2, n + 1) - Math.pow((1 - sqrt5) / 2, n + 1);
        return (int)(fibn / sqrt5);
    }
    public double myPow(double x, int n) {
        if(0 == n) return 0;
        // n<0
        if( 0 > n) return 1/myPow(x, Math.abs(n));
        // n>0  递归对半乘法
        return Math.pow(x, n);

    }


}
