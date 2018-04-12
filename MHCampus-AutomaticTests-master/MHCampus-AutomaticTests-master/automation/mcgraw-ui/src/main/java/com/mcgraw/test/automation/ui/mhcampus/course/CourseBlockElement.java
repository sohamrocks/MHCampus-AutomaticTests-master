package com.mcgraw.test.automation.ui.mhcampus.course;

/**
 * enum for course buttons. Value contains string under alt tag in html
 * 
 * @author Andrei_Turavets
 * 
 */
public enum CourseBlockElement {
	CREATE("Create"), CONNECT("Connect"), ALEKS("Aleks"), TEGRITY("Tegrity"), SIMNET(
			"SimNet"), GDP("GDP"), ACTIV_SIM("ActiveSim"), PRINT_ON_DEMAND(
			"Print On Demand"), LEARN_SMART("LearnSmart"), REMOTE_PROCTOR(
			"Proctoring"), LAUNCH_EBOOK("Launch Ebook"), CUSTOMIZE_BUTTON(
			"Button Customize"), SMART_BOOK("LearnSmart SmartBook"), CONNECT_MATH(
			"Connect Math");

	private String value;

	private CourseBlockElement(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static CourseBlockElement getByValue(String value) {
		for (CourseBlockElement courseBlockElement : values()) {
			if (courseBlockElement.getValue().equals(value)) {
				return courseBlockElement;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + value
				+ "]");
	}
}
