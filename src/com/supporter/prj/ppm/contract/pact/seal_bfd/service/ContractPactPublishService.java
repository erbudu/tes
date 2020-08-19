package com.supporter.prj.ppm.contract.pact.seal_bfd.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.file_upload.dao.FileUploadDao;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReport;
import com.supporter.prj.ppm.contract.pact.report.service.ContractPactReportService;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReview;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewBfd;
import com.supporter.prj.ppm.contract.pact.review.service.ContractPactReviewBfdService;
import com.supporter.prj.ppm.contract.pact.review.service.ContractPactReviewService;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVer;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerBf;
import com.supporter.prj.ppm.contract.pact.review_ver.service.ContractPactRevVerBfService;
import com.supporter.prj.ppm.contract.pact.review_ver.service.ContractPactRevVerService;
import com.supporter.prj.ppm.contract.pact.seal_bfd.constant.ContractPactPublishConstant;
import com.supporter.prj.ppm.contract.pact.seal_bfd.dao.ContractPactPublishDao;
import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.ContractPactPubBfdF;
import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.ContractPactPublish;
import com.supporter.prj.ppm.contract.pact.seal_bfd.entity.ContractPactPublishBfd;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

@Service
@Transactional(TransManager.APP)
public class ContractPactPublishService {

	@Autowired
	private ContractPactPublishDao dao;
	@Autowired
	private ContractPactPublishBfdService bfdService;
	@Autowired
	private ContractPactPubBfdFService bfdFileService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private ContractPactRevVerService revVerService;
	@Autowired
	private ContractPactRevVerBfService revVerBfdService;
	@Autowired
	private ContractPactReviewService reviewService;
	@Autowired
	private ContractPactReviewBfdService reviewBfdService;
	@Autowired
	private FileUploadDao fileUploadDao;
	@Autowired
	private BaseInfoService baseInfoService;
	@Autowired
	private ContractPactReportService reportService;

	/**
	 * 分页表格展示数据.
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<ContractPactPublish> getGrid(UserProfile user, JqGrid jqGrid, ContractPactPublish ContractPactPublish) {
		return dao.findPage(jqGrid, ContractPactPublish);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * @param user
	 * @param publishId
	 * @param prjId 
	 * @return
	 */
	public ContractPactPublish initEditOrViewPage(String publishId, String prjId, UserProfile user) {
		ContractPactPublish entity;
		if (StringUtils.isBlank(publishId)) {// 新建
			entity = new ContractPactPublish();
			entity.setPublishId(com.supporter.util.UUIDHex.newId());
			entity.setIsNew(true);
			entity.setStatus(ContractPactPublish.StatusCodeTable.DRAFT);
			entity.setCreatorId(user.getPersonId());
			entity.setCreator(user.getName());
			entity.setCreationDate(new Date());
			// 获取左侧项目信息栏选中项目的信息
			Prj prj = baseInfoService.PrjInformation(prjId);
			entity.setPrjId(prj.getPrjId());
			entity.setPrjNo(prj.getPrjNo());
			entity.setPrjName(prj.getPrjCName());
			// 联系人默认当前登录人
			entity.setContactId(user.getPersonId());
			entity.setContactName(user.getName());
			// 根据码表初始化资料清单
			List<ContractPactPublishBfd> bfds = initBfdList(entity.getPublishId());
			entity.setBfds(bfds);
			return entity;
		} else {// 编辑
			entity = dao.get(publishId);
			entity.setIsNew(false);
			entity.setModifierId(user.getPersonId());
			entity.setModifier(user.getName());
			entity.setModificationDate(new Date());
			// 获取某协议出版对应的资料清单
			List<ContractPactPublishBfd> bfds = new ArrayList<ContractPactPublishBfd>();
			bfds = bfdService.getListByPublishId(publishId);
			entity.setBfds(bfds);
			return entity;
		}
	}

