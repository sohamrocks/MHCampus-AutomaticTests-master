package com.mcgraw.test.automation.api.rest.endpoint;

import com.mcgraw.test.automation.api.rest.endpoint.exception.SerializerException;

/**
 * HTTP Message Serializer. Converts messages to/from byte array
 * 
 */
public interface Serializer {

	/**
	 * Serializes Message into byte array
	 * 
	 * @param t
	 * @return
	 * @throws SerializerException
	 */
	<T> byte[] serialize(T t) throws SerializerException;

	/**
	 * Deserializes message from byte array
	 * 
	 * @param content
	 * @param clazz
	 * @return
	 * @throws SerializerException
	 */
	<T> T deserialize(byte[] content, Class<T> clazz)
			throws SerializerException;

	/**
	 * Returns MIME type of serialized messages
	 * 
	 * @return
	 */
	String getMimeType();

	/**
	 * Checks whether mime types is supported by this serializer implementation
	 * 
	 * @see http://en.wikipedia.org/wiki/Internet_media_type
	 * 
	 * @param mimeType
	 *            - MIME Type
	 * 
	 * @return TRUE if specified type is supported
	 */
	boolean isSupported(String mimeType);
}
