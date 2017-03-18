package com.aurea.deadcode.detector.infra.func;

import java.util.function.Function;

@FunctionalInterface
public interface ElseFunction<T, R> extends Function<T, R> {

    default R elseApply(final T t) {
        return this.apply(t);
    }

}
