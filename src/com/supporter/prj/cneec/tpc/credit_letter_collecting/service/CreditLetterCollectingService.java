package com.supporter.prj.cneec.tpc.credit_letter_collecting.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.credit_letter_collecting.dao.CreditLetterCollectingDao;
import com.supporter.prj.cneec.tpc.credit_letter_collecting.dao.CreditLetterCollectingRDao;
import com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.CreditLetterCollecting;
import com.supporter.prj.cneec.tpc.credit_letter_collecting.entity.CreditLetterCollectingR;
import com.supporter.prj.cneec.tpc.receive_credit_letter.entity.ReceiveCreditLetter;
import com.supporter.prj.cneec.tpc.receive_credit_letter.service.ReceiveCreditLetterService;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**   
 * @Title: Service
 * @Description: 报告
 * @author liyinfeng
 * @date 2017-7-05 10:25:07
 * @version V1.0   
 *
 */
@Service
@Transactional(TransManager.APP)
public class CreditLetterCollectingService {
	
	//public static final String CURRENCY_TYPE = "CURRENCY_TYPE";// 币种
	public static final String CURRENCY_CATEGORY = "CURRENCY_CATEGORY";// 币种
	public static final String BUDGET_ITEM = TpcConstant.TPC_BUDGET_ITEM;// 预算项
	public static final String PrjMainContractCostType = "PRJ_BUDGET_TYPE";// 常用的项目评审类型
	
	//合同付款预算项
	public static String ITEM_PRJ_CATEGORY_SETTLEMENT = "ITEM_PRJ_CATEGORY_SETTLEMENT";
	//收入
	public static String ITEM_PRJ_CATEGORY_GATHERING = "ITEM_PRJ_CATEGORY_GATHERING";
	//出口退税
	public static String ITEM_DRAW_BACK = "ITEM_DRAW_BACK";
	//转收乳
	public static String ITEM_PRJ_CATEGORY_ZSL = "ITEM_PRJ_CATEGORY_ZSL";

	@Autowired
	private ReceiveCreditLetterService receiveCreditLetterService;
	@Autowired
	private CreditLetterCollectingDao reportDao;
	@Autowired
	private CreditLetterCollectingRDao reportContentDao;

