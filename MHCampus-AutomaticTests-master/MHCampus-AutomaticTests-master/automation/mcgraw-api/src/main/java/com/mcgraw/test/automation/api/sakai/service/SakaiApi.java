package com.mcgraw.test.automation.api.sakai.service;

import java.rmi.RemoteException;

import org.apache.commons.lang3.RandomStringUtils;

import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;
import com.mcgraw.test.automation.api.sakai.generated.sakailogin.SakaiLoginServiceStub.Logout;
import com.mcgraw.test.automation.api.sakai.generated.sakailogin.SakaiLoginServiceStub.LogoutResponse;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddMemberToGroupResponse;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddMemberToSiteWithRole;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddMemberToSiteWithRoleResponse;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewPageToSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewPageToSiteResponse;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSiteResponse;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewToolToPage;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewToolToPageResponse;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUserResponse;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.RemovePageFromSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.RemovePageFromSiteResponse;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.RemoveSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.RemoveSiteResponse;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.RemoveUser;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.RemoveUserResponse;
import com.mcgraw.test.automation.framework.core.exception.test.CommonTestRuntimeException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class SakaiApi extends BaseSakaiApi implements ISakaiApiService {

	@Override
	public AddNewUser createUser(String login, String password, String firstName, String lastName) throws RemoteException {
		AddNewUser user = sakaiApiFactory.addNewUser(login, password, firstName, lastName);
		user.setSessionid(getAdminSessionId());
		Logger.info("Create new user: User [login=" + login + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + user.getEmail() + "]");
		AddNewUserResponse response = sakaiScriptServiceStub.addNewUser(user);
		Logger.info("Create new user response: " + response.getAddNewUserReturn());
		return user;
	}

	@Override
	public void deleteUser(String login) throws RemoteException {
		RemoveUser removeUser = new RemoveUser();
		removeUser.setSessionid(getAdminSessionId());
		removeUser.setEid(login);
		Logger.info("Delete user: User [login=" + login+"]");
		RemoveUserResponse response = sakaiScriptServiceStub.removeUser(removeUser);
		Logger.info("Delete user response: " + response.getRemoveUserReturn());
	}

	@Override
	public AddNewSite addNewSite(String siteId) throws RemoteException {
		AddNewSite course = sakaiApiFactory.addNewCourse(siteId);
		course.setSessionid(getAdminSessionId());
		Logger.info("Create new course: Course [siteId=" + siteId + "]");
		AddNewSiteResponse response = sakaiScriptServiceStub.addNewSite(course);
		Logger.info("Create new course response: " + response.getAddNewSiteReturn());
		//default tool that should be initially added
		addNewToolToSite(course, SakaiTool.SITE_INFO);
		return course;
	}

	@Override
	public AddNewToolToPage addNewToolToSite(AddNewSite site, SakaiTool sakaiTool) throws RemoteException {	
		AddNewPageToSite page = new AddNewPageToSite();
		page.setSessionid(getAdminSessionId());
		page.setSiteid(site.getSiteid());
		page.setPagetitle(sakaiTool.getPageName());
		page.setPagelayout(0);
		Logger.info("Add page: " + sakaiTool.getPageName() + " to site: Site [siteId=" + site.getSiteid() + "]");
		AddNewPageToSiteResponse pageAddedResponse = sakaiScriptServiceStub.addNewPageToSite(page);
		Logger.info("Add page response: " + pageAddedResponse.getAddNewPageToSiteReturn());
		
		AddNewToolToPage tool = new AddNewToolToPage();
		tool.setSessionid(getAdminSessionId());
		tool.setSiteid(page.getSiteid());
		tool.setPagetitle(page.getPagetitle());
		tool.setTooltitle(sakaiTool.getPageName());
		tool.setToolid(sakaiTool.getToolId());
		tool.setLayouthints("0,0");	
		Logger.info("Add tool: " + sakaiTool + " to page: Page [pageTitle=" + page.getPagetitle() + "]");
		AddNewToolToPageResponse toolAddedResponse = sakaiScriptServiceStub.addNewToolToPage(tool);
		Logger.info("Add page response: " + toolAddedResponse.getAddNewToolToPageReturn());		
		return tool;
	}

	@Override
	public void deleteSite(String siteId) throws RemoteException {		
		RemoveSite removeSite = new RemoveSite();
		removeSite.setSessionid(getAdminSessionId());
		removeSite.setSiteid(siteId);
		Logger.info("Delete site: Site [siteId=" + siteId+"]");
		RemoveSiteResponse removeSiteResponse = sakaiScriptServiceStub.removeSite(removeSite);
		Logger.info("Delete site response: " + removeSiteResponse.getRemoveSiteReturn());				
	}

	@Override
	public void deletePageWithToolFromSite(AddNewSite course, SakaiTool tool) throws RemoteException {
		RemovePageFromSite removePageFromSite = new RemovePageFromSite();
		removePageFromSite.setSessionid(getAdminSessionId());
		removePageFromSite.setSiteid(course.getSiteid());
		removePageFromSite.setPagetitle(tool.getPageName());
		Logger.info("Delete page with tool: "+tool);
		RemovePageFromSiteResponse removePageFromSiteResponse = sakaiScriptServiceStub.removePageFromSite(removePageFromSite);
		Logger.info("Delete site page with tool response: " + removePageFromSiteResponse.getRemovePageFromSiteReturn());			
	}

	@Override
	public void addMemberToSiteWithRole(AddNewSite site, AddNewUser user, SakaiUserRole role) throws RemoteException  {
		AddMemberToSiteWithRole addMemberToSiteWithRole = new AddMemberToSiteWithRole();
		addMemberToSiteWithRole.setSessionid(getAdminSessionId());
		addMemberToSiteWithRole.setSiteid(site.getSiteid());
		addMemberToSiteWithRole.setEid(user.getEid());
		addMemberToSiteWithRole.setRoleid(role.getValue());
		Logger.info("Add member: id="+user.getEid()+" to site: siteId="+site.getSiteid());
		AddMemberToSiteWithRoleResponse response = sakaiScriptServiceStub.addMemberToSiteWithRole(addMemberToSiteWithRole);
		Logger.info("Add member to site with role response: " + response.getAddMemberToSiteWithRoleReturn());	
		
	}
}
