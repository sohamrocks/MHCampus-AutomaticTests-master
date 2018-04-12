package com.mcgraw.test.automation.framework.core.common.remote_access.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

import com.mcgraw.test.automation.framework.core.common.remote_access.mail.connect.ImapMailConnection;
import com.mcgraw.test.automation.framework.core.common.remote_access.mail.utils.MailReceiveHelper;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class IMAPMailReceiver implements IMailReceiver {
	public static final String PROTOCOL_IMAP = "imap";

	@Override
	public List<Letter> receiveMail(Mailbox mailbox, String folderName, boolean ssl) {
		{
			Store store = null;
			Folder folder = null;
			try {
				Logger.operation("Getting all messages of mailbox with owner '" + mailbox.getLogin() + "', password '"
						+ mailbox.getPassword() + "', at " + PROTOCOL_IMAP + " server '" + mailbox.getHost() + "', with ssl = " + ssl);
				ImapMailConnection mailConnection = new ImapMailConnection(mailbox.getHost(), mailbox.getLogin(), mailbox.getPassword(),
						ssl);
				store = MailReceiveHelper.connect(mailConnection);
				folder = store.getFolder(folderName);
				folder.open(Folder.READ_ONLY);
				Logger.info("Mailbox contains " + folder.getMessageCount() + " messages");

				int messageCount = folder.getMessageCount();
				List<Letter> letters = new ArrayList<Letter>(messageCount);

				for (int i = 1; i <= messageCount; i++) {
					Message msg = folder.getMessage(i);
					Logger.debug("Message #" + i + " received");
					Logger.trace("Message #" + i + ":");
					String message = MailReceiveHelper.extractMessageHeadersAndText(msg);
					Logger.trace(message);
					letters.add(new Letter(msg));
				}
				return letters;
			} catch (MessagingException e) {
				throw new RuntimeException("Unable to retrieve mail...", e);
			} catch (IOException e) {
				throw new RuntimeException("Unable to extract message text from mail...", e);
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
			Logger.operation("Getting full content of mailbox with owner '" + mailbox.getLogin() + "', password '" + mailbox.getPassword()
					+ "', at " + PROTOCOL_IMAP + " server '" + mailbox.getHost() + "', with ssl = " + ssl);
			ImapMailConnection mailConnection = new ImapMailConnection(mailbox.getHost(), mailbox.getLogin(), mailbox.getPassword(), ssl);
			store = MailReceiveHelper.connect(mailConnection);
			folder = store.getFolder(IMailReceiver.INBOX);
			folder.open(Folder.READ_ONLY);
			Logger.info("Mailbox contains " + folder.getMessageCount() + " messages");

			final StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= folder.getMessageCount(); i++) {
				Message msg = folder.getMessage(i);
				sb.append(MailReceiveHelper.extractMessageHeadersAndText(msg));
			}
			return sb.toString();
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to retrieve mail...", e);
		} catch (IOException e) {
			throw new RuntimeException("Unable to extract message text from mail...", e);
		} finally {
			MailReceiveHelper.closeFolderSilently(folder, false);
			MailReceiveHelper.closeStoreSilently(store);
		}
	}

	public void removeFolder(Mailbox mailbox, String folderName, boolean ssl) {
		Store store = null;
		Folder folder = null;
		try {
			Logger.operation("Removing folder with name '" + folderName + "' of mailbox with owner '" + mailbox.getLogin()
					+ "', password '" + mailbox.getPassword() + "', at " + PROTOCOL_IMAP + " server '" + mailbox.getHost()
					+ "', with ssl = " + ssl);
			ImapMailConnection mailConnection = new ImapMailConnection(mailbox.getHost(), mailbox.getLogin(), mailbox.getPassword(), ssl);
			store = MailReceiveHelper.connect(mailConnection);
			folder = store.getFolder(folderName);
			folder.delete(true);
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to retrieve mail...", e);
		} catch (IOException e) {
			throw new RuntimeException("Unable to extract message text from mail...", e);
		} finally {
			MailReceiveHelper.closeStoreSilently(store);
		}
	}

	public void truncateFolder(Mailbox mailbox, String folderName, boolean ssl) {
		Store store = null;
		Folder folder = null;
		try {
			Logger.operation("Removing all messages from folder with name '" + folderName + "' of mailbox with owner '"
					+ mailbox.getLogin() + "', password '" + mailbox.getPassword() + "', at " + PROTOCOL_IMAP + " server '"
					+ mailbox.getHost() + "', with ssl = " + ssl);
			ImapMailConnection mailConnection = new ImapMailConnection(mailbox.getHost(), mailbox.getLogin(), mailbox.getPassword(), ssl);
			store = MailReceiveHelper.connect(mailConnection);
			folder = store.getFolder(folderName);
			folder.open(Folder.READ_WRITE);
			for (int i = 1, count = folder.getMessageCount(); i <= count; i++) {
				Message message = folder.getMessage(i);
				Logger.debug("Deleting message with subject '" + message.getSubject() + "'... ");
				message.setFlag(Flags.Flag.DELETED, true);
			}
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to retrieve mail...", e);
		} catch (IOException e) {
			throw new RuntimeException("Unable to extract message text from mail...", e);
		} finally {
			MailReceiveHelper.closeFolderSilently(folder, true);
			MailReceiveHelper.closeStoreSilently(store);
		}
	}

	public void deleteMailsByBodyContent(Mailbox mailbox, String folderName, boolean ssl, String partOfBody) {
		Store store = null;
		Folder folder = null;
		try {
			Logger.operation("Removing messages which contain [" + partOfBody + "] in body from folder with name '" + folderName
					+ "' of mailbox with owner '" + mailbox.getLogin() + "', password '" + mailbox.getPassword() + "', at " + PROTOCOL_IMAP
					+ " server '" + mailbox.getHost() + "', with ssl = " + ssl);
			ImapMailConnection mailConnection = new ImapMailConnection(mailbox.getHost(), mailbox.getLogin(), mailbox.getPassword(), ssl);
			store = MailReceiveHelper.connect(mailConnection);
			folder = store.getFolder(folderName);
			folder.open(Folder.READ_WRITE);
			int counterForDeletedMessages = 0;
			//for (int i = 1, count = folder.getMessageCount(); i <= count; i++) {
				//Message message = folder.getMessage(i);
				//String mesageBody = MailReceiveHelper.obtainBodyFromMessage(message);
				//if (mesageBody.contains(partOfBody)) {
				//	message.setFlag(Flags.Flag.DELETED, true);
				//	counterForDeletedMessages++;
				//}
			//}
			   int count = folder.getMessageCount();
			   for (int i = count; i >= count-3; i--) {
			    Message message = folder.getMessage(i);
			    String mesageBody = MailReceiveHelper.obtainBodyFromMessage(message);
			    if (mesageBody.contains(partOfBody)) {
			     message.setFlag(Flags.Flag.DELETED, true);
			     counterForDeletedMessages++;
			    }
			   }
			if (counterForDeletedMessages > 0) {
				Logger.info("Deleted " + counterForDeletedMessages + " message(s) with content [" + partOfBody + "] in body ");
			} else {
				Logger.info("No messages with content [" + partOfBody + "] in body were found to delete");
			}

		} catch (MessagingException e) {
			throw new RuntimeException("Unable to retrieve mail...", e);
		} catch (IOException e) {
			throw new RuntimeException("Unable to extract message text from mail...", e);
		} finally {
			MailReceiveHelper.closeFolderSilently(folder, true);
			MailReceiveHelper.closeStoreSilently(store);
		}
	}
		
		public List<Letter> getMailsByBodyContent(Mailbox mailbox, String folderName, boolean ssl, String partOfBody) {
			Store store = null;
			Folder folder = null;
			List<Letter> targetLetters = new ArrayList<Letter>();
			try {
				Logger.operation("Get messages which contain [" + partOfBody + "] in body from folder with name '" + folderName
						+ "' of mailbox with owner '" + mailbox.getLogin() + "', password '" + mailbox.getPassword() + "', at " + PROTOCOL_IMAP
						+ " server '" + mailbox.getHost() + "', with ssl = " + ssl);
				ImapMailConnection mailConnection = new ImapMailConnection(mailbox.getHost(), mailbox.getLogin(), mailbox.getPassword(), ssl);
				store = MailReceiveHelper.connect(mailConnection);
				folder = store.getFolder(folderName);
				folder.open(Folder.READ_ONLY);
				//for (int i = 1, count = folder.getMessageCount(); i <= count; i++) {
				//	Message message = folder.getMessage(i);
					//String mesageBody = MailReceiveHelper.obtainBodyFromMessage(message);
				//	if (mesageBody.contains(partOfBody)) {
						//targetLetters.add(new Letter(message));
				//	}
				    int count = folder.getMessageCount();
				    for (int i = count; i >= count-3; i--) {
				     Message message = folder.getMessage(i);
				     String mesageBody = MailReceiveHelper.obtainBodyFromMessage(message);
				     if (mesageBody.contains(partOfBody)) {
				      targetLetters.add(new Letter(message));
				     }
				    }
				
				Logger.info("Mailbox contains " + targetLetters.size() + " messages which contain [" + partOfBody + "] in body");
				return targetLetters;

			} catch (MessagingException e) {
				throw new RuntimeException("Unable to retrieve mail...", e);
			} catch (IOException e) {
				throw new RuntimeException("Unable to extract message text from mail...", e);
			} finally {
				MailReceiveHelper.closeFolderSilently(folder, true);
				MailReceiveHelper.closeStoreSilently(store);
			}

	}

}
