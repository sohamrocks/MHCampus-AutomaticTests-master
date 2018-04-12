package com.mcgraw.test.automation.framework.core.common.remote_access.ssh;

public abstract class SshNode {
	public static final int OPERATION_TIMEOUT = 90000;

	public static final int DEFAULT_SSH_PORT = 22;

	/** SSH credentials & settings */
	private String hostname;

	private String username;

	private int port;

	private int socketTimeout;

	public SshNode(String hostname, String username) {
		this.hostname = hostname;
		this.username = username;
		this.port = DEFAULT_SSH_PORT;
		this.socketTimeout = OPERATION_TIMEOUT;
	}

	public SshNode(String hostname, String username, int socketTimeout) {
		this.hostname = hostname;
		this.username = username;
		this.port = DEFAULT_SSH_PORT;
		this.socketTimeout = socketTimeout;
	}

	public String getHostname() {
		return hostname;
	}

	public String getUsername() {
		return username;
	}

	public int getPort() {
		return port;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
}
