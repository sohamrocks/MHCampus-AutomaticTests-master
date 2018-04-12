package com.mcgraw.test.automation.api.rest.endpoint;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO Helper. Added to avoid dependency to similar Apache commons-io library
 *
 */
class IOUtils {

	/**
	 * Closes Resource without throwing any errors
	 * 
	 * @param closeable
	 */
	static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}
}
