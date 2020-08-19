package com.supporter.prj.ppm.ecc.base_info.partner.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerFso;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制项目合同伙伴办事处.
 * @author YAN
 * @date 2019-08-16 18:34:19
 * @version V1.0
 */
@Repository
public class EccPartnerFsoDao extends MainDaoSupport<EccPartnerFso, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccPartnerFso> findPage(JqGrid jqGrid, EccPartnerFso eccPartnerFso) {
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
			hql = "from " + EccPartnerFso.class.getName() + " where fsoName = ?";
			retList = this.retrieve(hql, fsoName);
		} else {//编辑时
			hql = "from " + EccPartnerFso.class.getName() + " where fsoId != ? and fsoName = ?";
			retList = this.retrieve(hql, fsoId, fsoName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

