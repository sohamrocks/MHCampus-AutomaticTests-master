package com.mcgraw.test.automation.framework.core.common.remote_access.http;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * HTTP utils
 * 
 * @author yyudzitski
 */
public class HttpUtils {
	/**
	 * Opens simple HttpURLConnection to URL and returns the whole content as
	 * String.
	 * 
	 * @param url
	 * @return URL content
	 * @throws IOException
	 * @throws Exception
	 */
	public String getContent(String url) throws IOException {
		Logger.operation("Open URL '" + url + "' and return it content as String.");
		URL connectionUrl = new URL(url);
		HttpURLConnection con = (HttpURLConnection) connectionUrl.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String line;
		StringBuilder result = new StringBuilder();
		while ((line = br.readLine()) != null) {
			result.append(line);
		}
		Logger.debug("Got " + result.length() + " bytes.");
		return result.toString();
	}

	/**
	 * Download file via http
	 * 
	 * @param address
	 *            - http address (http://...)
	 * @param localFileName
	 *            - local destination file
	 */
	public void downloadFile(String address, File localFileName) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;
		try {
			URL url = new URL(address);
			out = new BufferedOutputStream(new FileOutputStream(localFileName));
			conn = url.openConnection();
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
			}
		} catch (IOException ioe) {
			throw new RuntimeException("Cannot download file from " + address, ioe);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
				throw new RuntimeException("Cannot close HTTP channel or output file stream", ioe);
			}
		}
	}

	public File downloadFileWithAuthentification(String address, String login, String password, File localFile) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;
		try {
			URL url = new URL(address);
			out = new BufferedOutputStream(new FileOutputStream(localFile));
			String userPassword = login + ":" + password;
			String encoding = Base64.encodeBase64String(userPassword.getBytes());
			encoding = encoding.replace("\n", "");
			conn = url.openConnection();
			conn.setRequestProperty("Authorization", "Basic " + encoding);
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
			}
			return localFile;
		} catch (IOException ioe) {
			throw new RuntimeException("Cannot download file from " + address, ioe);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
				throw new RuntimeException("Cannot close HTTP channel or output file stream", ioe);
			}
		}
	}

	public int getResponseCode(String address) {
		int responseCode;
		try {
			URL url = new URL(address);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			responseCode = connection.getResponseCode();
			return responseCode;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}