package com.supporter.prj.linkworks.oa.doc_in.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.linkworks.oa.doc_in.entity.base.BaseDocIn;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "OA_DOC_IN", schema = "")
public class DocIn extends BaseDocIn implements IBusiEntity{
	private static final long serialVersionUID = 1L;
	public static final int IN = 1;
	public static final int OUT = 2;

	public static final int DRAFT = 0;
	public static final int PROCESSING = 1;
	public static final int COMPLETE = 2;
	public static final int ARCHIVED = 3;

	public static final int CONFIDENTIAL_RANK_0 = 0;// 无
	public static final int CONFIDENTIAL_RANK_1 = 1;// 秘密
	public static final int CONFIDENTIAL_RANK_2 = 2;// 机密
	public static final int CONFIDENTIAL_RANK_3 = 3;// 绝密

	public static final int NORMAL = 0; // 普通
	public static final int EMERGENT = 1;// 急件

	public static final int DANDGBAN = 1;
	public static final int XINGZHENG = 2;

	public static final String DANDGBAN_DESC = "党办收文";
	public static final String XINGZHENG_DESC = "行政收文";

	private String docStatusDesc;
	private String confidentialRankDesc;
	private String emergencyTypeDesc;
	private String docClassifyDesc;
	private boolean add;
	private long oldProcId = -1;
	/**
	 * 获取文档状态码表.
	 * 
	 * @return
	 */
	public static CodeTable getDocStatusCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(PROCESSING, "处理中");
		lcdtbl_Return.insertItem(COMPLETE, "已完成");
		lcdtbl_Return.insertItem(ARCHIVED, "已归档");

		return lcdtbl_Return;
	}

	/**
	 * 获取公文密级码表.
	 * 
	 * @return
	 */
	public static CodeTable getConfidentialRankCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(CONFIDENTIAL_RANK_0, "", 0);
		lcdtbl_Return.insertItem(CONFIDENTIAL_RANK_1, "秘密", 0);
		lcdtbl_Return.insertItem(CONFIDENTIAL_RANK_2, "机密", 0);
		lcdtbl_Return.insertItem(CONFIDENTIAL_RANK_3, "绝密", 0);
		return lcdtbl_Return;
	}

	/**
	 * 获取收文类型码表.
	 * 
	 * @return
	 */

	public static CodeTable getEmergencyTypeCodeTable() {
		CodeTable lcdtb_Return = new CodeTable();
		lcdtb_Return.insertItem(NORMAL, "普通", 0);
		lcdtb_Return.insertItem(EMERGENT, "急件", 0);
		return lcdtb_Return;
	}

	/**
	 * 获取收文类型码表.
	 * 
	 * @return
	 */

	public static CodeTable getDisplayEmergencyTypeCodeTable() {
		CodeTable lcdtb_Return = new CodeTable();
		lcdtb_Return.insertItem(NORMAL, "", 0);
		lcdtb_Return.insertItem(EMERGENT, "急件", 0);
		return lcdtb_Return;
	}

	@Transient
	public String getDocStatusDesc() {
		Integer status = getDocStatus();
		if (status != null) {
			return getDocStatusCodeTable().getDisplay(status);
		} else {
			return null;
		}
	}

	public void setDocStatusDesc(String docStatusDesc) {
		this.docStatusDesc = docStatusDesc;
	}

	@Transient
	public String getConfidentialRankDesc() {
		Long confidentialRank = getConfidentialRank();
		if (confidentialRank != null) {
			return getConfidentialRankCodeTable().getDisplay(confidentialRank);
		} else {
			return null;
		}
	}

	public void setConfidentialRankDesc(String confidentialRankDesc) {
		this.confidentialRankDesc = confidentialRankDesc;
	}

	@Transient
	public String getEmergencyTypeDesc() {
		String emergencyType = getEmergencyType();
		if (emergencyType != null) {
			return getEmergencyTypeCodeTable().getDisplay(
					Long.valueOf(emergencyType));
		} else {
			return null;
		}
	}

	public void setEmergencyTypeDesc(String emergencyTypeDesc) {
		this.emergencyTypeDesc = emergencyTypeDesc;
	}

	@Transient
	public String getDocClassifyDesc() {
		Long docClassify = this.getDocClassify();
		if (docClassify != null && docClassify == this.DANDGBAN) {
			return this.DANDGBAN_DESC;
		}
		if (docClassify != null && docClassify == this.XINGZHENG) {
			return this.XINGZHENG_DESC;
		}
		return "";
	}

	public void setDocClassifyDesc(String docClassifyDesc) {
		this.docClassifyDesc = docClassifyDesc;
	}

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}
	@Transient
	public int getDbYear() {
		return 0;
	}

	@Transient
	public String getDomainObjectId() {
		return "DOCIN";
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleId() {
		return "DOCIN";
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getCompanyNo() {
		return getDeptId();
	}

	@Override
	@Transient
	public String getKeyProps() {
		// TODO Auto-generated method stub
		return "doc_id";
	}

	@Override
	@Transient
	public String getKeyValues() {
		// TODO Auto-generated method stub
		return this.getDocId();
	}
	@Transient
	public long getOldProcId(){
		return oldProcId;
	}
	public void setOldProcId(long procId){
		this.oldProcId = procId;
	}
}
