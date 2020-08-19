package com.supporter.prj.linkworks.oa.meeting.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.linkworks.oa.meeting.entity.base.BaseMeetingType;

@Entity
@Table(name="OA_MEETING_TYPE"
    ,schema=""
)
public class MeetingType extends BaseMeetingType{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
