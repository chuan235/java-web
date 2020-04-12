package com.gc.leetcode.math02;

/**
 * @description: 校验是不是一个科学计数法
 */
public class VerifySienceNumber extends VerifyNumberTemplate {
    @Override
    protected boolean doVerify(String s) {
        // 检测e
        s = s.toLowerCase();
        int index = s.indexOf("e");
        if (index == 0 || index == -1) return false;
        // 根据e分割
        String partI = s.substring(0, index);
        String partII = s.substring(index + 1);
        // 非空
        if(isNotEmpty(partI) && isNotEmpty(partII)){
            // 正式校验
            if(verifyPartE(partI) && verifyPartE(partII)){
                return true;
            }
        }
        return false;
    }

    public boolean verifyPartE(String part){
        // 34 e 32  错误的表达
        if(startOrEndSpace(part)) return false;
        // 是一个小数或者是一个整数  1.2e3.4 3e3.4
        VerifyInteger verifyInteger = new VerifyInteger();
        VerifyDecimal verifyDecimal = new VerifyDecimal();
        if(verifyDecimal.doVerify(part) || verifyInteger.doVerify(part)){
            return true;
        }
        return false;
    }

    public boolean startOrEndSpace(String string){
        if(string.startsWith(" ") || string.endsWith(" ")){
            return true;
        }
        return false;
    }

}
