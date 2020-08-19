package com.supporter.prj.pm.fund_appropriation.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriation;

/**
 * 资金拨付
 * @author Administrator
 *
 */
@Repository
public class FundAppropriationDao extends MainDaoSupport < FundAppropriation, String > {
	

	/**
	 * 分页查询
	 * @param jqGrid 表格查询参数
	 * @param fundAppropriation 业务参数对象
	 * @param user 用户
	 * @return List<FundAppropriation>
	 */
	public List<FundAppropriation> findPage(JqGrid jqGrid, FundAppropriation fundAppropriation, UserProfile user) {
		if (fundAppropriation != null) {
			// 拨付编号
			String fundNo = fundAppropriation.getFundNo();
			if (StringUtils.isNotBlank(fundNo)) {
				jqGrid.addHqlFilter("fundNo = ? ", fundNo);
			}

			//状态
			Integer status = fundAppropriation.getStatus();
			if (status != null) {
				jqGrid.addHqlFilter("status = ? ", status);
			}

			// 性质
			Integer fundProperty = fundAppropriation.getFundProperty();
			if (fundProperty != null) {
				jqGrid.addHqlFilter("fundProperty = ? ", fundProperty);
			}

			// 只获取某项目下的数据
			String prjId = fundAppropriation.getPrjId();
			if (StringUtils.isNotBlank(prjId)) {
				jqGrid.addHqlFilter(" prjId = ? ", prjId);
			} else {// 判断条件值任意，目的是返回一个空表
				jqGrid.addHqlFilter(" 1 != 1");
			}
		}
		//String authHql = EIPService.getAuthorityService().getHqlFilter(user,
				//FileReceive.MODULE_ID,AuthConstant.AUTH_OPER_NAME_SETVALPROBLEM);
		//jqGrid.addHqlFilter(authHql);			
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取最大期数
	 * @return Integer
	 */
	public Integer getMaxPeriods() {
		String sql = "select max(budgetPeriod) from FundAppropriation";
		List<Integer> list = this.find(sql);
		if (list != null && list.size() > 0) {
			return  list.get(0);
		}
		return null;
	}
	
	/**
	 * 数据库中是否存在记录.
	 * @param fundId 资金拨付ID
	 * @return boolean
	 */
	public boolean existInDB(String fundId) {
		String hql = "select count(fundId) as recCount from "
				+ FundAppropriation.class.getName() + " where fundId=?";
		Object obj = this.retrieveFirst(hql, fundId);
		if (obj == null) {
			return false;
		} else {
			return (Long) obj > 0;
		}
	}

}
