package com.mcgraw.test.automation.api.rest.canvas.rsmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Json representation model for Canvas course Uses Jackson (2.x) for
 * data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CanvasDeleteRS {

	@JsonProperty(value = "delete")
	private boolean deleted;

	@Override
	public String toString() {
		return "Status [deleted=" + deleted + "]";

	}

}
