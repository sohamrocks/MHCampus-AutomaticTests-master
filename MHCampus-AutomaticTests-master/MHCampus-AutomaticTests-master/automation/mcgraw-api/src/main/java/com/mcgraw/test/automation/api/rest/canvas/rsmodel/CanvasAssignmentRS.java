package com.mcgraw.test.automation.api.rest.canvas.rsmodel;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * Json representation model for Canvas assignment Uses Jackson (2.x) for
 * data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class CanvasAssignmentRS {
	
	@JsonProperty(value = "id")
	private int id;
	
	@JsonProperty(value = "name")
	private String title;
	
	@JsonProperty(value = "course_id")
	private int courseId;
	
	@JsonProperty(value = "due_at")
	private Date dueDate;
	
	@JsonProperty(value = "points_possible")
	private int pointsPossible;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getPointsPossible() {
		return pointsPossible;
	}

	public void setPointsPossible(int pointsPossible) {
		this.pointsPossible = pointsPossible;
	}	
	
	@Override
	public String toString() {
		return "Canvas Assignment [id=" + id + ", title=" + title + ", courseId =" + courseId
				+ ", dueDate=" + dueDate + ", pointsPossible=" + pointsPossible + "]";
	}
}
