package com.supporter.prj.ppm.ecc.cac_review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacReview;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制委员会审核表.
 * @author YAN
 * @date 2019-08-16 18:45:21
 * @version V1.0
 */
@Repository
public class EccCacReviewDao extends MainDaoSupport<EccCacReview, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccCacReview> findPage(JqGrid jqGrid, EccCacReview eccCacReview) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param eccCacId
	 * @param eccCacName
	 * @return
	 */
	public boolean nameIsValid(String eccCacId,String eccCacName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(eccCacId)) {//新建时
			hql = "from " + EccCacReview.class.getName() + " where eccCacName = ?";
			retList = this.retrieve(hql, eccCacName);
		} else {//编辑时
			hql = "from " + EccCacReview.class.getName() + " where eccCacId != ? and eccCacName = ?";
			retList = this.retrieve(hql, eccCacId, eccCacName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

