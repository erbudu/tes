package com.supporter.prj.cneec.mail.eyou_interface.service;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
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
public class SSOMailService{
	
	@Autowired
	private MailService mailService;
	/**
	 * 获得单点登录地址
	 * @param IP
	 * @param userName
	 * @param domainName
	 * @param apiKey
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getSSOURL(String IP, String userName, String domainName,
			String apiKey,String secret) {
		StringBuffer ssoURL = new StringBuffer();
		mailService.setApi_secret(secret);
		String token ="";
		try {
			long auth_timestamp = mailService.getTimestamp();
			ssoURL.append("http://"+IP+":80/api/sso/login?");
			ssoURL.append("auth_type=auth");
			ssoURL.append("&auth_key=" + URLEncoder.encode(apiKey));
			ssoURL.append("&auth_timestamp=" + auth_timestamp);
			token = mailService.postToken(IP, userName, domainName, apiKey,auth_timestamp);
			ssoURL.append("&auth_token=" + URLEncoder.encode(token));
			 ssoURL.append("&email="
			 + URLEncoder.encode(mailService.getEmail(userName, domainName)));
			ssoURL.append("&auth_signature="
					+ mailService.getAuth_signature(userName, domainName, apiKey, token,auth_timestamp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ssoURL.toString();
	}
}
