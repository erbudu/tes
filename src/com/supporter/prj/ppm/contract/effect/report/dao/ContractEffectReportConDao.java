package com.supporter.prj.ppm.contract.effect.report.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReportCon;
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
public class ContractEffectReportConDao extends MainDaoSupport<ContractEffectReportCon, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractEffectReportCon> findPage(JqGrid jqGrid, ContractEffectReportCon contractEffectReportCon) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractEffectRvConId
	 * @param contractEffectRvConName
	 * @return
	 */
	public boolean nameIsValid(String contractEffectRvConId,String contractEffectRvConName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(contractEffectRvConId)) {//新建时
			hql = "from " + ContractEffectReportCon.class.getName() + " where contractEffectRvConName = ?";
			retList = this.retrieve(hql, contractEffectRvConName);
		} else {//编辑时
			hql = "from " + ContractEffectReportCon.class.getName() + " where contractEffectRvConId != ? and contractEffectRvConName = ?";
			retList = this.retrieve(hql, contractEffectRvConId, contractEffectRvConName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

