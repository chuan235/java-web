package com.gc.leetcode.number;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description: 求一个字符串中最长的不重复子串，返回子串和子串的长度
 * pwwkew   ->   wke   ->   3
 */
public class LongestSubstringNoRepeat {

    public static void main(String[] args) {

        String str = "pwwkew";
        String str1 = "pppppp";
        String str2 = "abcabcbb";
//        longestSub(str2);
//        longestSub2(str2);
//        longestSub3(str2);
        longestSub4(str2);
//        System.out.println(str.charAt(3));
    }

    /**
     * 暴力循环对比  时间复杂度 O(n^3)
     */
    public static void longestSub(String str) {
        int start = 0;
        int end = 1;
        int len = str.length();
        int maxLen = 0;
        String maxSubString = "";
        while (start < len && end < len) {
            for (int i = start; i < end; i++) {
                if (charEqs(str, i, end)) {
                    // 记录当前的长度
                    if ((end - start) > maxLen) {
                        maxLen = end - start;
                        maxSubString = str.substring(start, end);
                    }
                    System.out.println("找到一个不重复的子串，长度是：" + maxLen + "，子串是：" + maxSubString);
                    start = end;
                }
            }
            end++;
        }
        System.out.println("最长的不重复子串长度是：" + maxLen + "，子串是：" + maxSubString);
        return;
    }

    public static boolean charEqs(String str, int start, int end) {
        return str.charAt(start) == str.charAt(end);
    }

    /**
     * 比较滑动序列，O(2n) = O(n)
     */
    public static void longestSub2(String str) {
        int len = str.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < len && j < len) {
            if (!set.contains(str.charAt(j))) {
                // 把不重复的字符放入set中
                set.add(str.charAt(j++));
                // 取最大值
                ans = Math.max(ans, j - i);
            } else {
                // 存在相同的，就从前向后依次移除,这里可以直接找到对应的索引，而不是去循环移除
                set.remove(str.charAt(i++));
            }
        }
        System.out.println(ans);
        return;
    }

    /**
     * HashMap O(n)  cbacb
     */
    public static void longestSub3(String str) {
        int len = str.length();
        int maxLen = 0;
        // 字符，长度
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0, j = 0; j < len; j++) {
            if (map.containsKey(str.charAt(j))) {
                i = Math.max(i, map.get(str.charAt(j)));
            }
            maxLen = Math.max(maxLen, j - i + 1);
            // 将相同字符对应的长度覆盖即可
            map.put(str.charAt(i), j + 1);
        }
        System.out.println(maxLen);
        return;
    }

    public static void longestSub4(String str) {
        int len = str.length();
        int maxLen = 0;
        // ascii  使用数组替换HashMap
        int[] index = new int[128];
        for (int i = 0, j = 0; j < len; j++) {
            // 包含相同的字符
            i = Math.max(i, index[str.charAt(j)]);
            maxLen = Math.max(maxLen, j - i + 1);
            index[str.charAt(j)] = j + 1;
        }
        System.out.println(maxLen);
        return;
    }

}
