package com.mcgraw.test.automation.tests.lti2;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mcgraw.test.automation.framework.core.results.logger.Logger;

public class Lti2TestUtil {
	
	//Nested class to hold values in a list which will be used in the test class
	public class validateTcprofileResponse {
		public List<String> capabilities;
		public List<String> expectedMissingCapabilities;
		public int statusCode;
		boolean result;
		public validateTcprofileResponse() {
			super();
			this.capabilities = new ArrayList<String>();
			this.expectedMissingCapabilities = new ArrayList<String>();
		}
	}
	private final String mhcEndpoint = "https://login-aws-qa.mhcampus.com/SSO/LTI2/ToolProxyRegistration.aspx";
	private final String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
	private final String Accept_Encoding = "gzip, deflate";
	private final String Accept_Language = "en-US,en;q=0.8";
	private String regKey = "4r0UVsfL";
	private String regPassword = "4r0UVsfL";
	private String ltiVersion = "LTI-2p0";
	private String ltiMessageType = "ToolProxyRegistrationRequest";
	private String minReq01 = "basic-lti-launch-request";
	private String minReq02 = "ResourceLink.id";
	private String minReq03 = "User.id";
	private String minReq04 = "Membership.role";
	private String minReq05 = "Context.id";
	private static HttpClient client;
	private static String responseHtmlPage;
	private static HttpResponse response;
	private List<String> requiredCapabilities;
	private List<List<String>> subsets;

	//Main class constructor
	public Lti2TestUtil() {
		super();
		requiredCapabilities = new ArrayList<String>();
		requiredCapabilities.add(minReq01);
		requiredCapabilities.add(minReq02);
		requiredCapabilities.add(minReq03);
		requiredCapabilities.add(minReq04);
		requiredCapabilities.add(minReq05);
		subsets = findAllSubsets(requiredCapabilities);
	}
	public List<String> getSubsetTestValue(int testNum) {
		return subsets.get(testNum);
	}

	/**
	 * 
	 * @param testNumber
	 * @return
	 * @throws Exception
	 * Determinate what set of capabilities would be sent to the test class.
	 * There are 32 different combination of sets. count starts at 0 and ends in 31.
	 * 
	 */
	validateTcprofileResponse validateTcProfile(int testNumber) throws Exception {
		boolean result = true;
		Logger.info("Building set for test #"+testNumber);
		List<String> capabilitiesSet = subsets.get(testNumber);
		List<String> expectedMissingCapabilities = new ArrayList<String>();
		for (String c : requiredCapabilities) {
			if (!capabilitiesSet.contains(c)) {
				expectedMissingCapabilities.add(c);
			}
		}

		String guid = sendRequestFormToCelticTool(capabilitiesSet);
		Lti2HttpResponse response = mhcResponse(guid);
		Logger.info("Tc Profile capabilities set: " + capabilitiesSet.toString());
		Logger.info("Expected missing capabilities: "
				+ expectedMissingCapabilities.toString());
		boolean tempRes = validateMissingCapabilities(response.StatusCode, response.Html,
				expectedMissingCapabilities);
		result = result && tempRes;
		Logger.info("Actual result: " + tempRes);
		Logger.info("Status code: " + response.StatusCode);
		Logger.info("Actual response: " + response.Html);
		validateTcprofileResponse resp = new validateTcprofileResponse();
		resp.result = result;
		resp.capabilities = capabilitiesSet;
		resp.statusCode = response.StatusCode;
		return resp;
	}

