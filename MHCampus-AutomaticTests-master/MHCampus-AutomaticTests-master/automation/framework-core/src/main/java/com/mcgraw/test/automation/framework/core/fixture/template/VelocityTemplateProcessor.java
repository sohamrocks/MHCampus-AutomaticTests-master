package com.mcgraw.test.automation.framework.core.fixture.template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * Template processor for velocity templates
 *
 * @author Andrei Varabyeu
 *
 */
public class VelocityTemplateProcessor implements ITemplateProcessor {

	public static final String KEY_DATE_GENERATOR = "dateGenerator";

	private VelocityEngine ve;

	public VelocityTemplateProcessor() {

		ve = new VelocityEngine();
		/** * Configures the engine to use classpath to find templates */
		Properties props = new Properties();
		props.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath");
		props.setProperty("classpath." + VelocityEngine.RESOURCE_LOADER
				+ ".class", ClasspathResourceLoader.class.getName());
		props.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
				"org.apache.velocity.runtime.log.NullLogSystem");
		try {
			ve.init(props);
		} catch (Exception e) {
			throw new RuntimeException(
					"Unable to initialize template processor", e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.mcgraw.test.automation.framework.core.fixture.template.ITemplateProcessor#process(java
	 * .lang.String)
	 */
	@Override
	public String process(String templatePath) {
		try {
			Template template = ve.getTemplate(templatePath);

			VelocityContext context = new VelocityContext();

			for (Entry<String, Object> entry : TEMPLATE_DATA_PROCESSORS.entrySet()) {
				context.put(entry.getKey(), entry.getValue());
			}

			StringWriter sw = new StringWriter();
			template.merge(context, sw);
			sw.flush();
			return sw.toString();
		} catch (Exception e) {
			throw new RuntimeException("Unable to process template", e);
		}
	}

	/**
	 * Map of constants used in template context
	 */
	public static final Map<String, Object> TEMPLATE_DATA_PROCESSORS = new HashMap<String, Object>() {

		private static final long serialVersionUID = -2911049242354373543L;

		{
			put(KEY_DATE_GENERATOR, new DateGenerator());
			put("bbb", "aaa");
		}
	};

}
