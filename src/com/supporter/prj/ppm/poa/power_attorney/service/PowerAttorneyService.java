package com.supporter.prj.ppm.poa.power_attorney.service;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.sql.rowset.serial.SerialClob;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.runqian.report4.model.ReportDefine;
import com.runqian.report4.model.engine.ExtCellSet;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.usermodel.Engine;
import com.runqian.report4.usermodel.IReport;
import com.runqian.report4.util.ReportUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.hibernate.HibernateUtil;
import com.supporter.prj.ppm.poa.power_attorney.dao.PowerAttorneyContentDao;
import com.supporter.prj.ppm.poa.power_attorney.dao.PowerAttorneyDao;
import com.supporter.prj.ppm.poa.power_attorney.dao.PowerAttorneyPersonDao;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorney;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorneyContent;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorneyPerson;
import com.supporter.prj.eip.online_user.util.ExportUtils;
import com.supporter.util.CommonUtil;


/**
 *  Class Name: PowerAttorneyService.java
 *  Description: 
 *  @author GuoXiansheng  DateTime 2019年12月6日 上午10:01:54   
 *  @version 1.0
 */
@Service
public class PowerAttorneyService {

	@Autowired
	private PowerAttorneyDao powerAttorneyDao;
	@Autowired
	private PowerAttorneyContentDao powerAttorneyContentDao;
	@Autowired
	private PowerAttorneyPersonDao powerAttorneyPersonDao;

