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
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountRevokeApplyDao;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankAccountOpenApplyEffecDao;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountRevokeApply;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankAccountOpenApplyEffec;
import com.supporter.prj.linkworks.oa.bank_account.util.LogConstant;
import com.supporter.util.CommonUtil;


@Service
public class BankAccountRevokeApplyService {
	@Autowired
	private BankAccountRevokeApplyDao bankAccountRevokeApplyDao;
	@Autowired
	private BankAccountOpenApplyEffecDao bankAccountOpenApplyEffecDao;

	public BankAccountRevokeApply get(String moduleId) {
		return bankAccountRevokeApplyDao.get(moduleId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public BankAccountRevokeApply initEditOrViewPage(JqGrid jqGrid,String revokeId,String effectiveId,UserProfile user) {
		
		if(StringUtils.isBlank(revokeId)){
			if (!StringUtils.isBlank(effectiveId)) {
				BankAccountRevokeApply bankAccountRevokeApply=this.bankAccountRevokeApplyDao.getRevokeByEffectiveId(effectiveId);			
				if(bankAccountRevokeApply!=null){//存在撤销记录
					return bankAccountRevokeApply;
				}else{//第一次撤销
					BankAccountRevokeApply bankAccountRevokeApplyNew = newBankAccountRevokeApply(user);
					BankAccountOpenApplyEffec bankAccountOpenApplyEffec =this.bankAccountOpenApplyEffecDao.get(effectiveId);
					if(bankAccountOpenApplyEffec!=null){
						bankAccountRevokeApplyNew.setEffectiveId(bankAccountOpenApplyEffec.getEffectiveId());
						bankAccountRevokeApplyNew.setBankNumber(bankAccountOpenApplyEffec.getThebankAccount());//银行账号
						bankAccountRevokeApplyNew.setOpenBank(bankAccountOpenApplyEffec.getOpeningBank());//开户银行
						bankAccountRevokeApplyNew.setProjectManager(bankAccountOpenApplyEffec.getProjectManager());//项目经理
						bankAccountRevokeApplyNew.setBranchManagerId(bankAccountOpenApplyEffec.getControlledCompanyId());//部门/子公司/分公司经理Id
						bankAccountRevokeApplyNew.setBranchManager(bankAccountOpenApplyEffec.getControlledCompany());//部门/子公司/分公司经理
						bankAccountRevokeApplyNew.setBankAccountName(bankAccountOpenApplyEffec.getBankAccountName());//银行账户名称
						bankAccountRevokeApplyNew.setProjectName(bankAccountOpenApplyEffec.getProjectName());//项目名称
						bankAccountRevokeApplyNew.setNationalityId(bankAccountOpenApplyEffec.getNationalityId());//国别Id
						bankAccountRevokeApplyNew.setAccountPropertyId(bankAccountOpenApplyEffec.getAccountPropertyId());//账户单位性质Id
						bankAccountRevokeApplyNew.setOpenTime(bankAccountOpenApplyEffec.getOpenTime());//开户时间
						bankAccountRevokeApplyNew.setAuthorization(bankAccountOpenApplyEffec.getAuthorization());//授权
						bankAccountRevokeApplyNew.setAccountNature(bankAccountOpenApplyEffec.getAccountNature());//账户性质(流程标题用到)						
					}
					bankAccountRevokeApplyNew.setAdd(true);
					bankAccountRevokeApplyNew.setType("撤销");
					return bankAccountRevokeApplyNew;
				}	
			}else{
				return null;
			}
		}else{
			BankAccountRevokeApply bankAccountRevokeApply=this.bankAccountRevokeApplyDao.get(revokeId);
			return bankAccountRevokeApply;
		}
	}
	
	
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public BankAccountRevokeApply viewPage(String revokeId, UserProfile user) {
			BankAccountRevokeApply bankAccountRevokeApply =  bankAccountRevokeApplyDao.get(revokeId);
			return bankAccountRevokeApply;
	}
	
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public BankAccountRevokeApply newBankAccountRevokeApply(UserProfile auserprf_U){
    	BankAccountRevokeApply lbankAccountRevokeApply_N = new BankAccountRevokeApply();
    	lbankAccountRevokeApply_N.setRevokeId(com.supporter.util.UUIDHex.newId());
    	lbankAccountRevokeApply_N.setCreatedById(auserprf_U.getPersonId());
    	lbankAccountRevokeApply_N.setCreatedBy(auserprf_U.getName()); 
    	String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    	lbankAccountRevokeApply_N.setCreatedDate(date);
    	lbankAccountRevokeApply_N.setAccountOpenerId(auserprf_U.getDeptId());
    	if(auserprf_U.getDept()==null){
    		lbankAccountRevokeApply_N.setAccountOpener("无所属部门");
	   	}else{
	   		lbankAccountRevokeApply_N.setAccountOpener(auserprf_U.getDept().getName());
	   	}
//    	//单号
    	lbankAccountRevokeApply_N.setRevokeNumber(getCurrentNo());
    	lbankAccountRevokeApply_N.setStatus(Long.valueOf(BankAccountRevokeApply.DRAFT+""));
        return lbankAccountRevokeApply_N;
    }
	
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public BankAccountRevokeApply saveOrUpdate(UserProfile user, BankAccountRevokeApply bankAccountRevokeApply, Map< String, Object > valueMap) {
		BankAccountRevokeApply ret = null;
		if (bankAccountRevokeApply.getAdd()) {// 新建

			//保存主表
			this.bankAccountRevokeApplyDao.save(bankAccountRevokeApply);
			
			ret = bankAccountRevokeApply;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_BANKREVOKE_LOG_MESSAGE, bankAccountRevokeApply.getBankAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS5).info(
					user, LogConstant.ADD_BANKREVOKE_LOG_ACTION, logMessage,
					bankAccountRevokeApply, null);
		} else {// 编辑
			
			//权限验证(判断是不是有编辑信息系统维护申请的权限)
			//AuthUtil.canExecute(user, BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, bankAccountRevokeApply.getBankAccountRevokeApplyId(), bankAccountRevokeApply);									
			bankAccountRevokeApply.setModifiedBy(user.getName());
			bankAccountRevokeApply.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountRevokeApply.setModifiedDate(date);
			//编辑之后保存主表
			this.bankAccountRevokeApplyDao.update(bankAccountRevokeApply);
			ret = bankAccountRevokeApply;			
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_BANKREVOKE_LOG_MESSAGE, bankAccountRevokeApply.getBankAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS5).info(
					user, LogConstant.ADD_BANKREVOKE_LOG_ACTION, logMessage,
					bankAccountRevokeApply, null);

		}
		return ret;

	}

	
	 public  String getCurrentNo(){
		 		String no=	bankAccountRevokeApplyDao.getChangeNumber();
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
	    			no="撤销（申）"+no+"号("+year+")";
	    		}else{
	    			no="撤销（申）001号("+year+")";
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
	public BankAccountRevokeApply commit(UserProfile user, BankAccountRevokeApply bankAccountRevokeApply, Map< String, Object > valueMap) {
		BankAccountRevokeApply ret = null;
		boolean isNew=bankAccountRevokeApply.getAdd();		
		if(isNew){
			//保存主表
			this.bankAccountRevokeApplyDao.save(bankAccountRevokeApply);
			//撤销审批完成之后修改生效表中的状态
			BankAccountOpenApplyEffec bankAccountOpenApplyEffec=this.bankAccountOpenApplyEffecDao.get(bankAccountRevokeApply.getEffectiveId());
			if(bankAccountOpenApplyEffec!=null){
				bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.PROCESSING_REVOKE+""));//撤销审批中
			}
			this.bankAccountOpenApplyEffecDao.update(bankAccountOpenApplyEffec);
			ret = bankAccountRevokeApply;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{AddApplication : " + apply + "}", null, null);
		} else {// 编辑
			
			bankAccountRevokeApply.setModifiedBy(user.getName());
			bankAccountRevokeApply.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankAccountRevokeApply.setModifiedDate(date);
			//编辑之后保存主表
			this.bankAccountRevokeApplyDao.update(bankAccountRevokeApply);
			//撤销审批完成之后修改生效表中的状态
			BankAccountOpenApplyEffec bankAccountOpenApplyEffec=this.bankAccountOpenApplyEffecDao.get(bankAccountRevokeApply.getEffectiveId());
			if(bankAccountOpenApplyEffec!=null){
				bankAccountOpenApplyEffec.setStatus(Long.valueOf(BankAccountOpenApplyEffec.PROCESSING_REVOKE+""));//撤销审批中
			}
			this.bankAccountOpenApplyEffecDao.update(bankAccountOpenApplyEffec);
			ret = bankAccountRevokeApply;
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
	public void delete(UserProfile user, String revokeIds) {
		if (StringUtils.isNotBlank(revokeIds)) {
			for (String revokeId : revokeIds.split(",")) {
				BankAccountRevokeApply bankAccountRevokeApply=bankAccountRevokeApplyDao.get(revokeId);
				if(bankAccountRevokeApply==null){
					continue;
				}
				//权限验证(判断是不是有编辑信息系统维护申请的权限)
				//AuthUtil.canExecute(user, BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, bankAccountRevokeApply.getBankAccountRevokeApplyId(), bankAccountRevokeApply);	
				//删除主表
				this.bankAccountRevokeApplyDao.delete(bankAccountRevokeApply);

			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_BANKREVOKE_LOG_MESSAGE, "删除的主键为"+revokeIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS5).info(
					user, LogConstant.DELETE_BANKREVOKE_LOG_ACTION, logMessage,
					null, null);

		}
	}
	
	
	/**
	 * 修改（流程处理类调用的方法，用于修改状态和保存流程id）
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public void update(BankAccountRevokeApply bankAccountRevokeApply) {	
		this.bankAccountRevokeApplyDao.update(bankAccountRevokeApply);
	}
	
	/**
	 * 根据生效表的id获取对应的状态为草稿的撤销记录的条数
	 * @param effectiveId
	 * @return
	 */
	public int getRevokeApplyListByEffectiveId(String effectiveId) {	
		List<BankAccountRevokeApply> list=this.bankAccountRevokeApplyDao.getRevokeApplyListByEffectiveId(effectiveId);
		if(list!=null&&list.size()>0){
			return list.size();
		}else{
			return 0;
		}
	}	
	
	/**
	 * 保存报CMEC审批结果
	 * @param revokeId
	 * @param cmecResult
	 * @return
	 */
	public BankAccountRevokeApply saveCMECResult(String revokeId, int cmecResult) {
		if (StringUtils.isNotBlank(revokeId)) {
			BankAccountRevokeApply entity = this.get(revokeId);
			entity.setCmecResult(cmecResult);
			this.bankAccountRevokeApplyDao.update(entity);
			return entity;
		}
		return null;
	}
}
