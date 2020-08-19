package com.supporter.prj.linkworks.oa.use_seal_apply.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.linkworks.oa.use_seal_apply.entity.UseSealApplyBoard;

/**   
 * @Title: Entity
 * @Description: 功能模块�?
 * @author liyinfeng
 * @date 2017-03-15 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class UseSealApplyBoardDao extends MainDaoSupport < UseSealApplyBoard, String > {
	/**
	 * 根据applyId获取所有留言板.
	 * @param applyId
	 * @return
	 */
	public List < UseSealApplyBoard > getMessageByApplyId(String applyId) {
		String hql = "from " + UseSealApplyBoard.class.getName() + " where reportId = ? order by useSealTime desc";
		List < UseSealApplyBoard > list = this.find(hql, applyId);
		
		if (list == null || list.size() == 0) return null;
		
		return list;
	}
	
}
