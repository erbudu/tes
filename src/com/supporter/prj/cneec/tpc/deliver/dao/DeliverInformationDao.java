package com.supporter.prj.cneec.tpc.deliver.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.deliver.entity.DeliverInformation;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: DeliverInformationDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
@Repository
public class DeliverInformationDao extends MainDaoSupport<DeliverInformation, String> {

	/**
	 * 分页查询
	 */
	public List<DeliverInformation> findPage(JqGrid jqGrid, String deliverId) {
		// 客户明细须通过主表ID获取
		if (StringUtils.isNotBlank(deliverId)) {
			jqGrid.addHqlFilter(" deliverId = ? ", deliverId);
		} else {
			jqGrid.addHqlFilter(" deliverId = ? ", "");
		}
		jqGrid.addSortPropertyAsc("serialNumber");
		return this.retrievePage(jqGrid);
	}

}
