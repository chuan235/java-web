package com.gc.leetcode.math03;

import java.math.BigDecimal;
import java.util.*;

/**
 * @description: 给定坐标，求在一条直线上的最多点的个数
 */
public class MaxPoints {

    public static void main(String[] args) {
        MaxPoints points = new MaxPoints();
        int[][] data = new int[][]{
                new int[]{1, 1},
                new int[]{3, 3},
                new int[]{2, 2}};
        System.out.println("直线上最多的点："+points.maxPoints(data));


        int[][] data1 = new int[][]{
                new int[]{1, 1},
                new int[]{3, 2},
                new int[]{5, 3},
                new int[]{4, 1},
                new int[]{2, 3},
                new int[]{1, 4}};

        System.out.println("直线上最多的点："+points.maxPoints(data1));
//        Point A = new Point(2, 5);
//        Point B = new Point(5, 9);
//        points.calcoolateRate(A, B);
    }


    public int maxPoints(int[][] points) {
        List<Point> pointList = new ArrayList<>();
        // 构建点
        int size = points.length;
        for (int i = 0; i < size; i++) {
            pointList.add(new Point(points[i][0], points[i][1]));
        }

        // 使用一个Map来存储斜率  k  ---  toPoint
        Map<BigDecimal, Integer> kMap = new HashMap<>();
        // 循环构建直线
        for (int i = 0; i < size; i++) {
            // 选择基本点
            Point basePoint = pointList.get(i);
            // 构建直线方程  比较rate  两个不重复的点之间的所有可能情况
            for (int j = i + 1; j < size; j++) {
                Point dest = pointList.get(j);
                BigDecimal rate = this.calculateRate(basePoint, dest);
                if (kMap.containsKey(rate)) {
                    kMap.put(rate, kMap.get(rate) + 1);
                    continue;
                }
                kMap.put(rate, 1);
            }
        }
        System.out.println(kMap);
        Integer makeUps = maxValue(kMap) * 2;
        // 12 /2  /3 /4 6/2 6/3
        return calculateCount(makeUps, 2);
    }

    public int calculateCount(int makeUp, int rate) {
        if (makeUp == 1) return 1;
        int half = makeUp / rate;
        if (half * (half + 1) == makeUp) return half + 1;
        return calculateCount(makeUp, rate + 1);
    }

    private Integer maxValue(Map<BigDecimal, Integer> map) {
        if (map == null || map.size() == 0) return 0;
        Collection<Integer> collection = map.values();
        Object[] array = collection.toArray();
        Arrays.sort(array);
        return (Integer) array[array.length - 1];
    }

    private BigDecimal calculateRate(Point src, Point dest) {
        int srcX = src.getX();
        int srcY = src.getY();
        int destX = dest.getX();
        int destY = dest.getY();
        if (srcX == destX && srcY == destY) {
            throw new IllegalArgumentException("参数出现相同的坐标");
        }
        if (srcX == destX) {
            return new BigDecimal(1.0);
        }
        if (srcY == destY) {
            return new BigDecimal(0);
        }
        BigDecimal decimalY = new BigDecimal(destY - srcY);
        BigDecimal decimalX = new BigDecimal(destX - srcX);
        BigDecimal divide = divide(decimalY, decimalX, 2);
        return divide;
    }


    public static BigDecimal divide(BigDecimal b1, BigDecimal b2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else if (b1 != null && b2 != null) {
            return b1.divide(b2, scale, 4);
        } else if (b1 == null && b2 == null) {
            return null;
        } else if (b1 == null) {
            return null;
        } else {
            return b2 == null ? null : null;
        }
    }
}
