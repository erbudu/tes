package com.supporter.prj.linkworks.oa.maintenance.dao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.maintenance.entity.Maintenance;
import com.supporter.prj.linkworks.oa.maintenance.util.AuthConstant;
import com.supporter.prj.linkworks.oa.maintenance.util.MaintenanceConstant;

/**   
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class MaintenanceDao extends MainDaoSupport < Maintenance, String > {
	
	public String getMaintenanceNo() {
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String year=sdf.format(new Date());
		String hql = "select maintenanceNo from "
				+ Maintenance.class.getName()+" order by maintenance_no desc";
		List<String> list = this.find(hql);
		if(list!=null){
			if (list.size() > 0) {
				if (list.get(0) != null && !list.get(0).equals("")) {
					return list.get(0).toString();
				} else {
					return year+"-000";
				}
			}else{
				return year+"-000";
			}
		}else{
			return year+"-000";
		}
	}

	
	
	/**
	 * 分页查询
	 */
	public List<Maintenance> findPage(UserProfile user,JqGrid jqGrid, Maintenance maintenance) {
		if (maintenance != null) {
			String title = maintenance.getTitle();
			if (StringUtils.isNotBlank(title)) {
				jqGrid
						.addHqlFilter(
								" title like ? or swfName like ? or prjName like ? or createdBy like ? or prjDeptName like ? ",
								"%" + title + "%", "%" + title + "%", "%" + title + "%", "%" + title + "%", "%" + title + "%");
			}
			// 修改内容过滤
			if (maintenance.getModifiedContent() != null) {
				jqGrid.addHqlFilter(" modifiedContentCode= ? ",Long.valueOf(maintenance.getModifiedContent()));
			}
			jqGrid.addSortPropertyDesc("createdDate");
		}
		//权限过滤（判断获取数据范围的权限）
		String authHql="";
		authHql = EIPService.getAuthorityService().getHqlFilter(user,MaintenanceConstant.MODULE_ID,AuthConstant.AUTH_OPER_NAME_AUTHMAINTENANCEOFLIST );
		//System.out.println("authHql==="+authHql);
		jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}



	public List<Maintenance> getMaintenanceList() {
		StringBuffer hql = new StringBuffer("from " + Maintenance.class.getName()
				+ " where 1=1 ");
		List<Maintenance> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}

	
	
	
	
}
