package com.supporter.prj.ppm.prebid.review_ver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerBf;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfd;
import com.supporter.prj.ppm.prebid.review_ver.dao.PpmPrebidReviewVerBfdDao;
import com.supporter.prj.ppm.prebid.review_ver.dao.PpmPrebidReviewVerBfdFDao;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVer;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVerBfd;
import com.supporter.prj.ppm.prebid.review_ver.entity.PpmPrebidReviewVerBfdF;
import com.supporter.util.UUIDHex;

@Service
@Transactional(TransManager.APP)
public class PpmPrebidReviewVerFService {

	@Autowired
	private PpmPrebidReviewVerBfdFDao bfdFileDao;
	@Autowired
	private PpmPrebidReviewVerBfdDao prebidReviewVerBfdDao;
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
			List<FileUpload> files = fileUploadService.getList("CONPACTREVER", "reviewVerFile", bfd.getBfdId(), bfd.getRevVerId());
			for (int j = 0; j < files.size(); j++) {
				FileUpload file = files.get(j);
				PpmPrebidReviewVerBfdF bfdFile = new PpmPrebidReviewVerBfdF();
				bfdFile.setRecordId(com.supporter.util.UUIDHex.newId());
				bfdFile.setBfdId(bfd.getBfdId());
				bfdFile.setPrbidReportId(bfd.getRevVerId());
				bfdFile.setFuFileId(file.getFileId());
				bfdFile.setDisplayOrder(file.getDisplayOrder());
				bfdFile.setIsUseSeal(0); // 是否需要用印
				bfdFileDao.save(bfdFile);
			}
		}
	}

	/**
	 * 保存单个资料清单文件记录
	 * @param bfdFile 资料清单文件
	 */
	public void save(PpmPrebidReviewVerBfdF bfdFile) {
		bfdFileDao.save(bfdFile);
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
	public void delBdfFileByRevId(String reviewVerId) {
		if (StringUtils.isNotBlank(reviewVerId)) {
			bfdFileDao.deleteByProperty("reviewVerId", reviewVerId);
		}
	}
	
	public List<PpmPrebidReviewVerBfdF> getPpmPrebidReviewVerBfdFByPrbidReportId(String reviewVerId) {
		if(reviewVerId == "") { return null;}
		List<PpmPrebidReviewVerBfdF> entity = bfdFileDao.findBy("prbidReportId", reviewVerId);
		return entity;
	}
	
	public void updateWhole(PpmPrebidReviewVerBfdF entity) {
		bfdFileDao.saveOrUpdate(entity);
	}

	/**
	 * <pre>标记用印文件</pre>
	 * @param bfdId 资料清单主键
	 */
	public void markUsePrintFile(String bfdId) {
		List<PpmPrebidReviewVerBfdF> list = bfdFileDao.findBy("bfdId", bfdId);
		for(int i=0;i<list.size();i++) {
			PpmPrebidReviewVerBfdF PpmPrebidReviewVerBfdF = list.get(i);
			PpmPrebidReviewVerBfdF.setIsUseSeal(1);
			bfdFileDao.update(PpmPrebidReviewVerBfdF);
		}
	}
	/**
	 * <p>用印文件列表</p>
	 * @param prbidReportId 业务主键
	 * @return 
	 */
	public List<Map<String, String>> getUseSealGrid(String reviewVerId) {
		if(reviewVerId == null || reviewVerId == "") {
			return null;
		}
		List<Map<String,String>> li = new ArrayList<Map<String,String>>();
		String hql = "from "+PpmPrebidReviewVerBfdF.class.getName()+" where prbidReportId = ? and isUseSeal = ?";
		List<PpmPrebidReviewVerBfdF> list = bfdFileDao.find(hql,reviewVerId,1);
		for(int i = 0;i<list.size();i++) {
			Map<String,String> map = new HashMap<String, String>();
			PpmPrebidReviewVerBfd prebidReviewVerBfd = this.get(list.get(i).getBfdId());
			map.put("sealFileId", UUIDHex.newId());//用印文件主键
			
			map.put("fileUpBusinessId", prebidReviewVerBfd.getBfdId());//附件上传用的一级分类id
			map.put("bfdTypeName", prebidReviewVerBfd.getBfdTypeName());//文件类型
			map.put("fuFileName", list.get(i).getFuFileName());//文件名称
			map.put("fuFileId", list.get(i).getFuFileId());//文件id
			map.put("moduleId", PpmPrebidReviewVer.MODULE_ID);//应用编号
			map.put("busiType", PpmPrebidReviewVer.BUSI_TYPE);//附件上传业务一级分类
			li.add(map);
		}
		return li;
	}
	/**
	 * <pre>初始化确认用印文件</pre>
	 * @return
	 */
	public List<PpmPrebidReviewVerBfd> initPrintFile(String reviewVerId) {
		List<PpmPrebidReviewVerBfd> list = prebidReviewVerBfdDao.findBy("prbidReportId", reviewVerId);
		return list;
	}

	/**
	 * <pre>标记用印文件</pre>
	 * <pre>逻辑：标记前先将是否用印文件设为否，再将需要用印的设为是</pre>
	 * @param bfdIds 资料清单主键
	 */
	public void markUsePrintFile(String bfdIds, String reviewVerId) {
		if (bfdIds == null || bfdIds == "") {
		} ;
		List<PpmPrebidReviewVerBfdF> entityList = this
				.getPpmPrebidReviewVerBfdFByPrbidReportId(reviewVerId);
		for (PpmPrebidReviewVerBfdF PpmPrebidReviewVerBfdF : entityList) {
			PpmPrebidReviewVerBfdF.setIsUseSeal(0);
			this.updateWhole(PpmPrebidReviewVerBfdF);
		}
		String[] bfdIdArr = bfdIds.split(",");
		for (int i = 0; i < bfdIdArr.length; i++) {
			String hql = "from " + PpmPrebidReviewVerBfd.class.getName() + " where bfdTypeId = ? and reviewVerId = ?";
			PpmPrebidReviewVerBfd ppmPrebidReviewVerBfd = prebidReviewVerBfdDao.findUniqueResult(hql, bfdIdArr[i],reviewVerId);
			ppmPrebidReviewVerBfd.setIsUseSeal(1);
			prebidReviewVerBfdDao.update(ppmPrebidReviewVerBfd);
			this.markUsePrintFile(ppmPrebidReviewVerBfd.getBfdId());
		}
	}
	/**
	 * <p>获取用印业务</p>
	 * @return
	 */
	public String getUseSealBusiness() {
		return PpmPrebidReviewVer.USE_SEAL_BUSINESS;
	}
	
	/**
	 * <pre>功能:根据启动申报主键和码表中资料清单的ID获取资料清单数据</pre>
	 * <pre>描述:根据启动申报主键和码表资料清单项的id可以确定到对应的而且唯一的，已经保存过的资料清单项</pre>
	 * <pre>调用:1.启动申报编辑页面初始化数据</pre>
	 * @param prbidReportId 资料清单业务表单实体类主键
	 * @param itemId 资料清单id
	 * @return 资料清单业务表单实体类数据
	 */
	public List<PpmPrebidReportBfd> getDataListEdit(String prbidReportId, String bfdTypeId) {
		if(prbidReportId == null || prbidReportId == "" && bfdTypeId == null || bfdTypeId == "") {
			return null;
		}
		String hql = "from "+PpmPrebidReportBfd.class.getName()+" where prbidReportId = ? and bfdTypeId = ?";
		List<PpmPrebidReportBfd> list = prebidReviewVerBfdDao.find(hql,prbidReportId,bfdTypeId);
		return list;
	}

	public PpmPrebidReviewVerBfd get(String bfdId) {
		return prebidReviewVerBfdDao.get(bfdId);
	}


	/**
	 * 获取用印后的文件 更新文件表，用于用印
	 * @param promaryKey
	 * @param fuSealFileId
	 * @param fuSealFileName
	 * @return
	 */
	public String backtUseSealFile(String promaryKey, String fuSealFileId,
			String fuSealFileName) {
		if(promaryKey == null || promaryKey == "") {
			return "fail";
		}
		PpmPrebidReviewVerBfdF bfdF = bfdFileDao.get(promaryKey);
		bfdF.setFuFileId(fuSealFileId);
		bfdFileDao.update(bfdF);
		return "success";
	}
}

