package com.mcgraw.test.automation.api.rest.canvas.rqmodel;

import java.io.IOException;

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
import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentState;
import com.mcgraw.test.automation.api.rest.canvas.CanvasEnrollmentType;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS.Enrollment.EnrollmentTypeDeserializer;
import com.mcgraw.test.automation.api.rest.canvas.rsmodel.CanvasCourseRS.Enrollment.EnrollmentTypeSerializer;

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
public class CanvasEnrollUserRQ {

	@JsonProperty(value = "enrollment")
	private EnrollmentBlock enrollmentBlock;

	public EnrollmentBlock getEnrollment() {
		return enrollmentBlock;
	}

	public void setEnrollment(EnrollmentBlock enrollmentBlock) {
		this.enrollmentBlock = enrollmentBlock;
	}

	public static class EnrollmentBlock {

		@JsonProperty(value = "user_id")
		private int userId;

		@JsonSerialize(using = EnrollmentTypeSerializer.class)
		@JsonDeserialize(using = EnrollmentTypeDeserializer.class)
		@JsonProperty(value = "type")
		private CanvasEnrollmentType canvasEnrollmentType;
		
		@JsonSerialize(using = CanvasEnrollmentStateSerializer.class)
		@JsonDeserialize(using = CanvasEnrollmentStateDeserializer.class)
		@JsonProperty(value = "enrollment_state")
		private CanvasEnrollmentState canvasEnrollmentState;

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public CanvasEnrollmentType getCanvasEnrollmentType() {
			return canvasEnrollmentType;
		}

		public void setCanvasEnrollmentType(CanvasEnrollmentType canvasEnrollmentType) {
			this.canvasEnrollmentType = canvasEnrollmentType;
		}

		public CanvasEnrollmentState getCanvasEnrollmentState() {
			return canvasEnrollmentState;
		}

		public void setCanvasEnrollmentState(CanvasEnrollmentState canvasEnrollmentState) {
			this.canvasEnrollmentState = canvasEnrollmentState;
		}

		public static class CanvasEnrollmentStateSerializer extends JsonSerializer<CanvasEnrollmentState> {

			@Override
			public void serialize(CanvasEnrollmentState canvasEnrollmentState, JsonGenerator generator, SerializerProvider provider)
					throws IOException, JsonProcessingException {
				generator.writeString(canvasEnrollmentState.getValue());

			}
		}
		
		public static class CanvasEnrollmentStateDeserializer extends JsonDeserializer<CanvasEnrollmentState>{

			@Override
			public CanvasEnrollmentState deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
					throws IOException, JsonProcessingException {
				return CanvasEnrollmentState.getByValue(paramJsonParser.getText());
			}
			
		}
		
		

	}

}
