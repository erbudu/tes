package com.supporter.prj.cneec.todo.service;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Service;

import com.supporter.util.CommonUtil;


/**
 * 网报系统接口服务类
 * @author zhuguanglei
 *
 */
@Service
public class NCTodoService {
	
	private Client client = null;
	
//	private String SSOURL = "http://10.2.2.79:8018/ptssologin/PtSsoLoginServlet?";//测试环境;
//	private String clientUrl = "http://10.2.2.79:8018/uapws/service/nc.pub.login.imp.IPortalSsoMsg?wsdl";//测试环境;
	
	private String SSOURL = "http://10.2.2.84:80/ptssologin/PtSsoLoginServlet?";//正式环境;
	private String clientUrl = "http://10.2.2.84:80/uapws/service/nc.pub.login.imp.IPortalSsoMsg?wsdl";//正式环境;
	
	/**
	 * 创建动态客户端
	 * @return
	 */
	private Client getClient() {
		if (client == null) {
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			client = dcf.createClient(clientUrl);
		}
		return client;
	}
	
	/**
	 * 获取单点登录链接
	 * @param userCode
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String getSSOUrl(String userCode) throws NoSuchAlgorithmException {
		String url = SSOURL;
		long timestamp = System.currentTimeMillis();
		String parameter = "usercode=" + userCode + "&timestamp=" + timestamp;
		String authid = toMD5(userCode+timestamp);
		url += parameter + "&authid=" + authid;
		System.out.println("网报系统单点登录url:"+url);
		return url;
	}
	
	/**
	 * MD5加密
	 * @param content
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String toMD5(String content) throws NoSuchAlgorithmException {
        MessageDigest ins = MessageDigest.getInstance("MD5");
        ins.update(content.getBytes(Charset.forName("UTF-8")));
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        byte tmp[] = ins.digest();
        char str[] = new char[16 * 2];
        int k = 0;
        for (int i = 0; i < 16; i++) {
            byte byte0 = tmp[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
	
	/**
	 * 查询用户消息数量
	 * @param userCode
	 */
	public String queryUserMsgCount(String userCode) {
		String count = "";
		Object[] objects = new Object[0];
		try {
			// invoke("方法名",参数1,参数2,参数3....);
            objects = getClient().invoke("queryUserMsgCount", userCode);
            System.out.println(userCode + "查询网报系统消息数量返回数据:" + objects[0]);
            count = objects[0].toString();
		}catch(Exception e) {
			System.out.println(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "查询" + userCode + "网报系统消息数量数量时发生异常：" + e.toString());
		}
		return count;
	}

}
