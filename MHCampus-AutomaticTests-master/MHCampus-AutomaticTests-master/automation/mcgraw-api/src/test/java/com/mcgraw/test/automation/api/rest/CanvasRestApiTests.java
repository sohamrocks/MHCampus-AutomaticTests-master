package com.mcgraw.test.automation.api.rest;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUser;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserEnrollmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.api.rest.canvas.service.CanvasApiUtils;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;

@ContextConfiguration(locations = { "classpath:spring/test-api-context.xml" })
public class CanvasRestApiTests extends AbstractTestNGSpringContextTests{
	
	@Autowired
	private CanvasApiUtils canvasApiUtils;
	
	private String studentRandom = getRandomString();
	private String instructorRandom = getRandomString();
	private String courseRandom = getRandomString();
	
	private String studentLogin = "student" + studentRandom;
	private String studentName = "StudentName" + studentRandom;	

	private String teacherLogin = "instructor" + instructorRandom;
	private String teacherName = "InstructorName" + instructorRandom;

	private String courseName = "CourseName" + courseRandom;
		
	private String password = "123qweA@";
	
	private CanvasUser student;
	private CanvasUser teacher;
	private CanvasCourseRS course;
	private CanvasUserEnrollmentRS studentEnrollment;
	private CanvasUserEnrollmentRS teacherEnrollment;
	
	
	@Test(description = "Test canvas works")
	public void checkTestApi() throws JAXBException, RestEndpointIOException, UnsupportedEncodingException {
		student = canvasApiUtils.createUser(studentLogin, password, studentName);
		teacher = canvasApiUtils.createUser(teacherLogin, password, teacherName);
		course = canvasApiUtils.createPublishedCourse(courseName);
		studentEnrollment = canvasApiUtils.enrollToCourseAsActiveStudent(student, course);
		teacherEnrollment = canvasApiUtils.enrollToCourseAsActiveTeacher(teacher, course);
	}
	
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		canvasApiUtils.deleteEnrollment(studentEnrollment, course);
		canvasApiUtils.deleteEnrollment(teacherEnrollment, course);
		canvasApiUtils.deleteCourse(course);
		if(student != null) {
			CanvasUserRS studentToDelete = new CanvasUserRS();
			studentToDelete.setUser(student);
			canvasApiUtils.deleteUser(studentToDelete);
		}	
		if(teacher != null) {
			CanvasUserRS instructorToDelete = new CanvasUserRS();
			instructorToDelete.setUser(teacher);
			canvasApiUtils.deleteUser(instructorToDelete);
		}	
	}
	
	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}

}
