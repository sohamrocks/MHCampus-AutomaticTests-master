package com.mcgraw.test.automation.framework.core.configuration;

import java.lang.reflect.Field;

/**
 * Setter and Getter for Accessible Field
 *
 * @author Andrei Varabyeu
 *
 */
public class AccessibleField {

	private final Field f;
	private final Object bean;

	public AccessibleField(Object bean, Field f) {
		this.bean = bean;
		this.f = f;
	}

	public Class<?> getType() {
		return this.f.getType();
	}

	public void setValue(Object value) {
		try {
			this.f.set(this.bean, value);
		} catch (IllegalAccessException _) {
			this.f.setAccessible(true);
			try {
				this.f.set(this.bean, value);
			} catch (IllegalAccessException e) {
				throw new IllegalAccessError(e.getMessage());
			}
		}
	}

	public Object getValue() {
		try {
			return this.f.get(this.bean);
		} catch (IllegalAccessException _) {
			this.f.setAccessible(true);
			try {
				return this.f.get(this.bean);
			} catch (IllegalAccessException e) {
				throw new IllegalAccessError(e.getMessage());
			}
		}
	}
}
