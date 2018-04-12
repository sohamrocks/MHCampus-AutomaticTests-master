package com.mcgraw.test.automation.framework.core.common.remote_access.mail.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Predicate;
import com.mcgraw.test.automation.framework.core.common.remote_access.mail.connect.AbstractMailConnection;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.timing.SmartWait;

/**
 * @author nmironchyk
 */

public class MailReceiveHelper {
	
	public static String obtainBodyFromMessage(Message msg) throws IOException, MessagingException{
		Object content = msg.getContent();
		if (content instanceof MimeMultipart) {
			StringBuffer bufferContent = new StringBuffer();
			MimeMultipart mimeMultipartContent = (MimeMultipart) content;
			for (int i = 0; i < mimeMultipartContent.getCount(); i++) {
				BodyPart bodyPart = mimeMultipartContent.getBodyPart(i);
				bufferContent.append("=========BODY PART " + (i + 1) + " ["
						+ bodyPart.getContentType() + "]============\n");
				String[] cteHeader = bodyPart
						.getHeader("Content-Transfer-Encoding");
				if (null != cteHeader && cteHeader.length > 0) {
					bufferContent.append(IOUtils.toString(MimeUtility.decode(
							bodyPart.getInputStream(), cteHeader[0])));
				} else {
					bufferContent.append(IOUtils.toString(bodyPart
							.getInputStream()));
				}
				bufferContent.append("\n");
			}
			return bufferContent.toString();
		} else if (content instanceof String) {
			return(String) content;
		} else {
			throw new UnsupportedOperationException(
					"Unsupported content type: " + content.getClass());
		}
	}

	public static Store connect(final AbstractMailConnection mailConnection)
			throws MessagingException, IOException {
		final Store store = mailConnection.getStore();
		new SmartWait<AbstractMailConnection>(mailConnection)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.describe("Wait for connection...")
				.until(new Predicate<AbstractMailConnection>() {
					@Override
					public boolean apply(AbstractMailConnection arg0) {
						try {
							store.connect();
							return true;
						} catch (MessagingException e) {
							Logger.error(e.getMessage());
							return false;
						}
					}
				});
		return store;
	}

	public static void closeFolderSilently(Folder folder, boolean save) {
		if (null != folder && folder.isOpen()) {
			try {
				folder.close(save);
			} catch (MessagingException e) {
				Logger.error(e.getMessage());
			}
		}
	}

	public static void closeStoreSilently(Store store) {
		if (null != store && store.isConnected()) {
			try {
				store.close();
			} catch (MessagingException e) {
				Logger.error(e.getMessage());
			}
		}
	}

	public static String extractMessageHeadersAndText(Message msg)
			throws IOException, MessagingException {
		StringWriter sw = new StringWriter();
		@SuppressWarnings("unchecked")
		Enumeration<Header> e = msg.getAllHeaders();
		
		while (e.hasMoreElements()) {
			Header h = e.nextElement();
			sw.write(h.getName() + ": " + h.getValue() + "\n");
		}
		sw.write("\n");

		if (msg.getContent() instanceof Multipart) {
			Multipart multipart = (Multipart) msg.getContent();
			sw.write("Multipart message with " + multipart.getCount()
					+ " parts\n");

			for (int partIndex = 0; partIndex < multipart.getCount(); partIndex++) {
				final BodyPart bodyPart = multipart.getBodyPart(partIndex);
				extractMessageHeadersAndTextFromPart(sw,
						String.valueOf(partIndex), bodyPart);
			}
		} else {
			sw.write(IOUtils.toString(msg.getInputStream()));
		}
		sw.write("\n");
		return sw.toString();
	}

	private static void extractMessageHeadersAndTextFromPart(StringWriter sw,
			String partNumber, BodyPart bodyPart) throws MessagingException,
			IOException {
		final String contentType = bodyPart.getContentType();
		sw.append("\n[[Part #" + partNumber + ": " + contentType);
		if (contentType.startsWith("multipart/alternative;")) {
			BodyPart simplestAlternative = ((Multipart) bodyPart.getContent())
					.getBodyPart(0);
			extractMessageHeadersAndTextFromPart(sw, partNumber + ".0",
					simplestAlternative);
			return;
		}
		if (!isPrintable(contentType)) {
			sw.append(", considered non-printable]]\n");
			return;
		}
		sw.append("]]\n");

		if (contentType.equals("text/plain; charset=unicode-1-1-utf-7")) {
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			bodyPart.writeTo(bao);
			sw.append(bao.toString());
		} else {
			final Object partContent = bodyPart.getContent();
			if (partContent instanceof InputStream) {
				sw.append(IOUtils.toString((InputStream) partContent));
			} else if (partContent instanceof MimeMessage) {
				sw.append(((MimeMessage) partContent).getContent().toString());
			} else {
				sw.append(partContent.toString());
			}
		}
	}

	private static boolean isPrintable(String contentType) {
		return contentType.startsWith("text/")
				|| contentType.startsWith("application/xml")
				|| contentType.startsWith("message/rfc822")
				|| contentType.startsWith("message/delivery-status");
	}

}
