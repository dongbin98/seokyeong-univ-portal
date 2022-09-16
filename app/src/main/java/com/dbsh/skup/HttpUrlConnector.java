package com.dbsh.skup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUrlConnector {
	private HttpURLConnection connection;
	public HttpUrlConnector() {}
	public static HttpUrlConnector getInstance() {
		return LazyHolder.INSTANCE;
	}
	private static class LazyHolder {
		private static final HttpUrlConnector INSTANCE = new HttpUrlConnector();
	}

	public JSONObject getResponseWithToken(String HttpUrl, JSONObject request, String token) {
		try {
			URL url = new URL(HttpUrl);
			connection = (HttpURLConnection) url.openConnection();
			// Bearer
			connection.setRequestProperty("Authorization", "Bearer " + token);
			connection.setConnectTimeout(10000);// 연결 대기 시간 설정
			connection.setRequestMethod("POST");  // 전송 방식 POST
			connection.setDoInput(true);        // InputStream으로 서버로부터 응답받음
			connection.setDoOutput(true);       // OutputStream으로 POST데이터를 넘겨줌
			// 서버 Response를 JSON 형식의 타입으로 요청
			connection.setRequestProperty("Accept", "application/json");
			// 서버에게 Request Body 전달 시 application/json으로 서버에 전달
			connection.setRequestProperty("content-type", "application/json");

			OutputStream os = connection.getOutputStream();
			os.write(request.toString().getBytes(StandardCharsets.UTF_8));
			os.flush();

			// 연결 상태 확인
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println("request 실패!");
				return null;
			}
			// --------------
			// 서버에서 전송받기
			// --------------
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuilder sb = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
			reader.close();
			os.close();
			connection.disconnect();

			JSONObject response = new JSONObject(sb.toString());
			return response;
		} catch (IOException | JSONException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public JSONObject getResponse(String HttpUrl, JSONObject request) {
		try {
			URL url = new URL(HttpUrl);
			connection = (HttpURLConnection) url.openConnection();
			// Bearer
			connection.setConnectTimeout(10000);// 연결 대기 시간 설정
			connection.setRequestMethod("POST");  // 전송 방식 POST
			connection.setDoInput(true);        // InputStream으로 서버로부터 응답받음
			connection.setDoOutput(true);       // OutputStream으로 POST데이터를 넘겨줌
			// 서버 Response를 JSON 형식의 타입으로 요청
			connection.setRequestProperty("Accept", "application/json");
			// 서버에게 Request Body 전달 시 application/json으로 서버에 전달
			connection.setRequestProperty("content-type", "application/json");

			OutputStream os = connection.getOutputStream();
			os.write(request.toString().getBytes(StandardCharsets.UTF_8));
			os.flush();

			// 연결 상태 확인
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println("request 실패!");
				return null;
			}
			// --------------
			// 서버에서 전송받기
			// --------------
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuilder sb = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
			reader.close();
			os.close();
			connection.disconnect();

			JSONObject response = new JSONObject(sb.toString());
			return response;
		} catch (IOException | JSONException exception) {
			exception.printStackTrace();
			return null;
		}
	}
}
