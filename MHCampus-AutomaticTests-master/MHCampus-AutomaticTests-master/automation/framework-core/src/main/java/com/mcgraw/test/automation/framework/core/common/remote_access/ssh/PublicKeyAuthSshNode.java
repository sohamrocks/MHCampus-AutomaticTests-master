package com.mcgraw.test.automation.framework.core.common.remote_access.ssh;

public class PublicKeyAuthSshNode extends SshNode {

	private String privateKeyFile;
	private String passPhrase;

	public PublicKeyAuthSshNode(String hostname, String username,
			String privateKeyFile, String passPhrase) {
		super(hostname, username);
		this.passPhrase = passPhrase;
		this.privateKeyFile = privateKeyFile;
	}

	public String getPrivateKeyFile() {
		return privateKeyFile;
	}

	public void setPrivateKeyFile(String privateKeyFile) {
		this.privateKeyFile = privateKeyFile;
	}

	public String getPassPhrase() {
		return passPhrase;
	}

	public void setPassPhrase(String passPhrase) {
		this.passPhrase = passPhrase;
	}

}
