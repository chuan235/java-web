package com.gc.leetcode.math03;

/**
 * @description: 二进制加法  https://leetcode-cn.com/problems/add-binary
 * 1101 + 1010 = 10111
 * carry
 * 1 1 -> 0    1
 * 0 0 -> 0    0
 * 1 0 -> 1    0
 */
public class BinaryAdd {

    public static void main(String[] args) {
//        String rs = new BinaryAdd().addBinary("10011101", "1101");//10101010
//        System.out.println(rs);
    }

    public String addBinary1(String a, String b) {
        StringBuilder builder = new StringBuilder();
        // 存储进位
        int carray = 0;
        // 长度不足的用0补全
        for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; i--, j--) {
            int sum = carray;
            // a.charAt(i) - '0'  获取当前位， 不足的使用0补全
            sum += i >= 0 ? a.charAt(i) - '0' : 0;
            sum += j >= 0 ? b.charAt(j) - '0' : 0;
            // 和的余数作为当前的和
            builder.append(sum % 2);
            // 和的商作为进位
            carray = sum / 2;
        }
        builder.append(carray == 1 ? carray : "");
        return builder.toString();
    }


    public String addBinary(String a, String b) {
        boolean carray = false;
        String result = "";
        int lenA = a.length();
        int lenB = b.length();
        int min = lenA < lenB ? lenA : lenB;
        for (int i = 0; i < min; i++) {
            char achar = a.charAt(lenA - 1 - i);
            char bchar = b.charAt(lenB - 1 - i);
            char addChar = ' ';
            if (achar == bchar) {
                if (achar == '0') {
                    addChar = '0';
                } else {// 1+1
                    if (carray) {
                        addChar = '1';
                    } else {
                        addChar = '0';
                    }
                    carray = true;
                    result = addChar + result;
                    continue;
                }
            } else {
                addChar = '1';
            }
            if (carray) {
                // 存在进位
                if (addChar == '0') {
                    addChar = '1';
                    carray = false;
                } else {
                    addChar = '0';
                }
            }
            result = addChar + result;
        }
        if (lenA == lenB) {
            if (carray) {
                result = '1' + result;
            } else {
                result = '0' + result;
            }
        }
        String highBitA = a.substring(0, lenA - min);
        String highBitB = b.substring(0, lenB - min);
        // true B长 false A长
        boolean flag = highBitA.length() == 0 ? true : false;
        if (flag) {
            buildHighBit(result, highBitB, carray);
        } else {
            buildHighBit(result, highBitA, carray);
        }
        if (carray) {
            result = '1' + result;
        }
        System.out.println("result=" + result);
        return result;
    }

    public String buildHighBit(String result, String high, boolean carray) {
        for (int j = high.length() - 1; j >= 0; j--) {
            char curChar = high.charAt(j);
            char addChar = ' ';
            if (carray) {
                if (curChar == '0') {
                    addChar = '1';
                    carray = false;
                } else {
                    addChar = '0';
                    result = addChar + result;
                    continue;
                }
            }
            result = addChar + result;
        }
        return result;
    }

}
