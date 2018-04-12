package com.mcgraw.test.automation.framework.selenium2.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntegerProperty implements Comparable{
	private String key;
	private Integer value;
	
	public IntegerProperty(String key, Integer value){
		this.key=key;
		this.value=value;
	}
	
	public String getKey(){
		return key;
	}
	
	public Integer getValue(){
		return value;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Integer> convertListToMap(List<IntegerProperty> floatPropertyList){
		Map result=new HashMap<String, Integer>();
		for (IntegerProperty fp:floatPropertyList){
			result.put(fp.getKey(), fp.getValue());
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntegerProperty other = (IntegerProperty) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IntegerProperty [key=" + key + ", value=" + value + "]";
	}

	@Override
	public int compareTo(Object o) {
	    if (o == null) {
	        return 1;
	    }
		
	    if (getClass() != o.getClass())
			return 0;
	    
		return this.getValue().compareTo(((IntegerProperty)o).getValue());
	}
	
}
