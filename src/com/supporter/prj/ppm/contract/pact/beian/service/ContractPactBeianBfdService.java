package com.supporter.prj.ppm.contract.pact.beian.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.ppm.contract.pact.beian.dao.ContractPactBeianBfdDao;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeianBfd;

@Service
@Transactional(TransManager.APP)
public class ContractPactBeianBfdService {

	@Autowired
	private ContractPactBeianBfdDao bfdDao;

	/**
	 * 获取某协议备案的资料清单
	 * @param revVerId 协议备案主键
	 * @return 资料清单集合
	 */
	public List<ContractPactBeianBfd> getListByrecordId(String recordId) {
		List<ContractPactBeianBfd> bfdList = new ArrayList<ContractPactBeianBfd>();
		bfdList = bfdDao.findBy("recordId", recordId);
		return bfdList;
	}

	/**
	 * 保存某协议备案的资料清单
	 * @param bfds 资料清单集合
	 */
	public void saveBdf(List<ContractPactBeianBfd> bfds) {
		for (int i = 0; i < bfds.size(); i++) {
			ContractPactBeianBfd bfd = bfds.get(i);
			bfdDao.save(bfd);
		}
	}

	/**
	 * 删除某协议备案的资料清单
	 * @param revVerId 协议备案主键
	 */
	public void delete(String recordId) {
		bfdDao.deleteByProperty("recordId", recordId);
	}

}

