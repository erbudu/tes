package com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.entity.StampTaxItem;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 贸易印花税税目表.
 * @author LEGO
 * @date 2020-02-03 13:26:24
 * @version V1.0
 */
@Repository
public class StampTaxItemDao extends MainDaoSupport<StampTaxItem, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<StampTaxItem> findPage(JqGrid jqGrid, StampTaxItem stampTaxItem) {
		if (stampTaxItem != null) {
			String keyword = stampTaxItem.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				jqGrid.addHqlFilter(" taxItemName like ? ", "%" + keyword + "%");
			}
			// 根据创建时间倒序排列
			jqGrid.addSortPropertyAsc("orderNumber");
		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取所有印花税税目
	 * @return
	 */
	public List<StampTaxItem> getAllStampTaxItemList(){
		String hql = "from " + StampTaxItem.class.getName() + " order by orderNumber asc ";
		return this.retrieve(hql);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param itemId
	 * @param itemName
	 * @return
	 */
	public boolean nameIsValid(String itemId,String taxItemName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(itemId)) {//新建时
			hql = "from " + StampTaxItem.class.getName() + " where taxItemName = ?";
			retList = this.retrieve(hql, taxItemName);
		} else {//编辑时
			hql = "from " + StampTaxItem.class.getName() + " where itemId != ? and taxItemName = ?";
			retList = this.retrieve(hql, itemId, taxItemName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

