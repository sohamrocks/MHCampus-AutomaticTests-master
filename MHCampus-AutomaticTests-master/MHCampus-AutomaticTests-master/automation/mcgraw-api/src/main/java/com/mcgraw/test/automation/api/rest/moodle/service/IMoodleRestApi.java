package com.mcgraw.test.automation.api.rest.moodle.service;

import java.io.UnsupportedEncodingException;

import com.mcgraw.test.automation.api.rest.base.IRestApi;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleAddCategoryRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleAddCourseRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleAddUserRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleDeleteCategoryRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleDeleteUserRQ;
import com.mcgraw.test.automation.api.rest.moodle.rqmodel.MoodleEnrolUserRQ;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCategoryRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleCourseRS;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleUserRS;

/**
 * Interface for moodle REST api
 * 
 * @author Andrei_Turavets
 * 
 */
public interface IMoodleRestApi extends IRestApi {

	/** Create moodle user
	 * 
	 * @param rq
	 * @return MoodleUserRS json representation of user
	 * @throws RestEndpointIOException
	 * @throws UnsupportedEncodingException
	 */
	MoodleUserRS createUser(MoodleAddUserRQ rq) throws RestEndpointIOException, UnsupportedEncodingException;

	/**
	 * Delete moodle user
	 * 
	 * @param user
	 * @throws RestEndpointIOException
	 * @throws UnsupportedEncodingException
	 */
	void deleteUser(MoodleDeleteUserRQ rq) throws RestEndpointIOException, UnsupportedEncodingException;

	/**
	 * Create moodle category for courses
	 * 
	 * @param rq - Request body
	 * @return MoodleCategoryRS json representation of category
	 * @throws RestEndpointIOException
	 * @throws UnsupportedEncodingException
	 */
	MoodleCategoryRS createCategory(MoodleAddCategoryRQ rq) throws RestEndpointIOException, UnsupportedEncodingException;

	/**
	 * Create Moodle course
	 * 
	 * @param rq
	 * @return MoodleCourseRS json representation of course
	 * @throws RestEndpointIOException
	 * @throws UnsupportedEncodingException
	 */
	MoodleCourseRS createCourse(MoodleAddCourseRQ rq) throws RestEndpointIOException, UnsupportedEncodingException;

	/**
	 * Delete category with all courses in it
	 * 
	 * @param category
	 * @throws RestEndpointIOException
	 * @throws UnsupportedEncodingException
	 */
	void deleteCategoryWithCourses(MoodleDeleteCategoryRQ rq) throws RestEndpointIOException, UnsupportedEncodingException;

	/**
	 * Enroll user with to course with role
	 * @param role
	 * @param user
	 * @param course
	 * @throws RestEndpointIOException
	 * @throws UnsupportedEncodingException
	 */
	void enrolUserToCourse(MoodleEnrolUserRQ rq) throws RestEndpointIOException, UnsupportedEncodingException;

}
