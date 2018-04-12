package com.mcgraw.test.automation.ui.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mcgraw.test.automation.framework.core.common.remote_access.mail.Letter;

public class WelcomeEmail {

	private String username;
	private String password;
	private String loginPageAddress;
	private String customerNumber;
	private String sharedSecret;

	@Override
	public String toString() {
		return "CredentialsFromEmail [username=" + username + ", password="
				+ password + ", loginPageAddress=" + loginPageAddress
				+ ", customerNumber=" + customerNumber + ", sharedSecret="
				+ sharedSecret + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((customerNumber == null) ? 0 : customerNumber.hashCode());
		result = prime
				* result
				+ ((loginPageAddress == null) ? 0 : loginPageAddress.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((sharedSecret == null) ? 0 : sharedSecret.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WelcomeEmail other = (WelcomeEmail) obj;
		if (customerNumber == null) {
			if (other.customerNumber != null)
				return false;
		} else if (!customerNumber.equals(other.customerNumber))
			return false;
		if (loginPageAddress == null) {
			if (other.loginPageAddress != null)
				return false;
		} else if (!loginPageAddress.equals(other.loginPageAddress))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (sharedSecret == null) {
			if (other.sharedSecret != null)
				return false;
		} else if (!sharedSecret.equals(other.sharedSecret))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public static class InstanceCredentials {
		public String institution;
		public String username;
		public String password;
		public String pageAddressFromEmail;
		public String customerNumber;
		public String sharedSecret;
		public String pageAddressForLogin;
	}

	public static InstanceCredentials extractCredentials(Letter letter) {

		Pattern patternForRef = Pattern
				.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		Pattern patternForUsername = Pattern
				.compile("\\b[\\w.%-]+@[-.\\w]+\\.[-.\\w]+\\.[A-Za-z]{2,4}\\b");
		Pattern patternForPassword = Pattern.compile("Password:&#09;[\\w]+");
//		Pattern patternForPassword = Pattern.compile("Password:&#09;\\s+[\\w]+");
		Pattern patternForCustomerNumber = Pattern
				.compile("Customer Number:&#09;[\\w]+[-][\\w]+[-][\\w]+");
		//Pattern patternForCustomerNumber = Pattern
				//.compile("Customer Number:\\s+[\\w]+[-][\\w]+[-][\\w]+");
		Pattern patternSharedSecret = Pattern
			.compile("Shared Secret:&#09;[\\w]+");
		//Pattern patternSharedSecret = Pattern
				//.compile("Shared Secret:\\s+[\\w]+");

		Matcher matchRef = patternForRef.matcher("");
		Matcher matchUsername = patternForUsername.matcher("");
		Matcher matchPassword = patternForPassword.matcher("");
		Matcher matchCustomerNumber = patternForCustomerNumber.matcher("");
		Matcher matchSharedSecret = patternSharedSecret.matcher("");

		String body = letter.body;

		InstanceCredentials credentials = new InstanceCredentials();

		matchRef.reset(body);
		if (matchRef.find()) {
			credentials.pageAddressFromEmail = matchRef.group();
		}
		matchUsername.reset(body);
		if (matchUsername.find()) {
			credentials.username = matchUsername.group();
		}
		matchPassword.reset(body);
		if (matchPassword.find()) {
			credentials.password = matchPassword.group().substring(14);
		}
		matchCustomerNumber.reset(body);
		if (matchCustomerNumber.find()) {
			credentials.customerNumber = matchCustomerNumber.group().substring(
					21);
		}
		matchSharedSecret.reset(body);
		if (matchSharedSecret.find()) {
			credentials.sharedSecret = matchSharedSecret.group().substring(19);
		}

		return credentials;
	}
}