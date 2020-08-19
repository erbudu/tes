package com.supporter.prj.cneec.hr.gpms_interface.person_information.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.hr.gpms_interface.HrAppDaoSupport;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonBirthPlace;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonBirthPlaceCode;
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
public class PersonBirthPlaceDao extends HrAppDaoSupport<PersonBirthPlace, String > {
	@Autowired
	PersonBirthPlaceCodeDao personBirthPlaceCodeDao;
	/**
	 * 构造人员的籍贯的JSON.
	 * @param personNo
	 * @return
	 */
	public String getPersonBirthPlaceJosn(String empId){
		StringBuffer josnString= new StringBuffer();
		PersonBirthPlace personBirthPlace = this.get(empId);
		String ls_BirthPlace = "";//籍贯
		String ls_RealBirthPlace = "";//出生地
		if(CommonUtil.isNumber(CommonUtil.trim(personBirthPlace.getBirthPlace()))){
			PersonBirthPlaceCode lpbpc_PersonBirthPlaceCode = personBirthPlaceCodeDao.get(CommonUtil.trim(personBirthPlace.getBirthPlace()));
			ls_BirthPlace =  lpbpc_PersonBirthPlaceCode.getBirthPlace();
		}else{
			ls_BirthPlace = personBirthPlace.getBirthPlace() != null ? personBirthPlace.getBirthPlace() : "无";
		}
		if(CommonUtil.isNumber(CommonUtil.trim(personBirthPlace.getRealBirthplace()))){
			PersonBirthPlaceCode lpbpc_PersonRealBirthPlaceCode = personBirthPlaceCodeDao.get(CommonUtil.trim(personBirthPlace.getRealBirthplace()));
			ls_RealBirthPlace =  lpbpc_PersonRealBirthPlaceCode.getBirthPlace();
		}else{
			ls_RealBirthPlace = personBirthPlace.getRealBirthplace() != null ? personBirthPlace.getRealBirthplace() : "无";
		}
		josnString.append("\"birthPlace\":\"" +ls_BirthPlace.replaceAll("\u0009"," ") + "\"");
		josnString.append(",");
		josnString.append("\"realBirthPlace\":\"" +ls_RealBirthPlace.replaceAll("\u0009"," ") + "\"");
		return josnString.toString();
	}
}
