package com.supporter.prj.cneec.tpc.protocol_review.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.protocol_review.dao.ProtocolReviewDao;
import com.supporter.prj.cneec.tpc.protocol_review.entity.ProtocolReview;
import com.supporter.prj.cneec.tpc.protocol_review.util.ProtocolReviewConstant;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: ProtocolReviewService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-09-06
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class ProtocolReviewService {

	@Autowired
	private ProtocolReviewDao protocolReviewDao;
	@Autowired
	private RegisterProjectService projectService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, ProtocolReview.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param protocolReview
	 */
	public void getAuthCanExecute(UserProfile userProfile, ProtocolReview protocolReview) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, ProtocolReview.MODULE_ID, protocolReview.getReviewId(), protocolReview);
	}

	/**
	 * 获取框架协议评审对象集合
	 * @param user
	 * @param jqGrid
	 * @param protocolReview
	 * @return
	 */
	public List<ProtocolReview> getGrid(UserProfile user, JqGrid jqGrid, ProtocolReview protocolReview) {
		String authFilter = getAuthFilter(user);
		return this.protocolReviewDao.findPage(jqGrid, protocolReview, authFilter);
	}

	/**
	 * 获取单个框架协议评审对象
	 * @param reviewId
	 * @return
	 */
	public ProtocolReview get(String reviewId) {
		return this.protocolReviewDao.get(reviewId);
	}

	/**
	 * 根据项目ID获取框架协议评审单
	 * 框架协议评审与注册项目是一对一关系
	 * @param projectId
	 * @return
	 */
	public ProtocolReview getProtocolReviewByProjectId(String projectId) {
		return this.protocolReviewDao.findUniqueResult("projectId", projectId);
	}

	/**
	 * 
	 * @param reviewId
	 * @param user
	 * @return
	 */
	public ProtocolReview initEditOrViewPageByProjectId(String projectId, UserProfile user) {
		ProtocolReview protocolReview = getProtocolReviewByProjectId(projectId);
		if (protocolReview == null) {
			protocolReview = newProtocolReview(user);
			protocolReview.setReviewId(UUIDHex.newId());
			protocolReview.setAdd(true);
			RegisterProject project = this.projectService.get(projectId);
			if (project != null) {
				protocolReview.setProjectId(projectId);
				protocolReview.setProjectNo(project.getProjectNo());
				protocolReview.setProjectName(project.getProjectName());
				protocolReview.setCustomerId(project.getCustomerId());
				protocolReview.setCustomerName(project.getCustomerName());
			}
		}
		return protocolReview;
	}

	/**
	 * 新建框架协议评审对象
	 * @param user
	 * @return
	 */
	public ProtocolReview newProtocolReview(UserProfile user) {
		ProtocolReview protocolReview = new ProtocolReview();
		loadProtocolReview(protocolReview, user);
		protocolReview.setSwfStatus(ProtocolReview.DRAFT);
		return protocolReview;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public ProtocolReview loadProtocolReview(ProtocolReview protocolReview, UserProfile user) {
		protocolReview.setCreatedBy(user.getName());
		protocolReview.setCreatedById(user.getPersonId());
		protocolReview.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		protocolReview.setModifiedBy(user.getName());
		protocolReview.setModifiedById(user.getPersonId());
		protocolReview.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			protocolReview.setDeptName(dept.getName());
			protocolReview.setDeptId(dept.getDeptId());
		}
		// 设置状态
		protocolReview.setSwfStatus(ProtocolReview.DRAFT);
		return protocolReview;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param reviewId
	 * @param user
	 * @return
	 */
	public ProtocolReview initEditOrViewPage(String reviewId, UserProfile user) {
		ProtocolReview protocolReview;
		if (StringUtils.isBlank(reviewId)) {
			protocolReview = newProtocolReview(user);
			protocolReview.setReviewId(UUIDHex.newId());
			protocolReview.setAdd(true);
		} else {
			protocolReview = (ProtocolReview) this.protocolReviewDao.get(reviewId);
			protocolReview.setAdd(false);
		}
		return protocolReview;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param protocolReview
	 * @param valueMap
	 * @return
	 */
	public ProtocolReview saveOrUpdate(UserProfile user, ProtocolReview protocolReview, Map<String, Object> valueMap) {
		if (protocolReview.getAdd()) {
			// 装配基础信息
			loadProtocolReview(protocolReview, user);
			this.protocolReviewDao.save(protocolReview);
		} else {
			// 设置更新时间
			protocolReview.setModifiedBy(user.getName());
			protocolReview.setModifiedById(user.getPersonId());
			protocolReview.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.protocolReviewDao.update(protocolReview);
		}
		return protocolReview;
	}

	/**
	 * 保存提交
	 * @param user
	 * @param protocolReview
	 * @param valueMap
	 * @return
	 */
	public ProtocolReview commit(UserProfile user, ProtocolReview protocolReview, Map<String, Object> valueMap) {
		saveOrUpdate(user, protocolReview, valueMap);
		// 记录日志
		//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + protocolReview + "}", null, null);
		return protocolReview;
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param reviewIds
	 */
	public void delete(UserProfile user, String reviewIds) {
		if (StringUtils.isNotBlank(reviewIds)) {
			ProtocolReview protocolReview;
			for (String reviewId : reviewIds.split(",")) {
				protocolReview = this.get(reviewId);
				if (protocolReview == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, protocolReview);
				this.protocolReviewDao.delete(protocolReview);
			}
		}
	}

	public List<CheckBoxVO> getReviewConclusionList(String reviewId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String choose = "";
		if (reviewId.length() > 0) {
			ProtocolReview protocolReview = this.get(reviewId);
			if (protocolReview != null) {
				choose = protocolReview.getReviewConclusion();
			}
		}
		Map<Integer, String> map = ProtocolReviewConstant.getReviewConclusionMap();
		for (Integer key : map.keySet()) {
			String keyStr = key.toString();
			CheckBoxVO vo = new CheckBoxVO("reviewConclusion_" + key, "reviewConclusion", keyStr, map.get(key), keyStr.equals(choose));
			list.add(vo);
		}
		return list;
	}

	/**
	 * 判断字符串数组是否包含特定值
	 * @param arr
	 * @param tarVal
	 * @return
	 */
	public static boolean existByLoop(String[] arr, String tarVal) {
		for (String s : arr) {
			if (s.equals(tarVal)) return true;
		}
		return false;
	}

	/** 以下是选择框架协议评审方法 **/

	public ListPage<ProtocolReview> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<ProtocolReview> listPage = new ListPage<ProtocolReview>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.protocolReviewDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public ProtocolReview update(ProtocolReview protocolReview) {
			this.protocolReviewDao.update(protocolReview);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_EDIT.getOperName(), null);

		return protocolReview;

	}

}
