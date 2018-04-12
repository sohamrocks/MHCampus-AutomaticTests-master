package com.mcgraw.test.automation.framework.core.common.remote_access.mail.connect;

import javax.mail.URLName;

import com.sun.mail.pop3.POP3Store;

public class Pop3MailConnection extends AbstractMailConnection {
	public Pop3MailConnection(String mailServer, String login, String password,
			boolean ssl) {
		super(mailServer, login, password, ssl);
	}

	@Override
	protected int getPort(boolean ssl) {
		if (ssl) {
			return 995;
		} else {
			return 110;
		}
	}

	@Override
	protected String getPropertyPrefix() {
		return "mail.pop3";
	}

	@Override
	URLName getUrlName() {
		URLName urlName = new URLName("pop3", mailServer, getPort(ssl), null,
				login, password);
		return urlName;
	}

	@Override
	public POP3Store getStore() {		
		POP3Store store = new POP3Store(this.getSession(), this.getUrlName());
		return store;
	}

}
