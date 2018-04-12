package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import org.openqa.selenium.WebElement;

/**
 * All blocked elements should inherit
 * this class
 * 
 * @author Andrei_Turavets
 *
 */
public class BlockElement extends Element{

	public BlockElement(WebElement webElement) {
		super(webElement);
		
	}

}
