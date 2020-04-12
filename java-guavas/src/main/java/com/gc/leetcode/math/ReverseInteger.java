package com.gc.leetcode.math;

/**
 * @description: 数字反转 #7 https://leetcode-cn.com/problems/reverse-integer/
 */
public class ReverseInteger {

    public static void main(String[] args) {
        // 2^31-1
//        Integer maxValue = Integer.MAX_VALUE;
        // -2^31
//        Integer minValue = Integer.MIN_VALUE;

//        reverse(Integer.MIN_VALUE);
//        reverse(-121232);

        System.out.println(reverseC(-123214));
    }


    public static void reverse(Integer integer) {
        String prefix = integer < 0 ? "-" : "";
        String seqence = integer.toString().startsWith("-") ? integer.toString().substring(1) : integer.toString();
        int len = seqence.length();
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = seqence.charAt(len - 1 - i);
        }
        String resultString = prefix + new String(chars);
        try {
            int result = Integer.parseInt(resultString);
            System.out.println("result Number=" + result);
        } catch (NumberFormatException e) {
            System.out.println("转换数据超出范围，-2^31 ~ 2^31-1，转换后的数字是：");
            if (resultString.startsWith("-")) {
                System.out.println("-");
            }
            for (int i = 0; i < resultString.length(); i++) {
                if (resultString.startsWith("-") && i == 0) {
                    continue;
                }
                System.out.print("+" + (Integer.parseInt(Character.toString(resultString.charAt(i)))) * Math.pow(16, (resultString.length() - i - 1)));
            }
        }
    }


    public static int reverseC(int x) {
        long rs = 0;
        for (; x != 0; rs = rs * 10 + x % 10, x /= 10) {
        }
        return rs > Integer.MAX_VALUE || rs < Integer.MIN_VALUE ? 0 : (int) rs;
    }

    public static int reverseWhile(int x){
        long rs = 0;
        while(x != 0){
            rs = rs * 10 + x % 10;
            x /= 10;
        }
        return rs > Integer.MAX_VALUE || rs < Integer.MIN_VALUE ? 0 : (int) rs;
    }

}
