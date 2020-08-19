package com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.entity.ContractStampTaxAmount;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 贸易印花税金额表.
 * @author LEGO
 * @date 2020-02-03 13:12:36
 * @version V1.0
 */
@Repository
public class ContractStampTaxAmountDao extends MainDaoSupport<ContractStampTaxAmount, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractStampTaxAmount> findPage(JqGrid jqGrid, ContractStampTaxAmount contractStampTaxAmount) {
		return this.retrievePage(jqGrid);
	}
}

