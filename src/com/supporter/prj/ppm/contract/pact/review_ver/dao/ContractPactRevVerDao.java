package com.supporter.prj.ppm.contract.pact.review_ver.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVer;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerCon;

@Repository
public class ContractPactRevVerDao extends MainDaoSupport<ContractPactRevVer, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractPactRevVer> findPage(JqGrid jqGrid, ContractPactRevVer revVer) {
		String prjId = revVer.getPrjId();
		// 只显示当前项目对应的记录
		String hql = "prjId = ?";
		jqGrid.addHqlFilter(hql, prjId);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param revVerId
	 * @param revVerName
	 * @return
	 */
	public boolean nameIsValid(String revVerId, String revVerName) {
		String hql = null;
		List retList = null;
		if (StringUtils.isBlank(revVerId)) {// 新建时
			hql = "from " + ContractPactRevVer.class.getName() + " where revVerName = ?";
			retList = this.retrieve(hql, revVerName);
		} else {// 编辑时
			hql = "from " + ContractPactRevVer.class.getName() + " where revVerId != ? and revVerName = ?";
			retList = this.retrieve(hql, revVerId, revVerName);
		}
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

	/**
	 * 生成评审验证编号
	 * @return 评审验证编号
	 */
	public List<ContractPactRevVer> generateReviewVerNo() {
		String hql = "from " + ContractPactRevVer.class.getName() + " where revVerNo is not null order by creationDate desc";
		List<ContractPactRevVer> list = this.find(hql);
		return list;
	}

	/**
	 * 获取所有通过评审验证的评审id集合
	 * @return
	 */
	public List<String> getPassReviewId() {
		String hql = "select revId from " + ContractPactRevVer.class.getName() + " where revVerId in(" + "select revVerId from "
				+ ContractPactRevVerCon.class.getName() + " where revConStatus=" + ContractPactRevVerCon.ConStatusCodeTable.PASS + ")";
		return this.find(hql);
	}

	/**
	 * 获取所有评审验证的评审id集合
	 * @return 
	 */
	public List<String> getReviewId() {
		String hql = "select revId from " + ContractPactRevVer.class.getName() + " where 1=1 group by revId";
		return this.find(hql);
	}

	/**
	 * 获取某项目下所有评审验证的验证结论为通过的报审id
	 * @param prjId 项目id
	 * @return 报审id集合
	 */
	public List<String> getRevVerPassReport(String prjId) {
		String hql = "select reportId from " + ContractPactRevVer.class.getName() + " where prjId = ? and revVerId in(select revVerId from "
				+ ContractPactRevVerCon.class.getName() + " where revConStatus = ?)";
		return this.find(hql, prjId, ContractPactRevVerCon.ConStatusCodeTable.PASS);
	}

}

