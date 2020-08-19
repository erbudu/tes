package com.supporter.prj.cneec.tpc.external_quotation_review_dept.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.dao.ExternalQuotationReviewDeptDao;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity.ExternalQuotationReviewDept;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemand;
import com.supporter.prj.cneec.tpc.purchase_demand.service.PurchaseDemandService;
import com.supporter.prj.cneec.tpc.purchase_demand.util.PurchaseDemandConstant;
import com.supporter.prj.cneec.tpc.purchase_demand_sheet.service.PurchaseDemandSheetService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.RoleUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: ExternalQuotationReviewDeptService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2018-03-20
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class ExternalQuotationReviewDeptService {

	@Autowired
	private ExternalQuotationReviewDeptDao externalQuotationReviewDeptDao;
	@Autowired
	private PurchaseDemandService purchaseDemandService;
	@Autowired
	private PurchaseDemandSheetService purchaseDemandSheetService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, ExternalQuotationReviewDept.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param externalQuotationReviewDept
	 */
	public void getAuthCanExecute(UserProfile userProfile, ExternalQuotationReviewDept externalQuotationReviewDept) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, ExternalQuotationReviewDept.MODULE_ID, externalQuotationReviewDept.getExternalId(), externalQuotationReviewDept);
	}

	/**
	 * 获取对外报价评审对象集合
	 * @param user
	 * @param jqGrid
	 * @param externalQuotationReviewDept
	 * @return
	 */
	public List<ExternalQuotationReviewDept> getGrid(UserProfile user, JqGrid jqGrid, ExternalQuotationReviewDept externalQuotationReviewDept) {
		String authFilter = getAuthFilter(user);
		return this.externalQuotationReviewDeptDao.findPage(jqGrid, externalQuotationReviewDept, authFilter);
	}

	/**
	 * 获取单个对外报价评审对象
	 * @param externalId
	 * @return
	 */
	public ExternalQuotationReviewDept get(String externalId) {
		return this.externalQuotationReviewDeptDao.get(externalId);
	}

	/**
	 * 根据采购需求单ID获取对外报价评审单
	 * 对外报价评审单与采购需求单是一对一关系
	 * @param demandId
	 * @return
	 */
	public ExternalQuotationReviewDept getExternalQuotationReviewDeptByDemandId(String demandId) {
		return this.externalQuotationReviewDeptDao.findUniqueResult("demandId", demandId);
	}

	/**
	 * 新建对外报价评审对象
	 * @param user
	 * @return
	 */
	public ExternalQuotationReviewDept newExternalQuotationReviewDept(UserProfile user) {
		ExternalQuotationReviewDept externalQuotationReviewDept = new ExternalQuotationReviewDept();
		loadExternalQuotationReviewDept(externalQuotationReviewDept, user);
		externalQuotationReviewDept.setSwfStatus(ExternalQuotationReviewDept.DRAFT);
		return externalQuotationReviewDept;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public ExternalQuotationReviewDept loadExternalQuotationReviewDept(ExternalQuotationReviewDept externalQuotationReviewDept, UserProfile user) {
		externalQuotationReviewDept.setCreatedBy(user.getName());
		externalQuotationReviewDept.setCreatedById(user.getPersonId());
		externalQuotationReviewDept.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		externalQuotationReviewDept.setModifiedBy(user.getName());
		externalQuotationReviewDept.setModifiedById(user.getPersonId());
		externalQuotationReviewDept.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			externalQuotationReviewDept.setDeptName(dept.getName());
			externalQuotationReviewDept.setDeptId(dept.getDeptId());
		}
		// 设置状态
		externalQuotationReviewDept.setSwfStatus(ExternalQuotationReviewDept.DRAFT);
		return externalQuotationReviewDept;
	}

	/**
	 * 根据客户采购需求单初始化对象
	 * @param demandId
	 * @param user
	 * @return
	 */
	public ExternalQuotationReviewDept initExternalQuotationReviewDeptByDemandId(String demandId, UserProfile user) {
		ExternalQuotationReviewDept externalQuotationReviewDept = getExternalQuotationReviewDeptByDemandId(demandId);
		if (externalQuotationReviewDept == null) {
			externalQuotationReviewDept = newExternalQuotationReviewDept(user);
			externalQuotationReviewDept.setExternalId(UUIDHex.newId());
			externalQuotationReviewDept.setAdd(true);
			if (demandId.length() > 0) {
				PurchaseDemand purchaseDemand = purchaseDemandService.get(demandId);
				externalQuotationReviewDept.setProjectId(purchaseDemand.getProjectId());
				externalQuotationReviewDept.setProjectName(purchaseDemand.getProjectName());
				externalQuotationReviewDept.setDemandId(purchaseDemand.getDemandId());
				externalQuotationReviewDept.setBatchNo(purchaseDemand.getBatchNo());
				externalQuotationReviewDept.setPurchaseDemandName(purchaseDemand.getPurchaseDemandName());
				externalQuotationReviewDept.setProjectClassification(purchaseDemand.getProjectClassification());
				externalQuotationReviewDept.setProjectClassificationId(purchaseDemand.getProjectClassificationId());
				externalQuotationReviewDept.setCurrencyId(purchaseDemand.getCurrencyId());
				externalQuotationReviewDept.setCurrency(purchaseDemand.getCurrency());
				externalQuotationReviewDept.setQuotationAmount(purchaseDemand.getEstimatedAmount());
				externalQuotationReviewDept.setReviewClassification(purchaseDemand.getReviewClassification());
				externalQuotationReviewDept.setReviewAmount(purchaseDemand.getUsdAmount());// 评审金额
			}
			saveOrUpdate(user, externalQuotationReviewDept, null);
			externalQuotationReviewDept.setAdd(false);
		}
		return externalQuotationReviewDept;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param externalId
	 * @param user
	 * @return
	 */
	public ExternalQuotationReviewDept initEditOrViewPage(String externalId, UserProfile user) {
		ExternalQuotationReviewDept externalQuotationReviewDept;
		if (StringUtils.isBlank(externalId)) {
			externalQuotationReviewDept = newExternalQuotationReviewDept(user);
			externalQuotationReviewDept.setExternalId(UUIDHex.newId());
			externalQuotationReviewDept.setAdd(true);
		} else {
			externalQuotationReviewDept = (ExternalQuotationReviewDept) this.externalQuotationReviewDeptDao.get(externalId);
			externalQuotationReviewDept.setAdd(false);
		}
		return externalQuotationReviewDept;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param externalQuotationReviewDept
	 * @param valueMap
	 * @return
	 */
	public ExternalQuotationReviewDept saveOrUpdate(UserProfile user, ExternalQuotationReviewDept externalQuotationReviewDept, Map<String, Object> valueMap) {
		if (externalQuotationReviewDept.getAdd()) {
			// 装配基础信息
			loadExternalQuotationReviewDept(externalQuotationReviewDept, user);
			this.externalQuotationReviewDeptDao.save(externalQuotationReviewDept);
		} else {
			// 设置更新时间
			externalQuotationReviewDept.setModifiedBy(user.getName());
			externalQuotationReviewDept.setModifiedById(user.getPersonId());
			externalQuotationReviewDept.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.externalQuotationReviewDeptDao.update(externalQuotationReviewDept);
		}
		return externalQuotationReviewDept;
	}

	/**
	 * 加载评审数据并判断是否直接完成
	 * @param reviewDept
	 * @return
	 */
	public ExternalQuotationReviewDept loadReviewData(ExternalQuotationReviewDept reviewDept, UserProfile user) {
		// 超大额时直接完成
		String reviewClassification = reviewDept.getReviewClassification();
		if (reviewClassification.equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_SUPER)) {
			reviewDept.setSwfStatus(ExternalQuotationReviewDept.COMPLETED);
			update(reviewDept);
			// 发知会给经营经理公司评审
			sendNotifyMsg(user, reviewDept);
		}
		return reviewDept;
	}

	/**
	 * 保存提交
	 * @param user
	 * @param externalQuotationReviewDept
	 * @param valueMap
	 * @return
	 */
	public ExternalQuotationReviewDept commit(UserProfile user, ExternalQuotationReviewDept externalQuotationReviewDept, Map<String, Object> valueMap) {
		saveOrUpdate(user, externalQuotationReviewDept, valueMap);
		// 装载报价评审数据
		loadReviewData(externalQuotationReviewDept, user);
		// 记录日志
		//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + externalQuotationReviewDept + "}", null, null);
		return externalQuotationReviewDept;
	}

	/**
	 * 审批完成操作
	 * @param externalQuotationReviewDept
	 */
	public void completeExam(ExternalQuotationReviewDept externalQuotationReviewDept) {
		if (!externalQuotationReviewDept.isCompanyReview()) {
			this.purchaseDemandSheetService.createPurchaseDemandSheetByDemandId(externalQuotationReviewDept.getDemandId());
		}
	}

	/**
	 * 会审发送会签人知会
	 * @param deptReview
	 */
	public void sendMeetNotifyMsg(ExternalQuotationReviewDept deptReview) {
		//deptReview.setSwfStatus(ExternalQuotationReviewDept.PROCESSING);
		update(deptReview);
		// 获取会签人
		if (StringUtils.isNotBlank(deptReview.getSignerId())) {
			String[] signers = deptReview.getSignerId().split(",");
			if (signers.length > 0) {
				for (String personId : signers) {
					Message message = new Todo();
					message.setPersonId(personId);
					message.setEventTitle(deptReview.getDeptName() + deptReview.getProjectName() + deptReview.getQuotationTitle() + "对外报价事业部评审，会议资料如下，请查收");
					message.setNotifyTime(new Date());
					message.setWebPageURL("tpc/external_quotation_review_dept/external_quotation_review_dept_meet_notify.html?isCcPage=true&externalId="
							+ deptReview.getExternalId());
					message.setMessageType(ITodo.MsgType.CC);
					message.setRelatedRecordTable(ExternalQuotationReviewDept.MODULE_ID);
					EIPService.getBMSService().addMessage(message);
				}
			}
		}
		
	}
	
	/**
	 * 会审事业部审批完成发知会给经营经理
	 * @param user
	 * @param externalQuotationReviewDept
	 */
	public void sendNotifyMsg(UserProfile user, ExternalQuotationReviewDept externalQuotationReviewDept) {
		// 获取经营经理角色
		List<Person> generalManager = RoleUtil.getRolePersonsByDept(RoleUtil.ROLE_ID_JINGYING, user.getDept());
		if (generalManager != null && generalManager.size() > 0) {
			for (Person person : generalManager) {
				Message message = new Todo();
				message.setPersonId(person.getPersonId());
				message.setEventTitle("知会（贸易项目对外报价事业部初评已完成）：" + externalQuotationReviewDept.getQuotationTitle() + "(" + externalQuotationReviewDept.getDeptName() + ")");
				message.setNotifyTime(new Date());
				message.setWebPageURL("tpc/external_quotation_review_dept/external_quotation_review_dept_audit_notify.html?isCcPage=true&externalId="
						+ externalQuotationReviewDept.getExternalId());
				message.setMessageType(ITodo.MsgType.CC);
				message.setRelatedRecordTable(ExternalQuotationReviewDept.MODULE_ID);
				EIPService.getBMSService().addMessage(message);
			}
		}
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param externalIds
	 */
	public void delete(UserProfile user, String externalIds) {
		if (StringUtils.isNotBlank(externalIds)) {
			ExternalQuotationReviewDept externalQuotationReviewDept;
			for (String externalId : externalIds.split(",")) {
				externalQuotationReviewDept = this.get(externalId);
				if (externalQuotationReviewDept == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, externalQuotationReviewDept);
				this.externalQuotationReviewDeptDao.delete(externalQuotationReviewDept);
			}
		}
	}

	/**
	 * 更新对象
	 * @param review
	 * @return
	 */
	public ExternalQuotationReviewDept update(ExternalQuotationReviewDept review) {
		this.externalQuotationReviewDeptDao.update(review);
		return review;
	}

	/**
	 * 获取对外报价评审ListPage对象
	 * @param page
	 * @param pageSize
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public ListPage<ExternalQuotationReviewDept> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ExternalQuotationReviewDept> listPage = new ListPage<ExternalQuotationReviewDept>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.externalQuotationReviewDeptDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

}
