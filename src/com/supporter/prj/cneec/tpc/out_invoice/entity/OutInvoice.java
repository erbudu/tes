package com.supporter.prj.cneec.tpc.out_invoice.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.supporter.prj.cneec.tpc.out_invoice.entity.base.BaseOutInvoice;
import com.supporter.prj.cneec.tpc.util.IModuleEntity;
import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-11-22 10:25:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_OUT_INVOICE", schema = "")
public class OutInvoice extends BaseOutInvoice implements IBusiEntity, IModuleEntity {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCOUTI";
	public static final String DOMAIN_OBJECT_ID = "OutInvoice";

	private int year;
	private int month;
	private int day;
	
	
	private String prjDeptId; //项目所属部门
	

	//以下为流程中用到

	@Transient
	public String getInvoiceStatusDesc() {
		return Status.getCodeTable().get(this.getInvoiceStatus());
	}

	/**
	 * 无参构造函数
	 */
	public OutInvoice() {
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
	public OutInvoice(String reportId) {
		super(reportId);
	}

	public boolean equals(Object other) {
		if (!(other instanceof OutInvoice)) {
			return false;
		}
		OutInvoice castOther = (OutInvoice) other;
		return StringUtils.equals(this.getInvoiceOutId(), castOther
				.getInvoiceOutId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getInvoiceOutId()).toHashCode();
	}

	/**
	 *确认状态常量类.
	 */
	public static final class Status {
		public static final int DRAFT = 0;//未提交
		public static final int PROCESSING = 1;//审批进行中
		public static final int COMPLETE = 2;//审批完成

		/**
		 * 构造方法.
		 *
		 */
		private Status() {
		}

		/**
		 * 获取收款计划状态码表.
		 * @return Map
		 */
		public static Map<Integer, String> getCodeTable() {
			Map<Integer, String> map = new HashMap<Integer, String>();
			map.put(DRAFT, "草稿");
			map.put(PROCESSING, "审核中");
			map.put(COMPLETE, "审核完成");
			return map;
		}
	}

	/**
	 * 支出发票类型常量.
	 * @author Paul.Zang
	 */
	public static final class InvoiceType {
		public static final int WANG_LAI = 0;//往来发票
		public static final int FU_WU = 1;//服务发票
		public static final int ZENG_ZHI_SHUI_ZHUAN_YONG = 2;//增值税专用发票
		public static final int ZENG_ZHI_SHUI_PU_TONG = 3;//增值税普通发票
		public static final int JIAN_AN = 4;//建安发票
		public static final int SHOU_JU = 5;//收据发票

		/**
		 * 构造方法.
		 *
		 */
		private InvoiceType() {
		}

		/**
		 * 获取支出发票类型码表.
		 * @return Map<Integer, String>
		 */
		public static Map<Integer, String> getCodeTable() {
			Map<Integer, String> map = new HashMap<Integer, String>();
			map.put(WANG_LAI, "往来发票");
			map.put(FU_WU, "服务发票");
			map.put(ZENG_ZHI_SHUI_ZHUAN_YONG, "增值税专用发票");
			map.put(ZENG_ZHI_SHUI_PU_TONG, "增值税普通发票");
			map.put(JIAN_AN, "建安发票");
			map.put(SHOU_JU, "收据发票");
			return map;
		}
	}
	@Transient
	public boolean getIsDraft() {
		return this.getInvoiceStatus() == Status.DRAFT;
	}
	@Transient
	public boolean getIsProcessing() {
		return this.getInvoiceStatus() == Status.PROCESSING;
	}

	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
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
	public String getKeyProps() {
		return "report_id";
	}

	@Transient
	public String getKeyValues() {
		return this.getInvoiceOutId();
	}

	@Transient
	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}

	@Transient
	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}

	@Transient
	public int getDay() {
		return day;
	}


	public void setDay(int day) {
		this.day = day;
	}
	
	@Transient
	public String getInvoiceTypeDesc(){
		return InvoiceType.getCodeTable().get(this.getInvoiceType());
	}
	@Transient
	public String getCastRate(){
		if(this.getContractActMount()== null || this.getContractActMount()==0){
			return ".00";
		}else{
			return CommonUtil.format(this.getBegforIvoiceOutAmount()*100/this.getContractActMount(),"#####.00");
		}
	}

	@Transient
	public int getSwfStatus() {
		return super.getInvoiceStatus();
	}

	@Transient
	public String getSwfStatusDesc() {
		return this.getInvoiceStatusDesc();
	}

	@Transient
	public String getProcTitle() {
		String procTitle = CommonUtil.trim(super.getPrjName()) + CommonUtil.trim(super.getInvoiceNo()) + "（" + CommonUtil.trim(super.getDeptName()) + "）";
		return procTitle;
	}

	@Transient
	public int getDbYear() {
		return 0;
	}

	@Transient
	public String getEntityId() {
		return super.getInvoiceOutId();
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}
	
}
