public class Temp1 {
    final void longAccumulate(long x, LongBinaryOperator fn, boolean wasUncontended) {
        int h;
        // 线程随机值有没有初始化
        if ((h = getProbe()) == 0) {
            // 0就代表没有初始化，强制将当前线程初始化 probe=1
            ThreadLocalRandom.current();
            // 默认初始化之后 h = 1
            h = getProbe();
            wasUncontended = true;
        }
        // True if last slot nonempty
        boolean collide = false;
        for (; ; ) {
            Cell[] as;
            Cell a;
            int n;
            long v;
            if ((as = cells) != null && (n = as.length) > 0) {
                // cells不是null 并且已经初始化了
                // 找到当前线程的cell应该放的位置
                if ((a = as[(n - 1) & h]) == null) {
                    // index = (length-1) & h
                    // 位置是null，没有线程占用这个位置，尝试创建一个cell
                    if (cellsBusy == 0) {
                        // Optimistically create  对象逃逸
                        Cell r = new Cell(x);
                        // 修改 cellsBusy 为 1
                        if (cellsBusy == 0 && casCellsBusy()) {
                            boolean created = false;
                            try {               // Recheck under lock
                                Cell[] rs;
                                int m, j;
                                // 将cell放入数组的时候再次检查一遍
                                // 目的：如果当前线程在new Cell的时候，其他线程初始化了rs[index]的cell，那么这里就需要检查一下
                                if ((rs = cells) != null && (m = rs.length) > 0 && rs[j = (m - 1) & h] == null) {
                                    rs[j] = r;
                                    created = true;
                                }
                                // 如果其他线程已经把位置占了，则会跳过创建，进行下一次循环（扩容hash或者修改cell的值）
                            } finally {
                                cellsBusy = 0;
                            }
                            if (created){
                                break;
                            }
                            continue;           // Slot is now non-empty
                        }
                    }
                    // cellsBusy已经被其他线程修改（其他线程在对cells进行扩容）  修改cellsBusy失败
                    // 重新hash
                    collide = false;
                } else if (!wasUncontended){    // CAS already known to fail
                    // CAS失败之后重新hash
                    wasUncontended = true;      // Continue after rehash
                }else if (a.cas(v = a.value, ((fn == null) ? v + x : fn.applyAsLong(v, x)))){
                    // 如果index的cell不是null，则使用cas去修改这个cell的值
                    // 修改成功退出
                    break;
                }else if (n >= NCPU || cells != as){
                    // At max size or stale  cell数组最大等于CPU数量  或者是 cells已经被其他线程扩容了
                    // 再次循环   重新hash
                    collide = false;
                }else if (!collide){
                    // 再次hash
                    collide = true;
                }else if (cellsBusy == 0 && casCellsBusy()) {
                    // 当前index的cell不是空，并且cas修改cell的值失败了，但是这里表示当前没有线程去操作cells
                    // 本线程会尝试将 CellsBusy 置 1
                    try {
                        // Expand table unless stale
                        if (cells == as) {
                            // 将cells扩容   *2
                            Cell[] rs = new Cell[n << 1];
                            for (int i = 0; i < n; ++i){
                                rs[i] = as[i];
                            }
                            cells = rs;
                        }
                    } finally {
                        cellsBusy = 0;
                    }
                    collide = false;
                    continue;                   // Retry with expanded table
                }
                // 修改当前线程的probe  如果probe=1  ->  计算后为 270369  就等于是一个散列函数
                h = advanceProbe(h);
            } else if (cellsBusy == 0 && cells == as && casCellsBusy()) {
                // cells没有在初始化 + cells==null + 将cellsBusy设置为1
                boolean init = false;
                // Initialize cells
                try {
                    // 再次检查是null
                    if (cells == as) {
                        // 初始化cells  长度是2
                        Cell[] rs = new Cell[2];
                        // 默认的h=1  rs[1] = new Cell(x);
                        rs[h & 1] = new Cell(x);
                        // 初始化完成
                        cells = rs;
                        init = true;
                    }
                } finally {
                    // 标记置为0
                    cellsBusy = 0;
                }
                // 结束循环
                if (init){
                    break;
                }
            } else if (casBase(v = base, ((fn == null) ? v + x : fn.applyAsLong(v, x)))){
                // 如果这时cellsBusy不等于0或者 cells数组已经初始化  那么就会去CAS Base + X 如果失败继续循环，成功就退出
                break;                          // Fall back on using base
            }
        }
    }



    public void add(long x) {
        Cell[] as; long b, v; int m; Cell a;
        // 1、如果cells为null，则尝试修改base值
        if ((as = cells) != null || !casBase(b = base, b + x)) {
            // 2、cells不为null或者修改base出现竞争
            boolean uncontended = true;
            if (as == null || (m = as.length - 1) < 0 ||
                    (a = as[getProbe() & m]) == null ||
                    !(uncontended = a.cas(v = a.value, v + x))){
                // 修改base值  初始化  填入cell  修改cell  扩容
                longAccumulate(x, null, uncontended);
            }
        }
    }


    public long sum() {
        Cell[] as = cells; Cell a;
        // 拿出base
        long sum = base;
        if (as != null) {
            // 把cells数组中的值加起来
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    sum += a.value;
            }
        }
        return sum;
    }

    /**
     *
     * @param accumulatorFunction 运算接口,传入两个long值，然后返回这两个值的计算结果
     * @param identity 累加器的初始值
     */
    public LongAccumulator(LongBinaryOperator accumulatorFunction, long identity) {
        this.function = accumulatorFunction;
        base = this.identity = identity;
    }

}
