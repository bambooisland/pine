package io.github.bambooisland.pine.base;

import io.github.bambooisland.pine.base.values.FieldValue;

@FunctionalInterface
public interface Operator<T extends FieldValue> {
    public boolean accept(T value, T target);
}
