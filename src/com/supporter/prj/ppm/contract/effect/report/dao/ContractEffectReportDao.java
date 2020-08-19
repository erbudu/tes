package com.supporter.prj.ppm.contract.effect.report.dao;

import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReport;
import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 主合同签约报审表.
 * @author YAN
 * @date 2019-09-05 17:09:55
 * @version V1.0
 */
@Repository
public class ContractEffectReportDao extends MainDaoSupport<ContractEffectReport, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractEffectReport> findPage(JqGrid jqGrid, ContractEffectReport contractEffectReport) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractEffectId
	 * @param contractEffectName
	 * @return
	 */
	public boolean nameIsValid(String contractEffectId,String contractEffectName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(contractEffectId)) {//新建时
			hql = "from " + ContractEffectReport.class.getName() + " where contractEffectName = ?";
			retList = this.retrieve(hql, contractEffectName);
		} else {//编辑时
			hql = "from " + ContractEffectReport.class.getName() + " where contractEffectId != ? and contractEffectName = ?";
			retList = this.retrieve(hql, contractEffectId, contractEffectName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

