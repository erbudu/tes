package com.supporter.prj.cneec.tpc.drawback.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.benefit_budget.constant.BenefitBudgetItemConstant;
import com.supporter.prj.cneec.tpc.benefit_budget.entity.BenefitBudget;
import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.constant.LogConstant;
import com.supporter.prj.cneec.tpc.drawback.dao.DrawbackDao;
import com.supporter.prj.cneec.tpc.drawback.entity.Drawback;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.TpcBudgetUtil;
import com.supporter.prj.cneec.tpc.util.TpcCommonUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: DrawbackService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-11-20
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class DrawbackService {

	@Autowired
	private DrawbackDao drawbackDao;

	@Autowired
	private RegisterProjectService registerProjectService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, Drawback.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param drawback
	 */
	public void getAuthCanExecute(UserProfile userProfile, Drawback drawback) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, Drawback.MODULE_ID, drawback.getDrawbackId(), drawback);
	}

	/**
	 * 获取退税对象集合
	 * @param user
	 * @param jqGrid
	 * @param drawback
	 * @return
	 */
	public List<Drawback> getGrid(UserProfile user, JqGrid jqGrid, Drawback drawback) {
		String authFilter = getAuthFilter(user);
		return this.drawbackDao.findPage(jqGrid, drawback, authFilter);
	}

	/**
	 * 获取单个退税对象
	 * @param drawbackId
	 * @return
	 */
	public Drawback get(String drawbackId) {
		return this.drawbackDao.get(drawbackId);
	}

	/**
	 * 新建退税对象
	 * @param user
	 * @return
	 */
	public Drawback newDrawback(UserProfile user) {
		Drawback drawback = new Drawback();
		loadDrawback(drawback, user);
		drawback.setSwfStatus(Drawback.DRAFT);
		return drawback;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public Drawback loadDrawback(Drawback drawback, UserProfile user) {
		drawback.setCreatedBy(user.getName());
		drawback.setCreatedById(user.getPersonId());
		drawback.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		drawback.setModifiedBy(user.getName());
		drawback.setModifiedById(user.getPersonId());
		drawback.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			drawback.setDeptName(dept.getName());
			drawback.setDeptId(dept.getDeptId());
		}
		// 设置状态
		drawback.setSwfStatus(Drawback.DRAFT);
		return drawback;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param drawbackId
	 * @param user
	 * @return
	 */
	public Drawback initEditOrViewPage(String drawbackId, UserProfile user) {
		Drawback drawback;
		if (StringUtils.isBlank(drawbackId)) {
			drawback = newDrawback(user);
			drawback.setDrawbackId(UUIDHex.newId());
			drawback.setAdd(true);
		} else {
			drawback = (Drawback) this.drawbackDao.get(drawbackId);
			drawback.setAdd(false);
		}
		return drawback;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param drawback
	 * @param valueMap
	 * @return
	 */
	public Drawback saveOrUpdate(UserProfile user, Drawback drawback, Map<String, Object> valueMap) {
		if (drawback.getAdd()) {
			// 装配基础信息
			loadDrawback(drawback, user);
			this.drawbackDao.save(drawback);
		} else {
			// 设置更新时间
			drawback.setModifiedBy(user.getName());
			drawback.setModifiedById(user.getPersonId());
			drawback.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.drawbackDao.update(drawback);
		}
		return drawback;
	}

	/**
	 * 保存提交
	 * @param user
	 * @param drawback
	 * @param valueMap
	 * @return
	 */
	public Drawback commit(UserProfile user, Drawback drawback, Map<String, Object> valueMap) {
		// 设置退税单号
		if (StringUtils.isBlank(drawback.getDrawbackNo())) {
			drawback.setDrawbackNo(generatorDrawbackNo());
		}
		commitExcute(drawback);
		saveOrUpdate(user, drawback, valueMap);
		// 记录日志
		String logMessage = MessageFormat.format(LogConstant.PUBLISH_LOG_MESSAGE_DRAWBACK, drawback.getProjectName());
		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS_DRAWBACK).info(user, LogConstant.PUBLISH_LOG_ACTION_DRAWBACK, logMessage, drawback, null);
		return drawback;
	}

	public void commitExcute(Drawback drawback) {
		String projectId = drawback.getProjectId();
		// 设置项目经理
		if (StringUtils.isBlank(drawback.getPrjManagerId())) {
			RegisterProject project = this.registerProjectService.get(projectId);
			drawback.setPrjManagerId(project.getProjectChargeId());
			drawback.setPrjManager(project.getProjectCharge());
		}
		// 设置项目预算项（应收出口退税）
		if (StringUtils.isBlank(drawback.getBudgetId())) {
			drawback.setBudgetId(BenefitBudgetItemConstant.SUMMARY_EXPORT_DRAWBACK);
			BenefitBudget budget = TpcBudgetUtil.getBenefitBudget(projectId, drawback.getBudgetId());
			drawback.setBudgetName(budget.getBudgetName());
		}
	}

	/**
	 * 启动流程后执行操作
	 * @param drawback
	 */
	public void startProc(Drawback drawback) {
		drawback.setSwfStatus(Drawback.PROCESSING);
		this.update(drawback);
		// 将退税金额写入项目在途
		UserProfile user = TpcCommonUtil.getUserProfile(drawback.getCreatedById());
		this.addOnwayBudgetAmount(user, drawback.getProjectId(), drawback.getBudgetId(), drawback.getDrawbackAmount());
	}

	/**
	 * 中止流程处理操作
	 * @param drawback
	 */
	public void abortProc(Drawback drawback) {
		drawback.setSwfStatus(Drawback.DRAFT);
		this.update(drawback);
		// 将退税金额移除项目在途
		UserProfile user = TpcCommonUtil.getUserProfile(drawback.getCreatedById());
		this.removeOnwayBudgetAmount(user, drawback.getProjectId(), drawback.getBudgetId(), drawback.getDrawbackAmount());
	}

	/**
	 * 审批完成执行操作
	 * @param drawback
	 */
	public void completeExam(Drawback drawback) {
		drawback.setSwfStatus(Drawback.COMPLETED);
		this.update(drawback);
		// 将退税金额转为执行金额
		UserProfile user = TpcCommonUtil.getUserProfile(drawback.getCreatedById());
		this.transferToExecute(user, drawback.getProjectId(), drawback.getBudgetId(), drawback.getDrawbackAmount());
	}

	/**
	 * 在途金额增加
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void addOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.addOnwayBudgetAmount(TpcBudgetUtil.TPC_DRAWBACK, user, projectId, budgetId, amount);
	}

	/**
	 * 在途金额减少
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void removeOnwayBudgetAmount(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.removeOnwayBudgetAmount(TpcBudgetUtil.TPC_DRAWBACK, user, projectId, budgetId, amount);
	}

	/**
	 * 在途转执行
	 * @param user
	 * @param projectId
	 * @param budgetId
	 * @param amount
	 */
	public synchronized void transferToExecute(UserProfile user, String projectId, String budgetId, double amount) {
		TpcBudgetUtil.transferToExecute(TpcBudgetUtil.TPC_DRAWBACK, user, projectId, budgetId, amount);
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param drawbackIds
	 */
	public void delete(UserProfile user, String drawbackIds) {
		if (StringUtils.isNotBlank(drawbackIds)) {
			Drawback drawback;
			for (String drawbackId : drawbackIds.split(",")) {
				drawback = this.get(drawbackId);
				if (drawback == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, drawback);
				this.drawbackDao.delete(drawbackId);
			}
		}
	}

	/**
	 * 更新对象
	 * @param useSeal
	 * @return
	 */
	public Drawback update(Drawback drawback) {
		this.drawbackDao.update(drawback);
		return drawback;
	}

	/**
	 * 生成退税单号
	 * @return
	 */
	public synchronized String generatorDrawbackNo() {
		String drawbackNo;
		String NoHead = CommonUtil.format(new Date(), "yyyyMM");
		// 过滤条件
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("drawbackNo", NoHead + "%");
		List<String> likeSearhNames = new ArrayList<String>();
		likeSearhNames.add("drawbackNo");
		Integer count = this.drawbackDao.find(params, likeSearhNames, null).size();
		String NoEnd = String.format("%03d", count + 1);
		drawbackNo = NoHead + NoEnd;
		return drawbackNo;
	}

}
