/**
 * 
 */
package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Andrei Varabyeu
 * 
 */
public class WordCloud extends Element {

	public WordCloud(WebElement webElement) {
		super(webElement);
	}

	public Set<String> getWords(){
		Set<String> result = new HashSet<String>();
		for (WebElement word: this.findElements(By.xpath(".//*[contains(@id,'_word_')]"))){
			result.add(word.getText());
		}
		return result;
	}

}
