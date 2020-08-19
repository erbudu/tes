package com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.entity;

import com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.constant.BiddingPermissionStatusEnum;
import com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.entity.base.BaseBiddingPermission;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**   
 * @Title: Entity
 * @Description: 投标许可.
 * @author kangh_000
 * @date 2018-12-11 09:56:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "PC_PRE_BIDDING_PERMISSION", schema = "")
public class BiddingPermission extends BaseBiddingPermission {

	private static final long serialVersionUID = 1L;
	
	//----------------------------实体类扩展属性定义,注意属性前加@Transient注解标示非数据库字段----------------------------
	/**
	 *搜索关键词.
	 */
	@Transient
	private String keyword;

	@Transient
	private String biddingPermissionStatusDesc;
	
	//--------------------------------------实体枚举类定义-----------------------------------------
	
	
	//--------------------------------------构造函数定义-----------------------------------------
	/**
	 * 无参构造函数.
	 */
	public BiddingPermission(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param biddingPermissionId
	 */
	public BiddingPermission(String biddingPermissionId){
		super(biddingPermissionId);
	} 
	
	@Override
	public boolean equals(Object other)
	{		
		if ( !(other instanceof BiddingPermission) ){
			return false;
		}
		BiddingPermission castOther = (BiddingPermission) other;
		return StringUtils.equals(this.getBiddingPermissionId(), castOther.getBiddingPermissionId());
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append(getBiddingPermissionId()).toHashCode();
	}
	
	
	//--------------------------------------get和set方法-----------------------------------------
	/**
	 *方法: 取得keyword.
	 *@return: String  
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 *方法: 设置keyword.
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getBiddingPermissionStatusDesc() {
		if (StringUtils.isNotEmpty(biddingPermissionStatusDesc)) {
			return biddingPermissionStatusDesc;
		}

		for (BiddingPermissionStatusEnum biddingPermissionStatusEnum : BiddingPermissionStatusEnum.values()) {
			if (biddingPermissionStatusEnum.getInnerName() == getBiddingPermissionStatus()) {
				biddingPermissionStatusDesc = biddingPermissionStatusEnum.getDisplayName();

				break;
			}
		}

		return biddingPermissionStatusDesc;
	}

	public void setBiddingPermissionStatusDesc(String biddingPermissionStatusDesc) {
		this.biddingPermissionStatusDesc = biddingPermissionStatusDesc;
	}
}
