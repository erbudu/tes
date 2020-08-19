package com.supporter.prj.ppm.contract.effect.review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.effect.review.entity.ContractEffectRevEmpRp;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 评审人员的评审要点.
 * @author YAN
 * @date 2019-09-06 18:35:31
 * @version V1.0
 */
@Repository
public class ContractEffectRevEmpRpDao extends MainDaoSupport<ContractEffectRevEmpRp, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractEffectRevEmpRp> findPage(JqGrid jqGrid, ContractEffectRevEmpRp contractEffectRevEmpRp) {
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
			hql = "from " + ContractEffectRevEmpRp.class.getName() + " where rpName = ?";
			retList = this.retrieve(hql, rpName);
		} else {//编辑时
			hql = "from " + ContractEffectRevEmpRp.class.getName() + " where rpId != ? and rpName = ?";
			retList = this.retrieve(hql, rpId, rpName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

