package com.supporter.prj.ppm.contract.pact.beian.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeianBfd;

@Repository
public class ContractPactBeianBfdDao extends MainDaoSupport<ContractPactBeianBfd, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractPactBeianBfd> findPage(JqGrid jqGrid, ContractPactBeianBfd contractPactRecordBfd) {
		return this.retrievePage(jqGrid);
	}


}

