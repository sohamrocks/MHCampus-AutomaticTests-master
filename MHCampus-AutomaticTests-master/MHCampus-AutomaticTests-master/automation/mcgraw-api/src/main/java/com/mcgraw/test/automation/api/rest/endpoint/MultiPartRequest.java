package com.mcgraw.test.automation.api.rest.endpoint;

import java.util.ArrayList;
import java.util.List;

/**
 * MultiPartRequest. Contains part which should be serialized and binary part to
 * be placed in Request
 * 
 * @param <RQ>
 *            - Type of request to be serialized
 */
public class MultiPartRequest<RQ> {

	/** Set of Serialized Parts */
	private List<MultiPartSerialized<RQ>> serializedRQs;

	/** Set of binary parts */
	private List<MultiPartBinary> binaryRQs;

	public MultiPartRequest(List<MultiPartSerialized<RQ>> serializedRQs,
			List<MultiPartBinary> binaryRQs) {
		this.serializedRQs = serializedRQs;
		this.binaryRQs = binaryRQs;
	}

	public List<MultiPartBinary> getBinaryRQs() {
		return binaryRQs;
	}

	public List<MultiPartSerialized<RQ>> getSerializedRQs() {
		return serializedRQs;
	}

	/**
	 * Part of request to be serialized
	 * 
	 * @param <RQ>
	 */
	public static class MultiPartSerialized<RQ> {

		private String partName;

		private RQ request;

		public MultiPartSerialized(String partName, RQ request) {
			this.partName = partName;
			this.request = request;
		}

		public String getPartName() {
			return partName;
		}

		public RQ getRequest() {
			return request;
		}
	}

	/**
	 * Binary part of request
	 * 
	 */
	public static class MultiPartBinary {
		private String partName;
		private String filename;
		private String contentType;
		private byte[] data;

		public MultiPartBinary(String partName, String filename,
				String contentType, byte[] data) {
			this.partName = partName;
			this.filename = filename;
			this.data = data;
			this.contentType = contentType;
		}

		public byte[] getData() {
			return data;
		}

		public String getFilename() {
			return filename;
		}

		public String getPartName() {
			return partName;
		}

		public String getContentType() {
			return contentType;
		}

	}

	/**
	 * Builder for multipart requests
	 * 
	 * @param <RQ>
	 */
	public static class Builder<RQ> {
		private List<MultiPartSerialized<RQ>> serializedRQs;

		private List<MultiPartBinary> binaryRQs;

		public Builder() {
			serializedRQs = new ArrayList<MultiPartRequest.MultiPartSerialized<RQ>>();
			binaryRQs = new ArrayList<MultiPartRequest.MultiPartBinary>();
		}

		public Builder<RQ> addSerializedPart(String partName, RQ body) {
			serializedRQs.add(new MultiPartSerialized<RQ>(partName, body));
			return this;
		}

		public Builder<RQ> addBinaryPart(String partName, String filename,
				String contentType, byte[] data) {
			binaryRQs.add(new MultiPartBinary(partName, filename, contentType,
					data));
			return this;
		}

		public MultiPartRequest<RQ> build() {
			return new MultiPartRequest<RQ>(serializedRQs, binaryRQs);
		}
	}
}
