package com.supporter.prj.linkworks.oa.meeting.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.meeting.entity.MeetingAttendEmp;
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
public class MeetingAttendEmpDao extends MainDaoSupport < MeetingAttendEmp, String > {

	/**
	 * 分页查询
	 */
	public List<MeetingAttendEmp> findPage(JqGrid jqGrid, MeetingAttendEmp meeting) {

		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 根据ID获取.
	 * @param MeetingId
	 * @return
	 */
	public List <MeetingAttendEmp> findByMeetingId(String MeetingId) {
		String hql = "from " + MeetingAttendEmp.class.getName() + " where meetingId = ?";
		List <MeetingAttendEmp> list = this.find(hql, MeetingId);
		
		if (list == null || list.size() == 0) return null;
		
		return list;
	}

	
	
	
	
}
