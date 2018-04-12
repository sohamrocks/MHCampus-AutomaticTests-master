package com.mcgraw.test.automation.framework.core.common.remote_access.mail;

import java.util.List;

/**
 * @author nmironchyk
 */

public interface IMailReceiver {

	public static final String INBOX = "INBOX";

	/**
	 * Fetch text representation of all content of mailbox
	 *
	 * @param mailBox
	 * @param ssl
	 *            is SSL enabled
	 * @return List of Letters
	 */
	public List<Letter> receiveMail(Mailbox mailbox, boolean ssl);

	/**
	 * Fetch text representation of all content of mailbox
	 *
	 * @param mailbox
	 * @param folder
	 *            - Folder name
	 * @param ssl
	 *            - is SSL enabled
	 * @return
	 */
	public List<Letter> receiveMail(Mailbox mailbox, String folder, boolean ssl);

	/**
	 * Fetch text representation of all content of mailbox
	 *
	 * @param mailBox
	 * @param ssl
	 *            if ssl enabled
	 * @return text representation of all content of mailbox
	 */
	public String receiveFullContent(Mailbox mailbox, boolean ssl);

}
