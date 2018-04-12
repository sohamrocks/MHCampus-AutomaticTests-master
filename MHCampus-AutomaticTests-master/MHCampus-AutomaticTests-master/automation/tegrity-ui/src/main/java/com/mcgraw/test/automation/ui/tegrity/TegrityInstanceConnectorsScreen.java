package com.mcgraw.test.automation.ui.tegrity;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.How;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.PageRelativeUrl;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageRelativeUrl("/")                                                                                                                   
@PageFrameIdentificator(locators = @DefinedLocators(@DefinedLocator(how = How.ID, using = "aairsManagementFrame"))) 
@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@class = 'intagrationPageTitle']//*[contains(text(),'AAIRS')]")))
public class TegrityInstanceConnectorsScreen extends Screen {
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//a[contains(text(),'sign out')]"))
	Element signOutLink; 
	
	//------------------------------------------- Failed Sections -------------------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_MAIN']//*[@class = 'noAgentsMessage']"))
	Element authorizationSectionFailed;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_MAIN']//*[@class = 'noAgentsMessage']"))
	Element ssoInterlinksSectionFailed;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='AUTH_MAIN']//*[@class = 'noAgentsMessage']"))
	Element authenticationSectionFailed;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_MAIN']//*[@class = 'noAgentsMessage']"))
	Element gradebookSectionFailed;

	//------------------------------------------- Edit Links -------------------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_MAIN']/a"))
	Element editSsoLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_MAIN']/a"))
	Element editGradebookLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_MAIN']/a"))
	Element editAuthorizationLink;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='AUTH_MAIN']/a"))
	Element editAuthenticationLink;

	//------------------------------------------- Save Buttons -------------------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_MAIN']//*[@class = 'saveButton']"))
	Element saveGradebookConnectorsBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_MAIN']//*[@class = 'saveButton']"))
	Element saveAuthorizationConnectorsBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='AUTH_MAIN']//*[@class = 'saveButton']"))
	Element saveAuthenticationConnectorsBtn;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_MAIN']//*[@class = 'saveButton']"))
	Element saveButtonForSsoLinks;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "EzSetupRoot1_EzSetup2Integration1_SaveContinue_low"))
	Element saveAndContinueButton;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//button[contains(text(),'Save')]"))
	Element saveButton;
	
    //--------------------------------------- Authorization Connectors ---------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_MAIN']//*[contains(text(),'Angel')]"))
	Element angelConnectorInAuthorization;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_MAIN']//*[contains(text(),'Moodle')]"))
	Element moodleConnectorInAuthorization;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_MAIN']//*[contains(text(),'Canvas')]"))
	Element canvasConnectorInAuthorization;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_MAIN']//*[contains(text(),'Blackboard')]"))
	Element blackboardConnectorInAuthorization;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_MAIN']//*[contains(text(),'Custom')]"))
	Element customConnectorInAuthorization;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_MAIN']//*[contains(text(),'Sakai')]"))
	Element sakaiConnectorInAuthorization;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_MAIN']//*[contains(text(),'Builder')]"))
	Element builderConnectorInAuthorization;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_MAIN']//*[contains(text(),'IMS Import')]"))
	Element imsImportConnectorInAuthorization;
	
	//--------------------------------------- SsoLink Connectors ---------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_MAIN']//*[contains(text(),'Canvas')]"))
	Element canvasConnectorInSsoLink;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_MAIN']//*[contains(text(),'Blackboard')]"))
	Element blackboardConnectorInSsoLink;

	//--------------------------------------- Authentication Connectors ---------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='AUTH_MAIN']//*[contains(text(),'Angel')]"))
	Element angelConnectorInAuthentication;	

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='AUTH_MAIN']//*[contains(text(),'Moodle')]"))
	Element moodleConnectorInAuthentication;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='AUTH_MAIN']//*[contains(text(),'Sakai')]"))
	Element sakaiConnectorInAuthentication;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='AUTH_MAIN']//*[contains(text(),'Blackboard')]"))
	Element blackboardConnectorInAuthentication;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='AUTH_MAIN']//*[contains(text(),'Builder')]"))
	Element builderConnectorInAuthentication;

	//--------------------------------------- Gradebook Connectors ---------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_MAIN']//*[contains(text(),'Moodle')]"))
	Element moodleConnectorInGradebook;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_MAIN']//*[contains(text(),'Canvas')]"))
	Element canvasConnectorInGradebook;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_MAIN']//*[contains(text(),'Blackboard')]"))
	Element bBconnectorInGradebook;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_MAIN']//*[contains(text(),'Angel')]"))
	Element angelConnectorInGradebook;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_MAIN']//*[contains(text(),'Custom')]"))
	Element customConnectorInGradebook;

	//--------------------------------------- Containers ---------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "GRADES_SORTABLE"))
	Element containerGradebook;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "DATASET_SORTABLE"))
	Element containerAuthorization;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "AUTH_SORTABLE"))
	Element containerAuthentication;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "CMS_LINKS_SORTABLE"))
	Element containerSsoLinks;

	//--------------------------------------- Generic For Connectors ---------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_SORTABLE']//div[contains(text(),'Title:')]//input"))
	Input gradebookTitleForConnector;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_SORTABLE']//div[contains(text(),'Service URL:')]//input"))
	Input gradebookServiceUrlForConnector;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_SORTABLE']//textarea"))
	Input gradebookExtendedPropertiesForConnector;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_SORTABLE']//div[contains(text(),'Title:')]//input"))
	Input authorizationTitleForConnector;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_SORTABLE']//div[contains(text(),'Service URL:')]//input"))
	Input authorizationServiceUrlForConnector;

	@DefinedLocators({@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_SORTABLE']//textarea", priority = 1),@DefinedLocator(how = How.CSS, using = "input[id*='EXTENDED_PROPS']", priority = 2)})
	Input authorizationExtendedPropertiesForConnector;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_SORTABLE']//div[contains(text(),'Title:')]//input"))
	Input ssoLinksTitleForConnector;
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='AUTH_SORTABLE']//div[contains(text(),'Title:')]//input"))
	Input authenticationTitleForConnector;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='AUTH_SORTABLE']//div[contains(text(),'Service URL:')]//input"))
	Input authenticationServiceUrlForConnector;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='AUTH_SORTABLE']//textarea"))
	Input authenticationExtendedPropertiesForConnector;

	//--------------------------------------- For Canvas ---------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_SORTABLE']//div[contains(text(),'Canvas FQDN:')]//input"))
	Input gradebookFQDNForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_SORTABLE']//div[contains(text(),'Access Token:')]//input"))
	Input gradebookAccessTokenForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_SORTABLE']//span[contains(text(),'User Id Origin:')]//..//select"))
	Element gradebookUserIdOriginForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_SORTABLE']//span[contains(text(),'Course Id Origin:')]//..//select"))
	Element gradebookCourseIdOriginForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_SORTABLE']//div[contains(text(),'Canvas FQDN:')]//input"))
	Input authorizationFQDNForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_SORTABLE']//div[contains(text(),'Access Token:')]//input"))
	Input authorizationAccessTokenForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_SORTABLE']//span[contains(text(),'User Id Origin:')]//..//select"))
	Element authorizationUserIdOriginForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_SORTABLE']//span[contains(text(),'Course Id Origin:')]//..//select"))
	Element authorizationCourseIdOriginForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_SORTABLE']//span[contains(text(),'Secure Gateway:')]//..//select"))
	Element authorizationSecureGatewayForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_SORTABLE']//div[contains(text(),'Canvas FQDN:')]//input"))
	Input ssoLinksFQDNForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_SORTABLE']//div[contains(text(),'Access Token:')]//input"))
	Input ssoLinksAccessTokenForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_SORTABLE']//span[contains(text(),'Interlink Type:')]//..//select"))
	Element ssoLinksInterlinkTypeForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_SORTABLE']//span[contains(text(),'User Id Origin:')]//..//select"))
	Element ssoLinksUserIdOriginForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_SORTABLE']//span[contains(text(),'Course Id Origin:')]//..//select"))
	Element ssoLinksCourseIdOriginForCanvas;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_SORTABLE']//span[contains(text(),'Secure Gateway:')]//..//select"))
	Element ssoLinksSecureGatewayForCanvas;

	//--------------------------------------- For Blackboard ---------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='DATASET_SORTABLE']//div[contains(text(),'Blackboard Address:')]//input"))
	Input authorizationAddressForBlackboard;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_SORTABLE']//div[contains(text(),'Blackboard Address:')]//input"))
	Input ssoLinksAddressForBlackboard;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='AUTH_SORTABLE']//div[contains(text(),'Blackboard Address:')]//input"))
	Input authenticationAddressForBlackboard;

	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_SORTABLE']//div[contains(text(),'Blackboard Address:')]//input"))
	Input gradebookAddressForBlackboard;

	//--------------------------------------- For Custom ---------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='GRADES_SORTABLE']//*[contains(text(),'Extended Properties:')]//input"))
	Input gradebookExtendedPropertiesForCustom;

	//------------------------------------------- Filter Buttons -------------------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@id='CMS_LINKS_SORTABLE']//*[@class = 'agent-toolbar']//button[contains(text(),'Advanced Filters')]"))
	Element filterButton;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "FileUpload1"))
	Element uploadFile;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ButtonFileUpload"))
	Element loadCsvButton;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "RadioButton1"))
	Element includeAll;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "RadioButton2"))
	Element excludeAll;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "RadioButton3"))
	Element csvLists;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "SignOutLink"))
	Element logoutLink;
	
	//------------------------------------------- Test Buttons -------------------------------------------------------
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "3_test"))
	Element externalTestButtonForAuthentication; 
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='AUTH_MAIN']//*[contains(text(),'Test')]"))
	Element internalTestButtonForAuthentication;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "2_test"))
	Element externalTestButtonForAuthorization; 
	
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = "//*[@id='DATASET_SORTABLE']//*[contains(text(),'Test')]"))
	Element internalTestButtonForAuthorization; 
	
	private static By deleteAuthorizationLink = By.xpath("//div[@id='DATASET_SORTABLE']//*/a[contains(text(),'Close')]");
	private static By deleteSsoLinkLink = By.xpath("//div[@id='CMS_LINKS_SORTABLE']//*/a[contains(text(),'Close')]");
	private static By deleteAuthenticationLink = By.xpath("//div[@id='AUTH_SORTABLE']//*/a[contains(text(),'Close')]");
	private static By deleteGradebookLink = By.xpath("//div[@id='GRADES_SORTABLE']//*/a[contains(text(),'Close')]");
	
	private static String CONNECTORS_FRAME = "aairsManagementFrame";
	private static String ADVANCED_FILTERS_FRAME = "advancedFilters_iframe";

	public TegrityInstanceConnectorsScreen(Browser browser) {
		super(browser);
	}
	
	@Override
	public void clickLogout()
	{
		Logger.info("Logging out...");
		signOutLink.click();
		browser.waitForPage(TegrityInstanceLoginScreen.class);
	}

	public boolean isGradebookConnectorsAvailable() {
		return isConnectorAvailable(gradebookSectionFailed);
	}

	public boolean isAuthorizationConnectorsAvailable() {
		return isConnectorAvailable(authorizationSectionFailed);      
	}

	public boolean isSsoInterlinksConnectorsAvailable() {
		return isConnectorAvailable(ssoInterlinksSectionFailed);
	}

	public boolean isAuthenticationConnectorsAvailable() {
		return isConnectorAvailable(authenticationSectionFailed);
	}

	public void deleteAllConnectors(){   
		Logger.info("Deleting connectors, that were added before...");
		deleteAuthorizationConnector();
		deleteSsoLinkConnector();
		deleteAuthenticationConnector();	
		deleteGradebookConnector();
	}
	
	public void deleteAuthorizationConnector() {
		if(isConnectorAvailable(authorizationSectionFailed))
			deleteAuthorizationConnector(editAuthorizationLink, containerAuthorization);
		else
			Logger.info("Connectors for Authorization don't exist");	
	}
	
	public void deleteSsoLinkConnector() {
		if(isConnectorAvailable(ssoInterlinksSectionFailed))
			deleteSsoLinkConnector(editSsoLink, containerSsoLinks);
		else
			Logger.info("Connectors for Sso Link don't exist");	
	}
	
	public void deleteAuthenticationConnector() {
		if(isConnectorAvailable(authenticationSectionFailed))
			deleteAuthenticationConnector(editAuthenticationLink, containerAuthentication);
		else
			Logger.info("Connectors for Authentication don't exist");	
	}
	
	public void deleteGradebookConnector() {
		if(isConnectorAvailable(gradebookSectionFailed))
			deleteGradebookConnector(editGradebookLink, containerGradebook);
		else
			Logger.info("Connectors for Gradebook don't exist");	
	}
	
	public void configureImsImportAuthorizationConnector() {
		Logger.info("Configuring Ims Import connector for Authorization...");
		configureImsImportAuthorizationConnector(imsImportConnectorInAuthorization);
	}
	
	private void configureImsImportAuthorizationConnector(Element lmsConnector) {
		editSectionAndDragConnector(editAuthorizationLink, lmsConnector,
				containerAuthorization);
		saveAuthorizationSection();
	}
	
	public void configureImsImportAuthenticationConnector() {
		Logger.info("Configuring Ims Import connector for Authorization...");
		configureImsImportAuthenticationConnector(moodleConnectorInAuthentication);
	}
	
	private void configureImsImportAuthenticationConnector(Element lmsConnector) {
		editSectionAndDragConnector(editAuthenticationLink, lmsConnector,
				containerAuthentication);
		typeAuthenticationSettingsForImsImport();
		saveAuthenticationSection();
	}
	
	private void typeAuthenticationSettingsForImsImport() {
		browser.switchTo().frame(CONNECTORS_FRAME);
		authenticationServiceUrlForConnector.waitForPresence(2);
		String text = authenticationServiceUrlForConnector.getAttribute("value");;
		text = text.replace("MoodlelIntegrationService.svc", "MoodleConnectorPass.svc");
		authenticationServiceUrlForConnector.clear();
		authenticationServiceUrlForConnector.typeValue(text);
		browser.switchTo().defaultContent();
	}
	
	public void configureBuilderAuthorizationConnector() {
		Logger.info("Configuring Builder connector for Authorization...");
		configureBuilderAuthorizationConnector(builderConnectorInAuthorization);
	}
	
	private void configureBuilderAuthorizationConnector(Element lmsConnector) {
		editSectionAndDragConnector(editAuthorizationLink, lmsConnector,
				containerAuthorization);
		saveAuthorizationSection();
	}
	
	public void configureBuilderAuthenticationConnector() {
		Logger.info("Configuring Builder connector for Authentication...");
		configureBuilderAuthenticationConnector(builderConnectorInAuthentication);
	}
	
	private void configureBuilderAuthenticationConnector(Element lmsConnector) {
		editSectionAndDragConnector(editAuthenticationLink, lmsConnector,
				containerAuthentication);
		saveAuthenticationSection();
	}
	
	public void configureAngelGradebookConnector(String title,
			String serviceUrl, String extendedProperties) {
		Logger.info("Configuring Angel connector for Gradebook...");
		configureGenericGradebookConnector(title, serviceUrl,
				extendedProperties, angelConnectorInGradebook);
	}

	public void configureAngelAuthorizationConnector(String title,
			String serviceUrl, String extendedProperties) {
		Logger.info("Configuring Angel connector for Authorization...");
		configureGenericAuthorizationConnector(title, serviceUrl,
				extendedProperties, angelConnectorInAuthorization);
	}

	public void configureAngelAuthenticationConnector(String title,
			String serviceUrl, String extendedProperties) {
		Logger.info("Configuring Angel connector for Authentication...");
		configureGenericAuthenticationConnector(title, serviceUrl,
				extendedProperties, angelConnectorInAuthentication);
	}
	
	public void configureAngelAuthorizationConnector(String extendedProperties) {
		Logger.info("Configuring Angel connector for Authorization...");
		configureGenericAuthorizationConnector(extendedProperties, angelConnectorInAuthorization);
	}

	public void configureAngelAuthenticationConnector(String extendedProperties) {
		Logger.info("Configuring Angel connector for Authentication...");
		configureGenericAuthenticationConnector(extendedProperties, angelConnectorInAuthentication);
	}

	public void configureMoodleGradebookConnector(String extendedProperties) {
		Logger.info("Configuring Moodle connector for Gradebook...");
		configureGenericGradebookConnector(extendedProperties,
				moodleConnectorInGradebook);
	}

	public void configureMoodleAuthorizationConnector(String extendedProperties) {
		Logger.info("Configuring Moodle connector for Authorization...");
		configureGenericAuthorizationConnector(extendedProperties,
				moodleConnectorInAuthorization);
	}

	public void configureMoodleAuthenticationConnector(String extendedProperties) {
		Logger.info("Configuring Moodle connector for Authentication...");
		configureGenericAuthenticationConnector(extendedProperties,
				moodleConnectorInAuthentication);
	}

	public void configureSakaiGradebookConnector(String title,
			String serviceUrl, String extendedProperties) {
		Logger.info("Configuring Sakai connector for Gradebook...");
		configureGenericGradebookConnector(title, serviceUrl,
				extendedProperties, customConnectorInGradebook);
	}

	public void configureSakaiAuthorizationConnector(String extendedProperties) {
		Logger.info("Configuring Sakai connector for Authorization...");
		configureGenericAuthorizationConnector(extendedProperties,
				sakaiConnectorInAuthorization);
	}

	public void configureSakaiAuthenticationConnector(String serviceUrl, String extendedProperties) {
		Logger.info("Configuring Sakai connector for Authentication...");
		editSectionAndDragConnector(editAuthenticationLink, sakaiConnectorInAuthentication,
				containerAuthentication);
		typeAuthenticationSettingsForSakai(serviceUrl, extendedProperties);
		saveAuthenticationSection();
	}
	
	public void configureBlackboardGradebookConnector(String title, String address) {
		Logger.info("Configuring Blackboard connector for Gradebook...");
		editSectionAndDragConnector(editGradebookLink, bBconnectorInGradebook,
				containerGradebook);
		typeGradebookSettingsForBlackboard(title, address);
		saveGradebookSection();
	}

	public void configureCustomGradebookConnector(String title,
			String serviceUrl, String extendedProperties) {
		Logger.info("Configuring" + title + "connector for Gradebook...");
		editSectionAndDragConnector(editGradebookLink,
				customConnectorInGradebook, containerGradebook);
		typeGradebookSettingsForCustom(title, serviceUrl, extendedProperties);
		saveGradebookSection();
	}
	
	public void configureCustomAuthorizationConnector(String title,
			String serviceUrl, String extendedProperties)
	{
		editSectionAndDragConnector(editAuthorizationLink, customConnectorInAuthorization,
				containerAuthorization);
		typeAuthorizationSettingsForConnection(title, serviceUrl,
				extendedProperties);
		browser.pause(5000);
		saveAuthorizationSection();
		browser.pause(3000);
		
	}

	public void configureCanvasAuthorizationConnector(String title,   
			String fqdn, String accessToken, String userIdOrigin,
			String courseIdOrigin, String secureGateway) {
		Logger.info("Configuring Canvas connector for Authorization...");
		editSectionAndDragConnector(editAuthorizationLink,
				canvasConnectorInAuthorization, containerAuthorization);
		typeAuthorizationSettingsForCanvas(title, fqdn, accessToken,
				userIdOrigin, courseIdOrigin, secureGateway);
		saveAuthorizationSection();
	}

	public void configureCanvasGradebookConnector(String title, String fqdn,
			String accessToken, String userIdOrigin, String courseIdOrigin) {
		Logger.info("Configuring Canvas connector for Gradebook...");
		editSectionAndDragConnector(editGradebookLink,
				canvasConnectorInGradebook, containerGradebook);
		typeGradebookSettingsForCanvas(title, fqdn, accessToken, userIdOrigin,
				courseIdOrigin);
		saveGradebookSection();
	}

	public void configureCanvasSsoLinkConnector(String title, String fqdn,
			String accessToken, String interlinkType, String userIdOrigin,
			String courseIdOrigin, String secureGateway) {
		Logger.info("Configuring Canvas connector for SSO Link...");
		editSectionAndDragConnector(editSsoLink, canvasConnectorInSsoLink,
				containerSsoLinks);
		typeSsoLinksSettingsForCanvas(title, fqdn, accessToken, interlinkType,
				userIdOrigin, courseIdOrigin, secureGateway);
		saveSSOLinkSection();
	}

	public void configureCanvasSsoLinkConnectorWithIncludeFilter(String title,
			String fqdn, String accessToken, String interlinkType,
			String userIdOrigin, String courseIdOrigin, String secureGateway) {
		Logger.info("Configuring Canvas connector for SSO Link with Include All filter...");
		editSectionAndDragConnector(editSsoLink, canvasConnectorInSsoLink,
				containerSsoLinks);
		typeSsoLinksSettingsForCanvas(title, fqdn, accessToken, interlinkType,
				userIdOrigin, courseIdOrigin, secureGateway);
		chooseIncludeFilter();
	}

	public void configureCanvasSsoLinkConnectorWithExcludeFilter(String title,
			String fqdn, String accessToken, String interlinkType,
			String userIdOrigin, String courseIdOrigin, String secureGateway) {
		Logger.info("Configuring Canvas connector for SSO Link with Exclude All filter...");
		editSectionAndDragConnector(editSsoLink, canvasConnectorInSsoLink,
				containerSsoLinks);
		typeSsoLinksSettingsForCanvas(title, fqdn, accessToken, interlinkType,
				userIdOrigin, courseIdOrigin, secureGateway);
		chooseExcludeFilter();
	}

	public void configureCanvasSsoLinkConnectorWithCsvFilter(String title,
			String fqdn, String accessToken, String interlinkType,
			String userIdOrigin, String courseIdOrigin, String secureGateway,
			String fullPathToFile) {
		Logger.info("Configuring Canvas connector for SSO Link with CSV filter...");
		editSectionAndDragConnector(editSsoLink, canvasConnectorInSsoLink,
				containerSsoLinks);
		typeSsoLinksSettingsForCanvas(title, fqdn, accessToken, interlinkType,
				userIdOrigin, courseIdOrigin, secureGateway);
		chooseCvsListsFilter(fullPathToFile);
	}

	public void chooseIncludeFilter() {
		Logger.info("Choosing Include All filter...");
		editFilters();
		selectFilterRadioButton(includeAll);
		saveFilterSetting();
		saveSSOLinkSection();
	}

	public void chooseExcludeFilter() {
		Logger.info("Choosing Exclude All filter...");
		editFilters();
		selectFilterRadioButton(excludeAll);
		saveFilterSetting();
		saveSSOLinkSection();
	}

	public void chooseCvsListsFilter(String fullPathToFile) {
		Logger.info("Choosing CSV list filter...");
		editFilters();
		selectFilterRadioButton(csvLists);
		uploadFile(fullPathToFile);
		saveFilterSetting();
		saveSSOLinkSection();
	}
 
    public boolean isBlackboardConnectorsNeedToAdd(){
    	Logger.info("Checking if it needed to add the Blackboard connectors...");
    	boolean available = isAuthorizationConnectorsAvailable();
		if (available)
    		Logger.info("Blackboard connectors are exist");
    	else
    		Logger.info("Blackboard connectors don't exist");
    	
		return !available;
	}
 	
    public void configureBlackboardAuthorizationConnector(String title, String address) {
		Logger.info("Configuring Blackboard connector for Authorization...");
		editSectionAndDragConnector(editAuthorizationLink,
				blackboardConnectorInAuthorization, containerAuthorization);
		typeAuthorizationSettingsForBlackboard(title, address);
		saveAuthorizationSection();
	}
    
  	public void configureBlackboardSsoLinkConnector(String title, String address) {
		Logger.info("Configuring Blackboard connector for SSO Link...");
		editSectionAndDragConnector(editSsoLink, blackboardConnectorInSsoLink,
				containerSsoLinks);
		typeSsoLinksSettingsForBlackboard(title, address);
		saveSSOLinkSection();
	}
  	
  	public void configureBlackboardAuthenticationConnector(String title, String address) { 
		Logger.info("Configuring Blackboard connector for Authentication...");
		editSectionAndDragConnector(editAuthenticationLink,
				blackboardConnectorInAuthentication, containerAuthentication);
		typeAuthenticationSettingsForBlackboard(title, address);
		saveAuthenticationSection();
	}
    
	public TegrityInstanceDashboardScreen clickSaveAndContinueButton(){        
		Logger.info("Clicking Save & Continue on Tegrity Instance Connectors page...");
		browser.pause(2000);
		Element connectorFrame = browser.waitForElement(By.id(CONNECTORS_FRAME));
		browser.switchTo().frame(connectorFrame);
		saveAndContinueButton.waitForPresence(30);
		try{
			saveAndContinueButton.click();
		}catch(Exception e){
			Logger.info("Try click Save & Continue on Tegrity Instance Connectors page again...");
			browser.pause(5000);
			saveAndContinueButton.click();
		}
		browser.switchTo().defaultContent();
		TegrityInstanceDashboardScreen tegrityInstanceDashboardScreen = 
				browser.waitForPage(TegrityInstanceDashboardScreen.class, 30);
		browser.pause(3000);
		return tegrityInstanceDashboardScreen;
	}
	
	public void saveAuthorizationSection() {
		saveSection(saveAuthorizationConnectorsBtn);
		browser.pause(3000);
	}

	public void saveAuthenticationSection() {
		saveSection(saveAuthenticationConnectorsBtn);
		browser.pause(3000);
	}

	public void saveGradebookSection() {
		saveSection(saveGradebookConnectorsBtn);
		browser.pause(3000);
	}

	public void saveSSOLinkSection() {
		saveSection(saveButtonForSsoLinks);
		browser.pause(3000);
	}
	
	public String getResultOfExternalTestButtonForAuthentication(String username, String password){
		Logger.info("Getting result of EXTERNAL 'Test' button for Authentication connector...");		
		String result = getResultOfTestButton(externalTestButtonForAuthentication, username, password);
		browser.switchTo().defaultContent();		
		return result;	
	}

	public String getResultOfInternalTestButtonForAuthentication(String username, String password){
		Logger.info("Getting result of INTERNAL 'Test' button for Authentication connector...");		
		editSection(editAuthenticationLink);		
		String result = getResultOfTestButton(internalTestButtonForAuthentication, username, password);		
		saveAuthenticationConnectorsBtn.click();
		browser.pause(3000);
		browser.switchTo().defaultContent();		
		return result;	
	}	
	
	public String getResultOfExternalTestButtonForAuthorization(String username){
		Logger.info("Getting result of EXTERNAL 'Test' button for Authorization connector...");		
		String result = getResultOfTestButton(externalTestButtonForAuthorization, username, null);
		browser.switchTo().defaultContent();		
		return result;	
	}
	
	public String getResultOfInternalTestButtonForAuthorization(String username){
		Logger.info("Getting result of INTERNAL 'Test' button for Authorization connector...");		
		editSection(editAuthorizationLink);		
		String result = getResultOfTestButton(internalTestButtonForAuthorization, username, null);		
		saveAuthorizationConnectorsBtn.click();
		browser.pause(3000);
		browser.switchTo().defaultContent();
		return result;	
	}	
	
	public String getCoursesList(){
		openInterlinkConnector();
		goToAdvancedFiltersScreen();
		return browser.findElement(By.id("AdvancedFilter_TextBox")).getAttribute("value");
	}

	// Private methods
	//Added By Yuval 24.09.2017
	private void openInterlinkConnector(){
		if (isSsoInterlinksConnectorsAvailable()){
			browser.switchTo().frame(CONNECTORS_FRAME);
			editSsoLink.waitForPresence(10);
			editSsoLink.click();
			browser.pause(2000);
		}
	}
	
	private void goToAdvancedFiltersScreen(){
		filterButton.click();
		browser.pause(2000);
		browser.switchTo().frame("advancedFilters_iframe");
	}

	private void configureGenericAuthorizationConnector(String title,
			String serviceUrl, String extendedProperties, Element lmsConnector) {

		editSectionAndDragConnector(editAuthorizationLink, lmsConnector,
				containerAuthorization);
		typeAuthorizationSettingsForConnection(title, serviceUrl,extendedProperties);
		saveAuthorizationSection();
	}

	private void configureGenericAuthorizationConnector(
			String extendedProperties, Element lmsConnector) {

		editSectionAndDragConnector(editAuthorizationLink, lmsConnector,
				containerAuthorization);
		typeAuthorizationSettingsForConnection(extendedProperties);
		saveAuthorizationSection();
	}

	private void configureGenericAuthenticationConnector(String title,
			String serviceUrl, String extendedProperties, Element lmsConnector) {

		editSectionAndDragConnector(editAuthenticationLink, lmsConnector,
				containerAuthentication);
		typeAuthenticationSettingsForConnection(title, serviceUrl,
				extendedProperties);
		saveAuthenticationSection();
	}

	private void configureGenericAuthenticationConnector(
			String extendedProperties, Element lmsConnector) {

		editSectionAndDragConnector(editAuthenticationLink, lmsConnector,
				containerAuthentication);
		typeAuthenticationSettingsForConnection(extendedProperties);
		saveAuthenticationSection();
	}

	private void configureGenericGradebookConnector(String title,
			String serviceUrl, String extendedProperties, Element lmsConnector) {

		editSectionAndDragConnector(editGradebookLink, lmsConnector,
				containerGradebook);
		typeGradebookSettingsForConnection(title, serviceUrl,
				extendedProperties);
		saveGradebookSection();
	}

	private void configureGenericGradebookConnector(String extendedProperties,
			Element lmsConnector) {

		editSectionAndDragConnector(editGradebookLink, lmsConnector,
				containerGradebook);
		typeGradebookSettingsForConnection(extendedProperties);
		saveGradebookSection();
	}

	private void saveSection(Element saveButton) {
		Logger.info("Clicking Save Button...");
		browser.switchTo().frame(CONNECTORS_FRAME);
		saveButton.click();
		browser.pause(5000);
		browser.switchTo().defaultContent();
		browser.makeScreenshot();
	}

	private void uploadFile(String fullPathToFile) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		browser.switchTo().frame(ADVANCED_FILTERS_FRAME);
		uploadFile.sendKeys(fullPathToFile);
		loadCsvButton.click();
		browser.switchTo().defaultContent();
	}

	private void selectFilterRadioButton(Element filterButton) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		browser.switchTo().frame(ADVANCED_FILTERS_FRAME);
		filterButton.click();
		browser.pause(3000);
		browser.switchTo().defaultContent();
	}

	private void saveFilterSetting() {
		Logger.info("Clicking Save on Filter Setting...");
		browser.makeScreenshot();
		browser.switchTo().frame(CONNECTORS_FRAME);
		saveButton.click();
		browser.pause(3000);
		browser.switchTo().defaultContent();
	}

	private void editFilters() {
		browser.switchTo().frame(CONNECTORS_FRAME);
		editSsoLink.click();
		filterButton.click();
		browser.switchTo().defaultContent();
	}
	
	private void editSectionAndDragConnector(Element editSectionButton,
			Element connector, Element container) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		
		String containerID = (container.toString()).substring(3);
		editSectionButton.click();
		browser.pause(1000);
		Logger.info("Dragging connector...");
		new Actions(browser).clickAndHold(connector).moveToElement(container)
				.release().perform();
		try{
			browser.waitForElement(By.xpath("//div[@id='" + containerID + "']//*[contains(text(),'Test')]"), 10);
		}catch(Exception ex){
			Logger.info("Failed to drag connector, trying again...");
			new Actions(browser).clickAndHold(connector).moveToElement(container)
			     .release().perform();
		}
		
		browser.pause(1000);
		browser.switchTo().defaultContent();
	}
	
	private void typeAuthenticationSettingsForSakai(String serviceUrl,
			                String extendedProperties) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		authenticationServiceUrlForConnector.waitForPresence(2);
		authenticationServiceUrlForConnector.clear();
		authenticationServiceUrlForConnector.typeValue(serviceUrl);
		authenticationExtendedPropertiesForConnector.clear();
		authenticationExtendedPropertiesForConnector.typeValue(extendedProperties);
		browser.switchTo().defaultContent();
	}

	private void typeAuthorizationSettingsForConnection(String title,
			String serviceUrl, String extendedProperties) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		authorizationTitleForConnector.waitForPresence(2);
		if(authorizationTitleForConnector.getAttribute("value").isEmpty())
			authorizationTitleForConnector.typeValue(title);
		if(authorizationServiceUrlForConnector.getAttribute("value").isEmpty())
			authorizationServiceUrlForConnector.typeValue(serviceUrl);
		authorizationExtendedPropertiesForConnector.clear();
		authorizationExtendedPropertiesForConnector.typeValue(extendedProperties);
		browser.switchTo().defaultContent();
	}

	private void typeAuthorizationSettingsForConnection(
			String extendedProperties) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		authorizationExtendedPropertiesForConnector.waitForPresence(2);
		authorizationExtendedPropertiesForConnector.clear();
		authorizationExtendedPropertiesForConnector
				.typeValue(extendedProperties);
		browser.switchTo().defaultContent();
	}

	private void typeGradebookSettingsForConnection(String title,
			String serviceUrl, String extendedProperties) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		gradebookTitleForConnector.waitForPresence(2);
		gradebookTitleForConnector.clear();
		gradebookTitleForConnector.typeValue(title);
		gradebookServiceUrlForConnector.clear();
		gradebookServiceUrlForConnector.typeValue(serviceUrl);
		gradebookExtendedPropertiesForConnector.clear();
		gradebookExtendedPropertiesForConnector.typeValue(extendedProperties);
		browser.switchTo().defaultContent();
	}

	private void typeGradebookSettingsForConnection(String extendedProperties) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		gradebookExtendedPropertiesForConnector.waitForPresence(2);
		gradebookExtendedPropertiesForConnector.clear();
		gradebookExtendedPropertiesForConnector.typeValue(extendedProperties);
		browser.switchTo().defaultContent();
	}

	private void typeAuthenticationSettingsForConnection(String title,
			String serviceUrl, String extendedProperties) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		authenticationTitleForConnector.waitForPresence(2);
		if(authenticationTitleForConnector.getAttribute("value").isEmpty())
			authenticationTitleForConnector.typeValue(title);
		if(authenticationServiceUrlForConnector.getAttribute("value").isEmpty())
			authenticationServiceUrlForConnector.typeValue(serviceUrl);
		authenticationExtendedPropertiesForConnector.clear();
		authenticationExtendedPropertiesForConnector.typeValue(extendedProperties);
		browser.switchTo().defaultContent();
	}

	private void typeAuthenticationSettingsForConnection(
			String extendedProperties) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		authenticationExtendedPropertiesForConnector.waitForPresence(2);
		authenticationExtendedPropertiesForConnector.clear();
		authenticationExtendedPropertiesForConnector.typeValue(extendedProperties);
		browser.switchTo().defaultContent();
	}

	private void typeGradebookSettingsForCustom(String title,
			String serviceUrl, String extendedProperties) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		gradebookTitleForConnector.waitForPresence(2);
		gradebookTitleForConnector.clear();
		gradebookTitleForConnector.typeValue(title);
		gradebookServiceUrlForConnector.clear();
		gradebookServiceUrlForConnector.typeValue(serviceUrl);
		gradebookExtendedPropertiesForCustom.clear();
		gradebookExtendedPropertiesForCustom.typeValue(extendedProperties);
		browser.switchTo().defaultContent();
	}

	private void typeGradebookSettingsForCanvas(String title, String fqdn,
			String accessToken, String userIdOrigin, String courseIdOrigin) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		gradebookTitleForConnector.waitForPresence(2);
		gradebookTitleForConnector.clear();
		gradebookTitleForConnector.typeValue(title);
		gradebookFQDNForCanvas.clear();
		gradebookFQDNForCanvas.typeValue(fqdn);
		gradebookAccessTokenForCanvas.clear();
		gradebookAccessTokenForCanvas.typeValue(accessToken);
		gradebookUserIdOriginForCanvas.sendKeys(userIdOrigin);
		gradebookCourseIdOriginForCanvas.sendKeys(courseIdOrigin);
		browser.switchTo().defaultContent();
	}

	private void typeAuthorizationSettingsForCanvas(String title, String fqdn,
			String accessToken, String userIdOrigin, String courseIdOrigin,
			String secureGateway) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		authorizationTitleForConnector.waitForPresence(2);
		authorizationTitleForConnector.clear();
		authorizationTitleForConnector.typeValue(title);
		authorizationFQDNForCanvas.clear();
		authorizationFQDNForCanvas.typeValue(fqdn);
		authorizationAccessTokenForCanvas.clear();
		authorizationAccessTokenForCanvas.typeValue(accessToken);
		authorizationUserIdOriginForCanvas.sendKeys(userIdOrigin);
		authorizationCourseIdOriginForCanvas.sendKeys(courseIdOrigin);
		authorizationSecureGatewayForCanvas.sendKeys(secureGateway);
		browser.switchTo().defaultContent();
	}

	private void typeSsoLinksSettingsForCanvas(String title, String fqdn,
			String accessToken, String interlinkType, String userIdOrigin,
			String courseIdOrigin, String secureGateway) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		ssoLinksTitleForConnector.waitForPresence(2);
		ssoLinksTitleForConnector.clear();
		ssoLinksTitleForConnector.typeValue(title);
		ssoLinksFQDNForCanvas.clear();
		ssoLinksFQDNForCanvas.typeValue(fqdn);
		ssoLinksAccessTokenForCanvas.clear();
		ssoLinksAccessTokenForCanvas.typeValue(accessToken);
		ssoLinksInterlinkTypeForCanvas.sendKeys(interlinkType);
		ssoLinksUserIdOriginForCanvas.sendKeys(userIdOrigin);
		ssoLinksCourseIdOriginForCanvas.sendKeys(courseIdOrigin);
		ssoLinksSecureGatewayForCanvas.sendKeys(secureGateway);
		browser.switchTo().defaultContent();
	}
	
	private void typeAuthorizationSettingsForBlackboard(String title, String address) {
  		browser.switchTo().frame(CONNECTORS_FRAME);
  		authorizationTitleForConnector.waitForPresence(2);
  		authorizationTitleForConnector.clear();
  		authorizationTitleForConnector.typeValue(title);
  		authorizationAddressForBlackboard.clear();
	  	authorizationAddressForBlackboard.typeValue(address);
  		browser.switchTo().defaultContent();
  	}
	
  	private void typeSsoLinksSettingsForBlackboard(String title, String address) {
  		browser.switchTo().frame(CONNECTORS_FRAME);
  		ssoLinksTitleForConnector.waitForPresence(2);
  		ssoLinksTitleForConnector.clear();
	  	ssoLinksTitleForConnector.typeValue(title);
  		ssoLinksAddressForBlackboard.clear();
	  	ssoLinksAddressForBlackboard.typeValue(address);
  		browser.switchTo().defaultContent();
  	}
 
  private void typeAuthenticationSettingsForBlackboard(String title, String address) {
  		browser.switchTo().frame(CONNECTORS_FRAME);
  		authenticationTitleForConnector.waitForPresence(2);
  		authenticationTitleForConnector.clear();
	  	authenticationTitleForConnector.typeValue(title);
	  	authenticationAddressForBlackboard.clear();
	  	authenticationAddressForBlackboard.typeValue(address);
  		browser.switchTo().defaultContent();
  	}
  
  	private void typeGradebookSettingsForBlackboard(String title, String address) {
  		browser.switchTo().frame(CONNECTORS_FRAME);
  		gradebookTitleForConnector.waitForPresence(2);
  		gradebookTitleForConnector.clear();
	  	gradebookTitleForConnector.typeValue(title);
	  	gradebookAddressForBlackboard.clear();
  		gradebookAddressForBlackboard.typeValue(address);
  		browser.switchTo().defaultContent();
  	}

	private boolean isConnectorAvailable(Element failedConnection) {
		browser.switchTo().frame(CONNECTORS_FRAME);
		boolean isSectionErrorDisplayed = failedConnection.isDisplayed();
		browser.switchTo().defaultContent();
		return (!isSectionErrorDisplayed);
	}
		
	private void deleteAuthorizationConnector(Element editSectionButton,  Element container) {		 
		browser.switchTo().frame(CONNECTORS_FRAME);
		editSectionButton.waitForPresence(10);
		editSectionButton.click();
		browser.pause(2000);
			
		while(browser.findElements(deleteAuthorizationLink).size() != 0){
			List<WebElement> deleteLinks = browser.findElements(deleteAuthorizationLink);
			Element	deleteLink = (Element) deleteLinks.get(0);
			deleteLink.click();
			browser.pause(2000);
			Logger.info("Authorization connector was deleted successfully");
		}
		browser.switchTo().defaultContent();
		saveAuthorizationSection();
	}
	
	private void deleteSsoLinkConnector(Element editSectionButton,  Element container) {		
		browser.switchTo().frame(CONNECTORS_FRAME);
		editSectionButton.waitForPresence(10);
		editSectionButton.click();
		browser.pause(2000);
		
		while(browser.findElements(deleteSsoLinkLink).size() != 0){
			List<WebElement> deleteLinks = browser.findElements(deleteSsoLinkLink);
			Element	deleteLink = (Element) deleteLinks.get(0);
			deleteLink.click();
			browser.pause(2000);
			Logger.info("SsoLink connector was deleted successfully");
		}
		browser.switchTo().defaultContent();
		saveSSOLinkSection();
	}

	private void deleteAuthenticationConnector(Element editSectionButton,  Element container) {		
		browser.switchTo().frame(CONNECTORS_FRAME);
		editSectionButton.waitForPresence(10);
		editSectionButton.click();
		browser.pause(2000);
		
		while(browser.findElements(deleteAuthenticationLink).size() != 0){
			List<WebElement> deleteLinks = browser.findElements(deleteAuthenticationLink);
			Element	deleteLink = (Element) deleteLinks.get(0);
			deleteLink.click();
			browser.pause(2000);
			Logger.info("Authentication connector was deleted successfully");
		}
		browser.switchTo().defaultContent();
		saveAuthenticationSection();	
	}
			
	private void deleteGradebookConnector(Element editSectionButton,  Element container) {		
		browser.switchTo().frame(CONNECTORS_FRAME);
		editSectionButton.waitForPresence(10);
		editSectionButton.click();
		browser.pause(2000);
		
		while(browser.findElements(deleteGradebookLink).size() != 0){
			List<WebElement> deleteLinks = browser.findElements(deleteGradebookLink);
			Element	deleteLink = (Element) deleteLinks.get(0);
			deleteLink.click();
			browser.pause(2000);
			Logger.info("Gradebook connector was deleted successfully");
		}
		browser.switchTo().defaultContent();
		saveGradebookSection();
	}	
	
	private void editSection(Element editSectionButton) {
		Logger.info("Clicl Edit link...");	
		browser.switchTo().frame(CONNECTORS_FRAME);
		editSectionButton.click();
		browser.pause(2000);
		browser.switchTo().defaultContent();
	}
	
	private String getResultOfTestButton(Element testButton, String username, String password){
		Logger.info("Getting result of 'Test' button...");
		String result = null;		
		browser.switchTo().frame(CONNECTORS_FRAME);
		testButton.click();
		
		if( (username != null) && (password != null)){
			Logger.info("Type username and password...");
			Element clientId = browser.waitForElement(By.id("Test_USERNAME_INPUT"));  
			clientId.clear();
			clientId.sendKeys(username);
			Element clientPassword = browser.waitForElement(By.id("Test_PASSWORD_INPUT"));
			clientPassword.clear();
			clientPassword.sendKeys(password);		
		}else if(username != null){
			Logger.info("Type username...");
			Element clientId = browser.waitForElement(By.id("Test_USERID_INPUT"));  
			clientId.clear();
			clientId.sendKeys(username);
		}
		
		browser.makeScreenshot();
		Element runTestButton = browser.waitForElement(By.xpath("//*[@id='Dialog_1']/div/table/tbody/tr[4]/td[2]/input"));
		Logger.info("Clicking 'Test' button...");
		runTestButton.click();                                   
		browser.pause(10000);
		browser.makeScreenshot();
		Element textArea = browser.waitForElement(By.xpath("//*[@id='Dialog_1']/div/textarea"));
		result = textArea.getText();
		Logger.info("Connector test result is: " + result);
		Element closeWindow = browser.waitForElement(By.xpath("html/body/div[3]/div[3]/button"));
		Logger.info("Clicking 'Close Window' button...");
		closeWindow.click();
		browser.pause(2000);
		browser.makeScreenshot();
			
		return result;
	 }
	
	public void logOut(){
		Logger.info("Logging out...");
		logoutLink.click();
		browser.waitForPage(TegrityInstanceLoginScreen.class, 30);
	}
}