package com.supporter.prj.ppm.meeting_notice.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.meeting_notice.constant.MeetingNoticeContant;
import com.supporter.prj.ppm.meeting_notice.entity.MeetingNoticeEntity;
import com.supporter.prj.ppm.poa.use_seal.constant.UseSealConstant;
import com.supporter.prj.ppm.poa.use_seal.entity.UseSealStartEntity;

/**
 *<p>Title: MeetingNoticeDao</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author CHENHAO
 * @date 2019年11月25日
 * 
 */
@Repository
public class MeetingNoticeDao extends MainDaoSupport<MeetingNoticeEntity, String>{

	/**
	 * <pre>
	 * @Titile : 通过业务单主键名称和值获取会议通知实体
	 * @param businesPkName		业务单主键名称
	 * @param businesPkValue	业务单主键值
	 * @return	会议通知实体
	 * </pre>
	 */
	public MeetingNoticeEntity getByBusinesPk(String businesPkName, String businesPkValue) {

		String hql = "from "+MeetingNoticeEntity.class.getName()+" where businesPkName = ? and businesPkValue = ?";
		
		return this.findUniqueResult(hql, businesPkName, businesPkValue);
	}

	/**
	 * <pre>
	 * @Title : 获取会议通知列表
	 * @param jqGrid
	 * </pre>
	 * @param businesPkName 
	 * @param businesPkValue 
	 * @param prjId 
	 */
	public List<MeetingNoticeEntity> getGrid(JqGrid jqGrid, String businesPkValue, String businesPkName, String prjId) {
		
		
		if(!businesPkValue.isEmpty() && !businesPkName.isEmpty()) {
			
			String hql = "businesPkValue = ? and businesPkName = ?";
			
			jqGrid.addHqlFilter(hql, new Object[] {businesPkValue, businesPkName});
		}
		
		if(prjId != null && prjId != "") {
			
			String hql = "prjId = ?";
			
			jqGrid.addHqlFilter(hql, new Object[] {prjId});
		}
		
		return retrievePage(jqGrid);
		
	}

}
