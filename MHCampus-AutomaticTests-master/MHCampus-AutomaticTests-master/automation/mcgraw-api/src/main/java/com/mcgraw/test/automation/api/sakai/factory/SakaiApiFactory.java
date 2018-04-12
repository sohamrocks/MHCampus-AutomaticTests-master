package com.mcgraw.test.automation.api.sakai.factory;

import com.mcgraw.test.automation.api.sakai.generated.sakailogin.SakaiLoginServiceStub.Login;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewSite;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub.AddNewUser;

public class SakaiApiFactory {

	public AddNewUser addNewUser(String login, String password, String firstName, String lastName){
		String empty = "";
		return addNewUser(login, password, firstName, lastName, "email@email.com", empty);
	}
	public AddNewUser addNewUser(String login, String password, String firstName, String lastName, String email, String type) {
		AddNewUser addNewUser = new AddNewUser();
		addNewUser.setEid(login);
		addNewUser.setPassword(password);
		addNewUser.setFirstname(firstName);
		addNewUser.setLastname(lastName);
		addNewUser.setEmail(email);
		addNewUser.setType(type);
		return addNewUser;
	}

	public Login loginToServer(String loginName, String password) {
		Login login = new Login();
		login.setId(loginName);
		login.setPw(password);
		return login;
	}
	
	public AddNewSite addNewCourse(String siteId){
		String empty = "";
		return addNewSite(siteId, siteId, "course", empty, empty, empty, empty, false, empty, true, true, empty);
	}

	public AddNewSite addNewSite(String siteId, String siteTitle, String type, String description, String shortDesc, String iconUrl,
			String infoUrl, boolean joinable, String joinerrole, boolean published, boolean publicView, String skin) {
		AddNewSite addNewSite = new AddNewSite();
		addNewSite.setSiteid(siteId);
		addNewSite.setTitle(siteTitle);
		addNewSite.setType(type);
		addNewSite.setDescription(description);
		addNewSite.setShortdesc(shortDesc);
		addNewSite.setIconurl(iconUrl);
		addNewSite.setInfourl(infoUrl);
		addNewSite.setJoinable(joinable);
		addNewSite.setJoinerrole(joinerrole);
		addNewSite.setPublished(published);
		addNewSite.setPublicview(publicView);
		addNewSite.setSkin(skin);
		return addNewSite;
	}

}
