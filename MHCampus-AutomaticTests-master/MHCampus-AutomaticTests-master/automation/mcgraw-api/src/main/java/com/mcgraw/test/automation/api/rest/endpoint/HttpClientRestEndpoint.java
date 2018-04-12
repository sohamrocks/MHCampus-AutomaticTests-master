package com.mcgraw.test.automation.api.rest.endpoint;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mcgraw.test.automation.api.rest.endpoint.MultiPartRequest.MultiPartBinary;
import com.mcgraw.test.automation.api.rest.endpoint.exception.RestEndpointIOException;
import com.mcgraw.test.automation.api.rest.endpoint.exception.SerializerException;

/**
 * {@link com.mcgraw.test.automation.api.rest.endpoint.RestEndpoint}
 * implementation. Uses Apache HTTP Components
 * {@link org.apache.http.client.HttpClient} as default http client
 * implementation
 * 
 */
public class HttpClientRestEndpoint implements RestEndpoint {

	/** Serializer for converting HTTP messages */
	private Serializer serializer;

	/** Base Endpoint URL */
	private String baseUrl;

	/** Credentials used for client authentification */
	private Credentials credentials;

	/** Error Handler for HttpResponses */
	private ErrorHandler<HttpResponse> errorHandler;

	/** List of additional headers for HttpRequests */
	private List<Header> additionalHeaders;

	/** HTTP Client Supplier. Used for lazy initialization */
	private Supplier<HttpClient> httpClient = Suppliers.memoize(new Supplier<HttpClient>() {
		@Override
		public HttpClient get() {
			return initHttpClient(credentials);
		}
	});

	private boolean disabledSSLValidation = false;

	public void setDisabledSSLValidation(boolean disabledSSLValidation) {
		this.disabledSSLValidation = disabledSSLValidation;
	}

	/**
	 * Default constructor.
	 * 
	 * @param serializer
	 *            - Serializer for converting HTTP messages. Shouldn't be null
	 * @param errorHandler
	 *            - Error handler for HTTP messages
	 * @param credentials
	 *            - Credentials for HTTP client
	 * @param baseUrl
	 *            - REST WebService Base URL
	 */
	public HttpClientRestEndpoint(@Nonnull Serializer serializer, @Nullable ErrorHandler<HttpResponse> errorHandler,
			@Nullable Credentials credentials, @Nonnull String baseUrl, @Nullable List<Header> additionalHeaders) {
		this.serializer = Preconditions.checkNotNull(serializer, "Serializer should'be be null");
		this.baseUrl = Preconditions.checkNotNull(baseUrl, "Base URL shouldn't be null");

		this.errorHandler = errorHandler == null ? new DefaultErrorHandler() : errorHandler;
		this.credentials = credentials;
		this.additionalHeaders = additionalHeaders;
	}

