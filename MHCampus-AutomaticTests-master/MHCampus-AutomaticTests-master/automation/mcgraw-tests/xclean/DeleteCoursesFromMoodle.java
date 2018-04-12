package com.mcgraw.test.automation.tests.xclean;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.MoodleApplication;

public class DeleteCoursesFromMoodle extends BaseTest {

	/* Follow for deleting users manually from Moodle LMS
		1. Enter into Moodle as administrator
		2. Click "Site administration"->"Users"->"Accounts"->"Bulk user actions"
		3. Mark users, that you want to delete
		4. Add to selections
		5. Choose "Delete"
		6. Click "Go"->"Yes"->"Continue"  */
	

	@Autowired
	private MoodleApplication moodleApplication;
	
	@Value("${moodle.admin.login}")
	public String moodleAdminLogin;
	
	@Value("${moodle.admin.password}")
	public String moodleAdminPassword;

	private int countBefore;
	private int countAfter;	
		
	@BeforeClass
	public void testSuiteSetup() throws Exception {

		moodleApplication.loginToMoodle(moodleAdminLogin, moodleAdminPassword);
		
		Logger.info("Entering in Site Administration"); 		
		Element siteAdministration = browser.waitForElement(By.xpath("//span[text()='Site administration']"));
		siteAdministration.click();
		Element courses = browser.waitForElement(By.xpath("//span[text()='Courses']"));
		courses.click();
		Element editCourses = browser.waitForElement(By.xpath("//a[text()='Add/edit courses']"));
		Logger.info("Clicking on edit courses"); 		
		editCourses.click();
		
		WebElement category, delete, deleteAll, submitButton, continueButton;
		String categoryName;
		Boolean flag = true;
		int k=0;
		
		while(true){
			Element tableOfCategories = browser.waitForElement(By.xpath("//section/div/table/tbody"));
			List<WebElement> categories = tableOfCategories.findElements(By.tagName("tr"));
			if(flag){
				countBefore = categories.size();
				flag = false;
			}
			if(k == categories.size())
				break;
			for(int i=k; i<categories.size(); i++){
				category = categories.get(i);
				categoryName = category.findElement(By.xpath("//tr["+(i+1)+"]/td[1]/a")).getText();				
				if((categoryName.startsWith("Category")) || (categoryName.startsWith("category"))){
					Logger.info("Trying to delete the category: "+categoryName); 
					delete = category.findElement(By.xpath("//tr["+(i+1)+"]/td[3]/a[2]"));
					delete.click();
					deleteAll = browser.waitForElement(By.xpath("//option[text()='Delete all - cannot be undone']"));
					deleteAll.click();
					submitButton = browser.waitForElement(By.id("id_submitbutton")); 
					submitButton.click();
					continueButton = browser.waitForElement(By.xpath("//form/div/input"));
					continueButton.click();
					Logger.info(categoryName + " was deleted successfully!");
					break;
				}
				else{
					k++;
				    Logger.info("Not suitable for deleting: " + categoryName+"  does not begin with the word 'category'");
				}
			}
		}
		
		Logger.info("There are no more categories to delete..."); 		
		Element tableOfCategories = browser.waitForElement(By.xpath("//section/div/table/tbody"));
		List<WebElement> categories = tableOfCategories.findElements(By.tagName("tr"));		
		countAfter = categories.size();
	}
	
	@Test(description = "Number of deleted categories")
	public void NumberOfDeletedCategories() throws InterruptedException {

		Logger.info("BEFORE deleting were: " + countBefore + "categories"); 
		Logger.info("AFTER deleting are: " + countAfter + "categories"); 
		Logger.info("In all were deleted: " + (countBefore - countAfter) + " categories"); 
		
	}

}
