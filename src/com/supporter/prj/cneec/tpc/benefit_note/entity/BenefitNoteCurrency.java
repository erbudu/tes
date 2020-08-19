package com.supporter.prj.cneec.tpc.benefit_note.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.benefit_note.entity.base.BaseBenefitNoteCurrency;
import com.supporter.util.CommonUtil;

/**
 * @Title: BenefitNoteCurrency
 * @Description: 贸易项目效益测算币别表.
 * @author: yanweichao
 * @date: 2018-06-01
 * @version: V1.0
 */
@Entity
@Table(name = "TPC_BENEFIT_NOTE_CURRENCY", schema = "")
public class BenefitNoteCurrency extends BaseBenefitNoteCurrency implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	// Constructors

	/** default constructor */
	public BenefitNoteCurrency() {
		super();
	}

	/** minimal constructor */
	public BenefitNoteCurrency(String recordId) {
		super(recordId);
	}

	public BenefitNoteCurrency(String noteId, int orderDisplay) {
		super(noteId, orderDisplay);
	}

	@Transient
	public String getCurrencyDisplay() {
		return CommonUtil.trim(this.getCurrency()) + "[" + CommonUtil.format(this.getRate(), "#,###.0000") + "]";
	}

}
