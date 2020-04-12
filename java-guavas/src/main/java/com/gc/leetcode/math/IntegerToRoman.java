package com.gc.leetcode.math;

/**
 * @description: 整数转罗马数字 #12  https://leetcode-cn.com/problems/integer-to-roman/
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * <p>
 * 4 IV
 * 9 IX
 * 40 XL
 * 90 LC
 * 400 CD
 * 900 DM
 */
public class IntegerToRoman {


    /**
     * 把阿拉伯数字与罗马数字可能出现的所有情况和对应关系，放在两个数组中
     * 并且按照阿拉伯数字的大小降序排列，这是贪心选择思想
     */
    public static final int[] INT = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    public static final String[] ROMAN = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static void buildRoman(int x) {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        while (index < 13) {
            while (x >= INT[index]) {
                builder.append(ROMAN[index]);
                x -= INT[index];
            }
            index++;
        }
        System.out.println(builder.toString());
    }


    public static final String[] CONSTANT = new String[]{"I", "V", "X", "L", "C", "D", "M"};

    public static void main(String[] args) {
        buildRoman(2312);
    }

    public static void forceBuild(int num) {
        String[] Q = new String[]{"", "M", "MM", "MMM"};
        String[] B = new String[]{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] S = new String[]{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] G = new String[]{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String result = Q[(int)Math.floor(num / 1000)] + B[(int)Math.floor((num % 1000) / 100)] + S[(int)Math.floor((num % 100) / 10)] + G[num % 10];

    }

    /**
     * 14 XIV
     */
    public static String buildConstance(String result, int x) {
        if (x > 3999) return "";
        if (x == 0) return result;
        if (x == 4 || x == 9 || x == 40 || x == 90 || x == 400 || x == 900) {
            return buildSpace(result, x);
        }
        int index = 0;
        int space = 1;
        if (x < 5) {

        } else if (x < 10) {
            index = 1;
            space = 5;
        } else if (x < 50) {
            index = 2;
            space = 10;
        } else if (x < 100) {
            index = 3;
            space = 50;
        } else if (x < 500) {
            index = 4;
            space = 100;
        } else if (x < 1000) {
            index = 5;
            space = 500;
        }
        result += CONSTANT[index];
        x -= space;
        return buildConstance(result, x);
    }

    public static String buildSpace(String result, int x) {
        String temp = "";
        if (x == 4) {
            temp = "IV";
        } else if (x == 9) {
            temp = "IX";
        } else if (x == 40) {
            temp = "XL";
        } else if (x == 90) {
            temp = "LC";
        } else if (x == 400) {
            temp = "CD";
        } else if (x == 900) {
            temp = "DM";
        }
        return result + temp;
    }
}
