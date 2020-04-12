package com.gc.leetcode.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @description: https://leetcode-cn.com/problems/permutation-sequence/
 */
public class PermutationSequence {

    public static void main(String[] args) {
        // 1234 3124 3142 3214
        PermutationSequence sequence = new PermutationSequence(3, 6);
        System.out.println(sequence.buildResult());
    }

    private int n;
    private int k;
    private Vector<Integer> vector = new Vector<>();

    public PermutationSequence(int n, int k) {
        this.n = n;
        this.k = k;
        for (int i = 1; i <= n; i++) {
            vector.add(i);
        }
    }

    // build结果
    public String buildResult() {
        String result = "";
        for (int i = n; i >= 1; i--) {
            //int index = genFirstNumber(i);
            //System.out.println("index=" + index);
            //int value = linkListValue(index);
            //System.out.println("value=" + value);
            result += linkListValue(genFirstNumber(i));
        }
        return result;
    }

    // 操作链表，拿到第k个元素
    public int linkListValue(int k) {
        Integer result = vector.get(k);
        // 删除原链表中的值
        vector.remove(k);
        return result;
    }

    // 获取头字符的索引，n表示多少位的第一位
    public int genFirstNumber(int n) {
        // 间隔
        int space = factorial(n - 1);
        int result = 0;
        while (k > space) {
            result++;
            k -= space;
        }
        // n小于等于space的时候，直接返回0
        return result;
    }

    // 返回k的阶乘
    public int factorial(int k) {
        int result = 1;
        for (int i = 2; i <= k; i++) {
            result *= i;
        }
        return result;
    }

    // 回溯法
    public String buildResult(int n, int k) {
        int[] nums = new int[n];
        boolean[] isUse = new boolean[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i + 1;
            isUse[i] = false;
        }
        List<String> pre = new ArrayList<>();
        return backMethod(nums, isUse, n, k, 0, pre);
    }

    public String backMethod(int[] nums, boolean[] used, int n, int k, int depth, List<String> pre) {
        // 当递归深度为n的时候，返回结果
        if (depth == n) {
            StringBuilder sb = new StringBuilder();
            for (String c : pre) {
                sb.append(c);
            }
            return sb.toString();
        }
        // space
        int space = factorial(n - 1 - depth);
        for (int i = 0; i < n; i++) {
            if(used[i]) continue;
            if(k > space){
                // 拿到每组中具体的位置
                k -= space;
                continue;
            }
            pre.add(nums[i]+"");
            used[i] = true;
            return backMethod(nums, used, n, k, depth + 1, pre);
        }
        throw new RuntimeException("参数错误");
    }
}

