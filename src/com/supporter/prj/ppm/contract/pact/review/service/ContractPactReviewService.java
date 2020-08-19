package com.supporter.prj.ppm.contract.pact.review.service;

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
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReportBfd;
import com.supporter.prj.ppm.contract.pact.report.service.ContractPactReportBfdService;
import com.supporter.prj.ppm.contract.pact.report.service.ContractPactReportService;
import com.supporter.prj.ppm.contract.pact.review.constant.ContractPactReviewConstant;
import com.supporter.prj.ppm.contract.pact.review.dao.ContractPactReviewDao;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReview;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewBfd;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewBfdF;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import com.supporter.util.UUIDHex;

@Service
@Transactional(TransManager.APP)
public class ContractPactReviewService {
	@Autowired
	private ContractPactReviewDao dao;
	@Autowired
	private ContractPactReviewBfdService bfdService;
	@Autowired
	private ContractPactReviewBfdFService bfdFileService;
	@Autowired
	private ContractPactReviewConService contentService;
	@Autowired
	private BaseInfoService baseInfoService;
	@Autowired
	private ContractPactReportService reportService;
	@Autowired
	private ContractPactReportBfdService reportBfdService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private FileUploadDao fileUploadDao;

	/**
	 * 获取合同及协议评审列表
	 * @param jqGrid 列表
	 * @param review 实体对象
	 * @param user 当前登录用户
	 * @return 合同及协议评审列表
	 */
	public List<ContractPactReview> getGrid(JqGrid jqGrid, ContractPactReview review, UserProfile user) {
		return dao.findPage(jqGrid, review, user);
	}

	/**
	 *  进入新建或编辑或查看页面需要加载的信息.
	 * @param reviewId 主键
	 * @param prjId 项目id
	 * @param user 当前登录用户
	 * @return 实体对象
	 */
	public ContractPactReview initEditOrViewPage(String reviewId, String prjId, UserProfile user) {
		ContractPactReview review = new ContractPactReview();
		if (StringUtils.isBlank(reviewId)) {// 新建
			// 初始化主表基本信息
			review.setReviewId(com.supporter.util.UUIDHex.newId());
			review.setIsNew(true);
			review.setStatus(ContractPactReview.StatusCodeTable.DRAFT);
			review.setCreatorId(user.getPersonId());
			review.setCreator(user.getName());
			review.setCreationDate(new Date());
			// 获取左侧项目信息栏选中项目的信息
			Prj prj = baseInfoService.PrjInformation(prjId);
			review.setPrjId(prj.getPrjId());
			review.setPrjNo(prj.getPrjNo());
			review.setPrjName(prj.getPrjCName());
			review.setReviewType(ContractPactReviewConstant.REVIEW_TYPE);
			// 根据码表初始化资料清单
			List<ContractPactReviewBfd> bfds = new ArrayList<ContractPactReviewBfd>();
			List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems(ContractPactReviewConstant.BFD_CODETABLE_NAME);
			for (int i = 0; i < items.size(); i++) {
				ContractPactReviewBfd bfd = new ContractPactReviewBfd();
				bfd.setReviewBfdId(com.supporter.util.UUIDHex.newId());
				bfd.setReviewId(review.getReviewId());
				bfd.setFileTypeId(items.get(i).getItemId());
				bfd.setFileTypeName(items.get(i).getItemValue());
				bfds.add(bfd);
			}
			review.setBfds(bfds);
		} else {// 编辑|查看
			review = dao.get(reviewId);
			review.setIsNew(false);
			review.setModifierId(user.getPersonId());
			review.setModifier(user.getName());
			review.setModificationDate(new Date());
			// 获取某评审对应的资料清单
			List<ContractPactReviewBfd> bfds = bfdService.getListByPactReviewId(reviewId);
			List<ContractPactReviewBfd> bfdsNew = new ArrayList<ContractPactReviewBfd>();
			// 使查看页面资料清单顺序与编辑页面保持一致
			List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems(ContractPactReviewConstant.BFD_CODETABLE_NAME);
			if (CollectionUtils.isNotEmpty(items) && CollectionUtils.isNotEmpty(bfds)) {
				for (int i = 0; i < items.size(); i++) {
					String fileTypeId = items.get(i).getItemId();
					for (int j = 0; j < bfds.size(); j++) {
						if (bfds.get(j).getFileTypeId().equals(fileTypeId)) {
							bfdsNew.add(bfds.get(j));
						}
					}
				}
			}
			review.setBfds(bfdsNew);
		}
		return review;
	}

