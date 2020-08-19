package com.supporter.prj.cneec.tpc.benefit_note.constant;

/**
 * @Title: BenefitNoteConstant
 * @Description: 效益测算变更常量
 * @author: yanweichao
 * @date: 2018-6-4
 * @version: V1.0
 */
public interface BenefitNoteConstant {

	/** 效益预算表模板  **/
	public static final String BENEFIT_NOTE_EXCEL_PATH = "/tpc/benefit_note/template_excel/";
	public static final String BENEFIT_NOTE_TEMP = "benefit_note";
	public static final String BENEFIT_NOTE_TEMP0 = "benefit_note_0";

	/** 效益预算表变更版本  **/
	public static final String VERSIONS_OF_DEPT_REVIEW = "VERSIONS_OF_DEPT_REVIEW";// 事业部版
	public static final String VERSIONS_OF_COMPANY_REVIEW = "VERSIONS_OF_COMPANY_REVIEW";// 公司版

	/** 效益预算表默认币别数量  **/
	public static final int CURRENCY_NUM = 3;

}
