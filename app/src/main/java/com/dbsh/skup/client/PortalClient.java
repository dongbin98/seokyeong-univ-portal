package com.dbsh.skup.client;

import com.dbsh.skup.api.PortalApi;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PortalClient {
	private static PortalClient instance = null;
	private static PortalApi portalApi;

	private final static String BASE_URL = "https://sportal.skuniv.ac.kr/sportal/";
	private static String accessToken;

	private PortalClient(String accessToken) {
		this.accessToken = accessToken;
		Retrofit retrofit = new Retrofit.Builder()
				.client(getUnsafeOkHttpClient().build())
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		portalApi = retrofit.create(PortalApi.class);
	}

	public static PortalClient getInstance(String token) {
		if(instance == null) {
			instance = new PortalClient(token);
		} else {
			instance.setAccessToken(token);
		}
		return instance;
	}

	public OkHttpClient.Builder getUnsafeOkHttpClient() {
		try {
			final TrustManager[] trustAllCerts = new TrustManager[] {
					new X509TrustManager() {
						@Override
						public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
						}

						@Override
						public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
						}

						@Override
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return new java.security.cert.X509Certificate[]{};
						}
					}
			};

			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.addInterceptor(new Interceptor() {
				@Override
				public Response intercept(Chain chain) throws IOException {
					Request request = chain.request().newBuilder()
							.addHeader("Authorization", "Bearer " + accessToken).build();
					return chain.proceed(request);
				}
			});
			builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
			builder.hostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			return builder;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private OkHttpClient client = new OkHttpClient.Builder()
			.addInterceptor(new Interceptor() {
				@Override
				public Response intercept(Chain chain) throws IOException {
					Request request = chain.request().newBuilder()
							.addHeader("Authorization", "Bearer " + accessToken).build();
					return chain.proceed(request);
				}
			}).build();

	public static String getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(String accessToken) {
		PortalClient.accessToken = accessToken;
	}

	public static PortalApi getPortalApi() { return portalApi; }
}
