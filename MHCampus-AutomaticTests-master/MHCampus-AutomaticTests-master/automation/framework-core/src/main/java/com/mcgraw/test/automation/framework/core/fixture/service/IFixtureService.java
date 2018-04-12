package com.mcgraw.test.automation.framework.core.fixture.service;

import com.mcgraw.test.automation.framework.core.fixture.binding.Fixture;
import com.mcgraw.test.automation.framework.core.fixture.binding.FixtureType;

/**
 * Base interface for all types of fixtures
 *
 * @author Andrei Varabyeu
 *
 */
public interface IFixtureService {

	/**
	 * Import fixture
	 *
	 * @param name
	 *            - fixture name
	 */
	void importFixture(Fixture fixture);

	boolean isSupported(FixtureType type);

}
