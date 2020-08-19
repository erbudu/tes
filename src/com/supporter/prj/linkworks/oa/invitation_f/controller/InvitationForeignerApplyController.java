package com.supporter.prj.linkworks.oa.invitation_f.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.invitation_f.constants.InvitationAuthConstant;
import com.supporter.prj.linkworks.oa.invitation_f.entity.InvitationForeignerApply;
import com.supporter.prj.linkworks.oa.invitation_f.service.InvitationFExcelService;
import com.supporter.prj.linkworks.oa.invitation_f.service.InvitationForeignerApplyService;
import com.supporter.prj.linkworks.oa.invitation_f.util.AuthUtils;
import com.supporter.prj.linkworks.oa.invitation_f.util.InvitationConstant;
import com.supporter.spring_mvc.AbstractController;

/**
 * @Title: Controller
 * @Description: 物资信息设置.
 * @author yanbingchao
 * @date 2017-3-27 15:25:34
 * @version V1.0
 * 
 */

@Controller
@RequestMapping("oa/invitation_f")
public class InvitationForeignerApplyController extends AbstractController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private InvitationForeignerApplyService codeService;
	
	@Autowired
	private InvitationFExcelService excelService;

	/**
	 * 根据主键获取.
	 * 
	 * @param invitationId
	 *            主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	InvitationForeignerApply get(String invitationId) {
		InvitationForeignerApply code = codeService.get(invitationId);
		return code;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param cideId
	 *            主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	InvitationForeignerApply initEditOrViewPage(String invitationId,String docClassify) {
		UserProfile user = this.getUserProfile();
		InvitationForeignerApply entity = codeService.initEditOrViewPage(invitationId,docClassify, user);
		return entity;
	}
	
	@RequestMapping("viewPage")
	@ResponseBody
	public InvitationForeignerApply viewPage(String invitationId) {
		InvitationForeignerApply entity = codeService.viewPage(invitationId, this.getUserProfile());
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
	JqGrid getTreeGrid(HttpServletRequest request, JqGridReq jqGridReq,
			InvitationForeignerApply code) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.codeService.getGrid(user, jqGrid, code);
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
	OperResult<InvitationForeignerApply> saveOrUpdate(InvitationForeignerApply code) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(InvitationForeignerApply.class);
		AuthUtils.canExecute(user, InvitationAuthConstant.AUTH_OPER_NAME_SETVALINVITATION, code.getInvitationId(), code);
		InvitationForeignerApply entity = this.codeService.saveOrUpdate(user, code, valueMap);
		return OperResult.succeed(InvitationConstant.I18nKey.SAVE_SUCCESS, "保存成功",
				entity);
	}
	@RequestMapping("commit")
	public @ResponseBody
	OperResult<InvitationForeignerApply> commit(InvitationForeignerApply code) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(InvitationForeignerApply.class);
		code.setInvitationStatus(1);
		InvitationForeignerApply entity = this.codeService.saveOrUpdate(user, code, valueMap);
		return OperResult.succeed(InvitationConstant.I18nKey.SAVE_SUCCESS, "保存成功",
				entity);
	}
	/**
	 * 删除操作
	 * 
	 * @param invitationIds
	 *            主键集合，多个以逗号分隔
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String invitationIds) {
		UserProfile user = this.getUserProfile();
		this.codeService.delete(user, invitationIds);
		return OperResult.succeed(InvitationConstant.I18nKey.DELETE_SUCCESS, null,
				null);
	}
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String invitationId){
		return codeService.extractFiles(invitationId,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return codeService.batchExtractFiles(this.getUserProfile());
	}
	/**
	 * 判断名字是否重复
	 * 
	 * @param invitationId
	 * @param materialName
	 * @return
	 */
	@RequestMapping("checkNameIsValid")
	public @ResponseBody
	Boolean checkNameIsValid(String invitationId, String materialName) {
		return this.codeService.checkNameIsValid(invitationId, materialName);
	}

	@RequestMapping("getOutDocStatus")
	public @ResponseBody
	Map<String, String> getOutDocStatus() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		/*
		 * List <IComCodeTableItem> list =
		 * EIPService.getComCodeTableService().getCodeTableItems("UNIT");
		 * for(IComCodeTableItem item : list){ map.put(item.getItemId(),
		 * item.getDisplayName()); }
		 */
		map.put("0", "未提交");
		map.put("1", "审批进行中");
		map.put("2", "审批完成");
		return map;
	}

	/**
	 * 生成excel文档
	 * @param invitationId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("downloadExcelFile")
	public void downloadExcelFile(HttpServletResponse response, String invitationId) throws Exception{
		try{
			UserProfile user = this.getUserProfile();
			System.out.println("downloadPDF ...");
			this.excelService.downloadExcelFile(response ,invitationId, user);
			System.out.println("downloadPDF end.");
		}catch (Exception e){
			System.err.println(getClass().getName() + " downloadPDF ERROR: " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("downloadPDF method end.");
	}
	
	/**
	 * 重新生成excel
	 * @param response
	 * @param invitationId
	 * @return
	 */
	@RequestMapping({"reCreateExcelFile"})
	public @ResponseBody OperResult<?> reCreateExcelFile(HttpServletResponse response, String invitationId) {
	    boolean haveFile = false;
	    try{
	    	UserProfile user = this.getUserProfile();
	    	System.out.println("reCreatedPDF ...");
	    	haveFile = this.excelService.reCreateExcelFile(invitationId, user);
	    	System.out.println("reCreatedPDF end.");
	    }catch (Exception e){
	    	System.err.println(getClass().getName() + " reCreatedPDF ERROR: " + e.getMessage());
	    	e.printStackTrace();
	    }
	    System.out.println("reCreatedPDF method end.");
	    String result = haveFile ? "操作成功" : "操作失败";
	    return OperResult.succeed(result, haveFile ? "SUCCESS" : "FAIL", null);
	}

}
