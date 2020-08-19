package com.supporter.prj.linkworks.oa.abroad.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.abroad.dao.AbroadRealDateDao;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.abroad.entity.AbroadRealDate;
import com.supporter.prj.linkworks.oa.abroad.util.ConvertUtils;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadBack;
import com.supporter.prj.linkworks.oa.abroadPublicity.service.AbroadBackService;
import com.supporter.util.CommonUtil;


/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Service
public class AbroadRealDateService {

	@Autowired
	private AbroadRealDateDao abroadRealDateDao;
	@Autowired
	private AbroadService abroadService;
	@Autowired
	private AbroadBackService abroadBackService;
	
	/**
	 * 通过出国审批id获取实际出国时间
	 * @param recordId
	 * @return
	 */
	public String getRealDateByAbroadId(String recordId){
		AbroadRealDate abroadRealDate = abroadRealDateDao.getRealDateByAbroad(recordId);
		if(abroadRealDate != null){
			return abroadRealDate.getRealDateApply();
		}
		return null;
	}
	public AbroadRealDate getEntityByAbroadId(String recordId){
		AbroadRealDate abroadRealDate = abroadRealDateDao.getRealDateByAbroad(recordId);
		if(abroadRealDate != null){
			return abroadRealDate;
		}
		return null;
	}
	/**
	 * 通过id获取实际出国时间
	 * @param realId
	 * @return
	 */
	public AbroadRealDate get(String realId){
		AbroadRealDate abroadRealDate = abroadRealDateDao.get(realId);
		Abroad abroad = abroadService.get(abroadRealDate.getAbroadId());
		abroadRealDate.setTgtCountries(abroad.getTgtCountries());
		abroadRealDate.setDeptName(abroad.getApplierDept());
		return abroadRealDate;
	}

	/**
	 * 更新
	 * @param abroadRealDate
	 */
	public void update(AbroadRealDate abroadRealDate) {
		abroadRealDateDao.update(abroadRealDate);
		
	}

	/**
	 * 初始化数据
	 * @param recordId
	 * @param userProfile
	 * @return
	 */
	public AbroadRealDate initEditOrViewPage(String recordId, UserProfile userProfile) {
		AbroadRealDate realDate = this.abroadRealDateDao.getRealDateByAbroad(recordId);
		Abroad abroad = abroadService.get(recordId);
		if(realDate == null){//新建
			realDate = new AbroadRealDate();
			if(abroad != null){
				String abroadPublicityId = abroadService.getPubByAbroadId(recordId);//获取出国公示id
				if(StringUtils.isNotBlank(abroadPublicityId)){
					AbroadBack abroadBack = abroadBackService.getByPubId(abroadPublicityId, userProfile);
					if(abroadBack != null){//如果出国报告不为空，则使用出国报告的出国时间
						realDate.setRealDateApply(abroadBack.getRealLeaveDate());
					}
				}else{//否则，使用出国审批的出国时间
					realDate.setRealDateApply(abroad.getLeavingDate());
					
				}
				realDate.setAbroadId(abroad.getRecordId());
				realDate.setMaxSubmitedDate(getAbroadRealDateMaxSubmitedDate(abroad));
			}
			realDate.setSwfStatus(Abroad.DRAFT);
			realDate.setRealId(com.supporter.util.UUIDHex.newId());
			realDate.setCreatedBy(userProfile.getName());
			realDate.setCreatedById(userProfile.getPersonId());
			realDate.setCreatedDate(ConvertUtils.dateString());
			realDate.setModifiedBy(userProfile.getName());
			realDate.setModifiedById(userProfile.getPersonId());
			realDate.setModifiedDate(ConvertUtils.dateString());
			realDate.setTgtCountries(abroad.getTgtCountries());
			realDate.setDeptName(abroad.getApplierDept());
			realDate.setMaxSubmitedDate(getAbroadRealDateMaxSubmitedDate(abroad));
			this.abroadRealDateDao.save(realDate);
		}
		if(abroad != null){
			realDate.setTgtCountries(abroad.getTgtCountries());
			realDate.setDeptName(abroad.getApplierDept());
			realDate.setMaxSubmitedDate(getAbroadRealDateMaxSubmitedDate(abroad));
		}
		return realDate;
	}
	
	
	/**
	 * 保存或更新
	 * @param realDate
	 * @param userProfile
	 */
	public AbroadRealDate saveOrUpdate(String realId,String realDateApply,UserProfile userProfile){
		AbroadRealDate realDate = this.abroadRealDateDao.get(realId);
		realDate.setModifiedBy(userProfile.getName());
		realDate.setModifiedById(userProfile.getPersonId());
		realDate.setModifiedDate(ConvertUtils.dateString());
		if(StringUtils.isNotBlank(realDateApply)){
			realDate.setRealDateApply(realDateApply.substring(0, 10));
		}
		this.abroadRealDateDao.saveOrUpdate(realDate);
		return realDate;
	}
	
    /**
     * 获得最迟提交日期
     * @param abroad
     * @param monthCount
     * @return
     */
    public String getAbroadRealDateMaxSubmitedDate(Abroad abroad){
    	String begin = abroad.getPassDate();
    	if(begin.length()==0)return "";
    	String end = null;
    	try{
    		Date date = CommonUtil.parseDate(begin);
    		Calendar c = Calendar.getInstance();
    		c.setTime(date);
    		c.add(Calendar.MONTH, 3);
    		date = c.getTime();
    		end = CommonUtil.format(date, "yyyy-MM-dd");
    	}catch(Exception e){
    		e.printStackTrace();
    		end = begin;
    	}
    	return end;
    }
	
	
	
}
