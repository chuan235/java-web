package com.gc.leetcode.util;

/**
 * @description: 数学常用算法
 */
public class MathUtil {

    // 2^32-1 = 2147483647
    public static Integer MAX_INT_VALUE = Integer.MAX_VALUE;
    // -2147483648
    public static Integer MIN_INT_VALUE = Integer.MIN_VALUE;
    // 2^63 - 1 = 9223372036854775807
    public static Long MAX_LONG_VALUE = Long.MAX_VALUE;
    // -9223372036854775808
    public static Long MIN_LONG_VALUE = Long.MAX_VALUE;

    /**
     * 整数反转 123 -> 321 -123 -> -321
     *
     * @param x
     * @return
     */
    public static int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }

    /**
     * 求 a^b 返回int型
     *
     * @param a
     * @param b
     * @return
     */
    public static int power(int a, int b) {
        int power = 1;
        for (int c = 0; c < b; c++) {
            power *= a;
        }
        return power;
    }

    /**
     * 整数幂 返回long型
     *
     * @param a
     * @param b
     * @return
     */
    public static long power(long a, int b) {
        long power = 1;
        for (int c = 0; c < b; c++) {
            power *= a;
        }
        return power;
    }

    /**
     * 输入两个字符串相乘
     *
     * @param str1
     * @param str2
     * @return
     */
    public long multiply(String str1, String str2) {
        if (str1.startsWith("0") || str2.startsWith("0")) {
            return 0;
        }
        long long1 = MathUtil.convertString2Long(str1);
        long long2 = MathUtil.convertString2Long(str2);
        long result = long2 * long1;
        if (result < Long.MIN_VALUE) {
            System.out.println("输出结果已越界");
            return 0;
        }
        return result;
    }

    /**
     * 字符串转long数字
     *
     * @param string
     * @return
     */
    public static long convertString2Long(String string) {
        if (MathUtil.MAX_LONG_VALUE.equals(string)) {
            return Long.MAX_VALUE;
        }
        if (MathUtil.MIN_LONG_VALUE.equals(string)) {
            return Long.MIN_VALUE;
        }
        boolean unsignFlag = false;
        if (string.startsWith("-")) {
            unsignFlag = true;
        }
        long result = buildLong(unsignFlag ? string.substring(1) : string);
        return unsignFlag ? -result : result;
    }

    /**
     * @param string 1234
     * @return long 1234
     */
    public static long buildLong(String string) {
        long result = 0;
        int len = string.length();
        for (int i = 0; i < len; i++) {
            int value = Character.getNumericValue(string.charAt(i));
            result += value * MathUtil.power((long) 10, len - 1 - i);
        }
        if (result < Long.MIN_VALUE) {
            System.out.println("已越界...");
            result = 0;
        }
        return result;
    }

    public static long factorial(int n) {
        if (n == 1 || n == 0) {
            return 1;
        }
        return n * factorial(n - 1);
    }

}
