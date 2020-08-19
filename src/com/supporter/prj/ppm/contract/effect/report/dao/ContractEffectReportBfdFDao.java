package com.supporter.prj.ppm.contract.effect.report.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.effect.report.entity.ContractEffectReportBfdF;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 主合同签约评审资料清单单文件.
 * @author YAN
 * @date 2019-09-05 17:09:58
 * @version V1.0
 */
@Repository
public class ContractEffectReportBfdFDao extends MainDaoSupport<ContractEffectReportBfdF, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractEffectReportBfdF> findPage(JqGrid jqGrid, ContractEffectReportBfdF contractEffectReportBfdF) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param recordId
	 * @param recordName
	 * @return
	 */
	public boolean nameIsValid(String recordId,String recordName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(recordId)) {//新建时
			hql = "from " + ContractEffectReportBfdF.class.getName() + " where recordName = ?";
			retList = this.retrieve(hql, recordName);
		} else {//编辑时
			hql = "from " + ContractEffectReportBfdF.class.getName() + " where recordId != ? and recordName = ?";
			retList = this.retrieve(hql, recordId, recordName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

