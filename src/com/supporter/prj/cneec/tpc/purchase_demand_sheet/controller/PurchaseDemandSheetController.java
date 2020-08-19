package com.supporter.prj.cneec.tpc.purchase_demand_sheet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.control.util.JsonParseUtils;
import com.supporter.prj.cneec.tpc.purchase_demand_sheet.entity.PurchaseDemandSheet;
import com.supporter.prj.cneec.tpc.purchase_demand_sheet.service.PurchaseDemandSheetService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

/**
 * @Title: PurchaseDemandSheetController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2018-04-10
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/purchaseDemandSheet")
public class PurchaseDemandSheetController extends AbstractController {

	private static final long serialVersionUID = 1L;

	private static final String PARA_PAGE = "page";
	private static final String PARA_LIMIT = "rows";

	@Autowired
	private PurchaseDemandSheetService purchaseDemandSheetService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param purchaseDemandSheet
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, PurchaseDemandSheet purchaseDemandSheet) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.purchaseDemandSheetService.getGrid(user, jqGrid, purchaseDemandSheet);
		return jqGrid;
	}

	/**
	 * 获取客户信息JSON字符串
	 * @param jqGridReq
	 * @param response 设置response参数避免返回JSP页面异常
	 */
	@RequestMapping("/getJsonData")
	public void getJsonData(JqGridReq jqGridReq, HttpServletResponse response) {
		UserProfile userProfile = getUserProfile();
		try {
			ListPage<PurchaseDemandSheet> listPage = this.purchaseDemandSheetService.getListPage(
					getRequestParaInt(PARA_PAGE, 0), 
					getRequestParaInt(PARA_LIMIT, 20), 
					getRequestParameters(), 
					userProfile);
			HttpUtil.write(response, JsonParseUtils.getJsonFromListPage(listPage));
		} catch (Exception e) {
			System.out.println(PurchaseDemandSheetController.class.getName() + ".getJsonData() Error:" + e.getMessage());
		}
	}

	/**
	 * 判断是否有维护客户权限（默认都有）
	 * @param response
	 */
	@RequestMapping("/checkAuth")
	public void checkAuth(HttpServletResponse response) {
		String json = "{\"authorized\": \"true\",\"msg\": \"\"}";
		HttpUtil.write(response, json);
	}

	/**
	 * 删除操作
	 * 
	 * @param sheetIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String sheetIds) {
		UserProfile user = this.getUserProfile();
		this.purchaseDemandSheetService.delete(user, sheetIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

}