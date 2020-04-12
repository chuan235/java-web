package com.gc.leetcode.math;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 罗马字符转整数
 * @link https://leetcode-cn.com/problems/roman-to-integer/
 */
public class RomanToInteger {

    public static final Map<String, Integer> romanMap = new HashMap<>();

    static {
        romanMap.put("M", 1000);
        romanMap.put("CM", 900);
        romanMap.put("D", 500);
        romanMap.put("CD", 400);
        romanMap.put("C", 100);
        romanMap.put("XC", 90);
        romanMap.put("L", 50);
        romanMap.put("XL", 40);
        romanMap.put("X", 10);
        romanMap.put("IX", 9);
        romanMap.put("V", 5);
        romanMap.put("IV", 4);
        romanMap.put("I", 1);
    }

    public static void main(String[] args) {
        // 校验是否合法  IM
        change("MI");
    }

    public static void change(String roman) {
        int length = roman.length();
        int result = 0;
        for (int i = 0; i < length; i++) {
            String current = String.valueOf(roman.charAt(i));
            String key = current;
            if (i != length - 1) {
                Integer value = romanMap.get(current);
                Integer next = romanMap.get(String.valueOf(roman.charAt(i + 1)));
                if(value < next && value * 10 >= next){
                    // 满足特殊写法
                    key = current + roman.charAt(i + 1);
                    i++;
                }else if(value < next){
                    throw new IllegalArgumentException("请输入正确的罗马字符");
                }

            }
            result += romanMap.get(key);
        }
        System.out.println(result);
    }
}
