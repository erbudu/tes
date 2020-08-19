package com.supporter.prj.linkworks.oa.consignment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.consignment.entity.CmSwfDefProc;

/**
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0
 * 
 */
@Repository
public class CmSwfDefProcDao extends MainDaoSupport<CmSwfDefProc, String> {
	//获取全部流程
	public List<CmSwfDefProc> getAll() {
		String hql = "from "+CmSwfDefProc.class.getName()+" where 1=1 ";
		return this.find(hql);
	}

}
