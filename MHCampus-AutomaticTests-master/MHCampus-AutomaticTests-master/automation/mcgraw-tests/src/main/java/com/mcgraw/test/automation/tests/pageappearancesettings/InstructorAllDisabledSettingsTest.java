package com.mcgraw.test.automation.tests.pageappearancesettings;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.ui.mhcampus.TegrityCreateMhCampusCustomerScreen.PageAppearanceSettings;
import com.mcgraw.test.automation.ui.mhcampus.course.CourseBlockElement;

public class InstructorAllDisabledSettingsTest extends BasePageAppearanceSettingsTest {

	@BeforeClass
	public void changeInstanceSettingsToDisabledAndLoginAsInstructor() throws Exception {
		tegrityAdministrationApplication.editSettingsInMhCampusInstance(instance.customerNumber, PageAppearanceSettings.Disabled);
		browser.pause((mhCampusInstanceApplication.DIRECT_LOGIN_TIMEOUT)*5);

		mhCampusInstanceApplication.loginToMhCampusAsUser(instance.pageAddressForLogin, instance.institution, instructorLogin, password);
		mhCampusCourseBlockZoology = mhCampusInstanceApplication.expandCourse(courseNameZoology);
		mhCampusCourseBlockMarketing = mhCampusInstanceApplication.expandCourse(courseNameMarketing);
		mhCampusCourseBlockAlgebra = mhCampusInstanceApplication.expandCourse(courseNameAlgebra);
		mhCampusCourseBlockEmpty = mhCampusInstanceApplication.expandCourse(courseNameEmpty);
	}

	@Test(description = "Test 'TextBook search' not available for Empty course")
	public void testTextBookSearchNotAvailableForEmptyCourseUnderInstructor() {
		Assert.assertFalse(mhCampusCourseBlockEmpty.isTextbookSearchPresent(), "Text book search is present");
	}

	@Test(description = "Test 'Adopted services area' not available for Zoology")
	public void testAdoptedServicesAreaNotAvailableForZoologyUnderInstructor() {
		Assert.assertFalse(mhCampusCourseBlockZoology.isAdoptedServicesAreaPresent(), "Adopted services area is present");
	}

	@Test(description = "Test 'Non-aAdopted services area' not available for Zoology")
	public void testNonAdoptedServicesAreaNotAvailableForZoologyUnderInstructor() {
		Assert.assertFalse(mhCampusCourseBlockZoology.isNonAdoptedServicesAreaPresent(), "Non-adopted services area is present");
	}

	@Test(description = "Test 'view online resources' link available for Zoology")
	public void testViewOnlineResourcesLinkNotAvailableForZoologyUnderInstructor() {
		Assert.assertFalse(mhCampusCourseBlockZoology.isViewOnlineResourcesLinkPresent(), "View online resources link present");
	}

	@Test(description = "Test 'Launch Ebook' option not available for Zoology")
	public void testLaunchEbookOptionNotAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.LAUNCH_EBOOK);
	}

	@Test(description = "Test 'Create' option not available for Zoology")
	public void testCreateOptionNotAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.CREATE);
	}

	@Test(description = "Test 'ALEKS' option not available for Zoology")
	public void testALEKSOptionNotAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.ALEKS);
	}

	@Test(description = "Test 'SimNet' option not available for Zoology")
	public void testSimNetOptionNotAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.SIMNET);
	}

	@Test(description = "Test 'GDP' option not available for Zoology")
	public void testGDPOptionNotAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.GDP);
	}

	@Test(description = "Test 'Activ Sim' option not available for Zoology")
	public void testActivSimOptionNotAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameZoology, CourseBlockElement.ACTIV_SIM);
	}

	@Test(description = "Test 'Customize' option not available for Marketing")
	public void testCustomizeOptionNotAvailableForMarketingUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameMarketing, CourseBlockElement.CUSTOMIZE_BUTTON);
	}

	@Test(description = "Test 'Connect' option not available for Marketing")
	public void testConnectOptionNotAvailableForMarketingUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameMarketing, CourseBlockElement.CONNECT);
	}

	@Test(description = "Test 'Learn Smart' option not available for Marketing")
	public void testLearnSmartOptionNotAvailableForMarketingUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameMarketing, CourseBlockElement.LEARN_SMART);
	}

	@Test(description = "Test Connect Math button not available for Algebra")
	public void testConnectMathOptionNotAvailableForAlgebraUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsNotPresent(courseNameAlgebra, CourseBlockElement.CONNECT_MATH);
	}

	@Test(description = "Test Related Titles Area not available for Algebra")
	public void testRelatedTitlesAreaNotAvailableForAlgebraUnderInstructor() {
		Assert.assertFalse(mhCampusCourseBlockAlgebra.isRelatedTitlesAreaPresent(), "Related Titles Area present");
	}
}
