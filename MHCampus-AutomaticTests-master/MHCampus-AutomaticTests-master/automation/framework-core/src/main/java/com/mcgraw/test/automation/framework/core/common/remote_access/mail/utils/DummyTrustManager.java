package com.mcgraw.test.automation.framework.core.common.remote_access.mail.utils;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * DummyTrustManager - NOT SECURE
 */

public class DummyTrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] cert, String authType) {
		// everything is trusted
	}

	@Override
	public void checkServerTrusted(X509Certificate[] cert, String authType) {
		// everything is trusted
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}
}
