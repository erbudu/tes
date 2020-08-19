package com.supporter.prj.pm.delivery_construction.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.delivery_construction.entity.DeliveryDrawings;


@Repository
public class DeliveryDrawingsDao extends MainDaoSupport < DeliveryDrawings, String >{
	
	public List<DeliveryDrawings> findContentPage(UserProfile user, JqGrid jqGrid,DeliveryDrawings content,String deliveryId) {
		if(content != null){						
			if(StringUtils.isNotBlank(deliveryId)){
				jqGrid.addHqlFilter("deliveryId = '"+deliveryId +"'");
				return this.retrievePage(jqGrid);
			}
		}
		return null;
	}
	
	public List<DeliveryDrawings> getContentList(String id){
		String hql = "from DeliveryDrawings where deliveryId = '" + id + "'";
		List<DeliveryDrawings> list = find(hql);
		if(list != null && list.size() > 0){
			return list;
		}else{
			return null;	
		} 	
	}
	
	public List<DeliveryDrawings> getContentListByDrawingId(String drawingId){
		String hql = "from DeliveryDrawings where drawingId = '" + drawingId + "'";
		List<DeliveryDrawings> list = find(hql);
		if(list != null && list.size() > 0){
			return list;
		}else{
			return null;	
		} 	
	}
	
}
