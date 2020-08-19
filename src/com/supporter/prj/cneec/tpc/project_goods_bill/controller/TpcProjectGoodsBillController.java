package com.supporter.prj.cneec.tpc.project_goods_bill.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.control.util.JsonParseUtils;
import com.supporter.prj.cneec.tpc.project_goods_bill.entity.ProjectGoodsBillVO;
import com.supporter.prj.cneec.tpc.project_goods_bill.service.ProjectGoodsBillVOService;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

/**
 * @Title: ProjectGoodsBillController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-11-08
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/projectGoodsBill/build")
public class TpcProjectGoodsBillController extends AbstractController {

	private static final long serialVersionUID = 1L;

	private static final String PARA_PAGE = "page";
	private static final String PARA_LIMIT = "rows";
	@Autowired
	private ProjectGoodsBillVOService service;

	/**
	 * 获取客户订单信息JSON字符串
	 * @param jqGridReq
	 * @param response 设置response参数避免返回JSP页面异常
	 */
	@RequestMapping("/getJsonData")
	public void getJsonData(JqGridReq jqGridReq, HttpServletResponse response) {
		UserProfile userProfile = getUserProfile();
		try {
			ListPage<ProjectGoodsBillVO> listPage = this.service.getListPage(
					getRequestParaInt(PARA_PAGE, 0),
					getRequestParaInt(PARA_LIMIT, 20),
					getRequestParameters(),
					userProfile);
			String json = JsonParseUtils.getJsonFromListPage(listPage);
			HttpUtil.write(response, json);
		} catch (Exception e) {
			System.out.println(TpcProjectGoodsBillController.class.getName() + ".getJsonData() Error:" + e.getMessage());
		}
	}

	/**
	 * 
	 * @param request
	 * @param jqGridReq
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "findTreeGrid" })
	public @ResponseBody
	List<String> findTreeGrid() throws Exception {
		UserProfile user = getUserProfile();
		List<String> list = this.service.findTreeGrid(user, this.getRequestParameters());
		return list;
	}

}