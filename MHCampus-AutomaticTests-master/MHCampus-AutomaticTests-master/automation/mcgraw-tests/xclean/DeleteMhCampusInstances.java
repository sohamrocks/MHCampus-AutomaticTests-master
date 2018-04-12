package com.mcgraw.test.automation.tests.xclean;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.mhcampus.TegrityAccountsScreen;

public class DeleteMhCampusInstances extends BaseTest {

	private TegrityAccountsScreen tegrityAccountsScreen;
	private int deletedInstances = 0;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		tegrityAccountsScreen = tegrityAdministrationApplication.loginToTegrity();

		WebElement instance;
		String domain, customerNumber;	
		List<String> customerNumbers = new ArrayList<String>();
		
		Element pageSize = browser.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_MhCampusCustomers1_RadGridCustomers_ctl00_ctl03_ctl01_PageSizeComboBox_Arrow"));
		pageSize.click();
		Element size50 = pageSize.findElement(By.xpath("//div/ul/li[3]"));
		size50.click();
		Element mainTable = browser.findElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_MhCampusCustomers1_RadGridCustomers_ctl00"));
		int numPages = Integer.parseInt(mainTable.findElement(By.xpath("//tfoot/tr/td/table/tbody/tr/td/div[5]/strong[2]")).getText());
		
		
		for(int j=1; j<numPages; j++){  
			
			Element nextPage =  browser.waitForElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_MhCampusCustomers1_RadGridCustomers_ctl00']" +
					                                          "/tfoot/tr/td/table/tbody/tr/td/div[3]/input[1]"));
			nextPage.click();
			
			List<WebElement> instances = tegrityAccountsScreen.getCustomerTableRows();
			for(int i=0; i<instances.size(); i++){
				instance = instances.get(i);
				domain = instance.findElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_MhCampusCustomers1_RadGridCustomers_ctl00__"+
				                                                i+"']/td[2]/a")).getText();	
				if(domain.startsWith("epamInstance")){
					customerNumber = instance.findElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_ClientServicesMain1_ClientServicesDashboard1_MhCampusCustomers1_RadGridCustomers_ctl00__"+
	                                                                i+"']/td[6]")).getText();
					Logger.info("Instance: " + domain + " with customer number: " + customerNumber + " added to list for deleting");
					customerNumbers.add(customerNumber);
				}
			}
			Logger.info("--------------------------- On page: " + j + " number of instances to delete: " + customerNumbers.size() + "  ---------------------------"); 
		}
		
		deletedInstances = customerNumbers.size();
		for(int i=0; i<customerNumbers.size(); i++)			
			tegrityAdministrationApplication.deleteMhCampusInstance(customerNumbers.get(i));
			
		Logger.info("There are no more instances to delete..."); 	
	}

	@Test(description = "Number of deleted instances")
	public void NumberOfDeletedInstances() throws InterruptedException {

		Logger.info("In all were deleted: " + deletedInstances + " instances"); 
		
	}
}
