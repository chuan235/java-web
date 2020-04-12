package com.gc.leetcode.math02;

/**
 * @description: 定义一个抽象类，用来检查一些基本操作
 * 是否为空 去前后空格  去+/- ...
 */
public abstract class VerifyNumberTemplate implements VerifyNumber {
    // 方法入口
    @Override
    public boolean verify(String str) {
        // 判断是否为空
        if (isEmpty(str)) return false;
        // 去前后空格
        String value = processHeader(str);
        // 判断去除符号之后是否还存在值
        if (value.length() == 0) return false;
        // 调用模板方法
        return doVerify(value);
    }

    // 判断是否为空
    public boolean isEmpty(String s) {
        return s == null || "".equals(s) || "".equals(s.trim());
    }

    // 判断是否为空
    public boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    // 去前面的正负号
    public String processHeader(String s) {
        String result = s.trim();
        if (result.startsWith("+") || result.startsWith("-")) {
            result = result.substring(1);
        }
        return result;
    }

    // 具体的各自实现
    protected abstract boolean doVerify(String s);


}
