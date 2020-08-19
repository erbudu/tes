package com.supporter.prj.cneec.mail.eyou_interface.controller;


import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.mail.eyou_interface.service.MailUnReadCountService;
import com.supporter.prj.cneec.mail.eyou_interface.service.SSOMailService;
import com.supporter.spring_mvc.AbstractController;

/**   
 * @Title: Controller
 * @Description: 人力的人员信息.
 * @author tiansen
 * @date 2017-9-08
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("eip/desktop")
public class MailController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private SSOMailService sSOMailService;
	@Autowired
	private MailUnReadCountService mailUnReadCountService;
	
	 private String IP="mail.cneec.com.cn";
     private String apiKey="api@cneec.com.cn";
     private String domainName = "cneec.com.cn";
     private String secret = "api_secret";
	
	/**
	 * 获取单点登录EYOU MAIL地址
	 * @param systemInername
	 * @return
	 */
	@RequestMapping("SSOEyouMail")
	public @ResponseBody void SSOEyouMail(String accountName) {
		PrintWriter out = null;
		String ssoUrl = sSOMailService.getSSOURL(IP, accountName, domainName, apiKey,secret);
		try{
			HttpServletResponse response = this.getResponse();
			  /* 设置格式为text/json    */
            response.setContentType("text/json"); 
            /*设置字符集为'UTF-8'*/
            response.setCharacterEncoding("UTF-8"); 
            out = response.getWriter();
            out.write(ssoUrl);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	/**
	 * 获取未读邮件地址
	 * @param systemInername
	 * @return
	 */
	@RequestMapping("unReadMailCount")
	public @ResponseBody void unReadMailCount(String accountName) {
		PrintWriter out = null;
		String unReadMailCount = mailUnReadCountService.getUnReadMails(IP,apiKey,secret,accountName,domainName);
		try{
			HttpServletResponse response = this.getResponse();
			  /* 设置格式为text/json    */
            response.setContentType("text/json"); 
            /*设置字符集为'UTF-8'*/
            response.setCharacterEncoding("UTF-8"); 
            out = response.getWriter();
            out.write(unReadMailCount);
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	
}
