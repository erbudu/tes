package com.supporter.prj.ppm.ecc.group_review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.group_review.entity.EccGroupReview;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 集团管制审核表.
 * @author YAN
 * @date 2019-08-16 18:46:29
 * @version V1.0
 */
@Repository
public class EccGroupReviewDao extends MainDaoSupport<EccGroupReview, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccGroupReview> findPage(JqGrid jqGrid, EccGroupReview eccGroupReview) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param eccGroupId
	 * @param eccGroupName
	 * @return
	 */
	public boolean nameIsValid(String eccGroupId,String eccGroupName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(eccGroupId)) {//新建时
			hql = "from " + EccGroupReview.class.getName() + " where eccGroupName = ?";
			retList = this.retrieve(hql, eccGroupName);
		} else {//编辑时
			hql = "from " + EccGroupReview.class.getName() + " where eccGroupId != ? and eccGroupName = ?";
			retList = this.retrieve(hql, eccGroupId, eccGroupName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

