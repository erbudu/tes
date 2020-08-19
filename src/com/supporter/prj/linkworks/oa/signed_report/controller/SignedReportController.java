package com.supporter.prj.linkworks.oa.signed_report.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip.account.entity.AccountEntity;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.account.entity.Account;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetin;
import com.supporter.prj.linkworks.oa.signed_report.constants.SignedReportAuthConstant;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReport;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReportContent;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReportMessageBoard;
import com.supporter.prj.linkworks.oa.signed_report.service.SignedReportService;
import com.supporter.prj.linkworks.oa.signed_report.util.ConvertUtils;
import com.supporter.prj.linkworks.oa.signed_report.util.SignedReportAuthUtil;
import com.supporter.spring_mvc.AbstractController;
import com.supporter.util.CommonUtil;

/**
 * @Title: Controller
 * @Description: 功能模块表.
 * @author qiyuanbin
 * 
 */
@Controller
@RequestMapping("oa/signed_report")
public class SignedReportController extends AbstractController {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SignedReportService signedReportService;

	/**
	 * 获取字典数据-用于高级查询页面的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSingnedReportStatusCodetable")
	public Map<Integer, String> getSingnedReportStatusCodetable()throws IOException {
		Map<Integer, String> map = SignedReport.getSingnedReportStatusCodetable();
		return map;
	}
	
	//根据ID获取content
	@RequestMapping("getContetById")
	public @ResponseBody List<SignedReportContent> getContetById(String signedReportId) {
		UserProfile user = this.getUserProfile();
		List<SignedReportContent>  list = signedReportService.getContetById(user,signedReportId);
		return list;
	}
	
	/**
	 * 根据主键获取功能模块表.
	 */
	@RequestMapping("get")
	public @ResponseBody SignedReport get(String signedReportId) {
		SignedReport wmsWarehouse = signedReportService.get(signedReportId);
		return wmsWarehouse;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody SignedReport initEditOrViewPage(String signedReportId, int isAgreement) {
		UserProfile user = this.getUserProfile();
		SignedReport entity = signedReportService.initEditOrViewPage(signedReportId, isAgreement, user);
		return entity;
	}
	
	/**
	 * 获取签报审批意见.
	 * @param signedReportId
	 * @return
	 */
	@RequestMapping("/getExamList")
	@ResponseBody
	public Map < String, List < Map < String, Object > > > getExamList(String signedReportId){
		return signedReportService.getExamList(signedReportId);
	}
	
	/**
	 * 根据taskInstanceId获取examId
	 */
	@RequestMapping("getExamId")
	public @ResponseBody String getExamId(String taskInstanceId) {
		return this.signedReportService.getExamId(taskInstanceId);
	}
	
	/**
	 * 根据附近Id修改附件的二级名称
	 */
	@RequestMapping("updateFileByFileId")
	public @ResponseBody void updateFileByFileId(String fileId,String twoLevelId) {
		this.signedReportService.updateFileByFileId(fileId,twoLevelId);
	}
	/**
	 * 获取上传附件的列表
	 * @param moduleName
	 * @param busiType
	 * @param oneLevelId
	 * @param twoLevelId
	 * @return
	 */
	@RequestMapping("getFileUploadList")
	 public @ResponseBody  List<FileUpload> getFileUploadList(String moduleName, String busiType, String oneLevelId,String twoLevelId){
		return this.signedReportService.getFileUploadList(moduleName,busiType,oneLevelId,twoLevelId);
	}
	

	/**
	 * 分页表格展示数据.
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getTreeGrid(HttpServletRequest request, JqGridReq jqGridReq,String reason, String swfStatus ) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.signedReportService.getGrid(user, jqGrid, reason , swfStatus);
		return jqGrid;
	}

	

	

	/**
	 * 保存或更新数据.
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<SignedReport> saveOrUpdate(SignedReport signedReport) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(SignedReport.class);
		SignedReportAuthUtil.canExecute(user, SignedReportAuthConstant.AUTH_OPER_NAME_SETVALSETVALSIGNREPORT, signedReport.getSignedReportId(), signedReport);
		SignedReport entity = this.signedReportService.saveOrUpdate(user,signedReport, valueMap);
		return OperResult.succeed("saveSuccess", null, entity);
	}
	
	/**
	 * 保存或更新数据(部门经理审批页面修改签报用到).
	 */
	@RequestMapping("saveOrUpdateOfSwf")
	public @ResponseBody OperResult<SignedReport> saveOrUpdateOfSwf(SignedReport signedReport) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(SignedReport.class);
		SignedReport entity = this.signedReportService.saveOrUpdate(user,signedReport, valueMap);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 删除操作
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String signedReportIds) {
		UserProfile user = this.getUserProfile();
		this.signedReportService.delete(user, signedReportIds);
		return OperResult.succeed(ModuleConstant.I18nKey.DELETE_SUCCESS);
	}
	
