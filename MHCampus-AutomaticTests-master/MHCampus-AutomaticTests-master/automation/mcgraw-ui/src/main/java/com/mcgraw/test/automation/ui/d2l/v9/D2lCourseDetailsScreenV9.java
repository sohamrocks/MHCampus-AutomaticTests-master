package com.mcgraw.test.automation.ui.d2l.v9;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.ui.d2l.base.D2lContentCourseScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lCourseDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lEditHomepageScreen;
import com.mcgraw.test.automation.ui.d2l.base.D2lGradesDetailsScreen;
import com.mcgraw.test.automation.ui.d2l.v10.D2lEditHomepageScreenV10;
import com.mcgraw.test.automation.ui.mhcampus.course.connect.D2lConnectStudentRegistrationScreen;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@class = 'd2l-menuflyout-link-link']")))
public class D2lCourseDetailsScreenV9 extends D2lCourseDetailsScreen {

	public D2lCourseDetailsScreenV9(Browser browser) {
		super(browser);
	}

	@Override
	public D2lContentCourseScreen waitForD2lContentCoursePage() {
		D2lContentCourseScreenV9 D2lContentCourseScreenV9 = 
				browser.waitForPage(D2lContentCourseScreenV9.class);
		browser.makeScreenshot();
		return D2lContentCourseScreenV9;
	}

	@Override
	protected D2lGradesDetailsScreen waitForD2lGradesDetailsPage() {
		D2lGradesDetailsScreenV9 D2lGradesDetailsScreenV9 = 
				browser.waitForPage(D2lGradesDetailsScreenV9.class);
		browser.makeScreenshot();
		return D2lGradesDetailsScreenV9;
	}

	//add by Serhei Zlatov
	@Override
	protected D2lEditHomepageScreen waitForD2lEditHomepageScreenPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExistsPlugInErrorMessage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clickOnModuleTabLink() {
		// TODO Auto-generated method stub
	}

	@Override
	protected D2lCourseDetailsScreen waitForD2lCourseDetailsPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clickOnContinueBtn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isExistErrorMessageText() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public D2lConnectStudentRegistrationScreen clickBeginAsStudent() {
		// TODO Auto-generated method stub
		return null;
	}

}