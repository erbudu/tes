package com.supporter.prj.linkworks.oa.integral.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.integral.entity.PersonIntegral;

/**
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0
 * 
 */
@Repository
public class PersonIntegralDao extends MainDaoSupport<PersonIntegral, String> {

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param contractIds
	 *            模块ids
	 * @return
	 */
	public List<PersonIntegral> findPage(JqGrid jqGrid, PersonIntegral card) {
		if (card != null) {
			// 查询
			String year = card.getYear();
			if (StringUtils.isNotBlank(year)) {
				jqGrid
						.addHqlFilter(
								"year =  ?  or personName like ? or deptName like ? or personNo like ? ",
								year, "%" + year + "%", "%" + year + "%", "%"
										+ year + "%");
			}
		}
		return this.retrievePage(jqGrid);
	}

	public Integer getLastYearIntegral(String personIntegralId,
			String personId, Integer year) {
		String hql = "";
		if (StringUtils.isBlank(personIntegralId)) {
			hql = "from " + PersonIntegral.class.getName()
					+ " where personId = ? and year = ? ";
		} else {
			hql = "from " + PersonIntegral.class.getName() + " where id!= '"
					+ personIntegralId + "' and personId = ? and year = ? ";
		}
		String year2 = String.valueOf(year);
		List retList = find(hql, new Object[] { personId, year2 });
		if (CollectionUtils.isEmpty(retList)) {
			String year3 = String.valueOf(year - 1);
			List retList2 = find(hql, new Object[] { personId, year3 });
			if (CollectionUtils.isEmpty(retList2)) {
				return 0;
			} else {
				PersonIntegral entity = (PersonIntegral) retList2.get(0);
				Integer integral = entity.getFinalIntegral();
				if (entity.getFinalIntegral() != null) {
					return integral;
				} else {
					return 0;
				}
			}
		}
		return -1;
	}

	public List<PersonIntegral> findBySearch(PersonIntegral entity) {
		String hql = "from " + PersonIntegral.class.getName()
		+ " where 1 =1 ";
		Map<String,String> map = new HashMap<String,String>();
		String year = entity.getYear();
		if (StringUtils.isNotBlank(year)) {
			hql+= " and year = :year ";
			map.put("year",year);
		}
		String deptId = entity.getDeptId();
		if (StringUtils.isNotBlank(deptId)) {
			hql+= " and deptId = :deptId ";
			map.put("deptId",deptId);
		}
		return find(hql,map);
	}
}
