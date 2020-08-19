package com.supporter.prj.pm.contract_balance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pm_prj.dao.VPmProjectDao;
import com.supporter.prj.cneec.pc.pm_prj.entity.VPmProject;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.pm.contract_balance.dao.ContractBalanceConstSwfDao;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConst;
import com.supporter.prj.pm.contract_balance.entity.ContractBalanceConstSwf;
import com.supporter.util.CommonUtil;

/**
 * 结算
 * @author Administrator
 *
 */
@Service
public class ContractBalancePropRetriever extends AbstractPropRetriever {
	/**
	 * 注入需要使用的Service.
	 */
	@Autowired
	private ContractBalanceConstService service;
	@Autowired
	private ContractBalanceConstSwfDao balanceSwfDao;
	@Autowired
	private VPmProjectDao prjDao;

	/**
	 * 返回本实例所服务的业务实体类.
	 */
	@Override
	public String getId() {
		return ContractBalanceConstSwf.class.getName();
	}

	@Override
	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		String balanceId = CommonUtil.trim(entityId.toString());
		ContractBalanceConstSwf swf = balanceSwfDao.get(balanceId);
		ContractBalanceConst entity = service.get(balanceId);
		swf.setBalance(entity);
		
		VPmProject prj = prjDao.get(swf.getPrjId());
		if (prj != null) {
			swf.setPrjManagerId(prj.getPrjManagerId());
			swf.setPrjManagerName(prj.getPrjManager());
			swf.setPrjDeptId(prj.getDeptId());
		}
		
		return swf;
	}
	   
}
