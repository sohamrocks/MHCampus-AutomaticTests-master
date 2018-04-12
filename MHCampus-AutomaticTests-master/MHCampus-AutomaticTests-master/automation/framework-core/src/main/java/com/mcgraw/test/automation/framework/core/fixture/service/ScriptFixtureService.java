package com.mcgraw.test.automation.framework.core.fixture.service;

import java.util.concurrent.TimeUnit;

import com.mcgraw.test.automation.framework.core.common.ResourceUtils;
import com.mcgraw.test.automation.framework.core.common.remote_access.ssh.ISshService;
import com.mcgraw.test.automation.framework.core.common.remote_access.ssh.SshResult;
import com.mcgraw.test.automation.framework.core.fixture.binding.Fixture;
import com.mcgraw.test.automation.framework.core.fixture.binding.FixtureType;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * Service for dealing with Script fixtures
 *
 * @author Andrei Varabyeu
 *
 */
public class ScriptFixtureService extends BaseFixtureService implements
		IFixtureService {

	public static final String LOCAL_SCRIPT_DIR = "scripts";

	public static final int SCRIPT_EXECUTION_TIMEOUT_MIN = 40;

	private ISshService sshService;

	/**
	 * @param sshService
	 *            the sshService to set
	 */
	public void setSshService(ISshService sshService) {
		this.sshService = sshService;
	}

	@Override
	public void importFixture(Fixture fixture) {
		try {
			Logger.info("Importing script fixture '" + fixture.getName()
					+ "'...");
			String script = ResourceUtils
					.getResourceAsString(fixture.getPath());
			SshResult sshResult = sshService.executeCommand(script,
					SCRIPT_EXECUTION_TIMEOUT_MIN, TimeUnit.MINUTES);
			if (!importSuccessfull(sshResult)) {
				Logger.error("Script fixture might be not imported. Exit code: "
						+ sshResult.getExitCode()
						+ ". Ssh output: "
						+ sshResult.getOutput());
				throw new RuntimeException("Can't import fixture '"
						+ fixture.getName() + "'\n"
						+ sshResult.getErrorOutput());
			}
			Logger.info("Script fixture '" + fixture.getName()
					+ "' has been imported");
		} catch (Exception e) {
			throw new RuntimeException(e.getClass().getSimpleName()
					+ " occured on attempt to import fixture: "
					+ fixture.getPath() + ". Exception message: "
					+ e.getMessage());
		}
	}

	@Override
	public boolean isSupported(FixtureType type) {
		return FixtureType.SCRIPT.equals(type);
	}

}
