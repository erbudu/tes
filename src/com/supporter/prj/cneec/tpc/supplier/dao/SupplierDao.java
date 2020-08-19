package com.supporter.prj.cneec.tpc.supplier.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.supplier.entity.Supplier;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class SupplierDao extends MainDaoSupport<Supplier, String> {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param supplierId 模块ids
	 * @return
	 */
	public List<Supplier> findPage(JqGrid jqGrid, Supplier supplier, String authFilter){
		if (supplier != null){
			//查询
			String keyword = supplier.getKeyword();
			if(StringUtils.isNotBlank(keyword)){
				jqGrid.addHqlFilter(
						" supplierName like ? or address like ? ", 
						"%" + keyword + "%",
						"%" + keyword + "%");
			}
			//有效状态过滤
			if (StringUtils.isNotBlank(supplier.getSupplierControlStatusCode())){
				jqGrid.addHqlFilter(" supplierControlStatusCode = ? ", supplier.getSupplierControlStatusCode());
			}
	        //根据创建时间倒叙排列
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 判断名字是否重复
	 * 
	 * @param supplierId
	 * @param supplierName
	 * @return
	 */
	public boolean checkNameIsValid(String supplierId, String supplierName) {
		String hql = null;
		List retList = null;
		if (StringUtils.isBlank(supplierId)) {// 新建时
			hql = "from " + Supplier.class.getName() + " where supplierName = ?";
			retList = this.retrieve(hql, supplierName);
		} else {// 编辑时
			hql = "from " + Supplier.class.getName() + " where supplierId != ? and supplierName = ?";
			retList = this.retrieve(hql, supplierId, supplierName);
		}
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

	/** 以下是选择供应商方法 **/

	public ListPage<Supplier> getListPage(ListPage<Supplier> listPage, Map<String, Object> parameters, String authFilter) throws ParseException {
		String hql = getHql(parameters, authFilter);
		return retrievePage(listPage, hql, parameters);
	}

	private String getHql(Map<String, Object> parameters, String authFilter) throws ParseException {
		StringBuffer hql = new StringBuffer();
		hql.append("from " + Supplier.class.getName() + " t where 1=1");
		String keyword = (String) parameters.get("keyword");
		if (StringUtils.isNotBlank(keyword)) {
			hql.append(" and (t.supplierName like :keyword)");
			parameters.put("keyword", "%" + keyword + "%");
		}
		hql.append(" order by t.supplierName");
		return hql.toString();
	}

}
