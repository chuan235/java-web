package com.gc.leetcode.math03;

/**
 * @description: https://leetcode-cn.com/problems/fraction-to-recurring-decimal/
 * 分数转小数
 */
public class Fraction2Decimal {

    private static int codeCount = 0;

    public static void main(String[] args) {
        System.out.println(new Fraction2Decimal().fractionToDecimal(10, 78922346));
        System.out.println("codeCount=" + codeCount);
    }


    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) return "0";
        if (denominator == 0) throw new IllegalArgumentException("除数不能为0");
        if (numerator == denominator) return "1";
        StringBuilder builder = new StringBuilder();
        // 判断正负
        if ((numerator < 0) != (denominator < 0)) {
            builder.append("-");
            // 将负数转为正数
            numerator = -numerator;
            denominator = -denominator;
        }
        // 算出商和余数
        int q = numerator / denominator;
        int p = numerator % denominator;
        builder.append(q);
        builder.append(".");
        // 余数为0直接返回
        if (p == 0) {
            builder.append("0");
            return builder.toString();
        }
        // 进入计算小数位
        return dealDecimal(builder, p, denominator, 0);
    }

    // 第一次的余数  除数  1/3  1  3   相同的除数连续出现三次认为是无限循环
    public String dealDecimal(StringBuilder result, int remaind, int denominator, int count) {
        codeCount++;
        // 余数为0
        if (remaind == 0) {
            return result.toString();
        }
        // 限制递归的次数
        if (result.length() > 26) return result.toString();
        if (count >= 3) {
            String str = result.toString();
            int pIndex = str.indexOf(".");
            char f = str.charAt(pIndex + 1);
            char end = str.charAt(str.length() - 1);
            if (f == end) {
                String temp = str.substring(0, pIndex + 1);
                return temp + "(" + f + ")";
            }
            return str;
        }
        while ((remaind *= 10) < denominator) {
            result.append("0");
        }
        int temp = remaind / denominator;
        remaind %= denominator;
        result.append(temp);
        if (result.indexOf("" + temp) == result.length() - 2) {
            count++;
        }
        return dealDecimal(result, remaind, denominator, count);
    }
}
