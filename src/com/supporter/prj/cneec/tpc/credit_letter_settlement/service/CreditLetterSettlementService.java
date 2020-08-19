package com.supporter.prj.cneec.tpc.credit_letter_settlement.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.credit_letter_apply.entity.CreditLetterApply;
import com.supporter.prj.cneec.tpc.credit_letter_apply.service.CreditLetterApplyService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.dao.CreditLetterSettlementRDao;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.dao.CreditLetterSettlementDao;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.CreditLetterSettlement;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.CreditLetterSettlementR;
import com.supporter.prj.cneec.tpc.credit_letter_settlement.entity.CreditLetterSettlement.ControlStatus;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

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
public class CreditLetterSettlementService {
	
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
	/**
	 * 当前的信用证付款序列号（本年度） .
	 */
	private int ii_PaymentIndex = 0;
	@Autowired
	private RegisterProjectService registerProjectService;
	@Autowired
	private CreditLetterApplyService creditLetterApplyService;
	@Autowired
	private CreditLetterSettlementDao reportDao;
	@Autowired
	private CreditLetterSettlementRDao reportContentDao;

	
	public String getContractId(String creditLetterId){
		CreditLetterApply apply = creditLetterApplyService.get(creditLetterId);
		if(apply != null){
			return apply.getContractId();
		}else{
			return null;
		}
	}
	public String getPrjId(String creditLetterId){
		CreditLetterApply apply = creditLetterApplyService.get(creditLetterId);
		if(apply != null){
			return apply.getProjectId();
		}else{
			return null;
		}
	}
//	/**
//	 * 得到本位币的名称及币种编码.
//	 * @return
//	 */
//	public String getCurrencyType(){
//		String ls_SQL ="select  currency_type,currency_type_code from pc_currency_type where  is_standard_unit = 1 " ;
//		List<String[]> objList = reportDao.sqlQuery(ls_SQL, null, "");
//		String ls_currency_type = "";
//		String ls_currency_type_code = "";
//		for(String[] objs : objList){
//			ls_currency_type = CommonUtil.trim(objs[0]);
//			ls_currency_type_code = CommonUtil.trim(objs[1]);
//		}
//		return ls_currency_type+"_"+ls_currency_type_code;
//	}
//	//取当前币种对应的汇率.
//	public double getCurrencyExchangeRate(String ls_currency_type_code){
//		double ldb_CurrencyExchangeRate = 0.0;
//		String ls_SQL = "select currency_exchange_rate from pc_currency_type where  currency_type_code = ?";
//		List<Double> list = reportDao.sqlQuery(ls_SQL, null, ls_currency_type_code);
//		if(list != null && list.size()>0){
//			ldb_CurrencyExchangeRate = list.get(0);
//		}
//		return ldb_CurrencyExchangeRate;
//	}
	/**
	 * 获取相关的信用证.
	 * @return
	 */
	public CreditLetterApply getCreditLetterApply(String creditLetterId) {
		return creditLetterApplyService.get(creditLetterId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public CreditLetterSettlement get(String reportId) {
		return reportDao.get(reportId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public CreditLetterSettlement initEditOrViewPage(String reportId, String creditLetterId, UserProfile user) {
		if (StringUtils.isBlank(reportId)) {// 新建
			CreditLetterApply apply = this.getCreditLetterApply(creditLetterId);
			CreditLetterSettlement report = newCreditLetterSettlement(user, apply);
			report.setCreditLetterApply(this.getCreditLetterApply(report.getCreditLetterId()));
			//获取本位币
			report.setLsCurrencyType(getLsCurrencyType());
			return report;
		} else {// 编辑
			CreditLetterSettlement report =  reportDao.get(reportId);
			//如果有旧系统的流程，则获取旧系统的procId
//			long oldProcId = cneecExamService.getProcIdByRecord(report);
//			if (oldProcId > 0)report.setOldProcId(oldProcId);
			
			report.setCreditLetterSettlementRList(reportContentDao.getRecList(reportId));
			report.setCreditLetterApply(this.getCreditLetterApply(report.getCreditLetterId()));
			//获取本位币
			report.setLsCurrencyType(getLsCurrencyType());
			//获取记录总数
			report.setRecordCount(report.getCreditLetterSettlementRList().size());
			return report;
		}
	}
	
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public CreditLetterSettlement viewPage(String reportId, UserProfile user) {
			CreditLetterSettlement report =  reportDao.get(reportId);
			//如果有旧系统的流程，则获取旧系统的procId
//			long oldProcId = cneecExamService.getProcIdByRecord(report);
//			if (oldProcId > 0)report.setOldProcId(oldProcId);
			report.setCreditLetterSettlementRList(reportContentDao.getRecList(reportId));
			report.setCreditLetterApply(this.getCreditLetterApply(report.getCreditLetterId()));
			//获取本位币
			report.setLsCurrencyType(getLsCurrencyType());
			//获取记录总数
			report.setRecordCount(report.getCreditLetterSettlementRList().size());
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
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public CreditLetterSettlement newCreditLetterSettlement(UserProfile ausrprf_U, CreditLetterApply acla_CreditLetterApply){
        CreditLetterSettlement lcls_CreditLetterSettlement = new CreditLetterSettlement();
        lcls_CreditLetterSettlement.setCreditLetterId(acla_CreditLetterApply.getCreditLetterId());
		lcls_CreditLetterSettlement.setCurrencyType(acla_CreditLetterApply.getCurrencyType());
		lcls_CreditLetterSettlement.setCurrencyTypeCode(acla_CreditLetterApply.getCurrencyTypeCode());
		lcls_CreditLetterSettlement.setPaidById(ausrprf_U.getPersonId());
		lcls_CreditLetterSettlement.setPaidBy(ausrprf_U.getName());
		lcls_CreditLetterSettlement.setSettlementYear(
				CommonUtil.parseInt(CommonUtil.format(new Date(),"yyyy")));
		lcls_CreditLetterSettlement.setSettlementMonth(CommonUtil.parseInt(CommonUtil.format(new Date(),"MM")));
		lcls_CreditLetterSettlement.setCreditLetterSettlementDate(CommonUtil.format(new Date(),"yyyy-MM-dd"));
		Dept dept = ausrprf_U.getDept();
        if(dept != null){
	        lcls_CreditLetterSettlement.setPayerDeptId(dept.getDeptId());
	        lcls_CreditLetterSettlement.setPayerDeptName(dept.getName());
        }
//		lcls_CreditLetterSettlement.setUserProfile(ausrprf_U);
        
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );  
        String datestr = sdf.format( new  Date());  
        
        lcls_CreditLetterSettlement.setCreatedById(ausrprf_U.getPersonId());
        lcls_CreditLetterSettlement.setCreatedBy(ausrprf_U.getName());
        lcls_CreditLetterSettlement.setCreatedDate(datestr);
        
        lcls_CreditLetterSettlement.setModifiedById(ausrprf_U.getPersonId());
        lcls_CreditLetterSettlement.setModifiedBy(ausrprf_U.getName());
        lcls_CreditLetterSettlement.setModifiedDate(datestr);
        
        
        lcls_CreditLetterSettlement.setSettlementStatus(CreditLetterSettlement.DRAFT);
        lcls_CreditLetterSettlement.setCreditLetterSettlementId(com.supporter.util.UUIDHex.newId());
        lcls_CreditLetterSettlement.setControlStatus(ControlStatus.EFFECTIV_DESC);
        lcls_CreditLetterSettlement.setControlStatusCode(ControlStatus.EFFECTIV);
        return lcls_CreditLetterSettlement;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<CreditLetterSettlement> getGrid(UserProfile user, JqGrid jqGrid, CreditLetterSettlement report, Map<String, Object> paramsMap) {
		List<CreditLetterSettlement> list = reportDao.findPage(user, jqGrid, report, paramsMap);
		for(CreditLetterSettlement settlement : list){
			CreditLetterApply apply = creditLetterApplyService.get(settlement.getCreditLetterId());
			if(apply != null){
				settlement.setCreditLetterApply(apply);
				settlement.setProjectName(apply.getProjectName());
				settlement.setContractNo(apply.getContractNo());
			}
		}
		return list;
	}

	/**
	 * 分页表格展示信用证付款明细.
	 * @param jqGrid
	 * @param creditLetterSettlementId
	 * @return
	 */
	public List<CreditLetterSettlementR> getRecGrid(UserProfile user,JqGrid jqGrid,String creditLetterSettlementId) {
		return reportContentDao.getRecGrid(jqGrid, creditLetterSettlementId);
	}
	
	/**
	 * 分页表格展示数据.
	 * 历次信息证付款信息
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @return JqGrid
	 */
	public List<CreditLetterSettlement> getPastGrid(UserProfile user, JqGrid jqGrid, CreditLetterSettlement report, String contractId) {
		return reportDao.findPagePast(user, jqGrid, report, contractId);
	}
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public OperResult<CreditLetterSettlement> saveOrUpdate(UserProfile user, CreditLetterSettlement report, Map< String, Object > valueMap) {
		CreditLetterSettlement entity = null;
		CreditLetterSettlement ret = reportDao.get(report.getCreditLetterSettlementId());
		if (ret == null) {// 新建
			report.setAmountSettlement(report.getSAll());
			report.setAmountSettlementAct(report.getSActAll());
			this.reportDao.save(report);
			entity = report;
		} else {// 编辑
			this.reportDao.clear();
			ret.setAmountSettlement(report.getSAll());
			ret.setAmountSettlementAct(report.getSActAll());
			ret.setPaidById(report.getPaidById());
			ret.setPaidBy(report.getPaidBy());
			ret.setPayerDeptId(report.getPayerDeptId());
			ret.setPayerDeptName(report.getPayerDeptName());
			ret.setCreditLetterSettlementDate(report.getCreditLetterSettlementDate());
			this.reportDao.update(ret);
			entity = ret;
		}
		if (StringUtils.isBlank(entity.getPrjManagerId())) {
			CreditLetterApply apply = this.creditLetterApplyService.get(entity.getCreditLetterId());
			RegisterProject project = this.registerProjectService.get(apply.getProjectId());
			entity.setPrjManagerId(project.getProjectChargeId());//项目负责人
			entity.setDeptId(project.getDeptId());
			entity.setPrjId(apply.getProjectId());
			this.reportDao.update(entity);
		}
		if (CollectionUtils.isNotEmpty(report.getCreditLetterSettlementRList())) {
			saveCLSRList(report);
		}
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * @param abroad
	 */
	public void saveCLSRList(CreditLetterSettlement report){
		//先删除，再保存
		deleteR(report.getDelIds());
		List<CreditLetterSettlementR> clsRList = report.getCreditLetterSettlementRList();
		if(CollectionUtils.isNotEmpty(clsRList)){
			for(CreditLetterSettlementR ap:clsRList){
				ap.setCreditLetterSettlementId(report.getCreditLetterSettlementId());
				//如果从预算项里找不到名称，则从项目评审类型PRJ_BUDGET_TYPE码表里找名称
				if(StringUtils.isNotBlank(ap.getItemId())){
					String projectId = getPrjId(report.getCreditLetterId());
					String itemName = "";
					//if(budgetItemService.isExitBudgetItem(projectId,BudgetItem.ITEM_PRJ_CATEGORY_SETTLEMENT)){
						Map<String, String> map = getBudgetItemCodeTable(projectId);
   	    			    itemName = map.get(ap.getItemId());
   	    		    //}else{
   	    			//    itemName = getPrjMainContractCostType().get(ap.getItemId());
   	    		    //}
					ap.setItemName(itemName);
				}
				ap.setItemGroupId(ap.getItemId());
				
				Date ld_Year = new Date();					
				if(ap.getBudgetMonth() == 0){
					String ls_Month = CommonUtil.formatDate(ld_Year,"MM");
					ap.setBudgetMonth(Integer.parseInt(ls_Month));
				}
				if(ap.getBudgetYear()==0){
					String ls_Year = CommonUtil.formatDate(ld_Year,"yyyy");
					ap.setBudgetYear(Integer.parseInt(ls_Year));
				}
				if(ap.getUsableAmount() == null){
					ap.setUsableAmount(0.0);
				}
				if(ap.getUseSumAmount() == null){
					ap.setUseSumAmount(0.0);
				}
				if(ap.getRecordId() == null || "".equals(ap.getRecordId())){
					ap.setRecordId(com.supporter.util.UUIDHex.newId());
					this.reportContentDao.save(ap);
				}else{
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
				CreditLetterSettlementR ap = this.reportContentDao.get(delId);
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
	public CreditLetterSettlement update(CreditLetterSettlement report) {
			this.reportDao.update(report);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_EDIT.getOperName(), null);

		return report;

	}
	/**
	 * 作废或生效数据.
	 * 
	 * @param creditLetterSettlementId
	 * @param flag
	 * @return
	 */
	public void changeStatus(String creditLetterSettlementId) {
		if(!"".equals(creditLetterSettlementId)){
			CreditLetterSettlement p = reportDao.get(creditLetterSettlementId);
			if(p.getIsEffectiv()){
				if(p.getIsPartlyPaid()||p.getIsCompletedPaid()){
					//如果已经部分支付或全部支付，不进行操作。
				}else{
					p.setControlStatus(CreditLetterSettlement.ControlStatus.FAILURE_DESC);
					p.setControlStatusCode(CreditLetterSettlement.ControlStatus.FAILURE);
					p.setOnWayAmount(0);
					p.setOnWayAmountF(0);
					p.setRealSettlementAmount(0);
					p.setRealSettlementAmountF(0);
					reportDao.update(p);
					this.cancelPayableRecords(p);
				}
			}else{
				p.setControlStatus(CreditLetterSettlement.ControlStatus.EFFECTIV_DESC);
				p.setControlStatusCode(CreditLetterSettlement.ControlStatus.EFFECTIV);
				p.setOnWayAmount(p.getAmountSettlementAct());
				p.setPaymentStatus(CreditLetterSettlement.PaymentStatus.UNPAID.getKey());
				reportDao.update(p);
				this.unpaidPayableRecords(p);
			}
		}
	}
	/**
     * 支付状态为“取消支付”.
     */
    public void cancelPayment(String id){
    	CreditLetterSettlement lcls_CreditLetterSettlement = this.reportDao.get(id);
    	lcls_CreditLetterSettlement.setPaymentStatus(CreditLetterSettlement.PaymentStatus.CANCEL_PAID.getKey());
    	lcls_CreditLetterSettlement.setControlStatus(CreditLetterSettlement.ControlStatus.FAILURE_DESC);
    	lcls_CreditLetterSettlement.setControlStatusCode(CreditLetterSettlement.ControlStatus.FAILURE);
    	reportDao.update(lcls_CreditLetterSettlement);
    }
	/**
	 * 失效后清除待支付
	 * @param ps
	 */
	@SuppressWarnings("unchecked")
	public void cancelPayableRecords(CreditLetterSettlement ps){
		String sql = "select payable_id from pc_payable t where t.related_table='pc_credit_letter_settlement' and t.related_fields='credit_letter_settlement_id' and t.related_values = ?";
//		DataSet ds = SQLQuery.retrieve(sql, String.valueOf(ps.getCreditLetterSettlementId()));
//		for (int i = 1; i <= ds.getRowCount(); i++) {
//			Payable pay = new Payable(ds.getItemLong(i, "payable_id"));
//			if(pay.getStatus()==Payable.Status.UNPAID){
//				pay.setStatus(Payable.Status.CANCELED);
//				pay.update();
//				List llist_Records = pay.getPayableRecords();
//		        for (int j = 0; j < llist_Records.size(); j++) {
//		            PayableRecord lprec_Record = (PayableRecord)llist_Records.get(j);
//		            PayableRecordBO.getInstance().cancel(lprec_Record);
//		        }
//			}
//		}
	}
	/**
     * 生效后将取消状态的待支付改为未支付
     * @param lpcs_PrjContractSettlement 付款单实例. 一般来说，该付款单应该是已经审批通过的.
     * @return
     */
    public void unpaidPayableRecords(CreditLetterSettlement acls_CreditLetterSettlement) {
    	String sql = "select payable_id from pc_payable t where t.related_table='pc_credit_letter_settlement' and t.related_fields='credit_letter_settlement_id' and t.related_values = ?";
//		DataSet ds = SQLQuery.retrieve(sql, String.valueOf(acls_CreditLetterSettlement.getCreditLetterSettlementId()));
//		for (int i = 1; i <= ds.getRowCount(); i++) {
//			Payable pay = new Payable(ds.getItemLong(i, "payable_id"));
//			if(pay.getStatus()==Payable.Status.CANCELED){
//				pay.setStatus(Payable.Status.UNPAID);
//				pay.update();
//				List llist_Records = pay.getPayableRecords();
//		        for (int j = 0; j < llist_Records.size(); j++) {
//		            PayableRecord lprec_Record = (PayableRecord)llist_Records.get(j);
//		            lprec_Record.setRecordStatus(PayableRecord.Status.UNPAID);
//		            lprec_Record.update();
//		        }
//			}
//		}
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
	 * 获取预算项下拉列表
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
	    * 锁申请付款金额进入在途金额.
	    * @param ap_PrjContractSettlement
	    */
	public void lockOnwayAmount(CreditLetterSettlement settlement) {
		settlement.setOnWayAmount(settlement.getAmountSettlementAct());
		settlement.setOnWayAmountF(settlement.getAmountSettlement());
		settlement.setRealSettlementAmount(0);
		settlement.setRealSettlementAmountF(0);
		reportDao.update(settlement);
		CreditLetterApply apply = this.creditLetterApplyService.get(CommonUtil.trim(settlement.getCreditLetterId()));
		if (apply != null) {
			apply.setSettlementStatus(CreditLetterApply.SETTLEMENTING);
			this.creditLetterApplyService.update(apply);
		}
	}

	/**
	 * 解锁申请付款金额把在途金额清0.(用于流程中止、流程不同意终止).
	 * 
	 * @param ap_PrjContractSettlement
	 */
	public void unLockOnwayAmount(CreditLetterSettlement settlement) {
		settlement.setOnWayAmount(0);
		settlement.setOnWayAmountF(0);
		reportDao.update(settlement);
		CreditLetterApply apply = this.creditLetterApplyService.get(CommonUtil.trim(settlement.getCreditLetterId()));
		if (apply != null) {
			apply.setSettlementStatus(CreditLetterApply.SETTLEMENT);
			this.creditLetterApplyService.update(apply);
		}
	}

	 /**
	    * 解在途金额.
	    * @param ap_Payable
	    */
//	   public void unLockOnwayAmount(Payable ap_Payable){
//		   CreditLetterSettlement lcls_CreditLetterSettlement = this.reportDao.get(ap_Payable.getRelatedValues());
//		   //本位币在途金额
//		   if(ap_Payable.getAmountPaid() > 0){
//			   lcls_CreditLetterSettlement.setOnwayAmount(ap_Payable.getAmountRemain());
//		   } else {
//			   lcls_CreditLetterSettlement.setOnWayAmount(0);
//		   }
//		   //外币在途金额
//		   //lpcs_PrjContractSettlement.setOnwayAmountF(0);
//		   reportDao.update(lcls_CreditLetterSettlement);
//	   }
	   /**
	     * 将付款记录的明细逐个加入到待支付记录中.
	     * @param lpcs_PrjContractSettlement 付款单实例. 一般来说，该付款单应该是已经审批通过的.
	     * @return
	     */
	    public void addPayableRecords(CreditLetterSettlement settlement) {
	        if (settlement == null) {
//	            printMsg("addPayableRecords()","acls_CreditLetterSettlement is null");
	            return;
	        }
	        // 设置开证申请记录付款状态（避免重复付款）
	        CreditLetterApply apply = this.creditLetterApplyService.get(CommonUtil.trim(settlement.getCreditLetterId()));
	        if (apply != null) {
	        	apply.setSettlementStatus(CreditLetterApply.SETTLEMENTED);
	        	this.creditLetterApplyService.update(apply);
	        }
	        
//	        Payable lpayable_P = new Payable(-1);
//	        lpayable_P.initId();
//	        lpayable_P.setApplierId(acls_CreditLetterSettlement.getPaidById());
//	        lpayable_P.setApplierName(AppProfile.getInstance().getPersonName(acls_CreditLetterSettlement.getPaidById()));
//	        lpayable_P.setDataViewerUrl(getDataViewerUrl(acls_CreditLetterSettlement));
//	        lpayable_P.setDeptId(acls_CreditLetterSettlement.getPayerDeptId());
//	        lpayable_P.setRelatedRecord(acls_CreditLetterSettlement);
//	        lpayable_P.setRelatedRecNo(acls_CreditLetterSettlement.getCreditSettlementOrderNo());
//	        lpayable_P.setAuditedById(acls_CreditLetterSettlement.getAuditedById());
//	        lpayable_P.setAuditedBy(AppProfile.getInstance().getPersonName(acls_CreditLetterSettlement.getAuditedById()));
////	      摘要内容
//	        String ls_Summary =  " 信用证号：" + CreditLetterApplyBO.getInstance().getCreditLetterApply(
//	        		acls_CreditLetterSettlement.getCreditLetterId()).getCreditLetterNo()
//	        		+  "(币别:" + CommonUtil.trim(acls_CreditLetterSettlement.getCurrencyType()) + ",金额:"+CommonUtil.format(acls_CreditLetterSettlement.getAmountSettlement(), "###,###.00")+")";
//	        lpayable_P.setPayableSummary(ls_Summary);
//	        //加入待支付记录列表中.
//	        List llist_PayableRecs = lpayable_P.getPayableRecords();
//	        List ll_CreditLetterSettlements = acls_CreditLetterSettlement.getCreditLetterSettlementRecs();
//	        
//	        for (int i = 0; i < ll_CreditLetterSettlements.size(); i++) {
//	        	CreditLetterSettlementRec lcls_PCreditLetterSettlementRec = (
//	        			CreditLetterSettlementRec)ll_CreditLetterSettlements.get(i);
//	            
//	            //新建一个待支付记录实例
//	            PayableRecord lpayablerec_R = PayableClient.newCreditSetlPayableRecord(
//	            		acls_CreditLetterSettlement,lcls_PCreditLetterSettlementRec,lcls_PCreditLetterSettlementRec);
//	            
//	            lpayablerec_R.setActualPayDeptId(CreditLetterApplyBO.getInstance().getCreditLetterApply(
//	                    		acls_CreditLetterSettlement.getCreditLetterId()).getDeptId());
//	            lpayablerec_R.setRelatedRecNo(acls_CreditLetterSettlement.getCreditSettlementOrderNo());
//	           
//	            lpayablerec_R.setPayableSummary(ls_Summary);
//	            lpayablerec_R.setDataViewerUrl(getDataViewerUrl(acls_CreditLetterSettlement));
//	            lpayablerec_R.setPostPaymentHandler(PaymentCompletedHandler.class);
//	            
//	            //更新到待支付明细List中
//	            lpayablerec_R.setPayableId(lpayable_P.getPayableId());            
//	            llist_PayableRecs.add(lpayablerec_R);
//	        } 
//	         
//	        //刷新需要支付的金额总和等信息并保存到数据库中
//	        lpayable_P.refreshStatus();
	    }
	   /**
	    * 累计付款总额.
	    * @param ap_Payable
	    * @author ts
	    */
//	   public void refreshRealSettlementAmount(Payable ap_Payable){
//		   CreditLetterSettlement lcls_CreditLetterSettlement = this.reportDao.get(ap_Payable.getRelatedValues());
//		   //本位币已付款总额
//		   lcls_CreditLetterSettlement.setRealSettlementAmount(ap_Payable.getAmountPaid());
//		   //外币已付款总额
//		   List <PayableRecord> payableRecordList = ap_Payable.getPayableRecords();
//		   double settlementAmountF = 0;
//		   for(PayableRecord payableRecord:payableRecordList){
//			   settlementAmountF = payableRecord.getAmountPaidF();
//		   }
//		   lcls_CreditLetterSettlement.setRealSettlementAmountF(settlementAmountF);
//		   reportDao.update(lcls_CreditLetterSettlement);
//	   }
	
	/**
	 * 根据当前数据库中的序列号情况以及Manager实例中的序列号情况返回一个新的序列号.
	 * 在返回该新的序列号之后，内置的序列号计数器自动加1
	 * @return 最新的序列号
	 */
	public synchronized int generateNewIndex() {
		int li_LatestIndexInDB = getLatestIndexInDB();
		if (li_LatestIndexInDB > this.ii_PaymentIndex) ii_PaymentIndex = li_LatestIndexInDB;
		ii_PaymentIndex++;
		return ii_PaymentIndex;
	}

	/**
	 * 从信用证付款单中取得本年度的最大付款序列号.
	 * 
	 * @return
	 */
	private int getLatestIndexInDB() {
		String ls_SQL = "select max(credit_settlement_order_no) as lastest_id "
				+ " from tpc_credit_letter_settlement ";
		List<String> list = this.reportDao.sqlQuery(ls_SQL, null);
		if (list != null && list.size() > 0 && list.get(0) != null) {
			return Integer.valueOf(StringUtils.trim(list.get(0)));
		} else return 0;
	}

	/**
	 * 根据项目实例信用证付款.
	 * @param
	 * @return
	 */
	public double getCreditLetterAmountByprj(String projectId){
		double ld_CreditLetterAmount = 0;
		String ls_SQL = "select settlement.real_settlement_amount real,settlement.on_way_amount onWay" 
         + " from pc_credit_letter_settlement settlement ,pc_credit_letter_apply apply " 
         + " where apply.prj_id = ? and apply.credit_letter_id = settlement.credit_letter_id "
         + " and settlement.settlement_status = " + CreditLetterSettlement.CreditLetterSettlementStatus.COMPLETE
         +" and settlement.control_status_code='"+CreditLetterSettlement.EFFECTIV+"'"; 
        List<Object[]> list = this.reportDao.sqlQuery(ls_SQL,null,projectId);
		for (Object[] objs : list) {
			ld_CreditLetterAmount += ((Double)objs[0]).doubleValue() + ((Double)objs[1]).doubleValue();
		}
		return ld_CreditLetterAmount;
	}
	
	/**
	 * 根据项目部门名称信用证付款.
	 * @param
	 * @return
	 */
	public double getCreditLetterAmount(String as_PrjDeptName){
		double ld_CreditLetterAmount = 0;
		String ls_SQL = "select settlement.real_settlement_amount real,settlement.on_way_amount onWay" 
         + " from pc_credit_letter_settlement settlement ,pc_credit_letter_apply apply " 
         + " where apply.dept_name = ? and apply.credit_letter_id = settlement.credit_letter_id "
         + " and settlement.settlement_status = " + CreditLetterSettlement.CreditLetterSettlementStatus.COMPLETE
         + " and settlement.control_status_code='"+CreditLetterSettlement.ControlStatus.EFFECTIV+"'";
		List<Object[]> list = this.reportDao.sqlQuery(ls_SQL,null,as_PrjDeptName);
		for (Object[] objs : list) {
			ld_CreditLetterAmount += ((Double)objs[0]).doubleValue() + ((Double)objs[1]).doubleValue();
		}
		return ld_CreditLetterAmount;
	}

}
