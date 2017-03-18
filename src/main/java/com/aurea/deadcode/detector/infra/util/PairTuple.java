package com.aurea.deadcode.detector.infra.util;

public class PairTuple<S, T> {

    private final S x;
    private final T y;

    public PairTuple(S x, T y) {
        this.x = x;
        this.y = y;
    }

    public S getX() {
        return x;
    }

    public T getY() {
        return y;
    }
}
