package com.supporter.prj.linkworks.oa.bulletin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.bulletin.entity.AcccntAccessCount;
import com.supporter.prj.linkworks.oa.bulletin.entity.Bulletin;


/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Repository
public class AcccntAccessCountDao  extends MainDaoSupport < AcccntAccessCount, String >{

	/**
	 * 获取实体类的阅读次数
	 * @param bulletinId
	 * @return
	 */
	public AcccntAccessCount getcount(String bulletinId){
		String accessObjectType = Bulletin.class.getName();
		String accessObject = "OA_BULLETIN_OA:BULLETIN_ID:"+bulletinId;
		String hql = " from " +AcccntAccessCount.class.getName()+ " where accessObject = ? and accessObjectType = ?" ;
		List<AcccntAccessCount> list = this.retrieve(hql, accessObject, accessObjectType);;
		if(list != null && list.size() >0){
			return list.get(0);
		}else{
			return null;			
		}
	}
	
	/**
	 * 新建阅读次数对象
	 * @param bulletin
	 */
	public void newAcccntCount(Bulletin bulletin){
		AcccntAccessCount count = new AcccntAccessCount();
		count.setAccessObject("OA_BULLETIN_OA:BULLETIN_ID:"+bulletin.getBulletinId());
		count.setAccessObjectType(Bulletin.class.getName());
		count.setRecordId(com.supporter.util.UUIDHex.newId());
		count.setReadCount(0);
		count.setModifyCount(0);
		this.save(count);
	}
	
	/**
	 * 删除阅读次数
	 * @param bulletinId
	 */
	public void deleteAcccntCount(String bulletinId){
		AcccntAccessCount count = getcount(bulletinId);
		if(count != null){
			this.delete(count);
		}
	}
	
	/**
	 * 修改阅读次数
	 */
	public void updateAcccntCount(String bulletinId){
		AcccntAccessCount count = getcount(bulletinId);
		if(count != null){
			count.setReadCount(count.getReadCount()+1);
			this.update(count);
		}
	}
}
