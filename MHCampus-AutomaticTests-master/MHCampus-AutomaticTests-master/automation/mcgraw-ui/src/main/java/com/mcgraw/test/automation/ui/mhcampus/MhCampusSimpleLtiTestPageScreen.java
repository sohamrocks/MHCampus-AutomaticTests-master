package com.mcgraw.test.automation.ui.mhcampus;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "")))
public class MhCampusSimpleLtiTestPageScreen extends Screen {

	public MhCampusSimpleLtiTestPageScreen(Browser browser) {
		super(browser);
	}

	// --------------------------------------Simple LTI Form------------------------------------------------//

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "url"))
		Element URL;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ConsumerKey"))
		Element oAuthConsumerKey;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "Secret"))
		Element secret;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ContextId"))
		Element courseId;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ContextTitle"))
		Element courseTitle;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "ContextLabel"))
		Element contextLabel;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "UserId"))
		Element userId;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "PersonNameFull"))
		Element lisPersonfullName;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "PersonNameGiven"))
		Element lisPersonGivenName;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "PersonNameFamily"))
		Element lisPersonFamilyName;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "PersonSourceId"))
		Element lisPersonSourceDid;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "CourseSourceId"))
		Element lisCourseOfferingSourceDid;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "PersonContactEmail"))
		Element lisPersonContactEmailPrimary;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "roles"))
		Element roles;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "TextBoxResourceLinkId"))
		Element resourceLinkId;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "PresentationUrl"))
		Element launchPresentationReturnUrl;

		@DefinedLocators(@DefinedLocator(how = How.ID, using = "customParameters"))
		Element customParameters;
		
		
		@DefinedLocators(@DefinedLocator(how = How.ID, using = "submit"))
		Element submitButton;
		
		
		//Fill Form Methods
		public void readUrlField (String url){
			if(!URL.equals(null)){
				URL.getText();
			}else {
				fillUrlField(url);
			}
		}
		private void fillUrlField(String url) {
			URL.sendKeys(url);
		}
		public void fillCustomerNumber(String customerNumber){
			oAuthConsumerKey.clear();
			oAuthConsumerKey.sendKeys(customerNumber);
		}
		public void fillSharedSecret(String sharedSecret){
			secret.clear();
			secret.sendKeys(sharedSecret);
		}
		public void fillContextId(String contextId){
			courseId.clear();
			courseId.sendKeys(contextId);
		}
		public void fillContextTitle(String contextTitle){
			courseTitle.clear();
			courseTitle.sendKeys(contextTitle);
		}
		public void fillContextLabel(String courseLabel){
			contextLabel.clear();
			contextLabel.sendKeys(courseLabel);
		}
		public void fillUserId(String userId){
			this.userId.clear();
			this.userId.sendKeys(userId);
		}
		public void fillFullName(String fullName){
			lisPersonfullName.clear();
			lisPersonfullName.sendKeys(fullName);
		}
		public void fillGivenName(String givenName){
			lisPersonGivenName.clear();
			lisPersonGivenName.sendKeys(givenName);
		}
		public void fillFamilyName(String familyName){
			lisPersonFamilyName.clear();
			lisPersonFamilyName.sendKeys(familyName);
		}
		public void fillPersonSourceDid(String personSourceDid){
			lisPersonSourceDid.clear();
			lisPersonSourceDid.sendKeys(personSourceDid);
		}
		public void fillCourseOfferingSourceDid(String courseOfferingSourceDid){
			lisCourseOfferingSourceDid.clear();
			lisCourseOfferingSourceDid.sendKeys(courseOfferingSourceDid);
		}
		public void fillPersonContactEmailPrimary(String personContactEmailPrimary){
			lisPersonContactEmailPrimary.clear();
			lisPersonContactEmailPrimary.sendKeys(personContactEmailPrimary);
		}
		public void fillRole(String role){
			roles.clear();
			roles.sendKeys(role);
		}
		public void readResourceLinkId (String resourceLinkId){
			if(!this.resourceLinkId.equals(null)){
				this.resourceLinkId.getText();
			}else{	
				Logger.info("Something is wrong, no resourceLinkId");
			}
		}
		public void fillLaunchPresentationReturnUrl (String launchPresentationUrl){
			launchPresentationReturnUrl.clear();
			launchPresentationReturnUrl.sendKeys(launchPresentationUrl);
			}
		public void fillCustomParameters(String customParameters){
			this.customParameters.clear();
			this.customParameters.sendKeys(customParameters);
		}
		
		public void clickSubmitButton(){
			submitButton.click();
		}
}
