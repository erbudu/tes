package com.supporter.prj.linkworks.oa.invitation_f.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.invitation_f.entity.InvitationForeignerApply;
import com.supporter.prj.linkworks.oa.invitation_f.entity.InvitationPersons;
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
public class InvitationPersonsDao extends
		MainDaoSupport<InvitationPersons, String> {
	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param codeIds
	 *            物资ids
	 * @return
	 */
	public List<InvitationPersons> findPage(JqGrid jqGrid, String invitationId) {
		if (StringUtils.isNotBlank(invitationId)) {
			jqGrid.addHqlFilter("invitationId = ? ", invitationId);
			return this.retrievePage(jqGrid);
		}
		List<InvitationPersons> li = new ArrayList<InvitationPersons>();
		return li;

	}

	public List<InvitationPersons> getPersonsByApply(String invitationId) {
		if (StringUtils.isNotBlank(invitationId)) {
			String hql = "from " + InvitationPersons.class.getName()
					+ " where invitationId = ?";
			List<InvitationPersons> entities = this.find(hql,CommonUtil.trim(invitationId));
			return entities;
		}
		return null;

	}

}
