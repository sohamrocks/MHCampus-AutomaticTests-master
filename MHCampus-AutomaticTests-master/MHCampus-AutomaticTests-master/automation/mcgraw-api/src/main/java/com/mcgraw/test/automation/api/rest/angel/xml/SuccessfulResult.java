package com.mcgraw.test.automation.api.rest.angel.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuccessfulResult {
	
	@XmlElement(name = "success")
	private String successfullMessage;

	public void setSuccessfullMessage(String successfullMessage) {
		this.successfullMessage = successfullMessage;
	}

	public String getSuccessfullMessage() {
		return successfullMessage;
	}
	
	@Override
	public String toString(){
		return "Operation result [message=" + successfullMessage + "]";
	}
}
