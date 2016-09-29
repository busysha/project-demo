package com.project.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


@SuppressWarnings("deprecation")
public class HttpClientUtil {

	public static final Logger logger = Logger.getLogger(HttpClientUtil.class);

	public static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = "";
		HttpGet httpget = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
		httpget.setConfig(requestConfig);
		logger.info(" http request url : " + url);
		try {
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				logger.info(" http request status : " + response.getStatusLine());
				if (entity != null) {
					responseBody = EntityUtils.toString(entity);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
		} catch (ParseException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		logger.info(" http request response : " + responseBody);
		return responseBody;
	}
	
	public static String getWithHeaders(String url,Map<String,String> headers) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = "";
		HttpGet httpget = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
		httpget.setConfig(requestConfig);
		logger.info(" http request url : " + url);
		try {
			for (Entry<String, String> entry : headers.entrySet()) {
				httpget.addHeader(entry.getKey(), entry.getValue());
			}
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				logger.info(" http request status : " + response.getStatusLine());
				if (entity != null) {
					responseBody = EntityUtils.toString(entity);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
		} catch (ParseException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		logger.info(" http request response : " + responseBody);
		return responseBody;
	}

	public static String post(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = "";
		HttpPost httppost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
		httppost.setConfig(requestConfig);
		logger.info(" http request url : " + url);
		try {
			CloseableHttpResponse response = httpclient.execute(httppost);
			logger.info(" http request status : " + response.getStatusLine());
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseBody = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		logger.info(" http request response : " + responseBody);
		return responseBody;
	}
	
	public static String postWithHeaders(String url,Map<String,String> headers) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = "";
		HttpPost httppost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
		httppost.setConfig(requestConfig);
		logger.info(" http request url : " + url);
		try {
			for (Entry<String, String> entry : headers.entrySet()) {
				httppost.addHeader(entry.getKey(), entry.getValue());
			}
			CloseableHttpResponse response = httpclient.execute(httppost);
			logger.info(" http request status : " + response.getStatusLine());
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseBody = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		logger.info(" http request response : " + responseBody);
		return responseBody;
	}


	public static String postWithParams(String url, List<NameValuePair> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = "";
		HttpPost httppost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
		httppost.setConfig(requestConfig);
		logger.info(" http request url : " + url);
		try {
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			logger.info(" http request status : " + response.getStatusLine());
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseBody = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		logger.info(" http request response : " + responseBody);
		return responseBody;
	}
	
	public static String postWithHeadersAndParams(String url,Map<String,String> headers,List<NameValuePair> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = "";
		HttpPost httppost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
		httppost.setConfig(requestConfig);
		logger.info(" http request url : " + url);
		try {
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, "UTF-8");
			for (Entry<String, String> entry : headers.entrySet()) {
				httppost.addHeader(entry.getKey(), entry.getValue());
			}
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			logger.info(" http request status : " + response.getStatusLine());
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseBody = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		logger.info(" http request response : " + responseBody);
		return responseBody;
	}
	
	public static String postWithHeadersAndJsonBody(String url,Map<String,String> headers,String jsonBody){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String responseBody = "";
		HttpPost httppost = new HttpPost(url);
		//HttpPut httppost = new HttpPut(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
		httppost.setConfig(requestConfig);
		logger.info(" http request url : " + url);
		try {
			StringEntity jsonEntity = new StringEntity(jsonBody,"UTF-8");
			if(null != headers){
				for (Entry<String, String> entry : headers.entrySet()) {
					httppost.addHeader(entry.getKey(), entry.getValue());
				}
			}
			httppost.setEntity(jsonEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			logger.info(" http request status : " + response.getStatusLine());
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseBody = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		logger.info(" http request response : " + responseBody);
		return responseBody;
	}

	/*
	 * 
	 * @param url请求地址
	 * @param requestData 请求数据
	 * @param certUrl 证书地址
	 * @param certPassword 证书密码，默认商户号
	 * @return
	 */
	/*
	public static String wechatPay(String url,Object requestData,String certUrl,String certPassword){
		KeyStore keyStore = null;
		FileInputStream instream = null;
        try {
        	keyStore = KeyStore.getInstance("PKCS12");
        	instream = new FileInputStream(new File(certUrl));//加载本地的证书进行https加密传输
            keyStore.load(instream, certPassword.toCharArray());//设置证书密码
        } catch (Exception e) {
        	logger.error(e);
        } finally {
            try {
				instream.close();
			} catch (IOException e) {
				logger.error(e);
			}
        }
        // Trust own CA and all self-signed certs
		SSLContext sslcontext = null;
		try {
			sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certPassword.toCharArray()).build();
		} catch (Exception e) {
			logger.error(e);
		}
        // Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,new String[]{"TLSv1"},null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        //根据默认超时限制初始化requestConfig
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
        String responseBody = "";
        HttpPost httpPost = new HttpPost(url);
        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(requestData);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        //设置请求器的配置
        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
        	logger.error(e);
        } finally {
            httpPost.abort();
        }
        logger.info(" http request response : " + responseBody);
        return responseBody;
	}
	*/
}
