package com.supporter.prj.eip.sso.dao;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.eip.sso.util.SSOConstant;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.maintenance.entity.Maintenance;

/**   
 * @Title: Entity
 * @Description: 功能模块
 * @author tiansen
 * @date 2017-9-8 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class SSODao extends MainDaoSupport < Maintenance, String > {

	public String getAutString(String systemInername, UserProfile userProfile) {

		String accountName = userProfile.getAccountLogin(); //在实际项目中应替换为实际的当前用户账号
		String accountPW = userProfile.getAccountPassword(); //在实际项目中应替换为实际的当前用户账号密码
		String privateKey = getSSOSystemKey(systemInername); //与目标web应用约定的字符串;
		long timeStamp = System.currentTimeMillis() / 100000000; //取当前时间，精确到100秒之内，因为服务器之间的时间可能有误差
		String authStr = encrypt(accountName + ":" + timeStamp, privateKey);
		return authStr;
	}

	/**
	 * 获取SSOkey,单点登录系统约定的字符串来源注册表.
	 * @param systemInername
	 * @return
	 */
	public String getSSOSystemKey(String systemInername) {
		String registerKey = SSOConstant.SSOAppSettingInerName.getSSOKeyCtbl().getDisplay(systemInername);
		String privateKey = EIPService.getRegistryService().get(registerKey);
		return privateKey;
	}

	/**
	 * 获取systemBaseUrl,单点登录系统基本访问地址.
	 * @param systemInername
	 * @return
	 */
	public String getSSOSystemBaseUrl(String systemInername) {
		String registerKey = SSOConstant.SSOAppSettingInerName.getBaseUrlCtbl().getDisplay(systemInername);
		return EIPService.getRegistryService().get(registerKey);
	}

	/**
	 * 加密.
	 * @param str
	 * @param pwd
	 * @return
	 */
	private String encrypt(String str, String pwd) {
		if (pwd == null || pwd.length() <= 0) {
			System.out.println("pwd is empty.");
			return null;
		}

		String prand = "";
		byte[] pwdBytes = pwd.getBytes();
		for (int i = 0; i < pwd.length(); i++) {
			prand += Integer.toString(pwdBytes[i]);
		}

		int sPos = (int) Math.floor(prand.length() / 5);
		long mult = Long.parseLong(prand.charAt(sPos) + "" + prand.charAt(sPos * 2) + prand.charAt(sPos * 3) + prand.charAt(sPos * 4) + prand.charAt(sPos * 5));
		int incr = (int) Math.ceil(pwd.length() / 2.0);
		int modu = (int) (Math.pow(2, 31) - 1);
		if (mult < 2) {
			return null;
		}

		long salt = (long) Math.round(Math.random() * 1000000000) % 100000000;

		prand += salt;
		while (prand.length() > 10) {
			prand = Long.toString((Long.parseLong(prand.substring(0, 10)) + Long.parseLong(prand.substring(10, prand.length()))));
		}
		prand = Long.toString((mult * Long.parseLong(prand) + incr) % modu);

		byte[] strBytes = str.getBytes();

		String enc_str = "";

		long prandLong = Long.parseLong(prand);
		for (int i = 0; i < str.length(); i++) {
			int enc_chr = (int) ((int) strBytes[i] ^ (int) Math.floor(((double) prandLong / modu) * 255));
			if (enc_chr < 16) {
				enc_str += "0" + Integer.toHexString(enc_chr);
			} else {
				enc_str += Integer.toHexString(enc_chr);
			}
			prandLong = (mult * prandLong + incr) % modu;
		}

		String saltStr = Long.toHexString(salt);
		while (saltStr.length() < 8) {
			saltStr = "0" + saltStr;
		}
		enc_str += saltStr;

		return enc_str;
	}
}
