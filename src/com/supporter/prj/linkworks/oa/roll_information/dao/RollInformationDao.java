package com.supporter.prj.linkworks.oa.roll_information.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.roll_information.entity.RollInformation;
import com.supporter.util.CommonUtil;


/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Repository
public class RollInformationDao extends MainDaoSupport < RollInformation, String >{

	/**
	 * 分页查询
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public List<RollInformation> findPage(JqGrid jqGrid,String attr) {
		if(StringUtils.isNotBlank(attr)){
			jqGrid.addHqlFilter(
					" inforTitle like ? ", 
					"%" + attr + "%");
		}
		//添加状态的中文
		List<RollInformation> list = this.retrievePage(jqGrid);
		List<RollInformation> lists = new ArrayList<RollInformation>();
		for(RollInformation information :list){
			if(information.getPublishStatus() ==RollInformation.PUBLISHED){
				information.setStatus("已发布");
			}else{
				information.setStatus("草稿");
			}
			lists.add(information);
		}
		jqGrid.setRows(lists);
		return lists;
	}
	
	
	/**
	 * 获取已发布的滚动信息
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public List<RollInformation> getSrollInformation(JqGrid jqGrid) {
		String date = CommonUtil.format(new Date(), "yyyy-MM-dd");
		jqGrid.addHqlFilter( "  publishStatus = ?  ",  RollInformation.PUBLISHED);
		jqGrid.addHqlFilter("  publishStartDate <= ? ",date);
		jqGrid.addHqlFilter(" publishEndDate >= ? ", date);
		jqGrid.addSortPropertyDesc("modifiedDate");
		List<RollInformation> list = this.retrievePage(jqGrid);
		for(int i = 0; i <list.size();i++){
			RollInformation rollInformation = list.get(i);
			if(rollInformation.getModifiedDate().contains(date)){
				rollInformation.setIsNew(true);
				list.set(i, rollInformation);
			}
		}
		return list;
		
	}


	public List<RollInformation> getRollInformationList() {
		StringBuffer hql = new StringBuffer("from " + RollInformation.class.getName()
				+ " where 1=1 ");
		List<RollInformation> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
}
