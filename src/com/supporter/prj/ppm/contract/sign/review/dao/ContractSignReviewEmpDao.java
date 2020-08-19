package com.supporter.prj.ppm.contract.sign.review.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.sign.review.entity.ContractSignReviewEmp;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 评审人员.
 * @author YAN
 * @date 2019-09-06 18:35:30
 * @version V1.0
 */
@Repository
public class ContractSignReviewEmpDao extends MainDaoSupport<ContractSignReviewEmp, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractSignReviewEmp> findPage(JqGrid jqGrid, ContractSignReviewEmp contractSignReviewEmp) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param reviewEmpId
	 * @param reviewEmpName
	 * @return
	 */
	public boolean nameIsValid(String reviewEmpId,String reviewEmpName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(reviewEmpId)) {//新建时
			hql = "from " + ContractSignReviewEmp.class.getName() + " where reviewEmpName = ?";
			retList = this.retrieve(hql, reviewEmpName);
		} else {//编辑时
			hql = "from " + ContractSignReviewEmp.class.getName() + " where reviewEmpId != ? and reviewEmpName = ?";
			retList = this.retrieve(hql, reviewEmpId, reviewEmpName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

