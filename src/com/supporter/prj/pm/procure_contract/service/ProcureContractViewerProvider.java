package com.supporter.prj.pm.procure_contract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.pm.procure_contract.entity.ProcureContract;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractSwf;
import com.supporter.util.CommonUtil;

/**
 * 实体查看注册.
 */
@Service
public class ProcureContractViewerProvider implements IBusiEntityViewerProvider {

	@Autowired
	private ProcureContractService service;

	/**
	 * 获取实体类名
	 * 
	 * @return String
	 */
	public String getId() {
		return ProcureContractSwf.class.getName();
	}

	/**
	 * 获取实体查看URL
	 * 
	 * @param budgetYear
	 *            所在年库
	 * @param entityId
	 *            实体ID
	 * @return String
	 */
	public String getViewerUrl(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		String contractId = CommonUtil.trim(entityId.toString());
		ProcureContract entity = service.get(contractId);
		if (entity != null) {
			return "/pm/procure_contract/procureContract_view.html?contractId=" + entityId;
		}
		return null;
	}

}
