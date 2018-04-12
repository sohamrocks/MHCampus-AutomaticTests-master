package com.mcgraw.test.automation.framework.selenium2.ui;

/**
 * JavaScript functions
 * 
 * @author Andrei Varabyeu
 * 
 */
public enum JSActions {
	CLICK("click"),
	MOUSEOVER("mouseover"),
	FOCUS("focus"),
	MOUSEMOVE("mousemove"),
	MOUSEDOWN("mousedown"),
	FOCUSOUT("focusout"),
	BLUR("blur"),
	CHANGE("change"),
	KEYDOWN("keydown"),
	SUBMIT("submit");

	private String functionName;

	private JSActions(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

}