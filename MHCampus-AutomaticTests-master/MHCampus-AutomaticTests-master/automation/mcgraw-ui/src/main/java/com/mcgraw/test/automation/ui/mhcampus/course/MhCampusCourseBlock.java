package com.mcgraw.test.automation.ui.mhcampus.course;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Input;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.mhcampus.course.activesim.MhCampusActivSimScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.aleks.MhCampusALEKSReadyToUseScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.aleks.MhCampusALEKSScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.MhCampusConnectScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.MhCampusConnectStudentRegistrationScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connectmath.MhCampusConnectMathScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.gdp.MhCampusGDPScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.learnsmart.MhCampusLearnSmartScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.learnsmart.MhCampusLearnSmartStudentRegistrationScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.olc.MhCampusOlcEditionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetPairingPortalForStudent;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.smartbook.MhCampusSmartBookScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.smartbook.MhCampusSmartBookStudentRegistrationScreen;
import com.mcgraw.test.automation.ui.mhcampus.coursesmart.CourseSmartProfileScreen;
import com.mcgraw.test.automation.ui.mhcampus.createprovider.CreateProviderBookStore;
import com.mcgraw.test.automation.ui.mhcampus.createprovider.CreateProviderScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@class='toggle-content']")))
public class MhCampusCourseBlock extends Screen {

	/**
	 * All elements inside of course block should use this variable as Xpath
	 * prefix
	 */
	private String baseXpathLocator;

