package com.supporter.prj.cneec.hr.gpms_interface.person_information.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.hr.gpms_interface.HrAppDaoSupport;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonPerformanceInfo;
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
public class PersonPerformanceInfoDao extends HrAppDaoSupport<PersonPerformanceInfo, String > {
	
	/**
	 * 构造人员的业绩.
	 * @param personNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPersonPerformanceInfoJosn(String empId){
		StringBuffer josnString= new StringBuffer();
		List<PersonPerformanceInfo> personPerformanceInfoList = this.getPersonPerformanceInfoList(empId);
		int i = 1;
		for(PersonPerformanceInfo personPerformanceInfo:personPerformanceInfoList){
			josnString.append("{");
			//起止年月
			String execuDate = CommonUtil.trim(personPerformanceInfo.getExecuDate());
			if(execuDate == null ){
				execuDate = "";
			}
			josnString.append("\"execuDate\":\"" +  execuDate.replaceAll("\u0009"," ") + "\",");
			//项目名称
			String prjName = personPerformanceInfo.getPrjName();
			if(prjName == null){
				prjName="";
			}
			josnString.append("\"prjName\":\"" +  prjName.replaceAll("\u0009"," ") + "\",");
			//项目角色
			String prjRole = personPerformanceInfo.getPrjRole();
			if(prjRole == null){
				prjRole="";
			}
			josnString.append("\"prjRole\":\"" +  prjRole.replaceAll("\u0009"," ") + "\"");
			if(i == personPerformanceInfoList.size()){
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
	 * @return List < PersonPerformanceInfo >
	 */
	public List<PersonPerformanceInfo> getPersonPerformanceInfoList(String empId) {
		String hql = "from " + PersonPerformanceInfo.class.getName()
				+ " where empId =  ?";
		List<PersonPerformanceInfo> entities = this.find(hql,CommonUtil.trim(empId));
		return entities;
	}
	
}
