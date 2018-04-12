package com.mcgraw.test.automation.tests.xclean;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
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
			Actions builder = new Actions(browser);
			Element courseMenu = browser.waitForElement(By.xpath(".//*[@id='courses_menu_item']/a"));
			builder.moveToElement(courseMenu);
			builder.build().perform();
			browser.pause(2000);
			builder.moveToElement(browser.waitForElement(By.xpath("//span[contains(text(),'McGraw-Hill2')]")));
			builder.click(browser.findElement(By.xpath("//span[contains(text(),'McGraw-Hill2')]"))).perform();
			
			try{
				Element courseName = browser.waitForElement(By.id("course_name"));
				courseName.sendKeys("CourseName");
				Element findCourse = browser.waitForElement(By.xpath("//*[@id='new_course']/div[2]/button"));
				findCourse.click();
				Element course = browser.waitForElement(By.xpath("//a[contains(text(),'CourseName')]"));
				course.click();
			}catch (Exception e) {
				Logger.info("There not more courses was to delete..."); 
				break;
			}
			
			Element setting = browser.waitForElement(By.xpath("//*[@id='section-tabs']//*/a[contains(text(),'Settings')]"));
			setting.click();
			Element delete = browser.waitForElement(By.xpath("//*[@id='right-side']/div/a[5]"));
			delete.click();
			Element deleteCourse = browser.waitForElement(By.xpath("//*[@id='content']//*/button[contains(text(),'Delete Course')]"));
			deleteCourse.click();
			counter++;
		}
		
		canvasApplication.loginToCanvas(canvasApplication.canvasAdminLogin, canvasApplication.canvasAdminPassword);
		while(true){
			Actions builder = new Actions(browser);
			Element courseMenu = browser.waitForElement(By.xpath(".//*[@id='courses_menu_item']/a"));
			builder.moveToElement(courseMenu);
			builder.build().perform();
			browser.pause(2000);
			builder.moveToElement(browser.waitForElement(By.xpath("//span[contains(text(),'McGraw-Hill2')]")));
			builder.click(browser.findElement(By.xpath("//span[contains(text(),'McGraw-Hill2')]"))).perform();
			
			try{
				Element courseName = browser.waitForElement(By.id("course_name"));
				courseName.sendKeys("CourseName");
				Element findCourse = browser.waitForElement(By.xpath("//*[@id='new_course']/div[2]/button"));
				findCourse.click();
				Element instructor = browser.waitForElement(By.xpath("//a[contains(text(),'InstructorName')]"));
				instructor.click();
			}catch (Exception e) {
				Logger.info("The course was not found..."); 
				break;
			}
			
			Element setting = browser.waitForElement(By.xpath("//*[@id='section-tabs']//*/a[contains(text(),'Settings')]"));
			setting.click();
			Element delete = browser.waitForElement(By.xpath("//*[@id='right-side']/div/a[5]"));
			delete.click();
			Element deleteCourse = browser.waitForElement(By.xpath("//*[@id='content']//*/button[contains(text(),'Delete Course')]"));
			deleteCourse.click();
			counter++;
		}
		
	}
	
	@Test(description = "Number of deleted categories")
	public void nowCoursesWereDeleted() throws InterruptedException {

		Logger.info("Were deleted: " + counter + " courses"); 
		
	}

}
