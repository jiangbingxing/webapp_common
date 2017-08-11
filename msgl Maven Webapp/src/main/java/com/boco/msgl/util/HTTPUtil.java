package com.boco.msgl.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HTTPUtil {
	private static Logger log = Logger.getLogger(HTTPUtil.class);

	private static HttpGet getHttpGet(String url, Map<String, String> params, String encode) {
		StringBuffer buf = new StringBuffer(url);
		if (params != null) {
			// 地址增加?或�??&
			String flag = (url.indexOf('?') == -1) ? "?" : "&";
			// 添加参数
			for (String name : params.keySet()) {
				buf.append(flag);
				buf.append(name);
				buf.append("=");
				try {
					String param = params.get(name);
					if (param == null) {
						param = "";
					}
					buf.append(URLEncoder.encode(param, encode));
				} catch (UnsupportedEncodingException e) {
					log.error("URLEncoder Error,encode=" + encode + ",param=" + params.get(name), e);
				}
				flag = "&";
			}
		}
		HttpGet httpGet = new HttpGet(buf.toString());
		return httpGet;
	}

	/**
	 * 下载文件保存到本�?
	 * 
	 * @param path
	 *            文件保存位置
	 * @param url
	 *            文件地址
	 * @throws IOException
	 */
	public static byte[] downloadFile(String url, Map<String, String> param) throws IOException {
		HttpClient client = null;
		try {
			// 创建HttpClient对象
			client = new DefaultHttpClient();
			// 获得HttpGet对象
			HttpGet httpGet = getHttpGet(url, param, "utf-8");
			// 发�?�请求获得返回结�?
			HttpResponse response = client.execute(httpGet);
			// 如果成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				byte[] result = EntityUtils.toByteArray(response.getEntity());
				return result;
			}
			// 如果失败
			else {
				StringBuffer errorMsg = new StringBuffer();
				errorMsg.append("httpStatus:");
				errorMsg.append(response.getStatusLine().getStatusCode());
				errorMsg.append(response.getStatusLine().getReasonPhrase());
				errorMsg.append(", Header: ");
				Header[] headers = response.getAllHeaders();
				for (Header header : headers) {
					errorMsg.append(header.getName());
					errorMsg.append(":");
					errorMsg.append(header.getValue());
				}
				log.error("HttpResonse Error:" + errorMsg);
				return null;
			}
		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				client.getConnectionManager().shutdown();
			} catch (Exception e) {
				log.error("finally HttpClient shutdown error", e);
			}
		}
	}

	public static String doPost(String url, Map<String, String> params) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		log.info("create httppost:" + url);
		HttpPost post = null;
		try {
			post = postForm(url, params);
		} catch (Exception e) {
			log.error(e);
		}

		try {
			body = invoke(httpclient, post);
		} catch (Exception e) {
			log.error(e);
		}

		httpclient.getConnectionManager().shutdown();

		return body;

	}

	public static String get(String url) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		log.info("create httppost:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	private static String invoke(DefaultHttpClient httpclient, HttpUriRequest httpost) throws Exception {

		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);

		return body;
	}

	private static String paseResponse(HttpResponse response) throws Exception {
		log.info("get response from http server..");
		HttpEntity entity = response.getEntity();

		log.info("response status: " + response.getStatusLine());
		String charset = EntityUtils.getContentCharSet(entity);
		log.info(charset);

		String body = null;

		body = EntityUtils.toString(entity);
		log.info(body);

		return body;
	}

	private static HttpResponse sendRequest(DefaultHttpClient httpclient, HttpUriRequest httpost) throws Exception {
		log.info("execute post...");
		HttpResponse response = null;

		response = httpclient.execute(httpost);

		return response;
	}

	private static HttpPost postForm(String url, Map<String, String> params) throws Exception {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		log.info("set utf-8 form entity to httppost");
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

		return httpost;
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// (ModelMap modelMap, String Ip, String User, String UserEncryptd,
		// String Password,String PasswordEncryptd, String ShellCommand)
		map.put("Ip", "192.168.123.11");
		map.put("User", "root");
		map.put("Password", "late1619");
		map.put("ShellCommand", "ls");

		System.out.println(doPost("http://localhost:8380/BOCO_ShellCommand_Execute", map));
	}

}
