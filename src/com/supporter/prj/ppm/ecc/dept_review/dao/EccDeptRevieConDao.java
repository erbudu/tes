package com.supporter.prj.ppm.ecc.dept_review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptRevieCon;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 部门出口管制结论.
 * @author YAN
 * @date 2019-08-16 18:43:21
 * @version V1.0
 */
@Repository
public class EccDeptRevieConDao extends MainDaoSupport<EccDeptRevieCon, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccDeptRevieCon> findPage(JqGrid jqGrid, EccDeptRevieCon eccDeptRevieCon) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param deptEccRvConId
	 * @param deptEccRvConName
	 * @return
	 */
	public boolean nameIsValid(String deptEccRvConId,String deptEccRvConName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(deptEccRvConId)) {//新建时
			hql = "from " + EccDeptRevieCon.class.getName() + " where deptEccRvConName = ?";
			retList = this.retrieve(hql, deptEccRvConName);
		} else {//编辑时
			hql = "from " + EccDeptRevieCon.class.getName() + " where deptEccRvConId != ? and deptEccRvConName = ?";
			retList = this.retrieve(hql, deptEccRvConId, deptEccRvConName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

