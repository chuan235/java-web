package com.gc.leetcode.math;

/**
 * @description: 两数相除
 * @link https://leetcode-cn.com/problems/divide-two-integers/
 */
public class Divide2Integer {

    public static void main(String[] args) {
        //System.out.println(new Divide2Integer().divide(-2147483647, -1));


//        new Divide2Integer().divide2(-2147483647, -1);
        new Divide2Integer().divide2(123123, 1);
    }

    /**
     * 7/3 7/-3  -7/3 -7/-3
     * 缺点： 当除数非常小时，遍历次数非常多，时间复杂度很大
     * @param dividend 被除数
     * @param divisor  除数
     * @return 商
     */
    public int divide(int dividend, int divisor) {
        if (dividend == 0) return 0;
        // 无符号
        boolean resultFlag = (divisor > 0) ^ (dividend > 0);
        int result = 0;
        dividend = abs(dividend);
        divisor = abs(divisor);
        while (dividend >= divisor) {
            dividend -= divisor;
            result++;
        }
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return 0;
        }
        return resultFlag ? -result : result;
    }

    public int abs(int x) {
        if (x < 0) return -x;
        return x;
    }


    public int divide2(int dividend, int divisor) {
        if (dividend == 0) return 0;
        // 无符号
        boolean resultFlag = (divisor > 0) ^ (dividend > 0);
        dividend = abs(dividend);
        divisor = abs(divisor);
        int result = 0;
        // 统计除数左移的次数
        int count = 0;
        while (dividend >= divisor) {
            count++;
            divisor <<= 1;
        }
        // 16/1 10000 <- 0001  count = 4  4
        while(count > 0){
            count--;
            divisor >>= 1;// 1000
            // 1026/1 -> result=1024 -> 差2 1024右移：2
            if(divisor <= dividend){
                result += 1 << count;
                dividend -= divisor;
            }
        }
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            System.out.println("商越位");
            return 0;
        }
        return resultFlag ? -result : result;
    }


}

