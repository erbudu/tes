package com.supporter.prj.ppm.prj_org.member_duty.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prj_org.member_duty.constant.MemberDutyConstant;
import com.supporter.prj.ppm.prj_org.member_duty.entity.base.BaseMemberDutyEntity;

/**
 * 项目各业务负责人是实体类扩展
 * @author CHENHAO
 * @date 2019年12月2日
 */
@Entity
@Table(name="PPM_PRJ_MEMBER_DUTY",schema = "")
public class MemberDutyEntity extends BaseMemberDutyEntity{

	private static final long serialVersionUID = 1L;
	
	@Transient
	private boolean isNew;					//用于判断是否是新建
	
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	public String getResponsibleDisplayName() {
		
		return MemberDutyConstant.getResponsibleDisplayName(this.getResponsible());
	}
	
	public String getStatusName() {
		
		if(this.getStatus() == null) {
			
			return null;
			
		}else if(this.getStatus() == 0) {
			
			return "草稿";
			
		}else if(this.getStatus() == 1) {
			
			return "生效";
		}
		return null;
	}
}
