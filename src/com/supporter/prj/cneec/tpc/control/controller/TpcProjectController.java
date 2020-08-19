package com.supporter.prj.cneec.tpc.control.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.control.util.JsonParseUtils;
import com.supporter.prj.cneec.tpc.control.service.TpcProjectService;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.HttpUtil;

/**
 * @Title: TpcProjectController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-09-19
 * @version: V1.0
 */
@Controller("tpcProjectController")
@RequestMapping("tpc/project/build")
public class TpcProjectController extends AbstractController {

	private static final long serialVersionUID = 1L;

	private static final String PARA_PAGE = "page";
	private static final String PARA_LIMIT = "rows";
	@Autowired
	private TpcProjectService projectService;

	/**
	 * 获取项目信息JSON字符串
	 * @param jqGridReq
	 * @param response 设置response参数避免返回JSP页面异常
	 * 	(javax.servlet.ServletException: File &quot;/tpc/project/build/getJsonData.jsp&quot; not found)
	 */
	@RequestMapping("/getJsonData")
	public void getJsonData(JqGridReq jqGridReq, HttpServletResponse response) {
		UserProfile userProfile = getUserProfile();
		try {
			ListPage<RegisterProject> listPage = this.projectService.getListPage(
					getRequestParaInt(PARA_PAGE, 0), 
					getRequestParaInt(PARA_LIMIT, 20), 
					getRequestParameters(), 
					userProfile);
			HttpUtil.write(response, JsonParseUtils.getJsonFromListPage(listPage));
		} catch (Exception e) {
			System.out.println(TpcProjectController.class.getName() + ".getJsonData() Error:" + e.getMessage());
		}
	}

	/**
	 * 判断是否有维护项目权限（默认都有）
	 * @param response
	 */
	@RequestMapping("/checkAuth")
	public void checkAuth(HttpServletResponse response) {
		String json = "{\"authorized\": \"true\",\"msg\": \"\"}";
		HttpUtil.write(response, json);
	}

	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, RegisterProject registerProject) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.projectService.getGrid(user, jqGrid, registerProject);
		return jqGrid;
	}

}
