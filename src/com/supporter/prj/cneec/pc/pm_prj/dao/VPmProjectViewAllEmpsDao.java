package com.supporter.prj.cneec.pc.pm_prj.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.pc.pm_prj.entity.VPmProjectViewAllEmps;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;


/**
 * @Title: DAO
 * @Description: 功能模块表
 * @version V1.0
 * 
 */
@Repository
public class VPmProjectViewAllEmpsDao extends MainDaoSupport < VPmProjectViewAllEmps , String > {
	
	/**
	 *查看当前登录人是不是有全部权限
	 * 
	 * @param JqGrid jqGrid,String personId
	 * @return boolean
	 */
	public boolean getIsAllPmProjectAuth(String personId) {

		if (StringUtils.isNotBlank(personId)) {
			String hql = "from "+VPmProjectViewAllEmps.class.getName()+" where memberId = "+personId;
			
			List<VPmProjectViewAllEmps> list =  this.find(hql);
			if(list!=null&&list.size()>0){
				return true;
			}else{
				return false;
			}
		}else{//如果当前人的id为空，直接返回false			
			return false;
		}
	}
	
	
	
	
}