	/**
	 * 获取相关的信用证.
	 * @return
	 */
	public ReceiveCreditLetter getReceiveCreditLetter(String creditLetterId) {
		return receiveCreditLetterService.get(creditLetterId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public CreditLetterCollecting get(String reportId) {
		return reportDao.get(reportId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public CreditLetterCollecting initEditOrViewPage(String reportId, String creditLetterId, UserProfile user) {
		if (StringUtils.isBlank(reportId)) {// 新建
			ReceiveCreditLetter receiveCreditLetter = this.getReceiveCreditLetter(creditLetterId);
			CreditLetterCollecting report = newCreditLetterCollecting(user, receiveCreditLetter);
			report.setCreditLetterCollectingId(UUIDHex.newId());
			return report;
		} else {// 编辑
			CreditLetterCollecting report =  reportDao.get(reportId);
			report.setCreditLetterCollectingRList(reportContentDao.getRecList(reportId));
			//获取记录总数
			report.setRecordCount(report.getCreditLetterCollectingRList().size());
			return report;
		}
	}
	
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public CreditLetterCollecting viewPage(String reportId, UserProfile user) {
		CreditLetterCollecting report = new CreditLetterCollecting();
		return report;
	}
	
	/**
	 * 获取本位币
	 * @return
	 */	
	public String getLsCurrencyType(){
		List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CURRENCY_CATEGORY);		
		String lsCurrencyType = "";
		for(IComCodeTableItem item : list){
			if(item.getExtField4().equals("1")){
				lsCurrencyType = item.getDisplayName();
			}
		}
		return lsCurrencyType;
	}
	
	public double getCurrencyExchangeRate(String ls_currency_type_code){
		List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CURRENCY_CATEGORY);		
		String currencyRate = "";
		for(IComCodeTableItem item : list){
			if(item.getExtField3().equals(ls_currency_type_code)){
				currencyRate = item.getExtField2();
			}
		}
		return Double.valueOf(currencyRate);
	}
	/**
	 * 根据业务对象获取流程实例ID.
	 * @param report
	 * @return
	 */
	public String getProcId(CreditLetterCollecting report){
		if (report == null)return "";
		return EIPService.getWfService().getProcIdByRecord(report);
	}
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public CreditLetterCollecting newCreditLetterCollecting(UserProfile ausrprf_U, ReceiveCreditLetter receiveCreditLetter){
        CreditLetterCollecting creditLetterCollecting = new CreditLetterCollecting();

        creditLetterCollecting.setCreditLetterId(receiveCreditLetter.getReceiveCreditLetterId());
		creditLetterCollecting.setCurrencyType(receiveCreditLetter.getCurrencyType());
		creditLetterCollecting.setCurrencyTypeCode(receiveCreditLetter.getCurrencyTypeCode());
		creditLetterCollecting.setProjectId(receiveCreditLetter.getProjectId());
		creditLetterCollecting.setProjectName(receiveCreditLetter.getProjectName());
		creditLetterCollecting.setContractId(receiveCreditLetter.getContractId());
		creditLetterCollecting.setContractName(receiveCreditLetter.getContractName());
		creditLetterCollecting.setContractNo(receiveCreditLetter.getContractNo());
		creditLetterCollecting.setCreditLetterNo(receiveCreditLetter.getCreditLetterNo());
		creditLetterCollecting.setAmountCollecting(0d);
		creditLetterCollecting.setAmountCollectingAct(0d);
		creditLetterCollecting.setCollectingDate(CommonUtil.format(new Date(),"yyyy-MM-dd"));
		Dept dept = ausrprf_U.getDept();
        if(dept != null){
	        creditLetterCollecting.setDeptId(dept.getDeptId());
	        creditLetterCollecting.setDeptName(dept.getName());
        }
        
        creditLetterCollecting.setCreatedById(ausrprf_U.getPersonId());
        creditLetterCollecting.setCreatedBy(ausrprf_U.getName());
        creditLetterCollecting.setCreatedDate(CommonUtil.format(new Date(),"yyyy-MM-dd"));
        
        creditLetterCollecting.setModifiedById(ausrprf_U.getPersonId());
        creditLetterCollecting.setModifiedBy(ausrprf_U.getName());
        creditLetterCollecting.setModifiedDate(CommonUtil.format(new Date(),"yyyy-MM-dd"));
        
        creditLetterCollecting.setSwfStatus(CreditLetterCollecting.DRAFT);
        return creditLetterCollecting;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<CreditLetterCollecting> getGrid(UserProfile user, JqGrid jqGrid, CreditLetterCollecting report) {
		List<CreditLetterCollecting> list = reportDao.findPage(user, jqGrid, report);
		return list;
	}

	/**
	 * 分页表格展示信用证付款明细.
	 * @param jqGrid
	 * @param creditLetterCollectingId
	 * @return
	 */
	public List<CreditLetterCollectingR> getRecGrid(UserProfile user,JqGrid jqGrid,String creditLetterCollectingId) {
		return reportContentDao.getRecGrid(jqGrid, creditLetterCollectingId);
	}
	
	/**
	 * 分页表格展示数据.
	 * 历次信息证付款信息
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @return JqGrid
	 */
	public List<CreditLetterCollecting> getPastGrid(UserProfile user, JqGrid jqGrid, CreditLetterCollecting report, String contractId) {
		return reportDao.findPagePast(user, jqGrid, report, contractId);
	}
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public OperResult<CreditLetterCollecting> saveOrUpdate(UserProfile user, CreditLetterCollecting report, Map<String, Object> valueMap) {
		CreditLetterCollecting ret = reportDao.get(report.getCreditLetterCollectingId());
		if (ret == null) {// 新建
			report.setCreditCollectingOrderNo(generatorCollectingOrderNo());
			report.setAmountCollecting(report.getSAll());
			report.setAmountCollectingAct(report.getSActAll());
			this.reportDao.save(report);
		} else {// 编辑
			this.reportDao.clear();
			ret.setAmountCollecting(report.getSAll());
			ret.setAmountCollectingAct(report.getSActAll());
			ret.setDeptId(report.getDeptId());
			ret.setDeptName(report.getDeptName());
			ret.setCreatedBy(report.getCreatedBy());
			ret.setCreatedById(report.getCreatedById());
			ret.setCollectingDate(report.getCollectingDate());
			this.reportDao.update(ret);
		}
		// 保存收款明细
		if (CollectionUtils.isNotEmpty(report.getCreditLetterCollectingRList())) {
			saveCLSRList(report);
		}
		// 记录日志
//		String logMessage = MessageFormat.format(LogConstant.PUBLISH_REPORT_LOG_MESSAGE, report.getReportTitle());
//		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage, report, null);
		return OperResult.succeed("saveSuccess", null, report);
	}

	/**
	 * 审批完成
	 * @param creditLetterCollecting
	 */
	public void completeExam(CreditLetterCollecting creditLetterCollecting) {
		creditLetterCollecting.setSwfStatus(CreditLetterCollecting.COMPLETE);// 审批完成
		this.update(creditLetterCollecting);
	}

	/**
	 * 生成收款单号
	 * @return
	 */
	public synchronized String generatorCollectingOrderNo() {
		String collectingOrderNo = "";
		String noBegin = CommonUtil.format(new Date(), "yyyyMM");
		String noEnd = "001";
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("creditCollectingOrderNo", noBegin + "%");
		List<String> likeSearhNames = new ArrayList<String>();
		likeSearhNames.add("creditCollectingOrderNo");
		Map<String, Boolean> orderByMap = new LinkedHashMap<String, Boolean>();
		orderByMap.put("creditCollectingOrderNo", false);
		List<CreditLetterCollecting> list = this.reportDao.find(params, likeSearhNames, orderByMap);
		// 已存在记录取最大记录号+1
		if (list != null && list.size() > 0) {
			String orderNo = list.get(0).getCreditCollectingOrderNo();
			noEnd = String.format("%03d", Integer.valueOf(orderNo.substring(orderNo.length() - 3, orderNo.length())) + 1);
		}
		collectingOrderNo = noBegin + noEnd;
		return collectingOrderNo;
	}

	/**
	 * 保存收款明细
	 * @param abroad
	 */
	public void saveCLSRList(CreditLetterCollecting report) {
		// 先删除，再保存
		deleteR(report.getDelIds());
		List<CreditLetterCollectingR> clsRList = report.getCreditLetterCollectingRList();
		if (CollectionUtils.isNotEmpty(clsRList)) {
			String projectId = report.getProjectId();
			Map<String, String> map = getBudgetItemCodeTable(projectId);
			for (CreditLetterCollectingR ap : clsRList) {
				ap.setCreditLetterCollectingId(report.getCreditLetterCollectingId());
				// 如果从预算项里找不到名称，则从项目评审类型PRJ_BUDGET_TYPE码表里找名称
				if (StringUtils.isNotBlank(ap.getItemId())) {
					String itemName = "";
//					if (budgetItemService.isExitBudgetItem(projectId, BudgetItem.ITEM_PRJ_CATEGORY_SETTLEMENT)) {
						itemName = map.get(ap.getItemId());
//					} else {
//						itemName = getPrjMainContractCostType().get(ap.getItemId());
//					}
					ap.setItemName(itemName);
				}
				if (StringUtils.isNotBlank(ap.getCollectingClauseCode())) {
					ap.setCollectingClause(CreditLetterCollecting.getCollectingClauseCodeTable().get(ap.getCollectingClauseCode()));
				}
				ap.setItemGroupId(ap.getItemId());

				Date ld_Year = new Date();
				if (ap.getRecordId() == null || "".equals(ap.getRecordId())) {
					ap.setRecordId(com.supporter.util.UUIDHex.newId());
					this.reportContentDao.save(ap);
				} else {
					this.reportContentDao.update(ap);
				}
			}
		}
	}
	
	/**
	 * 通过明细id集合删除从表数据
	 * @param delIds
	 */
	public void deleteR(String delIds) {
		if (StringUtils.isNotBlank(delIds)) {
			for (String delId : delIds.split(",")) {
				CreditLetterCollectingR ap = this.reportContentDao.get(delId);
				if (ap != null) {
					this.reportContentDao.delete(delId);
				}
			}
		}
	}
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public CreditLetterCollecting update(CreditLetterCollecting report) {
			this.reportDao.update(report);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_EDIT.getOperName(), null);

		return report;

	}
	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String reportIds) {
		if (StringUtils.isNotBlank(reportIds)) {
			for (String reportId : reportIds.split(",")) {
				this.reportContentDao.deleteBySettlementId(reportId);
				this.reportDao.delete(reportId);
			}
			// 记录日志
//			String logMessage = MessageFormat.format(
//					LogConstant.PUBLISH_REPORT_LOG_MESSAGE, reportIds);
//			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
//					user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage,
//					null, null);
		}
	}
	/**
	 * 获取币种下拉列表
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> getCurrencyTypeCodeTable() throws IOException {
		List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(CURRENCY_CATEGORY);		
		Map<String, String> map = new LinkedHashMap<String, String>();
		for(IComCodeTableItem item : list){
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}

	/**
	 * 获取币种下拉列表
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> getBudgetItemCodeTable(String prjId) {
		List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(BUDGET_ITEM);		
		Map<String, String> map = new LinkedHashMap<String, String>();
		for(IComCodeTableItem item : list){
			//if(ITEM_PRJ_CATEGORY_SETTLEMENT.equals(item.getExtField1())){
				map.put(item.getItemId(), item.getDisplayName());
			//}
		}
		return map;
	}
	/**
	 * 获取币种下拉列表
	 * @return
	 * @throws IOException
	 */
	public Map<String, String> getPrjMainContractCostType(){
		List <IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems(PrjMainContractCostType);		
		Map<String, String> map = new LinkedHashMap<String, String>();
		if(list != null){
			for(IComCodeTableItem item : list){
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}

	/**
	 * 根据合同获取信用证.
	 */
	public Map<String, String> getCreditLetter(String projectId, String contractId) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (StringUtils.isNotBlank(projectId) && StringUtils.isNotBlank(contractId)) {
			List<ReceiveCreditLetter> list = this.receiveCreditLetterService.getList(projectId, contractId);
			for (ReceiveCreditLetter contract : list) {
				map.put(contract.getReceiveCreditLetterId(), contract.getCreditLetterNo());
			}
		}
		return map;
	}

}
