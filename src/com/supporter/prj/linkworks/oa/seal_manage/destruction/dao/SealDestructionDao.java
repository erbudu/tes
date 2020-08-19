package com.supporter.prj.linkworks.oa.seal_manage.destruction.dao;
import org.springframework.stereotype.Repository;
import com.supporter.prj.linkworks.oa.seal_manage.destruction.entity.SealDestruction;
import com.supporter.prj.linkworks.oa.seal_manage.engrave.entity.SealEngrave;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**   
 * @Title: Entity
 * @Description: 请假单数据表.
 * @author z
 * @date 2019-12-12 16:59:13
 * @version V1.0   
 *
 */
@Repository
public class SealDestructionDao extends MainDaoSupport< SealDestruction, String > {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List< SealDestruction > findPage(JqGrid jqGrid, SealDestruction sealDestruction, UserProfile user) {
		if (sealDestruction != null) {
			String sealName = sealDestruction.getSealName();
			if (StringUtils.isNotBlank(sealName)) {
				jqGrid.addHqlFilter(
						"(sealName like ? or createdBy like ? or deptName like ?)",
						"%" + sealName + "%", "%" + sealName + "%", "%" + sealName + "%");
			}
//			// 印章来源
			Integer sealSource = sealDestruction.getSealSource();
			if (sealSource != null) {
				jqGrid.addHqlFilter("sealSource = ? ", sealSource);
			}
//			// 状态
			Integer status = sealDestruction.getStatus();
			if (status != null) {
				jqGrid.addHqlFilter("status = ? ", status);
			}
			//权限过滤
			String authHql = EIPService.getAuthorityService().getHqlFilter(user,SealDestruction.MODULE_ID, SealDestruction.AUTH_DATASHOWAUTH);
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
			hql = "from " + SealDestruction.class.getName() + " where  applyName = ?";
			retList = this.retrieve(hql, applyName);
		}else{//编辑时
			hql = "from " + SealDestruction.class.getName() + " where applyId != ? and applyName = ?";
			retList = this.retrieve(hql, applyId, applyName);
		}
		if(CollectionUtils.isEmpty(retList)){
			return true;
		}
		return false;
	}
}

