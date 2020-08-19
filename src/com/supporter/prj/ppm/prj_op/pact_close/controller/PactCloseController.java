package com.supporter.prj.ppm.prj_op.pact_close.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_op.pact_close.entity.PactClose;
import com.supporter.prj.ppm.prj_op.pact_close.service.PactCloseService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/prj_op/pact_close/")
public class PactCloseController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PactCloseService pactCloseService;

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * @param id 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	@ResponseBody
	public PactClose initEditOrViewPage(String id, String prjId) {
		UserProfile user = this.getUserProfile();
		PactClose entity = pactCloseService.initEditOrViewPage(id, prjId, user);
		return entity;
	}

	/**
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return 列表
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, PactClose pactClose) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.pactCloseService.getGrid(user, jqGrid, pactClose);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * @param pactClose 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	@ResponseBody
	public OperResult<?> saveOrUpdate(PactClose pactClose) {
		UserProfile user = this.getUserProfile();
		PactClose entity = this.pactCloseService.saveOrUpdate(user, pactClose);
		return OperResult.succeed("保存成功", "保存成功", entity);
	}

	/**
	 * 删除操作
	 * @param ids 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	@ResponseBody
	public OperResult<?> batchDel(String id) {
		UserProfile user = this.getUserProfile();
		this.pactCloseService.delete(user, id);
		return OperResult.succeed("操作成功", "操作成功", null);
	}

	/**
	 * 提交操作
	 * @param id 主键id
	 * @param reportIds 合同及协议报审id
	 */
	@RequestMapping("valid")
	@ResponseBody
	public void valid(String id, String reportIds) {
		UserProfile user = this.getUserProfile();
		this.pactCloseService.valid(id, reportIds, user);
	}


}
