package com.supporter.prj.linkworks.oa.doc_in.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.doc_in.constants.AuthConstant;
import com.supporter.prj.linkworks.oa.doc_in.entity.DocIn;
import com.supporter.prj.linkworks.oa.doc_in.util.DocInConstant;
import com.supporter.prj.linkworks.oa.report.entity.Report;
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
public class DocInDao extends MainDaoSupport<DocIn, String> {
	/**
	 * 查询操作
	 * 
	 * @param budgetYear
	 * @param keyword
	 * @return List < DocIn >
	 */
	public List<DocIn> findByKeyword(String keyword) {
		String hql = "from " + DocIn.class.getName() + " where keyWords like ?";
		List<DocIn> entities = this.find(hql, "%" + CommonUtil.trim(keyword)
				+ "%");
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
	public List<DocIn> findPage(UserProfile user, JqGrid jqGrid, DocIn code) {
		String authHql = "";
		if (code != null) {
			String key = code.getDocTitle();
			if (StringUtils.isNotBlank(key)) {
				key = "%" + key + "%";
				jqGrid
						.addHqlFilter(
								"docTitle like ? or docNo like ? or inNo like ? or publisherName "
										+ "like ? or docType like ? or createdDate like ? ",
								key, key, key, key, key, key);
			}
			Long docClassify = code.getDocClassify();
			if (docClassify != null) {
				jqGrid.addHqlFilter("docClassify = ?", docClassify);
			}
			Integer status = code.getDocStatus();
			if (status != null) {
				jqGrid.addHqlFilter("docStatus = ?", status);
			}
		}
		// 权限过滤
		authHql = EIPService.getAuthorityService().getHqlFilter(user,
				DocInConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME_PAGESHOW);
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
			hql = "from " + DocIn.class.getName() + " where docNo = ?";
			retList = this.retrieve(hql, docNo);
		} else {// 编辑时
			hql = "from " + DocIn.class.getName()
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
	public List<DocIn> getDocIntList() {
		StringBuffer hql = new StringBuffer("from " + DocIn.class.getName()
				+ " where 1=1 ");
		List<DocIn> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
}
