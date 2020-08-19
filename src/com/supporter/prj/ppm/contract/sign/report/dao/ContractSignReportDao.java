package com.supporter.prj.ppm.contract.sign.report.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.ppm.contract.sign.report.entity.ContractSignReport;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;

/**
 * @Title: Entity
 * @Description: 主合同签约报审表.
 * @author YAN
 * @date 2019-09-05 17:09:55
 * @version V1.0
 */
@Repository
public class ContractSignReportDao extends MainDaoSupport<ContractSignReport, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractSignReport> findPage(JqGrid jqGrid, ContractSignReport contractSignReport) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param contractSignId
	 * @param contractSignName
	 * @return
	 */
	public boolean nameIsValid(String contractSignId,String contractSignName) {
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(contractSignId)) {//新建时
			hql = "from " + ContractSignReport.class.getName() + " where contractSignName = ?";
			retList = this.retrieve(hql, contractSignName);
		} else {//编辑时
			hql = "from " + ContractSignReport.class.getName() + " where contractSignId != ? and contractSignName = ?";
			retList = this.retrieve(hql, contractSignId, contractSignName);
		}
		if(CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}

}

