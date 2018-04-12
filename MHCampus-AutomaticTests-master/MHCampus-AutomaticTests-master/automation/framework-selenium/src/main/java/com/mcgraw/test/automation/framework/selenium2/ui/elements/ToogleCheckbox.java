package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ToogleCheckbox extends CheckBox {

	public ToogleCheckbox(WebElement webElement) {
		super(webElement);
	}

	@Override
	public void changeState() {
		WebElement parent = webElement.findElement(By.xpath("./parent::*/span"));
		parent.click();
	}
	
}
