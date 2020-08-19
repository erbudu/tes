package com.supporter.prj.ppm.contract.pact.beian.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeian;

@Repository
public class ContractPactBeianDao extends MainDaoSupport<ContractPactBeian, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractPactBeian> findPage(JqGrid jqGrid, ContractPactBeian entity) {
		String prjId = entity.getPrjId();
		// 只显示当前项目对应的记录
		String hql = "prjId = ?";
		jqGrid.addHqlFilter(hql, prjId);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param recordId
	 * @param recordName
	 * @return
	 */
	// public boolean nameIsValid(String recordId,String recordName) {
	// String hql = null;
	// List retList = null;
	// if(StringUtils.isBlank(recordId)) {//新建时
	// hql = "from " + ContractPactBeian.class.getName() + " where recordName = ?";
	// retList = this.retrieve(hql, recordName);
	// } else {//编辑时
	// hql = "from " + ContractPactBeian.class.getName() + " where recordId != ? and
	// recordName = ?";
	// retList = this.retrieve(hql, recordId, recordName);
	// }
	// if(CollectionUtils.isEmpty(retList)) {
	// return true;
	// }
	// return false;
	// }

	/**
	 * 获取最大备案编号
	 * @return 最大备案编号
	 */
	public List<ContractPactBeian> getMaxBeianNo() {
		String hql = "from " + ContractPactBeian.class.getName() + " where recordNo is not null order by creationDate desc";
		List<ContractPactBeian> list = this.find(hql);
		return list;
	}

	/**
	 * 获取所有备案的报审id
	 */
	public List<String> getAllReportId() {
		String hql = "select reportId from " + ContractPactBeian.class.getName() + " where 1=1 group by reportId";
		List<String> reportIdList = this.find(hql);
		return reportIdList;
	}

	/**
	 * 获取某项目下所有备案完成的报审id
	 * @param prjId 项目id
	 * @return 报审id集合
	 */
	public List<String> getBeianPassRepId(String prjId) {
		String hql = "select reportId from " + ContractPactBeian.class.getName() + " where status = ? and prjId = ? group by reportId";
		return this.find(hql, ContractPactBeian.StatusCodeTable.COMPLETE, prjId);
	}

}

