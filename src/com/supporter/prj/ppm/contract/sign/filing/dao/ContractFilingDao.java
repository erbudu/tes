package com.supporter.prj.ppm.contract.sign.filing.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractFiling;
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
public class ContractFilingDao extends MainDaoSupport<ContractFiling, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractFiling> findPage(JqGrid jqGrid, ContractFiling contractFiling) {
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
			hql = "from " + ContractFiling.class.getName() + " where filingName = ?";
			retList = this.retrieve(hql, filingName);
		} else {//编辑时
			hql = "from " + ContractFiling.class.getName() + " where filingId != ? and filingName = ?";
			retList = this.retrieve(hql, filingId, filingName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

