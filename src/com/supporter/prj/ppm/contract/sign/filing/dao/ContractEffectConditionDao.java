package com.supporter.prj.ppm.contract.sign.filing.dao;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractEffectCondition;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractFiling;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING.
 * @author YAN
 * @date 2019-09-17 11:37:24
 * @version V1.0
 */
@Repository
public class ContractEffectConditionDao extends MainDaoSupport<ContractEffectCondition, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractEffectCondition> findPage(JqGrid jqGrid, ContractEffectCondition contractFiling) {
		if (contractFiling!=null){
			String prjId = contractFiling.getPrjId();
			if (StringUtils.isNotBlank(prjId)){
				jqGrid.addHqlFilter(" prjId = ? ",prjId);
				return this.retrievePage(jqGrid);
			}
		}
		return null;
	}

}

