package com.mcgraw.test.automation.api.rest.canvas.rsmodel;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mcgraw.test.automation.api.rest.canvas.CourseDefaultView;
import com.mcgraw.test.automation.api.rest.canvas.CourseWorkflowState;
import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentType;

/**
 * Json representation model for Canvas course Uses Jackson (2.x) for
 * data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class CanvasCourseRS {

	@JsonProperty(value = "id")
	private int id;

	@JsonProperty(value = "sis_course_id")
	private String sisCourseId;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "course_code")
	private String courseCode;

	@JsonSerialize(using = CourseWorkflowStateSerializer.class)
	@JsonDeserialize(using = CourseWorkflowStateDeserializer.class)
	@JsonProperty(value = "workflow_state")
	private CourseWorkflowState courseWorkflowState;

	@JsonProperty(value = "account_id")
	private int accountId;

	@JsonProperty(value = "start_at")
	private Date startAtDate;

	@JsonProperty(value = "end_at")
	private Date endAtDate;

	@JsonProperty(value = "enrollments")
	private List<Enrollment> enrollmentList;

	@JsonProperty(value = "calendar")
	private CourseCalendar courseCalendar;

	@JsonSerialize(using = CourseDefaultViewSerializer.class)
	@JsonDeserialize(using = CourseDefaultViewDeserializer.class)
	@JsonProperty(value = "default_view")
	private CourseDefaultView courseDefaultView;

	@JsonProperty(value = "syllabus_body")
	private String syllabusBody;

	@JsonProperty(value = "needs_grading_count")
	private int needsGradingCount;

	@JsonProperty(value = "term")
	private EnrollmentTermForCourse term;

	@JsonProperty(value = "public_syllabus")
	private boolean publicSyllabus;

	@JsonProperty(value = "hide_final_grades")
	private boolean hideFinalGrades;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSisCourseId() {
		return sisCourseId;
	}

	public void setSisCourseId(String sisCourseId) {
		this.sisCourseId = sisCourseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public CourseWorkflowState getCourseWorkflowState() {
		return courseWorkflowState;
	}

	public void setCourseWorkflowState(CourseWorkflowState courseWorkflowState) {
		this.courseWorkflowState = courseWorkflowState;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Date getStartAtDate() {
		return startAtDate;
	}

	public void setStartAtDate(Date startAtDate) {
		this.startAtDate = startAtDate;
	}

	public Date getEndAtDate() {
		return endAtDate;
	}

	public void setEndAtDate(Date endAtDate) {
		this.endAtDate = endAtDate;
	}

	public List<Enrollment> getEnrollmentList() {
		return enrollmentList;
	}

	public void setEnrollmentList(List<Enrollment> enrollmentList) {
		this.enrollmentList = enrollmentList;
	}

	public CourseCalendar getCourseCalendar() {
		return courseCalendar;
	}

	public void setCourseCalendar(CourseCalendar courseCalendar) {
		this.courseCalendar = courseCalendar;
	}

	public CourseDefaultView getCourseDefaultView() {
		return courseDefaultView;
	}

	public void setCourseDefaultView(CourseDefaultView courseDefaultView) {
		this.courseDefaultView = courseDefaultView;
	}

	public String getSyllabusBody() {
		return syllabusBody;
	}

	public void setSyllabusBody(String syllabusBody) {
		this.syllabusBody = syllabusBody;
	}

	public int getNeedsGradingCount() {
		return needsGradingCount;
	}

	public void setNeedsGradingCount(int needsGradingCount) {
		this.needsGradingCount = needsGradingCount;
	}

	public EnrollmentTermForCourse getTerm() {
		return term;
	}

	public void setTerm(EnrollmentTermForCourse term) {
		this.term = term;
	}

	public boolean isPublicSyllabus() {
		return publicSyllabus;
	}

	public void setPublicSyllabus(boolean publicSyllabus) {
		this.publicSyllabus = publicSyllabus;
	}

	public boolean isHideFinalGrades() {
		return hideFinalGrades;
	}

	public void setHideFinalGrades(boolean hideFinalGrades) {
		this.hideFinalGrades = hideFinalGrades;
	}
	
	@Override
	public String toString(){
		return "Canvas course [id=" + id + ", name=" + name + " courseSis=" + sisCourseId + ", courseCode=" + courseCode + ", courseWorkflowState=" + courseWorkflowState + ", accountId=" + accountId
		+ ", courseDefaultView=" + courseDefaultView + "]";
	}

	public static class CourseWorkflowStateSerializer extends JsonSerializer<CourseWorkflowState> {

		@Override
		public void serialize(CourseWorkflowState courseWorkflowState, JsonGenerator generator, SerializerProvider provider)
				throws IOException, JsonProcessingException {
			generator.writeString(courseWorkflowState.getValue());
		}
	}

	public static class CourseWorkflowStateDeserializer extends JsonDeserializer<CourseWorkflowState> {

		@Override
		public CourseWorkflowState deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
				throws IOException, JsonProcessingException {
			return CourseWorkflowState.getByValue(paramJsonParser.getText());
		}
	}

	public static class CourseDefaultViewSerializer extends JsonSerializer<CourseDefaultView> {

		@Override
		public void serialize(CourseDefaultView courseDefaultView, JsonGenerator generator, SerializerProvider provider)
				throws IOException, JsonProcessingException {
			generator.writeString(courseDefaultView.getValue());
		}
	}

	public static class CourseDefaultViewDeserializer extends JsonDeserializer<CourseDefaultView> {

		@Override
		public CourseDefaultView deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
				throws IOException, JsonProcessingException {
			return CourseDefaultView.getByValue(paramJsonParser.getText());
		}
	}

	@JsonInclude(Include.NON_NULL)
	public static class Enrollment {

		@JsonSerialize(using = EnrollmentTypeSerializer.class)
		@JsonDeserialize(using = EnrollmentTypeDeserializer.class)
		@JsonProperty(value = "type")
		private CanvasEnrollmentType canvasEnrollmentType;

		@JsonProperty(value = "computed_final_score")
		private float computedFinalScore;

		@JsonProperty(value = "computed_current_score")
		private float computedCurrentScore;

		@JsonProperty(value = "computed_final_grade")
		private String computedFinalGrade;

		@JsonProperty(value = "computed_current_grade")
		private String computedCurrentGrade;

		public CanvasEnrollmentType getEnrollmentType() {
			return canvasEnrollmentType;
		}

		public void setEnrollmentType(CanvasEnrollmentType canvasEnrollmentType) {
			this.canvasEnrollmentType = canvasEnrollmentType;
		}

		public float getComputedFinalScore() {
			return computedFinalScore;
		}

		public void setComputedFinalScore(float computedFinalScore) {
			this.computedFinalScore = computedFinalScore;
		}

		public float getComputedCurrentScore() {
			return computedCurrentScore;
		}

		public void setComputedCurrentScore(float computedCurrentScore) {
			this.computedCurrentScore = computedCurrentScore;
		}

		public String getComputedFinalGrade() {
			return computedFinalGrade;
		}

		public void setComputedFinalGrade(String computedFinalGrade) {
			this.computedFinalGrade = computedFinalGrade;
		}

		public String getComputedCurrentGrade() {
			return computedCurrentGrade;
		}

		public void setComputedCurrentGrade(String computedCurrentGrade) {
			this.computedCurrentGrade = computedCurrentGrade;
		}

		public static class EnrollmentTypeSerializer extends JsonSerializer<CanvasEnrollmentType> {

			@Override
			public void serialize(CanvasEnrollmentType canvasEnrollmentType, JsonGenerator generator, SerializerProvider provider) throws IOException,
					JsonProcessingException {
				generator.writeString(canvasEnrollmentType.getValue());
			}

		}

		public static class EnrollmentTypeDeserializer extends JsonDeserializer<CanvasEnrollmentType> {

			@Override
			public CanvasEnrollmentType deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
					throws IOException, JsonProcessingException {
				return CanvasEnrollmentType.getByValue(paramJsonParser.getText());
			}
		}
	}

	@JsonInclude(Include.NON_NULL)
	public static class CourseCalendar {

		@JsonProperty(value = "ics")
		private String ics;

	}

	@JsonInclude(Include.NON_NULL)
	public static class EnrollmentTermForCourse {

		@JsonProperty(value = "id")
		private int id;

		@JsonProperty(value = "name")
		private String name;

		@JsonProperty(value = "start_at")
		private Date startAtDate;

		@JsonProperty(value = "end_at")
		private Date endAtDate;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getStartAtDate() {
			return startAtDate;
		}

		public void setStartAtDate(Date startAtDate) {
			this.startAtDate = startAtDate;
		}

		public Date getEndAtDate() {
			return endAtDate;
		}

		public void setEndAtDate(Date endAtDate) {
			this.endAtDate = endAtDate;
		}

	}

}
