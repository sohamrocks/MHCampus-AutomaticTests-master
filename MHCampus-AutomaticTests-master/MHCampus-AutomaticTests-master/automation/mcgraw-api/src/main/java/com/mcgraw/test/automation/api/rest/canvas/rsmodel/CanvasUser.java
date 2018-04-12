package com.mcgraw.test.automation.api.rest.canvas.rsmodel;

import java.util.Arrays;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CanvasUser {

	@JsonProperty(value = "id")
	private int id;

	@JsonProperty(value = "login_id")
	private String loginId;
	
	@JsonProperty(value = "name")
	private String name;
	
	@JsonProperty(value = "short_name")
	private String shortName;

	@JsonProperty(value = "avatar_image_url")
	private String avatarImageUrl;

	@JsonProperty(value = "school_name")
	private String schoolName;

	@JsonProperty(value = "visibility")
	private String visibility;

	@JsonProperty(value = "storage_quota")
	private String storageQuota;

	@JsonProperty(value = "avatar_image_updated_at")
	private String avatarImageUpdatedAt;

	@JsonProperty(value = "locale")
	private String locale;

	@JsonProperty(value = "unread_conversations_count")
	private int unreadConversationsCount;

	@JsonProperty(value = "registration_remote_ip")
	private String registrationRemoteIp;

	@JsonProperty(value = "merge_to")
	private String mergeTo;

	@JsonProperty(value = "workflow_state")
	private String workflowState;

	@JsonProperty(value = "browser_locale")
	private String browserLocale;

	@JsonProperty(value = "turnitin_id")
	private String turnitinTd;

	@JsonProperty(value = "subscribe_to_emails")
	private String subscribeToEmails;

	@JsonProperty(value = "show_user_services")
	private boolean showUserServices;

	@JsonProperty(value = "birthdate")
	private String birthdate;

	@JsonProperty(value = "gender")
	private String gender;

	@JsonProperty(value = "created_at")
	private String createdAt;

	@JsonProperty(value = "avatar_state")
	private String avatarState;

	@JsonProperty(value = "reminder_time_for_due_dates")
	private long reminderTimeForDueDates;

	@JsonProperty(value = "page_views_count")
	private int pageViewsCount;

	@JsonProperty(value = "sortable_name")
	private String sortableName;

	@JsonProperty(value = "preferences")
	private Map<String, String> preferences;

	@JsonProperty(value = "deleted_at")
	private String deletedAt;

	@JsonProperty(value = "reminder_time_for_grading")
	private long reminderTimeForGrading;

	@JsonProperty(value = "crocodoc_id")
	private String crocodocId;

	@JsonProperty(value = "country_code")
	private String countryCode;

	@JsonProperty(value = "last_logged_out")
	private String lastLoggedOut;

	@JsonProperty(value = "stuckSisFields")
	private String[] stuck_sis_fields;

	@JsonProperty(value = "school_position")
	private String schoolPosition;

	@JsonProperty(value = "unread_inbox_items_count")
	private int unreadInboxItemsCount;

	@JsonProperty(value = "avatar_image_source")
	private String avatarImageSource;

	@JsonProperty(value = "time_zone")
	private Object timeZone;

	@JsonProperty(value = "initial_enrollment_type")
	private String initialEnrollmentType;

	@JsonProperty(value = "lti_context_id")
	private String ltiContextId;

	@JsonProperty(value = "updated_at")
	private String updatedAt;

	@JsonProperty(value = "visible_inbox_types")
	private String visibleInboxTypes;

	@JsonProperty(value = "last_user_note")
	private String lastUserNote;

	@JsonProperty(value = "public")
	private String aPublic;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAvatarImageUrl() {
		return avatarImageUrl;
	}

	public void setAvatarImageUrl(String avatarImageUrl) {
		this.avatarImageUrl = avatarImageUrl;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getStorageQuota() {
		return storageQuota;
	}

	public void setStorageQuota(String storageQuota) {
		this.storageQuota = storageQuota;
	}

	public String getAvatarImageUpdatedAt() {
		return avatarImageUpdatedAt;
	}

	public void setAvatarImageUpdatedAt(String avatarImageUpdatedAt) {
		this.avatarImageUpdatedAt = avatarImageUpdatedAt;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public int getUnreadConversationsCount() {
		return unreadConversationsCount;
	}

	public void setUnreadConversationsCount(int unreadConversationsCount) {
		this.unreadConversationsCount = unreadConversationsCount;
	}

	public String getRegistrationRemoteIp() {
		return registrationRemoteIp;
	}

	public void setRegistrationRemoteIp(String registrationRemoteIp) {
		this.registrationRemoteIp = registrationRemoteIp;
	}

	public String getMergeTo() {
		return mergeTo;
	}

	public void setMergeTo(String mergeTo) {
		this.mergeTo = mergeTo;
	}

	public String getWorkflowState() {
		return workflowState;
	}

	public void setWorkflowState(String workflowState) {
		this.workflowState = workflowState;
	}

	public String getBrowserLocale() {
		return browserLocale;
	}

	public void setBrowserLocale(String browserLocale) {
		this.browserLocale = browserLocale;
	}

	public String getTurnitinTd() {
		return turnitinTd;
	}

	public void setTurnitinTd(String turnitinTd) {
		this.turnitinTd = turnitinTd;
	}

	public String getSubscribeToEmails() {
		return subscribeToEmails;
	}

	public void setSubscribeToEmails(String subscribeToEmails) {
		this.subscribeToEmails = subscribeToEmails;
	}

	public boolean isShowUserServices() {
		return showUserServices;
	}

	public void setShowUserServices(boolean showUserServices) {
		this.showUserServices = showUserServices;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getAvatarState() {
		return avatarState;
	}

	public void setAvatarState(String avatarState) {
		this.avatarState = avatarState;
	}

	public long getReminderTimeForDueDates() {
		return reminderTimeForDueDates;
	}

	public void setReminderTimeForDueDates(long reminderTimeForDueDates) {
		this.reminderTimeForDueDates = reminderTimeForDueDates;
	}

	public int getPageViewsCount() {
		return pageViewsCount;
	}

	public void setPageViewsCount(int pageViewsCount) {
		this.pageViewsCount = pageViewsCount;
	}

	public String getSortableName() {
		return sortableName;
	}

	public void setSortableName(String sortableName) {
		this.sortableName = sortableName;
	}

	public Map<String, String> getPreferences() {
		return preferences;
	}

	public void setPreferences(Map<String, String> preferences) {
		this.preferences = preferences;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(String deletedAt) {
		this.deletedAt = deletedAt;
	}

	public long getReminderTimeForGrading() {
		return reminderTimeForGrading;
	}

	public void setReminderTimeForGrading(long reminderTimeForGrading) {
		this.reminderTimeForGrading = reminderTimeForGrading;
	}

	public String getCrocodocId() {
		return crocodocId;
	}

	public void setCrocodocId(String crocodocId) {
		this.crocodocId = crocodocId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getLastLoggedOut() {
		return lastLoggedOut;
	}

	public void setLastLoggedOut(String lastLoggedOut) {
		this.lastLoggedOut = lastLoggedOut;
	}

	public String[] getStuck_sis_fields() {
		return stuck_sis_fields;
	}

	public void setStuck_sis_fields(String[] stuck_sis_fields) {
		this.stuck_sis_fields = stuck_sis_fields;
	}

	public String getSchoolPosition() {
		return schoolPosition;
	}

	public void setSchoolPosition(String schoolPosition) {
		this.schoolPosition = schoolPosition;
	}

	public int getUnreadInboxItemsCount() {
		return unreadInboxItemsCount;
	}

	public void setUnreadInboxItemsCount(int unreadInboxItemsCount) {
		this.unreadInboxItemsCount = unreadInboxItemsCount;
	}

	public String getAvatarImageSource() {
		return avatarImageSource;
	}

	public void setAvatarImageSource(String avatarImageSource) {
		this.avatarImageSource = avatarImageSource;
	}

	public Object getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(Object timeZone) {
		this.timeZone = timeZone;
	}

	public String getInitialEnrollmentType() {
		return initialEnrollmentType;
	}

	public void setInitialEnrollmentType(String initialEnrollmentType) {
		this.initialEnrollmentType = initialEnrollmentType;
	}

	public String getLtiContextId() {
		return ltiContextId;
	}

	public void setLtiContextId(String ltiContextId) {
		this.ltiContextId = ltiContextId;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getVisibleInboxTypes() {
		return visibleInboxTypes;
	}

	public void setVisibleInboxTypes(String visibleInboxTypes) {
		this.visibleInboxTypes = visibleInboxTypes;
	}

	public String getLastUserNote() {
		return lastUserNote;
	}

	public void setLastUserNote(String lastUserNote) {
		this.lastUserNote = lastUserNote;
	}

	public String getaPublic() {
		return aPublic;
	}

	public void setaPublic(String aPublic) {
		this.aPublic = aPublic;
	}

	@Override
	public String toString() {
		return "CanvasUser [id=" + id + ", avatarImageUrl=" + avatarImageUrl + ", schoolName=" + schoolName + ", visibility=" + visibility
				+ ", storageQuota=" + storageQuota + ", avatarImageUpdatedAt=" + avatarImageUpdatedAt + ", locale=" + locale
				+ ", unreadConversationsCount=" + unreadConversationsCount + ", registrationRemoteIp=" + registrationRemoteIp
				+ ", mergeTo=" + mergeTo + ", workflowState=" + workflowState + ", browserLocale=" + browserLocale + ", turnitinTd="
				+ turnitinTd + ", subscribeToEmails=" + subscribeToEmails + ", showUserServices=" + showUserServices + ", name=" + name
				+ ", birthdate=" + birthdate + ", gender=" + gender + ", createdAt=" + createdAt + ", avatarState=" + avatarState
				+ ", reminderTimeForDueDates=" + reminderTimeForDueDates + ", pageViewsCount=" + pageViewsCount + ", sortableName="
				+ sortableName + ", preferences=" + preferences + ", loginId=" + loginId + ", deletedAt=" + deletedAt
				+ ", reminderTimeForGrading=" + reminderTimeForGrading + ", crocodocId=" + crocodocId + ", shortName=" + shortName
				+ ", countryCode=" + countryCode + ", lastLoggedOut=" + lastLoggedOut + ", stuck_sis_fields="
				+ Arrays.toString(stuck_sis_fields) + ", schoolPosition=" + schoolPosition + ", unreadInboxItemsCount="
				+ unreadInboxItemsCount + ", avatarImageSource=" + avatarImageSource + ", timeZone=" + timeZone
				+ ", initialEnrollmentType=" + initialEnrollmentType + ", ltiContextId=" + ltiContextId + ", updatedAt=" + updatedAt
				+ ", visibleInboxTypes=" + visibleInboxTypes + ", lastUserNote=" + lastUserNote + ", aPublic=" + aPublic + "]";
	}
	
}