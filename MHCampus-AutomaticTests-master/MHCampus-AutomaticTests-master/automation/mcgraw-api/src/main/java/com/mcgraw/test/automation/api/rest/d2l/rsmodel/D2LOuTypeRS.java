package com.mcgraw.test.automation.api.rest.d2l.rsmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Json representation model for D2L organization unit type info Uses Jackson
 * (2.x) for data-binding
 * 
 * @see <a href="http://fasterxml.com/">http://fasterxml.com/</a>
 * 
 * @author Andrei_Turavets
 * 
 */
@JsonInclude(Include.NON_NULL)
public class D2LOuTypeRS {

	@JsonProperty(value = "Id")
	private int id;

	@JsonProperty(value = "Code")
	private String code;

	@JsonProperty(value = "Name")
	private String name;

	@JsonProperty(value = "Description")
	private String description;

	@JsonProperty(value = "SortOrder")
	private int sortOrder;

	@JsonProperty(value = "Permissions")
	private Permissions permissions;
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getSortOrder() {
		return sortOrder;
	}


	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}


	public Permissions getPermissions() {
		return permissions;
	}


	public void setPermissions(Permissions permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "D2L Org Unit Type [id=" + id + ", name=" + name + ", code=" + code + "]";
	}

	public static class Permissions {

		@JsonProperty(value = "CanEdit")
		private boolean canEdit;

		@JsonProperty(value = "CanDelete")
		private boolean canDelete;

		public boolean isCanEdit() {
			return canEdit;
		}

		public void setCanEdit(boolean canEdit) {
			this.canEdit = canEdit;
		}

		public boolean isCanDelete() {
			return canDelete;
		}

		public void setCanDelete(boolean canDelete) {
			this.canDelete = canDelete;
		}
	}

}
