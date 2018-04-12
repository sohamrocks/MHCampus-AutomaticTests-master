package com.mcgraw.test.automation.api.rest.canvas.rsmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentState;
import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentType;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasEnrollUserRQ.EnrollmentBlock.CanvasEnrollmentStateDeserializer;
import com.mcgraw.test.automation.api.rest.canvas.rqmodel.CanvasEnrollUserRQ.EnrollmentBlock.CanvasEnrollmentStateSerializer;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS.Enrollment.EnrollmentTypeDeserializer;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS.Enrollment.EnrollmentTypeSerializer;

/**
 * Json representation model for Canvas user enrollment. Uses Jackson (2.x) for
 * data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class CanvasUserEnrollmentRS {

	@JsonProperty(value = "id")
	private int id;

	@JsonProperty(value = "course_id")
	private int courseId;

	@JsonProperty(value = "course_section_id")
	private int courseSectionId;

	@JsonSerialize(using = CanvasEnrollmentStateSerializer.class)
	@JsonDeserialize(using = CanvasEnrollmentStateDeserializer.class)
	@JsonProperty(value = "enrollment_state")
	private CanvasEnrollmentState canvasEnrollmentState;

	@JsonSerialize(using = EnrollmentTypeSerializer.class)
	@JsonDeserialize(using = EnrollmentTypeDeserializer.class)
	@JsonProperty(value = "type")
	private CanvasEnrollmentType canvasEnrollmentType;

	@JsonProperty(value = "user_id")
	private int userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCourseSectionId() {
		return courseSectionId;
	}

	public void setCourseSectionId(int courseSectionId) {
		this.courseSectionId = courseSectionId;
	}

	public CanvasEnrollmentState getCanvasEnrollmentState() {
		return canvasEnrollmentState;
	}

	public void setCanvasEnrollmentState(CanvasEnrollmentState canvasEnrollmentState) {
		this.canvasEnrollmentState = canvasEnrollmentState;
	}

	public CanvasEnrollmentType getCanvasEnrollmentType() {
		return canvasEnrollmentType;
	}

	public void setCanvasEnrollmentType(CanvasEnrollmentType canvasEnrollmentType) {
		this.canvasEnrollmentType = canvasEnrollmentType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Canvas Enrollment [id=" + id + ", courseId=" + courseId + ", courseSectionId=" + courseSectionId
				+ ", canvasEnrollmentState=" + canvasEnrollmentState.getValue() + ", canvasEnrollmentType=" + canvasEnrollmentType.getValue() + "]";
	}
}
