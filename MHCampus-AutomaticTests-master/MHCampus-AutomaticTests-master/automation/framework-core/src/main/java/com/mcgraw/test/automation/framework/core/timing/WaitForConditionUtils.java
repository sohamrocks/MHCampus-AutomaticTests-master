package com.mcgraw.test.automation.framework.core.timing;

public class WaitForConditionUtils {
	
    private static long DefaultTimeout = 10000;	

	public interface TestCondition {
		  public Boolean condition() throws Exception;
	}	

	public static void WaitForCondition(TestCondition testCond, long timeoutMillis, String message) throws Exception {
		long started = System.currentTimeMillis();
		while (System.currentTimeMillis() - started < timeoutMillis) {
			if (testCond.condition()) {
				return;
			}
			Thread.sleep(5);
		}
		throw new Exception("Wait condition never became true: " + message + ". Condition: " +  testCond.getClass().getName());
	}

	public static void WaitForCondition(TestCondition testCond, long timeoutMillis) throws Exception {
		WaitForCondition(testCond, timeoutMillis, "No message");
	}
	
	public static void WaitForCondition(TestCondition testCond) throws Exception {
		WaitForCondition(testCond, DefaultTimeout, "No message");
	}	
	
	public static boolean WaitForConditionNoException(TestCondition testCond, long timeoutMillis) throws Exception {
		long started = System.currentTimeMillis();
		while (System.currentTimeMillis() - started < timeoutMillis) {
			if (testCond.condition()) {
				return true;
			}
			Thread.sleep(5);
		}

		return false;
	}
}
