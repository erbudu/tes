package com.supporter.prj.ppm.ecc.base_info.owner.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwnerFso;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制主办事处.
 * @author YAN
 * @date 2019-08-16 18:39:16
 * @version V1.0
 */
@Repository
public class EccOwnerFsoDao extends MainDaoSupport<EccOwnerFso, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccOwnerFso> findPage(JqGrid jqGrid, EccOwnerFso eccOwnerFso) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param fsoId
	 * @param fsoName
	 * @return
	 */
	public boolean nameIsValid(String fsoId,String fsoName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(fsoId)) {//新建时
			hql = "from " + EccOwnerFso.class.getName() + " where fsoName = ?";
			retList = this.retrieve(hql, fsoName);
		} else {//编辑时
			hql = "from " + EccOwnerFso.class.getName() + " where fsoId != ? and fsoName = ?";
			retList = this.retrieve(hql, fsoId, fsoName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

