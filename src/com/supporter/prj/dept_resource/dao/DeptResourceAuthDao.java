package com.supporter.prj.dept_resource.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.entity.DeptResourceAuth;

/**
 * @Title: Entity
 * @Description: 功能模块
 * @author jiaotilei
 * @date 2017-08-08
 * @version V1.0
 * 
 */
@Repository
public class DeptResourceAuthDao extends
		MainDaoSupport<DeptResourceAuth, String> {

	/**
	 * 分页查询
	 */
	public List<DeptResourceAuth> findPage(JqGrid jqGrid,
			DeptResourceAuth deptResourceAuth) {
		if (deptResourceAuth != null) {
			//
			String resourceId = deptResourceAuth.getResourceId();
			if (StringUtils.isNotBlank(resourceId)) {
				jqGrid.addHqlFilter(" resourceId = ? ", resourceId);
			}
			//
			String authType = deptResourceAuth.getAuthType();
			if (StringUtils.isNotBlank(resourceId)) {
				jqGrid.addHqlFilter("authType = ? ", authType);
			}
		}
		return this.retrievePage(jqGrid);
	}

	// 根据主表id和权限类型获取相关权限信息
	public List<Object[]> getByResourceId(String authType, String resourceId) {
		String hql = "select authorizeeType,authorizeeId,canRead,canWrite,canDelete,fullAccess,canNew from "
				+ DeptResourceAuth.class.getName()
				+ " where authType= '"
				+ authType + "' and resourceId= '" + resourceId + "'";
		List<Object[]> list = this.find(hql);
		return list;
	}
	
	// 根据主表id获取DeptResourceAuth列表
	public List<DeptResourceAuth> getListByResourceId(String resourceId) {
		String hql = " from "
				+ DeptResourceAuth.class.getName()
				+ " where resourceId= '" + resourceId + "'";
		List<DeptResourceAuth> list = this.find(hql);
		return list;
	}
	
	/**
	 * 授权类型为资源内容
	 * @param resourceId
	 * @param deptId
	 * @return
	 */
	// 根据主表id获取DeptResourceAuth列表(授权类型为资源内容，授权权限类型为部门，部门id为deptId)
	public List<DeptResourceAuth> getListByResourceIdOfContent(String resourceId,String authorizeeIds,String canTodo) {
		if(canTodo!=null&&!canTodo.equals("")&&authorizeeIds!=null&&!authorizeeIds.equals("")){			
			if(canTodo.equals("canRead")){
				canTodo="canRead ='1' or canWrite ='1' or canDelete ";
			}
			String hql = " from "+ DeptResourceAuth.class.getName()+" where authType='RESOURCE_CONTENT' ";
			if(StringUtils.isNotBlank(resourceId)){
				hql+=" and resourceId= '"+resourceId+"'"; 
			}			
			if(StringUtils.isNotBlank(authorizeeIds)){
				hql+=" and authorizeeId in ("+authorizeeIds+")"; 
			}
			if(StringUtils.isNotBlank(canTodo)){
				hql+=" and ("+canTodo+"='1' or fullAccess='1')"; 
			}
			List<DeptResourceAuth> list = this.find(hql);		
			return list;
		}else{
			return null;
		}
	}
	
	
	
	
	
	
	
	
/*	public List<Object[]> getAuthList(String sql){
		List<Object[]> list=this.find(sql);
		return list;
	}*/
}