	@DefinedLocators(@DefinedLocator(using = "//span[contains(.,'No, I am new to')]"))
	public Element iAmNewToButton;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "email-field"))
	public Input emailAddressInput;

	//@DefinedLocators(@DefinedLocator(how = How.CLASS_NAME, using = "large-button"))
	@DefinedLocators(@DefinedLocator(how = How.XPATH, using = ".//*[@value='Continue']"))
	public Input eBookContinueBtn;

	private String bookTitleLocator = "//h3[@class='book-title']";
	private String bookAuthorLocator = "//h4[@class='author']";
	private String bookPublisherLocator = "//h4[@class='publisher']";
	private String isbn10Locator = "//*[contains(@id, '_LabelISBN10')]";
	private String isbn13Locator = "//*[contains(@id, '_LabelISBN13')]";
	private String notYourBookLinkLocator = "//a[@class='book-remove']";
	private String textBookSearchInputLocator = "//input[@class='search-input']";
	private String findNowButtonLocator = "//*[@class='search-button lightbox cboxElement']";
	private String adoptedServicesAreaLocator = "//div[@class='addl-content-bottom']";
	private String nonAdoptedServicesAreaLocator = "//div[@class='container relevant-materials']//div[contains(.,'Additional Content and Services')]";
	private String viewOnlineResourcesLinkLocator = "//a[contains(text(),'View online resources')]";
	private String relatedTitlesAreaLocator = "//div[@class='container relevant-materials']/div[@class='service-title' and contains(.,' Educational Materials')]";
	private String frameLocator = "//iframe[@class='cboxIframe']";
	private String courseName;
	private String buttonXpathPattern = "//div[@class='featured']//a/img[@alt='%s']";
	
	public MhCampusCourseBlock(Browser browser, String courseName) {
		super(browser);
		this.courseName = courseName;
		baseXpathLocator = "//a[contains(text(),'" + courseName.toUpperCase()
				+ "')]/ancestor::div[1]";
	}

	public String getBookTitle() {
		return browser.findElement(
				By.xpath(baseXpathLocator + bookTitleLocator)).getText();
	}

	public String getBookAuthor() {
		return browser.findElement(
				By.xpath(baseXpathLocator + bookAuthorLocator)).getText();
	}

	public String getBookPublisher() {
		return browser.findElement(
				By.xpath(baseXpathLocator + bookPublisherLocator)).getText();
	}

	public String getBookIsbn10() {
		return browser.findElement(By.xpath(baseXpathLocator + isbn10Locator))
				.getText();
	}

	public String getBookIsbn13() {
		return browser.findElement(By.xpath(baseXpathLocator + isbn13Locator))
				.getText();
	}

	public void removeCurrentBook() {
		browser.waitForElement(
				By.xpath(baseXpathLocator + notYourBookLinkLocator)).click();
		browser.clickOkInAlertIfPresent();
		browser.waitForElement(By.xpath(baseXpathLocator
				+ textBookSearchInputLocator));
	}

	public int searchAndGetResultsCount() {
		browser.waitForElement(
				By.xpath(baseXpathLocator + findNowButtonLocator)).click();
		browser.switchTo()
				.frame(browser.waitForElement(By.xpath(frameLocator)));
		int resultsCount = browser.waitForElements(By.xpath(bookTitleLocator),
				30).size();
		Logger.info("Search returned " + resultsCount
				+ " results on the first page");
		browser.makeScreenshot();
		return resultsCount;
	}

	public void setFindTextBookField(String bookIsbn) {
		Input searchField = new Input(browser.waitForElement(By
				.xpath(baseXpathLocator + textBookSearchInputLocator)));
		searchField.typeValue(bookIsbn);
		browser.makeScreenshot();
	}

	public MhCampusCourseBlock selectBookForcourse(String bookIsbn) {
		Logger.info("Clicking 'Find Now' button...");
		try{
		selectselectBookForcourseHelper(bookIsbn);
		}catch (Exception e){
			Logger.info("Clicking for the first time on Find Now button failed, trying again");
			selectselectBookForcourseHelper(bookIsbn);
		}
		return browser.waitForPage(MhCampusCourseBlock.class, 15, courseName);
	}
	
	public boolean isNotYourBookOptionPresent() {
		Logger.info("Check if '(not your book?)' option is present for course: "
				+ courseName);
		browser.makeScreenshot();
		return browser.isElementPresent(By.xpath(baseXpathLocator
				+ notYourBookLinkLocator));
	}

	public boolean isAdoptedServicesAreaPresent() {
		Logger.info("Check if 'Adopted Services Area' is present for course: "
				+ courseName);
		browser.makeScreenshot();
		return browser.isElementPresent(By.xpath(baseXpathLocator
				+ adoptedServicesAreaLocator));
	}

	public boolean isNonAdoptedServicesAreaPresent() {
		Logger.info("Check if 'Non-adopted Services Area' is present for course: "
				+ courseName);
		browser.makeScreenshot();
		return browser.isElementPresent(By.xpath(baseXpathLocator
				+ nonAdoptedServicesAreaLocator));
	}

	public boolean isViewOnlineResourcesLinkPresent() {
		Logger.info("Check if 'View online resources' is present for course: "
				+ courseName);
		browser.makeScreenshot();
		return browser.isElementPresent(By.xpath(baseXpathLocator
				+ viewOnlineResourcesLinkLocator));
	}

	public boolean isCourseBlockElementPresent(CourseBlockElement button) {
		return browser.isElementPresent(By.xpath(baseXpathLocator
				+ String.format(buttonXpathPattern, button.getValue())));
	}

	public boolean isRelatedTitlesAreaPresent() {
		Logger.info("Check if 'Related titles area' is present for course: "
				+ courseName);
		browser.makeScreenshot();
		return browser.isElementPresent(By.xpath(baseXpathLocator
				+ relatedTitlesAreaLocator));
	}

	public boolean isTextbookSearchPresent() {
		Logger.info("Check if 'Text book search' is present for course: "
				+ courseName);
		browser.makeScreenshot();
		return browser.isElementPresent(By.xpath(baseXpathLocator
				+ textBookSearchInputLocator));
	}

	public MhCampusActivSimScreen clickActivSimButton() {
		Logger.info("Open ActiveSim");
		try{
			clickActivSimButtonHelper();
		}catch (Exception e){
			Logger.info("Clicking for the first time on ActivSim button failed, trying again");
			clickActivSimButtonHelper();
		}
		return browser.waitForPage(MhCampusActivSimScreen.class);
	}

	public void clickActivSimButtonAlreadyConfigured() {
		Logger.info("Open ActiveSim");
		browser.opensNewWindow(new Action() {

			@Override
			public void perform() {
				browser.pause(2000);
				browser.printHandles();
				Element activSimButton = browser.waitForElement(By
						.xpath(baseXpathLocator
								+ String.format(buttonXpathPattern,
										CourseBlockElement.ACTIV_SIM.getValue())));
				browser.pause(500);
				Logger.info("Clicking element: '" + activSimButton.getIdentifyingText() + "'");
				activSimButton.jsClick(browser);
				browser.pause(1000);
				browser.makeScreenshot();
				browser.printHandles();
				
				try {
					createNewAccount();
				} catch (Exception e) {
					Logger.info(e.toString());
					Logger.info("Creating a new account failed. Try to create new account again...");
					createNewAccount();
				}
			}
		});
		browser.pause(6000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
	}

	public MhCampusConnectScreen clickConnectButton() {
		Logger.info("Open Connect");
		try{
			clickConnectButtonHelper();
		}catch (Exception e){
			Logger.info("Clicking for the first time on Connect button failed, trying again");
			clickConnectButtonHelper();
		}
		return browser.waitForPage(MhCampusConnectScreen.class, 30);
	}

	public MhCampusConnectStudentRegistrationScreen clickConnectButtonAlreadyConfigured() {
		Logger.info("Open Connect");
		browser.opensNewWindow(new Action() {

			@Override
			public void perform() {
				browser.pause(2000);
				browser.printHandles();
				Element connectButton = browser.waitForElement(By							
						.xpath(baseXpathLocator
								+ String.format(buttonXpathPattern,
										CourseBlockElement.CONNECT.getValue())));
				Logger.info("Clicking element: '" + connectButton.getIdentifyingText() + "'");
				browser.pause(500);
				connectButton.jsClick(browser);
				browser.pause(1000);
				browser.makeScreenshot();
				browser.printHandles();
				
				createNewAccount();
			}
		});
		browser.pause(6000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
		return browser.waitForPage(MhCampusConnectStudentRegistrationScreen.class, 30);
	}

	public MhCampusConnectMathScreen clickConnectMathButton() {
		Logger.info("Open Connect Math");
		browser.opensNewWindow(new Action() {
			@Override
			public void perform() {
				Element connectMathButton = browser.waitForElement(By
						.xpath(baseXpathLocator
								+ String.format(buttonXpathPattern,
										CourseBlockElement.CONNECT_MATH
												.getValue())));
				connectMathButton.jsClick(browser);
			}
		});
		browser.pause(6000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
		return browser.waitForPage(MhCampusConnectMathScreen.class);
	}

	public MhCampusLearnSmartScreen clickLearnSmartButtonAsInstructor() {
		Logger.info("Open LearnSmart");
		clickLearnSmartButton();
		browser.pause(30000); 
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
		return browser.waitForPage(MhCampusLearnSmartScreen.class);
	}
	
	public MhCampusLearnSmartStudentRegistrationScreen clickLearnSmartButtonAsStudent() {
		Logger.info("Open LearnSmart Student registration");
		clickLearnSmartButton();
		browser.waitForAlert(10);; 
		browser.clickOkInAlertIfPresent();
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
		return browser.waitForPage(MhCampusLearnSmartStudentRegistrationScreen.class);
	}

	public MhCampusALEKSScreen clickAleksButton() {
		Logger.info("Open ALEKS");
		try{
			clickAleksButtonHelper();
		}catch (Exception e){
			Logger.info("Clicking for the first time on ALEKS button failed, trying again");
			clickAleksButtonHelper();
		}
		return browser.waitForPage(MhCampusALEKSScreen.class);
	}

	public MhCampusALEKSReadyToUseScreen clickAleksButtonThatAlreadyConfigured() {
		Logger.info("Open ALEKS");
		browser.opensNewWindow(new Action() {
			@Override
			public void perform() {
				Element aleksButton = browser.waitForElement(By
						.xpath(baseXpathLocator
								+ String.format(buttonXpathPattern,
										CourseBlockElement.ALEKS.getValue())));
				aleksButton.jsClick(browser);
			}
		});
		browser.pause(6000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
		return browser.waitForPage(MhCampusALEKSReadyToUseScreen.class);
	}

	public MhCampusSimNetScreen clickSimNetButton() {
		Logger.info("Open Simnet");
		try {
			clickSimNetButtonHelper();
		} catch (Exception e) {
			Logger.info("Clicking for the first time on SIMnet button failed, trying again");
			clickSimNetButtonHelper();
		}
		return browser.waitForPage(MhCampusSimNetScreen.class);
	}

	public MhCampusSimNetPairingPortalForStudent clickSimNetButtonAlreadyConfigured() {
		Logger.info("Open Simnet");
		browser.opensNewWindow(new Action() {
			@Override
			public void perform() {
				Element simNetButton = browser.waitForElement(By
						.xpath(baseXpathLocator
								+ String.format(buttonXpathPattern,
										CourseBlockElement.SIMNET.getValue())));
				simNetButton.jsClick(browser);
			}
		});
		browser.pause(6000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
		return browser.waitForPage(MhCampusSimNetPairingPortalForStudent.class);
	}

	public MhCampusGDPScreen clickGDPButton() {
		Logger.info("Open GDP");
		browser.opensNewWindow(new Action() {
			@Override
			public void perform() {
				Element simNetButton = browser.waitForElement(By
						.xpath(baseXpathLocator
								+ String.format(buttonXpathPattern,
										CourseBlockElement.GDP.getValue())));
				simNetButton.jsClick(browser);
			}
		});
		browser.pause(6000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
		return browser.waitForPage(MhCampusGDPScreen.class);
	}

	public MhCampusSmartBookScreen clickSmartBookButtonAsInstructor() {
		clickAdoptSmartBookWithAttemptsAndTimeOut(5, 30000);
		return browser.waitForPage(MhCampusSmartBookScreen.class);
	}

	public MhCampusSmartBookStudentRegistrationScreen clickSmartBookButtonAsStudent() {
		clickAdoptSmartBookWithAttemptsAndTimeOut(5, 30000);
		browser.makeScreenshot();
		return browser.waitForPage(MhCampusSmartBookStudentRegistrationScreen.class);
	}

	public MhCampusOlcEditionScreen clickViewOnlineResourcesLink() {
		Logger.info("Click view Online resources");
		try {
			MhCampusOlcEditionScreenHelper();
		} catch (Exception e) {
			Logger.info("Clicking for the first time on view Online resources button failed, trying again");
			MhCampusOlcEditionScreenHelper();
		}
		return browser.waitForPage(MhCampusOlcEditionScreen.class);
	}


	public CourseSmartProfileScreen clickLaunchEbookLinkAndFillEmail(
			String userEmail) {
		Logger.info("Click launch Ebook");
		if( !(userEmail.startsWith("instructor") || userEmail.startsWith("student")) )
			Logger.info("Incorrect email address: must start with 'instructor' or 'student'");
		
		Element launchEbookLink = browser
				.waitForElement(By.xpath(baseXpathLocator
						+ String.format(buttonXpathPattern,
								CourseBlockElement.LAUNCH_EBOOK.getValue())), 5);
		launchEbookLink.jsClick(browser);
		browser.pause(9000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
		browser.pause(6000);
		if(userEmail.startsWith("instructor"))
			clickContinue();
		fillEmailAddress(userEmail);
		try{
			fillFormForBookshelf(userEmail);
			//clickNext();  
		}catch (Exception e) {
			Logger.info("Failed to fill the form, try again...");
			browser.navigate().refresh();
			browser.pause(5000);
			fillEmailAddress(userEmail);
			fillFormForBookshelf(userEmail);
			//clickNext();  
		}
		
		return browser.waitForPage(CourseSmartProfileScreen.class);
	}
	
	public CreateProviderScreen clickLaunchEbookAsInstructor(
			String email) {
		clickLaunchEbook(email);
		try{
			acceptTheTerms();	
			//clickNext(); // AleksandrY currently is not used on UI
		}catch(Exception e){
			Logger.info("Can't accept the terms, try again...");
			browser.closeAllWindowsExceptFirst();
			browser.switchTo().defaultContent();
			clickLaunchEbook(email);
			acceptTheTerms();	
//			clickNext();  // AleksandrY currently is not used on UI
		}
		return browser.waitForPage(CreateProviderScreen.class);
	}

	public CreateProviderBookStore clickLaunchEbookAsStudent(String email) {
		Logger.info("Click launch Ebook");
		Element launchEbookLink = browser.waitForElement(By
				.xpath(baseXpathLocator
						+ String.format(buttonXpathPattern,
								CourseBlockElement.LAUNCH_EBOOK.getValue())));
		launchEbookLink.jsClick(browser);
		browser.pause(9000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
		return browser.waitForPage(CreateProviderBookStore.class);
	}

	public int getCountOfAdditionalRelevantMaterials() {
		List<WebElement> book = browser.findElements(By
				.xpath("//*[@class = 'book-search relevant-book']"));
		return book.size();
	}

	public boolean isBookPresentInBlock(String isbn) {
		return browser.isElementPresentWithWait(
				By.xpath(baseXpathLocator + "//span[contains(text(),'" + isbn
						+ "')]"), 10);
	}
	
	private void createNewAccount() {
		Logger.info("Create new account...");
		browser.makeScreenshot();
		String password = "user" + RandomStringUtils.randomNumeric(10);
		String email = password + "@gmail.com";
		ifUserDoesNotExistClickOnCreateNewAccountLink(email);
		fillTheForm(password);
	}

	private void ifUserDoesNotExistClickOnCreateNewAccountLink(String email) {
		Logger.info("Check if user does not exist in the system already...");
		browser.pause(2000);	//AlexandrY added to solve instability for server side 
		Element emailToCheck = browser.waitForElement(By.xpath("//*[@id='cboxLoadedContent']/div/div/div[1]/input[1]"), 10);
		browser.pause(1000);	//AlexandrY added to solve instability for server side
		emailToCheck.clear();
		emailToCheck.sendKeys(email);
		browser.makeScreenshot();
		Element findMyAccountButton = browser.waitForElement(By.xpath("//div[@class='corner floatR align checkAcctButton']"), 10);
		findMyAccountButton.click();
		browser.makeScreenshot();
		Logger.info("Click on 'createNewAccount' link...");
		Element createNewAccountLink = browser.waitForElement(By.linkText("create a new account"), 10);
		createNewAccountLink.click();
		browser.makeScreenshot();
		Element completeYourRegistrationLink = browser.waitForElement(By.xpath("//*[@id='cboxLoadedContent']/div/div/div[4]/span[3]/a"), 10);
		completeYourRegistrationLink.click();
	}

	private void fillTheForm(String password) {
		Logger.info("Fill the form for creating a new account...");
		String whatIsYourFavoriteMovie = "Test";
		Element passwordTextBox = browser.waitForElement(By.id("pw"));
		browser.pause(1000);// Added by Yuval
		passwordTextBox.sendKeys(password);
		browser.pause(1000);// Added by Yuval
		Element passwordConfirmTextBox = browser.waitForElement(By.id("pw2"));
		passwordConfirmTextBox.sendKeys(password);
		Element securityQuestion = browser.waitForElement(By.xpath("//*[@id='SecurityQuestions']/option[2]"));
		securityQuestion.click();
		Element securityAnswer = browser.waitForElement(By.name("securityanswer"));
		securityAnswer.sendKeys(whatIsYourFavoriteMovie);
		securityAnswer.sendKeys(Keys.TAB);

		Element acceptCheckBox = browser.waitForElement(By.xpath("//input[@class='chkAgreeTerms']"));
		acceptCheckBox.click();
		acceptCheckBox.sendKeys(Keys.END);
		browser.makeScreenshot();

		Element continueButton = browser.waitForElement(By.className("text16boldbluebutton"));
		Logger.info("Submit the form...");
		continueButton.click();
		browser.waitForAlert(10);
		browser.clickOkInAlertIfPresent();
	}
	
	private void selectselectBookForcourseHelper(String bookIsbn){
		browser.waitForElement(
				By.xpath(baseXpathLocator + findNowButtonLocator)).click();
		browser.pause(6000);
		browser.switchTo()
				.frame(browser.waitForElement(By.xpath(frameLocator)));
		browser.makeScreenshot();
		Element selectBook = browser.waitForElement(By.xpath("//img[@isbn='"
				+ bookIsbn + "']"));
		Logger.info("Clicking 'Select' button...");
		selectBook.click();
//		browser.pause(6000);				// commented by AlexandrY
		//browser.makeScreenshot();
		selectBook.waitForAbsence(15);		// modified by AlexandrY, old version is selectBook.waitForAbsence(15);
		browser.switchTo().defaultContent();
	}
	
	private void clickActivSimButtonHelper(){
		browser.opensNewWindow(new Action() {
			@Override
			public void perform() {
				browser.pause(2000);
				browser.printHandles();
				Element activSimButton = browser.waitForElement(By
						.xpath(baseXpathLocator
								+ String.format(buttonXpathPattern,
										CourseBlockElement.ACTIV_SIM.getValue())));	
				browser.pause(500);
				Logger.info("Clicking element: '" + activSimButton.getIdentifyingText() + "'");
				activSimButton.jsClick(browser);
				browser.pause(1000);
				browser.makeScreenshot();
				browser.printHandles();
				
				createNewAccount();
			}
		});
		browser.pause(6000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
	}
	
	private void clickConnectButtonHelper(){
		browser.opensNewWindow(new Action() {
			@Override
			public void perform() {
				browser.pause(2000);
				browser.printHandles();
				Element connectButton = browser.waitForElement(By
						.xpath(baseXpathLocator
								+ String.format(buttonXpathPattern,
										CourseBlockElement.CONNECT.getValue())));
				Logger.info("Clicking element: '" + connectButton.getIdentifyingText() + "'");
				browser.pause(500);
				connectButton.jsClick(browser);
				browser.pause(1000);
				browser.makeScreenshot();
				browser.printHandles();

				createNewAccount();
			}
		});
		browser.pause(6000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
	}

	private void clickLearnSmartButtonHelper(){
		browser.opensNewWindow(new Action() {
			@Override
			public void perform() {
				try{
					Element manageAccountButton = browser.findElement(By.id("buttonManageAccount"));
					Element learnSmartMathButton = browser.waitForElement(By
							.xpath(baseXpathLocator
									+ String.format(buttonXpathPattern,
											CourseBlockElement.LEARN_SMART
													.getValue())));
					learnSmartMathButton.jsClick(browser);
				}
				catch(Exception e){
					Element learnSmartMathButton = browser.waitForElement(By
							.xpath(baseXpathLocator
									+ String.format(buttonXpathPattern,
											CourseBlockElement.LEARN_SMART
													.getValue())));
					learnSmartMathButton.jsClick(browser);
					createNewAccount();
				}				
			}
		});
	}
	
	private void clickAleksButtonHelper(){
		browser.opensNewWindow(new Action() {
			@Override
			public void perform() {
				Element aleksButton = browser.waitForElement(By
						.xpath(baseXpathLocator
								+ String.format(buttonXpathPattern,
										CourseBlockElement.ALEKS.getValue())));
				aleksButton.jsClick(browser);
			}
		});
		browser.pause(6000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
	}
	
	private void clickSimNetButtonHelper(){
		browser.opensNewWindow(new Action() {
			@Override
			public void perform() {
				Element simNetButton = browser.waitForElement(By
						.xpath(baseXpathLocator
								+ String.format(buttonXpathPattern,
										CourseBlockElement.SIMNET.getValue())));
				simNetButton.jsClick(browser);
			}
		});
		browser.pause(6000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
	}
	private void clickSmartBookButton() {
		Logger.info("Open SmartBook");
		try {
			clickSmartBookButtonHelper();
		} catch (Exception e) {
			Logger.info("Clicking for the first time on SmartBook button failed, trying again");
			clickSmartBookButtonHelper();
		}
	}
	
	private void clickSmartBookButtonHelper(){
		browser.opensNewWindow(new Action() {
			@Override
			public void perform() {
				browser.pause(2000);
				browser.printHandles();
				Element smartBookButton = browser.waitForElement(By
						.xpath(baseXpathLocator
								+ String.format(buttonXpathPattern,
										CourseBlockElement.SMART_BOOK
												.getValue())));			
				Logger.info("Clicking element: '" + smartBookButton.getIdentifyingText() + "'");	
				browser.pause(500);
				smartBookButton.jsClick(browser);
				browser.pause(1000);
				browser.makeScreenshot();
				browser.printHandles();
				
				try {
					createNewAccount();
				} catch (Exception e) {
					Logger.info(e.toString());
					Logger.info("Creating a new account failed. Try to create new account again...");
					createNewAccount();
				}
			}
		});
		browser.pause(6000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
	}

	private void clickAdoptSmartBookWithAttemptsAndTimeOut(int attempts,
			int timeoutInMilisBetweenAttempts) {
		for (int i = 1; i < attempts; i++) {
			browser.closeAllWindowsExceptFirst();
			clickSmartBookButton();
			Logger.info("Check Access denied message");
			if (browser.waitForText("Access denied", 30)) {
				browser.makeScreenshot();
				Logger.info("Access denied message is present");
				browser.pause(timeoutInMilisBetweenAttempts);
			} else {
				break;
			}
		}
	}
	
	private void clickLearnSmartButton() {
		try {
			clickLearnSmartButtonHelper();
		} catch (Exception e) {
			Logger.info("Clicking for the first time on LearnSmart button failed, trying again");
			clickLearnSmartButtonHelper();
		}
	}
	private void MhCampusOlcEditionScreenHelper(){
		browser.pause(2000);
		browser.printHandles();
		Element viewResourcesLink = browser.waitForElement(By.xpath(viewOnlineResourcesLinkLocator));	
		Logger.info("Clicking element: '" + viewResourcesLink.getIdentifyingText() + "'");
		browser.pause(500);
		viewResourcesLink.jsClick(browser);
		viewResourcesLink.jsClick(browser);
		browser.pause(1000);
		browser.makeScreenshot();
		browser.printHandles();
		
		createNewAccount();
		
		browser.pause(6000);
		browser.switchToLastWindow();
		browser.manage().window().maximize();
		browser.makeScreenshot();
	}
	
	private void fillEmailAddress(String email) {
		Logger.info("Enter an email and click 'Continue'");
		emailAddressInput.waitForPresence(10); 
		emailAddressInput.typeValue(email);
		eBookContinueBtn.click();
	}
	
	private void clickLaunchEbook(String email) {
		Logger.info("Click launch Ebook");
		try{
			clickLaunchEbookHelper();
		}catch (Exception e){
			Logger.info("Clicking for the first time on eBook button failed, trying again");
			clickLaunchEbookHelper();
		}
	}
	
	private void clickLaunchEbookHelper(){
		Element launchEbookLink = browser.waitForElement(By
				.xpath(baseXpathLocator
						+ String.format(buttonXpathPattern,
								CourseBlockElement.LAUNCH_EBOOK.getValue())));
		launchEbookLink.jsClick(browser);
		browser.pause(9000);   
		browser.switchToLastWindow();  
		browser.manage().window().maximize();
		browser.makeScreenshot();
	}
	
	private void acceptTheTerms(){
		Logger.info("Accept the terms");
		browser.pause(5000);
		Element iAgree = browser.findElement(By.cssSelector("input[id='tos']"));
		iAgree.jsClick(browser);
		Element accept = browser.waitForElement(By.xpath("//button[contains(text(),'Accept')]"), 10);
		accept.click();
		browser.pause(9000);
	}
	
	private void clickNext(){
		Logger.info("Click 'next, next, next'");		
		Element next = browser.waitForElement(By.xpath("//button[contains(text(),'Next')]"), 10);
		for(int i=1; i<=4; i++)
		   next.click();
		Element done = browser.waitForElement(By.xpath("//button[contains(text(),'Done')]"), 10);
		done.click();
	}
	
	private void clickContinue(){
		Logger.info("Click 'Continue'button");	
		Element continueButton = browser.waitForElement(By.xpath("//span[contains(text(),'Continue')]"));
		continueButton.click();		
		browser.pause(5000);
	}
	
	private void fillFormForBookshelf(String userEmail){
		
		Logger.info("Fill the form and accept the terms");
		String prefix = RandomStringUtils.randomAlphanumeric(5);
		String userFirstName = prefix + "First";
		String userLastName = prefix + "Last";
		String userPassword = "$Test2016";
		
		browser.makeScreenshot();
		browser.manage().window().maximize();
		browser.makeScreenshot();
		
		Element firstName = browser.waitForElement(By.id("first-name-input"));
		firstName.sendKeys(userFirstName);
		Element lastName = browser.waitForElement(By.id("last-name-input"));
		lastName.sendKeys(userLastName);
		Element securityAnswer = browser.waitForElement(By.id("security-answer-input"));
		securityAnswer.sendKeys("Test");
		Element password = browser.waitForElement(By.id("password-input"));
		password.sendKeys(userPassword);
		Element passwordConfirm = browser.waitForElement(By.id("password-confirm-input"));
		passwordConfirm.sendKeys(userPassword);
		
		Element email = browser.waitForElement(By.xpath("//*[contains(text(),'Email me')]"));
		email.sendKeys(Keys.END);
		browser.makeScreenshot();
		
		Element iAgree = browser.findElement(By.cssSelector("input[id='tos']"));
		if(!iAgree.isSelected())
			iAgree.jsClick(browser);
		browser.pause(6000);
		browser.makeScreenshot();
		try{
			Logger.info("Click on Finish button");
			Element finish = browser.waitForElement(By.xpath(".//*[@id='registration-form']/div[8]/input"));
			finish.jsClick(browser);
		}catch (Exception e){
			Logger.info("Failed to click on Finish button, trying again..");
			iAgree = browser.findElement(By.cssSelector("input[id='tos']"));
			if(iAgree.isSelected())
				iAgree.jsClick(browser);
			iAgree.jsClick(browser);
			browser.pause(6000);
			browser.makeScreenshot();
		}
		browser.pause(6000);
		browser.makeScreenshot();
		
		Element close;			
		// Or's changes
		if(userEmail.startsWith("instructor")){				
			try{
//				close = browser.waitForElement(By.xpath("//*[@id='reader']/div[1]/button"), 10);
				close = browser.waitForElement(By.xpath("//button[@class='modal-x noButton']"), 10);
				close.click();
			}catch(Exception e){
				Logger.info("The FIRST close button doesn't present");
			}
	
			try{
				close = browser.waitForElement(By.xpath("//button[@class='tour-close noButton']"), 10);
				close.click();	
			}catch(Exception e){
				Logger.info("The SECOND close button doesn't present");
			}
		}
	}
}