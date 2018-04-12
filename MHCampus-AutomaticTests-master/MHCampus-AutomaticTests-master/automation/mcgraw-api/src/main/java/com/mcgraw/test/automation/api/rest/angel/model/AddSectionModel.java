package com.mcgraw.test.automation.api.rest.angel.model;

import com.mcgraw.test.automation.api.rest.angel.CourseType;
import com.mcgraw.test.automation.api.rest.angel.ValidCourseCategory;
import com.mcgraw.test.automation.api.rest.angel.ValidCourseSemester;
import com.mcgraw.test.automation.api.rest.base.model.BaseNameValueRQ;

public class AddSectionModel extends BaseNameValueRQ{
	
	/** The courseid should not exceeds 8 symbols*/
	private String courseId;
	
	private String title;
	
	private CourseType courseType;
	
	private ValidCourseCategory validCourseCategory;
	
	private ValidCourseSemester validCourseSemester;
	
	private String courseCode;
	
	private String instructorId;
	/** sectionCode equals to courseid*/
	private String sectionCode;

	public void setCourseId(String courseId) {
		this.courseId = courseId;
		this.setBodyParameter("COURSE_ID", courseId);
	}

	public void setTitle(String title) {
		this.title = title;
		this.setBodyParameter("TITLE", title);
	}

	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
		this.setBodyParameter("COURSE_TYPE", courseType.toString());
	}

	public void setValidCourseCategory(ValidCourseCategory validCourseCategory) {
		this.validCourseCategory = validCourseCategory;
		this.setBodyParameter("CATEGORY", title);
	}

	public void setValidCourseSemester(ValidCourseSemester validCourseSemester) {
		this.validCourseSemester = validCourseSemester;
		this.setBodyParameter("SEMESTER", validCourseSemester.getValue());
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
		this.setBodyParameter("COURSE", courseCode);
	}

	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
		this.setBodyParameter("INSTRUCTOR_ID", instructorId);
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
		this.setBodyParameter("SECTION", sectionCode);
	}

	public String getCourseId() {
		return courseId;
	}

	public String getTitle() {
		return title;
	}

	public CourseType getCourseType() {
		return courseType;
	}

	public ValidCourseCategory getValidCourseCategory() {
		return validCourseCategory;
	}

	public ValidCourseSemester getValidCourseSemester() {
		return validCourseSemester;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public String getInstructorId() {
		return instructorId;
	}

	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public String toString() {
		return courseType +" [courseId=" + courseId + ", title=" + title + ", validCourseCategory=" + validCourseCategory + ", validCourseSemester=" + validCourseSemester
				+ ", courseCode=" + courseCode + ", instructorId=" + instructorId +", sectionCode=" + sectionCode +"]";
	}

}
