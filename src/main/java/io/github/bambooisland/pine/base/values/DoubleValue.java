package io.github.bambooisland.pine.base.values;

import io.github.bambooisland.pine.base.Operator;

@SuppressWarnings("serial")
public class DoubleValue extends FieldValue {
    public final static Operator<DoubleValue> EQUAL = (value, target) -> {
        return value.getDouble() == target.getDouble();
    };
    public final static Operator<DoubleValue> GREATER = (value, target) -> {
        return value.getDouble() > target.getDouble();
    };
    public final static Operator<DoubleValue> LESS = (value, target) -> {
        return value.getDouble() < target.getDouble();
    };
    public final static Operator<DoubleValue> GREATER_EQUAL = (value, target) -> {
        return value.getDouble() >= target.getDouble();
    };
    public final static Operator<DoubleValue> LESS_EQUAL = (value, target) -> {
        return value.getDouble() <= target.getDouble();
    };
    public final static Operator<DoubleValue> NOT_EQUAL = (value, target) -> {
        return value.getDouble() != target.getDouble();
    };

    private double value;

    public DoubleValue(String value) {
        this.value = Double.parseDouble(value);
    }

    public DoubleValue(double value) {
        this.value = value;
    }

    public double getDouble() {
        return value;
    }

    @Override
    public String getStringValue() {
        return Double.toString(value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DoubleValue other = (DoubleValue) obj;
        if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
            return false;
        return true;
    }
}
