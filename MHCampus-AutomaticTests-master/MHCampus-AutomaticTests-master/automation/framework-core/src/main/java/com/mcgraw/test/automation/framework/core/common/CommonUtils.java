package com.mcgraw.test.automation.framework.core.common;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * Common methods
 *
 * @author Andrei Varabyeu
 *
 */
public class CommonUtils {

	public static final String NON_NEGATIVE_INT_PATTERN = "\\+?\\d+";
	public static final String NON_NEGATIVE_FLOAT_PATTERN = "\\+?\\d+(\\.\\d+)?";

	/**
	 * Returns Country name by ISO 3166 2 letter country code
	 *
	 * @param iso3166code
	 * @return
	 */
	public static String getCountryName(String iso3166code) {
		Locale[] locales = Locale.getAvailableLocales();
		for (Locale locale : locales) {
			if (iso3166code.equals(locale.getCountry())) {
				return locale.getDisplayCountry(Locale.US);
			}
		}
		throw new RuntimeException(
				"Could not find locale for country with ISO-3166 Code '"
						+ iso3166code + "'");
	}

	public static Set<String> getAvailibleCountryNames() {
		Locale[] locales = Locale.getAvailableLocales();
		Set<String> countryNames = new HashSet<String>(locales.length);
		for (Locale locale : locales) {
			String currentCountry;
			if (!(currentCountry = locale.getDisplayCountry(Locale.US))
					.isEmpty())
				countryNames.add(currentCountry);
		}
		return countryNames;
	}

	/**
	 * Returns Locale name by ISO 3166 2 letter country code
	 *
	 * @param iso3166code
	 * @return
	 */
	public static Locale getLocale(String iso3166code) {
		Locale[] locales = Locale.getAvailableLocales();
		for (Locale locale : locales) {
			if (iso3166code.equals(locale.getCountry())) {
				return locale;
			}
		}
		throw new RuntimeException(
				"Could not find locale for country with ISO-3166 Code '"
						+ iso3166code + "'");
	}

	public static Date parseDate(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse date: " + date, e);
		}
	}
	
	public static String parseDateToString(Date date) {
		return parseDateToString(date, "yyyy/MM/dd");
}
	
	public static String parseDateToString(Date date, String format) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
	}

	public static <T> boolean isEquals(Collection<T> collection1,
			Collection<T> collection2, Comparator<T> comparator) {
		if (collection1 == collection2)
			return true;
		if (collection1 == null || collection2 == null)
			return false;
		if (collection1.size() != collection2.size())
			return false;
		Iterator<T> collection1Iterator = collection1.iterator();
		Iterator<T> collection2Iterator = collection1.iterator();
		while (collection1Iterator.hasNext()) {
			T obj1 = collection1Iterator.next();
			T obj2 = collection2Iterator.next();
			if (0 != comparator.compare(obj1, obj2))
				return false;
		}
		return true;
	}

	public static <T> boolean isEquals(Collection<T> collection1,
			Collection<T> collection2) {
		return symmetricDifference(collection1, collection2).isEmpty();
	}

	public static <K, V> boolean isEquals(Map<K, V> collection1,
			Map<K, V> collection2) {
		return Maps.difference(collection1, collection2).areEqual();
	}

	public static <K, V> Map<K, ValueDifference<V>> symmetricDifference(
			Map<K, V> map1, Map<K, V> map2) {
		Logger.debug("Calculating Symmetric difference for maps: \n" + map1
				+ "\n" + map2);
		return Maps.difference(map1, map2).entriesDiffering();
	}

	public static <T> Set<T> symmetricDifference(Collection<T> collection1,
			Collection<T> collection2) {
		Logger.debug("Calculating Symmetric difference for collections: \n"
				+ collection1 + "\n" + collection2);
		return Sets.symmetricDifference(Sets.newHashSet(collection1),
				Sets.newHashSet(collection2));
	}

	public static boolean isPositiveInt(String string) {
		if (string == null)
			return false;
		if (string.matches(NON_NEGATIVE_INT_PATTERN)) {
			return Integer.parseInt(string) > 0;
		} else {
			return false;
		}
	}

	public static boolean isPositiveFloat(String string) {
		if (string == null)
			return false;
		if (string.matches(NON_NEGATIVE_FLOAT_PATTERN)) {
			return Float.parseFloat(string) > 0;
		} else {
			return false;
		}
	}

	/**
	 * Parses specified group using regular expressions
	 *
	 * @param str
	 * @param pattern
	 * @param group
	 * @return
	 */
	public static String parseGroup(String str, Pattern pattern, int group) {
		Matcher matcher = pattern.matcher(str);
		if (!matcher.find()) {
			throw new IllegalStateException("No match found");
		}
		return matcher.group(group);
	}

	/**
	 * Parses all groups using provided regexp
	 *
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static List<String> parseGroups(String str, Pattern pattern) {
		Matcher matcher = pattern.matcher(str);
		if (!matcher.find()) {
			throw new IllegalStateException("No match found");
		}
		List<String> groups = new ArrayList<String>(matcher.groupCount());
		for (int i = 0; i < matcher.groupCount(); i++) {
			groups.add(matcher.group(i));
		}
		return groups;
	}

	/**
	 * Parses number using specified Locale
	 *
	 * @param number
	 * @param locale
	 * @return
	 */
	public static Number parseNumber(String number, Locale locale) {
		try {
			return NumberFormat.getInstance(locale).parse(number);
		} catch (ParseException e) {
			throw new CommonTestRuntimeException("Unable to parse number '"
					+ number + "' using locale '" + locale + "'", e);
		}
	}

	/**
	 * Parses number using US Locale
	 *
	 * @param number
	 * @return
	 */
	public static Number parseNumber(String number) {
		return parseNumber(number, Locale.US);
	}
	
	public static String timeStamp() {
		return 	new java.text.SimpleDateFormat("yyyyMMdd_HHmm").format(java.util.Calendar.getInstance ().getTime());
	}	
	
	public static String timeStampDetailed() {
		return 	new java.text.SimpleDateFormat("yyyyMMdd-HHmmssSSS").format(java.util.Calendar.getInstance ().getTime());
	}	
}