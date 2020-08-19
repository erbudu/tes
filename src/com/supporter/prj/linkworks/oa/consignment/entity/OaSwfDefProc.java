package com.supporter.prj.linkworks.oa.consignment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SwfDefProc entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OA_SWF_DEF_PROC", schema = "")
public class OaSwfDefProc implements java.io.Serializable {

	// Fields

	private Long procDefId;
	private String procName;
	private String procInnerName;
	private String procDesc;
	private String handlerOnAbort;
	private String handlerOnCompl;
	private String dataField1;
	private String dfDesc1;
	private String dataField2;
	private String dfDesc2;
	private String dataField3;
	private String dfDesc3;
	private String dfDesc4;
	private String dataField4;
	private String dfDesc5;
	private String dataField5;
	private String dfDesc6;
	private String dataField6;
	private String dfDesc7;
	private String dataField7;
	private String dfDesc8;
	private String dataField8;
	private String dfDesc9;
	private String dataField9;
	private String dataField10;
	private String dfDesc10;
	private String dataField11;
	private String dfDesc11;
	private String dataField12;
	private String dfDesc12;
	private String dataField13;
	private String dfDesc13;
	private String dataField14;
	private String dfDesc14;
	private String dataField15;
	private String dfDesc15;
	private String dataField16;
	private String dfDesc16;
	private String dataField17;
	private String dfDesc17;
	private String dataField18;
	private String dfDesc18;
	private String dataField19;
	private String dfDesc19;
	private String dataField20;
	private String dfDesc20;
	private String dataViewerUrl;

	// Constructors

	/** default constructor */
	public OaSwfDefProc() {
	}

	/** minimal constructor */
	public OaSwfDefProc(Long procDefId, String procName) {
		this.procDefId = procDefId;
		this.procName = procName;
	}

	/** full constructor */
	public OaSwfDefProc(Long procDefId, String procName, String procInnerName,
			String procDesc, String handlerOnAbort, String handlerOnCompl,
			String dataField1, String dfDesc1, String dataField2,
			String dfDesc2, String dataField3, String dfDesc3, String dfDesc4,
			String dataField4, String dfDesc5, String dataField5,
			String dfDesc6, String dataField6, String dfDesc7,
			String dataField7, String dfDesc8, String dataField8,
			String dfDesc9, String dataField9, String dataField10,
			String dfDesc10, String dataField11, String dfDesc11,
			String dataField12, String dfDesc12, String dataField13,
			String dfDesc13, String dataField14, String dfDesc14,
			String dataField15, String dfDesc15, String dataField16,
			String dfDesc16, String dataField17, String dfDesc17,
			String dataField18, String dfDesc18, String dataField19,
			String dfDesc19, String dataField20, String dfDesc20,
			String dataViewerUrl) {
		this.procDefId = procDefId;
		this.procName = procName;
		this.procInnerName = procInnerName;
		this.procDesc = procDesc;
		this.handlerOnAbort = handlerOnAbort;
		this.handlerOnCompl = handlerOnCompl;
		this.dataField1 = dataField1;
		this.dfDesc1 = dfDesc1;
		this.dataField2 = dataField2;
		this.dfDesc2 = dfDesc2;
		this.dataField3 = dataField3;
		this.dfDesc3 = dfDesc3;
		this.dfDesc4 = dfDesc4;
		this.dataField4 = dataField4;
		this.dfDesc5 = dfDesc5;
		this.dataField5 = dataField5;
		this.dfDesc6 = dfDesc6;
		this.dataField6 = dataField6;
		this.dfDesc7 = dfDesc7;
		this.dataField7 = dataField7;
		this.dfDesc8 = dfDesc8;
		this.dataField8 = dataField8;
		this.dfDesc9 = dfDesc9;
		this.dataField9 = dataField9;
		this.dataField10 = dataField10;
		this.dfDesc10 = dfDesc10;
		this.dataField11 = dataField11;
		this.dfDesc11 = dfDesc11;
		this.dataField12 = dataField12;
		this.dfDesc12 = dfDesc12;
		this.dataField13 = dataField13;
		this.dfDesc13 = dfDesc13;
		this.dataField14 = dataField14;
		this.dfDesc14 = dfDesc14;
		this.dataField15 = dataField15;
		this.dfDesc15 = dfDesc15;
		this.dataField16 = dataField16;
		this.dfDesc16 = dfDesc16;
		this.dataField17 = dataField17;
		this.dfDesc17 = dfDesc17;
		this.dataField18 = dataField18;
		this.dfDesc18 = dfDesc18;
		this.dataField19 = dataField19;
		this.dfDesc19 = dfDesc19;
		this.dataField20 = dataField20;
		this.dfDesc20 = dfDesc20;
		this.dataViewerUrl = dataViewerUrl;
	}

	// Property accessors
	@Id
	@Column(name = "PROC_DEF_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getProcDefId() {
		return this.procDefId;
	}

	public void setProcDefId(Long procDefId) {
		this.procDefId = procDefId;
	}

