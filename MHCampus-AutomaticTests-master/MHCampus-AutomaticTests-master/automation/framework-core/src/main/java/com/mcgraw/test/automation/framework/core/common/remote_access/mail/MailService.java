package com.mcgraw.test.automation.framework.core.common.remote_access.mail;

import java.util.List;

/**
 * Mail Service: receiver and sender
 *
 * @author Andrei Varabyeu
 *
 */
public class MailService implements IMailReceiver, IMailSender {

	private IMailReceiver mailReceiver;

	private IMailSender mailSender;

	public void setMailReceiver(IMailReceiver mailReceiver) {
		this.mailReceiver = mailReceiver;
	}

	public void setMailSender(IMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public Letter sendLetter(Mailbox from, String to, String subject,
			String body, boolean ssl) {
		return mailSender.sendLetter(from, to, subject, body, ssl);
	}

	@Override
	public List<Letter> receiveMail(Mailbox mailbox, boolean ssl) {
		return mailReceiver.receiveMail(mailbox, ssl);
	}

	@Override
	public String receiveFullContent(Mailbox mailbox, boolean ssl) {
		return mailReceiver.receiveFullContent(mailbox, ssl);
	}

	@Override
	public List<Letter> receiveMail(Mailbox mailbox, String folder, boolean ssl) {
		return mailReceiver.receiveMail(mailbox, folder, ssl);
	}

}
