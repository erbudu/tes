package com.supporter.prj.cneec.tpc.supplier.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supporter.prj.cneec.tpc.control.util.JsonParseUtils;
import com.supporter.prj.cneec.tpc.supplier.entity.Supplier;
import com.supporter.prj.cneec.tpc.supplier.service.SupplierService;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;
import com.supporter.util.HttpUtil;

@Controller
@RequestMapping("tpc/supplier/build")
public class TpcSupplierController extends AbstractController {

	private static final long serialVersionUID = 1L;

	private static final String PARA_PAGE = "page";
	private static final String PARA_LIMIT = "rows";
	@Autowired
	private SupplierService supplierService;
	
	/**
	 * 获取客户信息JSON字符串
	 * @param jqGridReq
	 * @param response 设置response参数避免返回JSP页面异常
	 */
	@RequestMapping("/getJsonData")
	public void getJsonData(JqGridReq jqGridReq, HttpServletResponse response) {
		UserProfile userProfile = getUserProfile();
		try {
			ListPage<Supplier> listPage = this.supplierService.getListPage(
					getRequestParaInt(PARA_PAGE, 0), 
					getRequestParaInt(PARA_LIMIT, 20), 
					getRequestParameters(), 
					userProfile);
			HttpUtil.write(response, JsonParseUtils.getJsonFromListPage(listPage));
		} catch (Exception e) {
			System.out.println(TpcSupplierController.class.getName() + ".getJsonData() Error:" + e.getMessage());
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
