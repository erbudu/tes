package com.supporter.prj.ppm.ecc.base_info.customer.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerFso;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制客户办事处.
 * @author YAN
 * @date 2019-08-16 18:30:28
 * @version V1.0
 */
@Repository
public class EccCustomerFsoDao extends MainDaoSupport<EccCustomerFso, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccCustomerFso> findPage(JqGrid jqGrid, EccCustomerFso eccCustomerFso) {
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
			hql = "from " + EccCustomerFso.class.getName() + " where fsoName = ?";
			retList = this.retrieve(hql, fsoName);
		} else {//编辑时
			hql = "from " + EccCustomerFso.class.getName() + " where fsoId != ? and fsoName = ?";
			retList = this.retrieve(hql, fsoId, fsoName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

