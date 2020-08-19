package com.supporter.prj.pm.contract_balance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConst;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConstSwf;
import com.supporter.util.CommonUtil;

/**
 * 查看业务单
 * @author Administrator
 *
 */
@Service
public class ContractBalanceViewerProvider implements IBusiEntityViewerProvider {

	@Autowired
	private ContractBalanceConstService service;
	
	/**
	 * 返回本实例所服务的业务实体类.
	 * @return String
	 */
	public String getId() {
		return ContractBalanceConstSwf.class.getName();
	}
	
	/**
	 * 获取业务单URL
	 * @param budgetYear 年度
	 * @param entityId 业务ID
	 * @return String
	 */
	public String getViewerUrl(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		String balanceId = CommonUtil.trim(entityId.toString());
		ContractBalanceConst requirement = service.get(balanceId);
		if (requirement != null) {
			return "/pm/contract_balance/construction_balance_view.html?balanceId=" + entityId;
		}
		return null;
	}
}
