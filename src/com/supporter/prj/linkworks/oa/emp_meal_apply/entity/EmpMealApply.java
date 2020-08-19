package com.supporter.prj.linkworks.oa.emp_meal_apply.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.linkworks.oa.emp_meal_apply.entity.base.BaseEmpMealApply;
import com.supporter.util.CodeTable;

@Entity
@Table(name = "OA_EMP_MEAL_APPLY", schema = "")
public class EmpMealApply extends BaseEmpMealApply implements IBusiEntity{
	public static final String MODULE_ID = "EMPMEAL";
	public static final String DOMAIN_OBJECT_ID = "EmpMealApply";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean add;
	private String statusDesc;
	private String delIdRec;
	private String delIdCustormer;
	private String delIdNonRec;
	private List<EmpMealRec> recList;
	private List<NonEmpMealRec> nonList;
	private List<EmpCustormerMealRec> custormerList;

	private double countAmout;
	private String yearMonth;
	private long oldProcId= -1;
	
	@Transient
	public long getOldProcId() {
		return oldProcId;
	}

	public void setOldProcId(long oldProcId) {
		this.oldProcId = oldProcId;
	}

	@Transient
	public List<EmpMealRec> getRecList() {
		return recList;
	}

	public void setRecList(List<EmpMealRec> recList) {
		this.recList = recList;
	}

	@Transient
	public List<NonEmpMealRec> getNonList() {
		return nonList;
	}

	public void setNonList(List<NonEmpMealRec> nonList) {
		this.nonList = nonList;
	}

	@Transient
	public List<EmpCustormerMealRec> getCustormerList() {
		return custormerList;
	}

	public void setCustormerList(List<EmpCustormerMealRec> custormerList) {
		this.custormerList = custormerList;
	}

	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	@Transient
	public String getDelIdRec() {
		return delIdRec;
	}

	public void setDelIdRec(String delIdRec) {
		this.delIdRec = delIdRec;
	}

	@Transient
	public String getDelIdCustormer() {
		return delIdCustormer;
	}

	public void setDelIdCustormer(String delIdCustormer) {
		this.delIdCustormer = delIdCustormer;
	}

	@Transient
	public String getDelIdNonRec() {
		return delIdNonRec;
	}

	public void setDelIdNonRec(String delIdNonRec) {
		this.delIdNonRec = delIdNonRec;
	}

	@Transient
	public String getStatusDesc() {
		Integer sta = getStatus();
		if (sta != null) {
			return getCodeTable().getDisplay(sta);
		}
		return "";
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	// 审批中
	public static int PROCESSING = 1;
	// 草稿
	public static int DRAFT = 0;
	// 审批完成
	public static int COMPLETE = 2;

	public static CodeTable getCodeTable() {
		CodeTable lcdtbl_Return = new CodeTable();
		lcdtbl_Return.insertItem(DRAFT, "草稿", 0);
		lcdtbl_Return.insertItem(PROCESSING, "审批中", 0);
		lcdtbl_Return.insertItem(COMPLETE, "审批完成", 0);
		return lcdtbl_Return;
	}

	@Transient
	public double getCountAmout() {
		Double a = 0.00;
		Double i = getEmpInAmount();
		Double o = getEmpOutAmount();
		if (i != null) {
			a += i;
		}
		if (o != null) {
			a += o;
		}
		return a;
	}

	public void setCountAmout(double countAmout) {
		this.countAmout = countAmout;
	}

	@Transient
	public String getYearMonth() {
		Integer y = getYear();
		Integer m = getMonth();
		if (y != null && m != null) {
			return String.valueOf(y) + "-" + String.valueOf(m);
		}
		return "";
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	@Transient
	public int getDbYear() {
		return 0;
	}

	@Transient
	public String getDomainObjectId() {
		return "EmpMealApply";
	}

	@Transient
	public String getEntityName() {
		return getClass().getName();
	}

	@Transient
	public String getModuleId() {
		return "EMPMEAL";
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
		return "apply_id";
	}

	@Override
	@Transient
	public String getKeyValues() {
		// TODO Auto-generated method stub
		return this.getApplyId();
	}
}
