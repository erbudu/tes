package com.supporter.prj.cneec.cxf.server.service;


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.cxf.JsonDateValueProcessor;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.pm.bank_manage.dao.AccountDetailDao;
import com.supporter.prj.pm.bank_manage.dao.BankManageDao;
import com.supporter.prj.pm.bank_manage.entity.AccountDetail;
import com.supporter.prj.pm.bank_manage.entity.BankAccount;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceConstDao;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceConstSiteDao;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceConstSwfDao;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceVisaDao;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConst;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConstSite;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConstSwf;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceVisa;
import com.supporter.prj.pm.delivery_construction.dao.DeliveryConstructionDao;
import com.supporter.prj.pm.delivery_construction.dao.DeliveryDrawingsDao;
import com.supporter.prj.pm.delivery_construction.entity.DeliveryConstruction;
import com.supporter.prj.pm.delivery_construction.entity.DeliveryDrawings;
import com.supporter.prj.pm.design_change.dao.DesignChangeDao;
import com.supporter.prj.pm.design_change.entity.DesignChange;
import com.supporter.prj.pm.drawing_library.dao.DrawingLibraryDao;
import com.supporter.prj.pm.drawing_library.dao.DrawingLibraryVersionDao;
import com.supporter.prj.pm.drawing_library.entity.DrawingLibrary;
import com.supporter.prj.pm.drawing_library.entity.DrawingLibraryVersion;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaDao;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaSiteDao;
import com.supporter.prj.pm.enginee_negotiate.dao.EngineeVisaSwfDao;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisa;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSite;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSwf;
import com.supporter.prj.pm.external_drawings.dao.ExternalDrawingsContentDao;
import com.supporter.prj.pm.external_drawings.dao.ExternalDrawingsDao;
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawings;
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawingsContent;
import com.supporter.prj.pm.fund_appropriation.dao.FundAppropriationDao;
import com.supporter.prj.pm.fund_appropriation.dao.FundAppropriationSwfDao;
import com.supporter.prj.pm.fund_appropriation.dao.FundBudgetActualDao;
import com.supporter.prj.pm.fund_appropriation.dao.FundBudgetExpendDao;
import com.supporter.prj.pm.fund_appropriation.dao.FundReceiptActualDao;
import com.supporter.prj.pm.fund_appropriation.dao.FundReceiptDao;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriation;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriationSwf;
import com.supporter.prj.pm.fund_appropriation.entity.FundBudgetActual;
import com.supporter.prj.pm.fund_appropriation.entity.FundBudgetExpend;
import com.supporter.prj.pm.fund_appropriation.entity.FundReceipt;
import com.supporter.prj.pm.fund_appropriation.entity.FundReceiptActual;
import com.supporter.prj.pm.payment_onsite.dao.PaymentOnsiteDao;
import com.supporter.prj.pm.payment_onsite.dao.PaymentOnsiteSwfDao;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsite;
import com.supporter.prj.pm.payment_onsite.entity.PaymentOnsiteSwf;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractContentDao;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractDao;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractPayDao;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractSiteDao;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractSwfDao;
import com.supporter.prj.pm.procure_contract.entity.ProcureContract;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractContent;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractPay;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractSite;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractSwf;
import com.supporter.prj.pm.reserve_fund.dao.NotReimbursedDao;
import com.supporter.prj.pm.reserve_fund.dao.NotReimbursedRecDao;
import com.supporter.prj.pm.reserve_fund.dao.StockCashDao;
import com.supporter.prj.pm.reserve_fund.dao.StockCashRecDao;
import com.supporter.prj.pm.reserve_fund.entity.NotReimbursed;
import com.supporter.prj.pm.reserve_fund.entity.NotReimbursedRec;
import com.supporter.prj.pm.reserve_fund.entity.StockCash;
import com.supporter.prj.pm.reserve_fund.entity.StockCashRec;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 回盘webService
 * @author Administrator
 *
 */
@Service("pmWebServiceImpl")
@WebService(endpointInterface = "com.supporter.prj.cneec.cxf.server.service.IEIPPmWebService")
public class PmWebServiceImpl implements IEIPPmWebService {
	@Autowired
	private ProcureContractContentDao contentDao;
	@Autowired
	private ProcureContractSiteDao siteDao;
	@Autowired
	private ProcureContractPayDao paymentDao;
	@Autowired
	private ProcureContractDao contractDao;
	@Autowired
	private ProcureContractSwfDao contractSwfDao;
	
