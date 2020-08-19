package com.supporter.prj.cneec.tpc.contract_change.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_change.service.ContractChangeService;
import com.supporter.prj.cneec.tpc.control.util.JsonParseUtils;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

/**
 * @Title: TpcContractChangeController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-11-14
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/contractChange/build")
public class TpcContractChangeController extends AbstractController {

	private static final long serialVersionUID = 1L;

	private static final String PARA_PAGE = "page";
	private static final String PARA_LIMIT = "rows";
	@Autowired
	private ContractChangeService service;

	/**
	 * 获取采购合同变更信息JSON字符串
	 * @param jqGridReq
	 * @param response 设置response参数避免返回JSP页面异常
	 */
	@RequestMapping("/getJsonData")
	public void getJsonData(JqGridReq jqGridReq, HttpServletResponse response) {
		UserProfile userProfile = getUserProfile();
		try {
			ListPage<ContractChange> listPage = this.service.getListPage(
					getRequestParaInt(PARA_PAGE, 0),
					getRequestParaInt(PARA_LIMIT, 20),
					getRequestParameters(),
					userProfile);
			String json = JsonParseUtils.getJsonFromListPage(listPage);
			HttpUtil.write(response, json);
		} catch (Exception e) {
			System.out.println(TpcContractChangeController.class.getName() + ".getJsonData() Error:" + e.getMessage());
		}
	}

	/**
	 * 判断是否有维护需求单权限（默认都有）
	 * @param response
	 */
	@RequestMapping("/checkAuth")
	public void checkAuth(HttpServletResponse response) {
		String json = "{\"authorized\": \"true\",\"msg\": \"\"}";
		HttpUtil.write(response, json);
	}

}