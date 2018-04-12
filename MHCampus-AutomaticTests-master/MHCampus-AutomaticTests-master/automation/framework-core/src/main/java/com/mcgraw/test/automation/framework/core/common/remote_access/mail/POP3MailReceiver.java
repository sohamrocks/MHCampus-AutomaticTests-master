package com.mcgraw.test.automation.framework.core.common.remote_access.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

import com.mcgraw.test.automation.framework.core.common.remote_access.mail.connect.Pop3MailConnection;
import com.mcgraw.test.automation.framework.core.common.remote_access.mail.utils.MailReceiveHelper;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class POP3MailReceiver implements IMailReceiver {
	public static final String PROTOCOL_POP3 = "pop3";

	@Override
	public List<Letter> receiveMail(Mailbox mailbox, String folderName,
			boolean ssl) {
		{
			Store store = null;
			Folder folder = null;
			try {
				Logger.operation("Getting all messages of mailbox with owner '"
						+ mailbox.getLogin() + "', password '"
						+ mailbox.getPassword() + "', at " + PROTOCOL_POP3
						+ " server '" + mailbox.getHost() + "', with ssl = "
						+ ssl);
				Pop3MailConnection mailConnection = new Pop3MailConnection(
						mailbox.getHost(), mailbox.getLogin(),
						mailbox.getPassword(), ssl);
				store = MailReceiveHelper.connect(mailConnection);
				folder = store.getFolder(folderName);
				folder.open(Folder.READ_ONLY);
				Logger.info("Mailbox contains " + folder.getMessageCount()
						+ " messages");

				int messageCount = folder.getMessageCount();
				List<Letter> letters = new ArrayList<Letter>(messageCount);

				for (int i = 1; i <= messageCount; i++) {
					Message msg = folder.getMessage(i);
					Logger.trace("Message #" + i + ":");
					String message = MailReceiveHelper
							.extractMessageHeadersAndText(msg);
					Logger.trace(message);
					letters.add(new Letter(msg));
				}
				return letters;
			} catch (MessagingException e) {
				throw new RuntimeException("Unable to retrieve mail...", e);
			} catch (IOException e) {
				throw new RuntimeException(
						"Unable to extract message text from mail...", e);
			} finally {
				MailReceiveHelper.closeFolderSilently(folder, false);
				MailReceiveHelper.closeStoreSilently(store);
			}
		}
	}

	@Override
	public List<Letter> receiveMail(Mailbox mailbox, boolean ssl) {
		return receiveMail(mailbox, IMailReceiver.INBOX, ssl);
	}

	@Override
	public String receiveFullContent(Mailbox mailbox, boolean ssl) {
		Store store = null;
		Folder folder = null;
		try {
			Logger.operation("Getting full content of mailbox with owner '"
					+ mailbox.getLogin() + "', password '"
					+ mailbox.getPassword() + "', at " + PROTOCOL_POP3
					+ " server '" + mailbox.getHost() + "', with ssl = " + ssl);
			Pop3MailConnection mailConnection = new Pop3MailConnection(
					mailbox.getHost(), mailbox.getLogin(),
					mailbox.getPassword(), ssl);
			store = MailReceiveHelper.connect(mailConnection);
			folder = store.getFolder(IMailReceiver.INBOX);
			folder.open(Folder.READ_ONLY);
			Logger.info("Mailbox contains " + folder.getMessageCount()
					+ " messages");

			final StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= folder.getMessageCount(); i++) {
				Message msg = folder.getMessage(i);
				sb.append(MailReceiveHelper.extractMessageHeadersAndText(msg));
			}
			return sb.toString();
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to retrieve mail...", e);
		} catch (IOException e) {
			throw new RuntimeException(
					"Unable to extract message text from mail...", e);
		} finally {
			MailReceiveHelper.closeFolderSilently(folder, false);
			MailReceiveHelper.closeStoreSilently(store);
		}

	}

}
