package com.mcgraw.test.automation.framework.core.common.remote_access.mail;

import java.util.Date;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import com.mcgraw.test.automation.framework.core.common.remote_access.mail.connect.SmtpConnection;
import com.mcgraw.test.automation.framework.core.exception.MailAuthFailedException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class SMTPMailSender implements IMailSender {

	private static final Random random = new Random();

	/**
	 * Send letter via SMTP
	 *
	 * @author nmironchyk
	 * @param from
	 * @param to
	 * @param subject
	 * @param body
	 * @return letter
	 * @throws MessagingException
	 */
	@Override
	public Letter sendLetter(Mailbox from, String to, String subject,
			String body, boolean ssl) {
		try {
			Session mailSession = SmtpConnection.getSmtpMailSession(from, ssl);
			if (subject == null) {
				subject = "Random Subject" + random.nextInt();
			}
			if (body == null) {
				body = "Random Body" + random.nextInt();
			}
			Logger.operation("Send message from " + from.getAddress() + ", to "
					+ to + ", using server " + from.getHost()
					+ " With subject='"
					+ subject.substring(0, Math.min(subject.length(), 1024))
					+ "' and body = '"
					+ body.substring(0, Math.min(body.length(), 1024)) + "'");

			MimeMessage msg = new MimeMessage(mailSession);
			msg.setFrom();
			msg.setRecipients(Message.RecipientType.TO, to);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(body);
			sendMessage(msg);
			Letter ret = new Letter(subject, body);
			ret.setHeader("Subject", subject);
			return ret;
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send letter...", e);
		}
	}

	/**
	 * Send letter with autogenerated subject and body
	 *
	 * @param from
	 *            address of sender
	 * @param to
	 *            address of destination
	 */
	public Letter sendLetter(Mailbox from, String to, boolean ssl)
			throws MessagingException {
		return sendLetter(from, to, null, null, ssl);
	}

	private void sendMessage(Message msg) throws MessagingException {
		try {
			Transport.send(msg);
		} catch (javax.mail.AuthenticationFailedException exception) {
			throw new MailAuthFailedException(
					"Could not connect to mail server: "
							+ exception.getMessage(), exception);
		}
	}

}