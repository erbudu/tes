package com.supporter.prj.ppm.schedule_status.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.ppm.schedule_status.entity.ScheduleStatus;
import com.supporter.prj.ppm.schedule_status.service.ScheduleStatusService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.UUIDHex;


/**
 * @Title: ScheduleStatusController
 * @Description: 控制器类
 * @author: liyinfeng
 * @date: 2018-07-13
 * @version: V1.0
 */
@Controller
@RequestMapping("ppm/scheduleStatus")
public class ScheduleStatusController extends AbstractController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ScheduleStatusService scheduleStatusService;
	
	/**
	 * 返回列表. 分页表格展示数据.
	 * @param request
	 * @param jqGridReq
	 * @param scheduleStatus
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, ScheduleStatus scheduleStatus) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.scheduleStatusService.getGrid(user, jqGrid, scheduleStatus);
		return jqGrid;
	}

	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param id
	 * @param map
	 */
	@ModelAttribute
	public void getScheduleStatus(String id, Map<String, Object> map) {
		if (!StringUtils.isBlank(id)) {
			ScheduleStatus scheduleStatus = this.scheduleStatusService.get(id);
			if (scheduleStatus != null) {
				map.put("ScheduleStatus", scheduleStatus);
			}
		}
	}

	/**
	 * 根据ID获取功能模块
	 * @param id
	 * @return
	 */
	@RequestMapping("get")
//	@AuthCheck(module = ScheduleStatus.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	ScheduleStatus get(String id) {
		ScheduleStatus scheduleStatus = this.scheduleStatusService.get(id);
		return scheduleStatus;
	}

	/**
	 * 编辑或查看页面加载对象
	 * @param id
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
//	@AuthCheck(module = ScheduleStatus.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	ScheduleStatus initEditOrViewPage(String id) {
		UserProfile user = this.getUserProfile();
		ScheduleStatus scheduleStatus = this.scheduleStatusService.initEditOrViewPage(id, user);
		return scheduleStatus;
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
	OperResult<?> getTextDisplay(String moduleName) {
		UserProfile user = this.getUserProfile();
		String textDisplay = this.scheduleStatusService.getTextDisplay(user, moduleName);
		return OperResult.succeed(textDisplay);
	}

	/**
	 * 保存或更新数据.
	 * 保存同时将预算的数据按成本中心，年度自动获取过来
	 * @param scheduleStatus 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
//	@AuthCheck(module = ScheduleStatus.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<ScheduleStatus> saveOrUpdate(ScheduleStatus scheduleStatus) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.scheduleStatusService.getAuthCanExecute(user, scheduleStatus);
		Map<String, Object> valueMap = this.getPropValues(ScheduleStatus.class);
		ScheduleStatus entity = this.scheduleStatusService.saveOrUpdate(user, scheduleStatus, valueMap);
		return OperResult.succeed("saveSuccess", "保存成功", entity);
	}
	

	
	/**
	 * 提交数据
	 * @param scheduleStatus
	 * @return
	 */
	@RequestMapping("commit")
//	@AuthCheck(module = ScheduleStatus.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<ScheduleStatus> commit(ScheduleStatus scheduleStatus) {
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.scheduleStatusService.getAuthCanExecute(user, scheduleStatus);
		Map<String, Object> valueMap = this.getPropValues(ScheduleStatus.class);
		ScheduleStatus entity = this.scheduleStatusService.commit(user, scheduleStatus, valueMap);
		return OperResult.succeed("commitSuccess", "提交成功", entity);
	}

	
	/**
	 * 删除操作
	 * 
	 * @param ids 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
//	@AuthCheck(module = ScheduleStatus.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<?> batchDel(String ids) {
		UserProfile user = this.getUserProfile();
		//this.scheduleStatusService.delete(user, ids);
		return OperResult.succeed("deleteSuccess");
	}
	/**
	 * 失效操作
	 * 
	 * @param ids 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("cancel")
//	@AuthCheck(module = ScheduleStatus.MODULE_ID, oper = AuthConstant.AUTH_OPER_NAME_SETVAL, 
//	failCode = ExceptionCode.Auth.NOT_EXECUTABLE)
	public @ResponseBody
	OperResult<?> cancel(String ids) {
		UserProfile user = this.getUserProfile();
		//this.scheduleStatusService.cancel(user, ids);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 进度状态码表
	 * @return
	 */
	@RequestMapping(value = "/getScheduleStatusCodeTables")
	public @ResponseBody
	Map<String, String> getScheduleStatusCodeTables() {
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_SCHEDULE_STATUS");
		Map<String, String> map = new HashMap<String, String>();
		for(IComCodeTableItem item : items) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	
	
	
	/**
	 * getId
	 */
	@RequestMapping("getId")
	public @ResponseBody String getId(HttpServletRequest request) {
		return UUIDHex.newId();
	}

}