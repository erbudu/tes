package com.supporter.prj.cneec.pc.pm_prj.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.pc.pm_prj.entity.VPmProjectViewDEmps;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;


/**
 * @Title: DAO
 * @Description: 功能模块表
 * @version V1.0
 * 
 */
@Repository
public class VPmProjectViewDeptEmpsDao extends MainDaoSupport < VPmProjectViewDEmps , String > {
	
	/**
	 *查看当前登录人是不是在能看某个部门的项目的人员列表当中
	 * 
	 * @param JqGrid jqGrid,String personId
	 * @return boolean
	 */
	public boolean getIsDeptPmProjectAuth(String personId) {

		if (StringUtils.isNotBlank(personId)) {
//			jqGrid.addHqlFilter(
//				" memberId = ? ", Long.valueOf(personId) );		
//			List<VPcPrjViewDEmps> list =  this.retrievePage(jqGrid);
			
			String hql="from "+VPmProjectViewDEmps.class.getName()+" where memberId = "+personId;
			List<VPmProjectViewDEmps> list =  this.find(hql);
			//System.out.println("部门权限查出的数据条数："+list.size());
			if(list!=null&&list.size()>0){
				return true;
			}else{
				return false;
			}

		}else{//如果当前登录人的id为空，直接返回false
			return false;
		}
	}
	
	
	
	
}
