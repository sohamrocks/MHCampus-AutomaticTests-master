package com.mcgraw.test.automation.api.rest.endpoint;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


import com.mcgraw.test.automation.api.rest.endpoint.exception.SerializerException;
import com.mcgraw.test.automation.framework.core.common.JaxbMarshaller;

public class JaxbSerializer implements Serializer {

	@Override
	public <T> byte[] serialize(T t) throws SerializerException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JaxbMarshaller.getInstanceForContext(t.getClass()).marshall(t,
				new StreamResult(baos));
		return baos.toByteArray();
	}

	@Override
	public <T> T deserialize(byte[] content, Class<T> clazz)
			throws SerializerException {
		return JaxbMarshaller.getInstanceForContext(clazz).unmarshall(
				new StreamSource(new ByteArrayInputStream(content)), clazz);
	}

	@Override
	public String getMimeType() {
		return MIME.get(0);
	}

	@Override
	public boolean isSupported(String mimeType) {
		return MIME.contains(mimeType);
	}

	private static final List<String> MIME = new ArrayList<String>() {

		private static final long serialVersionUID = -7871877989848089771L;

		{
			add("application/x-www-form-urlencoded");
			add("text/xml");
			add("text/xml; Charset=windows-1252");
						
		}
	};

}