	/**
	 * 保存人员从表信息
	 * @param user
	 * @param powerAttorneyPerson
	 * @param valueMap
	 * @return
	 */
	public PowerAttorneyPerson saveOrUpdatePerson(UserProfile user, PowerAttorneyPerson powerAttorneyPerson, Map< String, Object > valueMap) {
		PowerAttorneyPerson ret = null;
		if(StringUtils.isBlank(powerAttorneyPerson.getLaPersonId())) {//人员从表新建
			powerAttorneyPerson.setLaPersonId(com.supporter.util.UUIDHex.newId());		
			powerAttorneyPersonDao.save(powerAttorneyPerson);
			ret=powerAttorneyPerson;
		}else{//人员从表编辑
			powerAttorneyPersonDao.update(powerAttorneyPerson);			
			ret=powerAttorneyPerson;
		}
		return  ret;
	}
	/**
	 * 分页表格展示被授权人.
	 * @param jqGrid
	 * @param user
	 * @param laId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PowerAttorneyPerson> getPersonGrid(UserProfile user, JqGrid jqGrid, String laId) {
		List<PowerAttorneyPerson> list = powerAttorneyPersonDao.getPersonGrid(jqGrid, laId);	
		jqGrid.setRows(list);
		return list;
	}
	/**
	 * 删除从表数据
	 * @param user
	 * @param laPersonId
	 */
	public void deletePerson(UserProfile user, String laPersonId) {
		if (StringUtils.isNotBlank(laPersonId)) {
			PowerAttorneyPerson person = this.powerAttorneyPersonDao.get(laPersonId);
			if (person != null) {
				this.powerAttorneyPersonDao.delete(laPersonId);
			}
		}
	}
	/**
	 * 保存或更新（主表）
	 * @param user
	 * @param powerAttorney
	 * @param valueMap
	 * @return
	 */
	public PowerAttorney saveOrUpdate(UserProfile user, PowerAttorney powerAttorney, Map< String, Object > valueMap) {
		PowerAttorney PowerAttorneyOne = this.powerAttorneyDao.selectPowerAttorneyByLaId(powerAttorney.getLaId());
		PowerAttorney ret = null;
		if(PowerAttorneyOne == null) { //save 新建
			ret = new PowerAttorney();
			PowerAttorneyContent powerAttorneyContent = new PowerAttorneyContent();
			ret.setLaId(powerAttorney.getLaId());
			ret.setPrjId(powerAttorney.getPrjId());
			ret.setLaBusinessTypeId(powerAttorney.getLaBusinessTypeId());
			ret.setLaBusinessType(powerAttorney.getLaBusinessType());
			ret.setLaType(powerAttorney.getLaType());
			ret.setLaTypeId(powerAttorney.getLaTypeId());
			ret.setLaCopyies(powerAttorney.getLaCopyies());
			ret.setProcStatus(PowerAttorney.DRAFT);
			ret.setLaEffectiveDate(powerAttorney.getLaEffectiveDate());
			ret.setLaExpirationDate(powerAttorney.getLaExpirationDate());
			ret.setBusinessUUID(powerAttorney.getBusinessUUID());
			ret.setCreatedBy(user.getName());
			ret.setCreatedById(user.getPersonId());
			ret.setModifiedBy(user.getName());
			ret.setModifiedById(user.getPersonId());
			ret.setHasSealDocument(PowerAttorney.INITIAL);
			ret.setHasEnclosure(powerAttorney.getHasEnclosure());
			if(user.getDept() != null) {
				ret.setDeptId(user.getDeptId());
				ret.setDeptName(user.getDept().getName());
				ret.setModifiedDeptId(user.getDeptId());
				ret.setModifiedDeptName(user.getDept().getName());
			}
			
			List<PowerAttorney> PowerAttorneyAll = this.powerAttorneyDao.selectPowerAttorneyAll();
			Calendar date = Calendar.getInstance();
			int sysYear = Integer.parseInt(String.valueOf(date.get(Calendar.YEAR)));
			List<Integer> number = new ArrayList<Integer>();
			String strNum = ""; 
			if(PowerAttorneyAll.size() > 0) {
				for (int i = 0; i < PowerAttorneyAll.size(); i++) {
					if(StringUtils.isNotBlank(PowerAttorneyAll.get(i).getLaNo())){
						String laNo = PowerAttorneyAll.get(i).getLaNo();
						int str_year = Integer.parseInt(laNo.substring(7, 11));
						int str_num = Integer.parseInt(laNo.substring(13, 17));
						if(str_year == sysYear) {
							number.add(str_num);
						}
					}
				}
				if(number.size() >0) {
					int num = Collections.max(number) + 1;
					strNum = String.format("%04d", num);
				}else {
					strNum = "0001";
				}
			} else {
				strNum = "0001";
			}
			ret.setLaNo("中电项目授字【"+sysYear+"】第"+strNum+"号");
			
			Date sysDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateNowStr = sdf.format(sysDate);
			Date createdDate = null;
			try {
				createdDate = sdf.parse(dateNowStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ret.setCreatedDate(createdDate);
			ret.setModifiedDate(createdDate);
			this.powerAttorneyDao.save(ret);
			String enclosureName = this.setEnclosureNameData(powerAttorney.getLaId());
			ret.setEnclosureName(enclosureName);
			this.powerAttorneyDao.update(ret);
			powerAttorneyContent.setLaId(powerAttorney.getLaId());
			try {
				powerAttorneyContent.setLaReasonC(new SerialClob(powerAttorney.getLaReasonC().toCharArray()));
				powerAttorneyContent.setLaReasonE(new SerialClob(powerAttorney.getLaReasonE().toCharArray()));
				powerAttorneyContent.setLaDesc(new SerialClob(powerAttorney.getLaDesc().toCharArray()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.powerAttorneyContentDao.save(powerAttorneyContent);
		} else {
			PowerAttorney powerAttorneyUpdate = PowerAttorneyOne;
			ret = powerAttorneyUpdate;
			PowerAttorneyContent powerAttorneyContentUpdate = this.powerAttorneyContentDao.selectPowerAttorneyContentByLaId(powerAttorney.getLaId());
			ret.setLaType(powerAttorney.getLaType());
			ret.setLaTypeId(powerAttorney.getLaTypeId());
			ret.setLaCopyies(powerAttorney.getLaCopyies());
			String enclosureName = this.setEnclosureNameData(powerAttorney.getLaId());
			ret.setEnclosureName(enclosureName);
			ret.setLaEffectiveDate(powerAttorney.getLaEffectiveDate());
			ret.setLaExpirationDate(powerAttorney.getLaExpirationDate());
			ret.setHasEnclosure(powerAttorney.getHasEnclosure());
			if(user.getDept() != null) {
				ret.setModifiedDeptId(user.getDeptId());
				ret.setModifiedDeptName(user.getDept().getName());
			}
			Date sysDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateNowStr = sdf.format(sysDate);
			Date modifiedDate = null;
			try {
				modifiedDate = sdf.parse(dateNowStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ret.setModifiedDate(modifiedDate);
			ret.setModifiedBy(user.getName());
			ret.setModifiedById(user.getPersonId());
			this.powerAttorneyDao.update(ret);
			try {
				powerAttorneyContentUpdate.setLaReasonC(new SerialClob(powerAttorney.getLaReasonC().toCharArray()));
				powerAttorneyContentUpdate.setLaReasonE(new SerialClob(powerAttorney.getLaReasonE().toCharArray()));
				powerAttorneyContentUpdate.setLaDesc(new SerialClob(powerAttorney.getLaDesc().toCharArray()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.powerAttorneyContentDao.update(powerAttorneyContentUpdate);
		}
		return ret;
	}
	/**
	 * 获取附件文件名
	 * 
	 * @param laId 授权书主键
	 * @return String
	 */
	public String setEnclosureNameData(String laId) {
		PowerAttorney powerAttorney = this.powerAttorneyDao.get(laId);
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("PWRATTORNEY", "LaEnclosureFile", powerAttorney.getLaId());
		if(list != null && list.size() > 0){
			for(IFile file:list){
				sb.append(file.getFileName() + ",");
			}
			if(sb != null&&sb.length() > 0){
				sb.deleteCharAt(sb.length() - 1);
			}			
		}
		return sb.toString();

	}
	/**
	 * 新建实例,并初始化必要的属性.
	 * @param user
	 * @param laId
	 * @return
	 */
	public PowerAttorney newPowerAttorney(UserProfile user,String laId){
		PowerAttorney powerAttorney_N = new PowerAttorney();
		if(laId!=null){
			powerAttorney_N.setLaId(laId);
		}else{
			powerAttorney_N.setLaId(com.supporter.util.UUIDHex.newId());
		}
		powerAttorney_N.setCreatedById(user.getPersonId());
		powerAttorney_N.setCreatedBy(user.getName());
		String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date createdDate = null;
		try {
			createdDate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		powerAttorney_N.setCreatedDate(createdDate);
		if(user.getDept() != null) {
			powerAttorney_N.setDeptId(user.getDeptId());
			powerAttorney_N.setDeptName(user.getDept().getName());
		}
		powerAttorney_N.setProcStatus(PowerAttorney.DRAFT);
		//编号
		return powerAttorney_N;
	}
	/** 
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param jqGrid
	 * @param laId
	 * @param user
	 * @param isEnglish
	 * @return
	 */
	public PowerAttorney initEditOrViewPage(JqGrid jqGrid,String laId, UserProfile user,String isEnglish) {
		if (StringUtils.isBlank(laId)) {// 新建
			PowerAttorney powerAttorney = newPowerAttorney(user,null);
			return powerAttorney;
		} else {// 编辑
			//获得主表
			PowerAttorney powerAttorney = this.powerAttorneyDao.get(laId);
			if(powerAttorney != null){
				//获得从表powerAttorneyContent
				PowerAttorneyContent powerAttorneyContent = this.powerAttorneyContentDao.get(laId);
				if(powerAttorneyContent != null) {
					if(powerAttorney.getLaTypeId() != null){
						try {
							if("1".equals(powerAttorney.getLaTypeId()) || (isEnglish != null && "no".equals(isEnglish))){//中文授权书
								powerAttorney.setLaReasonC(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
							}else if("2".equals(powerAttorney.getLaTypeId())||(isEnglish!=null&&"yes".equals(isEnglish))){//英文授权书
								powerAttorney.setLaReasonE(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
							}else{
								powerAttorney.setLaReasonC(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
								powerAttorney.setLaReasonE(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
							}
							powerAttorney.setLaDesc(powerAttorneyContent.getLaDesc().getSubString(1, (int) powerAttorneyContent.getLaDesc().length()));
						} catch (Exception e) {
							// TODO: handle exception
						}
					}

				}
				//获得从表powerAttorneyPerson
				List<PowerAttorneyPerson> personList = this.powerAttorneyPersonDao.getPersonByLaId(laId);
				String personName = "";
				String personNameDesc = "";
				String personNameCardDesc = "";
				if(personList != null && personList.size() > 0){
					for (PowerAttorneyPerson powerAttorneyPerson : personList) {
						if("1".equals(CommonUtil.trim(powerAttorney.getLaTypeId())) || (isEnglish != null && "no".equals(isEnglish))){//中文授权书
							personName= personName+powerAttorneyPerson.getAuthPersonName() + ",";
							if(powerAttorneyPerson.getGender() != null){							
								if("男".equals(powerAttorneyPerson.getGender())){
									personNameDesc = personNameDesc + powerAttorneyPerson.getAuthPersonName() + "先生,";
									personNameCardDesc=personNameCardDesc + powerAttorneyPerson.getAuthPersonName() + "先生（身份证/护照号码：" + powerAttorneyPerson.getPassportNo() + "）,";
								}else if("女".equals(powerAttorneyPerson.getGender())){
									personNameDesc = personNameDesc + powerAttorneyPerson.getAuthPersonName() + "女士,";
									personNameCardDesc = personNameCardDesc + powerAttorneyPerson.getAuthPersonName() + "女士（身份证/护照号码：" + powerAttorneyPerson.getPassportNo() + "）,";

								}							
							}
						}else if("2".equals(CommonUtil.trim(powerAttorney.getLaTypeId())) || (isEnglish != null && "yes".equals(isEnglish))){//英文授权书
							personName = personName + powerAttorneyPerson.getAuthPersonLastname() + " " + powerAttorneyPerson.getAuthPersonFirstname() + ",";
							if(powerAttorneyPerson.getGender() != null){
								if("男".equals(powerAttorneyPerson.getGender())){
									personNameDesc = personNameDesc + "Mr " + powerAttorneyPerson.getAuthPersonLastname() + " " + powerAttorneyPerson.getAuthPersonFirstname() + ",";
									personNameCardDesc = personNameCardDesc + "Mr " + powerAttorneyPerson.getAuthPersonLastname() + " " + powerAttorneyPerson.getAuthPersonFirstname() + " whose ID card /Passport No. is: " + powerAttorneyPerson.getPassportNo() + ",";
								}else if("女".equals(powerAttorneyPerson.getGender())){
									personNameDesc = personNameDesc + "Mrs " + powerAttorneyPerson.getAuthPersonLastname() + " " + powerAttorneyPerson.getAuthPersonFirstname() + ",";
									personNameCardDesc = personNameCardDesc + "Mrs " + powerAttorneyPerson.getAuthPersonLastname() + " " + powerAttorneyPerson.getAuthPersonFirstname() + " whose ID card /Passport No. is:" + powerAttorneyPerson.getPassportNo() + ",";
								}							
							}
						}else if("3".equals(CommonUtil.trim(powerAttorney.getLaTypeId())) || "4".equals(CommonUtil.trim(powerAttorney.getLaTypeId()))){
							personName = personName + powerAttorneyPerson.getAuthPersonName() + "," + powerAttorneyPerson.getAuthPersonLastname() + " " + powerAttorneyPerson.getAuthPersonFirstname() + ",";
						}
					}
					if(personName.length() > 0){
						int endIndex = personName.length() - 1;
						powerAttorney.setPersonName(personName.substring(0, endIndex));
					}
					if(personNameDesc.length() > 0){
						int endIndex=personNameDesc.length() - 1;
						powerAttorney.setPersonNameDesc(personNameDesc.substring(0, endIndex));
					}
					if(personNameCardDesc.length() > 0){
						int endIndex = personNameCardDesc.length() - 1;
						powerAttorney.setPersonNameCardDesc(personNameCardDesc.substring(0, endIndex));
					}
				}			
				String laEffectiveDate = CommonUtil.format(powerAttorney.getLaEffectiveDate(), "yyyy年MM月dd日");
				String laExpirationDate = CommonUtil.format(powerAttorney.getLaExpirationDate(), "yyyy年MM月dd日");
				if("1".equals(CommonUtil.trim(powerAttorney.getLaTypeId())) || (isEnglish != null && "no".equals(isEnglish))){ //中文授权书
					powerAttorney.setLaEffectiveDate(laEffectiveDate);
					powerAttorney.setLaExpirationDate(laExpirationDate);
				}else if("2".equals(CommonUtil.trim(powerAttorney.getLaTypeId())) || (isEnglish != null && "yes".equals(isEnglish))){  //英文授权书
					//语言环境设置为英文
					Locale l = new Locale("en");
					//日期格式
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
					//处理开始日期
					Date effectiveDate = null;
					try {
						effectiveDate = sdf.parse(laEffectiveDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String yearS = String.format("%tY", effectiveDate);
					String monthS = String.format(l,"%tB", effectiveDate);
					String dayS = String.format("%td", effectiveDate);

					//处理结束日期
					Date expirationDate = null;
					try {
						expirationDate = sdf.parse(laExpirationDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String yearE = String.format("%tY", expirationDate);
					String monthE = String.format(l,"%tB", expirationDate);
					String dayE = String.format("%td", expirationDate);

					powerAttorney.setLaEffectiveDate(monthS + " " + dayS + ", " + yearS);
					powerAttorney.setLaExpirationDate(monthE + " " + dayE + ", " + yearE);
				}
				return powerAttorney;
			}else{
				PowerAttorney powerAttorneyZ = newPowerAttorney(user, laId);
				return powerAttorneyZ;
			}
		}
	}
	/**
	 * 不分页表格展示所有授权书.
	 * @return
	 * @throws SQLException 
	 */
	public List<PowerAttorney> getPowerAttorneyAll() throws SQLException {
		List<PowerAttorney> list = powerAttorneyDao.getAllLa();	
		if(list.size() > 0 ) {
			for (int i = 0; i < list.size(); i++) {
				List<PowerAttorneyPerson> list2 = powerAttorneyPersonDao.getPersonByLaId(list.get(i).getLaId());
				if("2".equals(list.get(i).getLaTypeId())) {
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < list2.size(); j++) {
						sb.append(list2.get(j).getAuthPersonFirstname() + "·" + list2.get(j).getAuthPersonLastname() + ",");
					}
					if(sb.toString().length() > 0){
						int endIndex = sb.toString().length() - 1;
						list.get(i).setPersonName(sb.toString().substring(0, endIndex));
					}
				}else {
					StringBuffer sb2 = new StringBuffer();
					for (int j = 0; j < list2.size(); j++) {
						sb2.append(list2.get(j).getAuthPersonName() + ",");
					}
					if(sb2.toString().length()>0){
						int endIndex=sb2.toString().length() - 1;
						list.get(i).setPersonName(sb2.toString().substring(0, endIndex));
					}
				}
			}
			for (int i = 0; i < list.size(); i++) {
				PowerAttorneyContent powerAttorneyContent = powerAttorneyContentDao.selectPowerAttorneyContentByLaId(list.get(i).getLaId());
				if(powerAttorneyContent != null) {
					if("1".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
					} else if("2".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
					} else if("3".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()) + ";" + powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
					} else if("4".equals(list.get(i).getLaTypeId())) {
						if(powerAttorneyContent.getLaReasonC() != null && powerAttorneyContent.getLaReasonE() != null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length())
									+";"+powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
						}else if(powerAttorneyContent.getLaReasonE() == null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
						} else if(powerAttorneyContent.getLaReasonC() == null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
						} 
					}
				}
			}

		}
		return list;
	}
	/**
	 * 按业务主键获取所有授权书.
	 * @return
	 * @throws SQLException 
	 */
	public List<PowerAttorney> getAllLaByBusinessUUID(String businessUUID) throws SQLException {
		List<PowerAttorney> list=powerAttorneyDao.getAllLaByBusinessUUID(businessUUID);	
		if(list.size() > 0 ) {
			for (int i = 0; i < list.size(); i++) {
				List<PowerAttorneyPerson> list2 = powerAttorneyPersonDao.getPersonByLaId(list.get(i).getLaId());
				if("2".equals(list.get(i).getLaTypeId())) {
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < list2.size(); j++) {
						sb.append(list2.get(j).getAuthPersonFirstname() + "·" + list2.get(j).getAuthPersonLastname() + ",");
					}
					if(sb.toString().length() > 0){
						int endIndex = sb.toString().length() - 1;
						list.get(i).setPersonName(sb.toString().substring(0, endIndex));
					}
				}else {
					StringBuffer sb2 = new StringBuffer();
					for (int j = 0; j < list2.size(); j++) {
						sb2.append(list2.get(j).getAuthPersonName() + ",");
					}
					if(sb2.toString().length() > 0){
						int endIndex=sb2.toString().length() - 1;
						list.get(i).setPersonName(sb2.toString().substring(0, endIndex));
					}
				}
			}
			for (int i = 0; i < list.size(); i++) {
				PowerAttorneyContent powerAttorneyContent = powerAttorneyContentDao.selectPowerAttorneyContentByLaId(list.get(i).getLaId());
				if(powerAttorneyContent != null) {
					if("1".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
					} else if("2".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
					} else if("3".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()) + ";" + powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
					} else if("4".equals(list.get(i).getLaTypeId())) {
						if(powerAttorneyContent.getLaReasonC() != null && powerAttorneyContent.getLaReasonE() != null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length())
									+";"+powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
						}else if(powerAttorneyContent.getLaReasonE() == null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
						} else if(powerAttorneyContent.getLaReasonC() == null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
						} 
					}
				}
			}

		}
		return list;
	}
	/**
	 * 分页表格展示所有授权书.
	 * @param jqGrid
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public List<PowerAttorney> getPowerAttorneyGrid(UserProfile user,JqGrid jqGrid, String prjId) throws SQLException {
		List<PowerAttorney> list = new ArrayList<PowerAttorney>();
		if(StringUtils.isNotBlank(prjId)) {
			list = powerAttorneyDao.getPowerAttorneyGridByPrjId(jqGrid, prjId);
		}else {
			list = powerAttorneyDao.getPowerAttorneyGrid(jqGrid);
		}
		if(list.size() > 0 ) {
			for (int i = 0; i < list.size(); i++) {
				List<PowerAttorneyPerson> list2 = powerAttorneyPersonDao.getPersonByLaId(list.get(i).getLaId());
				if("2".equals(list.get(i).getLaTypeId())) {
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < list2.size(); j++) {
						sb.append(list2.get(j).getAuthPersonFirstname() + "·" + list2.get(j).getAuthPersonLastname() + ",");
					}
					if(sb.toString().length() > 0){
						int endIndex=sb.toString().length() - 1;
						list.get(i).setPersonName(sb.toString().substring(0, endIndex));
					}
				}else {
					StringBuffer sb2 = new StringBuffer();
					for (int j = 0; j < list2.size(); j++) {
						sb2.append(list2.get(j).getAuthPersonName() + ",");
					}
					if(sb2.toString().length() > 0){
						int endIndex = sb2.toString().length() - 1;
						list.get(i).setPersonName(sb2.toString().substring(0, endIndex));
					}
				}
			}
			for (int i = 0; i < list.size(); i++) {
				PowerAttorneyContent powerAttorneyContent = powerAttorneyContentDao.selectPowerAttorneyContentByLaId(list.get(i).getLaId());
				if(powerAttorneyContent != null) {
					if("1".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
					} else if("2".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
					} else if("3".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()) + ";" + powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
					} else if("4".equals(list.get(i).getLaTypeId())) {
						if(powerAttorneyContent.getLaReasonC() != null && powerAttorneyContent.getLaReasonE() != null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length())
									+";"+powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
						}else if(powerAttorneyContent.getLaReasonE() == null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
						} else if(powerAttorneyContent.getLaReasonC() == null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
						} 
					}
				}
			}
		}
		jqGrid.setRows(list);
		return list;
	}
	/**
	 * 通过项目编号和业务编码号获取分页授权书列表
	 * @param jqGrid
	 * @param laBusinessTypeId
	 * @param prjId
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PowerAttorney> getLaGridByProIdAndLaBusinessTypeId(JqGrid jqGrid,String laBusinessTypeId, String prjId) throws SQLException {
		List<PowerAttorney> list=powerAttorneyDao.getLaGridByProIdAndLaBusinessTypeId(jqGrid,laBusinessTypeId,prjId);	
		if(list.size() > 0 ) {
			for (int i = 0; i < list.size(); i++) {
				List<PowerAttorneyPerson> list2 = powerAttorneyPersonDao.getPersonByLaId(list.get(i).getLaId());
				if("2".equals(list.get(i).getLaTypeId())) {
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < list2.size(); j++) {
						sb.append(list2.get(j).getAuthPersonFirstname() + "·" + list2.get(j).getAuthPersonLastname() + ",");
					}
					if(sb.toString().length() > 0){
						int endIndex = sb.toString().length() - 1;
						list.get(i).setPersonName(sb.toString().substring(0, endIndex));
					}
				}else {
					StringBuffer sb2 = new StringBuffer();
					for (int j = 0; j < list2.size(); j++) {
						sb2.append(list2.get(j).getAuthPersonName() + ",");
					}
					if(sb2.toString().length() > 0){
						int endIndex = sb2.toString().length() - 1;
						list.get(i).setPersonName(sb2.toString().substring(0, endIndex));
					}
				}
			}
			for (int i = 0; i < list.size(); i++) {
				PowerAttorneyContent powerAttorneyContent = powerAttorneyContentDao.selectPowerAttorneyContentByLaId(list.get(i).getLaId());
				if(powerAttorneyContent != null) {
					if("1".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
					} else if("2".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
					} else if("3".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()) + ";" + powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
					} else if("4".equals(list.get(i).getLaTypeId())) {
						if(powerAttorneyContent.getLaReasonC() != null && powerAttorneyContent.getLaReasonE() != null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length())
									+";"+powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
						}else if(powerAttorneyContent.getLaReasonE() == null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
						} else if(powerAttorneyContent.getLaReasonC() == null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
						} 
					}
				}
			}
		}
		jqGrid.setRows(list);
		return list;
	}
	/**
	 * 通过项目编号和业务编码号获取不分页授权书列表
	 * @param laBusinessTypeId
	 * @param prjId
	 * @return
	 * @throws SQLException
	 */
	public List<PowerAttorney> getLaAllByProIdAndLaBusinessTypeId(String prjId, String laBusinessTypeId) throws SQLException {
		List<PowerAttorney> list=powerAttorneyDao.getLaByProIdAndLaBusinessTypeId(laBusinessTypeId, prjId);
		if(list.size() > 0 ) {
			for (int i = 0; i < list.size(); i++) {
				List<PowerAttorneyPerson> list2 = powerAttorneyPersonDao.getPersonByLaId(list.get(i).getLaId());
				if("2".equals(list.get(i).getLaTypeId())) {
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < list2.size(); j++) {
						sb.append(list2.get(j).getAuthPersonFirstname() + "·" + list2.get(j).getAuthPersonLastname() + ",");
					}
					if(sb.toString().length() > 0){
						int endIndex = sb.toString().length() - 1;
						list.get(i).setPersonName(sb.toString().substring(0, endIndex));
					}
				}else {
					StringBuffer sb2 = new StringBuffer();
					for (int j = 0; j < list2.size(); j++) {
						sb2.append(list2.get(j).getAuthPersonName() + ",");
					}
					if(sb2.toString().length() > 0){
						int endIndex = sb2.toString().length() - 1;
						list.get(i).setPersonName(sb2.toString().substring(0, endIndex));
					}
				}
			}
			for (int i = 0; i < list.size(); i++) {
				PowerAttorneyContent powerAttorneyContent = powerAttorneyContentDao.selectPowerAttorneyContentByLaId(list.get(i).getLaId());
				if(powerAttorneyContent != null) {
					if("1".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
					} else if("2".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
					} else if("3".equals(list.get(i).getLaTypeId())) {
						list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()) + ";" + powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
					} else if("4".equals(list.get(i).getLaTypeId())) {
						if(powerAttorneyContent.getLaReasonC() != null && powerAttorneyContent.getLaReasonE() != null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length())
									+";"+powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
						}else if(powerAttorneyContent.getLaReasonE() == null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
						} else if(powerAttorneyContent.getLaReasonC() == null) {
							list.get(i).setLaReason(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
						} 
					}
				}
			}
		}
		return list;
	}
	/**
	 * 获取当前项目当前业务下所有授权书数量
	 * @param laBusinessTypeId
	 * @param prjId
	 * @return 
	 */
	public int getLaNumberByProIdAndLaBusinessTypeId(String laBusinessTypeId, String prjId) throws SQLException{
		List<PowerAttorney> list = powerAttorneyDao.getLaByProIdAndLaBusinessTypeId(laBusinessTypeId, prjId);
		if(list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getReviewTimes() == null || list.get(i).getReviewTimes() == 0) {
					list.remove(i);
					i--;
				}
			}
		}
		return list.size();
	}
	/**
	 * 判断当前项目当前业务下是否有授权书通过评审
	 * @param laBusinessTypeId
	 * @param prjId
	 * @return havePass
	 */
	public boolean havePass(String laBusinessTypeId, String prjId) {
		boolean havePass = false;
		List<PowerAttorney> list = powerAttorneyDao.getLaByProIdAndLaBusinessTypeId(laBusinessTypeId, prjId);
		System.out.println(list.size());
		if(list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if(2 == list.get(i).getProcStatus()) {
					havePass = true ;
					break;
				}
			}
		}
		return havePass;
	}
	/**
	 * 获取单个授权书对象信息.
	 * @param laId
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	public PowerAttorney getFillFormData(UserProfile user, String laId) throws SQLException {
		PowerAttorney powerAttorney = powerAttorneyDao.get(laId);
		PowerAttorneyContent powerAttorneyContent = powerAttorneyContentDao.get(laId);
		if(powerAttorneyContent != null) {
			if(powerAttorneyContent.getLaReasonC() != null) {
				powerAttorney.setLaReasonC(powerAttorneyContent.getLaReasonC().getSubString(1, (int) powerAttorneyContent.getLaReasonC().length()));
			}
			if(powerAttorneyContent.getLaReasonE() != null) {
				powerAttorney.setLaReasonE(powerAttorneyContent.getLaReasonE().getSubString(1, (int) powerAttorneyContent.getLaReasonE().length()));
			}
			if(powerAttorneyContent.getLaDesc() != null) {
				powerAttorney.setLaDesc(powerAttorneyContent.getLaDesc().getSubString(1, (int) powerAttorneyContent.getLaDesc().length()));
			}
		}
		return powerAttorney;
	}
	/**
	 * 单项删除.
	 * @param laId
	 * @return
	 */
	public int singleDelete(String laId) {
		//删除上传的附件文件
		PowerAttorney powerAttorney = powerAttorneyDao.get(laId);
		if(powerAttorney != null) {
			if(powerAttorney.getProcStatus() == 0) {
				IFileUploadService fileUploadService = EIPService.getFileUploadService();
				List<IFile> list  = fileUploadService.getFileList("PWRATTORNEY", "LaEnclosureFile", powerAttorney.getLaId());
				if(list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						fileUploadService.deleteFile(list.get(i).getFileId());
					}
				}
				//删除被授权人
				List<PowerAttorneyPerson> personByLaId = powerAttorneyPersonDao.getPersonByLaId(laId);
				if(personByLaId.size() > 0) {
					powerAttorneyPersonDao.deletePersonByLaId(laId);
				}
				PowerAttorneyContent powerAttorneyContent = powerAttorneyContentDao.get(laId);
				//删除授权书内容
				if(powerAttorneyContent != null) {
					powerAttorneyContentDao.delete(laId);
				}
				//删除授权书
				powerAttorneyDao.delete(laId);

				return 1;
			}else {
				return 0;
			}
		}else {
			return 0;
		}
	}
	/**
	 * 单项删除.
	 * @param laId
	 * @return
	 */
	public void deleteAll() {
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		//删除所以上传的附件文件
		List<PowerAttorney> powerAttorneyList = powerAttorneyDao.find();
		if(powerAttorneyList.size() > 0) {
			for (int i = 0; i < powerAttorneyList.size(); i++) {
				List<IFile> list  = fileUploadService.getFileList("PWRATTORNEY", "LaEnclosureFile", powerAttorneyList.get(i).getLaId());
				if(list.size() > 0) {
					for (int j = 0; j < list.size(); j++) {
						fileUploadService.deleteFile(list.get(j).getFileId());
					}
				}
			}
		}
		//删除被授权人
		List<PowerAttorneyPerson> personEntities = powerAttorneyPersonDao.find();
		powerAttorneyPersonDao.delete(personEntities);
		//删除授权书内容
		List<PowerAttorneyContent> contentEntities = powerAttorneyContentDao.find();
		powerAttorneyContentDao.delete(contentEntities);
		//删除授权书
		List<PowerAttorney> powerAttorneyEntities = powerAttorneyDao.find();
		powerAttorneyDao.delete(powerAttorneyEntities);
	}
	/**
	 * 授权书导出.
	 * @return
	 */
	public HSSFWorkbook getAllLaInfoWorkbook() {
		String excelFileName = "POWERATTORNEY_LIST.xls";
		HSSFWorkbook wb = null;
		try {
			ExportUtils util = new ExportUtils();
			File file = util.getExcelDemoFile(File.separator + "template_excel_def" + File.separator + excelFileName);
			wb = util.getExcelWorkbook(file);
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setBorderBottom((short)1);
			cellStyle.setBorderLeft((short)1);
			cellStyle.setBorderTop((short)1);
			cellStyle.setBorderRight((short)1);
			List<PowerAttorney> list = this.getPowerAttorneyAll();
			if ((list != null) && (list.size() != 0)) {
				int length = list.size();
				HSSFSheet sheet = wb.getSheetAt(0);
				HSSFRow row = null;
				HSSFCell cell = null;
				for (int i = 0; i < length; i++) {
					row = sheet.createRow(i + 2);
					PowerAttorney powerAttorney = (PowerAttorney) list.get(i);
					cell = row.createCell(0, 1);
					cell.setCellValue(powerAttorney.getLaNo()); 
					cell.setCellStyle(cellStyle);
					cell = row.createCell(1, 1);
					cell.setCellValue(powerAttorney.getLaBusinessType()); 
					cell.setCellStyle(cellStyle);
					cell = row.createCell(2, 1);
					cell.setCellValue(powerAttorney.getLaType()); 
					cell.setCellStyle(cellStyle);
					cell = row.createCell(3, 1);
					cell.setCellValue(powerAttorney.getPersonName()); 
					cell.setCellStyle(cellStyle);
					cell = row.createCell(4, 1);
					cell.setCellValue(powerAttorney.getLaCopyies()); 
					cell.setCellStyle(cellStyle);
					cell = row.createCell(5, 1);
					cell.setCellValue(powerAttorney.getLaReason()); 
					cell.setCellStyle(cellStyle);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}
	/**
	 * 按项目编号和业务编码号授权书导出.
	 * @return
	 */
	public HSSFWorkbook getLaInfoWorkbook(String prjId, String laBusinessTypeId) {
		String excelFileName = "POWERATTORNEY_LIST.xls";
		HSSFWorkbook wb = null;
		try {
			ExportUtils util = new ExportUtils();
			File file = util.getExcelDemoFile(File.separator + "template_excel_def" + File.separator + excelFileName);
			wb = util.getExcelWorkbook(file);
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setBorderBottom((short)1);
			cellStyle.setBorderLeft((short)1);
			cellStyle.setBorderTop((short)1);
			cellStyle.setBorderRight((short)1);
			List<PowerAttorney> list = this.getLaAllByProIdAndLaBusinessTypeId(prjId, laBusinessTypeId);
			if ((list != null) && (list.size() != 0)) {
				int length = list.size();
				HSSFSheet sheet = wb.getSheetAt(0);
				HSSFRow row = null;
				HSSFCell cell = null;
				for (int i = 0; i < length; i++) {
					row = sheet.createRow(i + 2);
					PowerAttorney powerAttorney = (PowerAttorney) list.get(i);
					cell = row.createCell(0, 1);
					cell.setCellValue(powerAttorney.getLaNo()); 
					cell.setCellStyle(cellStyle);
					cell = row.createCell(1, 1);
					cell.setCellValue(powerAttorney.getLaBusinessType()); 
					cell.setCellStyle(cellStyle);
					cell = row.createCell(2, 1);
					cell.setCellValue(powerAttorney.getLaType()); 
					cell.setCellStyle(cellStyle);
					cell = row.createCell(3, 1);
					cell.setCellValue(powerAttorney.getPersonName()); 
					cell.setCellStyle(cellStyle);
					cell = row.createCell(4, 1);
					cell.setCellValue(powerAttorney.getLaCopyies()); 
					cell.setCellStyle(cellStyle);
					cell = row.createCell(5, 1);
					cell.setCellValue(powerAttorney.getLaReason()); 
					cell.setCellStyle(cellStyle);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}
	/**
	 * 更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public PowerAttorney update(PowerAttorney powerAttorney) {
		this.powerAttorneyDao.update(powerAttorney);
		return powerAttorney;
	}
	/**
	 * 保存或更新
	 * 
	 * @param laId 授权书编号
	 * @return
	 */
	public PowerAttorney get(String laId) {
		PowerAttorney powerAttorney = this.powerAttorneyDao.get(laId);
		return powerAttorney;
	}
	/**
	 * 上传盖章文件
	 * 
	 * @param laId 授权书编号
	 * @return
	 */
	public void hasSealDocument(String laId) {
		PowerAttorney powerAttorney = this.powerAttorneyDao.get(laId);
		powerAttorney.setHasSealDocument(PowerAttorney.UPLOADED);
		this.powerAttorneyDao.update(powerAttorney);

	}
	/**
	 * 被驳回申请后放弃申请操作
	 * @param laId
	 */
	public void abortAndInit(String laId) {
		PowerAttorney powerAttorney = this.powerAttorneyDao.get(laId);
		powerAttorney.setProcStatus(PowerAttorney.INITIAL);
		this.powerAttorneyDao.update(powerAttorney);

	}
	/**
	 * 判断是否已经上传盖章文件
	 * @param laId 授权书编号
	 * @return
	 */
	public int selectSealDocument(String laId) {
		PowerAttorney powerAttorney = this.powerAttorneyDao.get(laId);
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("PWRATTORNEY", "LaSealFile", powerAttorney.getLaId());
		return list.size();
	}
	/**
	 * 获取授权书附件list
	 * @param String moduleName, String busiType, String oneLevelId, String twoLevelId
	 * @return
	 */
	public List<IFile> getEnclosureList(String moduleName, String busiType, String oneLevelId, String twoLevelId) {
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList(moduleName, busiType, oneLevelId, twoLevelId);
		return list;
	}
	/**
	 * 调用中文授权书pdf导出报表模板导出文件
	 * @param laId
	 */
	@Transactional(readOnly=true)
	public void createPdfC(String laId) {
		String classPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		String ROOT_PATH = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
		String FILE_PATH = "/ppm/poa/power_attorney/rpt/out/";
		String license = ROOT_PATH + "/WEB-INF/lic/runqianWindowServer.lic";
		ExtCellSet.setLicenseFileName(license);
		final String laIdData = laId;
		final String imgSrc = ROOT_PATH + "/ppm/poa/power_attorney/img/logo.jpg";
		final Context cxt = new Context();
		final String defPath = ROOT_PATH + "/ppm/poa/power_attorney/rpt/rpt_file/" + "laDemoC.raq";
		final String pdfPath = ROOT_PATH + FILE_PATH + "demo.pdf";
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection){

				try {
					ReportDefine reportDef = (ReportDefine)ReportUtils.read(defPath);
					cxt.setParamValue("LA_ID", laIdData);
					cxt.setParamValue("imgSrc", imgSrc);
					cxt.setConnection("supp_app", connection);
					Engine engine = new Engine(reportDef,cxt);
					IReport ireport = engine.calc();
					//connection.close();
					//ReportUtils.exportToPDF(pdfPath,ireport);//不能分页
					ReportUtils.exportToPDF(pdfPath,ireport,true,true);//这是分页导出
				}catch(Exception e) {
					e.printStackTrace();
				}catch(Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 调用中文授权书pdf导出报表模板导出文件
	 * @param laId
	 */
	@Transactional(readOnly=true)
	public void createPdfE(String laId) {
		String classPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		String ROOT_PATH = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
		String FILE_PATH = "/ppm/poa/power_attorney/rpt/out/";
		String license = ROOT_PATH + "/WEB-INF/lic/runqianWindowServer.lic";
		ExtCellSet.setLicenseFileName(license);
		final String laIdData = laId;
		final String imgSrc = ROOT_PATH + "/ppm/poa/power_attorney/img/logo.jpg";
		final Context cxt = new Context();
		final String defPath = ROOT_PATH + "/ppm/poa/power_attorney/rpt/rpt_file/" + "laDemoE.raq";
		final String pdfPath = ROOT_PATH + FILE_PATH + "demo.pdf";
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection){

				try {
					ReportDefine reportDef = (ReportDefine)ReportUtils.read(defPath);
					cxt.setParamValue("LA_ID", laIdData);
					cxt.setParamValue("imgSrc", imgSrc);
					cxt.setConnection("supp_app", connection);
					Engine engine = new Engine(reportDef,cxt);
					IReport ireport = engine.calc();
					//connection.close();
					//ReportUtils.exportToPDF(pdfPath,ireport);//不能分页
					ReportUtils.exportToPDF(pdfPath,ireport,true,true);//这是分页导出
				}catch(Exception e) {
					e.printStackTrace();
				}catch(Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}
	public List<PowerAttorney> selectPowerAttorneyAll(){
		return powerAttorneyDao.selectPowerAttorneyAll();
	}
}

