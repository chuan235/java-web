package com.gc.leetcode.math02;

/**
 * @description: 校验是不是小数
 */
public class VerifyDecimal extends VerifyNumberTemplate {
    @Override
    protected boolean doVerify(String s) {
        // 检查小数点位置
        int index = s.indexOf(".");
        if (index == -1 || index == 0) return false;
        // 分别检查小数点前后是否是一个整数
        String before = s.substring(0, index);
        String after = s.substring(index + 1);
        // 非空检测
        if(isNotEmpty(before) && isNotEmpty(after)){
            VerifyInteger verifyInteger = new VerifyInteger();
            if(verifyInteger.doVerify(before) && verifyInteger.doVerify(after)){
                // 前后都是数字，满足要求
                return true;
            }
        }
        return false;
    }
}
