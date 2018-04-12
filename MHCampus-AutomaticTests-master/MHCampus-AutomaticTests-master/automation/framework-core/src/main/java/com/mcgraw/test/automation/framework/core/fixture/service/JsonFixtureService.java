package com.mcgraw.test.automation.framework.core.fixture.service;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

import com.mcgraw.test.automation.framework.core.common.ResourceUtils;
import com.mcgraw.test.automation.framework.core.common.remote_access.ssh.ISshService;
import com.mcgraw.test.automation.framework.core.common.remote_access.ssh.SshResult;
import com.mcgraw.test.automation.framework.core.fixture.binding.Fixture;
import com.mcgraw.test.automation.framework.core.fixture.binding.FixtureType;
import com.mcgraw.test.automation.framework.core.fixture.template.ITemplateProcessor;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * Service for dealing with JSON fixtures
 *
 * @author Andrei Varabyeu
 *
 */
public class JsonFixtureService extends BaseFixtureService implements
		IFixtureService {

	public static final String WEB_APP_DIR = "/usr/local/krux/components/kdjango_webapp/app/kdjango_webapp-deploy/webapp";

	public static final long JSON_IMPORT_TIMEOUT_SEC = 100l;

	private ITemplateProcessor templateProcessor;

	private ISshService sshService;

	/**
	 * @param sshService
	 *            the sshService to set
	 */
	public void setSshService(ISshService sshService) {
		this.sshService = sshService;
	}

	/**
	 * @param templateProcessor
	 *            the templateProcessor to set
	 */
	public void setTemplateProcessor(ITemplateProcessor templateProcessor) {
		this.templateProcessor = templateProcessor;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.mcgraw.test.automation.framework.core.fixture.IFixtureService#importFixture(java
	 * .lang .String)
	 */
	@Override
	public void importFixture(Fixture fixture) {
		try {
			String destFileName = fixture.getName() + ".json";
			Logger.info("Importing fixture '" + destFileName + "'...");
			InputStream fixtureStream;
			if (fixture.isTemplate()) {
				fixtureStream = IOUtils.toInputStream(templateProcessor
						.process(fixture.getPath()));
			} else {
				fixtureStream = ResourceUtils.getResourceAsInputStream(fixture
						.getPath());
			}
			if (null == fixtureStream) {
				throw new RuntimeException("Fixture '" + destFileName
						+ "' wasn't found by path '" + fixture.getPath() + "'");
			}
			sshService.uploadFile(fixtureStream, destFileName);
			String command = "export KRUX_ENVIRONMENT=functional;cd "
					+ WEB_APP_DIR
					+ ";kdjango_webapp-python manage.py loaddata ~/"
					+ destFileName;
			SshResult sshResult = sshService.executeCommand(command,
					JSON_IMPORT_TIMEOUT_SEC, TimeUnit.SECONDS);
			if (!importSuccessfull(sshResult)) {
				Logger.error("Json fixture might be not imported. Exit code: "
						+ sshResult.getExitCode() + ". Ssh output: "
						+ sshResult.getOutput());
				throw new RuntimeException("Can't import fixture '"
						+ destFileName + "'\n" + sshResult.getErrorOutput());
			}
		} catch (Exception e) {
			Logger.error("Exception ocurred during json fixture import", e);
			throw new RuntimeException("Can't import fixture '"
					+ fixture.getName() + ".json'");
		}

		Logger.info("Fixture '" + fixture.getName()
				+ ".json' has been imported");

	}

	@Override
	public boolean isSupported(FixtureType type) {
		return FixtureType.JSON.equals(type);
	}

}