package com.mcgraw.test.automation.framework.core.common.remote_access;

import java.io.IOException;
import java.net.ServerSocket;

import com.mcgraw.test.automation.framework.core.exception.SshServiceException;
import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;

/**
 * Useful network-related utils
 *
 * @author Andrei Varabyeu
 *
 */
public class NetworkUtils {
	/**
	 * Finds free port on the local machine
	 *
	 * @return free port number
	 * @throws SshServiceException
	 */
	public static int findFreePort() {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(0);
			return socket.getLocalPort();
		} catch (IOException e) {
			throw new CommonTestRuntimeException("Unable to find free port", e);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
	}
}
