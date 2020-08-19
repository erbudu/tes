package com.supporter.prj.ppm.ecc.base_info.partner.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartner;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制项目合作伙伴.
 * @author YAN
 * @date 2019-08-16 18:34:18
 * @version V1.0
 */
@Repository
public class EccPartnerDao extends MainDaoSupport<EccPartner, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccPartner> findPage(JqGrid jqGrid, EccPartner eccPartner) {
		if (eccPartner!=null){
			String eccId = eccPartner.getEccId();
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
	 * @param partnerId
	 * @param partnerName
	 * @return
	 */
	public boolean nameIsValid(String partnerId,String partnerName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(partnerId)) {//新建时
			hql = "from " + EccPartner.class.getName() + " where partnerName = ?";
			retList = this.retrieve(hql, partnerName);
		} else {//编辑时
			hql = "from " + EccPartner.class.getName() + " where partnerId != ? and partnerName = ?";
			retList = this.retrieve(hql, partnerId, partnerName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

