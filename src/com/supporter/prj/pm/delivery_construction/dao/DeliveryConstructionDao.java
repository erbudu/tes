package com.supporter.prj.pm.delivery_construction.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.delivery_construction.entity.DeliveryConstruction;

@Repository
public class DeliveryConstructionDao extends MainDaoSupport < DeliveryConstruction, String >{

	public List<DeliveryConstruction> findPage(JqGrid jqGrid, DeliveryConstruction deliveryConstruction,UserProfile user, Map<String, Object> map) {
		// //列表显示权限
		// String authHql = EIPService.getAuthorityService().getHqlFilter(user,
		// DeliveryConstruction.APP_NAME, AuthConstant.AUTH_OPER_NAME_PAGE);
		// jqGrid.addHqlFilter(authHql);
		if(deliveryConstruction != null){
			// 只获取某项目下的数据
			String prjId = deliveryConstruction.getPrjId();
			if (StringUtils.isNotBlank(prjId)) {
				jqGrid.addHqlFilter(" prjId = ? ", prjId);
			} else {// 判断条件值任意，目的是返回一个空表
				jqGrid.addHqlFilter(" 1 != 1");
			}
			//根据创建人查询
			String createdByName = deliveryConstruction.getCreatedByName();
			if (StringUtils.isNotBlank(createdByName) ) {
				jqGrid.addHqlFilter("createdByName like ? ", "%" + createdByName + "%");
			}
			//状态
			Integer status = deliveryConstruction.getStatus();
			if (status != null) {
				jqGrid.addHqlFilter("status = ? ", status);
			}
		}
		return this.retrievePage(jqGrid);
	}

    public String getMaxNumber() {
		String hql = "select deliveryNo  from " + DeliveryConstruction.class.getName() +" order by createdDate desc";
		List<Object> result = this.find(hql);
		if (result != null && result.size() > 0 && result.get(0) != null) {
			return result.get(0).toString();
		}
		return null;
    }
    
    /**
	 * 数据库中是否存在记录.
	 * @param deliveryId 交付施工ID
	 * @return boolean
	 */
	public boolean existInDB(String deliveryId) {
		String hql = "select count(deliveryId) as recCount from "
				+ DeliveryConstruction.class.getName() + " where deliveryId=?";
		Object obj = this.retrieveFirst(hql, deliveryId);
		if (obj == null) {
			return false;
		} else {
			return (Long) obj > 0;
		}
	}
	

}
