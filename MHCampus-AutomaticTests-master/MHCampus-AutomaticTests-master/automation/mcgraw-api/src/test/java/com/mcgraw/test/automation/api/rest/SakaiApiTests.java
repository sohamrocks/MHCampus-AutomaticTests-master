package com.mcgraw.test.automation.api.rest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;
import com.mcgraw.test.automation.api.sakai.service.ISakaiApiService;

@ContextConfiguration(locations = { "classpath:spring/test-api-context.xml" })
public class SakaiApiTests extends AbstractTestNGSpringContextTests {

	@Autowired
	private ISakaiApiService sakaiApiService;

	@Test(description = "Test Sakai api")
	public void addNewUser() throws Exception {

		AddNewUser user = sakaiApiService.createUser("epam" + getRandomString(), "123qweA@", "firstName" + getRandomString(), "lastName" + getRandomString());
				
		AddNewSite site = sakaiApiService.addNewSite("epamcourse"+getRandomString());	
		sakaiApiService.addMemberToSiteWithRole(site, user, SakaiUserRole.INSTRUCTOR);			
		sakaiApiService.addNewToolToSite(site, SakaiTool.EMAIL);				
		
		sakaiApiService.deletePageWithToolFromSite(site, SakaiTool.EMAIL);		
		sakaiApiService.deleteUser(user.getEid());
		sakaiApiService.deleteSite(site.getSiteid());

	}

	private String getRandomString() {
		return RandomStringUtils.randomNumeric(5);
	}

}
