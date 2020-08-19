package com.supporter.prj.ppm.poa.icc.entity;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.ppm.poa.icc.entity.base.BaseCoordination;
@Entity
@Table(name = "ppm_icc",schema="")
public class Coordination   extends BaseCoordination {
	private static final long serialVersionUID = 1L;
	/**
	 * <p>部门：经营管理部-部门编号</p>
	 */
	public static final String MANAGEMENT_DEPARTMENT = "48876683";
	
	/**
	 * <pre>角色：经营管理部-项目信息记录员</pre>
	 */
	public static final String ROLE_RECORDER = "BASEINFORECORDER";
	/**
	 * <pre>角色：经营管理部-经营管理部外派事业部经理</pre>
	 */
	public static final String ROLE_ExpatriateManagers = "BUSINESSMANAGERTOBUSINESS";
	private boolean isNew;
	public static final String MODULE_ID = "ICC";
	private String linkManAndPhone;
	@Transient
	public String getLinkManAndPhone() {
		return linkManAndPhone;
	}

	public void setLinkManAndPhone(String linkManAndPhone) {
		this.linkManAndPhone = linkManAndPhone;
	}

	@Transient
	public boolean getIsNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	public static Map<String, String> getCorType() {
		List<IComCodeTableItem> list = EIPService.getComCodeTableService()
				.getCodeTableItems("CorType");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (IComCodeTableItem item : list) {
			map.put(item.getItemId(), item.getDisplayName());
		}
		return map;
	}
	@Transient
	public int getDbYear()
    {
        return 0;
    }
	@Transient
    public String getDomainObjectId()
    {
        return "ICC";
    }
    @Transient
    public String getEntityName()
    {
        return getClass().getName();
    }
    @Transient
    public String getModuleId()
    {
        return "ICC";
    }
    @Transient
    public String getModuleBusiType()
    {
        return "";
    }
}
