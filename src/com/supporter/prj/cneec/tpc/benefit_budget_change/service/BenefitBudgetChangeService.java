package com.supporter.prj.cneec.tpc.benefit_budget_change.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.math.BigDecimal;
import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetConstant;
import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetItemConstant;
import com.supporter.prj.cneec.tpc.benefit_budget.dao.BenefitBudgetDao;
import com.supporter.prj.cneec.tpc.benefit_budget.dao.BenefitContractBudgetDao;
import com.supporter.prj.cneec.tpc.benefit_budget.dao.BenefitContractCurrencyDao;
import com.supporter.prj.cneec.tpc.benefit_budget.dao.BenefitContractDao;
import com.supporter.prj.cneec.tpc.benefit_budget.dao.VBenefitProjectDao;
import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitBudget;
import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitContract;
import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitContractBudget;
import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitContractCurrency;
import com.supporter.prj.cneec.tpc.benefit_budget.entity.VBenefitProject;
import com.supporter.prj.cneec.tpc.benefit_budget.service.BenefitBudgetService;
import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitBudgetUtil;
import com.supporter.prj.cneec.tpc.benefit_budget_change.dao.BenefitBudgetChangeDao;
import com.supporter.prj.cneec.tpc.benefit_budget_change.dao.BenefitContractBudgetChDao;
import com.supporter.prj.cneec.tpc.benefit_budget_change.dao.BenefitContractChDao;
import com.supporter.prj.cneec.tpc.benefit_budget_change.dao.BenefitContractCurrencyChDao;
import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitBudgetChange;
import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractBudgetCh;
import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractCh;
import com.supporter.prj.cneec.tpc.benefit_budget_change.entity.BenefitContractCurrencyCh;
import com.supporter.prj.cneec.tpc.benefit_budget_change.util.BenefitAmountBean;
import com.supporter.prj.cneec.tpc.benefit_note.util.BenefitAmountAccumulativeBean;
import com.supporter.prj.cneec.tpc.util.ColModelUtil;
import com.supporter.prj.cneec.tpc.util.TpcBudgetUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.account.entity.Account;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.binding.JsonUtil;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

import net.sf.json.JSONArray;

@Service
@Transactional(TransManager.APP)
public class BenefitBudgetChangeService {

	@Autowired
	private VBenefitProjectDao benefitProjectDao;
	@Autowired
	private BenefitBudgetChangeDao benefitBudgetChangeDao;
	@Autowired
	private BenefitContractChDao benefitContractChDao;
	@Autowired
	private BenefitContractDao benefitContractDao;
	@Autowired
	private BenefitContractCurrencyChDao benefitContractCurrencyChDao;
	@Autowired
	private BenefitContractBudgetChDao benefitContractBudgetChDao;
	@Autowired
	private BenefitContractBudgetDao benefitContractBudgetDao;
	@Autowired
	private BenefitContractCurrencyDao benefitContractCurrencyDao;
	@Autowired
	private BenefitBudgetDao benefitBudgetDao;
	@Autowired
	private BenefitBudgetService benefitBudgetService;
	/**
	 * 项目Grid
	 * @param jqGrid
	 * @param benefitProject
	 * @return
	 */
	public List<VBenefitProject> getGrid(JqGrid jqGrid, VBenefitProject benefitProject) {
		return this.benefitProjectDao.findPage(jqGrid, benefitProject);
	}

	/**
	 * 过程合同Grid
	enefitProjectrid
	 * @param parameters
	 * @return
	 */
	public List<BenefitContractCh> getBenefitContractGrid(JqGrid jqGrid, Map<String, Object> parameters) {
		return this.benefitContractChDao.findPage(jqGrid, parameters);
	}

	/**
	 * 单合同效益预算金额Grid
	 * @param jqGrid
	 * @param parameters
	 * @return
	 */
	public List<BenefitContractBudgetCh> getBenefitContractBudgetGrid(JqGrid jqGrid, Map<String, Object> parameters) {
		return this.benefitContractBudgetChDao.findPage(jqGrid, parameters);
	}

	/**
	 * 获取汇总预算表
	 * @param jqGrid
	 * @param projectId
	 * @param parameters
	 * @return
	 */
	public List<BenefitBudgetChange> getBenefitAssembleGrid(JqGrid jqGrid, Map<String, Object> parameters) {
		return this.benefitBudgetChangeDao.findPage(jqGrid, parameters);
	}

	/**
	 * 获取项目预算（项目汇总）效益预算表头
	 * @param projectId
	 * @return
	 */
	public String getBenefitBudgetAssembleTitleData(String projectId, boolean allBudget) {
		// 列表头
		List<Map<String, Object>> colModelData = new ArrayList<Map<String, Object>>();
		// 分组表头集合
		List<Map<String, Object>> groupTitleData = new ArrayList<Map<String, Object>>();

		int total_width = 0;
		Map<String, Object> jsonMap;
		// 主键
		jsonMap = ColModelUtil.getColModelKeyData("budgetId");// 设为主键为了与二级parent对应
		colModelData.add(jsonMap);

		// 序号
		jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_XH, "serialNumber", null, BenefitBudgetConstant.TITLE_WIDTH_XH);
		colModelData.add(jsonMap);
		total_width += BenefitBudgetConstant.TITLE_WIDTH_XH;

