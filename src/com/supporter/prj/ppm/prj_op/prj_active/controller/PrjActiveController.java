package com.supporter.prj.ppm.prj_op.prj_active.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_op.prj_active.entity.PrjActive;
import com.supporter.prj.ppm.prj_op.prj_active.service.PrjActiveService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/prj_op/prj_active")
public class PrjActiveController extends AbstractController {

	private static final long serialVersionUID = 1L;
	@Autowired
	private PrjActiveService service;

	/**
	 * 获取项目激活列表
	 * @param jqGrid 列表
	 * @param prjActive 实体对象
	 * @param user 当前登录用户
	 * @return 项目激活列表
	 */
	@RequestMapping("/getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, PrjActive prjActive) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		service.getGrid(jqGrid, prjActive, user);
		return jqGrid;
	}

	/**
	 *  进入新建或编辑或查看页面需要加载的信息.
	 * @param id 主键
	 * @param user 当前登录用户
	 * @return 实体对象
	 */
	@RequestMapping("/initEditOrViewPage")
	@ResponseBody
	public PrjActive initEditOrViewPage(String id, String prjId) {
		UserProfile user = this.getUserProfile();
		PrjActive entity = service.initEditOrViewPage(id, prjId, user);
		return entity;
	}

	/**
	 * 保存或更新
	 * @param prjActive 实体对象
	 * @return 操作结果
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public OperResult<PrjActive> saveOrUpdate(PrjActive prjActive) {
		UserProfile user = this.getUserProfile();
		PrjActive entity = service.saveOrUpdate(prjActive, user);
		return OperResult.succeed("保存成功", "保存成功", entity);
	}

	/**
	 * 删除
	 * @param id 主键
	 * @return	操作结果
	 */
	@RequestMapping("/batchDel")
	@ResponseBody
	public OperResult<?> batchDel(String id) {
		this.service.delete(id);
		return OperResult.succeed("操作成功", "操作成功", null);
	}

	/**
	 * 检查当前项目开发状态
	 * @param prjId 项目id
	 */
	// @RequestMapping("/checkPrjStatus")
	// @ResponseBody
	// public boolean checkPrjStatus(String prjId) {
	// boolean result = this.service.checkPrjStatus(prjId);
	// return result;
	// }

	/**
	 * 检测是否满足新建条件
	 * @return true-可以新建, false-不可以新建
	 */
	@RequestMapping("/checkNewCondition")
	@ResponseBody
	public boolean checkNewCondition(String prjId) {
		boolean result = this.service.checkNewCondition(prjId);
		return result;
	}

	/**
	 * 提交时设置项目激活状态为激活
	 * @param prjId 项目id
	 */
	@RequestMapping("/updatePrjActiveStatus")
	@ResponseBody
	public void updatePrjActiveStatus(String prjId) {
		this.service.updatePrjActiveStatus(prjId);
	}

}