	/**
	 * Initializes HTTP cllient
	 * 
	 * @param credentials
	 * @return
	 */
	private HttpClient initHttpClient(Credentials credentials) {

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

		PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager(schemeRegistry);
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(5);

		DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager);
		httpClient.getParams().setIntParameter("http.socket.timeout", 60000);
		if (null != credentials) {
			httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, credentials);
		}

		if (disabledSSLValidation) {
			return wrapClient(httpClient);
		}
		return httpClient;
	}

	/**
	 * wrap HttpClient to avoid the
	 * "javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated"
	 * 
	 * @param base
	 *            - httpClient to wrap
	 * @return wrapped httpClient
	 */
	public HttpClient wrapClient(HttpClient base) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = base.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));
			sr.register(new Scheme("https", 80, ssf));
			HttpClient resulthttpClient = new DefaultHttpClient(ccm, base.getParams());
			
			return resulthttpClient;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public <RS> RS post(String resource, List<NameValuePair> params, Class<RS> clazz) throws RestEndpointIOException,
			UnsupportedEncodingException {
		HttpPost post = new HttpPost(spliceUrl(resource));
		post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
		return executeInternal(post, clazz);
	}

	@Override
	public <RS> RS post(String resource, Map<String, String> URIParameters, List<NameValuePair> bodyParams, Class<RS> clazz)
			throws RestEndpointIOException, UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		Iterator<NameValuePair> i = bodyParams.iterator();
		while (i.hasNext()) {
			sb.append(i.next().toString());
			if (i.hasNext())
				sb.append("&");
		}
		StringEntity entity = new StringEntity(sb.toString(), Consts.UTF_8);
		entity.setContentType(URLEncodedUtils.CONTENT_TYPE);
		HttpPost post = new HttpPost(spliceUrl(resource, URIParameters));
		post.setEntity(entity);
		return executeInternal(post, clazz);
	}

	@Override
	public <RQ, RS> RS post(URI uri, RQ rq, Class<RS> clazz) throws RestEndpointIOException, UnsupportedEncodingException {
		HttpPost post = new HttpPost(uri);
		if (null != rq) {
			ByteArrayEntity httpEntity = new ByteArrayEntity(serializer.serialize(rq), ContentType.create(serializer.getMimeType(),
					Consts.UTF_8));
			post.setEntity(httpEntity);
		}
		return executeInternal(post, clazz);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mcgraw.test.automation.api.rest.endpoint.RestEndpoint#post(java.lang
	 * .String, java.lang.Object, java.lang.Class)
	 */
	@Override
	public <RQ, RS> RS post(String resource, RQ rq, Class<RS> clazz) throws RestEndpointIOException, UnsupportedEncodingException {
		HttpPost post = new HttpPost(spliceUrl(resource));
		if (null != rq) {
			ByteArrayEntity httpEntity = new ByteArrayEntity(serializer.serialize(rq), ContentType.create(serializer.getMimeType(),
					Consts.UTF_8));
			post.setEntity(httpEntity);
		}
		return executeInternal(post, clazz);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mcgraw.test.automation.api.rest.endpoint.RestEndpoint#post(java.lang
	 * .String, com.mcgraw.test.automation.api.rest.endpoint.MultiPartRequest,
	 * java.lang.Class)
	 */
	@Override
	public <RQ, RS> RS post(String resource, MultiPartRequest<RQ> request, Class<RS> clazz) throws RestEndpointIOException {
		HttpPost post = new HttpPost(spliceUrl(resource));

		if (null != request) {
			try {
				MultipartEntity entity = new MultipartEntity();
				for (MultiPartRequest.MultiPartSerialized<RQ> serializedPart : request.getSerializedRQs()) {
					FormBodyPart part = new FormBodyPart(serializedPart.getPartName(), new StringBody(new String(
							serializer.serialize(serializedPart.getRequest())), serializer.getMimeType(), Consts.UTF_8));
					entity.addPart(part);
				}

				for (MultiPartBinary partBinaty : request.getBinaryRQs()) {
					FormBodyPart part = new FormBodyPart(partBinaty.getPartName(), new ByteArrayBody(partBinaty.getData(),
							partBinaty.getContentType(), partBinaty.getFilename()));
					entity.addPart(part);
				}

				post.setEntity(entity);

			} catch (Exception e) {
				throw new RestEndpointIOException("Unable to build post multipart request", e);
			}
		}
		return executeInternal(post, clazz);
	}

	@Override
	public <RQ, RS> RS put(URI uri, RQ rq, Class<RS> clazz) throws RestEndpointIOException {
		HttpPut put = new HttpPut(uri);
		if (null != rq) {
			ByteArrayEntity httpEntity = new ByteArrayEntity(serializer.serialize(rq), ContentType.create(serializer.getMimeType(),
					Consts.UTF_8));
			put.setEntity(httpEntity);

		}
		return executeInternal(put, clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mcgraw.test.automation.api.rest.endpoint.RestEndpoint#put(java.lang
	 * .String, java.lang.Object, java.lang.Class)
	 */
	@Override
	public <RQ, RS> RS put(String resource, RQ rq, Class<RS> clazz) throws RestEndpointIOException {
		HttpPut put = new HttpPut(spliceUrl(resource));
		if (null != rq) {
			ByteArrayEntity httpEntity = new ByteArrayEntity(serializer.serialize(rq), ContentType.create(serializer.getMimeType(),
					Consts.UTF_8));
			put.setEntity(httpEntity);

		}
		return executeInternal(put, clazz);
	}

	@Override
	public <RQ, RS> RS delete(String resource, RQ rq, Class<RS> clazz) throws RestEndpointIOException {
		HttpDeleteWithBody delete = new HttpDeleteWithBody(spliceUrl(resource));
		if (null != rq) {
			ByteArrayEntity httpEntity = new ByteArrayEntity(serializer.serialize(rq), ContentType.create(serializer.getMimeType(),
					Consts.UTF_8));
			delete.setEntity(httpEntity);

		}
		return executeInternal(delete, clazz);
	}

	@Override
	public <RS> RS delete(String resource, Class<RS> clazz) throws RestEndpointIOException {
		HttpDelete delete = new HttpDelete(spliceUrl(resource));
		return executeInternal(delete, clazz);
	}
	
	@Override
	public <RS> RS delete(URI uri, Class<RS> clazz) throws RestEndpointIOException {
		HttpDelete delete = new HttpDelete(	uri);
		return executeInternal(delete, clazz);
	}
	
	@Override
	public <RS> RS get(URI uri, Class<RS> clazz) throws RestEndpointIOException {
		HttpGet get = new HttpGet(uri);
		return executeInternal(get, clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mcgraw.test.automation.api.rest.endpoint.RestEndpoint#get(java.lang
	 * .String, java.lang.Class)
	 */
	@Override
	public <RS> RS get(String resource, Class<RS> clazz) throws RestEndpointIOException {
		HttpGet get = new HttpGet(spliceUrl(resource));
		return executeInternal(get, clazz);
	}

	@Override
	public <RS> RS get(String resource, Map<String, String> parameters, Class<RS> clazz) throws RestEndpointIOException {
		HttpGet get = new HttpGet(spliceUrl(resource, parameters));
		return executeInternal(get, clazz);
	}

	/**
	 * Executes {@link org.apache.http.client.methods.HttpUriRequest}
	 * 
	 * @param rq
	 *            - Request
	 * @param clazz
	 *            - Response Body Type
	 * @return - Serialized Response Body
	 * @throws RestEndpointIOException
	 */
	private <RS> RS executeInternal(HttpUriRequest rq, Class<RS> clazz) throws RestEndpointIOException {
		try {
			if (null != additionalHeaders) {
				for (Header header : additionalHeaders) {
					rq.addHeader(header);
				}
			}
			ConsumedResponse response = new ConsumedResponse(httpClient.get().execute(rq));
			if (errorHandler.hasError(response)) {
				errorHandler.handle(response);
			}

			HttpEntity entity = response.getEntity();
			if (null != clazz && null != entity.getContentType().getValue() && serializer.isSupported(entity.getContentType().getValue())) {
				return serializer.deserialize(EntityUtils.toByteArray(entity), clazz);
			} else if (null == clazz) {
				return null;
			} else {
				throw new SerializerException("Unsupported media type '" + entity.getContentType() + "'");
			}

		} catch (SerializerException e) {
			throw e;
		} catch (IOException e) {
			throw new RestEndpointIOException("Unable to execute request", e);
		} finally {
			rq.abort();
		}

	}

	/**
	 * Splice base URL and URL of resource
	 * 
	 * @param resource
	 * @return
	 * @throws RestEndpointIOException
	 */
	private URI spliceUrl(String resource) throws RestEndpointIOException {
		try {
			if (resource == null) {
				return new URI(baseUrl);
			}
			return new URIBuilder(baseUrl).setPath(resource).build();
		} catch (URISyntaxException e) {
			throw new RestEndpointIOException("Unable to builder URL with base url '" + baseUrl + "' and resouce '" + resource + "'", e);
		}
	}

	/**
	 * Splice base URL and URL of resource
	 * 
	 * @param resource
	 * @return
	 * @throws RestEndpointIOException
	 */
	private URI spliceUrl(String resource, Map<String, String> parameters) throws RestEndpointIOException {
		try {
			if (resource == null) {
				return new URI(baseUrl);
			}
			URIBuilder builder = new URIBuilder(baseUrl).setPath(resource);
			for (Entry<String, String> parameter : parameters.entrySet()) {
				builder.addParameter(parameter.getKey(), parameter.getValue());
			}
			return builder.build();
		} catch (URISyntaxException e) {
			throw new RestEndpointIOException("Unable to builder URL with base url '" + baseUrl + "' and resouce '" + resource + "'", e);
		}
	}
}
