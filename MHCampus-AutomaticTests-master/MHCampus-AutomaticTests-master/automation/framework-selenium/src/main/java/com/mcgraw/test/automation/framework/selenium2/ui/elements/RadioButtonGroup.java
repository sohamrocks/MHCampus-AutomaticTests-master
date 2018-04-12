package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.selenium2.ui.UiItem;

/**
 * Base Radio Button Group representation
 *
 * @author Andrei Varabyeu
 *
 */
public class RadioButtonGroup extends Element {

	private static final String THIS_ELEMENT_RELATIVE_PATH = ".//";

	public RadioButtonGroup(WebElement webElement) {
		super(webElement);
	}

	public void selectItem(String item) {
		webElement.findElement(
				By.xpath(THIS_ELEMENT_RELATIVE_PATH + "*[text()='" + item
						+ "']/input")).click();
	}

	public void selectItem(UiItem item) {
		selectItem(item.getLabel());
	}

	public void selectItemByInputId(String inputID) {
		webElement.findElement(
				By.xpath(THIS_ELEMENT_RELATIVE_PATH + "input[@id='" + inputID
						+ "']")).click();
	}

}
