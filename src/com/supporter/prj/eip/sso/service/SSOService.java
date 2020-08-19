package com.supporter.prj.eip.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.sso.dao.SSODao;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**   
 * @Title: Service
 * @Description: 单点登录
 * @author tiansen
 * @date 2017-9-8
 * @version V1.0   
 *
 */
@Service
public class SSOService {
	
	@Autowired
	private SSODao ssoDao;
	
	/**
	 * 构造远程登录地址.
	 * @param systemInername
	 * @return
	 */
	public String getRemotSSOUrl(String systemInername,UserProfile userProfile) {
		String remotBaseUrl = ssoDao.getSSOSystemBaseUrl(systemInername);
		String auString = ssoDao.getAutString(systemInername, userProfile);
		String remotSSOUrl = remotBaseUrl + "logon/logon_by_sso_params.jsp?a=" + auString;
		return remotSSOUrl;
	}
	
	/**
	 * 构造远程返回地址.
	 * @param systemInername
	 * @param remotUrl
	 * @return
	 */
	public String getRemotUrl(String systemInername, String remotUrl) {
		String remotBaseUrl = ssoDao.getSSOSystemBaseUrl(systemInername);
		remotUrl = remotBaseUrl + remotUrl;
		return remotUrl;
	}

}
