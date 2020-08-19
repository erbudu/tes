package com.supporter.prj.cneec.tpc.prj_contract_table.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeCollectionTerms;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeContractAmount;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChangeGoods;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractOrder;
import com.supporter.prj.cneec.tpc.contract_change.service.ContractChangeService;
import com.supporter.prj.cneec.tpc.contract_online.entity.ContractCollectionTerms;
import com.supporter.prj.cneec.tpc.contract_online.entity.ContractContractAmount;
import com.supporter.prj.cneec.tpc.contract_online.entity.ContractGoods;
import com.supporter.prj.cneec.tpc.contract_online.entity.ContractOnline;
import com.supporter.prj.cneec.tpc.contract_online.service.ContractOnlineService;
import com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.service.ContractStampTaxAmountService;
import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractCollectionTerms;
import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractContractAmount;
import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractGoods;
import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractOnline;
import com.supporter.prj.cneec.tpc.order_change.entity.ContractChOrder;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeCollectionTerms;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeContractAmount;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeGoods;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderCollectionTerms;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderContractAmount;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderGoods;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderOnline;
import com.supporter.prj.cneec.tpc.order_online.service.OrderOnlineService;
import com.supporter.prj.cneec.tpc.prj_contract_modify.dao.PrjContractAmountBMDao;
import com.supporter.prj.cneec.tpc.prj_contract_modify.dao.PrjContractCollectionTermsBMDao;
import com.supporter.prj.cneec.tpc.prj_contract_modify.dao.PrjContractGoodsBMDao;
import com.supporter.prj.cneec.tpc.prj_contract_modify.dao.PrjContractTableBMDao;
import com.supporter.prj.cneec.tpc.prj_contract_modify.dao.SettlementPlanBMDao;
import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.PrjContractAmountBM;
import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.PrjContractCollectionTermsBM;
import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.PrjContractGoodsBM;
import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.PrjContractTableBM;
import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.SettlementPlanBM;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractAmountDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractCollectionTermsDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractGoodsDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.PrjContractTableDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.SettlementPlanBasicDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.dao.SettlementPlanDao;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractAmount;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractCollectionTerms;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractGoods;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.SettlementPlan;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.SettlementPlanBasic;
import com.supporter.prj.cneec.tpc.prj_contract_table.util.PrjContractTableConstant;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.FilesUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

/**
 * @Title: PrjContractTableService
 * @Description: 业务操作类
 */