	/**
	 * 保存
	 * @param review 实体对象
	 * @param user 当前登录用户
	 * @return 保存后的实体对象
	 */
	public ContractPactReview saveOrUpdate(ContractPactReview review, UserProfile user) {
		if (review != null) {
			if (review.getIsNew()) {// 新建
				// 保存评审
				dao.save(review);
				// 保存评审资料清单
				bfdService.saveBdf(review.getBfds());
			} else {
				// 保存评审
				review.setModifierId(user.getPersonId());
				review.setModifier(user.getName());
				review.setModificationDate(new Date());
				dao.update(review);
			}
			// 保存资料清单文件
			bfdFileService.saveBfdFile(review.getBfds());
		}
		return review;
	}

	/**
	 * 删除
	 * @param reviewId 评审主键
	 */
	public void delete(String reviewId) {
		// 删除评审
		dao.delete(reviewId);
		// 删除资料清单
		bfdService.delete(reviewId);
		// 删除资料清单文件
		bfdFileService.delBdfFileByReviewId(reviewId);
		// 删除评审结论
		contentService.delSwfResult(reviewId);
	}

	/**
	 * 根据主键获取实体对象
	 * @param id 模块主键
	 * @return 实体对象
	 */
	public ContractPactReview get(String id) {
		return dao.get(id);
	}

	/**
	 * 流程处理类更新
	 * @param pactReview
	 * @return
	 */
	public ContractPactReview update(ContractPactReview pactReview) {
		if (pactReview != null) {
			dao.update(pactReview);
		}
		return pactReview;
	}

	/**
	 * 设置提交信息
	 * @param reviewId
	 */
	public void valid(String reviewId, UserProfile user) {
		ContractPactReview pactReview = dao.get(reviewId);
		if (pactReview != null) {
			pactReview.setSubmitterId(user.getPersonId());
			pactReview.setSubmitter(user.getName());
			pactReview.setSubmissionDate(new Date());
			dao.update(pactReview);
		}
	}

	/**
	 * 编号生成规则
	 * @return 评审编号
	 */
	public String generateReviewNo() {
		List<ContractPactReview> list = dao.generateReviewNo();
		Date date = new Date();
		SimpleDateFormat regx = new SimpleDateFormat("yyyy");
		if (CollectionUtils.isEmpty(list)) {
			return "协评字" + regx.format(date) + "年第000号";
		} else {
			String no = list.get(0).getReviewNo();
			int maxNo = Integer.parseInt(no.substring(no.lastIndexOf("第") + 1, no.lastIndexOf("第") + 4)) + 1;
			String maxNoStr = String.format("%03d", maxNo);
			return "协评字" + regx.format(date) + "年第" + maxNoStr + "号";
		}
	}

	/**
	 * 获取某项目下所有审批结果为有条件通过的评审ID
	 * @param prjId 项目id
	 * @return Map<评审ID, 评审编号>
	 */
	public Map<String, String> getVerReview(String prjId) {
		Map<String, String> map = new HashMap<String, String>();
		// 获取某项目下所有审批结果为有条件通过的评审
		List<ContractPactReview> reviewIdList = dao.getConPassReview(prjId);
		if (CollectionUtils.isNotEmpty(reviewIdList)) {
			for (int i = 0; i < reviewIdList.size(); i++) {
				if (reviewIdList.get(i) != null) {
					map.put(reviewIdList.get(i).getReviewId(), reviewIdList.get(i).getReviewNo() 
						+ "_" + reviewIdList.get(i).getReportName());
				}
			}
		}
		return map;
	}

	/**
	 * 获取审批结论为通过的报审单ID集合
	 * @param prjId 项目id
	 * @return 报审单ID集合
	 */
	public List<String> getReviewPassReport(String prjId) {
		List<String> list = dao.getReviewPassReport(prjId);
		return list;
	}

	/**
	 * 根据主键获取评审对象
	 * @param reviewId 评审主键
	 * @return 实体对象
	 */
	public ContractPactReview getReview(String reviewId) {
		ContractPactReview entity = dao.get(reviewId);
		// 获取某评审对应的资料清单
		List<ContractPactReviewBfd> bfds = bfdService.getListByPactReviewId(reviewId);
		entity.setBfds(bfds);
		return entity;
	}

