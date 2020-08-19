package com.supporter.prj.cneec.tpc.benefit_note.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetConstant;
import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetItemConstant;
import com.supporter.prj.cneec.tpc.benefit_budget.constant.BudgetNumEnum;
import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitBudget;
import com.supporter.prj.cneec.tpc.benefit_budget.service.BenefitBudgetService;
import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitAssembleBean;
import com.supporter.prj.cneec.tpc.benefit_budget.util.BenefitBudgetUtil;
import com.supporter.prj.cneec.tpc.benefit_note.constant.BenefitNoteConstant;
import com.supporter.prj.cneec.tpc.benefit_note.dao.BenefitNoteBudgetDao;
import com.supporter.prj.cneec.tpc.benefit_note.dao.BenefitNoteCurrencyDao;
import com.supporter.prj.cneec.tpc.benefit_note.dao.BenefitNoteDao;
import com.supporter.prj.cneec.tpc.benefit_note.dao.VBenefitNoteAssembleDao;
import com.supporter.prj.cneec.tpc.benefit_note.dao.VBenefitNoteDao;
import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNote;
import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNoteBudget;
import com.supporter.prj.cneec.tpc.benefit_note.entity.BenefitNoteCurrency;
import com.supporter.prj.cneec.tpc.benefit_note.entity.VBenefitNote;
import com.supporter.prj.cneec.tpc.benefit_note.entity.VBenefitNoteAssemble;
import com.supporter.prj.cneec.tpc.benefit_note.util.BenefitAmountAccumulativeBean;
import com.supporter.prj.cneec.tpc.benefit_note.util.BenefitAmountBean;
import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review.service.ContractSignReviewService;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptAmount;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.service.ContractSignDeptReviewService;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.ColModelUtil;
import com.supporter.prj.cneec.tpc.util.ExportUtil;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.binding.JsonUtil;
import com.supporter.util.CommonUtil;

