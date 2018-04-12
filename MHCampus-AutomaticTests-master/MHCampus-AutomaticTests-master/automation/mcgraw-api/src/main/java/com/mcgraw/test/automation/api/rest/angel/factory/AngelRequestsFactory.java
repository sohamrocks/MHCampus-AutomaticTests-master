package com.mcgraw.test.automation.api.rest.angel.factory;

import java.util.Random;

import com.mcgraw.test.automation.api.rest.angel.AngelSectionRole;
import com.mcgraw.test.automation.api.rest.angel.AngelSystemRole;
import com.mcgraw.test.automation.api.rest.angel.CourseType;
import com.mcgraw.test.automation.api.rest.angel.ValidCourseCategory;
import com.mcgraw.test.automation.api.rest.angel.ValidCourseSemester;
import com.mcgraw.test.automation.api.rest.angel.model.AddSectionModel;
import com.mcgraw.test.automation.api.rest.angel.model.AddUserModel;
import com.mcgraw.test.automation.api.rest.angel.model.EnrolUserModel;
import com.mcgraw.test.automation.framework.core.common.test_data.random_utils.StringUtils;

public class AngelRequestsFactory {

	/** 
	 * Enroll user to the course with angelSectionRoles
	 * 
	 * @param user
	 * @param course
	 * @param angelSectionRole
	 * @return
	 */
	public EnrolUserModel createEnrolUserModelRequest(AddUserModel user, AddSectionModel course, AngelSectionRole angelSectionRole) {
		return createEnrolUserModelRequest(user.getUserName(), course.getCourseId(), angelSectionRole);
	}

	/**
	 * Base method for enrolling users in Angel
	 * 
	 * @return request body representation
	 */
	public EnrolUserModel createEnrolUserModelRequest(String userName, String courseId, AngelSectionRole angelSectionRole) {
		EnrolUserModel enrolUserModel = new EnrolUserModel();
		enrolUserModel.setUserName(userName);
		enrolUserModel.setCourseId(courseId);
		enrolUserModel.setAngelSectionRole(angelSectionRole);
		return enrolUserModel;
	}

	/**
	 * Add course request body for created user
	 * 
	 * @param userModel
	 * @return
	 */
	public AddSectionModel createAddCourseRequest(AddUserModel userModel) {
		return createAddCourseRequest("epamcourseid_" + StringUtils.randomAlphabetic(5).toUpperCase(), "epamtitle"
				+ StringUtils.randomAlphabetic(5).toUpperCase(), CourseType.COURSE, ValidCourseCategory.TESTCATEGORY,
				ValidCourseSemester.TEST_SEMESTER, "epamcourseCode_" + StringUtils.randomAlphabetic(5).toUpperCase(),
				userModel.getUserName());
	}
	
	public AddSectionModel createAddCourseRequest(AddUserModel instructor, String courseId, String courseName) {
		return createAddCourseRequest(courseId, courseName, CourseType.COURSE, ValidCourseCategory.TESTCATEGORY,
				ValidCourseSemester.TEST_SEMESTER, "epamcourseCode_" + StringUtils.randomAlphabetic(5).toUpperCase(),
				instructor.getUserName());
	}

	/**
	 * Base method for creating sections (courses, categories, libraries) in
	 * Angel
	 * 
	 * @return request body representation
	 */
	public AddSectionModel createAddCourseRequest(String courseId, String title, CourseType courseType,
			ValidCourseCategory validCourseCategory, ValidCourseSemester validCourseSemester, String courseCode, String instructorId) {
		AddSectionModel addCourseModel = new AddSectionModel();
		addCourseModel.setCourseId(courseId);
		addCourseModel.setTitle(title);
		addCourseModel.setCourseType(courseType);
		addCourseModel.setValidCourseCategory(validCourseCategory);
		addCourseModel.setValidCourseSemester(validCourseSemester);
		addCourseModel.setCourseCode(courseCode);
		addCourseModel.setInstructorId(instructorId);
		addCourseModel.setSectionCode(courseId);
		return addCourseModel;

	}

	/**
	 * Create Administrator user request body
	 * 
	 * @return request body representation
	 */
	public AddUserModel createAddAdministratorUserRequest() {
		return createAddUserRequest("epamadmin_" + new Random().nextInt(100000), "123qweA#",
				"epamadminfirst_" + StringUtils.randomAlphabetic(5), "epamadminlast_" + StringUtils.randomAlphabetic(5),
				AngelSystemRole.SYSTEM_ADMINISTRATOR);
	}

	/**
	 * Create Faculty user request body
	 * 
	 * @return request body representation
	 */
	public AddUserModel createAddFacultyUserRequest() {
		return createAddUserRequest("epamfaculty_" + new Random().nextInt(100000), "123qweA#",
				"epamfacultyfirst_" + StringUtils.randomAlphabetic(5), "epamfacultylast_" + StringUtils.randomAlphabetic(5),
				AngelSystemRole.FACULTY);
	}

	/**
	 * Create Student user request body
	 * 
	 * @return request body representation
	 */
	public AddUserModel createAddStudentUserRequest() {
		return createAddUserRequest("epamstudent_" + new Random().nextInt(100000), "123qweA#",
				"epamstudentfirst_" + StringUtils.randomAlphabetic(5), "epamstudentlast_" + StringUtils.randomAlphabetic(5),
				AngelSystemRole.STUDENT);
	}

	/**
	 * Base method for creating users in Angel
	 * 
	 * @return request body representation
	 */
	public AddUserModel createAddUserRequest(String userName, String password, String firstName, String lastName, AngelSystemRole systemRole) {
		AddUserModel addUserModel = new AddUserModel();
		addUserModel.setUserName(userName);
		addUserModel.setPassword(password);
		addUserModel.setFirstName(firstName);
		addUserModel.setLastName(lastName);
		addUserModel.setSystemRole(systemRole);
		return addUserModel;
	}

}
