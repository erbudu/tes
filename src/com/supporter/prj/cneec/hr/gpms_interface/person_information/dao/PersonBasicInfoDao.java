package com.supporter.prj.cneec.hr.gpms_interface.person_information.dao;


import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.hr.gpms_interface.HrAppDaoSupport;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonBasicInfo;
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
public class PersonBasicInfoDao extends HrAppDaoSupport<PersonBasicInfo, String > {
	/**
	 * 获取人员基本信息
	 * @param personNo
	 * @return
	 */
	public PersonBasicInfo getPersonBasicInfo(String personNo){
		return this.get(personNo);
	}
	
	
	/**
	 * 构造人员的基本信息的JSON.
	 * @param personNo
	 * @return
	 */
	public String getPersonBasicInfoJosn(PersonBasicInfo personBasicInfo){
		StringBuffer josnString= new StringBuffer();
		josnString.append("\"empId\":\"" + CommonUtil.trim(personBasicInfo.getEmpId()) + "\",");
		josnString.append("\"empName\":\"" + CommonUtil.trim(personBasicInfo.getEmpName()).replaceAll("\u0009"," ") + "\",");
		josnString.append("\"hrId\":\"" + CommonUtil.trim(personBasicInfo.getHrId()).replaceAll("\u0009"," ") + "\",");
		josnString.append("\"hrDeptName\":\"" + CommonUtil.trim(personBasicInfo.getHrDeptName()).replaceAll("\u0009"," ") + "\",");
		josnString.append("\"bloodType\":\"" + CommonUtil.trim(personBasicInfo.getBloodType()).replaceAll("\u0009"," ") + "\",");
		josnString.append("\"politicalLandscape\":\"" + CommonUtil.trim(personBasicInfo.getPoliticalLandscape()).replaceAll("\u0009"," ") + "\",");
		josnString.append("\"sex\":\"" + CommonUtil.trim(personBasicInfo.getSex()).replaceAll("\u0009"," ") + "\",");
		josnString.append("\"birthDate\":\"" + CommonUtil.format(personBasicInfo.getBirthDate(),"yyyy-MM-dd").replaceAll("\u0009"," ") + "\"");
		
		return josnString.toString();
	}
	
}
