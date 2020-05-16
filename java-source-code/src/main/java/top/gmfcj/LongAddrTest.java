package top.gmfcj;

import org.junit.Test;

import java.util.concurrent.atomic.LongAdder;

/**
 * LongAddr的核心类 Striped64  抽象类
 * <p>
 * Striped64 ->  内部类Cell，Cell只负责记录一个long型的value值(使用CAS设置cell的值)
 * 1、NCPU  代表当前操作系统的CPU数量
 * 2、cells cell数组
 * 3、base  base值，在没有线程竞争的情况下使用base
 * 4、cellsBusy  代表是否正在创建cell（在进行resize或者create cell的时候充当CAS锁）
 * <p>
 * PROBE = UNSAFE.objectFieldOffset
 * (Thread.class.getDeclaredField("threadLocalRandomProbe"));
 * PROBE表示每一个线程的hash码，在没有竞争的情况下，默认是0
 * 直到它们在 cellsBusy 竞争的时候，就会修改Probe为当前线程的hashCode，后续会根据这个值算出这一个线程存放的数据应为位于数组中的哪一个cell
 * 如果算出来的索引已经存在cell了，这时就是hash碰撞，就会扩容，再次计算index，知道index对应的cell为空的时候，就创建一个新的cell保存线程要设置的值
 */
public class LongAddrTest {

    @Test
    public void test() {
        long probe = 1;
        probe ^= probe << 13;   // xorshift
        probe ^= probe >>> 17;
        probe ^= probe << 5;
        // 2^18 + 2^13 + 2^5 + 1 = 270369
        System.out.println(probe);
    }

    @Test
    public void objectTaoyi() {
        for (int i = 0; i < 10000; i++) {
            // 对象逃逸
            String s = new String("ooxx" + i);
            System.out.println(s);
        }
    }
}
