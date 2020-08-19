package com.supporter.prj.cneec.tpc.tariff_vat_payment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.tariff_vat_payment.dao.TariffVatPaymentDao;
import com.supporter.prj.cneec.tpc.tariff_vat_payment.entity.TariffVatPayment;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.TpcBudgetUtil;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: TariffVatPaymentService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class TariffVatPaymentService {

	@Autowired
	private TariffVatPaymentDao tariffVatPaymentDao;
	@Autowired
	private RegisterProjectService registerProjectService;

	/**
	 * 获取审批完成的单子
	 * @param prjId 
	 * @param list 
	 * @return
	 */
	public List<TariffVatPayment> getComplete(String projectId, List<String> paymentIds){
		if(StringUtils.isBlank(projectId)) {
			return null;
		}
		return tariffVatPaymentDao.getComplete(projectId, paymentIds);
				
	}
	
	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, TariffVatPayment.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param tariffVatPayment
	 */
	public void getAuthCanExecute(UserProfile userProfile, TariffVatPayment tariffVatPayment) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, TariffVatPayment.MODULE_ID, tariffVatPayment.getPaymentId(), tariffVatPayment);
	}

	/**
	 * 获取进口关税和增值税对象集合
	 * @param user
	 * @param jqGrid
	 * @param tariffVatPayment
	 * @return
	 */
	public List<TariffVatPayment> getGrid(UserProfile user, JqGrid jqGrid, TariffVatPayment tariffVatPayment) {
		String authFilter = getAuthFilter(user);
		return this.tariffVatPaymentDao.findPage(jqGrid, tariffVatPayment, authFilter);
	}

	/**
	 * 获取单个进口关税和增值税对象
	 * @param paymentId
	 * @return
	 */
	public TariffVatPayment get(String paymentId) {
		return this.tariffVatPaymentDao.get(paymentId);
	}

	/**
	 * 新建进口关税和增值税对象
	 * @param user
	 * @return
	 */
	public TariffVatPayment newTariffVatPayment(UserProfile user) {
		TariffVatPayment tariffVatPayment = new TariffVatPayment();
		loadTariffVatPayment(tariffVatPayment, user);
		tariffVatPayment.setCurrencyId("CNY");
		tariffVatPayment.setCurrency("人民币");
		tariffVatPayment.setSwfStatus(TariffVatPayment.DRAFT);
		return tariffVatPayment;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public TariffVatPayment loadTariffVatPayment(TariffVatPayment tariffVatPayment, UserProfile user) {
		tariffVatPayment.setCreatedBy(user.getName());
		tariffVatPayment.setCreatedById(user.getPersonId());
		tariffVatPayment.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		tariffVatPayment.setModifiedBy(user.getName());
		tariffVatPayment.setModifiedById(user.getPersonId());
		tariffVatPayment.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			tariffVatPayment.setDeptName(dept.getName());
			tariffVatPayment.setDeptId(dept.getDeptId());
		}
		// 设置状态
		tariffVatPayment.setSwfStatus(TariffVatPayment.DRAFT);
		return tariffVatPayment;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param paymentId
	 * @param user
	 * @return
	 */
	public TariffVatPayment initEditOrViewPage(String paymentId, UserProfile user) {
		TariffVatPayment tariffVatPayment;
		if (StringUtils.isBlank(paymentId)) {
			tariffVatPayment = newTariffVatPayment(user);
			tariffVatPayment.setPaymentId(UUIDHex.newId());
			tariffVatPayment.setAdd(true);
		} else {
			tariffVatPayment = (TariffVatPayment) this.tariffVatPaymentDao.get(paymentId);
			tariffVatPayment.setAdd(false);
		}
		return tariffVatPayment;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param tariffVatPayment
	 * @param valueMap
	 * @return
	 */
	public TariffVatPayment saveOrUpdate(UserProfile user, TariffVatPayment tariffVatPayment, Map<String, Object> valueMap) {
		if (tariffVatPayment.getAdd()) {
			// 装配基础信息
			loadTariffVatPayment(tariffVatPayment, user);
			this.tariffVatPaymentDao.save(tariffVatPayment);
		} else {
			// 设置更新时间
			tariffVatPayment.setModifiedBy(user.getName());
			tariffVatPayment.setModifiedById(user.getPersonId());
			tariffVatPayment.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.tariffVatPaymentDao.update(tariffVatPayment);
		}
		return tariffVatPayment;
	}

	/**
	 * 保存提交
	 * @param user
	 * @param tariffVatPayment
	 * @param valueMap
	 * @return
	 */
	public TariffVatPayment commit(UserProfile user, TariffVatPayment tariffVatPayment, Map<String, Object> valueMap) {
		// 提交时生成单号
		if (StringUtils.isBlank(tariffVatPayment.getPaymentNo())) {
			tariffVatPayment.setPaymentNo(generatorPaymentNo());
		}
		saveOrUpdate(user, tariffVatPayment, valueMap);
		commitExcute(tariffVatPayment);
		// 记录日志
//		String logMessage = MessageFormat.format(LogConstant.PUBLISH_LOG_MESSAGE_TARIFFVATPAYMENT, tariffVatPayment.getContractName());
//		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS_TARIFFVATPAYMENT).info(user, LogConstant.PUBLISH_LOG_ACTION_TARIFFVATPAYMENT, logMessage, tariffVatPayment, null);
		return tariffVatPayment;
	}

	/**
	 * 设置审批变量信息
	 * @param tariffVatPayment
	 */
	public void commitExcute(TariffVatPayment tariffVatPayment) {
		String projectId = tariffVatPayment.getProjectId();
		// 设置项目经理
		if (StringUtils.isBlank(tariffVatPayment.getPrjManagerId())) {
			RegisterProject project = this.registerProjectService.get(projectId);
			tariffVatPayment.setPrjManagerId(project.getProjectChargeId());
			tariffVatPayment.setPrjManager(project.getProjectCharge());
			this.update(tariffVatPayment);
		}
	}

	/**
	 * 启动流程后执行操作
	 * @param tariffVatPayment
	 */
	public void startProc(TariffVatPayment tariffVatPayment) {
		tariffVatPayment.setSwfStatus(TariffVatPayment.PROCESSING);
		this.update(tariffVatPayment);
		// 将退税金额写入项目在途
		UserProfile user = TpcCommonUtil.getUserProfile(tariffVatPayment.getCreatedById());
		double amount = tariffVatPayment.getPaymentAmount();
		this.addOnwayBudgetAmount(user, tariffVatPayment.getProjectId(), tariffVatPayment.getPaymentType(), amount);
	}

	/**
	 * 中止流程处理操作
	 * @param tariffVatPayment
	 */
	public void abortProc(TariffVatPayment tariffVatPayment) {
		tariffVatPayment.setSwfStatus(TariffVatPayment.DRAFT);
		this.update(tariffVatPayment);
		// 将退税金额移除项目在途
		UserProfile user = TpcCommonUtil.getUserProfile(tariffVatPayment.getCreatedById());
		double amount = tariffVatPayment.getPaymentAmount();
		this.removeOnwayBudgetAmount(user, tariffVatPayment.getProjectId(), tariffVatPayment.getPaymentType(), amount);
	}

	/**
	 * 审批完成执行操作
	 * @param tariffVatPayment
	 */
	public void completeExam(TariffVatPayment tariffVatPayment) {
		tariffVatPayment.setSwfStatus(TariffVatPayment.COMPLETED);
		this.update(tariffVatPayment);
		// 将退税金额转为执行金额
		UserProfile user = TpcCommonUtil.getUserProfile(tariffVatPayment.getCreatedById());
		double amount = tariffVatPayment.getPaymentAmount();
		this.transferToExecute(user, tariffVatPayment.getProjectId(), tariffVatPayment.getPaymentType(), amount);
	}

	/**
	 * 在途金额增加
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void addOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.addOnwayBudgetAmount(TpcBudgetUtil.TPC_TARIFF_VAT_PAYMENT, user, projectId, budgetId, amount);
	}

	/**
	 * 在途金额减少
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void removeOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.removeOnwayBudgetAmount(TpcBudgetUtil.TPC_TARIFF_VAT_PAYMENT, user, projectId, budgetId, amount);
	}

	/**
	 * 在途转执行
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void transferToExecute(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.transferToExecute(TpcBudgetUtil.TPC_TARIFF_VAT_PAYMENT, user, projectId, budgetId, amount);
	}

	/**
	 * 生成记录编号
	 * @return
	 */
	public synchronized String generatorPaymentNo() {
		String paymentNo;
		String NoHead = CommonUtil.format(new Date(), "yyyyMM");
		List<TariffVatPayment> list = this.tariffVatPaymentDao.queryBy("paymentNo", NoHead + "%", true, "paymentNo", false);
		int index = 0;
		if (list != null && list.size() > 0) {
			index = Integer.valueOf(list.get(0).getPaymentNo().substring(NoHead.length()));
		}
		String NoEnd = String.format("%03d", (index + 1));
		paymentNo = NoHead + NoEnd;
		return paymentNo;
	}

	/**
	 * 更新对象
	 * @param tariffVatPayment
	 * @return
	 */
	public TariffVatPayment update(TariffVatPayment tariffVatPayment) {
		this.tariffVatPaymentDao.update(tariffVatPayment);
		return tariffVatPayment;
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param paymentIds
	 */
	public void delete(UserProfile user, String paymentIds) {
		if (StringUtils.isNotBlank(paymentIds)) {
			TariffVatPayment tariffVatPayment;
			for (String paymentId : paymentIds.split(",")) {
				tariffVatPayment = this.get(paymentId);
				if (tariffVatPayment == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, tariffVatPayment);
				this.tariffVatPaymentDao.delete(tariffVatPayment);
			}
		}
	}

	public List<CheckBoxVO> getPaymentTypeList(String paymentId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String choose = "";// 不设置默认
		if (paymentId.length() > 0) {
			TariffVatPayment tariffVatPayment = this.get(paymentId);
			if (tariffVatPayment != null) {
				choose = tariffVatPayment.getPaymentType();
			}
		}
		Map<String, String> map = TariffVatPayment.getPaymentTypeMap();
		for (String key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("paymentType_" + key, "paymentType", key.toString(), map.get(key), key.equals(choose));
			list.add(vo);
		}
		return list;
	}

	/**
	 * 获取历次付款用途
	 * @return
	 */
	public Map<String, String> getRemittancePurposeData() {
		List<String> list = this.tariffVatPaymentDao.getRemittancePurpose();
		Map<String, String> map = new HashMap<String, String>();
		for (String str : list) {
			map.put(str, str);
		}
		return map;
	}

	/**
	 * 设置打印次数
	 * @param paymentId
	 * @param userProfile
	 * @return
	 */
	public TariffVatPayment setPrintCount(String paymentId) {
		TariffVatPayment payment = this.get(paymentId);
		if (payment != null) {
			payment.setPrintCount(payment.getPrintCount() + 1);
			this.update(payment);
		}
		return payment;
	}

}
