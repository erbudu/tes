package com.supporter.prj.ud.func.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ud.func.entity.UdFuncPageCell;
import com.supporter.util.CommonUtil;

/**
 * @Title: dao
 * @Description: 数据迁移业务注册
 * @author yanbingchao
 * @date 2017-08-09 14:00:00
 * @version V1.0
 * 
 */
@Repository
public class UdFuncPageCellDao extends
		MainDaoSupport<UdFuncPageCell, String> {
	/**
	 * 查询操作
	 * 
	 * 
	 * @param keyword
	 * @return List < UdFuncPageCell >
	 */
	public List<UdFuncPageCell> findByKeyword(String keyword) {
		String hql = "from " + UdFuncPageCell.class.getName()
				+ " where pageId = ? order by listId";
		List<UdFuncPageCell> entities = this.find(hql, keyword);
		return entities;
	}

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param code
	 *            
	 * @return
	 */
	// 通过页面id查询页面表单
	public List<UdFuncPageCell> findPage(JqGrid jqGrid,
			UdFuncPageCell code) {
		if (code != null) {
			String key = code.getPageId();
			if (StringUtils.isNotBlank(key)) {
				jqGrid.addHqlFilter(
						" pageId = ? ",key);
			}
		}
		return this.retrievePage(jqGrid);
	}
}
