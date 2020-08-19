package com.supporter.prj.linkworks.oa.meeting_room.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

import com.supporter.prj.linkworks.oa.meeting_room.entity.MeetingRoomEq;

/**
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class MeetingRoomEqDao extends MainDaoSupport<MeetingRoomEq, String> {
	/**
	 * 根据ID获取.
	 * 
	 * @param roomId
	 * @return
	 */
	public List<MeetingRoomEq> getByRoomId(String roomId) {
		String hql = "from " + MeetingRoomEq.class.getName()
				+ " where meetingRoomId = ?";
		List<MeetingRoomEq> list = this.find(hql, roomId);

		if (list == null || list.size() == 0)
			return null;

		return list;
	}

	/**
	 * 根据会议室id获取会议室设备
	 * 
	 * @param jqGrid
	 * @param ap
	 * @param recordId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MeetingRoomEq> getEquipMentGrid(JqGrid jqGrid, String roomId) {
		jqGrid.addHqlFilter("meetingRoomId = ?  ", roomId);
		List<MeetingRoomEq> list = this.retrievePage(jqGrid);
		jqGrid.setRows(list);
		return list;

	}

}
