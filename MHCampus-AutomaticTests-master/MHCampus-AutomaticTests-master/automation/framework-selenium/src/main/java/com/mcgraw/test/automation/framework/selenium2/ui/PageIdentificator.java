package com.mcgraw.test.automation.framework.selenium2.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;


	@Target(value=ElementType.TYPE)
	@Retention(value= RetentionPolicy.RUNTIME)
	public @interface PageIdentificator {
		DefinedLocators locators();
	}