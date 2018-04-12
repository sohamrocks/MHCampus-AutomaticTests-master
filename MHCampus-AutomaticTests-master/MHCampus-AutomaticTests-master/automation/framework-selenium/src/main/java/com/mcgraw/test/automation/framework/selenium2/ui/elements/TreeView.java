package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;
import com.mcgraw.test.automation.framework.selenium2.ui.Browser;
import com.mcgraw.test.automation.framework.selenium2.ui.exception.TreeViewException;
import com.mcgraw.test.automation.framework.selenium2.wait.LoggedWait;

/**
 * TreeView representation
 *
 * @author Andrei Varabyeu
 *
 */
public class TreeView extends Element {

	private static enum TREE_NODE_STATUS {
		// @formatter:off
		EMPTY("jstree-leaf"),
		COLLAPSED("jstree-closed"),
		EXPANDED("jstree-open");
		// @formatter:on

		private String classname;

		private TREE_NODE_STATUS(String classname) {
			this.classname = classname;
		}

		public String getClassname() {
			return classname;
		}

		public boolean isStatusOf(WebElement webElement) {
			return null != webElement.getAttribute("class")
					&& webElement.getAttribute("class").contains(
							this.getClassname());
		}

	}

	public TreeView(WebElement webElement) {
		super(webElement);
	}

	public void search(String expression) {
		this.findElement(By.xpath(".//input[@id='search_tree']")).sendKeys(
				expression);
		this.findElement(By.xpath(".//input[@id='search_tree_element']"))
				.click();
	}

	public void clearTreeView() {
		this.findElement(By.xpath(".//input[@id='clear_tree_element']"))
				.click();
	}

	public void expandNode(String nodeName) {
		changeNodeStatus(nodeName, TREE_NODE_STATUS.EXPANDED);
	}

	public void collapseNode(String nodeName) {
		changeNodeStatus(nodeName, TREE_NODE_STATUS.COLLAPSED);
	}

	public List<String> getChildNames(String node) {
		expandNode(node);
		List<WebElement> childs = findNode(node).findElements(
				By.xpath("./ul/li"));
		List<String> nodeNames = new ArrayList<String>(childs.size());
		for (WebElement child : childs) {
			nodeNames.add(child.findElement(By.xpath("./a")).getText().trim());
		}
		return nodeNames;
	}

	public void selectNode(final String nodePath) {
		String[] splittedPath = nodePath.split("/");

		for (int index = 0; index < splittedPath.length - 1; index++) {
			expandNode(splittedPath[index]);
		}

		// Get last one
		String nodeToBeSelected = splittedPath[splittedPath.length - 1];
		WebElement nodeElement = findNode(nodeToBeSelected);
		if (!TREE_NODE_STATUS.EMPTY.isStatusOf(nodeElement)) {
			throw new TreeViewException("Node with path '" + nodePath
					+ "' cannot be selected. It contains child element(s)");
		}
		clickAtNode(nodeElement);
		waitForActionComplete();
	}

	private void changeNodeStatus(final String nodeName,
			final TREE_NODE_STATUS status) {
		WebElement node = findNode(nodeName);
		if (!status.isStatusOf(node)) {
			clickAtNode(node);
			waitForActionComplete();
		}
		if (!status.isStatusOf(node)) {
			throw new TreeViewException("Unable to set node status: " + status);
		}

	}

	private WebElement findNode(String nodeName) {
		return findNode(this, nodeName);
	}

	private WebElement findNode(WebElement parent, String nodeName) {
		Logger.debug("Trying to find node with name '" + nodeName + "'");
		List<WebElement> nodes = parent.findElements(By.xpath(String.format(
				".//li[a[normalize-space(text())='%s']]", nodeName)));
		if (nodes.isEmpty()) {
			throw new TreeViewException("Unable to find node with name '"
					+ nodeName + "'");
		}
		return nodes.get(0);
	}

	/**
	 * waiting for default timeout progress element to disappear from page
	 *
	 */
	private void waitForActionComplete() {
		try {
			Logger.action("Waiting for action to complete");
			Element progressElement = this.findElement(By
					.xpath("//div[@id='progress']"));
			Wait<Element> wait = new LoggedWait<Element>(progressElement)
					.withTimeout(Browser.DEFAULT_WAIT_FOR_ELEMENT,
							TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class);
			wait.until(new Function<Element, Boolean>() {

				@Override
				public Boolean apply(Element element) {
					return !element.isElementPresent();
				}

			});
		} catch (NoSuchElementException e) {
			Logger.warn("Progress indicator didn't appear!");
		}
	}

	private void clickAtNode(WebElement webElement) {
		webElement.findElement(By.xpath("./a")).click();
	}

}
