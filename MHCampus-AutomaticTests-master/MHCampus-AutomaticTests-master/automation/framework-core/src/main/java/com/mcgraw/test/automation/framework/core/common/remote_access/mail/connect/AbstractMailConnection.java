package com.mcgraw.test.automation.framework.core.common.remote_access.mail.connect;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import com.mcgraw.test.automation.framework.core.common.remote_access.mail.utils.DummySSLSocketFactory;

/**
 * @author nmironchyk
 */

public abstract class AbstractMailConnection {
	protected String mailServer;

	protected String login;

	protected String password;

	protected boolean ssl;

	abstract protected int getPort(boolean ssl);

	abstract protected String getPropertyPrefix();

	abstract URLName getUrlName();

	public abstract Store getStore();

	public AbstractMailConnection(String mailServer, String login, String password,
			boolean ssl) {
		this.mailServer = mailServer;
		this.login = login;
		this.password = password;
		this.ssl = ssl;
	}

	Session getSession() {
		int port = getPort(ssl);
		String prefix = getPropertyPrefix();
		Properties props = new Properties();
		props.put(prefix + ".host", mailServer);
		props.put(prefix + ".user", login);
		if (ssl) {								
			props.put(prefix + ".ssl.enable", "true");			
			props.put(prefix + ".socketFactory.class",
					DummySSLSocketFactory.class.getName());
			props.put(prefix + ".socketFactory.fallback", "false");
			props.put(prefix + ".port", port);
			props.put(prefix + ".socketFactory.port", port);
			props.put(prefix + ".connectiontimeout", "60000");
			props.put(prefix + ".timeout", "60000");
			  
			DummySSLSocketFactory.bypassSslCertificates();
		}
		System.out.println(props);
		Session mailSession = Session.getInstance(props);
		return mailSession;
	}
}
