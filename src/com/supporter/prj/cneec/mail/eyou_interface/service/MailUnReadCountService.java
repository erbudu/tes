package com.supporter.prj.cneec.mail.eyou_interface.service;

import java.io.StringReader;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.util.CommonUtil;

@Service
@Transactional(TransManager.APP)
public class MailUnReadCountService {

	@Autowired
	private MailService mailService ;
	/**
	 * 未读邮件个数。
	 * @param count
	 * @param IP
	 * @param apiKey
	 * @param secret
	 * @param userName
	 * @param domainName
	 * @return
	 */
	public String getUnReadMails(String IP,String apiKey,String secret,String userName,String domainName){
		mailService.setApi_secret(secret);
		String params =mailService.unReadUrlParams(secret, IP, apiKey, userName, domainName);
		String url = mailService.getUnReadURL(IP,userName);
		String y = "";
		String begin = "<entry>";
		String end = "</entry>";
		SAXBuilder reader  = null;
		StringReader xmlString = null;
		try {
			y = mailService.doGetForUnReadMail(url, params, "UTF-8", true);
			if(y.indexOf(begin)!=-1&&y.indexOf(end)!=-1){
				int bl = y.indexOf(begin);
				int el = y.indexOf(end)+end.length();
				y = y.substring(0, bl)+y.substring(el);
			}
			reader = new SAXBuilder();
			xmlString = new StringReader(y);
			InputSource source = new InputSource(xmlString);
			org.jdom.Document build = reader.build(source);
			Element root = build.getRootElement();
			Namespace space = Namespace.getNamespace("opensearch", "http://a9.com/-/spec/opensearch/1.1/");
			String g = root.getChild("totalResults",space).getTextTrim();
			return CommonUtil.trim(g);
		} catch (Exception e) {
			e.printStackTrace();
			return CommonUtil.trim("Error!");
		}finally{
			if(null!=xmlString)xmlString.close();
		}
	}
}
