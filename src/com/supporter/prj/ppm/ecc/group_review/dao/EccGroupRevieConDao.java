package com.supporter.prj.ppm.ecc.group_review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.group_review.entity.EccGroupRevieCon;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 集团评审结论.
 * @author YAN
 * @date 2019-08-16 18:46:30
 * @version V1.0
 */
@Repository
public class EccGroupRevieConDao extends MainDaoSupport<EccGroupRevieCon, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccGroupRevieCon> findPage(JqGrid jqGrid, EccGroupRevieCon eccGroupRevieCon) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param groupEccRvConId
	 * @param groupEccRvConName
	 * @return
	 */
	public boolean nameIsValid(String groupEccRvConId,String groupEccRvConName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(groupEccRvConId)) {//新建时
			hql = "from " + EccGroupRevieCon.class.getName() + " where groupEccRvConName = ?";
			retList = this.retrieve(hql, groupEccRvConName);
		} else {//编辑时
			hql = "from " + EccGroupRevieCon.class.getName() + " where groupEccRvConId != ? and groupEccRvConName = ?";
			retList = this.retrieve(hql, groupEccRvConId, groupEccRvConName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

