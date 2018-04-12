package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mcgraw.test.automation.framework.core.timing.SmartWait;
import com.mcgraw.test.automation.framework.selenium2.ui.exception.SegmentRuleBulderException;

public class SegmentRuleBuilder extends Element {

	private static final String AVAILIBLE_RULES_PATH = ".//ul[@class='list available']/li[starts-with(@class,'ui-state-default ui-element')]";

	private static final String SELECTED_RULES_PATH = ".//ul[@class='list selected']/li[starts-with(@class,'ui-state-default ui-element')]";

	private static final By SELECTED_ITEMS_PATH = By
			.xpath(".//div[@class='ui-widget-content list-container selected']//span[@class='count']");

	public SegmentRuleBuilder(WebElement webElement) {
		super(webElement);
	}

	public List<String> getAvailibleRules() {
		List<WebElement> availibleRules = getAvailibleRulesElements();
		List<String> availibleRuleNames = new ArrayList<String>(
				availibleRules.size());
		for (WebElement rule : availibleRules) {
			availibleRuleNames.add(rule.getText());
		}
		return availibleRuleNames;
	}

	public List<String> getSelectedRules() {
		List<WebElement> selectedRules = getSelectedRulesElements();
		List<String> selectedRuleNames = new ArrayList<String>(
				selectedRules.size());
		for (WebElement rule : selectedRules) {
			selectedRuleNames.add(rule.getText());
		}
		return selectedRuleNames;
	}

	private List<WebElement> getAvailibleRulesElements() {
		return filterVisible(this.findElements(By.xpath(AVAILIBLE_RULES_PATH)));
	}

	private List<WebElement> getSelectedRulesElements() {
		return filterVisible(this.findElements(By.xpath(SELECTED_RULES_PATH)));
	}

	public void addRule(String rule) {
		clickAtRule(AVAILIBLE_RULES_PATH, rule);
		new SmartWait<String>(new String(rule))
				.withTimeout(5, TimeUnit.SECONDS)
				.describe("Waiting for adding rule '" + rule + "'")
				.pollingEvery(1, TimeUnit.SECONDS)
				.until(new Predicate<String>() {
					@Override
					public boolean apply(@Nullable String ruleToBeClicked) {
						return getCurrentPredicate().contains(ruleToBeClicked);
					}
				});
	}

	public void removeRule(String rule) {
		final int selectedCount = getSelectedItemsCount();
		clickAtRule(SELECTED_RULES_PATH, rule);
		new SmartWait<String>(new String(rule))
				.withTimeout(5, TimeUnit.SECONDS)
				.describe("Waiting for removing rule '" + rule + "'")
				.pollingEvery(1, TimeUnit.SECONDS)
				.until(new Predicate<String>() {
					@Override
					public boolean apply(@Nullable String ruleToBeClicked) {
						return !getCurrentPredicate().contains(ruleToBeClicked)
								&& getSelectedItemsCount() < selectedCount;
					}
				});
	}

	public TreeView getTreeView() {
		return new TreeView(this);
	}

	private void clickAtRule(String path, String rule) {
		List<WebElement> foundRules = this.findElements(By.xpath(path
				+ "[normalize-space(text())='" + rule + "']"));
		if (foundRules.isEmpty()) {
			throw new SegmentRuleBulderException(
					"Unable to find rule with name '" + rule + "'");
		}

		foundRules.get(0).findElement(By.xpath("./a")).click();
	}

	public String getCurrentPredicate() {
		List<WebElement> predicate = this.findElements(By
				.xpath(".//div[@id='current_predicate']/span"));
		return predicate.isEmpty() ? "" : predicate.get(0).getText();
	}

	private List<WebElement> filterVisible(List<WebElement> elements) {
		return Lists.newArrayList(Iterables.filter(elements,
				new Predicate<WebElement>() {
					@Override
					public boolean apply(@Nullable WebElement element) {
						return element.isDisplayed();
					}
				}));
	}

	public int getSelectedItemsCount() {
		return Integer.parseInt(StringUtils.substringBefore(
				this.findElement(SELECTED_ITEMS_PATH).getText(), " items"));
	}

}
