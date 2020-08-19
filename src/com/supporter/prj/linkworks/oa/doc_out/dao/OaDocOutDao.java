package com.supporter.prj.linkworks.oa.doc_out.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.doc_out.constants.AuthConstant;
import com.supporter.prj.linkworks.oa.doc_out.constants.DocOutConstant;
import com.supporter.prj.linkworks.oa.doc_out.entity.OaDocOut;
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
public class OaDocOutDao extends MainDaoSupport<OaDocOut, String> {
	/**
	 * 查询操作
	 * 
	 * @param budgetYear
	 * @param keyword
	 * @return List < OaDocOut >
	 */
	public List<OaDocOut> findByKeyword(String keyword) {
		String hql = "from " + OaDocOut.class.getName()
				+ " where materialName like ?";
		List<OaDocOut> entities = this.find(hql, "%" + CommonUtil.trim(keyword)
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
	public List<OaDocOut> findPage(UserProfile user,JqGrid jqGrid, OaDocOut code) {
		String authHql = "";
		if (code != null) {			
			String keyWords = code.getKeyWords();
			if (StringUtils.isNotBlank(keyWords)) {
				keyWords = "%" + keyWords + "%";
				jqGrid
						.addHqlFilter(
								"docNo like ? or docTitle like ? or deptName like ? or draftsmanName like ? ",
								keyWords, keyWords, keyWords, keyWords);
			}
			Integer status = code.getDocStatus();
			if (status != null) {
				jqGrid.addHqlFilter("docStatus = ? ", status);
			}
		}
		//权限过滤
		authHql = EIPService.getAuthorityService().getHqlFilter(user,DocOutConstant.MODULE_ID,AuthConstant.AUTH_OPER_NAME__PAGESHOW);
		jqGrid.addHqlFilter(authHql);
		return this.retrievePage(jqGrid);
	}

	public List<OaDocOut> findPage(UserProfile user,JqGrid jqGrid, OaDocOut code,
			String classifyId) {
		
		if (code != null) {
			String keyWords = code.getKeyWords();
			if (StringUtils.isNotBlank(keyWords)) {
				jqGrid.addHqlFilter("keyWords like ? ", "%" + keyWords + "%");
			}
		}
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
			hql = "from " + OaDocOut.class.getName() + " where docNo = ?";
			retList = this.retrieve(hql, docNo);
		} else {// 编辑时
			hql = "from " + OaDocOut.class.getName()
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
	public List<OaDocOut> getAlltList() {
		StringBuffer hql = new StringBuffer("from " + OaDocOut.class.getName()
				+ " where 1=1 ");
		List<OaDocOut> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
}