	/**
	 * 验证是否可以删除
	 * @param signedReportIds
	 * @return
	 */
	@RequestMapping("verificationIsCanDelete")
	public @ResponseBody boolean verificationIsCanDelete(String signedReportId) {
		UserProfile user = this.getUserProfile();
		return this.signedReportService.verificationIsCanDelete(signedReportId, user);
	}
	
	/**
	 * 重置流程状态
	 */
	@RequestMapping("updateStatus")
	public @ResponseBody OperResult<SignedReport> updateStatus(String signedReportId,String swfStatus) {
		Integer swfStatusInt = Integer.parseInt(swfStatus);
		this.signedReportService.updateStatus(signedReportId,swfStatusInt);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}
	
	/**
	 * 上传扫描件/修改otherFiles字段
	 */
	@RequestMapping("uploadFiles")
	public @ResponseBody OperResult<SignedReport> uploadFiles(String signedReportId,String isSweepAttachment) {
		this.signedReportService.uploadFiles(signedReportId,isSweepAttachment);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}

	/**
	 * 判断名字是否重复
	 */
	@RequestMapping("checkNameIsValid")
	public @ResponseBody Boolean checkNameIsValid(String contractId, String contractName) {
		return this.signedReportService.checkNameIsValid(contractId,contractName);
	}
	
	/**
	 * 保存审批中会签人
	 */
	@RequestMapping("saveDeptSigner")
	public @ResponseBody OperResult<SignedReport> saveDeptSigner(String signedReportId,String signedType) {
		UserProfile user = this.getUserProfile();
		this.signedReportService.saveDeptSigner(signedReportId,signedType,user);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}
	
	/**
	 * 保存相关部门会签人
	 */
	@RequestMapping("saveAddDeptSignerNames")
	public @ResponseBody OperResult<SignedReport> saveAddDeptSignerNames(String signedReportId,String addDeptSignerIds,String addDeptSignerNames) {
		SignedReport report=this.signedReportService.get(signedReportId);
		if(report!=null){
				report.setAddDeptSignerIds(addDeptSignerIds);
				report.setAddDeptSignerNames(addDeptSignerNames);
//				//根据会签人保存他们所在的部门
//				for (String deptSignerId : deptSignerIds.split(",")) {
//					Person person = EIPService.getEmpService().getEmp(deptSignerId);
//					if(person==null)continue;
//					Dept dept = person.getDept();
//					if(dept==null)continue;;
//					if(!report.getSignedDeptIds().equals("")&&report.getSignedDeptIds().indexOf(dept.getDeptId()) == -1){						
//						report.setSignedDeptIds(report.getSignedDeptIds() + "," +dept.getDeptId());						
//						report.setSignedDeptNames(report.getSignedDeptNames()+"," +dept.getName());				        
//					}else{
//						report.setSignedDeptIds(dept.getDeptId());						
//						report.setSignedDeptNames(dept.getName());						
//					}
//				}
			this.signedReportService.update(report);
		}		
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}
	
	
	/**
	 * 获取相关部门会签人的部门名称
	 */
	@RequestMapping("getDeptNameByIds")
	public @ResponseBody String getDeptNameByIds(String signedReportId) {
		return this.signedReportService.getDeptNameByIds(signedReportId);
	}
	
	/**
	 * 获取原有部门会签人
	 */
	@RequestMapping("getDeptSigners")
	public @ResponseBody String getDeptSigners(String signedReportId) {
		return this.signedReportService.getDeptSigners(signedReportId);
	}

	
	/**
	 * 获取原有会签部门
	 */
	@RequestMapping("getDeptSignersDeptNames")
	public @ResponseBody String getDeptSignersDeptNames(String signedReportId) {
		return this.signedReportService.getDeptSignersDeptNames(signedReportId);
	}
	
