package com.mcgraw.test.automation.tests.xclean;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.CanvasApplication;

public class DeleteCoursesFromCanvas extends BaseTest {

	@Autowired
	private CanvasApplication canvasApplication;

	private int counter=0;
	
	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin, canvasApplication.canvasAdminPassword);
		while(true){
			Element admin = browser.waitForElement(By.id("global_nav_accounts_link"));
			admin.click();
			Element mcGrawHill = browser.waitForElement(By.xpath("//a[contains(text(),'McGraw-Hill2')]"));
			mcGrawHill.click();
			
			try{
				Element courseName = browser.waitForElement(By.id("course_name"));
				courseName.sendKeys("CourseName");
				Element findCourse = browser.waitForElement(By.xpath("//*[@id='new_course']/div/button"));
				findCourse.click();
			}catch (Exception e) {
				Logger.info("There not more courses was to delete..."); 
				break;
			}
			
			Element setting = browser.waitForElement(By.xpath("//*[@id='content']//*/a[contains(text(),'Settings')]"));
			setting.click();
			Element delete = browser.waitForElement(By.xpath("//*[@class='btn button-sidebar-wide delete_course_link']"));
			delete.click();
			Element deleteCourse = browser.waitForElement(By.xpath("//*[@id='content']//*/button[contains(text(),'Delete Course')]"));
			deleteCourse.click();
			counter++;
			Logger.info("Course was deleted  successfully, counter is: " + counter); 
		}	
	}
	
	@Test(description = "Number of deleted categories")
	public void nowCoursesWereDeleted() throws InterruptedException {

		Logger.info("Were deleted: " + counter + " courses"); 
		
	}

}