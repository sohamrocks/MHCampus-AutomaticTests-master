package com.mcgraw.test.automation.api.rest.angel.service;

import java.io.UnsupportedEncodingException;

import com.mcgraw.test.automation.api.rest.angel.AngelSectionRole;
import com.mcgraw.test.automation.api.rest.angel.AngelSystemRole;
import com.mcgraw.test.automation.api.rest.angel.factory.AngelRequestsFactory;
import com.mcgraw.test.automation.api.rest.angel.model.AddSectionModel;
import com.mcgraw.test.automation.api.rest.angel.model.AddUserModel;
import com.mcgraw.test.automation.api.rest.angel.model.EnrolUserModel;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;

public class AngelApiUtils {
	
	private AngelRestApi angelRestApi;
	
	private AngelRequestsFactory angelRequestsFactory;

	public AngelApiUtils(AngelRestApi angelRestApi, AngelRequestsFactory angelRequestsFactory) {
		this.angelRestApi = angelRestApi;
		this.angelRequestsFactory = angelRequestsFactory;
	}
	
	public AddUserModel createUser(String userName, String password, String firstName, String lastName, AngelSystemRole systemRole) throws RestEndpointIOException, UnsupportedEncodingException{
		AddUserModel addUserModel = angelRequestsFactory.createAddUserRequest(userName, password, firstName, lastName, systemRole);
		angelRestApi.createUser(addUserModel);
		return addUserModel;
	}
	
	public AddUserModel createStudent(String userName, String password, String firstName, String lastName) throws RestEndpointIOException, UnsupportedEncodingException{
		return createUser(userName, password, firstName, lastName, AngelSystemRole.STUDENT);
	}
	
	public AddUserModel createInstructor(String userName, String password, String firstName, String lastName) throws RestEndpointIOException, UnsupportedEncodingException{
		return createUser(userName, password, firstName, lastName, AngelSystemRole.FACULTY);
	}
	
	public AddSectionModel createCourse(AddUserModel instructor, String courseId, String courseName) throws RestEndpointIOException, UnsupportedEncodingException{
		AddSectionModel addSectionModel = angelRequestsFactory.createAddCourseRequest(instructor, courseId, courseName);
		angelRestApi.createCourse(addSectionModel);
		return addSectionModel;
	}
	
	public void enrolUserToCourse (AddUserModel user, AddSectionModel course, AngelSectionRole angelSectionRole) throws RestEndpointIOException, UnsupportedEncodingException{
		EnrolUserModel enrolUserModel = angelRequestsFactory.createEnrolUserModelRequest(user, course, angelSectionRole);
		angelRestApi.enrolUserToCourse(enrolUserModel);
	}
	
	
}
