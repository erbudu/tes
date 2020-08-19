package com.supporter.prj.linkworks.oa.news_exam.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.linkworks.oa.news_exam.entity.NewsExamRec;

/**
 * 查看业务单实现类.
 * @author linda
 *
 */
@Service
public class NewsExamRecViewerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		 return NewsExamRec.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/oa/news_exam/news_exam_view.html?recId=" + entityId;
	}
	   
}
