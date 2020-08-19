package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.dao;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_develop_progress.entity.DevelopProgress;
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
public class PrjDevelopProgressDao extends MainDaoSupport < DevelopProgress , String > {
	
	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param codeIds
	 *            物资ids
	 * @return
	 */
	public List<DevelopProgress> findPage(UserProfile user,JqGrid jqGrid, DevelopProgress filing) {
		String authHql = "";
		if (filing != null) {			
			String keyWords = filing.getKeyWords();
			if (StringUtils.isNotBlank(keyWords)) {
				keyWords = "%" + keyWords + "%";
				jqGrid.addHqlFilter("prjNameZh like ? ",keyWords);
			}
			String filingId = filing.getFilingId();
			if (StringUtils.isNotBlank(filingId)) {
				jqGrid.addHqlFilter("filingId = ? ",filingId);
				return this.retrievePage(jqGrid);
			}
		}
		//权限过滤
		//authHql = EIPService.getAuthorityService().getHqlFilter(user,DocOutConstant.MODULE_ID,AuthConstant.AUTH_OPER_NAME__PAGESHOW);
		//jqGrid.addHqlFilter(authHql);
		return null;
	}
}
