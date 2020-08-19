package com.supporter.prj.ppm.ecc.wg_review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.wg_review.entity.EccWgRevieCon;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制工作组评审结论.
 * @author YAN
 * @date 2019-08-16 18:44:49
 * @version V1.0
 */
@Repository
public class EccWgRevieConDao extends MainDaoSupport<EccWgRevieCon, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccWgRevieCon> findPage(JqGrid jqGrid, EccWgRevieCon eccWgRevieCon) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param wgEccRvConId
	 * @param wgEccRvConName
	 * @return
	 */
	public boolean nameIsValid(String wgEccRvConId,String wgEccRvConName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(wgEccRvConId)) {//新建时
			hql = "from " + EccWgRevieCon.class.getName() + " where wgEccRvConName = ?";
			retList = this.retrieve(hql, wgEccRvConName);
		} else {//编辑时
			hql = "from " + EccWgRevieCon.class.getName() + " where wgEccRvConId != ? and wgEccRvConName = ?";
			retList = this.retrieve(hql, wgEccRvConId, wgEccRvConName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

