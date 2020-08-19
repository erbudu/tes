package com.supporter.prj.ppm.contract.pact.review.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.ppm.contract.pact.review.constant.ContractPactReviewConstant;
import com.supporter.prj.ppm.contract.pact.review.dao.ContractPactReviewBfdFDao;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReview;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewBfd;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewBfdF;

@Service
@Transactional(TransManager.APP)
public class ContractPactReviewBfdFService {
	@Autowired
	ContractPactReviewBfdFDao bfdFileDao;
	@Autowired
	FileUploadService fileUploadService;

	/**
	 * 保存评审资料清单文件
	 * @param bfds 评审资料清单
	 */
	public void saveBfdFile(List<ContractPactReviewBfd> bfds) {
		for (int i = 0; i < bfds.size(); i++) {
			ContractPactReviewBfd bfd = bfds.get(i);
			// 先删除某资料清单对应的所有资料清单文件，然后重新保存
			delBfdFileByBfdId(bfd.getReviewBfdId());
			List<FileUpload> files = fileUploadService.getList(ContractPactReview.MODULE_ID, ContractPactReview.BUSI_TYPE, 
				bfd.getReviewId(), bfd.getFileTypeId());
			for (int j = 0; j < files.size(); j++) {
				FileUpload file = files.get(j);
				ContractPactReviewBfdF bfdFile = new ContractPactReviewBfdF();
				bfdFile.setRecordId(com.supporter.util.UUIDHex.newId());
				bfdFile.setBfdId(bfd.getReviewBfdId());
				bfdFile.setReviewId(bfd.getReviewId());
				bfdFile.setFuFileId(file.getFileId());
				bfdFile.setDisplayOrder(file.getDisplayOrder());
				bfdFile.setIsUseSeal(ContractPactReviewConstant.ISUSESEAL_N);
				bfdFileDao.save(bfdFile);
			}
		}
	}

	/**
	 * 删除资料清单对应的文件
	 * @param bfdId 资料清单主键
	 */
	public void delBfdFileByBfdId(String bfdId) {
		if (StringUtils.isNotBlank(bfdId)) {
			bfdFileDao.deleteByProperty("bfdId", bfdId);
		}
	}

	/**
	 * 删除合同下的所有资料清单文件
	 * @param reviewId 主表主键
	 */
	public void delBdfFileByReviewId(String reviewId) {
		if (StringUtils.isNotBlank(reviewId)) {
			bfdFileDao.deleteByProperty("reviewId", reviewId);
		}
	}

	/**
	 * 获取资料清单下的所有资料清单文件
	 * @param bfdId 资料清单主键
	 * @return 资料清单文件集合
	 */
	public List<ContractPactReviewBfdF> getFileByBfdId(String bfdId) {
		if (StringUtils.isNotBlank(bfdId)) {
			return bfdFileDao.findBy("bfdId", bfdId);
		}
		return null;
	}

	/**
	 * 获取待用印资料清单文件
	 * @param reviewId-主表主键
	 * @return 资料清单文件对象
	 */
	public List<ContractPactReviewBfdF> getUseSealFile(String reviewId) {
		List<ContractPactReviewBfdF> list = bfdFileDao.getUseSealGrid(reviewId);
		return list;
	}

	/**
	 * 保存单个资料清单文件记录
	 * @param bfdFile-资料清单文件
	 */
	public void save(ContractPactReviewBfdF bfdFile) {
		bfdFileDao.save(bfdFile);
	}

	/**
	 * 更新单个资料清单文件记录
	 * @param bfdFile-资料清单文件
	 */
	public void update(ContractPactReviewBfdF bfdFile) {
		bfdFileDao.update(bfdFile);
	}

}
