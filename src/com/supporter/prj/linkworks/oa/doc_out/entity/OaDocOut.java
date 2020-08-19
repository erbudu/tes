package com.supporter.prj.linkworks.oa.doc_out.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.linkworks.oa.doc_out.entity.base.BaseOaDocOut;
import com.supporter.prj.linkworks.oa.doc_out.service.OaDocOutService;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "OA_DOC_OUT", schema = "")
public class OaDocOut extends BaseOaDocOut implements IBusiEntity{
	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "DOCOUT";
	public static final String DOMAIN_OBJECT_ID = "DOCOUT";

	public static final int DRAFT = 0; // 草稿
	public static final int ABORTED = 4; // 流程终止
	public static final int PROCESSING = 1; // 审核中
	public static final int PUBLISHED = 2; // 已发布
	public static final int ARCHIVED = 3; // 已归档

	public static final String FILE = "文件";
	public static final String ORDER = "命令";
	public static final String DICISION = "决定";
	public static final String BULLETIN = "公告";
	public static final String ANNOUNCEMENT = "通告";
	public static final String NOTICE = "通知";
	public static final String NOTIFICATION = "通报";
	public static final String PROPOSAL = "议案";
	public static final String REPORT = "报告";
	public static final String ASK = "请示";
	public static final String REPLY = "批复";
	public static final String OPINION = "意见";
	public static final String LETTER = "函";
	public static final String MEETING_SUM = "会议纪要";
	public static final String OTHER = "其它";

	public static final int CONFIDENTIAL_RANK_0 = 0;// 无
	public static final int CONFIDENTIAL_RANK_1 = 1;// 秘密
	public static final int CONFIDENTIAL_RANK_2 = 2;// 机密
	public static final int CONFIDENTIAL_RANK_3 = 3;// 绝密

	private String docStatusDesc;
	private String distriOut;
	private boolean add;
	private String confidentialRankDesc;
	private String tem;	
	private String userName;
	private long oldProcId = -1;
	@Transient
	public String getTem() {
		return tem;
	}

	public void setTem(String tem) {
		this.tem = tem;
	}

	@Transient
	public String getConfidentialRankDesc() {
		Long rank = getConfidentialRank();
		if (rank != null) {
			return getConfidentialRankCodeTable().getDisplay(rank);
		} else {
			return null;
		}

	}

	public void setConfidentialRankDesc(String confidentialRankDesc) {
		this.confidentialRankDesc = confidentialRankDesc;
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

	/**
	 * 获取发文文档的状态.
	 * 
	 * @return
	 */
	public static CodeTable getOutDocStatus() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(PROCESSING, "审核中");
		lcdtbl_Return.insertItem(PUBLISHED, "已发布");
		lcdtbl_Return.insertItem(ARCHIVED, "已归档");
		lcdtbl_Return.insertItem(ABORTED, "流程终止");
		return lcdtbl_Return;
	}

	/**
	 * 获取文档的状态码表.
	 * 
	 * @return
	 */
	public static CodeTable getDocStatusCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(PROCESSING, "审核中");
		lcdtbl_Return.insertItem(PUBLISHED, "已发布");
		lcdtbl_Return.insertItem(ARCHIVED, "已归档");
		lcdtbl_Return.insertItem(ABORTED, "流程终止");
		return lcdtbl_Return;
	}

	/**
	 * 获取文档类型的码表.
	 * 
	 * @return
	 */
	public static CodeTable getDocTypeCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(FILE, FILE, 0);
		lcdtbl_Return.insertItem(ORDER, ORDER, 0);
		lcdtbl_Return.insertItem(DICISION, DICISION, 0);
		lcdtbl_Return.insertItem(BULLETIN, BULLETIN, 0);
		lcdtbl_Return.insertItem(ANNOUNCEMENT, ANNOUNCEMENT, 0);
		lcdtbl_Return.insertItem(NOTICE, NOTICE, 0);
		lcdtbl_Return.insertItem(NOTIFICATION, NOTIFICATION, 0);
		lcdtbl_Return.insertItem(PROPOSAL, PROPOSAL, 0);
		lcdtbl_Return.insertItem(REPORT, REPORT, 0);
		lcdtbl_Return.insertItem(ASK, ASK, 0);
		lcdtbl_Return.insertItem(REPLY, REPLY, 0);
		lcdtbl_Return.insertItem(OPINION, OPINION, 0);
		lcdtbl_Return.insertItem(LETTER, LETTER, 0);
		lcdtbl_Return.insertItem(MEETING_SUM, MEETING_SUM, 0);
		lcdtbl_Return.insertItem(OTHER, OTHER, 0);

		return lcdtbl_Return;
	}

	/**
	 * 获取公文密级码表.
	 * 
	 * @return
	 */
	public static CodeTable getConfidentialRankCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(CONFIDENTIAL_RANK_0, "");
		lcdtbl_Return.insertItem(CONFIDENTIAL_RANK_1, "秘密");
		lcdtbl_Return.insertItem(CONFIDENTIAL_RANK_2, "机密");
		lcdtbl_Return.insertItem(CONFIDENTIAL_RANK_3, "绝密");
		return lcdtbl_Return;
	}

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public String getDistriOut() {
		Long in = getInsideOnly();
		if (in != null && in == 1) {
			return "N";
		}
		return "Y";
	}

	public void setDistriOut(String distriOut) {
		this.distriOut = distriOut;
	}

	@Transient
	public int getDbYear() {
		return 0;
	}

	@Transient
	public String getDomainObjectId() {
		return "DOCOUT";
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleId() {
		return "DOCOUT";
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getCompanyNo() {
		return getDeptId();
	}
	@Transient
	public long getOldProcId(){
		return oldProcId;
	}
	public void setOldProcId(long procId){
		this.oldProcId = procId;
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
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	//流程是否到达接受修改
	@Transient
	public boolean getIsAccept() {
		OaDocOutService service = (OaDocOutService)SpringContextHolder.getBean(OaDocOutService.class);
		return service.getIsAccept(this.getProcId());
	}
}
