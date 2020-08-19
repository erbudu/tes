package com.supporter.prj.ppm.tool_tips.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.tool_tips.entity.base.BaseToolTips;
import com.supporter.util.CommonUtil;

/**
 * @Title: ToolTips
 * @Description: 采购需求实体类
 * @author: liyinfeng
 * @date: 2018-07-13
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_TOOL_TIPS", schema = "")
public class ToolTips extends BaseToolTips implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TOOLTIPS";
	public static final String BUSI_TYPE = "TOOLTIPS";
	public static final String DOMAIN_OBJECT_ID = "Tips";
	public final static String TOOLTIPS = "TOOLTIPS";
	public final static String OPERATIONDESC = "OPERATIONDESC";
	public final static String LOG_TYPE = "TOOLTIPS";

	// Constructors

	/** default constructor */
	public ToolTips() {
		super();
	}

	/** minimal constructor */
	public ToolTips(String id) {
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



	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 审核中
	public static final int COMPLETED = 20;// 审批完成

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static Map<Integer, String> getStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(DRAFT, "草稿");
		//map.put(PROCESSING, "审核中");
		map.put(COMPLETED, "生效");
		return map;
	}

	// 状态
	@Transient
	public String getStatusDesc() {
		return getStatusMap().get(this.getStatus());
	}
	
	/**
	 * 获取类型码表.
	 * @return
	 */
	public static Map<String, String> getInputTypeMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(TOOLTIPS, "提示语");
		map.put(OPERATIONDESC, "操作说明");
		return map;
	}
	
	// 类型
	@Transient
	public String getInputTypeDesc() {
		return getInputTypeMap().get(this.getInputType());
	}

}
