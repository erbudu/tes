package com.supporter.prj.ppm.prj_op.prj_close.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_op.prj_close.entity.PrjClose;
import com.supporter.prj.ppm.prj_op.prj_close.service.PrjCloseService;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("ppm/prj_op/prj_close")
public class PrjCloseController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PrjCloseService service;

	/**
	 * 获取项目激活列表
	 * @param jqGrid 列表
	 * @param PrjClose 实体对象
	 * @param user 当前登录用户
	 * @return 项目激活列表
	 */
	@RequestMapping("/getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, PrjClose prjClose) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		service.getGrid(jqGrid, prjClose, user);
		return jqGrid;
	}

	/**
	 *  进入新建或编辑或查看页面需要加载的信息.
	 * @param id 主键
	 * @param prjId 项目id
	 * @return 实体对象
	 */
	@RequestMapping("/initEditOrViewPage")
	@ResponseBody
	public PrjClose initEditOrViewPage(String id, String prjId) {
		UserProfile user = this.getUserProfile();
		PrjClose entity = service.initEditOrViewPage(id, prjId, user);
		return entity;
	}

	/**
	 * 保存或更新
	 * @param PrjClose 实体对象
	 * @return 操作结果
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public OperResult<PrjClose> saveOrUpdate(PrjClose prjClose) {
		UserProfile user = this.getUserProfile();
		PrjClose entity = service.saveOrUpdate(prjClose, user);
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
	 * 检查当前项目当前激活状态
	 * @param prjId 项目id
	 * @return 检查结果 0-关闭状态,1-激活状态
	 */
	@RequestMapping("/checkPrjStatus")
	@ResponseBody
	public int checkPrjStatus(String prjId) {
		int result = this.service.checkPrjStatus(prjId);
		return result;
	}

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
	 * 提交时设置项目激活状态为关闭
	 * @param prjId 项目id
	 */
	@RequestMapping("/updatePrjActiveStatus")
	@ResponseBody
	public void updatePrjActiveStatus(String prjId) {
		this.service.updatePrjActiveStatus(prjId);
	}

	/**
	 * 检查项目下是否存在生效的合同
	 * @param prjId 项目id
	 * @returns 操作结果 true-有，false-没有
	 */
	@RequestMapping("/checkExistEffectPact")
	@ResponseBody
	public boolean checkExistEffectPact(String prjId) {
		boolean result = this.service.checkExistEffectPact(prjId);
		return result;
	}
}
