package com.mcgraw.test.automation.api.sakai.service;

import java.rmi.RemoteException;

import com.mcgraw.test.automation.api.sakai.SakaiTool;
import com.mcgraw.test.automation.api.sakai.SakaiUserRole;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewToolToPage;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;

public interface ISakaiApiService {
	
	AddNewUser createUser(String login, String password, String firstName, String lastName) throws RemoteException;
		
	AddNewSite addNewSite(String siteId) throws RemoteException;
	
	AddNewToolToPage addNewToolToSite(AddNewSite site, SakaiTool sakaiTool) throws RemoteException;
	
	void addMemberToSiteWithRole(AddNewSite site, AddNewUser user, SakaiUserRole role) throws RemoteException;
		
	void deleteUser(String login) throws RemoteException;
	
	void deleteSite(String siteId)throws RemoteException;
	
	void deletePageWithToolFromSite(AddNewSite site, SakaiTool tool) throws RemoteException;

}
