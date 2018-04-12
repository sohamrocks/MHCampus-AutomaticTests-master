package com.mcgraw.test.automation.framework.core.common.remote_access.mail;

import com.mcgraw.test.automation.framework.core.common.SpecialSymbols;

/**
 * This is a structure class which represents common mailbox
 *
 * @author Andrei Varabyeu
 *
 */

public class Mailbox {

	/* Login to mailbox */
	private String login;

	/* Mailbox host */
	private String host;

	/* Mailbox password */
	private String password;

	public Mailbox(String host, String login, String password) {
		this.login = login;
		this.host = host;
		this.password = password;
	}

	public Mailbox() {

	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return this.login + SpecialSymbols.DOG + this.host;
	}

	@Override
	public String toString() {
		return "Mailbox [login=" + login + ", host=" + host + ", password="
				+ password + "]";
	}

}
