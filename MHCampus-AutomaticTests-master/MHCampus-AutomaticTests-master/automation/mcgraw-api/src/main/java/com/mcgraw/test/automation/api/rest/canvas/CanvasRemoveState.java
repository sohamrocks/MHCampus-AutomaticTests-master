package com.mcgraw.test.automation.api.rest.canvas;

public enum CanvasRemoveState {
	CONCLUDE("conclude"),
	DELETE("delete");
	
	private String value;

	private CanvasRemoveState(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static CanvasRemoveState getByValue(String value) {
		for (CanvasRemoveState canvasRemoveState : values()) {
			if (canvasRemoveState.getValue().equals(value)) {
				return canvasRemoveState;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + value + "]");
	}

}
