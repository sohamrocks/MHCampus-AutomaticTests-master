package com.mcgraw.test.automation.api.rest;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.angel.AngelSectionRole;
import com.mcgraw.test.automation.api.rest.angel.model.AddSectionModel;
import com.mcgraw.test.automation.api.rest.angel.model.AddUserModel;
import com.mcgraw.test.automation.api.rest.angel.service.AngelApiUtils;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;

@ContextConfiguration(locations = { "classpath:spring/test-api-context.xml" })
public class AngelRestApiTests extends AbstractTestNGSpringContextTests {

	@Autowired
	private AngelApiUtils angelApiUtils;

	private String password = "123qweA@";

	private AddUserModel instructor;
	private String instructorUserName;
	private String instructorFirstname;
	private String instructorLastname;

	private AddUserModel student1;
	private String student1UserName;
	private String student1Firstname;
	private String student1Lastname;

	private AddUserModel student2;
	private String student2UserName;
	private String student2Firstname;
	private String student2Lastname;

	private AddSectionModel course;
	/** The courseid should not exceeds 8 symbols*/
	String courseid;
	String courseName;

	@Test(description = "Test angel server exception thrown")
	public void checkTestSessionStart() throws JAXBException, RestEndpointIOException, UnsupportedEncodingException {

		String instructorRandom = getRandomString();
		String student1Random = getRandomString();
		String student2Random = getRandomString();
		String courseRandom = getRandomString();

		instructorUserName = "instructor" + instructorRandom;
		instructorFirstname = "instructorFirstname" + instructorRandom;
		instructorLastname = "instructorLastname" + instructorRandom;

		student1UserName = "student" + student1Random;
		student1Firstname = "studentFirstname" + student1Random;
		student1Lastname = "studentLastname" + student1Random;

		student2UserName = "student" + student2Random;
		student2Firstname = "studentFirstname" + student2Random;
		student2Lastname = "studentLastname" + student2Random;

		courseid = "id" + courseRandom;
		courseName = "courseName" + courseRandom;

		student1 = angelApiUtils.createStudent(student1UserName, password, student1Firstname, student1Lastname);
		student2 = angelApiUtils.createStudent(student2UserName, password, student2Firstname, student2Lastname);
		instructor = angelApiUtils.createInstructor(instructorUserName, password, instructorFirstname, instructorLastname);
		course = angelApiUtils.createCourse(instructor, courseid, courseName);

		angelApiUtils.enrolUserToCourse(student1, course, AngelSectionRole.STUDENT);
		angelApiUtils.enrolUserToCourse(student2, course, AngelSectionRole.STUDENT);

	}

	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}
}
