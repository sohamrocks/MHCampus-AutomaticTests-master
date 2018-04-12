package com.mcgraw.test.automation.tests.selfreg;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.applications.TegrityInstanceApplicationNoLocalConnector;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceConnectorsScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceDashboardForHelpDeskAdminScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceDashboardScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityInstanceLoginScreen;
import com.mcgraw.test.automation.ui.tegrity.TegrityIntroductionScreen;

public class TegritySelfRegForAdmin extends BaseTest {
	private int rerunCount = 3;

	@Autowired
	protected TegrityInstanceApplicationNoLocalConnector tegrityInstanceApplicationNoLocalConnector;

	private String fullAdmin;
	private String helpDeskAdmin;
    private String executiveAdmin;
    
    private int flag = 0;
	
	private TegrityInstanceConnectorsScreen tegrityInstanceConnectorsScreen;
	private TegrityIntroductionScreen tegrityIntroductionScreen;
	private TegrityInstanceDashboardScreen tegrityInstanceDashboardScreen;
	private TegrityInstanceDashboardForHelpDeskAdminScreen tegrityInstanceDashboardForHelpDeskAdminScreen;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
	
		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();			
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		
		tegrityInstanceConnectorsScreen.configureBuilderAuthorizationConnector();
		tegrityInstanceConnectorsScreen.configureBuilderAuthenticationConnector();
		
		tegrityInstanceDashboardScreen = tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
		browser.pause(tegrityInstanceApplicationNoLocalConnector.DIRECT_LOGIN_TIMEOUT);	
		
		// prepare data for test
		deleteAdminUsers();
		prepareTestData();
		createAdminUsers();
		
