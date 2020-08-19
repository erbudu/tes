package com.supporter.prj.ppm.ecc.dept_review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptReview;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 部门出口管制评审.
 * @author YAN
 * @date 2019-08-16 18:43:19
 * @version V1.0
 */
@Repository
public class EccDeptReviewDao extends MainDaoSupport<EccDeptReview, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccDeptReview> findPage(JqGrid jqGrid, EccDeptReview eccDeptReview) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param deptEccId
	 * @param deptEccName
	 * @return
	 */
	public boolean nameIsValid(String deptEccId,String deptEccName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(deptEccId)) {//新建时
			hql = "from " + EccDeptReview.class.getName() + " where deptEccName = ?";
			retList = this.retrieve(hql, deptEccName);
		} else {//编辑时
			hql = "from " + EccDeptReview.class.getName() + " where deptEccId != ? and deptEccName = ?";
			retList = this.retrieve(hql, deptEccId, deptEccName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

