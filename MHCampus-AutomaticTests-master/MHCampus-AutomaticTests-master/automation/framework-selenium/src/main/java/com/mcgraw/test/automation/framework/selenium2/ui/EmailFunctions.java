package com.mcgraw.test.automation.framework.selenium2.ui;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.mcgraw.test.automation.framework.core.common.remote_access.mail.Letter;

/**
 * Email Functions and Predicates
 *
 * @author Andrei Varabyeu
 *
 */
public class EmailFunctions {

	/**
	 * Predicate for letter with provided subject.
	 *
	 * @param subject
	 *            - Subject of letter. NotNull value
	 * @return
	 */
	public static Predicate<Letter> withSubject(@Nonnull final String subject) {
		Preconditions.checkNotNull(subject,
				"Subject of letter is not expected to be null");
		return new Predicate<Letter>() {
			@Override
			public boolean apply(Letter letter) {
				return subject.equals(letter.subject);

			}
			
			@Override
			public String toString() {
				return subject;

			}
		};
	}
}
