package com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.dao;
import com.supporter.prj.cneec.pc.pre_prj_manager.develop_agreement.entity.DevelopAgreement;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**   
 * @Title: Entity
 * @Description: 开发合作.
 * @author kangh_000
 * @date 2018-12-14 17:09:39
 * @version V1.0   
 *
 */
@Repository
public class DevelopAgreementDao extends MainDaoSupport< DevelopAgreement, String > {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List< DevelopAgreement > findPage(UserProfile user, JqGrid jqGrid, DevelopAgreement developAgreement) {
		//权限限定条件hql
		//String authHql = EIPService.getAuthorityService().getHqlFilter(user, AuthConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME);
		//jqGrid.addHqlFilter(authHql);
		if(developAgreement != null){
			//搜索关键字
			String keyword = developAgreement.getKeyword();
			if(StringUtils.isNotBlank(keyword)){
				String likeKeyword = "%" + keyword + "%";
				String hql = "(agreementNo like ? or agreementTypeName like ? or agreementName like ? or agreementOtherSide like ?)";
				jqGrid.addHqlFilter(hql, likeKeyword, likeKeyword, likeKeyword, likeKeyword);
			}
		}

		return this.retrievePage(jqGrid);
	}
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param developAgreementId
	 * @param propertyName 属性名称
	 * @param propertyName 属性值
	 * @return
	 */
	public boolean checkPropertyUniquenes(String developAgreementId, String propertyName, String propertyValue){
		String hql = null;
		List retList = null;
		//新建时
		if(StringUtils.isBlank(developAgreementId)){
			hql = "from " + DevelopAgreement.class.getName() + " where  " + propertyName + "= ?";
			retList = this.retrieve(hql, propertyValue);
		}else{
			//编辑时
			hql = "from " + DevelopAgreement.class.getName() + " where developAgreementId != ? and " + propertyName + "= ?";
			retList = this.retrieve(hql, developAgreementId, propertyValue);
		}
		if(CollectionUtils.isEmpty(retList)){
			return true;
		}
		return false;
	}

    public Integer getMaxNo(String noPre) {
		String hql = "select max(cast(substr(agreementNo,-3,3) as int)) from "
				+ DevelopAgreement.class.getName() + " where agreementNo like '" + noPre + "%' ";
		List<Object> result = this.find(hql);
		if (result != null && result.size() > 0 && result.get(0) != null) {
			return (Integer) result.get(0);
		}
		return null;
    }
}

