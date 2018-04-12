package com.mcgraw.test.automation.api.rest.angel.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mcgraw.test.automation.api.rest.base.IRestServerError;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class AngelServerError implements IRestServerError{

	@XmlElement(name = "error")
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "Angel ServerError [message=" + error + "]";
	}

}
