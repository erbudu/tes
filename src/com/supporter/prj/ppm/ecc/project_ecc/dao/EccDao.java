package com.supporter.prj.ppm.ecc.project_ecc.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.project_ecc.entity.Ecc;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制.
 * @author YAN
 * @date 2019-08-16 18:23:05
 * @version V1.0
 */
@Repository
public class EccDao extends MainDaoSupport<Ecc, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<Ecc> findPage(JqGrid jqGrid, Ecc ecc) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param eccId
	 * @param eccName
	 * @return
	 */
	public boolean nameIsValid(String eccId,String eccName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(eccId)) {//新建时
			hql = "from " + Ecc.class.getName() + " where eccName = ?";
			retList = this.retrieve(hql, eccName);
		} else {//编辑时
			hql = "from " + Ecc.class.getName() + " where eccId != ? and eccName = ?";
			retList = this.retrieve(hql, eccId, eccName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