	@Column(name = "PROC_NAME", nullable = false, length = 32)
	public String getProcName() {
		return this.procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	@Column(name = "PROC_INNER_NAME", length = 32)
	public String getProcInnerName() {
		return this.procInnerName;
	}

	public void setProcInnerName(String procInnerName) {
		this.procInnerName = procInnerName;
	}

	@Column(name = "PROC_DESC")
	public String getProcDesc() {
		return this.procDesc;
	}

	public void setProcDesc(String procDesc) {
		this.procDesc = procDesc;
	}

	@Column(name = "HANDLER_ON_ABORT", length = 64)
	public String getHandlerOnAbort() {
		return this.handlerOnAbort;
	}

	public void setHandlerOnAbort(String handlerOnAbort) {
		this.handlerOnAbort = handlerOnAbort;
	}

	@Column(name = "HANDLER_ON_COMPL", length = 64)
	public String getHandlerOnCompl() {
		return this.handlerOnCompl;
	}

	public void setHandlerOnCompl(String handlerOnCompl) {
		this.handlerOnCompl = handlerOnCompl;
	}

	@Column(name = "DATA_FIELD_1", length = 32)
	public String getDataField1() {
		return this.dataField1;
	}

	public void setDataField1(String dataField1) {
		this.dataField1 = dataField1;
	}

	@Column(name = "DF_DESC_1", length = 32)
	public String getDfDesc1() {
		return this.dfDesc1;
	}

	public void setDfDesc1(String dfDesc1) {
		this.dfDesc1 = dfDesc1;
	}

	@Column(name = "DATA_FIELD_2", length = 32)
	public String getDataField2() {
		return this.dataField2;
	}

	public void setDataField2(String dataField2) {
		this.dataField2 = dataField2;
	}

	@Column(name = "DF_DESC_2", length = 32)
	public String getDfDesc2() {
		return this.dfDesc2;
	}

	public void setDfDesc2(String dfDesc2) {
		this.dfDesc2 = dfDesc2;
	}

	@Column(name = "DATA_FIELD_3", length = 32)
	public String getDataField3() {
		return this.dataField3;
	}

	public void setDataField3(String dataField3) {
		this.dataField3 = dataField3;
	}

	@Column(name = "DF_DESC_3", length = 32)
	public String getDfDesc3() {
		return this.dfDesc3;
	}

	public void setDfDesc3(String dfDesc3) {
		this.dfDesc3 = dfDesc3;
	}

	@Column(name = "DF_DESC_4", length = 32)
	public String getDfDesc4() {
		return this.dfDesc4;
	}

	public void setDfDesc4(String dfDesc4) {
		this.dfDesc4 = dfDesc4;
	}

	@Column(name = "DATA_FIELD_4", length = 32)
	public String getDataField4() {
		return this.dataField4;
	}

	public void setDataField4(String dataField4) {
		this.dataField4 = dataField4;
	}

	@Column(name = "DF_DESC_5", length = 32)
	public String getDfDesc5() {
		return this.dfDesc5;
	}

	public void setDfDesc5(String dfDesc5) {
		this.dfDesc5 = dfDesc5;
	}

	@Column(name = "DATA_FIELD_5", length = 32)
	public String getDataField5() {
		return this.dataField5;
	}

	public void setDataField5(String dataField5) {
		this.dataField5 = dataField5;
	}

	@Column(name = "DF_DESC_6", length = 32)
	public String getDfDesc6() {
		return this.dfDesc6;
	}

	public void setDfDesc6(String dfDesc6) {
		this.dfDesc6 = dfDesc6;
	}

	@Column(name = "DATA_FIELD_6", length = 32)
	public String getDataField6() {
		return this.dataField6;
	}

	public void setDataField6(String dataField6) {
		this.dataField6 = dataField6;
	}

	@Column(name = "DF_DESC_7", length = 32)
	public String getDfDesc7() {
		return this.dfDesc7;
	}

	public void setDfDesc7(String dfDesc7) {
		this.dfDesc7 = dfDesc7;
	}

	@Column(name = "DATA_FIELD_7", length = 32)
	public String getDataField7() {
		return this.dataField7;
	}

	public void setDataField7(String dataField7) {
		this.dataField7 = dataField7;
	}

	@Column(name = "DF_DESC_8", length = 32)
	public String getDfDesc8() {
		return this.dfDesc8;
	}

	public void setDfDesc8(String dfDesc8) {
		this.dfDesc8 = dfDesc8;
	}

	@Column(name = "DATA_FIELD_8", length = 32)
	public String getDataField8() {
		return this.dataField8;
	}

	public void setDataField8(String dataField8) {
		this.dataField8 = dataField8;
	}

	@Column(name = "DF_DESC_9", length = 32)
	public String getDfDesc9() {
		return this.dfDesc9;
	}

	public void setDfDesc9(String dfDesc9) {
		this.dfDesc9 = dfDesc9;
	}

	@Column(name = "DATA_FIELD_9", length = 32)
	public String getDataField9() {
		return this.dataField9;
	}

	public void setDataField9(String dataField9) {
		this.dataField9 = dataField9;
	}

	@Column(name = "DATA_FIELD_10", length = 32)
	public String getDataField10() {
		return this.dataField10;
	}

	public void setDataField10(String dataField10) {
		this.dataField10 = dataField10;
	}

	@Column(name = "DF_DESC_10", length = 32)
	public String getDfDesc10() {
		return this.dfDesc10;
	}

	public void setDfDesc10(String dfDesc10) {
		this.dfDesc10 = dfDesc10;
	}

	@Column(name = "DATA_FIELD_11", length = 32)
	public String getDataField11() {
		return this.dataField11;
	}

	public void setDataField11(String dataField11) {
		this.dataField11 = dataField11;
	}

	@Column(name = "DF_DESC_11", length = 32)
	public String getDfDesc11() {
		return this.dfDesc11;
	}

	public void setDfDesc11(String dfDesc11) {
		this.dfDesc11 = dfDesc11;
	}

	@Column(name = "DATA_FIELD_12", length = 32)
	public String getDataField12() {
		return this.dataField12;
	}

	public void setDataField12(String dataField12) {
		this.dataField12 = dataField12;
	}

	@Column(name = "DF_DESC_12", length = 32)
	public String getDfDesc12() {
		return this.dfDesc12;
	}

	public void setDfDesc12(String dfDesc12) {
		this.dfDesc12 = dfDesc12;
	}

	@Column(name = "DATA_FIELD_13", length = 32)
	public String getDataField13() {
		return this.dataField13;
	}

	public void setDataField13(String dataField13) {
		this.dataField13 = dataField13;
	}

	@Column(name = "DF_DESC_13", length = 32)
	public String getDfDesc13() {
		return this.dfDesc13;
	}

	public void setDfDesc13(String dfDesc13) {
		this.dfDesc13 = dfDesc13;
	}

	@Column(name = "DATA_FIELD_14", length = 32)
	public String getDataField14() {
		return this.dataField14;
	}

	public void setDataField14(String dataField14) {
		this.dataField14 = dataField14;
	}

	@Column(name = "DF_DESC_14", length = 32)
	public String getDfDesc14() {
		return this.dfDesc14;
	}

	public void setDfDesc14(String dfDesc14) {
		this.dfDesc14 = dfDesc14;
	}

	@Column(name = "DATA_FIELD_15", length = 32)
	public String getDataField15() {
		return this.dataField15;
	}

	public void setDataField15(String dataField15) {
		this.dataField15 = dataField15;
	}

	@Column(name = "DF_DESC_15", length = 32)
	public String getDfDesc15() {
		return this.dfDesc15;
	}

	public void setDfDesc15(String dfDesc15) {
		this.dfDesc15 = dfDesc15;
	}

	@Column(name = "DATA_FIELD_16", length = 32)
	public String getDataField16() {
		return this.dataField16;
	}

	public void setDataField16(String dataField16) {
		this.dataField16 = dataField16;
	}

	@Column(name = "DF_DESC_16", length = 32)
	public String getDfDesc16() {
		return this.dfDesc16;
	}

	public void setDfDesc16(String dfDesc16) {
		this.dfDesc16 = dfDesc16;
	}

	@Column(name = "DATA_FIELD_17", length = 32)
	public String getDataField17() {
		return this.dataField17;
	}

	public void setDataField17(String dataField17) {
		this.dataField17 = dataField17;
	}

	@Column(name = "DF_DESC_17", length = 32)
	public String getDfDesc17() {
		return this.dfDesc17;
	}

	public void setDfDesc17(String dfDesc17) {
		this.dfDesc17 = dfDesc17;
	}

	@Column(name = "DATA_FIELD_18", length = 32)
	public String getDataField18() {
		return this.dataField18;
	}

	public void setDataField18(String dataField18) {
		this.dataField18 = dataField18;
	}

	@Column(name = "DF_DESC_18", length = 32)
	public String getDfDesc18() {
		return this.dfDesc18;
	}

	public void setDfDesc18(String dfDesc18) {
		this.dfDesc18 = dfDesc18;
	}

	@Column(name = "DATA_FIELD_19", length = 32)
	public String getDataField19() {
		return this.dataField19;
	}

	public void setDataField19(String dataField19) {
		this.dataField19 = dataField19;
	}

	@Column(name = "DF_DESC_19", length = 32)
	public String getDfDesc19() {
		return this.dfDesc19;
	}

	public void setDfDesc19(String dfDesc19) {
		this.dfDesc19 = dfDesc19;
	}

	@Column(name = "DATA_FIELD_20", length = 32)
	public String getDataField20() {
		return this.dataField20;
	}

	public void setDataField20(String dataField20) {
		this.dataField20 = dataField20;
	}

	@Column(name = "DF_DESC_20", length = 32)
	public String getDfDesc20() {
		return this.dfDesc20;
	}

	public void setDfDesc20(String dfDesc20) {
		this.dfDesc20 = dfDesc20;
	}

	@Column(name = "DATA_VIEWER_URL", length = 128)
	public String getDataViewerUrl() {
		return this.dataViewerUrl;
	}

	public void setDataViewerUrl(String dataViewerUrl) {
		this.dataViewerUrl = dataViewerUrl;
	}

}