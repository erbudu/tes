package com.supporter.prj.ppm.contract.pact.review_ver.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerF;

@Repository
public class ContractPactRevVerFDao extends MainDaoSupport<ContractPactRevVerF, String> {

	/**
	 * 分页查询
	 * @param jqGrid
	 * @return
	 */
	public List<ContractPactRevVerF> findPage(JqGrid jqGrid, ContractPactRevVerF contractPactRevVerF) {
		return this.retrievePage(jqGrid);
	}

	/**
	 * 获取待用印资料清单文件列表
	 * @param revVerId-主表主键
	 * @return 资料清单
	 */
	public List<ContractPactRevVerF> getUseSealGrid(String revVerId) {
		String hql = "from " + ContractPactRevVerF.class.getName() + " where revVerId = ? and isUseSeal = ?";
		List<ContractPactRevVerF> list = this.find(hql, revVerId, 1);
		return list;
	}

	// /**
	// * 判断名字是否重复
	// *
	// * @param bfdId
	// * @param bfdName
	// * @return
	// */
	// public boolean nameIsValid(String bfdId,String bfdName) {
	// String hql = null;
	// List retList = null;
	// if(StringUtils.isBlank(bfdId)) {//新建时
	// hql = "from " + ContractPactRevVerF.class.getName() + " where bfdName = ?";
	// retList = this.retrieve(hql, bfdName);
	// } else {//编辑时
	// hql = "from " + ContractPactRevVerF.class.getName() + " where bfdId != ? and
	// bfdName = ?";
	// retList = this.retrieve(hql, bfdId, bfdName);
	// }
	// if(CollectionUtils.isEmpty(retList)) {
	// return true;
	// }
	// return false;
	// }

}

