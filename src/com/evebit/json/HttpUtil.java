package com.evebit.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import android.content.Context;
import android.util.Log;


/**
 * Http???閿燂拷?閿燂拷?
 * 
 * @author Administrator
 * 
 */
public class HttpUtil {

	/**
	 * Get鐠囧嚖鎷�????閿熸枻鎷�??閿燂拷?閿燂拷?娑撹鎷�?閿熸枻鎷�??
	 * 
	 * @param context
	 * @param url
	 * @return 閿燂拷?閿燂拷?閿燂拷?
	 * @throws Y_Exception
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String httpGet(Context context, String url) throws Y_Exception {

		try {
			return EntityUtils.toString(httpGetHttpEntity(context, url));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get鐠囧嚖鎷�????閿熸枻鎷�??閿燂拷???閿熸枻鎷�??
	 * 
	 * @param url
	 * @return
	 * @throws Y_Exception
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public static InputStream httpGetInputStream(Context context, String url) throws Y_Exception {

		try {
			return httpGetHttpEntity(context, url).getContent();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HttpEntity httpGetHttpEntity(Context context, String url) throws Y_Exception {
		
		try {

			Log.v("------------------------HTTPUTIL------------", url);
			
			URL urls = new URL(url);
			URI uri = new URI(urls.getProtocol(), urls.getHost(), urls.getPath(), urls.getQuery(), null);
			HttpGet httpget = new HttpGet(uri);

			HttpClient httpclient = new DefaultHttpClient();

			HttpResponse httpResponse;

			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);

			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
						
			httpResponse = httpclient.execute(httpget);
			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				return httpResponse.getEntity();
			} else {
				throw Y_Exception.http(statusCode);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			throw Y_Exception.network(e);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw Y_Exception.network(e);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw Y_Exception.network(e);
		}
	}

	/**
	 * Post鐠囧嚖鎷�????閿熸枻鎷�??閿燂拷?閿燂拷?娑撹鎷�?閿熸枻鎷�??
	 * 
	 * @return
	 * @throws Y_Exception
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String httpPost(Context context, String url, List<NameValuePair> params) throws Y_Exception {

		try {
			return EntityUtils.toString(httpPostHttpEntity(context, url, params));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author hubaolin
	 * @param postUrl
	 * @param params
	 * @return
	 * @throws Y_Exception
	 */

	public static String doPost(String postUrl, List<BasicNameValuePair> params) throws Y_Exception {
		String result = null;
		HttpPost httpRequest = null;
		HttpResponse httpResponse = null;
		try {
			httpRequest = new HttpPost(postUrl);
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpResponse = new DefaultHttpClient().execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { //
				result = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			throw Y_Exception.network(e);
		}
		return result;

	}

	/**
	 * 
	 * @author hubaolin
	 * @throws Y_Exception
	 * @POST ??閿熸枻鎷�??鐠囧嚖鎷�??;
	 * 
	 * @postUrl post鐠囧嚖鎷�?????URL;
	 * 
	 * @params 閿燂拷?鐞涳拷閿燂拷????????
	 */
	public static String doPost(String postUrl, Map<String, String> params) throws Y_Exception {
		String result = null;
		HttpPost httpRequest = null;
		HttpResponse httpResponse = null;
		List<BasicNameValuePair> listPara = null;
		if (null != params) {
			listPara = new ArrayList<BasicNameValuePair>();
			Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> param = iterator.next();
				listPara.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
		}
		try {
			httpRequest = new HttpPost(postUrl);
			httpRequest.setEntity(new UrlEncodedFormEntity(listPara, HTTP.UTF_8));
			httpResponse = new DefaultHttpClient().execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { //
				result = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			throw Y_Exception.network(e);
		}
		return result;

	}

	/**
	 * Post鐠囧嚖鎷�????閿熸枻鎷�??閿燂拷???閿熸枻鎷�??
	 * 
	 * @param context
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public static InputStream httpPostInputStream(Context context, String url, List<NameValuePair> params) throws Y_Exception,
			IllegalStateException, IOException {

		return httpPostHttpEntity(context, url, params).getContent();
	}

	public static HttpEntity httpPostHttpEntity(Context context, String url, List<NameValuePair> params) throws Y_Exception {
		
		HttpPost httppost = new HttpPost(url);		
		try {
			HttpEntity Httpentity = new UrlEncodedFormEntity(params, "utf-8");
			httppost.setEntity(Httpentity);
			HttpClient hc = new DefaultHttpClient();
			
			// 鐠囧嚖鎷�??閿燂拷????
			hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
            // 鐠囦紮鎷�??閿燂拷????
			hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
            

			HttpResponse httpResponse = hc.execute(httppost);

			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return httpResponse.getEntity();
			} else {
				throw Y_Exception.http(statusCode);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			throw Y_Exception.network(e);
		}
	}

	public static String postRequest(final String url
		, final Map<String ,String> rawParams)throws Exception
	{
		FutureTask<String> task = new FutureTask<String>(
		new Callable<String>()
		{
			@Override
			public String call() throws Exception
			{
				// 创建HttpPost对象。
				HttpPost post = new HttpPost(url);
				// 如果传递参数个数比较多的话可以对传递的参数进行封装
				List<NameValuePair> params = 
					new ArrayList<NameValuePair>();
				for(String key : rawParams.keySet())
				{
					//封装请求参数
					params.add(new BasicNameValuePair(key 
						, rawParams.get(key)));
				}
				// 设置请求参数
				post.setEntity(new UrlEncodedFormEntity(
					params, "gbk"));
				HttpClient httpClient = new DefaultHttpClient();
				// 发送POST请求
				HttpResponse httpResponse = httpClient.execute(post);
				// 如果服务器成功地返回响应
				if (httpResponse.getStatusLine()
					.getStatusCode() == 200)
				{
					// 获取服务器响应字符串
					String result = EntityUtils
						.toString(httpResponse.getEntity());
					return result;
				}
				return null;
			}
		});
		new Thread(task).start();
		return task.get();
	}
}
