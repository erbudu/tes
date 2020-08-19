package com.supporter.prj.ppm.ecc.base_info.owner.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwner;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制客户.
 * @author YAN
 * @date 2019-08-16 18:39:15
 * @version V1.0
 */
@Repository
public class EccOwnerDao extends MainDaoSupport<EccOwner, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccOwner> findPage(JqGrid jqGrid, EccOwner eccOwner) {
		if (eccOwner!=null){
			String eccId = eccOwner.getEccId();
			if (StringUtils.isNotBlank(eccId)){
				jqGrid.addHqlFilter(" eccId = ? ",eccId);
				return this.retrievePage(jqGrid);
			}
		}
		return null;
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param ownerId
	 * @param ownerName
	 * @return
	 */
	public boolean nameIsValid(String ownerId,String ownerName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(ownerId)) {//新建时
			hql = "from " + EccOwner.class.getName() + " where ownerName = ?";
			retList = this.retrieve(hql, ownerName);
		} else {//编辑时
			hql = "from " + EccOwner.class.getName() + " where ownerId != ? and ownerName = ?";
			retList = this.retrieve(hql, ownerId, ownerName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

