package com.mcgraw.test.automation.tests.pageappearancesettings;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.ui.mhcampus.MhCampusIntroductionScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.CourseBlockElement;
import com.mcgraw.test.automation.ui.mhcampus.course.aleks.MhCampusALEKSReadyToUseScreen;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.MhCampusConnectSaveCourseSectionPair;
import com.mcgraw.test.automation.ui.mhcampus.course.gdp.MhCampusGDPPairingPortal;
import com.mcgraw.test.automation.ui.mhcampus.course.learnsmart.MhCampusLearnSmartScreenWithOutBar;
import com.mcgraw.test.automation.ui.mhcampus.course.simnet.MhCampusSimNetPairingPortalForInstructor;

public class StudentAllDisabledSettingsTest extends BasePageAppearanceSettingsTest {

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

	@Test(description = "Test 'TextBook search' not available for Empty course under student")
	public void testTextBookSearchNotAvailableForEmptyCourseUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusCourseBlockEmpty = mhCampusInstanceApplication.expandCourse(courseNameEmpty);
		Assert.assertFalse(mhCampusCourseBlockEmpty.isTextbookSearchPresent(), "Text book search is present");
	}

	@Test(description = "Test 'Adopted services area' not available for Zoology under student")
	public void testAdoptedServicesAreaNotAvailableForZoologyUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusCourseBlockZoology = mhCampusInstanceApplication.expandCourse(courseNameZoology);
		Assert.assertFalse(mhCampusCourseBlockZoology.isAdoptedServicesAreaPresent(), "Adopted services area is present");
	}

	@Test(description = "Test 'Non-Adopted services area' not available for Zoology under student")
	public void testNonAdoptedServicesAreaNotAvailableForZoologyUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusCourseBlockZoology = mhCampusInstanceApplication.expandCourse(courseNameZoology);
		Assert.assertFalse(mhCampusCourseBlockZoology.isNonAdoptedServicesAreaPresent(), "Non-adopted services area is present");
	}

	@Test(description = "Test 'View online resources' link not available for Zoology under student")
	public void testViewOnlineResourcesLinkNotAvailableForStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusCourseBlockZoology = mhCampusInstanceApplication.expandCourse(courseNameZoology);
		Assert.assertFalse(mhCampusCourseBlockZoology.isViewOnlineResourcesLinkPresent(), "View online resources link present");
	}

	@Test(description = "Test 'Launch EBook' link not present for Zoology under student")
	public void testLaunchEbookOprionNotPresentForZoologyStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.LAUNCH_EBOOK);
	}

	@Test(description = "Test 'Create' button not present for Zoology under student")
	public void testCreateOptionNotPresentForZoologyStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.CREATE);
	}

	@Test(description = "Test if 'ALEKS' not available for Zoology under student")
	public void testALEKSNotAvailableForZoologyUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusInstanceApplication.adoptALEKSForCourse(courseNameZoology);
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusALEKSReadyToUseScreen.class),
				notOnDesiredPageMessage(MhCampusALEKSReadyToUseScreen.class));
		browser.closeAllWindowsExceptFirst();

		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.ALEKS);
	}

	@Test(description = "Test if 'GDP' not available for Zoology under student")
	public void testGDPNotAvailableForZoologyUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusInstanceApplication.adoptGDPForCourse(courseNameZoology);
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusGDPPairingPortal.class),
				notOnDesiredPageMessage(MhCampusGDPPairingPortal.class));
		browser.closeAllWindowsExceptFirst();

		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.GDP);
	}

	@Test(description = "Test if 'SimNet' not available for Zoology under student")
	public void testSimNetNotAvailableForZoologyUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusInstanceApplication.adoptSimNetForCourse(courseNameZoology);
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusSimNetPairingPortalForInstructor.class),
				notOnDesiredPageMessage(MhCampusSimNetPairingPortalForInstructor.class));
		browser.closeAllWindowsExceptFirst();

		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.SIMNET);
	}

	@Test(description = "Test 'Customize' button not available for Marketing under student")
	public void testCustomizeNotAvailableForMarketingUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameMarketing, CourseBlockElement.CUSTOMIZE_BUTTON);
	}

	@Test(description = "Test if 'Connect' not available for Marketing under student")
	public void testConnectNotAvailableForMarketingUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		MhCampusConnectSaveCourseSectionPair mhCampusConnectSaveCourseSectionPair = mhCampusInstanceApplication.adoptConnectForCourse(
				courseNameMarketing, false);
		
		Assert.verifyTrue(mhCampusConnectSaveCourseSectionPair.isSuccessMessagePresent(),"Success message is absent");
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusConnectSaveCourseSectionPair.class),notOnDesiredPageMessage(MhCampusConnectSaveCourseSectionPair.class));

		browser.closeAllWindowsExceptFirst();

		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameMarketing, CourseBlockElement.CONNECT);
	}

	@Test(description = "Test if 'Learn Smart' not available for Marketing under student")
	public void testLearnsmartNotAvailableForMarketingUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);
        mhCampusInstanceApplication.adoptLearnSmartForCourse(courseNameMarketing);
		
		Assert.assertTrue(browser.isCurrentlyOnPageUrl(MhCampusLearnSmartScreenWithOutBar.class), notOnDesiredPageMessage(MhCampusLearnSmartScreenWithOutBar.class));		
		browser.closeAllWindowsExceptFirst(true);

		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameMarketing, CourseBlockElement.LEARN_SMART);
	}

	@Test(description = "Test 'Activ Sim' option not available for Zoology")
	public void testActivSimOptionNotAvailableForZoologyUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.ACTIV_SIM);
	}

	@Test(description = "Test Related Titles Area not available for Algebra")
	public void testRelatedTitlesAreaNotAvailableForAlgebraUnderStudent() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusCourseBlockAlgebra = mhCampusInstanceApplication.expandCourse(courseNameAlgebra);
		Assert.assertFalse(mhCampusCourseBlockAlgebra.isRelatedTitlesAreaPresent(), "Related Titles Area present");
	}

	@Test(description = "Test Connect Math button not available for Algebra")
	public void testConnectMathOptionNotAvailableForAlgebraUnderStudet() {
		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, studentLogin, password);
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameAlgebra, CourseBlockElement.CONNECT_MATH);
	}
	
}
