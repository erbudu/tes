package com.supporter.prj.cneec.tpc.use_seal.service;

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

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.contract_online_prepare.entity.ContractOnlinePrepare;
import com.supporter.prj.cneec.tpc.contract_online_prepare.service.ContractOnlinePrepareService;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.protocol_review.entity.ProtocolReview;
import com.supporter.prj.cneec.tpc.protocol_review.service.ProtocolReviewService;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.record_filing_manager.service.RecordFilingManagerService;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.use_seal.dao.UseSealDao;
import com.supporter.prj.cneec.tpc.use_seal.dao.UseSealDetailDao;
import com.supporter.prj.cneec.tpc.use_seal.entity.UseSeal;
import com.supporter.prj.cneec.tpc.use_seal.entity.UseSealDetail;
import com.supporter.prj.cneec.tpc.use_seal.util.LogConstant;
import com.supporter.prj.cneec.tpc.use_seal.util.UseSealConstant;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: UseSealService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-10-23
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class UseSealService {

	@Autowired
	private UseSealDao useSealDao;
	@Autowired
	private UseSealDetailDao useSealDetailDao;
	@Autowired
	private RecordFilingManagerService recordFilingManagerService;
	@Autowired
	private ProtocolReviewService protocolReviewService;
	@Autowired
	private RegisterProjectService registerProjectService;

	@Autowired
	private ContractOnlinePrepareService prepareService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, UseSeal.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param useSeal
	 */
	public void getAuthCanExecute(UserProfile userProfile, UseSeal useSeal) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, UseSeal.MODULE_ID, useSeal.getSealId(), useSeal);
	}

	/**
	 * 获取用印单对象集合
	 * @param user
	 * @param jqGrid
	 * @param useSeal
	 * @return
	 */
	public List<UseSeal> getGrid(UserProfile user, JqGrid jqGrid, UseSeal useSeal) {
		String authFilter = getAuthFilter(user);
		return this.useSealDao.findPage(jqGrid, useSeal, authFilter);
	}

	/**
	 * 获取单个用印单对象
	 * @param sealId
	 * @return
	 */
	public UseSeal get(String sealId) {
		return this.useSealDao.get(sealId);
	}

	/**
	 * 获取单个数据对象(框架协议备案)
	 * @param reviewId
	 * @return
	 */
	public UseSeal getUseSealByProtocolReviewId(String reviewId) {
		UseSeal useSeal = null;
		UseSealDetail detail = this.useSealDetailDao.findUniqueResult("signId", reviewId);
		if (detail != null) {
			useSeal = this.useSealDao.findUniqueResult("sealId", detail.getSealId());
			useSeal.setUseSealDetail(detail);
		}
		return useSeal;
	}
	
	/**
	 * 根据sealId获取UseSealDetail
	 * @param sealId
	 * @return
	 */
	public UseSealDetail getUseSealDetailByInforId(String inforId) {
		return useSealDetailDao.findUniqueResult("inforId", inforId);
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param reviewId
	 * @param user
	 * @return
	 */
	public UseSeal initEditOrViewPageByProtocolReviewId(String reviewId, UserProfile user) {
		UseSeal useSeal = getUseSealByProtocolReviewId(reviewId);
		if (useSeal == null) {
			useSeal = newUseSeal(user);
			useSeal.setSealId(UUIDHex.newId());
			useSeal.setAdd(true);
			useSeal.setRelyBy(TpcConstant.RELY_BY_PROTOCOL_REVIEW);
			ProtocolReview protocolReview = protocolReviewService.get(reviewId);
			useSeal.setProjectId(protocolReview.getProjectId());
			useSeal.setProjectName(protocolReview.getProjectName());
			RegisterProject project = registerProjectService.get(protocolReview.getProjectId());
			useSeal.setSealDeptId(project.getProjectDeptId());
			useSeal.setSealDeptName(project.getProjectDeptName());
			UseSealDetail detail = new UseSealDetail();
			detail.setAdd(true);
			detail.setDetailId(UUIDHex.newId());
			detail.setSealId(useSeal.getSealId());
			detail.setUseSealBusiness(TpcConstant.OUTLINE_AGREEMENT);
			detail.setSignId(protocolReview.getReviewId());
			detail.setReviewNo(protocolReview.getProtocolName());
			// 获取或生成协议编号
			getBusinessNo(reviewId, useSeal.getProjectId(), detail);
			useSeal.setUseSealDetail(detail);
		}
		return useSeal;
	}

	/**
	 * 新建用印单对象
	 * @param user
	 * @return
	 */
	public UseSeal newUseSeal(UserProfile user) {
		UseSeal useSeal = new UseSeal();
		loadUseSeal(useSeal, user);
		useSeal.setSwfStatus(UseSeal.DRAFT);
		return useSeal;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public UseSeal loadUseSeal(UseSeal useSeal, UserProfile user) {
		useSeal.setSealDate(CommonUtil.format(new Date(), "yyyy-MM-dd"));
		useSeal.setCreatedBy(user.getName());
		useSeal.setCreatedById(user.getPersonId());
		useSeal.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		useSeal.setModifiedBy(user.getName());
		useSeal.setModifiedById(user.getPersonId());
		useSeal.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			useSeal.setDeptName(dept.getName());
			useSeal.setDeptId(dept.getDeptId());
			useSeal.setSealDeptName(dept.getName());
			useSeal.setSealDeptId(dept.getDeptId());
		}
		// 设置状态
		useSeal.setSwfStatus(UseSeal.DRAFT);
		return useSeal;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param sealId
	 * @param user
	 * @return
	 */
	public UseSeal initEditOrViewPage(String sealId, UserProfile user) {
		UseSeal useSeal;
		if (StringUtils.isBlank(sealId)) {
			useSeal = newUseSeal(user);
			useSeal.setSealId(UUIDHex.newId());
			useSeal.setAdd(true);
		} else {
			useSeal = (UseSeal) this.useSealDao.get(sealId);
			useSeal.setAdd(false);
			List<UseSealDetail> detailList = this.useSealDetailDao.findBy("sealId", useSeal.getSealId());
			if (detailList != null && detailList.size() == 1) {
				useSeal.setUseSealDetail(detailList.get(0));
			}
		}
		return useSeal;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param useSeal
	 * @param valueMap
	 * @return
	 */
	public UseSeal saveOrUpdate(UserProfile user, UseSeal useSeal, Map<String, Object> valueMap) {
		// 生成编号
		if (StringUtils.isBlank(useSeal.getUseSealNo())) {
			useSeal.setUseSealNo(createdUseSealNo());
		}
		if (useSeal.getAdd()) {
			// 装配基础信息
			loadUseSeal(useSeal, user);
			this.useSealDao.save(useSeal);
		} else {
			// 设置更新时间
			useSeal.setModifiedBy(user.getName());
			useSeal.setModifiedById(user.getPersonId());
			useSeal.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.useSealDao.update(useSeal);
		}
		// 更新从表流水号
		updateUseSealDetail(useSeal);
		return useSeal;
	}

	/**
	 * 保存提交
	 * @param user
	 * @param useSeal
	 * @param valueMap
	 * @return
	 */
	public UseSeal commit(UserProfile user, UseSeal useSeal, Map<String, Object> valueMap) {
		saveOrUpdate(user, useSeal, valueMap);
		// 提交后判断并设置用印信息
		this.startProc(useSeal);
		// 记录日志
		String logMessage = MessageFormat.format(LogConstant.PUBLISH_USE_SEAL_LOG_MESSAGE, useSeal.getProjectName());
		EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(user, LogConstant.PUBLISH_USE_SEAL_LOG_ACTION, logMessage, useSeal, null);
		return useSeal;
	}

	/**
	 * 提交流程操作
	 * @param useSeal
	 */
	public void startProc(UseSeal useSeal) {
		UseSealDetail detail = useSeal.getUseSealDetail();
		// 框架协议用印设置备案信息
		if (detail != null && TpcConstant.OUTLINE_AGREEMENT.equals(detail.getUseSealBusiness())) {
			getBusinessNo(detail.getSignId(), useSeal.getProjectId(), detail);
			this.useSealDetailDao.update(detail);
		}
	}

	/**
	 * 审批完成操作
	 * @param useSeal
	 */
	public void completeExam(UseSeal useSeal) {
		// 将前置单用印状态置为完成
		List<UseSealDetail> detailList = this.useSealDetailDao.findBy("sealId", useSeal.getSealId());
		for (UseSealDetail useSealDetail : detailList) {
			// 修改前置单状态
			if (StringUtils.isNotBlank(useSealDetail.getInforId())) {
				this.prepareService.updateStatusByInforIds(useSealDetail.getInforId(), "sealStatus", ContractOnlinePrepare.SEAL_FINISH);
			}
		}
	}

	/**
	 * 给用印单明细添加流水号
	 * @param useSeal
	 */
	public void updateUseSealDetail(UseSeal useSeal) {
		// 更新从表
		if (useSeal.getUseSealDetail() != null) {
			// 唯一从表时（如框架协议评审）
			UseSealDetail detail = useSeal.getUseSealDetail();
			detail.setSerialNumber(useSeal.getUseSealNo() + "-" + String.format("%03d", 1));
			if (detail.getAdd()) {
				this.useSealDetailDao.save(detail);
			} else {
				this.useSealDetailDao.update(detail);
			}
		} else {
			// 定义要更新从表集合
			List<UseSealDetail> updateList = new ArrayList<UseSealDetail>();
			// 过滤条件
			Map<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("sealId", useSeal.getSealId());
			// 排序
			Map<String, Boolean> orderByMap = new LinkedHashMap<String, Boolean>();
			orderByMap.put("detailId", true);
			List<UseSealDetail> detailList = this.useSealDetailDao.find(params, orderByMap);
			int count = 1;
			int num = 0;
			for (UseSealDetail detail : detailList) {
				detail.setSerialNumber(useSeal.getUseSealNo() + "-" + String.format("%03d", count));
				updateList.add(detail);
				num += detail.getContractCopies();
				count++;
			}
			if (updateList.size() > 0) {
				useSeal.setContractCopies(num);
				this.useSealDao.update(useSeal);
				this.useSealDetailDao.update(updateList);
			}
		}
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param sealIds
	 */
	public void delete(UserProfile user, String sealIds) {
		if (StringUtils.isNotBlank(sealIds)) {
			UseSeal useSeal;
			for (String sealId : sealIds.split(",")) {
				useSeal = this.get(sealId);
				if (useSeal == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, useSeal);
				List<UseSealDetail> detailList = this.useSealDetailDao.findBy("sealId", sealId);
				for (UseSealDetail useSealDetail : detailList) {
					// 恢复前置单未用印
					String inforId = CommonUtil.trim(useSealDetail.getInforId());
					if (inforId.length() > 0) {
						this.prepareService.updateStatusByInforIds(useSealDetail.getInforId(), "sealStatus", ContractOnlinePrepare.SEAL_DRAFT);
					}
				}
				this.useSealDetailDao.deleteByProperty("sealId", sealId);
				this.useSealDao.delete(useSeal);
			}
		}
	}

	/**
	 * 更新对象
	 * @param useSeal
	 * @return
	 */
	public UseSeal update(UseSeal useSeal) {
		this.useSealDao.update(useSeal);
		return useSeal;
	}

	/**
	 * 获取从表集合
	 * @param user
	 * @param jqGrid
	 * @param useSealId
	 * @return
	 */
	public List<UseSealDetail> getDetailGrid(UserProfile user, JqGrid jqGrid, String sealId) {
		return this.useSealDetailDao.findPage(jqGrid, sealId);
	}

	/**
	 * 获取从表
	 * @param detailId
	 * @return
	 */
	public UseSealDetail getUseSealDetail(String detailId) {
		return this.useSealDetailDao.get(detailId);
	}
	
	/**
	 * 初始化从表
	 * @param detailId
	 * @return
	 */
	public UseSealDetail initEditOrViewPageUseSealDetail(String detailId) {
		UseSealDetail useSealDetail;
		if (StringUtils.isBlank(detailId)) {
			useSealDetail = new UseSealDetail();
			useSealDetail.setDetailId(UUIDHex.newId());
			useSealDetail.setAdd(true);
		} else {
			useSealDetail = (UseSealDetail) this.useSealDetailDao.get(detailId);
			useSealDetail.setAdd(false);
		}
		return useSealDetail;
	}

	/**
	 * 保存从表
	 * @param useSealDetail
	 * @param valueMap
	 * @return
	 */
	public UseSealDetail saveOrUpdateUseSealDetail(UseSealDetail useSealDetail, Map<String, Object> valueMap) {
		if (useSealDetail.getAdd()) {
			this.useSealDetailDao.save(useSealDetail);
		} else {
			this.useSealDetailDao.update(useSealDetail);
		}
		String inforId = CommonUtil.trim(useSealDetail.getInforId());
		if (inforId.length() > 0) {
			// 修改前置单状态
			this.prepareService.updateStatusByInforIds(inforId, "sealStatus", ContractOnlinePrepare.SEAL_PROCESS);
		}
		return useSealDetail;
	}

	/**
	 * 删除从表
	 * @param detailIds
	 */
	public void deleteUseSealDetail(String detailIds) {
		if (StringUtils.isNotBlank(detailIds)) {
			UseSealDetail useSealDetail;
			for (String detailId : detailIds.split(",")) {
				useSealDetail = this.getUseSealDetail(detailId);
				// 恢复前置单未用印
				String inforId = CommonUtil.trim(useSealDetail.getInforId());
				if (inforId.length() > 0) {
					this.prepareService.updateStatusByInforIds(inforId, "sealStatus", ContractOnlinePrepare.SEAL_DRAFT);
				}
				// 删除明细
				this.useSealDetailDao.delete(useSealDetail);
			}
		}
	}

	/**
	 * 用印业务类型
	 * @param detailId
	 * @return
	 */
	public List<CheckBoxVO> getUseSealBusinessList(String detailId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String choose = "";// 不设置默认
		if (detailId.length() > 0) {
			UseSealDetail useSealDetail = this.getUseSealDetail(detailId);
			if (useSealDetail != null) {
				choose = useSealDetail.getUseSealBusiness();
			}
		}
		Map<String, String> map = TpcConstant.getUseSealTypeOfContractMap();
		for (String key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("useSealBusiness_" + key, "useSealBusiness", key, map.get(key), key.equals(choose));
			list.add(vo);
		}
		return list;
	}

	/**
	 * 是否已备案
	 * @param detailId
	 * @return
	 */
	public List<CheckBoxVO> getFilingList(String detailId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String choose = "";// 不设置默认
		if (detailId.length() > 0) {
			UseSealDetail useSealDetail = this.getUseSealDetail(detailId);
			if (useSealDetail != null) {
				choose = useSealDetail.isFiling() ? "true" : "false";
			}
		}
		Map<String, String> map = UseSealConstant.getFilingMap();
		for (String key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("filing_" + key, "filing", key, map.get(key), key.equals(choose));
			list.add(vo);
		}
		return list;
	}

	/**
	 * 生成用印单号
	 * @return
	 */
	public synchronized String createdUseSealNo() {
		String serialNumber;
		String NoHead = CommonUtil.format(new Date(), "yyyyMM");
		Integer count = this.useSealDao.getSerialNumberIndex(NoHead);
		String NoEnd = String.format("%03d", count);
		serialNumber = NoHead + NoEnd;
		return serialNumber;
	}

	/**
	 * 根据评审单ID获取备案单销售合同/协议编号
	 * @param reviewId
	 * @return
	 */
	public String getBusinessNo(String reviewId, String projectId, UseSealDetail useSealDetail) {
		String businessNo = "";
		RecordFilingManager filing = this.recordFilingManagerService.getRecordFilingManagerByProtocolReviewId(reviewId);
		if (filing != null) {
			businessNo = filing.getBusinessNo();
			if (filing.getSwfStatus() == RecordFilingManager.COMPLETED) {
				useSealDetail.setFiling(true);
			} else {
				useSealDetail.setFiling(false);
			}
		} else {
			businessNo = createdBusinessNo(projectId, useSealDetail);
			useSealDetail.setFiling(false);
		}
		useSealDetail.setBusinessNo(businessNo);
		return businessNo;
	}

	/**
	 * 生成业务单号(合同签约及评审时即为合同编号)
	 * @param useSealDetail
	 * @return
	 */
	public synchronized String createdBusinessNo(String projectId, UseSealDetail useSealDetail) {
		String contractNo = null;
		boolean change = false;
		String business = CommonUtil.trim(useSealDetail.getUseSealBusiness());
		if (business.equals(TpcConstant.ORDER_PROTOCOL_CHANGER)) {
			//销售合同变更需要销售合同编号
			contractNo = "11";
			change = true;
		} else if (business.equals(TpcConstant.PURCHASE_PROTOCOL_CHANGER)) {
			// 采购合同变更需要采购合同编号
			contractNo = "22";
			change = true;
		}
		String businessNo = this.recordFilingManagerService.getMaxBusinessNo(business, projectId, change, contractNo);
		return businessNo;
	}

	/**
	 * 
	 * @param valueMap
	 * @param user
	 * @return
	 */
	public UseSeal loadUseSealByPre(UseSeal useSeal, Map<String, Object> valueMap, UserProfile user) {
		// 装填基础信息
		loadUseSeal(useSeal, user);
		useSeal.setRelyBy(TpcConstant.RELY_BY_CONTRACT_SIGN_REVIEW);
		String projectId = (String) valueMap.get("projectId");
		if (projectId != null) {
			RegisterProject project = this.registerProjectService.get(projectId);
			useSeal.setProjectId(projectId);
			useSeal.setProjectName(project.getProjectName());
			useSeal.setSealDeptId(project.getProjectDeptId());
			useSeal.setSealDeptName(project.getProjectDeptName());
		}
		return useSeal;
	}

	/**
	 * 初始化页面生成用印单草稿记录
	 * @param valueMap
	 * @param user
	 * @return
	 */
	public UseSeal initUseSealByPre(Map<String, Object> valueMap, UserProfile user) {
		// 经办人一次只能录入一个草稿用印单
		UseSeal useSeal;
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("swfStatus", UseSeal.DRAFT);
		params.put("relyBy", TpcConstant.RELY_BY_CONTRACT_SIGN_REVIEW);
		params.put("createdById", user.getPersonId());
		List<UseSeal> list = this.useSealDao.find(params);
		if (list != null && list.size() > 0) {
			useSeal = list.get(0);
		} else {
			// 生成一个带ID用印草稿
			useSeal = new UseSeal(UUIDHex.newId());
			useSeal.setAdd(true);
		}
		return useSeal;
	}

	/**
	 * 初始化页面生成用印明细后保存用印单
	 * @param valueMap
	 * @param user
	 * @return
	 */
	public UseSeal saveUseSealByPre(UseSeal useSeal, Map<String, Object> valueMap, UserProfile user) {
		if (useSeal.getAdd()) {
			loadUseSealByPre(useSeal, valueMap, user);
			this.useSealDao.save(useSeal);
		}
		return useSeal;
	}

	/**
	 * 初始化生成用印单明细
	 * @param parameters
	 * @return
	 */
	public UseSealDetail createUseSealDetailByPre(Map<String, Object> valueMap) {
		UseSealDetail useSealDetail = new UseSealDetail(UUIDHex.newId());
		useSealDetail.setAdd(true);
		if (valueMap != null && !valueMap.isEmpty()) {
			String sealId = (String) valueMap.get("sealId");
			useSealDetail.setSealId(sealId);
			String prepareId = (String) valueMap.get("prepareId");
			ContractOnlinePrepare prepare = this.prepareService.get(prepareId);
			useSealDetail.setSignId(prepare.getSignId());
			useSealDetail.setReviewNo(prepare.getSignReviewNo());
			useSealDetail.setInforId(prepare.getInforId());
			useSealDetail.setContractParty(prepare.getContractParty());
			useSealDetail.setContractName(prepare.getContractName());
			if (prepare.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				useSealDetail.setUseSealBusiness(TpcConstant.ORDER_CONTRACT);
			} else {
				useSealDetail.setUseSealBusiness(TpcConstant.PURCHASE_CONTRACT);
			}
			String projectId = prepare.getProjectId();
			getBusinessNo(prepare.getSignId(), projectId, useSealDetail);
		}
		return useSealDetail;
	}

	/**
	 * 初始化页面用印单确认完成（提交）
	 * @param valueMap
	 * @param user
	 * @return
	 */
	public UseSeal saveAndSubmitByPre(UseSeal useSeal, Map<String, Object> valueMap, UserProfile user) {
		// 验证是否可以提交
		String json = "{\"valid\": true,\"msg\": \"\"}";
		// 验证用印明细不能为空
		if (this.useSealDetailDao.findBy("sealId", useSeal.getSealId()).size() > 0) {
			saveOrUpdate(user, useSeal, valueMap);
		} else {
			json = "{\"valid\": false,\"msg\": \"useSealDetail-fail-empty\"}";
		}
		useSeal.setKeyword(json);
		return useSeal;
	}

}
