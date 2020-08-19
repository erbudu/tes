package com.supporter.prj.ppm.contract.effect.seal_bfd.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.ContractEffectSeal;
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
public class ContractEffectSealDao extends MainDaoSupport<ContractEffectSeal, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractEffectSeal> findPage(JqGrid jqGrid, ContractEffectSeal contractEffectSeal) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param effectSealId
	 * @param effectSealName
	 * @return
	 */
	public boolean nameIsValid(String effectSealId,String effectSealName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(effectSealId)) {//新建时
			hql = "from " + ContractEffectSeal.class.getName() + " where effectSealName = ?";
			retList = this.retrieve(hql, effectSealName);
		} else {//编辑时
			hql = "from " + ContractEffectSeal.class.getName() + " where effectSealId != ? and effectSealName = ?";
			retList = this.retrieve(hql, effectSealId, effectSealName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

