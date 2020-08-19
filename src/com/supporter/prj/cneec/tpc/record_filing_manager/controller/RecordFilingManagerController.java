package com.supporter.prj.cneec.tpc.record_filing_manager.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.record_filing_manager.service.RecordFilingManagerService;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.log.controller.AbstractController;
import com.supporter.util.CommonUtil;
import com.supporter.util.HttpUtil;

@Controller
@RequestMapping("tpc/recordFilingManager")
public class RecordFilingManagerController extends AbstractController {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private RecordFilingManagerService recordFilingManagerService;
	
	/**
	 * 返回列表
	 * 分页表格展示数据
	 * @param request
	 * @param jqGridReq
	 * @param recordFiling
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, RecordFilingManager recordFiling) throws Exception{
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.recordFilingManagerService.getGrid(user, jqGrid, recordFiling);
		return jqGrid;
	}
	
	/**
	 * 初始化加载对象，避免前台编辑页面没有的字段保存后丢失
	 * @param recordFilingId
	 * @param map
	 */
	@ModelAttribute
	public void getSupplier(@RequestParam(value = "recordFilingId", required = false) String recordFilingId, Map<String, Object> map) {
		if (StringUtils.isNotBlank(recordFilingId)) {
			RecordFilingManager recordFiling = this.recordFilingManagerService.get(recordFilingId);
			if (recordFiling != null) {
				map.put("recordFilingManager", recordFiling);
			}
		}
	}

	@RequestMapping("getRecordFilingByProtocolReivewId")
	public @ResponseBody
	RecordFilingManager getRecordFilingByProtocolReivewId(String protocolReviewId) {
		RecordFilingManager recordFiling = this.recordFilingManagerService.getRecordFilingManagerByProtocolReviewId(protocolReviewId);
		return recordFiling;
	}

	@RequestMapping("initRecordFilingByProtocolReivewId")
	public @ResponseBody
	RecordFilingManager initRecordFilingByProtocolReivewId(String protocolReviewId) {
		UserProfile user = this.getUserProfile();
		RecordFilingManager recordFiling = this.recordFilingManagerService.initRecordFilingByProtocolReivewId(protocolReviewId, user);
		return recordFiling;
	}

	/**
	 * 根据主键ID或项目ID初始化对象
	 * @return
	 */
	@RequestMapping("initEditPageByProjectId")
	public @ResponseBody
	RecordFilingManager initEditPageByProjectId() {
		RecordFilingManager recordFilingManager;
		UserProfile user = this.getUserProfile();
		String recordFilingId = CommonUtil.trim(this.getRequestPara("recordFilingId"));
		if (recordFilingId.length() > 0) {
			recordFilingManager = this.recordFilingManagerService.initEditOrViewPage(recordFilingId, user);
		} else {
			String projectId = CommonUtil.trim(this.getRequestPara("projectId"));
			recordFilingManager = this.recordFilingManagerService.initRecordFilingManagerByProjectId(projectId, user);
		}
		return recordFilingManager;
	}

	/**
	 * 进入新建、编辑或查看页面是加载信息
	 * @param recordFilingId
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody RecordFilingManager initEditOrView(String recordFilingId){
		UserProfile user = this.getUserProfile();
		RecordFilingManager recordFiling = this.recordFilingManagerService.initEditOrViewPage(recordFilingId, user);
		// 编辑权限验证
		boolean editOper = Boolean.valueOf(this.getRequestPara("editOper"));
		if (editOper) {
			this.recordFilingManagerService.getAuthCanExecute(user, recordFiling);
		}
		return recordFiling;
	}

	/**
	 * 保存或更新
	 * @param recordFiling
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<RecordFilingManager> saveOrUpdate(RecordFilingManager recordFiling){
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.recordFilingManagerService.getAuthCanExecute(user, recordFiling);
		Map<String, Object> valueMap = this.getPropValues(RecordFilingManager.class);
		RecordFilingManager entity = this.recordFilingManagerService.saveOrUpdate(user, recordFiling, valueMap);
		return OperResult.succeed("saveSuccess", "保存成功", entity);
	}

	/**
	 * 保存提交
	 * @param recordFiling
	 * @return
	 */
	@RequestMapping("commit")
	public @ResponseBody OperResult<RecordFilingManager> commit(RecordFilingManager recordFiling){
		UserProfile user = this.getUserProfile();
		// 权限验证
		//this.recordFilingManagerService.getAuthCanExecute(user, recordFiling);
		Map<String, Object> valueMap = this.getPropValues(RecordFilingManager.class);
		RecordFilingManager entity = this.recordFilingManagerService.commit(user, recordFiling, valueMap);
		return OperResult.succeed("commitSuccess", "提交成功", entity);
	}

