package com.supporter.prj.pm.procure_contract.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractSwf;

/**
 * @Title: ProcureContractSwfDao
 * @Description: DAO类
 * @author yanweichao
 * @date 2018-12-12
 * @version: V1.0
 */
@Repository
public class ProcureContractSwfDao extends MainDaoSupport<ProcureContractSwf, String> {

	/**
	 * 分页查询
	 * 
	 * @param user
	 *            用户
	 * @param jqGrid
	 *            表格请求参数
	 * @param swf
	 *            付款申请流程对象
	 * @return List< ProcureContractSwf >
	 */
	public List<ProcureContractSwf> findPage(UserProfile user, JqGrid jqGrid, ProcureContractSwf swf) {
		if (swf != null) {
			// 搜索关键字
			String keyword = swf.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				String likeKeyword = "%" + keyword + "%";
				String hql = "contractName like ?";
				jqGrid.addHqlFilter(hql, likeKeyword);
			}
		}
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取尚未启动流程的业务对象
	 * @return List < ProcureContractSwf >
	 */
	public List<ProcureContractSwf> getNotStartProcList() {
		String hql = "from " + ProcureContractSwf.class.getName()
				+ " where oaProcId is null or oaExamStatus=?";
		return this.find(hql, ProcureContractSwf.OAExamStatus.DRAFT);
	}

}
