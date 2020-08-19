package com.supporter.prj.ppm.poa.icc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;
import com.supporter.prj.ppm.poa.icc.entity.Coordination;
import com.supporter.prj.ppm.poa.icc.entity.CoordinationResult;
import com.supporter.prj.ppm.poa.icc.service.CoordinationResultService;
import com.supporter.prj.ppm.poa.icc.service.CoordinationService;

@Controller
@RequestMapping("/ppm/poa/icc/coordinatioresult")
public class CoordinationResultController extends AbstractController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CoordinationResultService coordinationResultService;
	@Autowired
	private CoordinationService coordinationService;
	/**
	 * 新增数据记录
	 * */
	@RequestMapping("/saveOrUpdate")
	public @ResponseBody
	OperResult<CoordinationResult> saveOrUpdate (CoordinationResult coordinationResult) throws ParseException {
		UserProfile user = this.getUserProfile();
		CoordinationResult entity = this.coordinationResultService.saveOrUpdate(coordinationResult, user);
	    return OperResult.succeed("saveSuccess", null, entity);
		
		
	}
	/**
	 * 初始化结果单据信息
	 * */
	@RequestMapping("/initEditOrViewPage")
	@ResponseBody
	public CoordinationResult initEditOrViewPage(String iccId) throws ParseException {
		UserProfile user = this.getUserProfile();
		CoordinationResult coordinationResult = this.coordinationResultService.getByIccId(iccId, user);
		return coordinationResult;
	}
	/**
	 * 檢查协调单据状态，如果未审批完成则不让填写
	 * 
	 * */
	@RequestMapping("/checkCoordinationStatus")
	@ResponseBody
	public Coordination checkCoordinationStatus(String iccId) throws ParseException {
		UserProfile user = this.getUserProfile();
		Coordination coordination = this.coordinationService.getById(iccId, user);
		return coordination;
	}
	/**
	 * 修改状态并保存，之后将不可修改
	 * 
	 * */
	@RequestMapping("/changeStatus")
	public @ResponseBody OperResult<CoordinationResult> changeStatus(CoordinationResult coordinationResult)
			throws ParseException {
		UserProfile user = this.getUserProfile();
		CoordinationResult entity = this.coordinationResultService.changeStatus(coordinationResult, user);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	@RequestMapping("/confirmNotice")
	public @ResponseBody Coordination confirmNotice(String iccId) {
		if(iccId.isEmpty() ) {return null;}
		
		Coordination coordination = coordinationResultService.confirmNotice(iccId);
		if(coordination == null) {return null;}
		return coordination;
	}
}