@Service
@Transactional(TransManager.APP)
public class PrjContractTableService {
	@Autowired
	private PrjContractTableDao prjContractTableDao;
	@Autowired
	private PrjContractAmountDao prjContractAmountDao;
	@Autowired
	private PrjContractGoodsDao prjContractGoodsDao;
	@Autowired
	private PrjContractCollectionTermsDao prjContractCollectionTermsDao;
	@Autowired
	private SettlementPlanBasicDao settlementPlanBasicDao;
	@Autowired
	private SettlementPlanDao settlementPlanDao;
	@Autowired
	private OrderOnlineService orderOnlineService;
	@Autowired
	private ContractOnlineService contractOnlineService;
	@Autowired
	private RegisterProjectService registerProjectService;
	@Autowired
	private ContractChangeService contractChangeService;
	@Autowired
	private PrjContractTableBMDao prjContractTableBMDao;
	@Autowired
	private PrjContractAmountBMDao prjContractAmountBMDao;
	@Autowired
	private PrjContractGoodsBMDao prjContractGoodsBMDao;
	@Autowired
	private PrjContractCollectionTermsBMDao prjContractCollectionTermsBMDao;
	@Autowired
	private SettlementPlanBMDao settlementPlanBMDao;
	@Autowired
	private ContractStampTaxAmountService taxAmountService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, PrjContractTable.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param prjContractTable
	 */
	public void getAuthCanExecute(UserProfile userProfile, PrjContractTable prjContractTable) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, PrjContractTable.MODULE_ID, prjContractTable.getContractId(), prjContractTable);
	}

	/**
	 * 获取合同信息表对象集合
	 * @param user
	 * @param jqGrid
	 * @param prjContractTable
	 * @return
	 */
	public List<PrjContractTable> getGrid(UserProfile user, JqGrid jqGrid, PrjContractTable prjContractTable) {
		String authFilter = getAuthFilter(user);
		return this.prjContractTableDao.findPage(jqGrid, prjContractTable, authFilter);
	}
	
	public List<PrjContractTable> getSaleGrid(UserProfile user, JqGrid jqGrid, PrjContractTable prjContractTable, String projectId, String contractTypeCode) {
		String authFilter = getAuthFilter(user);
		return this.prjContractTableDao.findSalePage(jqGrid, prjContractTable, authFilter, projectId, contractTypeCode);
	}
	
	public List<PrjContractTable> getDerivateGrid(UserProfile user, JqGrid jqGrid, PrjContractTable prjContractTable,
			String projectId, String contractTypeCode) {
		String authFilter = getAuthFilter(user);
		return this.prjContractTableDao.findDerivatePage(jqGrid, prjContractTable, authFilter, projectId,
				contractTypeCode);
	}

	public List<PrjContractTable> getContractGrid(UserProfile user, JqGrid jqGrid, PrjContractTable prjContractTable,
			String projectId, String contractTypeCode) {
		String authFilter = getAuthFilter(user);
		return this.prjContractTableDao.findContractPage(jqGrid, prjContractTable, authFilter, projectId,
				contractTypeCode);
	}

	/**
	 * 获取合同额集合
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	public List<PrjContractAmount> getContractAmountGrid(UserProfile user, JqGrid jqGrid, String contractId) {
		return this.prjContractAmountDao.findPage(jqGrid, contractId);
	}

	/**
	 * 获取货物及服务明细集合
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	public List<PrjContractGoods> getGoodsGrid(UserProfile user, JqGrid jqGrid, String contractId) {
		return this.prjContractGoodsDao.findPage(jqGrid, contractId);
	}

	/**
	 * 获取收款条件集合
	 * @param user
	 * @param jqGrid
	 * @param contractId
	 * @return
	 */
	public List<PrjContractCollectionTerms> getCollectionTermsGrid(UserProfile user, JqGrid jqGrid, String contractId) {
		return this.prjContractCollectionTermsDao.findPage(jqGrid, contractId);
	}

	/**
	 * 获取单个合同信息表对象
	 * @param contractId
	 * @return
	 */
	public PrjContractTable get(String contractId) {
		PrjContractTable table = this.prjContractTableDao.get(contractId);
		if (table != null) {
			List<PrjContractAmount> contractAmountList = this.prjContractAmountDao.findBy("contractId", table.getContractId());
			List<PrjContractGoods> contractGoodsList = this.prjContractGoodsDao.findBy("contractId", table.getContractId());
			List<PrjContractCollectionTerms> collectionTermsList = this.prjContractCollectionTermsDao.findBy("contractId", table.getContractId());
			table.setContractAmountList(contractAmountList);
			table.setContractGoodsList(contractGoodsList);
			table.setCollectionTermsList(collectionTermsList);
		}
		return table;
	}

	/**
	 * 根据合同ID获取单个合同信息表对象
	 * @param contractId
	 * @return
	 */
	public PrjContractTable getPrjContractTableByContractId(String contractId) {
		PrjContractTable table = this.prjContractTableDao.findUniqueResult("contractId", contractId);
		if (table != null) {
			List<PrjContractAmount> contractAmountList = this.prjContractAmountDao.findBy("contractId", table.getContractId());
			List<PrjContractGoods> contractGoodsList = this.prjContractGoodsDao.findBy("contractId", table.getContractId());
			List<PrjContractCollectionTerms> collectionTermsList = this.prjContractCollectionTermsDao.findBy("contractId", table.getContractId());
			table.setContractAmountList(contractAmountList);
			table.setContractGoodsList(contractGoodsList);
			table.setCollectionTermsList(collectionTermsList);
		}
		return table;
	}

	/**
	 * 获取合同List
	 * @param projectId
	 * @param contractType
	 * @return
	 * @throws ParseException 
	 */
	public List<PrjContractTable> getPrjContractList(Map<String, Object> parameters, UserProfile userProfile) throws ParseException {
		String authFilter = getAuthFilter(userProfile);
		List<PrjContractTable> list = this.prjContractTableDao.getList(parameters, authFilter);
		return list;
	}

	/**
	 * 获取合同Map
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws ParseException
	 */
	public Map<String, String> getPrjContractMap(Map<String, Object> parameters, UserProfile userProfile) throws ParseException {
		Map<String, String> contractMap = new LinkedHashMap<String, String>();
		List<PrjContractTable> list = this.getPrjContractList(parameters, userProfile);
		for (PrjContractTable contract : list) {
			contractMap.put(contract.getContractId(), "（" + contract.getContractNo() + "）" + contract.getContractName());
		}
		return contractMap;
	}

	/**
	 * 获取合同金额
	 * @param amountId
	 * @return
	 */
	public PrjContractAmount getPrjContractAmount(String amountId) {
		return this.prjContractAmountDao.get(amountId);
	}

	/**
	 * 获取合同金额LIST
	 * @param contractId
	 * @return
	 */
	public List<PrjContractAmount> getPrjContractAmountList(String contractId) {
		return this.prjContractAmountDao.queryBy("contractId", contractId, null, "currencyId", true);
	}

	/**
	 * 获取合同条款MAP
	 * 
	 * @param contractId
	 * @return
	 */
	public Map<String, Double> getPrjContractAmountMap(String contractId) {
		Map<String, Double> map = new LinkedHashMap<String, Double>();
		List<PrjContractAmount> list = getPrjContractAmountList(contractId);
		for (PrjContractAmount amount : list) {
			map.put(amount.getCurrencyId(), amount.getRate());
		}
		return map;
	}
	/**
	 * 获取合同条款
	 * 
	 * @param termsId
	 * @return
	 */
	public PrjContractCollectionTerms getPrjContractCollectionTerms(String termsId) {
		return this.prjContractCollectionTermsDao.get(termsId);
	}

	/**
	 * 获取合同条款LIST
	 * 
	 * @param contractId
	 * @return
	 */
	public List<PrjContractCollectionTerms> getPrjContractCollectionTermsList(String contractId) {
		return this.prjContractCollectionTermsDao.queryBy("contractId", contractId, null, "receiveDate", true);
	}

	/**
	 * 获取合同条款MAP
	 * 
	 * @param contractId
	 * @return
	 */
	public Map<String, String> getPrjContractCollectionTermsMap(String contractId) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		int year = CommonUtil.parseInt(CommonUtil.format(new Date(), "yyyy"));
		int month = CommonUtil.parseInt(CommonUtil.format(new Date(), "MM"));
		List<PrjContractCollectionTerms> list = this.prjContractCollectionTermsDao.getPrjContractPaymentTermsList(contractId, year, month);
		for (PrjContractCollectionTerms terms : list) {
			if (terms.canReceive()) {
				map.put(terms.getTermsId(), terms.getReceivables());
			}
		}
		return map;
	}

	/**
	 * 给收/付款条款增加在途金额
	 * @param termsId
	 * @param amount
	 */
	public void addOnwayAmountForTerms(String termsId, double amount) {
		PrjContractCollectionTerms terms = this.prjContractCollectionTermsDao.get(termsId);
		if (terms != null) {
			terms.setOnwayAmount(BigDecimal.valueOf(terms.getOnwayAmount()).add(BigDecimal.valueOf(amount)).doubleValue());
			this.prjContractCollectionTermsDao.update(terms);
		}
	}

	/**
	 * 给收/付款条款移除在途金额
	 * @param termsId
	 * @param amount
	 */
	public void removeOnwayAmountForTerms(String termsId, double amount) {
		PrjContractCollectionTerms terms = this.prjContractCollectionTermsDao.get(termsId);
		if (terms != null) {
			terms.setOnwayAmount(BigDecimal.valueOf(terms.getOnwayAmount()).subtract(BigDecimal.valueOf(amount)).doubleValue());
			this.prjContractCollectionTermsDao.update(terms);
		}
	}

	/**
	 * 给收/付款条款将在途转为实际收/付款金额（收付款时）
	 * @param termsId
	 * @param amount
	 */
	public void transferToRealReceiveAmountForTerms(String termsId, double amount) {
		PrjContractCollectionTerms terms = this.prjContractCollectionTermsDao.get(termsId);
		if (terms != null) {
			terms.setOnwayAmount(BigDecimal.valueOf(terms.getOnwayAmount()).subtract(BigDecimal.valueOf(amount)).doubleValue());
			terms.setRealReceiveAmount(BigDecimal.valueOf(terms.getRealReceiveAmount()).add(BigDecimal.valueOf(amount)).doubleValue());
			this.prjContractCollectionTermsDao.update(terms);
		}
	}

	/**
	 * 给收/付款条款将在途转为实际收/付款金额（退款时）
	 * @param termsId
	 * @param amount
	 */
	public void transferToRealReceiveAmountForTermsByRefund(String termsId, double amount) {
		PrjContractCollectionTerms terms = this.prjContractCollectionTermsDao.get(termsId);
		if (terms != null) {
			terms.setRealReceiveAmount(BigDecimal.valueOf(terms.getRealReceiveAmount()).subtract(BigDecimal.valueOf(amount)).doubleValue());
			this.prjContractCollectionTermsDao.update(terms);
		}
	}

	/**
	 * 获取合同列表
	 * @param page
	 * @param pageSize
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public ListPage<PrjContractTable> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<PrjContractTable> listPage = new ListPage<PrjContractTable>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.prjContractTableDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

	/**
	 * 根据销售合同创建合同表
	 * @param orderOnline
	 * @return
	 */
	public PrjContractTable createTpcPrjContractTableByOrderOnline(OrderOnline orderOnline) {
		String contractId = orderOnline.getOrderId();// 合同上线表ID与正式表ID一样
		if (this.get(contractId) != null) {
			System.out.println("createTpcPrjContractTableByOrderOnline error: PrjContractTable is exist");
			return null;
		}
		PrjContractTable table = new PrjContractTable();
		try {
			// 复制销售合同上线信息至合同正式表
			BeanUtils.copyProperties(table, orderOnline);
			// 设置独有属性
			table.setContractId(contractId);
			RegisterProject project = registerProjectService.get(orderOnline.getProjectId());
			table.setProjectNo(project.getProjectNo());
			table.setContractNo(orderOnline.getOrderNo());
			table.setContractName(orderOnline.getOrderName());
			table.setContractTypeCode(PrjContractTableConstant.SALE);
			table.setContractType(PrjContractTableConstant.getContractTypeMap().get(PrjContractTableConstant.SALE));
			table.setContractStatusCode(PrjContractTableConstant.EFFECT);
			table.setContractStatus(PrjContractTableConstant.getContractStatusMap().get(PrjContractTableConstant.EFFECT));
			table.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.prjContractTableDao.save(table);
			
			//创建印花税
			taxAmountService.createOrderStampTaxAmount(orderOnline, table);

			// 拷贝附件
			String[] sourceModule = new String[] { OrderOnline.MODULE_ID, OrderOnline.BUSI_TYPE, contractId, "" };
			String[] targetModule = new String[] { PrjContractTable.MODULE_ID, PrjContractTable.BUSI_TYPE, contractId, "" };
			FilesUtil.copySourceFilesToTargetModule(sourceModule, targetModule);

			// 复制采购合同上线合同金额至合同正式表的合同金额表
			List<PrjContractAmount> amountList = new ArrayList<PrjContractAmount>();
			List<OrderContractAmount> contractAmountList = orderOnline.getContractAmountList();
			if (CollectionUtils.isNotEmpty(contractAmountList) && contractAmountList.size() > 0) {
				PrjContractAmount amount;
				for (OrderContractAmount contractAmount : contractAmountList) {
					amount = new PrjContractAmount();
					BeanUtils.copyProperties(amount, contractAmount);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					amount.setContractId(contractId);
					amountList.add(amount);
				}
				// 批量保存
				if (amountList.size() > 0) {
					this.prjContractAmountDao.save(amountList);
				}
			}

			// 复制采购合同上线货品及服务信息至合同正式表的货品及服务信息表
			List<PrjContractGoods> goodsList = new ArrayList<PrjContractGoods>();
			List<OrderGoods> contractGoodsList = orderOnline.getGoodsList();
			if (CollectionUtils.isNotEmpty(contractGoodsList) && contractGoodsList.size() > 0) {
				PrjContractGoods goods;
				for (OrderGoods contractGoods : contractGoodsList) {
					goods = new PrjContractGoods();
					BeanUtils.copyProperties(goods, contractGoods);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					goods.setContractId(contractId);
					goodsList.add(goods);
				}
				// 批量保存
				if (goodsList.size() > 0) {
					this.prjContractGoodsDao.save(goodsList);
				}
			}

			// 复制采购合同上线收款条款至合同正式表的收款条款表
			List<PrjContractCollectionTerms> termsList = new ArrayList<PrjContractCollectionTerms>();
			List<OrderCollectionTerms> collectionTermsList = orderOnline.getCollectionTermsList();
			if (CollectionUtils.isNotEmpty(collectionTermsList) && collectionTermsList.size() > 0) {
				PrjContractCollectionTerms terms;
				for (OrderCollectionTerms collectionTerms : collectionTermsList) {
					terms = new PrjContractCollectionTerms();
					BeanUtils.copyProperties(terms, collectionTerms);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					terms.setContractId(contractId);
					termsList.add(terms);
				}
				// 批量保存
				if (termsList.size() > 0) {
					this.prjContractCollectionTermsDao.save(termsList);
				}
			}

			// 创建合同基础付款计划和合同付款计划
			int beginYear = CommonUtil.parseInt(CommonUtil.format(orderOnline.getEffectiveDate(), "yyyy"));
			int endYear = CommonUtil.parseInt(CommonUtil.format(orderOnline.getExpirationDate(), "yyyy"));
			Map<String, Double> planActAmounts = orderOnlineService.getPayPlanAmounts(contractId);
			for (int i = beginYear; i <= endYear; i++) {
				for (int j = 1; j <= 12; j++) {
					String date = i + "-" + j;
					if (j < 10) {
						date = i + "-0" + j;
					}
					// 合同基础付款计划
					SettlementPlanBasic settlementPlanBasic = new SettlementPlanBasic();
					settlementPlanBasic.setContractId(contractId);
					settlementPlanBasic.setSettlementYear(i);
					settlementPlanBasic.setSettlementMonth(j);
					settlementPlanBasic.setContractId(table.getContractId());
					settlementPlanBasic.setProjectId(table.getProjectId());
					// 直接从集合中取
					settlementPlanBasic.setSettlementAmount(CommonUtil.parseDouble(String.valueOf(planActAmounts.get(date)), 0));
					this.settlementPlanBasicDao.save(settlementPlanBasic);

					// 合同付款计划
					SettlementPlan settlementPlan = new SettlementPlan();
					settlementPlan.setContractId(contractId);
					settlementPlan.setSettlementYear(i);
					settlementPlan.setSettlementMonth(j);
					settlementPlan.setContractId(table.getContractId());
					settlementPlan.setProjectId(table.getProjectId());
					// 直接从集合中取
					settlementPlan.setSettlementAmount(CommonUtil.parseDouble(String.valueOf(planActAmounts.get(date)), 0));
					this.settlementPlanDao.save(settlementPlan);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * 根据采购合同信息上线创建合同表
	 * @param contractOnline
	 * @return
	 */
	public PrjContractTable createTpcPrjContractTableByContractOnline(ContractOnline contractOnline) {
		String contractId = contractOnline.getContractId();// 合同上线表ID与正式表ID一样
		if (this.get(contractId) != null) {
			System.out.println("createTpcPrjContractTableByContractOnline error: PrjContractTable is exist");
			return null;
		}
		PrjContractTable table = new PrjContractTable();
		try {
			// 复制采购合同上线信息至合同正式表
			BeanUtils.copyProperties(table, contractOnline);
			// 设置独有属性
			RegisterProject project = registerProjectService.get(contractOnline.getProjectId());
			table.setProjectNo(project.getProjectNo());
			table.setContractTypeCode(PrjContractTableConstant.PURCHASE);
			table.setContractType(PrjContractTableConstant.getContractTypeMap().get(PrjContractTableConstant.PURCHASE));
			table.setContractStatusCode(PrjContractTableConstant.EFFECT);
			table.setContractStatus(PrjContractTableConstant.getContractStatusMap().get(PrjContractTableConstant.EFFECT));
			table.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.prjContractTableDao.save(table);
			
			//创建印花税
			taxAmountService.createContractStampTaxAmount(contractOnline, table);

			// 拷贝附件
			String[] sourceModule = new String[] { ContractOnline.MODULE_ID, ContractOnline.BUSI_TYPE, contractId, "" };
			String[] targetModule = new String[] { PrjContractTable.MODULE_ID, PrjContractTable.BUSI_TYPE, contractId, "" };
			FilesUtil.copySourceFilesToTargetModule(sourceModule, targetModule);

			// 复制采购合同上线合同金额至合同正式表的合同金额表
			List<ContractContractAmount> contractAmountList = contractOnline.getContractAmountList();
			if (CollectionUtils.isNotEmpty(contractAmountList) && contractAmountList.size() > 0) {
				List<PrjContractAmount> amountList = new ArrayList<PrjContractAmount>();
				PrjContractAmount amount;
				for (ContractContractAmount contractAmount : contractAmountList) {
					amount = new PrjContractAmount();
					BeanUtils.copyProperties(amount, contractAmount);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					amount.setContractId(contractId);
					amountList.add(amount);
				}
				// 批量保存
				if (amountList.size() > 0) {
					this.prjContractAmountDao.save(amountList);
				}
			}

			// 复制采购合同上线货品及服务信息至合同正式表的货品及服务信息表
			List<ContractGoods> contractGoodsList = contractOnline.getGoodsList();
			if (CollectionUtils.isNotEmpty(contractGoodsList) && contractGoodsList.size() > 0) {
				List<PrjContractGoods> goodsList = new ArrayList<PrjContractGoods>();
				PrjContractGoods goods;
				for (ContractGoods contractGoods : contractGoodsList) {
					goods = new PrjContractGoods();
					BeanUtils.copyProperties(goods, contractGoods);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					goods.setContractId(contractId);
					goodsList.add(goods);
				}
				// 批量保存
				if (goodsList.size() > 0) {
					this.prjContractGoodsDao.save(goodsList);
				}
			}

			// 复制采购合同上线收款条款至合同正式表的收款条款表
			List<ContractCollectionTerms> collectionTermsList = contractOnline.getCollectionTermsList();
			if (CollectionUtils.isNotEmpty(collectionTermsList) && collectionTermsList.size() > 0) {
				List<PrjContractCollectionTerms> termsList = new ArrayList<PrjContractCollectionTerms>();
				PrjContractCollectionTerms terms;
				for (ContractCollectionTerms collectionTerms : collectionTermsList) {
					terms = new PrjContractCollectionTerms();
					BeanUtils.copyProperties(terms, collectionTerms);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					terms.setContractId(contractId);
					termsList.add(terms);
				}
				// 批量保存
				if (termsList.size() > 0) {
					this.prjContractCollectionTermsDao.save(termsList);
				}
			}

			// 创建合同基础付款计划和合同付款计划
			int beginYear = CommonUtil.parseInt(CommonUtil.format(contractOnline.getEffectiveDate(), "yyyy"));
			int endYear = CommonUtil.parseInt(CommonUtil.format(contractOnline.getExpirationDate(), "yyyy"));
			Map<String, Double> planActAmounts = contractOnlineService.getPayPlanAmounts(contractId);
			for (int i = beginYear; i <= endYear; i++) {
				for (int j = 1; j <= 12; j++) {
					String date = i + "-" + j;
					if (j < 10) {
						date = i + "-0" + j;
					}
					// 合同基础付款计划
					SettlementPlanBasic settlementPlanBasic = new SettlementPlanBasic();
					settlementPlanBasic.setContractId(contractId);
					settlementPlanBasic.setSettlementYear(i);
					settlementPlanBasic.setSettlementMonth(j);
					settlementPlanBasic.setContractId(table.getContractId());
					settlementPlanBasic.setProjectId(table.getProjectId());
					// 直接从集合中取
					settlementPlanBasic.setSettlementAmount(CommonUtil.parseDouble(String.valueOf(planActAmounts.get(date)), 0));
					this.settlementPlanBasicDao.save(settlementPlanBasic);

					// 合同付款计划
					SettlementPlan settlementPlan = new SettlementPlan();
					settlementPlan.setContractId(contractId);
					settlementPlan.setSettlementYear(i);
					settlementPlan.setSettlementMonth(j);
					settlementPlan.setContractId(table.getContractId());
					settlementPlan.setProjectId(table.getProjectId());
					// 直接从集合中取
					settlementPlan.setSettlementAmount(CommonUtil.parseDouble(String.valueOf(planActAmounts.get(date)), 0));
					this.settlementPlanDao.save(settlementPlan);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * 根据衍生合同信息上线创建合同表
	 * @param contractOnline
	 * @return
	 */
	public PrjContractTable createTpcPrjContractTableByDerivativeContractOnline(DerivativeContractOnline contractOnline) {
		String contractId = contractOnline.getContractId();// 合同上线表ID与正式表ID一样
		if (this.get(contractId) != null) {
			System.out.println("createTpcPrjContractTableByDerivativeContractOnline error: PrjContractTable is exist");
			return null;
		}
		PrjContractTable table = new PrjContractTable();
		try {
			// 复制衍生合同上线信息至合同正式表
			BeanUtils.copyProperties(table, contractOnline);
			// 设置独有属性
			RegisterProject project = registerProjectService.get(contractOnline.getProjectId());
			table.setProjectNo(project.getProjectNo());
			table.setContractPartyId(contractOnline.getSupplierId());// 合同对方即供方
			table.setContractParty(contractOnline.getSupplierName());
			table.setContractTypeCode(PrjContractTableConstant.DERIVATIVE);
			table.setContractType(PrjContractTableConstant.getContractTypeMap().get(PrjContractTableConstant.DERIVATIVE));
			table.setContractStatusCode(PrjContractTableConstant.EFFECT);
			table.setContractStatus(PrjContractTableConstant.getContractStatusMap().get(PrjContractTableConstant.EFFECT));
			table.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.prjContractTableDao.save(table);
			
			//创建印花税
			taxAmountService.createDerivativeStampTaxAmount(contractOnline, table);

			// 拷贝附件
			String[] sourceModule = new String[] { ContractOnline.MODULE_ID, ContractOnline.BUSI_TYPE, contractId, "" };
			String[] targetModule = new String[] { PrjContractTable.MODULE_ID, PrjContractTable.BUSI_TYPE, contractId, "" };
			FilesUtil.copySourceFilesToTargetModule(sourceModule, targetModule);

			// 复制衍生合同上线合同金额至合同正式表的合同金额表
			List<DerivativeContractContractAmount> contractAmountList = contractOnline.getContractAmountList();
			if (CollectionUtils.isNotEmpty(contractAmountList) && contractAmountList.size() > 0) {
				List<PrjContractAmount> amountList = new ArrayList<PrjContractAmount>();
				PrjContractAmount amount;
				for (DerivativeContractContractAmount contractAmount : contractAmountList) {
					amount = new PrjContractAmount();
					BeanUtils.copyProperties(amount, contractAmount);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					amount.setContractId(contractId);
					amountList.add(amount);
				}
				// 批量保存
				if (amountList.size() > 0) {
					this.prjContractAmountDao.save(amountList);
				}
			}

			// 复制衍生合同上线货品及服务信息至合同正式表的货品及服务信息表
			List<DerivativeContractGoods> contractGoodsList = contractOnline.getGoodsList();
			if (CollectionUtils.isNotEmpty(contractGoodsList) && contractGoodsList.size() > 0) {
				List<PrjContractGoods> goodsList = new ArrayList<PrjContractGoods>();
				PrjContractGoods goods;
				for (DerivativeContractGoods contractGoods : contractGoodsList) {
					goods = new PrjContractGoods();
					BeanUtils.copyProperties(goods, contractGoods);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					goods.setContractId(contractId);
					goodsList.add(goods);
				}
				// 批量保存
				if (goodsList.size() > 0) {
					this.prjContractGoodsDao.save(goodsList);
				}
			}

			// 复制衍生合同上线收款条款至合同正式表的收款条款表
			List<DerivativeContractCollectionTerms> collectionTermsList = contractOnline.getCollectionTermsList();
			if (CollectionUtils.isNotEmpty(collectionTermsList) && collectionTermsList.size() > 0) {
				List<PrjContractCollectionTerms> termsList = new ArrayList<PrjContractCollectionTerms>();
				PrjContractCollectionTerms terms;
				for (DerivativeContractCollectionTerms collectionTerms : collectionTermsList) {
					terms = new PrjContractCollectionTerms();
					BeanUtils.copyProperties(terms, collectionTerms);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					terms.setContractId(contractId);
					termsList.add(terms);
				}
				// 批量保存
				if (termsList.size() > 0) {
					this.prjContractCollectionTermsDao.save(termsList);
				}
			}

			// 创建合同基础付款计划和合同付款计划
			int beginYear = CommonUtil.parseInt(CommonUtil.format(contractOnline.getEffectiveDate(), "yyyy"));
			int endYear = CommonUtil.parseInt(CommonUtil.format(contractOnline.getExpirationDate(), "yyyy"));
			Map<String, Double> planActAmounts = contractOnlineService.getPayPlanAmounts(contractId);
			for (int i = beginYear; i <= endYear; i++) {
				for (int j = 1; j <= 12; j++) {
					String date = i + "-" + j;
					if (j < 10) {
						date = i + "-0" + j;
					}
					// 合同基础付款计划
					SettlementPlanBasic settlementPlanBasic = new SettlementPlanBasic();
					settlementPlanBasic.setContractId(contractId);
					settlementPlanBasic.setSettlementYear(i);
					settlementPlanBasic.setSettlementMonth(j);
					settlementPlanBasic.setContractId(table.getContractId());
					settlementPlanBasic.setProjectId(table.getProjectId());
					// 直接从集合中取
					settlementPlanBasic.setSettlementAmount(CommonUtil.parseDouble(String.valueOf(planActAmounts.get(date)), 0));
					this.settlementPlanBasicDao.save(settlementPlanBasic);

					// 合同付款计划
					SettlementPlan settlementPlan = new SettlementPlan();
					settlementPlan.setContractId(contractId);
					settlementPlan.setSettlementYear(i);
					settlementPlan.setSettlementMonth(j);
					settlementPlan.setContractId(table.getContractId());
					settlementPlan.setProjectId(table.getProjectId());
					// 直接从集合中取
					settlementPlan.setSettlementAmount(CommonUtil.parseDouble(String.valueOf(planActAmounts.get(date)), 0));
					this.settlementPlanDao.save(settlementPlan);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * 取已变更最大次数
	 * @param contractId
	 * @return
	 */
	public int getMaxCount(String contractId) {
		int count = 1;
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		Map<String, Boolean> orderByMap = new LinkedHashMap<String, Boolean>();
		params.put("contractId", contractId);
		orderByMap.put("changeCount", false);
		List<PrjContractTableBM> list = this.prjContractTableBMDao.find(params, orderByMap);
		if (list != null && list.size() > 0) {
			count = list.get(0).getChangeCount() + 1;
		}
		return count;
	}

	/**
	 * 删除合同信息（为保存新的合同信息）
	 * @param contractId
	 */
	public void deletePrjContractTable(String contractId) {
		this.prjContractTableDao.delete(contractId);
		this.prjContractAmountDao.deleteByProperty("contractId", contractId);
		this.prjContractGoodsDao.deleteByProperty("contractId", contractId);
		this.prjContractCollectionTermsDao.deleteByProperty("contractId", contractId);
		this.settlementPlanBasicDao.deleteByProperty("contractId", contractId);
		this.settlementPlanDao.deleteByProperty("contractId", contractId);
		String[] deleteModule = new String[] { PrjContractTable.MODULE_ID, PrjContractTable.BUSI_TYPE, contractId, "" };
		FilesUtil.deleteFiles(deleteModule);
	}

	/**
	 * 保存合同修改前信息
	 * @param contractId
	 * @return
	 */
	public PrjContractTable createTpcPrjContractTableBM(String changeId, String contractId) {
		int count = getMaxCount(contractId);
		// 原正式合同信息系统
		PrjContractTable table = this.get(contractId);
		PrjContractTableBM tableBM = new PrjContractTableBM();
		try {
			// 复制原正式表至合同BM表
			BeanUtils.copyProperties(tableBM, table);
			tableBM.setBmId(changeId);// 直接使用变更上线单的ID
			tableBM.setChangeCount(count);// 修改次数
			tableBM.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.prjContractTableBMDao.save(tableBM);

			// 拷贝附件（将正式表附加拷贝至BM表下）
			String[] sourceModule = new String[] { PrjContractTable.MODULE_ID, PrjContractTable.BUSI_TYPE, contractId, "" };
			String[] targetModule = new String[] { PrjContractTableBM.MODULE_ID, PrjContractTableBM.BUSI_TYPE, changeId, "" };
			FilesUtil.copySourceFilesToTargetModule(sourceModule, targetModule);

			// 复制原正式合同金额至金额BM表
			List<PrjContractAmount> contractAmountList = table.getContractAmountList();
			if (CollectionUtils.isNotEmpty(contractAmountList) && contractAmountList.size() > 0) {
				List<PrjContractAmountBM> amountList = new ArrayList<PrjContractAmountBM>();
				PrjContractAmountBM amount;
				for (PrjContractAmount contractAmount : contractAmountList) {
					amount = new PrjContractAmountBM();
					BeanUtils.copyProperties(amount, contractAmount);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					amount.setChangeId(changeId);
					amountList.add(amount);
				}
				// 批量保存
				if (amountList.size() > 0) {
					this.prjContractAmountBMDao.save(amountList);
				}
			}

			// 复制原正式合同货品及服务至货品及服务BM表
			List<PrjContractGoods> contractGoodsList = table.getContractGoodsList();
			if (CollectionUtils.isNotEmpty(contractGoodsList) && contractGoodsList.size() > 0) {
				List<PrjContractGoodsBM> goodsList = new ArrayList<PrjContractGoodsBM>();
				PrjContractGoodsBM goods;
				for (PrjContractGoods contractGoods : contractGoodsList) {
					goods = new PrjContractGoodsBM();
					BeanUtils.copyProperties(goods, contractGoods);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					goods.setChangeId(changeId);
					goodsList.add(goods);
				}
				// 批量保存
				if (goodsList.size() > 0) {
					this.prjContractGoodsBMDao.save(goodsList);
				}
			}

			// 复制原正式合同收款条款至收款条款BM表
			List<PrjContractCollectionTerms> collectionTermsList = table.getCollectionTermsList();
			if (CollectionUtils.isNotEmpty(collectionTermsList) && collectionTermsList.size() > 0) {
				List<PrjContractCollectionTermsBM> termsList = new ArrayList<PrjContractCollectionTermsBM>();
				PrjContractCollectionTermsBM terms;
				for (PrjContractCollectionTerms collectionTerms : collectionTermsList) {
					terms = new PrjContractCollectionTermsBM();
					BeanUtils.copyProperties(terms, collectionTerms);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					terms.setChangeId(changeId);
					termsList.add(terms);
				}
				// 批量保存
				if (termsList.size() > 0) {
					this.prjContractCollectionTermsBMDao.save(termsList);
				}
			}

			// 复制原合同付款计划至合同付款计划BM表
			List<SettlementPlanBasic> settlementPlanBasicList = this.settlementPlanBasicDao.getByContractId(contractId);
			if (CollectionUtils.isNotEmpty(settlementPlanBasicList) && settlementPlanBasicList.size() > 0) {
				List<SettlementPlanBM> planBMList = new ArrayList<SettlementPlanBM>();
				SettlementPlanBM planBM;
				for (SettlementPlanBasic settlementPlanBasic : settlementPlanBasicList) {
					planBM = new SettlementPlanBM();
					BeanUtils.copyProperties(planBM, settlementPlanBasic);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					planBM.setChangeId(changeId);
					planBMList.add(planBM);
				}
				// 批量保存
				if (planBMList.size() > 0) {
					this.settlementPlanBMDao.save(planBMList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * 根据销售合同变更生成合同表
	 * @param contractChOrder
	 * @return
	 */
	public PrjContractTable createTpcPrjContractTableByOrderChange(ContractChOrder contractChOrder) {
		String changeId = contractChOrder.getChangeId();
		String contractId = contractChOrder.getContractId();
		// 备份修改前合同信息
		createTpcPrjContractTableBM(changeId, contractId);
		// 删掉原合同信息
		deletePrjContractTable(contractId);

		// 重新新建合同信息
		PrjContractTable table = new PrjContractTable();
		try {
			// 复制销售合同上线信息至合同正式表
			BeanUtils.copyProperties(table, contractChOrder);
			// 设置独有属性
			table.setContractId(contractId);
			RegisterProject project = registerProjectService.get(contractChOrder.getProjectId());
			table.setProjectNo(project.getProjectNo());
			table.setContractNo(contractChOrder.getContractNo());
			table.setContractName(contractChOrder.getContractName());
			table.setContractTypeCode(PrjContractTableConstant.SALE);
			table.setContractType(PrjContractTableConstant.getContractTypeMap().get(PrjContractTableConstant.SALE));
			table.setContractStatusCode(PrjContractTableConstant.EFFECT);
			table.setContractStatus(PrjContractTableConstant.getContractStatusMap().get(PrjContractTableConstant.EFFECT));
			table.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.prjContractTableDao.save(table);

			// 拷贝附件
			String[] sourceModule = new String[] { contractChOrder.MODULE_ID, contractChOrder.BUSI_TYPE, changeId, "" };
			String[] targetModule = new String[] { PrjContractTable.MODULE_ID, PrjContractTable.BUSI_TYPE, contractId, "" };
			FilesUtil.copySourceFilesToTargetModule(sourceModule, targetModule);

			// 复制采购合同上线合同金额至合同正式表的合同金额表
			List<PrjContractAmount> amountList = new ArrayList<PrjContractAmount>();
			List<OrderChangeContractAmount> contractAmountList = contractChOrder.getContractAmountList();
			if (CollectionUtils.isNotEmpty(contractAmountList) && contractAmountList.size() > 0) {
				PrjContractAmount amount;
				for (OrderChangeContractAmount contractAmount : contractAmountList) {
					amount = new PrjContractAmount();
					BeanUtils.copyProperties(amount, contractAmount);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					amount.setContractId(contractId);
					amountList.add(amount);
				}
				// 批量保存
				if (amountList.size() > 0) {
					this.prjContractAmountDao.save(amountList);
				}
			}

			// 复制采购合同上线货品及服务信息至合同正式表的货品及服务信息表
			List<PrjContractGoods> goodsList = new ArrayList<PrjContractGoods>();
			List<OrderChangeGoods> contractGoodsList = contractChOrder.getGoodsList();
			if (CollectionUtils.isNotEmpty(contractGoodsList) && contractGoodsList.size() > 0) {
				PrjContractGoods goods;
				for (OrderChangeGoods contractGoods : contractGoodsList) {
					goods = new PrjContractGoods();
					BeanUtils.copyProperties(goods, contractGoods);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					goods.setContractId(contractId);
					goodsList.add(goods);
				}
				// 批量保存
				if (goodsList.size() > 0) {
					this.prjContractGoodsDao.save(goodsList);
				}
			}

			// 复制采购合同上线收款条款至合同正式表的收款条款表
			List<PrjContractCollectionTerms> termsList = new ArrayList<PrjContractCollectionTerms>();
			List<OrderChangeCollectionTerms> collectionTermsList = contractChOrder.getCollectionTermsList();
			if (CollectionUtils.isNotEmpty(collectionTermsList) && collectionTermsList.size() > 0) {
				PrjContractCollectionTerms terms;
				for (OrderChangeCollectionTerms collectionTerms : collectionTermsList) {
					terms = new PrjContractCollectionTerms();
					BeanUtils.copyProperties(terms, collectionTerms);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					terms.setContractId(contractId);
					termsList.add(terms);
				}
				// 批量保存
				if (termsList.size() > 0) {
					this.prjContractCollectionTermsDao.save(termsList);
				}
			}

			// 创建合同基础付款计划和合同付款计划
			int beginYear = CommonUtil.parseInt(CommonUtil.format(contractChOrder.getEffectiveDate(), "yyyy"));
			int endYear = CommonUtil.parseInt(CommonUtil.format(contractChOrder.getExpirationDate(), "yyyy"));
			Map<String, Double> planActAmounts = orderOnlineService.getPayPlanAmounts(contractId);
			for (int i = beginYear; i <= endYear; i++) {
				for (int j = 1; j <= 12; j++) {
					String date = i + "-" + j;
					if (j < 10) {
						date = i + "-0" + j;
					}
					// 合同基础付款计划
					SettlementPlanBasic settlementPlanBasic = new SettlementPlanBasic();
					settlementPlanBasic.setContractId(contractId);
					settlementPlanBasic.setSettlementYear(i);
					settlementPlanBasic.setSettlementMonth(j);
					settlementPlanBasic.setContractId(table.getContractId());
					settlementPlanBasic.setProjectId(table.getProjectId());
					// 直接从集合中取
					settlementPlanBasic.setSettlementAmount(CommonUtil.parseDouble(String.valueOf(planActAmounts.get(date)), 0));
					this.settlementPlanBasicDao.save(settlementPlanBasic);

					// 合同付款计划
					SettlementPlan settlementPlan = new SettlementPlan();
					settlementPlan.setContractId(contractId);
					settlementPlan.setSettlementYear(i);
					settlementPlan.setSettlementMonth(j);
					settlementPlan.setContractId(table.getContractId());
					settlementPlan.setProjectId(table.getProjectId());
					// 直接从集合中取
					settlementPlan.setSettlementAmount(CommonUtil.parseDouble(String.valueOf(planActAmounts.get(date)), 0));
					this.settlementPlanDao.save(settlementPlan);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * 根据采购合同变更生成合同表
	 * @param contractChange
	 * @return
	 */
	public PrjContractTable createTpcPrjContractTableByContractChange(ContractOrder contractOrder) {
		String changeId = contractOrder.getChangeId();
		String contractId = contractOrder.getContractId();// 合同上线表ID与正式表ID一样
		// 备份修改前合同信息
		createTpcPrjContractTableBM(changeId, contractId);
		// 删掉原合同信息
		deletePrjContractTable(contractId);

		// 重新新建合同信息
		PrjContractTable table = new PrjContractTable();
		try {
			// 将合同变更信息覆盖原合同信息
			BeanUtils.copyProperties(table, contractOrder);
			// 设置独有属性
			RegisterProject project = registerProjectService.get(contractOrder.getProjectId());
			table.setProjectNo(project.getProjectNo());
			table.setContractTypeCode(PrjContractTableConstant.PURCHASE);
			table.setContractType(PrjContractTableConstant.getContractTypeMap().get(PrjContractTableConstant.PURCHASE));
			table.setContractStatusCode(PrjContractTableConstant.EFFECT);
			table.setContractStatus(PrjContractTableConstant.getContractStatusMap().get(PrjContractTableConstant.EFFECT));
			table.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.prjContractTableDao.save(table);

			// 拷贝附件
			String[] sourceModule = new String[] { contractOrder.MODULE_ID, contractOrder.BUSI_TYPE, changeId, "" };
			String[] targetModule = new String[] { PrjContractTable.MODULE_ID, PrjContractTable.BUSI_TYPE, contractId, "" };
			FilesUtil.copySourceFilesToTargetModule(sourceModule, targetModule);

			// 复制采购合同上线合同金额至合同正式表的合同金额表
			List<ContractChangeContractAmount> contractAmountList = contractOrder.getContractAmountList();
			if (CollectionUtils.isNotEmpty(contractAmountList) && contractAmountList.size() > 0) {
				List<PrjContractAmount> amountList = new ArrayList<PrjContractAmount>();
				PrjContractAmount amount;
				for (ContractChangeContractAmount contractAmount : contractAmountList) {
					amount = new PrjContractAmount();
					BeanUtils.copyProperties(amount, contractAmount);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					amount.setContractId(contractId);
					amountList.add(amount);
				}
				// 批量保存
				if (amountList.size() > 0) {
					this.prjContractAmountDao.save(amountList);
				}
			}

			// 复制采购合同上线货品及服务信息至合同正式表的货品及服务信息表
			List<ContractChangeGoods> contractGoodsList = contractOrder.getGoodsList();
			if (CollectionUtils.isNotEmpty(contractGoodsList) && contractGoodsList.size() > 0) {
				List<PrjContractGoods> goodsList = new ArrayList<PrjContractGoods>();
				PrjContractGoods goods;
				for (ContractChangeGoods contractGoods : contractGoodsList) {
					goods = new PrjContractGoods();
					BeanUtils.copyProperties(goods, contractGoods);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					goods.setContractId(contractId);
					goodsList.add(goods);
				}
				// 批量保存
				if (goodsList.size() > 0) {
					this.prjContractGoodsDao.save(goodsList);
				}
			}

			// 复制采购合同上线收款条款至合同正式表的收款条款表
			List<ContractChangeCollectionTerms> collectionTermsList = contractOrder.getCollectionTermsList();
			if (CollectionUtils.isNotEmpty(collectionTermsList) && collectionTermsList.size() > 0) {
				List<PrjContractCollectionTerms> termsList = new ArrayList<PrjContractCollectionTerms>();
				PrjContractCollectionTerms terms;
				for (ContractChangeCollectionTerms collectionTerms : collectionTermsList) {
					terms = new PrjContractCollectionTerms();
					BeanUtils.copyProperties(terms, collectionTerms);
					// 设置关联主表属性(到时候通过该ID查找合同金额)
					terms.setContractId(contractId);
					termsList.add(terms);
				}
				// 批量保存
				if (termsList.size() > 0) {
					this.prjContractCollectionTermsDao.save(termsList);
				}
			}

			// 创建合同基础付款计划和合同付款计划
			int beginYear = CommonUtil.parseInt(CommonUtil.format(contractOrder.getEffectiveDate(), "yyyy"));
			int endYear = CommonUtil.parseInt(CommonUtil.format(contractOrder.getExpirationDate(), "yyyy"));
			Map<String, Double> planActAmounts = contractChangeService.getPayPlanAmounts(contractId);
			for (int i = beginYear; i <= endYear; i++) {
				for (int j = 1; j <= 12; j++) {
					String date = i + "-" + j;
					if (j < 10) {
						date = i + "-0" + j;
					}
					// 合同基础付款计划
					SettlementPlanBasic settlementPlanBasic = new SettlementPlanBasic();
					settlementPlanBasic.setContractId(contractId);
					settlementPlanBasic.setSettlementYear(i);
					settlementPlanBasic.setSettlementMonth(j);
					settlementPlanBasic.setContractId(table.getContractId());
					settlementPlanBasic.setProjectId(table.getProjectId());
					// 直接从集合中取
					settlementPlanBasic.setSettlementAmount(CommonUtil.parseDouble(String.valueOf(planActAmounts.get(date)), 0));
					this.settlementPlanBasicDao.save(settlementPlanBasic);

					// 合同付款计划
					SettlementPlan settlementPlan = new SettlementPlan();
					settlementPlan.setContractId(contractId);
					settlementPlan.setSettlementYear(i);
					settlementPlan.setSettlementMonth(j);
					settlementPlan.setContractId(table.getContractId());
					settlementPlan.setProjectId(table.getProjectId());
					// 直接从集合中取
					settlementPlan.setSettlementAmount(CommonUtil.parseDouble(String.valueOf(planActAmounts.get(date)), 0));
					this.settlementPlanDao.save(settlementPlan);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * 修改合同状态为变更中
	 * @param contract
	 * @param change 是/否
	 * @param user
	 * @return
	 */
	public PrjContractTable updateToChange(PrjContractTable contract, boolean change, UserProfile user) {
		String code = PrjContractTableConstant.CHANGE;
		if (!change) {
			code = PrjContractTableConstant.EFFECT;
		}
		String status = PrjContractTableConstant.getContractStatusMap().get(code);
		contract.setContractStatusCode(code);
		contract.setContractStatus(status);
		contract.setModifiedBy(user.getName());
		contract.setModifiedById(user.getPersonId());
		contract.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		return this.update(contract);
	}

	/**
	 * 修改合同信息
	 * @param contract
	 * @return
	 */
	public PrjContractTable update(PrjContractTable contract) {
		this.prjContractTableDao.update(contract);
		return contract;
	}

	/**
	 * 根据项目ID,合同类别等参数获取审批完成的合同
	 * @param projectId
	 * @return
	 */
	public List<PrjContractTable> getPrjContractTables(Map<String, Object> parameters, UserProfile userProfile) {
		String authFilter = getAuthFilter(userProfile);
		List<PrjContractTable> contractTableList = null;
		try {
			contractTableList = this.prjContractTableDao.getList(parameters, authFilter);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return contractTableList;
	}

	/**
	 * 根据项目ID获取审批完成的销售合同
	 * @param projectId
	 * @return
	 */
	public Map<String, String> getSaleContractByPrjId(String projectId, UserProfile userProfile) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("projectId", projectId);
		parameters.put("contractTypeCode", PrjContractTableConstant.SALE);
		List<PrjContractTable> contractTableList = getPrjContractTables(parameters, userProfile);
		if (contractTableList != null && contractTableList.size() > 0) {
			for (PrjContractTable prjContractTable : contractTableList) {
				map.put(prjContractTable.getContractId(), "(" + prjContractTable.getContractNo() + ")" + prjContractTable.getContractName());
			}
		}
		return map;
	}

	/**
	 * 根据项目ID获取审批完成的采购合同
	 * @param projectId
	 * @return
	 */
	public Map<String, String> getPurchaseContractByPrjId(String projectId, UserProfile userProfile) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("projectId", projectId);
		parameters.put("contractTypeCode", PrjContractTableConstant.PURCHASE);
		List<PrjContractTable> contractTableList = getPrjContractTables(parameters, userProfile);
		if (contractTableList != null && contractTableList.size() > 0) {
			for (PrjContractTable prjContractTable : contractTableList) {
				map.put(prjContractTable.getContractId(), "(" + prjContractTable.getContractNo() + ")" + prjContractTable.getContractName());
			}
		}
		return map;
	}

	/**
	 * 根据项目ID获取审批完成的衍生合同
	 * @param projectId
	 * @return
	 */
	public Map<String, String> getDerivativeContractByPrjId(String projectId, UserProfile userProfile) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("projectId", projectId);
		parameters.put("contractTypeCode", PrjContractTableConstant.DERIVATIVE);
		List<PrjContractTable> contractTableList = getPrjContractTables(parameters, userProfile);
		if (contractTableList != null && contractTableList.size() > 0) {
			for (PrjContractTable prjContractTable : contractTableList) {
				map.put(prjContractTable.getContractId(), "(" + prjContractTable.getContractNo() + ")" + prjContractTable.getContractName());
			}
		}
		return map;
	}

}
