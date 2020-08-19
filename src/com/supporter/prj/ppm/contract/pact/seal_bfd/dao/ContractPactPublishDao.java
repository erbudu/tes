package com.supporter.prj.ppm.contract.pact.seal_bfd.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.ContractPactPublish;

@Repository
public class ContractPactPublishDao extends MainDaoSupport<ContractPactPublish, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractPactPublish> findPage(JqGrid jqGrid, ContractPactPublish publish) {
		String prjId = publish.getPrjId();
		// 只显示当前项目对应的记录
		String hql = "prjId = ?";
		jqGrid.addHqlFilter(hql, prjId);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 编号生成规则
	 * @return 协议出版编号
	 */
	public List<ContractPactPublish> generatePublicNo() {
		String hql = "from " + ContractPactPublish.class.getName() + " where publishNo is not null order by creationDate desc";
		List<ContractPactPublish> list = this.find(hql);
		return list;
	}

	/**
	 * 获取协议出版报审id集合
	 * @return
	 */
	public List<String> getReportId() {
		String hql = "select reportId from " + ContractPactPublish.class.getName() + " where 1=1 group by reportId";
		List<String> list = this.find(hql);
		return list;
	}

	// /**
	// * 判断名字是否重复
	// *
	// * @param publishId
	// * @param publishName
	// * @return
	// */
	// public boolean nameIsValid(String publishId,String publishName) {
	// String hql = null;
	// List<> retList = null;
	// if(StringUtils.isBlank(publishId)) {//新建时
	// hql = "from " + ContractPactPublish.class.getName() + " where publishName =
	// ?";
	// retList = this.retrieve(hql, publishName);
	// } else {//编辑时
	// hql = "from " + ContractPactPublish.class.getName() + " where publishId != ?
	// and publishName = ?";
	// retList = this.retrieve(hql, publishId, publishName);
	// }
	// if(CollectionUtils.isEmpty(retList)) {
	// return true;
	// }
	// return false;
	// }

}

