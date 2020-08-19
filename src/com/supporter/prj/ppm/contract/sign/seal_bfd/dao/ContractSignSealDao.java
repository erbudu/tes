package com.supporter.prj.ppm.contract.sign.seal_bfd.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.sign.seal_bfd.entity.ContractSignSeal;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 主合同出版.
 * @author YAN
 * @date 2019-09-10 14:57:13
 * @version V1.0
 */
@Repository
public class ContractSignSealDao extends MainDaoSupport<ContractSignSeal, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractSignSeal> findPage(JqGrid jqGrid, ContractSignSeal contractSignSeal) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param signSealId
	 * @param signSealName
	 * @return
	 */
	public boolean nameIsValid(String signSealId,String signSealName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(signSealId)) {//新建时
			hql = "from " + ContractSignSeal.class.getName() + " where signSealName = ?";
			retList = this.retrieve(hql, signSealName);
		} else {//编辑时
			hql = "from " + ContractSignSeal.class.getName() + " where signSealId != ? and signSealName = ?";
			retList = this.retrieve(hql, signSealId, signSealName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