		// 预算说明
		jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_YS, "budgetName", "left", BenefitBudgetConstant.TITLE_WIDTH_YS);
		colModelData.add(jsonMap);
		total_width += BenefitBudgetConstant.TITLE_WIDTH_YS;

		int width = BenefitBudgetConstant.TITLE_WIDTH;

		if (allBudget) {
			// 可用预算
			jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_BUDGET_TITLE_KY, "availableBudgetAmountDisplay", "right", width);
			colModelData.add(jsonMap);
			
			// 在途预算
			jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_BUDGET_TITLE_ZT, "onWayAmountDisplay", "right", width);
			colModelData.add(jsonMap);

			// 执行预算
			jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_BUDGET_TITLE_ZX, "executeAmountDisplay", "right", width);
			colModelData.add(jsonMap);
		}

		// 设置预算折人民币合计
		jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_ZH, "totalBudgetAmountDisplay", "right", width);
		colModelData.add(jsonMap);

		// 设置预算合计百分比
		jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_ZB, "totalProportionDisplay", "right", BenefitBudgetConstant.TITLE_WIDTH_QT2);
		colModelData.add(jsonMap);

		// 设置标题分组(合计)
		jsonMap = ColModelUtil.getGroupTitleData("totalBudgetAmountDisplay", 2, BenefitBudgetConstant.HEAD_TITLE_XMHJ);
		groupTitleData.add(jsonMap);

		if (allBudget) {
			total_width += width * 4 + BenefitBudgetConstant.TITLE_WIDTH_QT2;
		} else {
			total_width += width + BenefitBudgetConstant.TITLE_WIDTH_QT2;
		}
		// 拼装JSON对象字符串
		String colModelJSON = JSONArray.fromObject(colModelData).toString();
		String groupTitleJSON = JSONArray.fromObject(groupTitleData).toString();
		String jsonData = "{\"result\":\"success\",\"width\":" + total_width + ",\"colModelJSON\":" + colModelJSON + ",\"groupTitleJSON\":" + groupTitleJSON + "}";
		// System.out.println("jsonData:" + jsonData);
		return jsonData;
	}

	/**
	 * 获取项目预算（项目汇总）ListPage JSON格式数据
	 * @param projectId
	 * @return
	 * @throws Exception 
	 */
	public String getBenefitBudgetAssembleGrid(String projectId, boolean allBudget) throws Exception {
		List<BenefitBudgetChange> list = this.benefitBudgetChangeDao.queryBy("projectId", projectId, false, "orderDisplay", true);
		String json = "[]";
		int count = 0;
		if (list != null && list.size() > 0) {
			count = list.size();
			json = loadBudgetAssembleExtendField(projectId, list, allBudget);
		}
		// 拼装前台需要的ListPage JSON格式数据，records记录个数，page当前页，total总页数，rows数据内容
		json = "{\"records\":\"" + count + "\", \"page\":1, \"total\":1, \"rows\":" + json + "}";
		// System.out.println(json);
		return json;
	}

	/**
	 * 装填扩展属性
	 * @param assembleList
	 * @param assembleBeanList
	 * @return
	 * @throws Exception 
	 */
	public String loadBudgetAssembleExtendField(String projectId, List<BenefitBudgetChange> assembleList, boolean allBudget) throws Exception {
		List<Map<String, Object>> assembleBeanList = new ArrayList<Map<String, Object>>();
		// 定义可扩展属性Bean，动态添加属性，用作前台JqGrid向右扩展展示
		Map<String, Object> bean = null;
		for (BenefitBudgetChange assemble : assembleList) {
			// 实例化扩展属性对象
			bean = BenefitBudgetUtil.getBaseAssembleMap(assemble);

			// 给基础属性赋值
			if (allBudget) {
				bean.put("availableBudgetAmountDisplay", assemble.getAvailableBudgetAmountDisplay());
				bean.put("onWayAmountDisplay", assemble.getOnWayAmountDisplay());
				bean.put("executeAmountDisplay", assemble.getExecuteAmountDisplay());
			}

			bean.put("totalBudgetAmountDisplay", assemble.getTotalBudgetAmountDisplay());
			bean.put("totalProportionDisplay", assemble.getTotalProportionDisplay());

			assembleBeanList.add(bean);
		}
		String json = JSONArray.fromObject(assembleBeanList).toString();
		// System.out.println("json:"+json);
		return json;
	}

	/**
	 * 获取合同（项目过程）效益预算过程表头
	 * @param projectId
	 * @return
	 */
	public String getBenefitContractAssembleTitleData(String projectId) {
		// 列表头集合
		List<Map<String, Object>> colModelData = new ArrayList<Map<String, Object>>();
		// 分组表头集合
		List<Map<String, Object>> groupTitleData = new ArrayList<Map<String, Object>>();

		int total_width = 0;
		Map<String, Object> jsonMap;
		// 主键
		jsonMap = ColModelUtil.getColModelKeyData("budgetId");// 设为主键为了与二级parent对应
		colModelData.add(jsonMap);

		// 序号
		int width_xh = BenefitBudgetConstant.TITLE_WIDTH_XH;
		jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_XH, "serialNumber", null, width_xh);
		colModelData.add(jsonMap);
		total_width += width_xh;

		// 预算说明
		int width_ys = BenefitBudgetConstant.TITLE_WIDTH_YS;
		jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_YS, "budgetName", "left", width_ys);
		colModelData.add(jsonMap);
		total_width += width_ys;

		// 获取合同个数及币别个数
		List<BenefitContractCh> processContractList = this.benefitContractChDao.queryBy("projectId", projectId, false, "createdDate", true);
		List<BenefitContractCurrencyCh> contractCurrencyList = null;
		int currencySize = 0;
		int i = 1;
		int width = BenefitBudgetConstant.TITLE_WIDTH;
		for (BenefitContractCh contract : processContractList) {
			contractCurrencyList = this.getCurrencyListByProcessId(contract.getChangeId());
			currencySize = contractCurrencyList.size();
			// 设置动态币别
			for (int j = 0; j < contractCurrencyList.size(); j++) {
				jsonMap = ColModelUtil.getColModelData(contractCurrencyList.get(j).getCurrency(), "contract" + i + "currency" + (j + 1) + "AmountDisplay", "right", width);
				colModelData.add(jsonMap);
			}

			// 折人民币合计金额
			jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_ZH, "contract" + i + "totalRmbAmountDisplay", "right", width);
			colModelData.add(jsonMap);

			// 百分比
			jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_ZB, "contract" + i + "proportionDisplay", "right", BenefitBudgetConstant.TITLE_WIDTH_QT2);
			colModelData.add(jsonMap);

			// 设置标题分组(合同名称)
			jsonMap = ColModelUtil.getGroupTitleData("contract" + i + "currency1AmountDisplay", (currencySize + 2), contract.getContractName());
			groupTitleData.add(jsonMap);
			total_width += (currencySize + 1) * width + BenefitBudgetConstant.TITLE_WIDTH_QT2;

			i++;
		}

		// 设置预算折人民币合计
		jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_ZH, "totalBudgetAmountDisplay", "right", width);
		colModelData.add(jsonMap);

		// 设置预算合计百分比
		jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_ZB, "totalProportionDisplay", "right", BenefitBudgetConstant.TITLE_WIDTH_QT2);
		colModelData.add(jsonMap);

		// 设置合计标题分组(合计)
		jsonMap = ColModelUtil.getGroupTitleData("totalBudgetAmountDisplay", 2, BenefitBudgetConstant.HEAD_TITLE_XMHJ);
		groupTitleData.add(jsonMap);
		total_width += width + BenefitBudgetConstant.TITLE_WIDTH_QT2;

		int count = processContractList != null ? processContractList.size() : 0;
		// 拼装JSON对象字符串
		String colModelJSON = JSONArray.fromObject(colModelData).toString();
		String groupTitleJSON = JSONArray.fromObject(groupTitleData).toString();
		String jsonData = "{\"result\":\"success\",\"count\":" + count + ",\"width\":" + total_width + ",\"colModelJSON\":" + colModelJSON + ",\"groupTitleJSON\":" + groupTitleJSON + "}";
		// System.out.println("jsonData:" + jsonData);
		return jsonData;
	}

	/**
	 * 获取合同（项目过程）ListPage JSON格式数据
	 * @param projectId
	 * @return
	 * @throws Exception 
	 */
	public String getBenefitContractAssembleGrid(String projectId) throws Exception {
		List<BenefitBudgetChange> list = this.benefitBudgetChangeDao.queryBy("projectId", projectId, false, "orderDisplay", true);
		String json = "[]";
		int count = 0;
		if (list != null && list.size() > 0) {
			count = list.size();
			json = loadContractAssembleExtendField(projectId, list);
		}
		// 拼装前台需要的ListPage JSON格式数据，records记录个数，page当前页，total总页数，rows数据内容
		json = "{\"records\":\"" + count + "\", \"page\":1, \"total\":1, \"rows\":" + json + "}";
		// System.out.println(json);
		return json;
	}

	/**
	 * 设置币别金额
	 * @param x
	 * @param index
	 * @param bean
	 * @param budget
	 */
	public void switchLoadAssembleExtendField(int x, int index, Map<String, Object> bean, BenefitContractBudgetCh budget) {
		switch (index) {
		case 9:
			bean.put("contract" + x + "currency9AmountDisplay", budget.getCurrency9AmountDisplay());
		case 8:
			bean.put("contract" + x + "currency8AmountDisplay", budget.getCurrency8AmountDisplay());
		case 7:
			bean.put("contract" + x + "currency7AmountDisplay", budget.getCurrency7AmountDisplay());
		case 6:
			bean.put("contract" + x + "currency6AmountDisplay", budget.getCurrency6AmountDisplay());
		case 5:
			bean.put("contract" + x + "currency5AmountDisplay", budget.getCurrency5AmountDisplay());
		case 4:
			bean.put("contract" + x + "currency4AmountDisplay", budget.getCurrency4AmountDisplay());
		case 3:
			bean.put("contract" + x + "currency3AmountDisplay", budget.getCurrency3AmountDisplay());
		case 2:
			bean.put("contract" + x + "currency2AmountDisplay", budget.getCurrency2AmountDisplay());
		case 1:
			bean.put("contract" + x + "currency1AmountDisplay", budget.getCurrency1AmountDisplay());

		default:
			break;
		}
	}
	/**
	 * 设置币别汇率属性值
	 * @param index
	 * @param currencyList
	 * @param rateMap
	 */
	public void switchLoadCurrencyRate(int index, List<BenefitContractCurrencyCh> currencyList, Map<String, Double> rateMap) {
		// 从第index个币别开始到第一个设置汇率
		switch (index) {
		case 9:
			rateMap.put("rate9", currencyList.get(8).getRate());
		case 8:
			rateMap.put("rate8", currencyList.get(7).getRate());
		case 7:
			rateMap.put("rate7", currencyList.get(6).getRate());
		case 6:
			rateMap.put("rate6", currencyList.get(5).getRate());
		case 5:
			rateMap.put("rate5", currencyList.get(4).getRate());
		case 4:
			rateMap.put("rate4", currencyList.get(3).getRate());
		case 3:
			rateMap.put("rate3", currencyList.get(2).getRate());
		case 2:
			rateMap.put("rate2", currencyList.get(1).getRate());
		case 1:
			rateMap.put("rate1", currencyList.get(0).getRate());

		default:
			break;
		}
	}
	/**
	 * 装填扩展属性
	 * @param assembleList
	 * @param assembleBeanList
	 * @return
	 * @throws Exception 
	 */
	public String loadContractAssembleExtendField(String projectId, List<BenefitBudgetChange> assembleList) throws Exception {
		List<Map<String, Object>> assembleBeanList = new ArrayList<Map<String, Object>>();
		// 定义可扩展属性Bean，动态添加属性，用作前台JqGrid向右扩展展示
		Map<String, Object> bean = null;
		// 获取合同
		List<BenefitContractCh> contractList = this.benefitContractChDao.queryBy("projectId", projectId, false, "createdDate", true);
		// 遍历预算项给预算项添加扩展属性及设置值
		for (BenefitBudgetChange assemble : assembleList) {
			// 实例化扩展属性对象
			bean = BenefitBudgetUtil.getBaseAssembleMap(assemble);

			// 给基础属性赋值
			bean.put("totalBudgetAmountDisplay", assemble.getTotalBudgetAmountDisplay());
			bean.put("totalProportionDisplay", assemble.getTotalProportionDisplay());

			// 给合同属性赋值
			int x = 1;
			for (BenefitContractCh contract : contractList) {
				// 获取该预算下该合同的预算金额
				BenefitContractBudgetCh contractBudget = this.getBenefitContractBudget(contract.getChangeId(), assemble.getBudgetId());

				// 赋值预算金额
				switchLoadAssembleExtendField(x, contract.getCurrencyNum(), bean, contractBudget);

				bean.put("contract" + x + "totalRmbAmountDisplay", contractBudget.getTotalRmbAmountDisplay());
				bean.put("contract" + x + "proportionDisplay", contractBudget.getProportionDisplay());
				x++;
			}
			assembleBeanList.add(bean);
		}
		String json = JSONArray.fromObject(assembleBeanList).toString();
		// System.out.println(json);
		return json;
	}

	/**
	 * 获取项目效益预算表单个项目对象
	 * @param projectId
	 * @return
	 */
	public VBenefitProject get(String projectId) {
		return this.benefitProjectDao.get(projectId);
	}

	/**
	 * 根据项目ID预算ID获取项目下预算项
	 * @param projectId
	 * @param budgetId
	 * @return
	 */
	public BenefitBudgetChange getBenefitBudget(String projectId, String budgetId) {
		budgetId = budgetId == null ? BenefitBudgetItemConstant.SUMMARY_TRADE_TOTAL : budgetId;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		params.put("budgetId", budgetId);
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("orderDisplay", true);
		List<BenefitBudgetChange> itemList = this.benefitBudgetChangeDao.queryByParam(params, null, orders);
		if (itemList.size() > 0) {
			return itemList.get(0);
		}
		return null;
	}

	/**
	 * 获取项目下预算项
	 * @param projectId
	 * @return
	 */
	public List<BenefitBudgetChange> getBenefitBudgetList(String projectId) {
		return this.benefitBudgetChangeDao.queryBy("projectId", projectId, false, "orderDisplay", true);
	}

	/**
	 * 获取项目效益预算表单个合同对象
	 * @param processId
	 * @return
	 */
	public BenefitContractCh getBenefitContract(String changeId) {
		BenefitContractCh contract = this.benefitContractChDao.get(changeId);
		if (contract != null) {
			contract.setCurrencyList(this.getCurrencyListByProcessId(changeId));
		}
		return contract;
	}

	/**
	 * 根据属性获取唯一过程合同对象（评审合同ID，销售合同ID）
	 * @param properName
	 * @param propValue
	 * @return
	 */
	public BenefitContractCh findBenefitContractBy(String properName, String propValue) {
		return this.benefitContractChDao.findUniqueResult(properName, propValue);
	}

	/**
	 * 根据编号获取唯一对象
	 * @param benefitNo
	 * @return
	 */
	public BenefitContractCh getByBenefitNo(String benefitNo) {
		return this.benefitContractChDao.findUniqueResult("benefitNo", benefitNo);
	}

	/**
	 * 根据项目ID获取合同集合
	 * @param projectId
	 * @return
	 */
	public List<BenefitContractCh> getBenefitContractList(String projectId) {
		return this.benefitContractChDao.queryByProjectId(projectId);
	}

	/**
	 * 根据主表ID,币别ID获取币别项
	 * 
	 * @param processId
	 * @param currencyId
	 * @return
	 */
	public BenefitContractCurrencyCh queryUniqueCurrency(String changeId, String currencyId) {
		return this.benefitContractCurrencyChDao.queryUnique(changeId, currencyId);
	}

	/**
	 * 根据主表ID获取币别集合
	 * 
	 * @param processId
	 * @return
	 */
	public List<BenefitContractCurrencyCh> getCurrencyListByProcessId(String changeId) {
		return this.benefitContractCurrencyChDao.queryByProcessId(changeId);
	}

	/**
	 * 获取项目效益预算表单个合同预算对象
	 * @param processBudgetId
	 * @return
	 */
	public BenefitContractBudgetCh getBenefitContractBudget(String processBudgetId) {
		return this.benefitContractBudgetChDao.get(processBudgetId);
	}

	/**
	 * 获取项目效益预算表单个合同预算对象
	 *
	 * @param processId
	 * @param budgetId
	 * @return
	 */
	public BenefitContractBudgetCh getBenefitContractBudget(String changeId, String budgetId) {
		return this.benefitContractBudgetChDao.queryUnique(changeId, budgetId);
	}

	/**
	 * 根据过程合同ID获取所有预算项
	 * @param processId
	 * @return
	 */
	public List<BenefitContractBudgetCh> getBenefitContractBudgetList(String changeId) {
		return this.benefitContractBudgetChDao.queryByProcessId(changeId);
	}



	/**
	 * 根据效益编号设置合同信息
	 *
	 * @param benefitNo
	 * @param contractId
	 * @param contractNo
	 * @param contractName
	 */
	public void updateContractByBenefitNo(String benefitNo, String contractId, String contractNo, String contractName) {
		BenefitContractCh benefitContract = this.getByBenefitNo(benefitNo);
		if (benefitContract != null) {
			benefitContract.setContractId(contractId);
			benefitContract.setContractNo(contractNo);
			benefitContract.setContractName(contractName);
			this.updateBenefitContract(benefitContract);
		}
	}

	public void updateBenefitContract(BenefitContractCh benefitContract) {
		this.benefitContractChDao.update(benefitContract);
	}

	/**
	 * 预算修改
	 */
	public void updateBenefitBudget(BenefitBudgetChange budget) {
		this.benefitBudgetChangeDao.update(budget);
	}

	/**
	 * 
	 * @param contractId
	 * @param user
	 * @return
	 */
	public BenefitContractCh createBenefitContractChange(String contractId, UserProfile user) {
		BenefitContractCh benefitContractCh = new BenefitContractCh();
		if(StringUtils.isBlank(contractId)) {
			return null;
		}
		
		BenefitContract benefitContract = null;
		//合同预算表
		List<BenefitContract> benefitContractList = benefitContractDao.findBy("contractId", contractId);
		if(!CommonUtil.isNullOrEmpty(benefitContractList)) {
			benefitContract = benefitContractList.get(0);
		}
		if(benefitContract == null) {
			return null;
		}
		try {
			BeanUtils.copyProperties(benefitContractCh, benefitContract);
			//复制主表信息，并保存
			benefitContractCh.setChangeId(com.supporter.util.UUIDHex.newId());
			benefitContractCh.setProcessId(benefitContract.getProcessId());
			benefitContractCh.setContractId(contractId);
			benefitContractCh.setContractNo(benefitContract.getContractNo());
			benefitContractCh.setSwfStatus(BenefitContractCh.DRAFT);
			benefitContractCh.setProcId(null);
			benefitContractCh.setCreatedById(benefitContract.getCreatedById());
			benefitContractCh.setCreatedBy(benefitContract.getCreatedBy());
			benefitContractCh.setCreatedDate(benefitContract.getCreatedDate());
			benefitContractCh.setDeptId(benefitContract.getDeptId());
			benefitContractCh.setDeptName(benefitContract.getDeptName());
			//保存主表
            benefitContractChDao.save(benefitContractCh);
            //预算项表
    		String  processId = benefitContract.getProcessId();
    		if(processId != null) {
    			List<BenefitContractBudget> benefitContractBudgetList = benefitBudgetService.getBenefitContractBudgetList(processId);
				if(benefitContractBudgetList != null) {
					for(int i = 0; i < benefitContractBudgetList.size(); i++) {
						BenefitContractBudget benefitContractBudget = benefitContractBudgetList.get(i);
						//定义初稿明细
						BenefitContractBudgetCh benefitContractBudgetCh = new BenefitContractBudgetCh();
						BeanUtils.copyProperties(benefitContractBudgetCh, benefitContractBudget);
						benefitContractBudgetCh.setProcessBudgetId(com.supporter.util.UUIDHex.newId());
						benefitContractBudgetCh.setPbOldId(benefitContractBudget.getProcessBudgetId());
						benefitContractBudgetCh.setChangeId(benefitContractCh.getChangeId());
						//保存
						benefitContractBudgetChDao.save(benefitContractBudgetCh);
					}
				}	
				//币别
				List<BenefitContractCurrency> benefitContractCurrencyList = benefitBudgetService.getCurrencyListByProcessId(processId);
				if(benefitContractCurrencyList != null) {
					for(int i = 0; i <benefitContractCurrencyList.size(); i++) {
						BenefitContractCurrency benefitContractCurrency = benefitContractCurrencyList.get(i);
						//定义初稿明细
						BenefitContractCurrencyCh benefitContractCurrencyCh = new BenefitContractCurrencyCh();
						BeanUtils.copyProperties(benefitContractCurrencyCh, benefitContractCurrency);
						benefitContractCurrencyCh.setRecordId(com.supporter.util.UUIDHex.newId());
						benefitContractCurrencyCh.setRecordOldId(benefitContractCurrency.getRecordId());
						benefitContractCurrencyCh.setChangeId(benefitContractCh.getChangeId());
						//保存
						benefitContractCurrencyChDao.save(benefitContractCurrencyCh);
					}
				}	
    		}else {
				System.out.println("processId为空");
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return benefitContractCh;
	}

	public BenefitContractCh initEditOrViewPage(String changeId, UserProfile user) {
		BenefitContractCh benefitContractCh = null;
		if (StringUtils.isNotBlank(changeId)) {
			benefitContractCh = (BenefitContractCh) this.benefitContractChDao.get(changeId);
		}
		if (benefitContractCh != null) {
			benefitContractCh.setCurrencyList(this.getCurrencyListByProcessId(changeId));
		}
		if (benefitContractCh == null) {
			benefitContractCh = new BenefitContractCh();
			if (StringUtils.isBlank(changeId)) {
				changeId = UUIDHex.newId();
			}
			benefitContractCh.setChangeId(changeId);
			benefitContractCh.setSwfStatus(BenefitContractCh.DRAFT);
		} else {
//			benefitContractCh.setCurrencyList(this.getCurrencyListByProcessId(changeId));
			benefitContractChDao.update(benefitContractCh);
		}
		return benefitContractCh;
	}


	public void delete(UserProfile user, String changeIds) {
		BenefitContractCh benefitContractCh = benefitContractChDao.get(changeIds);
		benefitContractChDao.delete(benefitContractCh);
		
	}

	public BenefitContractCh getBenefitContractChByChangeId(String changeId) {
		// TODO Auto-generated method stub
		return this.benefitContractChDao.get(changeId);
	}
	
	/**
	 * 流程更新对象
	 */
	public BenefitContractCh update(BenefitContractCh benefitContractCh) {
         if(benefitContractCh!=null) {
        	 return benefitContractCh;
         }	
         this.benefitContractChDao.update(benefitContractCh);
         return benefitContractCh;
	}
	//保存更新
	public BenefitContractCh saveOrUpdate(UserProfile user, BenefitContractCh benefitContractCh, Map<String, Object> valueMap) {
		//if (benefitContractCh.getChangeId().equals("")) {
		if (benefitContractCh.getAdd()) {
			// 装配基础信息
			loadBenefit(benefitContractCh, user);
			benefitContractCh.setAmountInUsd(getAmountInUSD(benefitContractCh.getChangeId()));
			this.benefitContractChDao.save(benefitContractCh);
		} else {
			benefitContractCh.setCreatedBy(user.getName());
			benefitContractCh.setCreatedById(user.getPersonId());
			benefitContractCh.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			Dept dept = user.getDept();
			if (dept != null) {
				benefitContractCh.setDeptName(dept.getName());
				benefitContractCh.setDeptId(dept.getDeptId());
			}
			// 设置更新时间
			benefitContractCh.setModifiedBy(user.getName());
			benefitContractCh.setModifiedById(user.getPersonId());
			benefitContractCh.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			benefitContractCh.setAmountInUsd(getAmountInUSD(benefitContractCh.getChangeId()));
			this.benefitContractChDao.update(benefitContractCh);
		}
//		saveOrUpdateBudget(benefitContractCh, valueMap);
//		List<BenefitContractBudgetCh> benefitContractBudgetChList = benefitContractCh.getBenefitBudgetChangeList();
//		System.out.println("benefitContractBudgetChList:"+benefitContractBudgetChList);
//		if(CollectionUtils.isNotEmpty(benefitContractBudgetChList)){
//			saveOrUpdateBenefitAmountList(benefitContractCh.getChangeId(), benefitContractBudgetChList);
//		}
//		List<BenefitContractCurrencyCh> benefitContractCurrencyChList = benefitContractCh.getCurrencyList();
//		if(CollectionUtils.isNotEmpty(benefitContractCurrencyChList)){
//			saveOrUpdateBenefitAmountList(benefitContractCh.getChangeId(), benefitContractBudgetChList);
//		}
		saveOrUpdateSummaryBudget(benefitContractCh, valueMap);
		setProfit(benefitContractCh);
		return benefitContractCh;
	}
	
	/**
	 * 保存测算预算表
	 */
	public void saveOrUpdateSummaryBudget(BenefitContractCh benefitContractCh, Map<String, Object> valueMap) {
		if (valueMap == null || valueMap.isEmpty()) {
			return;
		}

		String changeId = benefitContractCh.getChangeId();
		
		// 删除币别
		String delCurrencyIds = (String) valueMap.get("delCurrencyIds");
		if (StringUtils.isNotBlank(delCurrencyIds)) {
			for (String recordId : delCurrencyIds.split(",")) {
				this.benefitContractCurrencyChDao.deleteByProperty("recordId", recordId);
			}
		}
		
		List<BenefitContractCurrencyCh> benefitContractCurrencyChList;
		// 需要保存的销售合同预算币别
		String currencyListJson = (String) valueMap.get("currencyListJson");
		if (currencyListJson != null) {// 修改币别需要保存
			benefitContractCurrencyChList = new ArrayList<BenefitContractCurrencyCh>();
			List<BenefitContractCurrencyCh> currencyList = JsonUtil.toList(currencyListJson, BenefitContractCurrencyCh.class);
			BenefitContractCurrencyCh saveCurrency;
			
			int orderDisplay = 0;
			// 保存币别
			for (BenefitContractCurrencyCh currency : currencyList) {
				if (currency.getRecordId().startsWith("rowId_")) {// 多出的币别保存
					saveCurrency = new BenefitContractCurrencyCh(changeId, orderDisplay);
				} else {
					saveCurrency = this.benefitContractCurrencyChDao.get(currency.getRecordId());
				}
				saveCurrency.setCurrencyId(currency.getCurrencyId());
				saveCurrency.setCurrency(currency.getCurrency());
				if (StringUtils.isNotBlank(saveCurrency.getCurrencyId())) {
					saveCurrency.setRate(currency.getRate());
				} else {
					saveCurrency.setRate(0);
				}
				this.benefitContractCurrencyChDao.saveOrUpdate(saveCurrency);
				benefitContractCurrencyChList.add(saveCurrency);
				orderDisplay += 10;
			}
			// 重新给主表赋值币别集
			benefitContractCh.setCurrencyList(benefitContractCurrencyChList);
			benefitContractCh.setCurrencyNum(benefitContractCurrencyChList.size());
		} else {
			benefitContractCurrencyChList = this.getCurrencyListByProcessId(changeId);
		}

		// 获取汇率
		Map<String, Double> rateMap = new HashMap<String, Double>();
		double rate1, rate2, rate3, rate4, rate5, rate6, rate7, rate8, rate9;

		switchLoadCurrencyRate(benefitContractCh.getCurrencyNum(), benefitContractCurrencyChList, rateMap);
		double defaultRate = BenefitBudgetConstant.DEFAULT_VALUE;// 设置默认汇率
		rate1 = CommonUtil.parseDouble(String.valueOf(rateMap.get("rate1")), defaultRate);
		rate2 = CommonUtil.parseDouble(String.valueOf(rateMap.get("rate2")), defaultRate);
		rate3 = CommonUtil.parseDouble(String.valueOf(rateMap.get("rate3")), defaultRate);
		rate4 = CommonUtil.parseDouble(String.valueOf(rateMap.get("rate4")), defaultRate);
		rate5 = CommonUtil.parseDouble(String.valueOf(rateMap.get("rate5")), defaultRate);
		rate6 = CommonUtil.parseDouble(String.valueOf(rateMap.get("rate6")), defaultRate);
		rate7 = CommonUtil.parseDouble(String.valueOf(rateMap.get("rate7")), defaultRate);
		rate8 = CommonUtil.parseDouble(String.valueOf(rateMap.get("rate8")), defaultRate);
		rate9 = CommonUtil.parseDouble(String.valueOf(rateMap.get("rate9")), defaultRate);
				
		// 所有需要保存的预算集合（叶节点）
		String budgetListJson = (String) valueMap.get("budgetListJson");
		if (budgetListJson == null) {
			return;
		}
		// 前台传递的要保存属性组成对象便于赋值给要保存的预算项
		List<BenefitAmountBean> budgetList = JsonUtil.toList(budgetListJson, BenefitAmountBean.class);

		BenefitContractBudgetCh saveBudget;// 要保存的预算项
		BenefitContractBudgetCh saveBudgetParent;// 要保存预算的父级预算
		Map<String, BenefitContractBudgetCh> saveBudgetMap = new LinkedHashMap<String, BenefitContractBudgetCh>();
		Map<String, BenefitAmountAccumulativeBean> contractAmountMap = new LinkedHashMap<String, BenefitAmountAccumulativeBean>();

		// 遍历计算
		try {
			for (BenefitAmountBean amount : budgetList) {
//				System.out.println(amount.getProcessBudgetId()+"IDIIDI");
				saveBudget = this.benefitContractBudgetChDao.get(amount.getProcessBudgetId());
				// 根据币别汇率重置币别金额（没有该币别汇率清除对应币别值）、设置总金额
				amount.putAllCurrencyAmount(saveBudget.getBudgetName(), defaultRate, rate1, rate2, rate3, rate4, rate5, rate6, rate7, rate8, rate9);
				BeanUtils.copyProperties(saveBudget, amount);

				// 累加预算金额
				String budgetAmountKey = saveBudget.getParentBudgetId();
				if (budgetAmountKey != null) {// 取有下级预算项（无下级的直接计算）
					if (saveBudget.getLevel() == BenefitBudgetConstant.LEVEL_TWO) {// 二级预算项
						// 累加父级（一级）预算项金额
						accumulationBudgetAmount(budgetAmountKey, contractAmountMap, saveBudget);
					} else if (saveBudget.getLevel() == BenefitBudgetConstant.LEVEL_THREE) {// 三级预算项
						// 累加父级（二级）预算项金额
						accumulationBudgetAmount(budgetAmountKey, contractAmountMap, saveBudget);

						// 除了累加本级金额到二级还需要累加到一级(获取未拼装的一级预算项)
						saveBudgetParent = this.getBenefitContractBudgetCh(changeId, saveBudget.getParentBudgetId());
						budgetAmountKey = saveBudgetParent.getParentBudgetId();// 一级预算budgetId
						// 累加父级（一级）预算项金额
						accumulationBudgetAmount(budgetAmountKey, contractAmountMap, saveBudget);
					}
				}
//				System.out.println("预算项ID:"+saveBudget.getBudgetId());
				saveBudgetMap.put(saveBudget.getBudgetId(), saveBudget);
			}
		} catch (Exception e) {
			System.out.println("saveOrUpdateSummaryBudget copyProperties Error: " + e.getMessage());
		}

		// 添加非叶节点预算项并设置金额为下级金额合计,计算采购合同总金额和费用合计之和
		String _PURCHASE_TOTAL = BenefitBudgetItemConstant.SUMMARY_PURCHASE_TOTAL;
		String _EXPENSE_TOTAL = BenefitBudgetItemConstant.SUMMARY_EXPENSE_TOTAL;
		double sum = 0;
		try {
			for (Map.Entry<String, BenefitAmountAccumulativeBean> e : contractAmountMap.entrySet()) {
				// 获取父级预算项
				String parentBudgetId = e.getKey();
				saveBudgetParent = this.getBenefitContractBudgetCh(changeId, parentBudgetId);
				BeanUtils.copyProperties(saveBudgetParent, e.getValue());
				saveBudgetMap.put(saveBudgetParent.getBudgetId(), saveBudgetParent);
				if (saveBudgetParent.getBudgetId().equals(_PURCHASE_TOTAL) || saveBudgetParent.getBudgetId().equals(_EXPENSE_TOTAL)) {
					sum += saveBudgetParent.getTotalRmbAmount();
				}
			}
			// 添加采购合同总金额和费用合计之和
			contractAmountMap.put(_PURCHASE_TOTAL + _EXPENSE_TOTAL, new BenefitAmountAccumulativeBean(sum));
		} catch (Exception e) {
			System.out.println("saveOrUpdateSummaryBudget copyProperties Error: " + e.getMessage());
		}

		// 设置毛利等特殊预算项
		String _TRADE_TOTAL = BenefitBudgetItemConstant.SUMMARY_TRADE_TOTAL; // 销售合同总金额

		String _DRAWBACK = BenefitBudgetItemConstant.SUMMARY_EXPORT_DRAWBACK; // 应收出口退税
		String _PROJECT_FEE = BenefitBudgetItemConstant.SUMMARY_PROJECT_DOMESTIC_MANAGEMENT_FEE; // 项目国内管理费

		String _PROFIT = BenefitBudgetItemConstant.SUMMARY_GROSS_PROFIT; // 毛利
		String _PROFIT_RATE = BenefitBudgetItemConstant.SUMMARY_GROSS_PROFIT_RATE; // 毛利率
		String _LOSS = BenefitBudgetItemConstant.SUMMARY_EXPECTED_PROFIT_AND_LOSS; // 预计盈亏额
		String _PROFIT_MARGIN = BenefitBudgetItemConstant.SUMMARY_PROFIT_MARGIN; // 利润率

		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("changeId", changeId);
		params.put("budgetId", new String[] { _PROFIT, _PROFIT_RATE, _LOSS, _PROFIT_MARGIN });
		List<BenefitContractBudgetCh> specialBudgetList = this.benefitContractBudgetChDao.queryByParam(params, null, null);
		for (BenefitContractBudgetCh budget : specialBudgetList) {
			saveBudgetMap.put(budget.getBudgetId(), budget);
		}

		// 设置占比、毛利等
		int index = 0;
		BenefitContractBudgetCh reBudget;
		for (Map.Entry<String, BenefitContractBudgetCh> e : saveBudgetMap.entrySet()) {
			reBudget = e.getValue();
			if (reBudget.getOrderDisplay() == 0) {// 第一个预算（贸易销售合同金额）
				reBudget.setProportion(1.0);
			} else if (reBudget.getProportion() != BenefitBudgetConstant.DEFAULT_VALUE) {
				// 第一个和无比例项不设置（即第一项下级和第二三项及其下级）
				double total = 0;
				index = reBudget.getOrderDisplay() / BenefitBudgetConstant.ORDER_DISPLAY_ONE;
				if (index == 0) {// 第一项下级（该项折人民币合计/销售合同总金额）
					total = contractAmountMap.get(reBudget.getParentBudgetId()).getTotalRmbAmount();
				} else if (index == 1 || index == 2) {// 第二、三项及下级（该项折人民币合计/(采购合同总金额+费用合计)）
					total = contractAmountMap.get(_PURCHASE_TOTAL + _EXPENSE_TOTAL).getTotalRmbAmount();
				}
				if (total >= reBudget.getTotalRmbAmount() && total > 0) {
					reBudget.setProportion(reBudget.getTotalRmbAmount() / total);
				}
			} else {
				if (CommonUtil.trim(reBudget.getBudgetName()).equals("毛利")) {// 毛利
					// 毛利 = 销售合同总金额 + 应收出口退税 - 采购合同总金额 - 费用合计
					double profitAmount = 0;
					profitAmount = saveBudgetMap.get(_TRADE_TOTAL).getTotalRmbAmount();
					profitAmount = profitAmount + saveBudgetMap.get(_DRAWBACK).getTotalRmbAmount();
					profitAmount = profitAmount - saveBudgetMap.get(_PURCHASE_TOTAL).getTotalRmbAmount();
					profitAmount = profitAmount - saveBudgetMap.get(_EXPENSE_TOTAL).getTotalRmbAmount();
					reBudget.setTotalRmbAmount(profitAmount);
				} else if (CommonUtil.trim(reBudget.getBudgetName()).equals("毛利率")) {// 毛利率
					// 获取毛利
					double profitAmount = saveBudgetMap.get(_PROFIT).getTotalRmbAmount();
					double totalTradeAmount = saveBudgetMap.get(_TRADE_TOTAL).getTotalRmbAmount();
					if (profitAmount == 0) {
						profitAmount = totalTradeAmount;
						profitAmount = profitAmount + saveBudgetMap.get(_DRAWBACK).getTotalRmbAmount();
						profitAmount = profitAmount - saveBudgetMap.get(_PURCHASE_TOTAL).getTotalRmbAmount();
						profitAmount = profitAmount - saveBudgetMap.get(_EXPENSE_TOTAL).getTotalRmbAmount();
					}
					// 毛利率 = 毛利 / 销售合同总金额
					if (totalTradeAmount >= profitAmount && totalTradeAmount > 0) {
						reBudget.setTotalRmbAmount(profitAmount / totalTradeAmount);
					}
				} else if (CommonUtil.trim(reBudget.getBudgetName()).contains("预计盈亏额")) {// 预计盈亏额
					// 预计盈亏额 = 毛利 - 项目国内管理费
					double lossAmount = 0;
					// 获取毛利
					double profitAmount = saveBudgetMap.get(_PROFIT).getTotalRmbAmount();
					if (profitAmount == 0) {
						profitAmount = saveBudgetMap.get(_TRADE_TOTAL).getTotalRmbAmount();
						profitAmount = profitAmount + saveBudgetMap.get(_DRAWBACK).getTotalRmbAmount();
						profitAmount = profitAmount - saveBudgetMap.get(_PURCHASE_TOTAL).getTotalRmbAmount();
						profitAmount = profitAmount - saveBudgetMap.get(_EXPENSE_TOTAL).getTotalRmbAmount();
					}
					// 获取国内项目管理费
					double projectAmount = saveBudgetMap.get(_PROJECT_FEE).getTotalRmbAmount();
					lossAmount = profitAmount - projectAmount;
					reBudget.setTotalRmbAmount(lossAmount);
				} else if (CommonUtil.trim(reBudget.getBudgetName()).contains("利润率")) {// 利润率
					// 获取预计盈亏额
					double lossAmount = saveBudgetMap.get(_LOSS).getTotalRmbAmount();
					double totalTradeAmount = saveBudgetMap.get(_TRADE_TOTAL).getTotalRmbAmount();
					if (lossAmount == 0) {
						// 获取毛利 
						double profitAmount = saveBudgetMap.get(_PROFIT).getTotalRmbAmount();
						if (profitAmount == 0) {
							profitAmount = saveBudgetMap.get(_TRADE_TOTAL).getTotalRmbAmount();
							profitAmount = profitAmount + saveBudgetMap.get(_DRAWBACK).getTotalRmbAmount();
							profitAmount = profitAmount - saveBudgetMap.get(_PURCHASE_TOTAL).getTotalRmbAmount();
							profitAmount = profitAmount - saveBudgetMap.get(_EXPENSE_TOTAL).getTotalRmbAmount();
						}
						// 获取国内项目管理费
						double projectAmount = saveBudgetMap.get(_PROJECT_FEE).getTotalRmbAmount();
						lossAmount = profitAmount - projectAmount;
					}
					// 利润率 = 预计盈亏额 / 销售合同总金额
					if (totalTradeAmount >= lossAmount && totalTradeAmount > 0) {
						reBudget.setTotalRmbAmount(lossAmount / totalTradeAmount);
					}
				}
			}
			saveBudgetMap.put(reBudget.getBudgetId(), reBudget);
		}

		// 更新数据
		if (saveBudgetMap.size() > 0) {
			List<BenefitContractBudgetCh> saveBudgetList = new ArrayList<BenefitContractBudgetCh>(saveBudgetMap.values());
			this.benefitContractBudgetChDao.update(saveBudgetList);
		}
	}
	
	/**
	 * 根据主表和预算ID获取单个预算项
	 * @param changeId
	 * @param budgetId
	 * @return
	 */
	public BenefitContractBudgetCh getBenefitContractBudgetCh(String changeId, String budgetId) {
		return this.benefitContractBudgetChDao.queryUnique(changeId, budgetId);
	}
	
	/**
	 * 添加基础信息
	 * @return
	 */
	public BenefitContractCh loadBenefit(BenefitContractCh benefitContractCh, UserProfile user) {
		benefitContractCh.setCreatedBy(user.getName());
		benefitContractCh.setCreatedById(user.getPersonId());
		benefitContractCh.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		benefitContractCh.setModifiedBy(user.getName());
		benefitContractCh.setModifiedById(user.getPersonId());
		benefitContractCh.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			benefitContractCh.setDeptName(dept.getName());
			benefitContractCh.setDeptId(dept.getDeptId());
		}
		// 设置状态
		benefitContractCh.setSwfStatus(BenefitContractCh.DRAFT);
		return benefitContractCh;
	}
	/**
	 * 保存或更新预算表
	 * @param changeId
	 * @param contractAmountList
	 */
	public void saveOrUpdateBenefitAmountList(String changeId, List<BenefitContractBudgetCh> benefitBudgetChangeList) {
		for (BenefitContractBudgetCh amount : benefitBudgetChangeList) {
			amount.setChangeId(changeId);
			if (StringUtils.isBlank(amount.getBudgetId())) {
				this.benefitContractBudgetChDao.save(amount);
			} else {
				this.benefitContractBudgetChDao.update(amount);
			}
		}
	}

	public BenefitContractCh initBenefitContract(String changeId) {
		// TODO Auto-generated method stub
		return benefitContractChDao.get(changeId);
	}
	/**
	 * 获取合同预算变更的预算币别
	 * @param benefitContractCh
	 * @return
	 */
	public List<BenefitContractCurrencyCh> getBenefitContractCurrencyChList(BenefitContractCh benefitContractCh){
		return benefitContractCurrencyChDao.getBenefitContractCurrencyChList(benefitContractCh);
	}
	
	/**
	 * 获取合同预算币别
	 * @param jqGrid
	 * @param changeId
	 * @param parameters
	 * @return
	 */
	public List<BenefitContractCurrencyCh> getCurrencyGrid(JqGrid jqGrid, String changeId, Map<String, Object> parameters) {
			return this.benefitContractCurrencyChDao.findPage(jqGrid, parameters);
		}
	/**
	 * 累加父级预算项金额
	 * 
	 * @param budgetAmountKey
	 *            父级预算项ID
	 * @param contractAmountMap
	 *            预算金额集合
	 * @param budget
	 *            当前预算项
	 */
	public void accumulationBudgetAmount(String budgetAmountKey, Map<String, BenefitAmountAccumulativeBean> contractAmountMap,  BenefitContractBudgetCh budget) {
		// 金额累加类（set为累加值）
		BenefitAmountAccumulativeBean amountBean = null;
		try {
			if (contractAmountMap.containsKey(budgetAmountKey)) {
				amountBean = contractAmountMap.get(budgetAmountKey);
			} else {
				amountBean = new BenefitAmountAccumulativeBean();
			}
			BeanUtils.copyProperties(amountBean, budget);
		} catch (Exception e) {
			e.printStackTrace();
		}
		contractAmountMap.put(budgetAmountKey, amountBean);
	}

	public String checkBenefitChange(String contractId) {
		// TODO Auto-generated method stub
		return benefitContractChDao.checkBenefitChange(contractId);
	}

	public boolean checkBenefitChOk(String changeId) {
		// TODO Auto-generated method stub
		return benefitContractChDao.checkBenefitChOk(changeId);
	}
	/**
	 * 
	 * @param benefitContractCh
	 */
	public void completeExamAdjustPrjBudget(BenefitContractCh benefitContractCh){
		//根据变更合同预算表的主键获取变更效益预算表明细信息.
		List<BenefitContractBudgetCh> benefitContractBudgetChList = benefitContractBudgetChDao.findBy("changeId", benefitContractCh.getChangeId());
		String projectId = benefitContractCh.getProjectId();
		//是否发生了变更。
		boolean isAdujst = false;
		for(BenefitContractBudgetCh benefitContractBudgetCh:benefitContractBudgetChList){
			String benefitContractBudgetId = benefitContractBudgetCh.getPbOldId();
			//销售合同预算表的单条预算记录
			BenefitContractBudget  benefitContractBudget = benefitBudgetService.getBenefitContractBudget(benefitContractBudgetId);
			//变更中的预算金额
			double newTotalRmbAmount= benefitContractBudgetCh.getTotalRmbAmount();
			//变更前的预算金额
			double oldTotalRmbAmount= benefitContractBudget.getTotalRmbAmount();
			//差额
			double blanceRmbAmount= newTotalRmbAmount - oldTotalRmbAmount;
			if(blanceRmbAmount != 0){
				Person person = EIPService.getEmpService().getEmp(benefitContractCh.getCreatedById());
				Account account = EIPService.getAccountService().getDefaultAccount(person);
				UserProfile user = EIPService.getLogonService().getUserProfile(account);
				if(blanceRmbAmount > 0){
					//预算额增加
					TpcBudgetUtil.adjustBenfitBudget(user, benefitContractCh.getProjectId(), benefitContractBudgetCh.getBudgetId(), TpcBudgetUtil.TPC_AOS_ADD, blanceRmbAmount);
				}else{
					//预算额减少
					//负数时取绝对值.
					double  blanceRmbAmount_ABS= Math.abs(blanceRmbAmount);
					TpcBudgetUtil.adjustBenfitBudget(user, benefitContractCh.getProjectId(), benefitContractBudgetCh.getBudgetId(), TpcBudgetUtil.TPC_AOS_SUB, blanceRmbAmount_ABS);
				}
				isAdujst = true;
			}
		}
		//发生了变更才需要调整。
		if(isAdujst){
			benefitBudgetService.saveOrUpdateProportionAndSpecialBudget(projectId);
		}
	}
	
	/**
	 * 流程审批完成处理方法。
	 * @param user
	 * @param benefitContractCh
	 */
	public void completeExam(UserProfile user, BenefitContractCh benefitContractCh) {
		//变更合同预算表主键changeId
		String changeId = benefitContractCh.getChangeId();
		//原合同预算表的主键是processId
		String processId = benefitContractCh.getProcessId();
		BenefitContract benefitContract = benefitBudgetService.getBenefitContract(processId);
		// 复制变更合同预算表信息至原合同预算表
		try {
			//开始更新原合同预算表主表信息
			BeanUtils.copyProperties(benefitContract, benefitContractCh);
			this.benefitContractDao.update(benefitContract);
			//更新原合同预算表子表（币别汇率表）信息
			// 根据原合同预算表主键processId获取其币别表信息
			List<BenefitContractCurrency> currencyList = benefitContractCurrencyDao.findBy("processId", processId);
			// 变更合同预算表子表（币别汇率表）信息
			List<BenefitContractCurrencyCh> changeCurrencyList = benefitContractCurrencyChDao.findBy("changeId", changeId);
			int changeCurrencySize = changeCurrencyList.size();
			for (int i = 0; i < changeCurrencySize; i++) {
				String[] ignores = { "recordId", "changeId" };
				//获取币别的变更信息
				BenefitContractCurrencyCh changeCurrency = changeCurrencyList.get(i);
				//根据原币别主表的主键获取原币别信息
				BenefitContractCurrency currency = findByRecId(currencyList, changeCurrency.getRecordOldId());
				if (currency != null) {
					// 开始更新原预算表的币别表信息
					com.supporter.prj.pm.util.BeanUtils.copyProperties(changeCurrency, currency, ignores);
					benefitContractCurrencyDao.update(currency);
					// 将获取出来的币别表list中删掉已经copy完的这条信息
					currencyList.remove(currency);
				} else {
					// 新增
					currency = new BenefitContractCurrency();
					com.supporter.prj.pm.util.BeanUtils.copyProperties(changeCurrency, currency, ignores);
					currency.setRecordId(null);//处理主键用，配合copyProperties用
					currency.setProcessId(processId);
					benefitContractCurrencyDao.save(currency);
				}
			}
			//copy完了之后将相关已经copy完成的数据清掉
			if (currencyList.size() > 0) {
				benefitContractCurrencyDao.delete(currencyList);
			}
			//更新效益预算表
			//根据原合同预算表的主键获取原效益预算表信息
			List<BenefitContractBudget> budgetList = benefitContractBudgetDao.findBy("processId", processId);
			//根据变更合同预算表的主键获取变更效益预算表信息
			List<BenefitContractBudgetCh> changeBudgetList = benefitContractBudgetChDao.findBy("changeId", changeId);
			int changeBudgetSize = changeBudgetList.size();
			for (int i = 0; i < changeBudgetSize; i++) {
				String[] ignores = {"processBudgetId", "changeId"};
				BenefitContractBudgetCh changeBudget = changeBudgetList.get(i);
				BenefitContractBudget budget = findBySiteId(budgetList,changeBudget.getPbOldId());
				if(budget != null) {
					//更新
					com.supporter.prj.pm.util.BeanUtils.copyProperties(changeBudget, budget, ignores);
					benefitContractBudgetDao.update(budget);
					// 将获取出来的预算项list中删掉已经copy完的这条信息
					budgetList.remove(budget);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//根据原币别主表的主键获取原币别信息
	private BenefitContractCurrency findByRecId(List<BenefitContractCurrency> oldAmountList, String recordOldId) {
		if (StringUtils.isNotBlank(recordOldId)) {
		int len = oldAmountList.size();
		for (int i = 0; i < len; i++) {
			BenefitContractCurrency oldContent = oldAmountList.get(i);
			if (recordOldId.equals(oldContent.getRecordId())) {
				return oldContent;
			}
		}
		}
		return null;
	}
	
	private BenefitContractBudget findBySiteId(List<BenefitContractBudget> oldTermsList, String pbOldId) {
		if (StringUtils.isNotBlank(pbOldId)) {
			int len = oldTermsList.size();
			for (int i = 0; i < len; i++) {
				BenefitContractBudget oldSite = oldTermsList.get(i);
				if (pbOldId.equals(oldSite.getProcessBudgetId())) {
					return oldSite;
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据码表汇率计算折合美元金额
	 * @param changeId
	 * @return
	 */
	public double getAmountInUSD(String changeId) {
		BigDecimal amountInUSD = new BigDecimal(0);
		if (StringUtils.isNotBlank(changeId)) {
			double totalRmbAmount = 0;
			List<BenefitContractBudgetCh> budgetChs = this.benefitContractBudgetChDao.queryByProcessId(changeId);
			if (budgetChs != null) {
				BenefitContractBudgetCh budgetCh = budgetChs.get(0);
				//获取折合人民币总金额
				totalRmbAmount = budgetCh.getTotalRmbAmount();
			}
			if (totalRmbAmount > 0) {//如果折合人民币总金额大于0
				//获取美元码表项
				IComCodeTableItem codeItem = EIPService.getComCodeTableService().getCodetableItem("C-002");
				//获取美元币别汇率
				String UsdRate = codeItem.getExtField2();
				//计算折合美元金额amountInUSD
				amountInUSD = new BigDecimal(totalRmbAmount).divide(new BigDecimal(UsdRate));
			}
		}
		return amountInUSD.doubleValue();
	}
	/**
	 * 获取变更后项目毛利
	 * @param changeId
	 * @return
	 */
	public void setProfit (BenefitContractCh contractCh) {
		double XMML = 0;//项目毛利
		double YHTML = 0;//原合同毛利
		double NEWML = 0;//变更毛利
		//通过projectId获取项目预算总表
		BenefitBudget budget = this.benefitBudgetDao.getBenefitBudgetMaoliByProjectId(contractCh.getProjectId());
		if (budget != null) {
			XMML = budget.getTotalBudgetAmount();
		}
		//原合同毛利
		BenefitContractBudget contractBudget=this.benefitContractBudgetDao.getBenefitBudgetMaoliByProcessId(contractCh.getProcessId());
		if (contractBudget != null) {
			YHTML = contractBudget.getTotalRmbAmount();
		}
		//现在毛利
		BenefitContractBudgetCh contractBudgetCh=this.benefitContractBudgetChDao.getBenefitBudgetMaoliByChangeId(contractCh.getChangeId());
		if (contractBudgetCh != null) {
			NEWML = contractBudgetCh.getTotalRmbAmount();
		}
		//原项目毛利-原合同毛利+现在毛利=变更毛利
		double profit = new BigDecimal(XMML).subtract(new BigDecimal(YHTML)).add(new BigDecimal(NEWML)).doubleValue();
		contractCh.setProfit(profit);//变更毛利
		this.update(contractCh);
	}
}
