package com.supporter.prj.linkworks.oa.outside_account_manager.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.account.entity.AccountEntity;
import com.supporter.prj.eip.account.service.AccountEntityService;
import com.supporter.prj.eip.emp.dao.VDeptPostEmpDao;
import com.supporter.prj.eip.role.entity.RoleMemberRespDeptV;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManager;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManagerRec;

@Repository
public class OutsideAccountManagerDao extends MainDaoSupport<OutsideAccountManager, String> {
	
	@Autowired
	private VDeptPostEmpDao vDeptPostEmpDao;
	
	@Autowired
	private AccountEntityService accountEntityService;
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param netin
	 * @return
	 */
	public List<OutsideAccountManager> findPage(JqGrid jqGrid, OutsideAccountManager outsideAccountManager){
		if (outsideAccountManager != null){
			//查询框
			String keyword = outsideAccountManager.getKeyword();
			if (StringUtils.isNotBlank(keyword)){
				jqGrid.addHqlFilter(" createdDate like ? ", "%"+keyword+"%");
			}
			//按申请时间倒叙排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 根据部门ID获取角色中的相关负责人
	 * @param deptIds
	 * @param roleId
	 * @return
	 */
	public List<AccountEntity> getRoleMemberByDeptIds(String deptIds, String roleId){
		String hql = " from " + RoleMemberRespDeptV.class.getName() + " where roleId = :roleId and deptId in (:deptIds) ";
		Map<String, Object> param = new HashMap();
		param.put("roleId", roleId);
		param.put("deptIds", deptIds.split(","));
		List<RoleMemberRespDeptV> list = vDeptPostEmpDao.find(hql, param);
		if (list != null && list.size() > 0){
			List <AccountEntity> accountList = new ArrayList<AccountEntity>();
			for (RoleMemberRespDeptV v : list){
				AccountEntity accountEntity = accountEntityService.getAccount(v.getMemberId());
				accountList.add(accountEntity);
			}
			return accountList;
		}
		return null;
	}
}
