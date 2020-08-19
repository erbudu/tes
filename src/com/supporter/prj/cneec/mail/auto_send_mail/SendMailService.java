package com.supporter.prj.cneec.mail.auto_send_mail;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.util.CommonUtil;


@Service
@Transactional(TransManager.APP)
public class SendMailService {
	//Mail主机
	private static String MailServerHost = "mail.cneec.com.cn";
	private static String MailServerPort = "25";
	private static String UserName = "xxpt@cneec.com.cn";
	private static String Password = "x4x3p2t1";
	private static String FromAddress = "xxpt@cneec.com.cn";
	private static String MailDomain = "@cneec.com.cn";
	
	/**
	 * 发送邮件
	 * @param as_toAddress 发送邮件帐号不包括“@和@后的信息”
	 * @param as_subject 
	 * @param as_content
	 */
	public static void sendMail(String as_toAddress,String as_subject,String as_content){
		//这个类主要是设置邮件   
	      MailSenderInfo mailInfo = new MailSenderInfo();    
	      mailInfo.setMailServerHost(MailServerHost);    
	      mailInfo.setMailServerPort(MailServerPort);    
	      mailInfo.setValidate(true);    
	      mailInfo.setUserName(UserName);    
	      mailInfo.setPassword(Password);//您的邮箱密码    
	      mailInfo.setFromAddress(FromAddress);    
	      mailInfo.setToAddress(CommonUtil.trim(as_toAddress) + MailDomain);    
	      mailInfo.setSubject(CommonUtil.trim(as_subject));    
	      mailInfo.setContent(CommonUtil.trim(as_content));    
	         //这个类主要来发送邮件   
	      MailSender sms = new MailSender();   
	        //sms.sendTextMail(mailInfo);//发送文体格式    
	        sms.sendHtmlMail(mailInfo);//发送html格式   
	}
}