	@Autowired
	private EngineeVisaDao visaDao;
	@Autowired
	private EngineeVisaSwfDao visaSwfDao;
	@Autowired
	private EngineeVisaSiteDao visaSiteDao;
	
	@Autowired
	private ContractBalanceConstDao balanceDao;
	@Autowired
	private ContractBalanceConstSwfDao balanceSwfDao;
	@Autowired
	private ContractBalanceConstSiteDao balanceSiteDao;
	@Autowired
	private ContractBalanceVisaDao balanceVisaDao;
	
	@Autowired
	private PaymentOnsiteDao payDao;
	@Autowired
	private PaymentOnsiteSwfDao paySwfDao;
	
	@Autowired
	private FundAppropriationDao fundDao;
	@Autowired
	private FundAppropriationSwfDao fundSwfDao;
	@Autowired
	private FundBudgetExpendDao expendDao;
	@Autowired
	private FundBudgetActualDao budgetActualDao;
	@Autowired
	private FundReceiptActualDao actualDao;
	@Autowired
	private FundReceiptDao receiptDao;
	
	@Autowired
	private BankManageDao bankDao;
	@Autowired
	private AccountDetailDao accountDetailDao;
	
	@Autowired
	private DesignChangeDao designChangeDao;

	@Autowired
	private NotReimbursedDao notReimbursedDao;
	@Autowired
	private NotReimbursedRecDao notReimbursedRecDao;
	@Autowired
	private StockCashDao stockCashDao;
	@Autowired
	private StockCashRecDao stockCashRecDao;
	
	// 交付施工
	@Autowired
	private DeliveryConstructionDao deliveryDao;
	@Autowired
	private DeliveryDrawingsDao drawingsDao;

	// 图纸交接
	@Autowired
	private DrawingLibraryDao drawingLibraryDao;
	@Autowired
	private DrawingLibraryVersionDao drawingVersionDao;

	// 图纸外审
	@Autowired
	private ExternalDrawingsDao externalDao;
	@Autowired
	private ExternalDrawingsContentDao externalContentDao;

	private Charset charset = null;
	/**
	 * 获取系统默认的编码
	 * @return String
	 */
	private Charset getCharset() {
		if (charset != null) {
			return charset;
		} else {
			charset = Charset.defaultCharset();
			return charset;
		}
	}
	
	/**
	 * 用于简单测试
	 * @param msg 输入参数
	 * @return String
	 */
	@Override
	public String myTest(String msg) {
		return "返回数据：" + msg;
	}

	@SuppressWarnings("unchecked")
	@Transactional(TransManager.APP)
	@Override
	public boolean sendProcureContract(String jsonData) {
		//System.out.println("sendProcureContract:OA::" + jsonData);
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);
		
		JSONArray goodsJson = jsonObj.getJSONArray("goods");
		List<ProcureContractContent> goods = (List<ProcureContractContent>) JSONArray.toCollection(goodsJson, ProcureContractContent.class);
		
		JSONArray sitesJson = jsonObj.getJSONArray("sites");
		List<ProcureContractSite> sites = (List<ProcureContractSite>) JSONArray.toCollection(sitesJson, ProcureContractSite.class);
		
		JSONArray paymentsJson = jsonObj.getJSONArray("payments");
		List<ProcureContractPay> payments = (List<ProcureContractPay>) JSONArray.toCollection(paymentsJson, ProcureContractPay.class);
				
		jsonObj.remove("goods");
		jsonObj.remove("sites");
		jsonObj.remove("payments");
		
		//保存合同主表
		ProcureContract contract = (ProcureContract) JSONObject.toBean(jsonObj, ProcureContract.class);
		String contractId = contract.getContractId();
		if (contractDao.existInDB(contractId)) {
			contractDao.update(contract);
		} else {
			contractDao.save(contract);
		}
		//保存流程扩展表
		ProcureContractSwf swf = contractSwfDao.get(contractId);
		if (swf == null) {
			swf = new ProcureContractSwf();
			swf.setProcureContract(contract);
			contractSwfDao.save(swf);
		} else {
			swf.setProcureContract(contract);
			contractSwfDao.update(swf);
		}
		
