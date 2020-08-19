package com.supporter.prj.cneec.mail.eyou_interface.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.transaction.TransManager;





/**   
 * @Title: Service
 * @Description: EYOU MAIL
 * @author tiansen
 * @date 2017-9-26 10:25:07
 * @version V1.0   
 *
 */
@Service
@Transactional(TransManager.APP)
public class MailService{
	
	public static String api_secret; // 身份识别串对应的密钥，它的值是“api_secret”经过MD5加密后的值；
	/**
	 * 功能描述: 得到系统当前的整数时间戳
	 * 
	 * @return 系统当前的整数时间戳
	 */
	public long getTimestamp() {
		long time = System.currentTimeMillis();
		time /= 1000L;
		return  time;
	}
	/**
	 * 功能描述: 得到签名
	 * 
	 * @param userName
	 *            登录名
	 * @param domainName
	 *            所在的域
	 * @return 签名
	 */
	public String getAuth_signature(String userName, String domainName,
			String apiKey, String token,long auth_timestamp) {
		StringBuffer signature = new StringBuffer();
		signature.append(getApi_secret());
		signature.append(apiKey);
		signature.append(auth_timestamp);
		signature.append(getEmail(userName, domainName));
		signature.append(token);
		// 加token auth=auth时
		return MD5Encrypt.getCrypt(signature.toString()).toLowerCase();
	}
	
	/**
	 * 功能描述: 得到签名
	 * 
	 * @param userName
	 *            登录名
	 * @param domainName
	 *            所在的域
	 * @return 签名
	 */
	private String getAuth_signatureForUnReadMail(String apiKey, String token,long auth_timestamp) {
		StringBuffer signature = new StringBuffer();
		signature.append(getApi_secret());
		signature.append(apiKey);
		signature.append(auth_timestamp);
		signature.append(token);
		// 加token auth=auth时
		return MD5Encrypt.getCrypt(signature.toString()).toLowerCase();
	}
	/**
	 * 功能描述: 得到用户的密钥
	 * 
	 * @return 用户的密钥
	 */
	public String getApi_secret() {
		return api_secret;
	}
	
	/**
	 * 功能描述: 得到用户的密钥
	 * 
	 * @return 用户的密钥
	 */
	public void setApi_secret(String as_secret) {
		this.api_secret = MD5Encrypt.getCrypt(as_secret).toLowerCase();
	}

	/**
	 * 功能描述: 得到用户的邮件地址
	 * 
	 * @param userName
	 *            用户名
	 * @param domainName
	 *            用户所在的域
	 * @return 用户的邮件地址格式的字符串
	 */
	public static String getEmail(String userName, String domainName) {
		StringBuffer email = new StringBuffer();
		email.append(userName);
		email.append("@");
		email.append(domainName);
		return email.toString();
	}
	/**
	 * 获取POST方式获取TOKEN
	 * @param IP
	 * @param userName
	 * @param domainName
	 * @param apiKey
	 * @return
	 * @throws IOException
	 */
	public String postToken(String IP, String userName,
			String domainName, String apiKey,long auth_timestamp) throws IOException {
		String data = "";
			URL dataUrl = new URL("http://" + IP
					+ ":80/api/service/auth/get_token");
			HttpURLConnection con = (HttpURLConnection) dataUrl
					.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("content-type",
					"application/x-www-form-urlencoded");
			OutputStream os = con.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.write(getToken(IP, userName, domainName, apiKey,auth_timestamp).getBytes());
			dos.flush();
			dos.close();
			InputStream is = con.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			byte d[] = new byte[dis.available()];
			dis.read(d);
			data = new String(d);
			con.disconnect();
			os.close();
			dis.close();
			is.close();
		return data;
	}

	public  String getToken(String IP, String userName,
			String domainName, String apiKey,long auth_timestamp) {
		StringBuffer ssoURL = new StringBuffer();
		// 单点登录的地址
		// ssoURL.append("http://"+IP+"/api/service/auth/get_token?");
		ssoURL.append("auth_key=" + URLEncoder.encode(apiKey));
		ssoURL.append("&auth_timestamp=" + auth_timestamp);
		ssoURL.append("&email="
				+ URLEncoder.encode(getEmail(userName, domainName)));
		ssoURL.append("&auth_signature="
				+ getAuth_signatureForToken(userName, domainName, apiKey,auth_timestamp));
		return ssoURL.toString();
	}
	
	/**
	 * 功能描述: 得到签名
	 * 
	 * @param userName
	 *            登录名
	 * @param domainName
	 *            所在的域
	 * @return 签名
	 */
	public String getAuth_signatureForToken(String userName,
			String domainName, String apiKey,long auth_timestamp) {
		StringBuffer signature = new StringBuffer();
		signature.append(getApi_secret());
		signature.append(apiKey);
		signature.append(auth_timestamp);
		return MD5Encrypt.getCrypt(signature.toString()).toLowerCase();
	}

	/**
	 * 获得地址未读邮件访问地址.
	 * @param IP
	 * @return
	 */
	public String getUnReadURL(String IP,String user){
		return "http://"+IP+":80/api/user/" + user + "@cneec.com.cn/mail/-/unread?max-results=1";
	}
	/**
	 * 
	 * @param url
	 * @param params
	 * @param charset
	 * @param pretty
	 * @return
	 * @throws AuthorizationException
	 */
	public  String doGetForUnReadMail(String url,String params, String charset,
			boolean pretty) throws AuthorizationException {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		
		client.getHostConfiguration().setHost("mail.cneec.com.cn", 80, "http");
//		System.out.println("a");
		HttpMethod method = new GetMethod(url);
//		System.out.println("c");
		BufferedReader reader = null;
		try {
			method.setRequestHeader("GET", "mail.cneec.com.cn");
			method.setRequestHeader("Authorization", "auth "+params);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler()); 
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),
								charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty)
						response.append(line).append(
								System.getProperty("line.separator"));
					else
						response.append(line);
				}
			}else{
				throw new AuthorizationException("Authorization required");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
			if(client.getHttpConnectionManager()!=null){
				client.getHttpConnectionManager().closeIdleConnections(0);
			}
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return response.toString();
	}
	
	/**
	 * 获得参数
	 * @param secret
	 * @param IP
	 * @param apiKey
	 * @param userName
	 * @param domainName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String unReadUrlParams(String secret,String IP,String apiKey,String userName,String domainName){
		long auth_timestamp =  getTimestamp();
		StringBuffer ssoURL = new StringBuffer();
		ssoURL.append("auth_key=" + URLEncoder.encode(apiKey));
		ssoURL.append(",auth_timestamp=" + auth_timestamp);
		String token = "";
		try {
			token = this.postToken(IP, userName, domainName, apiKey,auth_timestamp);
			ssoURL.append(",auth_token=" + URLEncoder.encode(token));
			ssoURL.append(",auth_signature="
					+ this.getAuth_signatureForUnReadMail(apiKey, token,auth_timestamp));
		} catch (Exception e) {
			return unReadUrlParams(secret, IP, apiKey, userName, domainName);
		}
		return ssoURL.toString();
	}
	
	public static void main(String args[]) throws Exception {
		URL dataUrl = new URL("http://mail.cneec.com.cn"
				+ ":80/api/service/auth/get_token");
		HttpURLConnection con = (HttpURLConnection) dataUrl
				.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("content-type",
				"application/x-www-form-urlencoded");
		OutputStream os = con.getOutputStream();

	}
}
