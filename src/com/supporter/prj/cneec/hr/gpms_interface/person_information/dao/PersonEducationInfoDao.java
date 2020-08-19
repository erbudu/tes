package com.supporter.prj.cneec.hr.gpms_interface.person_information.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.hr.gpms_interface.HrAppDaoSupport;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonEducationInfo;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonNational;
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
public class PersonEducationInfoDao extends HrAppDaoSupport<PersonEducationInfo, String > {
	
	/**
	 * 构造人员的教育经历JSON.
	 * @param personNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPersonEducationInfoJosn(String empId){
		StringBuffer josnString= new StringBuffer();
		List<PersonEducationInfo> personEducationInfoList = this.getPersonEducationInfoList(empId);
		int i = 1;
		for(PersonEducationInfo personEducationInfo:personEducationInfoList){
			josnString.append("{");
			//毕业时间
			String graduationDate = CommonUtil.format(personEducationInfo.getGraduationDate(),"yyyy-MM-dd");
			josnString.append("\"graduationDate\":\"" +  graduationDate.replaceAll("\u0009"," ") + "\",");
			//毕业学校
			String graduatedScholl = personEducationInfo.getGraduatedScholl();
			if(graduatedScholl == null){
				graduatedScholl="";
			}
			josnString.append("\"graduatedScholl\":\"" +  graduatedScholl.replaceAll("\u0009"," ") + "\",");
			//专业
			String specialtyClass = personEducationInfo.getSpecialtyClass();
			if(specialtyClass == null){
				specialtyClass="";
			}
			josnString.append("\"specialtyClass\":\"" +  specialtyClass.replaceAll("\u0009"," ") + "\",");
			//学历
			String educationDesc = personEducationInfo.getEducationDesc();
			if(educationDesc == null){
				educationDesc="";
			}
			josnString.append("\"educationDesc\":\"" +  educationDesc.replaceAll("\u0009"," ") + "\",");
			//学位
			String degreeDesc = personEducationInfo.getDegreeDesc();
			if(degreeDesc == null){
				degreeDesc="";
			}
			josnString.append("\"degreeDesc\":\"" + degreeDesc.replaceAll("\u0009"," ") + "\"");
			if(i == personEducationInfoList.size()){
				josnString.append("}");
			}else{
				josnString.append("},");
			}
			i++;
		}
		return josnString.toString();
	}
	
	/**
	 * 查询人员简历列表
	 * 
	 * @param empId
	 * @return List < PersonTechnicalPositionsInfo >
	 */
	public List<PersonEducationInfo> getPersonEducationInfoList(String empId) {
		String hql = "from " + PersonEducationInfo.class.getName()
				+ " where empId =  ?";
		List<PersonEducationInfo> entities = this.find(hql,CommonUtil.trim(empId));
		return entities;
	}
	
}
