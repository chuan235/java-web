package com.gc.leetcode.math03;

public class Point {

    private int x;
    private int y;
    private boolean isAcross = false;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isAcross() {
        return isAcross;
    }

    public void setAcross(boolean across) {
        isAcross = across;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
