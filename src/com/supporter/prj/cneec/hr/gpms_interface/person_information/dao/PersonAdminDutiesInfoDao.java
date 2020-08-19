package com.supporter.prj.cneec.hr.gpms_interface.person_information.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.hr.gpms_interface.HrAppDaoSupport;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonAdminDutiesInfo;
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
public class PersonAdminDutiesInfoDao extends HrAppDaoSupport<PersonAdminDutiesInfo, String > {
	
	/**
	 * 构造人员的职务职称的JSON.
	 * @param personNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPersonAdminDutiesInfoJosn(String empId){
		StringBuffer josnString= new StringBuffer();
		List<PersonAdminDutiesInfo> personAdminDutiesInfoList = this.getPersonAdminDutiesInfoList(empId);
		String adminDutiesInfo ="";
		String engageTime = "";
		for(PersonAdminDutiesInfo personAdminDutiesInfo:personAdminDutiesInfoList){
			if(personAdminDutiesInfo.getAdministrativeDuties() != null){
				adminDutiesInfo += personAdminDutiesInfo.getAdministrativeDuties();
			}
			if(personAdminDutiesInfo.getAdministrativeDutiesOther() != null){
				adminDutiesInfo += personAdminDutiesInfo.getAdministrativeDutiesOther();
			}
			if(personAdminDutiesInfo.getStartDate() != null){
				engageTime += CommonUtil.format(personAdminDutiesInfo.getStartDate(),"yyyy-MM-dd");
			}
			if(personAdminDutiesInfo.getEndDate() != null){
				engageTime += "至"+ CommonUtil.format(personAdminDutiesInfo.getEndDate(),"yyyy-MM-dd");
			}
			
		}
		josnString.append("\"adminDuties\":\"" +adminDutiesInfo.replaceAll("\u0009"," ") + "\",");
		josnString.append("\"engageTime\":\"" +engageTime.replaceAll("\u0009"," ") + "\"");
		return josnString.toString();
	}
	
	/**
	 * 查询职务职称列表
	 * 
	 * @param empId
	 * @return List < PersonAdminDutiesInfo >
	 */
	public List<PersonAdminDutiesInfo> getPersonAdminDutiesInfoList(String empId) {
		String hql = "from " + PersonAdminDutiesInfo.class.getName()
				+ " where empId =  ?";
		List<PersonAdminDutiesInfo> entities = this.find(hql,CommonUtil.trim(empId));
		return entities;
	}
	
}
