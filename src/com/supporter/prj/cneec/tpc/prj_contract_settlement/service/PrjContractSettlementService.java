package com.supporter.prj.cneec.tpc.prj_contract_settlement.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.credit_letter_settlement.dao.CreditLetterSettlementDao;
import com.supporter.prj.cneec.tpc.invoice.service.InvoiceService;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.dao.PrjContractSettlementDao;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.dao.PrjContractSettlementProveDao;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.dao.PrjContractSettlementRecDao;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlement;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlementProve;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlementRec;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.util.CommonUtils;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractAmount;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractCollectionTerms;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.supplier.dao.SupplierBankAccountDao;
import com.supporter.prj.cneec.tpc.supplier.entity.SupplierBankAccount;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.util.JsonUtil;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

@Service
public class PrjContractSettlementService {

	@Autowired
	private PrjContractSettlementDao prjContractSettlementDao;//采购合同付款
	@Autowired 
	private PrjContractSettlementRecDao contractSettlementRecDao;//付款明细
	@Autowired
	private SupplierBankAccountDao bankAccountDao;//采购合同对应供方收款账户银行
	@Autowired
	private PrjContractTableService prjContractTableService;//正式合同
	@Autowired
	private RegisterProjectService projectService;//项目
	@Autowired
	private InvoiceService invoiceService;//收到发票
	@Autowired
	private PrjContractSettlementProveDao proveDao;//付款证明附件
	@Autowired
	private CreditLetterSettlementDao creditLetterSettlementDao;//合计费用
	
	private int ii_PaymentIndex = 0;//当前的付款单序列号（本年度） .

	/**
	 * 分页表格展示数据.
	 * @param user
	 * @param jqGrid
	 * @param attr
	 * @param prjId
	 * @param settlementStatus
	 * @param controlStatus
	 * @return
	 */
	public List<PrjContractSettlement> getGrid(UserProfile user, JqGrid jqGrid, String attr,String prjId,String settlementStatus,String controlStatus){
		List<PrjContractSettlement> list = prjContractSettlementDao.findPage(jqGrid, attr, prjId, settlementStatus, controlStatus);
		for(int i = 0; i<list.size();i++){
			PrjContractSettlement element = list.get(i);
			PrjContractTable contract = this.prjContractTableService.get(element.getContractId());
			if (contract != null) {
				element.setActAmount(contract.getContractRmbAmount());
			}
			list.set(i, element);
		}
		jqGrid.setRows(list);
		return list;
	}
	