/**
 * @Title: BenefitNoteService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2018-06-01
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class BenefitNoteService {

	@Autowired
	private BenefitNoteDao benefitNoteDao;
	@Autowired
	private BenefitNoteCurrencyDao benefitNoteCurrencyDao;
	@Autowired
	private BenefitNoteBudgetDao benefitNoteBudgetDao;
	@Autowired
	private VBenefitNoteDao vBenefitNoteDao;
	@Autowired
	private VBenefitNoteAssembleDao vBenefitNoteAssembleDao;

	@Autowired
	private BenefitBudgetService benefitBudgetService;

	@Autowired
	private RegisterProjectService registerProjectService;
	@Autowired
	private ContractSignDeptReviewService contractSignDeptReviewService;
	@Autowired
	private ContractSignReviewService contractSignReviewService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, BenefitNote.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param benefitNote
	 */
	public void getAuthCanExecute(UserProfile userProfile, BenefitNote benefitNote) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, BenefitNote.MODULE_ID, benefitNote.getNoteId(), benefitNote);
	}

	/**
	 * 获取贸易项目效益测算表对象集合
	 * @param user
	 * @param jqGrid
	 * @param benefitNote
	 * @return
	 */
	public List<BenefitNote> getGrid(UserProfile user, JqGrid jqGrid, Map<String, Object> valueMap) {
		String authFilter = getAuthFilter(user);
		return this.benefitNoteDao.findPage(jqGrid, valueMap, authFilter);
	}

	/**
	 * 获取币别表
	 * @param jqGrid
	 * @param noteId
	 * @param parameters
	 * @return
	 */
	public List<BenefitNoteCurrency> getNoteCurrencyGrid(JqGrid jqGrid, String noteId, Map<String, Object> parameters) {
		return this.benefitNoteCurrencyDao.findPage(jqGrid, parameters);
	}

	/**
	 * 获取汇总预算表
	 * @param jqGrid
	 * @param noteId
	 * @param parameters
	 * @return
	 */
	public List<BenefitNoteBudget> getNoteBudgetGrid(JqGrid jqGrid, String noteId, Map<String, Object> parameters) {
		return this.benefitNoteBudgetDao.findPage(jqGrid, parameters);
	}

	/**
	 * 获取效益测算过程表头
	 * @param noteId
	 * @return
	 */
	public String getBenefitAssembleTitleData(String noteId, Map<String, Object> valueMap) {
		// 列个数
		List<Map<String, Object>> colModelData = new ArrayList<Map<String, Object>>();
		// 分组个数
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

		// 获取主表
		BenefitNote note = this.get(noteId);

		int width = BenefitBudgetConstant.TITLE_WIDTH;
		if (note != null) {
			// 设置动态币别
			List<BenefitNoteCurrency> noteCurrencyList = this.getCurrencyListByNoteId(noteId);
			int i = 1;
			String currency = "币别", cName;
			for (BenefitNoteCurrency nc : noteCurrencyList) {
				cName = nc.getCurrency() != null ? nc.getCurrency() : currency + i;
				jsonMap = ColModelUtil.getColModelData(cName, "currency" + i + "AmountDisplay", "right", width);
				colModelData.add(jsonMap);
				i++;
			}

			// 折人民币合计金额
			jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_ZH, "totalRmbAmountDisplay", "right", width);
			colModelData.add(jsonMap);

			// 百分比
			jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_ZB, "proportionDisplay", "right", BenefitBudgetConstant.TITLE_WIDTH_QT2);
			colModelData.add(jsonMap);

			// 设置标题分组(合同名称)
			jsonMap = ColModelUtil.getGroupTitleData("currency1AmountDisplay", noteCurrencyList.size() + 2, note.getContractName());
			groupTitleData.add(jsonMap);
			total_width += (noteCurrencyList.size() + 1) * width + BenefitBudgetConstant.TITLE_WIDTH_QT2;
		}

		// 设置预算折人民币合计
		jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_ZH, "sumTotalAmountDisplay", "right", width);
		colModelData.add(jsonMap);
		
		// 设置预算合计百分比
		jsonMap = ColModelUtil.getColModelData(BenefitBudgetConstant.HEAD_TITLE_ZB, "sumTotalProportionDisplay", "right", BenefitBudgetConstant.TITLE_WIDTH_QT2);
		colModelData.add(jsonMap);
		
		// 设置合计标题分组(合计)
		jsonMap = ColModelUtil.getGroupTitleData("sumTotalAmountDisplay", 2, BenefitBudgetConstant.HEAD_TITLE_XMHJ);
		groupTitleData.add(jsonMap);
		total_width += width + BenefitBudgetConstant.TITLE_WIDTH_QT2;

		// 拼装JSON对象字符串
		String colModelJSON = JSONArray.fromObject(colModelData).toString();
		String groupTitleJSON = JSONArray.fromObject(groupTitleData).toString();
		String jsonData = "{\"result\":\"success\",\"width\":" + total_width + ",\"colModelJSON\":" + colModelJSON + ",\"groupTitleJSON\":" + groupTitleJSON + "}";
		// System.out.println("jsonData:" + jsonData);
		return jsonData;
	}

	/**
	 * 获取ListPage JSON格式数据（累加本次合同预算）
	 * @param noteId
	 * @return
	 * @throws Exception 
	 */
	public String getBenefitAssembleGrid(String noteId, Map<String, Object> valueMap) throws Exception {
		String json = "{\"records\":\"0\", \"page\":1, \"total\":1, \"rows\":[]}";
		BenefitNote note = this.get(noteId);
		if (note != null) {
			// 包括本次合同预算在内所有评审合同预算
			List<BenefitNoteBudget> list = this.getBudgetListByNoteId(noteId);
			int count = 0;
			if (list != null && list.size() > 0) {
				count = list.size();
				json = loadAssembleExtendField(note, list);
			}
			// 拼装前台需要的ListPage JSON格式数据，records记录个数，page当前页，total总页数，rows数据内容
			json = "{\"records\":\"" + count + "\", \"page\":1, \"total\":1, \"rows\":" + json + "}";
		}
		// System.out.println(json);
		return json;
	}

	/**
	 * 设置币别金额属性
	 * @param index
	 * @param budget
	 * @param bean
	 */
	public void switchLoadAssembleExtendField(int index, BenefitNoteBudget budget, Map<String, Object> bean) {
		// 从第index个币别开始赋值到第一个
		switch (index) {
		case 9:
			bean.put("currency9AmountDisplay", budget.getCurrency9AmountDisplay());
		case 8:
			bean.put("currency8AmountDisplay", budget.getCurrency8AmountDisplay());
		case 7:
			bean.put("currency7AmountDisplay", budget.getCurrency7AmountDisplay());
		case 6:
			bean.put("currency6AmountDisplay", budget.getCurrency6AmountDisplay());
		case 5:
			bean.put("currency5AmountDisplay", budget.getCurrency5AmountDisplay());
		case 4:
			bean.put("currency4AmountDisplay", budget.getCurrency4AmountDisplay());
		case 3:
			bean.put("currency3AmountDisplay", budget.getCurrency3AmountDisplay());
		case 2:
			bean.put("currency2AmountDisplay", budget.getCurrency2AmountDisplay());
		case 1:
			bean.put("currency1AmountDisplay", budget.getCurrency1AmountDisplay());

		default:
			break;
		}
	}

	/**
	 * 装填本次合同预算及项目合计
	 * @param note
	 * @param noteBudgetList
	 * @return
	 * @throws Exception
	 */
	public String loadAssembleExtendField(BenefitNote note, List<BenefitNoteBudget> noteBudgetList) throws Exception {
		List<Map<String, Object>> budgetBeanList = new ArrayList<Map<String, Object>>();
		// 定义可扩展属性Bean，动态添加属性，用作前台JqGrid向右扩展展示
		Map<String, Object> bean = null;
		for (BenefitNoteBudget noteBudget : noteBudgetList) {
			noteBudget.setExpanded(false);// 默认折叠
			// 实例化扩展属性对象
			bean = BenefitBudgetUtil.getBaseAssembleMap(noteBudget);
			// 预算金额
			switchLoadAssembleExtendField(note.getCurrencyNum(), noteBudget, bean);
			// 合同预算合计金额
			bean.put("totalRmbAmountDisplay", noteBudget.getTotalRmbAmountDisplay());
			bean.put("proportionDisplay", noteBudget.getProportionDisplay());
			// 项目合计金额
			bean.put("sumTotalAmountDisplay", noteBudget.getSumTotalAmountDisplay());
			bean.put("sumTotalProportionDisplay", noteBudget.getSumTotalProportionDisplay());
			budgetBeanList.add(bean);
		}
		String json = JSONArray.fromObject(budgetBeanList).toString();
		// System.out.println("json:"+json);
		return json;
	}

	/**
	 * 获取ListPage JSON格式数据（不累加本次合同预算）
	 * @param noteId
	 * @return
	 * @throws Exception
	 */
	public String getBudgetBenefitAssembleGrid(String noteId) throws Exception {
		String json = "{\"records\":\"0\", \"page\":1, \"total\":1, \"rows\":[]}";
		BenefitNote note = this.get(noteId);
		if (note != null) {
			String projectId = note.getProjectId();
			List<BenefitBudget> list = this.benefitBudgetService.getBenefitBudgetList(projectId);
			int count = 0;
			if (list != null && list.size() > 0) {
				count = list.size();
				json = loadBudgetAssembleExtendField(note, list);
			}
			// 拼装前台需要的ListPage JSON格式数据，records记录个数，page当前页，total总页数，rows数据内容
			json = "{\"records\":\"" + count + "\", \"page\":1, \"total\":1, \"rows\":" + json + "}";
		}
		// System.out.println(json);
		return json;
	}

	/**
	 * 装填本次合同预算及项目合计（除本次预算）
	 * @param note
	 * @param assembleList
	 * @return
	 * @throws Exception
	 */
	public String loadBudgetAssembleExtendField(BenefitNote note, List<BenefitBudget> assembleList) throws Exception {
		List<Map<String, Object>> assembleBeanList = new ArrayList<Map<String, Object>>();
		// 定义可扩展属性Bean，动态添加属性，用作前台JqGrid向右扩展展示
		Map<String, Object> bean = null;
		BenefitNoteBudget budget;
		for (BenefitBudget assemble : assembleList) {
			assemble.setExpanded(false);// 默认折叠
			// 实例化扩展属性对象
			bean = BenefitBudgetUtil.getBaseAssembleMap(assemble);
			bean.put("sumTotalAmountDisplay", assemble.getTotalBudgetAmountDisplay());
			bean.put("sumTotalProportionDisplay", assemble.getTotalProportionDisplay());

			// 给合同属性赋值
			if (note != null) {
				budget = this.getBenefitNoteBudget(note.getNoteId(), assemble.getBudgetId());
				if (budget != null) {
					// 赋值预算金额
					switchLoadAssembleExtendField(note.getCurrencyNum(), budget, bean);

					bean.put("totalRmbAmountDisplay", budget.getTotalRmbAmountDisplay());
					bean.put("proportionDisplay", budget.getProportionDisplay());
				}
			}
			assembleBeanList.add(bean);
		}
		String json = JSONArray.fromObject(assembleBeanList).toString();
		// System.out.println("json:"+json);
		return json;
	}

	/**
	 * 获取单个贸易项目效益测算表对象
	 * @param noteId
	 * @return
	 */
	public BenefitNote get(String noteId) {
		BenefitNote benefitNote = this.benefitNoteDao.get(noteId);
		if (benefitNote != null) {
			benefitNote.setCurrencyList(this.getCurrencyListByNoteId(noteId));
		}
		return benefitNote;
	}

	/**
	 * 根据效益编号获取对象集合
	 * @param benefitNo
	 * @return
	 */
	public List<BenefitNote> getByBenefitNo(String benefitNo) {
		return this.benefitNoteDao.queryBy("benefitNo", benefitNo, false, "versions", true);
	}

	/**
	 * 取最高版本集合
	 * @param projectId
	 * @param valueMap
	 * @return
	 */
	public List<VBenefitNote> getBenefitNoteTopList(String projectId, Map<String, Object> valueMap) {
		List<VBenefitNote> list = this.vBenefitNoteDao.queryBy("projectId", projectId, false, "createdDate", true);
		return list;
	}

	/**
	 * 排除本次效益测算表
	 * @param noteId
	 * @param projectId
	 * @param valueMap
	 * @return
	 */
	public Map<String, String> getOtherBenefitNoteMap(String noteId, String projectId, Map<String, Object> valueMap) {
		if (StringUtils.isBlank(projectId)) {
			projectId = this.get(noteId).getProjectId();
		}
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<VBenefitNote> list = this.getBenefitNoteTopList(projectId, valueMap);
		for (VBenefitNote benefitNote : list) {
			if (benefitNote.getNoteId().equals(noteId)) {
				continue;
			}
			String contract = "";
			if (StringUtils.isNotBlank(benefitNote.getContractNo())) {
				contract = "(" + benefitNote.getContractNo() + ")";
			}
			contract += benefitNote.getContractName();
			map.put(benefitNote.getNoteId(), contract);
		}
		return map;
	}

	/**
	 * 根据合同评审销售合同ID获取最高版本
	 * @param inforId
	 * @return
	 */
	public BenefitNote getBenefitNoteTopByInforId(String inforId) {
		BenefitNote benefitNote = null;
		// 获取效益测算表集合
		List<BenefitNote> list = this.benefitNoteDao.queryBy("inforId", inforId, false, "versions", false);
		if (list.size() > 0) {
			benefitNote = list.get(0);
			benefitNote.setCurrencyList(this.getCurrencyListByNoteId(benefitNote.getNoteId()));
		}
		return benefitNote;
	}

	/**
	 * 根据变更单号获取最高版本
	 * @param noteId
	 * @return
	 */
	public BenefitNote getBenefitNoteTopByBenefitNo(String benefitNo) {
		BenefitNote benefitNote = null;
		// 获取效益测算表集合
		List<BenefitNote> list = this.benefitNoteDao.queryBy("benefitNo", benefitNo, false, "versions", false);
		if (list.size() > 0) {
			benefitNote = list.get(0);
			benefitNote.setCurrencyList(this.getCurrencyListByNoteId(benefitNote.getNoteId()));
		}
		return benefitNote;
	}

	/**
	 * 获取单个预算项
	 * @param noteBudgetId
	 * @return
	 */
	public BenefitNoteBudget getBenefitNoteBudget(String noteBudgetId) {
		return this.benefitNoteBudgetDao.get(noteBudgetId);
	}

	/**
	 * 根据主表和预算ID获取单个预算项
	 * @param noteId
	 * @param budgetId
	 * @return
	 */
	public BenefitNoteBudget getBenefitNoteBudget(String noteId, String budgetId) {
		return this.benefitNoteBudgetDao.queryUnique(noteId, budgetId);
	}

	/**
	 * 变更单集合
	 * @param properName
	 * @param propValue
	 * @param likeSearch
	 * @param orderByName
	 * @param sort
	 * @return
	 */
	public List<BenefitNote> getList(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		return this.benefitNoteDao.queryBy(properName, propValue, likeSearch, orderByName, sort);
	}

	/**
	 * 根据评审合同Id获取唯一币别
	 * @param inforId
	 * @param currencyId
	 * @return
	 */
	public BenefitNoteCurrency queryUniqueCurrencyByInforId(String inforId, String currencyId) {
		BenefitNote note = this.getBenefitNoteTopByInforId(inforId);
		if (note != null) {
			return this.benefitNoteCurrencyDao.queryUnique(note.getNoteId(), currencyId);
		}
		return null;
	}

	/**
	 * 获取唯一币别
	 * @param properName
	 * @param propValue
	 * @param likeSearch
	 * @param orderByName
	 * @param sort
	 * @return
	 */
	public BenefitNoteCurrency queryUniqueCurrency(String noteId, String currencyId) {
		return this.benefitNoteCurrencyDao.queryUnique(noteId, currencyId);
	}

	/**
	 * 变更单币别集合
	 * @param properName
	 * @param propValue
	 * @param likeSearch
	 * @param orderByName
	 * @param sort
	 * @return
	 */
	public List<BenefitNoteCurrency> getCurrencyList(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		return this.benefitNoteCurrencyDao.queryBy(properName, propValue, likeSearch, orderByName, sort);
	}

	/**
	 * 根据主表ID获取币别集合
	 * 
	 * @param noteId
	 * @return
	 */
	public List<BenefitNoteCurrency> getCurrencyListByNoteId(String noteId) {
		return this.benefitNoteCurrencyDao.queryBy("noteId", noteId, null, "orderDisplay", true);
	}

	/**
	 * 根据评审合同ID获取变更单币别集合
	 * @param inforId
	 * @return
	 */
	public List<BenefitNoteCurrency> getCurrencyListByInforId(String inforId) {
		BenefitNote benefitNote = this.getBenefitNoteTopByInforId(inforId);
		if (benefitNote != null) {
			return benefitNote.getCurrencyList();
		}
		return null;
	}

	/**
	 * 变更单预算集合
	 * @param properName
	 * @param propValue
	 * @param likeSearch
	 * @param orderByName
	 * @param sort
	 * @return
	 */
	public List<BenefitNoteBudget> getBudgetList(String properName, Object propValue, Boolean likeSearch, String orderByName, Boolean sort) {
		return this.benefitNoteBudgetDao.queryBy(properName, propValue, likeSearch, orderByName, sort);
	}

	/**
	 * 根据主表ID获取预算项集合
	 * 
	 * @param noteId
	 * @return
	 */
	public List<BenefitNoteBudget> getBudgetListByNoteId(String noteId) {
		return this.benefitNoteBudgetDao.queryByNoteId(noteId);
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public BenefitNote loadBenefitNote(BenefitNote benefitNote, UserProfile user) {
		benefitNote.setCreatedBy(user.getName());
		benefitNote.setCreatedById(user.getPersonId());
		benefitNote.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		benefitNote.setModifiedBy(user.getName());
		benefitNote.setModifiedById(user.getPersonId());
		benefitNote.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			benefitNote.setDeptName(dept.getName());
			benefitNote.setDeptId(dept.getDeptId());
		}
		// 设置状态
		benefitNote.setSwfStatus(BenefitNote.DRAFT);
		return benefitNote;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param noteId
	 * @param user
	 * @return
	 */
	public BenefitNote initEditOrViewPage(String noteId, Map<String, Object> valueMap, UserProfile user) {
		BenefitNote benefitNote;
		if (StringUtils.isBlank(noteId)) {
			benefitNote = newBenefitNote(valueMap, user);
		} else {
			benefitNote = this.get(noteId);
		}
		benefitNote.setAdd(false);
		return benefitNote;
	}

	/**
	 * 初始化生成效益测算变更表
	 * @param valueMap
	 * @param user
	 * @return
	 */
	public BenefitNote newBenefitNote(Map<String, Object> valueMap, UserProfile user) {
		BenefitNote benefitNote = new BenefitNote();
		if (valueMap != null && !valueMap.isEmpty()) {
			try {
				BeanUtils.populate(benefitNote, valueMap);
			} catch (Exception e) {
				System.out.println("newBenefitNote Error :" + e.getMessage());
			}
		}
		loadBenefitNote(benefitNote, user);
		benefitNote.setSwfStatus(BenefitNote.DRAFT);
		this.benefitNoteDao.save(benefitNote);
		initSummaryBudget(benefitNote);
		return benefitNote;
	}

	/**
	 * 事业部评审初次生成效益测算表
	 * @param inforId
	 * @param valueMap
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BenefitNote initByDeptReviewInforId(String inforId, Map<String, Object> valueMap, UserProfile user) {
		// 评审中编辑通过合同ID获取最高版本
		BenefitNote benefitNote = this.getBenefitNoteTopByInforId(inforId);
		if (benefitNote == null) {// 不存在效益测算表则生成
			// 设置合同信息
			String projectId = (String) valueMap.get("projectId");
			RegisterProject project = this.registerProjectService.get(projectId);
			Map<String, Object> projectMap = new BeanMap(project);
			valueMap.putAll(projectMap);
			// 设置合同信息
			ContractSignDeptInfor infor = this.contractSignDeptReviewService.getContractSignDeptInfor(inforId);
			valueMap.put("signId", infor.getSignId());
			valueMap.put("contractName", infor.getContractName());
			benefitNote = newBenefitNote(valueMap, user);
			String noteId = benefitNote.getNoteId();
			infor.setNoteId(noteId);
			this.contractSignDeptReviewService.update(infor);

			// 初始化币别
			BenefitNoteCurrency noteCurrency;
			List<BenefitNoteCurrency> reBuildCurrencyList = new ArrayList<BenefitNoteCurrency>();
			int i = 0;// 币别数量
			// 设置效益测算表币别
			Map<String, ContractSignDeptAmount> amountMap = this.contractSignDeptReviewService.getInforAmountMap(inforId);
			for (Map.Entry<String, ContractSignDeptAmount> e : amountMap.entrySet()) {
				noteCurrency = new BenefitNoteCurrency();
				noteCurrency.setCurrency(e.getValue().getCurrency());
				noteCurrency.setCurrencyId(e.getValue().getCurrencyId());
				noteCurrency.setRate(e.getValue().getRate());
				noteCurrency.setNoteId(noteId);
				noteCurrency.setOrderDisplay(i * 10);
				reBuildCurrencyList.add(noteCurrency);
				i++;
			}
			// 追加币别
			int appendCurrencyNum = CommonUtil.parseInt(String.valueOf(valueMap.get("appendCurrencyNum")), 0);
			for (int j = i; j < appendCurrencyNum + i; j++) {
				noteCurrency = new BenefitNoteCurrency();
				noteCurrency.setNoteId(noteId);
				noteCurrency.setOrderDisplay(j * 10);
				reBuildCurrencyList.add(noteCurrency);
			}
			// 合同币别有效，删除原币别，保存为合同币别
			if (reBuildCurrencyList.size() > 0) {
				this.benefitNoteCurrencyDao.delete(benefitNote.getCurrencyList());
				this.benefitNoteCurrencyDao.save(reBuildCurrencyList);
				benefitNote.setCurrencyList(reBuildCurrencyList);
				benefitNote.setCurrencyNum(reBuildCurrencyList.size());
			}
		}
		benefitNote.setAdd(false);
		return benefitNote;
	}

	/**
	 * 公司评审初始化效益测算表
	 * @param inforId
	 * @param valueMap
	 * @param user
	 * @return
	 */
	public BenefitNote initByCompanyReviewInforId(String inforId, Map<String, Object> valueMap, UserProfile user) {
		// 评审中编辑通过合同ID获取最高版本
		BenefitNote benefitNote = this.getBenefitNoteTopByInforId(inforId);
		boolean hasSwfProc = Boolean.valueOf((String) valueMap.get("hasSwfProc"));
		if (benefitNote.getSwfStatus() == BenefitNote.DRAFT) {
			return benefitNote;
		} else {
			// 生成新的记录
			benefitNote = createBenefitNoteByNoteId(benefitNote.getNoteId(), BenefitNoteConstant.VERSIONS_OF_COMPANY_REVIEW, user);
			benefitNote.setHasSwfProc(hasSwfProc);
			// 修改合同效益测算表变更状态
			ContractSignInfor infor = this.contractSignReviewService.getContractSignInfor(inforId);
			infor.setChangeBenefit(true);
			infor.setNoteId(benefitNote.getNoteId());
			this.contractSignReviewService.update(infor);
		}
		benefitNote.setAdd(false);
		return benefitNote;
	}

	/**
	 * 评审中修改效益测算表
	 * @param noteId
	 * @param valueMap
	 * @param user
	 * @return
	 */
	public BenefitNote initByReview(String noteId, String versionsType, Map<String, Object> valueMap, UserProfile user) {
		BenefitNote benefitNote = this.get(noteId);
		String inforId = (String) valueMap.get("inforId");
		// 是否有流程，即会审修改提交效益测算表
		boolean hasSwfProc = Boolean.valueOf((String) valueMap.get("hasSwfProc"));
		if (benefitNote == null) {
			benefitNote = initByDeptReviewInforId(inforId, valueMap, user);
		} else if (hasSwfProc && versionsType.equals(benefitNote.getVersionsType())) {
			// 会审修改提交效益测算表,返回本评审版本效益测算表(即不再新增版本，若有修改在本次测算表流程中修改)
			benefitNote.setHasSwfProc(hasSwfProc);
			return benefitNote;
		} else if (benefitNote.getSwfStatus() == BenefitNote.DRAFT) {
			// 无流程或非本评审版本效益测算表且是草稿直接取该测算表
			benefitNote.setHasSwfProc(hasSwfProc);
			return benefitNote;
		} else {
			/**
			 * 无流程或非本评审版本效益测算表
			 * 1.网审修改效益测算表，非草稿新建版本；
			 * 2.公司评审需要根据事业部评审版本新建版本
			 */
			// 生成新的记录
			benefitNote = createBenefitNoteByNoteId(noteId, versionsType, user);
			benefitNote.setHasSwfProc(hasSwfProc);
			// 修改合同效益测算表变更状态
			if (versionsType.equals(BenefitNoteConstant.VERSIONS_OF_DEPT_REVIEW)) {
				ContractSignDeptInfor infor = this.contractSignDeptReviewService.getContractSignDeptInfor(inforId);
				infor.setChangeBenefit(true);
				infor.setNoteId(benefitNote.getNoteId());
				this.contractSignDeptReviewService.update(infor);
			} else if (versionsType.equals(BenefitNoteConstant.VERSIONS_OF_COMPANY_REVIEW)) {
				ContractSignInfor infor = this.contractSignReviewService.getContractSignInfor(inforId);
				infor.setChangeBenefit(true);
				infor.setNoteId(benefitNote.getNoteId());
				this.contractSignReviewService.update(infor);
			}
		}
		benefitNote.setAdd(false);
		return benefitNote;
	}

	/**
	 * 根据变更单ID生成新的变更单
	 * @param noteId
	 * @param versionType
	 * @param user
	 * @return
	 */
	public BenefitNote createBenefitNoteByNoteId(String noteId, String versionType, UserProfile user) {
		BenefitNote benefitNote = new BenefitNote();
		BenefitNote note = this.get(noteId);
		if (note != null) {
			if (note.getSwfStatus() == BenefitNote.DRAFT) {
				return note;
			}
			int usdIndex = -1;
			double usdRate = -1;
			List<BenefitNoteCurrency> noteCurrencyList = new ArrayList<BenefitNoteCurrency>();
			List<BenefitNoteBudget> noteBudgetList = new ArrayList<BenefitNoteBudget>();
			try {
				benefitNote = new BenefitNote();
				BeanUtils.copyProperties(benefitNote, note);
				loadBenefitNote(benefitNote, user);
				benefitNote.setParentNoteId(noteId);
				benefitNote.setVersions(note.getVersions() + 1);
				benefitNote.setVersionsType(versionType);
				// 状态，流程信息置为初始
				benefitNote.setSwfStatus(BenefitNote.DRAFT);
				benefitNote.setProcId(null);
				this.benefitNoteDao.save(benefitNote);
				// 初始化效益币别
				BenefitNoteCurrency noteCurrency;
				List<BenefitNoteCurrency> currencyList = this.getCurrencyListByNoteId(noteId);
				for (BenefitNoteCurrency currency : currencyList) {
					noteCurrency = new BenefitNoteCurrency();
					BeanUtils.copyProperties(noteCurrency, currency);
					noteCurrency.setRecordId(null);
					noteCurrency.setNoteId(benefitNote.getNoteId());
					noteCurrencyList.add(noteCurrency);
					if (noteCurrency.getCurrencyId().equals("USD")) {
						usdIndex = noteCurrency.getOrderDisplay() / 10;
						usdRate = noteCurrency.getRate();
					}
				}
				// 初始化效益测算表
				BenefitNoteBudget noteBudget;
				List<BenefitNoteBudget> budgetList = this.getBudgetListByNoteId(noteId);
				for (BenefitNoteBudget budget : budgetList) {
					noteBudget = new BenefitNoteBudget();
					BeanUtils.copyProperties(noteBudget, budget);
					noteBudget.setNoteBudgetId(null);
					noteBudget.setNoteId(benefitNote.getNoteId());
					noteBudget.setSumTotalAmount(0);
					noteBudget.setSumTotalProportion(0);
					noteBudgetList.add(noteBudget);
					// 有美元金额取销售合同总金额设置评审金额
					if (usdIndex > -1 && noteBudget.getBudgetId().equals(BenefitBudgetItemConstant.SUMMARY_TRADE_TOTAL)) {
						double allBudgetAmount = noteBudget.getAllBudgetAmount();
						double tradeAmount = noteBudget.getTotalRmbAmount();
						// 仅有一个美元金额直接赋值评审金额
						if ((allBudgetAmount * usdRate) == tradeAmount) {
							benefitNote.setReviewAmount(allBudgetAmount);
						} else {
							Map<String, String> rateMap = TpcConstant.getCurrencyRateMap();
							// 从码表中取人民币对美元汇率
							if (rateMap.containsKey("USD")) {
								usdRate = CommonUtil.parseDouble(rateMap.get("USD").toString(), 1);
							}
							benefitNote.setReviewAmount(tradeAmount / usdRate);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("createBenefitNoteByNoteId Error: " + e);
			}
			// 批量保存币别、测算信息
			if (noteBudgetList.size() > 0) {
				this.benefitNoteCurrencyDao.save(noteCurrencyList);
				this.benefitNoteBudgetDao.save(noteBudgetList);
				benefitNote.setCurrencyList(noteCurrencyList);
			}
		}
		return benefitNote;
	}

	/**
	 * 事业部评审完成批量创建事业部版效益测算表（事业部评审完成即结束而又需要修改效益测算表）
	 * 
	 * @param signId
	 * @param valueMap
	 * @param user
	 * @return
	 */
	public String createBenefitNoteListByDeptReview(String signId, Map<String, Object> valueMap, UserProfile user) {
		String json = "{\"result\": \"fail\", \"msg\": \"createBenefitNoteListByDeptReview-fail\"}";
		if (StringUtils.isNotBlank(signId)) {
			// 查找是否有当前评审单下且需要走流程的效益测算表（无则新建）
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("signId", signId);
			params.put("hasSwfProc", true);
			params.put("versionsType", BenefitNoteConstant.VERSIONS_OF_DEPT_REVIEW);
			int size = this.benefitNoteDao.queryByParam(params, null, null).size();
			if (size == 0) {
				List<ContractSignDeptInfor> contractList = this.contractSignDeptReviewService.getContractSignDeptInforByInforType(signId, ContractSignReviewUtil.INFOR_TYPE_ORDER);
				if (contractList.size() > 0) {
					BenefitNote benefitNote;
					for (ContractSignDeptInfor contract : contractList) {
						String noteId = contract.getNoteId();
						benefitNote = createBenefitNoteByNoteId(noteId, BenefitNoteConstant.VERSIONS_OF_DEPT_REVIEW, user);
						addReviewExamValue(benefitNote);
					}
					json = "{\"result\": \"success\", \"msg\": \"createBenefitNoteListByDeptReview-success\"}";
				}
			} else {
				json = "{\"result\": \"success\", \"msg\": \"createBenefitNoteListByDeptReview-exist\"}";
			}
		}
		return json;
	}

	/**
	 * 公司评审完成批量创建公司版效益测算表（公司评审完成即结束而又需要修改效益测算表）
	 * @param valueMap
	 * @param user
	 * @return
	 */
	public String createBenefitNoteListByCommpanyReview(String signId, Map<String, Object> valueMap, UserProfile user) {
		String json = "{\"result\": \"fail\", \"msg\": \"createBenefitNoteListByCommpanyReview-fail\"}";
		if (StringUtils.isNotBlank(signId)) {
			// 查找是否有当前评审单下且需要走流程的效益测算表
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("signId", signId);
			params.put("hasSwfProc", true);
			params.put("versionsType", BenefitNoteConstant.VERSIONS_OF_COMPANY_REVIEW);
			int size = this.benefitNoteDao.queryByParam(params, null, null).size();
			if (size == 0) {
				List<ContractSignInfor> contractList = this.contractSignReviewService.getContractSignInforByInforType(signId, ContractSignReviewUtil.INFOR_TYPE_ORDER);
				if (contractList.size() > 0) {
					BenefitNote benefitNote;
					for (ContractSignInfor contract : contractList) {
						String noteId = contract.getNoteId();
						benefitNote = createBenefitNoteByNoteId(noteId, BenefitNoteConstant.VERSIONS_OF_COMPANY_REVIEW, user);
						addReviewExamValue(benefitNote);
					}
					json = "{\"result\": \"success\", \"msg\": \"createBenefitNoteListByCommpanyReview-success\"}";
				}
			} else {
				json = "{\"result\": \"success\", \"msg\": \"createBenefitNoteListByCommpanyReview-exist\"}";
			}
		}
		return json;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param benefitNote
	 * @param valueMap
	 * @return
	 */
	public BenefitNote saveOrUpdate(UserProfile user, BenefitNote benefitNote, Map<String, Object> valueMap) {
		if (benefitNote.getAdd()) {
			// 装配基础信息
			loadBenefitNote(benefitNote, user);
			this.benefitNoteDao.save(benefitNote);
		} else {
			// 设置更新时间
			benefitNote.setModifiedBy(user.getName());
			benefitNote.setModifiedById(user.getPersonId());
			benefitNote.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.benefitNoteDao.update(benefitNote);
		}
		saveOrUpdateSummaryBudget(benefitNote, valueMap);
		return benefitNote;
	}

	/**
	 * 汇总表序号
	 * @param index
	 * @param level
	 * @return
	 */
	public String getSummarySerialNumber(int index, int level) {
		String serialNumber = "";
		if (level == BenefitBudgetConstant.LEVEL_ONE) {
			serialNumber = BudgetNumEnum.valueOf(index).getDisplayName();
		} else if (level == BenefitBudgetConstant.LEVEL_TWO) {
			serialNumber = index + "";
		} else if (level == BenefitBudgetConstant.LEVEL_THREE) {
			serialNumber = "&ensp;&ensp;&ensp;" + index + "）";
		}
		
		return serialNumber;
	}

	/**
	 * 初始化币别
	 * @param noteId
	 * @return
	 */
	public List<BenefitNoteCurrency> initNoteCurrency(BenefitNote benefitNote) {
		BenefitNoteCurrency currency;
		List<BenefitNoteCurrency> currencyList = new ArrayList<BenefitNoteCurrency>();
		// 初始化默认3个币别
		int currencyNum = benefitNote.getCurrencyNum() != 0 ? benefitNote.getCurrencyNum() : BenefitNoteConstant.CURRENCY_NUM;
		String noteId = benefitNote.getNoteId();
		for (int i = 0; i < currencyNum; i++) {
			currency = new BenefitNoteCurrency();
			currency.setNoteId(noteId);
			currency.setOrderDisplay(i * 10);
			currencyList.add(currency);
		}
		benefitNote.setCurrencyNum(currencyNum);
		benefitNote.setCurrencyList(currencyList);
		this.benefitNoteCurrencyDao.save(currencyList);
		return currencyList;
	}

	/**
	 * 初始化录入汇总效益测算表
	 * @return
	 */
	public boolean initSummaryBudget(BenefitNote benefitNote) {
		boolean flag = false;
		String noteId = benefitNote.getNoteId();

		// 初始化币别
		initNoteCurrency(benefitNote);
		
		// 获取效益测算表汇总预算项并写入表中
		List<BenefitNoteBudget> budgetList = new ArrayList<BenefitNoteBudget>();
		// 一级
		BenefitNoteBudget oneSummaryBudget;
		Map<String, String> oneBudgetMap = BenefitBudgetUtil.getBudgetNameMap(BenefitBudgetItemConstant.BENEFIT_CALC_SUMMARY);
		int i = 0;
		String serialNumber;
		int oneLevelIndex = BenefitBudgetConstant.ORDER_DISPLAY_ONE;
		for (Map.Entry<String, String> oneBudget : oneBudgetMap.entrySet()) {
			serialNumber = getSummarySerialNumber(i / oneLevelIndex, BenefitBudgetConstant.LEVEL_ONE);
			oneSummaryBudget = new BenefitNoteBudget(noteId, serialNumber, oneBudget.getKey(), oneBudget.getValue(), null, BenefitBudgetConstant.LEVEL_ONE, i);
			if (i == 0) {// 设置销售合同总金额比例为100%
				oneSummaryBudget.setProportion(1.0);
			}
			this.benefitNoteBudgetDao.save(oneSummaryBudget);
			budgetList.add(oneSummaryBudget);

			// 二级
			BenefitNoteBudget twoSummaryBudget;
			Map<String, String> twoBudgetMap = BenefitBudgetUtil.getBudgetNameMap(oneBudget.getKey());
			if (twoBudgetMap.size() == 0) {// 没有二级设置该一级为叶节点
				oneSummaryBudget.setLeaf(true);
			}
			int j = BenefitBudgetConstant.ORDER_DISPLAY_TWO;
			int twoLevelIndex = BenefitBudgetConstant.ORDER_DISPLAY_TWO;
			for (Map.Entry<String, String> twoBudget : twoBudgetMap.entrySet()) {
				serialNumber = getSummarySerialNumber(j / twoLevelIndex, BenefitBudgetConstant.LEVEL_TWO);
				twoSummaryBudget = new BenefitNoteBudget(noteId, serialNumber, twoBudget.getKey(), twoBudget.getValue(), oneSummaryBudget.getBudgetId(), BenefitBudgetConstant.LEVEL_TWO, (i + j));
				this.benefitNoteBudgetDao.save(twoSummaryBudget);
				budgetList.add(twoSummaryBudget);

				// 三级
				BenefitNoteBudget threeSummaryBudget;
				Map<String, String> threeBudgetMap = BenefitBudgetUtil.getBudgetNameMap(twoBudget.getKey());
				if (threeBudgetMap.size() == 0) {
					twoSummaryBudget.setLeaf(true);
				}
				int k = BenefitBudgetConstant.ORDER_DISPLAY_THREE;
				int threeLevelIndex = BenefitBudgetConstant.ORDER_DISPLAY_THREE;
				for (Map.Entry<String, String> threeBudget : threeBudgetMap.entrySet()) {
					serialNumber = getSummarySerialNumber(k / threeLevelIndex, BenefitBudgetConstant.LEVEL_THREE);
					threeSummaryBudget = new BenefitNoteBudget(noteId, serialNumber, threeBudget.getKey(), threeBudget.getValue(), twoSummaryBudget.getBudgetId(), BenefitBudgetConstant.LEVEL_THREE, (i + j + k));
					threeSummaryBudget.setLeaf(true);
					this.benefitNoteBudgetDao.save(threeSummaryBudget);
					budgetList.add(threeSummaryBudget);
					k += threeLevelIndex;
				}
				j += twoLevelIndex;
			}
			i += oneLevelIndex;
		}
		if (budgetList.size() > 0) {
			this.benefitNoteBudgetDao.update(budgetList);
			flag = true;
		}
		return flag;
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
	public void accumulationBudgetAmount(String budgetAmountKey, Map<String, BenefitAmountAccumulativeBean> contractAmountMap, BenefitNoteBudget budget) {
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

	/**
	 * 设置币别汇率属性值
	 * @param index
	 * @param currencyList
	 * @param rateMap
	 */
	public void switchLoadCurrencyRate(int index, List<BenefitNoteCurrency> currencyList, Map<String, Double> rateMap) {
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
	 * 保存测算预算表
	 */
	public void saveOrUpdateSummaryBudget(BenefitNote benefitNote, Map<String, Object> valueMap) {
		if (valueMap == null || valueMap.isEmpty()) {
			return;
		}

		String noteId = benefitNote.getNoteId();

		// 删除币别
		String delCurrencyIds = (String) valueMap.get("delCurrencyIds");
		if (StringUtils.isNotBlank(delCurrencyIds)) {
			for (String recordId : delCurrencyIds.split(",")) {
				this.benefitNoteCurrencyDao.deleteByProperty("recordId", recordId);
			}
		}

		List<BenefitNoteCurrency> noteCurrencyList;
		// 需要保存的销售合同预算币别
		String currencyListJson = (String) valueMap.get("currencyListJson");
		if (currencyListJson != null) {// 修改币别需要保存
			noteCurrencyList = new ArrayList<BenefitNoteCurrency>();
			List<BenefitNoteCurrency> currencyList = JsonUtil.toList(currencyListJson, BenefitNoteCurrency.class);
			BenefitNoteCurrency saveCurrency;
			
			int orderDisplay = 0;
			// 保存币别
			for (BenefitNoteCurrency currency : currencyList) {
				if (currency.getRecordId().startsWith("rowId_")) {// 多出的币别保存
					saveCurrency = new BenefitNoteCurrency(noteId, orderDisplay);
				} else {
					saveCurrency = this.benefitNoteCurrencyDao.get(currency.getRecordId());
				}
				saveCurrency.setCurrencyId(currency.getCurrencyId());
				saveCurrency.setCurrency(currency.getCurrency());
				if (StringUtils.isNotBlank(saveCurrency.getCurrencyId())) {
					saveCurrency.setRate(currency.getRate());
				} else {
					saveCurrency.setRate(0);
				}
				this.benefitNoteCurrencyDao.saveOrUpdate(saveCurrency);
				noteCurrencyList.add(saveCurrency);
				orderDisplay += 10;
			}
			// 重新给主表赋值币别集
			benefitNote.setCurrencyList(noteCurrencyList);
			benefitNote.setCurrencyNum(noteCurrencyList.size());
		} else {
			noteCurrencyList = this.getCurrencyListByNoteId(noteId);
		}

		// 获取汇率
		Map<String, Double> rateMap = new HashMap<String, Double>();
		double rate1, rate2, rate3, rate4, rate5, rate6, rate7, rate8, rate9;

		switchLoadCurrencyRate(benefitNote.getCurrencyNum(), noteCurrencyList, rateMap);
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

		BenefitNoteBudget saveBudget;// 要保存的预算项
		BenefitNoteBudget saveBudgetParent;// 要保存预算的父级预算
		Map<String, BenefitNoteBudget> saveBudgetMap = new LinkedHashMap<String, BenefitNoteBudget>();
		Map<String, BenefitAmountAccumulativeBean> contractAmountMap = new LinkedHashMap<String, BenefitAmountAccumulativeBean>();

		// 遍历计算
		try {
			for (BenefitAmountBean amount : budgetList) {
				saveBudget = this.benefitNoteBudgetDao.get(amount.getNoteBudgetId());
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
						saveBudgetParent = this.getBenefitNoteBudget(noteId, saveBudget.getParentBudgetId());
						budgetAmountKey = saveBudgetParent.getParentBudgetId();// 一级预算budgetId
						// 累加父级（一级）预算项金额
						accumulationBudgetAmount(budgetAmountKey, contractAmountMap, saveBudget);
					}
				}
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
				saveBudgetParent = this.getBenefitNoteBudget(noteId, parentBudgetId);
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
		params.put("noteId", noteId);
		params.put("budgetId", new String[] { _PROFIT, _PROFIT_RATE, _LOSS, _PROFIT_MARGIN });
		List<BenefitNoteBudget> specialBudgetList = this.benefitNoteBudgetDao.queryByParam(params, null, null);
		for (BenefitNoteBudget budget : specialBudgetList) {
			saveBudgetMap.put(budget.getBudgetId(), budget);
		}

		// 设置占比、毛利等
		int index = 0;
		BenefitNoteBudget reBudget;
		for (Map.Entry<String, BenefitNoteBudget> e : saveBudgetMap.entrySet()) {
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
			List<BenefitNoteBudget> saveBudgetList = new ArrayList<BenefitNoteBudget>(saveBudgetMap.values());
			this.benefitNoteBudgetDao.update(saveBudgetList);
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
		List<BenefitNote> noteList = this.getByBenefitNo(benefitNo);
		for (BenefitNote benefitNote : noteList) {
			benefitNote.setContractId(contractId);
			benefitNote.setContractNo(contractNo);
			benefitNote.setContractName(contractName);
			this.update(benefitNote);
		}
	}

	public void update(BenefitNote benefitNote) {
		this.benefitNoteDao.update(benefitNote);
	}

	/**
	 * 保存完成
	 * @param user
	 * @param benefitNote
	 * @param valueMap
	 * @return
	 */
	public BenefitNote commit(UserProfile user, BenefitNote benefitNote, Map<String, Object> valueMap) {
		// 设置编号
		if (StringUtils.isBlank(benefitNote.getBenefitNo())) {
			benefitNote.setBenefitNo(generatorBenefitNo(benefitNote.getProjectId()));
		}
		saveOrUpdate(user, benefitNote, valueMap);
		if (StringUtils.isBlank(benefitNote.getPrjManagerId())) {
			RegisterProject project = this.registerProjectService.get(benefitNote.getProjectId());
			benefitNote.setPrjManagerId(project.getProjectChargeId());
			benefitNote.setPrjManager(project.getProjectCharge());
		}
		// 设置状态(待确认，流程由系统判断提交)
		benefitNote.setSwfStatus(BenefitNote.CONFIRM);
		this.update(benefitNote);
		// 提交后续操作
		afterCommitExecute(benefitNote);
		// 记录日志
		//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + benefitNote + "}", null, null);
		return benefitNote;
	}

	/**
	 * 由系统提交流程
	 *
	 * @param user
	 * @param benefitNote
	 * @param valueMap
	 * @return
	 */
	public BenefitNote submit(UserProfile user, BenefitNote benefitNote, Map<String, Object> valueMap) {
		// 设置编号
		if (StringUtils.isBlank(benefitNote.getBenefitNo())) {
			benefitNote.setBenefitNo(generatorBenefitNo(benefitNote.getProjectId()));
		}
		saveOrUpdate(user, benefitNote, valueMap);
		// 设置状态(待确认，流程由系统判断提交)
		addReviewExamValue(benefitNote);
		// 提交后续操作
		afterCommitExecute(benefitNote);
		// 记录日志
		//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + benefitNote + "}", null, null);
		return benefitNote;
	}

	/**
	 * 生成编号
	 * @param projectId
	 * @return
	 */
	public synchronized String generatorBenefitNo(String projectId) {
		String benefitNo = null;
		String NoHead = CommonUtil.format(new Date(), "yyyyMM");
		List<BenefitNote> list = this.benefitNoteDao.queryBy("benefitNo", NoHead + "%", true, "benefitNo", false);
		int index = 0;
		if (list != null && list.size() > 0) {
			index = Integer.valueOf(list.get(0).getBenefitNo().substring(NoHead.length()));
		}
		String NoEnd = String.format("%03d", (index + 1));
		benefitNo = NoHead + NoEnd;
		return benefitNo;
	}

	/**
	 * 设置项目累计百分比和特殊项金额/比例
	 * @param noteBudgetList
	 */
	public List<BenefitNoteBudget> loadProportionAndSpecialBudget(String projectId, List<BenefitNoteBudget> noteBudgetList) {
		/** 获取项目已完成预算 **/
		Map<String, BenefitAssembleBean> budgetAssembleMap = new HashMap<String, BenefitAssembleBean>();
		List<VBenefitNoteAssemble> budgetAssembleList = this.vBenefitNoteAssembleDao.queryBy("projectId", projectId, false, "orderDisplay", true);
		for (VBenefitNoteAssemble budgetAssemble : budgetAssembleList) {
			budgetAssembleMap.put(budgetAssemble.getBudgetId(), budgetAssemble);
		}

		Map<String, BenefitNoteBudget> noteBudgetMap = new LinkedHashMap<String, BenefitNoteBudget>();
		Map<String, Double> totalAmountMap = new LinkedHashMap<String, Double>();

		// 添加非叶节点预算项并设置金额为下级金额合计,计算采购合同总金额和费用合计之和
		String _PURCHASE_TOTAL = BenefitBudgetItemConstant.SUMMARY_PURCHASE_TOTAL;
		String _EXPENSE_TOTAL = BenefitBudgetItemConstant.SUMMARY_EXPENSE_TOTAL;
		double sum12 = 0;
		// 遍历合同预算并设置合计为项目已完成预算
		for (BenefitNoteBudget budget : noteBudgetList) {
			String budgetId = budget.getBudgetId();
			// 将本次折人民币金额加到项目合计上(除了特殊项)
			if (budget.getCurrency1Amount() != BenefitBudgetConstant.DEFAULT_VALUE) {
				// 本次可能是第一次所以budgetAssembleMap可能是空集合
				double sumTotalAmount = budgetAssembleMap.get(budgetId) != null ? budgetAssembleMap.get(budgetId).getSumTotalAmount() : 0;
				budget.setSumTotalAmount(sumTotalAmount + budget.getTotalRmbAmount());
			}
			noteBudgetMap.put(budgetId, budget);
			// 添加一级预算项金额
			if (budget.getLevel() == BenefitBudgetConstant.LEVEL_ONE) {
				totalAmountMap.put(budgetId, budget.getSumTotalAmount());
			}
			// 计算采购合同总金额和费用合计之和
			if (budgetId.equals(_PURCHASE_TOTAL) || budgetId.equals(_EXPENSE_TOTAL)) {
				sum12 += budget.getSumTotalAmount();
			}
		}
		// 添加采购合同总金额和费用合计之和
		totalAmountMap.put(_PURCHASE_TOTAL + _EXPENSE_TOTAL, sum12);

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
		BenefitNoteBudget reBudget;
		for (Map.Entry<String, BenefitNoteBudget> e : noteBudgetMap.entrySet()) {
			reBudget = e.getValue();
			if (reBudget.getOrderDisplay() == 0) {// 第一个
				reBudget.setSumTotalProportion(1.0);
			} else if (reBudget.getSumTotalProportion() != BenefitBudgetConstant.DEFAULT_VALUE) {
				// 第一个和无比例项不设置（即第一项下级和第二三项及其下级）
				double total = 0;
				index = reBudget.getOrderDisplay() / BenefitBudgetConstant.ORDER_DISPLAY_ONE;
				if (index == 0) {// 第一项下级（该项折人民币合计/销售合同总金额）
					total = totalAmountMap.get(reBudget.getParentBudgetId());
				} else if (index == 1 || index == 2) {// 第二、三项及下级（该项折人民币合计/(采购合同总金额+费用合计)）
					total = totalAmountMap.get(_PURCHASE_TOTAL + _EXPENSE_TOTAL);
				}
				if (total >= reBudget.getSumTotalAmount() && total > 0) {
					reBudget.setSumTotalProportion(reBudget.getSumTotalAmount() / total);
				}
			} else {
				if (CommonUtil.trim(reBudget.getBudgetName()).equals("毛利")) {// 毛利
					// 毛利 = 销售合同总金额 + 应收出口退税 - 采购合同总金额 - 费用合计
					double profitAmount = 0;
					profitAmount = totalAmountMap.get(_TRADE_TOTAL);
					profitAmount = profitAmount + totalAmountMap.get(_DRAWBACK);
					profitAmount = profitAmount - totalAmountMap.get(_PURCHASE_TOTAL);
					profitAmount = profitAmount - totalAmountMap.get(_EXPENSE_TOTAL);
					reBudget.setSumTotalAmount(profitAmount);
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
						reBudget.setSumTotalAmount(profitAmount / totalTradeAmount);
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
					reBudget.setSumTotalAmount(lossAmount);
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
						reBudget.setSumTotalAmount(lossAmount / totalTradeAmount);
					}
				}
			}
			noteBudgetMap.put(reBudget.getBudgetId(), reBudget);
		}
		List<BenefitNoteBudget> reBudgetList = new ArrayList<BenefitNoteBudget>(noteBudgetMap.values());
		return reBudgetList;
	}

	/**
	 * 提交完成后续操作(删除多于币别，设置项目累计预算)
	 * @param benefitNote
	 */
	public void afterCommitExecute(BenefitNote benefitNote) {
		// 删除多余币别
		List<BenefitNoteCurrency> currencyList = this.getCurrencyListByNoteId(benefitNote.getNoteId());
		int currencyNum = currencyList.size();
		for (BenefitNoteCurrency currency : currencyList) {
			if (StringUtils.isBlank(currency.getCurrencyId())) {
				this.benefitNoteCurrencyDao.delete(currency);
				currencyNum--;
			}
		}
		benefitNote.setCurrencyNum(currencyNum);
		this.benefitNoteDao.update(benefitNote);
		// 设置占比及特殊项金额/比例
		List<BenefitNoteBudget> noteBudgetList = this.getBudgetListByNoteId(benefitNote.getNoteId());
		loadProportionAndSpecialBudget(benefitNote.getProjectId(), noteBudgetList);
		replaceReviewContractNoteId(benefitNote);
	}

	/**
	 * 合同签约评审结束效益测算表变更时添加评审流程属性
	 * @param benefitNote
	 */
	public void addReviewExamValue(BenefitNote benefitNote) {
		String signId = benefitNote.getSignId();
		String classificationId = null;
		String classification = null;
		String versionType = benefitNote.getVersionsType();
		if (versionType.equals(BenefitNoteConstant.VERSIONS_OF_DEPT_REVIEW)) {
			ContractSignDeptReview deptReview = this.contractSignDeptReviewService.get(signId);
			classificationId = deptReview.getProjectClassificationId();
			classification = deptReview.getProjectClassification();
		} else if (versionType.equals(BenefitNoteConstant.VERSIONS_OF_COMPANY_REVIEW)) {
			ContractSignReview review = this.contractSignReviewService.get(signId);
			classificationId = review.getProjectClassificationId();
			classification = review.getProjectClassification();
		}

		// 设置效益测算变更表信息
		benefitNote.setProjectClassificationId(classificationId);
		benefitNote.setProjectClassification(classification);
		// 设置评审金额（折美元）
		if (benefitNote.getReviewAmount() == 0) {
			BenefitNoteBudget noteBudget = this.getBenefitNoteBudget(benefitNote.getNoteId(), BenefitBudgetItemConstant.SUMMARY_TRADE_TOTAL);
			double tradeAmount = noteBudget.getTotalRmbAmount();
			Map<String, String> rateMap = TpcConstant.getCurrencyRateMap();
			// 从码表中取人民币对美元汇率
			double usdRate = 1;
			if (rateMap.containsKey("USD")) {
				usdRate = CommonUtil.parseDouble(rateMap.get("USD").toString(), 1);
			}
			benefitNote.setReviewAmount(tradeAmount / usdRate);
		}
		if (StringUtils.isBlank(benefitNote.getPrjManagerId())) {
			RegisterProject project = this.registerProjectService.get(benefitNote.getProjectId());
			benefitNote.setPrjManagerId(project.getProjectChargeId());
			benefitNote.setPrjManager(project.getProjectCharge());
		}
		benefitNote.setReviewClassification(ContractSignReviewUtil.getReviewClassification(classificationId, benefitNote.getReviewAmount()));
		benefitNote.setHasSwfProc(true);
		this.benefitNoteDao.update(benefitNote);
	}

	/**
	 * 提交待确认修改状态
	 */
	public void confirm(String noteId) {
		Integer swfStatus = BenefitNote.CONFIRM;
		this.benefitNoteDao.updateNoteStatusById(noteId, swfStatus);
	}

	/**
	 * 提交确认中修改状态
	 */
	public void confirming(String noteId) {
		BenefitNote benefitNote = this.get(noteId);
		// 从草稿直接确认中时需要执行提交后方法
		if (benefitNote.getSwfStatus() == BenefitNote.DRAFT) {
			if (StringUtils.isBlank(benefitNote.getBenefitNo())) {
				benefitNote.setBenefitNo(generatorBenefitNo(benefitNote.getProjectId()));
			}
			afterCommitExecute(benefitNote);
		}
		benefitNote.setSwfStatus(BenefitNote.CONFIRMING);
		this.benefitNoteDao.update(benefitNote);
	}

	/**
	 * 提交确认完成修改状态
	 */
	public void confirmed(String noteId) {
		Integer swfStatus = BenefitNote.CONFIRMED;
		this.benefitNoteDao.updateNoteStatusById(noteId, swfStatus);
	}

	/**
	 * 完成后处理业务
	 * @param benefitNote
	 */
	public void completeExam(BenefitNote benefitNote) {
		// 替换效益测算表
		replaceReviewContractNoteId(benefitNote);
	}

	/**
	 * 验证是否提交流程
	 * @param noteId
	 * @return
	 */
	public boolean isSubmitBenefitNote(String noteId) {
		BenefitNote benefitNote = this.get(noteId);
		if (benefitNote.isHasSwfProc() && benefitNote.getSwfStatus() > BenefitNote.CONFIRM) {
			return true;
		}
		return false;
	}

	/**
	 * 确认完成
	 * @param noteId
	 * @param validProc 是否验证有流程
	 * @return
	 */
	public boolean isBenefitNoteConfirmed(String noteId, boolean validProc) {
		BenefitNote benefitNote = this.get(noteId);
		if ((validProc && benefitNote.isHasSwfProc() || !validProc) && benefitNote.getSwfStatus() == BenefitNote.CONFIRMED) {
			return true;
		}
		return false;
	}

	/**
	 * 提交完成后替换评审合同效益测算表
	 * 1.评审提交完成设置合同效益测算单号
	 * 2.效益测算流程完成替换效益测算表信息
	 * @param benefitNote
	 */
	public void replaceReviewContractNoteId(BenefitNote benefitNote) {
		String noteId = benefitNote.getNoteId();
		String versionsType = CommonUtil.trim(benefitNote.getVersionsType());
		String inforId = benefitNote.getInforId();
		if (versionsType.equals(BenefitNoteConstant.VERSIONS_OF_DEPT_REVIEW)) {
			ContractSignDeptInfor infor = this.contractSignDeptReviewService.getContractSignDeptInfor(inforId);
			if (infor != null) {
				if (!infor.getNoteId().equals(noteId)) {
					infor.setChangeBenefit(true);
				}
				infor.setNoteId(noteId);
				infor.setBenefitNo(benefitNote.getBenefitNo());
				this.contractSignDeptReviewService.update(infor);
			}
		} else if (versionsType.equals(BenefitNoteConstant.VERSIONS_OF_COMPANY_REVIEW)) {
			ContractSignInfor infor = this.contractSignReviewService.getContractSignInfor(inforId);
			if (infor != null) {
				if (!infor.getNoteId().equals(noteId)) {
					infor.setChangeBenefit(true);
				}
				infor.setNoteId(noteId);
				infor.setBenefitNo(benefitNote.getBenefitNo());
				this.contractSignReviewService.update(infor);
			}
		}
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param noteIds
	 */
	public void delete(UserProfile user, String noteIds) {
		if (StringUtils.isNotBlank(noteIds)) {
			BenefitNote table;
			for (String noteId : noteIds.split(",")) {
				table = this.get(noteId);
				if (table == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, table);
				this.benefitNoteDao.delete(table);
				this.benefitNoteCurrencyDao.deleteByProperty("noteId", table.getNoteId());
				this.benefitNoteBudgetDao.deleteByProperty("noteId", table.getNoteId());
			}
		}
	}

	/**
	 * 校验合同名称
	 * @param noteId
	 * @param contractName
	 * @return
	 */
	public boolean checkNameIsValid(String noteId, String contractName) {
		List<BenefitNote> list = this.benefitNoteDao.findBy("contractName", contractName);
		// 已存在该合同名称且非本记录
		if (list.size() == 1 && !list.get(0).getNoteId().equals(noteId)) {
			return false;
		}
		return true;
	}

	/**
	 * 获取效益测算表选择记录
	 * @param page
	 * @param pageSize
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public ListPage<BenefitNote> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<BenefitNote> listPage = new ListPage<BenefitNote>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.benefitNoteDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

	/**
	 * 设置属性值
	 * @param budget
	 * @param currencyDesc
	 * @param currencyAmount
	 */
	public void switchLoadBudgetAmount(int index, BenefitNoteBudget budget, Map<String, Object> currencyMap) {
		String currencyDesc = "";
		double currencyAmount = 0;
		switch (index) {
		case 0:
			currencyDesc = budget.getCurrency1AmountDesc();
			currencyAmount = budget.getCurrency1Amount();
			break;
		case 1:
			currencyDesc = budget.getCurrency2AmountDesc();
			currencyAmount = budget.getCurrency2Amount();
			break;
		case 2:
			currencyDesc = budget.getCurrency3AmountDesc();
			currencyAmount = budget.getCurrency3Amount();
			break;
		case 3:
			currencyDesc = budget.getCurrency4AmountDesc();
			currencyAmount = budget.getCurrency4Amount();
			break;
		case 4:
			currencyDesc = budget.getCurrency5AmountDesc();
			currencyAmount = budget.getCurrency5Amount();
			break;
		case 5:
			currencyDesc = budget.getCurrency6AmountDesc();
			currencyAmount = budget.getCurrency6Amount();
			break;
		case 6:
			currencyDesc = budget.getCurrency7AmountDesc();
			currencyAmount = budget.getCurrency7Amount();
			break;
		case 7:
			currencyDesc = budget.getCurrency8AmountDesc();
			currencyAmount = budget.getCurrency8Amount();
			break;
		case 8:
			currencyDesc = budget.getCurrency9AmountDesc();
			currencyAmount = budget.getCurrency9Amount();
			break;
		default:
			break;
		}
		currencyMap.put("currencyDesc", currencyDesc);
		currencyMap.put("currencyAmount", currencyAmount);
	}

	/**
	 * 获取效益测算汇总表工作表
	 * 
	 * @param noteId
	 * @param downFileName
	 * @param user
	 * @return
	 */
	public Workbook getWorkbook(String noteId, String downFileName, UserProfile user) {
		String suffix = downFileName.substring(downFileName.lastIndexOf("."));
		String filePath = BenefitNoteConstant.BENEFIT_NOTE_EXCEL_PATH + BenefitNoteConstant.BENEFIT_NOTE_TEMP0 + suffix;
		Workbook wb = null;
		try {
			wb = ExportUtil.getWorkbook(filePath, suffix);
			Sheet sheet = wb.getSheetAt(0);// 第一个工作表

			// 填充合同预算
			List<BenefitNoteCurrency> currencyList = this.getCurrencyListByNoteId(noteId);
			List<BenefitNoteBudget> budgetList = this.getBudgetListByNoteId(noteId);
			loadBudgetWorkbook(wb, sheet, currencyList, budgetList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

	/**
	 * 填入预算项金额
	 * @param sheet
	 * @param currencyList
	 * @param noteBudgetList
	 * @param startIndex
	 */
	public void loadBudgetWorkbook(Workbook wb, Sheet sheet, List<BenefitNoteCurrency> currencyList, List<BenefitNoteBudget> noteBudgetList, int startIndex) {
		if (noteBudgetList != null && noteBudgetList.size() > 0) {

			CellStyle titleCell = ExportUtil.getTitleCellStyle(wb);// 金额表头

			CellStyle centerCell = ExportUtil.getSimpleCellStyle(wb);
			centerCell.setAlignment(CellStyle.ALIGN_CENTER); // 居中

			CellStyle leftCell = ExportUtil.getSimpleCellStyle(wb);
			leftCell.setAlignment(CellStyle.ALIGN_LEFT); // 居左

			CellStyle rightCell = ExportUtil.getSimpleCellStyle(wb);
			rightCell.setAlignment(CellStyle.ALIGN_RIGHT); // 居右

			int currencySize = currencyList.size();
			int budgetSize = noteBudgetList.size();

			Row row = null;// 行
			Cell cell = null;// 单元格
			BenefitNoteBudget budget = null;
			String serialNumber, budgetName;
			Map<String, Object> currencyMap = new HashMap<String, Object>();

			// 获取第(startIndex+1)行表头
			row = sheet.getRow(startIndex);

			// 设置币别
			for (int i = 0; i < currencySize; i++) {
				//sheet.setColumnWidth(i + 2, 16 * 256);// 第i+2列单元格宽度
				cell = row.createCell(i + 2);
				cell.setCellStyle(titleCell);
				cell.setCellValue(currencyList.get(i).getCurrency());
			}

			// 设置折人民币、占人民币总收入/总支出百分比
			//sheet.setColumnWidth(currencySize + 2, 18 * 256);
			cell = row.createCell(currencySize + 2);
			cell.setCellStyle(titleCell);
			cell.setCellValue(BenefitBudgetConstant.HEAD_TITLE_ZH);
			//sheet.setColumnWidth(currencySize + 3, 18 * 256);
			cell = row.createCell(currencySize + 3);
			cell.setCellStyle(titleCell);
			cell.setCellValue(BenefitBudgetConstant.HEAD_TITLE_ZB);

			// 单元格赋值
			for (int i = 0; i < budgetSize; i++) {
				budget = noteBudgetList.get(i);

				// 从第startIndex+1行开始
				row = sheet.createRow(startIndex + 1 + i);

				// 序号
				cell = row.createCell(0, 1);
				serialNumber = CommonUtil.trim(budget.getSerialNumber()).replaceAll("&ensp;", " ");
				cell.setCellValue(serialNumber);
				cell.setCellStyle(centerCell);

				// 预算说明
				cell = row.createCell(1, 1);
				budgetName = budget.getBudgetName();
				if (budget.getLevel() == BenefitBudgetConstant.LEVEL_TWO) {
					budgetName = "  " + budgetName;
				} else if (budget.getLevel() == BenefitBudgetConstant.LEVEL_THREE) {
					budgetName = "    " + budgetName;
				}
				cell.setCellValue(budgetName);
				cell.setCellStyle(leftCell);

				// 币别
				for (int j = 0; j < currencySize; j++) {
					cell = row.createCell(j + 2, 1);

					// 获取币别属性值
					switchLoadBudgetAmount(j, budget, currencyMap);
					String currencyDesc = currencyMap.containsKey("currencyDesc") ? (String) currencyMap.get("currencyDesc") : "";
					double currencyAmount = currencyMap.containsKey("currencyAmount") ? (Double) currencyMap.get("currencyAmount") : 0;
					cell.setCellValue(currencyDesc);
					if (currencyAmount != -1) {
						cell.setCellStyle(rightCell);
					} else {
						cell.setCellStyle(centerCell);
					}
				}

				// 折人民币合计
				cell = row.createCell(currencySize + 2, 1);
				cell.setCellValue(budget.getTotalRmbAmountDesc());
				cell.setCellStyle(rightCell);

				// 占比
				cell = row.createCell(currencySize + 3, 1);
				cell.setCellValue(budget.getProportionDesc());
				if (budget.getProportion() != -1) {
					cell.setCellStyle(rightCell);
				} else {
					cell.setCellStyle(centerCell);
				}
			}
		}
	}

}
