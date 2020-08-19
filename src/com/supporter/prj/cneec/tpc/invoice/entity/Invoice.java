package com.supporter.prj.cneec.tpc.invoice.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.invoice.entity.base.BaseInvoice;
import com.supporter.prj.cneec.tpc.util.IModuleEntity;
import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.util.CommonUtil;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.StringUtils;


/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-11-22 10:25:07
 * @version V1.0   
 *
 */
@Entity
@Table(name = "TPC_INVOICE", schema = "")
public class Invoice extends BaseInvoice implements IBusiEntity, IModuleEntity {

	private static final long serialVersionUID = 1L;
	public static final String MODULE_ID = "TPCINVOICE";
	public static final String DOMAIN_OBJECT_ID = "Invoice";

	
	private String prjDeptId; //项目所属部门
	 

	//以下为流程中用到
	
	private List<InvoiceRec> invoiceRecList;
	
	private String delIds;
	
	private int recordCount;
	private double sAll;
	private double sActAll;
	private String dateFrom;
	private String dateTo;
	private String projectName;
	
	@Transient
	public String getInvoiceStatusDesc() {
		return Invoice.Status.getCodeTable().get(this.getInvoiceStatus());
	}

	/**
	 * 无参构造函数
	 */
	public Invoice() {
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
	 * @author liyinfeng
	 * @createDate 2017年3月7日
	 * @since TODO: 来源版本
	 * @modifier liyinfeng
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
	public Invoice(String reportId) {
		super(reportId);
	}

	public boolean equals(Object other) {
		if (!(other instanceof Invoice)) {
			return false;
		}
		Invoice castOther = (Invoice) other;
		return StringUtils.equals(this.getInvoiceId(), castOther.getInvoiceId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getInvoiceId()).toHashCode();
	}
	@Transient
	public List<InvoiceRec> getInvoiceRecList() {
		return invoiceRecList;
	}

	public void setInvoiceRecList(List<InvoiceRec> invoiceRecList) {
		this.invoiceRecList = invoiceRecList;
	}
	
	
	/**
     *发票开发合同类型常量类.
     */
    public static final class InvoiceType {
    	public static final int EXPORT_DRAW = 1;//增值税专票-出口退税
    	public static final int HOME_DIKOU = 2;//增值税专票-国内抵扣
    	public static final int OTHER = 3;//增值税专票-成本
    	public static final int NO_ADD_TAX = 4;//非增值税专票
   	 	
   	 	/**
         * 构造方法.
         *
         */
        private InvoiceType() {}
   	 	/**
   	 	 * 获取发票开发合同类型码表.
   	 	 * @return Map
   	 	 */
   	 	public static Map<Integer, String> getCodeTable() {
   	 		Map<Integer, String> map = new HashMap<Integer, String>();
   	 		map.put(EXPORT_DRAW,"增值税专票-出口退税");
	        map.put(HOME_DIKOU,"增值税专票-国内抵扣");
	        map.put(OTHER,"增值税专票-成本"); 
	        map.put(NO_ADD_TAX,"非增值税专票"); 
	        return map;
   	 	}
    }

    /**
     *发票状态常量类.
     */
    public static final class Status {
    	public static final int DRAFT = 0;//未提交
    	public static final int PROCESSING = 1;//审批进行中
    	public static final int COMPLETE = 2;//审批完成
    	public static final int FINANCE_DEPARTMENT_ADD = 3;//计财务补填
   	 	
   	 	/**
         * 构造方法.
         *
         */
        private Status() {}
   	 	/**
   	 	 * 获取收款状态码表.
   	 	 * @return Map
   	 	 */
        public static Map<Integer, String> getCodeTable() {
   	 		Map<Integer, String> map = new HashMap<Integer, String>();
   	 		map.put(DRAFT,"草稿");
	        map.put(PROCESSING,"审核中");
	        map.put(COMPLETE,"审批完成"); 
	        map.put(FINANCE_DEPARTMENT_ADD,"财务补入"); 
	        return map;
   	 	}
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

	@Override
	@Transient
	public String getKeyProps() {
		return "report_id";
	}

	@Override
	@Transient
	public String getKeyValues() {
		return this.getInvoiceId();
	}
	
	@Transient
	public boolean getIsDraft(){
		return getInvoiceStatus() == Invoice.Status.DRAFT 
			|| getInvoiceStatus() == Invoice.Status.FINANCE_DEPARTMENT_ADD;
	}
	@Transient
	public boolean getIsProcessing(){
		return getInvoiceStatus() == Invoice.Status.PROCESSING;
	}

	@Transient
	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	@Transient
	public double getSAll() {
		return sAll;
	}

	public void setSAll(double all) {
		sAll = all;
	}
	@Transient
	public double getSActAll() {
		return sActAll;
	}

	public void setSActAll(double actAll) {
		sActAll = actAll;
	}
	
	@Transient
	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
	@Transient
	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	@Transient
	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	@Transient
	public String getProjectId() {
		return this.getPrjId();
	}

	@Transient
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@Transient
	public String getInvoiceTypeDesc(){
		return InvoiceType.getCodeTable().get(this.getInvoiceType());
	}

	@Transient
	public int getSwfStatus() {
		return this.getInvoiceStatus();
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
		return super.getInvoiceId();
	}
	
	@Transient
	public String getPrjDeptId() {
		return this.prjDeptId;
	}
	public void setPrjDeptId(String prjDeptId) {
		this.prjDeptId = prjDeptId;
	}
}
