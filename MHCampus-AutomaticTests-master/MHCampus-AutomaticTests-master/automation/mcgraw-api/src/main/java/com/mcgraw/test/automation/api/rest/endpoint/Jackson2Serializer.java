package com.mcgraw.test.automation.api.rest.endpoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcgraw.test.automation.api.rest.endpoint.exception.SerializerException;

/**
 * Serializer uses Jackson2 {@link https://github.com/FasterXML} API to
 * converting to/from POJO/JSON
 * 
 */
public class Jackson2Serializer implements Serializer {

	/**
	 * List of supported MIME types. It will be good to introduce encoding here,
	 * but first implementation uses UTF-8 only <br>
	 * TODO: Add encoding handling here
	 */
	private List<String> MIME_TYPES = new ArrayList<String>() {
		private static final long serialVersionUID = 7394081878944516157L;

		{
			add("application/json");
			add("application/+json");
			add("application/json;charset=UTF-8");
			add("application/json; charset=utf-8");
			add("application/json; charset=UTF-8");
			
		}
	};

	/** Default Object Mapper */
	private ObjectMapper objectMapper;

	public Jackson2Serializer(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Serializes POJO marked with <br>
	 * {@link https://github.com/FasterXML/jackson-annotations} to byte array
	 */
	@Override
	public <T> byte[] serialize(T t) throws SerializerException {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			objectMapper.getFactory().createGenerator(baos).writeObject(t);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new SerializerException("Unable to serialize object '" + t
					+ "'", e);
		} finally {
			IOUtils.closeQuietly(baos);
		}
	}

	/**
	 * Deserializes byte array to POJO marked with <br>
	 * {@link https://github.com/FasterXML/jackson-annotations}
	 */
	@Override
	public <T> T deserialize(byte[] content, Class<T> clazz)
			throws SerializerException {
		try {
			return objectMapper.getFactory().createParser(content)
					.readValueAs(clazz);
		} catch (IOException e) {
			throw new SerializerException("Unable to deserialize content '"
					+ new String(content) + "' to type '" + clazz.getName()
					+ "'", e);
		}
	}

	/**
	 * Returns default MIME type
	 */
	@Override
	public String getMimeType() {
		return MIME_TYPES.get(0);
	}

	/**
	 * Checks whether mime types is supported by this serializer implementation
	 */
	@Override
	public boolean isSupported(String mimeType) {
		return MIME_TYPES.contains(mimeType);
	}

}
