package com.supporter.prj.ppm.contract.pact.report.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.com_codetable.entity.CodetableItem;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeian;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReport;

@Repository
public class ContractPactReportDao extends MainDaoSupport<ContractPactReport, String> {
	/**
	 * 获取合同及协议报审列表
	 * @param jqGrid 列表
	 * @param report 实体对象
	 * @param user 当前登录用户
	 * @return 合同及协议报审列表
	 */
	public List<ContractPactReport> findPage(JqGrid jqGrid, ContractPactReport report, UserProfile user) {
		String prjId = report.getPrjId();
		// 只显示当前项目对应的记录
		String hql = "prjId = ?";
		jqGrid.addHqlFilter(hql, prjId);
		return this.retrievePage(jqGrid);
	}

	/**
	 *  获取某项目下已备案完成的报审列表,且报审的合同及协议类型为项目开发合作协议下子项
	 * @param jqGrid 列表
	 * @param report 实体对象
	 * @param user 当前登录用户
	 * @param keyword 模糊查询关键字
	 * @return 合同及协议报审列表
	 */
	public List<ContractPactReport> getBeianPassRepGrid(JqGrid jqGrid, ContractPactReport report, UserProfile user, String keyword) {
		String prjId = report.getPrjId();
		// 某项目下备案完成的报审记录
		String hql = "reportId in (select reportId from " + ContractPactBeian.class.getName() + " where prjId = ? and status = ?)";
		jqGrid.addHqlFilter(hql, prjId, ContractPactBeian.StatusCodeTable.COMPLETE);
		// 报审的合同及协议类型为项目开发合作协议下子项
		hql = "contractPactTypeId in (select codetableItemId from " + CodetableItem.class.getName()
				+ " where parentItemId = (select codetableItemId from " + CodetableItem.class.getName()
				+ " where displayNameZh = ? and codetableId = ?))";
		jqGrid.addHqlFilter(hql, "项目开发合作协议", "PPM_CONTRACT_PACT_TYPE");
		//模糊查询
		if(StringUtils.isNotBlank(keyword)){
			hql = "reportNo like ?  or reportName like ?";
			jqGrid.addHqlFilter(hql, "%" + keyword + "%", "%" + keyword + "%");
		}
		return this.retrievePage(jqGrid);
	}

	/**
	 *  获取某项目下已备案完成的报审列表
	 * @param jqGrid 列表
	 * @param report 实体对象
	 * @param user 当前登录用户
	 * @param keyword 模糊查询关键字
	 * @return 合同及协议报审列表
	 */
	public List<ContractPactReport> getBeianPassRepGridSimp(JqGrid jqGrid, ContractPactReport report, UserProfile user, String keyword) {
		String prjId = report.getPrjId();
		String reportId = report.getReportId();
		int status = report.getStatus();
		String hql = "";
		if (status == ContractPactReport.StatusCodeTable.COMPLETE) {
			// 驳回页面列表需添加当前记录关联的报审单
			if (StringUtils.isNotBlank(reportId)) {
				// 某项目下备案完成的报审记录,且该报审为审批完成(生效)状态,并添加当前记录关联的报审单
				hql = "status = ? and reportId in (select reportId from " + ContractPactBeian.class.getName() + " where prjId = ? and status = ?) "
						+ "or reportId = ?";
				jqGrid.addHqlFilter(hql, status, prjId, ContractPactBeian.StatusCodeTable.COMPLETE, reportId);
			} else {
				// 某项目下备案完成的报审记录,且该报审为审批完成(生效)状态
				hql = "status = ? and reportId in (select reportId from " + ContractPactBeian.class.getName() + " where prjId = ? and status = ?)";
				jqGrid.addHqlFilter(hql, status, prjId, ContractPactBeian.StatusCodeTable.COMPLETE);
			}
		} else {
			// 某项目下备案完成的报审记录
			hql = "reportId in (select reportId from " + ContractPactBeian.class.getName() + " where prjId = ? and status = ?)";
			jqGrid.addHqlFilter(hql, prjId, ContractPactBeian.StatusCodeTable.COMPLETE);
		}

		// 模糊查询
		if (StringUtils.isNotBlank(keyword)) {
			hql = "reportNo like ?  or reportName like ?";
			jqGrid.addHqlFilter(hql, "%" + keyword + "%", "%" + keyword + "%");
		}
		return this.retrievePage(jqGrid);
	}

	// /**
	// * 获取所有通过/有条件通过的报审
	// * @return 实体对象集合
	// */
	// public List<ContractPactReport> getPassReport() {
	// String hql = "from " + ContractPactReport.class.getName() + " where ? or
	// drawingNo = ? ";
	// return null;
	// }

	/**
	 *  获取最大报审编号
	 * @return 报审编号
	 */
	public List<ContractPactReport> generateReportNo() {
		String hql = "from " + ContractPactReport.class.getName()
				+ " where reportNo is not null order by reportNo desc";
		List<ContractPactReport> list = this.find(hql);
		return list;
	}

	/**
	 * 获取某项目下所有审批完成的合同及协议报审
	 * @param prjId 项目id
	 * @return 报审对象集合
	 */
	public List<ContractPactReport> getAllCompleteReport(String prjId) {
		String hql = "from " + ContractPactReport.class.getName() + " where status = ? and prjId = ?";
		List<ContractPactReport> list = this.find(hql, ContractPactReport.StatusCodeTable.COMPLETE, prjId);
		return list;
	}

	/**
	 * 获取某项目下已备案完成的报审对象，且该报审处于生效(审批完成)状态
	 * @param prjId 项目信息栏中选中项目的id
	 * @return 报审对象集合
	 */
	public List<ContractPactReport> getBeianPassReports(String prjId) {
		String hql = "from " + ContractPactReport.class.getName() + " where reportId in " + "(select reportId from "
				+ ContractPactBeian.class.getName() + " where prjId = ? and status = ?)" + "and status = ?";
		List<ContractPactReport> list = this.find(hql, prjId, ContractPactBeian.StatusCodeTable.COMPLETE,
				ContractPactReport.StatusCodeTable.COMPLETE);
		return list;
	}

}
