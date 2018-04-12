package com.mcgraw.test.automation.tests.pageappearancesettings;

import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.ui.mhcampus.course.CourseBlockElement;

public class InstructorAllEnabledSettingsTest extends BasePageAppearanceSettingsTest {

	@Test(description = "Test 'Adopted services area' is available for Zoology")
	public void testAdoptedServicesAreaPresentForZoologyUnderInstructor() {
		Assert.assertTrue(mhCampusCourseBlockZoology.isAdoptedServicesAreaPresent(), "Adopted services area not present");
	}

	@Test(description = "Test 'Non-aAdopted services area' is available for Zoology")
	public void testNonAdoptedServicesAreaPresentForZoologyUnderInstructor() {
		Assert.assertTrue(mhCampusCourseBlockZoology.isNonAdoptedServicesAreaPresent(), "Non-adopted services area not present");
	}

	@Test(description = "Test 'view online resources' link is available for Zoology")
	public void testViewOnlineResourcesLinkPresentForZoology() {
		Assert.assertTrue(mhCampusCourseBlockZoology.isViewOnlineResourcesLinkPresent(), "View online resources Link not present");
	}

	@Test(description = "Test 'Launch Ebook' option available for Zoology")
	public void testLaunchEbookOptionAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameZoology, CourseBlockElement.LAUNCH_EBOOK);
	}

	@Test(description = "Test 'Create' option available for Zoology")
	public void testCreateOptionAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameZoology, CourseBlockElement.CREATE);
	}

	@Test(description = "Test 'ALEKS' option available for Zoology")
	public void testALEKSOptionAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameZoology, CourseBlockElement.ALEKS);
	}

	@Test(description = "Test 'SimNet' option available for Zoology")
	public void testSimNetOptionAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameZoology, CourseBlockElement.SIMNET);
	}

	@Test(description = "Test 'GDP' option available for Zoology")
	public void testGDPOptionAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameZoology, CourseBlockElement.GDP);
	}

	@Test(description = "Test 'Activ Sim' option available for Zoology")
	public void testActivSimOptionAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameZoology, CourseBlockElement.ACTIV_SIM);
	}

	@Test(description = "Test 'Print On Demand' option available for Zoology")
	public void testPrintOnDemandOptionAvailableForZoologyUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameZoology, CourseBlockElement.PRINT_ON_DEMAND);
	}

	@Test(description = "Test 'Customize' option available for Marketing")
	public void testCustomizeOptionNAvailableForMarketingUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameMarketing, CourseBlockElement.CUSTOMIZE_BUTTON);
	}

	@Test(description = "Test 'Connect' option available for Marketing")
	public void testConnectOptionAvailableForMarketingUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameMarketing, CourseBlockElement.CONNECT);
	}

	@Test(description = "Test 'Learn Smart' option available for Marketing")
	public void testLearnSmartOptionAvailableForMarketingUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameMarketing, CourseBlockElement.LEARN_SMART);
	}

	@Test(description = "Test Connect Math button available for Algebra")
	public void testConnectMathOptionAvailableForAlgebraUnderInstructor() {
		mhCampusInstanceApplication.checkMhcampusCourseElementsPresent(courseNameAlgebra, CourseBlockElement.CONNECT_MATH);
	}

	@Test(description = "Test Related Titles Area available for Algebra")
	public void testRelatedTitlesAreaPresentForAlgebraUnderInstructor() {
		Assert.assertTrue(mhCampusCourseBlockAlgebra.isRelatedTitlesAreaPresent(), "Related Titles Area not present");
	}

}
