package com.supporter.prj.linkworks.oa.meeting.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.meeting.entity.Meeting;
import com.supporter.util.CommonUtil;

/**
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class MeetingDao extends MainDaoSupport<Meeting, String> {

	/**
	 * 分页查询
	 */
	public List<Meeting> findPage(Meeting meeting) {
		List<Meeting> list = new ArrayList<Meeting>();
		String hql = "from " + Meeting.class.getName() + " where 1 = 1 and ";
		if (meeting != null) {
			String meetingRoomId = meeting.getMeetingRoomId();
			if (StringUtils.isNotBlank(meetingRoomId)) {

				hql = hql + " meetingRoomId ='" + meetingRoomId + "'";
			}

			String periodPoints = meeting.getPeriodPoints();
			if (StringUtils.isNotBlank(periodPoints)) {
				hql = hql + " periodPoints like '%" + periodPoints + "%'";
			}

			// 根据前台的年份和周数查询Meeting
			int li_Year = Integer.parseInt(meeting.getLi_YearSelect());

			int weekIndex = Integer.parseInt(meeting.getWeekIndexSelect());

			// 获取第weekIndex周的第一天
			Date ld_WeekBegin = meeting.getFirstDateOfWeek(li_Year, weekIndex);

			// 获取第weekIndex周的最后一天
			Date ld_WeekEnd = meeting.getLastDateOfWeek(li_Year, weekIndex);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			String ld_WeekBeginZ = format1.format(ld_WeekBegin);
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
			String ld_WeekEndZ = format2.format(ld_WeekEnd);			
			hql = hql
					+ "to_date(expStartDate,'yyyy-MM-dd HH24:MI:SS')>=to_date('"
					+ ld_WeekBeginZ
					+ "','yyyy-MM-dd HH24:MI:SS') and to_date(expStartDate,'yyyy-MM-dd HH24:MI:SS')<=to_date('"
					+ ld_WeekEndZ + "','yyyy-MM-dd HH24:MI:SS')";
			list = this.find(hql);
		}

		return list;
	}

	/**
	 * 根据ID获取.
	 * 
	 * @param MeetingId
	 * @return
	 */
	public List<Meeting> getByRoomId(String roomId, String expStartDate) {	
		if (!roomId.equals("") && !expStartDate.equals("")) {
			String expStartDateZ=CommonUtil.format(expStartDate, "yyyy-MM-dd");
			String hql = "from " + Meeting.class.getName()
					+ " where meetingRoomId = ? and expStartDate like ? ";
			List<Meeting> list = this.find(hql, roomId, "%"+expStartDateZ+"%");
			if (list == null || list.size() == 0)
				return null;

			return list;
		} else {// 说明是从查看页面跳转到预定页面的
			return null;
		}
	}

}
