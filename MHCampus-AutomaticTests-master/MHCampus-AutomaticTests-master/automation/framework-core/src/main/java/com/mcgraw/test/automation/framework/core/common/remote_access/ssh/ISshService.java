package com.mcgraw.test.automation.framework.core.common.remote_access.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.mcgraw.test.automation.framework.core.exception.SshServiceException;

/**
 * Operations based on SSH protocol
 *
 * @author Andrei Varabyeu
 *
 */
public interface ISshService {

	/** Localhost as string */
	public static final String LOCALHOST_ADDRESS = "127.0.0.1";

	/**
	 * Starts port forwarding
	 *
	 * @param remoteServer
	 * @param remotePort
	 * @return
	 * @throws IOException
	 */
	int startPortForwarding(String remoteServer, int remotePort)
			throws SshServiceException;

	/**
	 * Stops port forwarding
	 */
	void stopPortForwarding();

	/**
	 * Is any port forwardings opened
	 *
	 * @return
	 */
	boolean hasActiveForwarding();

	/**
	 * Executes provided command with default timeout
	 *
	 * @param command
	 * @return
	 */
	SshResult executeCommand(String command) throws SshServiceException;

	/**
	 * Executes provided command with specified timeout
	 *
	 * @param command
	 * @param timeOut
	 * @param timeUnit
	 * @return
	 */
	SshResult executeCommand(String command, long timeOut, TimeUnit timeUnit)
			throws SshServiceException;

	/**
	 * Executes provided commands in one session with default timeouts
	 *
	 * @param command
	 * @return
	 */
	List<SshResult> executeCommands(String... command)
			throws SshServiceException;

	/**
	 * Executes provided commands in one session with provided timeouts
	 *
	 * @param command
	 * @return
	 */
	List<SshResult> executeCommands(long timeout, TimeUnit timeUnit,
			String... commands) throws SshServiceException;

	void uploadFile(InputStream sourceInputStream, String destFilename)
			throws SshServiceException;

	void removeFile(String filename) throws SshServiceException;

}
