package com.supporter.prj.linkworks.oa.authority_apply.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.authority_apply.entity.base.BaseAuthorityApply;
import com.supporter.util.CodeTable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;

/**   
 * @Title: Entity
 * @Description: 功能模块
 * @author gongjiwen
 * @date 2016-12-05 10:25:07
 * @version V1.0   
 *
 */
@Entity
@Table(name="OA_AUTHORITY_APPLY"
    ,schema=""
)
public class AuthorityApply extends BaseAuthorityApply {

	private static final long serialVersionUID = 1L;
	// 顶级TOP_MODULE_ID
	public static final String TOP_MODULE_ID = "0";
	
	public static final int DRAFT = 0; //草稿
    public static final int PROCESSING = 1; //审核中 
    public static final int COMPLETE = 2; //审核完成
    public static final int REJECTED = 3; //驳回

	/**
	 * 获取状态码表.
	 * @return
	 */
	public static CodeTable getApplyStatusCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿");
		lcdtbl_Return.insertItem(PROCESSING, "审核中");
		lcdtbl_Return.insertItem(COMPLETE, "审核完成");
		return lcdtbl_Return;
	}
	
	public static Map<Integer, String> getStatusCodeTable(){
    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(DRAFT, "草稿");
    	map.put(PROCESSING, "审核中");
    	map.put(COMPLETE, "审核完成");
		return map;
	}
	
	private String applyStatusDesc = "";
	private AuthorityApplyBoard authorityApplyBoard;
	private AuthorityApplyContent authorityApplyContent;
	private AuthorityApplyPerson authorityApplyPerson;

	/**
	 * 无参构造函数
	 */
	public AuthorityApply() {
		super();
	}
	/**
	 * 构造函数
	 * @param contractId
	 */
	public AuthorityApply(String applyId) {
		super(applyId);
	}

	public boolean equals(Object other) {
		if (!(other instanceof AuthorityApply)) {
			return false;
		}
		AuthorityApply castOther = (AuthorityApply) other;
		return this.getApplyId() == castOther.getApplyId();
		//		return StringUtils.equals(this.getReportId(), castOther.getReportId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getApplyId()).toHashCode();
	}
	
	private boolean add;
	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}
	@Transient
	public String getApplyStatusDesc() {
		return getApplyStatusCodeTable().getDisplay(this.getApplyStatus());
	}

	public void setApplyStatusDesc(String applyStatusDesc) {
		this.applyStatusDesc = applyStatusDesc;
	}
	@Transient
	public AuthorityApplyBoard getAuthorityApplyBoard() {
		return authorityApplyBoard;
	}

	public void setAuthorityApplyBoard(AuthorityApplyBoard authorityApplyBoard) {
		this.authorityApplyBoard = authorityApplyBoard;
	}
	@Transient
	public AuthorityApplyContent getAuthorityApplyContent() {
		return authorityApplyContent;
	}

	public void setAuthorityApplyContent(AuthorityApplyContent authorityApplyContent) {
		this.authorityApplyContent = authorityApplyContent;
	}
	@Transient
	public AuthorityApplyPerson getAuthorityApplyPerson() {
		return authorityApplyPerson;
	}

	public void setAuthorityApplyPerson(AuthorityApplyPerson authorityApplyPerson) {
		this.authorityApplyPerson = authorityApplyPerson;
	}
	//从表留言板意见
	private String  authorityApplyBoardDesc;
	@Transient
	public String getAuthorityApplyBoardDesc() {
		return authorityApplyBoardDesc;
	}

	public void setAuthorityApplyBoardDesc(String authorityApplyBoardDesc) {
		this.authorityApplyBoardDesc = authorityApplyBoardDesc;
	}
	
	
	
	//附件查看能用到
	private String files;

	@Transient
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	

	
	private String language;
	private String source;
    
	@Transient
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
    
	@Transient
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
	private String delIds;

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	@Transient
	public String getDelIds() {
		return delIds;
	}
	
	private List<AuthorityApplyPerson> innerList;
	private List<AuthorityApplyPerson> outerList;
	@Transient
	public List<AuthorityApplyPerson> getInnerList() {
		return innerList;
	}

	public void setInnerList(List<AuthorityApplyPerson> innerList) {
		this.innerList = innerList;
	}
	@Transient
	public List<AuthorityApplyPerson> getOuterList() {
		return outerList;
	}

	public void setOuterList(List<AuthorityApplyPerson> outerList) {
		this.outerList = outerList;
	}
	
	//被授权人姓名格式化
	private String PersonNameDesc;
	@Transient
	public String getPersonNameDesc() {
		return PersonNameDesc;
	}

	public void setPersonNameDesc(String personNameDesc) {
		PersonNameDesc = personNameDesc;
	}
	//公司分管领导id和名称
	private String notifierIds;
	private String notifierNames;
	@Transient
	public String getNotifierIds() {
		return notifierIds;
	}

	public void setNotifierIds(String notifierIds) {
		this.notifierIds = notifierIds;
	}
	@Transient
	public String getNotifierNames() {
		return notifierNames;
	}

	public void setNotifierNames(String notifierNames) {
		this.notifierNames = notifierNames;
	}
	
	private String authorityApplyReasonDesc;//
	private String authorityApplyReasonZHDesc;//
	private String authorityApplyReasonEDesc;//
	private String authorityApplyNoteDesc;//
	@Transient
	public String getAuthorityApplyNoteDesc() {
		return authorityApplyNoteDesc;
	}

	public void setAuthorityApplyNoteDesc(String authorityApplyNoteDesc) {
		this.authorityApplyNoteDesc = authorityApplyNoteDesc;
	}

	@Transient
	public String getAuthorityApplyReasonDesc() {
		return authorityApplyReasonDesc;
	}

	public void setAuthorityApplyReasonDesc(String authorityApplyReasonDesc) {
		this.authorityApplyReasonDesc = authorityApplyReasonDesc;
	}
	
	@Transient
	public String getAuthorityApplyReasonZHDesc() {
		return authorityApplyReasonZHDesc;
	}
	
	public void setAuthorityApplyReasonZHDesc(String authorityApplyReasonZHDesc) {
		this.authorityApplyReasonZHDesc = authorityApplyReasonZHDesc;
	}
	@Transient
	public String getAuthorityApplyReasonEDesc() {
		return authorityApplyReasonEDesc;
	}

	public void setAuthorityApplyReasonEDesc(String authorityApplyReasonEDesc) {
		this.authorityApplyReasonEDesc = authorityApplyReasonEDesc;
	}
	
	private String personNameCardDesc;
	@Transient
	public String getPersonNameCardDesc() {
		return personNameCardDesc;
	}

	public void setPersonNameCardDesc(String personNameCardDesc) {
		this.personNameCardDesc = personNameCardDesc;
	}
	private String personName;
	@Transient
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	
	
	//声明流程用到的参数
	@Transient
	public int getDbYear()
    {
        return 0;
    }
	@Transient
    public String getDomainObjectId()
    {
        return "AuthorityApply";
    }
    @Transient
    public String getEntityName()
    {
        return getClass().getName();
    }
    @Transient
    public String getModuleId()
    {
        return "OAAUTHAPPLY";
    }
    @Transient
    public String getModuleBusiType()
    {
        return "";
    }
    @Transient
    public String getCompanyNo()
    {
        return getApplyDeptId();
    }


	
	
	
	
	
	

}
