package com.lau.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpUtil {

	public static String httpGetByUrl(String url) throws ClientProtocolException, IOException {
		String responseBody = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};
			responseBody = httpclient.execute(httpget, responseHandler);
		} finally {
			httpclient.close();
		}
		return responseBody;
	}

	@SuppressWarnings("deprecation")
	public static String postByBodyString(String url, String bodyString) throws ClientProtocolException, IOException {
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httppost = new HttpPost(url);
			log.info("postByBodyString - url:{} , bodyString:{}",url,bodyString);
			StringEntity reqEntity = new StringEntity(bodyString, StandardCharsets.UTF_8);
			httppost.setEntity(reqEntity);
			//设置连接超时5秒，请求超时1秒，返回数据超时8秒
			RequestConfig requestConfig = RequestConfig.custom()
			        .setConnectTimeout(5000).setConnectionRequestTimeout(1000)  
			        .setSocketTimeout(8000).build(); 
			httppost.setConfig(requestConfig);
			response = httpclient.execute(httppost);
			HttpEntity responseEntity = response.getEntity();
			byte[] bytes = EntityUtils.toByteArray(responseEntity);
		    result = new String(bytes, StandardCharsets.UTF_8);
		} finally {
			if (null != response) {
				response.close();
			}
			httpclient.close();
		}
		return result;
	}

	public static String postByBodyStringWithSession(String session, String url, String bodyString) throws ClientProtocolException, IOException {
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);
			BasicHeader header1 = new BasicHeader("session_id", session);
			httppost.addHeader(header1);
			StringEntity reqEntity = new StringEntity(bodyString, StandardCharsets.UTF_8);
			httppost.setEntity(reqEntity);
			CloseableHttpResponse response = null;
			try {
				response = httpclient.execute(httppost);
				result = EntityUtils.toString(response.getEntity());
			} finally {
				if (null != response) {
					response.close();
				}
			}
		} finally {
			httpclient.close();
		}
		return result;
	}

	public static String bssPost(String url, String bodyString, String checkSum) throws ClientProtocolException, IOException {
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);
			BasicHeader header1 = new BasicHeader("checkSum", checkSum);
			httppost.addHeader(header1);
			StringEntity reqEntity = new StringEntity(bodyString, "UTF-8");
			httppost.setEntity(reqEntity);
			CloseableHttpResponse response = null;
			try {
				response = httpclient.execute(httppost);
				result = EntityUtils.toString(response.getEntity());
			} finally {
				if (null != response) {
					response.close();
				}
			}
		} finally {
			httpclient.close();
		}
		return result;
	}

	public static String postByParamMap(String url, Map<String, String> param) throws ClientProtocolException, IOException {
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();

		List<NameValuePair> values = new ArrayList<NameValuePair>();
		for (String s : param.keySet()) {
			values.add(new BasicNameValuePair(s, param.get(s)));
		}
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(values, "GBK");
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(entity);
			CloseableHttpResponse response = null;
			try {
				response = httpclient.execute(httppost);
				result = EntityUtils.toString(response.getEntity());
			} finally {
				if (null != response) {
					response.close();
				}
			}
		} finally {
			httpclient.close();
		}
		return result;
	}

}
