package com.supporter.prj.ppm.contract.pact.review_ver.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.ppm.contract.pact.review_ver.constant.ContractPactRevVerConstant;
import com.supporter.prj.ppm.contract.pact.review_ver.dao.ContractPactRevVerFDao;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVer;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerBf;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerF;

@Service
@Transactional(TransManager.APP)
public class ContractPactRevVerFService {

	@Autowired
	private ContractPactRevVerFDao bfdFileDao;
	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * 保存某评审验证的资料清单文件
	 * @param bfds
	 */
	public void saveBfdFile(List<ContractPactRevVerBf> bfds) {
		for (int i = 0; i < bfds.size(); i++) {
			ContractPactRevVerBf bfd = bfds.get(i);
			// 先删除某资料清单对应的所有资料清单文件，然后重新保存
			delBfdFileByBfdId(bfd.getBfdId());
			List<FileUpload> files = fileUploadService.getList(ContractPactRevVer.MODULE_ID, ContractPactRevVer.BUSI_TYPE, bfd.getRevVerId(),
					bfd.getFileTypeId());
			for (int j = 0; j < files.size(); j++) {
				FileUpload file = files.get(j);
				ContractPactRevVerF bfdFile = new ContractPactRevVerF();
				bfdFile.setRecordId(com.supporter.util.UUIDHex.newId());
				bfdFile.setBfdId(bfd.getBfdId());
				bfdFile.setRevVerId(bfd.getRevVerId());
				bfdFile.setFuFileId(file.getFileId());
				bfdFile.setDisplayOrder(file.getDisplayOrder());
				bfdFile.setIsUseSeal(ContractPactRevVerConstant.ISUSESEAL_N);
				bfdFileDao.save(bfdFile);
			}
		}
	}

	/**
	 * 保存单个资料清单文件记录
	 * @param bfdFile 资料清单文件
	 */
	public void save(ContractPactRevVerF bfdFile) {
		bfdFileDao.save(bfdFile);
	}

	/**
	 * 更新单个资料清单文件记录
	 * @param bfdFile-资料清单文件
	 */
	public void update(ContractPactRevVerF bfdFile) {
		bfdFileDao.update(bfdFile);
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
	 * 删除某评审验证下的所有资料清单文件
	 */
	public void delBdfFileByRevId(String revVerId) {
		if (StringUtils.isNotBlank(revVerId)) {
			bfdFileDao.deleteByProperty("revVerId", revVerId);
		}
	}

	/**
	 * 获取资料清单下的所有资料清单文件
	 */
	public List<ContractPactRevVerF> getFileByBfdId(String bfdId) {
		if (StringUtils.isNotBlank(bfdId)) {
			return bfdFileDao.findBy("bfdId", bfdId);
		}
		return null;
	}

	/**
	 * 获取待用印资料清单文件
	 * @param revVerId-主表主键
	 * @return 资料清单
	 */
	public List<ContractPactRevVerF> getUseSealFile(String revVerId) {
		List<ContractPactRevVerF> list = bfdFileDao.getUseSealGrid(revVerId);
		return list;
	}


}

