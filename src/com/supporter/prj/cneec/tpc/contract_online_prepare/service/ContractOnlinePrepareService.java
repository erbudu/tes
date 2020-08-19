package com.supporter.prj.cneec.tpc.contract_online_prepare.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.contract_online_prepare.dao.ContractOnlinePrepareDao;
import com.supporter.prj.cneec.tpc.contract_online_prepare.entity.ContractOnlinePrepare;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.entity.ContractSignDeptReview;
import com.supporter.prj.cneec.tpc.derivative_contract.entity.DerivativeContract;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

/**
 * @Title: ContractOnlinePrepareService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2018-05-15
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class ContractOnlinePrepareService {

	@Autowired
	private ContractOnlinePrepareDao contractOnlinePrepareDao;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, ContractOnlinePrepare.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param contractOnlinePrepare
	 */
	public void getAuthCanExecute(UserProfile userProfile, ContractOnlinePrepare contractOnlinePrepare) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, ContractOnlinePrepare.MODULE_ID, contractOnlinePrepare.getPrepareId(), contractOnlinePrepare);
	}

	/**
	 * 获取合同签约前评审完成合同选择单对象集合
	 * @param user
	 * @param jqGrid
	 * @param contractOnlinePrepare
	 * @return
	 */
	public List<ContractOnlinePrepare> getGrid(UserProfile user, JqGrid jqGrid, Map<String, Object> parameters) {
		String authFilter = getAuthFilter(user);
		return this.contractOnlinePrepareDao.findPage(jqGrid, parameters, authFilter);
	}

	/**
	 * 获取单个合同签约前评审完成合同选择单对象
	 * @param prepareId
	 * @return
	 */
	public ContractOnlinePrepare get(String prepareId) {
		return this.contractOnlinePrepareDao.get(prepareId);
	}

	/**
	 * 根据字段属性值获取唯一对象
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public ContractOnlinePrepare findUniqueResult(String fieldName, String fieldValue) {
		return this.contractOnlinePrepareDao.findUniqueResult(fieldName, fieldValue);
	}

	/**
	 * 根据合同签约前事业部评审合同创建对象
	 * @return
	 */
	public ContractOnlinePrepare createdByContractSignDeptReview(ContractSignDeptReview deptReview, ContractSignDeptInfor contract) {
		ContractOnlinePrepare contractOnlinePrepare = new ContractOnlinePrepare();
		try {
			BeanUtils.copyProperties(contractOnlinePrepare, deptReview);
			BeanUtils.copyProperties(contractOnlinePrepare, contract);
			contractOnlinePrepare.setStemFrom(ContractOnlinePrepare.STEM_FROM_DEPT_REVIEW);
			contractOnlinePrepare.setSignReviewNo(deptReview.getReviewNo());
			contractOnlinePrepare.setSealStatus(ContractOnlinePrepare.SEAL_DRAFT);
			contractOnlinePrepare.setFilingStatus(ContractOnlinePrepare.FILING_DRAFT);
			contractOnlinePrepare.setOnlineStatus(ContractOnlinePrepare.ONLINE_DRAFT);
			contractOnlinePrepare.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.contractOnlinePrepareDao.saveOrUpdate(contractOnlinePrepare);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contractOnlinePrepare;
	}
	
	/**
	 * 根据合同签约前公司评审合同创建对象
	 * @return
	 */
	public ContractOnlinePrepare createdByContractSignReview(ContractSignReview review, ContractSignInfor contract) {
		ContractOnlinePrepare contractOnlinePrepare = new ContractOnlinePrepare();
		try {
			BeanUtils.copyProperties(contractOnlinePrepare, review);
			BeanUtils.copyProperties(contractOnlinePrepare, contract);
			contractOnlinePrepare.setStemFrom(ContractOnlinePrepare.STEM_FROM_REVIEW);
			contractOnlinePrepare.setSignReviewNo(review.getReviewNo());
			contractOnlinePrepare.setSealStatus(ContractOnlinePrepare.SEAL_DRAFT);
			contractOnlinePrepare.setFilingStatus(ContractOnlinePrepare.FILING_DRAFT);
			contractOnlinePrepare.setOnlineStatus(ContractOnlinePrepare.ONLINE_DRAFT);
			contractOnlinePrepare.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.contractOnlinePrepareDao.saveOrUpdate(contractOnlinePrepare);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contractOnlinePrepare;
	}

	/**
	 * 根据衍生合同创建对象
	 * @return
	 */
	public ContractOnlinePrepare createdByDerivativeContract(DerivativeContract derivativeContract) {
		ContractOnlinePrepare contractOnlinePrepare = new ContractOnlinePrepare();
		try {
			BeanUtils.copyProperties(contractOnlinePrepare, derivativeContract);
			contractOnlinePrepare.setStemFrom(ContractOnlinePrepare.STEM_FROM_DERIVATIVE);
			contractOnlinePrepare.setSignId(derivativeContract.getDerivativeId());
			contractOnlinePrepare.setSignReviewNo(derivativeContract.getDerivativeNo());
			contractOnlinePrepare.setCustomerId(derivativeContract.getPaymentType());
			contractOnlinePrepare.setTotalRmbAmount(derivativeContract.getPaymentAmount());// 付款金额
			contractOnlinePrepare.setSealStatus(ContractOnlinePrepare.SEAL_DRAFT);
			contractOnlinePrepare.setFilingStatus(ContractOnlinePrepare.FILING_DRAFT);
			contractOnlinePrepare.setOnlineStatus(ContractOnlinePrepare.ONLINE_DRAFT);
			contractOnlinePrepare.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.contractOnlinePrepareDao.saveOrUpdate(contractOnlinePrepare);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contractOnlinePrepare;
	}

	/**
	 * 更新数据
	 * @param contractOnlinePrepare
	 */
	public void update(ContractOnlinePrepare contractOnlinePrepare) {
		this.contractOnlinePrepareDao.saveOrUpdate(contractOnlinePrepare);
	}

	/**
	 * 根据记录ID更新前置单状态
	 * @param prepareIds
	 * @param fieldName
	 * @param fieldVal
	 */
	public void updateStatusByPrepareIds(String prepareIds, String fieldName, int fieldVal) {
		Map<String, Object> values = new LinkedHashMap<String, Object>();
		values.put(fieldName, fieldVal);
		Map<String, Object> filterMap = new LinkedHashMap<String, Object>();
		filterMap.put("prepareId", prepareIds.split(","));
		this.contractOnlinePrepareDao.updateByParam(values, filterMap);
	}

	/**
	 * 根据合同ID更新前置单状态
	 * @param inforIds
	 * @param fieldName
	 * @param fieldVal
	 */
	public void updateStatusByInforIds(String inforIds, String fieldName, int fieldVal) {
		Map<String, Object> values = new LinkedHashMap<String, Object>();
		values.put(fieldName, fieldVal);
		Map<String, Object> filterMap = new LinkedHashMap<String, Object>();
		filterMap.put("inforId", inforIds.split(","));
		this.contractOnlinePrepareDao.updateByParam(values, filterMap);
	}

	/**
	 * 衍生合同用印/备案修改状态
	 */
	public void updateStatusByDerivativeFiling(String derivativeId, int sealStatus, int filingStatus) {
		Map<String, Object> values = new LinkedHashMap<String, Object>();
		values.put("sealStatus", sealStatus);
		values.put("filingStatus", filingStatus);
		Map<String, Object> filterMap = new LinkedHashMap<String, Object>();
		filterMap.put("signId", derivativeId);
		this.contractOnlinePrepareDao.updateByParam(values, filterMap);
	}

	/**
	 * 根据衍生合同单ID更新前置单状态
	 * @param derivativeId
	 * @param fieldName
	 * @param fieldVal
	 */
	public void updateStatusByDerivativeId(String derivativeId, String fieldName, int fieldVal) {
		Map<String, Object> values = new LinkedHashMap<String, Object>();
		values.put(fieldName, fieldVal);
		Map<String, Object> filterMap = new LinkedHashMap<String, Object>();
		filterMap.put("signId", derivativeId);
		this.contractOnlinePrepareDao.updateByParam(values, filterMap);
	}

}
