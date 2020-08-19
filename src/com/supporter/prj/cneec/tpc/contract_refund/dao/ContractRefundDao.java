package com.supporter.prj.cneec.tpc.contract_refund.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_refund.entity.ContractRefund;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: ContractRefundDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-11-24
 * @version: V1.0
 */
@Repository
public class ContractRefundDao extends MainDaoSupport<ContractRefund, String> {

	/**
	 * 分页查询
	 */
	public List<ContractRefund> findPage(JqGrid jqGrid, ContractRefund contractRefund, String authFilter) {
		if (contractRefund != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = contractRefund.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or contractNo like ? or contractName like ? or returnSlipNo like ? or collectionUnit like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (contractRefund.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", contractRefund.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

}
