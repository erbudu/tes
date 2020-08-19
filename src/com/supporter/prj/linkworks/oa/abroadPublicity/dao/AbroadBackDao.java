package com.supporter.prj.linkworks.oa.abroadPublicity.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;

import com.supporter.prj.linkworks.oa.abroadPublicity.entity.AbroadBack;
/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Repository
public class AbroadBackDao  extends MainDaoSupport < AbroadBack, String >{
	
	/**
	 * 通过pubId获取回国报告
	 * @param abroadId
	 * @return
	 */
	public AbroadBack getByPubId(String pubId){
		String hql = " from " + AbroadBack.class.getName() + " where pubId ='" + pubId + "'";
		if(StringUtils.isNotBlank(pubId)){
			List<AbroadBack> list = this.find(hql);
			if(list == null || list.size()<1){
				return null;
			}
			AbroadBack entity = list.get(0);
			if(entity.getCanceled() !=null && entity.getCanceled() == 1){
				entity.setIsCanceled("否");
			}else{
				entity.setIsCanceled("是");
			}
			if(entity.getSwfStatus()!=null && entity.getSwfStatus() ==0){
				entity.setStatus("草稿");
			}else if(entity.getSwfStatus() ==1){
				entity.setStatus("审核中");
			}else{
				entity.setStatus("审批完成");
			}
			return entity;
			
		}else{
			return null;
		}
	}
	
	

}
