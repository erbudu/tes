package com.supporter.prj.pm.enginee_negotiate.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.enginee_negotiate.constant.NegotiateConstant;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisaSwf;

/**   
 * @Title: Entity
 * @Description: PM_ENGINEE_VISA_SWF.
 * @author Administrator
 * @date 2018-07-04 18:07:38
 * @version V1.0   
 *
 */
@Repository
public class EngineeVisaSwfDao extends MainDaoSupport< EngineeVisaSwf, String > {
	/**
	 * 分页查询
	 * @param user 用户
	 * @param jqGrid 表格请求参数
	 * @param swf 签证流程对象
	 * @return List< EngineeVisaSwf >
	 */
	public List< EngineeVisaSwf > findPage(UserProfile user, JqGrid jqGrid,
			EngineeVisaSwf swf) {
		//权限限定条件hql
		String authHql = EIPService.getAuthorityService().getHqlFilter(user,
				EngineeVisaSwf.APP_NAME, NegotiateConstant.AUTH_EDIT_VISA);
		jqGrid.addHqlFilter(authHql);
		
		if (swf != null) {
			//搜索关键字
			String keyword = swf.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				String likeKeyword = "%" + keyword + "%";
				String hql = "applyNo like ?";
				jqGrid.addHqlFilter(hql, likeKeyword);
			}
		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取尚未启动流程的业务对象
	 * @return List < EngineeVisaSwf >
	 */
	public List < EngineeVisaSwf > getNotStartProcList() {
		String hql = "from " + EngineeVisaSwf.class.getName()
				+ " where oaProcId is null and oaExamStatus=?";
		return this.find(hql, EngineeVisaSwf.OAExamStatus.DRAFT);
	}
}

