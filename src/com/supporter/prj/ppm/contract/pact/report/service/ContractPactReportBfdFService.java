package com.supporter.prj.ppm.contract.pact.report.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.ppm.contract.pact.report.constant.ContractPactReportConstant;
import com.supporter.prj.ppm.contract.pact.report.dao.ContractPactReportBfdFDao;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReport;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReportBfd;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReportBfdF;

@Service
@Transactional(TransManager.APP)
public class ContractPactReportBfdFService {
	@Autowired
	ContractPactReportBfdFDao bfdFiledao;
	@Autowired
	FileUploadService fileUploadService;

	/**
	 * 保存资料清单文件
	 * @param bfd 资料清单对象
	 */
	public void saveBfdFile(List<ContractPactReportBfd> bfds) {
		for (int i = 0; i < bfds.size(); i++) {
			ContractPactReportBfd bfd = bfds.get(i);
			// 先删除某资料清单对应的所有资料清单文件，然后重新保存
			delBfdFileByBfdId(bfd.getBfdId());
			List<FileUpload> files = fileUploadService.getList(ContractPactReport.MODULE_ID, ContractPactReport.BUSI_TYPE, bfd.getReportId(),
					bfd.getFileTypeId());
			for (int j = 0; j < files.size(); j++) {
				FileUpload file = files.get(j);
				ContractPactReportBfdF bfdFile = new ContractPactReportBfdF();
				bfdFile.setRecordId(com.supporter.util.UUIDHex.newId());
				bfdFile.setBfdId(bfd.getBfdId());
				bfdFile.setReportId(bfd.getReportId());
				bfdFile.setFuFileId(file.getFileId());
				bfdFile.setDisplayOrder(file.getDisplayOrder());
				bfdFile.setIsUseSeal(ContractPactReportConstant.ISUSESEAL_N); // 是否需要用印
				bfdFiledao.save(bfdFile);
			}
		}
	}

	/**
	 * 删除资料清单对应的文件
	 */
	public void delBfdFileByBfdId(String bfdId) {
		if (StringUtils.isNotBlank(bfdId)) {
			bfdFiledao.deleteByProperty("bfdId", bfdId);
		}
	}

	/**
	 * 删除合同下的所有资料清单文件
	 */
	public void delBdfFileByReportId(String reportId) {
		if (StringUtils.isNotBlank(reportId)) {
			bfdFiledao.deleteByProperty("reportId", reportId);
		}
	}
}
