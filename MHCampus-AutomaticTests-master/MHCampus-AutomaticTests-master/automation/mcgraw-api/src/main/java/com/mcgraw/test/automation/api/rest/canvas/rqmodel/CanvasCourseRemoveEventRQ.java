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
import com.mcgraw.test.automation.api.rest.canvas.CanvasRemoveState;

/**
 * Json representation model for Canvas delete course Uses Jackson (2.x) for
 * data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CanvasCourseRemoveEventRQ {

	@JsonSerialize(using = CourseRemoveTaskSerializer.class)
	@JsonDeserialize(using = CourseRemoveTaskDeserializer.class)
	@JsonProperty(value = "event")
	private CanvasRemoveState canvasRemoveState;

	
	public CanvasRemoveState getCanvasRemoveState() {
		return canvasRemoveState;
	}

	public void setCanvasRemoveState(CanvasRemoveState canvasRemoveState) {
		this.canvasRemoveState = canvasRemoveState;
	}

	public static class CourseRemoveTaskSerializer extends JsonSerializer<CanvasRemoveState> {

		@Override
		public void serialize(CanvasRemoveState canvasRemoveState, JsonGenerator generator, SerializerProvider provider)
				throws IOException, JsonProcessingException {
			generator.writeString(canvasRemoveState.getValue());
		}
	}

	public static class CourseRemoveTaskDeserializer extends JsonDeserializer<CanvasRemoveState> {

		@Override
		public CanvasRemoveState deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
				throws IOException, JsonProcessingException {
			return CanvasRemoveState.getByValue(paramJsonParser.getText());
		}
	}
}
