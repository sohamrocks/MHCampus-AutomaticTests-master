package com.mcgraw.test.automation.ui.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@class='title'][contains(text(),'LTI Request Details')]")))
public class LtiToolConsumerTestPageScreen extends Screen {

	private static final String oauth_time_stamp = null;

	private static final String oauth_version = null;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@class='toggle-accordion active']"))
	Element expandAll;

	// --------------------------------------Form Data------------------------------------------------//

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "html/body/div[1]/form/div[3]/div[4]/div[1]/h4/a"))
	Element expandFormData;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'oauth_timestamp')]"))
	Element oAuthTimeStamp;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'oauth_version')]"))
	Element oAuthVersion;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'oauth_nonce')]"))
	Element oAuthNonce;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'oauth_signature_method')]"))
	Element oAuthSignatureMethod;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'context_id')]"))
	Element contextId;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'context_title')]"))
	Element contextTitle;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'context_label')]"))
	Element contextLable;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'lis_person_name_full')]"))
	Element lisPersonFullName;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'lis_person_name_given')]"))
	Element lisPersonNameGiven;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'lis_person_name_family')]"))
	Element lisPersonNameFamily;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'lis_person_sourcedid')]"))
	Element lisPersonSourceDid;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'lis_course_offering_sourcedid')]"))
	Element lisCourseOfferingSourceDid;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'lis_person_contact_email_primary')]"))
	Element lisPersonCOntactEmailPrimary;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'roles')]"))
	Element roles;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'resource_link_id')]"))
	Element resourceLinkId;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'user_id')]"))
	Element userID;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'launch_presentation_return_url')]"))
	Element launchPresentationReturnUrl;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'lti_version')]"))
	Element ltiVersion;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'lti_message_type')]"))
	Element ltiMessageType;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'oauth_callback')]"))
	Element oAuthCallback;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'oauth_realm')]"))
	Element oAuthRealm;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'custom_realm')]"))
	Element customRealm;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'oauth_consumer_key')]"))
	Element oAuthConsumerKey;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'custom_application_type')]"))
	Element customApplicationType;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[contains(text(),'oauth_signature')]"))
	Element oAuthSignature;

	public LtiToolConsumerTestPageScreen(Browser browser) {
		super(browser);
	}

	//This function return a dictionary of keys and values from the FormData frame in the test page. 
	public Map<String, String> getFormDataKeysAndValues() {
		List<WebElement> p = browser.findElements(By.tagName("p"));
//		List<String> results = new ArrayList<String>();
		Map<String, String> formDataKeysAndValues = new HashMap<String, String>();
		for (int i = 0; i < p.size(); i++) {
			formDataKeysAndValues.put(
					browser.findElement(By.xpath("html/body/div[1]/form/div[3]/div[4]/div[2]/div/p[" + i + "]/label"))
							.getText()
							+ ":",
					browser.findElement(By.xpath("html/body/div[1]/form/div[3]/div[4]/div[2]/div/p[" + i + "]/span"))
							.getText());
			// IF THE HashMap DOESN'T WORK USE BELOW
			// results.add(browser.findElement(By.xpath("html/body/div[1]/form/div[3]/div[4]/div[2]/div/p["+i+"]/label")).getText()
			// + ":" +
			// browser.findElement(By.xpath("html/body/div[1]/form/div[3]/div[4]/div[2]/div/p["+i+"]/span")).getText());
		}
		return formDataKeysAndValues;

	}

}
