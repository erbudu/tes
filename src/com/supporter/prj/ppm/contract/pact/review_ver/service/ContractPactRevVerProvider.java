package com.supporter.prj.ppm.contract.pact.review_ver.service;

import org.springframework.stereotype.Service;

import com.supporter.prj.eip.busi_entity.service.IBusiEntityViewerProvider;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVer;

/**
 * 示例性质的IBusiEntityViewerProvider实现类.
 * @author Arnold
 *
 */
@Service
public class ContractPactRevVerProvider implements IBusiEntityViewerProvider {
	
	/**
	 * 返回本实例所服务的业务实体类.
	 */
	public String getId() {
		return ContractPactRevVer.class.getName();
	}

	public String getViewerUrl(int budgetYear, Object entityId) {
		return "/ppm/index.html?docId=" + entityId;
	}
	   
}
