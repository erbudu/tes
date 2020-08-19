package com.supporter.prj.cneec.tpc.termination_quotation.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.termination_quotation.dao.TerminationQuotationDao;
import com.supporter.prj.cneec.tpc.termination_quotation.entity.TerminationQuotation;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApply;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: TerminationQuotationService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-09-18
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class TerminationQuotationService {

	@Autowired
	private TerminationQuotationDao terminationQuotationDao;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, TerminationQuotation.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param terminationQuotation
	 */
	public void getAuthCanExecute(UserProfile userProfile, TerminationQuotation terminationQuotation) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, TerminationQuotation.MODULE_ID, terminationQuotation.getQuotationId(), terminationQuotation);
	}

	/**
	 * 获取终止报价对象集合
	 * @param user
	 * @param jqGrid
	 * @param terminationQuotation
	 * @return
	 */
	public List<TerminationQuotation> getGrid(UserProfile user, JqGrid jqGrid, TerminationQuotation terminationQuotation) {
		String authFilter = getAuthFilter(user);
		return this.terminationQuotationDao.findPage(jqGrid, terminationQuotation, authFilter);
	}

	/**
	 * 获取单个终止报价对象
	 * @param quotationId
	 * @return
	 */
	public TerminationQuotation get(String quotationId) {
		return this.terminationQuotationDao.get(quotationId);
	}

	/**
	 * 新建终止报价对象
	 * @param user
	 * @return
	 */
	public TerminationQuotation newTerminationQuotation(UserProfile user) {
		TerminationQuotation terminationQuotation = new TerminationQuotation();
		loadTerminationQuotation(terminationQuotation, user);
		terminationQuotation.setSwfStatus(TerminationQuotation.DRAFT);
		return terminationQuotation;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public TerminationQuotation loadTerminationQuotation(TerminationQuotation terminationQuotation, UserProfile user) {
		terminationQuotation.setCreatedBy(user.getName());
		terminationQuotation.setCreatedById(user.getPersonId());
		terminationQuotation.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		terminationQuotation.setModifiedBy(user.getName());
		terminationQuotation.setModifiedById(user.getPersonId());
		terminationQuotation.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			terminationQuotation.setDeptName(dept.getName());
			terminationQuotation.setDeptId(dept.getDeptId());
		}
		// 设置状态
		terminationQuotation.setSwfStatus(TerminationQuotation.DRAFT);
		return terminationQuotation;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param quotationId
	 * @param user
	 * @return
	 */
	public TerminationQuotation initEditOrViewPage(String quotationId, UserProfile user) {
		TerminationQuotation terminationQuotation;
		if (StringUtils.isBlank(quotationId)) {
			terminationQuotation = newTerminationQuotation(user);
			terminationQuotation.setQuotationId(UUIDHex.newId());
			terminationQuotation.setAdd(true);
		} else {
			terminationQuotation = (TerminationQuotation) this.terminationQuotationDao.get(quotationId);
			terminationQuotation.setAdd(false);
		}
		return terminationQuotation;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param terminationQuotation
	 * @param valueMap
	 * @return
	 */
	public TerminationQuotation saveOrUpdate(UserProfile user, TerminationQuotation terminationQuotation, Map<String, Object> valueMap) {
		if (terminationQuotation.getAdd()) {
			// 装配基础信息
			loadTerminationQuotation(terminationQuotation, user);
			this.terminationQuotationDao.save(terminationQuotation);
		} else {
			// 设置更新时间
			terminationQuotation.setModifiedBy(user.getName());
			terminationQuotation.setModifiedById(user.getPersonId());
			terminationQuotation.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.terminationQuotationDao.update(terminationQuotation);
		}
		return terminationQuotation;
	}

	// TODO: modify begin
	/*
	 不需要，直接删除
	 */
	@Autowired
	private RegisterProjectService registerProjectService;
	// TODO: modify end

	/**
	 * 保存提交
	 * @param user
	 * @param terminationQuotation
	 * @param valueMap
	 * @return
	 */
	public TerminationQuotation commit(UserProfile user, TerminationQuotation terminationQuotation, Map<String, Object> valueMap) {
		saveOrUpdate(user, terminationQuotation, valueMap);
		// TODO: modify begin
		/*
		 不需要，直接删除
		 */
		System.out.println("TODO: modify: commit");
		terminationQuotation.setSwfStatus(TerminationQuotation.COMPLETED);
		this.terminationQuotationDao.update(terminationQuotation);
		registerProjectService.termination(user, terminationQuotation.getProjectId());
		// TODO: modify end
		// 记录日志
		//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + terminationQuotation + "}", null, null);
		return terminationQuotation;
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param quotationIds
	 */
	public void delete(UserProfile user, String quotationIds) {
		if (StringUtils.isNotBlank(quotationIds)) {
			TerminationQuotation terminationQuotation;
			for (String quotationId : quotationIds.split(",")) {
				terminationQuotation = this.get(quotationId);
				if (terminationQuotation == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, terminationQuotation);
				this.terminationQuotationDao.delete(terminationQuotation);
			}
		}
	}
	
	
	
	/**
	 * 保存或更新
	 */
	public TerminationQuotation update(TerminationQuotation terminationQuotation) {
		this.terminationQuotationDao.update(terminationQuotation);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_EDIT.getOperName(), null);

		return terminationQuotation;

	}
	
	

}
