package com.gc.leetcode.theads;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class H2O {

    private CountDownLatch hLatch = new CountDownLatch(2);
    private CountDownLatch oLatch = new CountDownLatch(1);

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        oLatch.await();
        releaseHydrogen.run();
        hLatch.countDown();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        releaseOxygen.run();
        oLatch.countDown();
        hLatch.await();
    }


    private Semaphore hPhore = new Semaphore(2);
    private Semaphore oPhore = new Semaphore(0);

    public void hydrogen1(Runnable releaseHydrogen) throws InterruptedException {
        hPhore.acquire();
        releaseHydrogen.run();
        oPhore.release();
    }

    public void oxygen1(Runnable releaseOxygen) throws InterruptedException {
        oPhore.acquire(2);
        releaseOxygen.run();
        hPhore.release(2);
    }

    private Object lock = new Object();
    private int groupSize = 0;

    public void hydrogen2(Runnable releaseHydrogen) throws InterruptedException {
        synchronized (lock){
            while(groupSize > 0 && groupSize <= 2){
                releaseHydrogen.run();
                groupSize++;
            }
            lock.wait();
        }
    }

    public void oxygen2(Runnable releaseOxygen) throws InterruptedException {
        while(groupSize == 0){
            releaseOxygen.run();
            groupSize++;
            lock.notify();
        }
        lock.wait();
    }

}
