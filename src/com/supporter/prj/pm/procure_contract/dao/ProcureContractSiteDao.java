package com.supporter.prj.pm.procure_contract.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.pm.procure_contract.entity.ProcureContractSite;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

/**
 * @Title: 涉及工程部位
 * @Description: DAO类
 * @author liyinfeng
 * @date 2018-6-14
 * @version: V1.0
 */
@Repository
public class ProcureContractSiteDao extends MainDaoSupport<ProcureContractSite, String> {

	/**
	 * 分页查询
	 * @param jqGrid 表格参数
	 * @param contractId 采购合同ID
	 * @return List<ProcureContractSite>
	 */
	public List<ProcureContractSite> findPage(JqGrid jqGrid, String contractId) {
		jqGrid.addHqlFilter(" contractId = ? ", CommonUtil.trim(contractId));
		jqGrid.addSortPropertyAsc("siteId");
		return this.retrievePage(jqGrid);
	}

}
