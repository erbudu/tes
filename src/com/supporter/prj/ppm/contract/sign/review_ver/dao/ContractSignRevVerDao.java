package com.supporter.prj.ppm.contract.sign.review_ver.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.sign.review_ver.entity.ContractSignRevVer;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 评审验证表.
 * @author YAN
 * @date 2019-09-09 10:46:27
 * @version V1.0
 */
@Repository
public class ContractSignRevVerDao extends MainDaoSupport<ContractSignRevVer, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractSignRevVer> findPage(JqGrid jqGrid, ContractSignRevVer contractSignRevVer) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param reviewVerId
	 * @param reviewVerName
	 * @return
	 */
	public boolean nameIsValid(String reviewVerId,String reviewVerName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(reviewVerId)) {//新建时
			hql = "from " + ContractSignRevVer.class.getName() + " where reviewVerName = ?";
			retList = this.retrieve(hql, reviewVerName);
		} else {//编辑时
			hql = "from " + ContractSignRevVer.class.getName() + " where reviewVerId != ? and reviewVerName = ?";
			retList = this.retrieve(hql, reviewVerId, reviewVerName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

