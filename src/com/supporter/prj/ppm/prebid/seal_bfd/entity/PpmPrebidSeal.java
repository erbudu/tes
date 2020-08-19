package com.supporter.prj.ppm.prebid.seal_bfd.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import com.supporter.prj.ppm.prebid.seal_bfd.entity.base.BasePpmPrebidSeal;

/**
 * 标前评审盖章版的资料清单
 * @author 王康
 *
 */
@Entity
@Table(name = "PPM_PREBID_SEAL", catalog = "")
public class PpmPrebidSeal extends BasePpmPrebidSeal{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>部门：经营管理部-部门编号</p>
	 */
	public static final String MANAGEMENT_DEPARTMENT = "48876683";
	/**
	 * <pre>角色：经营管理部-经营管理部外派事业部经理</pre>
	 */
	public static final String ROLE_ExpatriateManagers = "BUSINESSMANAGERTOBUSINESS";
}
