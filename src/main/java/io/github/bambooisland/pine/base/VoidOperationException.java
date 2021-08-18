package io.github.bambooisland.pine.base;

import java.io.IOException;

@SuppressWarnings("serial")
public class VoidOperationException extends IOException {
	public VoidOperationException() {
		super();
	}

	public VoidOperationException(String string) {
		super(string);
	}
}
