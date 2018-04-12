package com.mcgraw.test.automation.api.rest.moodle.service;

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

public class MoodleApiUtils {	
	
	private IMoodleRestApi moodleRestApi;

	private MoodleRequestsFactory moodleRequestsFactory;
		
	private MoodleApiUtils(MoodleRestApi moodleRestApi, MoodleRequestsFactory moodleRequestsFactory) {
		super();
		this.moodleRestApi = moodleRestApi;
		this.moodleRequestsFactory = moodleRequestsFactory;
	}

	public MoodleUserRS createUser(String login, String password, String firstName, String lastName) throws Exception {		
		MoodleAddUserRQ moodleAddUserRQ = moodleRequestsFactory.createAddUserRequest(login, password, firstName, lastName);
		return moodleRestApi.createUser(moodleAddUserRQ);
	}
	
	public MoodleCategoryRS createCategory(String categoryName) throws Exception {		
		MoodleAddCategoryRQ moodleAddCategoryRQ = moodleRequestsFactory.createAddCategoryRequest(categoryName);
		return moodleRestApi.createCategory(moodleAddCategoryRQ);
	}
	
	public MoodleCourseRS createCourseInsideCategory(String fullName, String shortName, MoodleCategoryRS category) throws Exception {		
		MoodleAddCourseRQ moodleAddCourseRQ = moodleRequestsFactory.createAddCourseRequest(fullName, shortName, category.getId());
		return moodleRestApi.createCourse(moodleAddCourseRQ);		
	}
	
	public void enrollToCourseAsStudent(MoodleUserRS userRS, MoodleCourseRS courseRS) throws Exception {
		MoodleEnrolUserRQ moodleEnrolUserRQ = moodleRequestsFactory.createEnrolUserToCourseRequest(MoodleUserRole.STUDENT, userRS, courseRS);
		moodleRestApi.enrolUserToCourse(moodleEnrolUserRQ);		
	}
	
	public void enrollToCourseAsInstructor(MoodleUserRS userRS, MoodleCourseRS courseRS) throws Exception {
		MoodleEnrolUserRQ moodleEnrolUserRQ = moodleRequestsFactory.createEnrolUserToCourseRequest(MoodleUserRole.TEACHER, userRS, courseRS);
		moodleRestApi.enrolUserToCourse(moodleEnrolUserRQ);		
	}
	
	public void deleteUser(MoodleUserRS userRS) throws Exception {		
		MoodleDeleteUserRQ moodleDeleteUserRQ = moodleRequestsFactory.createDeleteUserRequest(userRS);
		moodleRestApi.deleteUser(moodleDeleteUserRQ);
	}
	
	public void deleteCategoryWithCourses(MoodleCategoryRS moodleCategoryRS) throws Exception {
		MoodleDeleteCategoryRQ moodleDeleteCategoryRQ = moodleRequestsFactory.createDeleteCategoryRequest(moodleCategoryRS);
		moodleRestApi.deleteCategoryWithCourses(moodleDeleteCategoryRQ);		
	}
}
