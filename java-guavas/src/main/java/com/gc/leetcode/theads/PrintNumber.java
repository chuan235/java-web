package com.gc.leetcode.theads;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;


public class PrintNumber {

    public static void main(String[] args) {
        // 0102030405
        ZeroEvenOdd print = new ZeroEvenOdd(5);
        Thread zeroThread = new Thread(() -> {
            try {
                while(print.getNum() > 0){
                    print.zero((printNumber) -> {
                        System.out.print(printNumber);
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread evenThread = new Thread(() -> {
            try {
                while(print.getNum() >= 0) {
                    print.even((printNumber) -> {
                        System.out.print(printNumber);
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread oddThread = new Thread(() -> {
            try {
                while(print.getNum() >= 0) {
                    print.odd((printNumber) -> {
                        System.out.print(printNumber);
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        zeroThread.start();
        evenThread.start();
        oddThread.start();
    }
}

class ZeroEvenOdd {

    private int n;
    private Semaphore even = new Semaphore(0);
    private Semaphore odd = new Semaphore(0);
    private Semaphore zero = new Semaphore(1);

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    /**
     * printNumber.accept(x) outputs "x", where x is an integer.
     */
    public void zero(IntConsumer printNumber) throws InterruptedException {
        zero.acquire();
        printNumber.accept(0);
        if (n % 2 == 0) {
            odd.release();
        } else {
            even.release();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        even.acquire();
        printNumber.accept(n);
        n--;
        zero.release();
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        odd.acquire();
        printNumber.accept(n);
        n--;
        zero.release();
    }

    public int getNum() {
        return n;
    }
}
