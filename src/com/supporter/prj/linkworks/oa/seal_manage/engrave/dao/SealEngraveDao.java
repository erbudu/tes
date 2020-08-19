package com.supporter.prj.linkworks.oa.seal_manage.engrave.dao;
import org.springframework.stereotype.Repository;
import com.supporter.prj.linkworks.oa.seal_manage.engrave.entity.SealEngrave;
import com.supporter.prj.linkworks.oa.signed_report.constants.SignedReportAuthConstant;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**   
 * @Title: Entity
 * @Description: 印章销毁数据表.
 * @author z
 * @date 2019-12-12 17:52:29
 * @version V1.0   
 *
 */
@Repository
public class SealEngraveDao extends MainDaoSupport< SealEngrave, String > {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List< SealEngrave > findPage(JqGrid jqGrid, SealEngrave sealEngrave, UserProfile user) {
		if (sealEngrave != null) {
			String sealName = sealEngrave.getSealName();
			if (StringUtils.isNotBlank(sealName)) {
				jqGrid.addHqlFilter(
						"(sealName like ? or createdBy like ? or deptName like ?)",
						"%" + sealName + "%", "%" + sealName + "%", "%" + sealName + "%");
			}
//			// 印章类型
			Integer sealType = sealEngrave.getSealType();
			if (sealType != null) {
				jqGrid.addHqlFilter("sealType = ? ", sealType);
			}
//			// 状态
			Integer status = sealEngrave.getStatus();
			if (status != null) {
				jqGrid.addHqlFilter("status = ? ", status);
			}
			//权限过滤
			String authHql = EIPService.getAuthorityService().getHqlFilter(user,SealEngrave.MODULE_ID, SealEngrave.AUTH_DATASHOWAUTH);
			jqGrid.addHqlFilter(authHql);
			
			//按创建时间倒叙排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		return this.retrievePage(jqGrid);
	}
	/**
	 * 判断名字是否重复
	 * 
	 * @param applyId
	 * @param applyName
	 * @return
	 */
	public boolean nameIsValid(String applyId,String applyName){
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(applyId)){//新建时
			hql = "from " + SealEngrave.class.getName() + " where  applyName = ?";
			retList = this.retrieve(hql, applyName);
		}else{//编辑时
			hql = "from " + SealEngrave.class.getName() + " where applyId != ? and applyName = ?";
			retList = this.retrieve(hql, applyId, applyName);
		}
		if(CollectionUtils.isEmpty(retList)){
			return true;
		}
		return false;
	}
}

