package com.mcgraw.test.automation.api.rest.canvas.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentState;
import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentType;
import com.mcgraw.test.automation.api.rest.canvas.CanvasRemoveState;
import com.mcgraw.test.automation.api.rest.canvas.exception.AssignmentNotFound;
import com.mcgraw.test.automation.api.rest.canvas.exception.SubmissionNotFoundException;
import com.mcgraw.test.automation.api.rest.canvas.factory.CanvasRequestsFactory;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasAddCourseRQ;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasCourseRemoveEventRQ;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasEnrollUserRQ;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasEnrollmentRemoveTaskRQ;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasUserRQ;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasAccountRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasAssignmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasDeleteRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasSubmissionRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUser;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserEnrollmentRS;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasUserRS;
import com.mcgraw.test.automation.api.rest.endpoint.RestEndpoint;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class CanvasRestApi implements ICanvasRestApi {

	private RestEndpoint endpoint;
    private CanvasRequestsFactory canvasRequestsFactory;

	public void setCanvasRequestsFactory(CanvasRequestsFactory canvasRequestsFactory) {
		this.canvasRequestsFactory = canvasRequestsFactory;
	}

	public CanvasRestApi(RestEndpoint endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public CanvasUser createUser(CanvasUserRQ rq) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Creating canvas user");
		CanvasUser canvasUser = endpoint.post("/api/v1/accounts/self/users", rq, CanvasUser.class);
		Logger.info("Created: " + canvasUser.toString());
		return canvasUser;
	}

	@Override
	public CanvasCourseRS createCourse(CanvasAddCourseRQ rq) throws RestEndpointIOException, UnsupportedEncodingException {
		int accountId = getSelf().getId();
		rq.setAccountId(accountId);
		Logger.info("Creating canvas course");
		CanvasCourseRS canvasCourseRS = endpoint.post("/api/v1/accounts/" + accountId + "/courses", rq, CanvasCourseRS.class);
		Logger.info("Created: " + canvasCourseRS.toString());
		return canvasCourseRS;
	}

	@Override
	public CanvasUserEnrollmentRS enrollUserToCourse(CanvasUser user, CanvasCourseRS course, CanvasEnrollmentType role,
			CanvasEnrollmentState canvasEnrollmentState) throws RestEndpointIOException, UnsupportedEncodingException {		
		CanvasEnrollUserRQ canvasEnrollUserRQ = canvasRequestsFactory.createEnrollUserRequest(user.getId(), role, canvasEnrollmentState);
		Logger.info("Creating canvas enrollment");
		CanvasUserEnrollmentRS canvasUserEnrollmentRS = endpoint.post("/api/v1/courses/" + course.getId() + "/enrollments",
				canvasEnrollUserRQ, CanvasUserEnrollmentRS.class);
		Logger.info("Enrollment created. User: id=" + user.getId() + " was enrolled to the course: id=" + course.getId() + " with role " + role.getValue());
		return canvasUserEnrollmentRS;
	}

	@Override
	public void removeEnrollment(CanvasUserEnrollmentRS enrollment, CanvasCourseRS course, CanvasRemoveState canvasRemoveState)
			throws RestEndpointIOException, UnsupportedEncodingException {
		CanvasEnrollmentRemoveTaskRQ canvasEnrollmentRemoveTaskRQ = new CanvasEnrollmentRemoveTaskRQ();
		canvasEnrollmentRemoveTaskRQ.setCanvasRemoveState(canvasRemoveState);
		Logger.info("Deleting canvas enrollment");
		try{
			CanvasUserEnrollmentRS canvasUserEnrollmentAfterDelete = endpoint.delete("/api/v1/courses/" + course.getId() + "/enrollments/"
					+ enrollment.getId(), canvasEnrollmentRemoveTaskRQ, CanvasUserEnrollmentRS.class);
			Logger.info("Canvas enrollment deleted successfully. " + canvasUserEnrollmentAfterDelete.toString());
		}catch(Exception e){
			Logger.info("Cannot delete the canvas enrollment: " + enrollment.toString());
			Logger.info("The problem caused by:\n"+e.toString());
		}
	}

	@Override
	public void removeCourse(CanvasCourseRS course, CanvasRemoveState canvasRemoveState) throws RestEndpointIOException,
			UnsupportedEncodingException {
		CanvasCourseRemoveEventRQ canvasEnrollmentRemoveTaskRQ = new CanvasCourseRemoveEventRQ();
		canvasEnrollmentRemoveTaskRQ.setCanvasRemoveState(canvasRemoveState);
		Logger.info("Deleting canvas course");
		try{
			CanvasDeleteRS canvasDeleteRS = endpoint.delete("/api/v1/courses/" + course.getId(), canvasEnrollmentRemoveTaskRQ,
				CanvasDeleteRS.class);
			Logger.info("Canvas course deleted successfully: " + course.toString() + canvasDeleteRS.toString());
		}catch(Exception e){
			Logger.info("Cannot delete the course: " + course.toString());
			Logger.info("The problem caused by:\n"+e.toString());
		}
	}

	@Override
	public void deleteUser(CanvasUserRS user) throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Deleting canvas user");
		try{
			endpoint.delete("/api/v1/accounts/self/users/" + user.getUser().getId(), CanvasUserRS.class);
			Logger.info("Canvas user deleted successfully. " + user.toString());
		}catch(Exception e){
			Logger.info("Cannot delete the user: " + user.toString());
			Logger.info("The problem caused by:\n"+e.toString());
		}
	}

	/**
	 * Get the data about the account which currently uses api
	 * 
	 * @return json represntation user data
	 * 
	 * @throws RestEndpointIOException
	 * @throws UnsupportedEncodingException
	 */
	private CanvasAccountRS getSelf() throws RestEndpointIOException, UnsupportedEncodingException {
		Logger.info("Get data of currently logged in account");
		CanvasAccountRS canvasAccountRS = endpoint.get("/api/v1/accounts/self", CanvasAccountRS.class);
		Logger.info("The current account is: " + canvasAccountRS.toString());
		return canvasAccountRS;
	}

	@Override
	public CanvasAssignmentRS getCourseAssignmentByTitle(CanvasCourseRS course, String assignmentTitle) throws RestEndpointIOException {
		Logger.info("Get list of assignments for course: id=" + course.getId());
		CanvasAssignmentRS[] assignments = endpoint.get("/api/v1/courses/" + course.getId() + "/assignments", CanvasAssignmentRS[].class);
		List<CanvasAssignmentRS> assignmentsList = Arrays.asList(assignments);
		Logger.info("Found "+assignmentsList.size()+" assignments");
		Logger.info("Get assignment with title [" + assignmentTitle + "] from found data");
		List<CanvasAssignmentRS> assignmentsFoundByTitle = new ArrayList<CanvasAssignmentRS>();
		for (CanvasAssignmentRS assignment : assignmentsList) {
			if (assignment.getTitle().equals(assignmentTitle)) {
				Logger.info("Assignment found: " + assignment.toString());
				assignmentsFoundByTitle.add(assignment);
			}
		}
		if (assignmentsFoundByTitle.size() == 0) {
			throw new AssignmentNotFound("Assignment with title [" + assignmentTitle + "] not found");
		} else if (assignmentsFoundByTitle.size() > 1) {
			throw new CommonTestRuntimeException("The amount of found assignments by title [" + assignmentTitle + "]: "
					+ assignmentsFoundByTitle.size() + ", should be 1");
		}

		return assignmentsFoundByTitle.get(0);

	}
	
	@Override
	public CanvasAssignmentRS getCourseAssignmentByAssignmentId(CanvasCourseRS course, int assignmentId) throws RestEndpointIOException {
		Logger.info("Get list of assignments for course: id=" + course.getId());
		CanvasAssignmentRS[] assignments = endpoint.get("/api/v1/courses/" + course.getId() + "/assignments", CanvasAssignmentRS[].class);
		List<CanvasAssignmentRS> assignmentsList = Arrays.asList(assignments);
		Logger.info("Found "+assignmentsList.size()+" assignments");
		Logger.info("Get assignment with assignment id [" + assignmentId + "] from found data");
		List<CanvasAssignmentRS> assignmentsFoundById = new ArrayList<CanvasAssignmentRS>();
		for (CanvasAssignmentRS assignment : assignmentsList) {
			if (assignment.getId() == assignmentId) {
				Logger.info("Assignment found: " + assignment.toString());
				assignmentsFoundById.add(assignment);
			}
		}
		if (assignmentsFoundById.size() == 0) {
			throw new AssignmentNotFound("Assignment with assignment id [" + assignmentId + "] not found");
		} else if (assignmentsFoundById.size() > 1) {
			throw new CommonTestRuntimeException("The amount of found assignments by assignment id [" + assignmentId + "]: "
					+ assignmentsFoundById.size() + ", should be 1");
		}

		return assignmentsFoundById.get(0);

	}
	
	@Override
	public int getCountOfCourseAssignmentByTitle(CanvasCourseRS course, String assignmentTitle) throws RestEndpointIOException {
		Logger.info("Get list of assignments for course: id=" + course.getId());
		CanvasAssignmentRS[] assignments = endpoint.get("/api/v1/courses/" + course.getId() + "/assignments", CanvasAssignmentRS[].class);
		List<CanvasAssignmentRS> assignmentsList = Arrays.asList(assignments);
		Logger.info("Found "+assignmentsList.size()+" assignments");
		Logger.info("Get assignment with title [" + assignmentTitle + "] from found data");
		int counter = 0;
		for (CanvasAssignmentRS assignment : assignmentsList) {
			if (assignment.getTitle().equals(assignmentTitle)) {
				Logger.info("Assignment found: " + assignment.toString());
				counter++;
			}
		}
		if (counter == 0) {
			throw new AssignmentNotFound("Assignment with title [" + assignmentTitle + "] not found");
		}

		return counter;

	}

	@Override
	public List<CanvasSubmissionRS> getAssignmentSubmissionForUser(CanvasAssignmentRS assignment, CanvasUser user)
			throws RestEndpointIOException {
		Logger.info("Get list of submissions of assignment [id=" + assignment.getId() + "] for course [id=" + assignment.getCourseId() + "]");
		Map<String, String> someMap = new HashMap<String, String>();
		someMap.put("include", "submission_comments");
		CanvasSubmissionRS[] submissions = endpoint.get(
				"/api/v1/courses/" + assignment.getCourseId() + "/assignments/" + assignment.getId() + "/submissions", someMap,
				CanvasSubmissionRS[].class);
		List<CanvasSubmissionRS> submissionsList = Arrays.asList(submissions);
		Logger.info("Found "+submissionsList.size()+" submissions");
		Logger.info("Get submission for user[id=" + user.getId() + "] from found data");
		List<CanvasSubmissionRS> foundSubmissionsList = new ArrayList<CanvasSubmissionRS>();
		for (CanvasSubmissionRS submission : submissionsList) {
			if (submission.getAssignmentId() == assignment.getId() && submission.getUserId() == user.getId()) {
				Logger.info("Submission found: " + submission.toString());
				foundSubmissionsList.add(submission);
			}
		}
		if (foundSubmissionsList.size() == 0) {
			throw new SubmissionNotFoundException("submission of assignment [id=" + assignment.getId() + "] for user [id=" + user.getId()
					+ "] not found");
		} 
		
		return foundSubmissionsList;
	}
}

