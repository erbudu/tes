package com.supporter.prj.linkworks.oa.util.file_upload.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.user_defined.entity.UCard;
import com.supporter.prj.linkworks.oa.util.file_upload.entity.FupUpload;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class FupUploadDao extends MainDaoSupport < FupUpload, String > {
	

	/**
	 * 获取所有的记录.
	 * 
	 * @param user
	 * @return
	 */
	public List<FupUpload> getAllList(String relatedTable,String relatedValues) {
		StringBuffer hql = new StringBuffer("from " + FupUpload.class.getName()
				+ " where 1=1 ");
		if(StringUtils.isNotBlank(relatedTable)){
			hql.append("and relatedTable = '"+relatedTable+"'");
		}
		if(StringUtils.isNotBlank(relatedValues)){
			hql.append(" and relatedValues = '"+relatedValues+"' ");
		}		
		List<FupUpload> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
}
