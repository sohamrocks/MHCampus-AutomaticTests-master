package com.mcgraw.test.automation.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi;
import com.mcgraw.test.automation.api.blackboard.serviceapi.BlackBoardApi.BlackboardApiRoleIdentifier;
import com.mcgraw.test.automation.framework.core.common.test_data.random_utils.StringUtils;

@ContextConfiguration(locations = { "classpath:spring/test-api-context.xml" })
public class BlackBoardApiTests extends AbstractTestNGSpringContextTests {

	private static String instructorName = "instr_name";
	private static String instructorSurname = "instr_surname";
	private static String studentName = "stud_name";
	private static String studentSurname = "stud_surname";

	@Autowired
	BlackBoardApi blackBoardApi;

	@BeforeClass
	public void testSuiteSetup() throws Exception {
		
		blackBoardApi.loginAndInitialiseBlackBoardServices();
	}

	@AfterClass
	public void testSuiteTearDown() throws Exception {

		blackBoardApi.logout();
	}


	@Test
	public void testCreateStudent() throws Exception {

		blackBoardApi.deleteAllUsersByLoginPrefix("auto_student");
		String student = "auto_student_" + StringUtils.randomAlphabetic(5).toUpperCase();
		String studentId = blackBoardApi.createUser(student, studentName, studentSurname);
		blackBoardApi.deleteUser(studentId);
	}

	@Test
	public void testCreateInstructor() throws Exception {

		String instructor = "auto_instructor_" + StringUtils.randomAlphabetic(5).toUpperCase();
		String instructorId = blackBoardApi.createUser(instructor, instructorName, instructorSurname);
		blackBoardApi.deleteUser(instructorId);
	}

	@Test
	public void testCreateCourse() throws Exception {

		String course = "auto_course_" + StringUtils.randomAlphabetic(5).toUpperCase();
		String courseId = blackBoardApi.createCourse(course);
		blackBoardApi.deleteUser(courseId);
	}

	@Test
	public void testCreateUsersCourseAndEnrollments() throws Exception {

		String student = "student_" + StringUtils.randomAlphabetic(5).toUpperCase();
		String instructor = "instructor_" + StringUtils.randomAlphabetic(5).toUpperCase();
		String course = "course_" + StringUtils.randomAlphabetic(5).toUpperCase();

		String studentId = blackBoardApi.createUser(student, studentName, studentSurname);
		String instructorId = blackBoardApi.createUser(instructor, instructorName, instructorSurname);
		String courseId = blackBoardApi.createCourse(course);

		blackBoardApi.createEnrollment(studentId, courseId, BlackboardApiRoleIdentifier.STUDENT);
		blackBoardApi.createEnrollment(instructorId, courseId, BlackboardApiRoleIdentifier.INSTRUCTOR);

		blackBoardApi.deleteCourse(courseId);
		blackBoardApi.deleteUser(studentId);
		blackBoardApi.deleteUser(instructorId);
	}
}