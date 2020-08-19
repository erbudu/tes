package com.supporter.prj.ppm.meeting_notice.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.meeting_notice.constant.MeetingNoticeContant;
import com.supporter.prj.ppm.meeting_notice.entity.base.BaseMeetingNoticeEntity;


/**
 *<p>Title: 会议通知实体类扩展类</p>
 *<p>Description: 业务单中用到的其他字段信息及方法，不跟数据库做对应关系</p>
 *<p>Company: </p>
 * @author CHENHAO
 * @date 2019年11月25日
 * 
 */

@Entity
@Table(name="PPM_MEETING_NOTICE",schema = "")
public class MeetingNoticeEntity extends BaseMeetingNoticeEntity{

	@Transient
	private boolean isNew;					//用于判断是否是新建
	
	
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	@Transient
	public String getStatusName() {
		
		if(this.getStatus() != null) {
			
			if(this.getStatus() == MeetingNoticeContant.CREATE_MEETING) {
				
				return MeetingNoticeContant.DRAFT;
				
			}else if(this.getStatus() == MeetingNoticeContant.MEETING_VIEW) {
				
				return MeetingNoticeContant.NOTICED;
			}
		}
		
		return null;
	}
	
}
