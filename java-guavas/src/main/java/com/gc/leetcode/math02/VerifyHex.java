package com.gc.leetcode.math02;

/**
 * @description: 校验是不是一个十六进制的数
 */
public class VerifyHex extends VerifyNumberTemplate {

    @Override
    protected boolean doVerify(String s) {
        s = s.toUpperCase();
        // 0x开头
        if (s.startsWith("0X")) {
            s = s.substring(2);
        }
        // 遍历字符
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if(!isHex(s.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public boolean isHex(char c) {
        return (c >= 48 && c <= 57) || (c >= 65 && c <= 70);
    }
}
