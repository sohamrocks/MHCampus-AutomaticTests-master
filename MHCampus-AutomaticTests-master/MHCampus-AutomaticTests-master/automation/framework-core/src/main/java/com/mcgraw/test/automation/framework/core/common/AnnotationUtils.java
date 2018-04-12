package com.mcgraw.test.automation.framework.core.common;

import java.lang.annotation.Annotation;

/**
 * Utils for working with Java annotations through Reflection API
 *
 * @author Andrei Varabyeu
 *
 */
public class AnnotationUtils {

	public static boolean isAnnotationPresent(Class<?> clazz,
			Class<? extends Annotation> annotationClass, boolean recursive) {

		boolean isPresent = clazz.isAnnotationPresent(annotationClass);
		if (isPresent || !recursive
				|| clazz.getSuperclass().equals(Object.class)) {
			return isPresent;
		} else {
			return isAnnotationPresent(clazz.getSuperclass(), annotationClass,
					recursive);
		}

	}

	public static <T extends Annotation> T getAnnotation(Class<?> clazz,
			Class<T> annotationClass, boolean recursive) {
		if (isAnnotationPresent(clazz, annotationClass, false)
				|| clazz.getSuperclass().equals(Object.class)) {
			return clazz.getAnnotation(annotationClass);
		} else {
			return recursive ? getAnnotation(clazz.getSuperclass(),
					annotationClass, recursive) : null;
		}
	}
}
