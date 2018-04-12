package com.mcgraw.test.automation.ui.applications;

import java.util.List;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.framework.core.common.CommonUtils;
import com.mcgraw.test.automation.framework.core.common.remote_access.mail.Letter;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.runner.cli.MhCampusCliParams;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusInstanceLoginScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusReaderType;
import com.mcgraw.test.automation.ui.mhcampus.TegrityAccountsScreen;
import com.mcgraw.test.automation.ui.mhcampus.TegrityCreateMhCampusCustomerScreen;
import com.mcgraw.test.automation.ui.mhcampus.TegrityCreateMhCampusCustomerScreen.PageAppearanceSettings;
import com.mcgraw.test.automation.ui.mhcampus.TegrityLoginScreen;
import com.mcgraw.test.automation.ui.service.InstanceUtils;
import com.mcgraw.test.automation.ui.service.EmailClient;
import com.mcgraw.test.automation.ui.service.WelcomeEmail;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class TegrityAdministrationApplication {

	@Value("${tegrity.loginurl}")
	public String tegrityLoginUrl;

	@Value("${tegrity.service.name}")
	public String tegrityServiceName;

	@Value("${tegrity.login}")
	public String tegrityLogin;

	@Value("${tegrity.password}")
	public String tegrityPassword;

	@Value("${tegrity.score.url}")
	public String testScoreFormUrl;

	@Value("${tegrity.installationid}")
	public String tegrityInstallationId;

	@Value("${tegrity.sharedkey}")
	public String tegritySharedKey;
	
	//---------------------------------------------  MH Campus instance 1 ----------------------------
	@Value("${mhcampus.instance.institution.1}")
	public String mhcampusInstitution1;
	
	@Value("${mhcampus.instance.loginurl.1}")
	public String mhcampusLoginUrl1;
	
	@Value("${mhcampus.instance.username.1}")
	public String mhcampusUsername1;
	
	@Value("${mhcampus.instance.password.1}")
	public String mhcampusPassword1;
	
	@Value("${mhcampus.instance.customernumber.1}")
	public String customerNumber1;
	
	@Value("${mhcampus.instance.sharedsecret.1}")
	public String sharedSecret1;
	
	//---------------------------------------------  MH Campus instance 2 ----------------------------
	@Value("${mhcampus.instance.institution.2}")
	public String mhcampusInstitution2;
	
	@Value("${mhcampus.instance.loginurl.2}")
	public String mhcampusLoginUrl2;
	
	@Value("${mhcampus.instance.username.2}")
	public String mhcampusUsername2;
	
	@Value("${mhcampus.instance.password.2}")
	public String mhcampusPassword2;
	
	@Value("${mhcampus.instance.customernumber.2}")
	public String customerNumber2;
	
	@Value("${mhcampus.instance.sharedsecret.2}")
	public String sharedSecret2;
	
	//---------------------------------------------  MH Campus instance 3 ----------------------------
	@Value("${mhcampus.instance.institution.3}")
	public String mhcampusInstitution3;
	
	@Value("${mhcampus.instance.loginurl.3}")
	public String mhcampusLoginUrl3;
	
	@Value("${mhcampus.instance.username.3}")
	public String mhcampusUsername3;
	
	@Value("${mhcampus.instance.password.3}")
	public String mhcampusPassword3;
	
	@Value("${mhcampus.instance.customernumber.3}")
	public String customerNumber3;
	
	@Value("${mhcampus.instance.sharedsecret.3}")
	public String sharedSecret3;
	
	//---------------------------------------------  MH Campus instance 4 ----------------------------
	@Value("${mhcampus.instance.institution.4}")
	public String mhcampusInstitution4;
	
	@Value("${mhcampus.instance.loginurl.4}")
	public String mhcampusLoginUrl4;
	
	@Value("${mhcampus.instance.username.4}")
	public String mhcampusUsername4;
	
	@Value("${mhcampus.instance.password.4}")
	public String mhcampusPassword4;
	
	@Value("${mhcampus.instance.customernumber.4}")
	public String customerNumber4;
	
	@Value("${mhcampus.instance.sharedsecret.4}")
	public String sharedSecret4;
	
	//---------------------------------------------  MH Campus instance for Canvas Deep Integration with Connect ----------------------------
	
	@Value("${mhcampus.instance.institution.deep}")
	public String mhcampusInstitutionDeep;
	
	@Value("${mhcampus.instance.loginurl.deep}")
	public String mhcampusLoginUrlDeep;
	
	@Value("${mhcampus.instance.username.deep}")
	public String mhcampusUsernameDeep;
	
	@Value("${mhcampus.instance.password.deep}")
	public String mhcampusPasswordDeep;
	
	@Value("${mhcampus.instance.customernumber.deep}")
	public String customerNumberDeep;
	
	@Value("${mhcampus.instance.sharedsecret.deep}")
	public String sharedSecretDeep;

	private TegrityLoginScreen clientServiceScreen;
	private TegrityAccountsScreen campusAccountsScreen;
	private TegrityCreateMhCampusCustomerScreen accountCreatingScreen;

	private MhCampusInstanceLoginScreen mhCampusInstanceLoginScreen;

	private Browser browser;

	private EmailClient emailClient;

	private InstanceUtils instanceUtils;
	
	private boolean checkBoxShowActiveCourse;
	private boolean enableGradebookServices;
	
	public String Country ="United States";
	public String State ="TEXAS";
	public String School ="MATAGORDA MEDICAL GROUP";

	public boolean getEnableGradebookServices() {
		Logger.info("Gradebook Services enable: " + enableGradebookServices);
		return enableGradebookServices;
	}
	
	public boolean getCheckBoxShowActiveCourse() {
		return checkBoxShowActiveCourse;
	}

	public TegrityAdministrationApplication(Browser browser) {
		this.browser = browser;
	}

	public EmailClient getEmailClient() {
		return emailClient;
	}

	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}

	public TegrityAccountsScreen loginToTegrity() throws Exception {

		clientServiceScreen = browser.openScreen(tegrityLoginUrl, TegrityLoginScreen.class);
		campusAccountsScreen = clientServiceScreen.loginToTegrityClientServices(tegrityServiceName, tegrityLogin, tegrityPassword);
		return campusAccountsScreen;
	}
	
	public void setInstanceUtils(InstanceUtils instanceUtils) {
		this.instanceUtils = instanceUtils;
	}

	public MhCampusIntroductionScreen loginToMhCampusAsUser(String deployLoginPageAddress, String institution, String login, String password) {

		Logger.info("Logging in to MH Campus instance: URL=" + deployLoginPageAddress + " as user: username=" + login + ", password=" + password);
		browser.manage().deleteAllCookies();
		mhCampusInstanceLoginScreen = browser.openScreen(deployLoginPageAddress, MhCampusInstanceLoginScreen.class);
		return mhCampusInstanceLoginScreen.loginToCampusAsUser(institution, login, password);
	}

	public InstanceCredentials createNewMhCampusInstance(PageAppearanceSettings generalSetting, MhCampusReaderType readerType)
			throws Exception {

		return createNewMhCampusInstance(generalSetting, generalSetting, readerType);
	}
	
	public InstanceCredentials createNewMhCampusInstanceWithCentralOrg(PageAppearanceSettings generalSetting, MhCampusReaderType readerType)
			throws Exception {

		return createNewMhCampusInstanceWithCentralOrg(generalSetting, generalSetting, readerType);
	}

	public InstanceCredentials createNewMhCampusInstance(PageAppearanceSettings instructorSeting, PageAppearanceSettings studentSeting)
			throws Exception {

		return createNewMhCampusInstance(instructorSeting, studentSeting, MhCampusReaderType.COURSE_SMART);
	}

	public InstanceCredentials createNewMhCampusInstance(PageAppearanceSettings instructorSeting, PageAppearanceSettings studentSeting,
			MhCampusReaderType readerType) throws Exception {

		Logger.info("Creating new MH Campus Instance...");

		String domain = "epamInstance-" + CommonUtils.timeStampDetailed();
		String institution = "epamInstitution-" + CommonUtils.timeStampDetailed();
		String expectedLoginPageAddress = "http://" + domain + ".mhcampus.com";

		clientServiceScreen = browser.openScreen(tegrityLoginUrl, TegrityLoginScreen.class);
		campusAccountsScreen = clientServiceScreen.loginToTegrityClientServices(tegrityServiceName, tegrityLogin, tegrityPassword);
		accountCreatingScreen = campusAccountsScreen.goToAccountCreatingPage();
		
		//Check the CheckBox of Show Active Course selected or not
		checkBoxShowActiveCourse = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_CheckBoxShowActiveCourse")).isSelected();
		//Check the CheckBox of Gradebook Services selected or not
		enableGradebookServices = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_CheckBoxGradebookServices")).isSelected();  
		
		campusAccountsScreen = accountCreatingScreen.fillInDetailsForAccountAndSave(domain, institution, tegrityInstallationId,
				tegritySharedKey, emailClient.getLogin(), instructorSeting, studentSeting, readerType);

		List<Letter> receivedLetters = emailClient.WaitForEmailWithBodyContentArrival(expectedLoginPageAddress);
		Letter targetLetter = receivedLetters.get(0);
		InstanceCredentials instanceCredentials = WelcomeEmail.extractCredentials(targetLetter);
		
//      if the value from command line not null use it as pageAddressForLogin
		String instanceUrlFromCli = MhCampusCliParams.getInstance().getInstanceUrl();
		if (instanceUrlFromCli != null){
			instanceCredentials.pageAddressForLogin = instanceUrlFromCli;
		}
//		Otherwise, pageAddressForLogin equals to pageAddressFromEmail
		else{
			instanceCredentials.pageAddressForLogin = instanceCredentials.pageAddressFromEmail;
		}
		
		instanceCredentials.institution = institution;

		Logger.info("Extracted e-mail credentials for current instance:");
		Logger.info("MH Campus Instance login page address: " + instanceCredentials.pageAddressFromEmail);
		Logger.info("MH Campus Instance login: " + instanceCredentials.username);
		Logger.info("MH Campus Instance password: " + instanceCredentials.password);
		Logger.info("Customer number: " + instanceCredentials.customerNumber);
		Logger.info("Shared secret: " + instanceCredentials.sharedSecret);
		
		Logger.info("Is checkbox 'Show Active Course' selected: " + checkBoxShowActiveCourse);
		Logger.info("Is checkbox 'Gradebook Services' selected: " + enableGradebookServices);	

		Logger.info("Delete email by customer number");
		emailClient.deleteEmailByBodyContent(instanceCredentials.customerNumber);

		return instanceCredentials;

	}
	public InstanceCredentials createNewMhCampusInstanceWithCentralOrg(PageAppearanceSettings instructorSeting, PageAppearanceSettings studentSeting,
			MhCampusReaderType readerType) throws Exception {

		Logger.info("Creating new MH Campus Instance...");

		String domain = "epamInstance-" + CommonUtils.timeStampDetailed();
		String institution = "epamInstitution-" + CommonUtils.timeStampDetailed();
		String expectedLoginPageAddress = "http://" + domain + ".mhcampus.com";

		clientServiceScreen = browser.openScreen(tegrityLoginUrl, TegrityLoginScreen.class);
		campusAccountsScreen = clientServiceScreen.loginToTegrityClientServices(tegrityServiceName, tegrityLogin, tegrityPassword);
		accountCreatingScreen = campusAccountsScreen.goToAccountCreatingPage();
		
		//Check the CheckBox of Show Active Course selected or not
		checkBoxShowActiveCourse = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_CheckBoxShowActiveCourse")).isSelected();
		//Check the CheckBox of Gradebook Services selected or not
		enableGradebookServices = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_CheckBoxGradebookServices")).isSelected();  
		
		campusAccountsScreen = accountCreatingScreen.fillInDetailsForAccountAndSave(domain, institution, tegrityInstallationId,
				tegritySharedKey, emailClient.getLogin(), instructorSeting, studentSeting, readerType ,Country ,State ,School);

		List<Letter> receivedLetters = emailClient.WaitForEmailWithBodyContentArrival(expectedLoginPageAddress);
		Letter targetLetter = receivedLetters.get(0);
		InstanceCredentials instanceCredentials = WelcomeEmail.extractCredentials(targetLetter);
		
//      if the value from command line not null use it as pageAddressForLogin
		String instanceUrlFromCli = MhCampusCliParams.getInstance().getInstanceUrl();
		if (instanceUrlFromCli != null){
			instanceCredentials.pageAddressForLogin = instanceUrlFromCli;
		}
//		Otherwise, pageAddressForLogin equals to pageAddressFromEmail
		else{
			instanceCredentials.pageAddressForLogin = instanceCredentials.pageAddressFromEmail;
		}
		
		instanceCredentials.institution = institution;

		Logger.info("Extracted e-mail credentials for current instance:");
		Logger.info("MH Campus Instance login page address: " + instanceCredentials.pageAddressFromEmail);
		Logger.info("MH Campus Instance login: " + instanceCredentials.username);
		Logger.info("MH Campus Instance password: " + instanceCredentials.password);
		Logger.info("Customer number: " + instanceCredentials.customerNumber);
		Logger.info("Shared secret: " + instanceCredentials.sharedSecret);
		
		Logger.info("Is checkbox 'Show Active Course' selected: " + checkBoxShowActiveCourse);
		Logger.info("Is checkbox 'Gradebook Services' selected: " + enableGradebookServices);	

		Logger.info("Delete email by customer number");
		emailClient.deleteEmailByBodyContent(instanceCredentials.customerNumber);

		return instanceCredentials;

	}

	public void editSettingsInMhCampusInstance(String customerNumber, PageAppearanceSettings setting) throws Exception {

		Logger.info("Edit new MH Campus Instance...");
		try{
			clientServiceScreen = browser.openScreen(tegrityLoginUrl, TegrityLoginScreen.class);
			campusAccountsScreen = clientServiceScreen.loginToTegrityClientServices(tegrityServiceName, tegrityLogin, tegrityPassword);
			accountCreatingScreen = campusAccountsScreen.enterEditInstance(customerNumber);
			campusAccountsScreen = accountCreatingScreen.ChangeSettingsForAccountAndSave(setting);
		}catch(Exception e){
			Logger.info("Failed Edit new MH Campus Instance. Try again...");
			browser.pause(60000);
			clientServiceScreen = browser.openScreen(tegrityLoginUrl, TegrityLoginScreen.class);
			campusAccountsScreen = clientServiceScreen.loginToTegrityClientServices(tegrityServiceName, tegrityLogin, tegrityPassword);
			accountCreatingScreen = campusAccountsScreen.enterEditInstance(customerNumber);
			campusAccountsScreen = accountCreatingScreen.ChangeSettingsForAccountAndSave(setting);
		}
	}
	
	public void editSettingsInMhCampusInstance(String customerNumber, boolean isGradebookServicesEnable) throws Exception {

		Logger.info("Edit new MH Campus Instance...");
		try{
			clientServiceScreen = browser.openScreen(tegrityLoginUrl, TegrityLoginScreen.class);
			campusAccountsScreen = clientServiceScreen.loginToTegrityClientServices(tegrityServiceName, tegrityLogin, tegrityPassword);
		}catch(Exception e){
			Logger.info("Failed Login to Tegrity Service. Try again...");
			browser.pause(60000);
			clientServiceScreen = browser.openScreen(tegrityLoginUrl, TegrityLoginScreen.class);
			campusAccountsScreen = clientServiceScreen.loginToTegrityClientServices(tegrityServiceName, tegrityLogin, tegrityPassword);
		}
		accountCreatingScreen = campusAccountsScreen.enterEditInstance(customerNumber);
		Element gradebookServices = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_CheckBoxGradebookServices"));
		Logger.info("Edit Gradebook Services checkbox...");
		accountCreatingScreen.changeSettingsForAccountAndSave(isGradebookServicesEnable, gradebookServices);
		enableGradebookServices = isGradebookServicesEnable;
		browser.pause(60000*5);
	}
	
	public void editSettingsInMhCampusInstanceGatewayPreviewMode(String customerNumber, boolean GatewayPreviewMode) throws Exception {

		Logger.info("Edit new MH Campus Instance...");
		try{
			clientServiceScreen = browser.openScreen(tegrityLoginUrl, TegrityLoginScreen.class);
			campusAccountsScreen = clientServiceScreen.loginToTegrityClientServices(tegrityServiceName, tegrityLogin, tegrityPassword);
		}catch(Exception e){
			Logger.info("Failed Login to Tegrity Service. Try again...");
			browser.pause(60000);
			clientServiceScreen = browser.openScreen(tegrityLoginUrl, TegrityLoginScreen.class);
			campusAccountsScreen = clientServiceScreen.loginToTegrityClientServices(tegrityServiceName, tegrityLogin, tegrityPassword);
		}
		accountCreatingScreen = campusAccountsScreen.enterEditInstance(customerNumber);
		if(GatewayPreviewMode)
		{
			Logger.info("Try To check Gateway Preview Mode");
		}
		else
		{
			Logger.info("Try To Uncheck Gateway Preview Mode");
		}
		accountCreatingScreen.changeGatewayPreviewModeForAccountAndSave(GatewayPreviewMode);
		
	}
	
	public void editAutoParisAccountsInMhCampusInstance(String customerNumber, boolean isAutoParisAccountsEnable) throws Exception {

		Logger.info("Edit new MH Campus Instance...");
		try{
			clientServiceScreen = browser.openScreen(tegrityLoginUrl, TegrityLoginScreen.class);
			campusAccountsScreen = clientServiceScreen.loginToTegrityClientServices(tegrityServiceName, tegrityLogin, tegrityPassword);
		}catch(Exception e){
			Logger.info("Failed Login to Tegrity Service. Try again...");
			browser.pause(60000);
			clientServiceScreen = browser.openScreen(tegrityLoginUrl, TegrityLoginScreen.class);
			campusAccountsScreen = clientServiceScreen.loginToTegrityClientServices(tegrityServiceName, tegrityLogin, tegrityPassword);
		}
		accountCreatingScreen = campusAccountsScreen.enterEditInstance(customerNumber);
		Element autoParisAccounts = browser.waitForElement(By.id("ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_ParisAccountCreation1_CheckBoxAutoCreate"));
		Logger.info("Edit Auto Paris Accounts checkbox...");
		accountCreatingScreen.changeSettingsForAccountAndSave(isAutoParisAccountsEnable, autoParisAccounts);
		browser.pause(60000);
	}

	public void deleteMhCampusInstance(String customerNumber) throws Exception {
		instanceUtils.deleteInstance(customerNumber);
	}

	public InstanceCredentials createNewMhCampusInstance() throws Exception {
		return createNewMhCampusInstance(PageAppearanceSettings.Automatic, MhCampusReaderType.DEFAULT);
	}
	//-----------------------------------
	public InstanceCredentials createNewMhCampusInstanceWithCentralOrg() throws Exception {
		return createNewMhCampusInstanceWithCentralOrg(PageAppearanceSettings.Automatic, MhCampusReaderType.DEFAULT);
	}

	public InstanceCredentials createNewMhCampusInstance(MhCampusReaderType readerType) throws Exception {
		return createNewMhCampusInstance(PageAppearanceSettings.Automatic, readerType);
	}

	public InstanceCredentials createNewMhCampusInstance(PageAppearanceSettings settings) throws Exception {
		return createNewMhCampusInstance(settings, MhCampusReaderType.COURSE_SMART);
	}

	public InstanceCredentials useExistingMhCampusInstance(int numOfInstance) throws Exception {
		
		InstanceCredentials instanceCredentials = new InstanceCredentials();
		if(numOfInstance == 1){
			instanceCredentials.institution = mhcampusInstitution1;
			instanceCredentials.pageAddressForLogin = mhcampusLoginUrl1;
			instanceCredentials.pageAddressFromEmail = mhcampusLoginUrl1;
			instanceCredentials.username = mhcampusUsername1;
			instanceCredentials.password = mhcampusPassword1;
			instanceCredentials.customerNumber = customerNumber1;
			instanceCredentials.sharedSecret = sharedSecret1;
		}else if(numOfInstance == 2){
			instanceCredentials.institution = mhcampusInstitution2;
			instanceCredentials.pageAddressForLogin = mhcampusLoginUrl2;
			instanceCredentials.pageAddressFromEmail = mhcampusLoginUrl2;
			instanceCredentials.username = mhcampusUsername2;
			instanceCredentials.password = mhcampusPassword2;
			instanceCredentials.customerNumber = customerNumber2;
			instanceCredentials.sharedSecret = sharedSecret2;
		}else if(numOfInstance == 3){
			instanceCredentials.institution = mhcampusInstitution3;
			instanceCredentials.pageAddressForLogin = mhcampusLoginUrl3;
			instanceCredentials.pageAddressFromEmail = mhcampusLoginUrl3;
			instanceCredentials.username = mhcampusUsername3;
			instanceCredentials.password = mhcampusPassword3;
			instanceCredentials.customerNumber = customerNumber3;
			instanceCredentials.sharedSecret = sharedSecret3;
		}else if(numOfInstance == 4){
			instanceCredentials.institution = mhcampusInstitution4;
			instanceCredentials.pageAddressForLogin = mhcampusLoginUrl4;
			instanceCredentials.pageAddressFromEmail = mhcampusLoginUrl4;
			instanceCredentials.username = mhcampusUsername4;
			instanceCredentials.password = mhcampusPassword4;
			instanceCredentials.customerNumber = customerNumber4;
			instanceCredentials.sharedSecret = sharedSecret4;
		}else if(numOfInstance == 5){
			instanceCredentials.institution = mhcampusInstitutionDeep;
			instanceCredentials.pageAddressForLogin = mhcampusLoginUrlDeep;
			instanceCredentials.pageAddressFromEmail = mhcampusLoginUrlDeep;
			instanceCredentials.username = mhcampusUsernameDeep;
			instanceCredentials.password = mhcampusPasswordDeep;
			instanceCredentials.customerNumber = customerNumberDeep;
			instanceCredentials.sharedSecret = sharedSecretDeep;
		}else {
			Logger.info("Error! Incorrect number of MH Campus instance...");
			return null;
		}
			
		checkBoxShowActiveCourse = true;
		enableGradebookServices = true;
		
		Logger.info("Credentials for current instance:");
		Logger.info("MH Campus Instance login page address: " + instanceCredentials.pageAddressFromEmail);
		Logger.info("MH Campus Instance login: " + instanceCredentials.username);
		Logger.info("MH Campus Instance password: " + instanceCredentials.password);
		Logger.info("Customer number: " + instanceCredentials.customerNumber);
		Logger.info("Shared secret: " + instanceCredentials.sharedSecret);
		
		Logger.info("Is checkbox 'Show Active Course' selected: " + checkBoxShowActiveCourse);
		Logger.info("Is checkbox 'Gradebook Services' selected: " + enableGradebookServices);		
		
		return instanceCredentials;
	}
	
}
