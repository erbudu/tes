package com.supporter.prj.cneec.tpc.invoice_no_contract.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.invoice_no_contract.entity.InvoiceNoContractEntity;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * 非合同付款收发票Dao
 * @Title:InvoiceNoContractDao
 * @author CHENHAO
 *
 */

@Repository
public class InvoiceNoContractDao extends MainDaoSupport<InvoiceNoContractEntity, String>{

	/**
	 * 分页查询非合同付款收发票列表
	 * @param jqGrid
	 * @param queryStr
	 * @param prjId 
	 * @return
	 */
	public List<InvoiceNoContractEntity> findPage(JqGrid jqGrid, UserProfile user, InvoiceNoContractEntity entity) {
		
		jqGrid.addHqlFilter("prjId = ?", entity.getPrjId());
		if(StringUtils.isNotBlank(entity.getInvoiceNo())) {
			jqGrid.addHqlFilter("invoiceNo like ?", "%"+entity.getInvoiceNo()+"%");
		}
		if(StringUtils.isNotBlank(entity.getInvoiceTypeCode())) {
			jqGrid.addHqlFilter("invoiceTypeCode = ?", entity.getInvoiceTypeCode());
		}
		if(entity.getStatus() != null) {
			jqGrid.addHqlFilter("status = ?", entity.getStatus());
		}
//		String authHql = EIPService.getAuthorityService().getHqlFilter(user,InvoiceNoContractConstant.MODULE_NAME,InvoiceNoContractConstant.MODULE_AUTH);
//		jqGrid.addHqlFilter(authHql);
		return retrievePage(jqGrid);
	}

}
