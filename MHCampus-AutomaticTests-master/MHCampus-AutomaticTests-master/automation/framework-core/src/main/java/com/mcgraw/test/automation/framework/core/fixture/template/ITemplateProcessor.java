package com.mcgraw.test.automation.framework.core.fixture.template;

/**
 * Base interface for template processors
 *
 * @author Andrei Varabyeu
 *
 */
public interface ITemplateProcessor {

	/**
	 * Process template from given path
	 *
	 * @param templatePath
	 * @return
	 */
	String process(String templatePath);
}
