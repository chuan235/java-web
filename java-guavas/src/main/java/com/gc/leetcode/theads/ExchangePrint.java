package com.gc.leetcode.theads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 交替打印
 * 1、synchronized + boolean
 * 2、volatile
 * 3、AtomicInteger
 * 4、Semaphore
 * 5、ReentrantLock + Condition + boolean
 * 6、CountDownLatch做减法，CyclicBarrier做加法
 *
 */
public class ExchangePrint {

    public static void main(String[] args) throws InterruptedException {
        FooBar fooBar = new FooBar();
        Thread foo = new Thread(() -> {
            try {
                fooBar.foo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "foo");
        Thread bar = new Thread(() -> {
            try {
                fooBar.bar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "bar");

        foo.start();
        bar.start();

        new CountDownLatch(1).await();
    }

}

class FooBar {
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void foo() throws InterruptedException, BrokenBarrierException {
        for (int i = 0; i < 10; i++) {
            cyclicBarrier.await();
            // 最开始需要可以进来执行
            System.out.print("foo");
            countDownLatch.countDown();
        }
    }

    public void bar() throws InterruptedException, BrokenBarrierException {
        for (int i = 0; i < 10; i++) {
            cyclicBarrier.await();
            countDownLatch.await();
            System.out.print("bar\n");
            countDownLatch = new CountDownLatch(1);
        }
    }
}