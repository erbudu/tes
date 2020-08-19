package com.supporter.prj.linkworks.oa.util.file_upload.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.account.dao.AccountEntityDao;
import com.supporter.prj.eip.account.entity.AccountEntity;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.util.file_upload.dao.DianZiQianMingDao;
import com.supporter.prj.linkworks.oa.util.file_upload.entity.DianZiQianMingUtil;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Service
 * @Description: 报告
 * @author liyinfeng
 * @date 2017-7-05 10:25:07
 * @version V1.0   
 *
 */
@Service
@Transactional(TransManager.APP)
public class DianZiQianMingService {
	@Autowired
	private DianZiQianMingDao dao;
	@Autowired
	private ExtractFiles extractFiles;	
	@Autowired
	AccountEntityDao accountDao;
	
	public void updateAccount(String empId,String fileId){
		IEmployee emp = EIPService.getEmpService().getEmp(empId);
		AccountEntity account = (AccountEntity)EIPService.getAccountService().getDefaultAccount(emp);
		String electronicSignature = "service/eip/FileUpload/showImage?fileId=" + fileId;
		if(account!=null){
			account.setElectronicSignature(electronicSignature);
			accountDao.update(account);
		}		
	}

	public DianZiQianMingUtil get(String id){
		return dao.get(id);
	}
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <DianZiQianMingUtil> entitys= dao.getAllList();
		for(DianZiQianMingUtil entity:entitys){
			Long eId = entity.getSigntureId();
			String ext= CommonUtil.trim(entity.getFielType());
			String fileName = "";
			if(eId!=null){
				fileName=String.valueOf(eId)+"."+ext;
			}
			returnStr = extractFiles.extractDianZiQianMingFiles (String.valueOf(eId), fileName,
					"/oa/signature/", "EIPDEPT", "electronicSignature", userProfile);
			updateAccount(String.valueOf(entity.getEmpId()),returnStr);
		}
		return returnStr;
		//return reportDao.batchExtractFiles(userProfile);
	}
}
