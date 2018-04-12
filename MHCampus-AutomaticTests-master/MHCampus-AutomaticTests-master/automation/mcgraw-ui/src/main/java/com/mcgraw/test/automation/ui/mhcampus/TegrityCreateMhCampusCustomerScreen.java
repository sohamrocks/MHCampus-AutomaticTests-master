package com.mcgraw.test.automation.ui.mhcampus;

import org.openqa.selenium.By;
import org.openqa.selenium.support.How;


import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Select;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id='ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_BreadCrumb1_LabelCurrent'][contains(text(),'MHCampus customer')]")))
public class TegrityCreateMhCampusCustomerScreen extends TegrityCreateMhCampusCustomerScreenBase {

	private static final String BASE_SETTING_LOCATOR_PATTERN = "//span[.='%s']/ancestor::div[1]/following-sibling::div";

	private static final String START_OF_ID = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_";

	private static final String PROPERTIES_LAUNCH_EBOOK = "tenant_key=da8b8298-e57c-434b-9637-e680a3485050;secret=655b796f3e317d7c344932497157645c";
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "TextBoxDomain2"))
	Input userDomain;
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "TextBoxInstitution2"))
	Input userInstitution;
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "LinkButtonAddNewInstitution2"))
	Element addNewInstitution;
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "SchoolMHInfo_TextBoxInstallationId"))
	Input installationId;
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "SchoolMHInfo_TextBoxSharedKey"))
	Input sharedKey;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "MapCentralOrgIdChooser_RadComboBoxCountry_Input"))
	Element CentralOrgCountry;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "MapCentralOrgIdChooser_RadComboBoxStates_Input"))
	Element CentralOrgState;
	
	@DefinedLocators(@DefinedLocator(how = How.CLASS_NAME, using = "rcbHovered"))
	Element ElementHovered;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "MapCentralOrgIdChooser_TextBoxSchools"))
	Input CentralOrgSchool;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "MapCentralOrgIdChooser_TextBoxSchoolsAutoCompleteExtender_completionListElem"))
	Element CentralOrgSchoolVisible;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "cbGatewayPreviewMode"))
	Element GateWayPreviewModeRedioButton;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "TextBoxEmailContact"))
	Input contactEmail;
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "TextBoxEmailOwner"))
	Input ownerEmail;
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "ButtonCreateCustomer"))
	Element saveButton;
	@DefinedLocators(@DefinedLocator(how = How.ID, using = START_OF_ID
			+ "DropDownListEMHDigitalEnv']/option[3]"))
	Element QaStaging;
	

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_TextBookSearch_DropDownListInstructorSettings"))
	Select textbookSearchInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_TextBookSearch_DropDownListStudentSettings"))
	Select textbookSearchStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_EducationalMaterialSearch_DropDownListInstructorSettings"))
	Select materialSearchInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_EducationalMaterialSearch_DropDownListStudentSettings"))
	Select materialSearchStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LaunchOLC_DropDownListInstructorSettings"))
	Select launchOlcInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LaunchOLC_DropDownListStudentSettings"))
	Select launchOlcStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LaunchEbook_DropDownListInstructorSettings"))
	Select launchEbookInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LaunchEbook_DropDownListStudentSettings"))
	Select launchEbookStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Customize_DropDownListInstructorSettings"))
	Select customizeButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Customize_DropDownListStudentSettings"))
	Select customizeButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Create_DropDownListInstructorSettings"))
	Select createButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Create_DropDownListStudentSettings"))
	Select createButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Connect_DropDownListInstructorSettings"))
	Select connectButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Connect_DropDownListStudentSettings"))
	Select connectButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_ConnectMath_DropDownListInstructorSettings"))
	Select connectMathButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_ConnectMath_DropDownListStudentSettings"))
	Select connectMathButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Aleks_DropDownListInstructorSettings"))
	Select aleksButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Aleks_DropDownListStudentSettings"))
	Select aleksButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Tegrity_DropDownListInstructorSettings"))
	Select tegrityButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Tegrity_DropDownListStudentSettings"))
	Select tegrityButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Proctoring_DropDownListInstructorSettings"))
	Select proctoringButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Proctoring_DropDownListStudentSettings"))
	Select proctoringButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Simnet_DropDownListInstructorSettings"))
	Select simnetButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_Simnet_DropDownListStudentSettings"))
	Select simnetButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_GDP_DropDownListInstructorSettings"))
	Select gdpButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_GDP_DropDownListStudentSettings"))
	Select gdpButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_AdoptedServices_DropDownListInstructorSettings"))
	Select adoptedServicesAreaInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_AdoptedServices_DropDownListStudentSettings"))
	Select adoptedServicesAreaStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_RelatedTitles_DropDownListInstructorSettings"))
	Select relatedTitlesAreaInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_RelatedTitles_DropDownListStudentSettings"))
	Select relatedTitlesAreaStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_NonAdoptedServices_DropDownListInstructorSettings"))
	Select nonAdoptedServicesAreaInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_NonAdoptedServices_DropDownListStudentSettings"))
	Select nonAdoptedServicesAreaStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LearnSmart_DropDownListInstructorSettings"))
	Select learnSmartButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LearnSmart_DropDownListStudentSettings"))
	Select learnSmartButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_ACTIVESim_DropDownListInstructorSettings"))
	Select activeSimButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_ACTIVESim_DropDownListStudentSettings"))
	Select activeSimButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LearningTools_DropDownListInstructorSettings"))
	Select learningToolsButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LearningTools_DropDownListStudentSettings"))
	Select learningToolsButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LearnSmartSmartBook_DropDownListInstructorSettings"))
	Select learnSmartSmartBookButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LearnSmartSmartBook_DropDownListStudentSettings"))
	Select learnSmartSmartBookButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LearnSmartLabs_DropDownListInstructorSettings"))
	Select learnSmartLabsButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LearnSmartLabs_DropDownListStudentSettings"))
	Select learnSmartLabsButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LearnSmartAchieve_DropDownListInstructorSettings"))
	Select learnSmartAchieveButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LearnSmartAchieve_DropDownListStudentSettings"))
	Select learnSmartAchieveButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LearnSmartPrep_DropDownListInstructorSettings"))
	Select learnSmartPrepButtonInstructor;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_LearnSmartPrep_DropDownListStudentSettings"))
	Select learnSmartPrepButtonStudent;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_DropDownListEbookReaders"))
	Select eBookReaderType;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_DropDownListEMHDigitalEnv"))
	Element DigitalEnv;
	
	@DefinedLocators(@DefinedLocator(how = How.ID, using = "ctl00_ContentPlaceHolder1_ClientServicesMain1_NewCustomer1_cbLaunchToTestPage"))
	Element ltiLaunchFromToolConsumerCheckBox;
	
	
	public void setExtendedPropertiesForSetting(String settingName, String value) {
		Element extendedPropertiesInput = browser.waitForElement(By
				.xpath(String.format(BASE_SETTING_LOCATOR_PATTERN + "/input",
						settingName)));
		extendedPropertiesInput.sendKeys(value);
	}

	public enum PageAppearanceSettings {
		Automatic, Enabled, Disabled
	};

	public TegrityCreateMhCampusCustomerScreen(Browser browser) {
		super(browser);
	}

	public void typeDomain(String domain) {
		userDomain.clearAndTypeValue(domain);
	}

	public void typeInstitution(String institution) {
		userInstitution.clearAndTypeValue(institution);
	}

	public TegrityCreateInstitutionScreen gotoNewInstitutionPage() {
		addNewInstitution.click();
		return browser.waitForPage(TegrityCreateInstitutionScreen.class);
	}
	public void SetDigitalEnvToQA() {
		Select dropdown = new Select(DigitalEnv);
		dropdown.selectOptionByName("QaStaging");
	}

	public void fillInstallationId(String id) {
		installationId.clearAndTypeValue(id);
	}

	public void fillSharedKey(String key) {
		sharedKey.clearAndTypeValue(key);
	}

	public void typeContactEmail(String email) {
		contactEmail.clearAndTypeValue(email);
	}

	public void typeOwnerEmail(String email) {
		ownerEmail.clearAndTypeValue(email);
	}
	//Start-----------------Central Org-------------------------
	public void typeCentralOrgCountry(String country) {
		try{
		Logger.info("Typing Country"+" "+country );
		CentralOrgCountry.sendKeys(country);
		browser.waitForElementPresent(ElementHovered);
		ElementHovered.click();
		browser.pause(3000);
		}catch(Exception e)
			{
				Logger.info("Can't find "+" "+country+" " +"Because"+"\n"+ e  );
			}
		
	}
	
	public void typeCentralOrgState(String state) {
		try{
		Logger.info("Typing State"+" "+state );
		CentralOrgState.click();
		browser.pause(1000);
		CentralOrgState.sendKeys(state);
		browser.waitForElementPresent(ElementHovered);
		ElementHovered.click();
		browser.pause(3000);
		}catch(Exception e)
		{
			Logger.info("Can't find "+" "+state+" " +"Because"+"\n"+ e  );
		}
		
	}
	
	public void typeCentralOrgSchool(String school) {
		try{
		Logger.info("Typing School"+" "+school );
		CentralOrgSchool.clearAndTypeValue(school);
		browser.pause(3000);
		browser.waitForElementPresent(CentralOrgSchoolVisible);
		CentralOrgSchoolVisible.click();
		}catch(Exception e)
		{
			Logger.info("Can't find "+" "+school+" " +"Because"+"\n"+ e  );
		}
	}
	//End-----------------Central Org----------------------------
	public TegrityAccountsScreen saveAccount() {
		saveButton.click();
		return browser.waitForPage(TegrityAccountsScreen.class);
	}
	
	//Start----------------Central Org--------------------------------------------------------------------------------------------
	public TegrityCreateMhCampusCustomerScreen fillInDetailsForAccount(
			String domain, String institution, String installationId,
			String sharedSecret, String email, MhCampusReaderType readerType,String Country ,String State ,String School) {
		typeDomain(domain);
		typeInstitution(institution);

		TegrityCreateInstitutionScreen institutionCreatingScreen = gotoNewInstitutionPage();
		try{
			institutionCreatingScreen.typeFullNameOfInstitution(institution);
			institutionCreatingScreen.saveChanges();
		}catch (Exception e){
			Logger.info("Can't save institution name, try again:");
			Logger.info("Saving full institution name...");
			institutionCreatingScreen.typeFullNameOfInstitution(institution);
			institutionCreatingScreen.saveChanges();
		}
		SetDigitalEnvToQA();
		fillInstallationId(installationId);
		fillSharedKey(sharedSecret);
		browser.pause(1000);
		typeContactEmail(email);
		typeOwnerEmail(email);
		typeCentralOrgCountry(Country);
		typeCentralOrgState(State);
		typeCentralOrgSchool(School);
		//End---------------Central Org-------------------
		changeEbookReaderType(readerType);
		if(readerType.getValue().equals("VitalSource LTI"))
			typeExtendedPropertiesForLaunchEbook(PROPERTIES_LAUNCH_EBOOK); 
		
		return this;
	}

	public TegrityAccountsScreen fillInDetailsForAccountAndSave(String domain,
			String institution, String installationId, String sharedSecret,
			String email, PageAppearanceSettings generalSettings,
			MhCampusReaderType readerType,String Country ,String State ,String School) {
		fillInDetailsForAccountAndSave(domain, institution, installationId,
				sharedSecret, email, generalSettings, generalSettings,
				readerType,Country,State,School);
		return saveAccount();
	}

	public TegrityAccountsScreen fillInDetailsForAccountAndSave(String domain,
			String institution, String installationId, String sharedSecret,
			String email, PageAppearanceSettings instructorSettings,
			PageAppearanceSettings studentSettings,
			MhCampusReaderType readerType,String Country ,String State ,String School) {
		fillInDetailsForAccount(domain, institution, installationId,
				sharedSecret, email, readerType,Country,State,School);
		
		chooseSettingsForInstructor(instructorSettings.name());
		chooseSettingsForStudent(studentSettings.name());
		// avoiding of bug when some books can't be selected if settings are set
		// to Enabled
		if (instructorSettings.equals(PageAppearanceSettings.Enabled)) {
			setExtendedPropertiesForSetting("LearningTools button",
					"anykey=anyvalue");
		}
		
		return saveAccount();
	}
	
	public TegrityCreateMhCampusCustomerScreen fillInDetailsForAccount(
			String domain, String institution, String installationId,
			String sharedSecret, String email, MhCampusReaderType readerType) {
		typeDomain(domain);
		typeInstitution(institution);

		TegrityCreateInstitutionScreen institutionCreatingScreen = gotoNewInstitutionPage();
		try{
			institutionCreatingScreen.typeFullNameOfInstitution(institution);
			institutionCreatingScreen.saveChanges();
		}catch (Exception e){
			Logger.info("Can't save institution name, try again:");
			Logger.info("Saving full institution name...");
			institutionCreatingScreen.typeFullNameOfInstitution(institution);
			institutionCreatingScreen.saveChanges();
		}

		fillInstallationId(installationId);
		fillSharedKey(sharedSecret);
		typeContactEmail(email);
		typeOwnerEmail(email);
		changeEbookReaderType(readerType);
		if(readerType.getValue().equals("VitalSource LTI"))
			typeExtendedPropertiesForLaunchEbook(PROPERTIES_LAUNCH_EBOOK); 
		
		return this;
	}

	public TegrityAccountsScreen fillInDetailsForAccountAndSave(String domain,
			String institution, String installationId, String sharedSecret,
			String email, PageAppearanceSettings generalSettings,
			MhCampusReaderType readerType) {
		fillInDetailsForAccountAndSave(domain, institution, installationId,
				sharedSecret, email, generalSettings, generalSettings,
				readerType);
		return saveAccount();
	}

	public TegrityAccountsScreen fillInDetailsForAccountAndSave(String domain,
			String institution, String installationId, String sharedSecret,
			String email, PageAppearanceSettings instructorSettings,
			PageAppearanceSettings studentSettings,
			MhCampusReaderType readerType) {
		fillInDetailsForAccount(domain, institution, installationId,
				sharedSecret, email, readerType);
		
		chooseSettingsForInstructor(instructorSettings.name());
		chooseSettingsForStudent(studentSettings.name());
		// avoiding of bug when some books can't be selected if settings are set
		// to Enabled
		if (instructorSettings.equals(PageAppearanceSettings.Enabled)) {
			setExtendedPropertiesForSetting("LearningTools button",
					"anykey=anyvalue");
		}
		
		return saveAccount();
	}

	public TegrityCreateMhCampusCustomerScreen ChangeSettingsForAccount(
			PageAppearanceSettings settings) {
		
		chooseSettingsForInstructor(settings.name());
		chooseSettingsForStudent(settings.name());
		// avoiding of bug when some books can't be selected if settings are set
		// to Enabled
		if (settings.equals(PageAppearanceSettings.Enabled)) {
			setExtendedPropertiesForSetting("LearningTools button",
					"anykey=anyvalue");
		}
		return this;
	}

	public TegrityAccountsScreen ChangeSettingsForAccountAndSave(
			PageAppearanceSettings settings) {
		ChangeSettingsForAccount(settings);
		return saveAccount();
	}

	public TegrityAccountsScreen changeSettingsForAccountAndSave(Boolean enableGradebookServices, Element element) {
		if(enableGradebookServices && !(element.isSelected())){
			element.click();
			Logger.info("The valeu of element was MARKED...");
			browser.makeScreenshot();
		}
		if(!enableGradebookServices && element.isSelected()){
			element.click();
			Logger.info("The valeu of element was UNMARKED...");
			browser.makeScreenshot();
		}
		return saveAccount();
	}
	public TegrityAccountsScreen changeGatewayPreviewModeForAccountAndSave(Boolean GatewayPreviewMode) {
		if((GatewayPreviewMode == true) && (!GateWayPreviewModeRedioButton.isSelected()))
		{
			GateWayPreviewModeRedioButton.click();
			return saveAccount();
		}
		else if ((GatewayPreviewMode == true) && (GateWayPreviewModeRedioButton.isSelected()))
		{
			Logger.info("GateWayPreviewModeRedioButton is Allredy Selected");
			return saveAccount();
		}
		if((GatewayPreviewMode == false) && (GateWayPreviewModeRedioButton.isSelected()))
		{
			GateWayPreviewModeRedioButton.click();
			return saveAccount();
		}
		else if ((GatewayPreviewMode == false) && (!GateWayPreviewModeRedioButton.isSelected()))
		{
			Logger.info("GateWayPreviewModeRedioButton is Not Selected");
			return saveAccount();
		}
		return saveAccount();
		
	}
	
	private void changeEbookReaderType(MhCampusReaderType readerType) {
		if (!MhCampusReaderType.DEFAULT.equals(readerType)) {
			Logger.info("Change Reader Type to " + readerType.getValue());
			eBookReaderType.selectOptionByName(readerType.getValue());
		}
	}
	
	private void typeExtendedPropertiesForLaunchEbook(String properties) {
		Element extendPropreties = browser.waitForElement(By.id(START_OF_ID + "LaunchEbook_textboxExtededProperties"));
		extendPropreties.sendKeys(properties);
	}

	private void chooseSettingsForInstructor(String settings) {
		Logger.info("Select all settings for instructor to " + settings);
		textbookSearchInstructor.selectOptionByName(settings);
		materialSearchInstructor.selectOptionByName(settings);
		launchOlcInstructor.selectOptionByName(settings);
		launchEbookInstructor.selectOptionByName(settings);
		customizeButtonInstructor.selectOptionByName(settings);
		createButtonInstructor.selectOptionByName(settings);
		connectButtonInstructor.selectOptionByName(settings);
		connectMathButtonInstructor.selectOptionByName(settings);
		aleksButtonInstructor.selectOptionByName(settings);
		tegrityButtonInstructor.selectOptionByName(settings);
		proctoringButtonInstructor.selectOptionByName(settings);
		simnetButtonInstructor.selectOptionByName(settings);
		gdpButtonInstructor.selectOptionByName(settings);
		adoptedServicesAreaInstructor.selectOptionByName(settings);
		relatedTitlesAreaInstructor.selectOptionByName(settings);
		nonAdoptedServicesAreaInstructor.selectOptionByName(settings);
		learnSmartButtonInstructor.selectOptionByName(settings);
		activeSimButtonInstructor.selectOptionByName(settings);
		learningToolsButtonInstructor.selectOptionByName(settings);
		learnSmartSmartBookButtonInstructor.selectOptionByName(settings);
		learnSmartLabsButtonInstructor.selectOptionByName(settings);
		learnSmartAchieveButtonInstructor.selectOptionByName(settings);
		learnSmartPrepButtonInstructor.selectOptionByName(settings);
	}

	private void chooseSettingsForStudent(String settings) {
		Logger.info("Change all settings for student to " + settings);
		textbookSearchStudent.selectOptionByName(settings);
		materialSearchStudent.selectOptionByName(settings);
		launchOlcStudent.selectOptionByName(settings);
		launchEbookStudent.selectOptionByName(settings);
		customizeButtonStudent.selectOptionByName(settings);
		createButtonStudent.selectOptionByName(settings);
		connectButtonStudent.selectOptionByName(settings);
		connectMathButtonStudent.selectOptionByName(settings);
		aleksButtonStudent.selectOptionByName(settings);
		tegrityButtonStudent.selectOptionByName(settings);
		proctoringButtonStudent.selectOptionByName(settings);
		simnetButtonStudent.selectOptionByName(settings);
		gdpButtonStudent.selectOptionByName(settings);
		adoptedServicesAreaStudent.selectOptionByName(settings);
		relatedTitlesAreaStudent.selectOptionByName(settings);
		nonAdoptedServicesAreaStudent.selectOptionByName(settings);
		learnSmartButtonStudent.selectOptionByName(settings);
		activeSimButtonStudent.selectOptionByName(settings);
		learningToolsButtonStudent.selectOptionByName(settings);
		learnSmartSmartBookButtonStudent.selectOptionByName(settings);
		learnSmartLabsButtonStudent.selectOptionByName(settings);
		learnSmartAchieveButtonStudent.selectOptionByName(settings);
		learnSmartPrepButtonStudent.selectOptionByName(settings);
	}

	public void setTextbookSearch(PageAppearanceSettings setting) {
		textbookSearchInstructor.selectOptionByName(setting.name());
		textbookSearchStudent.selectOptionByName(setting.name());
	}
}
