package com.supporter.prj.linkworks.oa.netin.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetin;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetinPerson;

@Repository
public class OaNetinPersonDao extends MainDaoSupport<OaNetinPerson, String> {
	
	/**
	 * 获取人员列表
	 * @param jqGrid
	 * @param netinId
	 * @return
	 */
	public List<OaNetinPerson> findPage(JqGrid jqGrid, String netinId){
		if (StringUtils.isNotBlank(netinId)){
			//根据入网申请单id获取人员
			jqGrid.addHqlFilter(" netinId = ? ", netinId);
		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 删除入网申请下所有人员
	 * @param netin
	 */
	public void deletedInner(String netinId){
		List<OaNetinPerson> perosnList = getNetinPerosnListByNetinId(netinId);
		if (perosnList != null){
			this.delete(perosnList);
		}
	}
	
	/**
	 * 根据入网id获取人员
	 * @param netinId
	 * @return
	 */
	public List<OaNetinPerson> getNetinPerosnListByNetinId(String netinId){
		String hql = " from " + OaNetinPerson.class.getName() + " where netinId = ? ";
		List<OaNetinPerson> personList = this.find(hql, netinId);
		if (personList.size() == 0 || personList == null){
			return null;
		}
		return personList;
	}
	
	/**
	 * 获取维护单下离退休职工人员
	 * @param netinId
	 * @return
	 */
	public List<OaNetinPerson> getRetirementList(String netinId){
		if (StringUtils.isNotBlank(netinId)){
			String hql = " from " + OaNetinPerson.class.getName() + " where netinId = ? and userType = '离退休职工' ";
			List<OaNetinPerson> personList = this.find(hql, netinId);
			if (personList != null){
				return personList;
			}
		}
		return null;
	}
	
	/**
	 * 获取维护单下外聘人员
	 * @param netinId
	 * @return
	 */
	public List<OaNetinPerson> getOutsidePersonList(String netinId){
		if (StringUtils.isNotBlank(netinId)){
			String hql = " from " + OaNetinPerson.class.getName() + " where netinId = ? and userType = '外聘' ";
			List<OaNetinPerson> personList = this.find(hql, netinId);
			if (personList != null){
				return personList;
			}
		}
		return null;
	}
}
