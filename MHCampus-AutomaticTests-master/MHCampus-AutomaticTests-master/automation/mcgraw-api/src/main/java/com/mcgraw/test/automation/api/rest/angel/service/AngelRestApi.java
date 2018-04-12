package com.mcgraw.test.automation.api.rest.angel.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;

import com.mcgraw.test.automation.api.rest.angel.model.AddSectionModel;
import com.mcgraw.test.automation.api.rest.angel.model.AddUserModel;
import com.mcgraw.test.automation.api.rest.angel.model.EnrolUserModel;
import com.mcgraw.test.automation.api.rest.angel.xml.AngelServerError;
import com.mcgraw.test.automation.api.rest.angel.xml.SuccessfulResult;
import com.mcgraw.test.automation.api.rest.base.IRestServerError;
import com.mcgraw.test.automation.api.rest.endpoint.RestEndpoint;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * Implementation of angel rest api client
 * 
 * @author Andrei_Turavets
 * 
 */
public class AngelRestApi implements IAngelRestApi {

	private RestEndpoint endpoint;

	private String resourceUrl;

	private List<NameValuePair> creds;

	public AngelRestApi(RestEndpoint endpoint, String resourceUrl, List<NameValuePair> creds) {
		this.endpoint = endpoint;
		this.resourceUrl = resourceUrl;
		this.creds = creds;
	}

	@Override
	public void createUser(AddUserModel addUserModel) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Creating angel user");
		SuccessfulResult result = formAndExecuteRequest(createApiAction(AngelApiAction.CREATE_ACCOUNT), addUserModel.getParams(),
				SuccessfulResult.class);
		Logger.info("Response is " + result.getSuccessfullMessage() + ". " + addUserModel.toString());
	}

	@Override
	public void createCourse(AddSectionModel addCourseModel) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Creating angel course");
		SuccessfulResult result = formAndExecuteRequest(createApiAction(AngelApiAction.CREATE_UPDATE_SECTION), addCourseModel.getParams(),
				SuccessfulResult.class);
		Logger.info("Response is " + result.getSuccessfullMessage() + ". " + addCourseModel.toString());
		Logger.info("Initialize angel course [" + addCourseModel.getCourseId() + "]");
		sessionStart();
		sectionEnter(addCourseModel.getCourseId());
		sectionExit();
		sessionEnd();

	}

	@Override
	public void enrolUserToCourse(EnrolUserModel enrolUserModel) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Enrolling user");
		SuccessfulResult result = formAndExecuteRequest(createApiAction(AngelApiAction.ENROLL_USER), enrolUserModel.getParams(),
				SuccessfulResult.class);
		Logger.info("Response is " + result.getSuccessfullMessage() + ". " + enrolUserModel.toString());

	}


	private void sessionStart() throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Start interactive session");
		SuccessfulResult result = formAndExecuteRequest(createApiAction(AngelApiAction.SESSION_START), null, SuccessfulResult.class);
		Logger.info("Response is " + result.getSuccessfullMessage() + ". ");
	}

	private void sessionEnd() throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("End interactive session");
		SuccessfulResult result = formAndExecuteRequest(createApiAction(AngelApiAction.SESSION_END), null, SuccessfulResult.class);
		Logger.info("Response is " + result.getSuccessfullMessage() + ". ");
	}
	
	private void sectionEnter(String courseId) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Trying to enter section (course) ["+courseId+"]");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("SECTION", courseId));
		
		SuccessfulResult result = formAndExecuteRequest(createApiAction(AngelApiAction.SECTION_ENTER), params, SuccessfulResult.class);
		Logger.info("Response is " + result.getSuccessfullMessage() + ". ");
	}
	
	private void sectionExit() throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Exit section(course)");
		SuccessfulResult result = formAndExecuteRequest(createApiAction(AngelApiAction.SECTION_EXIT), null, SuccessfulResult.class);
		Logger.info("Response is " + result.getSuccessfullMessage() + ". ");
	}

	/**
	 * Forming the body of any request by adding credentials and execute it
	 * 
	 * @param bodyParams
	 * @param clazz
	 * @return Response body
	 * @throws RestEndpointIOException
	 * @throws UnsupportedEncodingException
	 */
	private <RS> RS formAndExecuteRequest(NameValuePair apiaction, List<NameValuePair> bodyParams, Class<RS> clazz)
			throws RestEndpointIOException, UnsupportedEncodingException {
		List<NameValuePair> fullBodyParams = new ArrayList<NameValuePair>();
		fullBodyParams.add(apiaction);
		fullBodyParams.addAll(creds);
		if (bodyParams != null) {
			fullBodyParams.addAll(bodyParams);
		}
		return endpoint.post(resourceUrl, fullBodyParams, clazz);
	}

	/**
	 * Create BasicNameValuePair for APIACTION element of request body
	 * 
	 * @param apiAction
	 * @return APIACTION element for request body
	 */
	private NameValuePair createApiAction(AngelApiAction apiAction) {
		return new BasicNameValuePair("APIACTION", apiAction.toString());
	}

	public AngelRestApi() {
		super();
	}

	@Override
	public IRestServerError testErrorMessage() throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Testing angel server message exception thrown");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("APIACTION", "SESSION_START"));
		params.add(new BasicNameValuePair("APIUSER", "wrongUser"));
		params.add(new BasicNameValuePair("APIPWD", "wrongPassword"));
		return endpoint.post(resourceUrl, params, AngelServerError.class);
	}

	private enum AngelApiAction {
		CREATE_ACCOUNT,
		CREATE_UPDATE_SECTION,
		ENROLL_USER,
		SESSION_START,
		SECTION_ENTER,
		SECTION_EXIT,
		SESSION_END;
	}

}
