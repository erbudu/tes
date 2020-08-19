package com.supporter.prj.cneec.tpc.benefit_note.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.benefit_note.entity.base.BaseVBenefitNote;
import com.supporter.prj.cneec.tpc.benefit_note.util.BenefitNoteUtil;

/**
 * @Title: VBenefitNote
 * @Description: 贸易项目效益测算表（最高且有效版本）.
 * @author: yanweichao
 * @date: 2018-06-01
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_V_BENEFIT_NOTE", schema = "")
public class VBenefitNote extends BaseVBenefitNote implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Constructors

	/** default constructor */
	public VBenefitNote() {
		super();
	}

	/** minimal constructor */
	public VBenefitNote(String noteId) {
		super(noteId);
	}

	private String keyword;// 搜索关键字

	@Transient
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public static final int CONFIRM = 10;// 待确认
	public static final int CONFIRMING = 20;// 确认中
	public static final int CONFIRMED = 30;// 已确认

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static Map<Integer, String> getSwfStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(CONFIRM, "待确认");
		map.put(CONFIRMING, "确认中");
		map.put(CONFIRMED, "已确认");
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

	// 版本
	@Transient
	public String getVersionsTypeDesc() {
		if (this.getVersionsType() != null) {
			return BenefitNoteUtil.getVersionsTypeMap().get(this.getVersionsType());
		}
		return "";
	}

	// 编号
	@Transient
	public String getBenefitNoDesc() {
		if (this.getBenefitNo() != null) {
			return this.getBenefitNo() + "-" + this.getVersions();
		}
		return "";
	}

	// 显示合同名称及状态
	@Transient
	public String getBenefitContractNameDesc() {
		if (this.getContractName() != null) {
			return this.getContractName() + "(" + this.getSwfStatusDesc() + ")";
		}
		return "";
	}

}
