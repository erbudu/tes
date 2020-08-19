package com.supporter.prj.cneec.hr.gpms_interface.person_information.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonAdminDutiesInfoDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonBasicInfoDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonBirthPlaceDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonCertificationsInfoDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonEducationInfoDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonExperienceInfoDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonNationalDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonPerformanceInfoDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonPhotoInfoDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.dao.PersonTechnicalPositionsInfoDao;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonBasicInfo;
import com.supporter.prj.cneec.hr.gpms_interface.person_information.entity.PersonPhotoInfo;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip.emp.entity.Employee;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Service
 * @Description: 人力人员信息
 * @author tiansen
 * @date 2017-9-16 10:25:07
 * @version V1.0   
 *
 */
@Service
@Transactional(TransManager.APP)
public class PersonInformationService{
	@Autowired
	private PersonBasicInfoDao personBasicInfoDao;
	@Autowired
	private PersonBirthPlaceDao personBirthPlaceDao;
	@Autowired
	private PersonNationalDao personNationalDao;
	@Autowired
	private PersonTechnicalPositionsInfoDao personTechnicalPositionsInfoDao;
	@Autowired
	private PersonAdminDutiesInfoDao personAdminDutiesInfoDao;
	@Autowired
	private PersonPhotoInfoDao personPhotoInfoDao;
	@Autowired
	private PersonEducationInfoDao personEducationInfoDao;
	@Autowired
	private PersonCertificationsInfoDao personCertificationsInfoDao;
	@Autowired
	private PersonExperienceInfoDao personExperienceInfoDao;
	@Autowired
	private PersonPerformanceInfoDao personPerformanceInfoDao;
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public String getPersonInfomation(String personNo) {
			StringBuffer returnJson= new StringBuffer();
//			String personNo="0006";
			if(personNo != null && !personNo.equals("") ){
				PersonBasicInfo personBasicInfo = personBasicInfoDao.get(personNo);
				if(personBasicInfo !=null){
					returnJson.append("{\"personInfoItem\":[");
					returnJson.append("{");
					//人员基本信息
					String personBasicInfoJosn = personBasicInfoDao.getPersonBasicInfoJosn(personBasicInfo);
					returnJson.append(personBasicInfoJosn);
					//人员籍贯
					String personBirthPlaceJosn = personBirthPlaceDao.getPersonBirthPlaceJosn(CommonUtil.trim(personBasicInfo.getEmpId()));
					returnJson.append(",");
					returnJson.append(personBirthPlaceJosn);
					//人员民族
					String personNationalJson = personNationalDao.getPersonNationalJosn(CommonUtil.trim(personBasicInfo.getEmpId()));
					returnJson.append(",");
					returnJson.append(personNationalJson);
					//人员技术职称
					String personTechnicalPositionsInfoJosn = personTechnicalPositionsInfoDao.getPersonTechnicalPositionsInfoJosn(personBasicInfo.getEmpId());
					returnJson.append(",");
					returnJson.append(personTechnicalPositionsInfoJosn);
					//人员职务
					String personAdminDutiesInfoJosn = personAdminDutiesInfoDao.getPersonAdminDutiesInfoJosn(personBasicInfo.getEmpId());
					returnJson.append(",");
					returnJson.append(personAdminDutiesInfoJosn);
					//证书
					String personCertificationsInfoJosn = personCertificationsInfoDao.getPersonCertificationsInfoJosn(personBasicInfo.getEmpId());
					returnJson.append(",");
					returnJson.append(personCertificationsInfoJosn);
					returnJson.append("}");
					returnJson.append("]}");
				}else{
					returnJson.append("{\"personInfoItem\":[");
					returnJson.append("]}");
				}
			}else{
				returnJson.append("{\"personInfoItem\":[");
				returnJson.append("]}");
			}
			return returnJson.toString();
	}
	/**
	 * 返回人员照片。
	 * @param user
	 * @return
	 */
	public PersonPhotoInfo getPersonPhotoInfo(String personNo) {
		StringBuffer returnJson= new StringBuffer();
//		String personNo="0006";
		if(personNo != null && !personNo.equals("") ){
			PersonBasicInfo personBasicInfo = personBasicInfoDao.get(personNo);
		//人员照片
		PersonPhotoInfo  personPhotoInfo = personPhotoInfoDao.get(personBasicInfo.getEmpId());
		System.out.println("personPhotoInfo:" + personPhotoInfo);
		return personPhotoInfo;
		}else{
			return null;
		}
	}
	
	/**
	 * 查看人员的教育背景.
	 * @param moduleId
	 * @return
	 */
	public String getPersonEducationInfo(String personNo) {
			StringBuffer returnJson= new StringBuffer();
//			String personNo="0006";
			if(personNo != null && !personNo.equals("") ){
				PersonBasicInfo personBasicInfo = personBasicInfoDao.get(personNo);
				if(personBasicInfo !=null){
					returnJson.append("{\"personEducationItem\":[");
					returnJson.append(personEducationInfoDao.getPersonEducationInfoJosn(personBasicInfo.getEmpId()));
					returnJson.append("]}");
				}else{
					returnJson.append("");
				}
			}else{
				returnJson.append("");
			}
			return returnJson.toString();
	}
	
	/**
	 * 查看人员的工作简历.
	 * @param moduleId
	 * @return
	 */
	public String getPersonExperienceInfo(String personNo) {
			StringBuffer returnJson= new StringBuffer();
//			String personNo="0006";
			if(personNo != null && !personNo.equals("") ){
				PersonBasicInfo personBasicInfo = personBasicInfoDao.get(personNo);
				if(personBasicInfo !=null){
					returnJson.append("{\"personExperienceInfoItem\":[");
					returnJson.append(personExperienceInfoDao.getPersonExperienceInfoJosn(personBasicInfo.getEmpId()));
					returnJson.append("]}");
				}else{
					returnJson.append("");
				}
			}else{
				returnJson.append("");
			}
			return returnJson.toString();
	}
	
	/**
	 * 查看人员的业绩.
	 * @param moduleId
	 * @return
	 */
	public String getPersonPerformanceInfo(String personNo) {
			StringBuffer returnJson= new StringBuffer();
//			String personNo="0006";
			if(personNo != null && !personNo.equals("") ){
				PersonBasicInfo personBasicInfo = personBasicInfoDao.get(personNo);
				if(personBasicInfo !=null){
					returnJson.append("{\"personPerformanceInfoItem\":[");
					returnJson.append(personPerformanceInfoDao.getPersonPerformanceInfoJosn(personBasicInfo.getEmpId()));
					returnJson.append("]}");
				}else{
					returnJson.append("");
				}
			}else{
				returnJson.append("");
			}
			return returnJson.toString();
	}

}
