package com.mcgraw.test.automation.ui.applications;

import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.ui.ecollege.ECollegeCourseMhCampusUnderAuthorScreen;
import com.mcgraw.test.automation.ui.ecollege.ECollegeCourseAuthorTab;
import com.mcgraw.test.automation.ui.ecollege.ECollegeCourseDetailsScreen;
import com.mcgraw.test.automation.ui.ecollege.ECollegeHomeScreen;
import com.mcgraw.test.automation.ui.ecollege.ECollegeLoginScreen;

public class ECollegeApplication {
	
	@Value("${ecollege.baseurl}")
	public String ecollegeBaseUrl;

	@Value("${ecollege.title}")
	public String ecollegeTitle;
	
	@Value("${ecollege.gradebook.service.url}")
	public String ecollegeGradebookServiceUrl;
	
	@Value("${ecollege.gradebook.extendedproperties}")
	public String ecollegeGradebookExtendedProperties;
	
	@Value("${ecollege.authorization.service.url}")
	public String ecollegeAuthorizationServiceUrl;
	
	@Value("${ecollege.authorization.extendedproperties}")
	public String ecollegeAuthorizationExtendedProperties;
	
	Browser browser;

	public ECollegeApplication(Browser browser) {
		this.browser = browser;
	}
	
	public ECollegeHomeScreen loginToEcollege(String username, String password) {
		Logger.info("Logging in to eCollege...");
		browser.switchToFirstWindow();
		browser.manage().deleteAllCookies();
		browser.switchTo().defaultContent();
		ECollegeHomeScreen eCollegeHomeScreen = null;
		
		try{
			ECollegeLoginScreen eCollegeLoginScreen = browser.openScreen(ecollegeBaseUrl,
					ECollegeLoginScreen.class);
			eCollegeHomeScreen = eCollegeLoginScreen.loginToEcollege(username, password);
			browser.manage().window().maximize();
		}catch(Exception e)
		{
			Logger.info("Failed Login in to eCollege. Try again.");
			ECollegeLoginScreen eCollegeLoginScreen = browser.openScreen(ecollegeBaseUrl,
					ECollegeLoginScreen.class);
			eCollegeHomeScreen = eCollegeLoginScreen.loginToEcollege(username, password);
			browser.manage().window().maximize();
		}
		return eCollegeHomeScreen;
	}
	
	public ECollegeCourseMhCampusUnderAuthorScreen completeMhCampusSetupWithEcollege(String instructorLogin, String instructorPassword, String courseShortName, String customerNumber)
	{
		Logger.info("Completing setup with eCollege, entering secret key...");
		ECollegeHomeScreen eCollegeHomeScreen = loginToEcollege(instructorLogin, instructorPassword);
		ECollegeCourseDetailsScreen eCollegeCourseDetailsScreen = eCollegeHomeScreen.goToCourse(courseShortName);
		ECollegeCourseAuthorTab authorTab = eCollegeCourseDetailsScreen.goToAuthorTab();
		ECollegeCourseMhCampusUnderAuthorScreen colledgeCourseMhCampusScreen = authorTab.clickMhCampusLinkFromAuthorTab();
		return colledgeCourseMhCampusScreen.changeCustomerNumber(customerNumber);		
	}

}