	/**
	 * 根据新选择的部门会签人获取其所在的部门名称
	 */
	@RequestMapping("getDeptNameByAddDeptSignerIds")
	public @ResponseBody String getDeptNameByAddDeptSignerIds(String addDeptSignerIds) {
		return this.signedReportService.getDeptNameByAddDeptSignerIds(addDeptSignerIds);
	}
	
	
	/**
	 * 详情页附件下载
	 */
	@RequestMapping("initFiles")
	public @ResponseBody String initFiles(String signedReportId) {
		return this.signedReportService.getFiles(signedReportId);
	}
	
	/**
	 * 扫描件附件下载
	 */
	@RequestMapping("initScanningCopy")
	public @ResponseBody String initScanningCopy(String signedReportId) {
		return this.signedReportService.initScanningCopy(signedReportId);
	}
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param abroad 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveMessageBoard")
	public @ResponseBody OperResult<SignedReportMessageBoard> saveMessageBoard(String content,String signedReportId) {
		UserProfile user = this.getUserProfile();
		SignedReportMessageBoard entity = this.signedReportService.saveMessageBoard(content, signedReportId, user);
		return OperResult.succeed("submitSuccess", null, entity);
	}
	
	/**
	 * 保存保存拟稿人修改的正文
	 */
	@RequestMapping("saveContentById")
	public @ResponseBody OperResult<SignedReport> saveContentById(String signedReportId,String content) {
		this.signedReportService.saveContentById(signedReportId,content);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}
	

	/**
	 * 秘书处获取新编号和签字时间,取出后直接更新到数据库
	 */
	@RequestMapping("generateNo")
	public @ResponseBody String generateNo(String signedReportId) {
		String signNo = this.signedReportService.generateNo(signedReportId);
		return signNo;
	}
	
	/**
	 * 验证签报号是否可用
	 * @param signNo
	 * @param signedReportId
	 * @return
	 */
	@RequestMapping("verificationNo")
	public @ResponseBody boolean verificationNo(String signNo, String signedReportId) {
		return this.signedReportService.verificationNo(signNo, signedReportId);
	}
	
	/**
	 * 获取留言板
	 */
	@RequestMapping("getMessageBoard")
	public @ResponseBody String getMessageBoard(String signedReportId) {
		return this.signedReportService.getMessageBoard(signedReportId);
	}
	
	/**
	 * 获取留言板
	 */
	@RequestMapping("getMessageBoardForIphone")
	public @ResponseBody String getMessageBoardForIphone(String signedReportId) {
		return this.signedReportService.getMessageBoardForIphone(signedReportId);
	}
	
	/**
	 * 获取秘书角色数据
	 * @param key  
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getSecretariatCodetable")
	public Map<String, String> getSecretariatCodetable() throws IOException {
		Map<String, String> map = ConvertUtils.getByRoleId("MISHUCHUFENGUANMISHU");
		return map;
	}
	
	/**
	 * 保存秘书处的审批信息
	 */
	@RequestMapping("saveSecretariat")
	public @ResponseBody OperResult<SignedReport> saveSecretariat(String signedReportId,String secratryId) {
		UserProfile user = this.getUserProfile();
		this.signedReportService.saveSecretariat(signedReportId,secratryId,user);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}
	
	/**
	 * 保存秘书选择的领导
	 */
	@RequestMapping("saveleaders")
	public @ResponseBody OperResult<SignedReport> saveleaders(String signedReportId,String leadersId,String leadersName) {
		this.signedReportService.saveleaders(signedReportId,leadersId,leadersName);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}
	
	/**
	 * 保存部门主管领导
	 */
	@RequestMapping("saveDeptLeader")
	public @ResponseBody OperResult<SignedReport> saveDeptLeader(String signedReportId) {
		UserProfile user = this.getUserProfile();
		this.signedReportService.saveDeptLeader(signedReportId,user);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}
	
