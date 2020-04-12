package com.gc.leetcode.math;

/**
 * @description: 判断一个整数是不是回文数 #9
 * https://leetcode-cn.com/problems/palindrome-number/
 */
public class PalindromeNumber {

    public static void main(String[] args) {
        int x = 121;
        System.out.println(isPalindrome(x));

    }

    public static boolean isPalindrome(int x) {
        if (x < 0) {
            // 负数直接返回false
            return false;
        }
        if (x < 10) {
            // 0-9直接返回true
            return true;
        }
        String str = Integer.toString(x);
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) != str.charAt(len - 1 - i)) {
                return false;
            }
        }
        return true;
    }


}
