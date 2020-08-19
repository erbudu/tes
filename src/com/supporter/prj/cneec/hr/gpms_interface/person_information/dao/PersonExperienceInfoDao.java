package com.supporter.prj.cneec.hr.gpms_interface.person_information.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.hr.gpms_interface.HrAppDaoSupport;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonExperienceInfo;
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
public class PersonExperienceInfoDao extends HrAppDaoSupport<PersonExperienceInfo, String > {
	
	/**
	 * 构造人员的工作简历JSON.
	 * @param personNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPersonExperienceInfoJosn(String empId){
		StringBuffer josnString= new StringBuffer();
		List<PersonExperienceInfo> personExperienceInfoList = this.getPersonExperienceInfoList(empId);
		int i = 1;
		for(PersonExperienceInfo personExperienceInfo:personExperienceInfoList){
			josnString.append("{");
			//起止年月
			String startDate = CommonUtil.format(personExperienceInfo.getStartDate(),"yyyy-MM-dd");
			String endDate = CommonUtil.format(personExperienceInfo.getEndDate(),"yyyy-MM-dd");
			if(startDate == null ){
				startDate = "";
			}
			if(endDate == null ){
				endDate = "";
			}
			String startEndDate = startDate + "至" + endDate;
			josnString.append("\"startEndDate\":\"" +  startEndDate.replaceAll("\u0009"," ") + "\",");
			//工作单位
			String workPlace = personExperienceInfo.getWorkPlace();
			if(workPlace == null){
				workPlace="";
			}
			josnString.append("\"workPlace\":\"" +  workPlace.replaceAll("\u0009"," ") + "\",");
			//职务
			String workPost = personExperienceInfo.getWorkPost();
			if(workPost == null){
				workPost="";
			}
			josnString.append("\"workPost\":\"" +  workPost.replaceAll("\u0009"," ") + "\"");
			if(i == personExperienceInfoList.size()){
				josnString.append("}");
			}else{
				josnString.append("},");
			}
			i++;
		}
		return josnString.toString();
	}
	
	/**
	 * 查询人员工作简历列表
	 * 
	 * @param empId
	 * @return List < PersonExperienceInfo >
	 */
	public List<PersonExperienceInfo> getPersonExperienceInfoList(String empId) {
		String hql = "from " + PersonExperienceInfo.class.getName()
				+ " where empId =  ?";
		List<PersonExperienceInfo> entities = this.find(hql,CommonUtil.trim(empId));
		return entities;
	}
	
}
