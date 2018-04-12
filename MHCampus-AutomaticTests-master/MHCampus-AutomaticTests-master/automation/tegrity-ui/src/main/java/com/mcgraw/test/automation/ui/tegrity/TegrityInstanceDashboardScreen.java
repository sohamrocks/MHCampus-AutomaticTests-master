package com.mcgraw.test.automation.ui.tegrity;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.LINK_TEXT, using = "Manage AAIRS"))) 
public class TegrityInstanceDashboardScreen extends Screen {

	@DefinedLocators(@DefinedLocator(how = How.LINK_TEXT, using = "Manage AAIRS")) 
	Element manageAAIRSlink;
	
	@DefinedLocators(@DefinedLocator(how = How.LINK_TEXT, using = "IMS Import")) 
	Element imsImportLink;  
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "aairsManagementFrame"))
	Element connectorsFrame;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='main']/div/iframe"))
	Element imsImportFrame;
	
	@DefinedLocators(@DefinedLocator(how = How.LINK_TEXT, using = "Manage Ad-hoc Users (User Builder)")) 
	Element userBuilderLink;
	
	@DefinedLocators(@DefinedLocator(how = How.LINK_TEXT, using = "Manage Admin Users")) 
	Element manageAdminUsersLink;
	
	@DefinedLocators(@DefinedLocator(how = How.LINK_TEXT, using = "Manage Ad-hoc Courses / Enrollments (Course Builder)")) 
	Element courseBuilderLink;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_lbNewUser")) 
	Element newUserLink;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_NewCourseButton")) 
	Element newCourseLink;
	
	@DefinedLocators(@DefinedLocator(how = How.LINK_TEXT, using = "Membership")) 
	Element membership;
	
	@DefinedLocators(@DefinedLocator(how = How.LINK_TEXT, using = "Admin Dashboard")) 
	Element adminDashboardLink;

    @DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_btnOK")) 
	Element okButtonNewUser;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ucNewCourseControl_ucDialog_btnOK")) 
	Element okButtonNewCourse;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_btnOK")) 
	Element okButtonMemberships;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*/a[contains(text(),'sign out')]"))
	Element logoutLink;

	public TegrityInstanceDashboardScreen(Browser browser) {
		super(browser);
	}
	
	public TegrityInstanceLoginScreen logoutFromTegrity() {
		Logger.info("Logging out...");
		logoutLink.click();
		return browser.waitForPage(TegrityInstanceLoginScreen.class);
	}

	public TegrityInstanceConnectorsScreen clickManageAairs() {
		Logger.info("Clicking manage aairs link...");		
		try{
			manageAAIRSlink.click();
			connectorsFrame.waitForPresence(40);
			browser.switchTo().frame(connectorsFrame);	
		}catch(Exception e){
			Logger.info("Trying click manage aairs link again...");
			manageAAIRSlink.click();
			connectorsFrame.waitForPresence(40);
			browser.switchTo().frame(connectorsFrame);	
		}
		browser.waitForElement(By.xpath(".//div[@id='GRADES_MAIN']/a[@class='Section_Link editLink']"), 30);
		browser.makeScreenshot();
		return browser.waitForPage(TegrityInstanceConnectorsScreen.class, 20);
	}
		
	public TegrityImsImportScreen clickImsImportLink() {
		Logger.info("Clicking Ims Import link...");				
		imsImportLink.click();
		browser.pause(2000);
		browser.makeScreenshot();
		imsImportFrame.waitForPresence(40);
		browser.switchTo().frame(imsImportFrame);
		browser.makeScreenshot();
		
		return browser.waitForPage(TegrityImsImportScreen.class, 20);
	}
	//Edit by lior
	public boolean isImsImportLinkPresent() {
		Logger.info("Check if 'Ims Import' link is present");
		boolean flag = imsImportLink.waitForPresence(10);

		if (flag) {
			return flag;
		} else {
			Logger.info("Try again...Check if 'Ims Import' link is present");
			flag = imsImportLink.waitForPresence(10);
			return flag;
		}
	}
	
	public boolean isUserBuilderLinkPresent() {
		Logger.info("Check if 'User Builder' link is present");
		return userBuilderLink.waitForPresence(10);
	}
	
	public boolean isCourseBuilderLinkPresent() {
		Logger.info("Check if 'Course Builder' link is present");
		return courseBuilderLink.waitForPresence(10);
	}
	
	public void createUserBuilder(String user) {
		Logger.info("Creating User Builder " + user);
		clickUserBuilderLink();
		browser.pause(1000);
		clickNewUserLink();
		browser.pause(1000);
		fillFormForUser(user);
		clickOkButton(okButtonNewUser);
		clickAdminDashboardLink();	
	}
	
	public void createAdminUserBuilder(String user, String role) {
		Logger.info("Creating Admin User Builder " + user);
		clickManageAdminUsersLink();
		clickNewUserLink();
		fillFormForUser(user);
		chooseRoleForAdmin(role);
		clickOkButton(okButtonNewUser);
		clickAdminDashboardLink();	
	}
	
	public void createCourseBuilder(String course) {
		Logger.info("Creating Course Builder " + course);
		clickCourseBuilderLink();
		clickNewCourseLink();
		fillFormForCourse(course);
		clickOkButton(okButtonNewCourse);
		clickAdminDashboardLink();
	}
	
	public void addMembership(String course, String user) {
		validateCorrectImput(user);
		Logger.info("Add Membership beetween " + course + " and " + user);
		clickCourseBuilderLink();
		clickMembershipLink();
		addUserToCourse(user);		
		clickOkButton(okButtonMemberships);
		clickAdminDashboardLink();
	}
	
	public void removeMembership(String course, String user) {
		validateCorrectImput(user);
		Logger.info("Remove Membership beetween " + course + " and " + user);
		clickCourseBuilderLink();
		clickMembershipLink();
		removeUserFromCourse(user);
		clickOkButton(okButtonMemberships);
		clickAdminDashboardLink();
	}
	
	public void deleteUsers() {
		userBuilderLink.waitForPresence(50);
		userBuilderLink.click();
		deleteItems("users");
	}
	
	public void deleteAdminUsers() {
		manageAdminUsersLink.waitForPresence(50);
		manageAdminUsersLink.click();
		deleteItems("admin users");
	}
	
	public void deleteCourses() {
		courseBuilderLink.waitForPresence(50);
		courseBuilderLink.click();
		deleteItems("courses");
	}
	
	public boolean isUserPresentUsingFilter(String user) {
		Logger.info("Check user " + user + " present using filter");
		clickUserBuilderLink();
		boolean flag = isItemPresent(user);		
		clickAdminDashboardLink();	
		return flag;
	}
	
	public boolean isAdminUserPresentUsingFilter(String user) {
		Logger.info("Check admin user " + user + " present using filter");
		clickManageAdminUsersLink();
		boolean flag = isItemPresent(user);		
		clickAdminDashboardLink();	
		return flag;
	}
	public boolean isCoursePresentUsingFilter(String course) {
		Logger.info("Check course " + course + " present using filter");
		clickCourseBuilderLink();
		boolean flag = isItemPresent(course);		
		clickAdminDashboardLink();	
		return flag;
	}
	
	// ---------------------------------  PRIVATE METHODS -----------------------------
	
	private boolean isItemPresent(String name) { 
		Element search = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_txtSearch"));
		search.sendKeys(name);                        
		Element filterButton = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_btnSearch"));
		filterButton.click();
		browser.pause(6000);
		
		Element tableOfItems = browser.waitForElement(By.xpath("//*[@id='contentDIV']/table/tbody"));
		List<WebElement> items = tableOfItems.findElements(By.tagName("tr"));
		if(items.size()!= 3){
			Logger.info("There are present: " + (items.size()-2) + " items");
			return false;
		}
		WebElement item = items.get(1);
		WebElement itemName = item.findElement(By.xpath("td[1]"));
		String  text = itemName.getText();
    	if(text.startsWith(name))
    		return true;
    	
		return false;
	}
	
	private void clickUserBuilderLink() {
		userBuilderLink.waitForPresence(500);
		userBuilderLink.click();
		browser.makeScreenshot();
		browser.pause(200);    //AlexandrY added to fix local instability
		browser.switchTo().frame(0);
	}
	
	private void clickNewUserLink() {
		newUserLink.waitForPresence(300);
		newUserLink.click();
		browser.makeScreenshot();
	}
	
	private void clickManageAdminUsersLink() {
		manageAdminUsersLink.waitForPresence(300);
		manageAdminUsersLink.click();
		browser.makeScreenshot();
		browser.switchTo().frame(0);
	}
	
	private void clickCourseBuilderLink() {
		courseBuilderLink.waitForPresence(300);
		courseBuilderLink.click();
		browser.makeScreenshot();
		browser.pause(200);    //AlexandrY added to fix local instability
		browser.switchTo().frame(0);
	}
	
	private void clickNewCourseLink() {
		newCourseLink.waitForPresence(300);
		newCourseLink.click();
		browser.makeScreenshot();
	}
	
	private void clickMembershipLink() {
		membership.waitForPresence(300);
		membership.click();
		browser.makeScreenshot();
	}
	
	private void addUserToCourse(String user) {
		//  add instructor to course
		if(user.startsWith("instructor")){
			Element userOption = browser.waitForElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ListBoxAllUsers']/option[1]"));
			userOption.click();      
			Element addInstructor = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor"));
			addInstructor.click();  
		}
		// add student to course
		if(user.startsWith("student")){
			Element userOption = browser.waitForElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ListBoxAllUsers']/option[2]"));
			userOption.click();      
			Element addStudent = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddStudent"));
			addStudent.click();   
		} 
		
		browser.makeScreenshot();   			
	}
	
	private void removeUserFromCourse(String user) {
		//  remove instructor to course
		if(user.startsWith("instructor")){                               
			Element userOption = browser.waitForElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ListBoxInstructors']/option[1]"));
			userOption.click();                                
			Element removeInstructor = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonDeleteInstructor"));
			removeInstructor.click();  
		}
		// remove student to course
		if(user.startsWith("student")){
			Element userOption = browser.waitForElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ListBoxStudents']/option[1]"));
			userOption.click();      
			Element removeStudent = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonDeleteStudent"));
			removeStudent.click();   
		} 
		
		browser.makeScreenshot();			
	}
	                                                   
	private void clickOkButton(Element okButton) {                     
		okButton.click();
		browser.clickOkInAlertIfPresent();
		browser.makeScreenshot();
	}
	
	private void clickAdminDashboardLink() {
		browser.switchTo().defaultContent();
		adminDashboardLink.click();
		browser.makeScreenshot();
	}
	
	private void chooseRoleForAdmin(String role) {
		Element markRole;
		if(role.equals("Full")){
			markRole = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_rbAdminRole"));
			markRole.click();			
		}else
			if(role.equals("Help Desk")){
				markRole = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_rbHelpDeskAdminRole"));
				markRole.click();	
			}else
				if(role.equals("Executive")){
					markRole = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_rbExecRole"));
					markRole.click();	
				}else
					Logger.info("Invalid input - Admin Role can be: 'Full', 'Help Desk' or 'Executive'");
		
		browser.makeScreenshot();
	}
	
	private void fillFormForUser(String user) {
	    //  fill the form
		Element userId = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_UserIDTextBox"));
		userId.sendKeys(user);
		Element userName = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_UserNameTextBox"));
		userName.sendKeys(user + "_UserName");
		Element email = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_EmailTextBox"));
		email.sendKeys(user + "@gmail.com");
		Element userPassword = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_txtPassword"));
		userPassword.sendKeys(user);
		Element userConfirmPassword = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_txtConfPassword"));
		userConfirmPassword.sendKeys(user);
		browser.makeScreenshot();
	}
	
	private void fillFormForCourse(String course) {
		//  fill the form
		Element courseId = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucNewCourseControl_ucDialog_CourseIDTextBox"));
		courseId.sendKeys(course + "_CourseId");         
		Element courseName = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ucNewCourseControl_ucDialog_FolderNameTextBox"));
		courseName.sendKeys(course + "_CourseName");
		browser.makeScreenshot();
	}
	
	private void validateCorrectImput(String user) {
		if( !(user.startsWith("instructor") || user.startsWith("student")) ){
			Logger.info("Username must start with 'instructor' or 'student'...");
			return;
		}
	}
	
	private void deleteItems(String name) {
		Logger.info("Trying to delete all " + name + " from Tegrity...");
		browser.makeScreenshot();
		browser.pause(2000);
		browser.switchTo().frame(0);
		
		WebElement item, deleteButton;
		Element tableOfItems = browser.waitForElement(By.xpath("//*[@id='contentDIV']/table/tbody"));
		List<WebElement> items = tableOfItems.findElements(By.tagName("tr"));
		int size = items.size()-2;
		if(items.size() <= 2)
			Logger.info("There are not " + name + " to delete...");
		else{
			Logger.info("There are " + size + " " + name + " to delete...");
	        while(items.size() > 2){  
	        	item = items.get(1);
	        	deleteButton = item.findElement(By.xpath("//*/a[contains(text(), 'Delete')]"));
	        	deleteButton.click();
	        	browser.clickOkInAlertIfPresent();
	        	tableOfItems = browser.waitForElement(By.xpath("//*[@id='contentDIV']/table/tbody"));
	        	items = tableOfItems.findElements(By.tagName("tr"));
	        }
	        Logger.info("All " + name + " were deleted successefully");
		}
		
		browser.makeScreenshot();
		browser.switchTo().defaultContent();		
		adminDashboardLink.click();
		browser.makeScreenshot();
	}
}
