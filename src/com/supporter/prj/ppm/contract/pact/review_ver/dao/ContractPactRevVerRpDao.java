package com.supporter.prj.ppm.contract.pact.review_ver.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerRp;

@Repository
public class ContractPactRevVerRpDao extends MainDaoSupport<ContractPactRevVerRp, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractPactRevVerRp> findPage(JqGrid jqGrid, ContractPactRevVerRp contractPactRevVerRp) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param rpId
	 * @param rpName
	 * @return
	 */
	public boolean nameIsValid(String rpId,String rpName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(rpId)) {//新建时
			hql = "from " + ContractPactRevVerRp.class.getName() + " where rpName = ?";
			retList = this.retrieve(hql, rpName);
		} else {//编辑时
			hql = "from " + ContractPactRevVerRp.class.getName() + " where rpId != ? and rpName = ?";
			retList = this.retrieve(hql, rpId, rpName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

