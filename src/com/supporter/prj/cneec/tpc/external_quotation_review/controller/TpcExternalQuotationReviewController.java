package com.supporter.prj.cneec.tpc.external_quotation_review.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.supporter.prj.cneec.tpc.control.util.JsonParseUtils;
import com.supporter.prj.cneec.tpc.external_quotation_review.entity.ExternalQuotationReview;
import com.supporter.prj.cneec.tpc.external_quotation_review.service.ExternalQuotationReviewService;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

/**
 * @Title: TpcExternalQuotationReviewController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-11-14
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/externalQuotationReview/build")
public class TpcExternalQuotationReviewController extends AbstractController {

	private static final long serialVersionUID = 1L;

	private static final String PARA_PAGE = "page";
	private static final String PARA_LIMIT = "rows";
	@Autowired
	private ExternalQuotationReviewService service;

	/**
	 * 获取对外报价评审信息JSON字符串
	 * @param jqGridReq
	 * @param response 设置response参数避免返回JSP页面异常
	 */
	@RequestMapping("getJsonData")
	public void getJsonData(JqGridReq jqGridReq, HttpServletResponse response) {
		UserProfile userProfile = getUserProfile();
		try {
			ListPage<ExternalQuotationReview> listPage = this.service.getListPage(
					getRequestParaInt(PARA_PAGE, 0),
					getRequestParaInt(PARA_LIMIT, 20),
					getRequestParameters(),
					userProfile);
			String json = JsonParseUtils.getJsonFromListPage(listPage);
			HttpUtil.write(response, json);
		} catch (Exception e) {
			System.out.println(TpcExternalQuotationReviewController.class.getName() + ".getJsonData() Error:" + e.getMessage());
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