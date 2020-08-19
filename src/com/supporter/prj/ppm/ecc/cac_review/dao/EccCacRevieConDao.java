package com.supporter.prj.ppm.ecc.cac_review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacRevieCon;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制委员会评审结论.
 * @author YAN
 * @date 2019-08-16 18:45:23
 * @version V1.0
 */
@Repository
public class EccCacRevieConDao extends MainDaoSupport<EccCacRevieCon, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccCacRevieCon> findPage(JqGrid jqGrid, EccCacRevieCon eccCacRevieCon) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param cacEccRvConId
	 * @param cacEccRvConName
	 * @return
	 */
	public boolean nameIsValid(String cacEccRvConId,String cacEccRvConName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(cacEccRvConId)) {//新建时
			hql = "from " + EccCacRevieCon.class.getName() + " where cacEccRvConName = ?";
			retList = this.retrieve(hql, cacEccRvConName);
		} else {//编辑时
			hql = "from " + EccCacRevieCon.class.getName() + " where cacEccRvConId != ? and cacEccRvConName = ?";
			retList = this.retrieve(hql, cacEccRvConId, cacEccRvConName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

