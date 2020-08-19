package com.supporter.prj.ppm.ecc.base_info.product.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.base_info.product.entity.EccProduct;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制产品.
 * @author YAN
 * @date 2019-08-19 15:22:21
 * @version V1.0
 */
@Repository
public class EccProductDao extends MainDaoSupport<EccProduct, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccProduct> findPage(JqGrid jqGrid, EccProduct eccProduct) {
		if (eccProduct!=null){
			String eccId = eccProduct.getEccId();
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
	 * @param productId
	 * @param productName
	 * @return
	 */
	public boolean nameIsValid(String productId,String productName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(productId)) {//新建时
			hql = "from " + EccProduct.class.getName() + " where productName = ?";
			retList = this.retrieve(hql, productName);
		} else {//编辑时
			hql = "from " + EccProduct.class.getName() + " where productId != ? and productName = ?";
			retList = this.retrieve(hql, productId, productName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

