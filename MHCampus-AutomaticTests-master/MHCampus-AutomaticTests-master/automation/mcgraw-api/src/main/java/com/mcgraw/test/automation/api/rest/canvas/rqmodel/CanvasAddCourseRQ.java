package com.mcgraw.test.automation.api.rest.canvas.rqmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Json representation model for Canvas create user Uses Jackson (2.x) for
 * data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CanvasAddCourseRQ {
	
	@JsonProperty(value = "account_id")
	private int accountId;
	
	
	@JsonProperty(value = "course")
	private CoursePart coursePart;
	
	@JsonProperty(value = "offer")
	private boolean published;
	
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	
	public CoursePart getCoursePart() {
		return coursePart;
	}

	public void setCoursePart(CoursePart coursePart) {
		this.coursePart = coursePart;
	}	

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}
	
	@JsonInclude(Include.NON_NULL)
	public static class CoursePart{
		
		@JsonProperty(value = "name")
		private String name;
		
		@JsonProperty(value = "sis_course_id")
		private String courseSis;
		
		@JsonProperty(value = "course_code")
		private String courseCode;

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
		
		public String getCourseSis() {
			return courseSis;
		}

		public void setCourseSis(String courseSis) {
			this.courseSis = courseSis;
		}
	}
	
}
