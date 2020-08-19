package com.supporter.prj.ppm.ecc.project_ecc.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.ecc.project_ecc.entity.base.BaseEcc;
import com.supporter.util.CodeTable;

/**
 * @Title: Entity
 * @Description: 出口管制.
 * @author: YAN
 * @date: 2019-08-16
 * @version: V1.0
 */
@Entity
@Table(name = "PPM_ECC", schema = "")
public class Ecc extends BaseEcc implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 无参构造函数.
	 */
	public Ecc() {
		super();
	}


	public static final int NEEDECC = 0;//需要管制
	public static final int NOTNEED = 1;//不需要管制
	/**
	 * 构造函数.
	 * @param eccId
	 */
	public Ecc(String eccId) {
		super(eccId);
	}

	// 出口管制状态
	public static final class StatusCodeTable {
		/** 草稿 */
		public static final int DRAFT = 0;
		/** 管制中 */
		public static final int ECCING = 1;
		/** 管制完成，且通过 */
		public static final int COMPLETE = 2;
		/** 管制完成，且不通过 */
		public static final int COMPLETENO = 3;

		// 状态
		public static CodeTable getCodeTable() {
			CodeTable ct = new CodeTable();
			ct.insertItem(DRAFT, "草稿");
			ct.insertItem(ECCING, "管制中");
			ct.insertItem(COMPLETE, "管制完成,通过");
			ct.insertItem(COMPLETENO, "管制完成，不通过");
			return ct;
		}
	}

}
