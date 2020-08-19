package com.supporter.prj.linkworks.oa.invitation_f.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;
import com.supporter.prj.linkworks.oa.invitation_f.constants.InvitationAuthConstant;
import com.supporter.prj.linkworks.oa.invitation_f.entity.InvitationForeignerApply;
import com.supporter.prj.linkworks.oa.invitation_f.util.InvitationConstant;
import com.supporter.util.CommonUtil;

/**
 * @Title: Entity
 * @Description: 物资档案设置
 * @author yanbingchao
 * @date 2017-03-27 14:00:00
 * @version V1.0
 * 
 */
@Repository
public class InvitationForeignerApplyDao extends
		MainDaoSupport<InvitationForeignerApply, String> {
	/**
	 * 查询操作
	 * 
	 * @param budgetYear
	 * @param keyword
	 * @return List < InvitationForeignerApply >
	 */
	public List<InvitationForeignerApply> findByKeyword(String keyword) {
		String hql = "from " + InvitationForeignerApply.class.getName()
				+ " where keyWords like ?";
		List<InvitationForeignerApply> entities = this.find(hql, "%"
				+ CommonUtil.trim(keyword) + "%");
		return entities;
	}

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param codeIds
	 *            物资ids
	 * @return
	 */
	public List<InvitationForeignerApply> findPage(UserProfile user,JqGrid jqGrid,
			InvitationForeignerApply code) {
		String authHql="";
		if (code != null) {
			String key = code.getSearchKeyword();
			if (key != null) {
				key = "%" + key + "%";
				jqGrid
						.addHqlFilter(
								" applyReason like ? or applyName like ? or deptName like ?",
								key, key, key);
			}
		}
		//权限过滤
		authHql = EIPService.getAuthorityService().getHqlFilter(user,InvitationConstant.MODULE_ID,InvitationAuthConstant.AUTH_OPER_NAME__PAGESHOW);
		jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param codeId
	 * @param materialName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkNameIsValid(String docId, String docNo) {
		String hql = null;
		List retList = null;
		if (StringUtils.isBlank(docId)) {// 新建时
			hql = "from " + InvitationForeignerApply.class.getName()
					+ " where docNo = ?";
			retList = this.retrieve(hql, docNo);
		} else {// 编辑时
			hql = "from " + InvitationForeignerApply.class.getName()
					+ " where docId != ? and docNo = ?";
			retList = this.retrieve(hql, docId, docNo);
		}
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}
	/**
	 * 获取所有的记录.
	 * 
	 * @param user
	 * @return
	 */
	public List<InvitationForeignerApply> getAllList() {
		StringBuffer hql = new StringBuffer("from " + InvitationForeignerApply.class.getName()
				+ " where 1=1 ");
		List<InvitationForeignerApply> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
}
