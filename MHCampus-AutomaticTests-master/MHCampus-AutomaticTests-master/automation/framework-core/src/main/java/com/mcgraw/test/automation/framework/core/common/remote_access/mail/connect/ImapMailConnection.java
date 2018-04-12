package com.mcgraw.test.automation.framework.core.common.remote_access.mail.connect;

import javax.mail.URLName;

import com.sun.mail.imap.IMAPStore;

public class ImapMailConnection extends AbstractMailConnection {
	public ImapMailConnection(String mailServer, String login, String password,
			boolean ssl) {
		super(mailServer, login, password, ssl);
	}

	@Override
	protected int getPort(boolean ssl) {
		if (ssl) {
			return 993;
		} else {
			return 143;
		}
	}

	@Override
	protected String getPropertyPrefix() {
		return "mail.imap";
	}

	@Override
	URLName getUrlName() {
		URLName urlName = new URLName("imap", mailServer, getPort(ssl), "",
				login, password);
		return urlName;
	}

	@Override
	public IMAPStore getStore() {
		IMAPStore store = new IMAPStore(this.getSession(), this.getUrlName());
		return store;
	}

}
