package com.supporter.prj.linkworks.oa.news_exam.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.eip.news.constant.DefaultNewsTypeEnum;
import com.supporter.prj.meip_service.news.entity.News;

/**
 * @Title: Entity
 * @Description: 需要在主页上显示的主题新闻.
 * @author ts
 * @date 2017-07-07 14:45:29
 * @version V1.0
 *
 */
@Repository
public class NewsGenerateHtmlDao extends MainDaoSupport < News, String > {
    /**
     * 根据返回新闻列表.
     * 
     * @param queryCount 查询条数
     * @return
     */
    public List < News > getNewsList(int queryCount) {
        String hql = "from " + News.class.getName()
                + " where typeId = ? and status = ? and (expiryTime is null or expiryTime >= ?) order by publishTime desc";
        
        ListPage < News > listPage = new ListPage < News >();
        listPage.setPageNo(1);
        if (queryCount <= 0)queryCount = 4;
        listPage.setPageSize(queryCount);
        return retrievePage(listPage, hql, new Object[]{DefaultNewsTypeEnum.NEWS.getTypeId(),
        		News.Status.published.getKey(), (new Date())}).getRows();
    }
    
    
}
