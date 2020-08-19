package com.supporter.prj.cneec.hr.gpms_interface.person_information.dao;


import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.hr.gpms_interface.HrAppDaoSupport;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonNational;
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
public class PersonNationalDao extends HrAppDaoSupport<PersonNational, String > {
	
	/**
	 * 构造人员的民族的JSON.
	 * @param personNo
	 * @return
	 */
	public String getPersonNationalJosn(String empId){
		StringBuffer josnString= new StringBuffer();
		PersonNational personNational = this.get(empId);
		String ls_PersonNational = CommonUtil.trim(personNational.getPersonNational());
		josnString.append("\"personNational\":\"" +ls_PersonNational.replaceAll("\u0009"," ") + "\"");
		return josnString.toString();
	}
	
}