		//子表全覆盖
		contentDao.deleteByProperty("contractId", contractId);
		siteDao.deleteByProperty("contractId", contractId);
		paymentDao.deleteByProperty("contractId", contractId);
		
		contentDao.save(goods);
		siteDao.save(sites);
		paymentDao.save(payments);
		
		/** 已经将代码迁移到PL系统，因为在EIP中无法处理待办信息
		//总部在接收到现场传入的合同后，给总部的合同管理员发知会，告知让其上传合同文本附件。
		long roleId = 1000001717; //"项目合同管理员";
		List <String> empIds = contractDao.getPrjMembersByRole(contract.getPrjId(), roleId);
		if (CollectionUtils.isNotEmpty(empIds)) {
			Date today = new Date();
			for (int i = 0; i < empIds.size(); i++) {
				Message msg = EIPService.getBMSService().newMessage();
				msg.setPersonId(empIds.get(i));
				msg.setCreatedDate(today);
				msg.setEventTitle("请上传现场采购合同文本 " + contract.getContractName());
				msg.setHasReceived(false);
				msg.setModuleId(ProcureContract.APP_NAME);
				msg.setWebappName("CNEEC_PL");
				String url = "pl/contract_file/editBySpot.action?contractFile.id=&prjId=&contractInNo=" + contract.getContractNo();
				msg.setWebPageURL(url);
				//发送待办
				EIPService.getBMSService().addMessage(msg);
			}
		}
		**/
		
