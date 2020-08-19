package com.supporter.prj.ppm.prebid.report.entity;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.ppm.prebid.report.entity.base.BasePpmPrebidReport;
/**
 * 报审
 * @author 王康
 *
 */
@Entity
@Table(name = "PPM_PREBID_REPORT", catalog = "")
public class PpmPrebidReport extends BasePpmPrebidReport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BUSI_TYPE = "prebidReportFile";
	public static final String MODULE_ID = "PREREPORT";
	public static final String DOMAIN_OBJECT_ID = "PrebidReport";
	private boolean add;
	
	private String proNo;
	private String proNameC;
	private String proNameE;
	
	private List<PpmPrebidReportBfd> dataList;
	

	@Transient
	public List<PpmPrebidReportBfd> getDataList() {
		return dataList;
	}

	public void setDataList(List<PpmPrebidReportBfd> dataList) {
		this.dataList = dataList;
	}

	@Transient
	public String getProNo() {
		return proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}

	@Transient
	public String getProNameC() {
		return proNameC;
	}

	public void setProNameC(String proNameC) {
		this.proNameC = proNameC;
	}

	@Transient
	public String getProNameE() {
		return proNameE;
	}

	public void setProNameE(String proNameE) {
		this.proNameE = proNameE;
	}


	@Transient
	public boolean getAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}
	@Transient
	public String getProcTitle() {
		String procTitle = this.getCreatedBy()+"发起的项目报审申请";
		return procTitle;
	}
	@Transient
	public int getDbYear()
    {
        return 0;
    }
	@Transient
	public String getModuleId() {
		return MODULE_ID;
	}
	@Transient
	public String getDomainObjectId() {
		return DOMAIN_OBJECT_ID;
	}
	@Transient
	public String getEntityId() {
		return this.getPrbidReportId();
	}

    @Transient
    public String getEntityName()
    {
        return getClass().getName();
    }
    @Transient
    public String getModuleBusiType()
    {
        return "";
    }

    
    
	public static final int DRAFT = 0; // 草稿
	public static final int PROCESSING = 10; // 审核中
	public static final int COMPLETED = 20;// 审批完成
}
