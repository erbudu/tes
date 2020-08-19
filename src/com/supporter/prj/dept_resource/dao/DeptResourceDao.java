package com.supporter.prj.dept_resource.dao;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.entity.DeptResource;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class DeptResourceDao extends MainDaoSupport<DeptResource, String> {

	public Integer getCount() {
		String hql = "select max(displayOrder) from "
				+ DeptResource.class.getName();
		List<Long> list = this.find(hql);
		if (list.size() > 0) {
			if (list.get(0) != null && !list.get(0).equals("")) {
				return list.get(0).intValue();
			} else {
				return Integer.valueOf(0);
			}
		}
		return null;
	}

	/**
	 * 分页查询
	 */
	public List<DeptResource> findPage(JqGrid jqGrid, DeptResource deptResource) {
		
		if (deptResource != null) {
			String resourceName = deptResource.getResourceName();
			if (StringUtils.isNotBlank(resourceName)) {
				jqGrid.addHqlFilter(
						" resourceName like ? or createdBy like ?  ", "%"
								+ resourceName + "%", "%" + resourceName + "%");
			}
			// 部门过滤
			if (deptResource.getDeptId() != null) {
				jqGrid.addHqlFilter(" deptId= ? ", deptResource.getDeptId());
			}
			//按照创建时间倒叙排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		return this.retrievePage(jqGrid);
	}

	// 获取资源类型为公告的部门资源(公司公告菜单用到)----------171127（与部门资源管理授权联系起来之前）
//	public List<Object[]> findDeptResourceOfBulletin() {
//		String hql = "select resourceId,resourceName from "
//				+ DeptResource.class.getName()+" where resourceTypeCode='BULLETIN'";		
//		List<Object[]> list = this.find(hql);
//		return list;
//	}
	
	
	// 获取资源类型为公告的部门资源(公司公告菜单用到)----------171128（与部门资源管理授权联系起来之前）
	public List<DeptResource> findDeptResourceOfBulletin() {
		String hql = "from "
				+ DeptResource.class.getName()+" where resourceTypeCode='BULLETIN'";		
		List<DeptResource> list = this.find(hql);
		return list;
	}
	
	
	// 获取资源类型为文章栏的部门资源(首页文章栏菜单用到)
	public List<Object[]> findDeptResourceOfArticleOfTop() {
		String hql = "select resourceId,resourceName from "
				+ DeptResource.class.getName()+" where resourceTypeCode='ARTICLE'";
		List<Object[]> list = this.find(hql);
		return list;
	}
	
	// 获取资源类型为文章栏的部门资源(文章栏菜单用到)
	public List<DeptResource> findDeptResourceOfArticle() {
		String hql = "from "
				+ DeptResource.class.getName()+" where resourceTypeCode='ARTICLE'";
		List<DeptResource> list = this.find(hql);
		return list;
	}
	
	
	// 根据部门资源id获取部门Id和资源名称(公司公告菜单用到)
	public Object[] getDeptIdAndResourceName(String resourceId) {
		String hql = "select resourceName,deptId from "
				+ DeptResource.class.getName()+" where resourceId='"+resourceId+"'";
		List<Object[]> list = this.find(hql);
		if (list == null || list.size() == 0)return null;
		return list.get(0);
	}
	
	//根据资源名称获取list（公司公告菜单用到）
	public List<DeptResource> getListByResourceName(String resourceName) {
		String hql = "from "+ DeptResource.class.getName()+" where resourceName='"+resourceName+"'";
		List<DeptResource> list = this.find(hql);
		return list;
	}
	
	
	
	// 根据管理员Id获取部门资源的Id
	public List<Object> getDeptResourceIdByManagerId(String managerId) {
		String hql = "select resourceId  from "
				+ DeptResource.class.getName()+" where managerId='"+managerId+"'";
		List<Object> list = this.find(hql);		
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//------------------------------------------------------------------------------------------
	/**
	 * 根据资源分类获取资源ID.
	 * @param typeCtblItemId
	 * @return
	 */
	public String getDeptResourceIdByItemId(String typeCtblItemId){
		typeCtblItemId = CommonUtil.trim(typeCtblItemId);
		if (typeCtblItemId.length() == 0)return "";
		List < DeptResource > list = this.findBy("typeCtblItemId", typeCtblItemId);
		if (list == null || list.size() == 0)return "";
		return list.get(0).getResourceId();
	}
	
	
	
	
	
	
    /**
     * 获取指定类型的资源列表.
     * @param as_TypeCode
     * @param adept_D 部门实例
     * @return
     */
/*    public List < DeptResource > getResourceList(String as_TypeCode, String deptId) {
        String ls_SQL = "select * from com_dept_resource where 1 = 1 ";     
        
        if (deptId.equals("")) ls_SQL += " and dept_id = '" + deptId + "'";
        
        String ls_TypeCode = CommonUtil.trim(as_TypeCode);
        if (ls_TypeCode.length() > 0) ls_SQL += " and resource_type_code = '" + ls_TypeCode + "'";  
         
        ls_SQL += " order by display_order"; 
        
        List < DeptResource > list = this.find(ls_SQL);      
        return list;
    }*/
	
	
	
    /**
     * 获取可以以指定方式访问的资源定义的列表.<br>
     * 如下因素将会被考虑：<br>
     * 1、当前用户所属用户组被设置为相应资源类型的“拥有管理权限的用户组”；<br>
     * 2、当前用户正好就是资源定义的“资源管理员”;<br>
     * 3、当前用户位于资源授权所指定的用户组、部门、岗位、人员范围中.
     * @param ausrprf_U 当前用户
     * @param as_AccessMode 访问模式, 请参照AccessMode的相应常量.
     * @param adrt_Type 资源类型
     * @param adept_D 所属的部门
     * @param as_AuthType 对资源内容进行访问还是对资源定义进行访问，参照DeptResourceAuth.RESOURCE_CONTENT/DEFINETION
     * @return
     */
/*    public List<DeptResource> getAccessibleResourceList(String ls_SQL) {      
        List < DeptResource > list = this.find(ls_SQL);       
        return list;
    }*/
	
    
    
    /**
     * 判断是否可以以指定方式访问指定资源定义实例.
     * @param ausrprf_U 用户
     * @param as_AccessMode 访问模式, 请参照AccessMode的相应常量.
     * @param adr_Resource 资源定义实例.
     * @return
     */
/*    public boolean canAccessResourceDef(String ls_SQL) {
        List < DeptResource > list = this.find(ls_SQL); 
        if(list!=null&&list.size()>0){
        	return true;
        }else{
        	return false;       	
        }
        
    }*/
    
    /**
     * 判断是否可以以指定方式访问指定资源的内容.
     * @param ausrprf_U 用户
     * @param as_AccessMode 访问模式, 请参照AccessMode的相应常量.
     * @param adr_Resource 资源定义实例.
     * @return
     */
/*    public boolean canAccessResourceContent(String ls_SQL) {
    	List < DeptResource > list = this.find(ls_SQL);
    	 if(list!=null&&list.size()>0){
         	return true;
         }else{
         	return false;       	
         }
    } */
	
	
}
