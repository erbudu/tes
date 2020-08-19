package com.supporter.prj.cneec.tpc.invoice.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.invoice.dao.InvoiceDao;
import com.supporter.prj.cneec.tpc.invoice.dao.InvoiceRecDao;
import com.supporter.prj.cneec.tpc.invoice.entity.Invoice;
import com.supporter.prj.cneec.tpc.invoice.entity.InvoiceRec;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.service.PrjContractSettlementService;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.prj_contract_table.util.PrjContractTableConstant;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
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
public class InvoiceService {
	
	@Autowired
	private RegisterProjectService registerProjectService;
	@Autowired
	private PrjContractTableService prjContractTableService;
	@Autowired
	private PrjContractSettlementService prjContractSettlementService;
	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private InvoiceRecDao invoiceRecDao;

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public Invoice get(String invoiceId) {
		return invoiceDao.get(invoiceId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public Invoice initEditOrViewPage(String invoiceId, String contractId, String projectId, UserProfile user) {
		if (StringUtils.isBlank(invoiceId)) {// 新建
			PrjContractTable apc_PrjContract = this.prjContractTableService.get(contractId);
			RegisterProject ap_Project = this.registerProjectService.get(projectId);
			Invoice invoice = newInvoice(user, ap_Project, apc_PrjContract);
			return invoice;
		} else {// 编辑
			Invoice invoice =  invoiceDao.get(invoiceId);
			//如果有旧系统的流程，则获取旧系统的procId
//			long oldProcId = cneecExamService.getProcIdByRecord(invoice);
//			if (oldProcId > 0)invoice.setOldProcId(oldProcId);
			
			invoice.setInvoiceRecList(invoiceRecDao.getRecList(invoiceId));
			//获取记录总数
			invoice.setRecordCount(invoice.getInvoiceRecList().size());
			return invoice;
		}
	}
	
	/**
	 * 进入new页面.
	 * @param moduleId
	 * @return
	 */
	public Invoice viewPage(String invoiceId, UserProfile user) {
			Invoice invoice =  newInvoice(user);
			//如果有旧系统的流程，则获取旧系统的procId
//			long oldProcId = cneecExamService.getProcIdByRecord(invoice);
//			if (oldProcId > 0)invoice.setOldProcId(oldProcId);
//			invoice.setInvoiceRecList(invoiceRecDao.getRecList(invoiceId));
			//获取记录总数
//			invoice.setRecordCount(invoice.getInvoiceRecList().size());
			return invoice;
	}
	
	/**
	 * 根据业务对象获取流程实例ID.
	 * @param invoice
	 * @return
	 */
	public String getProcId(Invoice invoice){
		if (invoice == null) return "";
		return EIPService.getWfService().getProcIdByRecord(invoice);
	}
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public Invoice newInvoice(UserProfile ausrprf_U, RegisterProject ap_Project, PrjContractTable apc_PrjContract){
        Invoice li_Invoice = new Invoice();
        li_Invoice.setPrjId(ap_Project.getProjectId());
		li_Invoice.setPrjName(ap_Project.getProjectName());
		li_Invoice.setContractorName(CommonUtil.trim(apc_PrjContract.getContractParty()));
        li_Invoice.setContractId(apc_PrjContract.getContractId());
		li_Invoice.setApplyPerson(ausrprf_U.getName());
		li_Invoice.setApplyPersonId(ausrprf_U.getPersonId());
		li_Invoice.setApplyDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
		Dept dept = ausrprf_U.getDept();
        if(dept != null){
        	li_Invoice.setDeptId(dept.getDeptId());
        	li_Invoice.setDeptName(dept.getName());
        }
//		lcls_Invoice.setUserProfile(ausrprf_U);
        
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );  
        String datestr = sdf.format( new  Date());  
        
        li_Invoice.setCreatedById(ausrprf_U.getPersonId());
        li_Invoice.setCreatedBy(ausrprf_U.getName());
        li_Invoice.setCreatedDate(datestr);
        
        li_Invoice.setModifiedById(ausrprf_U.getPersonId());
        li_Invoice.setModifiedBy(ausrprf_U.getName());
        li_Invoice.setModifiedDate(datestr);
        
        
        li_Invoice.setInvoiceStatus(Invoice.Status.DRAFT);
        li_Invoice.setInvoiceId(com.supporter.util.UUIDHex.newId());
        return li_Invoice;
    }
    public Invoice newInvoice(UserProfile ausrprf_U){
        Invoice li_Invoice = new Invoice();
        return li_Invoice;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<Invoice> getGrid(UserProfile user, JqGrid jqGrid, Invoice invoice) {
		List<Invoice> list = invoiceDao.findPage(user, jqGrid, invoice);
//		for(Invoice settlement : list){
//			CreditLetterApply apply = creditLetterApplyService.get(settlement.getCreditLetterId());
//			if(apply != null){
//				settlement.setCreditLetterApply(apply);
//				settlement.setProjectName(apply.getProjectName());
//				settlement.setContractNo(apply.getContractNo());
//			}
//		}
		return list;
	}

	/**
	 * 分页表格展示信用证付款明细.
	 * @param jqGrid
	 * @param creditLetterSettlementId
	 * @return
	 */
	public List<InvoiceRec> getRecGrid(UserProfile user,JqGrid jqGrid,String creditLetterSettlementId) {
		List<InvoiceRec> list = invoiceRecDao.getRecGrid(jqGrid, creditLetterSettlementId);
		if (list != null && list.size() > 0) {
			for (InvoiceRec rec : list) {
				PrjContractTable contract = this.prjContractTableService.get(rec.getContractId());
				if (contract != null) {
					rec.setContractNo(contract.getContractNo());
					rec.setContractName(contract.getContractName());
				}
				rec.setContractBudgetName("");
			}
		}
		return invoiceRecDao.getRecGrid(jqGrid, creditLetterSettlementId);
	}
	
	/**
	 * 分页表格展示数据.
	 * 历次信息证付款信息
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @return JqGrid
	 */
	public List<Invoice> getPastGrid(UserProfile user, JqGrid jqGrid, Invoice invoice, String contractId) {
		return invoiceDao.findPagePast(user, jqGrid, invoice, contractId);
	}
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public OperResult<Invoice> saveOrUpdate(UserProfile user, Invoice invoice, Map<String, Object> valueMap) {
		Invoice ret = invoiceDao.get(invoice.getInvoiceId());
		if (ret == null) {// 新建
			this.invoiceDao.save(invoice);
			if (CollectionUtils.isNotEmpty(invoice.getInvoiceRecList())) {
				saveCLSRList(invoice);
			}
		} else {// 编辑
			this.invoiceDao.clear();
			ret.setInvoiceAmount(invoice.getSAll());
			ret.setInvoiceNo(invoice.getInvoiceNo());
			ret.setInvoiceType(invoice.getInvoiceType());
			ret.setAuditedById(invoice.getAuditedById());
			ret.setAuditedBy(invoice.getAuditedBy());
			ret.setApplyPersonId(invoice.getApplyPersonId());
			ret.setApplyPerson(invoice.getApplyPerson());
			ret.setDeptId(invoice.getDeptId());
			ret.setDeptName(invoice.getDeptName());
			ret.setApplyDate(invoice.getApplyDate());
			this.invoiceDao.update(ret);
			if (CollectionUtils.isNotEmpty(invoice.getInvoiceRecList())) {
				saveCLSRList(invoice);
			}
		}
		// 记录日志
//		String logMessage = MessageFormat.format(LogConstant.PUBLISH_REPORT_LOG_MESSAGE, report.getReportTitle());
//		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage, report, null);
		return OperResult.succeed("saveSuccess", null, invoice);
	}

	/**
	 * 保存出国员工
	 * @param abroad
	 */
	public void saveCLSRList(Invoice invoice){
		//先删除，再保存
		deleteR(invoice.getDelIds());
		List<InvoiceRec> clsRList = invoice.getInvoiceRecList();
		if(CollectionUtils.isNotEmpty(clsRList)){
			for(InvoiceRec ap:clsRList){
				ap.setInvoiceId(invoice.getInvoiceId());
//				//如果从预算项里找不到名称，则从项目评审类型PRJ_BUDGET_TYPE码表里找名称
//				if(StringUtils.isNotBlank(ap.getItemId())){
//					String projectId = getPrjId(report.getCreditLetterId());
//					String itemName = "";
//					if(budgetItemService.isExitBudgetItem(projectId,BudgetItem.ITEM_PRJ_CATEGORY_SETTLEMENT)){
//						Map<String, String> map = budgetItemService.getBudgetItemCodeTable(projectId, BudgetItem.ITEM_PRJ_CATEGORY_SETTLEMENT);
//   	    			    itemName = map.get(ap.getItemId());
//   	    		    }else{
//   	    			    itemName = getPrjMainContractCostType().get(ap.getItemId());
//   	    		    }
//					ap.setItemName(itemName);
//				}
//				ap.setItemGroupId(ap.getItemId());
//				
//				Date ld_Year = new Date();					
//				if(ap.getBudgetMonth() == 0){
//					String ls_Month = CommonUtil.formatDate(ld_Year,"MM");
//					ap.setBudgetMonth(Integer.parseInt(ls_Month));
//				}
//				if(ap.getBudgetYear()==0){
//					String ls_Year = CommonUtil.formatDate(ld_Year,"yyyy");
//					ap.setBudgetYear(Integer.parseInt(ls_Year));
//				}
//				if(ap.getUsableAmount() == null){
//					ap.setUsableAmount(0.0);
//				}
//				if(ap.getUseSumAmount() == null){
//					ap.setUseSumAmount(0.0);
//				}
				if (ap.getRecordId() == null || "".equals(ap.getRecordId())) {
					ap.setRecordId(com.supporter.util.UUIDHex.newId());
					this.invoiceRecDao.save(ap);
				} else {
					this.invoiceRecDao.update(ap);
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
				InvoiceRec ap = this.invoiceRecDao.get(delId);
				if (ap != null) {
					this.invoiceRecDao.delete(delId);
				}
			}
		}
	}

	/**
	 * 财务费用补录
	 * @param user
	 * @param invoice
	 * @param valueMap
	 * @return
	 */
	public OperResult<Invoice> saveOrUpdateFinanceDepartmentAdd(UserProfile user, Invoice invoice, Map<String, Object> valueMap) {
		saveOrUpdate(user, invoice, valueMap);
		this.invoiceDao.clear();
		invoice.setInvoiceStatus(Invoice.Status.FINANCE_DEPARTMENT_ADD);
		this.update(invoice);
		return OperResult.succeed("saveSuccess", null, invoice);
	}

	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public Invoice update(Invoice invoice) {
			this.invoiceDao.update(invoice);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_EDIT.getOperName(), null);

		return invoice;

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
			Invoice p = invoiceDao.get(creditLetterSettlementId);
//			if(p.getIsEffectiv()){
//				if(p.getIsPartlyPaid()||p.getIsCompletedPaid()){
//					//如果已经部分支付或全部支付，不进行操作。
//				}else{
//					p.setControlStatus(Invoice.ControlStatus.FAILURE_DESC);
//					p.setControlStatusCode(Invoice.ControlStatus.FAILURE);
//					p.setOnWayAmount(0);
//					p.setOnWayAmountF(0);
//					p.setRealSettlementAmount(0);
//					p.setRealSettlementAmountF(0);
//					invoiceDao.update(p);
//					this.cancelPayableRecords(p);
//				}
//			}else{
//				p.setControlStatus(Invoice.ControlStatus.EFFECTIV_DESC);
//				p.setControlStatusCode(Invoice.ControlStatus.EFFECTIV);
//				p.setOnWayAmount(p.getAmountSettlementAct());
//				p.setPaymentStatus(Invoice.PaymentStatus.UNPAID.getKey());
//				invoiceDao.update(p);
//				this.unpaidPayableRecords(p);
//			}
		}
	}
	/**
     * 支付状态为“取消支付”.
     */
    public void cancelPayment(String id){
    	Invoice lcls_Invoice = this.invoiceDao.get(id);
//    	lcls_Invoice.setInvoiceStatus(Invoice.Status.CANCEL_PAID);
    	invoiceDao.update(lcls_Invoice);
    }
	/**
	 * 失效后清除待支付
	 * @param ps
	 */
	@SuppressWarnings("unchecked")
	public void cancelPayableRecords(Invoice ps){
		String sql = "select payable_id from pc_payable t where t.related_table='pc_credit_letter_settlement' and t.related_fields='credit_letter_settlement_id' and t.related_values='"+String.valueOf(ps.getInvoiceId())+"'";
//		DataSet ds = SQLQuery.retrieve(sql);
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
    public void unpaidPayableRecords(Invoice acls_CreditLetterSettlement) {
    	String sql = "select payable_id from pc_payable t where t.related_table='pc_credit_letter_settlement' and t.related_fields='credit_letter_settlement_id' and t.related_values = ?";
//		DataSet ds = SQLQuery.retrieve(sql, String.valueOf(acls_CreditLetterSettlement.getInvoiceId()));
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
	public void delete(UserProfile user, String invoiceIds) {
		if (StringUtils.isNotBlank(invoiceIds)) {
			for (String invoiceId : invoiceIds.split(",")) {
				this.invoiceRecDao.deleteBySettlementId(invoiceId);
				this.invoiceDao.delete(invoiceId);
			}
			// 记录日志
//			String logMessage = MessageFormat.format(
//					LogConstant.PUBLISH_REPORT_LOG_MESSAGE, invoiceIds);
//			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
//					user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage,
//					null, null);
		}
	}

	/**
	 * 根据项目下某合同合同对方查询所有合同
	 * @param projectId
	 * @param contractId
	 * @return
	 */
	public Map<String, String> getFBContractCodeTable(String projectId, String contractId) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		String contractPartyId = this.prjContractTableService.get(contractId).getContractPartyId();
		String ls_SQL = "from PrjContractTable where projectId = ? and contractPartyId = ? order by contractNo";
		List<PrjContractTable> list = this.invoiceDao.find(ls_SQL, projectId, contractPartyId);
		for (PrjContractTable contract : list) {
			map.put(contract.getContractId(), contract.getContractNo() + contract.getContractName());
		}
		return map;
	}

	/**
	 * 根据执行中项目获取采购合同列表(根据供应商id除重).
	 * 
	 * @param ap_Project
	 *            执行中项目实例.
	 * @retrun List 合同集包括（采购合同）,每一项都是一个合同实例.
	 */
	public Map<String, String> getSubPrjContracts(String projectId, UserProfile userProfile) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (CommonUtil.trim(projectId).length() > 0) {
			List<PrjContractTable> ll_PrjContracts = new ArrayList<PrjContractTable>();
			List<PrjContractTable> contractList = new ArrayList<PrjContractTable>();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("projectId", projectId);
			parameters.put("contractTypeCode", PrjContractTableConstant.PURCHASE);
			List<PrjContractTable> list = this.prjContractTableService.getPrjContractTables(parameters, userProfile);
			contractList.addAll(list);
			parameters.put("contractTypeCode", PrjContractTableConstant.DERIVATIVE);
			list = this.prjContractTableService.getPrjContractTables(parameters, userProfile);
			contractList.addAll(list);
			for (PrjContractTable contract : contractList) {
				String trem_vendorId = CommonUtil.trim(contract.getContractPartyId());
				int count = 0;
				for (int l = 0; l < ll_PrjContracts.size(); l++) {
					PrjContractTable tpc = ll_PrjContracts.get(l);
					if (trem_vendorId.equals(CommonUtil.trim(tpc.getContractPartyId()))) {
						count++;
					}
				}
				if (count > 0) continue;
				ll_PrjContracts.add(contract);
			}

			for (PrjContractTable contract : ll_PrjContracts) {
				map.put(contract.getContractId(), contract.getContractParty());
			}
		}
		return map;
	}

	/**
	 * 累计收到发票金额（审批完成、财务补录）
	 * @return
	 */
	public double getTotalInvoiceAmount(String contractId) {
		return invoiceRecDao.getTotalInvoiceAmount(contractId);
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, Double> getInvoiceAmount(String contractId) {
		Map<String, Double> map = new LinkedHashMap<String, Double>();
		double sumSettlementAmount = 0, sumInvoiceAmount = 0, contractAmount = 0;
		// 累计采购合同付款金额（所有结成功的结算单金额总和）
		sumSettlementAmount = prjContractSettlementService.getTotals(contractId, TpcConstant.STANDARD_CURRENCY, null).get(1);
		// 合同累计收到发票金额（审核中、审批完成、财务补录）
		sumInvoiceAmount = invoiceRecDao.getDetailInvoiceAmount(contractId);
		// 采购合同总金额
		PrjContractTable contract = prjContractTableService.get(contractId);
		contractAmount = contract.getContractRmbAmount();
		map.put("sumSettlementAmount", sumSettlementAmount);
		map.put("sumInvoiceAmount", sumInvoiceAmount);
		map.put("contractAmount", contractAmount);
		return map;
	}

}
