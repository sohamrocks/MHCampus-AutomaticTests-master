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
import com.mcgraw.test.automation.ui.applications.SakaiApplication;

public class DeleteUsersFromSakai extends BaseTest {

	@Autowired
	private SakaiApplication sakaiApplication;
	
	@Value("${sakai.adminlogin}")
	public String sakaiAdminLogin;
	
	@Value("${sakai.adminpassword}")
	public String sakaiAdminPassword;

	private int deletedUsers = 0;

	@BeforeClass
	public void testSuiteSetup() throws Exception {

		sakaiApplication.loginToSakaiAsAdmin(sakaiAdminLogin, sakaiAdminPassword);
		
		Logger.info("Clicking on Administration Workspace"); 		
		Element administrationWorkspace = browser.waitForElement(By.xpath("//*[@id='topnav']/li[2]/a/span"));
		administrationWorkspace.click();
		Element usersButton = browser.waitForElement(By.xpath("//*[@id='toolMenu']/ul/li[2]/a/span"));
		usersButton.click();
		
		WebElement user, userLink, removeUser, removeButton;
		String userName;
		int k=1;
		
		browser.switchTo().frame(browser.waitForElement(By.id("Mainxadminx210")));
		while(true){
			Element tableOfUsers = browser.waitForElement(By.xpath("//table/tbody"));
			List<WebElement> users = tableOfUsers.findElements(By.tagName("tr"));
			if(k == users.size())
				break;		
			for(int i=k; i<users.size(); i++){
				user = users.get(i);
				userName = user.findElement(By.xpath("//tr["+(i+1)+"]/td[1]/a")).getText();				
				if((userName.startsWith("instructor")) || (userName.startsWith("student"))){
					Logger.info("Trying to delete the user: "+userName);
					userLink = user.findElement(By.xpath("//tr["+(i+1)+"]/td[1]/a"));
					userLink.click();
					removeUser = browser.waitForElement(By.linkText("Remove User"));
					removeUser.click();
					removeButton = browser.waitForElement(By.xpath("//form/div/input[1]"));
					removeButton.click();
					Logger.info(userName + " was deleted successfully!");
					deletedUsers++;
					break;
				}
				else{
					k++;
					Logger.info("Not suitable for deleting: " + userName+ "  does not begin with the word 'instructor' or 'student'");
				}
			}
		}
		
		Logger.info("There are no more users to delete..."); 	
	}

	@Test(description = "Number of deleted users")
	public void NumberOfDeletedUsers() throws InterruptedException {

		Logger.info("In all were deleted: " + deletedUsers + " users"); 
		
	}
}
