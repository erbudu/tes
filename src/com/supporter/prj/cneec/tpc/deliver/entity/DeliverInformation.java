package com.supporter.prj.cneec.tpc.deliver.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.deliver.entity.base.BaseDeliverInformation;

/**   
 * @Title: Entity
 * @Description: TPC_DELIVER_INFORMATION.
 * @author Yanweichao
 * @date 2017-12-20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_DELIVER_INFORMATION", schema = "")
public class DeliverInformation extends BaseDeliverInformation {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public DeliverInformation(){
		super();
	}

	/**
	 * 构造函数.
	 * @param informationId
	 */
	public DeliverInformation(String informationId) {
		super(informationId);
	}

	private boolean isNew = false;// 是否新增

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public static final int DRAFT = 0; // 草稿
	public static final int FINISH = 10; // 交付完成

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static Map<Integer, String> getStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(FINISH, "交付完成");
		return map;
	}

	// 状态
	@Transient
	public String getStatusDesc() {
		return getStatusMap().get(this.getStatus());
	}

}
