package com.mcgraw.test.automation.ui.angel.course;

import java.util.List;

import org.openqa.selenium.support.How;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.PageIdentificator;
import com.mcgraw.test.automation.framework.selenium2.ui.Screen;
import com.mcgraw.test.automation.framework.selenium2.ui.elements.Element;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocator;
import com.mcgraw.test.automation.framework.selenium2.ui.locator.annotation.DefinedLocators;

@PageIdentificator(locators = @DefinedLocators(@DefinedLocator(using = "//*[@id='breadcrumbMenu']/li[@id='bcSectionHome' and contains(.,'Course')]")))
public class AngelCourseContext extends Screen {

	List<AngelCourseTabBlock> tabMenuItems;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "contentWin"))
	protected Element tabFrame;

	@DefinedLocators(@DefinedLocator(how = How.ID, using = "portalRefreshAll"))
	private Element refreshButton;

	public AngelCourseContext(Browser browser) {
		super(browser);
	}

	public <T extends AngelCourseContext> T getTabContext(TabMenuItem tabMenuItem) {
		browser.switchTo().defaultContent();
		@SuppressWarnings("unchecked")
		Class<T> tabClass = (Class<T>) tabMenuItem.getClazz();
		AngelCourseTabBlock tab = getTabMenuItem(tabMenuItem);
		if (tab.isActive()) {
			return browser.waitForPage(tabClass);
		}
		tab.click();
		T page = browser.waitForPage(tabClass);
		tabFrame.waitForPresence();
		browser.switchTo().frame(tabFrame);
		return page;
	}

	public enum TabMenuItem {
		Course(AngelCourseDetailsScreen.class), Report(AngelCourseReportScreen.class), Manage(AngelCourseManageScreen.class);

		private <T extends Screen> TabMenuItem(Class<T> clazz) {
			this.clazz = clazz;
		}

		private Class<?> clazz;

		public Class<?> getClazz() {
			return clazz;
		}
	};

	private AngelCourseTabBlock getTabMenuItem(TabMenuItem tabMenuItem) {
		for (AngelCourseTabBlock tabItem : tabMenuItems) {
			if (tabItem.getName().contains(tabMenuItem.toString())) {
				return tabItem;
			}
		}
		Logger.info("No tab items with name [" + tabMenuItem + "]");
		return null;
	}

}
