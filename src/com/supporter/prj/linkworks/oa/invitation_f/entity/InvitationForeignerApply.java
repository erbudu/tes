package com.supporter.prj.linkworks.oa.invitation_f.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.linkworks.oa.invitation_f.entity.base.BaseInvitationForeignerApply;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "OA_INVITATION_FOREIGNER_APPLY", schema = "")
public class InvitationForeignerApply extends BaseInvitationForeignerApply implements IBusiEntity{
	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "INVITATION";
	public static final String DOMAIN_OBJECT_ID = "INVITATION";
	private String searchKeyword;
	private boolean add;
	private String invitationStatusDesc;
	private InvitationPersons invitationPersons;
	private List<InvitationPersons> list;
	private String delIds;
	private long oldProcId = -1;
	
	public static final int DRAFT = 0;// 未提交
	public static final int PROCESSING = 1;// 审批进行中
	public static final int COMPLETE = 2;// 审批完成

	public static CodeTable getCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "未提交", 0);
		lcdtbl_Return.insertItem(PROCESSING, "审批进行中", 0);
		lcdtbl_Return.insertItem(COMPLETE, "审批完成", 0);
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
	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	@Transient
	public String getInvitationStatusDesc() {
		Integer status = getInvitationStatus();
		if (status != null) {
			return getCodeTable().getDisplay(status);
		}
		return "";
	}

	public void setInvitationStatusDesc(String invitationStatusDesc) {
		this.invitationStatusDesc = invitationStatusDesc;
	}

	@Transient
	public InvitationPersons getInvitationPersons() {
		return invitationPersons;
	}

	public void setInvitationPersons(InvitationPersons invitationPersons) {
		this.invitationPersons = invitationPersons;
	}

	@Transient
	public List<InvitationPersons> getList() {
		return list;
	}

	public void setList(List<InvitationPersons> list) {
		this.list = list;
	}

	@Transient
	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	@Transient
	public int getDbYear() {
		return 0;
	}

	@Transient
	public String getDomainObjectId() {
		return "Invitation";
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleId() {
		return "INVITATION";
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
	public long getOldProcId() {
		return oldProcId;
	}

	public void setOldProcId(long oldProcId) {
		this.oldProcId = oldProcId;
	}
	@Override
	@Transient
	public String getKeyProps() {
		// TODO Auto-generated method stub
		return "invitation_id";
	}

	@Override
	@Transient
	public String getKeyValues() {
		// TODO Auto-generated method stub
		return this.getInvitationId();
	}
	
	@Transient
	public String getIsPoliticiansDESC() {
		// TODO Auto-generated method stub
		return this.getIsPoliticians() ? "是" : "否";
	}
}
