package com.gc.leetcode.dp;

/**
 * @description:
 */
public class Status {
    public int lSum, rSum, mSum, iSum;

    public Status(int lSum, int rSum, int mSum, int iSum) {
        // 左节点开始的最大和
        this.lSum = lSum;
        // 右节点开始的最大和
        this.rSum = rSum;
        // 整个区间的最大子区间和
        this.mSum = mSum;
        // 整个区间的和
        this.iSum = iSum;
    }

    // geSubArray
    public int getSubArray(int[] nums) {
        return getInfo(nums, 0, nums.length - 1).mSum;
    }

    public Status getInfo(int[] a, int l, int r) {
        if (l == r) {
            return new Status(a[l], a[l], a[l], a[l]);
        }
        // 拆分
        int m = (l + r) >> 1;
        Status ls = getInfo(a, l, m);
        Status rs = getInfo(a, m + 1, r);
        return pushUp(ls, rs);

    }

    public Status pushUp(Status ls, Status rs) {
        // 合并
        int iSum = ls.iSum + rs.iSum;
        int lSum = Math.max(ls.lSum, ls.iSum + rs.lSum);
        int rSum = Math.max(rs.rSum, ls.rSum + rs.iSum);
        int mSum = Math.max(Math.max(ls.mSum, rs.mSum), ls.rSum+rs.lSum);
        return new Status(lSum, rSum, mSum, iSum);
    }

}

