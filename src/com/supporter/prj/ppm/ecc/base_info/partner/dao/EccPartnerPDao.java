package com.supporter.prj.ppm.ecc.base_info.partner.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerP;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制项目合伙人合作伙伴.
 * @author YAN
 * @date 2019-08-16 18:34:21
 * @version V1.0
 */
@Repository
public class EccPartnerPDao extends MainDaoSupport<EccPartnerP, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccPartnerP> findPage(JqGrid jqGrid, EccPartnerP eccPartnerP) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param partnerPId
	 * @param partnerPName
	 * @return
	 */
	public boolean nameIsValid(String partnerPId,String partnerPName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(partnerPId)) {//新建时
			hql = "from " + EccPartnerP.class.getName() + " where partnerPName = ?";
			retList = this.retrieve(hql, partnerPName);
		} else {//编辑时
			hql = "from " + EccPartnerP.class.getName() + " where partnerPId != ? and partnerPName = ?";
			retList = this.retrieve(hql, partnerPId, partnerPName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

