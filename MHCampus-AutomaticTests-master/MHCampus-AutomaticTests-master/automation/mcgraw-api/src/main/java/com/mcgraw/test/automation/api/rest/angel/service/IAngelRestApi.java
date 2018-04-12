package com.mcgraw.test.automation.api.rest.angel.service;

import java.io.UnsupportedEncodingException;

import com.mcgraw.test.automation.api.rest.angel.model.AddSectionModel;
import com.mcgraw.test.automation.api.rest.angel.model.AddUserModel;
import com.mcgraw.test.automation.api.rest.angel.model.EnrolUserModel;
import com.mcgraw.test.automation.api.rest.base.IRestApi;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;

public interface IAngelRestApi extends IRestApi{
	
	void createUser (AddUserModel addUserModel) throws RestEndpointIOException, UnsupportedEncodingException;
	
	void createCourse (AddSectionModel addCourseModel) throws RestEndpointIOException, UnsupportedEncodingException;
	
	void enrolUserToCourse(EnrolUserModel enrolUserModel)throws RestEndpointIOException, UnsupportedEncodingException;

}
