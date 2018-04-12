package com.mcgraw.test.automation.framework.selenium2.ui.proxyhandlers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Invocation handler decorator<br>
 *
 * @author Andrei Varabyeu
 *
 */
abstract public class InvocationHandlerDecorator implements InvocationHandler {

	private InvocationHandler handler;

	public InvocationHandlerDecorator(InvocationHandler handler) {
		this.handler = handler;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return handler.invoke(proxy, method, args);
	}

}
