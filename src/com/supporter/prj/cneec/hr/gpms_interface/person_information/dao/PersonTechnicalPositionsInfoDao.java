package com.supporter.prj.cneec.hr.gpms_interface.person_information.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.hr.gpms_interface.HrAppDaoSupport;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonTechnicalPositionsInfo;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author tiansen
 * @date 2017-09-16 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class PersonTechnicalPositionsInfoDao extends HrAppDaoSupport<PersonTechnicalPositionsInfo, String > {
	
	/**
	 * 构造人员的技术职称的JSON.
	 * @param personNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPersonTechnicalPositionsInfoJosn(String empId){
		StringBuffer josnString= new StringBuffer();
		List<PersonTechnicalPositionsInfo> personTechnicalPositionsInfoList = this.getPersonTechnicalPositionsInfoList(empId);
		String technicalPositions ="";
		for(PersonTechnicalPositionsInfo PersonTechnicalPositionsInfo:personTechnicalPositionsInfoList){
			if(!(PersonTechnicalPositionsInfo.getTechnicalPositions() == null)){
				technicalPositions += PersonTechnicalPositionsInfo.getTechnicalPositions();
			}
		}
		josnString.append("\"technicalPositions\":\"" +technicalPositions.replaceAll("\u0009"," ") + "\"");
		return josnString.toString();
	}
	
	/**
	 * 查询技术职称列表
	 * 
	 * @param empId
	 * @return List < PersonTechnicalPositionsInfo >
	 */
	public List<PersonTechnicalPositionsInfo> getPersonTechnicalPositionsInfoList(String empId) {
		String hql = "from " + PersonTechnicalPositionsInfo.class.getName()
				+ " where empId =  ?";
		List<PersonTechnicalPositionsInfo> entities = this.find(hql,CommonUtil.trim(empId));
		return entities;
	}
	
}
