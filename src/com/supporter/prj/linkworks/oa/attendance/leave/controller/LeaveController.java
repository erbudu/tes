/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.leave.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.attendance.leave.entiyt.LeaveEntity;
import com.supporter.prj.linkworks.oa.attendance.leave.service.LeaveService;
import com.supporter.spring_mvc.AbstractController;

/**
 * @author YUEYUN
 * @date 2019年7月15日
 * 
 */
@Controller
@RequestMapping("attendance/leave")
public class LeaveController extends AbstractController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private LeaveService leaveService;

	public JqGrid getGrid() {
		return null;
		
	}
	
	/**
	 * 描述：次方法用于判断当前登陆用户是否可以新建请假单 
	 * 允许新建条件：1.该用户第一次请假2.改用户的所有请假单都已销假
	 * @return true or false
	 */
	@RequestMapping("isCanBuild")
	@ResponseBody
	public boolean isCanBuild() {
		UserProfile user = getUserProfile();
		Boolean result = leaveService.isCanBuild(user);
		return result;
		
	}
	
	
	/**
	 * 判断是否可编辑删除
	 * @param leaveId
	 * @return
	 */
	@RequestMapping("isCanEdit")
	@ResponseBody
	public boolean isCanEdit(String leaveId) {
		UserProfile user = getUserProfile();
		return this.leaveService.isCanEdit(user, leaveId);
		
	}
	
	/**
	 * 改方法用于返获取当前登陆的用户信息，用于填充请假单中的请假单和部门等字段
	 * @return 用户信息
	 */
	@RequestMapping("userInfo")
	@ResponseBody
	public Map<String,String> userInfo() {
		UserProfile user = getUserProfile();
		HashMap<String,String> map = new HashMap<String, String>();
		String leavePersonName = user.getName();
		map.put("leavePersonName", leavePersonName);
		String leavePersonId = user.getPersonId();
		map.put("leavePersonId", leavePersonId);
		Dept dept = user.getDept();
		if(dept != null) {
		String deptName = dept.getName();
		map.put("deptName",deptName);
		String deptId = dept.getDeptId();
		map.put("deptId", deptId);
		}
		
		String level = EIPService.getEmpService().getEmployee(leavePersonId).getEmpLevel();//登陆用户的职位等级
		map.put("empLevel", level);
		return map;
		
	}
	
	/**
	 * 该方法用 于填充请假单数据 根据请假单主键获取请假单的所有数据
	 * @param leaveId 请假单主键
	 * @return 请假单数据模型
	 */
	@RequestMapping("initForm")
	@ResponseBody
	public LeaveEntity initForm(String leaveId) {
		UserProfile user = getUserProfile();
		LeaveEntity entity = leaveService.initForm(leaveId, user);
		return entity;
	}
	
	/**
	 *   描述：此方法为请假单控制层批量删除方法，作用是调用请假单业务层批量删除请假单数据
	 * @param leaveIds
	 * @return
	 */
	@RequestMapping("deleteBatch")
	@ResponseBody
	public OperResult deleteBatch(String leaveId) {
		UserProfile user = getUserProfile();
		leaveService.deleteBatch(user,leaveId);
		return OperResult.succeed(null);
		
	}
	
	/**
	 * 次方放用于获取列表数据
	 * @param request 
	 * @param jqGridReq
	 * @param leaveEntity
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	@ResponseBody 
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, LeaveEntity leaveEntity) throws Exception {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		UserProfile user = this.getUserProfile();
		this.leaveService.getGrid(jqGrid, leaveEntity, user);
		return jqGrid;
	}
	
	/**
	   *   描述：此方法为请假单控制层的保存方法，具体实现调用了请假单业务层的保存更新方法
	 * @param leaveEntity 请假单表单数据
	 * @return 
	 */
	@RequestMapping("saveOrUpdate")
	@ResponseBody
	public OperResult<LeaveEntity> saveOrUpdate(LeaveEntity leaveEntity){
		UserProfile user = getUserProfile();//当前登陆用户信息
		try {
			 leaveEntity = leaveService.saveOrUpdate(user,leaveEntity);
		} catch (Exception e) {
			e.printStackTrace();
			EIPService.getLogService("PURCHASE_DEBUGE").debug("请假单保存失败", e);
		}
		
		return OperResult.succeed("保存成功", null, leaveEntity);
		
	}
	
	/**
	 * 码表数据：用于列表跟多条件查询中请假类型下拉选
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getLeaveType")
	@ResponseBody
	public List<Map<String, String>> getLeaveType() throws Exception {
		List<Map<String, String>> rltList = new ArrayList();
		rltList = leaveService.getSelectOpinion();
		return rltList;
	}

}
