package com.supporter.prj.pm.contract_balance.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConst;
import com.supporter.prj.pm.contract_balance.service.ContractBalanceConstService;

/**   
 * @Title: Entity
 * @Description: PM_CONTRACT_BALANCE_CONST.
 * @author Administrator
 * @date 2018-07-04 18:07:38
 * @version V1.0   
 *
 */
@Repository
public class ContractBalanceConstDao extends MainDaoSupport< ContractBalanceConst, String > {
	/**
	 * 分页查询
	 * @param user 用户
	 * @param jqGrid 表格请求参数
	 * @param balance 结算
	 * @return List< ContractBalanceConst >
	 */
	public List< ContractBalanceConst > findPage(UserProfile user, JqGrid jqGrid,
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
				String hql = "applicationName like ? or createdDeptName like ?";
				jqGrid.addHqlFilter(hql, likeKeyword, likeKeyword);
			}
			// 只获取某项目下的数据
			String prjId = balance.getPrjId();
			if (StringUtils.isNotBlank(prjId)) {
				jqGrid.addHqlFilter(" prjId = ? ", prjId);
			} else {// 判断条件值任意，目的是返回一个空表
				jqGrid.addHqlFilter(" 1 != 1");
			}
		}
		return this.retrievePage(jqGrid);
	}
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param balanceId
	 * @param propertyName 属性名称
	 * @param propertyName 属性值
	 * @return
	 */
	public boolean checkPropertyUniquenes(String balanceId, String propertyName, String propertyValue){
		String hql = null;
		List retList = null;
		//新建时
		if(StringUtils.isBlank(balanceId)){
			hql = "from " + ContractBalanceConst.class.getName() + " where  " + propertyName + "= ?";
			retList = this.retrieve(hql, propertyValue);
		}else{
			//编辑时
			hql = "from " + ContractBalanceConst.class.getName() + " where balanceId != ? and " + propertyName + "= ?";
			retList = this.retrieve(hql, balanceId, propertyValue);
		}
		if(CollectionUtils.isEmpty(retList)){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取累计扣减
	 * @param contractId 合同ID
	 * @param balanceId 结算ID
	 * @return double
	 */
	public double getCumulativeDeductions(String contractId, String balanceId) {
		String hql = "select sum(cumulativeDeductions) as sumAmount from "
				+ ContractBalanceConst.class.getName() + " where contractId=? and balanceId<>?";
		Object obj = this.retrieveFirst(hql, contractId, balanceId);
		if (obj == null) {
			return 0;
		} else {
			return (Double) obj;
		}
	}
	
	public Integer getMaxPeriods(){
		String sql = "select max(period) from ContractBalanceConst";
		List<Integer> list = this.find(sql);
		if(list != null && list.size() > 0){
			return  list.get(0);
		}
		return null;
	}
	
	/**
	 * 数据库中是否存在记录.
	 * @param balanceId 结算ID
	 * @return boolean
	 */
	public boolean existInDB(String balanceId) {
		String hql = "select count(balanceId) as recCount from "
				+ ContractBalanceConst.class.getName() + " where balanceId=?";
		Object obj = this.retrieveFirst(hql, balanceId);
		if (obj == null) {
			return false;
		} else {
			return (Long) obj > 0;
		}
	}
}

