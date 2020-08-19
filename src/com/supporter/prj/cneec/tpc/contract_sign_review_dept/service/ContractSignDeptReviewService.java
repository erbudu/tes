package com.supporter.prj.cneec.tpc.contract_sign_review_dept.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetItemConstant;
import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitContractCurrency;
import com.supporter.prj.cneec.tpc.benefit_budget.service.BenefitBudgetService;
import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNoteCurrency;
import com.supporter.prj.cneec.tpc.benefit_note.service.BenefitNoteService;
import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.contract_online_prepare.service.ContractOnlinePrepareService;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review.service.ContractSignReviewService;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao.ContractSignDeptAmountDao;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao.ContractSignDeptInforDao;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao.ContractSignDeptItem1Dao;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao.ContractSignDeptItem2Dao;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao.ContractSignDeptOpinionDao;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.dao.ContractSignDeptReviewDao;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractDeptReviewRecord;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptAmount;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptItem1;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptItem2;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptOpinion;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignItemBean;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.external_quotation_review.entity.ExternalQuotationReview;
import com.supporter.prj.cneec.tpc.external_quotation_review.service.ExternalQuotationReviewService;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity.ExternalQuotationReviewDept;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.service.ExternalQuotationReviewDeptService;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemandDetail;
import com.supporter.prj.cneec.tpc.purchase_demand.service.PurchaseDemandService;
import com.supporter.prj.cneec.tpc.purchase_demand.util.PurchaseDemandConstant;
import com.supporter.prj.cneec.tpc.purchase_demand_sheet.entity.PurchaseDemandSheet;
import com.supporter.prj.cneec.tpc.purchase_demand_sheet.service.PurchaseDemandSheetService;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.FilesUtil;
import com.supporter.prj.cneec.tpc.util.TpcBudgetUtil;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.spring_mvc.binding.JsonUtil;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @Title: ContractSignDeptReviewService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2018-03-21
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class ContractSignDeptReviewService {

	@Autowired
	private ContractSignDeptReviewDao contractSignDeptReviewDao;
	@Autowired
	private ContractSignDeptInforDao contractSignDeptInforDao;
	@Autowired
	private ContractSignDeptAmountDao contractSignDeptAmountDao;
	@Autowired
	private ContractSignDeptItem1Dao contractSignDeptItem1Dao;// 销售合同明细
	@Autowired
	private ContractSignDeptItem2Dao contractSignDeptItem2Dao;// 采购合同明细
	@Autowired
	private ContractSignDeptOpinionDao contractSignDeptOpinionDao;

	@Autowired
	private ExternalQuotationReviewDeptService externalQuotationReviewDeptService;
	@Autowired
	private ExternalQuotationReviewService externalQuotationReviewService;
	@Autowired
	private PurchaseDemandService purchaseDemandService;
	@Autowired
	private RegisterProjectService registerProjectService;

	@Autowired
	private PurchaseDemandSheetService purchaseDemandSheetService;
	@Autowired
	private ContractOnlinePrepareService contractOnlinePrepareService;

	@Autowired
	private ContractSignReviewService contractSignReviewService;

	@Autowired
	private BenefitNoteService benefitNoteService;
	@Autowired
	private BenefitBudgetService benefitBudgetService;

	/**
	 * 数据显示权限过滤
	 * 
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, ContractSignDeptReview.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * 
	 * @param userProfile
	 * @param contractSignDeptReview
	 */
	public void getAuthCanExecute(UserProfile userProfile, ContractSignDeptReview contractSignDeptReview) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, ContractSignDeptReview.MODULE_ID, contractSignDeptReview.getSignId(), contractSignDeptReview);
	}

	/**
	 * 获取合同签约评审对象集合
	 * 
	 * @param user
	 * @param jqGrid
	 * @param contractSignDeptReview
	 * @return
	 */
	public List<ContractSignDeptReview> getGrid(UserProfile user, JqGrid jqGrid, ContractSignDeptReview contractSignDeptReview) {
		String authFilter = getAuthFilter(user);
		return this.contractSignDeptReviewDao.findPage(jqGrid, contractSignDeptReview, authFilter);
	}

	/**
	 * 获取合同对象集合
	 * 
	 * @param user
	 * @param jqGrid
	 * @param parameters
	 * @return
	 */
	public List<ContractSignDeptInfor> getInforGrid(UserProfile user, JqGrid jqGrid, Map<String, Object> parameters) {
		return this.contractSignDeptInforDao.findPage(jqGrid, parameters);
	}

	/**
	 * 获取合同金额对象集合
	 * 
	 * @param user
	 * @param jqGrid
	 * @param parameters
	 * @return
	 */
	public List<ContractSignDeptAmount> getInforAmountGrid(UserProfile user, JqGrid jqGrid, Map<String, Object> parameters) {
		return this.contractSignDeptAmountDao.findPage(jqGrid, parameters);
	}

	public List<ContractSignDeptAmount> getInforAmountList(String inforId) {
		return this.contractSignDeptAmountDao.queryBy("inforId", inforId, false, "amountId", true);
	}

	public List<ContractSignDeptAmount> findInforAmountBy(String properName, String propValue) {
		return this.contractSignDeptAmountDao.queryBy(properName, propValue, false, "amountId", true);
	}

	/**
	 * 合同金额币别MAP
	 * 
	 * @param inforId
	 * @return
	 */
	public Map<String, ContractSignDeptAmount> getInforAmountMap(String inforId) {
		Map<String, ContractSignDeptAmount> amountMap = new HashMap<String, ContractSignDeptAmount>();
		List<ContractSignDeptAmount> amountList = this.getInforAmountList(inforId);
		if (amountList != null && amountList.size() > 0) {
			for (ContractSignDeptAmount amount : amountList) {
				amountMap.put(amount.getCurrencyId(), amount);
			}
		}
		return amountMap;
	}

	/**
	 * 合同金额币别汇率MAP
	 * 
	 * @param inforId
	 * @return
	 */
	public Map<String, Object> getContractAmountRateMap(String inforId, Map<String, Object> valueMap) {
		Map<String, Object> amountMap = new HashMap<String, Object>();
		Map<String, Double> amountRateMap = new HashMap<String, Double>();
		if (valueMap.containsKey("inforType")) {
			int inforType = Integer.parseInt((String) valueMap.get("inforType"));
			int initialReviewType = 0;
			if (inforType == ContractSignReviewUtil.INFOR_TYPE_CONTRACT) {
				initialReviewType = Integer.parseInt((String) valueMap.get("initialReviewType"));
			}
			// 采购合同且销售+采购评审取所有销售合同效益测算表币别合集
			if (inforType == ContractSignReviewUtil.INFOR_TYPE_CONTRACT && initialReviewType == ContractSignReviewUtil.REVIEW_TYPE_SUBCONTRACT) {
				String signId = valueMap.get("signId").toString();
				List<ContractSignDeptInfor> inforList = this.getContractSignDeptInforByInforType(signId, ContractSignReviewUtil.INFOR_TYPE_ORDER);
				List<BenefitNoteCurrency> noteCurrencyList = new ArrayList<BenefitNoteCurrency>();
				List<BenefitNoteCurrency> currencyList = null;
				for (ContractSignDeptInfor infor : inforList) {
					currencyList = this.benefitNoteService.getCurrencyListByInforId(infor.getInforId());
					if (currencyList != null && currencyList.size() > 0) {
						noteCurrencyList.addAll(currencyList);
					}
				}
				if (noteCurrencyList.size() > 0) {
					for (BenefitNoteCurrency currency : noteCurrencyList) {
						amountRateMap.put(currency.getCurrencyId(), currency.getRate());
					}
					amountMap.put("result", true);
					amountMap.put("amountRateMap", amountRateMap);
				}
			} else {
				// 销售合同、采购合同评审取合同本身金额币别汇率
				Map<String, String> amountCurrencyMap = new HashMap<String, String>();
				List<ContractSignDeptAmount> amountList = this.getInforAmountList(inforId);
				if (amountList != null && amountList.size() > 0) {
					for (ContractSignDeptAmount amount : amountList) {
						amountRateMap.put(amount.getCurrencyId(), amount.getRate());
						amountCurrencyMap.put(amount.getCurrency(),
								"汇率[" + CommonUtil.format(amount.getRate(), "#,##0.00") + "]");
					}
					amountMap.put("result", true);
					amountMap.put("amountRateMap", amountRateMap);
					amountMap.put("amountCurrencyMap", amountCurrencyMap);
				}
			}
		}
		return amountMap;
	}

	/**
	 * 合同明细所有币别MAP
	 * 
	 * @param itemList
	 * @return
	 */
	public Map<String, Double[]> getInforItemAmountMap(ContractSignDeptInfor infor, List<?> itemList) {
		if (itemList == null) {
			itemList = this.getInforItemList(infor.getInforId(), infor.getInforType());
		}
		Map<String, Double[]> itemAmountMap = new LinkedHashMap<String, Double[]>();// 所有明细币别
		ContractSignItemBean item;
		for (Object object : itemList) {
			item = (ContractSignItemBean) object;
			String budgetAmountKey = CommonUtil.trim(item.getCurrencyId());
			if (budgetAmountKey.length() > 0) {
				double a = 0, b = 0;
				if (itemAmountMap.containsKey(budgetAmountKey)) {
					a = itemAmountMap.get(budgetAmountKey)[0];
					b = itemAmountMap.get(budgetAmountKey)[1];
				}
				itemAmountMap.put(budgetAmountKey, new Double[] { a + item.getAmount(), b + item.getRmbAmount() });
			}
		}
		return itemAmountMap;
	}

	/**
	 * 获取合同货物明细对象集合
	 * 
	 * @param user
	 * @param jqGrid
	 * @param parameters
	 * @return
	 */
	public List<?> getInforItemGrid(UserProfile user, JqGrid jqGrid, Map<String, Object> parameters) {
		if (parameters != null && parameters.containsKey("purchaseContractId")) {
			return this.contractSignDeptItem2Dao.findPage(jqGrid, parameters);
		} else {
			return this.contractSignDeptItem1Dao.findPage(jqGrid, parameters);
		}
	}

	public List<?> getInforItemList(String inforId, int inforType) {
		if (inforType == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
			return this.contractSignDeptItem1Dao.findBy("saleContractId", inforId);
		} else {
			return this.contractSignDeptItem2Dao.findBy("purchaseContractId", inforId);
		}
	}

	public List<ContractSignDeptItem1> findSaleInforItemBy(String properName, String propValue) {
		return this.contractSignDeptItem1Dao.findBy(properName, propValue);
	}

	public List<ContractSignDeptItem2> findPurchaseInforItemBy(String properName, String propValue) {
		return this.contractSignDeptItem2Dao.findBy(properName, propValue);
	}

	/**
	 * 获取合同MAP集合
	 * 
	 * @param signId
	 * @return
	 */
	public Map<String, Object> getContractInforMap(String signId) {
		// 定义MAP
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		// 销售合同信息
		List<Map<String, Object>> saleContractList = new ArrayList<Map<String, Object>>();
		// 采购合同信息
		List<Map<String, Object>> purchaseContractList = new ArrayList<Map<String, Object>>();
		// 查询参数
		List<ContractSignDeptInfor> inforList = this.contractSignDeptInforDao.queryBy("signId", signId, false, "inforId", true);
		Map<String, Object> contractMap;
		int i = 0, j = 0;
		for (ContractSignDeptInfor contract : inforList) {
			contractMap = new LinkedHashMap<String, Object>();
			contractMap.put("inforId", contract.getInforId());
			contractMap.put("reviewNo", contract.getReviewNo());
			contractMap.put("contractParty", contract.getContractParty());
			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				contractMap.put("inforId_index", "saleInforId" + i);
				saleContractList.add(contractMap);
				i++;
			} else {
				contractMap.put("inforId_index", "purchaseInforId" + j);
				purchaseContractList.add(contractMap);
				j++;
			}
		}
		map.put("saleContractSize", saleContractList.size());
		map.put("saleContractList", saleContractList);
		map.put("purchaseContractSize", purchaseContractList.size());
		map.put("purchaseContractList", purchaseContractList);
		return map;
	}

	/**
	 * 获取合同
	 * 
	 * @param signId
	 * @param infortype
	 * @return
	 */
	public List<ContractSignDeptInfor> getContractSignDeptInforByInforType(String signId, Integer infortype) {
		return this.contractSignDeptInforDao.getContractSignDeptInforByInforType(signId, infortype);
	}

	public List<ContractSignDeptInfor> findInforBy(String properName, String propValue) {
		return this.contractSignDeptInforDao.queryBy(properName, propValue, false, "inforId", true);
	}

	/**
	 * 获取单个合同签约评审对象
	 * 
	 * @param signId
	 * @return
	 */
	public ContractSignDeptReview get(String signId) {
		ContractSignDeptReview contractSignDeptReview = this.contractSignDeptReviewDao.get(signId);
		List<ContractSignDeptOpinion> signOpinions = this.contractSignDeptOpinionDao.findBy("signId", signId);
		if (contractSignDeptReview != null) {
			contractSignDeptReview.setSignOpinions(signOpinions);
		}
		return contractSignDeptReview;
	}

	/**
	 * 新建合同签约评审对象
	 * 
	 * @param user
	 * @return
	 */
	public ContractSignDeptReview newContractSignDeptReview(String signId, UserProfile user) {
		ContractSignDeptReview contractSignDeptReview = new ContractSignDeptReview(signId);
		loadContractSignDeptReview(contractSignDeptReview, user);
		contractSignDeptReview.setSwfStatus(ContractSignDeptReview.DRAFT);
		List<ContractSignDeptOpinion> signOpinions = newContractSignDeptOpinions(contractSignDeptReview);
		contractSignDeptReview.setSignOpinions(signOpinions);
		return contractSignDeptReview;
	}

	/**
	 * 添加基础信息
	 * 
	 * @return
	 */
	public ContractSignDeptReview loadContractSignDeptReview(ContractSignDeptReview contractSignDeptReview, UserProfile user) {
		contractSignDeptReview.setCreatedBy(user.getName());
		contractSignDeptReview.setCreatedById(user.getPersonId());
		contractSignDeptReview.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		contractSignDeptReview.setModifiedBy(user.getName());
		contractSignDeptReview.setModifiedById(user.getPersonId());
		contractSignDeptReview.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			contractSignDeptReview.setDeptName(dept.getName());
			contractSignDeptReview.setDeptId(dept.getDeptId());
		}
		// 设置状态
		contractSignDeptReview.setSwfStatus(ContractSignDeptReview.DRAFT);
		return contractSignDeptReview;
	}

	/**
	 * 初始化评审意见
	 * 
	 * @param contractSignDeptReview
	 * @return
	 */
	public List<ContractSignDeptOpinion> newContractSignDeptOpinions(ContractSignDeptReview contractSignDeptReview) {
		List<ContractSignDeptOpinion> signOpinions = new ArrayList<ContractSignDeptOpinion>();
		ContractSignDeptOpinion contractSignDeptOpinion;
		for (Map.Entry<Integer, String> entry : ContractSignReviewUtil.getOpinionManMap().entrySet()) {
			contractSignDeptOpinion = new ContractSignDeptOpinion();
			contractSignDeptOpinion.setSignId(contractSignDeptReview.getSignId());
			contractSignDeptOpinion.setDisplayOrder(entry.getKey());
			contractSignDeptOpinion.setOpinionMan(entry.getValue());
			signOpinions.add(contractSignDeptOpinion);
		}
		return signOpinions;
	}

	/**
	 * 初始化对象
	 * 
	 * @param signId
	 * @param user
	 * @return
	 */
	public ContractSignDeptReview initNew(ContractSignDeptReview contractSignDeptReview, Map<String, Object> parameters, UserProfile user) {
		if (contractSignDeptReview == null) {
			contractSignDeptReview = newContractSignDeptReview(UUIDHex.newId(), user);
		} else if (CommonUtil.trim(contractSignDeptReview.getSignId()).length() > 0) {
			return this.get(contractSignDeptReview.getSignId());
		} else {
			contractSignDeptReview.setSignId(UUIDHex.newId());
		}
		contractSignDeptReview.setAdd(true);
		this.saveOrUpdate(user, contractSignDeptReview, parameters);
		return contractSignDeptReview;
	}

	/**
	 * 保存或修改对象
	 * 
	 * @param user
	 * @param contractSignDeptReview
	 * @param valueMap
	 * @return
	 */
	public String saveOrUpdateByNew(UserProfile user, ContractSignDeptReview contractSignDeptReview, Map<String, Object> valueMap) {

		// 验证是否可以提交
		String json;

		/**
		 * 验证是否可以进行下一步 A.至少有一条合同记录
		 * 
		 * B.销售合同/采购合同评审： a).合同总金额不能为0 b).金额记录不能为空 c).合同明细不能为空 d).销售合同效益测算表不能为空
		 * e).执行汇率不能为0（采购合同明细查找对应效益测算表中是否有该币别有则取之无则取采购合同币别执行汇率）；
		 */
		List<ContractSignDeptInfor> contractInforList = this.contractSignDeptInforDao.queryBy("signId", contractSignDeptReview.getSignId(), false, "inforId", true);
		// A.至少有一条合同记录
		if (contractInforList.size() == 0) {
			json = "{\"valid\": false,\"contractMsg\": \"\",\"msg\": \"contractInfor-fail-empty\"}";
			return json;
		} else {
			// 销售+采购合同评审验证是否有采购合同
			boolean isSubContractReview = contractSignDeptReview.getReviewType() == ContractSignReviewUtil.REVIEW_TYPE_SUBCONTRACT;
			List<ContractSignDeptAmount> amountList;
			Map<String, Double[]> itemAmountMap;
			List<ContractSignDeptItem2> item2List;
			int count = 0;
			int size = contractInforList.size();
			String inforId = null;
			String contractMsg = "";
			BenefitNoteCurrency itemCurrency;
			for (ContractSignDeptInfor contract : contractInforList) {
				inforId = contract.getInforId();
				contractMsg = contract.getInforTypeDesc() + ":" + contract.getContractParty();
				// a).合同总金额不能为0
				if (contract.getTotalRmbAmount() == 0) {
					json = "{\"valid\": false,\"contractMsg\": \"" + contractMsg + "\",\"msg\": \"contractAmount-fail-zero\"}";
					return json;
				}
				// b).金额记录不能为空
				amountList = this.getInforAmountList(contract.getInforId());
				if (amountList == null || amountList.size() == 0) {
					json = "{\"valid\": false,\"contractMsg\": \"" + contractMsg + "\",\"msg\": \"contractInfor-fail-empty-amount\"}";
					return json;
				}
				// c).合同明细不能为空
				itemAmountMap = this.getInforItemAmountMap(contract, null);
				if (itemAmountMap.size() == 0) {
					json = "{\"valid\": false,\"contractMsg\": \"" + contractMsg + "\",\"msg\": \"contractInfor-fail-empty-item\"}";
					return json;
				}
				if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
					// d).销售合同效益测算表不能为空
					if (StringUtils.isBlank(contract.getNoteId())) {
						json = "{\"valid\": false,\"contractMsg\": \"" + contractMsg + "\",\"msg\": \"contractInfor-fail-empty-benefitNote\"}";
						return json;
					} else {
						// 遍历合同币别项
						for (Map.Entry<String, Double[]> e : itemAmountMap.entrySet()) {
							itemCurrency = this.benefitNoteService.queryUniqueCurrency(contract.getNoteId(), e.getKey());
							if (itemCurrency == null) {
								// d).销售合同币别在合同效益测算表不存在
								json = "{\"valid\": false,\"contractMsg\": \"" + contractMsg + "\",\"msg\": \"contractInfor-fail-empty-benefitNoteCurrency\"}";
								return json;
							}
						}
					}
					count++;
				} else {
					// e).采购合同币别执行汇率不能为0
					item2List = this.findPurchaseInforItemBy("purchaseContractId", inforId);
					for (ContractSignDeptItem2 item : item2List) {
						// 查找采购合同采购项币别(外币)在销售合同效益测算表是否存在
						if (!TpcConstant.STANDARD_CURRENCY.equals(item.getCurrencyId()) && item.getCurrencyId() != null) {
							itemCurrency = this.benefitNoteService.queryUniqueCurrencyByInforId(item.getSaleContractId(), item.getCurrencyId());
							if (itemCurrency == null) {
								json = "{\"valid\": false,\"contractMsg\": \"" + contractMsg + "的" + item.getItemName() + "币别[" + item.getCurrency() + "]"
										+ "\",\"msg\": \"contractInfor-fail-item-noExecuteRate\"}";
								return json;
							}
						}
					}
					count++;
				}
				// 删除合同多余金额币别
				if (amountList.size() > itemAmountMap.size()) {
					for (ContractSignDeptAmount amount : amountList) {
						if (!itemAmountMap.containsKey(amount.getCurrencyId())) {
							this.contractSignDeptAmountDao.delete(amount);
						}
					}
					// 有可能多余币别金额计算入合同总金额，需要重新计算
					updateContractSignDeptAmountByItem(inforId, null);
				}
				String fileNames = getFileNames(contract);
				contract.setReviewBasis(fileNames);// 设置评审依据资料为附件名称
			}
			// 销售合同采购合同评审时仅有一种合同类别
			if (isSubContractReview && count != size) {
				json = "{\"valid\": false,\"contractMsg\": \"" + contractMsg + "\",\"msg\": \"contractInfor-fail-onlyOneTypeContract\"}";
				return json;
			}
		}

		/** 验证通过 **/

		// 生成编号
		if (StringUtils.isBlank(contractSignDeptReview.getReviewNo())) {
			contractSignDeptReview.setReviewNo(generatorReviewNo());
		}
		// 装填项目信息
		String projectId = contractSignDeptReview.getProjectId();
		RegisterProject project = registerProjectService.get(projectId);
		contractSignDeptReview.setProjectName(project.getProjectName());
		contractSignDeptReview.setProjectNature(project.getProjectNature());
		contractSignDeptReview.setProjectNatureId(project.getProjectNatureId());
		contractSignDeptReview.setProjectClassification(project.getProjectClassification());
		contractSignDeptReview.setProjectClassificationId(project.getProjectClassificationId());

		// 装填客户采购需求单信息(取本评审单下销售合同存储的客户包ID,继而查询采购需求单,由于可能将某个包下所有明细删除所以取明细中ID不太准确)
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("signId", contractSignDeptReview.getSignId());
		Map<String, Boolean> orderByMap = new LinkedHashMap<String, Boolean>();
		orderByMap.put("inforId", true);
		// 有销售合同的取销售合同,无销售合同取采购合同
		boolean hasSaleContract = contractSignDeptReview.getReviewType() != ContractSignReviewUtil.REVIEW_TYPE_CONTRACT;
		if (hasSaleContract) {
			params.put("inforType", ContractSignReviewUtil.INFOR_TYPE_ORDER);
		} else {
			params.put("inforType", ContractSignReviewUtil.INFOR_TYPE_CONTRACT);
		}
		List<ContractSignDeptInfor> contractList = this.contractSignDeptInforDao.find(params, orderByMap);
		String recordIds = "";
		List<Double> contractAmountList = new ArrayList<Double>();
		contractAmountList.add(0d);
		int i = 0;
		String reviewNo = contractSignDeptReview.getReviewNo();
		for (ContractSignDeptInfor contract : contractList) {
			// 获取合同评审金额
			contractAmountList.add(contract.getTotalRmbAmount());
			// 有销售合同取采购需求单等信息,顺便修改评审单号
			if (hasSaleContract) {
				if (recordIds.length() > 0)
					recordIds += ",";
				recordIds += contract.getRecordId();
				contract.setReviewNo(reviewNo + "-" + String.format("%03d", i + 1));
				contractList.set(i, contract);
			}
			i++;
		}
		// 更新销售合同评审单号（测试发现已经更新存入数据库中了，保险起见还是更新保存一下）
		if (hasSaleContract && contractList.size() > 0) {
			this.contractSignDeptInforDao.update(contractList);
		}

		// 客户需求单
		String demandIds = "", batchNos = "", purchaseDemandNames = "";
		// 对外报价评审单
		String externalIds = "", quotationTitles = "";
		if (recordIds.length() > 0) {
			List<PurchaseDemandSheet> sheetList = this.purchaseDemandSheetService.getListByRecordIds(recordIds);
			// 记录客户需求单
			Map<String, String> demandReviewMap = new LinkedHashMap<String, String>();
			Map<String, String> demandNoMap = new LinkedHashMap<String, String>();
			Map<String, String> demandNameMap = new LinkedHashMap<String, String>();
			for (PurchaseDemandSheet sheet : sheetList) {
				demandReviewMap.put(sheet.getDemandId(), sheet.getReviewClassification());
				demandNoMap.put(sheet.getDemandId(), sheet.getBatchNo());
				demandNameMap.put(sheet.getDemandId(), sheet.getPurchaseDemandName());
			}
			demandIds = StringUtils.join(demandNoMap.keySet().toArray(), ",");
			batchNos = StringUtils.join(demandNoMap.values().toArray(), ",");
			purchaseDemandNames = StringUtils.join(demandNameMap.values().toArray(), ",");
			// 获取对外报价评审信息
			for (Map.Entry<String, String> e : demandReviewMap.entrySet()) {
				if (e.getValue().equals(PurchaseDemandConstant.REVIEW_AGENT_YES) || e.getValue().equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_XIAO_YES)) {
					// 代理评审或自营小额评审取事业部评审表
					ExternalQuotationReviewDept deptReview = externalQuotationReviewDeptService.getExternalQuotationReviewDeptByDemandId(e.getKey());
					if (deptReview != null) {
						externalIds += "," + deptReview.getExternalId();
						quotationTitles += "," + deptReview.getQuotationTitle();
					}
				} else if (e.getValue().equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_DA) || e.getValue().equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_SUPER)) {
					// 自营大额/超大额评审取公司评审表
					ExternalQuotationReview review = externalQuotationReviewService.getExternalQuotationReviewByDemandId(e.getKey());
					if (review != null) {
						externalIds += "," + review.getExternalId();
						quotationTitles += "," + review.getQuotationTitle();
					}
				}
			}
			externalIds = externalIds.replaceFirst(",", "");
			quotationTitles = quotationTitles.replaceFirst(",", "");
		}
		contractSignDeptReview.setDemandId(demandIds);
		contractSignDeptReview.setBatchNo(batchNos);
		contractSignDeptReview.setPurchaseDemandName(purchaseDemandNames);
		contractSignDeptReview.setExternalId(externalIds);
		contractSignDeptReview.setQuotationTitle(quotationTitles);

		// 获取最大合同金额转美元金额作为评审金额
		double reviewAmount = (Double) Collections.max(contractAmountList);
		Map<String, String> rateMap = TpcConstant.getCurrencyRateMap();
		// 从码表中取人民币对美元汇率
		double usdRate = 1;
		if (rateMap.containsKey("USD")) {
			usdRate = CommonUtil.parseDouble(rateMap.get("USD").toString(), 1);
		}
		contractSignDeptReview.setReviewAmount(reviewAmount / usdRate);
		this.contractSignDeptReviewDao.update(contractSignDeptReview);

		json = "{\"valid\": true,\"isMeetReview\": " + contractSignDeptReview.isMeetReview() + ",\"msg\": \"\"}";
		return json;
	}

	/**
	 * 获取附件名称
	 * 
	 * @param contract
	 * @return
	 */
	public String getFileNames(ContractSignDeptInfor contract) {
		String busiType;
		if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
			busiType = ContractSignDeptReview.BUSI_TYPE_ORDER;
		} else {
			busiType = ContractSignDeptReview.BUSI_TYPE_CONTRACT;
		}
		String fileNames = FilesUtil.getFileNames(ContractSignDeptReview.MODULE_ID, busiType, contract.getInforId(), "");
		return fileNames;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * 
	 * @param signId
	 * @param user
	 * @return
	 */
	public ContractSignDeptReview initEditOrViewPage(String signId, UserProfile user) {
		ContractSignDeptReview contractSignDeptReview;
		if (StringUtils.isBlank(signId)) {
			contractSignDeptReview = newContractSignDeptReview(UUIDHex.newId(), user);
			contractSignDeptReview.setAdd(true);
		} else {
			contractSignDeptReview = this.get(signId);
			contractSignDeptReview.setAdd(false);
		}
		return contractSignDeptReview;
	}

	/**
	 * 保存或修改对象
	 * 
	 * @param user
	 * @param contractSignDeptReview
	 * @param valueMap
	 * @return
	 */
	public ContractSignDeptReview saveOrUpdate(UserProfile user, ContractSignDeptReview contractSignDeptReview, Map<String, Object> valueMap) {
		if (contractSignDeptReview.getAdd()) {
			// 装配基础信息
			loadContractSignDeptReview(contractSignDeptReview, user);
			this.contractSignDeptReviewDao.save(contractSignDeptReview);
		} else {
			// 设置更新时间
			contractSignDeptReview.setModifiedBy(user.getName());
			contractSignDeptReview.setModifiedById(user.getPersonId());
			contractSignDeptReview.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.contractSignDeptReviewDao.update(contractSignDeptReview);
		}
		if (CollectionUtils.isNotEmpty(contractSignDeptReview.getSignOpinions())) {
			for (ContractSignDeptOpinion opinion : contractSignDeptReview.getSignOpinions()) {
				this.contractSignDeptOpinionDao.saveOrUpdate(opinion);
			}
		}
		return contractSignDeptReview;
	}

	/**
	 * 提交后需要处理业务操作
	 * 
	 * @param contractSignDeptReview
	 */
	public void afterCommitExecute(UserProfile user, ContractSignDeptReview contractSignDeptReview) {
		// 判断评审信息（会审直接完成）
		if (contractSignDeptReview.isMeetReview()) {
			updateBusinissByMeetFinish(user, contractSignDeptReview);
		} else {
			updateBenefitNote(contractSignDeptReview);
		}
	}

	/**
	 * 会议评审单完成需要处理的业务： 1.评审完成后处理合同评审业务 2.评审完成后处理项目预算业务
	 */
	public void updateBusinissByMeetFinish(UserProfile user, ContractSignDeptReview contractSignDeptReview) {
		finishReviewBusiness(contractSignDeptReview);
		finishCreatedByConfirmBusiness(user, contractSignDeptReview);
	}

	/**
	 * 评审完成后，系统确认完成需要处理的业务 1.设置销售/采购合同评审意见 2.设置合同对应的客户需求选择单状态（不需要公司评审时）
	 * 3.销售合同设置效益测算表确认状态 4.设置合同签约前评审单意见 5.生成合同签约公司评审草稿（需要公司评审时）
	 * 
	 * @param contractSignDeptReview
	 */
	public void finishReviewBusiness(ContractSignDeptReview contractSignDeptReview) {
		// 自营大额、自营超大额需要公司评审
		boolean needCompanyReview = contractSignDeptReview.isCompanyReview();
		// 设置合同评审结论为同意
		List<ContractSignDeptInfor> contractList = this.contractSignDeptInforDao.queryBy("signId", contractSignDeptReview.getSignId(), false, "inforId", true);
		for (ContractSignDeptInfor contract : contractList) {
			// 1.设置销售/采购合同评审意见
			if (CommonUtil.trim(contract.getReviewConclusion()).length() == 0) {
				contract.setReviewConclusion(TpcConstant.REVIEW_CONCLUSION_AGREE);
			}
			this.contractSignDeptInforDao.update(contract);

			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				if (!needCompanyReview) {// 不需要公司评审
					// 2.设置合同对应的客户需求选择单状态
					this.purchaseDemandSheetService.updateReviewStatusReviewedByRecordIds(contract.getRecordId(), contract.getReviewConclusion());
				}
				// 3.销售合同设置效益测算表确认状态
				this.benefitNoteService.confirmed(contract.getNoteId());
			}
		}

		// 4.设置合同签约前评审单意见
		contractSignDeptReview.setReviewConclusion(TpcConstant.REVIEW_CONCLUSION_AGREE);
		// 需要公司评审或会审置为完成
		if (needCompanyReview || contractSignDeptReview.isMeetReview()) {
			contractSignDeptReview.setSwfStatus(ContractSignDeptReview.COMPLETED);
		}
		update(contractSignDeptReview);

		// 5.生成合同签约公司评审草稿（需要公司评审时）
		if (needCompanyReview) {
			// 需要公司评审的生成合同签约公司评审草稿
			this.contractSignReviewService.createContractSignReviewByDeptReview(contractSignDeptReview.getSignId(), null);
		}
	}

	/**
	 * 评审完成后，经办人确认后处理的业务 1.将合同写入用印、备案、合同信息上线初始化选择单中（不需要公司评审时） 2.将销售合同预算写入项目预算库中
	 * 3.将采购合同金额转入在途（即加锁预算）
	 * 
	 * @param user
	 * @param contractSignReview
	 */
	public void finishCreatedByConfirmBusiness(UserProfile user, ContractSignDeptReview contractSignDeptReview) {
		boolean needCompanyReview = contractSignDeptReview.isCompanyReview();
		List<ContractSignDeptInfor> contractList = this.contractSignDeptInforDao.queryBy("signId", contractSignDeptReview.getSignId(), false, "inforId", true);

		for (ContractSignDeptInfor contract : contractList) {
			// 1.将合同写入用印、备案、合同信息上线初始化选择单中（不需要公司评审时）
			if (!needCompanyReview) {
				this.contractOnlinePrepareService.createdByContractSignDeptReview(contractSignDeptReview, contract);
			}

			// 2.将销售合同预算写入项目预算库中
			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				this.benefitBudgetService.createBenefitByBenefitNote(contract.getNoteId());
			}
		}

		// 3.将采购合同金额转入在途（即加锁预算）
		String projectId = contractSignDeptReview.getProjectId();
		// 采购合同扣除预算即为采购合同总金额下货款（服务款）金额
		String budgetId = BenefitBudgetItemConstant.SUMMARY_PURCHASE_PAYMENT;
		String inforId;
		for (ContractSignDeptInfor contract : contractList) {
			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_CONTRACT) {
				inforId = contract.getInforId();

				// // 按合同币别金额计算扣除预算
				// List<ContractSignDeptAmount> contractAmounts =
				// this.getInforAmountList(contract.getInforId());
				// for (ContractSignDeptAmount contractAmount : contractAmounts)
				// {
				// //
				// 扣除预算时按原币*执行汇率折合人民币计算（执行汇率效益测算表中对应币别汇率，如果该币别在效益测算表中不存在由则业务人员输入）
				// this.addOnwayBudgetAmount(user, projectId, budgetId,
				// contractAmount.getOriginalAmount() *
				// contractAmount.getExecuteRate());
				// }

				// 按采购合同明细币别金额扣除预算
				// 获取采购合同金额
				Map<String, ContractSignDeptAmount> amountMap = this.getInforAmountMap(inforId);
				List<ContractSignDeptItem2> itemList = this.findPurchaseInforItemBy("purchaseContractId", inforId);
				for (ContractSignDeptItem2 item : itemList) {
					double executeRate = 0;
					// 获取采购明细对应销售合同的效益测算表（过程合同效益测算表）
					BenefitContractCurrency benefitContractCurrency = this.benefitBudgetService.queryUniqueCurrency(item.getSaleContractId(), item.getCurrencyId());
					if (benefitContractCurrency != null) {
						executeRate = benefitContractCurrency.getRate();
					} else if (amountMap.get(item.getCurrencyId()) != null) {
						executeRate = amountMap.get(item.getCurrencyId()).getExecuteRate();
					}
					// 扣除预算时按原币*执行汇率折合人民币计算（执行汇率效益测算表中对应币别汇率，如果该币别在效益测算表中不存在由则业务人员输入）
					this.addOnwayBudgetAmount(user, projectId, budgetId, item.getAmount() * executeRate);
				}
			}
		}
	}

	/**
	 * 审批完成执行操作
	 * 
	 * @param contractSignDeptReview
	 */
	public void completeExam(ContractSignDeptReview contractSignDeptReview) {
		finishReviewBusiness(contractSignDeptReview);
	}

	/**
	 * 网审知会页面经办人确认评审完成(未修改效益测算表直接确认完成)
	 */
	public String completeConfirm(UserProfile user, String signId) {
		ContractSignDeptReview contractSignDeptReview = this.get(signId);
		String json = "{\"success\": true,\"msg\": \"\"}";
		boolean flag = true;
		List<ContractSignDeptInfor> contractList = this.contractSignDeptInforDao.queryBy("signId", contractSignDeptReview.getSignId(), false, "inforId", true);
		for (ContractSignDeptInfor contract : contractList) {
			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				// 判断效益测算表是否确认完成
				if (!this.benefitNoteService.isBenefitNoteConfirmed(contract.getNoteId(), false)) {
					json = "{\"success\": false,\"msg\": \"(" + contract.getBenefitNo() + ")" + contract.getContractName() + "\"}";
					flag = false;
					break;
				}
			}
		}
		if (flag) {// 验证通过
			contractSignDeptReview.setSwfStatus(ContractSignDeptReview.COMPLETED);
			update(contractSignDeptReview);
			finishCreatedByConfirmBusiness(user, contractSignDeptReview);
		}
		return json;
	}

	/**
	 * 添加在途金额(审批完成扣除采购合同预算)
	 * 
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void addOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.addOnwayBudgetAmount(TpcBudgetUtil.TPC_CONTRACT_SIGN_DEPT_REVIEW, user, projectId, budgetId, amount);
	}

	/**
	 * 验证是否可以提交（会审验证是否所有效益测算表提交流程确认）
	 */
	public String commitByMeetReview(UserProfile user, ContractSignDeptReview contractSignDeptReview, Map<String, Object> valueMap) {
		String json = "{\"success\": true,\"msg\": \"\"}";
		boolean flag = true;
		// 是否验证效益测算表提交
		boolean validBenefitNote = Boolean.valueOf((String) valueMap.get("validBenefitNote"));
		List<ContractSignDeptInfor> contractList = this.contractSignDeptInforDao.queryBy("signId", contractSignDeptReview.getSignId(), false, "inforId", true);
		for (ContractSignDeptInfor contract : contractList) {
			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				if (validBenefitNote) {
					// 验证效益测算表是否已提交
					if (!this.benefitNoteService.isSubmitBenefitNote(contract.getNoteId())) {
						json = "{\"success\": false,\"msg\": \"(" + contract.getBenefitNo() + ")" + contract.getContractName() + "\"}";
						flag = false;
						break;
					}
				} else {
					// 验证效益测算表是否流程审批完成
					if (!this.benefitNoteService.isBenefitNoteConfirmed(contract.getNoteId(), true)) {
						json = "{\"success\": false,\"msg\": \"(" + contract.getBenefitNo() + ")" + contract.getContractName() + "\"}";
						flag = false;
						break;
					}
				}
			}
		}
		if (!validBenefitNote && flag) {// 验证通过
			// 处理会审完成业务
			updateBusinissByMeetFinish(user, contractSignDeptReview);
		}
		return json;
	}

	/**
	 * 保存提交
	 * 
	 * @param user
	 * @param contractSignDeptReview
	 * @param valueMap
	 * @return
	 */
	public ContractSignDeptReview commit(UserProfile user, ContractSignDeptReview contractSignDeptReview, Map<String, Object> valueMap) {
		saveOrUpdate(user, contractSignDeptReview, valueMap);
		afterCommitExecute(user, contractSignDeptReview);
		// 记录日志
		// EIPService.getLogService("PURCHASE_APPLY").info(user,
		// Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " +
		// contractSignDeptReview + "}", null, null);
		return contractSignDeptReview;
	}

	/**
	 * 给效益测算变更记录表设置评审单属性
	 * 
	 * @param contractSignReview
	 */
	public void updateBenefitNote(ContractSignDeptReview contractSignDeptReview) {
		List<ContractSignDeptInfor> contractList = this.contractSignDeptInforDao.queryBy("signId", contractSignDeptReview.getSignId(), false, "inforId", true);
		for (ContractSignDeptInfor contract : contractList) {
			if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER && StringUtils.isNotBlank(contract.getNoteId())) {
				this.benefitNoteService.confirming(contract.getNoteId());
			}
		}
	}

	/**
	 * 会审发送会签人知会
	 * 
	 * @param contractSignDeptReview
	 */
	public void sendMeetNotifyMsg(ContractSignDeptReview contractSignDeptReview) {
		contractSignDeptReview.setSwfStatus(ContractSignDeptReview.PROCESSING);
		update(contractSignDeptReview);
		// 获取会签人
		if (StringUtils.isNotBlank(contractSignDeptReview.getSignerId())) {
			String[] signers = contractSignDeptReview.getSignerId().split(",");
			if (signers.length > 0) {
				for (String personId : signers) {
					Message message = new Todo();
					message.setPersonId(personId);
					message.setEventTitle(contractSignDeptReview.getDeptName() + contractSignDeptReview.getProjectName() + contractSignDeptReview.getReviewNo()
							+ "合同签约前事业部评审，会议资料如下，请查收");
					message.setNotifyTime(new Date());
					message.setWebPageURL("tpc/contract_sign_review_dept/contract_sign_dept_review_meet_notify.html?isCcPage=true&signId=" + contractSignDeptReview.getSignId());
					message.setMessageType(ITodo.MsgType.CC);
					message.setRelatedRecordTable(ContractSignDeptReview.MODULE_ID);
					EIPService.getBMSService().addMessage(message);
				}
			}
		}
		
	}

	/**
	 * 更新对象
	 * 
	 * @param review
	 * @return
	 */
	public ContractSignDeptReview update(ContractSignDeptReview review) {
		this.contractSignDeptReviewDao.update(review);
		return review;
	}

	/**
	 * 更新合同对象
	 * 
	 * @param infor
	 * @return
	 */
	public ContractSignDeptInfor update(ContractSignDeptInfor infor) {
		this.contractSignDeptInforDao.update(infor);
		return infor;
	}

	/**
	 * 生成评审单号
	 * 
	 * @return
	 */
	public synchronized String generatorReviewNo() {
		String reviewNo = null;
		String NoHead = "CNEECMP" + CommonUtil.format(new Date(), "yyyyMM");
		// 过滤条件(取已生成评审单号的记录)
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("reviewNo", NoHead + "%");
		List<String> likeSearhNames = new ArrayList<String>();
		likeSearhNames.add("reviewNo");
		Map<String, Boolean> orderByMap = new LinkedHashMap<String, Boolean>();
		orderByMap.put("reviewNo", false);
		List<ContractSignDeptReview> list = this.contractSignDeptReviewDao.find(params, likeSearhNames, orderByMap);
		Integer count = list.size();
		// 已有记录取最大
		if (count != 0) {
			count = Integer.parseInt(list.get(0).getReviewNo().substring(NoHead.length()));
		}
		String NoEnd = String.format("%03d", count + 1);
		reviewNo = NoHead + NoEnd;
		return reviewNo;
	}

	/**
	 * 批量删除对象
	 * 
	 * @param user
	 * @param signIds
	 */
	public void delete(UserProfile user, String signIds) {
		if (StringUtils.isNotBlank(signIds)) {
			ContractSignDeptReview contractSignDeptReview;
			for (String signId : signIds.split(",")) {
				contractSignDeptReview = this.get(signId);
				if (contractSignDeptReview == null)
					continue;
				// 权限验证
				this.getAuthCanExecute(user, contractSignDeptReview);
				// 获取评审单下合同信息（删除合同前恢复选择客户采购需求单）
				List<ContractSignDeptInfor> contractList = this.contractSignDeptInforDao.queryBy("signId", signId, false, "inforId", true);
				for (ContractSignDeptInfor contract : contractList) {
					String[] sourceModule;
					if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
						// 删除效益测算表
						String noteId = CommonUtil.trim(contract.getNoteId());
						if (noteId.length() > 0) {
							this.benefitNoteService.delete(user, noteId);
						}
						// 修改选择单状态（未评审,可以再次生成销售合同）
						this.purchaseDemandSheetService.updateReviewStatusByRecordIds(contract.getRecordId(), PurchaseDemandSheet.NO_REVIEW);
						sourceModule = new String[] { ContractSignReview.MODULE_ID, ContractSignReview.BUSI_TYPE_ORDER, contract.getInforId(), "" };
					} else {
						// 清除销售合同明细的采购合同属性，删除采购合同明细
						this.contractSignDeptItem1Dao.removePurchaseValue(contract.getInforId());
						sourceModule = new String[] { ContractSignReview.MODULE_ID, ContractSignReview.BUSI_TYPE_CONTRACT, contract.getInforId(), "" };
					}
					FilesUtil.deleteFiles(sourceModule);
				}
				this.contractSignDeptInforDao.deleteByProperty("signId", signId);
				this.contractSignDeptAmountDao.deleteByProperty("signId", signId);
				this.contractSignDeptItem1Dao.deleteByProperty("saleReviewId", signId);
				this.contractSignDeptItem2Dao.deleteByProperty("purchaseReviewId", signId);
				this.contractSignDeptOpinionDao.deleteByProperty("signId", signId);
				this.contractSignDeptReviewDao.delete(contractSignDeptReview);
			}
		}
	}

	/**
	 * 判断两个数组是否相同
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public boolean isEquals(String[] array1, String[] array2) {
		Arrays.sort(array1);
		Arrays.sort(array2);
		return Arrays.equals(array1, array2);
	}

	/**
	 * 判断数组是否包含另一个数组
	 * 
	 * @param baseArr
	 * @param targetArr
	 * @return
	 */
	public boolean isContained(String[] baseArr, String[] targetArr) {
		if (baseArr == null || targetArr == null)
			return false;
		if (baseArr.length < targetArr.length)
			return false;
		String aStr = StringUtils.join(baseArr, ",");
		for (int i = 0, len = targetArr.length; i < len; i++) {
			if (aStr.indexOf(targetArr[i]) == -1)
				return false;
		}
		return true;
	}

	/**
	 * 获取单个合同签约评审对象
	 * 
	 * @param inforId
	 * @return
	 */
	public ContractSignDeptInfor getContractSignDeptInfor(String inforId) {
		return this.contractSignDeptInforDao.get(inforId);
	}

	public static boolean existByLoop(String[] arr, String tarVal) {
		for (String s : arr) {
			if (s.equals(tarVal))
				return true;
		}
		return false;
	}

	/**
	 * 生成销售合同
	 * 
	 * @param signId
	 * @param sheetIds
	 *            单个或多个选择单ID
	 * @return
	 */
	public boolean createSaleContract(String signId, Integer initialReviewType, String sheetIds) {
		List<PurchaseDemandSheet> sheetList = this.purchaseDemandSheetService.getListBySheetIds(sheetIds);
		if (sheetList != null && sheetList.size() > 0) {
			PurchaseDemandSheet sheet = sheetList.get(0);
			// String singleCategoryCode = sheet.getSingleCategoryCode();
			// 生成合同
			ContractSignDeptInfor contractSignDeptInfor = new ContractSignDeptInfor();
			contractSignDeptInfor.setInforId(UUIDHex.newId());
			contractSignDeptInfor.setInforType(ContractSignReviewUtil.INFOR_TYPE_ORDER);
			contractSignDeptInfor.setSignId(signId);
			contractSignDeptInfor.setCustomerId(sheet.getCustomerId());
			contractSignDeptInfor.setContractParty(sheet.getCustomerName());
			// 记录客户包
			Map<String, String> recordMap = new LinkedHashMap<String, String>();
			// 记录客户MAP
			Map<String, String> customerMap = new LinkedHashMap<String, String>();
			// 记录币别MAP
			Map<String, Object[]> currencyMap = new LinkedHashMap<String, Object[]>();
			for (PurchaseDemandSheet demandSheet : sheetList) {
				recordMap.put(demandSheet.getRecordId(), demandSheet.getBatchNo());
				customerMap.put(demandSheet.getCustomerId(), demandSheet.getCustomerName());
				// 设置币别、预估金额、汇率
				currencyMap.put(demandSheet.getCurrencyId(), new Object[] { demandSheet.getCurrency(), demandSheet.getEstimatedAmount(), demandSheet.getRate() });
			}

			// 判断客户是否多个
			if (customerMap.size() > 1) {
				return false;
			}
			// 保存销售合同
			String recordIds = StringUtils.join(recordMap.keySet().toArray(), ",");
			contractSignDeptInfor.setRecordId(recordIds);
			this.contractSignDeptInforDao.save(contractSignDeptInfor);
			// 修改选择单状态（评审中，禁止再次生成销售合同）
			this.purchaseDemandSheetService.updateReviewStatusBySheetIds(sheetIds, PurchaseDemandSheet.REVIEWING);

			// 定义合同编号
			String saleContractId = contractSignDeptInfor.getInforId();

			// 生成合同预算金额表
			List<ContractSignDeptAmount> amountList = new ArrayList<ContractSignDeptAmount>();
			ContractSignDeptAmount signDeptAmount;
			double totalRmbAmount = 0;
			for (Map.Entry<String, Object[]> e : currencyMap.entrySet()) {
				signDeptAmount = new ContractSignDeptAmount();
				signDeptAmount.setInforId(saleContractId);
				signDeptAmount.setInforType(ContractSignReviewUtil.INFOR_TYPE_ORDER);
				signDeptAmount.setSignId(signId);
				signDeptAmount.setCurrencyId(e.getKey());
				signDeptAmount.setCurrency(e.getValue()[0].toString());
				signDeptAmount.setOriginalAmount(Double.parseDouble(e.getValue()[1].toString()));
				signDeptAmount.setRate(Double.parseDouble(e.getValue()[2].toString()));
				signDeptAmount.setRmbAmount(signDeptAmount.getOriginalAmount() * signDeptAmount.getRate());
				amountList.add(signDeptAmount);
				totalRmbAmount += signDeptAmount.getRmbAmount();
			}
			// 保存合同预算金额
			if (amountList.size() > 0) {
				this.contractSignDeptAmountDao.save(amountList);
				contractSignDeptInfor.setTotalRmbAmount(totalRmbAmount);
				this.contractSignDeptInforDao.update(contractSignDeptInfor);
			}

			// 生成合同货物/服务明细
			List<PurchaseDemandDetail> detailList = new ArrayList<PurchaseDemandDetail>();
			if (recordMap.size() > 0) {
				for (String recordId : recordMap.keySet()) {
					List<PurchaseDemandDetail> list = this.purchaseDemandService.getDetailList(recordId);
					// 合并所有货物/服务明细
					detailList.addAll(list);
				}
			}
			List<ContractSignDeptItem1> itemList = new ArrayList<ContractSignDeptItem1>();
			ContractSignDeptItem1 item;
			try {
				for (PurchaseDemandDetail demandDetail : detailList) {
					item = new ContractSignDeptItem1();
					BeanUtils.copyProperties(item, demandDetail);
					item.setInitialReviewType(initialReviewType);
					item.setSaleReviewId(signId);
					item.setSaleContractId(saleContractId);
					// 备注追加需求单号
					item.setRemarks(CommonUtil.trim(item.getRemarks()) + " (" + recordMap.get(item.getRecordId()) + ")");
					itemList.add(item);
				}
			} catch (Exception e) {
				System.err.println("createSaleContract: " + e.getMessage());
				return false;
			}
			// 保存货物明细
			if (itemList.size() > 0) {
				this.contractSignDeptItem1Dao.save(itemList);
			}
		}
		return true;
	}

	/**
	 * 生成采购合同
	 * 
	 * @param signId
	 * @param inforId
	 * @return
	 */
	public ContractSignDeptInfor createPurchaseContract(Map<String, Object> valueMap) {
		ContractSignDeptInfor contractSignDeptInfor = new ContractSignDeptInfor();
		contractSignDeptInfor.setInforId(UUIDHex.newId());
		contractSignDeptInfor.setInforType(ContractSignReviewUtil.INFOR_TYPE_CONTRACT);
		if (valueMap != null) {
			String signId = valueMap.containsKey("signId") ? (String) valueMap.get("signId") : "";
			contractSignDeptInfor.setSignId(signId);
			String singleCategoryCode = valueMap.containsKey("singleCategoryCode") ? (String) valueMap.get("singleCategoryCode") : "";
			// 采购合同且单一供方时直接找本项目下已生成采购合同中供方
			if (singleCategoryCode.equals(TpcConstant.SINGLE_SUPPLIER)) {
				setSupplier(contractSignDeptInfor, valueMap);
			}
		}
		return contractSignDeptInfor;
	}

	/**
	 * 设置供方
	 * 
	 * @param contractSignDeptInfor
	 * @param valueMap
	 */
	public void setSupplier(ContractSignDeptInfor contractSignDeptInfor, Map<String, Object> valueMap) {
		// 获取本项目下已生成采购合同中供方
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("singleCategoryCode", TpcConstant.SINGLE_SUPPLIER);
		params.put("projectId", valueMap.get("projectId").toString());
		params.put("inforType", ContractSignReviewUtil.INFOR_TYPE_CONTRACT);
		List<ContractSignDeptInfor> contractList = this.contractSignDeptInforDao.findList(params);
		// 设置供方
		if (contractList != null && contractList.size() > 0) {
			ContractSignDeptInfor infor = contractList.get(0);
			contractSignDeptInfor.setCustomerId(infor.getCustomerId());
			contractSignDeptInfor.setContractParty(infor.getContractParty());
		}
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * 
	 * @param inforId
	 * @param user
	 * @return
	 */
	public ContractSignDeptInfor initEditOrViewInforPage(String inforId, Map<String, Object> valueMap) {
		ContractSignDeptInfor contractSignDeptInfor;
		if (StringUtils.isBlank(inforId)) {// 采购合同需要新建
			contractSignDeptInfor = createPurchaseContract(valueMap);
			contractSignDeptInfor.setAdd(true);
		} else {
			contractSignDeptInfor = (ContractSignDeptInfor) this.contractSignDeptInforDao.get(inforId);
			contractSignDeptInfor.setAdd(false);
		}
		return contractSignDeptInfor;
	}

	/**
	 * 保存或修改对象
	 * 
	 * @param user
	 * @param contractSignDeptInfor
	 * @param valueMap
	 * @return
	 */
	public ContractSignDeptInfor saveOrUpdate(UserProfile user, ContractSignDeptInfor contractSignDeptInfor, Map<String, Object> valueMap) {
		if (contractSignDeptInfor.getAdd()) {
			this.contractSignDeptInforDao.save(contractSignDeptInfor);
		} else {
			this.contractSignDeptInforDao.update(contractSignDeptInfor);
		}
		// 保存更新金额
		if (valueMap != null && valueMap.containsKey("contractAmountListJson")) {
			saveOrUpdateContractAmount(contractSignDeptInfor, valueMap);
		}
		// 设置合同金额对象
		contractSignDeptInfor.setContractAmountList(this.getInforAmountList(contractSignDeptInfor.getInforId()));
		return contractSignDeptInfor;
	}

	/**
	 * 保存或更新合同金额
	 * 
	 * @param valueMap
	 */
	public void saveOrUpdateContractAmount(ContractSignDeptInfor contractSignDeptInfor, Map<String, Object> valueMap) {
		String inforId = contractSignDeptInfor.getInforId();
		String signId = contractSignDeptInfor.getSignId();
		int inforType = contractSignDeptInfor.getInforType();

		// 获取对象list集合JSON字符串
		String contractAmountListJson = valueMap.get("contractAmountListJson").toString();
		Set<String> ignoreProperties = TpcCommonUtil.getIgnoreProperties(ContractSignDeptAmount.class, contractAmountListJson);
		List<ContractSignDeptAmount> list = JsonUtil.toList(contractAmountListJson, ContractSignDeptAmount.class);
		ContractSignDeptAmount saveAmount;
		for (ContractSignDeptAmount amount : list) {
			// 获取数据库记录并替换前台可能修改的属性
			if (amount.getAmountId() == null || amount.getAmountId().contains("rowIdPrev")) {
				amount.setAmountId(null);
				saveAmount = new ContractSignDeptAmount(inforId, signId, inforType, amount.getCurrency(), amount.getCurrencyId());
			} else {
				saveAmount = this.contractSignDeptAmountDao.get(amount.getAmountId());
			}
			TpcCommonUtil.copyWithoutIgnoreProperties(amount, saveAmount, ignoreProperties);
			saveAmount.setInforType(inforType);
			this.contractSignDeptAmountDao.saveOrUpdate(saveAmount);
		}
		// 删除合同金额
		if (valueMap.containsKey("delAmountIds")) {
			String delAmountIds = valueMap.get("delAmountIds").toString();
			if (StringUtils.isNotBlank(delAmountIds)) {
				for (String amountId : delAmountIds.split(",")) {
					this.contractSignDeptAmountDao.deleteByProperty("amountId", amountId);
				}
			}
		}
	}

	/**
	 * 生成合同评审单号
	 * 
	 * @param reviewNo
	 * @return
	 */
	public synchronized String generatorContractSignDeptInforReviewNo(String reviewNo) {
		String inforReviewNo = null;
		// 过滤条件
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("reviewNo", reviewNo + "%");
		List<String> likeSearhNames = new ArrayList<String>();
		likeSearhNames.add("reviewNo");
		Integer count = this.contractSignDeptInforDao.find(params, likeSearhNames, null).size();
		String NoEnd = String.format("%03d", count + 1);
		inforReviewNo = reviewNo + "-" + NoEnd;
		return inforReviewNo;
	}

	/**
	 * 批量删除对象
	 * 
	 * @param user
	 * @param inforIds
	 */
	public void deleteContractSignDeptInfor(UserProfile user, String inforIds) {
		if (StringUtils.isNotBlank(inforIds)) {
			for (String inforId : inforIds.split(",")) {
				String[] sourceModule;
				ContractSignDeptInfor contract = this.contractSignDeptInforDao.get(inforId);
				if (contract.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
					// 修改选择单状态（未评审,可以再次生成销售合同）
					this.purchaseDemandSheetService.updateReviewStatusByRecordIds(contract.getRecordId(), PurchaseDemandSheet.NO_REVIEW);
					// 删除销售合同明细及对应采购合同明细
					this.contractSignDeptItem1Dao.deleteByProperty("saleContractId", inforId);
					this.contractSignDeptItem2Dao.deleteByProperty("saleContractId", inforId);
					sourceModule = new String[] { ContractSignReview.MODULE_ID, ContractSignReview.BUSI_TYPE_ORDER, contract.getInforId(), "" };
				} else {
					// 清除销售合同明细的采购合同属性，删除采购合同明细
					this.contractSignDeptItem1Dao.removePurchaseValue(inforId);
					this.contractSignDeptItem2Dao.deleteByProperty("purchaseContractId", inforId);
					sourceModule = new String[] { ContractSignReview.MODULE_ID, ContractSignReview.BUSI_TYPE_CONTRACT, contract.getInforId(), "" };
				}
				FilesUtil.deleteFiles(sourceModule);
				this.contractSignDeptAmountDao.deleteByProperty("inforId", inforId);
				// 删除效益测算表
				String noteId = CommonUtil.trim(contract.getNoteId());
				if (noteId.length() > 0) {
					this.benefitNoteService.delete(user, noteId);
				}
				this.contractSignDeptInforDao.delete(contract);
			}
		}
	}

	/**
	 * 保存更新明细集合
	 * 
	 * @param valueMap
	 */
	public void saveOrUpdateItem(Map<String, Object> valueMap) {
		if (valueMap == null || valueMap.isEmpty()) {
			return;
		}
		String itemType = CommonUtil.trim((String) valueMap.get("itemType"));// 明细级别
		if (itemType.equals("saleItem")) {// 销售合同明细
			saveSaleItem(valueMap);
		} else if (itemType.equals("purchaseItem")) {// 采购合同明细
			savePurchaseItem(valueMap);
		}
	}

	/**
	 * 保存销售合同明细
	 */
	public void saveSaleItem(Map<String, Object> valueMap) {
		// 获取销售合同明细对象list集合JSON字符串
		String itemListJson = (String) valueMap.get("itemListJson");
		String levelType = (String) valueMap.get("levelType");// 明细级别
		if (StringUtils.isBlank(itemListJson) || StringUtils.isBlank(levelType))
			return;

		// 获取币别码表
		Map<String, String> currencyMap = TpcConstant.getCommonCurrencyMap();
		// 根据JSON获取忽略保存属性
		Set<String> ignoreProperties = TpcCommonUtil.getIgnoreProperties(ContractSignDeptItem1.class, itemListJson);

		ContractSignDeptItem1 rebuildItem;
		List<ContractSignDeptItem1> list = JsonUtil.toList(itemListJson, ContractSignDeptItem1.class);

		if (levelType.equals("twoLevel")) {// 二级明细来源于新增
			String parentItemId = (String) valueMap.get("parentItemId");
			ContractSignDeptItem1 parentItem = this.contractSignDeptItem1Dao.get(parentItemId);
			if (parentItem == null)
				return;
			double amount = 0, rmbAmount = 0;
			for (ContractSignDeptItem1 item : list) {
				// 获取数据库记录并替换前台可能修改的属性
				if (CommonUtil.trim(item.getItemId()).contains("rowId_")) {
					item.setItemId(null);
					rebuildItem = new ContractSignDeptItem1(parentItem.getInitialReviewType(), parentItemId, parentItem.getItemName(), parentItem.getSaleReviewId(),
							parentItem.getSaleContractId());
				} else {
					rebuildItem = this.contractSignDeptItem1Dao.get(item.getItemId());
				}
				TpcCommonUtil.copyWithoutIgnoreProperties(item, rebuildItem, ignoreProperties);
				rebuildItem.setCurrency(parentItem.getCurrency());
				rebuildItem.setCurrencyId(parentItem.getCurrencyId());
				this.contractSignDeptItem1Dao.saveOrUpdate(rebuildItem);
				amount += rebuildItem.getAmount();
				rmbAmount += rebuildItem.getRmbAmount();
			}
			// 设置一级叶节点属性值为false且清除关联采购合同属性值
			if (parentItem.isLeaf()) {
				parentItem.setLeaf(false);
				parentItem.setPurchaseReviewId(null);
				parentItem.setPurchaseContractId(null);
				// 根据ID删除对应的采购合同明细记录
				this.contractSignDeptItem2Dao.deleteByProperty("itemId", parentItemId);
			}
			// 一级明细金额为二级之和
			parentItem.setAmount(amount);
			parentItem.setRmbAmount(rmbAmount);
			this.contractSignDeptItem1Dao.update(parentItem);
			// 修改销售合同金额
			updateContractSignDeptAmountByItem(parentItem.getSaleContractId(), null);
		} else {// 一级明细来源于客户需求单，这里是修改
			String inforId = null;
			List<ContractSignDeptItem1> saleItemList = new ArrayList<ContractSignDeptItem1>();
			for (ContractSignDeptItem1 item : list) {
				// 获取数据库记录并替换前台可能修改的属性
				rebuildItem = this.contractSignDeptItem1Dao.get(item.getItemId());
				TpcCommonUtil.copyWithoutIgnoreProperties(item, rebuildItem, ignoreProperties);
				rebuildItem.setCurrency(currencyMap.get(item.getCurrencyId()));
				this.contractSignDeptItem1Dao.update(rebuildItem);
				inforId = rebuildItem.getSaleContractId();
				saleItemList.add(rebuildItem);
			}
			// 修改销售合同金额
//			updateContractSignDeptAmountByItem(inforId, saleItemList);
			//2019年12月13日  saleItemList中未包含当前页面显示的以外的其他明细，传入null使计算所有一级明细总金额
			updateContractSignDeptAmountByItem(inforId, null);
		}
	}

	/**
	 * 保存采购合同明细
	 */
	public void savePurchaseItem(Map<String, Object> valueMap) {
		// 获取销售合同明细对象list集合JSON字符串
		String itemListJson = (String) valueMap.get("itemListJson");
		String itemIds = (String) valueMap.get("itemIds");

		List<ContractSignDeptItem1> saleItemList = new ArrayList<ContractSignDeptItem1>();
		List<ContractSignDeptItem2> purchaseItemList = new ArrayList<ContractSignDeptItem2>();

		if (StringUtils.isNotBlank(itemListJson)) {// 修改采购明细
			// 获取币别码表
			Map<String, String> currencyMap = TpcConstant.getCommonCurrencyMap();
			// 根据JSON获取忽略保存属性
			Set<String> ignoreProperties = TpcCommonUtil.getIgnoreProperties(ContractSignDeptItem1.class, itemListJson);
			ContractSignDeptItem2 rebuildItem;
			List<ContractSignDeptItem2> list = JsonUtil.toList(itemListJson, ContractSignDeptItem2.class);
			String inforId = null;
			for (ContractSignDeptItem2 item : list) {
				// 获取数据库记录并替换前台可能修改的属性
				rebuildItem = this.contractSignDeptItem2Dao.get(item.getItemId());
				TpcCommonUtil.copyWithoutIgnoreProperties(item, rebuildItem, ignoreProperties);
				rebuildItem.setCurrency(currencyMap.get(item.getCurrencyId()));
				this.contractSignDeptItem2Dao.update(rebuildItem);
				inforId = rebuildItem.getPurchaseContractId();
				purchaseItemList.add(rebuildItem);
			}
			// 修改采购合同金额
			updateContractSignDeptAmountByItem(inforId, purchaseItemList);
		} else if (StringUtils.isNotBlank(itemIds)) {// 添加采购明细
			String signId = valueMap.get("signId").toString();
			String purchaseContractId = valueMap.get("purchaseContractId").toString();
			ContractSignDeptItem1 saleItem;
			ContractSignDeptItem2 purchaseItem;
			if (StringUtils.isNotBlank(itemIds)) {
				for (String itemId : itemIds.split(",")) {
					// 给销售合同明细添加采购合同属性信息
					saleItem = this.contractSignDeptItem1Dao.get(itemId);
					saleItem.setPurchaseReviewId(signId);
					saleItem.setPurchaseContractId(purchaseContractId);
					saleItemList.add(saleItem);

					// 生成采购明细
					purchaseItem = new ContractSignDeptItem2();
					TpcCommonUtil.copyProperties(saleItem, purchaseItem);
					// 清除币别金额信息（业务人员输入）
					purchaseItem.setCurrency(null);
					purchaseItem.setCurrencyId(null);
					purchaseItem.setAmount(0);
					purchaseItem.setRmbAmount(0);
					purchaseItem.setTaxRebateRate(0);
					purchaseItemList.add(purchaseItem);
				}
			}
			
			if (saleItemList.size() > 0) {
				this.contractSignDeptItem1Dao.update(saleItemList);
				this.contractSignDeptItem2Dao.save(purchaseItemList);
				// 修改合同金额
				updateContractSignDeptAmountByItem(purchaseContractId, null);
			}
		}

	}

	/**
	 * 合同添加/删除明细后更新合同金额
	 * 
	 * @param inforId
	 */
	public void updateContractSignDeptAmountByItem(String inforId, List<?> itemList) {
		Map<String, String> currencyMap = TpcConstant.getCommonCurrencyMap();
		Map<String, String> currencyRateMap = TpcConstant.getCurrencyRateMap();
		ContractSignDeptInfor infor = this.getContractSignDeptInfor(inforId);
		String signId = infor.getSignId();
		int inforType = infor.getInforType();
		if (itemList == null) {
			if (inforType == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("saleContractId", inforId);
				parameters.put("parentItemId", null);
				itemList = this.contractSignDeptItem1Dao.findList(parameters);
			} else {
				itemList = this.findPurchaseInforItemBy("purchaseContractId", inforId);
			}
		}
		if (itemList != null && itemList.size() > 0) {
			Map<String, Double[]> itemAmoutMap = this.getInforItemAmountMap(infor, itemList);
			// 合同原金额项根据货物/服务赋值
			if (itemAmoutMap.size() > 0) {
				// 获取合同已有金额项
				Map<String, ContractSignDeptAmount> amountMap = this.getInforAmountMap(inforId);
				// 设置合同金额
				ContractSignDeptAmount amount = null;
				double totalRmbAmount = 0;
				String currencyId;
				// 遍历明细金额赋值给合同对应币别金额
				for (Map.Entry<String, Double[]> e : itemAmoutMap.entrySet()) {
					currencyId = e.getKey();
					amount = amountMap.get(currencyId);
					if (amount == null) {// 不存在该币别时添加
						amount = new ContractSignDeptAmount(inforId, signId, inforType, currencyMap.get(currencyId), currencyId);
						amount.setRate(Double.parseDouble(currencyRateMap.get(currencyId)));
					}
					amount.setOriginalAmount(itemAmoutMap.get(currencyId)[0]);
					amount.setRmbAmount(itemAmoutMap.get(currencyId)[1]);
					this.contractSignDeptAmountDao.saveOrUpdate(amount);
					amount.getAmountId();
					totalRmbAmount += amount.getRmbAmount();
				}
				infor.setTotalRmbAmount(totalRmbAmount);
				this.contractSignDeptInforDao.update(infor);
			}
		}
	}

	/**
	 * 批量删除货物服务对象
	 * 
	 * @param user
	 * @param inforIds
	 */
	public void deleteContractSignDeptItem(UserProfile user, String itemIds, int deleteType) {
		if (StringUtils.isNotBlank(itemIds)) {
			for (String itemId : itemIds.split(",")) {
				String inforId;
				ContractSignDeptItem1 item = this.contractSignDeptItem1Dao.get(itemId);
				if (deleteType == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
					inforId = item.getSaleContractId();
					if (!item.isLeaf()) {// 删除的是一级且存在二级明细
						// 存在二级明细则删除
						this.contractSignDeptItem1Dao.deleteByProperty("parentItemId", item.getItemId());
						this.contractSignDeptItem2Dao.deleteByProperty("parentItemId", item.getItemId());
					} else if (CommonUtil.trim(item.getParentItemId()).length() > 0) {
						ContractSignDeptItem1 parentItem = this.contractSignDeptItem1Dao.get(item.getParentItemId());
						// 删除的是二级明细判断一级明细下是否还有其他二级(仅此1条)，无则修改属性
						int count = this.contractSignDeptItem1Dao.findBy("parentItemId", item.getParentItemId()).size();
						if (count == 1) {
							parentItem.setLeaf(true);
						}
						parentItem.setAmount(parentItem.getAmount() - item.getAmount());
						parentItem.setRmbAmount(parentItem.getRmbAmount() - item.getRmbAmount());
						this.contractSignDeptItem1Dao.update(parentItem);
					}
					// 销售合同明细直接删除
					this.contractSignDeptItem1Dao.delete(item);
				} else {
					// 销售合同明细重置采购合同ID为空
					inforId = item.getPurchaseContractId();
					item.setPurchaseReviewId(null);
					item.setPurchaseContractId(null);
					this.contractSignDeptItem1Dao.update(item);
				}
				// 采购合同明细删除
				this.contractSignDeptItem2Dao.deleteByProperty("itemId", itemId);

				// 更新合同金额
				updateContractSignDeptAmountByItem(inforId, null);
			}
		}
	}

	/**
	 * 合同明细获取可选用币别 评审采购合同添加采购项币别汇率： 1.销售+采购合同一起评审时，采购合同不再填写币别汇率，合同币别、金额从采购明细汇总，
	 * 采购项币别从对应的销售合同效益测算表币别中获取且汇率不可修改（折人民币按该币别汇率计算）；
	 * 
	 * 2.采购合同评审，添加采购项币别可以任意选择，采购合同采购项添加执行汇率（页面不显示，后台设置）、实时汇率（可以修改），
	 * 选择币别后从效益测算表中获取该币别汇率置入两个汇率中，若效益测算表中不存在，手动输入实时汇率，扣除预算按执行汇率，执行汇率不存在按实时汇率扣除；
	 * 
	 * @param filterParams
	 * @return
	 */
	public Map<String, String> getCanUseCurrencyMap(Map<String, Object> filterParams) {
		Map<String, String> currencyMap = new LinkedHashMap<String, String>();
		if (filterParams != null) {
			int initialReviewType = 0;
			if (filterParams.containsKey("saleContractId")) {// 销售合同获取过滤币别选项
				String saleContractId = (String) filterParams.get("saleContractId");
				List<ContractSignDeptAmount> amountList = this.contractSignDeptAmountDao.queryBy("inforId", saleContractId, false, "amountId", true);
				for (ContractSignDeptAmount amount : amountList) {
					currencyMap.put(amount.getCurrencyId(), amount.getCurrency());
				}
			} else if (filterParams.containsKey("initialReviewType")) {// 非销售合同判断评审类型
				initialReviewType = Integer.parseInt(filterParams.get("initialReviewType").toString());
				if (initialReviewType == ContractSignReviewUtil.REVIEW_TYPE_SUBCONTRACT) {
					// 销售+采购一块评审时取该采购项对应销售合同效益测算表币别
					String itemSaleContractId = filterParams.get("itemSaleContractId").toString();
					List<BenefitNoteCurrency> currencyList = this.benefitNoteService.getCurrencyListByInforId(itemSaleContractId);
					if (currencyList != null) {
						for (BenefitNoteCurrency currency : currencyList) {
							currencyMap.put(currency.getCurrencyId(), currency.getCurrency());
						}
					}
				} else if (initialReviewType == ContractSignReviewUtil.REVIEW_TYPE_CONTRACT) {
					// 采购合同评审取采购合同中的币别
					String purchaseContractId = filterParams.get("purchaseContractId").toString();
					List<ContractSignDeptAmount> amountList = this.contractSignDeptAmountDao.queryBy("inforId", purchaseContractId, false, "amountId", true);
					for (ContractSignDeptAmount amount : amountList) {
						currencyMap.put(amount.getCurrencyId(), amount.getCurrency());
					}
				}
			}
		}
		return currencyMap;
	}

	/**
	 * 选择评审单号select下拉列表
	 * 
	 * @param projectId
	 * @param type
	 * @return
	 * @throws ParseException
	 */
	public Map<String, String> getContractSignDeptInforMap(Map<String, Object> parameters, UserProfile userProfile) throws ParseException {
		String authFilter = getAuthFilter(userProfile);
		Map<String, String> inforMap = new LinkedHashMap<String, String>();
		List<ContractSignDeptInfor> inforList = this.contractSignDeptInforDao.getList(parameters, authFilter);
		for (ContractSignDeptInfor infor : inforList) {
			inforMap.put(infor.getInforId(), infor.getReviewNo());
		}
		return inforMap;
	}

	/**
	 * 校验销售合同名称
	 * 
	 * @param inforId
	 * @param contractName
	 * @return
	 */
	public boolean checkContractNameIsValid(String inforId, String contractName) {
		List<ContractSignDeptInfor> list = this.contractSignDeptInforDao.queryBy("contractName", contractName, false, "inforId", true);
		// 已存在该合同名称且非本记录
		if (list.size() > 1 || (list.size() == 1 && !list.get(0).getInforId().equals(inforId))) {
			return false;
		}
		return true;
	}

	/** 以下是选择签约评审单方法 **/

	// 主表(评审单)
	public ListPage<ContractSignDeptReview> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ContractSignDeptReview> listPage = new ListPage<ContractSignDeptReview>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.contractSignDeptReviewDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

	// 从表(销售合同/采购合同信息)
	public ListPage<ContractSignDeptInfor> getInforListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ContractSignDeptInfor> listPage = new ListPage<ContractSignDeptInfor>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.contractSignDeptInforDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

	// 主表（评审单）加从表（销售合同/采购合同信息）
	public ListPage<ContractDeptReviewRecord> getRecordListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ContractDeptReviewRecord> listPage = new ListPage<ContractDeptReviewRecord>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);

		List<ContractDeptReviewRecord> refinedList = new ArrayList<ContractDeptReviewRecord>();

		ListPage<ContractSignDeptReview> reviews = new ListPage<ContractSignDeptReview>();
		reviews.setPageNo(page);
		reviews.setPageSize(pageSize);
		reviews.setAutoCount(true);
		reviews = this.contractSignDeptReviewDao.getListPage(reviews, parameters, authFilter);
		List<ContractSignDeptReview> reviewList = reviews.getRows();
		// 是否展开（默认折叠）
		boolean expand = Boolean.valueOf(String.valueOf(parameters.get("expand")));
		ContractDeptReviewRecord record;
		for (ContractSignDeptReview review : reviewList) {
			// list中记录等于需求记录时不再添加
			if (refinedList.size() == pageSize) {
				break;
			}
			record = new ContractDeptReviewRecord();
			record.setInforId(review.getSignId());
			record.setSignId(review.getSignId());
			record.setReviewNo(review.getReviewNo());
			record.setLevel(0);
			record.setLeaf(false);
			record.setExpanded(expand);
			refinedList.add(record);
			// 获取销售合同/采购合同明细
			parameters.put("signId", review.getSignId());
			ListPage<ContractSignDeptInfor> infors = new ListPage<ContractSignDeptInfor>();
			infors.setPageNo(page);
			infors.setPageSize(pageSize - refinedList.size());// 可添加从表记录只能为总可添加记录减去已添加记录
			infors.setAutoCount(true);
			infors = this.contractSignDeptInforDao.getListPage(infors, parameters, authFilter);
			List<ContractSignDeptInfor> inforList = infors.getRows();
			if (CollectionUtils.isNotEmpty(inforList)) {
				for (ContractSignDeptInfor infor : inforList) {
					record = new ContractDeptReviewRecord();
					BeanUtils.copyProperties(record, infor);
					record.setParent(review.getSignId());
					record.setMontage(review.getReviewNo() + "-" + infor.getContractParty());
					refinedList.add(record);
				}
			} else {
				// 明细为空时清除该评审单
				refinedList.remove(record);
			}
			listPage.setRowCount(listPage.getRowCount() + inforList.size() + 1);
		}
		if (listPage.getRowCount() > 0) {
			listPage.setRowCount(listPage.getRowCount() + 1);
		}
		listPage.setRows(refinedList);
		return listPage;
	}

	/**
	 * 选择货物/服务明细（非树状结构）
	 * 
	 * @param page
	 * @param pageSize
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public ListPage<ContractSignDeptItem1> getItemListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ContractSignDeptItem1> listPage = new ListPage<ContractSignDeptItem1>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.contractSignDeptItem1Dao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

	/**
	 * 选择货物/服务明细（树状结构）
	 * 
	 * @param page
	 * @param pageSize
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public ListPage<ContractSignDeptItem1> getItemTreeListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ContractSignDeptItem1> listPage = new ListPage<ContractSignDeptItem1>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.contractSignDeptItem1Dao.getListPage(listPage, parameters, authFilter);
		List<ContractSignDeptItem1> refinedList = reBuildTreeList(listPage.getRows());
		listPage.setRows(refinedList);
		listPage.setRowCount(refinedList.size());
		return listPage;
	}

	/**
	 * 处理数据
	 * 
	 * @param itemList
	 * @return
	 */
	public List<ContractSignDeptItem1> reBuildTreeList(List<ContractSignDeptItem1> itemList) {
		if (CollectionUtils.isNotEmpty(itemList)) {
			List<ContractSignDeptItem1> refinedList = new ArrayList<ContractSignDeptItem1>();
			for (ContractSignDeptItem1 item : itemList) {
				item.setParent(String.valueOf(item.getParent()));
				refinedList.add(item);
			}
			return refinedList;
		}
		return itemList;
	}

}
