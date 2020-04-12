package com.gc.leetcode.math03;

import com.gc.leetcode.util.MathUtil;

/**
 * https://leetcode-cn.com/problems/excel-sheet-column-title/       给定一个正整数，返回它在 Excel 表中相对应的列名称
 *
 * @description:
 */
public class ExcelMath {

    public static void main(String[] args) {
        ExcelMath math = new ExcelMath();
        System.out.println(math.convertToTitle(701));
        math.titleToNumber("ZY");
    }

    public String convertToTitleLeetCode(int n) {
        StringBuilder builder = new StringBuilder();
        while (n != 0) {
            n--;
            builder.append((char) (n % 26) + 'A');
            n /= 26;
        }
        return builder.toString();
    }

    // 1 -> A -> 65
    // 26 -> Z -> 90
    // 27 -> AZ
    // 701 -> ZY
    public String convertToTitle(int n) {
        if (!checkN(n)) throw new IllegalArgumentException("参数超过显示范围");
        // 0 / 1 / 2 / 3
        int divid = Math.floorDiv(n, 26);
        int mod = Math.floorMod(n, 26);
        if (divid == 0 && mod != 0) {
            return new String(new char[]{(char) (64 + divid)});
        }
        StringBuilder builder = new StringBuilder(2);
        if (mod == 0) {
            if (divid == 1) {
                return "Z";
            } else {
                builder.append((char) (divid + 63));
                builder.append("Z");
                return builder.toString();
            }
        }
        builder.append((char) (64 + divid));
        builder.append((char) (64 + mod));
        return builder.toString();
    }

    public boolean checkN(int n) {
        if (n <= 0 || n > 26 * 27) return false;
        return true;
    }

    // A - 1
    // AA -27
    // CC -
    // 26^0*index + 26^1*index + 26^2*index + ...
    public int titleToNumber(String s) {
        int result = 1;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int base = MathUtil.power(26, len - i -1);
            char c = s.charAt(i);
            result += base*(c - '@');
        }
        System.out.println(result);
        return result;
    }
}
