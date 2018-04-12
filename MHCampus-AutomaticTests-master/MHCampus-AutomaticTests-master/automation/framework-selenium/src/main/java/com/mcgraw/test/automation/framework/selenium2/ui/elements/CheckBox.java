/**
 *
 */
package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * @author Andrei Varabyeu
 *
 */
public class CheckBox extends Element {

	public CheckBox(WebElement webElement) {
		super(webElement);
	}

	public boolean isChecked() {
		String checkedAttr = webElement.getAttribute("checked");
		return (null != checkedAttr);
	}

	public boolean setChecked(boolean checked) {
		if (checked) {
			Logger.operation("Checking '" + getIdentifyingText() + "' checkbox.");
		} else {
			Logger.operation("Unchecking '" + this.getIdentifyingText()
					+ "' checkbox.");
		}

		if (((!(checked) || isChecked())) && ((checked || !(isChecked()))))
			return false;
		changeState();
		return true;
	}

	public void changeState() {
		webElement.click();
	}

}