		return true;
	}

	@SuppressWarnings("unchecked")
	@Transactional(TransManager.APP)
	@Override
	public boolean sendEngineeVisa(String jsonData) {
		//System.out.println("sendEngineeVisa:OA::" + jsonData);
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);

		JSONArray sitesJson = jsonObj.getJSONArray("sites");
		List<EngineeVisaSite> sites = (List<EngineeVisaSite>) JSONArray.toCollection(sitesJson,
				EngineeVisaSite.class);
		
		jsonObj.remove("sites");
		
		//保存签证主表
		EngineeVisa visa = (EngineeVisa) JSONObject.toBean(jsonObj, EngineeVisa.class);
		String visaId = visa.getVisaId();
		if (visaDao.existInDB(visaId)) {
			visaDao.update(visa);
		} else {
			visaDao.save(visa);
		}
		//保存流程扩展表
		EngineeVisaSwf swf = visaSwfDao.get(visaId);
		if (swf == null) {
			swf = new EngineeVisaSwf();
			swf.setEngineeVisa(visa);
			swf.setOaExamStatus(EngineeVisaSwf.OAExamStatus.DRAFT);
			visaSwfDao.save(swf);
		} else {
			swf.setEngineeVisa(visa);
			swf.setOaProcId(null);
			swf.setOaExamStatus(EngineeVisaSwf.OAExamStatus.DRAFT);
			visaSwfDao.update(swf);
		}
		
		//子表全覆盖
		visaSiteDao.deleteByProperty("visaId", visaId);
		visaSiteDao.save(sites);
		
		return true;
	}

	@SuppressWarnings("unchecked")
	@Transactional(TransManager.APP)
	@Override
	public boolean sendContractBalance(String jsonData) {
		//System.out.println("sendContractBalance:OA::" + jsonData);
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);
		
		JSONArray sitesJson = jsonObj.getJSONArray("sites");
		List<ContractBalanceConstSite> sites = (List<ContractBalanceConstSite>) JSONArray.toCollection(sitesJson, ContractBalanceConstSite.class);
		JSONArray visasJson = jsonObj.getJSONArray("visas");
		List<ContractBalanceVisa> visas = (List<ContractBalanceVisa>) JSONArray.toCollection(visasJson, ContractBalanceVisa.class);
		//System.out.println("sendContractBalance:OA::" + 1);
		jsonObj.remove("sites");
		jsonObj.remove("visas");
		
		//保存结算主表
		ContractBalanceConst balance = (ContractBalanceConst) JSONObject.toBean(jsonObj, ContractBalanceConst.class);
		String balanceId = balance.getBalanceId();
		if (balanceDao.existInDB(balanceId)) {
			//System.out.println("sendContractBalance:OA::" + 2);
			balanceDao.update(balance);
		} else {
			//System.out.println("sendContractBalance:OA::" + 3);
			balanceDao.save(balance);
		}
		//System.out.println("sendContractBalance:OA::" + 4);
		//保存流程扩展表
		ContractBalanceConstSwf swf = balanceSwfDao.get(balanceId);
		if (swf == null) {
			swf = new ContractBalanceConstSwf();
			swf.setBalance(balance);
			swf.setOaExamStatus(ContractBalanceConstSwf.OAExamStatus.DRAFT);
			balanceSwfDao.save(swf);
			//System.out.println("sendContractBalance:OA::" + 5);
		} else {
			swf.setBalance(balance);
			swf.setOaProcId(null);
			swf.setOaExamStatus(ContractBalanceConstSwf.OAExamStatus.DRAFT);
			balanceSwfDao.update(swf);
			//System.out.println("sendContractBalance:OA::" + 6);
		}
		
		//子表全覆盖
		balanceSiteDao.deleteByProperty("balanceId", balanceId);
		balanceSiteDao.save(sites);
		balanceVisaDao.deleteByProperty("balanceId", balanceId);
		balanceVisaDao.save(visas);
		
		return true;
	}

	@Transactional(TransManager.APP)
	@Override
	public boolean sendPaymentApply(String jsonData) {
		//System.out.println("sendPaymentApply:OA::" + jsonData);
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);
		//施工签证付款，因为不是实体类PaymentOnsite的属性，此处也用不到，删除
		if (jsonObj.containsKey("constVisaPay")) {
			jsonObj.remove("constVisaPay");
		}
		if (jsonObj.containsKey("rmbConstVisaPay")) {
			jsonObj.remove("rmbConstVisaPay");
		}
		
		//保存付款申请主表
		PaymentOnsite pay = (PaymentOnsite) JSONObject.toBean(jsonObj, PaymentOnsite.class);
		String id = pay.getId();
		if (payDao.existInDB(id)) {
			payDao.update(pay);
		} else {
			payDao.save(pay);
		}
		//保存流程扩展表
		PaymentOnsiteSwf swf = paySwfDao.get(id);
		if (swf == null) {
			swf = new PaymentOnsiteSwf();
			swf.setPaymentOnsite(pay);
			swf.setOaExamStatus(PaymentOnsiteSwf.OAExamStatus.DRAFT);
			paySwfDao.save(swf);
		} else {
			swf.setPaymentOnsite(pay);
			swf.setOaProcId(null);
			swf.setOaExamStatus(PaymentOnsiteSwf.OAExamStatus.DRAFT);
			paySwfDao.update(swf);
		}
		
		return true;
	}
	
	/**
	 * 推送付款申请实际支付
	 * @param jsonData json格式的数据
	 * @return boolean  true:成功   false：失败
	 */
	@Transactional(TransManager.APP)
	@Override
	public boolean sendPaymentActual(String jsonData) {
		//System.out.println("sendPaymentApply:OA::" + jsonData);
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);
		//施工签证付款，因为不是实体类PaymentOnsite的属性，此处也用不到，删除
		if (jsonObj.containsKey("constVisaPay")) {
			jsonObj.remove("constVisaPay");
		}
		if (jsonObj.containsKey("rmbConstVisaPay")) {
			jsonObj.remove("rmbConstVisaPay");
		}
		
		//保存付款申请主表
		PaymentOnsite pay = (PaymentOnsite) JSONObject.toBean(jsonObj, PaymentOnsite.class);
		String id = pay.getId();
		if (payDao.existInDB(id)) {
			payDao.update(pay);
		} else {
			payDao.save(pay);
		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(TransManager.APP)
	@Override
	public boolean sendFundAppropriation(String jsonData) {
		//System.out.println("sendFundAppropriation:OA::" + jsonData);
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);
		
		JSONArray receiptJson = jsonObj.getJSONArray("receipt");
		List<FundReceipt> receipt = (List<FundReceipt>) JSONArray.toCollection(receiptJson, FundReceipt.class);
		
		JSONArray actualJson = jsonObj.getJSONArray("receiptActual");
		List<FundReceiptActual> actuals = (List<FundReceiptActual>) JSONArray.toCollection(actualJson, FundReceiptActual.class);
		
		JSONArray budgetJson = jsonObj.getJSONArray("budget");
		List<FundBudgetExpend> budget = (List<FundBudgetExpend>) JSONArray.toCollection(budgetJson, FundBudgetExpend.class);
		
		JSONArray budgetActualJson = jsonObj.getJSONArray("budgetActual");
		List<FundBudgetActual> budgetActual = (List<FundBudgetActual>) JSONArray.toCollection(budgetActualJson, FundBudgetActual.class);
		
		jsonObj.remove("receipt");
		jsonObj.remove("receiptActual");
		jsonObj.remove("budget");
		jsonObj.remove("budgetActual");
		
		//保存资金拨付主表
		FundAppropriation fund = (FundAppropriation) JSONObject.toBean(jsonObj, FundAppropriation.class);
		String fundId = fund.getFundId();
		if (fundDao.existInDB(fundId)) {
			fundDao.update(fund);
		} else {
			fundDao.save(fund);
		}
		//保存流程扩展表
		FundAppropriationSwf swf = fundSwfDao.get(fundId);
		if (swf == null) {
			swf = new FundAppropriationSwf();
			swf.setFund(fund);
			swf.setOaExamStatus(FundAppropriationSwf.OAExamStatus.DRAFT);
			fundSwfDao.save(swf);
		} else {
			swf.setFund(fund);
			swf.setOaProcId(null);
			swf.setOaExamStatus(FundAppropriationSwf.OAExamStatus.DRAFT);
			fundSwfDao.update(swf);
		}
		
		//子表全覆盖
		receiptDao.deleteByProperty("fundId", fundId);
		actualDao.deleteByProperty("fundId", fundId);
		expendDao.deleteByProperty("fundId", fundId);
		budgetActualDao.deleteByProperty("fundId", fundId);
		
		receiptDao.save(receipt);
		actualDao.save(actuals);
		expendDao.save(budget);
		budgetActualDao.save(budgetActual);
		
		return true;
	}

	@Override
	public String returnProcureContract(String entityId) {
		ProcureContractSwf entity = contractSwfDao.get(entityId);
		if (entity == null) {
			return "";
		}
		/**
		if (entity.getOaExamStatus() == ProcureContractSwf.OAExamStatus.COMPLETE) {
			Map < String, Object > map = new HashMap <String, Object>();
			map.put("entityId", entityId);
			map.put("oaExamResult", entity.getOaExamResult());
			map.put("oaExamOpinion", entity.getOaExamOpinion());
			JSONObject jsonObj = JSONObject.fromObject(map);
			String jsonStr = "";
			try {
				jsonStr = new String(jsonObj.toString().getBytes(getCharset()), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			return jsonStr;
		}
		**/
		return "";
		
	}

	@Override
	public String returnEngineeVisa(String entityId) {
		EngineeVisaSwf entity = visaSwfDao.get(entityId);
		if (entity == null) {
			return "";
		}
		
		if (entity.getOaExamStatus() == EngineeVisaSwf.OAExamStatus.COMPLETE
				|| entity.getOaExamStatus() == EngineeVisaSwf.OAExamStatus.FAIL) {
			Map < String, Object > map = new HashMap <String, Object>();
			map.put("entityId", entityId);
			map.put("oaExamResult", entity.getOaExamResult());
			map.put("oaExamOpinion", entity.getOaExamOpinion());
			JSONObject jsonObj = JSONObject.fromObject(map);
			String jsonStr = "";
			try {
				jsonStr = new String(jsonObj.toString().getBytes(getCharset()), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			return jsonStr;
		}
		
		return "";
	}

	@Override
	public String returnContractBalance(String entityId) {
		ContractBalanceConstSwf entity = balanceSwfDao.get(entityId);
		if (entity == null) {
			return "";
		}
		
		if (entity.getOaExamStatus() == ContractBalanceConstSwf.OAExamStatus.COMPLETE
				|| entity.getOaExamStatus() == ContractBalanceConstSwf.OAExamStatus.FAIL) {
			Map < String, Object > map = new HashMap <String, Object>();
			map.put("entityId", entityId);
			map.put("oaExamResult", entity.getOaExamResult());
			map.put("oaExamOpinion", entity.getOaExamOpinion());
			JSONObject jsonObj = JSONObject.fromObject(map);
			String jsonStr = "";
			try {
				jsonStr = new String(jsonObj.toString().getBytes(getCharset()), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			return jsonStr;
		}
		
		return "";
	}

	@Override
	public String returnPaymentApply(String entityId) {
		PaymentOnsiteSwf entity = paySwfDao.get(entityId);
		if (entity == null) {
			return "";
		}
		
		if (entity.getOaExamStatus() == PaymentOnsiteSwf.OAExamStatus.COMPLETE
				|| entity.getOaExamStatus() == PaymentOnsiteSwf.OAExamStatus.FAIL) {
			Map < String, Object > map = new HashMap <String, Object>();
			map.put("entityId", entityId);
			map.put("oaExamResult", entity.getOaExamResult());
			map.put("oaExamOpinion", entity.getOaExamOpinion());
			JSONObject jsonObj = JSONObject.fromObject(map);
			String jsonStr = "";
			try {
				jsonStr = new String(jsonObj.toString().getBytes(getCharset()), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			return jsonStr;
		}
		
		return "";
	}
	
	@Override
	public String returnFundAppropriation(String entityId) {
		FundAppropriationSwf entity = fundSwfDao.get(entityId);
		if (entity == null) {
			return "";
		}
		
		if (entity.getOaExamStatus() == FundAppropriationSwf.OAExamStatus.COMPLETE
				|| entity.getOaExamStatus() == FundAppropriationSwf.OAExamStatus.FAIL) {
			Map < String, Object > map = new HashMap <String, Object>();
			map.put("entityId", entityId);
			map.put("oaExamResult", entity.getOaExamResult());
			map.put("oaExamOpinion", entity.getOaExamOpinion());
			JSONObject jsonObj = JSONObject.fromObject(map);
			String jsonStr = "";
			try {
				jsonStr = new String(jsonObj.toString().getBytes(getCharset()), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			return jsonStr;
		}
		
		return "";
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(TransManager.APP)
	@Override
	public boolean sendBank(String jsonData) {
		//System.out.println("sendBank:OA::" + jsonData);
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);

		JSONArray accountDetailsJson = jsonObj.getJSONArray("accountDetails");
		List<AccountDetail> accountDetails = (List<AccountDetail>) JSONArray.toCollection(accountDetailsJson,
				AccountDetail.class);
		
		jsonObj.remove("accountDetails");
		
		//保存银行账户主表
		BankAccount entity = (BankAccount) JSONObject.toBean(jsonObj, BankAccount.class);
		String id = entity.getId();
		if (bankDao.existInDB(id)) {
			bankDao.update(entity);
		} else {
			bankDao.save(entity);
		}
		
		//子表全覆盖
		accountDetailDao.deleteByProperty("accountId", id);
		accountDetailDao.save(accountDetails);
		
		return true;
	}

	@Transactional(TransManager.APP)
	@Override
	public boolean sendDesignChange(String jsonData) {
		//System.out.println("sendDesignChange:OA::" + jsonData);
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);

		//保存设计变更主表
		DesignChange entity = (DesignChange) JSONObject.toBean(jsonObj, DesignChange.class);
		String applyId = entity.getApplyId();
		if (designChangeDao.existInDB(applyId)) {
			designChangeDao.update(entity);
		} else {
			designChangeDao.save(entity);
		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(TransManager.APP)
	@Override
	public boolean sendNotReimbursed(String jsonData) {
		//System.out.println("sendNotReimbursed:OA::" + jsonData);
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);
		
		JSONArray recsJson = jsonObj.getJSONArray("recs");
		List<NotReimbursedRec> recs = (List<NotReimbursedRec>) JSONArray.toCollection(recsJson,
				NotReimbursedRec.class);
		
		jsonObj.remove("recs");

		//保存未报销现金主表
		NotReimbursed entity = (NotReimbursed) JSONObject.toBean(jsonObj, NotReimbursed.class);
		String reimbursedId = entity.getReimbursedId();
		if (notReimbursedDao.existInDB(reimbursedId)) {
			notReimbursedDao.update(entity);
		} else {
			notReimbursedDao.save(entity);
		}
		
		//子表全覆盖
		notReimbursedRecDao.deleteByProperty("reimbursedId", reimbursedId);
		notReimbursedRecDao.save(recs);
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(TransManager.APP)
	@Override
	public boolean sendStockCash(String jsonData) {
		//System.out.println("sendStockCash:OA::" + jsonData);
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);
		
		JSONArray recsJson = jsonObj.getJSONArray("recs");
		List<StockCashRec> recs = (List<StockCashRec>) JSONArray.toCollection(recsJson,
				StockCashRec.class);
		
		jsonObj.remove("recs");

		//保存库存现金主表
		StockCash entity = (StockCash) JSONObject.toBean(jsonObj, StockCash.class);
		String stockId = entity.getStockId();
		if (stockCashDao.existInDB(stockId)) {
			stockCashDao.update(entity);
		} else {
			stockCashDao.save(entity);
		}
		
		//子表全覆盖
		stockCashRecDao.deleteByProperty("stockId", stockId);
		stockCashRecDao.save(recs);
		
		return true;
	}

	/**
	 * 交付施工报盘接口实现方法
	 * @param jsonData
	 * @return 操作结果
	 */
	@SuppressWarnings("unchecked")
	@Transactional(TransManager.APP)
	@Override
	public boolean sendDeliveryConstruction(String jsonData) {
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);

		// 从表信息
		JSONArray drawingsJson = jsonObj.getJSONArray("drawings");
		List<DeliveryDrawings> deliveryDrawings = (List<DeliveryDrawings>) JSONArray.toCollection(drawingsJson,
				DeliveryDrawings.class);

		jsonObj.remove("drawings");
		//保存主表
		DeliveryConstruction delivery = (DeliveryConstruction) JSONObject.toBean(jsonObj, DeliveryConstruction.class);
		String deliveryId = delivery.getDeliveryId();
		if (deliveryDao.existInDB(deliveryId)) {
			deliveryDao.update(delivery);
		} else {
			deliveryDao.save(delivery);
		}
		// 子表全覆盖
		drawingsDao.deleteByProperty("deliveryId", deliveryId);
		drawingsDao.save(deliveryDrawings);

		return true;
	}

	/**
	 * 图纸交接报盘接口实现方法
	 * @param jsonData
	 * @return 操作结果
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean sendDrawingLibrary(String jsonData) {
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);

		// 从表信息
		JSONArray drawingsJson = jsonObj.getJSONArray("drawingVersions");
		List<DrawingLibraryVersion> drawingVersions = (List<DrawingLibraryVersion>) JSONArray.toCollection(drawingsJson,
				DrawingLibraryVersion.class);

		jsonObj.remove("drawingVersions");
		// 保存主表
		DrawingLibrary drawingLibrary = (DrawingLibrary) JSONObject.toBean(jsonObj, DrawingLibrary.class);
		// 主表主键
		String drawingId = drawingLibrary.getId();
		if (drawingLibraryDao.existInDB(drawingId)) {
			drawingLibraryDao.update(drawingLibrary);
		} else {
			drawingLibraryDao.save(drawingLibrary);
		}
		// 子表全覆盖
		drawingVersionDao.deleteByProperty("libraryId", drawingId);
		drawingVersionDao.save(drawingVersions);

		return true;
	}

	/**
	 * 图纸外审报盘接口实现方法
	 * @param jsonData
	 * @return 操作结果
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean sendExternalDrawing(String jsonData) {
		if (StringUtils.isBlank(jsonData)) {
			return false;
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(jsonData, jsonConfig);

		// 从表信息
		JSONArray drawingsJson = jsonObj.getJSONArray("drawingContents");
		List<ExternalDrawingsContent> externalContents = (List<ExternalDrawingsContent>) JSONArray
				.toCollection(drawingsJson, ExternalDrawingsContent.class);

		jsonObj.remove("drawingContents");
		// 保存主表
		ExternalDrawings externalDrawings = (ExternalDrawings) JSONObject.toBean(jsonObj, ExternalDrawings.class);
		// 主表主键
		String externalId = externalDrawings.getExternalId();
		if (externalDao.existInDB(externalId)) {
			externalDao.update(externalDrawings);
		} else {
			externalDao.save(externalDrawings);
		}
		// 子表全覆盖
		externalContentDao.deleteByProperty("externalId", externalId);
		externalContentDao.save(externalContents);

		return true;
	}
	


}
