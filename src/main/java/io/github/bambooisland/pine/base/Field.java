package io.github.bambooisland.pine.base;

import java.io.Serializable;

import io.github.bambooisland.pine.base.values.FieldValue;

@SuppressWarnings("serial")
public class Field<T extends FieldValue> implements Serializable {
    private Column column;
    private T value;

    public Field(Column column, T value) {
        this.column = column;
        this.value = value;
    }

    public Column getColumn() {
        return column;
    }

    public T getValue() {
        return value;
    }

    public String getStringValue() {
        return value.getStringValue();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((column == null) ? 0 : column.hashCode());
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
        Field<?> other = (Field<?>) obj;
        if (column == null) {
            if (other.column != null)
                return false;
        } else if (!column.equals(other.column))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

}
