package com.mcgraw.test.automation.api.blackboard.serviceapi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.client.Stub;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.rampart.handler.WSSHandlerConstants;
import org.apache.rampart.handler.config.OutflowConfiguration;
import org.apache.ws.security.WSPasswordCallback;
import org.apache.ws.security.handler.WSHandlerConstants;

import com.mcgraw.test.automation.api.blackboard.exceptions.NullGradebookInfoException;
import com.mcgraw.test.automation.api.blackboard.generated.contextws.ContextWSStub;
import com.mcgraw.test.automation.api.blackboard.generated.contextws.ContextWSStub.Login;
import com.mcgraw.test.automation.api.blackboard.generated.coursemembershipws.CourseMembershipWSStub;
import com.mcgraw.test.automation.api.blackboard.generated.coursemembershipws.CourseMembershipWSStub.CourseMembershipVO;
import com.mcgraw.test.automation.api.blackboard.generated.coursemembershipws.CourseMembershipWSStub.SaveCourseMembership;
import com.mcgraw.test.automation.api.blackboard.generated.coursemembershipws.CourseMembershipWSStub.SaveCourseMembershipResponse;
import com.mcgraw.test.automation.api.blackboard.generated.coursews.CourseWSStub;
import com.mcgraw.test.automation.api.blackboard.generated.coursews.CourseWSStub.CourseVO;
import com.mcgraw.test.automation.api.blackboard.generated.coursews.CourseWSStub.DeleteCourse;
import com.mcgraw.test.automation.api.blackboard.generated.coursews.CourseWSStub.DeleteCourseResponse;
import com.mcgraw.test.automation.api.blackboard.generated.coursews.CourseWSStub.SaveCourse;
import com.mcgraw.test.automation.api.blackboard.generated.coursews.CourseWSStub.SaveCourseResponse;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.AttemptFilter;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.AttemptVO;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.ColumnFilter;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.ColumnVO;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GetAttempts;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GetGradebookColumns;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GetGradebookTypes;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GetGrades;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GetGradingSchemas;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GradebookTypeFilter;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GradebookTypeVO;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GradingSchemaFilter;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.GradingSchemaVO;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.ScoreFilter;
import com.mcgraw.test.automation.api.blackboard.generated.gradebookws.GradebookWSStub.ScoreVO;
import com.mcgraw.test.automation.api.blackboard.generated.userws.UserWSStub;
import com.mcgraw.test.automation.api.blackboard.generated.userws.UserWSStub.DeleteUser;
import com.mcgraw.test.automation.api.blackboard.generated.userws.UserWSStub.DeleteUserResponse;
import com.mcgraw.test.automation.api.blackboard.generated.userws.UserWSStub.GetUser;
import com.mcgraw.test.automation.api.blackboard.generated.userws.UserWSStub.GetUserResponse;
import com.mcgraw.test.automation.api.blackboard.generated.userws.UserWSStub.SaveUser;
import com.mcgraw.test.automation.api.blackboard.generated.userws.UserWSStub.SaveUserResponse;
import com.mcgraw.test.automation.api.blackboard.generated.userws.UserWSStub.UserExtendedInfoVO;
import com.mcgraw.test.automation.api.blackboard.generated.userws.UserWSStub.UserFilter;
import com.mcgraw.test.automation.api.blackboard.generated.userws.UserWSStub.UserVO;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

import edu.emory.mathcs.backport.java.util.Arrays;

@SuppressWarnings("deprecation")
public class BlackBoardApi {

	private static final int GET_ALL_USERS_WITH_AVAILABILITY = 1;

	private String serverURL;
	private String rampartModulePath;
	private String loginName;
	private String loginPassword;

	private String vendorId;
	private String programId;

