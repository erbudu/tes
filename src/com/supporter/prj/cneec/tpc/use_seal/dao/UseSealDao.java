package com.supporter.prj.cneec.tpc.use_seal.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.use_seal.entity.UseSeal;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: UseSealDao
 * @Description: DAO类
 * @author: yanweichao
 * @date: 2017-10-23
 * @version: V1.0
 */
@Repository
public class UseSealDao extends MainDaoSupport<UseSeal, String> {

	/**
	 * 分页查询
	 */
	public List<UseSeal> findPage(JqGrid jqGrid, UseSeal useSeal, String authFilter) {
		if (useSeal != null) {
			// 列表页面搜索输入框可查询字段
			String keyword = useSeal.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" projectName like ? or contractCopies like ? or sealDeptName like ? or stampPerson like ? or stampDate like ?",
									"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
			}
			// 用印类型记录过滤
			String relyBy = useSeal.getRelyBy();
			if (StringUtils.isNotBlank(relyBy)) {// 用印依赖类型
				jqGrid.addHqlFilter(" relyBy = ? ", relyBy);
			} else {
				jqGrid.addHqlFilter(" 1 <> 1 ");
			}
			/* 以下是更多条件中选择项 */
			// 状态过滤
			if (useSeal.getSwfStatus() != null) {
				jqGrid.addHqlFilter(" swfStatus = ? ", useSeal.getSwfStatus());
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 获取当前年月最大流水号
	 * 
	 * @param NoHead
	 * @return
	 */
	public Integer getSerialNumberIndex(String NoHead) {
		Integer count = 1;
		String hql = "select max(t.useSealNo) from " + UseSeal.class.getName() + " t where t.useSealNo like ?";
		List<String> list = this.find(hql, NoHead + "%");
		if (list.size() > 0) {
			String str = list.get(0);
			if (StringUtils.isNotBlank(str)) {
				String count_s = str.substring(str.length() - 3);
				Integer b = Integer.parseInt(count_s);
				count = b > 0 ? (b + 1) : 1;
			}
		}
		return count;
	}

}
