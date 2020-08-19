package com.supporter.prj.cneec.tpc.prj_contract_settlement.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.prj_contract_settlement.entity.PrjContractSettlementProve;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;

@Repository
public class PrjContractSettlementProveDao extends MainDaoSupport < PrjContractSettlementProve, String >{

	/**
	 * 获得在某个新建采购合同中上传的付款证明附件
	 * @param impl
	 * @param uf
	 * @return
	 */
	public String getFileNamesList(String settlementId){
		String hql = "from " + PrjContractSettlementProve.class.getName()
		+ "' where settlementId = ?";
		List<PrjContractSettlementProve> list = this.find(hql, settlementId);
		StringBuffer fileNames = new StringBuffer();
		for(PrjContractSettlementProve examFile : list){
			fileNames.append(examFile.getFileName());
		}
		return fileNames.toString();
	}
	
}
