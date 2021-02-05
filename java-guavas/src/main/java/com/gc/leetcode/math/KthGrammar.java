package com.gc.leetcode.math;

/**
 * @description: https://leetcode.com/explore/learn/card/recursion-i/253/conclusion/1675/
 */
public class KthGrammar {

    /*
    N(K)
    preK = N-1(K+1/2)  123456...


     */
    public int kthGrammar(int N, int K) {
        // 0 -> 01   1 -> 10
        String[] cache = new String[N + 1];
        cache[0] = "0";
        cache[1] = "01";
        cache[2] = "0110";
        for (int i = 3; i < N; i++) {
            cache[i] = grammarHelper(cache[i - 1]);
        }
        return cache[N - 1].charAt(K - 1);
    }


    public int kthGrammar2(int N, int K) {
        if (N == 1) return 0;
        if (K % 2 == 0) {
            // 偶数索引的值与前一行相反
            return kthGrammar2(N - 1, K / 2) == 0 ? 1 : 0;
        } else {
            // 奇数索引的值与前一行 K+1/2相等
            return kthGrammar2(N - 1, (K + 1) / 2) == 0 ? 0 : 1;
        }
    }

    // 对2的优化
    public int kthGrammar3(int N, int K) {
        if (N == 1) return 0;
        // 0 -> 01   1 -> 10  左0=父0^0 左1=父0^1  右1=父1^0  右0=父1^1
        // 只需要知道父节点的值  和   K的基偶性   基数是0  偶数是1  =>  ~K & 1
        return (~K & 1) ^ kthGrammar3(N - 1, (K + 1) / 2);
    }

    // 从下向上进行递推
    public int kthGrammar4(int N, int K) {
        int res = 0;
        while (K > 1) {
            // 计算出父级K的位置  奇数位父子节点相同  偶数位相反
            K = (K % 2 == 1) ? K + 1 : K / 2;
            res ^= 1;
        }
        return res;
    }

    // 统计K-1中二进制记法的1的个数
    public int kthGrammar5(int N, int K) {
        int cnt = 0;
        --K;
        while (K != 0) {
            cnt += K & 1;
            K >>= 1;
        }
        return cnt % 2;
    }

    private String grammarHelper(String pre) {
        // 每四位构建一次
        StringBuffer buffer = new StringBuffer();
        int curIndex = 0;
        while (curIndex <= pre.length()) {
            String tmp = pre.substring(curIndex, curIndex + 4);
            if ("0110".equals(tmp)) {
                buffer.append("01101001");
            } else if ("1001".equals(tmp)) {
                buffer.append("10010110");
            }
            curIndex += 4;

        }
        return buffer.toString();
    }
}
