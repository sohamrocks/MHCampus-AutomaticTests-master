package com.mcgraw.test.automation.api.sakai.service;

import java.rmi.RemoteException;

import com.mcgraw.test.automation.api.sakai.factory.SakaiApiFactory;
import com.mcgraw.test.automation.api.sakai.generated.sakailogin.SakaiLoginServiceStub;
import com.mcgraw.test.automation.api.sakai.generated.sakailogin.SakaiLoginServiceStub.Logout;
import com.mcgraw.test.automation.api.sakai.generated.sakaiscript.SakaiScriptServiceStub;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class BaseSakaiApi {

	/** Storage for administrator session id */
	protected static String adminSessionId;

	private String adminLogin = "qa_admin";
	private String adminPassword = "sak_S4k";
	
	protected SakaiApiFactory sakaiApiFactory;

	protected SakaiLoginServiceStub sakaiLoginServiceStub;

	protected SakaiScriptServiceStub sakaiScriptServiceStub;

	public SakaiApiFactory getSakaiApiFactory() {
		return sakaiApiFactory;
	}

	public void setSakaiApiFactory(SakaiApiFactory sakaiApiFactory) {
		this.sakaiApiFactory = sakaiApiFactory;
	}

	public SakaiLoginServiceStub getSakaiLoginServiceStub() {
		return sakaiLoginServiceStub;
	}

	public void setSakaiLoginServiceStub(SakaiLoginServiceStub sakaiLoginServiceStub) {
		this.sakaiLoginServiceStub = sakaiLoginServiceStub;
	}

	public SakaiScriptServiceStub getSakaiScriptServiceStub() {
		return sakaiScriptServiceStub;
	}

	public void setSakaiScriptServiceStub(SakaiScriptServiceStub sakaiScriptServiceStub) {
		this.sakaiScriptServiceStub = sakaiScriptServiceStub;
	}

	protected String getAdminSessionId() throws RemoteException {
		if (null != adminSessionId) {
			return adminSessionId;
		}
		adminSessionId = loginToSakaiAsAdminAndGetSessionId();
		return adminSessionId;
	}

	protected String loginToSakaiAsAdminAndGetSessionId() throws RemoteException {
		Logger.info("Sakai api: initialize the session");
		String sessionId = sakaiLoginServiceStub.login(sakaiApiFactory.loginToServer(adminLogin, adminPassword)).getLoginReturn();

		Logger.info("Sakai api: session id for logged in user: " + sessionId);
		return sessionId;
	}

	protected void logoutFromSakai() throws RemoteException {
		Logger.info("Sakai api: log out from session: [" + adminSessionId+"]");
		Logout logout = new Logout();
		logout.setSessionid(adminSessionId);
		sakaiLoginServiceStub.logout(logout);
	}

}
