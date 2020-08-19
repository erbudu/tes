package com.supporter.prj.ppm.ecc.base_info.fund_plan.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.ecc.base_info.fund_plan.entity.EccFundPlan;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 出口管制资金安排.
 * @author YAN
 * @date 2019-08-16 18:42:25
 * @version V1.0
 */
@Repository
public class EccFundPlanDao extends MainDaoSupport<EccFundPlan, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<EccFundPlan> findPage(JqGrid jqGrid, EccFundPlan eccFundPlan) {
		if (eccFundPlan!=null){
			String eccId = eccFundPlan.getEccId();
			if (StringUtils.isNotBlank(eccId)){
				jqGrid.addHqlFilter(" eccId = ? ",eccId);
				return this.retrievePage(jqGrid);
			}
		}
		return null;
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param fpId
	 * @param fpName
	 * @return
	 */
	public boolean nameIsValid(String fpId,String fpName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(fpId)) {//新建时
			hql = "from " + EccFundPlan.class.getName() + " where fpName = ?";
			retList = this.retrieve(hql, fpName);
		} else {//编辑时
			hql = "from " + EccFundPlan.class.getName() + " where fpId != ? and fpName = ?";
			retList = this.retrieve(hql, fpId, fpName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

