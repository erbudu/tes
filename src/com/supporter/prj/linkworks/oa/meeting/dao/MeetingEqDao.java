package com.supporter.prj.linkworks.oa.meeting.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.meeting.entity.MeetingEq;

/**
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class MeetingEqDao extends MainDaoSupport<MeetingEq, String> {

	/**
	 * 分页查询
	 */
	public List<MeetingEq> findPage(JqGrid jqGrid, MeetingEq meeting) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 根据ID获取.
	 * 
	 * @param MeetingId
	 * @return
	 */
	public List<MeetingEq> findByMeetingId(String MeetingId) {
		String hql = "from " + MeetingEq.class.getName()
				+ " where meetingId = ?";
		List<MeetingEq> list = this.find(hql, MeetingId);

		if (list == null || list.size() == 0)
			return null;

		return list;
	}
	
	/**
	 * 根据ID获取.
	 * 
	 * @param MeetingId
	 * @return
	 */
	public List<MeetingEq> findByMeetingIdAndMeeingEqId(String MeetingId,String equipmentId) {
		String hql = "from " + MeetingEq.class.getName()
				+ " where meetingId = ? and equipmentId= ?";
		List<MeetingEq> list = this.find(hql, MeetingId,equipmentId);

		if (list == null || list.size() == 0)
			return null;

		return list;
	}

}
