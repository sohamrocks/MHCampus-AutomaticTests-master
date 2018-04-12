package com.mcgraw.test.automation.api.rest;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.api.rest.moodle.MoodleUserRole;
import com.mcgraw.test.automation.api.rest.moodle.factory.MoodleRequestsFactory;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleAddCategoryRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleAddCourseRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleAddUserRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleDeleteCategoryRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleDeleteUserRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleEnrolUserRQ;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCategoryRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCourseRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleUserRS;
import com.mcgraw.test.automation.api.rest.moodle.service.IMoodleRestApi;

@ContextConfiguration(locations = { "classpath:spring/test-api-context.xml" })
public class MoodleRestApiTests extends AbstractTestNGSpringContextTests {

	private MoodleAddUserRQ moodleAddUserRQ;

	private MoodleAddCategoryRQ moodleAddCategoryRQ;

	private MoodleAddCourseRQ moodleAddCourseRQ;
	
	private MoodleDeleteUserRQ moodleDeleteUserRQ;
	
	private MoodleDeleteCategoryRQ moodleDeleteCategoryRQ;
	
	private MoodleEnrolUserRQ moodleEnrolUserRQ;

	private MoodleUserRS moodleUserRS;

	private MoodleCategoryRS moodleCategoryRS;

	private MoodleCourseRS moodleCourseRS;

	@Autowired
	private IMoodleRestApi moodleRestApi;

	@Autowired
	private MoodleRequestsFactory moodleRequestsFactory;

	@BeforeClass
	public void createDataForRequests() {
		moodleAddUserRQ = moodleRequestsFactory.createAddUserRequest();
		moodleAddCategoryRQ = moodleRequestsFactory.createAddCategoryRequest();
	}

	@Test(description = "Test moodle create moodleUser")
	public void checkCreateUser() throws RestEndpointIOException, UnsupportedEncodingException {

		moodleUserRS = moodleRestApi.createUser(moodleAddUserRQ);
		Assert.assertNotNull(moodleUserRS, "User is null");
	}

	@Test(description = "Test moodle create moodleCategory", dependsOnMethods = { "checkCreateUser" })
	public void checkCreateCategory() throws RestEndpointIOException, UnsupportedEncodingException {
		moodleCategoryRS = moodleRestApi.createCategory(moodleAddCategoryRQ);
		Assert.assertNotNull(moodleCategoryRS, "moodleCategory is null");
	}

	@Test(description = "Test moodle create createCourse", dependsOnMethods = { "checkCreateCategory" })
	public void checkCreateCourse() throws RestEndpointIOException, UnsupportedEncodingException {
		moodleAddCourseRQ = moodleRequestsFactory.createAddCourseRequest(moodleCategoryRS);
		moodleCourseRS = moodleRestApi.createCourse(moodleAddCourseRQ);
		Assert.assertNotNull(moodleCourseRS, "moodleCourse is null");
	}

	@Test(description = "Test moodle enrol user", dependsOnMethods = { "checkCreateCourse" })
	public void checkEnrolUserToCourse() throws RestEndpointIOException, UnsupportedEncodingException {
		moodleEnrolUserRQ = moodleRequestsFactory.createEnrolUserToCourseRequest(MoodleUserRole.STUDENT, moodleUserRS, moodleCourseRS);
		moodleRestApi.enrolUserToCourse(moodleEnrolUserRQ);
	}

	@AfterClass
	public void deleteDataForRequests() throws RestEndpointIOException, UnsupportedEncodingException {
		moodleDeleteCategoryRQ = moodleRequestsFactory.createDeleteCategoryRequest(moodleCategoryRS);
		moodleRestApi.deleteCategoryWithCourses(moodleDeleteCategoryRQ);
				
		moodleDeleteUserRQ = moodleRequestsFactory.createDeleteUserRequest(moodleUserRS);
		moodleRestApi.deleteUser(moodleDeleteUserRQ);
	}
}
