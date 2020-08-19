package com.supporter.prj.ppm.contract.pact.beian.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeianBfdF;

@Repository
public class ContractPactBeianBfdFDao extends MainDaoSupport<ContractPactBeianBfdF, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractPactBeianBfdF> findPage(JqGrid jqGrid, ContractPactBeianBfdF contractPactRecordBfdF) {
		return this.retrievePage(jqGrid);
	}


}

