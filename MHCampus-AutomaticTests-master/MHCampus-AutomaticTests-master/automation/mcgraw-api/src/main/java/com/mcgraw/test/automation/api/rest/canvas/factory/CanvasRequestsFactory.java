package com.mcgraw.test.automation.api.rest.canvas.factory;

import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentState;
import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentType;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasAddCourseRQ;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasAddCourseRQ.CoursePart;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasEnrollUserRQ;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasEnrollUserRQ.EnrollmentBlock;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasUserRQ;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasUserRQ.Pseudonym;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasUserRQ.UserPart;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.framework.core.common.test_data.random_utils.StringUtils;

public class CanvasRequestsFactory {

	/**
	 * By default in UI full, short and sortable names are the same.
	 * Replicate it via api
	 * 
	 * @param login
	 * @param password
	 *            - don't use '#' in password
	 * @param name
	 * @return
	 */
	public CanvasUserRQ createAddUserRequest(String login, String password, String name) {
		return createAddUserRequest(login, password, name, name, name);
	}
	
	public CanvasUserRQ createAddUserRequest(String login, String password, String name, String sis_id) {
		return createAddUserRequest(login, password, name, name, name, sis_id);
	}

	/**
	 * Base method for creating json for createUser request
	 * 
	 * @param uniqueId
	 * @param password
	 *            - don't use '#' in password
	 * @param fullName
	 * @param shortName
	 * @param sortableName
	 * @return json representaion
	 */
	public CanvasUserRQ createAddUserRequest(String uniqueId, String password, String fullName, String shortName, String sortableName) {
		CanvasUserRQ canvasUserRQ = new CanvasUserRQ();
		Pseudonym pseudonym = new Pseudonym();
		UserPart userPart = new UserPart();
		pseudonym.setUniqueId(uniqueId);
		pseudonym.setPassword(password);
		userPart.setFullName(fullName);
		userPart.setShortName(shortName);
		userPart.setSortableName(sortableName);
		canvasUserRQ.setPseudonym(pseudonym);
		canvasUserRQ.setUserPart(userPart);
		return canvasUserRQ;
	}
	
	public CanvasUserRQ createAddUserRequest(String uniqueId, String password, String fullName, String shortName, String sortableName, String sis_id) {
		CanvasUserRQ canvasUserRQ = new CanvasUserRQ();
		Pseudonym pseudonym = new Pseudonym();
		UserPart userPart = new UserPart();
		pseudonym.setUniqueId(uniqueId);
		pseudonym.setPassword(password);
		pseudonym.setSisId(sis_id);
		userPart.setFullName(fullName);
		userPart.setShortName(shortName);
		userPart.setSortableName(sortableName);
		canvasUserRQ.setPseudonym(pseudonym);
		canvasUserRQ.setUserPart(userPart);
		return canvasUserRQ;
	}

	/**
	 * Create published course accountId = 0 and will be setup later via request
	 * 
	 * @return json representaion
	 */
	public CanvasAddCourseRQ createAddCourseRequest(String courseName, boolean isPublished) {
		String numericRandom = StringUtils.randomNumeric(5);
		return createAddCourseRequest(0, "cod" + numericRandom, courseName, isPublished);
	}
	
	public CanvasAddCourseRQ createAddCourseRequest(String courseName, String courseSis, boolean isPublished) {
		String numericRandom = StringUtils.randomNumeric(5);
		return createAddCourseRequestWithSis(0, "cod" + numericRandom, courseName, courseSis, isPublished);
	}
	/**
	 * Base method for creating json for createCourse request
	 * 
	 * @param accountId
	 * @param courseCode
	 *            - max length is 8
	 * @param courseName
	 * @param isPublished
	 * @return json representaion
	 */
	public CanvasAddCourseRQ createAddCourseRequest(int accountId, String courseCode, String courseName, boolean isPublished) {
		CanvasAddCourseRQ canvasAddCourseRQ = new CanvasAddCourseRQ();
		canvasAddCourseRQ.setAccountId(accountId);
		CoursePart coursePart = new CoursePart();
		coursePart.setCourseCode(courseCode);
		coursePart.setName(courseName);
		canvasAddCourseRQ.setCoursePart(coursePart);
		canvasAddCourseRQ.setPublished(isPublished);
		return canvasAddCourseRQ;
	}
	
	/**
	 * Base method for creating json for createCourse request for courses with sis
	 * 
	 * @param accountId
	 * @param courseCode
	 *            - max length is 8
	 * @param courseName
	 * @param courseSis
	 * @param isPublished
	 * @return json representaion
	 */
	public CanvasAddCourseRQ createAddCourseRequestWithSis(int accountId, String courseCode, String courseName, String courseSis, boolean isPublished) {
		CanvasAddCourseRQ canvasAddCourseRQ = new CanvasAddCourseRQ();
		canvasAddCourseRQ.setAccountId(accountId);
		//canvasAddCourseRQ.setCourseSis(courseSis);		
		CoursePart coursePart = new CoursePart();
		coursePart.setCourseCode(courseCode);
		coursePart.setName(courseName);
		coursePart.setCourseSis(courseSis);
		canvasAddCourseRQ.setCoursePart(coursePart);
		canvasAddCourseRQ.setPublished(isPublished);
		return canvasAddCourseRQ;
	}	

	public CanvasEnrollUserRQ createEnrollUserRequest(CanvasUserRS userRs, CanvasEnrollmentType canvasEnrollmentType,
			CanvasEnrollmentState canvasEnrollmentState) {
		return createEnrollUserRequest(userRs.getUser().getId(), canvasEnrollmentType, canvasEnrollmentState);
	}

	public CanvasEnrollUserRQ createEnrollUserRequest(int userId, CanvasEnrollmentType canvasEnrollmentType,
			CanvasEnrollmentState canvasEnrollmentState) {
		CanvasEnrollUserRQ canvasEnrollUserRQ = new CanvasEnrollUserRQ();
		EnrollmentBlock enrollmentBlock = new EnrollmentBlock();
		enrollmentBlock.setUserId(userId);
		enrollmentBlock.setCanvasEnrollmentType(canvasEnrollmentType);
		enrollmentBlock.setCanvasEnrollmentState(canvasEnrollmentState);
		canvasEnrollUserRQ.setEnrollment(enrollmentBlock);

		return canvasEnrollUserRQ;
	}

}
