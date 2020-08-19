package com.supporter.prj.cneec.tpc.navigation.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.external_quotation_review.entity.ExternalQuotationReview;
import com.supporter.prj.cneec.tpc.external_quotation_review.service.ExternalQuotationReviewService;
import com.supporter.prj.cneec.tpc.protocol_review.entity.ProtocolReview;
import com.supporter.prj.cneec.tpc.protocol_review.service.ProtocolReviewService;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemand;
import com.supporter.prj.cneec.tpc.purchase_demand.service.PurchaseDemandService;
import com.supporter.prj.cneec.tpc.record_filing_manager.dao.RecordFilingManagerDao;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.register_project.util.RegisterProjectConstant;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.util.CommonUtil;

@Service
@Transactional(TransManager.APP)
public class TpcNavigationService {

	@Autowired
	private RegisterProjectService projectService;
	@Autowired
	private ProtocolReviewService protocolReviewService;
	@Autowired
	private RecordFilingManagerDao filingManagerDao;
	@Autowired
	private PurchaseDemandService demandService;
	@Autowired
	private ExternalQuotationReviewService quotationReviewService;

	/**
	 * 根据选择的项目项目确认贸易业务作业图
	 * @param projectId
	 * @return
	 */
	public String findTpcProject(String projectId) {
		String json = "{\"result\":\"error\",\"record\":\"\"}";
		if (StringUtils.isNotBlank(projectId)) {
			RegisterProject project = this.projectService.get(projectId);
			Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
			if (project.isHasFrameworkAgreement()) {
				jsonMap.put("hasFrameworkAgreement", true);
				// 有框架协议时判断是否框架协议评审及备案完成
				ProtocolReview review = this.protocolReviewService.getProtocolReviewByProjectId(projectId);
				if (review != null) {
					jsonMap.put("protocolReviewId", review.getReviewId());
					if (review.getSwfStatus() == ProtocolReview.COMPLETED) {
						jsonMap.put("ProtocolReview", "COMPLETED");
						// 框架协议完成
						List<RecordFilingManager> filingManagerList = this.filingManagerDao.findByPropValues(new String[] {"recordFilingTypeCode", "projectId"}, new String[] { TpcConstant.OUTLINE_AGREEMENT, projectId });
						if (filingManagerList != null && filingManagerList.size() > 0) {
							// 判断是否有备案单（不一定用印，但一定会备案，且备案单与框架协议评审单一对一）
							RecordFilingManager filingManager = filingManagerList.get(0);
							if (filingManager.getSwfStatus() == RecordFilingManager.COMPLETED) {
								jsonMap.put("RecordFilingManager", "COMPLETED");
							} else if (review.getSwfStatus() == RecordFilingManager.PROCESSING) {
								// 备案审核中
								jsonMap.put("RecordFilingManager", "COMPLETED");
							} else {
								// 备案草稿
								jsonMap.put("RecordFilingManager", "DRAFT");
							}
						} else {
							jsonMap.put("RecordFilingManager", "NULL");
						}
					} else if (review.getSwfStatus() == ProtocolReview.PROCESSING) {
						// 框架协议审核中
						jsonMap.put("ProtocolReview", "PROCESSING");
					} else {
						// 框架协议草稿
						jsonMap.put("ProtocolReview", "DRAFT");
					}
				} else {
					// 无框架协议
					jsonMap.put("protocolReviewId", "");
					jsonMap.put("ProtocolReview", "NULL");
				}
			} else {
				// 无框架协议时从采购需求单开始
				jsonMap.put("hasFrameworkAgreement", false);
			}
			String jsonData = JSONArray.fromObject(jsonMap).toString();
			// data 中第一个为框架协议、备案信息
			json = "{\"result\":\"success\",\"record\":" + jsonData.substring(1, jsonData.length() - 1) + "}";
		}
		return json;
	}

	/**
	 * 根据选择的采购需求单确认贸易业务作业图
	 * @param projectId
	 * @return
	 */
	public String findTpcPurchaseDemand(String demandId) {
		String json = "{\"result\":\"error\",\"record\":\"\"}";
		if (StringUtils.isNotBlank(demandId)) {
			PurchaseDemand demand = this.demandService.get(demandId);
			RegisterProject project = this.projectService.get(demand.getProjectId());
			Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
			boolean self_support = false;
			// 判断自营贸易还是代理贸易
			if (CommonUtil.trim(project.getProjectClassificationId()).equals(RegisterProjectConstant.SELF_SUPPORT)) {
				// 自营
				jsonMap.put("self_support", true);
				self_support = true;
			} else {
				// 代理
				jsonMap.put("self_support", false);
			}
			jsonMap.put("estimatedAmount", demand.getEstimatedAmount());
			// 判断金额是否需要对外报价评审
			int judgeAmountOk = -1;
			double usdAmount = demand.getUsdAmount();
			if (usdAmount != 0) {
				if ((self_support && usdAmount < 1000000) || (!self_support && usdAmount < 5000000)) {
					judgeAmountOk = 0;
				} else {
					judgeAmountOk = 1;
				}
			}
			jsonMap.put("usdAmount", usdAmount);
			jsonMap.put("judgeAmountOk", judgeAmountOk);
			// 需要对外报价评审时查询是否已经有评审记录
			if (judgeAmountOk > 0) {
				ExternalQuotationReview quotationReview = this.quotationReviewService.getExternalQuotationReviewByDemandId(demandId);
				if (quotationReview != null) {
					jsonMap.put("externalQuotationReviewId", quotationReview.getExternalId());
					if (quotationReview.getSwfStatus() == ExternalQuotationReview.COMPLETED) {
						jsonMap.put("ExternalQuotationReview", "COMPLETED");
					} else if (quotationReview.getSwfStatus() == ExternalQuotationReview.PROCESSING) {
						jsonMap.put("ExternalQuotationReview", "PROCESSING");
					} else {
						jsonMap.put("ExternalQuotationReview", "DRAFT");
					}
				} else {
					// 未创建记录
					jsonMap.put("externalQuotationReviewId", "");
					jsonMap.put("ExternalQuotationReview", "NULL");
				}
			}
			String jsonData = JSONArray.fromObject(jsonMap).toString();
			json = "{\"result\":\"success\",\"record\":" + jsonData.substring(1, jsonData.length() - 1) + "}";
		}
		return json;
	}

}
