package com.mcgraw.test.automation.tests.centralorgservices;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import com.mcgraw.test.automation.tests.base.BaseTest;
import com.mcgraw.test.automation.ui.service.WelcomeEmail.InstanceCredentials;

public class CreateMapping extends BaseTest {

	private InstanceCredentials instance;
	
	@Test(description = "Check if MHCampus Instance Was Created With Central Org")
	public void CheckMHCampusInstanceWasCreatedWithCentralOrg() throws Exception {
		
		instance = tegrityAdministrationApplication.createNewMhCampusInstanceWithCentralOrg();
	}
	
	@AfterClass
	public void testSuiteTearDown() throws Exception {
		
		if(instance != null)
			tegrityAdministrationApplication.deleteMhCampusInstance(instance.customerNumber);
		
	}
}
