package com.mcgraw.test.automation.tests.xclean;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;

public class DeleteUsersFromCanvas extends BaseTest {

	@Autowired
	private CanvasApplication canvasApplication;

	private int counter=0;
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin, canvasApplication.canvasAdminPassword);
		Element admin = browser.waitForElement(By.id("global_nav_accounts_link"));
		admin.click();
		Element mcGrawHill = browser.waitForElement(By.xpath("//a[contains(text(),'McGraw-Hill2')]"));
		mcGrawHill.click();
		Element users = browser.waitForElement(By.xpath("//a[contains(text(),'Users')]"));
		users.click();
		browser.pause(6000);
		
		while(true){
			
		   try{
				Element userName = browser.waitForElement(By.id("user_name"));
				userName.sendKeys("StudentName");
				Element findUser = browser.waitForElement(By.xpath("//*[@id='new_user']/div/button"));
				findUser.click();
				Element user = browser.waitForElement(By.xpath("//a[contains(text(),'StudentName')]"), 20);
				user.click();
				Element delete = browser.waitForElement(By.xpath("//a[contains(text(),'Delete from McGraw-Hill2')]"));
				delete.click();
				Element deleteUser = browser.waitForElement(By.xpath("//*[@id='content']//*/button[contains(text(),'Delete StudentName')]"));
				deleteUser.click();
				counter++;
				Logger.info("Student was deleted  successfully, counter is: " + counter); 
			}catch (Exception e) {
				Logger.info("There not more students to delete..."); 
				break;
			}			
		}
		
	   while(true){
			
		   try{
			   Element userName = browser.waitForElement(By.id("user_name"));
				userName.sendKeys("InstructorName");
				Element findUser = browser.waitForElement(By.xpath("//*[@id='new_user']/div/button"));
				findUser.click();
				Element user = browser.waitForElement(By.xpath("//a[contains(text(),'InstructorName')]"), 20);
				user.click();
				Element delete = browser.waitForElement(By.xpath("//a[contains(text(),'Delete from McGraw-Hill2')]"));
				delete.click();
				Element deleteUser = browser.waitForElement(By.xpath("//*[@id='content']//*/button[contains(text(),'Delete InstructorName')]"));
				deleteUser.click();
				counter++;
				Logger.info("Instructor was deleted  successfully, counter is: " + counter); 
			}catch (Exception e) {
				Logger.info("There not more instructors to delete..."); 
				break;
			}		
		}
	}
	
	@Test(description = "Number of deleted categories")
	public void nowUserssWereDeleted() throws InterruptedException {

		Logger.info("Were deleted: " + counter + " users"); 
		
	}

}