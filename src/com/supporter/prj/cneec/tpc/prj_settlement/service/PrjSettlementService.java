package com.supporter.prj.cneec.tpc.prj_settlement.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitBudget;
import com.supporter.prj.cneec.tpc.benefit_budget.service.BenefitBudgetService;
import com.supporter.prj.cneec.tpc.prj_contract_settlement.util.CommonUtils;
import com.supporter.prj.cneec.tpc.prj_settlement.dao.PrjSettlementDao;
import com.supporter.prj.cneec.tpc.prj_settlement.dao.PrjSettlementRecDao;
import com.supporter.prj.cneec.tpc.prj_settlement.entity.PrjSettlement;
import com.supporter.prj.cneec.tpc.prj_settlement.entity.PrjSettlementRec;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.util.TpcBudgetUtil;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.util.JsonUtil;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Service
public class PrjSettlementService {

	@Autowired
	private PrjSettlementDao prjSettlementDao;
	@Autowired
	private RegisterProjectService projectService;//项目
	@Autowired
	private PrjSettlementRecDao settlementRecDao;//明细
	@Autowired
	private BenefitBudgetService benefitBudgetService;//执行预算
	
	private int ii_PaymentIndex = 0;//当前的付款单序列号（本年度） .

	/**
	 * 获取审批完成的单子
	 * @return
	 */
	public List<PrjSettlement> getComplete(String prjId, List<String> settlementIds) {
		if(StringUtils.isBlank(prjId)) {
			return null;
		}
		return prjSettlementDao.getComplete(prjId, settlementIds);
		
	}
	
	/**
	 * 分页表格展示数据.
	 * @param user
	 * @param jqGrid
	 * @param abroad
	 * @return
	 */
	public List<PrjSettlement> getGrid(JqGrid jqGrid, String attr, String prjId, String settlementStatus) {
		List<PrjSettlement> list = prjSettlementDao.findPage(jqGrid, attr, prjId, settlementStatus);
		return list;
	}

