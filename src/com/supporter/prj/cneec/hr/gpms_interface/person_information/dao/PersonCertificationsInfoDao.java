package com.supporter.prj.cneec.hr.gpms_interface.person_information.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.hr.gpms_interface.HrAppDaoSupport;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonCertificationsInfo;
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
public class PersonCertificationsInfoDao extends HrAppDaoSupport<PersonCertificationsInfo, String > {
	
	/**
	 * 构造人员的资格证书的JSON.
	 * @param personNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPersonCertificationsInfoJosn(String empId){
		StringBuffer josnString= new StringBuffer();
		List<PersonCertificationsInfo> personCertificationsInfoList = this.getPersonCertificationsInfoList(empId);
		String ls_CertificationsName = "";
		for(PersonCertificationsInfo personCertificationsInfo:personCertificationsInfoList){
			if(personCertificationsInfo != null){
				if(personCertificationsInfo.getCertificationsLevel() != null && !personCertificationsInfo.getCertificationsLevel().equals("")){
					ls_CertificationsName +=personCertificationsInfo.getCertificationsName() + "(" + CommonUtil.trim(personCertificationsInfo.getCertificationsLevel()) + ")";
				}else{
					ls_CertificationsName += personCertificationsInfo.getCertificationsName();
				}
			}
		}
		josnString.append("\"certificationsName\":\"" +ls_CertificationsName.replaceAll("\u0009"," ") + "\"");
		return josnString.toString();
	}
	
	/**
	 * 查询资格证书的列表
	 * 
	 * @param empId
	 * @return List < PersonCertificationsInfo >
	 */
	public List<PersonCertificationsInfo> getPersonCertificationsInfoList(String empId) {
		String hql = "from " + PersonCertificationsInfo.class.getName()
				+ " where empId =  ?";
		List<PersonCertificationsInfo> entities = this.find(hql,CommonUtil.trim(empId));
		return entities;
	}
	
}
