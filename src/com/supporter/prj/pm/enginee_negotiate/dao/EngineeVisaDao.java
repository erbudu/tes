package com.supporter.prj.pm.enginee_negotiate.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.enginee_negotiate.constant.NegotiateConstant;
import com.supporter.prj.pm.enginee_negotiate.entity.EngineeVisa;

/**   
 * @Title: 签证
 * @Description: PM_ENGINEE_VISA.
 * @author Administrator
 * @date 2018-07-04 18:08:55
 * @version V1.0   
 *
 */
@Repository
public class EngineeVisaDao extends MainDaoSupport< EngineeVisa, String > {
	/**
	 * 分页查询
	 * @param user 用户
	 * @param jqGrid 表格参数
	 * @param engineeVisa 签证实体参数
	 * @return List< EngineeVisa >
	 */
	public List< EngineeVisa > findPage(UserProfile user, JqGrid jqGrid, EngineeVisa engineeVisa) {
		//权限限定条件hql
		String authHql = EIPService.getAuthorityService().getHqlFilter(user, 
				NegotiateConstant.MODULE_ID, NegotiateConstant.AUTH_EDIT_VISA);
		jqGrid.addHqlFilter(authHql);
		
		if (engineeVisa != null) {
			//搜索关键字(记录编号，合同名称)
			String keyword = engineeVisa.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				String likeKeyword = "%" + keyword + "%";
				String hql = "applyNo like ? or applyName like ?";
				jqGrid.addHqlFilter(hql, likeKeyword, likeKeyword);
			}

			// 类型
			String applyTypeId = engineeVisa.getApplyTypeId();
			if (StringUtils.isNotBlank(applyTypeId)) {
				String hql = "applyTypeId = ?";
				jqGrid.addHqlFilter(hql, applyTypeId);
			}

			// 状态
			Integer status = engineeVisa.getStatus();
			if (status > -1) {
				jqGrid.addHqlFilter("status = ? ", status);
			}

			// 只获取某项目下的数据
			String prjId = engineeVisa.getPrjId();
			if (StringUtils.isNotBlank(prjId)) {
				jqGrid.addHqlFilter(" prjId = ? ", prjId);
			} else {// 判断条件值任意，目的是返回一个空表
				jqGrid.addHqlFilter(" 1 != 1");
			}
		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取结算用的签证
	 * @param negotiateIds 洽商IDS
	 * @return EngineeVisa
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public EngineeVisa getValidEngineeVisa(List <String> negotiateIds) {
		if (negotiateIds == null || negotiateIds.size() == 0) {
			return null;
		}
		
		String hql = "from " + EngineeVisa.class.getName() + " where status=" + EngineeVisa.Status.COMPLETE;
		String filter = "";
		int size = negotiateIds.size();
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				filter += " or ";
			}
			filter += "negotiateId=?";
		}
		hql += " and (" + filter + ") order by createdDate desc";
		Object[] idsArray = new String[negotiateIds.size()];
		List < EngineeVisa > visas = this.retrieve(hql, negotiateIds.toArray(idsArray));
		if (visas == null || visas.size() == 0) {
			return null;
		} else {
			return visas.get(0);
		}
	}
	
	/**
	 * 判断指定字段唯一性
	 * 
	 * @param visaId 签证ID
	 * @param propertyName 属性名称
	 * @param propertyValue 属性值
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public boolean checkPropertyUniquenes(String visaId, String propertyName, String propertyValue) {
		String hql = null;
		
		List retList = null;
		//新建时
		if (StringUtils.isBlank(visaId)) {
			hql = "from " + EngineeVisa.class.getName() + " where  " + propertyName + "= ?";
			retList = this.retrieve(hql, propertyValue);
		} else {
			//编辑时
			hql = "from " + EngineeVisa.class.getName() + " where visaId != ? and " + propertyName + "= ?";
			retList = this.retrieve(hql, visaId, propertyValue);
		}
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 数据库中是否存在记录.
	 * @param visaId 签证ID
	 * @return boolean
	 */
	public boolean existInDB(String visaId) {
		String hql = "select count(visaId) as recCount from "
				+ EngineeVisa.class.getName() + " where visaId=?";
		Object obj = this.retrieveFirst(hql, visaId);
		if (obj == null) {
			return false;
		} else {
			return (Long) obj > 0;
		}
	}
}

