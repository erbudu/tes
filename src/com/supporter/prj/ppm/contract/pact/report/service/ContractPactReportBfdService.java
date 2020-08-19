package com.supporter.prj.ppm.contract.pact.report.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.ppm.contract.pact.report.dao.ContractPactReportBfdDao;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReportBfd;

@Service
@Transactional(TransManager.APP)
public class ContractPactReportBfdService {
	@Autowired
	ContractPactReportBfdDao bfdDao;
	@Autowired
	ContractPactReportBfdFService bfdFService;

	/**
	 * 获取某报审的资料清单
	 * @param reportId 报审表主键
	 * @return 资料清单集合
	 */
	public List<ContractPactReportBfd> getListByPactReportId(String reportId) {
		List<ContractPactReportBfd> bfdList = new ArrayList<ContractPactReportBfd>();
		bfdList = bfdDao.findBy("reportId", reportId);
		return bfdList;
	}

	/**
	 * 保存某报审的资料清单
	 * @param bfds 资料清单集合
	 */
	public void saveBdf(List<ContractPactReportBfd> bfds) {
		for (int i = 0; i < bfds.size(); i++) {
			ContractPactReportBfd bfd = bfds.get(i);
			bfdDao.save(bfd);
		}
	}

	/**
	 * 删除某报审的资料清单
	 * @param reportId 报审表主键
	 */
	public void delete(String reportId) {
		List<ContractPactReportBfd> bfdList = new ArrayList<ContractPactReportBfd>();
		bfdList = bfdDao.findBy("reportId", reportId);
		for (int i = 0; i < bfdList.size(); i++) {
			ContractPactReportBfd bfd = bfdList.get(i);
			bfdDao.delete(bfd);
		}
	}

}