	/**
	 * 保存分管领导会签人
	 */
	@RequestMapping("saveLeadersSigner")
	public @ResponseBody OperResult<SignedReport> saveLeadersSigner(String signedReportId,String leaderSignerNames,String leaderSignerIds) {
		this.signedReportService.saveLeadersSigner(signedReportId,leaderSignerNames,leaderSignerIds);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}
	
	/**
	 * 总裁秘书保存
	 */
	@RequestMapping("savepresidentSecratryAdd")
	public @ResponseBody OperResult<SignedReport> savepresidentSecratryAdd(String signedReportId,String presidentSecratryAddId,String presidentSecratryAdd) {
		this.signedReportService.savepresidentSecratryAdd(signedReportId,presidentSecratryAddId,presidentSecratryAdd);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}
	
	/**
	 * 加签人保存审批附件
	 */
	@RequestMapping("saveExamFile")
	public @ResponseBody OperResult<SignedReport> saveExamFile(String signedReportId) {
		UserProfile user = this.getUserProfile();
		this.signedReportService.saveExamFile(user,signedReportId);
		return OperResult.succeed(ModuleConstant.I18nKey.SAVE_SUCCESS,null,null);
	}
	

	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String signedReportId){
		return signedReportService.extractFiles(signedReportId,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return signedReportService.batchExtractFiles(this.getUserProfile());
	}
	
	/**
	 * 提取历史数据的扫描件
	 * @return
	 */
	@RequestMapping("batchExtractScanningCopy")
	public @ResponseBody String batchExtractScanningCopy(){
		return signedReportService.batchExtractScanningCopy(this.getUserProfile());
	}
	
	
	
	/**
	 * 获取签报正文的落款.
	 * @param signedReportId
	 * @return
	 */
	@RequestMapping("/getInscribe")
	@ResponseBody
	public List < Map < String, Object > > getInscribe(String signedReportId){
		SignedReport signedReport=signedReportService.get(signedReportId);
        if(signedReport==null)return null;  		
		List < Map < String, Object > > maps = new ArrayList < Map < String, Object > >();		
		Map < String, Object > map = new HashMap<String, Object>();
		Person person = EIPService.getEmpService().getEmp(signedReport.getDeptLeaderSigneId());
		List < Account > accounts = EIPService.getAccountService().getAccounts(person);
		if (accounts != null && accounts.size() > 0){
			AccountEntity account = (AccountEntity)accounts.get(0);
			map.put("electronicSignature", account.getElectronicSignature());
		}
		map.put("deptName", signedReport.getDeptName());
		map.put("deptLeaderSigneName", signedReport.getDeptLeaderSigneName(signedReport.getDeptLeaderSigneId()));
		map.put("deptLeaderSigneDate", signedReport.getDeptLeaderSigneDate());
		maps.add(map);

		return maps;
	}
	
	
	/**
	 *验证是不是公司领导（如果是返回“no”）
	 * 
	 * @param deptSignerIds 人员id
	 * @return String (yes/no)
	 */
	@RequestMapping("checkIsLeaders")
	public @ResponseBody String checkIsLeaders(String addDeptSignerIds) {
		String isSatisfied=this.signedReportService.checkIsLeaders(addDeptSignerIds);
		return isSatisfied;
	}
	
	/**
	 * 获取是否佣金代理协议map
	 * @return
	 */
	@RequestMapping("getIsAgreementMap")
	public @ResponseBody Map<Integer, String> getIsAgreementMap() {
		return SignedReport.getIsAgreementMap();
	}
	
	
	/**
	 * 判断当前登录人有没有权限查看上传的扫描件
	 * @return String (yes/no)
	 */
	@RequestMapping("isShowOfScanningCopy")
	public @ResponseBody String isShowOfScanningCopy(String signedReportId) {
		String createdById = CommonUtil.trim(this.get(signedReportId).getCreatedById());
		String isCanOperate=this.signedReportService.isShowOfScanningCopy(this.getUserProfile(),createdById);
		return isCanOperate;
	}
	
	
	/**
	 * 获取部门内最大协议编号集合（不包含当前编号）
	 * @param signedReportId
	 * @param deptId
	 * @return
	 */
	@RequestMapping("getMaxAgreementNos")
	public @ResponseBody Map<String, String> getMaxAgreementNos(String signedReportId, String deptId) {
		return this.signedReportService.getMaxAgreementNos(signedReportId, deptId);
	}
	
}
