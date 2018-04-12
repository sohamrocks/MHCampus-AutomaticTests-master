package com.mcgraw.test.automation.framework.selenium2.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoubleProperty{
	private String key;
	private Double value;
	
	public DoubleProperty(String key, Double value){
		this.key=key;
		this.value=value;
	}
	
	public String getKey(){
		return key;
	}
	
	public Double getValue(){
		return value;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Double> convertListToMap(List<DoubleProperty> doublePropertyList){
		Map result=new HashMap<String, Double>();
		for (DoubleProperty fp:doublePropertyList){
			result.put(fp.getKey(), fp.getValue());
		}
		return result;
	}
	
}
