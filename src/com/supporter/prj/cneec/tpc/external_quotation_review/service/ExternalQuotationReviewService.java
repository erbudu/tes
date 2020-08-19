package com.supporter.prj.cneec.tpc.external_quotation_review.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.external_quotation_review.dao.ExternalQuotationReviewDao;
import com.supporter.prj.cneec.tpc.external_quotation_review.entity.ExternalQuotationReview;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.entity.ExternalQuotationReviewDept;
import com.supporter.prj.cneec.tpc.external_quotation_review_dept.service.ExternalQuotationReviewDeptService;
import com.supporter.prj.cneec.tpc.purchase_demand.util.PurchaseDemandConstant;
import com.supporter.prj.cneec.tpc.purchase_demand_sheet.service.PurchaseDemandSheetService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: ExternalQuotationReviewService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-09-25
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class ExternalQuotationReviewService {

	@Autowired
	private ExternalQuotationReviewDao externalQuotationReviewDao;
	@Autowired
	private PurchaseDemandSheetService purchaseDemandSheetService;
	@Autowired
	private ExternalQuotationReviewDeptService externalQuotationReviewDeptService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, ExternalQuotationReview.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param externalQuotationReview
	 */
	public void getAuthCanExecute(UserProfile userProfile, ExternalQuotationReview externalQuotationReview) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, ExternalQuotationReview.MODULE_ID, externalQuotationReview.getExternalId(), externalQuotationReview);
	}

	/**
	 * 获取对外报价评审对象集合
	 * @param user
	 * @param jqGrid
	 * @param externalQuotationReview
	 * @return
	 */
	public List<ExternalQuotationReview> getGrid(UserProfile user, JqGrid jqGrid, ExternalQuotationReview externalQuotationReview) {
		String authFilter = getAuthFilter(user);
		return this.externalQuotationReviewDao.findPage(jqGrid, externalQuotationReview, authFilter);
	}

	/**
	 * 获取单个对外报价评审对象
	 * @param externalId
	 * @return
	 */
	public ExternalQuotationReview get(String externalId) {
		return this.externalQuotationReviewDao.get(externalId);
	}

	/**
	 * 根据采购需求单ID获取对外报价评审单
	 * 对外报价评审单与采购需求单是一对一关系
	 * @param demandId
	 * @return
	 */
	public ExternalQuotationReview getExternalQuotationReviewByDemandId(String demandId) {
		return this.externalQuotationReviewDao.findUniqueResult("demandId", demandId);
	}

	/**
	 * 新建对外报价评审对象
	 * @param user
	 * @return
	 */
	public ExternalQuotationReview newExternalQuotationReview(UserProfile user) {
		ExternalQuotationReview externalQuotationReview = new ExternalQuotationReview();
		loadExternalQuotationReview(externalQuotationReview, user);
		externalQuotationReview.setSwfStatus(ExternalQuotationReview.DRAFT);
		return externalQuotationReview;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public ExternalQuotationReview loadExternalQuotationReview(ExternalQuotationReview externalQuotationReview, UserProfile user) {
		externalQuotationReview.setCreatedBy(user.getName());
		externalQuotationReview.setCreatedById(user.getPersonId());
		externalQuotationReview.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		externalQuotationReview.setModifiedBy(user.getName());
		externalQuotationReview.setModifiedById(user.getPersonId());
		externalQuotationReview.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			externalQuotationReview.setDeptName(dept.getName());
			externalQuotationReview.setDeptId(dept.getDeptId());
		}
		// 设置状态
		externalQuotationReview.setSwfStatus(ExternalQuotationReview.DRAFT);
		return externalQuotationReview;
	}

	/**
	 * 根据客户采购需求单初始化对象
	 * @param demandId
	 * @param user
	 * @return
	 */
	public ExternalQuotationReview initExternalQuotationReviewByDeptExternalId(String deptExternalId, UserProfile user) {
		ExternalQuotationReview externalQuotationReview = this.get(deptExternalId);
		if (externalQuotationReview == null) {
			externalQuotationReview = new ExternalQuotationReview();
			if (deptExternalId.length() > 0) {
				ExternalQuotationReviewDept deptReview = this.externalQuotationReviewDeptService.get(deptExternalId);
				try {
					BeanUtils.copyProperties(externalQuotationReview, deptReview);
				} catch (Exception e) {
					e.printStackTrace();
				}
				loadExternalQuotationReview(externalQuotationReview, user);
				externalQuotationReview.setAdd(true);
				externalQuotationReview.setSignerId(null);
				externalQuotationReview.setSignerName(null);
			}
			saveOrUpdate(user, externalQuotationReview, null);
			externalQuotationReview.setAdd(false);
		}
		return externalQuotationReview;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param externalId
	 * @param user
	 * @return
	 */
	public ExternalQuotationReview initEditOrViewPage(String externalId, UserProfile user) {
		ExternalQuotationReview externalQuotationReview;
		if (StringUtils.isBlank(externalId)) {
			externalQuotationReview = newExternalQuotationReview(user);
			externalQuotationReview.setExternalId(UUIDHex.newId());
			externalQuotationReview.setAdd(true);
		} else {
			externalQuotationReview = (ExternalQuotationReview) this.externalQuotationReviewDao.get(externalId);
			externalQuotationReview.setAdd(false);
		}
		return externalQuotationReview;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param externalQuotationReview
	 * @param valueMap
	 * @return
	 */
	public ExternalQuotationReview saveOrUpdate(UserProfile user, ExternalQuotationReview externalQuotationReview, Map<String, Object> valueMap) {
		if (externalQuotationReview.getAdd()) {
			// 装配基础信息
			loadExternalQuotationReview(externalQuotationReview, user);
			this.externalQuotationReviewDao.save(externalQuotationReview);
		} else {
			// 设置更新时间
			externalQuotationReview.setModifiedBy(user.getName());
			externalQuotationReview.setModifiedById(user.getPersonId());
			externalQuotationReview.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.externalQuotationReviewDao.update(externalQuotationReview);
		}
		return externalQuotationReview;
	}

	/**
	 * 添加报价评审数据
	 * @param reviewDept
	 * @return
	 */
	public ExternalQuotationReview loadReviewData(ExternalQuotationReview review) {
		// 超大额时直接完成
		String reviewClassification = review.getReviewClassification();
		if (reviewClassification.equals(PurchaseDemandConstant.REVIEW_SELF_SUPPORT_SUPER)) {
			review.setSwfStatus(ExternalQuotationReview.COMPLETED);
			update(review);
			//超大额直接完成时写入需求选择单
			completeExam(review);
		}
		return review;
	}

	/**
	 * 保存提交
	 * @param user
	 * @param externalQuotationReview
	 * @param valueMap
	 * @return
	 */
	public ExternalQuotationReview commit(UserProfile user, ExternalQuotationReview externalQuotationReview, Map<String, Object> valueMap) {
		saveOrUpdate(user, externalQuotationReview, valueMap);
		// 装载报价评审数据
		loadReviewData(externalQuotationReview);
		// 记录日志
		//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + externalQuotationReview + "}", null, null);
		return externalQuotationReview;
	}

	/**
	 * 审批完成写入需求选择单
	 * @param externalQuotationReview
	 */
	public void completeExam(ExternalQuotationReview externalQuotationReview) {
		this.purchaseDemandSheetService.createPurchaseDemandSheetByDemandId(externalQuotationReview.getDemandId());
	}

	/**
	 * 会审发送会签人知会
	 * @param review
	 */
	public void sendMeetNotifyMsg(ExternalQuotationReview review) {
		//review.setSwfStatus(ExternalQuotationReview.PROCESSING);
		update(review);
		// 获取会签人
		if (StringUtils.isNotBlank(review.getSignerId())) {
			String[] signers = review.getSignerId().split(",");
			if (signers.length > 0) {
				for (String personId : signers) {
					Message message = new Todo();
					message.setPersonId(personId);
					message.setEventTitle(review.getDeptName() + review.getProjectName() + review.getQuotationTitle() + "对外报价公司评审，会议资料如下，请查收");
					message.setNotifyTime(new Date());
					message.setWebPageURL("tpc/external_quotation_review/external_quotation_review_meet_notify.html?isCcPage=true&externalId="
							+ review.getExternalId());
					message.setMessageType(ITodo.MsgType.CC);
					message.setRelatedRecordTable(ExternalQuotationReview.MODULE_ID);
					EIPService.getBMSService().addMessage(message);
				}
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
			ExternalQuotationReview externalQuotationReview;
			for (String externalId : externalIds.split(",")) {
				externalQuotationReview = this.get(externalId);
				if (externalQuotationReview == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, externalQuotationReview);
				this.externalQuotationReviewDao.delete(externalQuotationReview);
			}
		}
	}

	/**
	 * 更新对象
	 * @param review
	 * @return
	 */
	public ExternalQuotationReview update(ExternalQuotationReview review) {
		this.externalQuotationReviewDao.update(review);
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
	public ListPage<ExternalQuotationReview> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ExternalQuotationReview> listPage = new ListPage<ExternalQuotationReview>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.externalQuotationReviewDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

}
