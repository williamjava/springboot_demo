package com.jhzz.demo.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.util.StringUtils;
import com.jhzz.demo.common.constant.Constant;

/**
 * http协议工具
 *
 * @author sunjian 2016/12/01
 */
public class HttpUtils {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public final static int CONNECT_TIMEOUT = 600000;

	public final static int READ_TIMEOUT = 600000;

	/**
	 * 设置连接超时时间(单位毫秒)／设置读数据超时时间(单位毫秒)
	 */
	public final static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(READ_TIMEOUT)
			.setConnectTimeout(CONNECT_TIMEOUT).build();

	/**
	 * POST请求 公共方法
	 *
	 * @param url
	 *            URL
	 * @param requestEntity
	 *            请求参数
	 * @return 返回结果
	 */
	public static String post(String url, String requestEntity, String contentTypeValue) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			logger.info("post request url:[{}]", url);
			logger.info("requestEntity:[{}]", requestEntity);
			logger.info("contentTypeValue:[{}]", contentTypeValue);
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new StringEntity(requestEntity, Constant.ENCODING_UTF8));
			httpPost.addHeader(Constant.CONTENT_TYPE, contentTypeValue);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity, Constant.ENCODING_UTF8);
			logger.info("respnse:[{}]", result);
			EntityUtils.consume(httpEntity);
		} catch (Exception e) {
			logger.error("http 请求异常", e);
			return result;
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("http 请求关闭异常", e);
			}
		}
		return result;
	}

	/**
	 * POST请求 公共方法
	 *
	 * @param url
	 *            URL
	 * @param contentTypeValue
	 *            请求参数
	 * @return 返回结果
	 */
	public static String post(String url, Map<String, String> mapParam, String contentTypeValue) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			logger.info("request(url:" + url + "):" + mapParam.toString());

			if (mapParam != null && mapParam.size() > 0) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : mapParam.entrySet()) {
					pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, Constant.ENCODING_UTF8);
				httpPost.setEntity(entity);
			}

			httpPost.setConfig(requestConfig);
			httpPost.addHeader(Constant.CONTENT_TYPE, contentTypeValue);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity, Constant.ENCODING_UTF8);
			logger.info("respnse:" + result);
			EntityUtils.consume(httpEntity);
		} catch (Exception e) {
			logger.error("http 请求异常", e);
			return result;
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("http 请求关闭异常", e);
			}
		}
		return result;
	}

	public static String postAdvance(String url, Map<String, String> mapParam, String contentTypeValue) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			StringBuilder sb = new StringBuilder(url + "?");

			logger.info("request(url:" + url + "):" + mapParam.toString());

			if (mapParam != null && mapParam.size() > 0)
				for (Map.Entry<String, String> entry : mapParam.entrySet())
					sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");

			sb.deleteCharAt(sb.length() - 1);
			logger.info("url路径是：" + sb.toString());

			HttpPost httpPost = new HttpPost(sb.toString());
			httpPost.setConfig(requestConfig);
			httpPost.addHeader(Constant.CONTENT_TYPE, contentTypeValue);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity, Constant.ENCODING_UTF8);
			logger.info("respnse:" + result);
			EntityUtils.consume(httpEntity);
		} catch (Exception e) {
			logger.error("http 请求异常", e);
			return result;
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("http 请求关闭异常", e);
			}
		}
		return result;
	}

	/**
	 * GET请求 公共方法
	 *
	 * @param url
	 *            URL
	 * @param parameterMap
	 *            请求参数
	 * @return 返回结果
	 */
	public static String get(String url, Map<String, String> parameterMap) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parameterMap != null) {
				for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = entry.getValue();

					// if (StringUtils.isNotEmpty(name)) {
					if (!StringUtils.isEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}

			StringBuilder sb = new StringBuilder(url);
			sb.append(url.contains("?") ? "&" : "?");
			sb.append(EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")));
			HttpGet httpGet = new HttpGet(sb.toString());
			httpGet.setConfig(requestConfig);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity, "utf-8");
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
			logger.error(url + ":", e);
		} catch (IOException e) {
			logger.error(url + ":", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("http 请求关闭异常", e);
			}
		}
		return result;
	}

	/**
	 * GET请求
	 *
	 * @param url
	 * @param requestEntity
	 *            请求参数
	 * @return 返回结果
	 */
	public static String getSn(String url, String requestEntity) {
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			StringBuilder sb = new StringBuilder(url + "?data=" + requestEntity);
			HttpGet httpGet = new HttpGet(sb.toString());
			httpGet.setConfig(requestConfig);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity, "utf-8");
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
			logger.error(url + ":", e);
		} catch (IOException e) {
			logger.error(url + ":", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("http 请求关闭异常", e);
			}
		}
		return result;
	}

}
