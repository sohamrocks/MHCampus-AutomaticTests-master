package com.mcgraw.test.automation.framework.core.fixture.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.mcgraw.test.automation.framework.core.common.JaxbMarshaller;
import com.mcgraw.test.automation.framework.core.common.ResourceUtils;
import com.mcgraw.test.automation.framework.core.db.SqlService;
import com.mcgraw.test.automation.framework.core.fixture.binding.Fixture;
import com.mcgraw.test.automation.framework.core.fixture.binding.FixtureType;
import com.mcgraw.test.automation.framework.core.fixture.binding.Fixtures;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * Service for all fixture types management
 *
 * @author Andrei Khveras
 *
 */
public class FixtureManager {

	private static final String CLEAN_DB_SCRIPT_FILENAME = "fixtures/cleandb.sql";

	private static final String FIXTURE_DEFINITIONS_FILENAME = "fixtures/fixtures.definition.xml";

	/* List of fixtures defined in xml file */
	private Fixtures fixtures;

	private SqlService simpleSqlExecutor;

	private List<IFixtureService> fixtureServices;

	/* Names of already imported fixtures */
	private static Set<String> importedFixtures = new HashSet<String>();

	/* Names of already imported fixtures */
	private static Map<String, RuntimeException> failedFixtures = new HashMap<String, RuntimeException>();

	/**
	 * @param simpleSqlExecutor
	 *            the simpleSqlExecutor to set
	 */
	public void setSimpleSqlExecutor(SqlService simpleSqlExecutor) {
		this.simpleSqlExecutor = simpleSqlExecutor;
	}

	/**
	 * @param fixtureServices
	 *            the fixtureServices to set
	 */
	public void setFixtureServices(List<IFixtureService> fixtureServices) {
		this.fixtureServices = fixtureServices;
	}

	public FixtureManager() {
		fixtures = JaxbMarshaller
				.getInstanceForContext(Fixtures.class.getPackage().getName())
				.unmarshall(
						ResourceUtils
								.getResourceAsSource(FIXTURE_DEFINITIONS_FILENAME),
						Fixtures.class);
	}

	public synchronized void importFixture(String name, boolean force) {
		if (importedFixtures.contains(name)) {
			if (force) {
				Logger.info("Forcing fixture with name '" + name + "' import");
			} else {
				Logger.info("Fixture with name '" + name
						+ "' is already imported");
				return;
			}
		}

		if (failedFixtures.containsKey(name)) {
			Logger.error("Fixture import has been failed already. Re-throw exception...");
			throw failedFixtures.get(name);
		}

		Fixture fixture = getFixture(name);
		Logger.info("Fixture '" + name + "' depends on ["
				+ StringUtils.join(fixture.getDependOn(), ";") + "]");

		/* Importing all dependencies */
		for (String dependOnFixtureName : fixture.getDependOn()) {
			if (!name.equals(dependOnFixtureName)) {
				importFixture(dependOnFixtureName, force);
			}
		}

		try {
			getFixtureService(fixture.getType()).importFixture(fixture);
		} catch (RuntimeException e) {
			failedFixtures.put(name, e);
			throw e;
		}
		importedFixtures.add(name);
	}

	/**
	 * Finds fixture by name
	 *
	 * @param name
	 * @return
	 */
	private Fixture getFixture(String name) {
		for (Fixture fixture : fixtures.getFixture()) {
			if (fixture.getName().equals(name))
				return fixture;
		}
		throw new RuntimeException("There is no defined fixture with name '"
				+ name + "'");
	}

	public synchronized void cleanDatabase() {
		Logger.debug("Cleaning out obsolete test data from database...");
		simpleSqlExecutor.executeResourceSqlScript(CLEAN_DB_SCRIPT_FILENAME);
		importedFixtures.clear();
	}

	public IFixtureService getFixtureService(final FixtureType type) {
		Optional<IFixtureService> service = Iterables.tryFind(fixtureServices,
				new Predicate<IFixtureService>() {
					@Override
					public boolean apply(IFixtureService service) {
						return service.isSupported(type);
					}
				});
		if (!service.isPresent()) {
			throw new RuntimeException("Unsupported type of fixture: " + type);
		}
		return service.get();
	}
}
