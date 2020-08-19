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
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountChangeApplyDao;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountChanApplyRecordDao;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyEffecDao;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyRecordDao;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountChangeApply;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountChanApplyRecord;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyEffec;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyRecord;
import com.supporter.prj.linkworks.oa.bank_account.util.LogConstant;
import com.supporter.util.CommonUtil;


@Service
public class BankAccountChanApplyRecordService {
	@Autowired
	private BankAccountChanApplyRecordDao bankAccountChanApplyRecordDao;	
	@Autowired
	private BankAccountChangeApplyDao bankAccountChangeApplyDao;
	@Autowired
	private BankAccountOpenApplyEffecDao bankAccountOpenApplyEffecDao;
	@Autowired
	private BankAccountOpenApplyRecordDao bankAccountOpenApplyRecordDao;



	public BankAccountChanApplyRecord get(String moduleId) {
		return bankAccountChanApplyRecordDao.get(moduleId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public BankAccountChanApplyRecord initEditOrViewPage(JqGrid jqGrid,String changeApplyId, UserProfile user) {
		if (!StringUtils.isBlank(changeApplyId)) {
			BankAccountChanApplyRecord bankAccountChanApplyRecord =  bankAccountChanApplyRecordDao.getByChangeApplyId(changeApplyId);
			if(bankAccountChanApplyRecord!=null){//本次变更已经备案				
				bankAccountChanApplyRecord.setAdd(false);
				//查询出上次变更的备案
				return bankAccountChanApplyRecord;
			}else{//本次变更不存在备案
				bankAccountChanApplyRecord = newBankAccountChanApplyRecord(user);
				BankAccountChangeApply bankAccountChangeApply =  bankAccountChangeApplyDao.get(changeApplyId);
				if(bankAccountChangeApply!=null){		
					bankAccountChanApplyRecord.setChangeApplyId(bankAccountChangeApply.getChangeApplyId());//申请id
					bankAccountChanApplyRecord.setChangeNumber(bankAccountChangeApply.getChangeNumber());//申请单编号
					bankAccountChanApplyRecord.setEffectiveId(bankAccountChangeApply.getEffectiveId());//生效Id
					BankAccountOpenApplyEffec bankAccountOpenApplyEffec=this.bankAccountOpenApplyEffecDao.get(bankAccountChangeApply.getEffectiveId());
					if(bankAccountOpenApplyEffec!=null){
						bankAccountChanApplyRecord.setNationality(bankAccountOpenApplyEffec.getNationality());//国别
						Long changeTimes=bankAccountOpenApplyEffec.getChangeTimes();
						if(changeTimes!=null&&changeTimes==0){//说明这是生效表的第一变更，还不存在变更备案，初始化变更备案的时候取对应的开立备案中的数据
							
							//BankAccountOpenApplyEffec bankAccountOpenApplyEffec= new BankAccountOpenApplyEffec();
							//将生效表相同于变更备案表属性的值复制到变更备案表
							BeanUtils.copyProperties(bankAccountOpenApplyEffec,bankAccountChanApplyRecord);
							//根据开立申请备案主键Id获取出对应的申请备案记录
							if(bankAccountOpenApplyEffec.getOpenApplyRecordId()!=null){
								BankAccountOpenApplyRecord bankAccountOpenApplyRecord=this.bankAccountOpenApplyRecordDao.get(bankAccountOpenApplyEffec.getOpenApplyRecordId());
								if(bankAccountOpenApplyRecord!=null){
									bankAccountChanApplyRecord.setZipCode(bankAccountOpenApplyRecord.getZipCode());//邮政编码
									bankAccountChanApplyRecord.setContact(bankAccountOpenApplyRecord.getContact());//联系方式
									bankAccountChanApplyRecord.setNote(bankAccountOpenApplyRecord.getNote());//备注
									bankAccountChanApplyRecord.setBankContactsPhoneCalls(bankAccountOpenApplyRecord.getBankContactsPhoneCalls());//银行联系人及电话
									bankAccountChanApplyRecord.setEmail(bankAccountOpenApplyRecord.getEmail());//E-main
								}
							}

							bankAccountChanApplyRecord.setStatus(0l);
							
							//bankAccountChanApplyRecord=this.getByOpenApplyRecordId(bankAccountChanApplyRecord, bankAccountChangeApply.getOpenApplyRecordId());
						}else if(changeTimes!=null&&changeTimes>0){//说明这条生效表中的数据以前变更过，并且存在变更备案，所以初始化本次变更备案的时候取上次变更备案的数据
							bankAccountChanApplyRecord=this.getByEffectiveId(bankAccountChanApplyRecord,bankAccountOpenApplyEffec.getEffectiveId());				
						}
					}
					bankAccountChanApplyRecord.setAdd(true);
					return bankAccountChanApplyRecord;					
				}else{//前台传过来的changeApplyId获取不到对应的变更记录
					return null;
				}
			}
		}else{//前台传过来的changeApplyId为空
			return null;
		}
	}
	
	
	
/*	*//**
	 * 根据开立申请备案主键Id获取出对应的申请备案记录 
	 *//*
	public BankAccountChanApplyRecord getByOpenApplyRecordId(BankAccountChanApplyRecord bankAccountChanApplyRecord,String openApplyRecordId) {
		//根据开立申请备案主键Id获取出对应的申请备案记录
		BankAccountOpenApplyRecord bankAccountOpenApplyRecord=this.bankAccountOpenApplyRecordDao.get(openApplyRecordId);
		if(bankAccountOpenApplyRecord!=null){
			bankAccountChanApplyRecord.setProjectName(bankAccountOpenApplyRecord.getProjectName());//项目名称
			bankAccountChanApplyRecord.setOpeningBank(bankAccountOpenApplyRecord.getOpenBank());//开户银行
			bankAccountChanApplyRecord.setOpenTime(bankAccountOpenApplyRecord.getOpenTime());//开户时间
			bankAccountChanApplyRecord.setAccountTerm(bankAccountOpenApplyRecord.getAccountTerm());//账户期限
			bankAccountChanApplyRecord.setThebankAccount(bankAccountOpenApplyRecord.getThebankAccount());//银行账号
			bankAccountChanApplyRecord.setBankAccountName(bankAccountOpenApplyRecord.getAccountName());//银行账户名称
			bankAccountChanApplyRecord.setAccountCurrencyId(bankAccountOpenApplyRecord.getAccountCurrencyId());//账户币别
			//bankAccountChanApplyRecord.setAccountCurrency(bankAccountOpenApplyRecord.getAccountCurrency());//账户币别
			bankAccountChanApplyRecord.setAccountNatureId(bankAccountOpenApplyRecord.getAccountNatureId());//账户性质Id
			//bankAccountChanApplyRecord.setAccountNature(bankAccountOpenApplyRecord.getAccountNature());//账户性质			
			bankAccountChanApplyRecord.setBankAddress(bankAccountOpenApplyRecord.getBankAddress());//银行地址
			bankAccountChanApplyRecord.setZipCode(bankAccountOpenApplyRecord.getZipCode());//邮政编码
			bankAccountChanApplyRecord.setBankContactsPhoneCalls(bankAccountOpenApplyRecord.getBankContactsPhoneCalls());//银行联系人及电话
			bankAccountChanApplyRecord.setEmail(bankAccountOpenApplyRecord.getEmail());//E-main
			bankAccountChanApplyRecord.setAuthorizationPersonids(bankAccountOpenApplyRecord.getAuthorizationPersonIds());//授权人Id
			bankAccountChanApplyRecord.setAuthorizationPersonnames(bankAccountOpenApplyRecord.getAuthorizationPersonNames());//授权人名字
			bankAccountChanApplyRecord.setAuthorization(bankAccountOpenApplyRecord.getAuthorization());//权限
			bankAccountChanApplyRecord.setAuthdateFrom(bankAccountOpenApplyRecord.getAuthdateFrom());//授权开始日期
			bankAccountChanApplyRecord.setAuthdateTo(bankAccountOpenApplyRecord.getAuthdateTo());//授权结束日期
			bankAccountChanApplyRecord.setNote(bankAccountOpenApplyRecord.getNote());//备注
//			bankAccountChanApplyRecord.setThoseResponsibleId(bankAccountOpenApplyRecord.getThoseResponsibleId());//责任人Id
//			bankAccountChanApplyRecord.setThoseResponsible(bankAccountOpenApplyRecord.getThoseResponsible());//责任人
			bankAccountChanApplyRecord.setContact(bankAccountOpenApplyRecord.getContact());//联系方式
			bankAccountChanApplyRecord.setDepartmentManagerId(bankAccountOpenApplyRecord.getDepartmentManagerId());//部门经理Id
			bankAccountChanApplyRecord.setDepartmentManager(bankAccountOpenApplyRecord.getDepartmentManager());//部门经理
			bankAccountChanApplyRecord.setApplyId(bankAccountOpenApplyRecord.getApplyId());//开立申请Id	
		}
		return bankAccountChanApplyRecord;
	}*/
	
	
	
	/**
	 * 根据effectiveId获取出所有的变更备案记录，然后根据生效时间倒叙排列取出第一条数据（最新一条，也就是上次的变更备案记录） 
	 */
	public BankAccountChanApplyRecord getByEffectiveId(BankAccountChanApplyRecord bankAccountChanApplyRecord,String effectiveId) {
		//根据effectiveId获取出所有的变更备案记录，然后根据生效时间倒叙排列取出第一条数据（最新一条，也就是上次的变更备案记录）
		BankAccountChanApplyRecord bankAccountChanApplyRecordLast=this.bankAccountChanApplyRecordDao.getByEffectiveId(effectiveId);
		if(bankAccountChanApplyRecordLast!=null){
			
			//将申请表相同于生效表属性的值复制到生效表
			BeanUtils.copyProperties(bankAccountChanApplyRecordLast,bankAccountChanApplyRecord);
			
			
/*			bankAccountChanApplyRecord.setProjectName(bankAccountChanApplyRecordLast.getProjectName());//项目名称
			bankAccountChanApplyRecord.setOpeningBank(bankAccountChanApplyRecordLast.getOpeningBank());//开户银行
			bankAccountChanApplyRecord.setOpenTime(bankAccountChanApplyRecordLast.getOpenTime());//开户时间
			bankAccountChanApplyRecord.setAccountTerm(bankAccountChanApplyRecordLast.getAccountTerm());//账户期限
			bankAccountChanApplyRecord.setThebankAccount(bankAccountChanApplyRecordLast.getThebankAccount());//银行账号
			bankAccountChanApplyRecord.setBankAccountName(bankAccountChanApplyRecordLast.getBankAccountName());//银行账户名称
			bankAccountChanApplyRecord.setAccountCurrencyId(bankAccountChanApplyRecordLast.getAccountCurrencyId());//账户币别
			//bankAccountChanApplyRecord.setAccountCurrency(bankAccountChanApplyRecordLast.getAccountCurrency());//账户币别
			bankAccountChanApplyRecord.setAccountNatureId(bankAccountChanApplyRecordLast.getAccountNatureId());//账户性质Id
			//bankAccountChanApplyRecord.setAccountNature(bankAccountChanApplyRecordLast.getAccountNature());//账户性质
			bankAccountChanApplyRecord.setBankAddress(bankAccountChanApplyRecordLast.getBankAddress());//银行地址
			bankAccountChanApplyRecord.setZipCode(bankAccountChanApplyRecordLast.getZipCode());//邮政编码
			bankAccountChanApplyRecord.setBankContactsPhoneCalls(bankAccountChanApplyRecordLast.getBankContactsPhoneCalls());//银行联系人及电话
			bankAccountChanApplyRecord.setEmail(bankAccountChanApplyRecordLast.getEmail());//E-main
			bankAccountChanApplyRecord.setAuthorizationPersonids(bankAccountChanApplyRecordLast.getAuthorizationPersonids());//授权人Id
			bankAccountChanApplyRecord.setAuthorizationPersonnames(bankAccountChanApplyRecordLast.getAuthorizationPersonnames());//授权人名字
			bankAccountChanApplyRecord.setAuthorization(bankAccountChanApplyRecordLast.getAuthorization());//授权
			bankAccountChanApplyRecord.setAuthdateFrom(bankAccountChanApplyRecordLast.getAuthdateFrom());//授权开始日期
			bankAccountChanApplyRecord.setAuthdateTo(bankAccountChanApplyRecordLast.getAuthdateTo());//授权结束日期
			bankAccountChanApplyRecord.setNote(bankAccountChanApplyRecordLast.getNote());//备注
//			bankAccountChanApplyRecord.setThoseResponsibleId(bankAccountChanApplyRecordLast.getThoseResponsibleId());//责任人Id
//			bankAccountChanApplyRecord.setThoseResponsible(bankAccountChanApplyRecordLast.getThoseResponsible());//责任人
			bankAccountChanApplyRecord.setContact(bankAccountChanApplyRecordLast.getContact());//联系方式
			bankAccountChanApplyRecord.setDepartmentManagerId(bankAccountChanApplyRecordLast.getDepartmentManagerId());//部门经理Id
			bankAccountChanApplyRecord.setDepartmentManager(bankAccountChanApplyRecordLast.getDepartmentManager());//部门经理
			bankAccountChanApplyRecord.setApplyId(bankAccountChanApplyRecordLast.getApplyId());//开立申请Id	
*/		
			bankAccountChanApplyRecord.setZipCode(bankAccountChanApplyRecordLast.getZipCode());//邮政编码
			bankAccountChanApplyRecord.setContact(bankAccountChanApplyRecordLast.getContact());//联系方式
			bankAccountChanApplyRecord.setNote(bankAccountChanApplyRecordLast.getNote());//备注
			bankAccountChanApplyRecord.setBankContactsPhoneCalls(bankAccountChanApplyRecordLast.getBankContactsPhoneCalls());//银行联系人及电话
			bankAccountChanApplyRecord.setEmail(bankAccountChanApplyRecordLast.getEmail());//E-main

		}
		return bankAccountChanApplyRecord;
	}
	
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public BankAccountChanApplyRecord viewPage(String changeApplyId, UserProfile user) {
			BankAccountChanApplyRecord bankAccountChanApplyRecord =  bankAccountChanApplyRecordDao.get(changeApplyId);
			return bankAccountChanApplyRecord;
	}
	
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public BankAccountChanApplyRecord newBankAccountChanApplyRecord(UserProfile auserprf_U){
    	BankAccountChanApplyRecord lbankAccountChanApplyRecord_N = new BankAccountChanApplyRecord();
    	lbankAccountChanApplyRecord_N.setChangeRecordId(com.supporter.util.UUIDHex.newId());
    	lbankAccountChanApplyRecord_N.setCreatedById(auserprf_U.getPersonId());
    	lbankAccountChanApplyRecord_N.setCreatedBy(auserprf_U.getName()); 
    	String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    	lbankAccountChanApplyRecord_N.setCreatedDate(date);
    	lbankAccountChanApplyRecord_N.setThoseResponsibleId(auserprf_U.getPersonId());//责任人Id
    	lbankAccountChanApplyRecord_N.setThoseResponsible(auserprf_U.getName());//责任人姓名    	
//    	//单号
    	lbankAccountChanApplyRecord_N.setRecordNumber(getCurrentNo());
    	lbankAccountChanApplyRecord_N.setStatus(0l);
        return lbankAccountChanApplyRecord_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<BankAccountChanApplyRecord> getGrid(UserProfile user, JqGrid jqGrid, BankAccountChanApplyRecord bankAccountChanApplyRecord) {
		List<BankAccountChanApplyRecord> list1=this.bankAccountChanApplyRecordDao.findPage(user,jqGrid, bankAccountChanApplyRecord);//根据输入的查询条件查询列表
		return list1;	
		
	}
	
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public BankAccountChanApplyRecord saveOrUpdate(UserProfile user, BankAccountChanApplyRecord bankAccountChanApplyRecord, Map< String, Object > valueMap) {
		BankAccountChanApplyRecord ret = null;
		//账户性质
		String accountNature = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_ACCOUNT").getDisplay(bankAccountChanApplyRecord.getAccountNatureId());
		bankAccountChanApplyRecord.setAccountNature(accountNature);	
		//币别
		String accountCurrency = EIPService.getComCodeTableService().getCodeTable("CURRENCY_CATEGORY").getDisplay(bankAccountChanApplyRecord.getAccountCurrencyId());
		bankAccountChanApplyRecord.setAccountCurrency(accountCurrency);
		//账户单位性质
		String accountProperty = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_COMPANY").getDisplay(bankAccountChanApplyRecord.getAccountPropertyId());
		bankAccountChanApplyRecord.setAccountProperty(accountProperty);	
		
		//账户属性（活期、定期）
		String accountPro = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_PRO").getDisplay(bankAccountChanApplyRecord.getAccountProId());
		bankAccountChanApplyRecord.setAccountPro(accountPro);	
		
		//保存之前先判断一下如果是和项目无关就清除和项目相关信息
		if(bankAccountChanApplyRecord.getIsRelevanceprj().equals("femail")){//无关联项目
			bankAccountChanApplyRecord.setIsPrjSystemInOrOut("");
			bankAccountChanApplyRecord.setProjectName("");
			bankAccountChanApplyRecord.setProjectManager("");
			bankAccountChanApplyRecord.setProjectStatus("");
			bankAccountChanApplyRecord.setCondition("");
		}
		
		BankAccountOpenApplyEffec bankAccountOpenApplyEffec=this.bankAccountOpenApplyEffecDao.get(bankAccountChanApplyRecord.getEffectiveId());
		if(bankAccountOpenApplyEffec!=null){
			bankAccountChanApplyRecord.setIsCooperation(bankAccountOpenApplyEffec.getIsCooperation());//是否合作银行
			bankAccountChanApplyRecord.setOpenApplyRecordId(bankAccountOpenApplyEffec.getOpenApplyRecordId());//对应开立申请Id
		}
		
		if (bankAccountChanApplyRecord.getAdd()) {// 新建
						

			
			//保存主表
			this.bankAccountChanApplyRecordDao.save(bankAccountChanApplyRecord);			
			ret = bankAccountChanApplyRecord;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_BANKCHAN_RECORD_LOG_MESSAGE, bankAccountChanApplyRecord.getBankAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS1).info(
					user, LogConstant.ADD_BANKCHAN_RECORD_LOG_ACTION, logMessage,
					bankAccountChanApplyRecord, null);			
		} else {// 编辑
			bankAccountChanApplyRecord.setModifiedBy(user.getName());
			bankAccountChanApplyRecord.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountChanApplyRecord.setModifiedDate(date);
			//编辑之后保存主表
			this.bankAccountChanApplyRecordDao.update(bankAccountChanApplyRecord);

			ret = bankAccountChanApplyRecord;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_BANKCHAN_RECORD_LOG_MESSAGE, bankAccountChanApplyRecord.getBankAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS1).info(
					user, LogConstant.EDIT_BANKCHAN_RECORD_LOG_ACTION, logMessage,
					bankAccountChanApplyRecord, null);			
		}
		return ret;

	}

	
	 public  String getCurrentNo(){
		 		String no=	bankAccountChanApplyRecordDao.getRecordNumber();
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
	    			no="变更（备）"+no+"号("+year+")";
	    		}else{
	    			no="变更（备）001号("+year+")";
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
	public BankAccountChanApplyRecord commit(UserProfile user, BankAccountChanApplyRecord bankAccountChanApplyRecord, Map< String, Object > valueMap) {
		BankAccountChanApplyRecord ret = null;
		boolean isNew=bankAccountChanApplyRecord.getAdd();
		//账户性质
		String accountNature = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_ACCOUNT").getDisplay(bankAccountChanApplyRecord.getAccountNatureId());
		bankAccountChanApplyRecord.setAccountNature(accountNature);	
		//币别
		String accountCurrency = EIPService.getComCodeTableService().getCodeTable("CURRENCY_CATEGORY").getDisplay(bankAccountChanApplyRecord.getAccountCurrencyId());
		bankAccountChanApplyRecord.setAccountCurrency(accountCurrency);
		//账户单位性质
		String accountProperty = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_COMPANY").getDisplay(bankAccountChanApplyRecord.getAccountPropertyId());
		bankAccountChanApplyRecord.setAccountProperty(accountProperty);	
		//账户属性（活期、定期）
		String accountPro = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_PRO").getDisplay(bankAccountChanApplyRecord.getAccountProId());
		bankAccountChanApplyRecord.setAccountPro(accountPro);			
		//保存之前先判断一下如果是和项目无关就清除和项目相关信息
		if(bankAccountChanApplyRecord.getIsRelevanceprj().equals("femail")){//无关联项目
			bankAccountChanApplyRecord.setIsPrjSystemInOrOut("");
			bankAccountChanApplyRecord.setProjectName("");
			bankAccountChanApplyRecord.setProjectManager("");
			bankAccountChanApplyRecord.setProjectStatus("");
			bankAccountChanApplyRecord.setCondition("");
		}
		BankAccountOpenApplyEffec bankAccountOpenApplyEffec=this.bankAccountOpenApplyEffecDao.get(bankAccountChanApplyRecord.getEffectiveId());
		if(bankAccountOpenApplyEffec!=null){
			bankAccountChanApplyRecord.setIsCooperation(bankAccountOpenApplyEffec.getIsCooperation());//是否合作银行
			bankAccountChanApplyRecord.setOpenApplyRecordId(bankAccountOpenApplyEffec.getOpenApplyRecordId());//对应开立申请备案的Id
		}	
		
		//状态（备案生效）
		bankAccountChanApplyRecord.setStatus(1l);
		String chanReEffecDate = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
		bankAccountChanApplyRecord.setChanReEffecDate(chanReEffecDate);
		
		if(isNew){			 		
			//保存
			this.bankAccountChanApplyRecordDao.save(bankAccountChanApplyRecord);
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_BANKCHAN_RECORD_LOG_MESSAGE, bankAccountChanApplyRecord.getBankAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS1).info(
					user, LogConstant.ADD_BANKCHAN_RECORD_LOG_ACTION, logMessage,
					bankAccountChanApplyRecord, null);				
		} else {// 编辑		
			bankAccountChanApplyRecord.setModifiedBy(user.getName());
			bankAccountChanApplyRecord.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountChanApplyRecord.setModifiedDate(date);
			//编辑之后保存
			this.bankAccountChanApplyRecordDao.update(bankAccountChanApplyRecord);
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_BANKCHAN_RECORD_LOG_MESSAGE, bankAccountChanApplyRecord.getBankAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS1).info(
					user, LogConstant.EDIT_BANKCHAN_RECORD_LOG_ACTION, logMessage,
					bankAccountChanApplyRecord, null);				
			
		}
		//同时修改对应的变更表的状态（改为变更完成已备案）
		BankAccountChangeApply bankAccountChangeApply=this.bankAccountChangeApplyDao.get(bankAccountChanApplyRecord.getChangeApplyId());
		bankAccountChangeApply.setStatus(Long.valueOf(BankAccountChangeApply.COMPLETED_RE));//变更完成已备案（变更表状态）
		this.bankAccountChangeApplyDao.update(bankAccountChangeApply);
		//提交完成之后更改生效表
		if(bankAccountOpenApplyEffec!=null){			
			//修改生效表
			bankAccountOpenApplyEffec.setIsRelevanceprj(bankAccountChanApplyRecord.getIsRelevanceprj());//是否关联项目
			bankAccountOpenApplyEffec.setIsPrjSystemInOrOut(bankAccountChanApplyRecord.getIsPrjSystemInOrOut());//项目来源
			bankAccountOpenApplyEffec.setProjectName(bankAccountChanApplyRecord.getProjectName());//项目名称
			bankAccountOpenApplyEffec.setProjectManager(bankAccountChanApplyRecord.getProjectManager());//项目经理
			bankAccountOpenApplyEffec.setAccountPropertyId(bankAccountChanApplyRecord.getAccountPropertyId());//账户单位性质Id
			bankAccountOpenApplyEffec.setAccountProperty(bankAccountChanApplyRecord.getAccountProperty());//账户单位性质
			//bankAccountOpenApplyEffec.setOpeningBank(bankAccountChanApplyRecord.getOpeningBank());//开户银行
			//bankAccountOpenApplyEffec.setOpenTime(bankAccountChanApplyRecord.getOpenTime());//开户时间
			//bankAccountOpenApplyEffec.setThebankAccount(bankAccountChanApplyRecord.getThebankAccount());//银行账号
			//bankAccountOpenApplyEffec.setBankAccountName(bankAccountChanApplyRecord.getBankAccountName());//银行账户名称
			//bankAccountOpenApplyEffec.setAccountCurrency(bankAccountChanApplyRecord.getAccountCurrency());//账户币别
			//bankAccountOpenApplyEffec.setAccountNatureId(bankAccountChanApplyRecord.getAccountNatureId());//账户性质Id
			//bankAccountOpenApplyEffec.setAccountNature(bankAccountChanApplyRecord.getAccountNature());//账户性质
			bankAccountOpenApplyEffec.setBankAddress(bankAccountChanApplyRecord.getBankAddress());//银行地址
			bankAccountOpenApplyEffec.setAccountProId(bankAccountChanApplyRecord.getAccountProId());//账户属性Id(活期、定期)
			bankAccountOpenApplyEffec.setAccountPro(bankAccountChanApplyRecord.getAccountPro());//账户属性(活期、定期)
			bankAccountOpenApplyEffec.setAuthorizationPeriod(bankAccountChanApplyRecord.getAuthorizationPeriod());//预留印鉴
			bankAccountOpenApplyEffec.setAuthorizationPersonids(bankAccountChanApplyRecord.getAuthorizationPersonids());//授权人Id
			bankAccountOpenApplyEffec.setAuthorizationPersonnames(bankAccountChanApplyRecord.getAuthorizationPersonnames());//授权人名字
			bankAccountOpenApplyEffec.setAuthorization(bankAccountChanApplyRecord.getAuthorization());//权限
			bankAccountOpenApplyEffec.setAuthdateFrom(bankAccountChanApplyRecord.getAuthdateFrom());//授权开始日期
			bankAccountOpenApplyEffec.setAuthdateTo(bankAccountChanApplyRecord.getAuthdateTo());//授权结束日期
			bankAccountOpenApplyEffec.setAccountReason(bankAccountChanApplyRecord.getAccountReason());//开户原因
			bankAccountOpenApplyEffec.setProjectStatus(bankAccountChanApplyRecord.getProjectStatus());//项目情况
			bankAccountOpenApplyEffec.setCondition(bankAccountChanApplyRecord.getCondition());//项目所在国外汇管制及税收情况
			bankAccountOpenApplyEffec.setCredibilityLetter(bankAccountChanApplyRecord.getCredibilityLetter());//银行资信情况
			//bankAccountOpenApplyEffec.setApplyId(bankAccountChanApplyRecord.getApplyId());//开立申请Id	
			
			bankAccountOpenApplyEffec.setChangeTimes(bankAccountOpenApplyEffec.getChangeTimes()+1);//更改变更后的次数
			bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.COMPLETED_CHAN_Re+""));//变更完成已备案（生效表状态）	
			this.bankAccountOpenApplyEffecDao.update(bankAccountOpenApplyEffec);
			ret = bankAccountChanApplyRecord;		
		}
		return ret;
	}
	

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String changeApplyIds) {
		if (StringUtils.isNotBlank(changeApplyIds)) {
			for (String changeApplyId : changeApplyIds.split(",")) {
				BankAccountChanApplyRecord bankAccountChanApplyRecord=bankAccountChanApplyRecordDao.get(changeApplyId);
				if(bankAccountChanApplyRecord==null){
					continue;
				}
				//权限验证(判断是不是有编辑信息系统维护申请的权限)
				//AuthUtil.canExecute(user, BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, bankAccountChanApplyRecord.getBankAccountChangeApplyId(), bankAccountChanApplyRecord);	
				//删除主表
				this.bankAccountChanApplyRecordDao.delete(bankAccountChanApplyRecord);
	

			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_BANKCHAN_RECORD_LOG_MESSAGE, "删除的主键为"+changeApplyIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS1).info(
					user, LogConstant.DELETE_BANKCHAN_RECORD_LOG_ACTION, logMessage,
					null, null);

		}
	}
	
	
	/**
	 * 附件下载部分
	 * @param bankAccountChanApplyRecord
	 * @return
	 */
	public String getFiles(BankAccountChangeApply bankAccountChanApplyRecord){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAMAINTENAN", "OAMAINTENAN2", bankAccountChanApplyRecord.getChangeApplyId());
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
	public void update(BankAccountChanApplyRecord bankAccountChanApplyRecord) {	
		this.bankAccountChanApplyRecordDao.update(bankAccountChanApplyRecord);
	}
	
	



	
	
	
}
