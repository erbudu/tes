package com.supporter.prj.cneec.tpc.custom.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.custom.entity.Custom;
import com.supporter.prj.cneec.tpc.custom.entity.CustomLinkman;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class CustomLinkmanDao extends MainDaoSupport<CustomLinkman, String> {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param linkman
	 * @return
	 */
	public List<CustomLinkman> findPage(JqGrid jqGrid, String customId){
		if (StringUtils.isNotBlank(customId)){
	        //根据客户编号获取该客户名下的联系人
			jqGrid.addHqlFilter(" customId = ? ", customId);
			//根据是否主联系人排序
			jqGrid.addSortPropertyDesc("isMainLinkman");
			return this.retrievePage(jqGrid);
		}
		List<CustomLinkman> list = new ArrayList<CustomLinkman>();
		return list;
	}
	
	/**
	 * 删除该客户下所有联系人
	 */
	public void deleteInner(Custom custom){
		List<CustomLinkman> linkmans = getLinkmanByCustomId(custom.getCustomId());
		this.delete(linkmans);
	}
	
	/**
	 * 根据客户ID获取联系人列表
	 * @param abroadRecordId
	 * @return
	 */
	public List<CustomLinkman> getLinkmanByCustomId(String customId) {
		String hql = "from " + CustomLinkman.class.getName()
				+ " where customId = ? ";
		List<CustomLinkman> linkmans = this.find(hql, customId);
		if (linkmans == null || linkmans.size() == 0) {
			return null;
		}
		return linkmans;
	}
	
	/**
	 * 获取该客户下的主联系人
	 * @param customId
	 * @return
	 */
	public List<CustomLinkman> getIsMainLinkmanList(String customId, String linkmanId){
		List<CustomLinkman> linkmans = new ArrayList();
		String hql = "from " + CustomLinkman.class.getName()
				+ " where customId = ? and isMainLinkman = 1 and linkmanId <> ? ";
		linkmans = this.find(hql, customId,linkmanId);
		if (linkmans == null || linkmans.size() == 0) {
			return null;
		}
		return linkmans;
	}
}
