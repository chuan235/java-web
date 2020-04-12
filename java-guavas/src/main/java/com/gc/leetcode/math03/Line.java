package com.gc.leetcode.math03;

import java.math.BigDecimal;

public class Line {

    private Point src;
    private Point dest;
    private BigDecimal rate;

    public Line() {
    }

    public Line(Point src, Point dest, BigDecimal rate) {
        this.src = src;
        this.dest = dest;
        this.rate = rate;
    }

    public Point getSrc() {
        return src;
    }

    public void setSrc(Point src) {
        this.src = src;
    }

    public Point getDest() {
        return dest;
    }

    public void setDest(Point dest) {
        this.dest = dest;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
