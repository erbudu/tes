package com.supporter.prj.cneec.tpc.drawback.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.drawback.entity.Drawback;
import com.supporter.prj.cneec.tpc.drawback.service.DrawbackService;
import com.supporter.prj.cneec.tpc.util.OperResultConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: DrawbackController
 * @Description: 控制器类
 * @author: yanweichao
 * @date: 2017-11-20
 * @version: V1.0
 */
@Controller
@RequestMapping("tpc/drawback")
public class DrawbackController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private DrawbackService drawbackService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param drawback
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, Drawback drawback) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.drawbackService.getGrid(user, jqGrid, drawback);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param drawbackId
	 * @param map
	 */
	@ModelAttribute
	public void getDrawback(String drawbackId, Map<String, Object> map) {
		if (!StringUtils.isBlank(drawbackId)) {
			Drawback drawback = this.drawbackService.get(drawbackId);
			if (drawback != null) {
				map.put("drawback", drawback);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param drawbackId
	 * @return
	 */
	@RequestMapping("get")
	public @ResponseBody
	Drawback get(String drawbackId) {
		Drawback drawback = this.drawbackService.get(drawbackId);
		return drawback;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param drawbackId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	Drawback initEditOrViewPage(String drawbackId) {
		UserProfile user = this.getUserProfile();
		Drawback drawback = this.drawbackService.initEditOrViewPage(drawbackId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.drawbackService.getAuthCanExecute(user, drawback);
		}
		return drawback;
	}

	/**
	 * 保存或更新数据.
	 * 
	 * @param drawback 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<Drawback> saveOrUpdate(Drawback drawback) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.drawbackService.getAuthCanExecute(user, drawback);
		Map<String, Object> valueMap = this.getPropValues(Drawback.class);
		Drawback entity = this.drawbackService.saveOrUpdate(user, drawback, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_SAVE_SUCCESS, "保存成功", entity);
	}

	/**
	 * 提交数据
	 * @param drawback
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<Drawback> commit(Drawback drawback) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.drawbackService.getAuthCanExecute(user, drawback);
		Map<String, Object> valueMap = this.getPropValues(Drawback.class);
		Drawback entity = this.drawbackService.commit(user, drawback, valueMap);
		return OperResult.succeed(OperResultConstant.CODE_OPER_COMMIT_SUCCESS, "提交成功", entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param drawbackIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult<?> batchDel(String drawbackIds) {
		UserProfile user = this.getUserProfile();
		this.drawbackService.delete(user, drawbackIds);
		return OperResult.succeed(OperResultConstant.CODE_OPER_DELETE_SUCCESS);
	}

	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectSwfStatusData() {
		return Drawback.getSwfStatusMap();
	}

}