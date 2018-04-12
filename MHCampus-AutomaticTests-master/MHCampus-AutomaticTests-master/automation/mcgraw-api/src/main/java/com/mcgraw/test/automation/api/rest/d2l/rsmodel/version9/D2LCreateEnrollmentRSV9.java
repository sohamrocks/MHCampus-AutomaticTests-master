package com.mcgraw.test.automation.api.rest.d2l.rsmodel.version9;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.version9.D2LUserRQV9.D2LUserRoleDeserializerForV9;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.version9.D2LUserRQV9.D2LUserRoleSerializerForV9;
import com.mcgraw.test.automation.api.rest.d2l.rsmodel.D2LCreateEnrollmentRS;

public class D2LCreateEnrollmentRSV9 extends D2LCreateEnrollmentRS {
	
	@JsonSerialize(using = D2LUserRoleSerializerForV9.class)
	@JsonDeserialize(using = D2LUserRoleDeserializerForV9.class)
	@JsonProperty(value = "RoleId")
	protected D2LUserRole role;

}
