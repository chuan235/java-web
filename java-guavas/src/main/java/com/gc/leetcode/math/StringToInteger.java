package com.gc.leetcode.math;

/**
 * @description: 字符串转整数  #8 https://leetcode-cn.com/problems/string-to-integer-atoi/
 */
public class StringToInteger {

    public static void main(String[] args) {
        String str = " 12 -0a43 dadda 342 ";
        System.out.println(convert2Int(str));
    }


    public static int convert2Int(String x) {
        long rs = 0;
        int len = x.length();
        int start = 0;
        int flag = 1;
        for (int i = 0; i < len; i++) {
            char currentChar = x.charAt(i);
            if (currentChar != ' ' && currentChar != '-' && (currentChar > '9' || currentChar < '0')) {
                break;
            }
            if (currentChar == ' ') {
                continue;
            }
            if (currentChar == '-' && rs > 0) {
                break;
            }
            if (currentChar == '-') {
                flag = -1;
                continue;
            }
            int currentInt = Integer.parseInt(Character.toString(x.charAt(i)));
            rs = rs * power(10, start) + currentInt;
            start++;
        }
        rs = flag * rs;
        return rs > Integer.MAX_VALUE || rs < Integer.MIN_VALUE ? 0 : (int) rs;
    }


    public static int power(int a, int b) {
        int power = 1;
        for (int c = 0; c < b; c++) {
            power *= a;
        }
        return power;
    }
}
