package com.supporter.prj.pm.fund_appropriation.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.fund_appropriation.entity.FundAppropriationSwf;

/**   
 * @Title: Entity
 * @Description: pm_fund_appropriate_swf.
 * @author Administrator
 * @date 2018-07-04 18:07:38
 * @version V1.0   
 *
 */
@Repository
public class FundAppropriationSwfDao extends MainDaoSupport< FundAppropriationSwf, String > {
	/**
	 * 分页查询
	 * @param user 用户
	 * @param jqGrid 表格请求参数
	 * @param swf 资金拨付流程对象
	 * @return List< EngineeVisaSwf >
	 */
	public List< FundAppropriationSwf > findPage(UserProfile user, JqGrid jqGrid,
			FundAppropriationSwf swf) {
		//权限限定条件hql
//		String authHql = EIPService.getAuthorityService().getHqlFilter(user,
//				FundAppropriationSwf.APP_NAME, NegotiateConstant.AUTH_EDIT_VISA);
//		jqGrid.addHqlFilter(authHql);
		
		if (swf != null) {
			//搜索关键字
			String keyword = swf.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				String likeKeyword = "%" + keyword + "%";
				String hql = "fundNo like ?";
				jqGrid.addHqlFilter(hql, likeKeyword);
			}
		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取尚未启动流程的业务对象
	 * @return List < FundAppropriationSwf >
	 */
	public List < FundAppropriationSwf > getNotStartProcList() {
		String hql = "from " + FundAppropriationSwf.class.getName()
				+ " where oaProcId is null or oaExamStatus=?";
		return this.find(hql, FundAppropriationSwf.OAExamStatus.DRAFT);
	}
}

