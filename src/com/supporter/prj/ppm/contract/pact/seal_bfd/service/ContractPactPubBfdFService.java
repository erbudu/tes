package com.supporter.prj.ppm.contract.pact.seal_bfd.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.ppm.contract.pact.seal_bfd.constant.ContractPactPublishConstant;
import com.supporter.prj.ppm.contract.pact.seal_bfd.dao.ContractPactPubBfdFDao;
import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.ContractPactPubBfdF;
import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.ContractPactPublish;
import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.ContractPactPublishBfd;

@Service
@Transactional(TransManager.APP)
public class ContractPactPubBfdFService {

	@Autowired
	private ContractPactPubBfdFDao bfdFileDao;
	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * 保存某协议出版的资料清单文件
	 * @param bfds
	 */
	public void saveBfdFile(List<ContractPactPublishBfd> bfds) {
		for (int i = 0; i < bfds.size(); i++) {
			ContractPactPublishBfd bfd = bfds.get(i);
			// 先删除某资料清单对应的所有资料清单文件，然后重新保存
			delBfdFileRecord(bfd.getBfdId());
			List<FileUpload> files = fileUploadService.getList(ContractPactPublish.MODULE_ID, ContractPactPublish.BUSI_TYPE, 
				bfd.getPublishId(), bfd.getFileTypeId());
			for (int j = 0; j < files.size(); j++) {
				FileUpload file = files.get(j);
				ContractPactPubBfdF bfdFile = new ContractPactPubBfdF();
				bfdFile.setRecordId(com.supporter.util.UUIDHex.newId());
				bfdFile.setBfdId(bfd.getBfdId());
				bfdFile.setPublishId(bfd.getPublishId());
				bfdFile.setFuFileId(file.getFileId());
				bfdFile.setDisplayOrder(file.getDisplayOrder());
				bfdFile.setIsUseSeal(ContractPactPublishConstant.ISUSESEAL_N);
				bfdFileDao.save(bfdFile);
			}
		}
	}

	/**
	 * 保存单个资料清单文件记录
	 * @param bfdFile 资料清单文件
	 */
	public void save(ContractPactPubBfdF bfdFile) {
		bfdFileDao.save(bfdFile);
	}

	/**
	 * 删除资料清单对应文件的表记录
	 */
	public void delBfdFileRecord(String bfdId) {
		bfdFileDao.deleteByProperty("bfdId", bfdId);
	}

	/**
	 * 删除资料清单对应的文件及其附件文件
	 */
	public void delBfdFileByBfdId(String bfdId) {
		if (StringUtils.isNotBlank(bfdId)) {
			// 删除资料清单文件的磁盘文件及附件表记录
			List<ContractPactPubBfdF> bfdFiles = bfdFileDao.findBy("bfdId", bfdId);
			if (CollectionUtils.isNotEmpty(bfdFiles)) {
				for (int i = 0; i < bfdFiles.size(); i++) {
					ContractPactPubBfdF bfdFile = bfdFiles.get(i);
					String fileId = bfdFile.getFuFileId();
					fileUploadService.deleteFile(fileId);
				}
			}
			// 删除资料清单文件表记录
			bfdFileDao.deleteByProperty("bfdId", bfdId);
		}
	}

	/**
	 * 删除某协议出版下的所有资料清单文件
	 */
	public void delBdfFileByRevId(String publishId) {
		if (StringUtils.isNotBlank(publishId)) {
			bfdFileDao.deleteByProperty("publishId", publishId);
		}
	}

}

