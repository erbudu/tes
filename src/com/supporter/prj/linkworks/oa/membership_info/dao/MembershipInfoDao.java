package com.supporter.prj.linkworks.oa.membership_info.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import com.supporter.prj.linkworks.oa.membership_info.constants.AuthConstant;
import com.supporter.prj.linkworks.oa.membership_info.constants.MembershipConstant;
import com.supporter.prj.linkworks.oa.membership_info.entity.MembershipInfo;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class MembershipInfoDao extends MainDaoSupport < MembershipInfo, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<MembershipInfo> findPage(UserProfile user,JqGrid jqGrid, MembershipInfo entity) {
		String authHql = "";
		if(entity != null){
			//查询
			String empNo = entity.getEmpNo();
			if(StringUtils.isNotBlank(empNo)){
				jqGrid.addHqlFilter(
						"empNo = ? or empName like ? ",empNo,"%"+empNo+"%" );
			}
			jqGrid.addSortPropertyDesc("createdDate");
			jqGrid.addSortPropertyAsc("rowid");
		}
		authHql = EIPService.getAuthorityService().getHqlFilter(user,MembershipConstant.MODULE_ID,AuthConstant.AUTH_OPER_NAME__PAGESHOW);
		jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 判断名字是否重复
	 * 
	 * @param contractId
	 * @param contractName
	 * @return
	 */
	public boolean checkNameIsValid(String membershipInfoId, String empNo) {
		String hql = null;
		List retList = null;
		if (StringUtils.isBlank(membershipInfoId)) {// 新建时
			hql = "from " + MembershipInfo.class.getName() + " where empNo = ?";
			retList = this.retrieve(hql, empNo);
		} else {// 编辑时
			hql = "from " + MembershipInfo.class.getName() + " where membershipInfoId != ? and empNo = ?";
			retList = this.retrieve(hql, membershipInfoId, empNo);
		}
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}
}
