package com.supporter.prj.linkworks.oa.news_alerts.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.news_alerts.entity.NewsAlerts;
import com.supporter.util.CommonUtil;
/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Repository
public class NewsAlertsDao extends MainDaoSupport < NewsAlerts, String >{

//	@Autowired
//	private NewsAlertsContentDao newsAlertsContentDao;
	
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public List<NewsAlerts> findPage(JqGrid jqGrid,String attr) {
		if(StringUtils.isNotBlank(attr)){
			jqGrid.addHqlFilter(
					" newsTitle like ? ", 
					"%" + attr + "%");
		}
		return this.retrievePage(jqGrid);
	}
	String ls_SQL =	"select news_alerts_id from oa_news_alerts "
			+ " where publish_status = " + NewsAlerts.PUBLISHED
			+ " and publish_start_date <= '" + CommonUtil.format(new Date(), "yyyy-MM-dd") + "'"
			+ " and publish_end_date >= '" + CommonUtil.format(new Date(), "yyyy-MM-dd") + "'"
			+ " order by modified_date desc";
	
	/**
	 * 获取已发布的新闻快讯
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public NewsAlerts getNewAlerts(JqGrid jqGrid) {
		String date = CommonUtil.format(new Date(), "yyyy-MM-dd");
		jqGrid.addHqlFilter( "  publishStatus = ?  ",  NewsAlerts.PUBLISHED);
		jqGrid.addHqlFilter("  publishStartDate <= ? ",date);
		jqGrid.addHqlFilter(" publishEndDate >= ? ", date);
		jqGrid.addSortPropertyDesc("modifiedDate");
		List<NewsAlerts> list = this.retrievePage(jqGrid);
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}

	public List<NewsAlerts> getNewsAlertsList() {
		StringBuffer hql = new StringBuffer("from " + NewsAlerts.class.getName()
				+ " where 1=1 ");
		List<NewsAlerts> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
}
