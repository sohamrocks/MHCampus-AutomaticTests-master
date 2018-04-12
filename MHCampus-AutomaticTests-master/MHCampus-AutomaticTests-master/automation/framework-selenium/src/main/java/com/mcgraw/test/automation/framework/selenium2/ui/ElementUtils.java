package com.mcgraw.test.automation.framework.selenium2.ui;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.mcgraw.test.automation.framework.selenium2.ui.elements.BlockElement;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Select;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.PredefinedLocator;

/**
 * These utils help to detect whether the field or class
 * refers to any kind of element
 * 
 * @author Andrei_Turavets
 *
 */
public class ElementUtils {

	public static boolean isBlockElement(Class<?> clazz) {
		return BlockElement.class.isAssignableFrom(clazz);
	}

	public static boolean isBlockElement(Field field) {
		return isBlockElement(field.getType());
	}

	public static boolean isBlockElementList(Field field) {
		if (!isParameterizedList(field)) {
			return false;
		}
		Class<?> listParameterClass = getGenericParameterClass(field);
		return isBlockElement(listParameterClass);
	}

	public static boolean isElement(Class<?> clazz) {
		return Element.class.isAssignableFrom(clazz);
	}

	public static boolean isElement(Field field) {
		return isElement(field.getType());
	}

	public static boolean isElementList(Field field) {
		if (!isParameterizedList(field)) {
			return false;
		}

		if (field.getAnnotation(PredefinedLocator.class) == null && field.getAnnotation(DefinedLocators.class) == null) {
			return false;
		}
		Class<?> listParameterClass = getGenericParameterClass(field);

		return isElement(listParameterClass);
	}

	public static boolean isSelect(Class<?> clazz) {
		return Select.class.isAssignableFrom(clazz);
	}

	public static boolean isSelect(Field field) {
		return isSelect(field.getType());
	}

	private static boolean isList(Field field) {
		return List.class.isAssignableFrom(field.getType());
	}

	private static boolean hasGenericParameter(Field field) {
		return field.getGenericType() instanceof ParameterizedType;
	}

	private static boolean isParameterizedList(Field field) {
		return isList(field) && hasGenericParameter(field);
	}

	public static Class<?> getGenericParameterClass(Field field) {
		if (!hasGenericParameter(field)) {
			return null;
		}
		Type genericType = field.getGenericType();
		return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
	}

}
