package com.mcgraw.test.automation.framework.core.fixture.service;

import java.util.List;

import com.mcgraw.test.automation.framework.core.common.remote_access.ssh.SshResult;

/**
 * Base Logic for fixture services
 *
 * @author Andrei Varabyeu
 *
 */
public abstract class BaseFixtureService {

	private List<String> errorsToBeSkipped;

	public void setErrorsToBeSkipped(List<String> errorsToBeSkipped) {
		this.errorsToBeSkipped = errorsToBeSkipped;
	}

	/**
	 * If error matches to at least one error pattern we need to skip that error
	 *
	 * @param errorOutput
	 * @return
	 */
	protected boolean ignoreError(String errorOutput) {
		for (String pattern : errorsToBeSkipped) {
			if (errorOutput.matches(pattern)) {
				return true;
			}
		}
		return false;
	}

	protected boolean importSuccessfull(SshResult result) {
		if (!result.successExitCode()) {
			return false;
		}
		return (result.successfull() || ignoreError(result.getErrorOutput()));

	}
}
