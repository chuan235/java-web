package com.gc.leetcode.theads;

import java.util.concurrent.CountDownLatch;

/**
 * 开启三个线程顺序打印同一个对象的三个方法
 */
public class SeqPrint {

    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();
        Thread threadA = new Thread(() -> {
            try {
                foo.one();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "threadA");

        Thread threadB = new Thread(() -> {
            try {
                foo.two();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "threadB");

        Thread threadC = new Thread(() -> {
            try {
                foo.three();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "threadC");

        threadA.start();
        threadB.start();
        threadC.start();

        new CountDownLatch(1).await();
    }

}


class Foo {

    private Object lock = new Object();
    private int status = 0;

    public void one() throws InterruptedException {
        synchronized (lock) {
            status++;
            System.out.println(Thread.currentThread().getName() + ": one");
            lock.notify();
        }
    }

    public void two() throws InterruptedException {
        synchronized (lock) {
            while (status != 1) {
                lock.wait();
            }
            System.out.println(Thread.currentThread().getName() + ": two");
            status++;
            lock.notify();
        }
    }

    public void three() throws InterruptedException {
        synchronized (lock) {
            while (status != 2) {
                lock.wait();
            }
            System.out.println(Thread.currentThread().getName() + ": three");
            lock.notify();
        }
    }
}