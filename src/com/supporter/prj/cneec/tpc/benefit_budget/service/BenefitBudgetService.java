package com.supporter.prj.cneec.tpc.benefit_budget.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitBudgetUtil;
import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNote;
import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNoteBudget;
import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNoteCurrency;
import com.supporter.prj.cneec.tpc.benefit_note.service.BenefitNoteService;
import com.supporter.prj.cneec.tpc.util.ColModelUtil;
import com.supporter.prj.cneec.tpc.util.ExportUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

/**
 * @Title: BenefitBudgetService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2018-06-06
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class BenefitBudgetService {

	@Autowired
	private VBenefitProjectDao benefitProjectDao;
	@Autowired
	private BenefitBudgetDao benefitBudgetDao;
	@Autowired
	private BenefitContractDao benefitContractDao;
	@Autowired
	private BenefitContractCurrencyDao benefitContractCurrencyDao;
	@Autowired
	private BenefitContractBudgetDao benefitContractBudgetDao;

	@Autowired
	private BenefitNoteService benefitNoteService;

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
	public List<BenefitContract> getBenefitContractGrid(JqGrid jqGrid, Map<String, Object> parameters) {
		return this.benefitContractDao.findPage(jqGrid, parameters);
	}

	/**
	 * 单合同效益预算金额Grid
	 * @param jqGrid
	 * @param parameters
	 * @return
	 */
	public List<BenefitContractBudget> getBenefitContractBudgetGrid(JqGrid jqGrid, Map<String, Object> parameters) {
		return this.benefitContractBudgetDao.findPage(jqGrid, parameters);
	}

	/**
	 * 获取汇总预算表
	 * @param jqGrid
	 * @param projectId
	 * @param parameters
	 * @return
	 */
	public List<BenefitBudget> getBenefitAssembleGrid(JqGrid jqGrid, Map<String, Object> parameters) {
		return this.benefitBudgetDao.findPage(jqGrid, parameters);
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
		List<BenefitBudget> list = this.benefitBudgetDao.queryBy("projectId", projectId, false, "orderDisplay", true);
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
	public String loadBudgetAssembleExtendField(String projectId, List<BenefitBudget> assembleList, boolean allBudget) throws Exception {
		List<Map<String, Object>> assembleBeanList = new ArrayList<Map<String, Object>>();
		// 定义可扩展属性Bean，动态添加属性，用作前台JqGrid向右扩展展示
		Map<String, Object> bean = null;
		for (BenefitBudget assemble : assembleList) {
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
		List<BenefitContract> processContractList = this.benefitContractDao.queryBy("projectId", projectId, false, "createdDate", true);
		List<BenefitContractCurrency> contractCurrencyList = null;
		int currencySize = 0;
		int i = 1;
		int width = BenefitBudgetConstant.TITLE_WIDTH;
		for (BenefitContract contract : processContractList) {
			contractCurrencyList = this.getCurrencyListByProcessId(contract.getProcessId());
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
		List<BenefitBudget> list = this.benefitBudgetDao.queryBy("projectId", projectId, false, "orderDisplay", true);
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
	public void switchLoadAssembleExtendField(int x, int index, Map<String, Object> bean, BenefitContractBudget budget) {
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
	 * 装填扩展属性
	 * @param assembleList
	 * @param assembleBeanList
	 * @return
	 * @throws Exception 
	 */
	public String loadContractAssembleExtendField(String projectId, List<BenefitBudget> assembleList) throws Exception {
		List<Map<String, Object>> assembleBeanList = new ArrayList<Map<String, Object>>();
		// 定义可扩展属性Bean，动态添加属性，用作前台JqGrid向右扩展展示
		Map<String, Object> bean = null;
		// 获取合同
		List<BenefitContract> contractList = this.benefitContractDao.queryBy("projectId", projectId, false, "createdDate", true);
		// 遍历预算项给预算项添加扩展属性及设置值
		for (BenefitBudget assemble : assembleList) {
			// 实例化扩展属性对象
			bean = BenefitBudgetUtil.getBaseAssembleMap(assemble);

			// 给基础属性赋值
			bean.put("totalBudgetAmountDisplay", assemble.getTotalBudgetAmountDisplay());
			bean.put("totalProportionDisplay", assemble.getTotalProportionDisplay());

			// 给合同属性赋值
			int x = 1;
			for (BenefitContract contract : contractList) {
				// 获取该预算下该合同的预算金额
				BenefitContractBudget contractBudget = this.getBenefitContractBudget(contract.getProcessId(), assemble.getBudgetId());

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
	public BenefitBudget getBenefitBudget(String projectId, String budgetId) {
		budgetId = budgetId == null ? BenefitBudgetItemConstant.SUMMARY_TRADE_TOTAL : budgetId;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		params.put("budgetId", budgetId);
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		orders.put("orderDisplay", true);
		List<BenefitBudget> itemList = this.benefitBudgetDao.queryByParam(params, null, orders);
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
	public List<BenefitBudget> getBenefitBudgetList(String projectId) {
		return this.benefitBudgetDao.queryBy("projectId", projectId, false, "orderDisplay", true);
	}

	/**
	 * 获取项目效益预算表单个合同对象
	 * @param processId
	 * @return
	 */
	public BenefitContract getBenefitContract(String processId) {
		BenefitContract contract = this.benefitContractDao.get(processId);
		if (contract != null) {
			contract.setCurrencyList(this.getCurrencyListByProcessId(processId));
		}
		return contract;
	}

	/**
	 * 根据属性获取唯一过程合同对象（评审合同ID，销售合同ID）
	 * @param properName
	 * @param propValue
	 * @return
	 */
	public BenefitContract findBenefitContractBy(String properName, String propValue) {
		return this.benefitContractDao.findUniqueResult(properName, propValue);
	}

	/**
	 * 根据编号获取唯一对象
	 * @param benefitNo
	 * @return
	 */
	public BenefitContract getByBenefitNo(String benefitNo) {
		return this.benefitContractDao.findUniqueResult("benefitNo", benefitNo);
	}

	/**
	 * 根据项目ID获取合同集合
	 * @param projectId
	 * @return
	 */
	public List<BenefitContract> getBenefitContractList(String projectId) {
		return this.benefitContractDao.queryByProjectId(projectId);
	}

	/**
	 * 根据主表ID,币别ID获取币别项
	 * 
	 * @param processId
	 * @param currencyId
	 * @return
	 */
	public BenefitContractCurrency queryUniqueCurrency(String processId, String currencyId) {
		return this.benefitContractCurrencyDao.queryUnique(processId, currencyId);
	}

	/**
	 * 根据主表ID获取币别集合
	 * 
	 * @param processId
	 * @return
	 */
	public List<BenefitContractCurrency> getCurrencyListByProcessId(String processId) {
		return this.benefitContractCurrencyDao.queryByProcessId(processId);
	}

	/**
	 * 获取项目效益预算表单个合同预算对象
	 * @param processBudgetId
	 * @return
	 */
	public BenefitContractBudget getBenefitContractBudget(String processBudgetId) {
		return this.benefitContractBudgetDao.get(processBudgetId);
	}

	/**
	 * 获取项目效益预算表单个合同预算对象
	 *
	 * @param processId
	 * @param budgetId
	 * @return
	 */
	public BenefitContractBudget getBenefitContractBudget(String processId, String budgetId) {
		return this.benefitContractBudgetDao.queryUnique(processId, budgetId);
	}

	/**
	 * 根据过程合同ID获取所有预算项
	 * @param processId
	 * @return
	 */
	public List<BenefitContractBudget> getBenefitContractBudgetList(String processId) {
		return this.benefitContractBudgetDao.queryByProcessId(processId);
	}

	/**
	 * 合同评审完成生成项目预算
	 * @param noteId
	 */
	public void createBenefitByBenefitNote(String noteId) {
		BenefitNote benefitNote = this.benefitNoteService.get(noteId);
		if (benefitNote != null) {
			String projectId = benefitNote.getProjectId();
			String projectName = benefitNote.getProjectName();

			// 币别集
			List<BenefitNoteCurrency> noteCurrencyList = this.benefitNoteService.getCurrencyListByNoteId(noteId);

			// 定义要保存的合同，合同预算，项目预算
			BenefitContract contract;
			BenefitContractCurrency contractCurrency;
			BenefitContractBudget contractBudget;
			BenefitBudget budget;
			String processId;
			// 获取预算信息
			List<BenefitNoteBudget> noteBudgetList1 = this.benefitNoteService.getBudgetListByNoteId(noteId);
			try {
				/** 生成过程合同 **/
				clearBenefitContract(benefitNote.getInforId());//先清除已有的过程合同预算，以免主键冲突无法插入新的记录
				contract = new BenefitContract(benefitNote.getInforId());
				BeanUtils.copyProperties(contract, benefitNote);
				this.benefitContractDao.save(contract);
				processId = contract.getProcessId();
				/** 生成过程合同币别集 **/
				for (BenefitNoteCurrency noteCurrency : noteCurrencyList) {
					contractCurrency = new BenefitContractCurrency();
					BeanUtils.copyProperties(contractCurrency, noteCurrency);
					contractCurrency.setRecordId(null);
					contractCurrency.setProcessId(processId);
					this.benefitContractCurrencyDao.save(contractCurrency);
				}
				for (BenefitNoteBudget noteBudget : noteBudgetList1) {
					/** 生成过程合同预算 **/
					contractBudget = new BenefitContractBudget();
					BeanUtils.copyProperties(contractBudget, noteBudget);
					contractBudget.setProcessId(processId);
					this.benefitContractBudgetDao.save(contractBudget);

					/** 生成项目预算项 **/
					String budgetId = noteBudget.getBudgetId();
					// 获取项目预算项（有则不直接获取）
					budget = this.getBenefitBudget(projectId, budgetId);
					if (budget == null) {
						// 生成预算项表
						budget = new BenefitBudget(projectId, projectName);
						BeanUtils.copyProperties(budget, noteBudget);
					}

					// 合同收入合同支出赋值预算
					if (!BenefitBudgetUtil.isSpecialBudget(noteBudget.getBudgetName())) {
						// 总预算及可用预算增加
						budget.setAvailableBudgetAmount(budget.getAvailableBudgetAmount() + noteBudget.getTotalRmbAmount());
					} else {
						budget.setAvailableBudgetAmount(BenefitBudgetConstant.DEFAULT_VALUE);
						budget.setOnWayAmount(BenefitBudgetConstant.DEFAULT_VALUE);
						budget.setExecuteAmount(BenefitBudgetConstant.DEFAULT_VALUE);
					}
					budget.setTotalBudgetAmount(budget.getTotalBudgetAmount() + noteBudget.getTotalRmbAmount());
					this.benefitBudgetDao.saveOrUpdate(budget);
				}
			} catch (Exception e) {
				System.out.println("createBenefitByBenefitNote Error: " + e);
			}
			// 计算特殊预算项及占比
			saveOrUpdateProportionAndSpecialBudget(projectId);
		}
	}
	
	/**
	 * 清理已有的过程合同预算
	 * @param processId
	 */
	public void clearBenefitContract(String processId) {
		if (StringUtils.isNotBlank(processId)) {
			BenefitContract contract = this.getBenefitContract(processId);
			if (contract != null) {
				//先删除过程合同预算项
				List<BenefitContractBudget> budget = this.getBenefitContractBudgetList(processId);
				if (budget != null) this.benefitContractBudgetDao.delete(budget);
				//再删除过程合同币别
				List<BenefitContractCurrency> currency = this.benefitContractCurrencyDao.queryByProcessId(processId);
				if (currency != null) this.benefitContractCurrencyDao.delete(currency);
				//最后删除过程合同预算
				this.benefitContractDao.delete(contract);
			}
		}
	}

	/**
	 * 设置特殊预算项及占比
	 * @param projectId
	 */
	public void saveOrUpdateProportionAndSpecialBudget(String projectId) {
		Map<String, BenefitBudget> budgetMap = new LinkedHashMap<String, BenefitBudget>();
		Map<String, Double> totalAmountMap = new LinkedHashMap<String, Double>();
		List<BenefitBudget> budgetList = this.benefitBudgetDao.queryBy("projectId", projectId, false, "orderDisplay", true);
		// 添加非叶节点预算项并设置金额为下级金额合计,计算采购合同总金额和费用合计之和
		String _PURCHASE_TOTAL = BenefitBudgetItemConstant.SUMMARY_PURCHASE_TOTAL;
		String _EXPENSE_TOTAL = BenefitBudgetItemConstant.SUMMARY_EXPENSE_TOTAL;
		double sum12 = 0;
		for (BenefitBudget budget : budgetList) {
			budgetMap.put(budget.getBudgetId(), budget);
			if (budget.getLevel() == BenefitBudgetConstant.LEVEL_ONE) {
				totalAmountMap.put(budget.getBudgetId(), budget.getTotalBudgetAmount());
			}
			if (budget.getBudgetId().equals(_PURCHASE_TOTAL) || budget.getBudgetId().equals(_EXPENSE_TOTAL)) {
				sum12 += budget.getTotalBudgetAmount();
			}
		}
		// 添加采购合同总金额和费用合计之和
		totalAmountMap.put(_PURCHASE_TOTAL + _EXPENSE_TOTAL, sum12);
		if (!budgetMap.isEmpty()) {
			// 设置毛利等预算项
			String _TRADE_TOTAL = BenefitBudgetItemConstant.SUMMARY_TRADE_TOTAL; // 销售合同总金额

			String _DRAWBACK = BenefitBudgetItemConstant.SUMMARY_EXPORT_DRAWBACK; // 应收出口退税
			String _PROJECT_FEE = BenefitBudgetItemConstant.SUMMARY_PROJECT_DOMESTIC_MANAGEMENT_FEE; // 项目国内管理费

			String _PROFIT = BenefitBudgetItemConstant.SUMMARY_GROSS_PROFIT; // 毛利
			//String _PROFIT_RATE = BenefitBudgetItemConstant.SUMMARY_GROSS_PROFIT_RATE; // 毛利率
			String _LOSS = BenefitBudgetItemConstant.SUMMARY_EXPECTED_PROFIT_AND_LOSS; // 预计盈亏额
			//String _PROFIT_MARGIN = BenefitBudgetItemConstant.SUMMARY_PROFIT_MARGIN; // 利润率

			// 设置占比、毛利等
			int index = 0;
			BenefitBudget reBudget;
			for (Map.Entry<String, BenefitBudget> e : budgetMap.entrySet()) {
				reBudget = e.getValue();
				if (reBudget.getOrderDisplay() == 0) {// 第一个
					reBudget.setTotalProportion(1.0);
				} else if (reBudget.getTotalProportion() != BenefitBudgetConstant.DEFAULT_VALUE) {
					// 第一个和无比例项不设置（即第一项下级和第二三项及其下级）
					double total = 0;
					index = reBudget.getOrderDisplay() / BenefitBudgetConstant.ORDER_DISPLAY_ONE;
					if (index == 0) {// 第一项下级（该项折人民币合计/销售合同总金额）
						total = totalAmountMap.get(reBudget.getParentBudgetId());
					} else if (index == 1 || index == 2) {// 第二、三项及下级（该项折人民币合计/(采购合同总金额+费用合计)）
						total = totalAmountMap.get(_PURCHASE_TOTAL + _EXPENSE_TOTAL);
					}
					if (total >= reBudget.getTotalBudgetAmount() && total > 0) {
						reBudget.setTotalProportion(reBudget.getTotalBudgetAmount() / total);
					}
				} else {
					if (CommonUtil.trim(reBudget.getBudgetName()).equals("毛利")) {// 毛利
						// 毛利 = 销售合同总金额 + 应收出口退税 - 采购合同总金额 - 费用合计
						double profitAmount = 0;
						profitAmount = totalAmountMap.get(_TRADE_TOTAL);
						profitAmount = profitAmount + totalAmountMap.get(_DRAWBACK);
						profitAmount = profitAmount - totalAmountMap.get(_PURCHASE_TOTAL);
						profitAmount = profitAmount - totalAmountMap.get(_EXPENSE_TOTAL);
						reBudget.setTotalBudgetAmount(profitAmount);
					} else if (CommonUtil.trim(reBudget.getBudgetName()).equals("毛利率")) {// 毛利率
						// 获取毛利
						double profitAmount = totalAmountMap.get(_PROFIT);
						double totalTradeAmount = totalAmountMap.get(_TRADE_TOTAL);
						if (profitAmount == 0) {
							profitAmount = totalTradeAmount;
							profitAmount = profitAmount + totalAmountMap.get(_DRAWBACK);
							profitAmount = profitAmount - totalAmountMap.get(_PURCHASE_TOTAL);
							profitAmount = profitAmount - totalAmountMap.get(_EXPENSE_TOTAL);
						}
						// 毛利率 = 毛利 / 销售合同总金额
						if (totalTradeAmount >= profitAmount && totalTradeAmount > 0) {
							reBudget.setTotalBudgetAmount(profitAmount / totalTradeAmount);
						}
					} else if (CommonUtil.trim(reBudget.getBudgetName()).contains("预计盈亏额")) {// 预计盈亏额
						// 预计盈亏额 = 毛利 - 项目国内管理费
						double lossAmount = 0;
						// 获取毛利
						double profitAmount = totalAmountMap.get(_PROFIT);
						if (profitAmount == 0) {
							profitAmount = totalAmountMap.get(_TRADE_TOTAL);
							profitAmount = profitAmount + totalAmountMap.get(_DRAWBACK);
							profitAmount = profitAmount - totalAmountMap.get(_PURCHASE_TOTAL);
							profitAmount = profitAmount - totalAmountMap.get(_EXPENSE_TOTAL);
						}
						// 获取国内项目管理费
						double projectAmount = totalAmountMap.get(_PROJECT_FEE);
						lossAmount = profitAmount - projectAmount;
						reBudget.setTotalBudgetAmount(lossAmount);
					} else if (CommonUtil.trim(reBudget.getBudgetName()).contains("利润率")) {// 利润率
						// 获取预计盈亏额
						double lossAmount = totalAmountMap.get(_LOSS);
						double totalTradeAmount = totalAmountMap.get(_TRADE_TOTAL);
						if (lossAmount == 0) {
							// 获取毛利
							double profitAmount = totalAmountMap.get(_PROFIT);
							if (profitAmount == 0) {
								profitAmount = totalAmountMap.get(_TRADE_TOTAL);
								profitAmount = profitAmount + totalAmountMap.get(_DRAWBACK);
								profitAmount = profitAmount - totalAmountMap.get(_PURCHASE_TOTAL);
								profitAmount = profitAmount - totalAmountMap.get(_EXPENSE_TOTAL);
							}
							// 获取国内项目管理费
							double projectAmount = totalAmountMap.get(_PROJECT_FEE);
							lossAmount = profitAmount - projectAmount;
						}
						// 利润率 = 预计盈亏额 / 销售合同总金额
						if (totalTradeAmount >= lossAmount && totalTradeAmount > 0) {
							reBudget.setTotalBudgetAmount(lossAmount / totalTradeAmount);
						}
					}
				}
				budgetMap.put(reBudget.getBudgetId(), reBudget);
			}
			List<BenefitBudget> saveBudgetList = new ArrayList<BenefitBudget>(budgetMap.values());
			this.benefitBudgetDao.update(saveBudgetList);
		}
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
		BenefitContract benefitContract = this.getByBenefitNo(benefitNo);
		if (benefitContract != null) {
			benefitContract.setContractId(contractId);
			benefitContract.setContractNo(contractNo);
			benefitContract.setContractName(contractName);
			this.updateBenefitContract(benefitContract);
		}
	}

	public void updateBenefitContract(BenefitContract benefitContract) {
		this.benefitContractDao.update(benefitContract);
	}

	/**
	 * 预算修改
	 */
	public void updateBenefitBudget(BenefitBudget budget) {
		this.benefitBudgetDao.update(budget);
	}

	/**
	 * 获取项目效益预算汇总表
	 * 
	 * @param projectId
	 * @param downFileName
	 * @param allBudget
	 * @param user
	 * @return
	 */
	public Workbook getProjectWorkbook(String projectId, String downFileName, boolean allBudget, UserProfile user) {
		String suffix = downFileName.substring(downFileName.lastIndexOf("."));
		String filePath = BenefitBudgetConstant.BENEFIT_BUDGET_EXCEL_PATH;
		filePath += BenefitBudgetConstant.BENEFIT_BUDGET_TEMP0 + suffix;
		Workbook wb = null;
		try {
			// 获取Excel模板
			wb = ExportUtil.getWorkbook(filePath, suffix);
			Sheet sheet = wb.getSheetAt(0);// 第一个工作表

			// 表头单元格样式
			CellStyle titleStyle = ExportUtil.getTitleCellStyle(wb);

			// 获取预算对象集合
			List<BenefitBudget> budgetList = this.benefitBudgetDao.queryBy("projectId", projectId, false, "orderDisplay", true);

			// 定义起始位置
			int row_index = 0;// 起始行
			int col_index = 0;// 起始列

			// 设置序号合并单元格
			sheet.addMergedRegion(new CellRangeAddress(row_index, (row_index + 1), col_index, col_index));// 开始行，结束行，开始列，结束列
			ExportUtil.setTitleCell(sheet, true, row_index, 0, BenefitBudgetConstant.HEAD_TITLE_XH, titleStyle);

			// 设置预算说明合并单元格
			sheet.addMergedRegion(new CellRangeAddress(row_index, (row_index + 1), (col_index + 1), (col_index + 1)));
			ExportUtil.setTitleCell(sheet, true, row_index, 1, BenefitBudgetConstant.HEAD_TITLE_YS, titleStyle);

			int index = col_index + 2;// 预算金额单元格起始列

			if (allBudget) {
				// 设置可用预算单元格内容及样式
				sheet.addMergedRegion(new CellRangeAddress(row_index, (row_index + 1), index, index));
				ExportUtil.setTitleCell(sheet, true, row_index, index, BenefitBudgetConstant.HEAD_BUDGET_TITLE_KY, titleStyle);

				// 设置在途预算单元格内容及样式
				sheet.addMergedRegion(new CellRangeAddress(row_index, (row_index + 1), (index + 1), (index + 1)));
				ExportUtil.setTitleCell(sheet, true, row_index, (index + 1), BenefitBudgetConstant.HEAD_BUDGET_TITLE_ZT, titleStyle);

				// 设置执行预算单元格内容及样式
				sheet.addMergedRegion(new CellRangeAddress(row_index, (row_index + 1), (index + 2), (index + 2)));
				ExportUtil.setTitleCell(sheet, true, row_index, (index + 2), BenefitBudgetConstant.HEAD_BUDGET_TITLE_ZX, titleStyle);

				// 装填预算金额数据
				setBudgetAmountCellValue(wb, sheet, budgetList);
				index += 3;
			}

			// 设置合计合并单元格
			CellRangeAddress region = new CellRangeAddress(row_index, row_index, index, (index + 1));
			sheet.addMergedRegion(region);

			// 设置标题：项目累计、折人民币合计、占比
			ExportUtil.setTitleCell(sheet, true, row_index, index, BenefitBudgetConstant.HEAD_TITLE_XMHJ, titleStyle);
			ExportUtil.setTitleCell(sheet, false, (row_index + 1), index, BenefitBudgetConstant.HEAD_TITLE_ZH, titleStyle);
			ExportUtil.setTitleCell(sheet, false, (row_index + 1), (index + 1), BenefitBudgetConstant.HEAD_TITLE_ZB, titleStyle);

			// 装填合计
			setTotalBudgetCellValue(wb, sheet, region, budgetList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

	/**
	 * 装填预算金额数据
	 */
	public void setBudgetAmountCellValue(Workbook wb, Sheet sheet, List<BenefitBudget> budgetList) {
		// 定义单元格样式
		CellStyle centerCell = ExportUtil.getSimpleCellStyle(wb);
		centerCell.setAlignment(CellStyle.ALIGN_CENTER); // 居中

		CellStyle leftCell = ExportUtil.getSimpleCellStyle(wb);
		leftCell.setAlignment(CellStyle.ALIGN_LEFT); // 居左

		CellStyle rightCell = ExportUtil.getSimpleCellStyle(wb);
		rightCell.setAlignment(CellStyle.ALIGN_RIGHT); // 居右

		CellStyle numericCell = ExportUtil.getNumericCellStyle(wb, null);

		if (budgetList != null && budgetList.size() > 0) {
			int row_index = 2;// 从第3行开始
			int col_start = 2;// 从第3列开始

			// 获取预算集合
			String serialNumber, budgetName;
			for (BenefitBudget budget : budgetList) {
				if (sheet.getRow(row_index) == null || sheet.getRow(row_index).getCell(0) == null) {
					// 第一列,序号
					serialNumber = CommonUtil.trim(budget.getSerialNumber()).replaceAll("&ensp;", " ");
					ExportUtil.setValueByRowAndCol(sheet, row_index, 0, serialNumber, centerCell);

					// 第二列,预算说明
					budgetName = budget.getBudgetName();
					if (budget.getLevel() == BenefitBudgetConstant.LEVEL_TWO) {
						budgetName = "  " + budgetName;
					} else if (budget.getLevel() == BenefitBudgetConstant.LEVEL_THREE) {
						budgetName = "    " + budgetName;
					}
					ExportUtil.setValueByRowAndCol(sheet, row_index, 1, budgetName, leftCell);
				}

				// 第col_start列,可用金额
				if (budget.getAvailableBudgetAmount() != BenefitBudgetConstant.DEFAULT_VALUE) {
					ExportUtil.setValueByRowAndCol(sheet, row_index, col_start, budget.getAvailableBudgetAmount(), numericCell);
				} else {
					ExportUtil.setValueByRowAndCol(sheet, row_index, col_start, budget.getAvailableBudgetAmountDesc(), centerCell);
				}

				// 第col_start+1列,在途金额
				if (budget.getOnWayAmount() != BenefitBudgetConstant.DEFAULT_VALUE) {
					ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 1), budget.getOnWayAmount(), numericCell);
				} else {
					ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 1), budget.getOnWayAmountDesc(), centerCell);
				}

				// 第col_start+2列,执行预算
				if (budget.getExecuteAmount() != BenefitBudgetConstant.DEFAULT_VALUE) {
					ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 2), budget.getExecuteAmount(), numericCell);
				} else {
					ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 2), budget.getExecuteAmountDesc(), centerCell);
				}
				row_index++;
			}
		}
	}

	/**
	 * 装填项目合计预算数据
	 */
	public void setTotalBudgetCellValue(Workbook wb, Sheet sheet, CellRangeAddress region, List<BenefitBudget> budgetList) {
		// 定义单元格样式
		CellStyle centerCell = ExportUtil.getSimpleCellStyle(wb);
		centerCell.setAlignment(CellStyle.ALIGN_CENTER); // 居中

		CellStyle leftCell = ExportUtil.getSimpleCellStyle(wb);
		leftCell.setAlignment(CellStyle.ALIGN_LEFT); // 居左

		CellStyle rightCell = ExportUtil.getSimpleCellStyle(wb);
		rightCell.setAlignment(CellStyle.ALIGN_RIGHT); // 居右

		CellStyle numericCell = ExportUtil.getNumericCellStyle(wb, null);

		// 获取预算金额并遍历
		if (budgetList != null && budgetList.size() > 0) {
			int row_index = 2;// 从第3行开始
			int col_start = region.getFirstColumn();
			int col_end = region.getLastColumn();

			String serialNumber, budgetName;
			for (BenefitBudget budget : budgetList) {
				if (sheet.getRow(row_index) == null || sheet.getRow(row_index).getCell(0) == null) {
					// 第一列,序号
					serialNumber = CommonUtil.trim(budget.getSerialNumber()).replaceAll("&ensp;", " ");
					ExportUtil.setValueByRowAndCol(sheet, row_index, 0, serialNumber, centerCell);

					// 第二列,预算说明
					budgetName = budget.getBudgetName();
					if (budget.getLevel() == BenefitBudgetConstant.LEVEL_TWO) {
						budgetName = "  " + budgetName;
					} else if (budget.getLevel() == BenefitBudgetConstant.LEVEL_THREE) {
						budgetName = "    " + budgetName;
					}
					ExportUtil.setValueByRowAndCol(sheet, row_index, 1, budgetName, leftCell);
				}

				// 第1列,预算折人民币合计
				ExportUtil.setValueByRowAndCol(sheet, row_index, col_start, budget.getTotalBudgetAmount(), numericCell);

				// 第2列,占比
				if (budget.getTotalProportion() != BenefitBudgetConstant.DEFAULT_VALUE) {
					ExportUtil.setValueByRowAndCol(sheet, row_index, col_end, budget.getTotalProportionDesc(), rightCell);
				} else {
					ExportUtil.setValueByRowAndCol(sheet, row_index, col_end, budget.getTotalProportionDesc(), centerCell);
				}
				row_index++;
			}
		}
	}

	/**
	 * 获取项目效益预算过程表
	 * 
	 * @param projectId
	 * @param downFileName
	 * @param user
	 * @return
	 */
	public Workbook getProcessWorkbook(String projectId, String downFileName, UserProfile user) {
		String suffix = downFileName.substring(downFileName.lastIndexOf("."));
		String filePath = BenefitBudgetConstant.BENEFIT_BUDGET_EXCEL_PATH;
		filePath += BenefitBudgetConstant.BENEFIT_PROCESS_TEMP0 + suffix;
		Workbook wb = null;
		try {
			// 获取Excel模板
			wb = ExportUtil.getWorkbook(filePath, suffix);
			Sheet sheet = wb.getSheetAt(0);// 第一个工作表

			// 获取过程表合同对象集合
			List<BenefitContract> list = this.benefitContractDao.queryBy("projectId", projectId, false, "createdDate", true);

			// 表头单元格样式
			CellStyle titleStyle = ExportUtil.getTitleCellStyle(wb);

			// 定义起始位置
			int row_index = 0;
			int col_index = 0;

			// 设置序号合并单元格
			sheet.addMergedRegion(new CellRangeAddress(row_index, (row_index + 1), col_index, col_index));// 开始行，结束行，开始列，结束列
			ExportUtil.setTitleCell(sheet, true, row_index, 0, BenefitBudgetConstant.HEAD_TITLE_XH, titleStyle);

			// 设置预算说明合并单元格
			sheet.addMergedRegion(new CellRangeAddress(row_index, (row_index + 1), (col_index + 1), (col_index + 1)));
			ExportUtil.setTitleCell(sheet, true, row_index, 1, BenefitBudgetConstant.HEAD_TITLE_YS, titleStyle);

			// 设置合同名称合并单元格
			CellRangeAddress region;

			int index = col_index + 2;// 合同单元格起始列
			List<BenefitContractCurrency> currencyList = null;
			for (BenefitContract contract : list) {
				int count = contract.getCurrencyNum() + 1;
				// 设置合同名称合并单元格
				region = new CellRangeAddress(row_index, row_index, index, (index + count));
				sheet.addMergedRegion(region);
				ExportUtil.setTitleCell(sheet, true, row_index, index, contract.getContractName(), titleStyle);

				// 设置币别单元格内容及样式
				currencyList = this.getCurrencyListByProcessId(contract.getProcessId());
				for (int i = 0; i < currencyList.size(); i++) {
					ExportUtil.setTitleCell(sheet, false, (row_index + 1), (index + i), currencyList.get(i).getCurrency(), titleStyle);
				}

				// 设置折合人民币单元格内容及样式
				ExportUtil.setTitleCell(sheet, false, (row_index + 1), (index + count - 1), BenefitBudgetConstant.HEAD_TITLE_ZH, titleStyle);

				// 设置百分比单元格内容及样式
				ExportUtil.setTitleCell(sheet, false, (row_index + 1), (index + count), BenefitBudgetConstant.HEAD_TITLE_ZB, titleStyle);

				// 装填合同预算数据
				setContactBudgetCellValue(wb, sheet, region, contract);

				index += count + 1;
			}
			// 设置合计合并单元格
			region = new CellRangeAddress(row_index, row_index, index, (index + 1));
			sheet.addMergedRegion(region);
			ExportUtil.setTitleCell(sheet, true, row_index, index, BenefitBudgetConstant.HEAD_TITLE_XMHJ, titleStyle);

			ExportUtil.setTitleCell(sheet, false, (row_index + 1), index, BenefitBudgetConstant.HEAD_TITLE_ZH, titleStyle);
			ExportUtil.setTitleCell(sheet, false, (row_index + 1), (index + 1), BenefitBudgetConstant.HEAD_TITLE_ZB, titleStyle);

			// 装填合计
			// 获取预算对象集合
			List<BenefitBudget> budgetList = this.benefitBudgetDao.queryBy("projectId", projectId, false, "orderDisplay", true);
			setTotalBudgetCellValue(wb, sheet, region, budgetList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

	/**
	 * 生成单个合同效益预算汇总表
	 * 
	 * @param processId
	 * @param downFileName
	 * @param user
	 * @return
	 */
	public Workbook getContractWorkbook(String processId, String downFileName, UserProfile user) {
		String suffix = downFileName.substring(downFileName.lastIndexOf("."));
		String filePath = BenefitBudgetConstant.BENEFIT_BUDGET_EXCEL_PATH;
		filePath += BenefitBudgetConstant.BENEFIT_CONTRACT_TEMP0 + suffix;
		Workbook wb = null;
		try {
			// 获取Excel模板（模板默认字体为宋体，行高13.5；使用poi生成Excel时默认字体为Arial，行高12.75）
			wb = ExportUtil.getWorkbook(filePath, suffix);
			Sheet sheet = wb.getSheetAt(0);// 第一个工作表

			// 获取合同对象
			BenefitContract contract = this.benefitContractDao.findUniqueResult("processId", processId);

			// 表头单元格样式
			CellStyle titleStyle = ExportUtil.getTitleCellStyle(wb);

			// 定义起始位置
			int row_index = 0;
			int col_index = 0;

			// 设置序号合并单元格
			sheet.addMergedRegion(new CellRangeAddress(row_index, (row_index + 1), col_index, col_index));// 开始行，结束行，开始列，结束列
			ExportUtil.setTitleCell(sheet, true, row_index, col_index, BenefitBudgetConstant.HEAD_TITLE_XH, titleStyle);

			// 设置预算说明合并单元格
			sheet.addMergedRegion(new CellRangeAddress(row_index, (row_index + 1), (col_index + 1), (col_index + 1)));
			ExportUtil.setTitleCell(sheet, true, 0, 1, BenefitBudgetConstant.HEAD_TITLE_YS, titleStyle);

			// 设置合同名称合并单元格
			int index = col_index + 2;// 合同单元格起始列
			CellRangeAddress region;
			int count = contract.getCurrencyNum() + 1;
			// 设置合同名称合并单元格
			region = new CellRangeAddress(row_index, row_index, index, (index + count));
			sheet.addMergedRegion(region);
			ExportUtil.setTitleCell(sheet, true, row_index, index, contract.getContractName(), titleStyle);

			// 设置币别单元格内容及样式
			List<BenefitContractCurrency> currencyList = this.getCurrencyListByProcessId(contract.getProcessId());
			for (int i = 0; i < currencyList.size(); i++) {
				ExportUtil.setTitleCell(sheet, false, (row_index + 1), (index + i), currencyList.get(i).getCurrency(), titleStyle);
			}

			// 设置折合人民币单元格内容及样式
			ExportUtil.setTitleCell(sheet, false, (row_index + 1), (index + count - 1), BenefitBudgetConstant.HEAD_TITLE_ZH, titleStyle);

			// 设置百分比单元格内容及样式
			ExportUtil.setTitleCell(sheet, false, (row_index + 1), (index + count), BenefitBudgetConstant.HEAD_TITLE_ZB, titleStyle);

			// 装填合同预算数据
			setContactBudgetCellValue(wb, sheet, region, contract);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

	/**
	 * 装填合同预算数据
	 * 
	 * @param wb
	 * @param region
	 *            要设置的区域
	 * @param contract
	 *            合同对象
	 */
	public void setContactBudgetCellValue(Workbook wb, Sheet sheet, CellRangeAddress region, BenefitContract contract) {
		// 定义单元格样式
		CellStyle centerCell = ExportUtil.getSimpleCellStyle(wb);
		centerCell.setAlignment(CellStyle.ALIGN_CENTER); // 居中

		CellStyle leftCell = ExportUtil.getSimpleCellStyle(wb);
		leftCell.setAlignment(CellStyle.ALIGN_LEFT); // 居左

		CellStyle rightCell = ExportUtil.getSimpleCellStyle(wb);
		rightCell.setAlignment(CellStyle.ALIGN_RIGHT); // 居右

		CellStyle numericCell = ExportUtil.getNumericCellStyle(wb, null);// 数字

		// 获取预算金额并遍历
		List<BenefitContractBudget> list = this.benefitContractBudgetDao.queryBy("processId", contract.getProcessId(), false, "orderDisplay", true);
		if (list != null && list.size() > 0) {
			int row_index = 2;// 从第三行开始
			int col_start = region.getFirstColumn();
			int col_end = region.getLastColumn();

			String serialNumber, budgetName;
			for (BenefitContractBudget budget : list) {
				if (sheet.getRow(row_index) == null || sheet.getRow(row_index).getCell(0) == null) {
					// 第一列,序号
					serialNumber = CommonUtil.trim(budget.getSerialNumber()).replaceAll("&ensp;", " ");
					ExportUtil.setValueByRowAndCol(sheet, row_index, 0, serialNumber, centerCell);

					// 第二列,预算说明
					budgetName = budget.getBudgetName();
					if (budget.getLevel() == BenefitBudgetConstant.LEVEL_TWO) {
						budgetName = "  " + budgetName;
					} else if (budget.getLevel() == BenefitBudgetConstant.LEVEL_THREE) {
						budgetName = "    " + budgetName;
					}
					ExportUtil.setValueByRowAndCol(sheet, row_index, 1, budgetName, leftCell);
				}

				// 遍历币别金额
				switchSetValueByRowAndCol(sheet, row_index, col_start, centerCell, numericCell, contract.getCurrencyNum(), budget);

				// 第col_end - 1列,折人民币合计
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_end - 1), budget.getTotalRmbAmount(), numericCell);

				// 第col_end列,百分比
				if (budget.getProportion() != BenefitBudgetConstant.DEFAULT_VALUE) {
					ExportUtil.setValueByRowAndCol(sheet, row_index, col_end, budget.getProportionDesc(), rightCell);
				} else {
					ExportUtil.setValueByRowAndCol(sheet, row_index, col_end, budget.getProportionDesc(), centerCell);
				}
				row_index++;
			}
		}
	}

	/**
	 * 设置单元格内容
	 * 
	 * @param sheet
	 * @param row_index
	 * @param col_start
	 * @param centerCell
	 * @param numericCell
	 * @param size
	 * @param budget
	 */
	public void switchSetValueByRowAndCol(Sheet sheet, int row_index, int col_start, CellStyle centerCell, CellStyle numericCell, int size, BenefitContractBudget budget) {
		switch (size) {
		case 9:
			if (budget.getCurrency9Amount() != BenefitBudgetConstant.DEFAULT_VALUE) {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 8), budget.getCurrency9Amount(), numericCell);
			} else {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 8), budget.getCurrency9AmountDesc(), centerCell);
			}
		case 8:
			if (budget.getCurrency8Amount() != BenefitBudgetConstant.DEFAULT_VALUE) {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 7), budget.getCurrency8Amount(), numericCell);
			} else {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 7), budget.getCurrency8AmountDesc(), centerCell);
			}
		case 7:
			if (budget.getCurrency7Amount() != BenefitBudgetConstant.DEFAULT_VALUE) {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 6), budget.getCurrency7Amount(), numericCell);
			} else {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 6), budget.getCurrency7AmountDesc(), centerCell);
			}
		case 6:
			if (budget.getCurrency6Amount() != BenefitBudgetConstant.DEFAULT_VALUE) {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 5), budget.getCurrency6Amount(), numericCell);
			} else {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 5), budget.getCurrency6AmountDesc(), centerCell);
			}
		case 5:
			if (budget.getCurrency5Amount() != BenefitBudgetConstant.DEFAULT_VALUE) {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 4), budget.getCurrency5Amount(), numericCell);
			} else {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 4), budget.getCurrency5AmountDesc(), centerCell);
			}
		case 4:
			if (budget.getCurrency4Amount() != BenefitBudgetConstant.DEFAULT_VALUE) {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 3), budget.getCurrency4Amount(), numericCell);
			} else {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 3), budget.getCurrency4AmountDesc(), centerCell);
			}
		case 3:
			if (budget.getCurrency3Amount() != BenefitBudgetConstant.DEFAULT_VALUE) {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 2), budget.getCurrency3Amount(), numericCell);
			} else {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 2), budget.getCurrency3AmountDesc(), centerCell);
			}
		case 2:
			if (budget.getCurrency2Amount() != BenefitBudgetConstant.DEFAULT_VALUE) {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 1), budget.getCurrency2Amount(), numericCell);
			} else {
				ExportUtil.setValueByRowAndCol(sheet, row_index, (col_start + 1), budget.getCurrency2AmountDesc(), centerCell);
			}
		case 1:
			if (budget.getCurrency1Amount() != BenefitBudgetConstant.DEFAULT_VALUE) {
				ExportUtil.setValueByRowAndCol(sheet, row_index, col_start, budget.getCurrency1Amount(), numericCell);
			} else {
				ExportUtil.setValueByRowAndCol(sheet, row_index, col_start, budget.getCurrency1AmountDesc(), centerCell);
			}

		default:
			break;
		}
	}

}
