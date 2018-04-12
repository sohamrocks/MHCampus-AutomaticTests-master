package com.mcgraw.test.automation.api.rest.canvas.rsmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json representation model for Canvas submission Uses Jackson (2.x) for
 * data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class CanvasSubmissionRS {
	
	@JsonProperty(value = "id")
	private int id;
	
	@JsonProperty(value = "score")
	private int scoreNumber;
	
	@JsonProperty(value = "grade")
	private String scorePercent;
	
	@JsonProperty(value = "user_id")
	private int userId;
	
	@JsonProperty(value = "assignment_id")
	private int assignmentId;
	
	
	@JsonProperty(value = "submission_comments")
	private List<SubmissionComment> submissionComments;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScoreNumber() {
		return scoreNumber;
	}

	public void setScoreNumber(int scoreNumber) {
		this.scoreNumber = scoreNumber;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setSubmissionComments(List<SubmissionComment> submissionComments) {
		this.submissionComments = submissionComments;
	}
	
	public SubmissionComment getSubmissionComment(){
		return submissionComments.get(0);
	}

	public String getScorePercent() {
		return scorePercent;
	}

	public void setScorePercent(String scorePercent) {
		this.scorePercent = scorePercent;
	}

	public int getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}
	
	@Override
	public String toString() {
		return "Canvas Submission [id=" + id + ", score number=" + scoreNumber + ", percentage score =" + scorePercent
				+ ", user id=" + userId + ", assignment id=" + assignmentId + "]";
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class SubmissionComment{
		
		@JsonProperty(value = "comment")
		private String comment;
		
		@JsonProperty(value = "author_name")
		private String authorName;

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String getAuthorName() {
			return authorName;
		}

		public void setAuthorName(String authorName) {
			this.authorName = authorName;
		}
		
	}

}
