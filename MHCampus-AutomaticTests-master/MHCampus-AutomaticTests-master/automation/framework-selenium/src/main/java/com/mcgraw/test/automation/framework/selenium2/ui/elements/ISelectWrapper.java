package com.mcgraw.test.automation.framework.selenium2.ui.elements;

public interface ISelectWrapper {
	public org.openqa.selenium.support.ui.Select getWrappedSelector();
	
	public void selectOptionByIndex(int index);
	public void selectOptionByName(String value);
	
}