	/**
	 * 获取明细表
	 * @param jqGrid
	 * @param settlementId
	 * @return
	 */
	public List<PrjSettlementRec> getRecGrid(JqGrid jqGrid,String settlementId){
		List<PrjSettlementRec> list = this.settlementRecDao.getRecGrid(jqGrid, settlementId);
		return list;
	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @return
	 */
	public PrjSettlement initEditOrViewPage(Map<String, Object> paramMap, UserProfile userProfile) {
		String settlementId = (String) paramMap.get("settlementId");
		String projectId = (String) paramMap.get("projectId");
		PrjSettlement settlement = null;
		if(StringUtils.isNotBlank(settlementId)){//修改
			settlement = this.prjSettlementDao.get(settlementId);
		} else {// 新建
			settlement = new PrjSettlement();
			settlement.setPaidById(userProfile.getPersonId());
			settlement.setSettlementId(com.supporter.util.UUIDHex.newId());
			settlement.setPaidBy(userProfile.getName());
			settlement.setPayerDeptId(userProfile.getDeptId());
			settlement.setPaidFlag(false);
			settlement.setSettlementApplyDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
			settlement.setSettlementYear(CommonUtil.parseInt(CommonUtil.format(new Date(), "yyyy")));
			settlement.setSettlementmonth(CommonUtil.parseInt(CommonUtil.format(new Date(), "MM")));
			// 设置付款单序列号(本年度内顺序编号)
//			int li_Index = generateNewIndex();
//			settlement.setSettlementIndex(li_Index);
//			settlement.setSettlementNo("贸非付字(" + Calendar.getInstance().get(Calendar.YEAR) + ")第"
//					+ CommonUtil.format(li_Index, "00000") + "号");
			settlement.setCreatedBy(userProfile.getName());
			settlement.setCreatedById(userProfile.getPersonId());
			settlement.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
			settlement.setSettlementStatus(PrjSettlement.DRAFT);
			if (userProfile.getDept() != null) {
				settlement.setPayerDeptName(userProfile.getDept().getName());
			}
			if (StringUtils.isNotBlank(projectId)) {
				RegisterProject project = this.projectService.get(projectId);
				if (project != null) {
					settlement.setPrjId(project.getProjectId());
					settlement.setPrjName(project.getProjectName());
					settlement.setPrjManagerId(project.getProjectChargeId());
					settlement.setPrjManager(project.getProjectCharge());
				}
			}
		}
		return settlement;
	}

	/**
	 * 打印预览
	 * @return
	 */
	public PrjSettlement viewPrint(String settlementId, UserProfile userProfile) {
		PrjSettlement settlement = this.get(settlementId);
		if (settlement != null) {
			// 从表
			List<PrjSettlementRec> list = this.settlementRecDao.getBySettlementId(settlementId);
			settlement.setMaterialList(list);
		}
		return settlement;
	}

	/**
	 * 设置打印份数
	 * @return
	 */
	public String setPrintCount(String settlementId, UserProfile userProfile) {
		String json = "{\"successful\": false, \"msg\": \"cannot get entity\"}";
		PrjSettlement settlement = this.get(settlementId);
		if (settlement != null) {
			settlement.setPrintCount(settlement.getPrintCount() + 1);
			this.prjSettlementDao.update(settlement);
			json = "{\"successful\": true, \"msg\": \"\"}";
		}
		return json;
	}

	/**
	 * 设置银行出纳
	 * @return
	 */
	public PrjSettlement viewByBankTeller(String settlementId, UserProfile userProfile) {
		PrjSettlement settlement = this.get(settlementId);
		if (settlement != null) {
			if (StringUtils.isBlank(settlement.getBankTellerIds())) {
				settlement.setBankTellerIds(userProfile.getPersonId());
				settlement.setBankTellerNames(userProfile.getName());
				this.prjSettlementDao.update(settlement);
			}
			// 从表
			List<PrjSettlementRec> list = this.settlementRecDao.getBySettlementId(settlementId);
			settlement.setMaterialList(list);
		}
		return settlement;
	}

	/**
	 * 根据当前数据库中的序列号情况以及Manager实例中的序列号情况返回一个新的序列号. 在返回该新的序列号之后，内置的序列号计数器自动加1
	 * 
	 * @return 最新的序列号
	 */
	public int generateNewIndex(){
		int li_LatestIndexInDB = this.prjSettlementDao.getLatestIndexInDB();
        if (li_LatestIndexInDB > this.ii_PaymentIndex) ii_PaymentIndex = li_LatestIndexInDB;
        ii_PaymentIndex++;
        return ii_PaymentIndex;
	}

    /**
     * 获取预算项数据
     * @param codetableId
     * @return
     */
    public List<Double> getBudgetById(String prjId,String itemId){
    	//项目ID
    	List<Double> list = new ArrayList<Double>();
    	BenefitBudget benefitBudget = benefitBudgetService.getBenefitBudget(prjId,itemId);
    	double budgetAmount = benefitBudget.getTotalBudgetAmount();
    	double availableAmount = benefitBudget.getAvailableBudgetAmount();
    	double usableAmount = budgetAmount - availableAmount;
    	list.add(budgetAmount);
    	list.add(usableAmount);
    	list.add(availableAmount);
    	return list;
    }
    
    /**
	 * 根据费用支付实例获取它的所有结成功的结算单金额总和.
	 * 
	 * @author ts
	 * @param PrjContract
	 *            合同实例.
	 * @return 本合同的结算总金额.
	 */
	public double getTotalSettlementAmountAct(String prjId) {
		double act = 0;
		List<PrjSettlement> list = this.prjSettlementDao.getTotalSettlementAmountAct(prjId);
		for (PrjSettlement prjSettlement : list) {
			act = act + prjSettlement.getOnWayAmount() + prjSettlement.getRealSettlementAmount();
		}
		return act;
	}
    
    /**
	 * 删除
	 * @param user
	 * @param settlementIds
	 */
	public void delete(UserProfile user, String settlementIds) {
		if (StringUtils.isNotBlank(settlementIds)) {
			for (String settlementId : settlementIds.split(",")) {
				PrjSettlement settlement = this.prjSettlementDao.get(settlementId);
				List<PrjSettlementRec> list = this.settlementRecDao.getBySettlementId(settlementId);
				if(list!= null && list.size()>0){
					this.settlementRecDao.delete(list);
				}
				this.prjSettlementDao.delete(settlement);
			}
		}
	}

	/**
	 * 保存或更新
	 * @param user
	 * @param prjSettlement
	 * @return
	 */
	public PrjSettlement saveOrUpdate(UserProfile user,PrjSettlement prjSettlement) {
		prjSettlement.setModifiedBy(user.getName());
		prjSettlement.setModifiedById(user.getPersonId());
		prjSettlement.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
		prjSettlement.setSettlementApplyDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
		List<PrjSettlementRec> list = prjSettlement.getMaterialList();
		double settlementAmount = 0;
		Map<String, String> currency = TpcConstant.getCurrencyMap();
		if (list != null && list.size() > 0) {
			for(int i = 0; i<list.size();i++){
				PrjSettlementRec rec = list.get(i);
				settlementAmount += rec.getSettlementAmount();
				rec.setSettlementId(prjSettlement.getSettlementId());
				rec.setCurrencyType(currency.get(rec.getCurrencyTypeCode()));
				list.set(i, rec);
			}
		}
		prjSettlement.setSettlementAmount(settlementAmount);
		this.prjSettlementDao.saveOrUpdate(prjSettlement);
		List<PrjSettlementRec> befList = this.settlementRecDao.getBySettlementId(prjSettlement.getSettlementId());
		if (befList != null && befList.size() > 0) {// 删除之前的明细
			this.settlementRecDao.delete(befList);
		}
		if (list != null && list.size() > 0) {
			this.settlementRecDao.save(list);
		}
		return prjSettlement;
	}

	/**
	 * 提交
	 * successful保存成功，body对象，valid验证对象信息，existBudget存在该预算，validBudget验证预算，msg未通过验证提示信息
	 * @param user
	 * @param prjSettlement
	 * @return
	 */
	public String commit(UserProfile user, PrjSettlement prjSettlement) {
		String body = JsonUtil.toJson(prjSettlement);
		String json = "";
		int li_Index = generateNewIndex();
		prjSettlement.setSettlementIndex(li_Index);
		prjSettlement.setSettlementNo("贸非付字(" + Calendar.getInstance().get(Calendar.YEAR) + ")第"
				+ CommonUtil.format(li_Index, "00000") + "号");
		saveOrUpdate(user, prjSettlement);
		// 验证是否可以提交
		String prjId = CommonUtil.trim(prjSettlement.getPrjId());
		String settlementId = CommonUtil.trim(prjSettlement.getSettlementId());
		double settlementAmount = prjSettlement.getSettlementAmount();
		if (prjId.length() == 0 || settlementId.length() == 0 || settlementAmount == 0) {
			json = "{\"successful\": true,\"body\": " + body + ",\"valid\": false,\"msg\": \"cannotValid\"}";
		} else {
			String result = verificationSubmit(prjId, prjSettlement);
			json = "{\"successful\": true,\"body\": " + body + ",\"valid\": true," + result + "}";
		}
		return json;
	}

	/**
	 * 验证是否可以提交
	 *
	 * @param projectId
	 * @param prjSettlement
	 * @return
	 */
	public String verificationSubmit(String projectId, PrjSettlement prjSettlement) {
		String result = "\"existBudget\": true,\"validBudget\": true,\"msg\": \"\"";
		// 获取所有预算
		Map<String, BenefitBudget> budgetMap = TpcBudgetUtil.getBenefitBudgetMap(projectId);
		if (budgetMap.size() > 0) {
			Map<String, Double> budgetAmountMap = new HashMap<String, Double>();
			// 获取本次付款明细集合
			List<PrjSettlementRec> prjSettlementRecs = prjSettlement.getMaterialList();
			String budgetId;
			// 本次单预算项付款金额
			double sumSettlementAmount = 0d;
			// 遍历付款按预算项计算合计
			for (PrjSettlementRec settlementRec : prjSettlementRecs) {
				budgetId = settlementRec.getBudgetId();
				// 有效预算金额
				double availableAmount = 0d;
				// 获取单预算项在主合同预算金额
				BenefitBudget budget = budgetMap.get(budgetId);
				if (budget != null) {
					availableAmount = budget.getAvailableBudgetAmount();
				} else {
					result = "\"existBudget\": false,\"validBudget\": false,\"msg\": \"noBudget\"";
					break;
				}
				// 累计预算付款金额
				if (budgetAmountMap.containsKey(budgetId)) {
					sumSettlementAmount += settlementRec.getSettlementAmount();
				} else {
					sumSettlementAmount = settlementRec.getSettlementAmount();
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

	/**
	 * 启动流程操作
	 * @param prjSettlement
	 */
	public void startProc(PrjSettlement prjSettlement) {
		prjSettlement.setSettlementStatus(PrjSettlement.PROCESSING);
		// 锁主表预算
		prjSettlement.setOnWayAmount(prjSettlement.getSettlementAmount());
		prjSettlement.setRealSettlementAmount(0);
		this.update(prjSettlement);

		// 锁从表预算
		UserProfile user = TpcCommonUtil.getUserProfile(prjSettlement.getCreatedById());
		String projectId = prjSettlement.getPrjId();
		List<PrjSettlementRec> recList = this.settlementRecDao.findBy("settlementId", prjSettlement.getSettlementId());
		for (PrjSettlementRec prjSettlementRec : recList) {
			// 明细锁在途
			prjSettlementRec.setOnWayAmount(prjSettlementRec.getSettlementAmount());
			prjSettlementRec.setOnWayAmountF(prjSettlementRec.getSettlementAmountF());
			this.settlementRecDao.update(prjSettlementRec);
			// 锁项目预算
			this.addOnwayBudgetAmount(user, projectId, prjSettlementRec.getBudgetId(), prjSettlementRec.getSettlementAmount());
		}
	}

	/**
	 * 中止流程操作
	 * @param prjSettlement
	 */
	public void abortProc(PrjSettlement prjSettlement) {
		prjSettlement.setSettlementStatus(PrjSettlement.DRAFT);
		// 解锁主表预算
		prjSettlement.setOnWayAmount(0);
		this.update(prjSettlement);

		// 解锁从表预算
		UserProfile user = TpcCommonUtil.getUserProfile(prjSettlement.getCreatedById());
		String projectId = prjSettlement.getPrjId();
		List<PrjSettlementRec> recList = this.settlementRecDao.findBy("settlementId", prjSettlement.getSettlementId());
		for (PrjSettlementRec prjSettlementRec : recList) {
			// 明细解锁在途
			prjSettlementRec.setOnWayAmount(0);
			prjSettlementRec.setOnWayAmountF(0);
			this.settlementRecDao.update(prjSettlementRec);
			// 解锁项目预算
			this.removeOnwayBudgetAmount(user, projectId, prjSettlementRec.getBudgetId(), prjSettlementRec.getSettlementAmount());
		}
	}

	/**
	 * 审批完成执行操作
	 * @param prjSettlement
	 */
	public void completeExam(PrjSettlement prjSettlement) {
		// 设置付款状态为支付完毕
		prjSettlement.setRealPaymentDate(CommonUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
		prjSettlement.setPaymentStatus(PrjSettlement._COMPLETED);
		prjSettlement.setPaidFlag(true);
		// 设置审批完成
		prjSettlement.setSettlementStatus(PrjSettlement.COMPLETE);
		prjSettlement.setConfirmDate(CommonUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
		// 清除在途
		prjSettlement.setOnWayAmount(0);
		
		prjSettlement.setRealSettlementAmount(prjSettlement.getSettlementAmount());
		this.update(prjSettlement);
		
		// 刷新实际支付情况（付款明细）
		UserProfile user = TpcCommonUtil.getUserProfile(prjSettlement.getCreatedById());
		String projectId = prjSettlement.getPrjId();
		List<PrjSettlementRec> recList = this.settlementRecDao.findBy("settlementId", prjSettlement.getSettlementId());
		for (PrjSettlementRec prjSettlementRec : recList) {
			// 明细解锁在途
			prjSettlementRec.setOnWayAmount(0);
			prjSettlementRec.setOnWayAmountF(0);
			//人民币
			prjSettlementRec.setRealSettlementAmount(prjSettlementRec.getSettlementAmount());
			//原币
			prjSettlementRec.setRealSettlementAmountF(prjSettlementRec.getSettlementAmountF());
			prjSettlementRec.setRealCurrencyType(CommonUtil.trim(prjSettlementRec.getCurrencyType()));
			prjSettlementRec.setRealCurrencyTypeCode(CommonUtil.trim(prjSettlementRec.getCurrencyTypeCode()));
			prjSettlementRec.setRealSettlementAmountPF(prjSettlementRec.getSettlementAmountF());
			this.settlementRecDao.update(prjSettlementRec);
			// 项目预算在途转执行
			this.transferToExecute(user, projectId, prjSettlementRec.getBudgetId(), prjSettlementRec.getSettlementAmount());
		}
	}

	/**
	 * 加锁预算
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void addOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.addOnwayBudgetAmount(TpcBudgetUtil.TPC_PRJ_SETTLEMENT, user, projectId, budgetId, amount);
	}

	/**
	 * 解锁预算
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void removeOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.removeOnwayBudgetAmount(TpcBudgetUtil.TPC_PRJ_SETTLEMENT, user, projectId, budgetId, amount);
	}

	/**
	 * 在途转执行
	 * 
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void transferToExecute(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.transferToExecute(TpcBudgetUtil.TPC_PRJ_SETTLEMENT, user, projectId, budgetId, amount);
	}

	public PrjSettlement get(String settlementId){
		PrjSettlement settlement = this.prjSettlementDao.get(settlementId);
		if (StringUtils.isBlank(settlement.getPrjManagerId())) {
			RegisterProject project = this.projectService.get(settlement.getPrjId());
			if (project != null) {
				settlement.setPrjManagerId(project.getProjectChargeId());
				settlement.setPrjManager(project.getProjectCharge());
			}
		}
		return settlement;
	}
	
	public void update(PrjSettlement prjSettlement){
		this.prjSettlementDao.update(prjSettlement);
	}

	/**
	 * 保存或更新特殊审批
	 * @param settlementId
	 * @param isFullSwf
	 * @return
	 */
	public PrjSettlement saveIsFullSwf(String settlementId, String isFullSwf) {
		PrjSettlement prjSettlement = this.get(settlementId);
		if(StringUtils.isNotBlank(isFullSwf)){
			prjSettlement.setIsFillSwf(Integer.parseInt(isFullSwf.trim()));
		}
		this.update(prjSettlement);
		return prjSettlement;
	}

	/**
	 * 获取历次付款用途
	 * @return
	 */
	public Map<String, String> getRemittancePurposeData() {
		List<String> list = this.prjSettlementDao.getRemittancePurpose();
		Map<String, String> map = new HashMap<String, String>();
		for (String str : list) {
			map.put(str, str);
		}
		return map;
	}

}
