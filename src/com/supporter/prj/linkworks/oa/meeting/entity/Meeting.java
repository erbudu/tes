package com.supporter.prj.linkworks.oa.meeting.entity;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.linkworks.oa.meeting.entity.base.BaseMeeting;
import com.supporter.util.CommonUtil;
import com.supporter.util.XCalendar;

@Entity
@Table(name = "OA_MEETING", schema = "")
public class Meeting extends BaseMeeting {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean add;

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public static Map<String, String> getMeetingTypeCodeTable() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems("MEETINGTYPE");
		// Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		// map.put(1, "内部会议");
		// map.put(2, "外部会议（内宾）");
		// map.put(3, "外部会议（外宾）");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}

	// 用于保存时获取前台的数据（保存参会人员）
	private String[] MeetingAttendEmps;

	@Transient
	public String[] getMeetingAttendEmps() {
		return MeetingAttendEmps;
	}

	public void setMeetingAttendEmps(String[] meetingAttendEmps) {
		MeetingAttendEmps = meetingAttendEmps;
	}
	private String notifierIds;
	@Transient
	public String getNotifierIds() {
		return notifierIds;
	}

	public void setNotifierIds(String notifierIds) {
		this.notifierIds = notifierIds;
	}
	//用于编辑时回显参会人员
	private String notifierNames;
	@Transient
	public String getNotifierNames() {
		return notifierNames;
	}

	public void setNotifierNames(String notifierNames) {
		this.notifierNames = notifierNames;
	}
	
	
	// 用于保存时获取前台的数据（保存会议所用设备）
	private String MeetingEqs;

	@Transient
	public String getMeetingEqs() {
		return MeetingEqs;
	}

	public void setMeetingEqs(String meetingEqs) {
		MeetingEqs = meetingEqs;
	}

	// 用于接受从数据库获取的参会人员
	private List<MeetingAttendEmp> listEmp;

	@Transient
	public List<MeetingAttendEmp> getListEmp() {
		return listEmp;
	}

	public void setListEmp(List<MeetingAttendEmp> listEmp) {
		this.listEmp = listEmp;
	}

	// 用于接受从数据库获取的会议所用设备
	private List<MeetingEq> listEq;

	@Transient
	public List<MeetingEq> getListEq() {
		return listEq;
	}

	public void setListEq(List<MeetingEq> listEq) {
		this.listEq = listEq;
	}

	// 用于查看时存储会议类型
	private String meetingTypeDesc;

	@Transient
	public String getMeetingTypeDesc() {
		return meetingTypeDesc;
	}

	public void setMeetingTypeDesc(String meetingTypeDesc) {
		this.meetingTypeDesc = meetingTypeDesc;
	}

	// 获取指定年度的指定周的第一天，由于习惯所限，将周一作为第一天
	public Date getFirstDateOfWeek(int ai_Year, int ai_WeekIndex) {
		Date ld_FirstDayOfYear = CommonUtil.parseDate(ai_Year + "-01-01");

		Date ld_Temp = XCalendar.relativeDate(ld_FirstDayOfYear,
				(ai_WeekIndex - 1) * 7);
		Date ld_WeekBegin = XCalendar.getWeekFirstDate(ld_Temp);

		return XCalendar.relativeDate(ld_WeekBegin, 1);
	}

	// 获取指定年度的指定周的最后一天，由于习惯所限，将周日作为第七天
	public Date getLastDateOfWeek(int ai_Year, int ai_WeekIndex) {
		Date ld_WeekBegin = getFirstDateOfWeek(ai_Year, ai_WeekIndex);
		Date ld_WeekEnd = XCalendar.getWeekLastDate(ld_WeekBegin);

		return XCalendar.relativeDate(ld_WeekEnd, 1);
	}

	// 获取指定日期属于所属年度的第几个周
	public int getWeekIndex(Date ad_Date) {
		int li_Year = CommonUtil
				.parseInt(CommonUtil.format(ad_Date, "yyyy"), 0);
		for (int i = 1; i <= 53; i++) {
			Date ld_WeekBegin = getFirstDateOfWeek(li_Year, i);
			Date ld_WeekEnd = getLastDateOfWeek(li_Year, i);

			boolean lbool_IsCurrentWeek = (ld_WeekBegin.compareTo(ad_Date) <= 0)
					&& (ld_WeekEnd.compareTo(ad_Date) >= 0);

			if (lbool_IsCurrentWeek)
				return i;
		}

		// 肯定出错了
		return 0;
	}

	private String li_YearSelect;
	private String weekIndexSelect;

	@Transient
	public String getLi_YearSelect() {
		return li_YearSelect;
	}

	public void setLi_YearSelect(String li_YearSelect) {
		this.li_YearSelect = li_YearSelect;
	}

	@Transient
	public String getWeekIndexSelect() {
		return weekIndexSelect;
	}

	public void setWeekIndexSelect(String weekIndexSelect) {
		this.weekIndexSelect = weekIndexSelect;
	}





}
