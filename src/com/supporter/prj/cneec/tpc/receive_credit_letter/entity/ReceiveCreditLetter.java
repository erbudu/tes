package com.supporter.prj.cneec.tpc.receive_credit_letter.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supporter.prj.cneec.tpc.receive_credit_letter.entity.base.BaseReceiveCreditLetter;
import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.util.CommonUtil;


/**   
 * @Title: Entity
 * @Description: 收取信用证（该信用证与开出信用证模块的信用证无关）
 * @author liyinfeng
 * @date 2017-11-22 10:25:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_RECEIVE_CREDIT_LETTER", schema = "")
public class ReceiveCreditLetter extends BaseReceiveCreditLetter implements IBusiEntity {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCRCL";
	public static final String BUSI_TYPE = "TPCRCL";
	public static final String DOMAIN_OBJECT_ID = "RCL";

	public static final int DRAFT = 0;//草稿
	public static final int PROCESSING = 1;//审核中
	public static final int COMPLETE = 2;//审批完成

	private boolean add;
	
	
	private String prjDeptId; //项目所属部门

	@Transient
	public boolean isAdd() {
		return this.add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public static Map<Integer, String> getSettlementStatusCodeTable(){
    	Map<Integer, String> map = new LinkedHashMap<Integer, String>();
    	map.put(-1, "全部");
    	map.put(DRAFT, "草稿");
    	map.put(PROCESSING, "审核中");
    	map.put(COMPLETE, "审批完成");
		return map;
	}

	@Transient
	public String getSwfStatusDesc() {
		return ReceiveCreditLetterStatus.getValueByKey(this.getSwfStatus());
	}

	/**
	 * 无参构造函数
	 */
	public ReceiveCreditLetter() {
		super();
	}

	public enum AuthManageType {
		normal(IModule.AuthManageType.NORMAL, "普通角色&权限项方式"), byAdmin(
				IModule.AuthManageType.BY_ADMIN, "管理员权限"), none(
				IModule.AuthManageType.NONE, "不控制");
		private int key;
		private String value;

		AuthManageType(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public static int getKeyByValue(String value) {
			AuthManageType[] types = AuthManageType.values();
			for (AuthManageType type : types) {
				if (StringUtils.equals(value, type.getValue())) {
					return type.getKey();
				}
			}
			return 0;
		}

		public static String getValueByKey(int key) {
			AuthManageType[] types = AuthManageType.values();
			for (AuthManageType type : types) {
				if (key == type.getKey()) {
					return type.getValue();
				}
			}
			return null;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * 
	 * 日志操作类型
	 * @author gongjiwen
	 * @createDate 2017年3月7日
	 * @since TODO: 来源版本
	 * @modifier gongjiwen
	 * @modifiedDate 2017年3月7日
	 */
	public enum LogOper {
		MODULE_ADD("新建应用"), MODULE_EDIT("编辑应用"), MODULE_DEL("删除应用"), MODULE_CHANGE_DISPLAY_ORDER(
				"调整应用显示顺序");
		private String operName;

		LogOper(String operName) {
			this.operName = operName;
		}

		public String getOperName() {
			return operName;
		}

		public void setOperName(String operName) {
			this.operName = operName;
		}

	}

	/**
	 * 构造函数
	 * @param contractId
	 */
	public ReceiveCreditLetter(String reportId) {
		super(reportId);
	}

	public boolean equals(Object other) {
		if (!(other instanceof ReceiveCreditLetter)) {
			return false;
		}
		ReceiveCreditLetter castOther = (ReceiveCreditLetter) other;
		return StringUtils.equals(this.getReceiveCreditLetterId(), castOther.getReceiveCreditLetterId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getReceiveCreditLetterId()).toHashCode();
	}
	public enum ReceiveCreditLetterStatus {
		//zero(0, ""), 
		DRAFT(ReceiveCreditLetter.DRAFT, "草稿"),
		PROCESSING(ReceiveCreditLetter.PROCESSING, "审核中"),
		COMPLETE(ReceiveCreditLetter.COMPLETE, "审批完成");
		private int key;
		private String value;

		ReceiveCreditLetterStatus(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public static int getKeyByValue(String value) {
			ReceiveCreditLetterStatus[] types = ReceiveCreditLetterStatus.values();
			for (ReceiveCreditLetterStatus type : types) {
				if (StringUtils.equals(value, type.getValue())) {
					return type.getKey();
				}
			}
			return 1;
		}

		public static String getValueByKey(int key) {
			ReceiveCreditLetterStatus[] types = ReceiveCreditLetterStatus.values();
			for (ReceiveCreditLetterStatus type : types) {
				if (key == type.getKey()) {
					return type.getValue();
				}
			}
			return null;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	@Override
	@Transient
	public String getKeyProps() {
		return "receive_credit_letter_id";
	}

	@Override
	@Transient
	public String getKeyValues() {
		return this.getReceiveCreditLetterId();
	}

	/**
	 * 当前实例是否完成了审批.
	 */
	@Transient
	public boolean getIsComplete() {
		return this.getSwfStatus() == ReceiveCreditLetter.ReceiveCreditLetterStatus.COMPLETE.key;
	}

	// 以下为流程中用到
	private String procTitle;
	private String prjManagerId;
	private String prjManager;

	@Column(name = "PRJ_MANAGER_ID", length = 32)
	public String getPrjManagerId() {
		return this.prjManagerId;
	}

	public void setPrjManagerId(String prjManagerId) {
		this.prjManagerId = prjManagerId;
	}

	@Column(name = "PRJ_MANAGER", length = 32)
	public String getPrjManager() {
		return this.prjManager;
	}

	public void setPrjManager(String prjManager) {
		this.prjManager = prjManager;
	}

	@Transient
	public String getProcTitle() {
		procTitle = CommonUtil.trim(this.getProjectName()) + "的" + CommonUtil.trim(this.getContractName()) + "（" + this.getDeptName() + "）";
		return procTitle;
	}

	@Transient
	public double getDbYear() {
		return 0;
	}

	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}

	@Transient
	public String getEntityId() {
		return this.getReceiveCreditLetterId();
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}

	@Transient
	public String getModuleBusiType() {
		return "";
	}

	@Transient
	public String getCompanyNo() {
		return this.getDeptId();
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}

}
