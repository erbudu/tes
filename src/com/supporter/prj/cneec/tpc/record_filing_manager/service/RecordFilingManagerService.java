package com.supporter.prj.cneec.tpc.record_filing_manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.contract_change.entity.ContractChange;
import com.supporter.prj.cneec.tpc.contract_online_prepare.entity.ContractOnlinePrepare;
import com.supporter.prj.cneec.tpc.contract_online_prepare.service.ContractOnlinePrepareService;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignInfor;
import com.supporter.prj.cneec.tpc.contract_sign_review.entity.ContractSignReview;
import com.supporter.prj.cneec.tpc.contract_sign_review.service.ContractSignReviewService;
import com.supporter.prj.cneec.tpc.contract_sign_review_dept.util.ContractSignReviewUtil;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChange;
import com.supporter.prj.cneec.tpc.protocol_review.entity.ProtocolReview;
import com.supporter.prj.cneec.tpc.protocol_review.service.ProtocolReviewService;
import com.supporter.prj.cneec.tpc.record_filing_manager.dao.RecordFilingManagerDao;
import com.supporter.prj.cneec.tpc.record_filing_manager.entity.RecordFilingManager;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.use_seal.dao.UseSealDetailDao;
import com.supporter.prj.cneec.tpc.use_seal.entity.UseSeal;
import com.supporter.prj.cneec.tpc.use_seal.entity.UseSealDetail;
import com.supporter.prj.cneec.tpc.use_seal.service.UseSealService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;


