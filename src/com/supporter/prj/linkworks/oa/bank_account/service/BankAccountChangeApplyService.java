package com.supporter.prj.linkworks.oa.bank_account.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountChangeApplyDao;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyEffecDao;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountRevokeApplyDao;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountChangeApply;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyEffec;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountRevokeApply;
import com.supporter.prj.linkworks.oa.bank_account.util.LogConstant;
import com.supporter.util.CommonUtil;


@Service
public class BankAccountChangeApplyService {
	@Autowired
	private BankAccountChangeApplyDao bankAccountChangeApplyDao;
	@Autowired
	private BankAccountOpenApplyEffecDao bankAccountOpenApplyEffecDao;
	@Autowired
	private BankAccountRevokeApplyDao bankAccountRevokeApplyDao;
	
	

	public BankAccountChangeApply get(String moduleId) {
		return bankAccountChangeApplyDao.get(moduleId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public BankAccountChangeApply initEditOrViewPage(JqGrid jqGrid,String changeApplyId,String effectiveId,UserProfile user) {
		if (StringUtils.isBlank(changeApplyId)) {// 新建
			BankAccountChangeApply bankAccountChangeApply = newBankAccountChangeApply(user);
			bankAccountChangeApply.setAdd(true);
			bankAccountChangeApply.setType("变更");
			BankAccountOpenApplyEffec bankAccountOpenApplyEffec =this.bankAccountOpenApplyEffecDao.get(effectiveId);
			if(bankAccountOpenApplyEffec!=null){
				bankAccountChangeApply.setEffectiveId(bankAccountOpenApplyEffec.getEffectiveId());//生效表Id
				bankAccountChangeApply.setOpenApplyRecordId(bankAccountOpenApplyEffec.getOpenApplyRecordId());//生效表对应的开立申请表的Id
				bankAccountChangeApply.setBankNumber(bankAccountOpenApplyEffec.getThebankAccount());//银行账号
				bankAccountChangeApply.setOpenBank(bankAccountOpenApplyEffec.getOpeningBank());//开户银行
				bankAccountChangeApply.setProjectManager(bankAccountOpenApplyEffec.getProjectManager());//项目经理
				bankAccountChangeApply.setBranchManagerId(bankAccountOpenApplyEffec.getControlledCompanyId());//部门/子公司/分公司经理
				bankAccountChangeApply.setBranchManager(bankAccountOpenApplyEffec.getControlledCompany());//部门/子公司/分公司经理
				bankAccountChangeApply.setBankAccountName(bankAccountOpenApplyEffec.getBankAccountName());//银行账户名称
				bankAccountChangeApply.setOpenTime(bankAccountOpenApplyEffec.getOpenTime());//开户时间
				bankAccountChangeApply.setAuthorization(bankAccountOpenApplyEffec.getAuthorization());//授权
				bankAccountChangeApply.setProjectName(bankAccountOpenApplyEffec.getProjectName());//项目名称
				bankAccountChangeApply.setNationalityId(bankAccountOpenApplyEffec.getNationalityId());//国别Id
				bankAccountChangeApply.setAccountPropertyId(bankAccountOpenApplyEffec.getAccountPropertyId());//账户单位性质Id
				bankAccountChangeApply.setAccountNature(bankAccountOpenApplyEffec.getAccountNature());//账户性质(流程标题用到)
			}
			
			return bankAccountChangeApply;
		} else {// 编辑
			//获得主表
			BankAccountChangeApply bankAccountChangeApply =  bankAccountChangeApplyDao.get(changeApplyId);
						
			//查看时显示部门名称
	    	if(user.getDept()==null){
	    		bankAccountChangeApply.setAccountOpener("无所属部门");
		   	}else{
		   		bankAccountChangeApply.setAccountOpener(user.getDept().getName());
		   	}
			bankAccountChangeApply.setAdd(false);		
			return bankAccountChangeApply;
		}
	}
		
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public BankAccountChangeApply newBankAccountChangeApply(UserProfile auserprf_U){
    	BankAccountChangeApply lbankAccountChangeApply_N = new BankAccountChangeApply();
    	lbankAccountChangeApply_N.setChangeApplyId(com.supporter.util.UUIDHex.newId());
    	lbankAccountChangeApply_N.setCreatedById(auserprf_U.getPersonId());
    	lbankAccountChangeApply_N.setCreatedBy(auserprf_U.getName()); 
    	String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    	lbankAccountChangeApply_N.setCreatedDate(date);
    	lbankAccountChangeApply_N.setAccountOpenerId(auserprf_U.getDeptId());
    	if(auserprf_U.getDept()==null){
    		lbankAccountChangeApply_N.setAccountOpener("无所属部门");
	   	}else{
	   		lbankAccountChangeApply_N.setAccountOpener(auserprf_U.getDept().getName());
	   	}
//    	//单号
    	lbankAccountChangeApply_N.setChangeNumber(getCurrentNo());
    	lbankAccountChangeApply_N.setStatus(Long.valueOf(BankAccountChangeApply.DRAFT+""));
        return lbankAccountChangeApply_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<BankAccountChangeApply> getGrid(UserProfile user, JqGrid jqGrid, BankAccountChangeApply bankAccountChangeApply) {
		List<BankAccountChangeApply> list1=this.bankAccountChangeApplyDao.findPage(user,jqGrid, bankAccountChangeApply);//根据输入的查询条件查询列表
		BankAccountRevokeApply bankAccountRevokeApply=this.bankAccountRevokeApplyDao.getRevokeByEffectiveId(bankAccountChangeApply.getEffectiveId());//根据输入的查询条件查询列表

		if(bankAccountRevokeApply!=null){
			BankAccountChangeApply bankAccountChangeApplyRevo=new BankAccountChangeApply();
			bankAccountChangeApplyRevo.setChangeApplyId(com.supporter.util.UUIDHex.newId());
			bankAccountChangeApplyRevo.setRevokeId(bankAccountRevokeApply.getRevokeId());//撤销Id
			bankAccountChangeApplyRevo.setEffectiveId(bankAccountRevokeApply.getEffectiveId());
			bankAccountChangeApplyRevo.setOpenBank(bankAccountRevokeApply.getOpenBank());//开户银行
			bankAccountChangeApplyRevo.setBankNumber(bankAccountRevokeApply.getBankNumber());//银行账号
			bankAccountChangeApplyRevo.setOpenTime(bankAccountRevokeApply.getOpenTime());//开户时间
			bankAccountChangeApplyRevo.setAuthorization(bankAccountRevokeApply.getAuthorization());//授权
			bankAccountChangeApplyRevo.setType(bankAccountRevokeApply.getType());//类型
			bankAccountChangeApplyRevo.setStatus(bankAccountRevokeApply.getStatus());//状态	
			bankAccountChangeApplyRevo.setProcId(bankAccountRevokeApply.getProcId());//流程Id	
			bankAccountChangeApplyRevo.setAccountOpener(bankAccountRevokeApply.getAccountOpener());//部门
			bankAccountChangeApplyRevo.setAccountNature(bankAccountRevokeApply.getAccountNature());//账户性质
			list1.add(bankAccountChangeApplyRevo);
		}

		
		return list1;	
		
	}
	/**
	 * 根据生效表的id获取对应的状态为草稿的变更记录的条数
	 * @param effectiveId
	 * @return
	 */
	public int getChangeApplyListByEffectiveId(String effectiveId) {	
		List<BankAccountChangeApply> list=this.bankAccountChangeApplyDao.getChangeApplyListByEffectiveId(effectiveId);
		if(list!=null&&list.size()>0){
			return list.size();
		}else{
			return 0;
		}
	}
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public BankAccountChangeApply saveOrUpdate(UserProfile user, BankAccountChangeApply bankAccountChangeApply, Map< String, Object > valueMap) {
		BankAccountChangeApply ret = null;
		if (bankAccountChangeApply.getAdd()) {// 新建

			//保存主表
			this.bankAccountChangeApplyDao.save(bankAccountChangeApply);
			
			ret = bankAccountChangeApply;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_BANKCHAN_LOG_MESSAGE, bankAccountChangeApply.getBankAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS3).info(
					user, LogConstant.ADD_BANKCHAN_LOG_ACTION, logMessage,
					bankAccountChangeApply, null);
		} else {// 编辑
			
			//权限验证(判断是不是有编辑信息系统维护申请的权限)
			//AuthUtil.canExecute(user, BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, bankAccountChangeApply.getBankAccountChangeApplyId(), bankAccountChangeApply);									
			bankAccountChangeApply.setModifiedBy(user.getName());
			bankAccountChangeApply.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountChangeApply.setModifiedDate(date);
			//编辑之后保存主表
			this.bankAccountChangeApplyDao.update(bankAccountChangeApply);
			ret = bankAccountChangeApply;			
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_BANKCHAN_LOG_MESSAGE, bankAccountChangeApply.getBankAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS3).info(
					user, LogConstant.EDIT_BANKCHAN_LOG_ACTION, logMessage,
					bankAccountChangeApply, null);

		}
		return ret;

	}

	
	 public  String getCurrentNo(){
		 		String no=	bankAccountChangeApplyDao.getChangeNumber();
			    SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
				String year=sdf.format(new Date());
	    		if(year.equals(no.substring(10,14))){
	    			//2017-002
	    			//变更（申）001号(2018);
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
	    			no="变更（申）"+no+"号("+year+")";
	    		}else{
	    			no="变更（申）001号("+year+")";
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
	public BankAccountChangeApply commit(UserProfile user, BankAccountChangeApply bankAccountChangeApply, Map< String, Object > valueMap) {
		BankAccountChangeApply ret = null;
		boolean isNew=bankAccountChangeApply.getAdd();		
		if(isNew){
			//保存主表
			this.bankAccountChangeApplyDao.save(bankAccountChangeApply);
			//提交完成之后更改生效表
			BankAccountOpenApplyEffec bankAccountOpenApplyEffec=this.bankAccountOpenApplyEffecDao.get(bankAccountChangeApply.getEffectiveId());
			if(bankAccountOpenApplyEffec!=null){
				//bankAccountOpenApplyEffec.setChangeTimes(bankAccountOpenApplyEffec.getChangeTimes()+1);
				bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.PROCESSING_CHAN+""));//变更审批中
			}
			this.bankAccountOpenApplyEffecDao.update(bankAccountOpenApplyEffec);
			ret = bankAccountChangeApply;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{AddApplication : " + apply + "}", null, null);
		} else {// 编辑
			
			bankAccountChangeApply.setModifiedBy(user.getName());
			bankAccountChangeApply.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountChangeApply.setModifiedDate(date);
			//编辑之后保存主表
			this.bankAccountChangeApplyDao.update(bankAccountChangeApply);
			//提交完成之后更改生效表
			BankAccountOpenApplyEffec bankAccountOpenApplyEffec=this.bankAccountOpenApplyEffecDao.get(bankAccountChangeApply.getEffectiveId());
			if(bankAccountOpenApplyEffec!=null){
				//bankAccountOpenApplyEffec.setChangeTimes(bankAccountOpenApplyEffec.getChangeTimes()+1);
				bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.PROCESSING_CHAN+""));//变更审批中
			}
			this.bankAccountOpenApplyEffecDao.update(bankAccountOpenApplyEffec);
			ret = bankAccountChangeApply;
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
	public void delete(UserProfile user, String changeApplyIds) {
		if (StringUtils.isNotBlank(changeApplyIds)) {
			for (String changeApplyId : changeApplyIds.split(",")) {
				BankAccountChangeApply bankAccountChangeApply=bankAccountChangeApplyDao.get(changeApplyId);
				if(bankAccountChangeApply==null){
					continue;
				}
				//权限验证(判断是不是有编辑信息系统维护申请的权限)
				//AuthUtil.canExecute(user, BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, bankAccountChangeApply.getBankAccountChangeApplyId(), bankAccountChangeApply);	
				//删除主表
				this.bankAccountChangeApplyDao.delete(bankAccountChangeApply);
				

			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_BANKCHAN_LOG_MESSAGE, "删除的主键为"+changeApplyIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS3).info(
					user, LogConstant.ADD_BANKCHAN_LOG_ACTION, logMessage,
					null, null);

		}
	}
	
	/**
	 * 保存报CMEC审批结果
	 * @param changeApplyId
	 * @param cmecResult
	 * @return
	 */
	public BankAccountChangeApply saveCMECResult(String changeApplyId, int cmecResult) {
		if (StringUtils.isNotBlank(changeApplyId)) {
			BankAccountChangeApply entity = this.get(changeApplyId);
			entity.setCmecResult(cmecResult);
			this.bankAccountChangeApplyDao.update(entity);
			return entity;
		}
		return null;
	}
	
	
	/**
	 * 修改（流程处理类调用的方法，用于修改状态和保存流程id）
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public void update(BankAccountChangeApply bankAccountChangeApply) {	
		this.bankAccountChangeApplyDao.update(bankAccountChangeApply);
	}
}
