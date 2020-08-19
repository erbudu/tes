package com.supporter.prj.linkworks.oa.abroad.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.abroad.entity.AbroadRealDate;


/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Repository
public class AbroadRealDateDao extends MainDaoSupport < AbroadRealDate, String >{

	/**
	 * 通过出国审批id获取实际出国时间
	 * @param recordId
	 * @return
	 */
	public AbroadRealDate getRealDateByAbroad(String recordId){
		AbroadRealDate abroadRealDate = null;
		if(StringUtils.isNotBlank(recordId)){
			String hql = "from " + AbroadRealDate.class.getName() + " where abroadId = ? ";
			List<AbroadRealDate> list = this.find(hql, recordId);
			if(CollectionUtils.isNotEmpty(list)){
				abroadRealDate = list.get(0);			
			}
		}
		return abroadRealDate;
	}
}
