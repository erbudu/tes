package com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.dao;
import com.supporter.prj.cneec.pc.pre_prj_manager.bidding_permission.entity.BiddingPermission;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**   
 * @Title: Entity
 * @Description: 投标许可.
 * @author kangh_000
 * @date 2018-12-11 09:56:38
 * @version V1.0   
 *
 */
@Repository
public class BiddingPermissionDao extends MainDaoSupport< BiddingPermission, String > {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List< BiddingPermission > findPage(UserProfile user, JqGrid jqGrid, BiddingPermission biddingPermission) {
		//权限限定条件hql
		//String authHql = EIPService.getAuthorityService().getHqlFilter(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME);
		//jqGrid.addHqlFilter(authHql);
		if(biddingPermission != null){
			//搜索关键字
			String keyword = biddingPermission.getKeyword();
			if(StringUtils.isNotBlank(keyword)){
				String likeKeyword = "%" + keyword + "%";
				String hql = "biddingPermissionId like ?";
				jqGrid.addHqlFilter(hql, likeKeyword);
			}
		}
		return this.retrievePage(jqGrid);
	}
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param biddingPermissionId
	 * @param propertyName 属性名称
	 * @param propertyName 属性值
	 * @return
	 */
	public boolean checkPropertyUniquenes(String biddingPermissionId, String propertyName, String propertyValue){
		String hql = null;
		List retList = null;
		//新建时
		if(StringUtils.isBlank(biddingPermissionId)){
			hql = "from " + BiddingPermission.class.getName() + " where  " + propertyName + "= ?";
			retList = this.retrieve(hql, propertyValue);
		}else{
			//编辑时
			hql = "from " + BiddingPermission.class.getName() + " where biddingPermissionId != ? and " + propertyName + "= ?";
			retList = this.retrieve(hql, biddingPermissionId, propertyValue);
		}
		if(CollectionUtils.isEmpty(retList)){
			return true;
		}
		return false;
	}

    public Integer getMaxPermissionNo(String recTypeStr) {
		String hql = "select max(cast(substr(biddingPermissionNo,-4,3) as int)) from "
				+ BiddingPermission.class.getName() + " where biddingPermissionNo like '" + recTypeStr + "%' ";
		List<Object> result = this.find(hql);
		if (result != null && result.size() > 0 && result.get(0) != null) {
			return (Integer) result.get(0);
		}
		return null;
    }
}

