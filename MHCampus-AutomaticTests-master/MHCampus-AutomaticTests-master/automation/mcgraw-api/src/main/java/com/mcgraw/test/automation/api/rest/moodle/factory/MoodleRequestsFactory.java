package com.mcgraw.test.automation.api.rest.moodle.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.cglib.transform.impl.AddDelegateTransformer;

import com.mcgraw.test.automation.api.rest.moodle.MoodleUserRole;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleAddCategoryRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleAddCourseRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleAddUserRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleDeleteCategoryRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleDeleteUserRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleEnrolUserRQ;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCategoryRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCourseRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleUserRS;
import com.mcgraw.test.automation.framework.core.common.test_data.random_utils.StringUtils;

/**
 * Requests factory for Moodle rest api
 * 
 * @author Andrei_Turavets
 * 
 */
public class MoodleRequestsFactory {	
			
	public MoodleAddUserRQ createAddUserRequest(String userName, String password, String firstName, String lastName, String email,
			String city, String country, String auth) {
		MoodleAddUserRQ addedUserRQ = new MoodleAddUserRQ();
		addedUserRQ.setUserName(userName);
		addedUserRQ.setPassword(password);
		addedUserRQ.setFirstName(firstName);
		addedUserRQ.setLastName(lastName);
		addedUserRQ.setEmail(email);
		addedUserRQ.setCity(city);
		addedUserRQ.setCountry(country);
		addedUserRQ.setAuth(auth);
		return addedUserRQ;
	}

	public MoodleDeleteUserRQ createDeleteUserRequest(MoodleUserRS user) {
		MoodleDeleteUserRQ rq = new MoodleDeleteUserRQ();
		rq.setUserId(user.getId());
		return rq;
	}
	
	public MoodleAddCategoryRQ createAddCategoryRequest(String name) {
		MoodleAddCategoryRQ moodleAddCategoryRQ = new MoodleAddCategoryRQ();
		moodleAddCategoryRQ.setCategoryName(name);
		return moodleAddCategoryRQ;
	}	

	public MoodleDeleteCategoryRQ createDeleteCategoryRequest(MoodleCategoryRS category) {
		MoodleDeleteCategoryRQ moodleDeleteCategoryRQ = new MoodleDeleteCategoryRQ();
		moodleDeleteCategoryRQ.setCategoryId(category.getId());
		moodleDeleteCategoryRQ.setRecursive("1");
		return moodleDeleteCategoryRQ;
	}

	public MoodleAddCourseRQ createAddCourseRequest(String fullName, String shortName, String categoryId) {
		MoodleAddCourseRQ moodleAddCourseRQ = new MoodleAddCourseRQ();
		moodleAddCourseRQ.setFullName(fullName);
		moodleAddCourseRQ.setShortName(shortName);
		moodleAddCourseRQ.setCategoryId(categoryId);
		return moodleAddCourseRQ;
	}

	public MoodleEnrolUserRQ createEnrolUserToCourseRequest(MoodleUserRole role, MoodleUserRS user, MoodleCourseRS course) {
		return createEnrolUserToCourseRequest(role.getValue(), user.getId(), course.getId());
	}

	public MoodleEnrolUserRQ createEnrolUserToCourseRequest(String role, String userId, String courseId) {
		MoodleEnrolUserRQ moodleEnrolUserRQ = new MoodleEnrolUserRQ();
		moodleEnrolUserRQ.setRole(role);
		moodleEnrolUserRQ.setUserId(userId);
		moodleEnrolUserRQ.setCourseId(courseId);
		return moodleEnrolUserRQ;
	}
	
	public MoodleAddCategoryRQ createAddCategoryRequest() {
		String numericRandom = StringUtils.randomNumeric(5);
		return createAddCategoryRequest("category" + numericRandom);
	}

	public MoodleAddCourseRQ createAddCourseRequest(MoodleCategoryRS category) {
		String numericRandom = StringUtils.randomNumeric(5);
		return createAddCourseRequest("courseFull" + numericRandom, "courseShort" + numericRandom, category.getId());
	}
	
	public MoodleAddUserRQ createAddUserRequest(String userName, String password, String firstName, String lastName) {
		String numericRandom = StringUtils.randomNumeric(5);
		return createAddUserRequest(userName, password, firstName, lastName, "email" + numericRandom + "@email.com", "Minsk", "BY", "manual");
	}	
	
	public MoodleAddUserRQ createAddUserRequest() {
		String numericRandom = StringUtils.randomNumeric(5);
		return createAddUserRequest("user" + numericRandom, "123qweA@", "name" + numericRandom, "surname" + numericRandom);
	}
}
