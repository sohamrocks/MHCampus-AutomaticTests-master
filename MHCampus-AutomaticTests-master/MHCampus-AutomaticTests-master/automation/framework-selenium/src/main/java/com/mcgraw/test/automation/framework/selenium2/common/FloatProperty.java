package com.mcgraw.test.automation.framework.selenium2.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FloatProperty{
	private String key;
	private Float value;
	
	public FloatProperty(String key, Float value){
		this.key=key;
		this.value=value;
	}
	
	public String getKey(){
		return key;
	}
	
	public Float getValue(){
		return value;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Float> convertListToMap(List<FloatProperty> floatPropertyList){
		Map result=new HashMap<String, Float>();
		for (FloatProperty fp:floatPropertyList){
			result.put(fp.getKey(), fp.getValue());
		}
		return result;
	}
	
}
