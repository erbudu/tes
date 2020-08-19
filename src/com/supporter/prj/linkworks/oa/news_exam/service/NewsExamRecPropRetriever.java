package com.supporter.prj.linkworks.oa.news_exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.linkworks.oa.news_exam.entity.NewsExamRec;

/**
 * NewsExamRec的IPropRetriever实现类.
 * @author linda
 *
 */
@Service
public class NewsExamRecPropRetriever extends AbstractPropRetriever {
	
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private NewsExamRecService service;

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	@Override
	public String getId() {
		return NewsExamRec.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if(entityId == null){
			return null;
		}
		return service.getNewsExamRec(entityId.toString());
	}
	   
}
