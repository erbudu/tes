package com.supporter.prj.ppm.ecc.wg_review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.wg_review.entity.EccWgReview;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制工作组会商审核表.
 * @author YAN
 * @date 2019-08-16 18:44:47
 * @version V1.0
 */
@Repository
public class EccWgReviewDao extends MainDaoSupport<EccWgReview, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccWgReview> findPage(JqGrid jqGrid, EccWgReview eccWgReview) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param eccWgId
	 * @param eccWgName
	 * @return
	 */
	public boolean nameIsValid(String eccWgId,String eccWgName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(eccWgId)) {//新建时
			hql = "from " + EccWgReview.class.getName() + " where eccWgName = ?";
			retList = this.retrieve(hql, eccWgName);
		} else {//编辑时
			hql = "from " + EccWgReview.class.getName() + " where eccWgId != ? and eccWgName = ?";
			retList = this.retrieve(hql, eccWgId, eccWgName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

