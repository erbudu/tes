package com.supporter.prj.cneec.tpc.derivative_contract.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitBudget;
import com.supporter.prj.cneec.tpc.benefit_budget.service.BenefitBudgetService;
import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.contract_online_prepare.service.ContractOnlinePrepareService;
import com.supporter.prj.cneec.tpc.derivative_contract.dao.DerivativeContractDao;
import com.supporter.prj.cneec.tpc.derivative_contract.entity.DerivativeContract;
import com.supporter.prj.cneec.tpc.derivative_contract.util.DerivativeContractUtil;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.TpcBudgetUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: DerivativeContractService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2018-05-21
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class DerivativeContractService {

	@Autowired
	private DerivativeContractDao derivativeContractDao;

	@Autowired
	private ContractOnlinePrepareService contractOnlinePrepareService;
	@Autowired
	private BenefitBudgetService benefitBudgetService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, DerivativeContract.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param derivativeContract
	 */
	public void getAuthCanExecute(UserProfile userProfile, DerivativeContract derivativeContract) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, DerivativeContract.MODULE_ID, derivativeContract.getDerivativeId(), derivativeContract);
	}

	/**
	 * 获取衍生合同单对象集合
	 * @param user
	 * @param jqGrid
	 * @param derivativeContract
	 * @return
	 */
	public List<DerivativeContract> getGrid(UserProfile user, JqGrid jqGrid, DerivativeContract derivativeContract) {
		String authFilter = getAuthFilter(user);
		return this.derivativeContractDao.findPage(jqGrid, derivativeContract, authFilter);
	}
	
	/**
	 * 获取衍生合同单对象集合
	 * @param user
	 * @param jqGrid
	 * @param derivativeContract
	 * @return
	 */ 
	public ListPage<DerivativeContract> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<DerivativeContract> listPage = new ListPage<DerivativeContract>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.derivativeContractDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

	/**
	 * 获取单个衍生合同单对象
	 * @param derivativeId
	 * @return
	 */
	public DerivativeContract get(String derivativeId) {
		return this.derivativeContractDao.get(derivativeId);
	}

	/**
	 * 新建衍生合同单对象
	 * @param user
	 * @return
	 */
	public DerivativeContract newDerivativeContract(UserProfile user) {
		DerivativeContract derivativeContract = new DerivativeContract();
		loadDerivativeContract(derivativeContract, user);
		derivativeContract.setSwfStatus(DerivativeContract.DRAFT);
		return derivativeContract;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public DerivativeContract loadDerivativeContract(DerivativeContract derivativeContract, UserProfile user) {
		derivativeContract.setCreatedBy(user.getName());
		derivativeContract.setCreatedById(user.getPersonId());
		derivativeContract.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		derivativeContract.setModifiedBy(user.getName());
		derivativeContract.setModifiedById(user.getPersonId());
		derivativeContract.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			derivativeContract.setDeptName(dept.getName());
			derivativeContract.setDeptId(dept.getDeptId());
		}
		// 设置状态
		derivativeContract.setSwfStatus(DerivativeContract.DRAFT);
		return derivativeContract;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param derivativeId
	 * @param user
	 * @return
	 */
	public DerivativeContract initEditOrViewPage(String derivativeId, UserProfile user) {
		DerivativeContract derivativeContract;
		if (StringUtils.isBlank(derivativeId)) {
			derivativeContract = newDerivativeContract(user);
			derivativeContract.setDerivativeId(UUIDHex.newId());
			derivativeContract.setAdd(true);
		} else {
			derivativeContract = (DerivativeContract) this.derivativeContractDao.get(derivativeId);
			derivativeContract.setAdd(false);
		}
		return derivativeContract;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param derivativeContract
	 * @param valueMap
	 * @return
	 */
	public DerivativeContract saveOrUpdate(UserProfile user, DerivativeContract derivativeContract, Map<String, Object> valueMap) {
		if (derivativeContract.getAdd()) {
			// 装配基础信息
			loadDerivativeContract(derivativeContract, user);
			this.derivativeContractDao.save(derivativeContract);
		} else {
			// 设置更新时间
			derivativeContract.setModifiedBy(user.getName());
			derivativeContract.setModifiedById(user.getPersonId());
			derivativeContract.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.derivativeContractDao.update(derivativeContract);
		}
		return derivativeContract;
	}

	/**
	 * 完成衍生合同记录单
	 * @param derivativeContract
	 */
	public void finish(UserProfile user, DerivativeContract derivativeContract) {
		// 设置单号
		derivativeContract.setDerivativeNo(generatorDerivativeNo(derivativeContract.getProjectId()));
		// 状态置为完成
		derivativeContract.setSwfStatus(DerivativeContract.COMPLETED);
		this.derivativeContractDao.update(derivativeContract);
		// 写入选择合同记录单
		this.contractOnlinePrepareService.createdByDerivativeContract(derivativeContract);

		// 合同金额写入在途
		String projectId = derivativeContract.getProjectId();
		// 衍生合同扣除预算即为费用合计下衍生合同付款类型
		String budgetId = derivativeContract.getPaymentType();
		// 扣除预算时按原币*执行汇率折合人民币计算（执行汇率效益测算表中对应币别汇率，如果该币别在效益测算表中不存在由则业务人员输入）
		double amount = derivativeContract.getPaymentAmount() * derivativeContract.getExecuteRate();
		this.addOnwayBudgetAmount(user, projectId, budgetId, amount);
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
		TpcBudgetUtil.addOnwayBudgetAmount(TpcBudgetUtil.TPC_DERIVATIVE_CONTRACT, user, projectId, budgetId, amount);
	}

	/**
	 * 保存提交
	 * @param user
	 * @param derivativeContract
	 * @param valueMap
	 * @return
	 */
	public DerivativeContract commit(UserProfile user, DerivativeContract derivativeContract, Map<String, Object> valueMap) {
		saveOrUpdate(user, derivativeContract, valueMap);
		finish(user, derivativeContract);
		// 记录日志
		//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + derivativeContract + "}", null, null);
		return derivativeContract;
	}

	/**
	 * 
	 * @param user
	 * @param derivativeContract
	 * @param valueMap
	 * @return
	 */
	public String validCommit(UserProfile user, DerivativeContract derivativeContract, Map<String, Object> valueMap) {
		String json = "{\"success\": true,\"existBudget\": true,\"validBudget\": true,\"msg\": \"\"}";
		String projectId = derivativeContract.getProjectId();
		String budgetId = derivativeContract.getPaymentType();
		BenefitBudget budget = this.benefitBudgetService.getBenefitBudget(projectId, budgetId);
		if (budget != null) {
			// 合同金额
			double amount = derivativeContract.getPaymentAmount() * derivativeContract.getExecuteRate();
			if (amount > budget.getAvailableBudgetAmount()) {
				// 合同金额超出项目当前可用预算
				json = "{\"success\": true,\"existBudget\": true,\"validBudget\": false,\"msg\": \"derivativeContract-outAvailableAmount\"}";
			}
		} else {
			// 无预算项不可提交
			json = "{\"success\": true,\"existBudget\": false,\"validBudget\": false,\"msg\": \"derivativeContract-noBudget\"}";
		}
		return json;
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param derivativeIds
	 */
	public void delete(UserProfile user, String derivativeIds) {
		if (StringUtils.isNotBlank(derivativeIds)) {
			DerivativeContract derivativeContract;
			for (String derivativeId : derivativeIds.split(",")) {
				derivativeContract = this.get(derivativeId);
				if (derivativeContract == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, derivativeContract);
				this.derivativeContractDao.delete(derivativeContract);
			}
		}
	}

	/**
	 * 生成衍生合同单号
	 * @param projectId
	 * @return
	 */
	public synchronized String generatorDerivativeNo(String projectId) {
		String derivativeNo;
		String NoHead = CommonUtil.format(new Date(), "yyyyMM");
		// 过滤条件
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("derivativeNo", NoHead + "%");
		List<String> likeSearhNames = new ArrayList<String>();
		likeSearhNames.add("derivativeNo");
		Integer count = this.derivativeContractDao.find(params, likeSearhNames, null).size();
		String NoEnd = String.format("%03d", count + 1);
		derivativeNo = NoHead + NoEnd;
		return derivativeNo;
	}

	/**
	 * 合同付款类型单选
	 * @param derivativeId
	 * @return
	 */
	public List<CheckBoxVO> getPaymentTypeList(String derivativeId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String choose = "";// 不设置默认
		if (derivativeId.length() > 0) {
			DerivativeContract contract = this.get(derivativeId);
			if (contract != null) {
				choose = contract.getPaymentType();
			}
		}
		Map<String, String> map = DerivativeContractUtil.getPaymentTypeMap();
		for (String key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("paymentType_" + key, "paymentType", key, map.get(key), key.equals(choose));
			list.add(vo);
		}
		return list;
	}

}
