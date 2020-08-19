package com.supporter.prj.cneec.tpc.project_goods_bill.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.project_goods_bill.entity.ProjectGoodsBill;
import com.supporter.prj.cneec.tpc.project_goods_bill.service.ProjectGoodsBillService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: ProjectGoodsBillController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-11-08
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/projectGoodsBill")
public class ProjectGoodsBillController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ProjectGoodsBillService projectGoodsBillService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param projectGoodsBill
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ProjectGoodsBill projectGoodsBill) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.projectGoodsBillService.getGrid(user, jqGrid, projectGoodsBill);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param billId
	 * @param map
	 */
	@ModelAttribute
	public void getProjectGoodsBill(String billId, Map<String, Object> map) {
		if (!StringUtils.isBlank(billId)) {
			ProjectGoodsBill projectGoodsBill = this.projectGoodsBillService.get(billId);
			if (projectGoodsBill != null) {
				map.put("projectGoodsBill", projectGoodsBill);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param billId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	ProjectGoodsBill get(String billId) {
		ProjectGoodsBill projectGoodsBill = this.projectGoodsBillService.get(billId);
		return projectGoodsBill;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param billId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	ProjectGoodsBill initEditOrViewPage(String billId) {
		UserProfile user = this.getUserProfile();
		ProjectGoodsBill projectGoodsBill = this.projectGoodsBillService.initEditOrViewPage(billId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.projectGoodsBillService.getAuthCanExecute(user, projectGoodsBill);
		}
		return projectGoodsBill;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param projectGoodsBill 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<ProjectGoodsBill> saveOrUpdate(ProjectGoodsBill projectGoodsBill) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.projectGoodsBillService.getAuthCanExecute(user, projectGoodsBill);
		Map<String, Object> valueMap = this.getPropValues(ProjectGoodsBill.class);
		ProjectGoodsBill entity = this.projectGoodsBillService.saveOrUpdate(user, projectGoodsBill, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param billIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String billIds) {
		// invalidRecord
		UserProfile user = this.getUserProfile();
		this.projectGoodsBillService.delete(user, billIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectBillStatusData")
	public @ResponseBody
	Map<Integer, String> selectBillStatusData() {
		return ProjectGoodsBill.getBillStatusMap();
	}

}