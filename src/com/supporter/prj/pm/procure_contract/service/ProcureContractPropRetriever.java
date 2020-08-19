package com.supporter.prj.pm.procure_contract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pm_prj.dao.VPmProjectDao;
import com.supporter.prj.cneec.pc.pm_prj.entity.VPmProject;
import com.supporter.prj.eip.busi_entity.service.AbstractPropRetriever;
import com.supporter.prj.pm.procure_contract.dao.ProcureContractSwfDao;
import com.supporter.prj.pm.procure_contract.entity.ProcureContract;
import com.supporter.prj.pm.procure_contract.entity.ProcureContractSwf;
import com.supporter.util.CommonUtil;

/**
 * @Title: 流程涉及实体注册
 * @Description: 流程定义变量必备类
 * @author: liyinfeng
 * @date: 2018-6-14
 * @version: V1.0
 */
@Service
public class ProcureContractPropRetriever extends AbstractPropRetriever {

	@Autowired
	private ProcureContractService service;
	@Autowired
	private ProcureContractSwfDao swfDao;
	@Autowired
	private VPmProjectDao prjDao;
	
	public String getId() {
		return ProcureContractSwf.class.getName();
	}

	/**
	 * 获取实体类.
	 * @param budgetYear 年度
	 * @param entityId 实体ID
	 * @return Object
	 */
	protected Object getEntity(int budgetYear, Object entityId) {
		if (entityId == null) {
			return null;
		}
		String id = CommonUtil.trim(entityId.toString());
		ProcureContractSwf swf = swfDao.get(id);
		ProcureContract entity = service.get(id);
		swf.setProcureContract(entity);
		
		VPmProject prj = prjDao.get(swf.getPrjId());
		if (prj != null) {
			swf.setPrjManagerId(prj.getPrjManagerId());
			swf.setPrjManagerName(prj.getPrjManager());
		}
		return swf;
	}

}