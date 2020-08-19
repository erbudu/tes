package com.supporter.prj.cneec.tpc.credit_letter_apply.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.credit_letter_apply.dao.CreditLetterApplyDao;
import com.supporter.prj.cneec.tpc.credit_letter_apply.entity.CreditLetterApply;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;


@Service
@Transactional(TransManager.APP)
public class CreditLetterApplyService {
	
	@Autowired
	private CreditLetterApplyDao creditLetterApplyDao;

	@Autowired
	private PrjContractTableService prjContractTableService;
	
	@Autowired
	private RegisterProjectService registerProjectService;
	
	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, CreditLetterApply.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param creditLetterApply
	 */
	public void getAuthCanExecute(UserProfile userProfile, CreditLetterApply creditLetterApply) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, CreditLetterApply.MODULE_ID, creditLetterApply.getCreditLetterId(), creditLetterApply);
	}

	/**
	 * 初始化
	 * @param user
	 * @return
	 */
	public CreditLetterApply newCreditLetterApply(UserProfile user){
		CreditLetterApply creditLetterApply = new CreditLetterApply();
		creditLetterApply = loadingCreditLetterApply(creditLetterApply, user);
		creditLetterApply.setBusinessRatio(0.00);
		creditLetterApply.setAmountCreditLetter(0.000);
		creditLetterApply.setMarginRatio(0.00);
		return creditLetterApply;
	}
	
	/**
	 * 装填基本信息
	 * @param lapply_N
	 * @param user
	 * @return
	 */
	public CreditLetterApply loadingCreditLetterApply(CreditLetterApply lapply_N, UserProfile user){
		lapply_N.setCreditLetterApplyDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		lapply_N.setCreatedBy(user.getName());
		lapply_N.setCreatedById(user.getPersonId());
		lapply_N.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		lapply_N.setModifiedBy(user.getName());
		lapply_N.setModifiedById(user.getPersonId());
		lapply_N.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		return lapply_N;
	}
	
	/**
	 * 进入新建或编辑页面
	 * @param creditLetterId
	 * @param user
	 * @return
	 */
	public CreditLetterApply initEditOrViewPage(String creditLetterId, UserProfile user){
		CreditLetterApply creditLetterApply;
		if (StringUtils.isBlank(creditLetterId)){
			creditLetterApply = newCreditLetterApply(user);
			creditLetterApply.setCreditLetterId(UUIDHex.newId());
			creditLetterApply.setApplyPersonId(user.getPersonId());
			creditLetterApply.setApplyPerson(user.getName());
			Dept dept = user.getDept();
			if (dept != null) {
				creditLetterApply.setDeptId(dept.getDeptId());
				creditLetterApply.setDeptName(dept.getName());
			}
			creditLetterApply.setSwfStatus(CreditLetterApply.DRAFT);
			creditLetterApply.setIsNew(true);
		}else{
			creditLetterApply = this.creditLetterApplyDao.get(creditLetterId);
			creditLetterApply.setIsNew(false);
		}
		return creditLetterApply;
	}
	
	/**
	 * 获取单个数据对象
	 * @param creditLetterId
	 * @return
	 */
	public CreditLetterApply get(String creditLetterId){
		return this.creditLetterApplyDao.get(creditLetterId);
	}
	
	/**
	 * 分页列表
	 * @param user
	 * @param jqGrid
	 * @param creditLetterApply
	 * @return
	 */
	public List<CreditLetterApply> getGrid(UserProfile user, JqGrid jqGrid, CreditLetterApply creditLetterApply){
		String authFilter = getAuthFilter(user);
		return creditLetterApplyDao.findPage(jqGrid, creditLetterApply, authFilter);
	}
	
	/**
	 * 保存或者更新
	 * @param user
	 * @param creditLetterApply
	 * @return
	 */
	public CreditLetterApply saveOrUpdate(UserProfile user, CreditLetterApply creditLetterApply){
		//获取相关附件名称
		String filesName = getFilesName(creditLetterApply);
		creditLetterApply.setFilesName(filesName);
		//获取已选择的采购合同
		PrjContractTable contract = this.prjContractTableService.get(creditLetterApply.getContractId());
		creditLetterApply.setContractNo(contract.getContractNo());
		creditLetterApply.setContractRecordFilingNo(contract.getFilingNo());
		if (creditLetterApply.getIsNew()){//如果是新建
			creditLetterApply = loadingCreditLetterApply(creditLetterApply, user);
			this.creditLetterApplyDao.save(creditLetterApply);
		}else{//如果是编辑
			creditLetterApply.setModifiedBy(user.getName());
			creditLetterApply.setModifiedById(user.getPersonId());
			creditLetterApply.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.creditLetterApplyDao.update(creditLetterApply);
		}
		return creditLetterApply;
	}

	public void setPrjManagerId(CreditLetterApply creditLetterApply) {
		String projectId = creditLetterApply.getProjectId();
		RegisterProject project = registerProjectService.get(projectId);
		creditLetterApply.setPrjManagerId(project.getProjectChargeId());
		update(creditLetterApply);
	}

	/**
	 * 保存提交
	 * @param user
	 * @param creditLetterApply
	 * @return
	 */
	public CreditLetterApply commit(UserProfile user, CreditLetterApply creditLetterApply){
		// 生成开证流水号
		creditLetterApply.setCreditLetterOrderNo(createdCreditLetterOrderNo());
		saveOrUpdate(user, creditLetterApply);
		setPrjManagerId(creditLetterApply);
		return creditLetterApply;
	}

	public void update(CreditLetterApply creditLetterApply) {
		this.creditLetterApplyDao.update(creditLetterApply);
	}

	/**
	 * 删除
	 * @param creditLetterIds
	 */
	public void delete(UserProfile user, String creditLetterIds){
		if (StringUtils.isNotBlank(creditLetterIds)){
			CreditLetterApply creditLetterApply;
			for (String creditLetterId : creditLetterIds.split(",")){
				creditLetterApply = this.get(creditLetterId);
				if (creditLetterApply == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, creditLetterApply);
				deleteFile(creditLetterId);
				this.creditLetterApplyDao.delete(creditLetterId);
			}
		}
	}
	
	/**
	 * 获取相关附件list
	 * @param creditLetterId
	 * @return
	 */
	public List<IFile> getFilesList(String creditLetterId){
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("TPCCRELETAPP", "TPC_CREDIT_LETTER_APPLY", creditLetterId);
		return list;
	}
	
	/**
	 * 获取相关文件名称
	 * @param creditLetterApply
	 * @return
	 */
	public String getFilesName(CreditLetterApply creditLetterApply){
		List<IFile> list = getFilesList(creditLetterApply.getCreditLetterId());
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
	 * @param creditLetterId
	 */
	public void deleteFile(String creditLetterId){
		List<IFile> list = getFilesList(creditLetterId);
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		if (CollectionUtils.isNotEmpty(list)){
			for(IFile file:list){
				fileUploadService.deleteFile(file.getFileId());
			}
		}
	}
	
	/**
	 * 创建信用证流水号
	 * @return
	 */
	public String createdCreditLetterOrderNo(){
		String creditLetterOrderNo = null;
		Calendar date = Calendar.getInstance();
		String NoHead = String.valueOf(date.get(Calendar.YEAR));
		Integer count = this.creditLetterApplyDao.getCreditLetterApplyIndex(NoHead);
		String NoEnd = String.format("%05d", (count));
		creditLetterOrderNo = NoHead + NoEnd;
		return creditLetterOrderNo;
	}
	
	/**
	 * 获取经营性质
	 * @param creditLetterId
	 * @return
	 */
	public List<CheckBoxVO> getBusinessTypeData(String creditLetterId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String choose = null;
		if (creditLetterId.length() > 0) {
			CreditLetterApply creditLetter = this.get(creditLetterId);
			if (creditLetter != null) {
				choose = creditLetter.getBusinessTypeCode();
			}
		}
		Map<String, String> map = CreditLetterApply.getBusinessTypeMap();
		for (String key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("businessTypeCode_" + key, "businessTypeCode", key.toString(), map.get(key), key.equals(choose));
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 获取付款期限
	 * @param creditLetterId
	 * @return
	 */
	public List<CheckBoxVO> getSettlementTermData(String creditLetterId) {
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		String choose = null;
		if (creditLetterId.length() > 0) {
			CreditLetterApply creditLetter = this.get(creditLetterId);
			if (creditLetter != null) {
				choose = (CreditLetterApply.FUTURRE).equals(creditLetter.getSettlementTermCode()) ? CreditLetterApply.FUTURRE : CreditLetterApply.NOW;
			}
		}
		Map<String, String> map = CreditLetterApply.getSettlementTermMap();
		for (String key : map.keySet()) {
			CheckBoxVO vo = new CheckBoxVO("settlementTermCode_" + key, "settlementTermCode", key.toString(), map.get(key), key.equals(choose));
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 获取进口国别
	 * @return
	 */
	public Map<String, String> getForeignData(){
		Map<String, String> map = new HashMap<String, String>();
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems(TpcConstant.AREA, TpcConstant.FOREIGN);
		for (IComCodeTableItem f : list) {
			map.put(f.getItemId(), f.getItemValue());
			List<IComCodeTableItem> itemList = EIPService.getComCodeTableService().getSubItems(TpcConstant.AREA, f.getItemId());
			for (IComCodeTableItem item : itemList) {
				map.put(item.getItemId(), item.getItemValue());
			}
		}
		return map;
	}
	
	/**
	 * 根据项目ID获取审批完成的采购合同
	 * @param prjId
	 * @return
	 */
	public Map<String, String> selectContractData(String projectId, UserProfile userProfile) {
		return this.prjContractTableService.getPurchaseContractByPrjId(projectId, userProfile);
	}
}
