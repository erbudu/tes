package com.supporter.prj.cneec.tpc.out_invoice.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.collection_confirmation.service.CollectionConfirmationService;
import com.supporter.prj.cneec.tpc.credit_letter_apply.entity.CreditLetterApply;
import com.supporter.prj.cneec.tpc.credit_letter_apply.service.CreditLetterApplyService;
import com.supporter.prj.cneec.tpc.out_invoice.dao.OutInvoiceDao;
import com.supporter.prj.cneec.tpc.out_invoice.entity.OutInvoice;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
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
public class OutInvoiceService {
	
 
	@Autowired
	private RegisterProjectService registerProjectService;
	@Autowired
	private PrjContractTableService prjContractTableService;
	@Autowired
	private CreditLetterApplyService creditLetterApplyService;
	@Autowired
	private OutInvoiceDao reportDao;
	@Autowired
	private CollectionConfirmationService collectionConfirmationService;

	
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
	public OutInvoice get(String reportId) {
		return reportDao.get(reportId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public OutInvoice initEditOrViewPage(String reportId, String contractId, String projectId, UserProfile user) {
		if (StringUtils.isBlank(reportId)) {// 新建
			OutInvoice report = newOutInvoice(user, projectId, contractId);
			return report;
		} else {// 编辑
			OutInvoice report =  reportDao.get(reportId);
			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );  
			try{
				Date date = sdf.parse(report.getModifiedDate()); 
				String ls_Year = CommonUtil.formatDate(date,"yyyy");
				report.setYear(Integer.parseInt(ls_Year));
				String ls_month = CommonUtil.formatDate(date,"MM");
				report.setMonth(Integer.parseInt(ls_month));
				String ls_day = CommonUtil.formatDate(date,"dd");
				report.setDay(Integer.parseInt(ls_day));
			}catch(ParseException e){
				e.printStackTrace();
			}
			//如果有旧系统的流程，则获取旧系统的procId
//			long oldProcId = cneecExamService.getProcIdByRecord(report);
//			if (oldProcId > 0)report.setOldProcId(oldProcId);
			
			return report;
		}
	}
	
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public OutInvoice viewPage(String reportId, UserProfile user) {
		OutInvoice report =  new OutInvoice();
			//OutInvoice report =  reportDao.get(reportId);
			
			//如果有旧系统的流程，则获取旧系统的procId
//			long oldProcId = cneecExamService.getProcIdByRecord(report);
//			if (oldProcId > 0)report.setOldProcId(oldProcId);
			return report;
	}
	
	/**
	 * 根据业务对象获取流程实例ID.
	 * @param report
	 * @return
	 */
	public String getProcId(OutInvoice report){
		if (report == null)return "";
		return EIPService.getWfService().getProcIdByRecord(report);
	}
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public OutInvoice newOutInvoice(UserProfile ausrprf_U, String projectId, String contractId){
        OutInvoice outInvoice = new OutInvoice();
        outInvoice.setPrjId(projectId);
		outInvoice.setContractId(contractId);
		outInvoice.setBegforIvoiceOutAmount(getBeforeIvoiceOutAmount(contractId));
		RegisterProject project = this.registerProjectService.get(projectId);
		if (project != null) {
			outInvoice.setPrjName(project.getProjectName());
		}
		PrjContractTable contract = this.prjContractTableService.get(contractId);
		if (contract != null) {
			outInvoice.setContractName(contract.getContractName());
			outInvoice.setContractNo(contract.getContractNo());
			outInvoice.setContractActMount(contract.getContractRmbAmount());
		}
		Dept dept = ausrprf_U.getDept();
        if(dept != null){
        	outInvoice.setDeptId(dept.getDeptId());
        	outInvoice.setDeptName(dept.getName());
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );  
        String datestr = sdf.format( new  Date());  
        
        outInvoice.setCreatedById(ausrprf_U.getPersonId());
        outInvoice.setCreatedBy(ausrprf_U.getName());
        outInvoice.setCreatedDate(datestr);
        
        outInvoice.setModifiedById(ausrprf_U.getPersonId());
        outInvoice.setModifiedBy(ausrprf_U.getName());
        outInvoice.setModifiedDate(datestr);
        
        Date ld_Year = new Date();					
		String ls_Year = CommonUtil.formatDate(ld_Year,"yyyy");
		outInvoice.setYear(Integer.parseInt(ls_Year));
		String ls_month = CommonUtil.formatDate(ld_Year,"MM");
		outInvoice.setMonth(Integer.parseInt(ls_month));
		String ls_day = CommonUtil.formatDate(ld_Year,"dd");
		outInvoice.setDay(Integer.parseInt(ls_day));
        
        outInvoice.setInvoiceStatus(OutInvoice.Status.DRAFT);
        outInvoice.setInvoiceOutId(com.supporter.util.UUIDHex.newId());
        return outInvoice;
    }
    
	/**
	 * 获取本合同已开发票金额.
	 * @param contractId
	 * @return
	 */
	private double getBeforeIvoiceOutAmount(String contractId) {
		String sql = "select sum(now_invoice_out_amount) as sum_amount from tpc_out_invoice where contract_id = ?"
			+ " and invoice_status = " + OutInvoice.Status.COMPLETE;
		List<BigDecimal> sumAmount = this.reportDao.sqlQuery(sql,null, contractId);
		System.out.println("contractId="+contractId);
		if (sumAmount != null && sumAmount.size() > 0 && sumAmount.get(0) != null) {
			return sumAmount.get(0).doubleValue();
		}
		return 0;
	}
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<OutInvoice> getGrid(UserProfile user, JqGrid jqGrid, OutInvoice report) {
		List<OutInvoice> list = reportDao.findPage(user, jqGrid, report);
		for(OutInvoice settlement : list){
//			CreditLetterApply apply = creditLetterApplyService.get(settlement.getCreditLetterId());
//			if(apply != null){
//				settlement.setCreditLetterApply(apply);
//				settlement.setProjectName(apply.getProjectName());
//				settlement.setContractNo(apply.getContractNo());
//			}
		}
		return list;
	}

	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public OperResult<OutInvoice> saveOrUpdate(UserProfile user, OutInvoice report, Map< String, Object > valueMap) {
		OutInvoice ret = reportDao.get(report.getInvoiceOutId());
		if (ret == null) {// 新建
			this.reportDao.save(report);
		} else {// 编辑
			this.reportDao.clear();
			this.reportDao.update(report);
		}
		// 记录日志
//		String logMessage = MessageFormat.format(
//				LogConstant.PUBLISH_REPORT_LOG_MESSAGE, report.getReportTitle());
//		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
//				user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage,
//				report, null);
		return OperResult.succeed("saveSuccess", null,
				report);

	}
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public OutInvoice update(OutInvoice report) {
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
			OutInvoice p = reportDao.get(creditLetterSettlementId);
//			if(p.getIsEffectiv()){
//				if(p.getIsPartlyPaid()||p.getIsCompletedPaid()){
//					//如果已经部分支付或全部支付，不进行操作。
//				}else{
//					p.setControlStatus(OutInvoice.ControlStatus.FAILURE_DESC);
//					p.setControlStatusCode(OutInvoice.ControlStatus.FAILURE);
//					p.setOnWayAmount(0);
//					p.setOnWayAmountF(0);
//					p.setRealSettlementAmount(0);
//					p.setRealSettlementAmountF(0);
//					reportDao.update(p);
//					this.cancelPayableRecords(p);
//				}
//			}else{
//				p.setControlStatus(OutInvoice.ControlStatus.EFFECTIV_DESC);
//				p.setControlStatusCode(OutInvoice.ControlStatus.EFFECTIV);
//				p.setOnWayAmount(p.getAmountSettlementAct());
//				p.setPaymentStatus(OutInvoice.PaymentStatus.UNPAID.getKey());
//				reportDao.update(p);
//				this.unpaidPayableRecords(p);
//			}
		}
	}
	/**
     * 支付状态为“取消支付”.
     */
    public void cancelPayment(String id){
    	OutInvoice lcls_OutInvoice = this.reportDao.get(id);
//    	lcls_OutInvoice.setPaymentStatus(OutInvoice.PaymentStatus.CANCEL_PAID.getKey());
    	reportDao.update(lcls_OutInvoice);
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
	 * 根据执行中项目获取采购合同列表.
	 * @param ap_Project 执行中项目实例.
	 * @retrun List 合同集包括（采购合同）,每一项都是一个合同实例.
	 */
	public Map<String, String> getSubPrjContracts(String projectId, UserProfile userProfile) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (CommonUtil.trim(projectId).length() > 0) {
			map = this.prjContractTableService.getPurchaseContractByPrjId(projectId, userProfile);
		}
		return map;
	}
	
	/**
	 * 获取销售合同下客户名称和第三方收款单位名称
	 * @param contractId
	 * @return
	 */
	public Map<String, String> getOwnerNameMap(String contractId){
		Map<String, String> map = new LinkedHashMap<String, String>();
		//获取客户名称
		String customName = this.prjContractTableService.get(contractId).getCustomerName();
		map.put(customName, customName);
		//获取销售合同收款下所有第三方单位名称
		List<String> companyNames = this.collectionConfirmationService.getCompanyNames(contractId);
		if (companyNames != null) {
			for (String companyName : companyNames) {
				map.put(companyName, companyName);
			}
		}
		return map;
	}

}
