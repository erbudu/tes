package com.supporter.prj.cneec.tpc.custom.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.custom.entity.Custom;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

@Repository
public class CustomDao extends MainDaoSupport<Custom, String> {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<Custom> findPage(JqGrid jqGrid, Custom custom, String authFilter){
		if (custom != null){
			//查询
			String keyword = custom.getKeyword();
			if(StringUtils.isNotBlank(keyword)){
				jqGrid.addHqlFilter(
						" customerName like ? or customerNo like ? ", 
						"%" + keyword + "%",
						"%" + keyword + "%");
			}
			 //状态过滤
	        if(StringUtils.isNotBlank(custom.getCustomControlStatusCode())){//是否有效
	        	jqGrid.addHqlFilter(" customControlStatusCode = ? " ,custom.getCustomControlStatusCode());
	        }
	        if(custom.getStatus() != null){//是否提交
	        	jqGrid.addHqlFilter(" status = ? ", custom.getStatus());
	        }
	        //根据客户编号倒叙排列
			jqGrid.addSortPropertyDesc("customerNo");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 判断名字是否重复
	 * 
	 * @param contractId
	 * @param contractName
	 * @return
	 */
	public boolean checkNameIsValid(String customId, String customerName) {
		String hql = null;
		List retList = null;
		if (StringUtils.isBlank(customId)) {// 新建时
			hql = "from " + Custom.class.getName() + " where customerName = ?";
			retList = this.retrieve(hql, customerName);
		} else {// 编辑时
			hql = "from " + Custom.class.getName() + " where customId != ? and customerName = ?";
			retList = this.retrieve(hql, customId, customerName);
		}
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}
}
