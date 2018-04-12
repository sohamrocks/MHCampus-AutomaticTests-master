package com.mcgraw.test.automation.framework.core.common.remote_access.mail.connect;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.net.ssl.TrustManager;

import com.mcgraw.test.automation.framework.core.common.remote_access.mail.Mailbox;
import com.mcgraw.test.automation.framework.core.common.remote_access.mail.utils.DummyTrustManager;
import com.sun.mail.util.MailSSLSocketFactory;

public class SmtpConnection {
	public static final String MAIL_PORT = "mail.smtp.port";

	public static final String MAIL_FROM_PROPRTY = "mail.from";

	public static final String MAIL_PORT_PROPRTY = "mail.smtp.port";

	public static final String MAIL_HOST_PROPRTY = "mail.smtp.host";

	public static final String MAIL_AUTH_PROPRTY = "mail.smtp.auth";

	public static final String MAIL_SUBMITTER_PROPRTY = "mail.smtp.submitter"; 

	public static final String MAIL_SSL_PROPRTY = "mail.smtp.ssl.socketFactory";

	public static final String MAIL_STARTTLS_PROPRTY = "mail.smtp.starttls.enable"; 

	public static class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator(String userName, String password) {
			super();
			authentication = new PasswordAuthentication(userName, password);
		}

		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}

	}

	/**
	 * @param from
	 * @param isSmtpAuth
	 * @return
	 * @throws MessagingException
	 */
	public static Session getSmtpMailSession(Mailbox from, boolean isSmtpAuth)
			throws MessagingException {
		Session mailSession;
		Properties props = new Properties();

		props.put(MAIL_HOST_PROPRTY, from.getHost());
		props.put(MAIL_PORT, getPort(isSmtpAuth));
		props.put(MAIL_FROM_PROPRTY, from.getAddress());
		if (isSmtpAuth) {
			System.setProperty("javax.net.debug", "ssl,handshake");

			MailSSLSocketFactory socketFactory;
			try {
				socketFactory = new MailSSLSocketFactory();
				socketFactory.setTrustAllHosts(true);
				TrustManager[] trustManagers = { new DummyTrustManager() };
				socketFactory.setTrustManagers(trustManagers);
			} catch (GeneralSecurityException ex) {
				throw new MessagingException(
						"Can't create trust-all SSL socket factory - "
								+ ex.getMessage());
			}

			props.put(MAIL_SSL_PROPRTY, socketFactory);
			props.put(MAIL_AUTH_PROPRTY, "true");
			props.put(MAIL_STARTTLS_PROPRTY, "true");

			Authenticator auth = new Authenticator(from.getLogin(),
					from.getPassword());
			props.put(MAIL_SUBMITTER_PROPRTY, auth.getPasswordAuthentication()
					.getUserName());
			mailSession = Session.getInstance(props, auth);
		} else {
			mailSession = Session.getInstance(props);
		}

		return mailSession;
	}

	private static int getPort(boolean ssl) {
		if (ssl) {
			return 465;
		} else {
			return 25;
		}
	}
}
