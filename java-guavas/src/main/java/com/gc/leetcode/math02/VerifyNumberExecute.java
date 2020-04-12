package com.gc.leetcode.math02;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 校验执行者
 */
public class VerifyNumberExecute implements VerifyNumber {

    private List<VerifyNumber> chains = new ArrayList<>();

    public VerifyNumberExecute(){
        this.initVerify();
    }

    private void initVerify(){
        VerifyHex verifyHex = new VerifyHex();
        chains.add(verifyHex);
        VerifyInteger verifyInteger = new VerifyInteger();
        chains.add(verifyInteger);
        VerifyDecimal verifyDecimal = new VerifyDecimal();
        chains.add(verifyDecimal);
        VerifySienceNumber verifySienceNumber = new VerifySienceNumber();
        chains.add(verifySienceNumber);
    }

    @Override
    public boolean verify(String str) {
        for (VerifyNumber chain : chains) {
            boolean result = chain.verify(str);
            System.out.println(chain.getClass().getSimpleName()+" check result: "+ result);
            if(result) return true;
//            if(chain.verify(str)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        VerifyNumberExecute execute = new VerifyNumberExecute();
        //execute.verify(" 0.1 ");
        // 增加一个检验16进制的数  0X0-F
        execute.verify("  -90e3    ");
        execute.verify("  0Xabf72    ");
    }
}
