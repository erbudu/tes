package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.entity.DevelopProgress;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.service.PrjDevelopProgressService;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.constant.PrePrjManagerFilingConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 功能模块表.
 * @author tiansen
 * 
 */
@Controller
@RequestMapping("pc/prj_develop_progress")
public class PrjDevelopProgressController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	PrjDevelopProgressService progressService;
	
	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param cideId
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	DevelopProgress initEditOrViewPage(String progressId,String filingId) {
		UserProfile user = this.getUserProfile();
		DevelopProgress entity = progressService.initEditOrViewPage(progressId,filingId, user);
		return entity;
	}
	/**
	 * 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			DevelopProgress filing) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.progressService.getGrid(user, jqGrid, filing);
		return jqGrid;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param code
	 *            页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<DevelopProgress> saveOrUpdate(DevelopProgress filing) {
		UserProfile user = this.getUserProfile();
		//权限验证
		DevelopProgress entity = this.progressService.saveOrUpdate(filing,user);
		return OperResult.succeed(PrePrjManagerFilingConstant.I18nKey.SAVE_SUCCESS, "保存成功",
				entity);
	}
	
	/**
	 * 删除操作
	 * 
	 * @param docIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String progressIds) {
		UserProfile user = this.getUserProfile();
		this.progressService.delete(progressIds,user);
		return OperResult.succeed(PrePrjManagerFilingConstant.I18nKey.DELETE_SUCCESS, null,
				null);
	}
	
}