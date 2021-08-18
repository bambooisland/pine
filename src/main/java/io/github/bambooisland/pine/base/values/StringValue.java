package io.github.bambooisland.pine.base.values;

import java.util.function.BiPredicate;
import java.util.regex.Pattern;

import io.github.bambooisland.pine.base.Operator;

@SuppressWarnings("serial")
public class StringValue extends FieldValue {
    public final static Operator<StringValue> EQUAL_OPERATOR = (value, target) -> {
        return value.equals(target);
    };
    public final static Operator<StringValue> NOT_EQUAL_OPERATOR = (value, target) -> {
        return !value.equals(target);
    };
    @Deprecated
    public final static Operator<StringValue> LIKE_OPERATOR = (value, target) -> {
        return value.getString().matches(target.getString());
    };
    @Deprecated
    public final static Operator<StringValue> NOT_LIKE_OPERATOR = (value, target) -> {
        return !value.getString().matches(target.getString());
    };
    public final static BiPredicate<StringValue, Pattern> LIKE_PREDICATE = (value, target) -> {
        return target.matcher(value.getString()).matches();
    };
    public final static BiPredicate<StringValue, Pattern> NOT_LIKE_PREDICATE = (value, target) -> {
        return !target.matcher(value.getString()).matches();
    };

    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getString() {
        return value;
    }

    @Override
    public String getStringValue() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        StringValue other = (StringValue) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
}
