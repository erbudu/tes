package com.supporter.prj.cneec.tpc.trade_homepage.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity.ExternalQuotationReviewDept;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.service.ExternalQuotationReviewDeptService;
import com.supporter.prj.cneec.tpc.protocol_review.entity.ProtocolReview;
import com.supporter.prj.cneec.tpc.protocol_review.service.ProtocolReviewService;
import com.supporter.prj.cneec.tpc.purchase_demand.util.PurchaseDemandConstant;
import com.supporter.prj.cneec.tpc.record_filing_manager.dao.RecordFilingManagerDao;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.record_filing_manager.service.RecordFilingManagerService;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.register_project.util.RegisterProjectConstant;
import com.supporter.prj.cneec.tpc.trade_homepage.constant.HomepageTypeEnum;
import com.supporter.prj.cneec.tpc.trade_homepage.constant.TradeHomepageConstant;
import com.supporter.prj.cneec.tpc.trade_homepage.dao.TradeHomepageDao;
import com.supporter.prj.cneec.tpc.trade_homepage.entity.TradeHomepage;
import com.supporter.prj.cneec.tpc.trade_homepage.util.TradeHomepageUtil;
import com.supporter.prj.cneec.tpc.use_seal.entity.UseSeal;
import com.supporter.prj.cneec.tpc.use_seal.service.UseSealService;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.log4j.XLogger;

@Service
@Transactional(TransManager.APP)
public class TradeHomepageService {

	@Autowired
	private TradeHomepageDao tradeHomepageDao;
	@Autowired
	private RegisterProjectService projectService;
	@Autowired
	private ProtocolReviewService protocolReviewService;
	@Autowired
	private UseSealService useSealService;
	@Autowired
	private RecordFilingManagerService filingManagerService;
	@Autowired
	private RecordFilingManagerDao filingManagerDao;
	@Autowired
	private ExternalQuotationReviewDeptService quotationReviewDeptService;

	public void clearCache() {
		String[] cacheKeys = new String[] {
				//"KEYHOMEPAGE_TRADE",
				"KEYHOMEPAGE_TRADE_PROTOCOL",
				"KEYHOMEPAGE_TRADE_INTENTION_PERFORMANCE",
				"KEYHOMEPAGE_TRADE_CONTRACT_EXECUTION",
				"KEYHOMEPAGE"
		};
		for (String cacheKey : cacheKeys) {
			if (EIPService.getCacheService().keyExists(cacheKey)) {
				System.out.println("存在并清除缓存：" + cacheKey);
				EIPService.getCacheService().remove(cacheKey);
			} else {
				System.out.println("不存在缓存：" + cacheKey);
			}
		}
	}

