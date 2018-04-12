package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.google.common.collect.Lists;
import com.mcgraw.test.automation.framework.selenium2.ui.UiItem;
import com.mcgraw.test.automation.framework.selenium2.ui.WebDriverFunctions;

/**
 * Combobox Representation<br>
 * This class contains type to avoid using plaing strings<br>
 * We should use enums or some another holders for constants for the UI elements
 *
 * @author Andrei Varabyeu
 *
 * @param <T>
 */
public class ComboBox<T extends UiItem> extends Element {

	private Select select;

	public ComboBox(WebElement webElement) {
		super(webElement);
		select = new Select(webElement);
	}

	public List<String> getAllItems() {
		return Lists.transform(getOptions(),
				WebDriverFunctions.getWebElementText());
	}

	public void selectItem(T t) {
		select.selectByValue(t.getLabel());
	}

	private List<WebElement> getOptions() {
		return select.getOptions();
	}

}
