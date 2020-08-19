package com.supporter.prj.cneec.e2b.banknameandcode.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.e2b.banknameandcode.entity.BankNameAndCode;
import com.supporter.prj.cneec.e2b.banknameandcode.service.BankNameAndCodeService;
import com.supporter.prj.cneec.e2b.util.JsonParseUtils;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

/**
 * @Title: BankNameAndCodeController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2018-11-08
 * @version: V1.0
 */
@Controller
@RequestMapping("e2b/bankNameAndCode")
public class BankNameAndCodeController extends AbstractController {

	private static final long serialVersionUID = 1L;

	private static final String PARA_PAGE = "page";
	private static final String PARA_LIMIT = "rows";

	@Autowired
	private BankNameAndCodeService bankNameAndCodeService;

	/**
	 * 获取客户信息JSON字符串
	 * @param jqGridReq
	 * @param response 设置response参数避免返回JSP页面异常
	 */
	@RequestMapping("/getJsonData")
	public void getJsonData(JqGridReq jqGridReq, HttpServletResponse response) {
		UserProfile userProfile = getUserProfile();
		try {
			ListPage<BankNameAndCode> listPage = this.bankNameAndCodeService.getListPage(
					getRequestParaInt(PARA_PAGE, 0), 
					getRequestParaInt(PARA_LIMIT, 20), 
					getRequestParameters(), 
					userProfile);
			HttpUtil.write(response, JsonParseUtils.getJsonFromListPage(listPage));
		} catch (Exception e) {
			System.out.println(BankNameAndCodeController.class.getName() + ".getJsonData() Error:" + e.getMessage());
		}
	}

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param bankNameAndCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, BankNameAndCode bankNameAndCode) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.bankNameAndCodeService.getGrid(user, jqGrid, bankNameAndCode);
		return jqGrid;
	}

	/**
	 * 装载对象
	 * @param codeId
	 */
	@RequestMapping("get")
	public @ResponseBody
	BankNameAndCode get(String codeId) {
		BankNameAndCode bankNameAndCode = this.bankNameAndCodeService.get(codeId);
		return bankNameAndCode;
	}

}