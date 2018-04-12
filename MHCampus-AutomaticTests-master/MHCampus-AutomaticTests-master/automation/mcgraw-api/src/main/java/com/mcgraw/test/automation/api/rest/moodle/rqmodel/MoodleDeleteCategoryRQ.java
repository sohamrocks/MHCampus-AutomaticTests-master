package com.mcgraw.test.automation.api.rest.moodle.rqmodel;

import com.mcgraw.test.automation.api.rest.base.model.BaseNameValueRQ;

public class MoodleDeleteCategoryRQ extends BaseNameValueRQ{
	
	 private String categoryId;
	 
	 private String recursive;

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
		this.setBodyParameter("categories[0][id]", categoryId);
	}

	public void setRecursive(String recursive) {
		this.recursive = recursive;
		this.setBodyParameter("categories[0][recursive]", "1");
	}

	public String getCategoryId() {
		return categoryId;
	}

	public String getRecursive() {
		return recursive;
	}
	
	@Override
	public String toString() {
		return "Moodle Category [Category id=" + categoryId + "]";
	}
	 
	 

}
