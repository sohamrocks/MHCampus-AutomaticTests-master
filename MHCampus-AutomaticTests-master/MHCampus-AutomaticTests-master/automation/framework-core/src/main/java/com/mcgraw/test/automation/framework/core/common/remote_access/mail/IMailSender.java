package com.mcgraw.test.automation.framework.core.common.remote_access.mail;

import javax.mail.MessagingException;

public interface IMailSender {
	/**
	 * Send letter via SMTP
	 *
	 * @param from
	 * @param to
	 * @param subject
	 * @param body
	 * @return letter
	 * @throws MessagingException
	 */
	public Letter sendLetter(Mailbox from, String to, String subject,
			String body, boolean ssl);

}
