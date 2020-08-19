package com.supporter.prj.ppm.tool_tips.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.ppm.tool_tips.entity.OperationDesc;
import com.supporter.prj.ppm.tool_tips.entity.ToolTips;
import com.supporter.prj.ppm.tool_tips.service.ToolTipsService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.UUIDHex;


/**
 * @Title: ToolTipsController
 * @Description: 控制器类
 * @author: liyinfeng
 * @date: 2018-07-13
 * @version: V1.0
 */
@Controller
@RequestMapping("ppm/toolTips")
public class ToolTipsController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ToolTipsService toolTipsService;
	
	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param toolTips
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ToolTips toolTips) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.toolTipsService.getGrid(user, jqGrid, toolTips);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param id
	 * @param map
	 */
	@ModelAttribute
	public void getToolTips(String id, Map<String, Object> map) {
		if (!StringUtils.isBlank(id)) {
			ToolTips toolTips = this.toolTipsService.get(id);
			if (toolTips != null) {
				map.put("ToolTips", toolTips);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param id
	 * @return
	 */
	@RequestMapping("get")
//	@AuthCheck(module = ToolTips.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	ToolTips get(String tipsId) {
		ToolTips toolTips = this.toolTipsService.get(tipsId);
		return toolTips;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param id
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
//	@AuthCheck(module = ToolTips.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	ToolTips initEditOrViewPage(String tipsId) {
		UserProfile user = this.getUserProfile();
		ToolTips toolTips = this.toolTipsService.initEditOrViewPage(tipsId, user);
		return toolTips;
	}
	
	/**
	 * 获取提示信息
	 * @param id
	 * @return
	 */
	@RequestMapping("getTextDisplay")
//	@AuthCheck(module = ToolTips.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<?> getTextDisplay(String moduleName, String busiType, String oneLevelId, String oneLevelIdEle) {
		String textDisplay = this.toolTipsService.getTextDisplay(moduleName, busiType, oneLevelId, oneLevelIdEle);
		return OperResult.succeed(textDisplay);
	}
	
	/**
	 * 获取业务操作
	 * @param id
	 * @return
	 */
	@RequestMapping("getOperationDesc")
//	@AuthCheck(module = ToolTips.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<OperationDesc> getOperationDesc(String moduleName, String busiType, String oneLevelId, String oneLevelIdEle) {
		OperationDesc desc = this.toolTipsService.getOperationDesc(moduleName, busiType, oneLevelId, oneLevelIdEle);
		return OperResult.succeed(desc);
	}

	/**
	 * 保存或更新数据.
	 * 保存同时将预算的数据按成本中心，年度自动获取过来
	 * @param toolTips 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
//	@AuthCheck(module = ToolTips.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<ToolTips> saveOrUpdate(ToolTips toolTips) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.toolTipsService.getAuthCanExecute(user, toolTips);
		Map<String, Object> valueMap = this.getPropValues(ToolTips.class);
		ToolTips entity = this.toolTipsService.saveOrUpdate(user, toolTips, valueMap);
		return OperResult.succeed("保存成功", "saveSuccess", entity);
	}
	

	
	/**
	 * 提交数据
	 * @param toolTips
	 * @return
	 */
	@RequestMapping("commit")
//	@AuthCheck(module = ToolTips.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<ToolTips> commit(ToolTips toolTips) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.toolTipsService.getAuthCanExecute(user, toolTips);
		Map<String, Object> valueMap = this.getPropValues(ToolTips.class);
		ToolTips entity = this.toolTipsService.commit(user, toolTips, valueMap);
		return OperResult.succeed("commitSuccess", "提交成功", entity);
	}

	
	/**
	 * 删除操作
	 * 
	 * @param ids 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
//	@AuthCheck(module = ToolTips.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<?> batchDel(String ids) {
		UserProfile user = this.getUserProfile();
		this.toolTipsService.delete(user, ids);
		return OperResult.succeed("deleteSuccess");
	}
	/**
	 * 失效操作
	 * 
	 * @param ids 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("cancel")
//	@AuthCheck(module = ToolTips.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<?> cancel(String ids) {
		UserProfile user = this.getUserProfile();
		this.toolTipsService.cancel(user, ids);
		return OperResult.succeed("deleteSuccess");
	}
	
	
	/**
	 * 验证是否能失效
	 * 
	 * @param demandId 三级需求主表ID
	 * @return
	 */
	@RequestMapping("checkIsRepeatByLabelNoAndModuleId")
//	@AuthCheck(module = ToolTips.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<?> checkIsRepeatByLabelNoAndModuleId(String labelNo, String moduleId, String tipsId) {
		String warning = this.toolTipsService.checkIsRepeatByLabelNoAndModuleId(labelNo, moduleId, tipsId);
		return OperResult.succeed(warning);
	}
	
	
	/**
	 * 获取状态字典数据
	 */
	@RequestMapping(value = "/selectSwfStatusData")
	public @ResponseBody
	Map<Integer, String> selectStatusData() {
		return ToolTips.getStatusMap();
	}

	
	/**
	 * 类型
	 * @return
	 */
	@RequestMapping(value = "/getTypeCodeTables")
	public @ResponseBody
	Map<String, String> getImpleDeptCodeTables() {
		UserProfile user = this.getUserProfile();
		return this.toolTipsService.getTypeCodeTables(user);
	}
	
	
	/**
	 * getId
	 */
	@RequestMapping("getId")
	public @ResponseBody String getId(HttpServletRequest request) {
		return UUIDHex.newId();
	}

}