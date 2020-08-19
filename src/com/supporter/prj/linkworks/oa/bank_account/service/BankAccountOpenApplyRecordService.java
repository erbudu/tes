package com.supporter.prj.linkworks.oa.bank_account.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyDao;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyEffecDao;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyRecordDao;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyEffec;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyRecord;
import com.supporter.prj.linkworks.oa.bank_account.util.LogConstant;
import com.supporter.util.CommonUtil;


@Service
public class BankAccountOpenApplyRecordService {
	@Autowired
	private BankAccountOpenApplyRecordDao bankAccountOpenApplyRecordDao;
	
	@Autowired
	private BankAccountOpenApplyDao bankAccountOpenApplyDao;
	
	@Autowired
	private BankAccountOpenApplyEffecDao bankAccountOpenApplyEffecDao;



	public BankAccountOpenApplyRecord get(String moduleId) {
		return bankAccountOpenApplyRecordDao.get(moduleId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public BankAccountOpenApplyRecord initEditOrViewPage(JqGrid jqGrid,String applyId,String recordId ,UserProfile user) {
		BankAccountOpenApply bankAccountOpenApply =  bankAccountOpenApplyDao.get(applyId);
		if (!StringUtils.isBlank(applyId)) {
			BankAccountOpenApplyRecord bankAccountOpenApplyRecord =  bankAccountOpenApplyRecordDao.getByApplyId(applyId);
			if(bankAccountOpenApplyRecord!=null){//已经存在备案
				bankAccountOpenApplyRecord.setAdd(false);
				if(bankAccountOpenApply!=null){
					bankAccountOpenApplyRecord.setNationality(bankAccountOpenApply.getNationality());//国别
				}
				return bankAccountOpenApplyRecord;
			}else{//从未备案
				bankAccountOpenApplyRecord = newBankAccountOpenApplyRecord(user);
				
				if(bankAccountOpenApply!=null){
					bankAccountOpenApplyRecord.setApplyId(bankAccountOpenApply.getApplyId());//申请id
					bankAccountOpenApplyRecord.setApplicationNumber(bankAccountOpenApply.getApplicationNumber());//申请单编号
					bankAccountOpenApplyRecord.setProjectName(bankAccountOpenApply.getProjectName());//项目名称
					bankAccountOpenApplyRecord.setAccountName(bankAccountOpenApply.getAccountName());//银行账户名称
					bankAccountOpenApplyRecord.setOpenBank(bankAccountOpenApply.getOpeningBank());//开户银行
					bankAccountOpenApplyRecord.setAccountCurrencyId(bankAccountOpenApply.getAccountCurrencyId());//账户币别
					//bankAccountOpenApplyRecord.setAccountCurrency(bankAccountOpenApply.getAccountCurrency());//账户币别
					bankAccountOpenApplyRecord.setAccountNatureId(bankAccountOpenApply.getAccountNatureId());//账户性质Id
					bankAccountOpenApplyRecord.setAccountNature(bankAccountOpenApply.getAccountNature());//账户性质
					bankAccountOpenApplyRecord.setBankAddress(bankAccountOpenApply.getBankAddress());//银行地址
					bankAccountOpenApplyRecord.setAuthorization(bankAccountOpenApply.getAuthorization());//授权
					bankAccountOpenApplyRecord.setAuthdateFrom(bankAccountOpenApply.getAuthdateFrom());//授权开始时间
					bankAccountOpenApplyRecord.setAuthdateTo(bankAccountOpenApply.getAuthdateTo());//授权结束时间
					bankAccountOpenApplyRecord.setAuthorizationPersonIds(bankAccountOpenApply.getAuthorizationPersonids());
					bankAccountOpenApplyRecord.setAuthorizationPersonNames(bankAccountOpenApply.getAuthorizationPersonnames());
					bankAccountOpenApplyRecord.setDepartmentManagerId(bankAccountOpenApply.getControlledCompanyId());//部门经理Id
					bankAccountOpenApplyRecord.setDepartmentManager(bankAccountOpenApply.getControlledCompany());//部门经理
					bankAccountOpenApplyRecord.setNationality(bankAccountOpenApply.getNationality());//国别
					bankAccountOpenApplyRecord.setAuthorizationPeriod(bankAccountOpenApply.getAuthorizationPeriod());//预留印鉴
				}
				bankAccountOpenApplyRecord.setAdd(true);  
				return bankAccountOpenApplyRecord;
			}
		}else{
			BankAccountOpenApplyRecord bankAccountOpenApplyRecord =  bankAccountOpenApplyRecordDao.get(recordId);
			if(bankAccountOpenApplyRecord!=null&&bankAccountOpenApplyRecord.getApplyId()!=null){
				BankAccountOpenApply bankAccountOpenApply1 =  bankAccountOpenApplyDao.get(bankAccountOpenApplyRecord.getApplyId());
				if(bankAccountOpenApply1!=null){
					bankAccountOpenApplyRecord.setNationality(bankAccountOpenApply1.getNationality());
				}
				
			}
			return bankAccountOpenApplyRecord;
		}
	}
	
	
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public BankAccountOpenApplyRecord viewPage(String applyId, UserProfile user) {
			BankAccountOpenApplyRecord bankAccountOpenApplyRecord =  bankAccountOpenApplyRecordDao.get(applyId);
			return bankAccountOpenApplyRecord;
	}
	
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public BankAccountOpenApplyRecord newBankAccountOpenApplyRecord(UserProfile auserprf_U){
    	BankAccountOpenApplyRecord lbankAccountOpenApplyRecord_N = new BankAccountOpenApplyRecord();
    	lbankAccountOpenApplyRecord_N.setRecordId(com.supporter.util.UUIDHex.newId());
    	lbankAccountOpenApplyRecord_N.setCreatedById(auserprf_U.getPersonId());
    	lbankAccountOpenApplyRecord_N.setCreatedBy(auserprf_U.getName()); 
    	String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    	lbankAccountOpenApplyRecord_N.setCreatedDate(date);
    	lbankAccountOpenApplyRecord_N.setThoseResponsibleId(auserprf_U.getPersonId());//责任人Id
    	lbankAccountOpenApplyRecord_N.setThoseResponsible(auserprf_U.getName());//责任人姓名
    	
//    	//单号
    	lbankAccountOpenApplyRecord_N.setRecordNumber(getCurrentNo());
    	lbankAccountOpenApplyRecord_N.setStatus(0l);
        return lbankAccountOpenApplyRecord_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<BankAccountOpenApplyRecord> getGrid(UserProfile user, JqGrid jqGrid, BankAccountOpenApplyRecord bankAccountOpenApplyRecord) {
		List<BankAccountOpenApplyRecord> list1=this.bankAccountOpenApplyRecordDao.findPage(user,jqGrid, bankAccountOpenApplyRecord);//根据输入的查询条件查询列表
		return list1;	
		
	}
	
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public BankAccountOpenApplyRecord saveOrUpdate(UserProfile user, BankAccountOpenApplyRecord bankAccountOpenApplyRecord, Map< String, Object > valueMap) {
		BankAccountOpenApplyRecord ret = null;
		//账户性质
		String accountNature = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_ACCOUNT").getDisplay(bankAccountOpenApplyRecord.getAccountNatureId());
		bankAccountOpenApplyRecord.setAccountNature(accountNature);
		//币别
		String accountCurrency = EIPService.getComCodeTableService().getCodeTable("CURRENCY_CATEGORY").getDisplay(bankAccountOpenApplyRecord.getAccountCurrencyId());
		bankAccountOpenApplyRecord.setAccountCurrency(accountCurrency);		
		if (bankAccountOpenApplyRecord.getAdd()) {// 新建
		
			//保存主表
			this.bankAccountOpenApplyRecordDao.save(bankAccountOpenApplyRecord);			
			ret = bankAccountOpenApplyRecord;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_BANKOPEN_RECORD_LOG_MESSAGE, bankAccountOpenApplyRecord.getAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS2).info(
					user, LogConstant.ADD_BANKOPEN_RECORD_LOG_ACTION, logMessage,
					bankAccountOpenApplyRecord, null);			
		} else {// 编辑
			bankAccountOpenApplyRecord.setModifiedBy(user.getName());
			bankAccountOpenApplyRecord.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountOpenApplyRecord.setModifiedDate(date);
			//编辑之后保存主表
			this.bankAccountOpenApplyRecordDao.update(bankAccountOpenApplyRecord);

			ret = bankAccountOpenApplyRecord;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_BANKOPEN_RECORD_LOG_MESSAGE, bankAccountOpenApplyRecord.getAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS2).info(
					user, LogConstant.EDIT_BANKOPEN_RECORD_LOG_ACTION, logMessage,
					bankAccountOpenApplyRecord, null);			
		}
		return ret;

	}

	
	 public  String getCurrentNo(){
		 		String no=	bankAccountOpenApplyRecordDao.getRecordNumber();
			    SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
				String year=sdf.format(new Date());
	    		if(year.equals(no.substring(10,14))){
	    			//2017-002
	    			String nm = no.substring(5,8);
	    			String number = String.valueOf((Integer.parseInt(nm)+1));
	    			if(number.length()<nm.length()){
	    				no = "";
	    				for (int i = 0; i < nm.length()-number.length(); i++) {
							no += "0";
						}
	    				no += number;
	    			}else{
	    				no = number;
	    			}
	    			no="开立（备）"+no+"号("+year+")";
	    		}else{
	    			no="开立（备）001号("+year+")";
	    		}
	    	return no;
	    }
	
	
	
    /**
	 * 保存提交
	 * 
	 * @param user 用户信息
	 * @param apply 实体类
	 * @return
	 */
	public BankAccountOpenApplyRecord commit(UserProfile user, BankAccountOpenApplyRecord bankAccountOpenApplyRecord, Map< String, Object > valueMap) {
		BankAccountOpenApplyRecord ret = null;
		boolean isNew=bankAccountOpenApplyRecord.getAdd();
		//账户性质
		String accountNature = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_ACCOUNT").getDisplay(bankAccountOpenApplyRecord.getAccountNatureId());
		bankAccountOpenApplyRecord.setAccountNature(accountNature);	
		//币别
		String accountCurrency = EIPService.getComCodeTableService().getCodeTable("CURRENCY_CATEGORY").getDisplay(bankAccountOpenApplyRecord.getAccountCurrencyId());
		bankAccountOpenApplyRecord.setAccountCurrency(accountCurrency);			
		if(isNew){			 
			//状态（备案生效）
			bankAccountOpenApplyRecord.setStatus(1l);
			//保存
			this.bankAccountOpenApplyRecordDao.save(bankAccountOpenApplyRecord);
			//同时修改对应的申请表的状态（改为审批完成已备案）
			BankAccountOpenApply bankAccountOpenApply=this.bankAccountOpenApplyDao.get(bankAccountOpenApplyRecord.getApplyId());
			if(bankAccountOpenApply!=null){
				bankAccountOpenApply.setStatus(Long.valueOf(BankAccountOpenApply.COMPLETED_RE));//审批完成已备案
				this.bankAccountOpenApplyDao.update(bankAccountOpenApply);
			}
			
			//同时修改对应的生效表
//			BankAccountOpenApplyEffec bankAccountOpenApplyEffec=this.bankAccountOpenApplyEffecDao.getByApplyId(bankAccountOpenApplyRecord.getApplyId());
//			if(bankAccountOpenApplyEffec!=null){
//				bankAccountOpenApplyEffec.setOpenApplyRecordId(bankAccountOpenApplyRecord.getRecordId());//开立备案Id
//				bankAccountOpenApplyEffec.setOpenTime(bankAccountOpenApplyRecord.getOpenTime());//开户时间
//				bankAccountOpenApplyEffec.setAccountTerm(bankAccountOpenApplyRecord.getAccountTerm());//账户期限
//				bankAccountOpenApplyEffec.setBankAccountName(bankAccountOpenApplyRecord.getAccountName());//银行账户名称				
//				bankAccountOpenApplyEffec.setThebankAccount(bankAccountOpenApplyRecord.getThebankAccount());//银行账号
//				bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.COMPLETED_RE));//状态
//				this.bankAccountOpenApplyEffecDao.update(bankAccountOpenApplyEffec);
//			}
			
			
			BankAccountOpenApplyEffec bankAccountOpenApplyEffec= new BankAccountOpenApplyEffec();
			//将申请表相同于生效表属性的值复制到生效表
			BeanUtils.copyProperties(bankAccountOpenApply,bankAccountOpenApplyEffec);
			//保存生效表
			bankAccountOpenApplyEffec.setBankAccountName(bankAccountOpenApply.getAccountName());//银行账户名称
			bankAccountOpenApplyEffec.setEffectiveId(com.supporter.util.UUIDHex.newId());
			bankAccountOpenApplyEffec.setChangeTimes(0l);
			//bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.COMPLETED_NORE+""));//开立完成未备案
			if(bankAccountOpenApplyEffec.getNationalityId()!=null&&bankAccountOpenApplyEffec.getNationalityId().equals("BANK-C-001")){//国别等于中国
				bankAccountOpenApplyEffec.setIsOutside("no");
			}else{//国别不等于中国
				bankAccountOpenApplyEffec.setIsOutside("yes");
			}
			bankAccountOpenApplyEffec.setOpenApplyRecordId(bankAccountOpenApplyRecord.getRecordId());//开立备案Id
			bankAccountOpenApplyEffec.setOpenTime(bankAccountOpenApplyRecord.getOpenTime());//开户时间
			bankAccountOpenApplyEffec.setAccountTerm(bankAccountOpenApplyRecord.getAccountTerm());//账户期限				
			bankAccountOpenApplyEffec.setBankAccountName(bankAccountOpenApplyRecord.getAccountName());//银行账户名称
			bankAccountOpenApplyEffec.setThebankAccount(bankAccountOpenApplyRecord.getThebankAccount());//银行账号
			bankAccountOpenApplyEffec.setThebankAccount(bankAccountOpenApplyRecord.getContact());//联系方式
			bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.COMPLETED_RE));//状态
			this.bankAccountOpenApplyEffecDao.save(bankAccountOpenApplyEffec);			
			
			ret = bankAccountOpenApplyRecord;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_BANKOPEN_RECORD_LOG_MESSAGE, bankAccountOpenApplyRecord.getAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS2).info(
					user, LogConstant.ADD_BANKOPEN_RECORD_LOG_ACTION, logMessage,
					bankAccountOpenApplyRecord, null);			
		} else {// 编辑
			
			bankAccountOpenApplyRecord.setModifiedBy(user.getName());
			bankAccountOpenApplyRecord.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountOpenApplyRecord.setModifiedDate(date);
			//状态（备案生效）
			bankAccountOpenApplyRecord.setStatus(1l);
			//编辑之后保存
			this.bankAccountOpenApplyRecordDao.update(bankAccountOpenApplyRecord);
			//同时修改对应的申请表的状态（改为审批完成已备案）
			BankAccountOpenApply bankAccountOpenApply=this.bankAccountOpenApplyDao.get(bankAccountOpenApplyRecord.getApplyId());
			bankAccountOpenApply.setStatus(Long.valueOf(BankAccountOpenApply.COMPLETED_RE));//审批完成已备案
			this.bankAccountOpenApplyDao.update(bankAccountOpenApply);
			
			//同时修改对应的生效表
//			BankAccountOpenApplyEffec bankAccountOpenApplyEffec=this.bankAccountOpenApplyEffecDao.getByApplyId(bankAccountOpenApplyRecord.getApplyId());
//			if(bankAccountOpenApplyEffec!=null){
//				bankAccountOpenApplyEffec.setOpenApplyRecordId(bankAccountOpenApplyRecord.getRecordId());//开立备案Id
//				bankAccountOpenApplyEffec.setOpenTime(bankAccountOpenApplyRecord.getOpenTime());//开户时间
//				bankAccountOpenApplyEffec.setAccountTerm(bankAccountOpenApplyRecord.getAccountTerm());//账户期限				
//				bankAccountOpenApplyEffec.setBankAccountName(bankAccountOpenApplyRecord.getAccountName());//银行账户名称
//				bankAccountOpenApplyEffec.setThebankAccount(bankAccountOpenApplyRecord.getThebankAccount());//银行账号
//				bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.COMPLETED_RE));//状态
//				this.bankAccountOpenApplyEffecDao.update(bankAccountOpenApplyEffec);
//			}
			
			
			BankAccountOpenApplyEffec bankAccountOpenApplyEffec= new BankAccountOpenApplyEffec();
			//将申请表相同于生效表属性的值复制到生效表
			BeanUtils.copyProperties(bankAccountOpenApply,bankAccountOpenApplyEffec);
			//保存生效表
			bankAccountOpenApplyEffec.setBankAccountName(bankAccountOpenApply.getAccountName());//银行账户名称
			bankAccountOpenApplyEffec.setEffectiveId(com.supporter.util.UUIDHex.newId());
			bankAccountOpenApplyEffec.setChangeTimes(0l);
			//bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.COMPLETED_NORE+""));//开立完成未备案
			if(bankAccountOpenApplyEffec.getNationalityId()!=null&&bankAccountOpenApplyEffec.getNationalityId().equals("BANK-C-001")){//国别等于中国
				bankAccountOpenApplyEffec.setIsOutside("no");
			}else{//国别不等于中国
				bankAccountOpenApplyEffec.setIsOutside("yes");
			}
			bankAccountOpenApplyEffec.setOpenApplyRecordId(bankAccountOpenApplyRecord.getRecordId());//开立备案Id
			bankAccountOpenApplyEffec.setOpenTime(bankAccountOpenApplyRecord.getOpenTime());//开户时间
			bankAccountOpenApplyEffec.setAccountTerm(bankAccountOpenApplyRecord.getAccountTerm());//账户期限				
			bankAccountOpenApplyEffec.setBankAccountName(bankAccountOpenApplyRecord.getAccountName());//银行账户名称
			bankAccountOpenApplyEffec.setThebankAccount(bankAccountOpenApplyRecord.getThebankAccount());//银行账号
			bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.COMPLETED_RE));//状态
			this.bankAccountOpenApplyEffecDao.save(bankAccountOpenApplyEffec);
			
			
			ret = bankAccountOpenApplyRecord;
			
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_BANKOPEN_RECORD_LOG_MESSAGE, bankAccountOpenApplyRecord.getAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS2).info(
					user, LogConstant.EDIT_BANKOPEN_RECORD_LOG_ACTION, logMessage,
					bankAccountOpenApplyRecord, null);			
		}
		return ret;
	}
	

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String applyIds) {
		if (StringUtils.isNotBlank(applyIds)) {
			for (String applyId : applyIds.split(",")) {
				BankAccountOpenApplyRecord bankAccountOpenApplyRecord=bankAccountOpenApplyRecordDao.get(applyId);
				if(bankAccountOpenApplyRecord==null){
					continue;
				}
				//权限验证(判断是不是有编辑信息系统维护申请的权限)
				//AuthUtil.canExecute(user, BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, bankAccountOpenApplyRecord.getBankAccountOpenApplyId(), bankAccountOpenApplyRecord);	
				//删除主表
				this.bankAccountOpenApplyRecordDao.delete(bankAccountOpenApplyRecord);

			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_BANKOPEN_RECORD_LOG_MESSAGE, "删除的主键id为："+applyIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS2).info(
					user, LogConstant.DELETE_BANKOPEN_RECORD_LOG_ACTION, logMessage,
					null, null);			

		}
	}
	
	
	/**
	 * 附件下载部分
	 * @param bankAccountOpenApplyRecord
	 * @return
	 */
	public String getFiles(BankAccountOpenApply bankAccountOpenApplyRecord){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAMAINTENAN", "OAMAINTENAN2", bankAccountOpenApplyRecord.getApplyId());
		for(IFile file:list){
			sb.append("<a onclick=\"downloads('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 修改（流程处理类调用的方法，用于修改状态和保存流程id）
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public void update(BankAccountOpenApplyRecord bankAccountOpenApplyRecord) {	
		this.bankAccountOpenApplyRecordDao.update(bankAccountOpenApplyRecord);
	}
	
	



	
	
	
}
