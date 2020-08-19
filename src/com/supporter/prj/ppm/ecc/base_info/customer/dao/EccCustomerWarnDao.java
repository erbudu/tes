package com.supporter.prj.ppm.ecc.base_info.customer.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerWarn;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制客户警告.
 * @author YAN
 * @date 2019-08-16 18:30:30
 * @version V1.0
 */
@Repository
public class EccCustomerWarnDao extends MainDaoSupport<EccCustomerWarn, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccCustomerWarn> findPage(JqGrid jqGrid, EccCustomerWarn eccCustomerWarn) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param warnId
	 * @param warnName
	 * @return
	 */
	public boolean nameIsValid(String warnId,String warnName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(warnId)) {//新建时
			hql = "from " + EccCustomerWarn.class.getName() + " where warnName = ?";
			retList = this.retrieve(hql, warnName);
		} else {//编辑时
			hql = "from " + EccCustomerWarn.class.getName() + " where warnId != ? and warnName = ?";
			retList = this.retrieve(hql, warnId, warnName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

