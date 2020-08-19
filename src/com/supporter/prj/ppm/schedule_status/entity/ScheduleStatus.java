package com.supporter.prj.ppm.schedule_status.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.schedule_status.entity.base.BaseScheduleStatus;

/**
 * @Title: ScheduleStatus
 * @Description: 采购需求实体类
 * @author: liyinfeng
 * @date: 2018-07-13
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_SCHEDULE_STATUS", schema = "")
public class ScheduleStatus extends BaseScheduleStatus implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "SCHEDULE";
	public static final String BUSI_TYPE = "SCHEDULE";
	public static final String DOMAIN_OBJECT_ID = "ScheduleStatus";

	// Constructors

	/** default constructor */
	public ScheduleStatus() {
		super();
	}

	/** minimal constructor */
	public ScheduleStatus(String id) {
		super(id);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


}
