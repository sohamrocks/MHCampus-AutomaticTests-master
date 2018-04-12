package com.mcgraw.test.automation.tests.pageappearancesettings;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.CourseBlockElement;
import com.mcgraw.test.automation.ui.mhcampus.course.aleks.MhCampusALEKSReadyToUseScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.MhCampusConnectSaveCourseSectionPair;
import com.mcgraw.test.automation.ui.mhcampus.course.connectmath.MhCampusConnectMathReadyToUseScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.gdp.MhCampusGDPPairingPortal;
import com.mcgraw.test.automation.ui.mhcampus.course.learnsmart.MhCampusLearnSmartScreenWithOutBar;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetPairingPortalForInstructor;

public class StudentAllEnabledSettingsTest extends BasePageAppearanceSettingsTest {

	@BeforeClass(description = "Login as student for the 1st time and process pop-ups")
	public void firstStudentLogin() {
		MhCampusIntroductionScreen mhCampusIntroductionScreen = mhCampusInstanceApplication.loginToMhCampusAsUser(
				instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusIntroductionScreen.acceptRules();
	}

	@AfterMethod(description = "Guarantee to close new windows even if test method failed")
	public void closeExtraBrowserWindows() {
		browser.closeAllWindowsExceptFirst();
	}

	@Test(description = "Test if 'Connect Math' adopted under student")
	public void testConnectMathOptionAvailableForStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusInstanceApplication.adoptConnectMathForCourse(courseNameAlgebra);
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusConnectMathReadyToUseScreen.class),
				notOnDesiredPageMessage(MhCampusConnectMathReadyToUseScreen.class));

		browser.closeAllWindowsExceptFirst();
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameAlgebra, CourseBlockElement.CONNECT_MATH);
	}

	@Test(description = "Test if 'Connect' adopted under student")
	public void testConnectOptionAvailableForStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		MhCampusConnectSaveCourseSectionPair mhCampusConnectSaveCourseSectionPair = mhCampusInstanceApplication.adoptConnectForCourse(
				courseNameMarketing, false);
		
		Assert.verifyTrue(mhCampusConnectSaveCourseSectionPair.isSuccessMessagePresent(),"Success message is absent");
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusConnectSaveCourseSectionPair.class),notOnDesiredPageMessage(MhCampusConnectSaveCourseSectionPair.class));

		browser.closeAllWindowsExceptFirst();

		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameMarketing, CourseBlockElement.CONNECT);
	}

	@Test(description = "Test if 'Learn Smart' adopted under student")
	public void testLearnsmartOptionAvailableForStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusInstanceApplication.adoptLearnSmartForCourse(courseNameMarketing);
		
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusLearnSmartScreenWithOutBar.class),
				notOnDesiredPageMessage(MhCampusLearnSmartScreenWithOutBar.class));
		browser.closeAllWindowsExceptFirst(true);

		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameMarketing, CourseBlockElement.LEARN_SMART);
	}

	@Test(description = "Test if 'ALEKS' adopted under student")
	public void testALEKSOptionAvailableForStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusInstanceApplication.adoptALEKSForCourse(courseNameZoology);
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusALEKSReadyToUseScreen.class),
				notOnDesiredPageMessage(MhCampusALEKSReadyToUseScreen.class));
		browser.closeAllWindowsExceptFirst();

		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameZoology, CourseBlockElement.ALEKS);
	}

	@Test(description = "Test if 'GDP' adopted under student")
	public void testGDPOptionAvailableForStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusInstanceApplication.adoptGDPForCourse(courseNameZoology);
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusGDPPairingPortal.class),
				notOnDesiredPageMessage(MhCampusGDPPairingPortal.class));
		browser.closeAllWindowsExceptFirst();

		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameZoology, CourseBlockElement.GDP);
	}

	@Test(description = "Test if 'SimNet' adopted under student")
	public void testSimNetOptionAvailableForStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusInstanceApplication.adoptSimNetForCourse(courseNameZoology);
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusSimNetPairingPortalForInstructor.class),
				notOnDesiredPageMessage(MhCampusSimNetPairingPortalForInstructor.class));
		browser.closeAllWindowsExceptFirst();

		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameZoology, CourseBlockElement.SIMNET);
	}

	@Test(description = "Test 'Launch Ebook' option available for Zoology")
	public void testLaunchEbookOptionAvailableForZoologyUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameZoology, CourseBlockElement.LAUNCH_EBOOK);
	}

	@Test(description = "Test 'view online resources' link is available for Zoology")
	public void testViewOnlineResourcesLinkPresentForZoologyUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusCourseBlockZoology = mhCampusInstanceApplication.expandCourse(courseNameZoology);
		Assert.assertTrue(mhCampusCourseBlockZoology.isViewOnlineResourcesLinkPresent(), "View online resources Link not present");
	}

	@Test(description = "Test 'Adopted services area' is available for Zoology")
	public void testAdoptedServicesAreaPresentForZoologyUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusCourseBlockZoology = mhCampusInstanceApplication.expandCourse(courseNameZoology);
		Assert.assertTrue(mhCampusCourseBlockZoology.isAdoptedServicesAreaPresent(), "Adopted services area not present");
	}

	@Test(description = "Test 'Non-Adopted services area' not available for Zoology under student")
	public void testNonAdoptedServicesAreaNotAvailableForZoologyUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusCourseBlockZoology = mhCampusInstanceApplication.expandCourse(courseNameZoology);
		Assert.assertFalse(mhCampusCourseBlockZoology.isNonAdoptedServicesAreaPresent(), "Non-adopted services area is present");
	}

	@Test(description = "Test Related Titles Area not available for Algebra")
	public void testRelatedTitlesAreaNotAvailableForAlgebraUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusCourseBlockAlgebra = mhCampusInstanceApplication.expandCourse(courseNameAlgebra);
		Assert.assertFalse(mhCampusCourseBlockAlgebra.isRelatedTitlesAreaPresent(), "Related Titles Area present");
	}

	@Test(description = "Test 'TextBook search' not available for Empty course under student")
	public void testTextBookSearchNotAvailableForEmptyCourseUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusCourseBlockEmpty = mhCampusInstanceApplication.expandCourse(courseNameEmpty);
		Assert.assertFalse(mhCampusCourseBlockEmpty.isTextbookSearchPresent(), "Text book search is present");
	}

	@Test(description = "Test 'Customize' button not available for Marketing under student")
	public void testCustomizeNotAvailableForMarketingUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameMarketing, CourseBlockElement.CUSTOMIZE_BUTTON);
	}

	@Test(description = "Test 'Create' button not present for Zoology under student")
	public void testCreateOptionNotPresentForZoologyStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.CREATE);
	}
	
}
