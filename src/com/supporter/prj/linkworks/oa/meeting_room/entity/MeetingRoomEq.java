package com.supporter.prj.linkworks.oa.meeting_room.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.meeting_room.entity.base.BaseMeetingRoomEq;

@Entity
@Table(name = "OA_MEETING_ROOM_EQ", schema = "")
public class MeetingRoomEq extends BaseMeetingRoomEq {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long displayOrderDesc;

	@Transient
	public Long getDisplayOrderDesc() {
		return displayOrderDesc;
	}

	public void setDisplayOrderDesc(Long displayOrderDesc) {
		this.displayOrderDesc = displayOrderDesc;
	}

}
