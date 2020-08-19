package com.supporter.prj.pm.enginee_negotiate.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaMeet;

/**
 * @Title: 签证
 * @Description: PM_ENGINEE_VISA_MEET.
 * @author Administrator
 * @date 2018-07-04 18:08:55
 * @version V1.0
 *
 */
@Repository
public class EngineeVisaMeetDao extends MainDaoSupport<EngineeVisaMeet, String> {
	
	/**
	 * 获取会议
	 * 
	 * @param procId
	 *            流程ID
	 * @return EngineeVisaMeet
	 */
	public EngineeVisaMeet getMeetByProcId(String procId) {
		String hql = "from " + EngineeVisaMeet.class.getName() + " where procId=?";
		List<EngineeVisaMeet> list = this.find(hql, procId);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}

}

