package com.supporter.prj.ppm.contract.effect.filing.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.effect.filing.entity.ContractEffectFiling;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING.
 * @author YAN
 * @date 2019-09-17 11:37:24
 * @version V1.0
 */
@Repository
public class ContractEffectFilingDao extends MainDaoSupport<ContractEffectFiling, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractEffectFiling> findPage(JqGrid jqGrid, ContractEffectFiling contractFiling) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param filingId
	 * @param filingName
	 * @return
	 */
	public boolean nameIsValid(String filingId,String filingName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(filingId)) {//新建时
			hql = "from " + ContractEffectFiling.class.getName() + " where filingName = ?";
			retList = this.retrieve(hql, filingName);
		} else {//编辑时
			hql = "from " + ContractEffectFiling.class.getName() + " where filingId != ? and filingName = ?";
			retList = this.retrieve(hql, filingId, filingName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

