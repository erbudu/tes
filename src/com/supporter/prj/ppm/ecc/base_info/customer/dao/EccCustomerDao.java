package com.supporter.prj.ppm.ecc.base_info.customer.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomer;
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
 * @date 2019-08-16 18:30:26
 * @version V1.0
 */
@Repository
public class EccCustomerDao extends MainDaoSupport<EccCustomer, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccCustomer> findPage(JqGrid jqGrid, EccCustomer eccCustomer) {
		if (eccCustomer!=null){
			String eccId = eccCustomer.getEccId();
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
	 * @param customerId
	 * @param customerName
	 * @return
	 */
	public boolean nameIsValid(String customerId,String customerName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(customerId)) {//新建时
			hql = "from " + EccCustomer.class.getName() + " where customerName = ?";
			retList = this.retrieve(hql, customerName);
		} else {//编辑时
			hql = "from " + EccCustomer.class.getName() + " where customerId != ? and customerName = ?";
			retList = this.retrieve(hql, customerId, customerName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

