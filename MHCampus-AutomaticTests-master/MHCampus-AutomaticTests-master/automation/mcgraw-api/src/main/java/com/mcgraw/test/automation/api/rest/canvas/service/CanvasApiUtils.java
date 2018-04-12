package com.mcgraw.test.automation.api.rest.canvas.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentState;
import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentType;
import com.mcgraw.test.automation.api.rest.canvas.CanvasRemoveState;
import com.mcgraw.test.automation.api.rest.canvas.factory.CanvasRequestsFactory;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasAssignmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasSubmissionRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUser;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserEnrollmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;

public class CanvasApiUtils {

	private CanvasRestApi canvasRestApi;

	private CanvasRequestsFactory canvasRequestsFactory;

	private CanvasApiUtils(CanvasRestApi canvasRestApi, CanvasRequestsFactory canvasRequestsFactory) {
		super();
		this.canvasRestApi = canvasRestApi;
		this.canvasRequestsFactory = canvasRequestsFactory;
	}

	public CanvasUser createUser(String login, String password, String name) throws RestEndpointIOException, UnsupportedEncodingException {
		return canvasRestApi.createUser(canvasRequestsFactory.createAddUserRequest(login, password, name));
	}
	// added
	public CanvasUser createUserWithSis(String login, String password, String name ,String Sisid) throws RestEndpointIOException, UnsupportedEncodingException {
		return canvasRestApi.createUser(canvasRequestsFactory.createAddUserRequest(login, password, name,Sisid));
	}

	public CanvasCourseRS createPublishedCourse(String courseName) throws RestEndpointIOException, UnsupportedEncodingException {
		return canvasRestApi.createCourse(canvasRequestsFactory.createAddCourseRequest(courseName, true));
	}

	public CanvasCourseRS createPublishedCourseWithSis(String courseName, String courseSis) throws RestEndpointIOException, UnsupportedEncodingException {
		return canvasRestApi.createCourse(canvasRequestsFactory.createAddCourseRequest(courseName, courseSis, true));
	}
	
	public CanvasCourseRS createUnpublishedCourse(String courseName) throws RestEndpointIOException, UnsupportedEncodingException {
		return canvasRestApi.createCourse(canvasRequestsFactory.createAddCourseRequest(courseName, false));
	}

	public CanvasUserEnrollmentRS enrollToCourseAsActiveStudent(CanvasUser user, CanvasCourseRS course) throws RestEndpointIOException,
			UnsupportedEncodingException {
		return canvasRestApi.enrollUserToCourse(user, course, CanvasEnrollmentType.STUDENT_ENROLLMENT, CanvasEnrollmentState.ACTIVE);
	}

	public CanvasUserEnrollmentRS enrollToCourseAsActiveTeacher(CanvasUser user, CanvasCourseRS course) throws RestEndpointIOException,
			UnsupportedEncodingException {
		return canvasRestApi.enrollUserToCourse(user, course, CanvasEnrollmentType.TEACHER_ENROLLMENT, CanvasEnrollmentState.ACTIVE);
	}

	public CanvasUserEnrollmentRS enrollToCourseAsInvitedStudent(CanvasUser user, CanvasCourseRS course) throws RestEndpointIOException,
			UnsupportedEncodingException {
		return canvasRestApi.enrollUserToCourse(user, course, CanvasEnrollmentType.STUDENT_ENROLLMENT, CanvasEnrollmentState.INVITED);
	}

	public CanvasUserEnrollmentRS enrollToCourseAsInvitedTeacher(CanvasUser user, CanvasCourseRS course) throws RestEndpointIOException,
			UnsupportedEncodingException {
		return canvasRestApi.enrollUserToCourse(user, course, CanvasEnrollmentType.TEACHER_ENROLLMENT, CanvasEnrollmentState.INVITED);
	}
	
	public void deleteEnrollment(CanvasUserEnrollmentRS enrollment, CanvasCourseRS course) throws RestEndpointIOException, UnsupportedEncodingException{
		canvasRestApi.removeEnrollment(enrollment, course, CanvasRemoveState.DELETE);
	}
	
	public void concludeEnrollment(CanvasUserEnrollmentRS enrollment, CanvasCourseRS course) throws RestEndpointIOException, UnsupportedEncodingException{
		canvasRestApi.removeEnrollment(enrollment, course, CanvasRemoveState.CONCLUDE);
	}
	
	public void deleteCourse(CanvasCourseRS course) throws RestEndpointIOException, UnsupportedEncodingException{
		canvasRestApi.removeCourse(course, CanvasRemoveState.DELETE);
	}
	
	public void concludeCourse(CanvasCourseRS course) throws RestEndpointIOException, UnsupportedEncodingException{
		canvasRestApi.removeCourse(course, CanvasRemoveState.CONCLUDE);
	}

	public void deleteUser(CanvasUserRS user) throws RestEndpointIOException, UnsupportedEncodingException {
		canvasRestApi.deleteUser(user);
	}
	
	public CanvasAssignmentRS getCourseAssignmentByAssignmentId(CanvasCourseRS course, int assignmentId) throws RestEndpointIOException{
		return canvasRestApi.getCourseAssignmentByAssignmentId(course, assignmentId);
	}
	
	public CanvasAssignmentRS getCourseAssignmentByTitle(CanvasCourseRS course, String assignmentTitle) throws RestEndpointIOException{
		return canvasRestApi.getCourseAssignmentByTitle(course, assignmentTitle);
	}
	
	public int getCountOfCourseAssignmentByTitle(CanvasCourseRS course, String assignmentTitle) throws RestEndpointIOException{
		return canvasRestApi.getCountOfCourseAssignmentByTitle(course, assignmentTitle);
	}
	
	public List<CanvasSubmissionRS> getSubmissionAssignmentForUser(CanvasAssignmentRS assignment, CanvasUser user) throws RestEndpointIOException{
		return canvasRestApi.getAssignmentSubmissionForUser(assignment, user);
	}

}

