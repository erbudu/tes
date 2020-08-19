package com.supporter.prj.ppm.prj_org.member_duty.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.prj_org.member_duty.entity.MemberDutyEntity;

/**
 * MemberDutyDao
 * @author CHENHAO
 * @date 2019年12月2日
 */

@Repository
public class MemberDutyDao extends MainDaoSupport<MemberDutyEntity, String>{

	/**
	 * 获取分页的各业务负责人列表
	 * @param jqGrid
	 * @param prjId
	 * @return
	 */
	public List<MemberDutyEntity> getGrid(JqGrid jqGrid, String prjId) {

		String hql = "prjId = ?";
		
		jqGrid.addHqlFilter(hql, new Object[] {prjId});
		
		return retrievePage(jqGrid);
	}

	/**
	 * 人名查重
	 * @param prjId
	 * @param personId
	 * @param recordId
	 * @return
	 */
	public boolean isRepeat(String prjId, String personId, String recordId) {

		String hql = "from " + MemberDutyEntity.class.getName() +" where personId = ? and prjId = ?";
		
		if(recordId != "" && recordId != null) {
			
			hql = hql + " and recordId != ?";
			
			return this.find(hql, personId, prjId, recordId).size() == 0;
		}
		
		return this.find(hql, personId, prjId).size() == 0;
		
	}

}
