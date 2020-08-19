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
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyDao;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyEffecDao;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApply;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyEffec;
import com.supporter.prj.linkworks.oa.bank_account.util.LogConstant;
import com.supporter.util.CommonUtil;


@Service
public class BankAccountOpenApplyService {
	@Autowired
	private BankAccountOpenApplyDao bankAccountOpenApplyDao;
	@Autowired
	private BankAccountOpenApplyEffecDao bankAccountOpenApplyEffecDao;



	public BankAccountOpenApply get(String moduleId) {
		return bankAccountOpenApplyDao.get(moduleId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public BankAccountOpenApply initEditOrViewPage(JqGrid jqGrid,String applyId, UserProfile user) {
		if (StringUtils.isBlank(applyId)) {// 新建
			BankAccountOpenApply bankAccountOpenApply = newBankAccountOpenApply(user);
			bankAccountOpenApply.setAdd(true);
			return bankAccountOpenApply;
		} else {// 编辑
			//获得主表
			BankAccountOpenApply bankAccountOpenApply =  bankAccountOpenApplyDao.get(applyId);
					
//			//查看时显示部门名称
//	    	if(user.getDept()==null){
//	    		bankAccountOpenApply.setAccountOpener("无所属部门");
//		   	}else{
//		   		bankAccountOpenApply.setAccountOpener(user.getDept().getName());
//		   	}
		    			
			bankAccountOpenApply.setAdd(false);		
			return bankAccountOpenApply;
		}
	}
	
	
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public BankAccountOpenApply viewPage(String applyId, UserProfile user) {
			BankAccountOpenApply bankAccountOpenApply =  bankAccountOpenApplyDao.get(applyId);
			return bankAccountOpenApply;
	}
	
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public BankAccountOpenApply newBankAccountOpenApply(UserProfile auserprf_U){
    	BankAccountOpenApply lbankAccountOpenApply_N = new BankAccountOpenApply();
    	lbankAccountOpenApply_N.setApplyId(com.supporter.util.UUIDHex.newId());
    	lbankAccountOpenApply_N.setCreatedById(auserprf_U.getPersonId());
    	lbankAccountOpenApply_N.setCreatedBy(auserprf_U.getName()); 
    	String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    	lbankAccountOpenApply_N.setCreatedDate(date);
    	lbankAccountOpenApply_N.setAccountOpenerId(auserprf_U.getDeptId());
    	if(auserprf_U.getDept()==null){
    		lbankAccountOpenApply_N.setAccountOpener("无所属部门");
	   	}else{
	   		lbankAccountOpenApply_N.setAccountOpener(auserprf_U.getDept().getName());
			//1获取部门正职这个角色 
			Role role=EIPService.getRoleService().getRole("DEPTLEADER");
			//2获取创建人所在的部门
			Dept dept=auserprf_U.getDept();
			Person person=null;
			List<Person> persons=EIPService.getRoleService().getPersonsForDept(role, dept);
			if(persons!=null&&persons.size()>0){
				person=persons.get(0);
			}
			if(person!=null){
				lbankAccountOpenApply_N.setControlledCompanyId(person.getPersonId());
				lbankAccountOpenApply_N.setControlledCompany(person.getName());
	   		}
	   		

	   	}
//    	//单号
    	lbankAccountOpenApply_N.setApplicationNumber(getCurrentNo());
    	lbankAccountOpenApply_N.setStatus(Long.valueOf(BankAccountOpenApply.DRAFT+""));
        return lbankAccountOpenApply_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<BankAccountOpenApply> getGrid(UserProfile user, JqGrid jqGrid, BankAccountOpenApply bankAccountOpenApply) {
		List<BankAccountOpenApply> list1=this.bankAccountOpenApplyDao.findPage(user,jqGrid, bankAccountOpenApply);//根据输入的查询条件查询列表
		return list1;	
		
	}
	
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public BankAccountOpenApply saveOrUpdate(UserProfile user, BankAccountOpenApply bankAccountOpenApply, Map< String, Object > valueMap) {
		BankAccountOpenApply ret = null;
		//根据码表id获取码表对应项
		//国别
		String nationality = EIPService.getComCodeTableService().getCodeTable("ALL_COUNTRIES").getDisplay(bankAccountOpenApply.getNationalityId());
		bankAccountOpenApply.setNationality(nationality);
		//账户单位性质
		String accountProperty = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_COMPANY").getDisplay(bankAccountOpenApply.getAccountPropertyId());
		bankAccountOpenApply.setAccountProperty(accountProperty);				
		//账户性质
		String accountNature = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_ACCOUNT").getDisplay(bankAccountOpenApply.getAccountNatureId());
		bankAccountOpenApply.setAccountNature(accountNature);	
		//币别
		String accountCurrency = EIPService.getComCodeTableService().getCodeTable("CURRENCY_CATEGORY").getDisplay(bankAccountOpenApply.getAccountCurrencyId());
		bankAccountOpenApply.setAccountCurrency(accountCurrency);	
		//开户银行一级名称
		String openBankFirstName = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_FRIST_N").getDisplay(bankAccountOpenApply.getOpenBankFirstNameId());
		bankAccountOpenApply.setOpenBankFirstName(openBankFirstName);	
		//账户属性
		String accountPro = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_PRO").getDisplay(bankAccountOpenApply.getAccountProId());
		bankAccountOpenApply.setAccountPro(accountPro);			
		
		if (bankAccountOpenApply.getAdd()) {// 新建
					
			//保存主表
			this.bankAccountOpenApplyDao.save(bankAccountOpenApply);			
			ret = bankAccountOpenApply;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_BANKOPEN_LOG_MESSAGE, bankAccountOpenApply.getOpeningBank());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS1).info(
					user, LogConstant.ADD_BANKOPEN_LOG_ACTION, logMessage,
					bankAccountOpenApply, null);
		} else {// 编辑
			
			//权限验证(判断是不是有编辑信息系统维护申请的权限)
			//AuthUtil.canExecute(user, BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, bankAccountOpenApply.getBankAccountOpenApplyId(), bankAccountOpenApply);									
			bankAccountOpenApply.setModifiedBy(user.getName());
			bankAccountOpenApply.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountOpenApply.setModifiedDate(date);
			//编辑之后保存主表
			this.bankAccountOpenApplyDao.update(bankAccountOpenApply);

			ret = bankAccountOpenApply;			
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_BANKOPEN_LOG_MESSAGE, bankAccountOpenApply.getOpeningBank());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS1).info(
					user, LogConstant.EDIT_BANKOPEN_LOG_ACTION, logMessage,
					bankAccountOpenApply, null);

		}
		return ret;

	}

	
	 public  String getCurrentNo(){
		 		String no=	bankAccountOpenApplyDao.getApplicationNumber();
			    SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
				String year=sdf.format(new Date());
	    		if(year.equals(no.substring(10,14))){
	    			//2017-002
	    			//开立（申）001号(2018);
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
	    			no="开立（申）"+no+"号("+year+")";
	    		}else{
	    			no="开立（申）001号("+year+")";
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
	public BankAccountOpenApply commit(UserProfile user, BankAccountOpenApply bankAccountOpenApply, Map< String, Object > valueMap) {
		BankAccountOpenApply ret = null;
		boolean isNew=bankAccountOpenApply.getAdd();		
		BankAccountOpenApplyEffec bankAccountOpenApplyEffec= new BankAccountOpenApplyEffec();
		//根据码表id获取码表对应项
		//国别
		String nationality = EIPService.getComCodeTableService().getCodeTable("ALL_COUNTRIES").getDisplay(bankAccountOpenApply.getNationalityId());
		bankAccountOpenApply.setNationality(nationality);
		//账户单位性质
		String accountProperty = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_COMPANY").getDisplay(bankAccountOpenApply.getAccountPropertyId());
		bankAccountOpenApply.setAccountProperty(accountProperty);				
		//账户性质
		String accountNature = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_ACCOUNT").getDisplay(bankAccountOpenApply.getAccountNatureId());
		bankAccountOpenApply.setAccountNature(accountNature);	
		//币别
		String accountCurrency = EIPService.getComCodeTableService().getCodeTable("CURRENCY_CATEGORY").getDisplay(bankAccountOpenApply.getAccountCurrencyId());
		bankAccountOpenApply.setAccountCurrency(accountCurrency);	
		//开户银行一级名称
		String openBankFirstName = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_FRIST_N").getDisplay(bankAccountOpenApply.getOpenBankFirstNameId());
		bankAccountOpenApply.setOpenBankFirstName(openBankFirstName);	
		//账户属性
		String accountPro = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_PRO").getDisplay(bankAccountOpenApply.getAccountProId());
		bankAccountOpenApply.setAccountPro(accountPro);				
		if(isNew){
		 
			//状态（审批完成未备案）
			bankAccountOpenApply.setStatus(Long.valueOf(BankAccountOpenApply.COMPLETED_NORE+""));
			//审批完成日期
			String targetDate = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountOpenApply.setTargetDate(targetDate);
			BeanUtils.copyProperties(bankAccountOpenApply,bankAccountOpenApplyEffec);		
			//保存主表
			this.bankAccountOpenApplyDao.save(bankAccountOpenApply);
//			bankAccountOpenApplyEffec.setEffectiveId(com.supporter.util.UUIDHex.newId());
//			bankAccountOpenApplyEffec.setChangeTimes(0l);
//			bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.COMPLETED_NORE+""));//开立完成未备案
//			this.bankAccountOpenApplyEffecDao.save(bankAccountOpenApplyEffec);
			
			ret = bankAccountOpenApply;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{AddApplication : " + apply + "}", null, null);
		} else {// 编辑
			
			bankAccountOpenApply.setModifiedBy(user.getName());
			bankAccountOpenApply.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountOpenApply.setModifiedDate(date);
			//状态（审批完成未备案）
			bankAccountOpenApply.setStatus(Long.valueOf(BankAccountOpenApply.COMPLETED_NORE+""));
			//审批完成日期
			String targetDate = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountOpenApply.setTargetDate(targetDate);
			BeanUtils.copyProperties(bankAccountOpenApply,bankAccountOpenApplyEffec);
			//编辑之后保存主表
			this.bankAccountOpenApplyDao.update(bankAccountOpenApply);
//			bankAccountOpenApplyEffec.setEffectiveId(com.supporter.util.UUIDHex.newId());
//			bankAccountOpenApplyEffec.setChangeTimes(0l);
//			bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.COMPLETED_NORE+""));//开立完成未备案
//			this.bankAccountOpenApplyEffecDao.save(bankAccountOpenApplyEffec);
			ret = bankAccountOpenApply;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + apply + "}", null, null);
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
				BankAccountOpenApply bankAccountOpenApply=bankAccountOpenApplyDao.get(applyId);
				if(bankAccountOpenApply==null){
					continue;
				}
				//权限验证(判断是不是有编辑信息系统维护申请的权限)
				//AuthUtil.canExecute(user, BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, bankAccountOpenApply.getBankAccountOpenApplyId(), bankAccountOpenApply);	
				//删除主表
				this.bankAccountOpenApplyDao.delete(bankAccountOpenApply);
				

			}			
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_BANKOPEN_LOG_MESSAGE, "删除的主键id为："+applyIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS1).info(
					user, LogConstant.DELETE_BANKOPEN_LOG_ACTION, logMessage,
					null, null);

		}
	}
	
	
	/**
	 * 附件下载部分
	 * @param bankAccountOpenApply
	 * @return
	 */
	public String getFiles(BankAccountOpenApply bankAccountOpenApply){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAMAINTENAN", "OAMAINTENAN2", bankAccountOpenApply.getApplyId());
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
	public void update(BankAccountOpenApply bankAccountOpenApply) {	
		this.bankAccountOpenApplyDao.update(bankAccountOpenApply);
	}
	/**
	 * 验证在当前申请单位、当前银行、当前币别是否已经存在该账户(账号)
	 * @param bankAccountOpenApply
	 * @return
	 */
	public String checkAccountByDeptBankCurrency(BankAccountOpenApply bankAccountOpenApply){
		String check="";
		String nationalityId=bankAccountOpenApply.getNationalityId();//国别
		String accountNatureId=bankAccountOpenApply.getAccountNatureId();//账户性质
		BankAccountOpenApplyEffec bankAccountOpenApplyEffec=null;
		if(nationalityId!=null){
			if(nationalityId.equals("BANK-C-001")){//中国
				if(!accountNatureId.equals("BASIC_ACCOUNT")&&!accountNatureId.equals("BAIL_ACCOUNT")){//不是保证金账户也不是基本账户
					bankAccountOpenApplyEffec = this.bankAccountOpenApplyEffecDao.getAccountByDeptBankCurrency(bankAccountOpenApply);					
				}
			}else{//外国
				bankAccountOpenApplyEffec = this.bankAccountOpenApplyEffecDao.getAccountByDeptBankCurrency(bankAccountOpenApply);
			}
		}
		if(bankAccountOpenApplyEffec!=null){
			check = "{\"accountOpener\":\""+bankAccountOpenApplyEffec.getAccountOpener()+"\"," +
					 "\"accountCurrency\":\""+bankAccountOpenApplyEffec.getAccountCurrency()+"\"," +
					 "\"openingBank\":\""+bankAccountOpenApplyEffec.getOpeningBank()+"\"}";
		}
		return check;
	}
	
	/**
	 * 保存报CMEC审批结果
	 * @param applyId
	 * @param cmecResult
	 * @return
	 */
	public BankAccountOpenApply saveCMECResult(String applyId, int cmecResult) {
		if (StringUtils.isNotBlank(applyId)) {
			BankAccountOpenApply entity = this.get(applyId);
			entity.setCmecResult(cmecResult);
			this.bankAccountOpenApplyDao.update(entity);
			return entity;
		}
		return null;
	}	

/*	*//**
	 * 获取世界上的国家
	 * @return
	 *//*
	public Map<String, String> getAllCountryList(){	
		return this.bankAccountOpenApplyDao.getAllCountryList();
	}*/
	
	
	
}
