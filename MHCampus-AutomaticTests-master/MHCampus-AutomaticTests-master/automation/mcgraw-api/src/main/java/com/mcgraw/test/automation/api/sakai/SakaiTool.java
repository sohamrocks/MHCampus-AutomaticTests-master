package com.mcgraw.test.automation.api.sakai;

/**
 * Sakai tool names and ids
 * 
 * @author Andrei_Turavets
 *
 */
public enum SakaiTool {
	SITE_INFORMATION_DISPLAY("Site Information Display","sakai.iframe.site"),
	ANNOUNCEMENTS("Announcements","sakai.announcements"),
	ASSIGNMENTS("Assignments", "sakai.assignment"),
	CHATROOM("Chat Room","sakai.chat"),
	DROPBOX("Drop Box","sakai.dropbox"),
	EMAIL("Email","sakai.mailtool"),
	EMAIL_ARCHIVE("Email Archive","sakai.mailbox"),
//	EXTERNAL_TOOL("External Tool",""),
	FORUMS("Forums","sakai.forums"),
	GRADEBOOK("Gradebook","sakai.gradebook.tool"),
//	LESSONS("Lessons",""),
	LINK_TOOL("Link Tool","sakai.rutgers.linktool"),//need to check
	MESSAGES("Messages","sakai.messages"),
	NEWS("News","sakai.news"),
	PODCASTS("Podcasts","sakai.podcasts"),
	POLLS("Polls","sakai.poll"),
	POSTEM("PostEm","sakai.postem"),
	RESOURCES("Resources","sakai.resources"),
	ROSTER("Roster","sakai.site.roster"),
	SCHEDULE("Schedule","sakai.schedule"),
	SECTION_INFO("Section Info",""),
	SITE_INFO("Site Info","sakai.siteinfo"),
	STATISTICS("Statistics", "sakai.sections"),
	SYLLABUS("Syllabus","sakai.syllabus"),
	TESTS_AND_QUIZES("Tests & Quizzes","sakai.samigo.tool"),//need to check
	WEB_CONTENT("Web Content","sakai.iframe"),
	WIKI("Wiki","sakai.rwiki");
		
	private SakaiTool(String toolName, String toolId) {
		this.pageName = toolName;
		this.toolId = toolId;
	}

	private String pageName;

	private String toolId;

	public String getPageName() {
		return pageName;
	}

	public String getToolId() {
		return toolId;
	}

	@Override
	public String toString() {
		return "Sakai tool: [name=" + pageName + ", tool id=" + toolId + "]";
	}

}
