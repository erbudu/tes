package com.supporter.prj.ppm.contract.effect.review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.effect.review.entity.ContractEffectReviewCon;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 评审结果.
 * @author YAN
 * @date 2019-09-06 18:35:32
 * @version V1.0
 */
@Repository
public class ContractEffectReviewConDao extends MainDaoSupport<ContractEffectReviewCon, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractEffectReviewCon> findPage(JqGrid jqGrid, ContractEffectReviewCon contractEffectReviewCon) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractEffectRevConId
	 * @param contractEffectRevConName
	 * @return
	 */
	public boolean nameIsValid(String contractEffectRevConId,String contractEffectRevConName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(contractEffectRevConId)) {//新建时
			hql = "from " + ContractEffectReviewCon.class.getName() + " where contractEffectRevConName = ?";
			retList = this.retrieve(hql, contractEffectRevConName);
		} else {//编辑时
			hql = "from " + ContractEffectReviewCon.class.getName() + " where contractEffectRevConId != ? and contractEffectRevConName = ?";
			retList = this.retrieve(hql, contractEffectRevConId, contractEffectRevConName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

