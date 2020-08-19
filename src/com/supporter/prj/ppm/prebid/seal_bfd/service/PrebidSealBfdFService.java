package com.supporter.prj.ppm.prebid.seal_bfd.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.ppm.prebid.seal_bfd.dao.PrebidSealBfdDao;
import com.supporter.prj.ppm.prebid.seal_bfd.dao.PrebidSealBfdFDao;
import com.supporter.prj.ppm.prebid.seal_bfd.entity.PpmPrebidSealBfd;
import com.supporter.prj.ppm.prebid.seal_bfd.entity.PpmPrebidSealBfdF;

@Service
@Transactional(TransManager.APP)
public class PrebidSealBfdFService {

	@Autowired
	private PrebidSealBfdDao bfdDao;
	@Autowired
	private PrebidSealBfdFDao bfdFileDao;
	@Autowired
	private FileUploadService fileUploadService;
	
	
	
	/**
	 * 获取某评审验证的资料清单
	 * @param revVerId 评审验证主键
	 * @return 资料清单集合
	 */
	public List<PpmPrebidSealBfd> getListByPublishId(String prbidReportId) {
		List<PpmPrebidSealBfd> bfdList = new ArrayList<PpmPrebidSealBfd>();
		bfdList = bfdDao.findBy("prbidReportId", prbidReportId);
		return bfdList;
	}
	/**
	 * 保存某协议出版的资料清单文件
	 * @param bfds
	 */
	public void saveBfdFile(List<PpmPrebidSealBfd> bfds) {
		for (int i = 0; i < bfds.size(); i++) {
			PpmPrebidSealBfd bfd = bfds.get(i);
			// 先删除某资料清单对应的所有资料清单文件，然后重新保存
			delBfdFileRecord(bfd.getBfdId());
			List<FileUpload> files = fileUploadService.getList("SEALBFD", "STAMPEDAUTHORIZATION", bfd.getBfdId(), bfd.getPrbidReportId());
			for (int j = 0; j < files.size(); j++) {
				FileUpload file = files.get(j);
				PpmPrebidSealBfdF bfdFile = new PpmPrebidSealBfdF();
				bfdFile.setRecordId(com.supporter.util.UUIDHex.newId());
				bfdFile.setBfdId(bfd.getBfdId());
				bfdFile.setPrbidReportId(bfd.getPrbidReportId());
				bfdFile.setFuSealFileId(file.getFileId());
				bfdFile.setDisplayOrder(file.getDisplayOrder());
				bfdFile.setFuFileName(file.getFileName());
				bfdFileDao.save(bfdFile);
			}
		}
	}

	/**
	 * 保存单个资料清单文件记录
	 * @param bfdFile 资料清单文件
	 */
	public void save(PpmPrebidSealBfdF bfdFile) {
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
			List<PpmPrebidSealBfdF> bfdFiles = bfdFileDao.findBy("bfdId", bfdId);
			if (CollectionUtils.isNotEmpty(bfdFiles)) {
				for (int i = 0; i < bfdFiles.size(); i++) {
					PpmPrebidSealBfdF bfdFile = bfdFiles.get(i);
					String fileId = bfdFile.getFuSealFileId();
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

