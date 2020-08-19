package com.supporter.prj.ppm.contract.effect.review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.effect.review.entity.PpmContractEffectReview;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 生效评审表.
 * @author YAN
 * @date 2019-09-06 18:35:28
 * @version V1.0
 */
@Repository("PpmContractEffectReviewDao")
public class PpmContractEffectReviewDao extends MainDaoSupport<PpmContractEffectReview, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<PpmContractEffectReview> findPage(JqGrid jqGrid, PpmContractEffectReview contractEffectReview) {
		if (contractEffectReview!=null){
			int status = contractEffectReview.getStatus();
			if (status!=0){
				jqGrid.addHqlFilter(" status = ? and contractEffectReviewId in (select v.contractEffectReviewId from " +
						" ContractEffectReviewCon v where v.rvConBussinesStatus!=0 and v.contractEffectReviewId = contractEffectReviewId ) ",status);
			}
			String prjId = contractEffectReview.getPrjId();
			System.out.println("项目id"+prjId);
			if (StringUtils.isNotBlank(prjId)){
				jqGrid.addHqlFilter(" prjId = ? ",prjId);
				return this.retrievePage(jqGrid);
			}
		}
		return null;
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractEffectReviewId
	 * @param contractEffectReviewName
	 * @return
	 */
	public boolean nameIsValid(String contractEffectReviewId,String contractEffectReviewName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(contractEffectReviewId)) {//新建时
			hql = "from " + PpmContractEffectReview.class.getName() + " where contractEffectReviewName = ?";
			retList = this.retrieve(hql, contractEffectReviewName);
		} else {//编辑时
			hql = "from " + PpmContractEffectReview.class.getName() + " where contractEffectReviewId != ? and contractEffectReviewName = ?";
			retList = this.retrieve(hql, contractEffectReviewId, contractEffectReviewName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

