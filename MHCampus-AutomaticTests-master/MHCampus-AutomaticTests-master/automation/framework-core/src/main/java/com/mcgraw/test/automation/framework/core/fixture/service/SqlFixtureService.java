package com.mcgraw.test.automation.framework.core.fixture.service;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.UncategorizedSQLException;

import com.mcgraw.test.automation.framework.core.common.ResourceUtils;
import com.mcgraw.test.automation.framework.core.db.SqlService;
import com.mcgraw.test.automation.framework.core.fixture.binding.Fixture;
import com.mcgraw.test.automation.framework.core.fixture.binding.FixtureType;
import com.mcgraw.test.automation.framework.core.fixture.template.ITemplateProcessor;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * Service for dealing with SQL script fixtures
 *
 * @author Andrei Khveras
 *
 */
public class SqlFixtureService implements IFixtureService {

	public static final String LOCAL_SCRIPT_DIR = "scripts";

	private ITemplateProcessor templateProcessor;

	private SqlService sqlExecutor;

	/**
	 * @param sqlExecutor the sqlExecutor to set
	 */
	public void setSqlService(SqlService sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	/**
	 * @param templateProcessor
	 *            the templateProcessor to set
	 */
	public void setTemplateProcessor(ITemplateProcessor templateProcessor) {
		this.templateProcessor = templateProcessor;
	}
	
	@Override
	public void importFixture(Fixture fixture) {
		try {
			Logger.info("Importing sql fixture '"+fixture.getName()+"'...");
			InputStream fixtureStream;
			if (fixture.isTemplate()) {
				fixtureStream = IOUtils.toInputStream(templateProcessor
						.process(fixture.getPath()));
			} else {
				fixtureStream = ResourceUtils.getResourceAsInputStream(fixture
						.getPath());
			}
			
			sqlExecutor.executeResourceSqlScript(fixtureStream);
			Logger.info("Sql fixture '" + fixture.getName()
					+ "' has been imported");
		} 
		
		catch (UncategorizedSQLException e) {
			throw new RuntimeException(e.getClass().getSimpleName()
					+ " occured on attempt to import fixture: "
					+ fixture.getPath() + ". Exception message: "
					+ e.getCause().getMessage());
		}
		
		
		catch (Exception e) {
			throw new RuntimeException(e.getClass().getSimpleName()
					+ " occured on attempt to import fixture: "
					+ fixture.getPath() + ". Exception message: "
					+ e.getMessage());
		}
	}

	@Override
	public boolean isSupported(FixtureType type) {
		return FixtureType.SQL.equals(type);
	}

}
