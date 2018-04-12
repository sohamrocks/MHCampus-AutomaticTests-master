package com.mcgraw.test.automation.framework.core.common.remote_access.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;

import com.google.common.base.Function;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.mcgraw.test.automation.framework.core.common.ResourceUtils;
import com.mcgraw.test.automation.framework.core.common.remote_access.NetworkUtils;
import com.mcgraw.test.automation.framework.core.exception.SshServiceException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.core.results.logger.LoggingOutputStream;
import com.mcgraw.test.automation.framework.core.timing.SmartWait;

/**
 * SSH client implementation based on JSch API<br>
 *
 * @see <a href="http://www.jcraft.com/jsch/">http://www.jcraft.com/jsch/</a>
 * @author Andrei_Varabyeu
 *
 */
public class JSchSshService implements ISshService {

	private PublicKeyAuthSshNode sshNode;

	private JSch jsch;

	private Session cachedSession;

	private long defaultTimeout;

	private boolean keepSession;

	private int forwardedPort;

	public static java.util.Properties AES_256_ENCRYPTION_PROPERTIES = new java.util.Properties() {
		private static final long serialVersionUID = -1599599645281394886L;
		{
			put("cipher.s2c", "aes256-cbc,3des-cbc,blowfish-cbc");
			put("cipher.c2s", "aes256-cbc,3des-cbc,blowfish-cbc");
			put("CheckCiphers", "aes256-cbc");
		}
	};

	public JSchSshService() {
		/* Disabled due to very deep logs */
		// JSch.setLogger(new JSchToLog4hTransformer());
		jsch = new JSch();
		defaultTimeout = 10;
		keepSession = true;
	}

	/**
	 * @param sshNode
	 *            the sshNode to set
	 * @throws JSchException
	 * @throws IOException
	 */
	public void setSshNode(PublicKeyAuthSshNode sshNode) throws JSchException,
			IOException {
		this.sshNode = sshNode;
		byte[] privateKey = ResourceUtils.getResourceAsByteArray(sshNode
				.getPrivateKeyFile());
		jsch.addIdentity(sshNode.getPrivateKeyFile(), privateKey, null, sshNode
				.getPassPhrase().getBytes());
	}

	/**
	 * @param keepSession
	 *            the keepSession to set
	 */
	public void setKeepSession(boolean keepSession) {
		this.keepSession = keepSession;
	}

	@Override
	public int startPortForwarding(String remoteServer, int remotePort)
			throws SshServiceException {
		try {
			cachedSession = getSession();
			Logger.info("Setup port forwarding [" + LOCALHOST_ADDRESS + ":"
					+ LOCALHOST_ADDRESS + " ---> " + remoteServer + ":"
					+ remotePort + "]");
			int freePort = NetworkUtils.findFreePort();
			cachedSession
					.setPortForwardingL(freePort, remoteServer, remotePort);
			Logger.info("Port forwarded successfully");
			forwardedPort = freePort;
			return freePort;
		} catch (JSchException e) {
			throw new SshServiceException("Unable to setup port forwarding", e);
		}

	}


	@Override
	public void stopPortForwarding() {
		if (cachedSession != null && cachedSession.isConnected()) {
			try {
				cachedSession.delPortForwardingL(forwardedPort);
			} catch (JSchException e) {
				// do nothing
			}
		}

		closeSession(cachedSession);
	}


	@Override
	public boolean hasActiveForwarding() {
		try {
			return (null != cachedSession && cachedSession.isConnected() && cachedSession
					.getPortForwardingL().length > 0);
		} catch (JSchException e) {
			return false;
		}
	}


	@Override
	public SshResult executeCommand(String command, long timeOut,
			TimeUnit timeUnit) throws SshServiceException {
		Session session = null;
		try {
			session = getSession();
			SshResult sshResult = executeCommand(session, command,
					TimeUnit.SECONDS.convert(timeOut, timeUnit));

			return sshResult;
		} catch (JSchException e) {
			throw new SshServiceException(
					"Exception during ssh command execution", e);
		} finally {
			closeSession(session);
		}

	}


	@Override
	public SshResult executeCommand(String command) throws SshServiceException {
		return executeCommand(command, defaultTimeout, TimeUnit.SECONDS);

	}


	@Override
	public List<SshResult> executeCommands(String... commands)
			throws SshServiceException {
		return executeCommands(defaultTimeout, TimeUnit.SECONDS, commands);
	}


	@Override
	public List<SshResult> executeCommands(long timeout, TimeUnit timeUnit,
			String... commands) throws SshServiceException {
		Session session = null;
		try {
			session = getSession();
			List<SshResult> results = new ArrayList<SshResult>(commands.length);
			for (String command : commands) {
				results.add(executeCommand(session, command,
						TimeUnit.SECONDS.convert(timeout, timeUnit)));
			}
			return results;
		} catch (JSchException e) {
			throw new SshServiceException(
					"Exception during ssh command execution", e);
		} finally {
			closeSession(session);
		}
	}

