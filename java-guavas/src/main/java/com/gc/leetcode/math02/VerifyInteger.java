package com.gc.leetcode.math02;

/**
 * @description: 检查是否为一个整数
 */
public class VerifyInteger extends VerifyNumberTemplate {
    @Override
    protected boolean doVerify(String s) {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            // isDigits 断定一个字符是不是0-9
            if(!Character.isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
