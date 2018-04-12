package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.core.exception.ItemNotFoundException;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;


public class CalendarDropDownMenu extends Element {

	public final static String FIRST_LOCATOR = "//div[contains(@class,'ranges ui-widget-header')]/div//div[contains(@class,'ui-datepicker-header')]";

	public final static String LAST_LOCATOR = "//div[contains(@class,'ranges ui-widget-header')]/div[2]//div[contains(@class,'ui-datepicker-header')]";
	
	public final static String DROPDOWNMENU_LOCATOR ="//div[contains(@class,'ui-daterangepicker') or contains(@class,'ui-multiselect')]/ul/li//*[translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='%s']"; 

	private Element nextClicker;

	private Element prevClicker;

	public CalendarDropDownMenu(WebElement webElement) {
		super(webElement);
	}

	public void selectDateRange(CalendarRange range) {
		selectMenuItem(CalendarMenuItemName.DATARANGE);
		Browser.getBrowser().pause(1500);
		selectFirstDate(range.getStartYear(), range.getStartMonth(),
				range.getStartDay());
		selectLastDate(range.getEndYear(), range.getEndMonth(), range.getEndDay());

	}
	
	public void selectDate(CalendarMenuItemName itemName,int year, String month, String day ){
		selectMenuItem(itemName);
		selectFirstDate( year,month,day);
	}
	
	public void selectMenuItem(CalendarMenuItemName itemName){
		this.click();
		String xpath=String.format(DROPDOWNMENU_LOCATOR, itemName.getValue());
		List<WebElement> foundMenus=this.findElements(By.xpath(xpath));
		int countOfMenus=foundMenus.size();
		if (countOfMenus==0){
			throw new ItemNotFoundException("Menu item '"+itemName.name()+"' didn't appear!");
		}
		WebElement item=foundMenus.get(countOfMenus-1);
		item.click();
	}
	

	private void selectFirstDate(int year, String month, String day) {
		selectYear(year, FIRST_LOCATOR);
		selectMonth(month, FIRST_LOCATOR);
		selectDay(day, FIRST_LOCATOR);
	}

	private void selectLastDate(int year, String month, String day) {
		selectYear(year, LAST_LOCATOR);
		selectMonth(month, LAST_LOCATOR);		
		selectDay(day, LAST_LOCATOR);
	}

	private void selectDay(String day, String order) {
		String xpath = String
				.format(order
						+ "/../table[@class='ui-datepicker-calendar']/tbody/tr/td/a[contains(text(),'%s')]",
						day);		
		Element dayElement=Browser.getBrowser().findElement(By.xpath(xpath));
		dayElement.click();
	}

	private void selectYear(int yearToSelect, String order) {
//		nextClicker = Browser.getBrowser().findElement(
//				By.xpath(order + "/a[2]"));

//		prevClicker = Browser.getBrowser().findElement(By.xpath(order + "/a"));
		int presentYear = Integer.parseInt(Browser.getBrowser()
				.findElement(By.xpath(order + "/div/span[2]")).getText());

		if (yearToSelect > presentYear) {
			int gap = yearToSelect - presentYear;
			for (int i = 0; i < gap * 12; i++) {
				nextClicker = Browser.getBrowser().findElement(
						By.xpath(order + "/a[2]"));
				nextClicker.click();
			}
		}
		if (yearToSelect < presentYear) {
			int gap = presentYear - yearToSelect;
			for (int i = 0; i < gap * 12; i++) {
				prevClicker = Browser.getBrowser().findElement(By.xpath(order + "/a"));
				prevClicker.click();
			}
		}
	}

	private void selectMonth(String month, String order) {
		
		while (!Browser.getBrowser().findElement(By.xpath(order + "/div/span"))
				.getText().equals("January")) {
			prevClicker = Browser.getBrowser().findElement(By.xpath(order + "/a"));
			prevClicker.click();
		}

		while (!Browser.getBrowser().findElement(By.xpath(order + "/div/span"))
				.getText().equals(month)) {
			nextClicker = Browser.getBrowser().findElement(
					By.xpath(order + "/a[2]"));
			nextClicker.click();
		}
	}

}
