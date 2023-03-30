package io.github.bambooisland.pine.base;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
public class Element implements Serializable {
    private Field<?>[] fields;

    public Element(Field<?>[] fields) {
        this.fields = fields;
    }

    public Field<?> getField(int i) {
        return fields[i];
    }

    public Field<?>[] getAllFields() {
        return fields;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(fields);
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
        Element other = (Element) obj;
        if (!Arrays.equals(fields, other.fields))
            return false;
        return true;
    }
}
