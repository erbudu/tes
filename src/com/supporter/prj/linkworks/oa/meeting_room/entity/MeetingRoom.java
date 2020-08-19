package com.supporter.prj.linkworks.oa.meeting_room.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.meeting_room.entity.base.BaseMeetingRoom;

@Entity
@Table(name = "OA_MEETING_ROOM", schema = "")
public class MeetingRoom extends BaseMeetingRoom {

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

	private List<MeetingRoomEq> list;

	@Transient
	public List<MeetingRoomEq> getList() {
		return list;
	}

	public void setList(List<MeetingRoomEq> list) {
		this.list = list;
	}

	private String delIds;

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	@Transient
	public String getDelIds() {
		return delIds;
	}

}
