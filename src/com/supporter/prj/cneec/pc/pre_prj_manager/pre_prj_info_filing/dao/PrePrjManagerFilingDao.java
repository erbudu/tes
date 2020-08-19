package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.dao;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.entity.PrePrjManagerFiling;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;


/**
 * @Title: DAO
 * @Description: 功能模块表
 * @version V1.0
 * 
 */
@Repository
public class PrePrjManagerFilingDao extends MainDaoSupport < PrePrjManagerFiling , String > {
	
	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param codeIds
	 *            物资ids
	 * @return
	 */
	public List<PrePrjManagerFiling> findPage(UserProfile user,JqGrid jqGrid, PrePrjManagerFiling filing) {
		String authHql = "";
		if (filing != null) {			
			String keyWords = filing.getKeyWords();
			if (StringUtils.isNotBlank(keyWords)) {
				keyWords = "%" + keyWords + "%";
				jqGrid.addHqlFilter("prjNameZh like ? ",keyWords);
			}
			Integer status = filing.getStatus();
			if (status != null) {
				jqGrid.addHqlFilter("status = ? ", status);
			}
		}
		//权限过滤
		//authHql = EIPService.getAuthorityService().getHqlFilter(user,DocOutConstant.MODULE_ID,AuthConstant.AUTH_OPER_NAME__PAGESHOW);
		//jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}
}
