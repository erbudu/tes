package com.supporter.prj.ppm.contract.effect.seal_bfd.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.effect.seal_bfd.entity.ContractEffectSealBfd;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 主合同签约出版资料清单.
 * @author YAN
 * @date 2019-09-10 14:57:15
 * @version V1.0
 */
@Repository
public class ContractEffectSealBfdDao extends MainDaoSupport<ContractEffectSealBfd, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractEffectSealBfd> findPage(JqGrid jqGrid, ContractEffectSealBfd contractEffectSealBfd) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param bfdId
	 * @param bfdName
	 * @return
	 */
	public boolean nameIsValid(String bfdId,String bfdName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(bfdId)) {//新建时
			hql = "from " + ContractEffectSealBfd.class.getName() + " where bfdName = ?";
			retList = this.retrieve(hql, bfdName);
		} else {//编辑时
			hql = "from " + ContractEffectSealBfd.class.getName() + " where bfdId != ? and bfdName = ?";
			retList = this.retrieve(hql, bfdId, bfdName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

