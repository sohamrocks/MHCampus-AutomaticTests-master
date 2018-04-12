package com.mcgraw.test.automation.ui.service;

import java.util.List;

import com.mcgraw.test.automation.framework.core.common.remote_access.mail.IMAPMailReceiver;
import com.mcgraw.test.automation.framework.core.common.remote_access.mail.Letter;
import com.mcgraw.test.automation.framework.core.common.remote_access.mail.Mailbox;
import com.mcgraw.test.automation.framework.core.timing.WaitForConditionUtils;
import com.mcgraw.test.automation.framework.core.timing.WaitForConditionUtils.TestCondition;

public class EmailClient {

	private Mailbox mailbox;
	private IMAPMailReceiver mailReceiver;
	private List<Letter> foundLetters;
	
	public void setMailbox(Mailbox mailbox) {
		this.mailbox = mailbox;
	}

	public void setMailReceiver(IMAPMailReceiver mailReceiver) {
		this.mailReceiver = mailReceiver;
	}

	public String getLogin() {
		return mailbox.getLogin();
	}
	
	public class EmailArrived implements TestCondition {
		private String partOfBody;

		public EmailArrived(String partOfBody) {
			super();
			this.partOfBody = partOfBody;
		}

		public Boolean condition() throws Exception {
			foundLetters = mailReceiver.getMailsByBodyContent(mailbox, "INBOX",
					true, partOfBody);
			return foundLetters.size() > 0;
		}
	}

	public List<Letter> WaitForEmailWithBodyContentArrival(String partOfBody)
			throws Exception {
		WaitForConditionUtils.WaitForCondition(new EmailArrived(partOfBody),
				300000, "No e-mail that contains [" + partOfBody
						+ "] arrived within 2 minutes!");
		return foundLetters;
	}

	public void deleteEmailByBodyContent(String partOfBody) {
		mailReceiver.deleteMailsByBodyContent(mailbox, "INBOX", true,
				partOfBody);
	}
}