	/**
	 * 获取可退款采购合同付款单LIST
	 * 
	 * @param projectId
	 * @param contractId 销售合同ID
	 * @return
	 */
	public List<PrjContractSettlement> getRefundList(String projectId, String contractId) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(projectId)) {
			params.put("prjId", projectId);
		}
		if (StringUtils.isNotBlank(contractId)) {
			params.put("contractId", contractId);
		}
		// 必须已完成
		params.put("settlementStatus", PrjContractSettlement.SettlementStatus.COMPLETE);
		// 未退款、退款中、部分退款完成
		params.put("refundStatus", new Integer[] { PrjContractSettlement.REFUND, PrjContractSettlement.REFUNDING, PrjContractSettlement.REFUNDED_PARTIAL });
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("createdDate", true);
		return this.prjContractSettlementDao.queryByParam(params, null, orders);
	}

	/**
	 * 获取可退款采购合同付款单MAP
	 * @return
	 */
	public Map<String, String> getRefundMap(String projectId, String contractId) {
		Map<String, String> detailMap = new HashMap<String, String>();
		List<PrjContractSettlement> list = this.getRefundList(projectId, contractId);
		for (PrjContractSettlement entity : list) {
			detailMap.put(entity.getSettlementId(), "（" + entity.getSettlementNo() + "）-" + entity.getContractName());
		}
		return detailMap;
	}


	/**
	 * 从表LIST
	 * @param settlementId
	 * @return
	 */
	public List<PrjContractSettlementRec> getRefundDetailList(String settlementId) {
		List<PrjContractSettlementRec> list = new ArrayList<PrjContractSettlementRec>();
		List<PrjContractSettlementRec> detailList = this.contractSettlementRecDao.findBy("settlementId", settlementId);
		for (PrjContractSettlementRec detail : detailList) {
			if (detail.canRefund()) {
				list.add(detail);
			}
		}
		return list;
	}

	/**
	 * 从表Map
	 * @param settlementId
	 * @return
	 */
	public Map<String, String> getRefundDetailMap(String settlementId) {
		Map<String, String> detailMap = new HashMap<String, String>();
		List<PrjContractSettlementRec> detailList = this.getRefundDetailList(settlementId);
		for (PrjContractSettlementRec detail : detailList) {
			detailMap.put(detail.getRecordId(), detail.getPaymentTerms());
		}
		return detailMap;
	}

	/**
	 * 新建页面选择银行时，根据合同的供方Id及收款单位获得开户银行
	 * @param supplierId
	 * @param gatheringUnit
	 * @return
	 */
	public Map<String, String> getBankCodeTable(String supplierId, String gatheringUnit) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<SupplierBankAccount> list = this.bankAccountDao.getBankBySupplierAndGatheringUnit(supplierId, gatheringUnit, null);
		for (SupplierBankAccount account : list) {
			if (StringUtils.isNotBlank(account.getBank())) {
				map.put(account.getBank(), account.getBank());
			}
		}
		return map;
	}

	/**
	 * 新建页面选择银行账户时，根据合同的供方Id及收款单位获得开户银行
	 * @param supplierId
	 * @param gatheringUnit
	 * @return
	 */
	public Map<String, String> getBankAccountCodeTable(String supplierId,String gatheringUnit,String bank){
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<SupplierBankAccount> list = this.bankAccountDao.getBankBySupplierAndGatheringUnit(supplierId, gatheringUnit,bank);
		for(SupplierBankAccount account :list){
			if(StringUtils.isNotBlank(account.getBankAccount())){
				map.put(account.getBankAccount(), account.getBankAccount());
			}
		}
		return map;
	}
	
	/**
	 * 新建时，在选择合同页面获取合同
	 * @param parameters
	 * @param userProfile
	 * @return
	 */
	public Map<String, String> getContractCodeTable(String projectId, UserProfile userProfile) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (CommonUtil.trim(projectId).length() > 0) {
			map.putAll(this.prjContractTableService.getPurchaseContractByPrjId(projectId, userProfile));
			map.putAll(this.prjContractTableService.getDerivativeContractByPrjId(projectId, userProfile));
		}
		return map;
	}

	/**
	 * 初始化添加和修改
	 * @param parameters
	 * @param userProfile
	 * @return
	 */
	public PrjContractSettlement initEditOrViewPage(Map<String, Object> parameters,UserProfile userProfile){
		String settlementId = (String) parameters.get("settlementId");
		String contractId = (String) parameters.get("contractId");
		PrjContractTable contract = null;
		PrjContractSettlement settlement = null;
			
		if(StringUtils.isNotBlank(settlementId)){//修改
			settlement = this.prjContractSettlementDao.get(settlementId);
			List<PrjContractSettlementRec> list = this.contractSettlementRecDao.getBySettlementId(settlementId);
			settlement.setMaterialList(list);
		} else {// 新建
			settlement = new PrjContractSettlement();
			settlement.setSettlementId(UUIDHex.newId());
			if(StringUtils.isNotBlank(contractId)){
				contract = this.prjContractTableService.get(contractId);
				settlement.setIsHistory(1);//需要判断apcontract_PC.getNewBudgetId()==0
				settlement.setPrjId(contract.getProjectId());
				settlement.setPrjName(contract.getProjectName());
				settlement.setContractId(contract.getContractId());
				settlement.setContractNo(contract.getContractNo());
				settlement.setContractName(contract.getContractName());
				settlement.setContractorId(contract.getContractPartyId());
				settlement.setContractorName(contract.getContractParty());//供应商名称
				settlement.setGatheringUnit(contract.getContractParty());
				RegisterProject project = this.projectService.get(settlement.getPrjId());
				if (project != null) {
					settlement.setPrjManagerId(project.getProjectChargeId());
					settlement.setPrjManager(project.getProjectCharge());
				}
				settlement.setPaidFlag(false);
				settlement.setSettlementDate(CommonUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
				settlement.setPaidById(userProfile.getPersonId());
//				settlement.setGatheringUnit(CommonUtil.trim(apcontract_PC.getGatheringUnit()));//合同表中的收款单位没有
				settlement.setPaidBy(userProfile.getName());
				settlement.setSettlementHandler(userProfile.getName());
				settlement.setSettlementHandlerId(userProfile.getPersonId());
				settlement.setSettlementYear(CommonUtil.parseInt(CommonUtil.format(new Date(),"yyyy")));
				settlement.setSettlementMonth(CommonUtil.parseInt(CommonUtil.format(new Date(),"MM")));
				settlement.setSettlementApplyDate(CommonUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
				settlement.setPayerDeptId(userProfile.getDeptId());
				settlement.setControlStatus(PrjContractSettlement.ControlStatus.EFFECTIV);
				settlement.setControlStatusCode(PrjContractSettlement.ControlStatus.EFFECTIV_DESC);
				settlement.setSettlementStatus(PrjContractSettlement.SettlementStatus.DRAFT);
				if(userProfile.getDept()!= null){
					if(StringUtils.isNotBlank(userProfile.getDept().getName())){
						settlement.setPayerDeptName(userProfile.getDept().getName());
					}
				}
				int li_Index = generateNewIndex();
				settlement.setSettlementIndex(li_Index);
				settlement.setSettlementNo("贸付字(" + Calendar.getInstance().get(Calendar.YEAR) + ")第"
						+ CommonUtil.format(li_Index, "00000") + "号");
				settlement.setCreatedBy(userProfile.getName());
				settlement.setCreatedById(userProfile.getPersonId());
				settlement.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
				settlement.setModifiedBy(userProfile.getName());
				settlement.setModifiedById(userProfile.getPersonId());
				settlement.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
			}
		}
		return settlement;
	}

	/**
	 * 打印合同付款
	 * @param settlementId
	 * @param parameters
	 * @param userProfile
	 * @return
	 */
	public PrjContractSettlement viewPrint(String settlementId, UserProfile userProfile) {
		PrjContractSettlement settlement = this.get(settlementId);
		if (settlement != null) {
			if (StringUtils.isNotBlank(settlement.getContractId())) {
				// 合同
				PrjContractTable contract = this.prjContractTableService.get(settlement.getContractId());
				settlement.setContract(contract);
				settlement.setActAmount(contract.getContractRmbAmount());
			}
			// 从表
			List<PrjContractSettlementRec> list = this.contractSettlementRecDao.getBySettlementId(settlementId);
			settlement.setMaterialList(list);
		}
		return settlement;
	}

	/**
	 * 设置打印份数
	 * @param userProfile
	 * @param settlementId
	 * @return
	 */
	public String setPrintCount(String settlementId, UserProfile userProfile) {
		String json = "{\"successful\": false, \"msg\": \"cannot get entity\"}";
		PrjContractSettlement settlement = this.get(settlementId);
		if (settlement != null) {
			settlement.setPrintCount(settlement.getPrintCount() + 1);
			this.prjContractSettlementDao.update(settlement);
			json = "{\"successful\": true, \"msg\": \"\"}";
		}
		return json;
	}

	/**
	 * 设置银行出纳
	 * @param userProfile
	 * @param settlementId
	 * @return
	 */
	public PrjContractSettlement viewByBankTeller(String settlementId, UserProfile userProfile) {
		PrjContractSettlement settlement = this.get(settlementId);
		if (settlement != null) {
			if (StringUtils.isBlank(settlement.getBankTellerIds())) {
				settlement.setBankTellerIds(userProfile.getPersonId());
				settlement.setBankTellerNames(userProfile.getName());
				this.prjContractSettlementDao.update(settlement);
			}
			if (StringUtils.isNotBlank(settlement.getContractId())) {
				// 合同
				PrjContractTable contract = this.prjContractTableService.get(settlement.getContractId());
				settlement.setContract(contract);
				settlement.setActAmount(contract.getContractRmbAmount());
			}
			// 从表
			List<PrjContractSettlementRec> list = this.contractSettlementRecDao.getBySettlementId(settlementId);
			settlement.setMaterialList(list);
		}
		return settlement;
	}

    /**
     * 根据当前数据库中的序列号情况以及Manager实例中的序列号情况返回一个新的序列号.
     * 在返回该新的序列号之后，内置的序列号计数器自动加1
     * @return 最新的序列号
     */
    public synchronized int generateNewIndex(){
        int li_LatestIndexInDB = this.prjContractSettlementDao.getMaxIndex();
        if (li_LatestIndexInDB > this.ii_PaymentIndex) ii_PaymentIndex = li_LatestIndexInDB;
        ii_PaymentIndex++;
        return ii_PaymentIndex;
    }
    
	/**
	 * 通过项目id获取项目
	 * @param projectId
	 * @return
	 */
	public RegisterProject getPrjById(String projectId) {
		RegisterProject project = projectService.get(projectId);
		return project;
	}

	/**
	 * 根据客户获取银行或者银行账户
	 * @param supplierId
	 * @return
	 */
	public Map<String, String> selectBankOrAccount(String supplierId, String gatheringUnit, String bank) {
		// 根据供方ID，收款单位，银行名称获取收款账户
		List<SupplierBankAccount> list = this.bankAccountDao.getBankBySupplierAndGatheringUnit(supplierId, gatheringUnit, bank);
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (list != null && list.size() > 0) {
			for (SupplierBankAccount account : list) {
				if (StringUtils.isNotBlank(bank)) {// 获取银行账户
					map.put(account.getBankAccount(), account.getBankAccount());
				} else {// 获取银行
					map.put(account.getBank(), account.getBank());
				}
			}
		}
		return map;
	}

	/**
	 * 根据客户获取银行或者银行账户
	 * @param supplierId
	 * @return
	 */
	public List<SupplierBankAccount> selectBankAccount(String supplierId, String gatheringUnit, String bank) {
		// 根据供方ID，收款单位，银行名称获取收款账户
		List<SupplierBankAccount> list = this.bankAccountDao.getBankBySupplierAndGatheringUnit(supplierId, gatheringUnit, bank);
		List<SupplierBankAccount> bankAccountList = new ArrayList<SupplierBankAccount>();
		if (list != null && list.size() > 0) {
			for (SupplierBankAccount account : list) {
				bankAccountList.add(account);
			}
		}
		return bankAccountList;
	}

	/**
	 * 获取明细表
	 * @param jqGrid
	 * @param settlementId
	 * @return
	 */
	public List<PrjContractSettlementRec> getRecGrid(JqGrid jqGrid, String settlementId) {
		List<PrjContractSettlementRec> list = this.contractSettlementRecDao.getRecGrid(jqGrid, settlementId);
		return list;
	}

	public PrjContractSettlementRec getRec(String recordId) {
		return this.contractSettlementRecDao.get(recordId);
	}

	/**
	 * 保存付款证明附件
	 * @param user
	 * @param signedReportId
	 */
	public void saveProveFile(String settlementId){
		String fileNames = proveDao.getFileNamesList(settlementId);
	    IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("TPCCONSET", "PayProve", settlementId);
		if(list !=null && list.size()>0){
			for(IFile file : list){
				if(StringUtils.isNotBlank(fileNames)){
					if(fileNames.indexOf(file.getFileId()) == -1){
						saveFiles(file,settlementId);
					}
				}else{
					saveFiles(file,settlementId);
				}
				
			}
		}
	}

	public void saveFiles(IFile file,String settlementId){
		PrjContractSettlementProve prove = new PrjContractSettlementProve();
		prove.setFileContentType(file.getContentType());
		prove.setFileName(file.getFileName());
		prove.setFileSize(file.getFileSize());
		prove.setSettlementId(settlementId);
		prove.setProveId(com.supporter.util.UUIDHex.newId());
//		prove.setTypeId(typeId)
		this.proveDao.save(prove);
	}

	/**
	 * 返回付款条款
	 * @param contractId
	 * @return
	 */
	public Map<String, String> getPaymentTermsMap(String contractId) {
		Map<String, String> map = this.prjContractTableService.getPrjContractCollectionTermsMap(contractId);
		return map;
	}

	/**
	 * 根据id获取合同付款条款
	 * @param paymentTermsId
	 * @return
	 */
	public PrjContractCollectionTerms getPrjContractPaymentTerms(String paymentTermsId) {
		PrjContractCollectionTerms collectionTerms = this.prjContractTableService.getPrjContractCollectionTerms(paymentTermsId);
		return collectionTerms;
	}

	/**
	 * 保存或更新
	 * @param user
	 * @param contractSettlement
	 * @return
	 */
	public PrjContractSettlement saveOrUpdate(UserProfile user,PrjContractSettlement contractSettlement) {
		List<PrjContractSettlementRec> list = contractSettlement.getMaterialList();
		contractSettlement.setModifiedBy(user.getName());
		contractSettlement.setModifiedById(user.getPersonId());
		contractSettlement.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
		this.prjContractSettlementDao.saveOrUpdate(contractSettlement);
		List<PrjContractSettlementRec> listBefore = this.contractSettlementRecDao.getBySettlementId(contractSettlement.getSettlementId());
		if (listBefore != null && listBefore.size() > 0) {
			this.contractSettlementRecDao.delete(listBefore);
		}
		if (list != null && list.size() > 0) {
			for(PrjContractSettlementRec rec :list){
				rec.setBudgetYear(contractSettlement.getSettlementYear()+"");
				rec.setBudgetMonth(contractSettlement.getSettlementMonth()+"");
				rec.setSettlementId(contractSettlement.getSettlementId());
				this.contractSettlementRecDao.save(rec);
			}
		}

		return contractSettlement;
	}

	/**
	 * 提交
	 * successful保存成功，body对象，valid验证对象信息，existBudget存在该预算，validBudget验证预算，msg未通过验证提示信息
	 * @param user
	 * @param contractSettlement
	 * @return
	 */
	public String commit(UserProfile user, PrjContractSettlement contractSettlement) {
		String body = JsonUtil.toJson(contractSettlement);
		// 默认验证通过
		String json = "";
		saveOrUpdate(user, contractSettlement);
		// 
		accountOtherRate(contractSettlement);
		// 验证是否可以提交
		String prjId = CommonUtil.trim(contractSettlement.getPrjId());
		String settlementId = CommonUtil.trim(contractSettlement.getSettlementId());
		double settlementAmount = contractSettlement.getSettlementAmountAct();
		if (prjId.length() == 0 || settlementId.length() == 0 || settlementAmount == 0) {
			json = "{\"successful\": true,\"body\": " + body + ",\"valid\": false,\"msg\": \"cannotValid\"}";
		} else {
			String result = verificationSubmit(prjId, contractSettlement);
			json = "{\"successful\": true,\"body\": " + body + ",\"valid\": true," + result + "}";
		}
		return json;
	}

	/**
	 * 验证是否可以提交
	 * @param projectId
	 * @param contractSettlement
	 * @return
	 */
	public String verificationSubmit(String projectId, PrjContractSettlement contractSettlement) {
		String result = "\"existBudget\": true,\"validBudget\": true,\"msg\": \"\"";
		// 获取采购合同所有付款条款
		List<PrjContractCollectionTerms> termsList = this.prjContractTableService.getPrjContractCollectionTermsList(contractSettlement.getContractId());
		if (termsList.size() > 0) {
			Map<String, PrjContractCollectionTerms> termsMap = new HashMap<String, PrjContractCollectionTerms>();
			for (PrjContractCollectionTerms terms : termsList) {
				termsMap.put(terms.getTermsId(), terms);
			}
			Map<String, Double> termsAmountMap = new HashMap<String, Double>();
			// 获取本次付款明细集合
			List<PrjContractSettlementRec> prjSettlementRecs = contractSettlement.getMaterialList();
			// 本次付款明细对应付款条款累计付款金额
			double sumSettlementAmount = 0d;
			// 遍历付款按对应条款计算合计
			for (PrjContractSettlementRec prjSettlementRec : prjSettlementRecs) {
				String termsId = prjSettlementRec.getPaymentTermsId();
				// 有效预算金额(该付款记录对应的付款条款可付金额)
				double availableAmount = 0d;
				// 通过该付款记录对应的付款条款可付金额判断（合同评审完成即扣除预算）
				PrjContractCollectionTerms terms = termsMap.get(termsId);
				if (terms != null) {
					availableAmount = terms.getReceiveabledAmount();// 通过执行金额判断
				} else {
					result = "\"existBudget\": false,\"validBudget\": false,\"msg\": \"noBudget\"";
					break;
				}
				// 累计明细付款金额
				if (termsAmountMap.containsKey(termsId)) {
					sumSettlementAmount = termsAmountMap.get(termsId) + prjSettlementRec.getSettlementAmount();
				} else {
					sumSettlementAmount = prjSettlementRec.getSettlementAmount();
				}
				// 本次付款某个明细对应条款的合计金额超过该条款有效预算金额
				if (sumSettlementAmount > availableAmount) {
					result = "\"existBudget\": true,\"validBudget\": false,\"msg\": \"outAvailableAmount\"";
					break;
				}
				termsAmountMap.put(termsId, sumSettlementAmount);
			}
		}
		return result;
	}
/*
	public String verificationSubmit(String projectId, PrjContractSettlement contractSettlement) {
		String result = "\"existBudget\": true,\"validBudget\": true,\"msg\": \"\"";
		// 获取所有预算
		Map<String, BenefitBudget> budgetMap = TpcBudgetUtil.getBenefitBudgetMap(projectId);
		if (budgetMap.size() > 0) {
			Map<String, Double> budgetAmountMap = new HashMap<String, Double>();
			// 获取本次付款明细集合
			List<PrjContractSettlementRec> prjSettlementRecs = contractSettlement.getMaterialList();
			String budgetId = contractSettlement.getBudgetId();
			// 本次单预算项付款金额
			double sumSettlementAmount = 0d;
			// 遍历付款按预算项计算合计
			for (PrjContractSettlementRec prjSettlementRec : prjSettlementRecs) {
				// 有效预算金额
				double availableAmount = 0d;
				// 获取单预算项在主合同预算金额
				BenefitBudget budget = budgetMap.get(budgetId);
				if (budget != null) {
					availableAmount = budget.getExecuteAmount();// 通过执行金额判断（合同评审完成即扣除预算）
				} else {
					result = "\"existBudget\": false,\"validBudget\": false,\"msg\": \"noBudget\"";
					break;
				}
				// 累计预算付款金额
				if (budgetAmountMap.containsKey(budgetId)) {
					sumSettlementAmount += prjSettlementRec.getSettlementAmount();
				} else {
					sumSettlementAmount = prjSettlementRec.getSettlementAmount();
				}
				// 本次付款某个预算的合计金额超过可用预算金额
				if (sumSettlementAmount > availableAmount) {
					result = "\"existBudget\": true,\"validBudget\": false,\"msg\": \"outAvailableAmount\"";
					break;
				}
				budgetAmountMap.put(budgetId, sumSettlementAmount);
			}
		}
		return result;
	}
*/
	/**
	 * 获取合计
	 * @param jqGrid
	 * @param contractId
	 * @param currencyCode
	 * @return
	 */
	public List<Map<String, String>> getTotal(String contractId, String settlementId) {
		List<PrjContractAmount> listContract = this.prjContractTableService.getPrjContractAmountList(contractId);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (PrjContractAmount amount : listContract) {
			Map<String, String> map = new HashMap<String, String>();
			List<Double> listAmount = getTotals(contractId, amount.getCurrencyId(), settlementId);
			String currency = amount.getCurrency();
			String currencyId = amount.getCurrencyId();
			double rate = amount.getRate();
			double currencyAmount = amount.getOriginalAmount();
			map.put("currency", currency);
			map.put("currencyId", currencyId);
			map.put("rate", rate + "");
			map.put("currencyAmount", currencyAmount + "");
			map.put("sumAmountF", listAmount.get(0) + "");
			map.put("sumAmount", listAmount.get(1) + "");
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 计算获取合计
	 * @param contractId
	 * @param currencyCode
	 * @return
	 */
	public List<Double> getTotals(String contractId, String currencyCode, String settlementId) {
		List<Double> total = new ArrayList<Double>();
		String settlementDate = "";
		if (StringUtils.isNotBlank(settlementId)) {
			PrjContractSettlement contractSettlement = this.get(settlementId);
			if (contractSettlement != null) {
				settlementDate = contractSettlement.getCreatedDate();
			}
		}
		double ld_CreditRealSettlementAmount = this.creditLetterSettlementDao.getTotalAmount(contractId, currencyCode, "real_settlement_amount", settlementDate);
		double ld_CreditOnwayAmount = this.creditLetterSettlementDao.getTotalAmount(contractId, currencyCode, "on_way_amount", settlementDate);
		double ld_CreditRealSettlementAmountF = this.creditLetterSettlementDao.getTotalAmount(contractId, currencyCode, "real_settlement_amount_f", settlementDate);
		double ld_CreditOnwayAmountF = this.creditLetterSettlementDao.getTotalAmount(contractId, currencyCode, "on_way_amount_f", settlementDate);
		double ld_TotalSettlementAmountF = 0;
		double ld_TotalSettlementAmount = 0;
		ld_TotalSettlementAmountF = ld_TotalSettlementAmountF + ld_CreditRealSettlementAmountF + ld_CreditOnwayAmountF;
		ld_TotalSettlementAmount = ld_TotalSettlementAmount + ld_CreditOnwayAmount + ld_CreditRealSettlementAmount;
		List<PrjContractSettlement> list = this.prjContractSettlementDao.getTotalOnWayAmountF(contractId, currencyCode, false, true, settlementDate);
		for (PrjContractSettlement settlement : list) {
//			if (!(settlement.getPaymentStatus() == PrjContractSettlement.PaymentStatus.COMPLETED)) {
				List<PrjContractSettlementRec> prjContractSettlementRecList = this.contractSettlementRecDao.getBySettlementId(settlement.getSettlementId());
				for (PrjContractSettlementRec prjContractSettlementRec : prjContractSettlementRecList) {
					if (prjContractSettlementRec.getCurrencyTypeCode().equals(CommonUtil.trim(currencyCode))) {
						if (prjContractSettlementRec.getOnWayAmountF() == 0 && prjContractSettlementRec.getRealSettlementAmountF() == 0) {
							ld_TotalSettlementAmountF = BigDecimal.valueOf(ld_TotalSettlementAmountF).add(BigDecimal.valueOf(prjContractSettlementRec.getSettlementAmountF())).doubleValue();
						} else {
							ld_TotalSettlementAmountF = BigDecimal.valueOf(ld_TotalSettlementAmountF).add(BigDecimal.valueOf(prjContractSettlementRec.getRealSettlementAmountF())).doubleValue();
							ld_TotalSettlementAmountF = BigDecimal.valueOf(ld_TotalSettlementAmountF).add(BigDecimal.valueOf(prjContractSettlementRec.getOnWayAmountF())).doubleValue();
						}
					}
				}
				for (PrjContractSettlementRec prjContractSettlementRec : prjContractSettlementRecList) {
					if (prjContractSettlementRec.getCurrencyTypeCode().equals(CommonUtil.trim(currencyCode))) {
						if (prjContractSettlementRec.getOnWayAmountF() == 0 && prjContractSettlementRec.getRealSettlementAmountF() == 0) {
							ld_TotalSettlementAmount = BigDecimal.valueOf(ld_TotalSettlementAmount).add(BigDecimal.valueOf(prjContractSettlementRec.getSettlementAmount())).doubleValue();
						} else {
							ld_TotalSettlementAmount = BigDecimal.valueOf(ld_TotalSettlementAmount).add(BigDecimal.valueOf(prjContractSettlementRec.getOnWayAmount())).doubleValue();
							ld_TotalSettlementAmount = BigDecimal.valueOf(ld_TotalSettlementAmount).add(BigDecimal.valueOf(prjContractSettlementRec.getRealSettlementAmount())).doubleValue();
						}
					}
				}
//			}
		}
		total.add(ld_TotalSettlementAmountF);
		total.add(ld_TotalSettlementAmount);
		return total;
	}

	/**
	 * 计算采购合同付款的各项比例.
	 * @param settlement
	 */
	public void accountOtherRate(PrjContractSettlement settlement) {
		PrjContractTable contract = this.prjContractTableService.get(settlement.getContractId());
		double ld_TotalSettlementAmount = settlement.getSettlementAmountAct() + getTotals(settlement.getContractId(), TpcConstant.STANDARD_CURRENCY, settlement.getSettlementId()).get(1);
		double ld_TotalSettlementRate = ld_TotalSettlementAmount / (contract.getContractRmbAmount());
		double ld_TotalInvoiceAmount = this.invoiceService.getTotalInvoiceAmount(settlement.getContractId());
		double ld_TotalInvoiceRate = ld_TotalInvoiceAmount / (contract.getContractRmbAmount());
		settlement.setTotalSettlementAmount(ld_TotalSettlementAmount);
		settlement.setTotalSettlementRate(ld_TotalSettlementRate);
		settlement.setTotalInvoiceAmount(ld_TotalInvoiceAmount);
		settlement.setTotalInvoiceRate(ld_TotalInvoiceRate);
		this.update(settlement);
	}

	/**
	 * 删除
	 * @param user
	 * @param settlementIds
	 */
	public void delete(UserProfile user, String settlementIds) {
		if (StringUtils.isNotBlank(settlementIds)) {
			for (String settlementId : settlementIds.split(",")) {
				PrjContractSettlement settlement = this.prjContractSettlementDao.get(settlementId);
				List<PrjContractSettlementRec> list = this.contractSettlementRecDao.getBySettlementId(settlementId);
				if (list != null && list.size() > 0) {
					this.contractSettlementRecDao.delete(list);
				}
				this.prjContractSettlementDao.delete(settlement);
			}
		}
	}
	
	/**
	 * 根据id获取对象
	 * @param settlementId
	 * @return
	 */
	public PrjContractSettlement get(String settlementId) {
		PrjContractSettlement settlement = this.prjContractSettlementDao.get(settlementId);
		if (StringUtils.isBlank(settlement.getPrjManagerId())) {
			RegisterProject project = this.projectService.get(settlement.getPrjId());
			if (project != null) {
				settlement.setPrjManagerId(project.getProjectChargeId());
				settlement.setPrjManager(project.getProjectCharge());
			}
		}
		return settlement;
	}

	/**
	 * 更新
	 * 
	 * @param contractSettlement
	 */
	public void update(PrjContractSettlement contractSettlement) {
		this.prjContractSettlementDao.update(contractSettlement);
	}

	/**
	 * 提交流程需处理操作
	 */
	public void startProc(PrjContractSettlement contractSettlement) {
		contractSettlement.setSettlementStatus(PrjContractSettlement.SettlementStatus.PROCESSING);
		contractSettlement.setOnWayAmount(contractSettlement.getSettlementAmountAct());
		contractSettlement.setOnWayAmountF(contractSettlement.getAmountSettlement());
		contractSettlement.setRealSettlementAmount(0);
		contractSettlement.setRealSettlementAmountF(0);
		this.prjContractSettlementDao.update(contractSettlement);
		// 将付款金额写入合同在途
		List<PrjContractSettlementRec> detailList = this.contractSettlementRecDao.findBy("settlementId", contractSettlement.getSettlementId());
		for (PrjContractSettlementRec rec : detailList) {
			rec.setOnWayAmount(rec.getSettlementAmount());
			rec.setOnWayAmountF(rec.getSettlementAmountF());
			this.contractSettlementRecDao.update(rec);
			this.prjContractTableService.addOnwayAmountForTerms(rec.getPaymentTermsId(), rec.getSettlementAmountF());
		}
	}

	/**
	 * 中止流程处理操作
	 * @param contractSettlement
	 */
	public void abortProc(PrjContractSettlement contractSettlement) {
		contractSettlement.setSettlementStatus(PrjContractSettlement.SettlementStatus.DRAFT);
		contractSettlement.setOnWayAmount(0);
		contractSettlement.setOnWayAmountF(0);
		this.prjContractSettlementDao.update(contractSettlement);
		// 将付款金额移除在途
		List<PrjContractSettlementRec> detailList = this.contractSettlementRecDao.findBy("settlementId", contractSettlement.getSettlementId());
		for (PrjContractSettlementRec rec : detailList) {
			rec.setOnWayAmount(0);
			rec.setOnWayAmountF(0);
			this.contractSettlementRecDao.update(rec);
			this.prjContractTableService.removeOnwayAmountForTerms(rec.getPaymentTermsId(), rec.getSettlementAmountF());
		}
	}

	/**
	 * 审批完成执行操作
	 * @param contractSettlement
	 */
	public void completeExam(PrjContractSettlement contractSettlement) {
		// 设置付款状态为支付完毕
		contractSettlement.setRealPaymentDate(CommonUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
		contractSettlement.setPaymentStatus(PrjContractSettlement.PaymentStatus.COMPLETED);
		contractSettlement.setPaidFlag(true);
		// 设置审批完成
		contractSettlement.setSettlementStatus(PrjContractSettlement.SettlementStatus.COMPLETE);
		contractSettlement.setConfirmDate(CommonUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
		// 清除在途
		contractSettlement.setOnWayAmount(0);
		contractSettlement.setOnWayAmountF(0);
		contractSettlement.setRealSettlementAmount(contractSettlement.getSettlementAmountAct());
		this.prjContractSettlementDao.update(contractSettlement);

		// 将付款明细金额转为实际付款金额并将合同金额增加实际付款
		List<PrjContractSettlementRec> detailList = this.contractSettlementRecDao.findBy("settlementId", contractSettlement.getSettlementId());
		for (PrjContractSettlementRec rec : detailList) {
			rec.setOnWayAmount(0);
			rec.setOnWayAmountF(0);
			rec.setRealCurrencyTypeCode(rec.getCurrencyTypeCode());
			rec.setRealCurrencyType(rec.getCurrencyType());
			rec.setRealSettlementAmount(rec.getSettlementAmount());
			rec.setRealSettlementAmountF(rec.getSettlementAmountF());
			rec.setRealSettlementAmountPF(rec.getSettlementAmountF());
			this.contractSettlementRecDao.update(rec);
			this.prjContractTableService.transferToRealReceiveAmountForTerms(rec.getPaymentTermsId(), rec.getSettlementAmountF());
		}
	}

	/**
	 * 退款处理修改付款单退款状态
	 */
	public void updateRefundStatus(String settlementId, int refundStatus) {
		PrjContractSettlement contractSettlement = this.get(settlementId);
		if (contractSettlement != null) {
			// 若是退款删除置为草稿时需判断采购合同付款单是否已有退款，若是退款确认完成需判断付款单是否全部退款
			if (refundStatus == PrjContractSettlement.SettlementStatus.DRAFT || refundStatus == PrjContractSettlement.REFUNDED) {
				boolean isDraft = refundStatus == PrjContractSettlement.SettlementStatus.DRAFT;
				int clearSize = 0;
				List<PrjContractSettlementRec> detailList = this.contractSettlementRecDao.findBy("settlementId", settlementId);
				for (PrjContractSettlementRec detail : detailList) {
					double judgeAmount = isDraft ? detail.getSettlementAmountF() : 0;
					if (detail.getRealSettlementAmountF() == judgeAmount) {
						clearSize++;
					}
				}
				// 有若干付款明细实际付款金额不等于付款金额，即已经有部分退款
				// 有若干付款明细实际付款金额非0，即仍有未全部退款
				if (clearSize < detailList.size()) {
					refundStatus = PrjContractSettlement.REFUNDED_PARTIAL;
				}
			}
			contractSettlement.setRefundStatus(refundStatus);
			this.prjContractSettlementDao.update(contractSettlement);
		}
	}

	/**
	 * 给付款明细增加在途金额
	 * @param detailId
	 * @param amount
	 */
	public void addOnwayAmountForDetail(String detailId, double amount) {
		PrjContractSettlementRec entity = this.contractSettlementRecDao.get(detailId);
		if (entity != null) {
			double amountRmb = amount * entity.getRate();
			entity.setOnWayAmountF(BigDecimal.valueOf(entity.getOnWayAmountF()).add(BigDecimal.valueOf(amount)).doubleValue());
			entity.setOnWayAmount(BigDecimal.valueOf(entity.getOnWayAmount()).add(BigDecimal.valueOf(amountRmb)).doubleValue());
			this.contractSettlementRecDao.update(entity);
		}
	}

	/**
	 * 给付款明细移除在途金额
	 * @param detailId
	 * @param amount
	 */
	public void removeOnwayAmountForDetail(String detailId, double amount) {
		PrjContractSettlementRec entity = this.contractSettlementRecDao.get(detailId);
		if (entity != null) {
			double amountRmb = amount * entity.getRate();
			entity.setOnWayAmountF(BigDecimal.valueOf(entity.getOnWayAmountF()).subtract(BigDecimal.valueOf(amount)).doubleValue());
			entity.setOnWayAmount(BigDecimal.valueOf(entity.getOnWayAmount()).subtract(BigDecimal.valueOf(amountRmb)).doubleValue());
			this.contractSettlementRecDao.update(entity);
		}
	}

	/**
	 * 给付款明细将在途转为实际付款金额(实际付款-在途=新的实际付款金额)
	 * @param detailId
	 * @param amount
	 */
	public void transferToRealSettlementAmountForDetail(String detailId, double amount) {
		PrjContractSettlementRec entity = this.contractSettlementRecDao.get(detailId);
		if (entity != null) {
			double amountRmb = amount * entity.getRate();
			entity.setOnWayAmountF(BigDecimal.valueOf(entity.getOnWayAmountF()).subtract(BigDecimal.valueOf(amount)).doubleValue());
			entity.setOnWayAmount(BigDecimal.valueOf(entity.getOnWayAmount()).subtract(BigDecimal.valueOf(amountRmb)).doubleValue());
			entity.setRealSettlementAmountF(BigDecimal.valueOf(entity.getRealSettlementAmountF()).subtract(BigDecimal.valueOf(amount)).doubleValue());
			entity.setRealSettlementAmount(BigDecimal.valueOf(entity.getRealSettlementAmount()).subtract(BigDecimal.valueOf(amountRmb)).doubleValue());
			this.contractSettlementRecDao.update(entity);
			// 更新合同实际付款
			this.prjContractTableService.transferToRealReceiveAmountForTermsByRefund(entity.getPaymentTermsId(), amount);
		}
	}

	/**
	 * 获取历次付款用途
	 * @return
	 */
	public Map<String, String> getRemittancePurposeData() {
		Map<String, String> map = new HashMap<String, String>();
		List<String> list = this.prjContractSettlementDao.getRemittancePurpose();
		for (String str : list) {
			map.put(str, str);
		}
		return map;
	}

}
