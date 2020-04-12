package com.gc.leetcode.math;

import com.gc.leetcode.util.MathUtil;

/**
 * @description:
 */
public class TwoStringMultiply {

    public static void main(String[] args) {
//        new TwoStringMultiply().buildLong1("797234232"); 9 10
//        new TwoStringMultiply().buildLong2("7972342321");
//        new TwoStringMultiply().buildReversLong("797234232");

//        new TwoStringMultiply().buildLong2("79723428321321787832121");
//        System.out.println((long)9223372036854775808);

        new TwoStringMultiply().multiply("123123142324","4124197312321");
    }

    public void multiply(String str1, String str2) {
        if (str1.startsWith("0") || str2.startsWith("0")) {
            System.out.println("0");
            return;
        }
        long long1 = MathUtil.convertString2Long(str1);
        long long2 = MathUtil.convertString2Long(str2);
        long result = long2 * long1;
        if(result < Long.MIN_VALUE){
            System.out.println("输出结果已越界");
        }
        System.out.println("result="+result);
    }






    // 231 20
    public long buildLong1(String string) {
        long result = 0;
        int len = string.length();
        for (int i = len - 1; i >= 0; i--) {
            int value = Character.getNumericValue(string.charAt(i));
            int power = len - i - 1;
            result += value * MathUtil.power((long) 10, power);
        }
        return result;
    }

    /**
     * @param string 1234
     * @return long 4321
     */
    public long buildReversLong(String string) {
        long result = 0;
        int len = string.length();
        for (int i = 0; i < len; i++) {
            int value = Character.getNumericValue(string.charAt(i));
            result += value * MathUtil.power((long) 10, i);
            System.out.println(result);
        }
        return result;
    }
}
