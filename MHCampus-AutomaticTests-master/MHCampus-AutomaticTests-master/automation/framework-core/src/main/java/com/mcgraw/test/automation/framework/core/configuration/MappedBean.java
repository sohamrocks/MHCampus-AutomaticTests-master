/**
 *
 */
package com.mcgraw.test.automation.framework.core.configuration;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.runner.cli.CommandLineParameters;
import com.mcgraw.test.automation.framework.core.runner.cli.Mapped;

/**
 * Bean with default values placed in Property files
 *
 * @author Andrei Varabyeu
 *
 */
public abstract class MappedBean<C extends CommandLineParameters> {

	protected abstract void initialize(C commandLineParameters);

	public void initializeDefaults(Properties defaultProperties) {
		initializeDefaults(defaultProperties, false);
	}

	public void initializeDefaults(Properties defaultProperties,
			boolean forceUpdate) {
		for (Field field : this.getClass().getDeclaredFields()) {
			if (!getMapping().containsKey(field.getName())) {
				continue;
			}

			/* Check Supported Datatype */
			if (!field.getType().equals(String.class)
					&& !field.getType().equals(Boolean.class)
					&& !field.getType().equals(Integer.class)
					&& !field.getType().equals(List.class)) {
				throw new RuntimeException(
						"Unsupported data type in Mapped Bean "
								+ this.getClass()
								+ " in field "
								+ field.getName()
								+ ". Possible Data Types: [String, Integer, Boolean, List]");
			}
			Object beanProperty = getPropertyFromBean(field);

			/*
			 * If property is not initialized we should retrieve data from
			 * default properties
			 */
			if ((null == beanProperty) || forceUpdate) {
				String propertyName = (String) getMapping()
						.get(field.getName());
				Logger.trace("Property '"
						+ propertyName
						+ "' is no setted vi CM. Trying to obtain as envrinment variable...");
				String propertyValue = System.getenv(propertyName);

				if (null == propertyValue) {
					Logger.trace("There is no environment variable with name '"
							+ propertyName
							+ "'. Trying to find in related property file");
					propertyValue = defaultProperties.getProperty(propertyName);
				}
				Logger.trace("Property [" + propertyName + "=" + propertyValue
						+ "]");

				if (field.getType().equals(Boolean.class)) {
					setPropertyToBean(field, Boolean.valueOf(propertyValue));
				} else if (field.getType().equals(Integer.class)) {
					setPropertyToBean(field, Integer.valueOf(propertyValue));
				} else if (field.getType().equals(List.class)) {
					setPropertyToBean(field,
							Collections.singletonList(propertyValue));
				} else {
					setPropertyToBean(field, propertyValue);
				}
			}
		}
	}

	public void initializeDefaults(C commandLineParameters,
			Properties defaultProperties) {
		initialize(commandLineParameters);
		initializeDefaults(defaultProperties);
	}

	private Object getPropertyFromBean(Field field) {
		AccessibleField fieldProcessor = new AccessibleField(this, field);
		return fieldProcessor.getValue();

	}

	private void setPropertyToBean(Field field, Object value) {
		AccessibleField fieldProcessor = new AccessibleField(this, field);
		fieldProcessor.setValue(value);
	}

	private Map<String, String> getMapping() {
		Map<String, String> mappedFields = new HashMap<String, String>();
		for (Field field : this.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Mapped.class)) {
				mappedFields.put(field.getName(),
						field.getAnnotation(Mapped.class).value());
			}
		}

		return mappedFields;
	}
}
