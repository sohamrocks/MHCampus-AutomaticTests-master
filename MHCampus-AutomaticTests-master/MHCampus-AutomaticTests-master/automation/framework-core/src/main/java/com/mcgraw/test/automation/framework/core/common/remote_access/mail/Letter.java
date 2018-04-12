package com.mcgraw.test.automation.framework.core.common.remote_access.mail;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.mail.BodyPart;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.io.IOUtils;

/**
 * This "structure" class represents letter which contains subject and body.
 *
 * @author Andrei Varabyeu
 */
public class Letter {
	private HashMap<String, String> headers = new HashMap<String, String>();

	/* 'From' message header */
	public String from;

	/* 'Subject' message header */
	public String subject;

	/* 'Body' message header */
	public String body;

	/* 'ReplyTo' message header */
	public String replyTo;
	
	/* 'Date' message header */
	public Date receivedDate;

	public Letter(String subject, String body) {
		super();
		this.subject = subject;
		this.body = body;

	}

	public Letter(String subject, String body, String from, String replyTo, String toWhom) {
		super();
		this.from = from;
		this.subject = subject;
		this.body = body;
		this.replyTo = replyTo;
	}

	@SuppressWarnings("unchecked")
	public Letter(Message msg) throws MessagingException, IOException {
		super();
		Enumeration<Header> enum_headers = msg.getAllHeaders();

		while (enum_headers.hasMoreElements()) {
			Header h = enum_headers.nextElement();
			headers.put(h.getName(), h.getValue());
			if (h.getName().equals("From")) {
				from = h.getValue();
			}
			if (h.getName().equals("Subject")) {
				subject = h.getValue();
			}
			if (h.getName().equals("Reply-To")) {
				replyTo = h.getValue();
			}
			if (h.getName().equals("Date")) {
				receivedDate = msg.getReceivedDate();
			}
		}

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
			body = bufferContent.toString();
		} else if (content instanceof String) {
			body = (String) content;
		} else {
			throw new UnsupportedOperationException(
					"Unsupported content type: " + content.getClass());
		}

	}

	public void setHeader(String key, String value) {
		headers.put(key, value);
	}

	public String getHeader(String key) {
		return headers.get(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((headers == null) ? 0 : headers.hashCode());
		result = prime * result + ((replyTo == null) ? 0 : replyTo.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		/* if obj refers to null */

		if (obj == null)
			return false;

		/* both objects has the same type */

		if (!(getClass() == obj.getClass())) {
			return false;
		} else {
			Letter letter = (Letter) obj;
			String sbj_ext = letter.getHeader("Subject");
			String sbj_this = this.getHeader("Subject");
			if (sbj_ext.contains(sbj_this) || sbj_this.contains(sbj_ext)) {
				return true;
			} else {
				return false;
			}
		}
	}

}
