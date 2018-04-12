package com.mcgraw.test.automation.api.rest.moodle.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.api.rest.base.IRestServerError;
import com.mcgraw.test.automation.api.rest.base.model.IBaseNameValueRQ;
import com.mcgraw.test.automation.api.rest.endpoint.RestEndpoint;
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
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleServerError;
import com.mcgraw.test.automation.api.rest.moodle.rsmodel.MoodleUserRS;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class MoodleRestApi implements IMoodleRestApi {

	private RestEndpoint endpoint;
	
	private String resourceUrl;

	private Map<String, String> uriParams;

	public MoodleRestApi(RestEndpoint endpoint, String resourceUrl, Map<String, String> uriParams) {
		super();
		this.endpoint = endpoint;
		this.resourceUrl = resourceUrl;
		this.uriParams = uriParams;		
	}

	@Override
	public IRestServerError testErrorMessage() throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Testing moodle server message exception thrown");
		List<NameValuePair> emptyBodyParams = new ArrayList<NameValuePair>();
		return endpoint.post(resourceUrl, uriParams, emptyBodyParams, MoodleServerError.class);
	}

	@Override
	public MoodleUserRS createUser(MoodleAddUserRQ rq) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Creating Moodle user. " + rq.toString());
		MoodleUserRS[] addedUsers = formAndExecuteRequest(rq, MoodleFunction.CREATE_USER, MoodleUserRS[].class);
		Logger.info("Moodle user created successfully. " + addedUsers[0].toString());
		return addedUsers[0];
	}

	@Override
	public void deleteUser(MoodleDeleteUserRQ rq) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Deleting: " + rq.toString());
		formAndExecuteRequest(rq, MoodleFunction.DELETE_USER, String.class);
		Logger.info("Deleted: " + rq.toString());
	}

	@Override
	public MoodleCategoryRS createCategory(MoodleAddCategoryRQ rq) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Creating:" + rq.toString());
		MoodleCategoryRS[] addedCategories = formAndExecuteRequest(rq, MoodleFunction.CREATE_CATEGORY, MoodleCategoryRS[].class);
		Logger.info("Created: " + addedCategories[0].toString());
		return addedCategories[0];
	}

	@Override
	public MoodleCourseRS createCourse(MoodleAddCourseRQ rq) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Creating: " + rq.toString());
		MoodleCourseRS[] addedCourses = formAndExecuteRequest(rq, MoodleFunction.CREATE_COURSE, MoodleCourseRS[].class);
		Logger.info("Created: " + addedCourses[0].toString());
		return addedCourses[0];
	}

	@Override
	public void deleteCategoryWithCourses(MoodleDeleteCategoryRQ rq) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Deleting: " + rq.toString());
		formAndExecuteRequest(rq, MoodleFunction.DELETE_CATEGORY, String.class);
		Logger.info("Deleted: " + rq.toString());
	}

	@Override
	public void enrolUserToCourse(MoodleEnrolUserRQ rq) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Enrolling Moodle user:  id="	+ rq.getUserId() + " to the course: id=" + rq.getCourseId() + " with role " + MoodleUserRole.getByValue(rq.getRole()));
		formAndExecuteRequest(rq, MoodleFunction.ENROLL_USER_TO_COURSE, String.class);
		Logger.info("Moodle enrollment created successfully");
	}	

	/**
	 * Core function for forming and executing
	 * moodle requests
	 * @param rq
	 * @param function
	 * @param clazz
	 * @return Response body
	 * @throws RestEndpointIOException
	 * @throws UnsupportedEncodingException
	 */
	private <RS> RS formAndExecuteRequest(IBaseNameValueRQ rq, MoodleFunction function, Class<RS> clazz) throws RestEndpointIOException, UnsupportedEncodingException{
		uriParams.put("wsfunction", function.getValue());
		return endpoint.post(resourceUrl, uriParams, rq.getParams(), clazz);		
	}
	
	private enum MoodleFunction{
		CREATE_USER("core_user_create_users"),
		DELETE_USER("core_user_delete_users"),
		CREATE_CATEGORY("core_course_create_categories"),
		CREATE_COURSE("core_course_create_courses"),
		DELETE_CATEGORY("core_course_delete_categories"),
		ENROLL_USER_TO_COURSE("enrol_manual_enrol_users");
		
		private final String value;

		public String getValue() {
			return value;
		}

		private MoodleFunction(String value) {
			this.value = value;
		}

		public static MoodleFunction getByValue(String value) {
			for (MoodleFunction moodleFunction : values()){
				if (moodleFunction.getValue().equals(value)) {
					return moodleFunction;
				}
			}
			throw new IllegalArgumentException("Unable to find function with name [" + value + "]");
		}
		
	}	
}