	/**
	 * 获取有效一级菜单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TradeHomepage> getActivedOneLevel(String otherParams) {
		JSONObject jsonObject = JSONObject.fromObject(otherParams);// 字符串转json对象
		int homepageType = jsonObject.getInt("homepageType");
		//clearCache();
		String cacheKey = "KEYHOMEPAGE_TRADE";
		if (EIPService.getCacheService().keyExists(cacheKey)) {
			System.out.println("getActivedOneLevel存在cacheKey:"+cacheKey);
			return (ArrayList<TradeHomepage>) EIPService.getCacheService().get(cacheKey);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", null);
		params.put("menuLevel", TradeHomepageConstant.MENU_LEVEL_ONE);
		params.put("isActive", Boolean.valueOf(true));
		params.put("homepageType", new Integer[] { homepageType, HomepageTypeEnum.ALL.getAttrInnerName() });

		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("displayOrder", Boolean.valueOf(true));
		List<TradeHomepage> tradeHomepageList = this.tradeHomepageDao.queryByParam(params, orders);
//		for (TradeHomepage t : tradeHomepageList) {
//			System.out.println(t.getDisplayName() +" "+t.getHomepageType());
//		}
		if (tradeHomepageList.size() == 0) {
			// 初始化所有菜单
			initDefaultTradeHomepage();
			// 重新获取一级菜单
			tradeHomepageList = this.tradeHomepageDao.queryByParam(params, orders);
		}
		//EIPService.getCacheService().put(cacheKey, (java.io.Serializable) tradeHomepageList);
		return tradeHomepageList;
	}

	/**
	 * 获取所有一级菜单
	 * 
	 * @return
	 */
	public List<TradeHomepage> getAllOneLevels(String otherParams) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", null);
		params.put("menuLevel", TradeHomepageConstant.MENU_LEVEL_ONE);
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("displayOrder", Boolean.valueOf(true));
		List<TradeHomepage> tradeHomepageList = this.tradeHomepageDao.queryByParam(params, orders);
		if (tradeHomepageList.size() == 0) {
			// 初始化所有菜单
			initDefaultTradeHomepage();
			// 重新获取一级菜单
			tradeHomepageList = this.tradeHomepageDao.queryByParam(params, orders);
		}
		return tradeHomepageList;
	}

	/**
	 * 获取有效二级菜单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TradeHomepage> getActivedTwoLevel(String parentId, String otherParams) {
		String projectClassificationId = "";// 项目性质（自营、代理）
		String reviewClassification = "";// 客户采购需求单对外评审类型
		if (parentId.equals(TradeHomepageConstant.oneLevelId3)) {// 对外报价评审
			// 获取选中的客户采购需求单评审类型
			JSONObject jsonObject = JSONObject.fromObject(otherParams);// 字符串转json对象
			if (jsonObject.has("projectClassificationId")) {
				projectClassificationId = jsonObject.getString("projectClassificationId");
			}
			if (jsonObject.has("reviewClassification")) {
				reviewClassification = jsonObject.getString("reviewClassification");
			}
		}
		String cacheKey = "KEYHOMEPAGE_TRADE_" + parentId;
		if (EIPService.getCacheService().keyExists(cacheKey)) {
			System.out.println("getActivedTwoLevel存在cacheKey:"+cacheKey);
			return (ArrayList<TradeHomepage>) EIPService.getCacheService().get(cacheKey);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", parentId);
		params.put("menuLevel", TradeHomepageConstant.MENU_LEVEL_TWO);
		params.put("isActive", Boolean.valueOf(true));

		// 事业部评审过滤（自营小额评审，代理评审）
		if (projectClassificationId.equals(RegisterProjectConstant.AGENT) || reviewClassification.equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_YES)) {
			params.put("homepageType", new Integer[] { HomepageTypeEnum.REVIEW_BUSINESS.getAttrInnerName(), HomepageTypeEnum.REVIEW_ALL.getAttrInnerName() });
		}

		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("displayOrder", Boolean.valueOf(true));
		List<TradeHomepage> tradeHomepageList = this.tradeHomepageDao.queryByParam(params, orders);
		if (tradeHomepageList.size() == 0) {
			System.out.println("getActivedTwoLevel:initDefaultTradeHomepage");
			tradeHomepageList = initDefaultTradeHomepage();
		}
		
		// 小额事业部评审修改菜单名称
		if (reviewClassification.equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_YES)) {
			for (int i = 0; i < tradeHomepageList.size(); i++) {
				TradeHomepage ele = tradeHomepageList.get(i);
				if (ele.getTradeHomepageId().equals(TradeHomepageConstant.twoLevelId_EXTERNAL)) {
					ele.setOperName(TradeHomepageConstant.getTwoLevelMenuOfReviewMap().get(TradeHomepageConstant.twoLevelId_EXTERNAL));
					tradeHomepageList.set(i, ele);
					break;
				}
			}
		}
		//EIPService.getCacheService().put(cacheKey, (java.io.Serializable) tradeHomepageList);
		return tradeHomepageList;
	}

	/**
	 * 获取所有二级菜单
	 * 
	 * @return
	 */
	public List<TradeHomepage> getAllTwoLevels(String parentId, String otherParams) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", parentId);
		params.put("menuLevel", TradeHomepageConstant.MENU_LEVEL_TWO);
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("displayOrder", Boolean.valueOf(true));
		List<TradeHomepage> tradeHomepageList = this.tradeHomepageDao.queryByParam(params, orders);
		if (tradeHomepageList.size() == 0) {
			tradeHomepageList = initDefaultTradeHomepage();
		}
		return tradeHomepageList;
	}

	/**
	 * 获取有效三级菜单
	 * 
	 * @param lowerStyle
	 * @param parentId
	 * @return
	 */
	public Map<String, Object> getActivedThreeLevel(int lowerStyle, String parentId) {
		return getDataStructureByStyle(lowerStyle, parentId, Boolean.valueOf(true));
	}

	/**
	 * 获取所有三级菜单
	 * 
	 * @param lowerStyle
	 * @param parentId
	 * @return
	 */
	public Map<String, Object> getAllThreeLevels(int lowerStyle, String parentId) {
		return getDataStructureByStyle(lowerStyle, parentId, Boolean.valueOf(false));
	}

	/**
	 * 初始化贸易导航菜单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<TradeHomepage> initDefaultTradeHomepage() {
		String rootDir = EIPService.getContextService().getRootDirPath();
		String filePath = TradeHomepageConstant.TRADE_HOMEPAGE_PAGE_PATH;
		List<TradeHomepage> tradeHomepageList = new ArrayList<TradeHomepage>();
		try {
			String jsonData = TradeHomepageUtil.readFileFromData(rootDir + filePath);
			System.out.println("initDefaultTradeHomepage:\n"+jsonData);
			tradeHomepageList = (List<TradeHomepage>) JSONArray.toCollection(JSONArray.fromObject(jsonData), TradeHomepage.class);
			for (int i = 0; i < tradeHomepageList.size(); i++) {
				//this.tradeHomepageDao.saveOrUpdate((TradeHomepage) tradeHomepageList.get(i));
			}
			System.out.println("END:"+tradeHomepageList.size());
		} catch (IOException e) {
			XLogger.getLogger().error("初始化贸易项目导航页面读取JSON文件失败", e);
		}
		return tradeHomepageList;
	}

	/**
	 * 设置下级附属菜单信息
	 * 
	 * @param tradeHomepageList
	 * @param childrenList
	 * @param parentId
	 */
	private static void subTradeHomepage(List<TradeHomepage> tradeHomepageList, List<TradeHomepage> childrenList, String parentId) {
		for (TradeHomepage tradeHomepage : tradeHomepageList) {
			if ((StringUtils.isNotEmpty(tradeHomepage.getParentId())) && (parentId.equals(tradeHomepage.getParentId()))) {
				childrenList.add(tradeHomepage);
				subTradeHomepage(tradeHomepageList, childrenList, tradeHomepage.getTradeHomepageId());
			}
		}
	}

	/**
	 * 根据展示样式获取数据结构
	 * 
	 * @param lowerStyle
	 * @param parentId
	 * @param isActive
	 * @return
	 */
	private Map<String, Object> getDataStructureByStyle(int lowerStyle, String parentId, Boolean isActive) {
		Map<String, Object> result = new HashMap<String, Object>();
		if ((lowerStyle == TradeHomepageConstant.GROUP) || (lowerStyle == TradeHomepageConstant.GROUP_ONE_ROW) || (lowerStyle == TradeHomepageConstant.GROUP_TWO_ROW)) {
			result.put("tradeHomepageArray", getGroupTradeHomepage(parentId, isActive));
		} else if (lowerStyle == TradeHomepageConstant.TILE) {
			result.put("tradeHomepageArray", getTileTradeHomepage(parentId, isActive));
		} else if ((lowerStyle == TradeHomepageConstant.ONE_ROW) || (lowerStyle == TradeHomepageConstant.TWO_ROW)) {
			result.put("tradeHomepageArray", getRowTradeHomepage(parentId, isActive));
		}
		result.put("style", Integer.valueOf(lowerStyle));
		return result;
	}

	/**
	 * 组样式数据结构
	 * 
	 * @param parentId
	 * @param isActive
	 * @return
	 */
	private List<Map<String, Object>> getGroupTradeHomepage(String parentId, Boolean isActive) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", parentId);
		if (isActive.booleanValue()) {
			params.put("isActive", isActive);
		}
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("displayOrder", Boolean.valueOf(true));
		List<TradeHomepage> tradeHomepageList = this.tradeHomepageDao.queryByParam(params, orders);
		for (TradeHomepage tradeHomepage : tradeHomepageList) {
			Map<String, Object> map = new HashMap<String, Object>();
			params.put("parentId", tradeHomepage.getTradeHomepageId());
			List<TradeHomepage> childrenList = this.tradeHomepageDao.queryByParam(params, orders);

			map.put("parent", tradeHomepage);
			map.put("children", childrenList);
			list.add(map);
		}
		return list;
	}

	/**
	 * 标题样式数据结构
	 * 
	 * @param parentId
	 * @param isActive
	 * @return
	 */
	private List<TradeHomepage> getTileTradeHomepage(String parentId, Boolean isActive) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (isActive.booleanValue()) {
			params.put("isActive", isActive);
		}
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("displayOrder", Boolean.valueOf(true));

		List<TradeHomepage> tradeHomepageList = this.tradeHomepageDao.queryByParam(params, orders);
		List<TradeHomepage> childrenList = new ArrayList<TradeHomepage>();

		subTradeHomepage(tradeHomepageList, childrenList, parentId);
		return childrenList;
	}

	/**
	 * 行样式数据结构
	 * 
	 * @param parentId
	 * @param isActive
	 * @return
	 */
	private List<TradeHomepage> getRowTradeHomepage(String parentId, Boolean isActive) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", parentId);
		if (isActive.booleanValue()) {
			params.put("isActive", isActive);
		}
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("displayOrder", Boolean.valueOf(true));

		List<TradeHomepage> listTradeHomepage = this.tradeHomepageDao.queryByParam(params, orders);
		return listTradeHomepage;
	}

	/**
	 * 获取贸易导航主页节点信息
	 * @param projectId 项目ID
	 * @param oneLevelId 一级菜单ID
	 * @return
	 */
	public String getTradeNodeParams(String projectId, String oneLevelId, String twoLevelId, String threeLevelId, String otherParams) {

		//System.out.println("getTradeNodeParams：projectId:"+ projectId + ",oneLevelId:"+ oneLevelId);

		String json = "{\"result\":\"error\",\"nodes\":\"\"}";
		if (StringUtils.isNotBlank(projectId)) {
			// 要传递的参数
			Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
			RegisterProject project = this.projectService.get(projectId);
			jsonMap.put("projectId", projectId);
			jsonMap.put("projectNo", project.getProjectNo());
			jsonMap.put("projectName", project.getProjectName());
			jsonMap.put("hasFrameworkAgreement", project.isHasFrameworkAgreement());
			jsonMap.put("projectClassificationId", project.getProjectClassificationId());
			jsonMap.put("singleCategoryCode", project.getSingleCategoryCode());
			jsonMap.put("isExceedCompleteDate", project.isExceedCompleteDate());
			if (oneLevelId.equals(TradeHomepageConstant.oneLevelId1)) {// 框架协议审批
				loadTradeNodeOfProtocolReview(projectId, project.isHasFrameworkAgreement(), jsonMap, otherParams);
			} else if (oneLevelId.equals(TradeHomepageConstant.oneLevelId2)) {// 客户采购意向
				loadTradeNodeOfPurchaseIntention(projectId, project.isHasFrameworkAgreement(), jsonMap, otherParams);
			} else if (oneLevelId.equals(TradeHomepageConstant.oneLevelId3)) {// 对外报价评审
				loadTradeNodeOfExternalReview(projectId, project.isHasFrameworkAgreement(), jsonMap, otherParams);
			} else if (oneLevelId.equals(TradeHomepageConstant.oneLevelId4)) {// 合同评审及签约
				loadTradeNodeOfReviewAndSign(projectId, project.isHasFrameworkAgreement(), jsonMap, otherParams);
			} else if (oneLevelId.equals(TradeHomepageConstant.oneLevelId5)) {// 合同变更
				loadTradeNodeOfContractChange(projectId, project.isHasFrameworkAgreement(), jsonMap, otherParams);
			} else if (oneLevelId.equals(TradeHomepageConstant.oneLevelId6)) {// 合同履行
				loadTradeNodeOfContractPerform(projectId, project.isHasFrameworkAgreement(), jsonMap, otherParams);
			}
			String jsonData = JSONArray.fromObject(jsonMap).toString();
			// data 中第一个为框架协议、备案信息
			json = "{\"result\":\"success\",\"nodes\":" + jsonData.substring(1, jsonData.length() - 1) + "}";
		}
		return json;
	}

	/**
	 * 框架协议评审
	 * @param jsonMap
	 * @param otherParams
	 */
	public void loadTradeNodeOfProtocolReview(String projectId, boolean agree, Map<String, Object> jsonMap, String otherParams) {
		// 有框架协议时判断是否框架协议评审及备案完成
		ProtocolReview review = this.protocolReviewService.getProtocolReviewByProjectId(projectId);
		if (review != null) {
			jsonMap.put("protocolReviewId", review.getReviewId());
			jsonMap.put("protocolReviewStatus", review.getSwfStatus());
			jsonMap.put("protocolReviewOK", review.getSwfStatus() == ProtocolReview.COMPLETED);
			// 框架协议完成
			if (review.getSwfStatus() == ProtocolReview.COMPLETED) {
				UseSeal useSeal = useSealService.getUseSealByProtocolReviewId(review.getReviewId());
				if (useSeal != null) {
					jsonMap.put("protocolUseSealId", useSeal.getSealId());
					jsonMap.put("protocolUseSealStatus", useSeal.getSwfStatus());
					jsonMap.put("protocolUseSealOK", useSeal.getSwfStatus() == UseSeal.COMPLETED);
				}
				RecordFilingManager filingManager = filingManagerService.getRecordFilingManagerByProtocolReviewId(review.getReviewId());
				if (filingManager != null) {
					jsonMap.put("protocolFilingId", filingManager.getRecordFilingId());
					jsonMap.put("protocolFilingStatus", filingManager.getSwfStatus());
					jsonMap.put("protocolFilingOK", filingManager.getSwfStatus() == RecordFilingManager.COMPLETED);
				}
			}
		}
	}

	/**
	 * 客户采购意向
	 * @param jsonMap
	 * @param otherParams
	 */
	public void loadTradeNodeOfPurchaseIntention(String projectId, boolean agree, Map<String, Object> jsonMap, String otherParams) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		// 有框架协议时判断是否框架协议评审及备案完成(无框架协议时可以直接新建)
		if (agree) {
			ProtocolReview review = this.protocolReviewService.getProtocolReviewByProjectId(projectId);
			if (review != null) {
				jsonMap.put("protocolReviewId", review.getReviewId());
				jsonMap.put("protocolReviewName", review.getProtocolName());
			}
			params.put("recordFilingTypeCode", TpcConstant.OUTLINE_AGREEMENT);
			List<RecordFilingManager> filingList = filingManagerDao.find(params);
			if (filingList != null && filingList.size() > 0) {
				RecordFilingManager filingManager = filingList.get(0);
				jsonMap.put("protocolFilingOK", filingManager.getSwfStatus() == RecordFilingManager.COMPLETED);
			}
		}
	}

	/**
	 * 对外报价评审
	 * @param jsonMap
	 * @param otherParams
	 */
	public void loadTradeNodeOfExternalReview(String projectId, boolean agree, Map<String, Object> jsonMap, String otherParams) {
		JSONObject jsonObject = JSONObject.fromObject(otherParams);// 字符串转json对象
		String demandId = "";
		if (jsonObject.has("demandId")) {
			demandId = jsonObject.getString("demandId");// 获取内容
		}
		ExternalQuotationReviewDept quotationReviewDept = this.quotationReviewDeptService.getExternalQuotationReviewDeptByDemandId(demandId);
		if (quotationReviewDept != null) {
			jsonMap.put("deptExternalId", quotationReviewDept.getExternalId());
			jsonMap.put("deptExternalOK", quotationReviewDept.getSwfStatus() == ExternalQuotationReviewDept.COMPLETED);
		}
	}

	/**
	 * 合同评审及签约
	 * @param jsonMap
	 * @param otherParams
	 */
	public void loadTradeNodeOfReviewAndSign(String projectId, boolean agree, Map<String, Object> jsonMap, String otherParams) {
		// 合同签约前评审需要判断是否已完成对外报价评审
		JSONObject jsonObject = JSONObject.fromObject(otherParams);// 字符串转json对象
		String demandId = "";
		String reviewClassification = "";
		if (jsonObject.has("demandId")) {
			demandId = jsonObject.getString("demandId");
			reviewClassification = jsonObject.getString("reviewClassification");
			if (reviewClassification.equals(PurchaseDemandConstant.REVIEW_AGENT_NO) || reviewClassification.equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_NO)) {
				// 无对外报价评审时
				//ExternalQuotationReview review = this.protocolReviewService.getProtocolReviewByProjectId(projectId);
				//jsonMap.put("protocolReviewId", review.getReviewId());
			} else if (reviewClassification.equals(PurchaseDemandConstant.REVIEW_AGENT_YES) || reviewClassification.equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_YES)) {
				// 仅事业部评审时
				//jsonMap.put("protocolReviewId", review.getReviewId());
			} else {
				// 公司评审（会审）时
				//jsonMap.put("protocolReviewId", review.getReviewId());
			}
		}
	}
	/**
	 * 合同变更
	 * @param jsonMap
	 * @param otherParams
	 */
	public void loadTradeNodeOfContractChange(String projectId, boolean agree, Map<String, Object> jsonMap, String otherParams) {
		// 合同签约前评审需要判断是否已完成对外报价评审
		JSONObject jsonObject = JSONObject.fromObject(otherParams);// 字符串转json对象
		String demandId = "";
		String reviewClassification = "";
		if (jsonObject.has("demandId")) {
			demandId = jsonObject.getString("demandId");
			reviewClassification = jsonObject.getString("reviewClassification");
			if (reviewClassification.equals(PurchaseDemandConstant.REVIEW_AGENT_NO) || reviewClassification.equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_NO)) {
				// 无对外报价评审时
				//ExternalQuotationReview review = this.protocolReviewService.getProtocolReviewByProjectId(projectId);
				//jsonMap.put("protocolReviewId", review.getReviewId());
			} else if (reviewClassification.equals(PurchaseDemandConstant.REVIEW_AGENT_YES) || reviewClassification.equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_YES)) {
				// 仅事业部评审时
				//jsonMap.put("protocolReviewId", review.getReviewId());
			} else {
				// 公司评审（会审）时
				//jsonMap.put("protocolReviewId", review.getReviewId());
			}
		}
	}
	/**
	 * 合同履行
	 * @param jsonMap
	 * @param otherParams
	 */
	public void loadTradeNodeOfContractPerform(String projectId, boolean agree, Map<String, Object> jsonMap, String otherParams) {
		JSONObject jsonObject = JSONObject.fromObject(otherParams);// 字符串转json对象

	}

}