	// HTTP POST request to do initial registration to the IMS Celtic Tool
	private String sendRequestFormToCelticTool(List<String> capabilities)
			throws Exception {
		Logger.info("Generate HTTP Request, sending it to the celtic tool.");
		// Generate random id
		UUID rnd = UUID.randomUUID();
		String guid = rnd.toString().replaceAll("-", "");
		Logger.info("Random Cookie: " + guid);
		String url = "http://ltiapps.net/test/tc-register.php";
		client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		// Add headers to get request
		post.addHeader("Accept", Accept);
		post.addHeader("Accept-Encoding", Accept_Encoding);
		post.addHeader("Cookie", "PHPSESSID=" + guid);
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("lti_message_type",ltiMessageType
				));
		urlParameters.add(new BasicNameValuePair("lti_version",ltiVersion ));
		urlParameters.add(new BasicNameValuePair("reg_key", regKey));
		urlParameters.add(new BasicNameValuePair("reg_password", regPassword));
		urlParameters.add(new BasicNameValuePair("tc_profile_url",
				"http://ltiapps.net/test/tc-profile.php/" + guid));
		urlParameters.add(new BasicNameValuePair("launch_presentation_return_url",
				"http://ltiapps.net/test/tc-return.php"));
		for (String capability : capabilities) {
			urlParameters.add(new BasicNameValuePair("capability[]", capability));
		}
		urlParameters.add(new BasicNameValuePair("service[]", "ToolConsumerProfile.url"));
		urlParameters.add(new BasicNameValuePair("service[]", "Tool Proxy"));
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		response = client.execute(post);
		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line + "\n");
		}
		return guid;
	}
	// HTTP POST request to get response from MHCampus
	protected Lti2HttpResponse mhcResponse(String guid) throws Exception {
		Logger.info("Sending HttpPost request to MHCampus, triggering a response that will validate if MHC is handeling lti2.0 request well");
		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadTrustMaterial(new File("c:\\cacerts"), "changeit".toCharArray(),
						new TrustSelfSignedStrategy())
				.build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2" }, null,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", socketFactory).build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		// HttpHost proxy = new HttpHost("127.0.0.1", 9999);
		client = HttpClientBuilder.create()/* .setProxy(proxy) */.setConnectionManager(cm)
				.build();
		HttpPost post = new HttpPost(mhcEndpoint);
		// add header
		post.addHeader("Accept", Accept);
		post.addHeader("Accept-Encoding", Accept_Encoding);
		post.addHeader("Accept-Language", Accept_Language);
		// WebForm Parmas
		String tcProfileUrl = "http://ltiapps.net/test/tc-profile.php/" + guid;
		Logger.info("Full TC profile URL: "+tcProfileUrl);
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("lti_message_type",ltiMessageType
				));
		urlParameters.add(new BasicNameValuePair("lti_version",ltiVersion ));
		urlParameters.add(new BasicNameValuePair("reg_key", regKey));
		urlParameters.add(new BasicNameValuePair("reg_password", regPassword));
		urlParameters.add(new BasicNameValuePair("tc_profile_url", tcProfileUrl));
		urlParameters.add(new BasicNameValuePair("launch_presentation_return_url",
				"http://ltiapps.net/test/tc-return.php"));
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		HttpResponse response = client.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();
		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line + "\n");
		}
		responseHtmlPage = result.toString();
		Lti2HttpResponse lti2Response = new Lti2HttpResponse();
		lti2Response.Html = responseHtmlPage;
		lti2Response.StatusCode = statusCode;
		return lti2Response;
	}

	// Go through HTML response and compare status success and failure
	public boolean validateMissingCapabilities(int statusCode, String responseUrl,
			List<String> expectedMissingCapabilities)
			throws UnsupportedEncodingException {
		boolean result = true;
//		if (statusCode != 302)
//			return false;
		for (String ec : expectedMissingCapabilities) {
			if (responseUrl.indexOf(ec) == -1) { // not found in string
				return false;
			}
		}
		return result;
	}
	
	//Postivie test (if all required caps are in the request and status code is 200)
	public boolean validatePositiveFlow(int statusCode){
		boolean result = true;
		if (statusCode != 200) {
			return false;
		}
		if (!(requiredCapabilities.size() == subsets.size())) {
			System.out.println("Caps in request: " + subsets.toString());
			System.out.println("Size of caps array: " + subsets.size());
			return false;
		}
		return result;
	}

	public boolean validateCapabilities(HashSet<String> capabilities) {
		boolean result = true;
		// 1. Parse response to HTML document
		Document doc = Jsoup.parse(responseHtmlPage);
		// 2. Build list of capabilities
		Elements pElements = doc.getElementsByTag("p"); // Search for
														// <p>Capabilities</p>
		for (Element p : pElements) {
			if (p.nodeName().equals("Capabilities")) {
				@SuppressWarnings("unused")
				Elements sibs = p.siblingElements();
			}
		}
		// 3. Compare original capabilities list to target list (found in
		// response POSITIVE)
		return result;
	}
	public static List<List<String>> findAllSubsets(List<String> input) {
		// subsets one by one
		List<List<String>> result = new ArrayList<List<String>>();
		int n = input.size();
		for (int i = 0; i < (1 << n); i++) {
			List<String> current = new ArrayList<String>();
			for (int j = 0; j < n; j++)
				// (1<<j) is a number with jth bit 1
				// so when we 'and' them with the
				// subset number we get which numbers
				// are present in the subset and which
				// are not
				if ((i & (1 << j)) > 0)
					current.add(input.get(j));
			result.add(current);
		}
		return result;
	}
}