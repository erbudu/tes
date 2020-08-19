package com.supporter.prj.cneec.pc.pre_prj_manager.export_control.dao;
import com.supporter.prj.cneec.pc.pre_prj_manager.export_control.entity.ExportControl;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**   
 * @Title: Entity
 * @Description: 出口管制.
 * @author kangh_000
 * @date 2018-12-20 17:53:29
 * @version V1.0   
 *
 */
@Repository
public class ExportControlDao extends MainDaoSupport< ExportControl, String > {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List< ExportControl > findPage(UserProfile user, JqGrid jqGrid, ExportControl exportControl) {
		//权限限定条件hql
		//String authHql = EIPService.getAuthorityService().getHqlFilter(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME);
		//jqGrid.addHqlFilter(authHql);
		if(exportControl != null){
			//搜索关键字
			String keyword = exportControl.getKeyword();
			if(StringUtils.isNotBlank(keyword)){
				String likeKeyword = "%" + keyword + "%";
				String hql = "countryCode like ? countryNameZh like ? countryNameEn like ? reginNameZh like ? reginNameEn like ? nameZh like ? nameEn like ? ";
				jqGrid.addHqlFilter(hql, likeKeyword, likeKeyword, likeKeyword, likeKeyword, likeKeyword, likeKeyword, likeKeyword);
			}

			String type = exportControl.getType();

			if(StringUtils.isNotBlank(type)){
				String hql = "";

				if (type.equals("0")) {
					hql = "controlCategory = 0";
				} else if (type.equals("1")) {
					hql = "controlCategory = 1 or controlCategory = 2";
				} else {
					hql = "controlCategory = 3";
				}

				jqGrid.addHqlFilter(hql);
			}
		}
		return this.retrievePage(jqGrid);
	}
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param exportControlId
	 * @param propertyName 属性名称
	 * @param propertyName 属性值
	 * @return
	 */
	public boolean checkPropertyUniquenes(String exportControlId, String propertyName, String propertyValue){
		String hql = null;
		List retList = null;
		//新建时
		if(StringUtils.isBlank(exportControlId)){
			hql = "from " + ExportControl.class.getName() + " where  " + propertyName + "= ?";
			retList = this.retrieve(hql, propertyValue);
		}else{
			//编辑时
			hql = "from " + ExportControl.class.getName() + " where exportControlId != ? and " + propertyName + "= ?";
			retList = this.retrieve(hql, exportControlId, propertyValue);
		}
		if(CollectionUtils.isEmpty(retList)){
			return true;
		}
		return false;
	}
}

