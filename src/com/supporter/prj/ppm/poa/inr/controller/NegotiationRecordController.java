package com.supporter.prj.ppm.poa.inr.controller;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;
import com.supporter.prj.ppm.poa.inr.entity.NegotiationRecord;
import com.supporter.prj.ppm.poa.inr.service.NegotiationRecordService;

@Controller
@RequestMapping("/ppm/poa/inr")
public class NegotiationRecordController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private NegotiationRecordService negotiationRecordService;
	/*
	 * 获取列表数据
	 * 
	 * */
	@RequestMapping("/getGrid")
	@ResponseBody
	public JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, NegotiationRecord negotiationRecord ,String prjId) throws Exception { UserProfile user = getUserProfile();
    JqGrid jqGrid = jqGridReq.initJqGrid(request);
    List list = this.negotiationRecordService.getGrid(user, jqGrid, negotiationRecord,prjId);
    System.out.println(list);
    jqGrid.setRows(list);
    jqGrid.setRowCount(list.size());
    
    return jqGrid;
  }
	/**
	 * 初始化页面数据
	 * 
	 * */
	@RequestMapping("/initEditOrViewPage")
	@ResponseBody
	public NegotiationRecord initEditOrViewPage(String negotiationRecordId) throws ParseException{
		UserProfile user =this.getUserProfile();
		NegotiationRecord negotiationRecord =this.negotiationRecordService.getById(negotiationRecordId,user);
    return negotiationRecord;
  } 
	@RequestMapping("/initData")
	@ResponseBody
	public NegotiationRecord initData(String inrId, String prjId) {
		UserProfile user = this.getUserProfile();
		NegotiationRecord entity = negotiationRecordService.initData(inrId, prjId, user);
		return entity;
	}
	
	/**
	 * 点击修改按钮进行修改信息
	 * */
	@RequestMapping("/edit")
	public @ResponseBody
	OperResult<NegotiationRecord> edit (NegotiationRecord negotiationRecord) throws ParseException {
		UserProfile user = this.getUserProfile();
		NegotiationRecord entity = this.negotiationRecordService.edit(negotiationRecord, user);
	    return OperResult.succeed("saveSuccess", null, entity);
		
		
	}
	/**
	 * 新增记录
	 * */
	@RequestMapping("/saveOrUpdate")
	public @ResponseBody
	OperResult<NegotiationRecord> saveOrUpdate (NegotiationRecord negotiationRecord) throws ParseException {
		UserProfile user = this.getUserProfile();
		NegotiationRecord entity = this.negotiationRecordService.saveOrUpdate(negotiationRecord, user);
	    return OperResult.succeed("saveSuccess", null, entity);
		
		
	}
	/**
	 * 点击确认按钮状态变为生效同时进行保存操作
	 * 
	 * **/
	@RequestMapping("/changeStatus")
	public @ResponseBody
	OperResult<NegotiationRecord> changeStatus (NegotiationRecord negotiationRecord) throws ParseException {
		UserProfile user = this.getUserProfile();
		NegotiationRecord entity = this.negotiationRecordService.changeStatus(negotiationRecord, user);
	    return OperResult.succeed("saveSuccess", null, entity);
		
		
	}
	/**
	 * 单项删除
	 * */
	@RequestMapping("/batchDel")
	public @ResponseBody OperResult<NegotiationRecord> batchDel(String negotiationRecordId) {
		UserProfile user = this.getUserProfile();
		this.negotiationRecordService.delete(user, negotiationRecordId);
		return OperResult.succeed("deleteSuccess", null, null);

	}
}
