package com.supporter.prj.ppm.contract.sign.review.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.contract.sign.review.entity.PpmContractSignReview;

/**
 * @Title: Entity
 * @Description: 签约评审表.
 * @author YAN
 * @date 2019-09-06 18:35:28
 * @version V1.0
 */
@Repository("PpmContractSignReviewDao")
public class PpmContractSignReviewDao extends MainDaoSupport<PpmContractSignReview, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<PpmContractSignReview> findPage(JqGrid jqGrid, PpmContractSignReview contractSignReview) {
		if (contractSignReview!=null){
			String prjId = contractSignReview.getPrjId();
			int status = contractSignReview.getStatus();
			if (status!=0){
				jqGrid.addHqlFilter(" status = ? and contractSignReviewId in (select v.contractSignReviewId from " +
						" ContractSignReviewCon v where v.rvConBussinesStatus!=0 and v.contractSignReviewId = contractSignReviewId ) ",status);
			}
			if (StringUtils.isNotBlank(prjId)){
				jqGrid.addHqlFilter(" prjId = ? ",prjId);
				return this.retrievePage(jqGrid);
			}
		}
		return null;
	}

	/**
	 * 获取需要评审验证的评审列表
	 * @param jqGrid
	 * @param contractSignReview
	 * @return
	 */
	public List<PpmContractSignReview> findPageTwo(JqGrid jqGrid, PpmContractSignReview contractSignReview) {
		if (contractSignReview != null) {
			String prjId = contractSignReview.getPrjId();
			if (StringUtils.isNotBlank(prjId)) {
				jqGrid.addHqlFilter(" prjId = ? ", prjId);

			}
			jqGrid.addHqlFilter(
					"status = ? and contractSignReviewId in (select contractSignReviewId from "
							+ " ContractSignReviewCon where rvConStatus = ?) ",
					PpmContractSignReview.StatusCodeTable.COMPLETE, 1);
			return this.retrievePage(jqGrid);
		}
		return null;
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractSignReviewId
	 * @param contractSignReviewName
	 * @return
	 */
	public boolean nameIsValid(String contractSignReviewId,String contractSignReviewName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(contractSignReviewId)) {//新建时
			hql = "from " + PpmContractSignReview.class.getName() + " where contractSignReviewName = ?";
			retList = this.retrieve(hql, contractSignReviewName);
		} else {//编辑时
			hql = "from " + PpmContractSignReview.class.getName() + " where contractSignReviewId != ? and contractSignReviewName = ?";
			retList = this.retrieve(hql, contractSignReviewId, contractSignReviewName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}


}

