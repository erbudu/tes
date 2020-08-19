package com.supporter.prj.cneec.tpc.deliver.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.deliver.entity.base.BaseDeliver;

/**
 * @Title: Deliver
 * @Description: 交付实体类
 * @author: yanweichao
 * @date: 2017-12-20
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_DELIVER", schema = "")
public class Deliver extends BaseDeliver implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCDELIVER";

	// Constructors

	/** default constructor */
	public Deliver() {
		super();
	}

	/** minimal constructor */
	public Deliver(String deliverId) {
		super(deliverId);
	}

	private boolean add;// 是否新增
	private String keyword;// 搜索关键字
	private DeliverInformation information;

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

	@Transient
	public DeliverInformation getInformation() {
		return information;
	}

	public void setInformation(DeliverInformation information) {
		this.information = information;
	}

	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 进行中
	public static final int COMPLETED = 20;// 完成

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static Map<Integer, String> getSwfStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		map.put(PROCESSING, "交付进行中");
		map.put(COMPLETED, "全部交付完成");
		return map;
	}

	// 状态
	@Transient
	public String getSwfStatusDesc() {
		if (this.getSwfStatus() != null) {
			return getSwfStatusMap().get(this.getSwfStatus());
		}
		return "";
	}


}
