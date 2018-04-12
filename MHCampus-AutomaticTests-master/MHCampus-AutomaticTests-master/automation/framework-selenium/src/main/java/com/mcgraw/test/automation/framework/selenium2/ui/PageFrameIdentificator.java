package com.mcgraw.test.automation.framework.selenium2.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

/**
 * Use this annotation if {@link com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator} 
 * contains {@link com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator} which
 * is located in the frames. 
 * 
 * Browser will switch to each frame from the set of {@link com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator} of 
 * {@link com.mcgraw.test.automation.framework.selenium2.ui.PageFrameIdentificator} 
 * the class marked by this annotation. if frameCheckCanBeSkipped() == true, Browser will try to wait for frame and if the frame will not
 * be found, the browser continues find elements outside of frames
 *
 * @author Andrei_Turavets
 *
 */
@Target(value=ElementType.TYPE)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface PageFrameIdentificator {
	
	DefinedLocators locators();
	
	boolean frameCheckCanBeSkipped() default false; 

}