	private String dataSourceId; // DataSource "SYSTEM"

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public void setRampartModulePath(String rampartModulePath) {
		this.rampartModulePath = rampartModulePath;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public enum BlackboardApiRoleIdentifier {
		STUDENT("S"), INSTRUCTOR("P");

		private final String value;

		public String getValue() {
			return value;
		}

		private BlackboardApiRoleIdentifier(String value) {
			this.value = value;
		}

		public static BlackboardApiRoleIdentifier getByValue(String value) {
			for (BlackboardApiRoleIdentifier blackboardApiRoleIdentifier : values()) {
				if (blackboardApiRoleIdentifier.getValue().equals(value)) {
					return blackboardApiRoleIdentifier;
				}
			}
			throw new IllegalArgumentException("No matching constant for [" + value + "]");
		}
	}

	private static String studentRoleIdentifier = "S";
	private static String instructorRoleIdentifier = "P";

	private ContextWSStub contextWSStub;
	private UserWSStub userWSStub;
	private CourseWSStub courseWSStub;
	private CourseMembershipWSStub courseMembershipWSStub;
	private GradebookWSStub gradebookWSStub;

	private PasswordCallbackClass pwcb = null;
	private static OutflowConfiguration ofc;
	private static ConfigurationContext ctx;

	private enum Service {
		Context, User, Course, CourseMembership, Gradebook
	};

	public BlackBoardApi() throws Exception {
	}

	public PasswordCallbackClass getPasswordCallbackClass() {
		if (pwcb == null) {
			pwcb = new BlackBoardApi.PasswordCallbackClass();
		}
		;

		return this.pwcb;
	}

	public static String getStudentRoleIdentifier() {
		return studentRoleIdentifier;
	}

	public static String getInstructorRoleIdentifier() {
		return instructorRoleIdentifier;
	}

	public void loginAndInitialiseBlackBoardServices() throws Exception {

		Logger.info("Initializing Blackboard API...");
		contextWSStub = (ContextWSStub) createWebServiceClient(Service.Context);
		this.loginToBlackBoard();
		courseWSStub = (CourseWSStub) createWebServiceClient(Service.Course);
		userWSStub = (UserWSStub) createWebServiceClient(Service.User);
		courseMembershipWSStub = (CourseMembershipWSStub) createWebServiceClient(Service.CourseMembership);
		gradebookWSStub = (GradebookWSStub) createWebServiceClient(Service.Gradebook);
	}

	public void logout() throws Exception {

		Logger.info("Logging out from Blackboard API...");
		if(contextWSStub != null)
			contextWSStub.logout();
	}

	public String createUser(String userName, String givenName, String familyName) throws Exception {

		UserExtendedInfoVO detail = new UserExtendedInfoVO();
		detail.setGivenName(givenName);
		detail.setFamilyName(familyName);

		UserVO who = new UserVO();
		who.setUserBatchUid(userName);
		who.setName(userName);
		who.setPassword(userName);
		who.setIsAvailable(true);
		who.setDataSourceId(dataSourceId);

		who.setExtendedInfo(detail);

		SaveUser saveUser = new SaveUser();
		saveUser.addUser(who);

		SaveUserResponse saveUserResponse = userWSStub.saveUser(saveUser);
		String[] userIds = saveUserResponse.get_return();

		if (userIds != null && userIds.length == 1) {
			Logger.info("New user was created. UserName and Password = '" + userName + "'. UserId = '" + userIds[0] + "'");
			return userIds[0];
		}

		Logger.info("Failed to create user");
		return null;
	}

	public String createCourse(String courseName) throws Exception {

		CourseVO courseVO = new CourseVO();
		courseVO.setCourseId(courseName);
		courseVO.setName(courseName);
		courseVO.setDataSourceId(dataSourceId);
		courseVO.setAllowGuests(true);
		courseVO.setAvailable(true);

		SaveCourse saveCourse = new SaveCourse();
		saveCourse.setC(courseVO);

		SaveCourseResponse saveCourseResponse = courseWSStub.saveCourse(saveCourse);

		String courseId = null;
		courseId = saveCourseResponse.get_return();

		if (courseId != null) {
			Logger.info("New course was created. CourseName = '" + courseName + "'. CourseId = '" + courseId + "'");
			return courseId;
		}

		Logger.info("Failed to create new course");
		return null;
	}

	public void deleteUser(String userId) throws Exception {

		String[] userIds = new String[] { userId };
		DeleteUser deleteUser = new DeleteUser();
		deleteUser.setUserId(userIds);
		DeleteUserResponse deleteUserResponse = userWSStub.deleteUser(deleteUser);
		String[] deleteResponse = deleteUserResponse.get_return();

		if (deleteResponse != null && deleteResponse.length == 1) {

			Logger.info("User with id = " + userId + "' was deleted");
		} else {
			Logger.info("Failed to delete user");
		}
	}

	public void deleteAllUsersByLoginPrefix(String prefix) throws RemoteException {
		Logger.info("Deleting all users with prefix: [" + prefix + "]");
		UserFilter userFilter = new UserFilter();
		userFilter.setFilterType(GET_ALL_USERS_WITH_AVAILABILITY);
		GetUser getUser = new GetUser();
		getUser.setFilter(userFilter);
		GetUserResponse getUserResponse = userWSStub.getUser(getUser);
		List<UserVO> allblackboardUsers = Arrays.asList(getUserResponse.get_return());
		List<UserVO> usersToDelete = new ArrayList<UserVO>();
		for (UserVO userVO : allblackboardUsers) {
			if (userVO.getName().startsWith(prefix.toLowerCase())) {
				usersToDelete.add(userVO);
			}
		}

		if (usersToDelete.size() == 0) {
			Logger.info("Users with prefix: [" + prefix + "] not found to delete");
			return;
		}
		String[] userIdsToDelete = new String[usersToDelete.size()];
		for (int i = 0; i < userIdsToDelete.length; i++) {
			userIdsToDelete[i] = usersToDelete.get(i).getId();
		}

		DeleteUser deleteUser = new DeleteUser();
		deleteUser.setUserId(userIdsToDelete);
		DeleteUserResponse deleteUserResponse = userWSStub.deleteUser(deleteUser);
		String[] deleteResponse = deleteUserResponse.get_return();

		if (deleteResponse != null && deleteResponse.length == userIdsToDelete.length) {
			Logger.info("Deleted [" + userIdsToDelete.length + "] users with prefix [" + prefix + "]");
		} else if (deleteResponse != null && deleteResponse.length != userIdsToDelete.length && deleteResponse.length != 0) {
			Logger.info("Tried to delete [" + userIdsToDelete.length + "] user, but deleted [" + deleteResponse.length + "] users");
		} else {
			Logger.info("Failed to delete users");
		}
	}

	public void deleteCourse(String courseId) throws Exception {

		String[] idsArr = new String[] { courseId };
		DeleteCourse deleteCourse = new DeleteCourse();
		deleteCourse.setIds(idsArr);
		DeleteCourseResponse deleteCourseResponse = courseWSStub.deleteCourse(deleteCourse);
		String[] deletedCourseResponse = deleteCourseResponse.get_return();

		if (deletedCourseResponse != null && deletedCourseResponse.length == 1) {
			Logger.info("Course with id = '" + courseId + "' was deleted");
		} else {
			Logger.info("Failed to delete course");
		}
	}

	public void createEnrollment(String userId, String courseId, BlackboardApiRoleIdentifier blackboardApiRoleIdentifier) throws Exception {

		CourseMembershipVO membership = new CourseMembershipVO();
		membership.setAvailable(true);
		membership.setCourseId(courseId);
		membership.setDataSourceId(dataSourceId);
		membership.setRoleId(blackboardApiRoleIdentifier.getValue());
		membership.setUserId(userId);

		CourseMembershipVO[] membershipArr = new CourseMembershipVO[] { membership };

		SaveCourseMembership saveMembership = new SaveCourseMembership();
		saveMembership.setCourseId(courseId);
		saveMembership.setCmArray(membershipArr);

		SaveCourseMembershipResponse saveResponse = courseMembershipWSStub.saveCourseMembership(saveMembership);
		String[] enrollmentIds = saveResponse.get_return();

		if (enrollmentIds != null && enrollmentIds.length == 1) {
			Logger.info("New enrollment was created: user with id = " + userId + " was enrolled to the course with id = " + courseId);
		} else {
			Logger.info("Failed to create enrollment");
		}
	}

	public class GradebookInfo {
		public String AssignmentId;
		public String AssignmentTitle;
		public String Category;
		public String Description;
		public String ScoreType;
		public String ScorePoints;
		public Boolean IsStudentViewable;
		public Boolean IsIncludedInGrade;
		public String[] Comments;
		public String[] ScoresReceived;

		private String messageforScoreItems() {
			StringBuilder messageBuilder = new StringBuilder();
			int amountOfScoreItems = ScoresReceived.length;
			for (int i = 0; i < amountOfScoreItems; i++) {
				messageBuilder.append("ScoreItemData" + i + " [");
				messageBuilder.append("ScoreReceived = " + ScoresReceived[i] + ",");
				messageBuilder.append("Comment=" + Comments[i] + "], ");

			}
			return messageBuilder.toString();
		}

		@Override
		public String toString() {
			return "GradebookInfo [AssignmentId=" + AssignmentId + ", AssignmentTitle=" + AssignmentTitle + ", Category=" + Category
					+ ", Description=" + Description + ", ScoreType=" + ScoreType + ", ScorePoints=" + ScorePoints + ", IsStudentViewable="
					+ IsStudentViewable + ", IsIncludedInGrade=" + IsIncludedInGrade + ", " + messageforScoreItems() + "]";
		}
	}

	public GradebookInfo getGradebook(String courseId, String columnName, String userId) throws Exception {

		// Extracting Gradebook main info (AssignmentId, AssignmentTitle,
		// Description, ScorePoints,
		// IsIncludedInGrade, IsStudentViewable)
		ColumnFilter columnFilter = new ColumnFilter();
		columnFilter.setFilterType(2); // 2 =
										// GET_COLUMN_BY_COURSE_ID_AND_COLUMN_NAME
		columnFilter.setIds(new String[] { courseId });
		columnFilter.setNames(new String[] { columnName });
		GetGradebookColumns getGradebookColumns = new GetGradebookColumns();
		getGradebookColumns.setCourseId(courseId);
		getGradebookColumns.setFilter(columnFilter);
		ColumnVO[] columns = gradebookWSStub.getGradebookColumns(getGradebookColumns).get_return();

		ColumnVO column = columns[0];
		if (column == null) {
			throw new NullGradebookInfoException("No gradebook info for course " + courseId);
		}
		String gradeBookTypeId = column.getGradebookTypeId();
		String scaleId = column.getScaleId();
		String columnId = column.getId();

		// Extracting Category (e.g. Blog)
		GradebookTypeFilter gradebookTypeFilter = new GradebookTypeFilter();
		gradebookTypeFilter.setFilterType(3); // 3 = GET_GRADEBOOK_TYPE_BY_ID
		gradebookTypeFilter.setIds(new String[] { gradeBookTypeId });
		GetGradebookTypes getGradebookTypes = new GetGradebookTypes();
		getGradebookTypes.setCourseId(courseId);
		getGradebookTypes.setFilter(gradebookTypeFilter);
		GradebookTypeVO[] gradebookTypes = gradebookWSStub.getGradebookTypes(getGradebookTypes).get_return();

		// Extracting Score Type (Score / Text / Letter / Percentage)
		GradingSchemaFilter gradingSchemaFilter = new GradingSchemaFilter();
		gradingSchemaFilter.setFilterType(3); // 3 = GET_GRADING_SCHEMA_BY_ID
		gradingSchemaFilter.setIds(new String[] { scaleId });
		GetGradingSchemas getGradingSchemas = new GetGradingSchemas();
		getGradingSchemas.setCourseId(courseId);
		getGradingSchemas.setFilter(gradingSchemaFilter);
		GradingSchemaVO[] gradingSchema = gradebookWSStub.getGradingSchemas(getGradingSchemas).get_return();

		// Getting gradeId to extract Attemps information further
		ScoreFilter scoreFilter = new ScoreFilter();
		scoreFilter.setFilterType(2); // 2 = GET_SCORE_BY_COLUMN_ID_AND_USER_ID
		scoreFilter.setColumnId(columnId);
		scoreFilter.setUserIds(new String[] { userId });
		GetGrades getGrades = new GetGrades();
		getGrades.setCourseId(courseId);
		getGrades.setFilter(scoreFilter);
		ScoreVO[] scores = gradebookWSStub.getGrades(getGrades).get_return();
		String[] scoresIds = new String[scores.length];
		for (int i = 0; i < scores.length; i++) {
			scoresIds[i] = scores[i].getId();
		}

		// Student comment & score received
		AttemptFilter attemptFilter = new AttemptFilter();
		attemptFilter.setFilterType(3); // 3 = GET_ATTEMPT_BY_IDS
		attemptFilter.setIds(scoresIds);
		GetAttempts getAttempts = new GetAttempts();
		getAttempts.setCourseId(courseId);
		getAttempts.setFilter(attemptFilter);
		AttemptVO[] attempts = gradebookWSStub.getAttempts(getAttempts).get_return();

		GradebookInfo gradebookData = new GradebookInfo();

		gradebookData.AssignmentId = column.getColumnName();
		gradebookData.AssignmentTitle = column.getColumnDisplayName();
		gradebookData.Description = column.getDescription();
		gradebookData.ScorePoints = Integer.toString((int) Math.round(column.getPossible()));
		gradebookData.IsIncludedInGrade = column.getScorable();
		gradebookData.IsStudentViewable = column.getVisible();
		gradebookData.Category = gradebookTypes[0].getDisplayedTitle();
		gradebookData.ScoreType = gradingSchema[0].getTitle().replace(".title", "");
		gradebookData.Comments = new String[attempts.length];
		gradebookData.ScoresReceived = new String[attempts.length];

		for (int i = 0; i < attempts.length; i++) {
			gradebookData.Comments[i] = attempts[i].getFeedbackToUser();
			gradebookData.ScoresReceived[i] = Integer.toString((int) Math.round(attempts[i].getScore()));
		}

		Logger.info("Gradebook details extracted via blackboard API: " + gradebookData.toString());

		return gradebookData;
	}

	private void loginToBlackBoard() throws Exception {

		String sessionValue = contextWSStub.initialize().get_return();
		Logger.info("Value returned from calling initialize method is " + sessionValue);

		this.getPasswordCallbackClass().setSessionId(sessionValue);

		Login loginCreds = new Login();
		loginCreds.setUserid(loginName);
		loginCreds.setPassword(loginPassword);
		loginCreds.setClientVendorId(vendorId);
		loginCreds.setClientProgramId(programId);
		loginCreds.setLoginExtraInfo("");
		loginCreds.setExpectedLifeSeconds(60 * 60);
		boolean loginResult = contextWSStub.login(loginCreds).get_return();

		if (loginResult) {
			Logger.info("Login to BlackBoard web service was successfull");
		} else {
			Logger.info("Login to BlackBoard web service was NOT successfull");
		}
	}

	private Stub createWebServiceClient(Service service) throws Exception {

		// String fullPathtoRampartModule =
		// getClass().getResource(rampartModulePath).getPath();

		ctx = ConfigurationContextFactory.createConfigurationContextFromFileSystem(rampartModulePath);

		Stub stub;

		switch (service) {
		case Context:
			stub = new ContextWSStub(ctx, serverURL + "/webapps/ws/services/Context.WS");
			break;
		case User:
			stub = new UserWSStub(ctx, serverURL + "/webapps/ws/services/User.WS");
			break;
		case Course:
			stub = new CourseWSStub(ctx, serverURL + "/webapps/ws/services/Course.WS");
			break;
		case CourseMembership:
			stub = new CourseMembershipWSStub(ctx, serverURL + "/webapps/ws/services/CourseMembership.WS");
			break;
		case Gradebook:
			stub = new GradebookWSStub(ctx, serverURL + "/webapps/ws/services/Gradebook.WS");
			break;
		default:
			stub = null;
			break;
		}

		ServiceClient client = stub._getServiceClient();
		Options options = client.getOptions();
		options.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION, HTTPConstants.HEADER_PROTOCOL_10);
		options.setProperty(WSHandlerConstants.PW_CALLBACK_REF, this.getPasswordCallbackClass());

		ofc = new OutflowConfiguration();
		ofc.setActionItems("UsernameToken Timestamp");
		ofc.setUser("session");
		ofc.setPasswordType("PasswordText");

		options.setProperty(WSSHandlerConstants.OUTFLOW_SECURITY, ofc.getProperty());
		client.engageModule("rampart");

		return stub;
	}

	private static class PasswordCallbackClass implements CallbackHandler {

		String sessionId = null;

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
			for (int i = 0; i < callbacks.length; i++) {
				WSPasswordCallback pwcb = (WSPasswordCallback) callbacks[i];
				String pw = "nosession";

				if (sessionId != null) {
					pw = sessionId;
				}
				pwcb.setPassword(pw);
			}
		}
	}
}
