package com.supporter.prj.ppm.ecc.base_info.customer.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerPartner;
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
 * @date 2019-08-16 18:30:29
 * @version V1.0
 */
@Repository
public class EccCustomerPartnerDao extends MainDaoSupport<EccCustomerPartner, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccCustomerPartner> findPage(JqGrid jqGrid, EccCustomerPartner eccCustomerPartner) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param customerId
	 * @param customerName
	 * @return
	 */
	public boolean nameIsValid(String customerId,String customerName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(customerId)) {//新建时
			hql = "from " + EccCustomerPartner.class.getName() + " where customerName = ?";
			retList = this.retrieve(hql, customerName);
		} else {//编辑时
			hql = "from " + EccCustomerPartner.class.getName() + " where customerId != ? and customerName = ?";
			retList = this.retrieve(hql, customerId, customerName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

