package com.gc.gitbook.skiplist;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Random;

/**
 * @description: An Efficient Random-Access List  增加根据索引操作
 */
public class SkiplistList<T> {

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


    // 查询索引为i的节点
    Node<T> findPred(int i) {
        Node<T> u = sentinel;
        int r = h;
        // index of the current node in list 0
        int j = -1;
        while (r >= 0) {
            while (u.next[r] != null && j + u.length[r] < i) {
                j += u.length[r];
                u = u.next[r];
            }
            r--;
        }
        return u;
    }

    // 跳表查询
    T get(int i) {
        return findPred(i).next[0].x;
    }

    // 替换元素
    T set(int i, T x) {
        Node<T> u = findPred(i).next[0];
        T y = u.x;
        u.x = x;
        return y;
    }

    // 指定索引插入元素
    Node<T> add(int i, T x) {
        Node<T> w = new Node(x, i);
        if (w.height() > h){
            h = w.height();
        }
        return add(i, w);
    }
    Node<T> add(int i, Node<T> w){
        Node<T> u = sentinel;
        // insert Node height
        int k = w.height();
        // list height
        int r = h;
        // index of u
        int j = -1;
        while(r >= 0){
            while (u.next[r] != null && j + u.length[r] < i) {
                j += u.length[r];
                u = u.next[r];
            }
            // accounts for new node in list 0
            u.length[r]++;
            if(r <= k){
                w.next[r] = u.next[r];
                u.next[r] = w;
                // i 表示w的索引  j 表示比w小的索引
                w.length[r] = u.length[r] - (i - j);
                // 修改前索引节点的length
                u.length[r] = i - j;
            }
            r--;
        }
        n++;
        return u;
    }

    // 根据索引删除元素，操作与add相反
    T remove(int i){
        T x = null;
        Node<T> u = sentinel;
        int r = h;
        // index of node u
        int j = -1;
        while(r >= 0) {
            while (u.next[r] != null && j + u.length[r] < i) {
                j += u.length[r];
                u = u.next[r];
            }
            // for the node we are removing
            u.length[r]--;
            if(j + u.length[r] + 1 == i && u.next[r] == null){
                x = u.next[r].x;
                // update u length
                u.length[r] += u.next[r].length[r];
                // 跳过u.next[r]
                u.next[r] = u.next[r].next[r];
                if(u == sentinel && u.next[r] == null){
                    h--;
                }
            }
            r--;
        }
        n--;
        return x;
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
        int[] length;

        Node(T ix, int h) {
            x = ix;
            next = (Node<T>[]) Array.newInstance(Node.class, h + 1);
            length = new int[h + 1];
        }

        int height() {
            return next.length - 1;
        }
    }


}


