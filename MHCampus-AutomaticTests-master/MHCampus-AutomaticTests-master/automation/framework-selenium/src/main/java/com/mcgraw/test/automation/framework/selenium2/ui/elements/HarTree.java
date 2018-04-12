package com.mcgraw.test.automation.framework.selenium2.ui.elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mcgraw.test.automation.framework.selenium2.ui.Browser;

/**
 * Representation of SVG Graph placed on web pages
 *
 * @author Andrei Khveras
 *
 */
public class HarTree extends Element {

	public HarTree(WebElement webElement) {
		super(webElement);
	}
	
	public List<HarTreeNode> getNodeList(Browser browser, String rootNodeName) {
		List<HarTreeNode> result = new ArrayList<HarTreeNode>();
		addChildNodesToList(browser, result, this, rootNodeName);
		return result;
	}

	public void addChildNodesToList(Browser browser,
			List<HarTreeNode> existingList, WebElement parentElement,
			String parentNodeName) {
		for (WebElement currentNode : parentElement.findElements(By
				.xpath("./ul/li"))) {
			HarTreeNode currentHarTreeNode = new HarTreeNode(parentNodeName,
					getNodeName(currentNode), getNodeResponceTime(currentNode),
					getNodeLink(currentNode), getNodeType(currentNode),
					getNodeHasCookies(currentNode),
					getNodeIsCollector(currentNode));
			existingList.add(currentHarTreeNode);
			addChildNodesToList(browser, existingList, currentNode,
					currentHarTreeNode.getName());
		}
	}

	private String getNodeName(WebElement nodeUlElement) {
		return nodeUlElement.findElement(
				By.xpath("./span[contains(@id,'entry')]")).getText();
	}

	private String getNodeResponceTime(WebElement nodeUlElement) {
		List<WebElement> respTimeRect = nodeUlElement.findElements(By
				.xpath("./div[contains(@class,'threat latency')]"));
		if (respTimeRect.size() > 0) {
			return respTimeRect.get(0).getText();
		} else {
			return "";
		}
	}

	private String getNodeLink(WebElement nodeUlElement) {
		return nodeUlElement.findElement(By.xpath("./a")).getAttribute("href");
	}

	private String getNodeType(WebElement nodeUlElement) {
		String nodeImgSrc = nodeUlElement.findElement(By.xpath("./a/img"))
				.getAttribute("src");
		if (nodeImgSrc.contains("js.gif")) {
			return "javascript";
		} else if (nodeImgSrc.contains("css.png")) {
			return "css";
		} else if (nodeImgSrc.contains("redirect.gif")) {
			return "redirect";
		} else if (nodeImgSrc.contains("images.png")) {
			return "image";
		} else if (nodeImgSrc.contains("html.png")) {
			return "html";
		} else if (nodeImgSrc.contains("flash.gif")) {
			return "flash";
		} else {
			return "<cannot determine content type by src='" + nodeImgSrc
					+ "'>";
		}
	}

	private boolean getNodeHasCookies(WebElement nodeUlElement) {
		return (nodeUlElement.findElements(
				By.xpath("./img[contains(@id,'cookie')]")).size() > 0);
	}

	private boolean getNodeIsCollector(WebElement nodeUlElement) {
		return (nodeUlElement.findElements(
				By.xpath("./img[contains(@id,'collector')]")).size() > 0);
	}

	public static class HarTreeNode {

		private String parentNodeName;
		private String name;
		private String responceTime;
		private String link;
		private String type;
		private boolean cookies;
		private boolean collector;

		/**
		 * @param parentNodeName
		 * @param name
		 * @param responceTime
		 * @param link
		 * @param type
		 * @param cookies
		 * @param collector
		 */
		public HarTreeNode(String parentNodeName, String name,
				String responceTime, String link, String type, boolean cookies,
				boolean collector) {
			super();
			this.parentNodeName = parentNodeName;
			this.name = name;
			this.responceTime = responceTime;
			this.link = link;
			this.type = type;
			this.cookies = cookies;
			this.collector = collector;
		}

		/**
		 * @return the parentNodeName
		 */
		public String getParentNodeName() {
			return parentNodeName;
		}

		/**
		 * @param parentNodeName
		 *            the parentNodeName to set
		 */
		public void setParentNodeName(String parentNodeName) {
			this.parentNodeName = parentNodeName;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the responceTime
		 */
		public String getResponceTime() {
			return responceTime;
		}

		/**
		 * @param responceTime
		 *            the responceTime to set
		 */
		public void setResponceTime(String responceTime) {
			this.responceTime = responceTime;
		}

		/**
		 * @return the link
		 */
		public String getLink() {
			return link;
		}

		/**
		 * @param link
		 *            the link to set
		 */
		public void setLink(String link) {
			this.link = link;
		}

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * @param type
		 *            the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}

		/**
		 * @return the cookies
		 */
		public boolean isCookies() {
			return cookies;
		}

		/**
		 * @param cookies
		 *            the cookies to set
		 */
		public void setCookies(boolean cookies) {
			this.cookies = cookies;
		}

		/**
		 * @return the collector
		 */
		public boolean isCollector() {
			return collector;
		}

		/**
		 * @param collector
		 *            the collector to set
		 */
		public void setCollector(boolean collector) {
			this.collector = collector;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "HarTreeNode [parentNodeName=" + parentNodeName + ", name="
					+ name + ", responceTime=" + responceTime + ", link="
					+ link + ", type=" + type + ", cookies=" + cookies
					+ ", collector=" + collector + "]";
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (collector ? 1231 : 1237);
			result = prime * result + (cookies ? 1231 : 1237);
			result = prime * result + ((link == null) ? 0 : link.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime
					* result
					+ ((parentNodeName == null) ? 0 : parentNodeName.hashCode());
			result = prime * result
					+ ((responceTime == null) ? 0 : responceTime.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			HarTreeNode other = (HarTreeNode) obj;
			if (collector != other.collector)
				return false;
			if (cookies != other.cookies)
				return false;
			if (link == null) {
				if (other.link != null)
					return false;
			} else if (!link.equals(other.link))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (parentNodeName == null) {
				if (other.parentNodeName != null)
					return false;
			} else if (!parentNodeName.equals(other.parentNodeName))
				return false;
			if (responceTime == null) {
				if (other.responceTime != null)
					return false;
			} else if (!responceTime.equals(other.responceTime))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}

	}
}
