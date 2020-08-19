package com.supporter.prj.pm.contract_balance.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConst;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConstSwf;
import com.supporter.prj.pm.contract_balance.service.ContractBalanceConstService;

/**   
 * @Title: Entity
 * @Description: PM_CONTRACT_BALANCE_CONST_SWF.
 * @author Administrator
 * @date 2018-07-04 18:07:38
 * @version V1.0   
 *
 */
@Repository
public class ContractBalanceConstSwfDao extends MainDaoSupport< ContractBalanceConstSwf, String > {
	/**
	 * 分页查询
	 * @param user 用户
	 * @param jqGrid 表格请求参数
	 * @param balance 结算
	 * @return List< ContractBalanceConstSwf >
	 */
	public List< ContractBalanceConstSwf > findPage(UserProfile user, JqGrid jqGrid,
			ContractBalanceConst balance) {
		//权限限定条件hql
		String authHql = EIPService.getAuthorityService().getHqlFilter(user,
				ContractBalanceConstService.MODULE_ID, ContractBalanceConstService.AUTH_EDIT);
		jqGrid.addHqlFilter(authHql);
		
		if (balance != null) {
			//搜索关键字
			String keyword = balance.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				String likeKeyword = "%" + keyword + "%";
				String hql = "applicationNo like ?";
				jqGrid.addHqlFilter(hql, likeKeyword);
			}
		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取尚未启动流程的业务对象
	 * @return List < ContractBalanceConstSwf >
	 */
	public List < ContractBalanceConstSwf > getNotStartProcList() {
		String hql = "from " + ContractBalanceConstSwf.class.getName()
				+ " where oaProcId is null or oaExamStatus=?";
		return this.find(hql, ContractBalanceConstSwf.OAExamStatus.DRAFT);
	}
}

