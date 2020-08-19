package com.supporter.prj.ppm.contract.effect.filing.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.effect.filing.entity.ContractEffectFilingBfd;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: PPM_CONTRACT_FILING_BFD.
 * @author YAN
 * @date 2019-09-17 11:37:25
 * @version V1.0
 */
@Repository
public class ContractEffectFilingBfdDao extends MainDaoSupport<ContractEffectFilingBfd, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractEffectFilingBfd> findPage(JqGrid jqGrid, ContractEffectFilingBfd contractFilingBfd) {
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
			hql = "from " + ContractEffectFilingBfd.class.getName() + " where bfdName = ?";
			retList = this.retrieve(hql, bfdName);
		} else {//编辑时
			hql = "from " + ContractEffectFilingBfd.class.getName() + " where bfdId != ? and bfdName = ?";
			retList = this.retrieve(hql, bfdId, bfdName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

