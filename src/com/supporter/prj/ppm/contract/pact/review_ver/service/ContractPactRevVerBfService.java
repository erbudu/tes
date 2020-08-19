package com.supporter.prj.ppm.contract.pact.review_ver.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.ppm.contract.pact.review_ver.dao.ContractPactRevVerBfDao;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerBf;

@Service
@Transactional(TransManager.APP)
public class ContractPactRevVerBfService {

	@Autowired
	private ContractPactRevVerBfDao bfdDao;

	/**
	 * 获取某评审验证的资料清单
	 * @param revVerId 评审验证主键
	 * @return 资料清单集合
	 */
	public List<ContractPactRevVerBf> getListByRevVerId(String revVerId) {
		List<ContractPactRevVerBf> bfdList = new ArrayList<ContractPactRevVerBf>();
		bfdList = bfdDao.findBy("revVerId", revVerId);
		return bfdList;
	}

	/**
	 * 保存某评审验证的资料清单
	 * @param bfds 资料清单集合
	 */
	public void saveBdf(List<ContractPactRevVerBf> bfds) {
		for (int i = 0; i < bfds.size(); i++) {
			ContractPactRevVerBf bfd = bfds.get(i);
			bfdDao.save(bfd);
		}
	}

	/**
	 * 删除某评审验证的资料清单
	 * @param revVerId 评审验证主键
	 */
	public void delete(String revVerId) {
		bfdDao.deleteByProperty("revVerId", revVerId);
	}

	/**
	 * 根据资料清单主键获取资料清单
	 * @param bfdId-资料清单主键
	 * @return
	 */
	public ContractPactRevVerBf get(String bfdId) {
		if (StringUtils.isNotBlank(bfdId)) {
			ContractPactRevVerBf entity = bfdDao.get(bfdId);
			return entity;
		}
		return null;
	}

}

