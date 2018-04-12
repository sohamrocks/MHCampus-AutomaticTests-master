package com.mcgraw.test.automation.framework.core.common.remote_access.ssh;

public class BasicPassAuthSshNode extends SshNode {

	private String password;

	public BasicPassAuthSshNode(String hostname, String username,
			String password) {
		super(hostname, username);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
