package com.supporter.prj.ppm.contract.pact.review.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.ppm.contract.pact.review.dao.ContractPactReviewBfdDao;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewBfd;

@Service
@Transactional(TransManager.APP)
public class ContractPactReviewBfdService {
	@Autowired
	ContractPactReviewBfdDao bfdDao;

	/**
	 * 获取某评审的资料清单
	 * @param reviewId 评审表主键
	 * @return 资料清单集合
	 */
	public List<ContractPactReviewBfd> getListByPactReviewId(String reviewId) {
		List<ContractPactReviewBfd> bfdList = new ArrayList<ContractPactReviewBfd>();
		bfdList = bfdDao.findBy("reviewId", reviewId);
		return bfdList;
	}

	/**
	 * 保存某评审的资料清单
	 * @param bfds 资料清单集合
	 */
	public void saveBdf(List<ContractPactReviewBfd> bfds) {
		for (int i = 0; i < bfds.size(); i++) {
			ContractPactReviewBfd bfd = bfds.get(i);
			bfdDao.save(bfd);
		}
	}

	/**
	 * 删除某评审的资料清单
	 * @param reviewId 评审表主键
	 */
	public void delete(String reviewId) {
		List<ContractPactReviewBfd> bfdList = new ArrayList<ContractPactReviewBfd>();
		bfdList = bfdDao.findBy("reviewId", reviewId);
		for (int i = 0; i < bfdList.size(); i++) {
			ContractPactReviewBfd bfd = bfdList.get(i);
			bfdDao.delete(bfd);
		}
	}

	/**
	 * 根据资料清单主键获取资料清单
	 * @param bfdId-资料清单主键
	 * @return
	 */
	public ContractPactReviewBfd get(String bfdId) {
		if (StringUtils.isNotBlank(bfdId)) {
			ContractPactReviewBfd entity = bfdDao.get(bfdId);
			return entity;
		}
		return null;
	}

}
