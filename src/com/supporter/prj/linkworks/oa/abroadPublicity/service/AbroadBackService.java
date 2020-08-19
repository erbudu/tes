package com.supporter.prj.linkworks.oa.abroadPublicity.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.abroadPublicity.dao.AbroadBackDao;
import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadBack;
import com.supporter.prj.linkworks.oa.abroadPublicity.util.CommonUtils;
import com.supporter.util.CommonUtil;


/**   
 * @Title: Service
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Service
public class AbroadBackService {

	@Autowired
	private AbroadBackDao abroadBackDao;
//	@Autowired
//	private AbroadPublicityDao abroadPublicityDao;
//	@Autowired
//	private AbroadDAO abroadDAO;
	
	

	/**
	 * 判断报告是否过期false过期，true不过期
	 * @param backId
	 * @return
	 */
	public boolean getIsExpired(String backId){
		AbroadBack abroadBack = this.abroadBackDao.get(backId);
		String currentDate = CommonUtil.format(new Date(), "yyyy-MM-dd");
		if(abroadBack.getSwfStatus() == 2)return false;
		if(CommonUtil.trim(abroadBack.getSubmitEndDate()).length()>0&&CommonUtil.trim(abroadBack.getSubmitEndDate()).length()==0)return true;
		if(CommonUtil.trim(abroadBack.getSubmitEndDate()).compareTo(currentDate)>=0){
			return false;
		}
		return true;
	}
	
	/**
	 * 初始化添加修改
	 * @param pubId
	 * @return
	 */
	public AbroadBack initEditOrViewPage(String pubId,UserProfile user){
		AbroadBack abroadBack = null;
		
		if(StringUtils.isNotBlank(pubId)){//修改或查询
			abroadBack = this.abroadBackDao.getByPubId(pubId);
			if(abroadBack == null){
				abroadBack = newAbroadBack(pubId);
			}
		}
		return abroadBack;
	}
	/**
	 * 初始化显示详情
	 * @param pubId
	 * @return
	 */
	public AbroadBack getByPubId(String pubId,UserProfile user){
		AbroadBack abroadBack = null;
		
		if(StringUtils.isNotBlank(pubId)){//查询
			abroadBack = this.abroadBackDao.getByPubId(pubId);
		}
		
		return abroadBack;
	}
	
	/**
	 * 新建回国报告
	 * @param pubId
	 * @return
	 */
	public AbroadBack newAbroadBack(String pubId){
		AbroadBack abroadBack = new AbroadBack();
		abroadBack.setBackId(com.supporter.util.UUIDHex.newId());
		abroadBack.setPubId(pubId);
		abroadBack.setCanceled(AbroadBack.NO_CANCELED);
		abroadBackDao.save(abroadBack);
		return abroadBack;
	}

	/**
	 * \保存
	 * @param user
	 * @param abroadBack
	 * @param submitEndDate
	 * @return
	 */
	public AbroadBack save(UserProfile user, AbroadBack abroadBack,String submitEndDate) {
		AbroadBack back = this.abroadBackDao.get(abroadBack.getBackId());
		if(back != null){
			if(StringUtils.isNotBlank(submitEndDate)){
				abroadBack.setSubmitEndDate(CommonUtils.dateStringSub(submitEndDate));			
			}else{
				abroadBack.setSubmitEndDate(CommonUtils.dateString());	
			}
//			System.out.println(abroadBack.getBackDate());
//			System.out.println(abroadBack.getBackId());
//			System.out.println(abroadBack.getCanceledReason());
//			System.out.println(abroadBack.getIfCompleted());
//			System.out.println(abroadBack.getCanceled());
//			System.out.println(abroadBack.getPubId());
//			System.out.println(abroadBack.getRealLeaveDate());
//			System.out.println(abroadBack.getSubmitEndDate());
//			System.out.println(abroadBack.getSwfStatus());
//			System.out.println(abroadBack.getApplier());
//			System.out.println(abroadBack.getCountries());
//			System.out.println(abroadBack.getDeptId());
//			System.out.println("11111111");
			abroadBack.setRealLeaveDate(CommonUtils.dateStringSub(abroadBack.getRealLeaveDate()));
			abroadBack.setBackDate(CommonUtils.dateStringSub(abroadBack.getBackDate()));
			this.abroadBackDao.update(abroadBack);
		}
		return abroadBack;
	}

	
	/**
	 * 保存最晚报告时间
	 * @param backId
	 * @param submitEndDate
	 */
	public void updateSubmitEndDate(String backId, String submitEndDate) {
		AbroadBack back = this.abroadBackDao.get(backId);
		if(back != null){
			back.setSubmitEndDate(submitEndDate);
			abroadBackDao.update(back);
		}
		
	}
	
	
	
	public AbroadBack get(String backId){
		AbroadBack abroadBack = this.abroadBackDao.get(backId);
		return abroadBack;
		
	}
	
	public void update(AbroadBack abroadBack){
		this.abroadBackDao.update(abroadBack);
	}

}
