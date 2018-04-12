package com.mcgraw.test.automation.framework.selenium2.ui;

public class StringItem implements UiItem {

	private String item;

	public StringItem(String item) {
		this.item = item;
	}

	@Override
	public String getLabel() {
		return item;
	}

	@Override
	public UiItem asUiItem(String label) {
		return new StringItem(item);
	}

}