	/**
	 * 保存完成（合同签约评审）
	 * @param recordFiling
	 * @return
	 */
	@RequestMapping("complete")
	public @ResponseBody
	OperResult<RecordFilingManager> complete(RecordFilingManager recordFiling) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(RecordFilingManager.class);
		RecordFilingManager entity = this.recordFilingManagerService.complete(user, recordFiling, valueMap);
		return OperResult.succeed("commitSuccess", "提交成功", entity);
	}

	/**
	 * 删除/批量删除
	 * @param recordFilingIds
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String recordFilingIds){
		UserProfile user = this.getUserProfile();
		this.recordFilingManagerService.delete(user, recordFilingIds);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 获取审批状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSwfStatus")
	public Map<Integer,String> getSwfStatus(){
		Map<Integer,String> map = RecordFilingManager.getSwfStatusMap();
		return map;
	}
	
	/**
	 * 获取备案业务类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getRecordFilingTypeData")
	public List<CheckBoxVO> getRecordFilingTypeData(String recordFilingId){
		String relyBy = CommonUtil.trim(this.getRequestPara("relyBy"));
		return this.recordFilingManagerService.getRecordFilingTypeData(recordFilingId, relyBy);
	}
	
	/**
	 * 获取评审单
	 * @param projectId
	 * @param value
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectReview")
	public Map<String,String> selectReview(String projectId, String recordFilingTypeCode, String recordFilingId){
		Map<String,String> map = this.recordFilingManagerService.getReviewMap(projectId, recordFilingTypeCode, recordFilingId);
		return map;
	}
	
	/**
	 * 获取是否用印
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getIsUseSealData")
	public List<CheckBoxVO> getIsUseSealData(String recordFilingId){
		return this.recordFilingManagerService.getIsUseSealData(recordFilingId);
	}
	/**
	 * 获取是否需要盖章
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getIsStampData")
	public List<CheckBoxVO> getIsStampData(String recordFilingId){
		return this.recordFilingManagerService.getIsStampData(recordFilingId);
	}
	/**
	 * 变更了评审单
	 * @param recordFiling
	 * @return
	 */
	@RequestMapping("changeReview")
	public @ResponseBody String changeReview(RecordFilingManager recordFiling){
		String businessNo = this.recordFilingManagerService.changeReview(recordFiling);
		return businessNo;
	}
	
	/**
	 * 生成合同/协议编号
	 * @param recordFiling
	 * @return
	 */
	@RequestMapping("createdBusinessNo")
	public @ResponseBody
	String createdBusinessNo(RecordFilingManager recordFiling) {
		UserProfile user = this.getUserProfile();
		String businessNo = this.recordFilingManagerService.createdBusinessNo(recordFiling);
		recordFiling.setBusinessNo(businessNo);
		Map<String, Object> valueMap = this.getPropValues(RecordFilingManager.class);
		this.recordFilingManagerService.saveOrUpdate(user, recordFiling, valueMap);
		return businessNo;
	}

	/**
	 * 根据合同列表生成备案单(合同签约评审合同)
	 * @return
	 */
	@RequestMapping("initRecordFilingByInforId")
	public @ResponseBody
	RecordFilingManager initRecordFilingByInforId(String inforId) {
		RecordFilingManager recordFiling;
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		if (StringUtils.isNotBlank(inforId)){
			recordFiling = this.recordFilingManagerService.initRecordFilingByInforId(inforId, valueMap, user);
		} else {
			String recordFilingId = (String) valueMap.get("recordFilingId");
			recordFiling = this.recordFilingManagerService.initEditOrViewPage(recordFilingId, user);
		}
		return recordFiling;
	}

	/**
	 * 根据合同列表生成备案单(衍生合同)
	 * @return
	 */
	@RequestMapping("initRecordFilingByDerivativeId")
	public @ResponseBody
	RecordFilingManager initRecordFilingByDerivativeId(String derivativeId) {
		RecordFilingManager recordFiling;
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getRequestParameters();
		if (StringUtils.isNotBlank(derivativeId)){
			recordFiling = this.recordFilingManagerService.initRecordFilingByDerivativeId(derivativeId, valueMap, user);
		} else {
			String recordFilingId = (String) valueMap.get("recordFilingId");
			recordFiling = this.recordFilingManagerService.initEditOrViewPage(recordFilingId, user);
		}
		return recordFiling;
	}

	/**
	 * 衍生合同佣金代理合同需要发送刘佩鑫确认签报
	 * @param recordFiling
	 * @param response
	 */
	@RequestMapping("/sendExamNotifyMsg")
	public void sendExamNotifyMsg(RecordFilingManager recordFiling, HttpServletResponse response) {
		UserProfile user = this.getUserProfile();
		this.recordFilingManagerService.sendExamNotifyMsg(recordFiling, user);
		String json = "{\"success\": true,\"msg\": \"\"}";
		HttpUtil.write(response, json);
	}

	/**
	 * 签报确认
	 * @param recordFiling
	 * @param response
	 */
	@RequestMapping("/completeExam")
	public void completeExam(RecordFilingManager recordFiling, HttpServletResponse response) {
		this.recordFilingManagerService.completeExam(recordFiling);
		String json = "{\"success\": true,\"msg\": \"\"}";
		HttpUtil.write(response, json);
	}

	/**
	 * 签报驳回
	 * @param recordFiling
	 * @param response
	 */
	@RequestMapping("/rejectExam")
	public void rejectExam(RecordFilingManager recordFiling, HttpServletResponse response) {
		this.recordFilingManagerService.rejectExam(recordFiling);
		String json = "{\"success\": true,\"msg\": \"\"}";
		HttpUtil.write(response, json);
	}

}
