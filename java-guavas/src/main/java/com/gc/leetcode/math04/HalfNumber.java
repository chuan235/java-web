package com.gc.leetcode.math04;

/**
 * https://leetcode-cn.com/problems/factorial-trailing-zeroes/
 * @description: 判断阶乘后的0
 */
public class HalfNumber {

    // 5! -> 120  -> 1
    // 10! -> 3628800 -> 2
    public int trailingZeroes(int n) {
        int count = 0;
        while(n > 0){
            count += n / 5;
            n /= 5;
        }
        return count;
    }


}
