package com.mcgraw.test.automation.api.rest.canvas.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentState;
import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentType;
import com.mcgraw.test.automation.api.rest.canvas.CanvasRemoveState;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasAddCourseRQ;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasUserRQ;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasAssignmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasSubmissionRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUser;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserEnrollmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;

public interface ICanvasRestApi {

	CanvasUser createUser(CanvasUserRQ rq) throws RestEndpointIOException, UnsupportedEncodingException;

	CanvasCourseRS createCourse(CanvasAddCourseRQ rq) throws RestEndpointIOException, UnsupportedEncodingException;

	CanvasUserEnrollmentRS enrollUserToCourse(CanvasUser user, CanvasCourseRS course, CanvasEnrollmentType role,
			CanvasEnrollmentState canvasEnrollmentState) throws RestEndpointIOException, UnsupportedEncodingException;

	void removeEnrollment(CanvasUserEnrollmentRS enrollment, CanvasCourseRS course,
			CanvasRemoveState canvasRemoveState) throws RestEndpointIOException, UnsupportedEncodingException;
	
	void removeCourse(CanvasCourseRS course, CanvasRemoveState canvasRemoveState)throws RestEndpointIOException, UnsupportedEncodingException;
	
	void deleteUser(CanvasUserRS user) throws RestEndpointIOException, UnsupportedEncodingException;
	
	public CanvasAssignmentRS getCourseAssignmentByAssignmentId(CanvasCourseRS course, int assignmentId) throws RestEndpointIOException; 
	
	CanvasAssignmentRS getCourseAssignmentByTitle(CanvasCourseRS course, String assignmentTitle) throws RestEndpointIOException;
	
	int getCountOfCourseAssignmentByTitle(CanvasCourseRS course, String assignmentTitle) throws RestEndpointIOException;
	
	List<CanvasSubmissionRS> getAssignmentSubmissionForUser(CanvasAssignmentRS assignment, CanvasUser user) throws RestEndpointIOException;

}