	/**
	 * 根据码表初始化资料清单
	 * @return 资料清单
	 */
	private List<ContractPactPublishBfd> initBfdList(String publishId) {
		List<ContractPactPublishBfd> bfds = new ArrayList<ContractPactPublishBfd>();
		List<IComCodeTableItem> items = EIPService.getComCodeTableService()
			.getCodeTableItems(ContractPactPublishConstant.BFD_CODETABLE_NAME);
		for (int i = 0; i < items.size(); i++) {
			// 只显示合同及协议的资料清单
			if ("协议及合同文本".equals(items.get(i).getItemValue())) {
				ContractPactPublishBfd bfd = new ContractPactPublishBfd();
				bfd.setBfdId(com.supporter.util.UUIDHex.newId());
				bfd.setPublishId(publishId);
				bfd.setFileTypeId(items.get(i).getItemId());
				bfd.setFileTypeName(items.get(i).getItemValue());
				bfds.add(bfd);
			}
		}
		return bfds;
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param ContractPactPublish 实体类
	 * @return
	 */
	public ContractPactPublish saveOrUpdate(UserProfile user, ContractPactPublish entity) {
		if (entity.getIsNew()) {// 新建
			// 保存评审验证
			dao.save(entity);
			// 保存评审验证资料清单
			bfdService.saveBdf(entity.getBfds());
		} else {// 编辑
			// 保存评审验证
			entity.setModifierId(user.getPersonId());
			entity.setModifier(user.getName());
			entity.setModificationDate(new Date());
			dao.update(entity);
		}
		// 保存评审验证资料清单文件
		bfdFileService.saveBfdFile(entity.getBfds());
		return entity;
	}

	/**
	 * 删除
	 * @param publishId 评审验证主键
	 */
	public void delete(String publishId) {
		// 删除评审验证
		dao.delete(publishId);
		// 删除资料清单
		bfdService.delete(publishId);
		// 删除资料清单文件
		bfdFileService.delBdfFileByRevId(publishId);
	}

	/**
	 * 根据主键获取实体对象
	 * @param id 模块主键
	 * @return 实体对象
	 */
	public ContractPactPublish get(String id) {
		return dao.get(id);
	}

	/**
	 * 流程处理类更新实体对象
	 * @param pactReview
	 * @return
	 */
	public ContractPactPublish update(ContractPactPublish pactPublish) {
		if (pactPublish != null) {
			dao.update(pactPublish);
		}
		return pactPublish;
	}

	/**
	 * 提交
	 * @param publishId 协议出版主键
	 * @return  操作结果
	 * @param user 当前登录用户
	 */
	public void valid(String publishId, UserProfile user) {
		ContractPactPublish entity = dao.get(publishId);
		if (entity != null) {
			entity.setSubmitterId(user.getPersonId());
			entity.setSubmitter(user.getName());
			entity.setSubmissionDate(new Date());
			dao.update(entity);
		}
	}

	/**
	 * 编号生成规则
	 * @return 协议出版编号
	 */
	public String generatePublicNo() {
		List<ContractPactPublish> list = dao.generatePublicNo();
		Date date = new Date();
		SimpleDateFormat regx = new SimpleDateFormat("yyyy");
		if (CollectionUtils.isEmpty(list)) {
			return "协出版字" + regx.format(date) + "年第000号";
		} else {
			String no = list.get(0).getPublishNo();
			int maxNo = Integer.parseInt(no.substring(no.lastIndexOf("第") + 1, no.lastIndexOf("第") + 4)) + 1;
			String maxNoStr = String.format("%03d", maxNo);
			return "协出版字" + regx.format(date) + "年第" + maxNoStr + "号";
		}
	}

	/**
	 * 根据报审id获取协议出版对象
	 * @param reportId 合同及协议报审主键
	 * @return  协议出版实体对象
	 */
	public ContractPactPublish getPubByReportId(String reportId) {
		List<ContractPactPublish> pubList = dao.findBy("reportId", reportId);
		if (CollectionUtils.isNotEmpty(pubList)) {
			ContractPactPublish publish = pubList.get(0);
			List<ContractPactPublishBfd> bfdList = bfdService.getListByPublishId(publish.getPublishId());
			publish.setBfds(bfdList);
			return publish;
		}
		return null;
	}

	/**
	 * 初始化协议出版资料清单文件
	 * (将同一资料清单类型的评审/评审验证资料清单文件复制给协议出版)
	 * @param reportId 报审id
	 * @param bfdId 协议出版资料清单id
	 * @param publishId 协议出版id
	 */
	public void initBfdFile(String reportId, String bfdsStr, String publishId, UserProfile user) {
		// 获取协议出版模块的协议及合同文本资料清单
		List<ContractPactPublishBfd> publishBfds = new ArrayList<ContractPactPublishBfd>();
		String[] bfdArr = bfdsStr.split("&");
		for (int i = 1; i < bfdArr.length; i += 4) {
			if (StringUtils.isNotBlank(bfdArr[i])) {
				ContractPactPublishBfd bfd = new ContractPactPublishBfd();
				bfd.setBfdId(bfdArr[i].substring(bfdArr[i].lastIndexOf("=") + 1));
				bfd.setPublishId(bfdArr[i + 1].substring(bfdArr[i + 1].lastIndexOf("=") + 1));
				bfd.setFileTypeId(bfdArr[i + 2].substring(bfdArr[i + 2].lastIndexOf("=") + 1));
				bfd.setFileTypeName(bfdArr[i + 3].substring(bfdArr[i + 3].lastIndexOf("=") + 1));
				if ("协议及合同文本".equals(bfd.getFileTypeName())) {
					// 删除资料清单以前的资料清单文件
					bfdFileService.delBfdFileByBfdId(bfdArr[i].substring(bfdArr[i].lastIndexOf("=") + 1));
					publishBfds.add(bfd);
				}
			}
		}
		//根据报审id查找该报审在评审验证or评审模块验证通过
		ContractPactRevVer verEntity = revVerService.getRevVerByReportId(reportId);
		ContractPactReview reviewEntity = reviewService.getReviewByReportId(reportId);
		if(verEntity != null) { //报审是在评审验证模块验证通过
			// 获取评审验证模块"协议及合同文件"资料清单
			List<ContractPactRevVerBf> verBfdList = revVerBfdService.getListByRevVerId(verEntity.getRevVerId());
			if(CollectionUtils.isNotEmpty(verBfdList)) {
				for(int i = 0; i< verBfdList.size(); i++) {
					ContractPactRevVerBf verBfd = verBfdList.get(i);
					if ("协议及合同文本".equals(verBfd.getFileTypeName())) {
						String verBfdId = verBfd.getBfdId();
						//获取评审验证模块的"协议及合同文件"资料清单文件
						List<FileUpload> files = fileUploadService.getList(verEntity.getModuleId(),
								verEntity.getBusiType(), verEntity.getRevVerId(), verBfd.getFileTypeId());
						// 将评审验证模块的"协议及合同文件"资料清单文件复制到协议出版模块
						copyBfdFile(files, publishBfds.get(0).getBfdId(), publishId, verBfd.getFileTypeId(), user);
					}
				}
			}
		} else if (reviewEntity != null) { // 报审是在评审模块验证通过
			// 获取评审模块"协议及合同文件"资料清单
			List<ContractPactReviewBfd> reviewBfdList = reviewBfdService.getListByPactReviewId(reviewEntity.getReviewId());
			if (CollectionUtils.isNotEmpty(reviewBfdList)) {
				for (int i = 0; i < reviewBfdList.size(); i++) {
					ContractPactReviewBfd reviewBfd = reviewBfdList.get(i);
					if ("协议及合同文本".equals(reviewBfd.getFileTypeName())) {
						String reviewBfdId = reviewBfd.getReviewBfdId();
						// 获取评审模块的"协议及合同文件"资料清单文件
						List<FileUpload> files = fileUploadService.getList(reviewEntity.getModuleId(), reviewEntity.getBusiType(), reviewBfdId,
								reviewEntity.getReviewId());
						// 将评审模块的"协议及合同文件"资料清单文件复制到协议出版模块
						copyBfdFile(files, publishBfds.get(0).getBfdId(), publishId, reviewBfd.getFileTypeId(), user);
					}
				}
			}
		}
	}

	/**
	 * 将资料清单文件复制到协议出版模块
	 * @param files 资料清单文件集合
	 */
	public void copyBfdFile(List<FileUpload> files, String bfdId, String publishId, String fileTypeId,
			UserProfile user) {
		if (CollectionUtils.isNotEmpty(files)) {
			for (int i = 0; i < files.size(); i++) {
				// 将资料清单文件信息保存到附件数据表，并复制磁盘中的资料清单文件
				FileUpload sourceFile = files.get(i);
				String[] levelId = { publishId, fileTypeId };
				File file = fileUploadService.getFile(sourceFile.getFileId());
				String newfileId = fileUploadService.saveFile(file, ContractPactPublish.MODULE_ID, ContractPactPublish.BUSI_TYPE,
						sourceFile.getFileName(), user, levelId);
				// 初始化协议出版资料清单文件
				FileUpload targetFile = fileUploadDao.get(newfileId);
				ContractPactPubBfdF bfdFile = new ContractPactPubBfdF();
				bfdFile.setRecordId(com.supporter.util.UUIDHex.newId());
				bfdFile.setBfdId(bfdId);
				bfdFile.setPublishId(publishId);
				bfdFile.setFuFileId(targetFile.getFileId());
				bfdFile.setIsUseSeal(ContractPactPublishConstant.ISUSESEAL_N);
				bfdFile.setDisplayOrder(targetFile.getDisplayOrder());
				bfdFileService.save(bfdFile);
			}
		}
	}

	/**
	 * 获取审批结论最终通过且未协议出版的报审单号
	 * @param prjId 
	 * @return map(报审id,报审单号)
	 */
	public Map<String, String> getAllPassReport(String prjId) {
		// 获取审批结论最终通过的报审
		Map<String, String> map = reportService.getAllPassReport(prjId);
		// 获取协议出版关联过的报审单号
		List<String> reportIdList = dao.getReportId();
		if (CollectionUtils.isNotEmpty(reportIdList)) {
			for (int i = 0; i < reportIdList.size(); i++) {
				map.remove(reportIdList.get(i));
			}
		}
		return map;
	}

	/**
	 * 协议出版编辑操作下,获取该条协议出版当前关联的报审单
	 * @return map(报审id,报审单号)
	 */
	public Map<String, String> getPassReport(String reportId) {
		Map<String, String> map = new HashMap<String, String>();
		ContractPactReport report = reportService.getReport(reportId);
		if (report != null) {
			map.put(reportId, report.getReportNo() + "_" + report.getReportName());
		}
		return map;
	}
}

