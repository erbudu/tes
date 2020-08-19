package com.supporter.prj.cneec.tpc.prj_contract_modify.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_contract_modify.entity.PrjContractTableBM;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.util.CommonUtil;

@Repository
public class PrjContractTableBMDao extends MainDaoSupport<PrjContractTableBM, String> {

	/**
	 * 分页查询
	 */
	public List<PrjContractTableBM> findPage(JqGrid jqGrid, PrjContractTableBM prjContractTable, String authFilter) {
		if (prjContractTable != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = prjContractTable.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectNo like ? or projectName like ? or contractNo like ? or contractName like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (CommonUtil.trim(prjContractTable.getContractStatusCode()).length() > 0) {
				jqGrid.addHqlFilter(" contractStatusCode = ? ", prjContractTable.getContractStatusCode());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 根据contractId获取对象
	 * @param contractId
	 * @return
	 */
	public List<PrjContractTableBM> getByContractId(String contractId) {
		String hql = " from " + PrjContractTableBM.class.getName() + " where contractId = ?";
		List<PrjContractTableBM> list = this.find(hql, contractId);
		return list;
	}

}
