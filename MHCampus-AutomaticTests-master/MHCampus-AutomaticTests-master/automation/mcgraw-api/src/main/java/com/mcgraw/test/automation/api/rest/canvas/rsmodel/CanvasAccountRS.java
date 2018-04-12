package com.mcgraw.test.automation.api.rest.canvas.rsmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Json representation model for Canvas account Uses Jackson (2.x) for
 * data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CanvasAccountRS {

	@JsonProperty(value = "id")
	private int id;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "default_time_zone")
	private String defaultTimeZone;

	@JsonProperty(value = "parent_account_id")
	private Integer parentAccountId;

	@JsonProperty(value = "root_account_id")
	private int rootAccountId;
	//added by Yuval 14.05.2017
	@JsonProperty(value = "uuid")
	private String uuid;

	@JsonProperty(value = "default_storage_quota_mb")
	private int defaultStorageQuotaMb;

	@JsonProperty(value = "default_user_storage_quota_mb")
	private int defaultUserStorageQuotaMb;

	@JsonProperty(value = "default_group_storage_quota_mb")
	private int defaultGroupStorageQuotaMb;
	
	@JsonProperty(value = "workflow_state")
	private String workflowState;

	public String getWorkflowState() {
		return workflowState;
	}

	public void setWorkflowState(String workflowState) {
		this.workflowState = workflowState;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultTimeZone() {
		return defaultTimeZone;
	}

	public void setDefaultTimeZone(String defaultTimeZone) {
		this.defaultTimeZone = defaultTimeZone;
	}

	public Integer getParentAccountId() {
		return parentAccountId;
	}

	public void setParentAccountId(Integer parentAccountId) {
		this.parentAccountId = parentAccountId;
	}

	public int getRootAccountId() {
		return rootAccountId;
	}

	public void setRootAccountId(int rootAccountId) {
		this.rootAccountId = rootAccountId;
	}

	public int getDefaultStorageQuotaMb() {
		return defaultStorageQuotaMb;
	}

	public void setDefaultStorageQuotaMb(int defaultStorageQuotaMb) {
		this.defaultStorageQuotaMb = defaultStorageQuotaMb;
	}

	public int getDefaultUserStorageQuotaMb() {
		return defaultUserStorageQuotaMb;
	}

	public void setDefaultUserStorageQuotaMb(int defaultUserStorageQuotaMb) {
		this.defaultUserStorageQuotaMb = defaultUserStorageQuotaMb;
	}

	public int getDefaultGroupStorageQuotaMb() {
		return defaultGroupStorageQuotaMb;
	}

	public void setDefaultGroupStorageQuotaMb(int defaultGroupStorageQuotaMb) {
		this.defaultGroupStorageQuotaMb = defaultGroupStorageQuotaMb;
	}

	@Override
	public String toString() {
		return "Canvas account [id=" + id + ", name=" + name + ", parentAccountId=" + parentAccountId + ", rootAccountId=" + rootAccountId
				+ ", defaultStorageQuotaMb=" + defaultStorageQuotaMb + ", defaultUserStorageQuotaMb=" + defaultUserStorageQuotaMb
				+ ", defaultGroupStorageQuotaMb=" + defaultGroupStorageQuotaMb + "]";
	}
}
