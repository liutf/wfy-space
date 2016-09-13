package com.lau.core;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Huyi {

	/**
	 * <p>发送GET请求
	 * 
	 * @param  url GET请求地址
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */
	public static byte[] doGet(String url) {

		return Huyi.doGet(url , null , null , 0);
	}

	/**
	 * <p>发送GET请求
	 * 
	 * @param  url       GET请求地址
	 * @param  headerMap GET请求头参数容器
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */
	public static byte[] doGet(String url , Map headerMap) {

		return Huyi.doGet(url , headerMap , null , 0);
	}

	/**
	 * <p>发送GET请求
	 * 
	 * @param  url       GET请求地址
	 * @param  proxyUrl  代理服务器地址
	 * @param  proxyPort 代理服务器端口号
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 * @modify 窦海宁, 2012-03-19
	 */
	public static byte[] doGet(String url , String proxyUrl , int proxyPort) {

		return Huyi.doGet(url , null , proxyUrl , proxyPort);
	}

	/**
	 * <p>发送GET请求
	 * 
	 * @param  url       GET请求地址
	 * @param  headerMap GET请求头参数容器
	 * @param  proxyUrl  代理服务器地址
	 * @param  proxyPort 代理服务器端口号
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 * @modify 窦海宁, 2012-03-19
	 */
	public static byte[] doGet(String url , Map headerMap , String proxyUrl , int proxyPort) {

		byte[]     content    = null;
		HttpClient httpClient = new HttpClient();
		GetMethod  getMethod  = new GetMethod(url);

		if (headerMap != null) {

			//头部请求信息
			if (headerMap != null) {

				Iterator iterator = headerMap.entrySet().iterator();
				while (iterator.hasNext()) {

					Entry entry = (Entry) iterator.next();
					getMethod.addRequestHeader(entry.getKey().toString() , entry.getValue().toString());
				}
			}
		}

		if (StringUtils.isNotBlank(proxyUrl)) {

			httpClient.getHostConfiguration().setProxy(proxyUrl , proxyPort);
		}

		//设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT , 10000);
		//postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER , new DefaultHttpMethodRetryHandler());
		InputStream inputStream = null;
		try {

			if (httpClient.executeMethod(getMethod) == HttpStatus.SC_OK) {

				//读取内容
				inputStream = getMethod.getResponseBodyAsStream();
				content     = IOUtils.toByteArray(inputStream);
			} else {

				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
		} catch (IOException ex) {

			ex.printStackTrace();
		} finally {

			IOUtils.closeQuietly(inputStream);
			getMethod.releaseConnection();
		}
		return content;
	}

	/**
	 * <p>发送POST请求`
	 * 
	 * @param  url          POST请求地址
	 * @param  parameterMap POST请求参数容器
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */
	public static byte[] doPost(String url , Map parameterMap) {

		return Huyi.doPost(url , null , parameterMap , null , null , 0);
	}

	/**
	 * <p>发送POST请求
	 * 
	 * @param  url          POST请求地址
	 * @param  parameterMap POST请求参数容器
	 * @param  paramCharset 参数字符集名称
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 * @modify 窦海宁, 2012-05-21
	 */
	public static byte[] doPost(String url , Map parameterMap , String paramCharset) {

		return Huyi.doPost(url , null , parameterMap , paramCharset , null , 0);
	}

	/**
	 * <p>发送POST请求
	 * 
	 * @param  url          POST请求地址
	 * @param  headerMap    POST请求头参数容器
	 * @param  parameterMap POST请求参数容器
	 * @param  paramCharset 参数字符集名称
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 * @modify 窦海宁, 2012-05-21
	 */
	public static byte[] doPost(String url , Map headerMap , Map parameterMap , String paramCharset) {

		return Huyi.doPost(url , headerMap , parameterMap , paramCharset , null , 0);
	}

	/**
	 * <p>发送POST请求
	 * 
	 * @param  url          POST请求地址
	 * @param  parameterMap POST请求参数容器
	 * @param  paramCharset 参数字符集名称
	 * @param  proxyUrl     代理服务器地址
	 * @param  proxyPort    代理服务器端口号
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */
	public static byte[] doPost(String url , Map parameterMap , String paramCharset , String proxyUrl , int proxyPort) {

		return Huyi.doPost(url , null , parameterMap , paramCharset , proxyUrl , proxyPort);
	}

	/**
	 * <p>发送POST请求
	 * 
	 * @param  url          POST请求地址
	 * @param  headerMap    POST请求头参数容器
	 * @param  parameterMap POST请求参数容器
	 * @param  paramCharset 参数字符集名称
	 * @param  proxyUrl     代理服务器地址
	 * @param  proxyPort    代理服务器端口号
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 * @modify 窦海宁, 2012-05-21
	 */
	public static byte[] doPost(String url , Map headerMap , Map parameterMap , String paramCharset , String proxyUrl , int proxyPort) {

		byte[]     content    = null;
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);

		if (StringUtils.isNotBlank(paramCharset)) {

			postMethod.getParams().setContentCharset(paramCharset);
			postMethod.getParams().setHttpElementCharset(paramCharset);
		}

		if (headerMap != null) {

			//头部请求信息
			if (headerMap != null) {

				Iterator iterator = headerMap.entrySet().iterator();
				while (iterator.hasNext()) {

					Entry entry = (Entry) iterator.next();
					postMethod.addRequestHeader(entry.getKey().toString() , entry.getValue().toString());
				}
			}
		}

		Iterator iterator = parameterMap.keySet().iterator();
		while (iterator.hasNext()) {

			String key = (String) iterator.next();
			postMethod.addParameter(key , (String) parameterMap.get(key));
		}

		if (StringUtils.isNotBlank(proxyUrl)) {

			httpClient.getHostConfiguration().setProxy(proxyUrl , proxyPort);
		}

		//设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT , 10000);
		//postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER , new DefaultHttpMethodRetryHandler());
		InputStream inputStream = null;
		try {

			if (httpClient.executeMethod(postMethod) == HttpStatus.SC_OK) {

				//读取内容
				inputStream = postMethod.getResponseBodyAsStream();
				content     = IOUtils.toByteArray(inputStream);
			} else {

				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
		} catch (IOException ex) {

			ex.printStackTrace();
		} finally {

			IOUtils.closeQuietly(inputStream);
			postMethod.releaseConnection();
		}
		return content;
	}
	
	public static String postMail(String url,String url2,String email) {

		byte[]     content    = null;
		HttpClient httpClient = new HttpClient();
		GetMethod  getMethod  = new GetMethod(url);

		//设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT , 10000);
		//postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER , new DefaultHttpMethodRetryHandler());
		InputStream inputStream = null;
		try {

			if (httpClient.executeMethod(getMethod) == HttpStatus.SC_OK) {
				GetMethod  getMethod2  = new GetMethod(url2);
				if(httpClient.executeMethod(getMethod2) == HttpStatus.SC_OK){
					PostMethod postMethod=new PostMethod(url2);
					postMethod.addParameter("email", email);
					postMethod.addParameter("email2", email);
					postMethod.addParameter("password", "123456");
					httpClient.executeMethod(postMethod);
					inputStream = postMethod.getResponseBodyAsStream();
					content     = IOUtils.toByteArray(inputStream);
				}
				//读取内容
//				inputStream = getMethod.getResponseBodyAsStream();
//				content     = IOUtils.toByteArray(inputStream);
			} else {

				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
			getMethod.releaseConnection();
		}
		return new String(content);
	}
	
	public static String postMail2(String url,String url2,String url3) {

		byte[]     content    = null;
		HttpClient httpClient = new HttpClient();
		GetMethod  getMethod  = new GetMethod(url);

		//设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT , 60000);
		//postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER , new DefaultHttpMethodRetryHandler());
		InputStream inputStream = null;
		try {

			if (httpClient.executeMethod(getMethod) == HttpStatus.SC_OK) {
				
				inputStream = getMethod.getResponseBodyAsStream();
				content     = IOUtils.toByteArray(inputStream);
				
				String content1=new String(content);
				int index=content1.indexOf("<div class=\"div-m-0 text-c\">");
				String tmp=content1.substring(index);
				int index2=tmp.indexOf("</div>");
				String tmp2=tmp.substring(0,index2);
				String emailtmp=tmp2.substring(tmp.indexOf("value=")+7,tmp.indexOf(" />")-1);
				System.out.println(emailtmp);
//				String url="https://workflowy.com/invite/3bf81761.emlx";
//				String url2="https://workflowy.com/accounts/register/";
				postMail(url2, url3, emailtmp);

				Thread.sleep(20000);
				//读取内容
				httpClient.executeMethod(getMethod);
				inputStream = getMethod.getResponseBodyAsStream();
				byte[] contenttmp2     = IOUtils.toByteArray(inputStream);

				String p="https://10minutemail.net/";
				String tmpemail=new String(contenttmp2);
//				System.out.println(tmpemail);
				int indextmp1=tmpemail.indexOf("WorkFlowy&lt;help@workflowy.com&gt;</td><td><a href=\"readmail.html?mid=");
				String tmpem=tmpemail.substring(indextmp1);
				int indextmp2=tmpemail.indexOf("Please verify your email address</a>");
				String tmpem2=tmpemail.substring(indextmp1,indextmp2);
				System.out.println(tmpem2.substring(tmpem2.indexOf("mid="),tmpem2.indexOf("\">")));
				String mid=tmpem2.substring(tmpem2.indexOf("mid="),tmpem2.indexOf("\">"));

				GetMethod  getMethodLast  = new GetMethod(p+"readmail.html?"+mid);
				if(httpClient.executeMethod(getMethodLast) == HttpStatus.SC_OK){
					inputStream = getMethodLast.getResponseBodyAsStream();
					byte []contentlst     = IOUtils.toByteArray(inputStream);
					String lastStr=new String(contentlst);
					int indextmp4=lastStr.indexOf("<div id=\"tab2\" class=\"tab_content\">");
					String tmpem4=lastStr.substring(indextmp4);
					int indextmp5=tmpem4.indexOf("<a href=\"https://workflowy.com/email/verify");
					String tmpem5=tmpem4.substring(indextmp5);
					int indextmp6=tmpem5.indexOf("\"");
					int indextmp7=tmpem5.indexOf("\">");
					String tmpem6=tmpem5.substring(indextmp6+1,indextmp7);
					System.out.println(tmpem6);
					GetMethod  getMethodLastOk  = new GetMethod(tmpem6);
					httpClient.executeMethod(getMethodLastOk);
				}

			} else {

				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
			getMethod.releaseConnection();
		}
		return new String(content);
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		String url="https://workflowy.com/invite/3bf81761.emlx";
		String url2="https://workflowy.com/accounts/register/";
		String email="pic22217@ploae.com";
//		System.out.println(postMail(url, url2, email));
//		byte []con	=doGet("https://10minutemail.net/new.html");
////		System.out.println(new String(con));
//		String content=new String(con);
//		int index=content.indexOf("<div class=\"div-m-0 text-c\">");
//		String tmp=content.substring(index);
//		int index2=tmp.indexOf("</div>");
//		String tmp2=tmp.substring(0,index2);
//		String emailtmp=tmp2.substring(tmp.indexOf("value=")+7,tmp.indexOf(" />")-1);
//		System.out.println(tmp2.substring(tmp.indexOf("value=")+7,tmp.indexOf(" />")-1));
//		byte []con1	=doGet("https://10minutemail.org");
//		System.out.println(new String(con));



//		String content2=new String(con1);
//		  Thread thread=new Thread();
//		  thread.run();
//		  thread.start();
//			  Runnable runnable=new Runnable() {
//				  @Override
//				  public void run() {
//					  for (int i = 0; i <2; i++) {
						String WF_INVIETE_URL="https://workflowy.com/invite/3bf81761.emlx";
					  	String WF_REGISTER_URL="https://workflowy.com/accounts/register/";
					  	postMail2("https://10minutemail.net", WF_INVIETE_URL,WF_REGISTER_URL);
//					  }
//				  }
//			  };

//			  runnable.run();
//		System.out.println("-------------------"+new String(b1));
	}
	
}
