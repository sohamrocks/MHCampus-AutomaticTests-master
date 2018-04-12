package com.mcgraw.test.automation.ui.mhcampus;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;


@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id='ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_BreadCrumb1_LabelCurrent'][contains(text(),'MH Campus Accounts')]")))
public class TegrityAccountsScreen extends
		TegrityCreateMhCampusCustomerScreenBase {
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_QuickLinks1_LinkButtonCreateMHCampusCustomer"))
	Element NewAccountLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//table[contains(@id,'MhCampusCustomers1')]"))
	Element CustomerTable;

	public TegrityAccountsScreen(Browser browser) {
		super(browser);
	}

	public TegrityCreateMhCampusCustomerScreen goToAccountCreatingPage() {
		NewAccountLink.click();
		return browser.waitForPage(TegrityCreateMhCampusCustomerScreen.class);
	}

	public enum CustomerTableFilteringHeader {
		DOMAIN("Domain"), INSTITUTION("Institution"), CUSTOMER_NUMBER(
				"Customer"), INSTALLATION_ID("Installation");

		private String valueForXpath;

		private CustomerTableFilteringHeader(String valueForXpath) {
			this.valueForXpath = valueForXpath;
		}

		public String getvalueForXpath() {
			return valueForXpath;
		}

		public static CustomerTableFilteringHeader getByValue(
				String valueForXpath) {
			for (CustomerTableFilteringHeader customerTableFilteringHeader : values()) {
				if (customerTableFilteringHeader.getvalueForXpath().equals(
						valueForXpath)) {
					return customerTableFilteringHeader;
				}
			}
			throw new IllegalArgumentException("No matching constant for ["
					+ valueForXpath + "]");
		}

	}

	public TegrityCreateMhCampusCustomerScreen enterEditInstance(
			String customerNumber) {
		return enterEditInstance(CustomerTableFilteringHeader.CUSTOMER_NUMBER,
				customerNumber);
	}

	public TegrityCreateMhCampusCustomerScreen enterEditInstance(
			CustomerTableFilteringHeader column, String valueToSearch) {
		browser.makeScreenshot();
		searchDataByColumn(column, valueToSearch);
		browser.makeScreenshot();
		Logger.info("Clicking 'Edit' button...");
		Element editButton = browser.waitForElement(By
				.xpath(".//a[contains(text(),'Edit')]"));
		editButton.click();
		return browser.waitForPage(TegrityCreateMhCampusCustomerScreen.class);
	}

	public Element searchDataByColumn(CustomerTableFilteringHeader column,
			String valueToSearch) {
		Logger.info("Searching Data by column...");
		CustomerTable.waitForPresence();
		Element filteringInput = CustomerTable.findElement(By
				.xpath("//tr[@class='rgFilterRow']/td/input[contains(@name,'"
						+ column.getvalueForXpath() + "Column')]"));
		filteringInput.sendKeys(valueToSearch);
		filteringInput.sendKeys(Keys.ENTER);
		Logger.info("Wait for first row...");
		browser.pause(9000);
		Element firstRow = new Element(getCustomerTableRows().get(0));
		return firstRow;
	}

	public List<WebElement> getCustomerTableRows() {
		if (!CustomerTable.waitForPresence(30)) {
			throw new CommonTestRuntimeException(
					"The Customers' table wasn't rendered");
		}

		return CustomerTable.findElements(By.xpath("./tbody/tr"));
	}

}
