package com.supporter.prj.pm.procure_contract.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.pm.procure_contract.entity.ProcureContract;

/**
 * @Title: ProcureContractDao
 * @Description: DAO类
 * @author liyinfeng
 * @date 2018-6-14
 * @version: V1.0
 */
@Repository
public class ProcureContractDao extends MainDaoSupport<ProcureContract, String> {

	/**
	 * 分页查询
	 * @param jqGrid 表格请求参数
	 * @param procureContract 实体参数
	 * @param authFilter 权限条件
	 * @param map 
	 * @return List<ProcureContract>
	 */
	public List<ProcureContract> findPage(JqGrid jqGrid, ProcureContract procureContract,
			String authFilter, Map<String, Object> map) {
		if (procureContract != null) {
			// 列表页面搜索输入框可查询字段

			String keyword = procureContract.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" (contractName like ? or contractNo like ?) ", "%" + keyword + "%", "%" + keyword + "%");
			}
			// 只获取某项目下的数据
			String prjId = procureContract.getPrjId();
			if (StringUtils.isNotBlank(prjId)) {
				jqGrid.addHqlFilter(" prjId = ? ", prjId);
			} else {// 判断条件值任意，目的是返回一个空表
				jqGrid.addHqlFilter(" 1 != 1");
			}
			//采购类型
			if (procureContract.getContractType() > -1) {
				jqGrid.addHqlFilter("contractType = ?", procureContract.getContractType());
			}
			//状态
			int status = procureContract.getStatus();
			if (status > -1) {
				jqGrid.addHqlFilter("status = ? ", status);
			}	
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取委托代采
	 * @param contractId 合同ID
	 * @return ProcureContract
	 */
	public List<ProcureContract> getConsignPurchase(String contractId) {
		Map <String, Object> params = new HashMap <String, Object>();
		params.put("relativeContractId", contractId);
		params.put("buyType", ProcureContract.BuyType.FOR_OTHER);
		String hql = "from " + ProcureContract.class.getName()
				+ " where buyType=:buyType and relativeContractId=:relativeContractId and status="
				+ ProcureContract.Status.COMPLETE;
		
		return this.find(hql, params);
	}
	
	/**
	 * 数据库中是否存在记录.
	 * @param contractId 采购合同ID
	 * @return boolean
	 */
	public boolean existInDB(String contractId) {
		String hql = "select count(contractId) as recCount from "
				+ ProcureContract.class.getName() + " where contractId=?";
		Object obj = this.retrieveFirst(hql, contractId);
		if (obj == null) {
			return false;
		} else {
			return (Long) obj > 0;
		}
	}
	
	/**
	 * 获取项目角色人员
	 * @param prjId 项目ID
	 * @param roleId 角色ID
	 * @return List <String>
	 */
	@SuppressWarnings("unchecked")
	public List <String> getPrjMembersByRole(String prjId, long roleId) {
		String sql = "select emp_id from v_prj_member where prj_id=? and role_id=?";
		Map <String, Type> scalarMap = new LinkedHashMap <String, Type>();
		scalarMap.put("emp_id", StringType.INSTANCE);
		return this.sqlQuery(sql, scalarMap, prjId, roleId);
	}
	
}
