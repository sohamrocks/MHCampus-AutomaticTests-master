package com.mcgraw.test.automation.framework.core.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

public class ResourceUtils {

	public static File getResourceAsTempFile(final String resource) {
		return getResourceAs(resource, new IOConverter<File>() {

			@Override
			public File convert(InputStream is) throws IOException {
				String fileName = FilenameUtils.getBaseName(resource);
				String extension = FilenameUtils.getExtension(resource);
				File tempFile = File.createTempFile(fileName, "." + extension);
				FileUtils.copyInputStreamToFile(
						getResourceAsInputStream(resource), tempFile);
				return tempFile;
			}
		});
	}

	public static Source getResourceAsSource(String resource) {
		return getResourceAs(resource, new IOConverter<Source>() {

			@Override
			public Source convert(InputStream is) throws IOException {
				return new StreamSource(is);
			}
		});
	}

	public static String getResourceAsString(String resource) {
		return getResourceAs(resource, new IOConverter<String>() {
			@Override
			public String convert(InputStream is) throws IOException {
				String data = IOUtils.toString(is);
				IOUtils.closeQuietly(is);
				return data;
			}
		});
	}

	public static byte[] getResourceAsByteArray(String resource) {
		return getResourceAs(resource, new IOConverter<byte[]>() {
			@Override
			public byte[] convert(InputStream is) throws IOException {
				byte[] data = IOUtils.toByteArray(is);
				IOUtils.closeQuietly(is);
				return data;
			}
		});
	}

	public static InputStream getResourceAsInputStream(String resource) {
		return getClassLoader().getResourceAsStream(resource);
	}

	private static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public static <T> T getResourceAs(String resource, IOConverter<T> converter) {
		InputStream is = null;
		try {
			is = getResourceAsInputStream(resource);
			return converter.convert(is);
		} catch (IOException e) {
			throw new RuntimeException("Unable to convert resource with name '"
					+ resource + "' using converter " + converter.getClass());
		}
	}

	private interface IOConverter<T> {
		T convert(InputStream is) throws IOException;

	}

}
