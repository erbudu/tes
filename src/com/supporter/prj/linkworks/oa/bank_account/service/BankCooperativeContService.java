package com.supporter.prj.linkworks.oa.bank_account.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bank_account.dao.BankCooperativeContDao;
import com.supporter.prj.linkworks.oa.bank_account.entity.BankCooperativeCont;
import com.supporter.prj.linkworks.oa.bank_account.util.LogConstant;
import com.supporter.util.CommonUtil;


@Service
public class BankCooperativeContService {
	@Autowired
	private BankCooperativeContDao bankCooperativeContDao;

	public BankCooperativeCont get(String moduleId) {
		return bankCooperativeContDao.get(moduleId);
	}
	
	public String getAddressOfBank(String moduleId) {
		return bankCooperativeContDao.getAddressOfBank(moduleId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public BankCooperativeCont initEditOrViewPage(JqGrid jqGrid,String cooperativeId,UserProfile user) {
		if (StringUtils.isBlank(cooperativeId)) {// 新建
			BankCooperativeCont bankCooperativeCont = newBankCooperativeCont(user);
			bankCooperativeCont.setAdd(true);
			return bankCooperativeCont;
		} else {// 编辑
			//获得主表
			BankCooperativeCont bankCooperativeCont =  bankCooperativeContDao.get(cooperativeId);
	    	bankCooperativeCont.setAdd(false);		
			return bankCooperativeCont;
		}
	}
	
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public BankCooperativeCont newBankCooperativeCont(UserProfile auserprf_U){
    	BankCooperativeCont lbankCooperativeCont_N = new BankCooperativeCont();
    	lbankCooperativeCont_N.setCooperativeId(com.supporter.util.UUIDHex.newId());
    	lbankCooperativeCont_N.setCreatedById(auserprf_U.getPersonId());
    	lbankCooperativeCont_N.setCreatedBy(auserprf_U.getName()); 
    	String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    	lbankCooperativeCont_N.setCreatedDate(date);
    	lbankCooperativeCont_N.setStatus(Long.valueOf(BankCooperativeCont.DRAFT+""));
        return lbankCooperativeCont_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<BankCooperativeCont> getGrid(UserProfile user, JqGrid jqGrid, BankCooperativeCont bankCooperativeCont) {
		List<BankCooperativeCont> list=this.bankCooperativeContDao.findPage(user,jqGrid, bankCooperativeCont);//根据输入的查询条件查询列表
		return list;	
		
	}
	
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public BankCooperativeCont saveOrUpdate(UserProfile user, BankCooperativeCont bankCooperativeCont, Map< String, Object > valueMap) {
		BankCooperativeCont ret = null;
		//开户银行一级名称
		String openBankFirstName = EIPService.getComCodeTableService().getCodeTable("BANK_ACCOUNT_OPEN_FRIST_N").getDisplay(bankCooperativeCont.getOpenBankFirstNameId());
		bankCooperativeCont.setOpenBankFirstName(openBankFirstName);			
		if (bankCooperativeCont.getAdd()) {// 新建

			//保存主表
			this.bankCooperativeContDao.save(bankCooperativeCont);
			
			ret = bankCooperativeCont;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_BANKCOOPERRATIVE_LOG_MESSAGE, bankCooperativeCont.getBankAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS6).info(
					user, LogConstant.ADD_BANKCOOPERRATIVE_LOG_ACTION, logMessage,
					bankCooperativeCont, null);
		} else {// 编辑
			
			//权限验证(判断是不是有编辑信息系统维护申请的权限)
			//AuthUtil.canExecute(user, BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, bankCooperativeCont.getBankCooperativeContId(), bankCooperativeCont);									
			bankCooperativeCont.setModifiedBy(user.getName());
			bankCooperativeCont.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankCooperativeCont.setModifiedDate(date);
			//编辑之后保存主表
			this.bankCooperativeContDao.update(bankCooperativeCont);
			ret = bankCooperativeCont;			
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_BANKCOOPERRATIVE_LOG_MESSAGE, bankCooperativeCont.getBankAccountName());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS6).info(
					user, LogConstant.EDIT_BANKCOOPERRATIVE_LOG_ACTION, logMessage,
					bankCooperativeCont, null);

		}
		return ret;

	}
	
    /**
	 * 保存提交
	 * 
	 * @param user 用户信息
	 * @param apply 实体类
	 * @return
	 */
	public BankCooperativeCont commit(UserProfile user, BankCooperativeCont bankCooperativeCont, Map< String, Object > valueMap) {
		BankCooperativeCont ret = null;
		boolean isNew=bankCooperativeCont.getAdd();		
		if(isNew){
			 
			//状态（审批完成）
			bankCooperativeCont.setStatus(Long.valueOf(BankCooperativeCont.EFFECTIVE+""));
			//保存主表
			this.bankCooperativeContDao.save(bankCooperativeCont);
			ret = bankCooperativeCont;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{AddApplication : " + apply + "}", null, null);
		} else {// 编辑
			
			bankCooperativeCont.setModifiedBy(user.getName());
			bankCooperativeCont.setModifiedById(user.getPersonId());
			String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			bankCooperativeCont.setModifiedDate(date);
			//状态（审批完成）
			bankCooperativeCont.setStatus(Long.valueOf(BankCooperativeCont.EFFECTIVE+""));
			//编辑之后保存主表
			this.bankCooperativeContDao.update(bankCooperativeCont);
			ret = bankCooperativeCont;
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
	public void delete(UserProfile user, String cooperativeIds) {
		if (StringUtils.isNotBlank(cooperativeIds)) {
			for (String cooperativeId : cooperativeIds.split(",")) {
				BankCooperativeCont bankCooperativeCont=bankCooperativeContDao.get(cooperativeId);
				if(bankCooperativeCont==null){
					continue;
				}
				//权限验证(判断是不是有编辑信息系统维护申请的权限)
				//AuthUtil.canExecute(user, BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFBTN, bankCooperativeCont.getBankCooperativeContId(), bankCooperativeCont);	
				//删除主表
				this.bankCooperativeContDao.delete(bankCooperativeCont);
				

			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_BANKCOOPERRATIVE_LOG_MESSAGE, "删除的主键为"+cooperativeIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS6).info(
					user, LogConstant.DELETE_BANKCOOPERRATIVE_LOG_ACTION, logMessage,
					null, null);

		}
	}
	
	public List<BankCooperativeCont> findBankCooperativeContList() {

		List<BankCooperativeCont> listDeptResource = this.bankCooperativeContDao.getBankCooperativeContList();
		return listDeptResource;
	}
	
	
	
	
	
}
