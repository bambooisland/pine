package io.github.bambooisland.pine.base.values;

import io.github.bambooisland.pine.base.Operator;

@SuppressWarnings("serial")
public class BoolValue extends FieldValue {
    public final static Operator<BoolValue> EQUAL = (value, target) -> {
        return value.getBool() == target.getBool();
    };
    public final static Operator<BoolValue> NOT_EQUAL = (value, target) -> {
        return value.getBool() != target.getBool();
    };

    private boolean value;

    public BoolValue(String value) {
        this.value = Boolean.parseBoolean(value);
    }

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getBool() {
        return value;
    }

    @Override
    public String getStringValue() {
        return Boolean.toString(value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (value ? 1231 : 1237);
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
        BoolValue other = (BoolValue) obj;
        if (value != other.value)
            return false;
        return true;
    }
}
