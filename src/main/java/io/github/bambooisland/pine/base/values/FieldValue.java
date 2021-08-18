package io.github.bambooisland.pine.base.values;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class FieldValue implements Serializable {
    public abstract String getStringValue();
}
