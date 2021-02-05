package com.gc.gitbook.skiplist;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Random;

/**
 * @description: An Efficient SSet
 */
public class SkiplistSet<T> {

    // 第一个空的链表，起到一个维护索引的作用
    private Node<T> sentinel;
    // 整个跳表最大的高度
    private int h;

    private transient Comparator<T> comparator;
    private Random random = new Random();
    // 缓存索引  插入的节点就靠这个来维护索引
    private Node<T>[] stack;
    // 节点数量
    private int n;

    public SkiplistSet(int height, Comparator<T> comparator){
        this.h = height;
        this.comparator = comparator;
        this.sentinel = new Node<>(null, height);
        this.n = 0;
    }

    // 添加元素 O(logn)
    Node<T> add(T x) {
        Node<T> u = sentinel;
        int r = h;
        int comp = 0;
        // step1: loop find
        while (r >= 0) {
            while (u.next[r] != null && (comp = comparator.compare(u.next[r].x, x)) < 0) {
                u = u.next[r];
            }
            if (u.next[r] != null && comp == 0) {
                // already exists
                return u.next[r];
            }
            // go down store u
            stack[r--] = u;
        }
        // step2: insert
        Node<T> w = new Node<>(x, pickHeight());
        while (h < w.height()) {
            // height increased
            stack[++h] = sentinel;
        }
        // insert w index
        for (int i = 0; i < w.next.length; i++) {
            w.next[i] = stack[i].next[i];
            stack[i].next[i] = w;
        }
        n++;
        return w;
    }

    // 查询x的前一个节点
    Node<T> findPredNode(T x) {
        Node<T> u = sentinel;
        int r = h;
        while (r >= 0) {
            while (u.next[r] != null && comparator.compare(u.next[r].x, x) < 0) {
                // 找到比当前小的索引值
                // go right in list r
                u = u.next[r];
            }
            // go down into list r-1
            // 找不到的时候向下寻找
            r--;
        }
        return u;
    }

    boolean remove(T x) {
        boolean result = false;
        Node<T> u = sentinel;
        int r = h;
        int comp = 0;
        while (r >= 0) {
            while (u.next[r] != null && (comp = comparator.compare(u.next[r].x, x)) < 0) {
                u = u.next[r];
            }
            // 判断值是否相等
            if (u.next[r] != null && comp == 0) {
                // delete index
                u.next[r] = u.next[r].next[r];
                if (u == sentinel && u.next[r] == null) {
                    // height has gone down
                    h--;
                }
                result = true;
            }
            // go down delete
            r--;
        }
        if (result) n--;
        return result;
    }

    // 跳表查询
    T find(T x) {
        Node<T> u = findPredNode(x);
        return u.next[0] == null ? null : u.next[0].x;
    }

    // 随机生成高度的逻辑
    int pickHeight() {
        int z = random.nextInt();
        int k = 0;
        int m = 1;
        // 循环判断z的二进制哪一位是0
        while ((z & m) != 0) {
            k++;
            m <<= 1;
        }
        return k;
    }

    static class Node<T> {
        T x;
        Node<T>[] next;

        Node(T ix, int h) {
            x = ix;
            next = (Node<T>[]) Array.newInstance(Node.class, h + 1);
        }

        int height() {
            return next.length - 1;
        }
    }


}


