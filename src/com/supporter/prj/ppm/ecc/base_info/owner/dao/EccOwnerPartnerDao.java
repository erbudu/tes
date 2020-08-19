package com.supporter.prj.ppm.ecc.base_info.owner.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwnerPartner;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制客户的合伙人.
 * @author YAN
 * @date 2019-08-16 18:39:17
 * @version V1.0
 */
@Repository
public class EccOwnerPartnerDao extends MainDaoSupport<EccOwnerPartner, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccOwnerPartner> findPage(JqGrid jqGrid, EccOwnerPartner eccOwnerPartner) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param ownerPartnerId
	 * @param ownerPartnerName
	 * @return
	 */
	public boolean nameIsValid(String ownerPartnerId,String ownerPartnerName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(ownerPartnerId)) {//新建时
			hql = "from " + EccOwnerPartner.class.getName() + " where ownerPartnerName = ?";
			retList = this.retrieve(hql, ownerPartnerName);
		} else {//编辑时
			hql = "from " + EccOwnerPartner.class.getName() + " where ownerPartnerId != ? and ownerPartnerName = ?";
			retList = this.retrieve(hql, ownerPartnerId, ownerPartnerName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

