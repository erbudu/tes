package com.supporter.prj.ppm.contract.effect.direct.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.effect.direct.entity.ContractEffectDirect;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 主合同直接生效.
 * @author YAN
 * @date 2019-09-18 10:30:48
 * @version V1.0
 */
@Repository
public class ContractEffectDirectDao extends MainDaoSupport<ContractEffectDirect, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractEffectDirect> findPage(JqGrid jqGrid, ContractEffectDirect contractEffectDirect) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param directId
	 * @param directName
	 * @return
	 */
	public boolean nameIsValid(String directId,String directName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(directId)) {//新建时
			hql = "from " + ContractEffectDirect.class.getName() + " where directName = ?";
			retList = this.retrieve(hql, directName);
		} else {//编辑时
			hql = "from " + ContractEffectDirect.class.getName() + " where directId != ? and directName = ?";
			retList = this.retrieve(hql, directId, directName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

