package com.supporter.prj.linkworks.oa.meeting_room.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.meeting_room.entity.MeetingRoom;

/**
 * @Title: Entity
 * @Description: 功能模块
 * @author jiaotilei
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class MeetingRoomDao extends MainDaoSupport<MeetingRoom, String> {

	public Long getCount() {

		String hql = "select max(displayOrder) from "
				+ MeetingRoom.class.getName();
		List<Long> list = this.find(hql);
		if (list.size() > 0) {
			if (list.get(0) != null && !list.get(0).equals("")) {
				return list.get(0);
			} else {
				return Long.valueOf(0);
			}
		}
		return null;
	}

	/**
	 * 分页查询
	 */
	public List<MeetingRoom> findPage(UserProfile user,JqGrid jqGrid, MeetingRoom meetingRoom) {
		if (meetingRoom != null) {
			String roomName = meetingRoom.getRoomName();
			if (StringUtils.isNotBlank(roomName)) {
				jqGrid.addHqlFilter(" roomName like ? ", "%" + roomName + "%");
			} 
		}
		jqGrid.addSortPropertyAsc("displayOrder");
		
//		String authHql = "";
//		//权限过滤(验证是否有权限获取列表)
//		authHql = EIPService.getAuthorityService().getHqlFilter(user,MeetingRoomConstant.MODULE_ID,BankaccountOpenAuthConstant.AUTH_OPER_NAME_AUTHMEETINGROOMOFLIST );
//		jqGrid.addHqlFilter(authHql);		
		return this.retrievePage(jqGrid);
	}

	// 获取所有的会议室id和会议室
	public List<Object[]> findMeetingRoom() {
		String hql = "select roomId,roomName from "
				+ MeetingRoom.class.getName()+" order by displayOrder";
		List<Object[]> list = this.find(hql);
		return list;
	}

}
