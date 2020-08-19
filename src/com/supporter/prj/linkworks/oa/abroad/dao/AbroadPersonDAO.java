package com.supporter.prj.linkworks.oa.abroad.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.abroad.entity.AbroadPerson;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Repository
public class AbroadPersonDAO extends MainDaoSupport < AbroadPerson, String >{

	/**
	 * 根据出国审批单号获取人员列表
	 * @param abroadRecordId
	 * @return
	 */
	public List<AbroadPerson> getManagerByAbroadId(String abroadRecordId) {
		String hql = "from " + AbroadPerson.class.getName()
				+ " where abroadRecordId = ? ";
		List<AbroadPerson> managers = this.find(hql, abroadRecordId);
		if (managers == null || managers.size() == 0)
			return null;
		return managers;
	}
	
	/**
	 * 根据出国审批单号获取外部人员列表
	 * @param abroadRecordId
	 * @return
	 */
	public List<AbroadPerson> getForeignByAbroadId(String abroadRecordId) {
		String hql = "from " + AbroadPerson.class.getName()
				+ " where personType = 0 and abroadRecordId = ? ";
		List<AbroadPerson> managers = this.find(hql, abroadRecordId);
		if (managers == null || managers.size() == 0){
			return null;
		}
		return managers;
	}
	
	/**
	 * 根据出国申请id获取出国人员
	 * @param jqGrid
	 * @param ap
	 * @param recordId
	 * @return
	 */
	public List<AbroadPerson> getRecGrid(JqGrid jqGrid,String recordId){
		
			if(StringUtils.isNotBlank(recordId)){
				jqGrid.addHqlFilter( " abroadRecordId like ? ",  "%" + recordId + "%");
			}
			return this.retrievePage(jqGrid);
	}
	
	/**
	 * 根据出国申请id获取出国外部人员
	 * @param jqGrid
	 * @param ap
	 * @param recordId
	 * @return
	 */
	public List<AbroadPerson> getForeignGrid(JqGrid jqGrid,AbroadPerson ap,String recordId){
		
		if (StringUtils.isNotBlank(recordId)) {
			jqGrid.addHqlFilter("abroadRecordId like ?  ", "%" + recordId + "%");
			jqGrid.addHqlFilter("personType = ?  ", 0);
			List<AbroadPerson> list = this.retrievePage(jqGrid);
			List<AbroadPerson> lists = new ArrayList<AbroadPerson>(); 
			for(AbroadPerson abroadPerson:list){
				lists.add(abroadPerson);
			}
			jqGrid.setRows(lists);
			return lists;
		}else{
			return null;
		}
	}
	
	/**
	 * 修改时获取内部员工
	 * @param recordId
	 * @return
	 */
	public List<AbroadPerson> getEmployeesName(String recordId){		
		String hql = "from " + AbroadPerson.class.getName() + " where abroadRecordId = ? and personType = '1' ";
		List<AbroadPerson> list = this.find(hql, recordId);
		return list;
	}
	
	/**
	 * 修改时删除原有内部人员呢
	 * @param recordId
	 * @return
	 */
	public void deleteInner(Abroad abroad){	
		String hql = "from " + AbroadPerson.class.getName() + " where abroadRecordId = ?  ";
		List<AbroadPerson> lists = this.find(hql, abroad.getRecordId());
		this.delete(lists);
	}
}
