package com.supporter.prj.ppm.contract.sign.report.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReportCon;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 报审审核结果.
 * @author YAN
 * @date 2019-09-05 17:09:59
 * @version V1.0
 */
@Repository
public class ContractSignReportConDao extends MainDaoSupport<ContractSignReportCon, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractSignReportCon> findPage(JqGrid jqGrid, ContractSignReportCon contractSignReportCon) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractSignRvConId
	 * @param contractSignRvConName
	 * @return
	 */
	public boolean nameIsValid(String contractSignRvConId,String contractSignRvConName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(contractSignRvConId)) {//新建时
			hql = "from " + ContractSignReportCon.class.getName() + " where contractSignRvConName = ?";
			retList = this.retrieve(hql, contractSignRvConName);
		} else {//编辑时
			hql = "from " + ContractSignReportCon.class.getName() + " where contractSignRvConId != ? and contractSignRvConName = ?";
			retList = this.retrieve(hql, contractSignRvConId, contractSignRvConName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

