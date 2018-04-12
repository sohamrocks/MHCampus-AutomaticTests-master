package com.mcgraw.test.automation.framework.core.runner;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.PropertySource;

import com.mcgraw.test.automation.framework.core.configuration.AccessibleField;
import com.mcgraw.test.automation.framework.core.runner.cli.CommandLineParameters;
import com.mcgraw.test.automation.framework.core.runner.cli.Mapped;

/**
 * Spring's property source which is used for Command Line parameters
 *
 * @author Andrei Varabyeu
 *
 * @param <T>
 */
public class CommandLineParametersPropertySource<T extends CommandLineParameters>
		extends PropertySource<Map<String, Object>> {

	private Map<String, Object> properties;

	public CommandLineParametersPropertySource(T mappedBean) {
		super("Mapped bean property source");
		properties = readProperties(mappedBean);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.core.env.PropertySource#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String name) {
		return properties.get(name);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.core.env.PropertySource#containsProperty(java.lang
	 * .String)
	 */
	@Override
	public boolean containsProperty(String name) {
		return properties.containsKey(name);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.core.env.PropertySource#getSource()
	 */
	@Override
	public Map<String, Object> getSource() {
		return properties;
	}

	/**
	 * Parses property values from bean CLI property source
	 *
	 * @param mappedBean
	 * @return
	 */
	private static <T extends CommandLineParameters> Map<String, Object> readProperties(
			T mappedBean) {
		Map<String, Object> props = new HashMap<String, Object>();
		for (Field field : mappedBean.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Mapped.class)) {
				props.put(field.getAnnotation(Mapped.class).value(),
						new AccessibleField(mappedBean, field).getValue());
			}
		}

		return props;

	}

}
