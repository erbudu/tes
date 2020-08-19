package com.supporter.prj.ppm.contract.pact.seal_bfd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.ppm.contract.pact.seal_bfd.dao.ContractPactPublishBfdDao;
import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.ContractPactPublishBfd;

@Service
@Transactional(TransManager.APP)
public class ContractPactPublishBfdService {

	@Autowired
	private ContractPactPublishBfdDao bfdDao;

	/**
	 * 获取某评审验证的资料清单
	 * @param revVerId 评审验证主键
	 * @return 资料清单集合
	 */
	public List<ContractPactPublishBfd> getListByPublishId(String publishId) {
		List<ContractPactPublishBfd> bfdList = new ArrayList<ContractPactPublishBfd>();
		bfdList = bfdDao.findBy("publishId", publishId);
		return bfdList;
	}

	/**
	 * 保存某评审验证的资料清单
	 * @param bfds 资料清单集合
	 */
	public void saveBdf(List<ContractPactPublishBfd> bfds) {
		for (int i = 0; i < bfds.size(); i++) {
			ContractPactPublishBfd bfd = bfds.get(i);
			bfdDao.save(bfd);
		}
	}

	/**
	 * 删除某评审验证的资料清单
	 * @param revVerId 评审验证主键
	 */
	public void delete(String publishId) {
		bfdDao.deleteByProperty("publishId", publishId);
	}


}

