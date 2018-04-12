package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.exception.ItemNotFoundException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.JSActions;

public class AutoCompleteInput extends Input {

	public enum AutocompleteType {
		OPTIONS_SELECTOR, LINKED_SWITCHER;
	}

	public static final String AUTOCOMPLETE_SUGGESTIONS_LOCATOR = "//div[@class='ac_results'][contains(@style,'display: block;')]/ul/li";
	public static final String SWITCHER_SUGGESTIONS_LOCATOR = "//ul[contains(@class,'ui-autocomplete')][contains(@style,'display: block;')]/li";

	public AutoCompleteInput(WebElement webElement) {
		super(webElement);
	}

	private AutocompleteType getInputType() {
		Logger.debug("Determining input type...");
		if (this.getAttribute("class").contains("ac_input")) {
			Logger.debug("Input type: simple autocomplete input or multiple comma-separated values selector");
			return AutocompleteType.OPTIONS_SELECTOR;
		} else if (this.getAttribute("class").contains("ui-autocomplete-input")) {
			Logger.debug("Input type: simple autocomplete selector with suggestions linked to some actions");
			return AutocompleteType.LINKED_SWITCHER;
		} else {
			throw new RuntimeException(
					"Unable to determine autocomplete input type");
		}

	}

	// Starts to type value and selects matching suggestion.
	// Throws an exception if matching suggestions are not found
	public void typeValue(Browser browser, String value) {
		typeValues(browser, Collections.singletonList(value));
	}

	// Types several comma-separated values using suggestions.
	public void typeValues(Browser browser, List<String> values) {
		Logger.operation("Type value '" + values + "' to Autocomplete Input '"
				+ getIdentifyingText() + "'");
		AutocompleteType type = getInputType();

		switch (type) {
		case OPTIONS_SELECTOR:
			for (String value : values) {
				clickSuggestion(value);
			}
			break;
		case LINKED_SWITCHER:
			for (String value : values) {
				typeAndSelectLinkedValue(browser, value);
			}
			break;
		default:
			throw new RuntimeException("Not implemented for this input type.");
		}
	}

	// This works for autocomplete fields like on on Yield Analytics page and
	// doesn't work for list where
	// items are linked with some actions (for example, for publisher switcher
	// input)
	public List<String> getAutocompleteSuggestions(Browser browser) {
		Logger.debug("Fetching autocomplete suggestions for '"
				+ getIdentifyingText() + "' autocomplete input.");
		List<String> result = new ArrayList<String>();
		AutocompleteType type = getInputType();

		switch (type) {
		case OPTIONS_SELECTOR:
			for (WebElement suggestItem : browser.findElements(By
					.xpath(AUTOCOMPLETE_SUGGESTIONS_LOCATOR))) {
				result.add(suggestItem.getText());
			}
			break;
		case LINKED_SWITCHER:
			for (WebElement suggestItem : browser.findElements(By
					.xpath(SWITCHER_SUGGESTIONS_LOCATOR))) {
				result.add(suggestItem.getText());
			}
			break;
		default:
			throw new RuntimeException("Not implemented for this input type.");
		}

		return result;
	}

	private void clickSuggestion(String suggestionText)
			throws ItemNotFoundException {
		String textBefore = webElement.getText();
		for (int i = 0; i < suggestionText.length(); i++) {
			String nextCharacter = new Character(suggestionText.charAt(i))
					.toString();
			webElement.sendKeys(nextCharacter);
			String countryElementPath = "//li[strong[text()='"
					+ suggestionText.substring(0, i + 1) + "'] and text()='"
					+ suggestionText.substring(i + 1, suggestionText.length())
					+ "']";
			Logger.debug(countryElementPath);
			List<WebElement> suggestions = webElement.findElements(By
					.xpath(countryElementPath));
			if (!suggestions.isEmpty()) {
				System.out.println(suggestions.get(0).getTagName());
				suggestions.get(0).click();
				if (webElement.getText().equalsIgnoreCase(
						textBefore + ", " + suggestionText)) {
					throw new RuntimeException(
							"Exception occurred on attempt to add a new value");
				}
				return;
			}
		}
		throw new ItemNotFoundException("There is no country with name '"
				+ suggestionText + "'");
	}

	private void typeAndSelectLinkedValue(Browser browser, String value)
			throws ItemNotFoundException {
		Logger.debug("Trying to select linked suggestion for '" + value
				+ "' text");

		browser.pause(1500);
		webElement.clear();
		for (int i = 0; i < value.length(); i++) {
			String nextCharacter = new Character(value.charAt(i)).toString();
			sendKeys(nextCharacter);

			browser.fireJavaScriptEventForElement(JSActions.KEYDOWN, this);

			browser.pause(1000);
			List<WebElement> selectSuggestions = browser.findElements(By
					.partialLinkText(value));

			if (selectSuggestions.size() == 1) {
				selectSuggestions.get(0).click();
				return;
			}

			if (i == (value.length() - 1)) {
				if (selectSuggestions.size() == 0) {
					throw new ItemNotFoundException(
							"There were no suggestions after typing '" + value
									+ "' value");
				}
				if (selectSuggestions.size() > 1) {
					Logger.warn("There were more than 1 suggestions after typing '"
							+ value + "' value. First one will be selected.");
					selectSuggestions.get(0).click();
					return;
				}
			}
		}
	}

}
