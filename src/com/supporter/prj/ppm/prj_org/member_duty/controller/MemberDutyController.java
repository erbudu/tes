package com.supporter.prj.ppm.prj_org.member_duty.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prj_org.member_duty.constant.MemberDutyConstant;
import com.supporter.prj.ppm.prj_org.member_duty.entity.MemberDutyEntity;
import com.supporter.prj.ppm.prj_org.member_duty.service.MemberDutyService;
import com.supporter.spring_mvc.AbstractController;

/**
 * MemberDuty MemberDutyController
 * @author CHENHAO
 * @date 2019年12月2日
 */

@Controller
@RequestMapping("ppm/member_duty/")
public class MemberDutyController extends AbstractController{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MemberDutyService memberDutyService;
	
	/**
	 * 		此方法用于获取项目开发工作组，成员角色的下拉选数据。
	 * @return
	 */
	@RequestMapping( value= {"getMemberRoleOfSelect"})
	public @ResponseBody Map<String ,String> getMemberRoleOfSelect(){
		return MemberDutyConstant.getMemberRoleOfSelect();
	}
	
	/**
	 * 获取业务负责人员页面列表
	 * @param request
	 * @param jqGridReq
	 * @param prjId 项目id
	 * @return
	 */
	
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,String prjId) {
		
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		
		memberDutyService.getGrid(jqGrid,prjId);
		
		return jqGrid;
	}
	
	/**
	 * 保存或修改
	 * @param entity
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<MemberDutyEntity> saveOrUpdate(MemberDutyEntity entity){
		
		UserProfile user = getUserProfile();
		
		return memberDutyService.saveOrUpdate(entity,user);
	}
	
	/**
	 * 删除业务负责人员信息
	 * @param recordId
	 * @return
	 */
	
	@RequestMapping("delMember")
	public @ResponseBody OperResult<MemberDutyEntity> delMember(String recordId) {
		
		 memberDutyService.delMember(recordId);
		 
		 return OperResult.succeed(null);
	}
	
	/**
	 * 初始化添加或查看页面数据
	 * @param recordId
	 * @return
	 */
	@RequestMapping("initPageData")
	public @ResponseBody MemberDutyEntity initPageData(String recordId, String prjId) {
		
		return memberDutyService.initPageData(recordId, prjId);
	}
	
	/**
	 * 获取负责业务下拉列表内容
	 * @return
	 */
	@RequestMapping("getResponsible")
	public @ResponseBody List<Map<String,String>> getSelect() {
		
		return MemberDutyConstant.getResponsible();
	}
	
	/**
	 * 根据部门id返回部门名称
	 * @param deptId	部门编号
	 * @return	部门名称
	 */
	@RequestMapping("getDeptName")
	public @ResponseBody String getDeptName(String deptId) {
		
		return memberDutyService.getDeptName(deptId);
	}
	
	@RequestMapping("isRepeat")
	public @ResponseBody boolean isRepeat(String prjId, String personId, String recordId) {
		
		return memberDutyService.isRepeat(prjId, personId, recordId);
	}
}
