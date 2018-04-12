package com.mcgraw.test.automation.api.rest.moodle.rqmodel;

import com.mcgraw.test.automation.api.rest.base.model.BaseNameValueRQ;

public class MoodleAddCategoryRQ extends BaseNameValueRQ {

	private String categoryName;

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
		this.setBodyParameter("categories[0][name]", categoryName);
	}

	public String getCategoryName() {
		return categoryName;
	}

	@Override
	public String toString() {
		return "Moodle category [Category Name=" + categoryName + "]";
	}

}
