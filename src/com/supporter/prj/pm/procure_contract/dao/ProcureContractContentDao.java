package com.supporter.prj.pm.procure_contract.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractContent;
import com.supporter.util.CommonUtil;

/**
 * @Title: 采购内容
 * @Description: DAO类
 * @author liyinfeng
 * @date 2018-6-14
 * @version: V1.0
 */
@Repository
public class ProcureContractContentDao extends MainDaoSupport<ProcureContractContent, String> {

	/**
	 * 分页查询
	 * @param jqGrid 查询表格参数
	 * @param contractId 采购合同ID		
	 * @return List<ProcureContractContent>
	 */
	public List<ProcureContractContent> findPage(JqGrid jqGrid, String contractId,String buyItem) {
		jqGrid.addHqlFilter(" contractId = ? ", CommonUtil.trim(contractId));
		if (StringUtils.isNotBlank(buyItem)) {
			jqGrid.addHqlFilter(" buyItem like ? ", "%" + buyItem + "%");
		}
		jqGrid.addSortPropertyAsc("recId");
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 根据采购项查询采购内容列表
	 */
	public List<ProcureContractContent> getContentListByBuyItem(String recId,String buyItem) {
		List<ProcureContractContent> editers = null;
		if (StringUtils.isBlank(recId)) {// 新建时判断整个库
			String hql = "from " + ProcureContractContent.class.getName()
				+ " where buyItem = ? ";
			editers = this.find(hql, CommonUtil.trim(buyItem));
		}else{
			String hql = "from " + ProcureContractContent.class.getName()
				+ " where recId <> ? and buyItem = ?";
			editers = this.find(hql, CommonUtil.trim(recId), CommonUtil.trim(buyItem));
		}
		return editers;
	}		
	
	public ProcureContractContent getByContractId(String contractId){
		String hql = "from ProcureContractContent where contractId = '" + contractId + "'";
		List<ProcureContractContent> list = find(hql);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;	
		} 	
	}
	
	//用于服务与物资合同结算的取值
	public List<ProcureContractContent> getListByContractId(String contractId){
		String hql = "from ProcureContractContent where contractId = '" + contractId + "'";
		List<ProcureContractContent> list = find(hql);
		if(list != null && list.size() > 0){
			return list;
		}else{
			return null;	
		} 	
	}

}