	private SshResult executeCommand(Session session, String command,
			long duration) throws JSchException {
		Channel channel = session.openChannel("exec");
		((ChannelExec) channel).setCommand(command);
		channel.setInputStream(null);
		// LoggingOutputStream outputStream = new LoggingOutputStream(
		// Logger.getLogger(), Level.DEBUG);
		LoggingOutputStream errorStream = new LoggingOutputStream(
				Logger.getLogger(), Level.ERROR);
		// channel.setOutputStream(outputStream);
		((ChannelExec) channel).setErrStream(errorStream);
		channel.connect();

		try {
			int exitCode = new SmartWait<Channel>(channel)
					.pollingEvery(3, TimeUnit.SECONDS)
					.withTimeout(duration, TimeUnit.SECONDS)
					.describe("Wait for SSH command execution...")
					.until(new Function<Channel, Integer>() {
						@Override
						public Integer apply(Channel channel) {
							return channel.isClosed() ? channel.getExitStatus()
									: null;
						}
					});
			return new SshResult(exitCode, "", errorStream.toString());
		} finally {
			// IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(errorStream);
			closeQuietly(channel);
		}

	}

	
	@Override
	public void uploadFile(InputStream sourceInputStream, String destFilename)
			throws SshServiceException {
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			session = getSession();
			sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			sftpChannel.put(sourceInputStream, destFilename);
			IOUtils.closeQuietly(sourceInputStream);
		} catch (Exception e) {
			throw new SshServiceException(
					"Exception during uploading file with name '"
							+ destFilename + "'", e);
		} finally {
			closeQuietly(sftpChannel);
			closeSession(session);
		}
	}


	@Override
	public void removeFile(String filename) throws SshServiceException {
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			session = getSession();
			sftpChannel = (ChannelSftp) session.openChannel("sftp");
			sftpChannel.connect();
			sftpChannel.rm(filename);

		} catch (Exception e) {
			throw new SshServiceException(
					"Exception during removing file with name '" + filename
							+ "'", e);
		} finally {
			closeQuietly(sftpChannel);
			closeSession(session);
		}

	}

	/**
	 * Opens JSch session
	 *
	 * @return
	 * @throws JSchException
	 */
	private Session getSession() throws JSchException {
		if (keepSession) {
			if (cachedSession == null || !cachedSession.isConnected()) {
				cachedSession = openSession();
			}
			return cachedSession;
		} else {
			return openSession();
		}
	}

	private Session openSession() throws JSchException {
		Session session = jsch.getSession(sshNode.getUsername(),
				sshNode.getHostname(), sshNode.getPort());
		session.setConfig(AES_256_ENCRYPTION_PROPERTIES);
		UserInfo userInfo = new JSchUserInfo(sshNode.getPassPhrase());
		session.setUserInfo(userInfo);
		session.connect();
		return session;
	}

	/**
	 * Closes channel if it's opened
	 *
	 * @param channel
	 */
	private void closeQuietly(Channel channel) {
		if (null != channel && channel.isConnected()) {
			channel.disconnect();
		}
	}

	public void destroy() {
		closeQuietly(cachedSession);
	}

	private void closeSession(Session session) {
		if (!keepSession) {
			closeQuietly(session);
		}
	}

	/**
	 * Closes session if it's opened
	 *
	 * @param session
	 */
	private void closeQuietly(Session session) {
		if (null != session && session.isConnected()) {
			session.disconnect();
		}
	}

	/**
	 * User Info implementation
	 *
	 * @author Andrei Varabyeu
	 *
	 */
	public class JSchUserInfo implements UserInfo {
		private String passPhrase;

		public JSchUserInfo(String passPhrase) {
			this.passPhrase = passPhrase;
		}

		public String getPassphrase() {
			return passPhrase;
		}

		public String getPassword() {
			return null;
		}

		public boolean promptPassword(String message) {
			return false;
		}

		public boolean promptPassphrase(String message) {
			return true;
		}

		public boolean promptYesNo(String message) {
			return true;
		}

		public void showMessage(String message) {
			Logger.info(message);
		}
	}

	/**
	 * Transforms JSch logs to the Log4j logs
	 *
	 * @author Andrei Varabyeu
	 *
	 */
	@SuppressWarnings("unused")
	private static class JSchToLog4hTransformer implements
			com.jcraft.jsch.Logger {

		private static final Map<Integer, Level> LEVELS = new HashMap<Integer, Level>() {
			private static final long serialVersionUID = 112744159974435518L;

			{
				put(com.jcraft.jsch.Logger.DEBUG, Level.DEBUG);
				put(com.jcraft.jsch.Logger.INFO, Level.INFO);
				put(com.jcraft.jsch.Logger.WARN, Level.WARN);
				put(com.jcraft.jsch.Logger.ERROR, Level.ERROR);
				put(com.jcraft.jsch.Logger.FATAL, Level.FATAL);
			}
		};

		public boolean isEnabled(int level) {
			return true;
		}

		public void log(int level, String message) {
			Logger.log(LEVELS.get(level), "[JSch]: " + message);
		}
	}

}
