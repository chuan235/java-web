package com.gc.leetcode.number;

import java.util.LinkedList;

/**
 * @description: 两个链表相加，返回一个新的链表
 *
 * 2 4 3
 * 5 6 4 4
 *
 * 7 0 8 4
 */
public class ListNodeAdd {

    public static void main(String[] args) {
        LinkedList<Integer> l1 = new LinkedList<>();
        LinkedList<Integer> l2 = new LinkedList<>();

        l1.addFirst(3);
        l1.addFirst(4);
        l1.addFirst(2);

        l2.addFirst(4);
        l2.addFirst(4);
        l2.addFirst(6);
        l2.addFirst(5);

        System.out.println(addTwoNumbers(l1, l2));
    }

    public static LinkedList<Integer> addTwoNumbers(LinkedList<Integer> l1, LinkedList<Integer> l2) {
        LinkedList<Integer> result = new LinkedList<>();
        int upBit = 0;
        // 遍历相加
        while (l1.size() > 0 && l2.size() > 0) {
            int total = l1.removeFirst() + l2.removeFirst();
            if (upBit == 1) {
                // 存在进位
                result.addFirst((total >= 10 ? total % 10 : total) + 1);
            } else {
                result.addFirst(total >= 10 ? total % 10 : total);
            }
            upBit = total >= 10 ? 1 : 0;
//            if (total >= 10) {
//                // 检查是否已经存在进位
//                if (upBit == 1) {
//                    result.addFirst(total % 10 + 1);
//                } else {
//                    result.addFirst(total % 10);
//                }
//                upBit = 1;
//            } else {
//                // 检查是否已经存在进位
//                if (upBit == 1) {
//                    result.addFirst(total + 1);
//                } else {
//                    result.addFirst(total);
//                }
//                upBit = 0;
//            }
        }
        // 链表的长度不一样
        boolean flag = l1.size() > l2.size() ? true : false;
        if(flag || l2.size() > l1.size()){
            if(flag){
                // 遍历l1
                while(l1.size() > 0){
                    result.addFirst(l1.removeFirst());
                }
//                for (int i = l2.size(); i < l1.size(); i++) {
//                    result.addFirst(l1.get(i));
//                }
            }else{
                // 遍历l2
                while(l2.size() > 0){
                    result.addFirst(l2.removeFirst());
                }
//                for (int i = l1.size(); i < l2.size(); i++) {
//                    result.addFirst(l2.get(i));
//                }
            }
        }
        return result;
    }
}
