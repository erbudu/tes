package com.supporter.prj.ppm.contract.sign.review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.sign.review.entity.ContractSignReviewCon;
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
public class ContractSignReviewConDao extends MainDaoSupport<ContractSignReviewCon, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractSignReviewCon> findPage(JqGrid jqGrid, ContractSignReviewCon contractSignReviewCon) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractSignRevConId
	 * @param contractSignRevConName
	 * @return
	 */
	public boolean nameIsValid(String contractSignRevConId,String contractSignRevConName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(contractSignRevConId)) {//新建时
			hql = "from " + ContractSignReviewCon.class.getName() + " where contractSignRevConName = ?";
			retList = this.retrieve(hql, contractSignRevConName);
		} else {//编辑时
			hql = "from " + ContractSignReviewCon.class.getName() + " where contractSignRevConId != ? and contractSignRevConName = ?";
			retList = this.retrieve(hql, contractSignRevConId, contractSignRevConName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

