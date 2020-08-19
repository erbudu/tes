package com.supporter.prj.ppm.contract.pact.beian.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.ppm.contract.pact.beian.dao.ContractPactBeianBfdFDao;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeian;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeianBfd;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeianBfdF;
import com.supporter.prj.ppm.contract.pact.seal_bfd.constant.ContractPactPublishConstant;

@Service
@Transactional(TransManager.APP)
public class ContractPactBeianBfdFService {

	@Autowired
	private ContractPactBeianBfdFDao bfdFileDao;
	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * 保存某协议备案的资料清单文件
	 * @param bfds
	 */
	public void saveBfdFile(List<ContractPactBeianBfd> bfds) {
		for (int i = 0; i < bfds.size(); i++) {
			ContractPactBeianBfd bfd = bfds.get(i);
			// 先删除某资料清单对应的所有资料清单文件，然后重新保存
			delBfdFileByBfdId(bfd.getBfdId());
			List<FileUpload> files = fileUploadService.getList(ContractPactBeian.MODULE_ID, ContractPactBeian.BUSI_TYPE, bfd.getRecordId(),
					bfd.getFileTypeId());
			for (int j = 0; j < files.size(); j++) {
				FileUpload file = files.get(j);
				ContractPactBeianBfdF bfdFile = new ContractPactBeianBfdF();
				bfdFile.setId(com.supporter.util.UUIDHex.newId());
				bfdFile.setBfdId(bfd.getBfdId());
				bfdFile.setRecordId(bfd.getRecordId());
				bfdFile.setFuFileId(file.getFileId());
				bfdFile.setDisplayOrder(file.getDisplayOrder());
				bfdFile.setIsUseSeal(ContractPactPublishConstant.ISUSESEAL_N);
				bfdFileDao.save(bfdFile);
			}
		}
	}

	/**
	 * 删除资料清单对应的文件
	 */
	public void delBfdFileByBfdId(String bfdId) {
		if (StringUtils.isNotBlank(bfdId)) {
			bfdFileDao.deleteByProperty("bfdId", bfdId);
		}
	}

	/**
	 * 删除某协议备案下的所有资料清单文件
	 */
	public void delBdfFileByRevId(String recordId) {
		if (StringUtils.isNotBlank(recordId)) {
			bfdFileDao.deleteByProperty("recordId", recordId);
		}
	}

}

