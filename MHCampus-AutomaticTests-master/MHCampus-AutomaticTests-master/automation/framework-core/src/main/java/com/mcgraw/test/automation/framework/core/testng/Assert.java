package com.mcgraw.test.automation.framework.core.testng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.results.logger.LoggerLevel;

/**
 * Custom Assert class allow to use verify methods
 * 
 * @author Andrei_Turavets
 *
 */
public class Assert extends org.testng.Assert{
	
	/** Thread-Safe failures map */
	private static ThreadLocal<Map<ITestResult, List<Throwable>>> verificationFailuresMap = new ThreadLocal<Map<ITestResult, List<Throwable>>>(){
		@Override
		protected Map<ITestResult, List<Throwable>> initialValue() {
			return new HashMap<ITestResult, List<Throwable>>();
		}
	};
	
	private static Map<ITestResult, List<Throwable>> getVerificationFailuresMap(){
		return verificationFailuresMap.get();
	}
		
	/** This value is becomes true in custom runner to support verifies
	 *  In eclipse runner it is false, all verifies will behave themselves as asserts as we can't handle verifies in eclipse runner*/
	public static boolean IS_VERIFY_SUPPORTED = false;
	
	private static String ERROR_MESSAGE_PATTERN= "%1$s. See stacktrace of error [%2$s] in the end of the test ";
    
    public static void verifyTrue(boolean condition) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertTrue(condition);
    		return;
    	}
    	try {
    		assertTrue(condition);
    	} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
    		
    	}
    }
    
    public static void verifyTrue(boolean condition, String message) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertTrue(condition, message);
    		return;
    	}
    	try {
    		assertTrue(condition, message);
    	} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
    	}
    }
    
    public static void verifyFalse(boolean condition) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertFalse(condition);
    		return;
    	}
    	try {
    		assertFalse(condition);
		} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
		}
    }
    
    public static void verifyFalse(boolean condition, String message) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertFalse(condition, message);
    		return;
    	}
    	try {
    		assertFalse(condition, message);
    	} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
    	}
    }
    
    public static void verifyEquals(boolean actual, boolean expected) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertEquals(actual, expected);
    		return;
    	}
    	try {
    		assertEquals(actual, expected);
		} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
		}
    }
    
    public static void verifyEquals(boolean actual, boolean expected, String message) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertEquals(actual, expected, message);
    		return;
    	}
    	try {
    		assertEquals(actual, expected, message);
		} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
		}
    }

    public static void verifyEquals(Object actual, Object expected) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertEquals(actual, expected);
    		return;
    	}
    	try {
    		assertEquals(actual, expected);
		} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
		}
    }
    
    public static void verifyEquals(Object actual, Object expected, String message) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertEquals(actual, expected, message);
    		return;
    	}
    	try {
    		assertEquals(actual, expected, message);
		} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
		}
    }
    
    public static void verifyEquals(Object[] actual, Object[] expected,  String message) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertEquals(actual, expected, message);
    		return;
    	}
    	try {
    		assertEquals(actual, expected, message);
		} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
		}
    }
    
    public static void verifyNotEquals(boolean actual, boolean expected) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertNotEquals(actual, expected);
    		return;
    	}
    	try {
    		assertNotEquals(actual, expected);
		} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
		}
    }
    
    public static void verifyNotEquals(boolean actual, boolean expected, String message) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertNotEquals(actual, expected, message);
    		return;
    	}
    	try {
    		assertNotEquals(actual, expected, message);
		} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
		}
    }
    
    public static void verifyNotEquals(Object actual, Object expected) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertNotEquals(actual, expected);
    		return;
    	}
    	try {
    		assertNotEquals(actual, expected);
		} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
		}
    }
    
    public static void verifyNotEquals(Object actual, Object expected, String message) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertNotEquals(actual, expected, message);
    		return;
    	}
    	try {
    		assertNotEquals(actual, expected, message);
		} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
		}
    }
    
    public static void verifyNotEquals(Object[] actual, Object[] expected) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertNotEquals(actual, expected);
    		return;
    	}
    	try {
    		assertNotEquals(actual, expected);
		} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
		}
    }
    
    public static void verifyNotEquals(Object[] actual, Object[] expected,  String message) {
    	if (!IS_VERIFY_SUPPORTED){
    		assertNotEquals(actual, expected, message);
    		return;
    	}
    	try {
    		assertNotEquals(actual, expected, message);
		} catch(Throwable e) {
    		addVerificationFailure(e);
    		Logger.getLogger().log(LoggerLevel.VERIFY_FAILED, String.format(ERROR_MESSAGE_PATTERN, ExceptionUtils.getMessage(e), getVerificationFailures().size()));
		}
    }
    
	public static List<Throwable> getVerificationFailures() {
		List<Throwable> verificationFailures = getVerificationFailuresMap().get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>() : verificationFailures;
	}
	
	public static void clearVerificationFailuresMap(){
		getVerificationFailuresMap().clear();
	}
	
	private static void addVerificationFailure(Throwable e) {
		List<Throwable> verificationFailures = getVerificationFailures();
		getVerificationFailuresMap().put(Reporter.getCurrentTestResult(), verificationFailures);
		verificationFailures.add(e);
	}

}