		tegrityInstanceDashboardScreen.logoutFromTegrity();
	}
	
	@AfterMethod  
	public void logoutFromTegrity() throws Exception {
		if(flag == 1)
			tegrityIntroductionScreen.logOut();
		if(flag == 2)
			tegrityInstanceDashboardScreen.logoutFromTegrity();
		if(flag == 3)
			tegrityInstanceDashboardForHelpDeskAdminScreen.logoutFromTegrity();
	}
	
	@Test(description = "Check 'Executive' Admin can enter into Tegrity")
	public void checkExecutiveAdmin() throws Exception {
		
		// use TegrityIntroductionScreen for logout
		flag = 1;
		
		tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(executiveAdmin, executiveAdmin);
		Assert.assertNotNull(tegrityInstanceDashboardScreen, "Admin user " + executiveAdmin + " can not login");
	}
	
	@Test(description = "Check 'Full Desk' Admin can enter into Tegrity")
	public void checkFullAdmin() throws Exception {
		
		// use TegrityInstanceDashboardScreen for logout
		flag = 2;
		
		tegrityInstanceDashboardScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdmin(fullAdmin, fullAdmin);
		Assert.assertNotNull(tegrityInstanceDashboardScreen, "Admin user " + fullAdmin + " can not login");
	}
	
	@Test(description = "Check 'Help Desk' Admin can enter into Tegrity")
	public void checkHelpDeskAdmin() throws Exception {
		
		// use TegrityInstanceDashboardScreen for logout
		flag = 3;
		
		try{    //AlexandrY added to fin instability on server
			tegrityInstanceDashboardForHelpDeskAdminScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsHelpDeskAdmin(helpDeskAdmin, helpDeskAdmin);
		}catch(Exception e){
			Logger.info("Failed to login to TegrityInstance as HelpDeskAdmin, trying aggain...");
			tegrityInstanceDashboardForHelpDeskAdminScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsHelpDeskAdmin(helpDeskAdmin, helpDeskAdmin);			
		}
		Assert.assertNotNull(tegrityInstanceDashboardForHelpDeskAdminScreen, "Admin user " + helpDeskAdmin + " can not login");
	}
	
	@Test(description = "Check admin users are not present, after deleting", dependsOnMethods = { "checkExecutiveAdmin", "checkFullAdmin", "checkHelpDeskAdmin"})
	public void checkAdminUsersNotPresentAfterDeleting() throws Exception {
		
		// use TegrityInstanceDashboardScreen for logout
		flag = 2;
		
		// delete admin users from Tegrity   
		tegrityInstanceDashboardScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdmin();
		deleteAdminUsers();
		
		// check admin users don't present after deleting
		Assert.verifyFalse(tegrityInstanceDashboardScreen.isAdminUserPresentUsingFilter(helpDeskAdmin), "The user " + helpDeskAdmin + " is present");
		Assert.verifyFalse(tegrityInstanceDashboardScreen.isAdminUserPresentUsingFilter(fullAdmin), "The user " + fullAdmin + " is present");
		Assert.verifyFalse(tegrityInstanceDashboardScreen.isAdminUserPresentUsingFilter(executiveAdmin), "The user " + executiveAdmin + " is present");
	}

	@Test(description = "Check admin users are present, after deleting and adding again", dependsOnMethods = "checkAdminUsersNotPresentAfterDeleting")
	public void checkAdminUsersPresentAfterDeletingAndAddingAgain() throws Exception {
		
		// use TegrityInstanceDashboardScreen for logout
		flag = 2;
		
		// create the same users, courses and membership again  
		tegrityInstanceDashboardScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdmin();
		createAdminUsers();
		
		// check admin users don't present after deleting
		Assert.verifyTrue(tegrityInstanceDashboardScreen.isAdminUserPresentUsingFilter(helpDeskAdmin), "The user " + helpDeskAdmin + " don't present");
		Assert.verifyTrue(tegrityInstanceDashboardScreen.isAdminUserPresentUsingFilter(fullAdmin), "The user " + fullAdmin + " don't present");
		Assert.verifyTrue(tegrityInstanceDashboardScreen.isAdminUserPresentUsingFilter(executiveAdmin), "The user " + executiveAdmin + " don't present");
		
		tegrityInstanceDashboardScreen.logoutFromTegrity();
		deleteAllConnectors();
	}
	
	@Test(description = "Check 'Full' Admin can enter into Tegrity after deleting connectors", dependsOnMethods = "checkAdminUsersPresentAfterDeletingAndAddingAgain")
	public void checkFullAdminAfterDeletingConnectors() throws Exception {
		
		// use TegrityInstanceDashboardScreen for logout
		flag = 2;
		
		try{    //AlexandrY added to fin instability on server
			tegrityInstanceDashboardScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdmin(fullAdmin, fullAdmin);
		}catch(Exception e) {
			Logger.info("Failed to login to TegrityInstance as Admin, trying aggain...");
			browser.waitForElement(By.xpath(".//*/a[contains(text(),'sign out')]"), 30).click();
			browser.waitForPage(TegrityInstanceLoginScreen.class, 10);
			tegrityInstanceDashboardScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdmin(fullAdmin, fullAdmin);
		}
		Assert.assertNotNull(tegrityInstanceDashboardScreen, "Admin user " + fullAdmin + " can not login");
	}
	
	// Must work, when will be solved the problem with deleting and adding again 2 types of admin users: Executive and Help Desk
	/*@Test(description = "Check 'Executive' Admin can enter into Tegrity after deleting connectors", dependsOnMethods = "checkAdminUsersPresentAfterDeletingAndAddingAgain")
	public void checkExecutiveAdminAfterDeletingConnectors() throws Exception {
		
		// use TegrityIntroductionScreen for logout
		flag = 1;
		
		tegrityIntroductionScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityAsUser(executiveAdmin, executiveAdmin);
		Assert.assertNotNull(tegrityInstanceDashboardScreen, "Admin user " + executiveAdmin + " can not login");
	}
	
	@Test(description = "Check 'Help Desk' Admin can enter into Tegrity after deleting connectors", dependsOnMethods = "checkAdminUsersPresentAfterDeletingAndAddingAgain")
	public void checkHelpDeskAdminAfterDeletingConnectors() throws Exception {
		
		// use TegrityInstanceDashboardScreen for logout
		flag = 3;
		
		tegrityInstanceDashboardForHelpDeskAdminScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsHelpDeskAdmin(helpDeskAdmin, helpDeskAdmin);
		Assert.assertNotNull(tegrityInstanceDashboardForHelpDeskAdminScreen, "Admin user " + helpDeskAdmin + " can not login");
	}*/
	
	private void deleteAllConnectors(){
		tegrityInstanceConnectorsScreen = tegrityInstanceApplicationNoLocalConnector.loginToTegrityInstanceAsAdminAndClickManageAairsLink();			
		tegrityInstanceConnectorsScreen.deleteAllConnectors();
		tegrityInstanceDashboardScreen = tegrityInstanceConnectorsScreen.clickSaveAndContinueButton();
	}
	
	private void createAdminUsers() {
		tegrityInstanceDashboardScreen.createAdminUserBuilder(helpDeskAdmin, "Help Desk");
		tegrityInstanceDashboardScreen.createAdminUserBuilder(fullAdmin, "Full");
		tegrityInstanceDashboardScreen.createAdminUserBuilder(executiveAdmin, "Executive");
	}
	
	private void deleteAdminUsers() {
		tegrityInstanceDashboardScreen.deleteAdminUsers();
	}

	private void prepareTestData() {
		fullAdmin = "fullAdmin" + getRandomString();
		helpDeskAdmin = "helpDeskAdmin" + getRandomString();
		executiveAdmin = "executiveAdmin" + getRandomString();
	}

	private String getRandomString() {
		return RandomStringUtils.randomAlphanumeric(5);
	}
}