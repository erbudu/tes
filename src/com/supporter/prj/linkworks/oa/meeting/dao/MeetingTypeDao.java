package com.supporter.prj.linkworks.oa.meeting.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.meeting.entity.MeetingType;

/**
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class MeetingTypeDao extends MainDaoSupport<MeetingType, String> {

//	/**
//	 * 分页查询
//	 */
//	public List<MeetingType> findPage(JqGrid jqGrid, MeetingType meeting) {
//		return this.retrievePage(jqGrid);
//	}

	/**
	 * 根据ID获取.
	 * 
	 * @param MeetingId
	 * @return
	 */
	public List<MeetingType> findAll() {
		String hql = "from " + MeetingType.class.getName();
		List<MeetingType> list = this.find(hql);

		if (list == null || list.size() == 0)
			return null;

		return list;
	}

}
