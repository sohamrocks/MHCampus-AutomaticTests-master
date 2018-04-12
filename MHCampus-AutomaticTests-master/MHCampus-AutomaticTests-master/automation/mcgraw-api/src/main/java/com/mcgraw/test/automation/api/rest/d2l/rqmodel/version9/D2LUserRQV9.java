package com.mcgraw.test.automation.api.rest.d2l.rqmodel.version9;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mcgraw.test.automation.api.rest.d2l.D2LUserRole;
import com.mcgraw.test.automation.api.rest.d2l.rqmodel.D2LUserRQ;

@JsonInclude(Include.NON_NULL)
public class D2LUserRQV9 extends D2LUserRQ{
	
	@JsonSerialize(using = D2LUserRoleSerializerForV9.class)
	@JsonDeserialize(using = D2LUserRoleDeserializerForV9.class)
	@JsonProperty(value = "RoleId")
	protected D2LUserRole role;
	
	public static class D2LUserRoleSerializerForV9 extends JsonSerializer<D2LUserRole> {

		@Override
		public void serialize(D2LUserRole userRole, JsonGenerator generator, SerializerProvider provider) throws IOException,
				JsonProcessingException {
			generator.writeString(String.valueOf(userRole.getRoleIdVersion9()));

		}
	}

	public static class D2LUserRoleDeserializerForV9 extends JsonDeserializer<D2LUserRole> {

		@Override
		public D2LUserRole deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext) throws IOException,
				JsonProcessingException {
			return D2LUserRole.getByIdForVersion9(paramJsonParser.getIntValue());
		}
	}

}
