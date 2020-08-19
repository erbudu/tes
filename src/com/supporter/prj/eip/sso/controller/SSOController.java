package com.supporter.prj.eip.sso.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.eip.sso.service.SSOService;
import com.supporter.spring_mvc.AbstractController;

/**   
 * @Title: Controller
 * @Description: 系统单点登录.
 * @author tiansen
 * @date 2017-9-08
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/eip/sso")
public class SSOController extends AbstractController {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SSOService ssoService;

	
	/**
	 * 单点登录地址.
	 * @param systemInername
	 * @return
	 */
	@RequestMapping("/getRemotSSOUrl")
	@ResponseBody
	public String getRemotSSOUrl(String systemInername) {
		return this.ssoService.getRemotSSOUrl(systemInername,this.getUserProfile());
	}
	
	
	/**
	 * 返回远程地址.
	 * 
	 * @param systemInername
	 * @param remotUrl
	 * @return
	 */
	@RequestMapping("/getRemotUrl")
	@ResponseBody
	public String getRemotUrl(String systemInername, String remotUrl) {
		return this.ssoService.getRemotUrl(systemInername, remotUrl);
	}

	
}