@Service
@Transactional(TransManager.APP)
public class RecordFilingManagerService {
	@Autowired
	private RecordFilingManagerDao recordFilingManagerDao;
	@Autowired
	private ProtocolReviewService protocolReviewService;
	@Autowired
	private ContractSignReviewService reviewService;
	@Autowired
	private UseSealService useSealService;
	@Autowired
	private UseSealDetailDao useSealDetailDao;
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
		String authFilter = AuthUtil.getAuthFilter(userProfile, RecordFilingManager.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param recordFiling
	 */
	public void getAuthCanExecute(UserProfile userProfile, RecordFilingManager recordFiling) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, RecordFilingManager.MODULE_ID, recordFiling.getRecordFilingId(), recordFiling);
	}

	/**
	 * 初始化新建备案
	 * @param user
	 * @return
	 */
	public RecordFilingManager newRecordFilingManager(UserProfile user){
		RecordFilingManager recordFilingManager = new RecordFilingManager();
		loadingRecordFilingManager(recordFilingManager, user);
		return recordFilingManager;
	}
	
	/**
	 * 装填备案基本信息
	 * @param lrecordFiling_N
	 * @param user
	 * @return
	 */
	public RecordFilingManager loadingRecordFilingManager(RecordFilingManager lrecordFiling_N, UserProfile user){
		lrecordFiling_N.setCreatedBy(user.getName());
		lrecordFiling_N.setCreatedById(user.getPersonId());
		lrecordFiling_N.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		lrecordFiling_N.setModifiedBy(user.getName());
		lrecordFiling_N.setModifiedById(user.getPersonId());
		lrecordFiling_N.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			lrecordFiling_N.setDeptName(dept.getName());
			lrecordFiling_N.setDeptId(dept.getDeptId());
		}
		return lrecordFiling_N;
	}
	
	/**
	 * 进入新建、编辑或查看页面需要加载的信息
	 * @param recordFilingId
	 * @param user
	 * @return
	 */
	public RecordFilingManager initEditOrViewPage(String recordFilingId, UserProfile user){
		RecordFilingManager recordFilingManager;
		if (StringUtils.isBlank(recordFilingId)){
			recordFilingManager = newRecordFilingManager(user);
			recordFilingManager.setRecordFilingId(UUIDHex.newId());
			recordFilingManager.setSwfStatus(RecordFilingManager.DRAFT);
			recordFilingManager.setIsNew(true);
		}else{
			recordFilingManager = this.recordFilingManagerDao.get(recordFilingId);
			recordFilingManager.setIsNew(false);
		}
		return recordFilingManager;
	}
	
	/**
	 * 获取单个数据对象
	 * @param recordFilingId
	 * @return
	 */
	public RecordFilingManager get(String recordFilingId){
		return this.recordFilingManagerDao.get(recordFilingId);
	}

	/**
	 * 获取单个数据对象(框架协议备案)
	 * @param reviewId
	 * @return
	 */
	public RecordFilingManager getRecordFilingManagerByProtocolReviewId(String reviewId) {
		return this.recordFilingManagerDao.findUniqueResult("reviewId", reviewId);
	}

	/**
	 * 根据评审单创建备案记录(框架协议备案)
	 * @param protocolReviewId
	 * @param user
	 * @return
	 */
	public RecordFilingManager initRecordFilingByProtocolReivewId(String reviewId, UserProfile user){
		RecordFilingManager recordFilingManager = getRecordFilingManagerByProtocolReviewId(reviewId);
		if (recordFilingManager == null) {
			recordFilingManager = newRecordFilingManager(user);
			recordFilingManager.setRecordFilingId(UUIDHex.newId());
			recordFilingManager.setSwfStatus(RecordFilingManager.DRAFT);
			recordFilingManager.setIsNew(true);
			recordFilingManager.setRelyBy(TpcConstant.RELY_BY_PROTOCOL_REVIEW);
			ProtocolReview protocolReview = protocolReviewService.get(reviewId);
			recordFilingManager.setProjectId(protocolReview.getProjectId());
			recordFilingManager.setProjectName(protocolReview.getProjectName());
			recordFilingManager.setRecordFilingTypeCode(TpcConstant.OUTLINE_AGREEMENT);
			recordFilingManager.setRecordFilingType(TpcConstant.getRecordFilingTypeMap().get(TpcConstant.OUTLINE_AGREEMENT));
			recordFilingManager.setReviewId(protocolReview.getReviewId());
			recordFilingManager.setReviewNo(protocolReview.getProtocolName());
			recordFilingManager.setBusinessNo(getBusinessNo(recordFilingManager));
		}
		return recordFilingManager;
	}

	/**
	 * 根据项目ID初始化对象
	 * @param projectId
	 * @param user
	 * @return
	 */
	public RecordFilingManager initRecordFilingManagerByProjectId(String projectId, UserProfile user) {
		RecordFilingManager recordFilingManager = newRecordFilingManager(user);
		recordFilingManager.setRecordFilingId(UUIDHex.newId());
		recordFilingManager.setSwfStatus(RecordFilingManager.DRAFT);
		recordFilingManager.setIsNew(true);
		if (projectId.length() > 0) {
			RegisterProject project = registerProjectService.get(projectId);
			recordFilingManager.setProjectId(project.getProjectId());
			recordFilingManager.setProjectName(project.getProjectName());
		}
		return recordFilingManager;
	}

	/**
	 * 分页展示备案
	 * @param user
	 * @param jqGrid
	 * @param recordFiling
	 * @return
	 */
	public List<RecordFilingManager> getGrid(UserProfile user, JqGrid jqGrid, RecordFilingManager recordFiling){
		String authFilter = getAuthFilter(user);
		return recordFilingManagerDao.findPage(jqGrid, recordFiling, authFilter);
	}
	
	/**
	 * 保存或更新
	 * @param user
	 * @param recordFiling
	 * @param valueMap
	 * @return
	 */
	public RecordFilingManager saveOrUpdate(UserProfile user, RecordFilingManager recordFiling, Map<String, Object> valueMap){
		//获取相关文件名称
		String fileName = getFileName(recordFiling);
		recordFiling.setFilesName(fileName);
		if (recordFiling.getIsNew()){//如果是新建
			recordFiling = loadingRecordFilingManager(recordFiling, user);
			this.recordFilingManagerDao.save(recordFiling);
			// 修改前置单状态
			updatePrepareProcess(recordFiling);
		}else{//如果是编辑
			recordFiling.setModifiedBy(user.getName());
			recordFiling.setModifiedById(user.getPersonId());
			recordFiling.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.recordFilingManagerDao.update(recordFiling);
		}
		return recordFiling;
	}
	
	/**
	 * 保存提交
	 * @param user
	 * @param recordFiling
	 * @param valueMap
	 * @return
	 */
	public RecordFilingManager commit(UserProfile user, RecordFilingManager recordFiling, Map<String, Object> valueMap){
		//生成备案号
		if (StringUtils.isBlank(recordFiling.getRecordFilingNo())) {
			recordFiling.setRecordFilingNo(createdRecordFilingNo());
		}
		saveOrUpdate(user, recordFiling, valueMap);
		if (TpcConstant.DERIVATION_CONTRACT.equals(recordFiling.getRecordFilingTypeCode())) {
			this.completeCommit(recordFiling);// 衍生合同用印/备案
		}
		return recordFiling;
	}

	/**
	 * 流程提交操作
	 * @param recordFiling
	 */
	public void startProc(RecordFilingManager recordFiling) {
		// 设置用印信息及合同编号
		getBusinessNo(recordFiling);
		// 衍生合同重新设置盖章状态（盖章与用印共用一个字段设置合同编号时会置为否）
		if (TpcConstant.DERIVATION_CONTRACT.equals(recordFiling.getRecordFilingTypeCode())) {
			recordFiling.setIsUseSeal(RecordFilingManager.IS_STAMP);
		}
		this.recordFilingManagerDao.update(recordFiling);
	}

	/**
	 * 合同签约评审备案直接完成
	 * @param user
	 * @param recordFiling
	 * @param valueMap
	 * @return
	 */
	public RecordFilingManager complete(UserProfile user, RecordFilingManager recordFiling, Map<String, Object> valueMap){
		//生成备案号
		if (StringUtils.isBlank(recordFiling.getRecordFilingNo())) {
			recordFiling.setRecordFilingNo(createdRecordFilingNo());
		}
		saveOrUpdate(user, recordFiling, valueMap);
		getBusinessNo(recordFiling);
		recordFiling.setSwfStatus(RecordFilingManager.COMPLETED);
		this.recordFilingManagerDao.update(recordFiling);
		updatePrepareFinish(recordFiling);
		return recordFiling;
	}

	/**
	 * 提交完成执行方法(衍生合同备案)
	 * @param recordFiling
	 */
	public void completeCommit(RecordFilingManager recordFiling) {
		if (!recordFiling.isStamp()) {// 不用印盖章设置合同编号并直接完成（用印时提交流程后设置合同编号）
			// 设置用印信息及合同编号
			getBusinessNo(recordFiling);
			recordFiling.setSwfStatus(RecordFilingManager.COMPLETED);
			this.recordFilingManagerDao.update(recordFiling);
			updatePrepareFinish(recordFiling);
		}
	}

	/**
	 * 衍生合同佣金代理合同备案需要检查签报扫描件
	 * @param recordFiling
	 */
	public void sendExamNotifyMsg(RecordFilingManager recordFiling, UserProfile user) {
		saveOrUpdate(user, recordFiling, null);
		if (StringUtils.isBlank(recordFiling.getRecordFilingNo())) {
			recordFiling.setRecordFilingNo(createdRecordFilingNo());
		}
		recordFiling.setSwfStatus(RecordFilingManager.PROCESSING);
		this.recordFilingManagerDao.update(recordFiling);
		// 获取审核人（合同管理员,刘佩鑫）
		String roleId = "CONTRACT_MANAGEMENT";
		Role role = EIPService.getRoleService().getRole(roleId);
		Dept dept = user.getDept();
		List<Person> list = EIPService.getRoleService().getPersonsForDept(role, dept);
		for (Person person : list) {
			Message message = new Todo();
			message.setPersonId(person.getPersonId());
			message.setEventTitle(recordFiling.getDeptName() + recordFiling.getProjectName() + "佣金代理合同备案签报扫描件如下，请确认");
			message.setNotifyTime(new Date());
			message.setWebPageURL("tpc/derivative_contract_filing/derivativeContractFiling_exam_notify.html?recordFilingId=" + recordFiling.getRecordFilingId());
			message.setMessageType(ITodo.MsgType.CC);
			message.setRelatedRecordTable(RecordFilingManager.MODULE_ID);
			EIPService.getBMSService().addMessage(message);
		}
	}

	/**
	 * 刘佩鑫签报确认
	 */
	public void completeExam(RecordFilingManager recordFiling) {
		recordFiling.setSwfStatus(RecordFilingManager.COMPLETED);
		this.recordFilingManagerDao.update(recordFiling);
		updatePrepareFinish(recordFiling);
	}

	/**
	 * 刘佩鑫签报驳回
	 */
	public void rejectExam(RecordFilingManager recordFiling) {
		recordFiling.setSwfStatus(RecordFilingManager.DRAFT);
		this.recordFilingManagerDao.update(recordFiling);
		Message message = new Todo();
		message.setPersonId(recordFiling.getCreatedById());
		message.setEventTitle(recordFiling.getDeptName() + recordFiling.getProjectName() + "佣金代理合同备案签报申请被驳回");
		message.setNotifyTime(new Date());
		message.setWebPageURL("tpc/derivative_contract_filing/derivativeContractFiling_exam_reject.html?recordFilingId=" + recordFiling.getRecordFilingId());
		message.setMessageType(ITodo.MsgType.CC);
		message.setRelatedRecordTable(RecordFilingManager.MODULE_ID);
		EIPService.getBMSService().addMessage(message);
	}

	/**
	 * 更新对象
	 * @param recordFiling
	 * @return
	 */
	public RecordFilingManager update(RecordFilingManager recordFiling) {
		this.recordFilingManagerDao.update(recordFiling);
		return recordFiling;
	}

	/**
	 * 删除备案
	 * @param recordFilingIds
	 */
	public void delete(UserProfile user, String recordFilingIds){
		if (StringUtils.isNotBlank(recordFilingIds)){
			RecordFilingManager recordFiling;
			for (String recordFilingId : recordFilingIds.split(",")){
				recordFiling = this.get(recordFilingId);
				if (recordFiling == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, recordFiling);
				updatePrepareDraft(recordFiling);
				//先删除相关附件
				deleteFile(recordFilingId);
				//再删除备案信息
				this.recordFilingManagerDao.delete(recordFiling);
			}
		}
	}

	/**
	 * 获取相关文件名称
	 * @param recordFiling
	 * @return
	 */
	public String getFileName(RecordFilingManager recordFiling){
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList(RecordFilingManager.MODULE_ID, RecordFilingManager.BUSI_TYPE_CONTRACT, recordFiling.getRecordFilingId());
		StringBuffer sb = new StringBuffer();
		for (IFile file : list){
			sb.append(file.getFileName()).append(",");
		}
		if (list != null && list.size()>0){
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	
	/**
	 * 删除相关附件
	 * @param recordFilingId
	 */
	public void deleteFile(String recordFilingId){
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("RECORD_FILING", "filesName", recordFilingId);
		if (CollectionUtils.isNotEmpty(list)){
			for(IFile file:list){
				fileUploadService.deleteFile(file.getFileId());
			}
		}
	}
	
	/**
	 * 获取是否已用印
	 * @param recordFilingId
	 * @return
	 */
	public List<CheckBoxVO> getIsUseSealData(String recordFilingId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String choose = null;
		if (StringUtils.isNotBlank(recordFilingId)) {
			RecordFilingManager redordFiling = this.get(recordFilingId);
			if (redordFiling != null) {
				choose = redordFiling.getIsUseSeal();
			}
		}
		Map<String, String> map = RecordFilingManager.getIsUseSealMap();
		for (String key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("isUseSeal_" + key, "isUseSeal", key.toString(), map.get(key), key.equals(choose));
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 获取是否需要盖章
	 * @param recordFilingId
	 * @return
	 */
	public List<CheckBoxVO> getIsStampData(String recordFilingId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String choose = null;
		if (StringUtils.isNotBlank(recordFilingId)) {
			RecordFilingManager redordFiling = this.get(recordFilingId);
			if (redordFiling != null) {
				choose = redordFiling.getIsStamp();
			}
		}
		Map<String, String> map = RecordFilingManager.getIsStampMap();
		for (String key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("isUseSeal_" + key, "isUseSeal", key.toString(), map.get(key), key.equals(choose));
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 获取备案业务类型
	 * @param recordFilingId
	 * @return
	 */
	public List<CheckBoxVO> getRecordFilingTypeData(String recordFilingId, String relyBy) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String choose = null;
		if (StringUtils.isNotBlank(recordFilingId)) {
			RecordFilingManager redordFiling = this.get(recordFilingId);
			if (redordFiling != null) {
				choose = redordFiling.getRecordFilingTypeCode();
			}
		}
		Map<String, String> map;
		if (relyBy.length() > 0 && relyBy.equals(TpcConstant.RELY_BY_CONTRACT_SIGN_REVIEW)) {
			map = TpcConstant.getRecordFilingTypeOfContractMap();
		} else {
			map = TpcConstant.getRecordFilingTypeMap();
		}
		for (String key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("recordFilingTypeCode_" + key, "recordFilingTypeCode", key.toString(), map.get(key), key.equals(choose));
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 根据项目ID和备案业务类型获取评审单
	 * @param prjId
	 * @param businessType
	 * @return
	 */
	public Map<String, String> getReviewMap(String prjId, String recordFilingTypeCode, String recordFilingId){
		Map<String, String> map = new LinkedHashMap<String, String>();
		RecordFilingManager rf = null;
		if (StringUtils.isNotBlank(recordFilingId)){
			rf = get(recordFilingId);
		}
		if(StringUtils.isNotBlank(recordFilingTypeCode)){
			if (TpcConstant.ORDER_CONTRACT.equals(recordFilingTypeCode)){//销售合同备案
				if (rf != null && TpcConstant.ORDER_CONTRACT.equals(rf.getRecordFilingTypeCode())){
					map.put(rf.getReviewId(), rf.getReviewNo());
				}
				List<ContractSignInfor> list = this.recordFilingManagerDao.getOrderSignReviewList(prjId);
				if (list != null){
					for (ContractSignInfor infor : list){
						ContractSignReview review = this.reviewService.get(infor.getSignId());
						map.put(infor.getInforId(), review.getReviewNo()+infor.getContractParty());
					}
				}
			}else if (TpcConstant.ORDER_PROTOCOL_CHANGER.equals(recordFilingTypeCode)){//销售合同协议变更
				if (rf != null && TpcConstant.ORDER_PROTOCOL_CHANGER.equals(rf.getRecordFilingTypeCode())){
					map.put(rf.getReviewId(), rf.getReviewNo());
				}
				List<OrderChange> list = this.recordFilingManagerDao.getOrderProtocolChange(prjId);
//				if (list != null){
//					for (OrderChange entity:list){
//						map.put(entity.getChangeId(), entity.getBusinessNo());
//					}
//				}
			}else if (TpcConstant.PURCHASE_CONTRACT.equals(recordFilingTypeCode)){//采购合同
				if (rf != null && TpcConstant.PURCHASE_CONTRACT.equals(rf.getRecordFilingTypeCode())){
					map.put(rf.getReviewId(), rf.getReviewNo());
				}
				List<ContractSignInfor> list = this.recordFilingManagerDao.getContractSignReviewList(prjId);
				if (list != null){
					for (ContractSignInfor infor : list){
						ContractSignReview review = this.reviewService.get(infor.getSignId());
						map.put(infor.getInforId(), review.getReviewNo()+infor.getContractParty());
					}
				}
			}else if (TpcConstant.PURCHASE_PROTOCOL_CHANGER.equals(recordFilingTypeCode)){//采购合同协议变更
				if (rf != null && TpcConstant.PURCHASE_PROTOCOL_CHANGER.equals(rf.getRecordFilingTypeCode())){
					map.put(rf.getReviewId(), rf.getReviewNo());
				}
				List<ContractChange> list = this.recordFilingManagerDao.getContractProtocolChange(prjId);
//				if (list != null){
//					for (ContractChange entity:list){
//						map.put(entity.getChangeId(), entity.getSerialNumber());
//					}
//				}
			}else if (TpcConstant.OUTLINE_AGREEMENT.equals(recordFilingTypeCode)){//框架协议
				if (rf != null && TpcConstant.OUTLINE_AGREEMENT.equals(rf.getRecordFilingTypeCode())){
					map.put(rf.getReviewId(), rf.getReviewNo());
				}
				List<ProtocolReview> list = this.recordFilingManagerDao.getProtocolReviewList(prjId);
				if (list != null){
					for (ProtocolReview pr : list){
						map.put(pr.getReviewId(), pr.getProtocolName());
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 变更了评审单
	 * @param recordFiling
	 * @return
	 */
	public String changeReview(RecordFilingManager recordFiling){
		String result = null;
		String reviewId = recordFiling.getReviewId();
		UseSealDetail detail = this.useSealDetailDao.findUniqueResult("signId", reviewId);
		if (detail != null){//用印不为空，返回已用印、用印ID、用印单号和销售合同协议编号
			result = "1,"+detail.getDetailId()+","+detail.getSerialNumber()+","+detail.getBusinessNo();
		}else{
			RecordFilingManager rfEntity = get(recordFiling.getRecordFilingId());
			String isUseSeal = rfEntity!=null ? rfEntity.getIsUseSeal() : "";
			String rfBusinessNo = rfEntity!=null ? rfEntity.getBusinessNo() : "";
			if (StringUtils.isNotBlank(rfBusinessNo) && (RecordFilingManager.IS_NOT_USE_SEAL.equals(isUseSeal))){
				result = "0,,,"+rfBusinessNo;
			}else{
				result = "0,,,";
			}
		}
		return result;
	}

	/**
	 * 根据框架协议评审单ID获取协议编号
	 * @param reviewId
	 * @return
	 */
	public String getBusinessNo(RecordFilingManager recordFilingManager) {
		String businessNo = "";
		String properName = null;
		String propValue = null;
		if (recordFilingManager.getRecordFilingTypeCode().equals(TpcConstant.OUTLINE_AGREEMENT)) {// 框架协议根据评审单ID获取用印
			properName = "signId";
			propValue =	recordFilingManager.getReviewId();
		} else {// 合同签约评审、衍生合同
			properName = "inforId";
			propValue =	recordFilingManager.getPreformId();
		}
		UseSealDetail detail = this.useSealDetailDao.findUniqueResult(properName, propValue);
		if (detail != null) {
			UseSeal useSeal = this.useSealService.get(detail.getSealId());
			businessNo = detail.getBusinessNo();
			recordFilingManager.setBusinessNo(businessNo);
			recordFilingManager.setSealId(detail.getDetailId());
			recordFilingManager.setSealNo(detail.getSerialNumber());
			if (useSeal.getSwfStatus() == UseSeal.COMPLETED) {
				recordFilingManager.setIsUseSeal(RecordFilingManager.IS_USE_SEAL);
			} else {
				recordFilingManager.setIsUseSeal(RecordFilingManager.IS_NOT_USE_SEAL);
			}
		} else {
			businessNo = createdBusinessNo(recordFilingManager);
			recordFilingManager.setIsUseSeal(RecordFilingManager.IS_NOT_USE_SEAL);
		}
		recordFilingManager.setBusinessNo(businessNo);
		return businessNo;
	}

	/**
	 * 生成协议编号/销售合同编号/采购合同编号/衍生合同编号/销售合同变更编号/采购合同变更编号/衍生合同变更编号
	 * @param recordFiling
	 * @return
	 */
	public synchronized String createdBusinessNo(RecordFilingManager recordFiling) {
		String contractNo = null;
		boolean change = false;
		String typeCode = CommonUtil.trim(recordFiling.getRecordFilingTypeCode());
		if (typeCode.equals(TpcConstant.ORDER_PROTOCOL_CHANGER)) {
			//销售合同变更需要销售合同编号
			contractNo = "";
			change = true;
		} else if (typeCode.equals(TpcConstant.PURCHASE_PROTOCOL_CHANGER)) {
			// 采购合同变更需要采购合同编号
			contractNo = "";
			change = true;
		} else if (typeCode.equals(TpcConstant.DERIVATION_CONTRACT_CHANGE)) {
			// 衍生合同变更需要衍生合同编号
			contractNo = "";
			change = true;
		}
		String businessNo = getMaxBusinessNo(typeCode, recordFiling.getProjectId(), change, contractNo);
		return businessNo;
	}

	/**
	 * 获取当前项目下最大业务类型编号
	 *
	 * @param typeCode
	 * @param projectId
	 * @param contractNo
	 * @return
	 */
	public String getMaxBusinessNo(String typeCode, String projectId, boolean change, String contractNo) {
		// 用印及备案均可生成编号且无先后顺序，所以需要同时查找备案单和用印单
		String businessNo = "";
		if (StringUtils.isBlank(typeCode) || StringUtils.isBlank(projectId)) {
			return null;
		}
		RegisterProject project = this.registerProjectService.get(projectId);
		// 前置编号
		String projectNo = project.getProjectNo();
		String NoHead = "";
		// 判断用印/备案业务
		if (typeCode.equals(TpcConstant.OUTLINE_AGREEMENT)) {// 框架协议编号（项目编号+(FA)）
			businessNo = projectNo + "(FA)";
			return businessNo;
		} else if (typeCode.equals(TpcConstant.ORDER_CONTRACT)) {// 销售合同编号（项目编号+3位顺序号）
			NoHead = projectNo;
		} else if (typeCode.equals(TpcConstant.PURCHASE_CONTRACT)) {// 采购合同编号（项目编号+“-”+3位顺序号）
			NoHead = projectNo + "-";
		} else if (typeCode.equals(TpcConstant.DERIVATION_CONTRACT)) {// 衍生合同编号（项目编号+“-”+3位顺序号）
			NoHead = projectNo + "-";
		} else if (typeCode.equals(TpcConstant.ORDER_PROTOCOL_CHANGER)) {// 销售合同变更编号（销售合同编号+“e”+顺序号）
			NoHead = contractNo + "e";
		} else if (typeCode.equals(TpcConstant.PURCHASE_PROTOCOL_CHANGER)) {// 采购合同变更编号（采购合同编号+“e”+顺序号）
			NoHead = contractNo + "e";
		} else if (typeCode.equals(TpcConstant.DERIVATION_CONTRACT_CHANGE)) {// 衍生合同变更编号（衍生合同编号+“e”+顺序号）
			NoHead = contractNo + "e";
		}
		// 过滤条件(取已生成合同编号销售合同或采购合同的记录)
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		Map<String, Object> params2 = new LinkedHashMap<String, Object>();
		params.put("businessNo", NoHead + "%");
		params.put("recordFilingTypeCode", typeCode);
		params2.put("businessNo", NoHead + "%");
		params2.put("useSealBusiness", typeCode);
		List<String> likeSearhNames = new ArrayList<String>();
		likeSearhNames.add("businessNo");
		Map<String, Boolean> orderByMap = new LinkedHashMap<String, Boolean>();
		orderByMap.put("businessNo", false);
		List<RecordFilingManager> filingList = this.recordFilingManagerDao.queryByParam(params, likeSearhNames, orderByMap);
		List<UseSealDetail> useSealDetailList = this.useSealDetailDao.queryByParam(params2, likeSearhNames, orderByMap);
		int filingMaxIndex = 0;
		if (filingList.size() > 0) {// 截取取最大编号值顺序号（去掉前置编号）
			filingMaxIndex = Integer.parseInt(filingList.get(0).getBusinessNo().substring(NoHead.length()));
		}
		int detailMaxIndex = 0;
		if (useSealDetailList.size() > 0) {
			detailMaxIndex = Integer.parseInt(useSealDetailList.get(0).getBusinessNo().substring(NoHead.length()));
		}
		int maxIndex = filingMaxIndex > detailMaxIndex ? filingMaxIndex : detailMaxIndex;
		String NoEnd;
		if (!change) {
			NoEnd = String.format("%03d", (maxIndex + 1));// 销售/采购/衍生合同编号
		} else {
			NoEnd = String.valueOf(maxIndex + 1);// 销售/采购/衍生合同变更编号
		}
		businessNo = NoHead + NoEnd;
		return businessNo;
	}

	/**
	 * 生成备案编号
	 * @return
	 */
	public String createdRecordFilingNo(){
		String recordFilingNo = null;
		String NoHead = CommonUtil.format(new Date(), "yyyyMM");
		Integer count = this.recordFilingManagerDao.getRecordFilingIndex(NoHead);
		String NoEnd = String.format("%03d", count);
		recordFilingNo = NoHead + NoEnd;
		return recordFilingNo;
	}

	/**
	 * 根据前置单初始化生成备案单
	 *
	 * @param inforId
	 * @param parameters
	 * @param user
	 * @return
	 */
	public RecordFilingManager initRecordFilingByInforId(String inforId, Map<String, Object> parameters, UserProfile user) {
		RecordFilingManager recordFiling = this.recordFilingManagerDao.findUniqueResult("preformId", inforId);
		if (recordFiling == null) {
			recordFiling = newRecordFilingManager(user);
			recordFiling.setRecordFilingId(UUIDHex.newId());
			recordFiling.setSwfStatus(RecordFilingManager.DRAFT);
			recordFiling.setIsNew(true);
			ContractOnlinePrepare prepare = this.prepareService.findUniqueResult("inforId", inforId);
			recordFiling.setProjectId(prepare.getProjectId());
			recordFiling.setProjectName(prepare.getProjectName());
			recordFiling.setReviewId(prepare.getSignId());
			recordFiling.setReviewNo(prepare.getSignReviewNo());
			if (prepare.getInforType() == ContractSignReviewUtil.INFOR_TYPE_ORDER) {
				recordFiling.setRecordFilingTypeCode(TpcConstant.ORDER_CONTRACT);
			} else {
				recordFiling.setRecordFilingTypeCode(TpcConstant.PURCHASE_CONTRACT);
			}
			recordFiling.setRecordFilingType(TpcConstant.getRecordFilingTypeMap().get(recordFiling.getRecordFilingTypeCode()));
			recordFiling.setRelyBy(TpcConstant.RELY_BY_CONTRACT_SIGN_REVIEW);
			recordFiling.setPreformId(inforId);
			recordFiling.setContractName(prepare.getContractName());
		}
		return recordFiling;
	}

	/**
	 * 根据前置单初始化生成备案单
	 *
	 * @param prepareId
	 * @param parameters
	 * @param user
	 * @return
	 */
	public RecordFilingManager initRecordFilingByDerivativeId(String derivativeId, Map<String, Object> parameters, UserProfile user) {
		RecordFilingManager recordFiling = this.recordFilingManagerDao.findUniqueResult("preformId", derivativeId);
		if (recordFiling == null) {
			recordFiling = newRecordFilingManager(user);
			recordFiling.setRecordFilingId(UUIDHex.newId());
			recordFiling.setSwfStatus(RecordFilingManager.DRAFT);
			recordFiling.setIsNew(true);
			// 衍生合同用印/备案
			recordFiling.setRecordFilingId(UUIDHex.newId());
			ContractOnlinePrepare prepare = this.prepareService.findUniqueResult("signId", derivativeId);
			if (prepare != null) {
				recordFiling.setProjectId(prepare.getProjectId());
				recordFiling.setProjectName(prepare.getProjectName());
				recordFiling.setRecordFilingTypeCode(TpcConstant.DERIVATION_CONTRACT);
				recordFiling.setRecordFilingType(TpcConstant.getRecordFilingTypeMap().get(TpcConstant.DERIVATION_CONTRACT));
				recordFiling.setReviewId(prepare.getDerivativeId());
				recordFiling.setReviewNo(prepare.getDerivativeNo());
				recordFiling.setRelyBy(TpcConstant.RELY_BY_DERIVATIVE_CONTRACT);
				recordFiling.setPreformId(derivativeId);
				recordFiling.setContractName(prepare.getContractName());
			}
		}
		return recordFiling;
	}

	/**
	 * 删除操作设置前置单为草稿
	 * @param recordFiling
	 */
	public void updatePrepareDraft(RecordFilingManager recordFiling) {
		// 删除备案单后将前置单置为默认状态
		String preformId = CommonUtil.trim(recordFiling.getPreformId());
		String relyBy = CommonUtil.trim(recordFiling.getRelyBy());
		if (preformId.length() > 0 && relyBy.length() > 0) {
			if (relyBy.equals(TpcConstant.RELY_BY_CONTRACT_SIGN_REVIEW)) {
				// 合同签约合同备案，通过合同ID找到前置单修改备案状态为默认
				this.prepareService.updateStatusByInforIds(recordFiling.getPreformId(), "filingStatus", ContractOnlinePrepare.FILING_DRAFT);
			} else if (relyBy.equals(TpcConstant.RELY_BY_DERIVATIVE_CONTRACT)) {
				// 如果是衍生合同用印/备案，通过衍生合同单ID修改合同前置表的用印和备案状态
				String derivativeId = recordFiling.getPreformId();
				this.prepareService.updateStatusByDerivativeFiling(derivativeId, ContractOnlinePrepare.SEAL_DRAFT, ContractOnlinePrepare.FILING_DRAFT);
			}
		}
	}

	/**
	 * 保存记录后设置前置单为进行中
	 */
	public void updatePrepareProcess(RecordFilingManager recordFiling) {
		String preformId = CommonUtil.trim(recordFiling.getPreformId());
		String relyBy = CommonUtil.trim(recordFiling.getRelyBy());
		if (preformId.length() > 0 && relyBy.length() > 0) {
			if (relyBy.equals(TpcConstant.RELY_BY_CONTRACT_SIGN_REVIEW)) {
				// 合同签约合同备案，通过合同ID找到前置单修改备案状态为默认
				this.prepareService.updateStatusByInforIds(preformId, "filingStatus", ContractOnlinePrepare.FILING_PROCESS);
			} else if (relyBy.equals(TpcConstant.RELY_BY_DERIVATIVE_CONTRACT)) {
				// 如果是衍生合同用印/备案，通过衍生合同单ID修改合同前置表的用印和备案状态
				int sealStatus = ContractOnlinePrepare.SEAL_DRAFT;
				if (recordFiling.isStamp()) {// 用印
					sealStatus = ContractOnlinePrepare.SEAL_PROCESS;
				}
				this.prepareService.updateStatusByDerivativeFiling(preformId, sealStatus, ContractOnlinePrepare.FILING_PROCESS);
			}
		}
	}

	/**
	 * 审批完成设置前置单为完成状态
	 * @param recordFiling
	 */
	public void updatePrepareFinish(RecordFilingManager recordFiling) {
		String preformId = CommonUtil.trim(recordFiling.getPreformId());
		String relyBy = CommonUtil.trim(recordFiling.getRelyBy());
		if (preformId.length() > 0 && relyBy.length() > 0) {
			if (relyBy.equals(TpcConstant.RELY_BY_CONTRACT_SIGN_REVIEW)) {
				// 修改前置单状态
				this.prepareService.updateStatusByInforIds(preformId, "filingStatus", ContractOnlinePrepare.FILING_FINISH);
			} else if (relyBy.equals(TpcConstant.RELY_BY_DERIVATIVE_CONTRACT)) {
				// 用印/备案完成时修改合同前置表状态为已备案
				int sealStatus = ContractOnlinePrepare.SEAL_DRAFT;
				if (recordFiling.isStamp()) {// 用印盖章
					sealStatus = ContractOnlinePrepare.SEAL_FINISH;
				}
				this.prepareService.updateStatusByDerivativeFiling(preformId, sealStatus, ContractOnlinePrepare.FILING_FINISH);
			}
		}
	}

	public ListPage<RecordFilingManager> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<RecordFilingManager> listPage = new ListPage<RecordFilingManager>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.recordFilingManagerDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

}