	/**
	 * 根据报审id获取评审对象
	 * @param reportId 报审主键
	 * @return 评审对象
	 */
	public ContractPactReview getReviewByReportId(String reportId) {
		List<ContractPactReview> list = dao.findBy("reportId", reportId);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取所有报审单id
	 * @return 报审id集合
	 */
	public List<String> getReportId() {
		List<String> list = dao.getReportId();
		return list;
	}

	/**
	* 评审编辑操作下，获取评审关联的报审单
	* @return  Map<报审id, 报审编号>
	*/
	public Map<String, String> getCompleteReport(String reportId) {
		Map<String, String> map = new HashMap<String, String>();
		ContractPactReport report = reportService.getReport(reportId);
		if (report != null) {
			map.put(reportId, report.getReportNo());
		}
		return map;
	}

	/**
	 * 获取所有某项目下审批完成且未创建评审的合同及协议报审
	 * @param prjId 项目id
	 * @return  Map<报审id, 报审编号+"_"+报审名称>
	 */
	public Map<String, String> getAllCompleteReport(String prjId) {
		// 获取所有审批完成的合同及协议报审
		Map<String, String> map = reportService.getAllCompleteReportPlus(prjId);
		// 去掉已经创建评审的报审单
		List<String> reportIdList = getReportId();
		if (CollectionUtils.isNotEmpty(reportIdList)) {
			for (int i = 0; i < reportIdList.size(); i++) {
				map.remove(reportIdList.get(i));
			}
		}
		return map;
	}

	/**
	 * 获取所有某项目下审批完成且未创建评审的合同及协议报审(不包括当前评审关联的报审)
	 * 主要用于评审的驳回页面
	 * @param prjId 项目id
	 * @return  Map<报审id, 报审编号+"_"+报审名称>
	 */
	public Map<String, String> getAllCompleteReportPlus(String prjId, String reportId) {
		// 获取所有审批完成的合同及协议报审
		Map<String, String> map = reportService.getAllCompleteReportPlus(prjId);
		// 去掉已经创建评审的报审单
		List<String> reportIdList = getReportId();
		if (CollectionUtils.isNotEmpty(reportIdList)) {
			for (int i = 0; i < reportIdList.size(); i++) {
				map.remove(reportIdList.get(i));
			}
		}
		// 添加上当前评审关联的报审
		ContractPactReport report = reportService.getReport(reportId);
		if (report != null) {
			map.put(reportId, report.getReportNo() + "_" + report.getReportName());
		}
		return map;
	}

	/**
	 * 标记需要用印资料清单下的资料清单文件
	 * @param chkValueStr-用印资料清单主键字符串
	 * @param reviewId-主表主键
	 */
	public void markUsePrintFile(String chkValueStr, String reviewId) {
		String[] bfdIdArr = chkValueStr.split(",");
		for(int i=0;i<bfdIdArr.length;i++) {
			String bfdId = bfdIdArr[i];
			// 获取资料清单下的资料清单文件
			if (StringUtils.isNotBlank(bfdId)) {
				List<ContractPactReviewBfdF> bfdFileList = bfdFileService.getFileByBfdId(bfdId);
				// 标记需要用印的资料清单文件
				if (CollectionUtils.isNotEmpty(bfdFileList)) {
					for (int j = 0; j < bfdFileList.size(); j++) {
						ContractPactReviewBfdF bfdFile = bfdFileList.get(j);
						if (bfdFile != null) {
							bfdFile.setIsUseSeal(ContractPactReviewConstant.ISUSESEAL_Y);
							bfdFileService.update(bfdFile);
						}
					}
				}
			}
		}
	}

	/**
	 * 获取待用印资料清单文件信息
	 * @param reviewId-主表主键
	 * @return 资料清单文件信息
	 */
	public List<Map<String, String>> getUseSealFile(String reviewId) {
		// 资料清单文件集合
		List<ContractPactReviewBfdF> bfdFileList = new ArrayList<ContractPactReviewBfdF>();
		if (StringUtils.isNotBlank(reviewId)) {
			bfdFileList = bfdFileService.getUseSealFile(reviewId);
		}
		// 资料清单文件相关信息
		List<Map<String, String>> li = new ArrayList<Map<String, String>>();
		if (CollectionUtils.isNotEmpty(bfdFileList)) {
			for (int i = 0; i < bfdFileList.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				ContractPactReviewBfd bfdEntity = bfdService.get(bfdFileList.get(i).getBfdId());// 资料清单对象
				map.put("sealFileId", UUIDHex.newId());// 用印文件主键
				map.put("fileUpBusinessId", bfdFileList.get(i).getRecordId());// 用印文件主键
				map.put("bfdTypeName", bfdEntity.getFileTypeName());// 文件类型
				String fileUploadId = bfdFileList.get(i).getFuFileId();
				if (StringUtils.isNotBlank(fileUploadId)) {
					FileUpload fileUpload = fileUploadService.get(fileUploadId);
					if (fileUpload != null) {
						map.put("fuFileName", fileUpload.getFileName());// 文件名称
						map.put("fuFileId", fileUpload.getFileId());// 文件id
					}
				}
				map.put("moduleName", ContractPactReview.MODULE_ID);// 应用编号-申报准备
				map.put("busiType", ContractPactReview.BUSI_TYPE);// 附件上传业务一级分类
				li.add(map);
			}
			return li;
		}

		return null;
	}

	/**
	 * 带入关联报审的资料清单文件
	 * @param reviewId-评审id
	 * @param bfdsStr-资料清单属性拼接而成的字符串
	 * @param reportId-报审id
	 */
	public void initBfdFile(String reviewId, String bfdsStr, String reportId, UserProfile user) {
		// 获取评审模块的资料清单
		List<ContractPactReviewBfd> reviewBfds = new ArrayList<ContractPactReviewBfd>();
		String[] bfdArr = bfdsStr.split("&");
		for (int i = 1; i < bfdArr.length; i += 4) {
			if (StringUtils.isNotBlank(bfdArr[i])) {
				ContractPactReviewBfd bfd = new ContractPactReviewBfd();
				bfd.setReviewBfdId(bfdArr[i].substring(bfdArr[i].lastIndexOf("=") + 1));
				bfd.setReviewId(bfdArr[i + 1].substring(bfdArr[i + 1].lastIndexOf("=") + 1));
				bfd.setFileTypeId(bfdArr[i + 2].substring(bfdArr[i + 2].lastIndexOf("=") + 1));
				bfd.setFileTypeName(bfdArr[i + 3].substring(bfdArr[i + 3].lastIndexOf("=") + 1));
				reviewBfds.add(bfd);
			}
		}
		// 获取报审模块资料清单
		List<ContractPactReportBfd> reportBfdList = reportBfdService.getListByPactReportId(reportId);
		if (CollectionUtils.isNotEmpty(reportBfdList)) {
			for (int i = 0; i < reportBfdList.size(); i++) {
				ContractPactReportBfd reportBfd = reportBfdList.get(i);
				// 报审模块资料清单类型
				String reportFileType = reportBfd.getFileTypeName();
				// String reviewBfdId = reviewBfd.getReviewBfdId();
				// 获取报审模块资料清单文件
				List<FileUpload> files = fileUploadService.getList(ContractPactReport.MODULE_ID,
						ContractPactReport.BUSI_TYPE, reportId, reportBfd.getFileTypeId());
				// 获取评审模块与报审模块同一资料清单类型的评审资料清单主键
				String reviewBfdId = "";
				String reviewFileTypeId = "";
				for (int j = 0; j < reviewBfds.size(); j++) {
					if (reviewBfds.get(j).getFileTypeName().equals(reportFileType)) {
						reviewBfdId = reviewBfds.get(j).getReviewBfdId();
						reviewFileTypeId = reviewBfds.get(j).getFileTypeId();
						break;
					}
				}
				// 将评审模块资料清单文件复制到评审验证模块
				copyBfdFile(files, reviewBfdId, reviewId, reviewFileTypeId, user);
			}
		}
	}

	/**
	 * 将报审模块资料清单文件复制到评审模块
	 * @param files-报审模块资料清单文件集合
	 * @param reviewBfdId-评审模块资料清单id
	 * @param reviewId-评审模块主键
	 * @param user-当前登录用户
	 */
	private void copyBfdFile(List<FileUpload> files, String reviewBfdId, String reviewId, String reviewFileTypeId,
			UserProfile user) {
		if (CollectionUtils.isNotEmpty(files)) {
			for (int i = 0; i < files.size(); i++) {
				// 资料清单文件信息保存到附件数据表并复制磁盘中的资料清单文件
				FileUpload sourceFile = files.get(i);
				String[] levelId = { reviewId, reviewFileTypeId };
				File file = fileUploadService.getFile(sourceFile.getFileId());
				String newfileId = fileUploadService.saveFile(file, ContractPactReview.MODULE_ID,
						ContractPactReview.BUSI_TYPE, sourceFile.getFileName(), user, levelId);
				FileUpload targetFile = fileUploadDao.get(newfileId);
				// 初始化评审资料清单文件
				ContractPactReviewBfdF bfdFile = new ContractPactReviewBfdF();
				bfdFile.setRecordId(com.supporter.util.UUIDHex.newId());
				bfdFile.setBfdId(reviewBfdId);
				bfdFile.setReviewId(reviewId);
				bfdFile.setFuFileId(targetFile.getFileId());
				bfdFile.setIsUseSeal(ContractPactReviewConstant.ISUSESEAL_N);
				bfdFile.setDisplayOrder(targetFile.getDisplayOrder());
				bfdFileService.save(bfdFile);
			}
		}
	}

}
