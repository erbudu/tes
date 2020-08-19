package com.supporter.prj.ppm.contract.pact.review_ver.service;

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
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReview;
import com.supporter.prj.ppm.contract.pact.review.entity.ContractPactReviewBfd;
import com.supporter.prj.ppm.contract.pact.review.service.ContractPactReviewBfdService;
import com.supporter.prj.ppm.contract.pact.review.service.ContractPactReviewService;
import com.supporter.prj.ppm.contract.pact.review_ver.constant.ContractPactRevVerConstant;
import com.supporter.prj.ppm.contract.pact.review_ver.dao.ContractPactRevVerDao;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVer;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerBf;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerF;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import com.supporter.util.UUIDHex;

@Service
@Transactional(TransManager.APP)
public class ContractPactRevVerService {

	@Autowired
	private ContractPactRevVerDao dao;
	@Autowired
	private ContractPactRevVerBfService bfdService;
	@Autowired
	private ContractPactRevVerFService bfdFileService;
	@Autowired
	private ContractPactRevVerConService contentService;
	@Autowired
	private ContractPactReviewBfdService reviewBfdService;
	@Autowired
	private ContractPactReviewService reviewService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private FileUploadDao fileUploadDao;
	@Autowired
	private BaseInfoService baseInfoService;

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractPactRevVer > getGrid(UserProfile user, JqGrid jqGrid, ContractPactRevVer contractPactRevVer) {
		return dao.findPage(jqGrid, contractPactRevVer);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * @param user 当前登录用户
	 * @param revVerId 主键
	 * @param prjId 项目id
	 * @return 初始化后的实体对象
	 */
	public ContractPactRevVer initEditOrViewPage(String revVerId, String prjId, UserProfile user) {
		ContractPactRevVer entity;
		if (StringUtils.isBlank(revVerId)) {// 新建
			entity = new ContractPactRevVer();
			entity.setRevVerId(com.supporter.util.UUIDHex.newId());
			entity.setRevVerificatContent(ContractPactRevVerConstant.REVVER_VERIFICAT_CONTENT);
			entity.setIsNew(true);
			entity.setStatus(ContractPactRevVer.StatusCodeTable.DRAFT);
			entity.setCreatorId(user.getPersonId());
			entity.setCreator(user.getName());
			entity.setCreationDate(new Date());
			// 获取左侧项目信息栏选中项目的信息
			Prj prj = baseInfoService.PrjInformation(prjId);
			entity.setPrjId(prj.getPrjId());
			entity.setPrjNo(prj.getPrjNo());
			entity.setPrjName(prj.getPrjCName());

			// 根据码表初始化资料清单
			List<ContractPactRevVerBf> bfds = new ArrayList<ContractPactRevVerBf>();
			List<IComCodeTableItem> items = EIPService.getComCodeTableService()
				.getCodeTableItems(ContractPactRevVerConstant.BFD_CODETABLE_NAME);
			for (int i = 0; i < items.size(); i++) {
				ContractPactRevVerBf bfd = new ContractPactRevVerBf();
				bfd.setBfdId(com.supporter.util.UUIDHex.newId());
				bfd.setRevVerId(entity.getRevVerId());
				bfd.setFileTypeId(items.get(i).getItemId());
				bfd.setFileTypeName(items.get(i).getItemValue());
				bfds.add(bfd);
			}
			entity.setBfds(bfds);
			return entity;
		} else {// 编辑
			entity = dao.get(revVerId);
			entity.setIsNew(false);
			entity.setModifierId(user.getPersonId());
			entity.setModifier(user.getName());
			entity.setModificationDate(new Date());
			// 获取某评审验证对应的资料清单
			List<ContractPactRevVerBf> bfds = new ArrayList<ContractPactRevVerBf>();
			bfds = bfdService.getListByRevVerId(revVerId);
			entity.setBfds(bfds);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * @param user 用户信息
	 * @param contractPactRevVer 实体类
	 * @return
	 */
	public ContractPactRevVer saveOrUpdate(UserProfile user, ContractPactRevVer entity) {
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
	 * @param revVerId 评审验证主键
	 */
	public void delete(String revVerId) {
		// 删除评审验证
		dao.delete(revVerId);
		// 删除资料清单
		bfdService.delete(revVerId);
		// 删除资料清单文件
		bfdFileService.delBdfFileByRevId(revVerId);
		// 删除评审结论
		contentService.delSwfResult(revVerId);
	}

	/**
	 * 根据主键获取实体对象
	 * @param id 模块主键
	 * @return 实体对象
	 */
	public ContractPactRevVer get(String id) {
		return dao.get(id);
	}

	/**
	 * 流程处理类更新实体对象
	 * @param pactReview
	 * @return
	 */
	public ContractPactRevVer update(ContractPactRevVer pactReview) {
		if (pactReview != null) {
			dao.update(pactReview);
		}
		return pactReview;
	}

	/**
	 * 生成评审验证编号
	 * @return 评审验证编号
	 */
	public String generateRevVerNo() {
		List<ContractPactRevVer> list = dao.generateReviewVerNo();
		Date date = new Date();
		SimpleDateFormat regx = new SimpleDateFormat("yyyy");
		if (CollectionUtils.isEmpty(list)) {
			return "协评验字" + regx.format(date) + "年第000号";
		} else {
			String no = list.get(0).getRevVerNo();
			int maxNo = Integer.parseInt(no.substring(no.lastIndexOf("第") + 1, no.lastIndexOf("第") + 4)) + 1;
			String maxNoStr = String.format("%03d", maxNo);
			return "协评验字" + regx.format(date) + "年第" + maxNoStr + "号";
		}
	}

	/**
	 * 提交
	 * @param revVerId 评审验证主键
	 * @return  操作结果
	 * @param user 当前登录用户
	 */
	public void valid(String revVerId, UserProfile user) {
		ContractPactRevVer entity = dao.get(revVerId);
		if (entity != null) {
			entity.setSubmitterId(user.getPersonId());
			entity.setSubmitter(user.getName());
			entity.setSubmissionDate(new Date());
			dao.update(entity);
		}
	}

	/**
	 * 获取某项目下所有验证结论为通过的报审
	 * @param prjId 项目id
	 * @return 报审ID集合
	 */
	public List<String> getRevVerPassReport(String prjId) {
		List<String> list = dao.getRevVerPassReport(prjId);
		return list;
	}

	/**
	 * 通过报审id获取实体对象
	 * @param reportId 协议报审主键
	 * @return 评审验证对象
	 */
	public ContractPactRevVer getRevVerByReportId(String reportId) {
		List<ContractPactRevVer> list = dao.findBy("reportId", reportId);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 初始化评审验证模块资料清单文件
	 * (将同一资料清单类型的评审资料清单文件复制给评审验证)
	 */
	public void initBfdFile(String reviewId, String bfdsStr, String revVerId, UserProfile user) {
		// 获取评审验证模块的资料清单
		List<ContractPactRevVerBf> revVerBfds = new ArrayList<ContractPactRevVerBf>();
		String[] bfdArr = bfdsStr.split("&");
		for (int i = 1; i < bfdArr.length; i += 4) {
			if(StringUtils.isNotBlank(bfdArr[i])) {
				ContractPactRevVerBf bfd = new ContractPactRevVerBf();
				bfd.setBfdId(bfdArr[i].substring(bfdArr[i].lastIndexOf("=") + 1));
				bfd.setRevVerId(bfdArr[i + 1].substring(bfdArr[i + 1].lastIndexOf("=") + 1));
				bfd.setFileTypeId(bfdArr[i + 2].substring(bfdArr[i + 2].lastIndexOf("=") + 1));
				bfd.setFileTypeName(bfdArr[i + 3].substring(bfdArr[i + 3].lastIndexOf("=") + 1));
				revVerBfds.add(bfd);
			}
		}
		// 获取评审模块资料清单
		List<ContractPactReviewBfd> reviewBfdList = reviewBfdService.getListByPactReviewId(reviewId);
		if (CollectionUtils.isNotEmpty(reviewBfdList)) {
			for (int i = 0; i < reviewBfdList.size(); i++) {
				ContractPactReviewBfd reviewBfd = reviewBfdList.get(i);
				// 评审模块资料清单类型
				String reviewFileType = reviewBfd.getFileTypeName();
				// String reviewBfdId = reviewBfd.getReviewBfdId();
				// 获取评审模块资料清单文件
				List<FileUpload> files = fileUploadService.getList(ContractPactReview.MODULE_ID, ContractPactReview.BUSI_TYPE,
						reviewId, reviewBfd.getFileTypeId());
				// 获取评审验证模块与评审模块同一资料清单类型的评审验证资料清单主键
				String revVerBfdId = "";
				// 资料清单类型id
				String fileTypeId = "";
				for (int j = 0; j < revVerBfds.size(); j++) {
					if (revVerBfds.get(j).getFileTypeName().equals(reviewFileType)) {
						revVerBfdId = revVerBfds.get(j).getBfdId();
						fileTypeId = revVerBfds.get(j).getFileTypeId();
						break;
					}
				}
				// 将评审模块资料清单文件复制到评审验证模块
				copyBfdFile(files, revVerBfdId, revVerId, fileTypeId, user);
			}
		}
	}

	/**
	 * 将评审模块资料清单文件复制到评审验证模块
	 * @param files 评审模块资料清单文件集合
	 * @param bfdId 评审验证模块资料清单id
	 * @param revVerId 评审验证模块主键
	 * @param user 当前登录用户
	 */
	private void copyBfdFile(List<FileUpload> files, String bfdId, String revVerId, String fileTypeId,
			UserProfile user) {
		if (CollectionUtils.isNotEmpty(files)) {
			for (int i = 0; i < files.size(); i++) {
				// 资料清单文件信息保存到附件数据表并复制磁盘中的资料清单文件
				FileUpload sourceFile = files.get(i);
				String[] levelId = { revVerId, fileTypeId };
				File file = fileUploadService.getFile(sourceFile.getFileId());
				String newfileId = fileUploadService.saveFile(file, ContractPactRevVer.MODULE_ID, ContractPactRevVer.BUSI_TYPE,
						sourceFile.getFileName(), user, levelId);
				FileUpload targetFile = fileUploadDao.get(newfileId);
				// 初始化评审验证资料清单文件
				ContractPactRevVerF bfdFile = new ContractPactRevVerF();
				bfdFile.setRecordId(com.supporter.util.UUIDHex.newId());
				bfdFile.setBfdId(bfdId);
				bfdFile.setRevVerId(revVerId);
				bfdFile.setFuFileId(targetFile.getFileId());
				bfdFile.setIsUseSeal(0);
				bfdFile.setDisplayOrder(targetFile.getDisplayOrder());
				bfdFileService.save(bfdFile);
			}
		}
	}

	/**
	 * 获取某项目下需评审验证但未评审验证的评审单号
	 * @param prjId 项目id
	 * @return map(评审id,评审单号)
	 */
	public Map<String, String> getVerReview(String prjId) {
		// 获取所有待验证的评审（评审模块评审结论为有条件通过的评审）
		Map<String, String> map = reviewService.getVerReview(prjId);
		if (!map.isEmpty()) {
			// 获取评审验证模块的评审ID集合
			List<String> reviewIdList = dao.getReviewId();
			// 去掉所有评审验证模块验证通过的评审
			if (CollectionUtils.isNotEmpty(reviewIdList)) {
				for (int i = 0; i < reviewIdList.size(); i++) {
					map.remove(reviewIdList.get(i));
				}
			}
		}
		return map;
	}

	/**
	 * 评审验证编辑操作下，获取评审验证当前关联的评审单
	 * @param reviewId 评审ID
	 * @return map(评审id,评审单号)
	 */
	public Map<String, String> getVerReviewPlus(String reviewId) {
		Map<String, String> map = new HashMap<String, String>();
		ContractPactReview review = reviewService.getReview(reviewId);
		if (review != null) {
			map.put(reviewId, review.getReviewNo() + "_" + review.getReportName());
		}
		return map;
	}

	/**
	 * 标记需要用印资料清单下的资料清单文件
	 * @param chkValueStr-用印资料清单主键字符串
	 * @param revVerId-主表主键
	 */
	public void markUsePrintFile(String chkValueStr, String revVerId) {
		String[] bfdIdArr = chkValueStr.split(",");
		for (int i = 0; i < bfdIdArr.length; i++) {
			String bfdId = bfdIdArr[i];
			// 获取资料清单下的资料清单文件
			if (StringUtils.isNotBlank(bfdId)) {
				List<ContractPactRevVerF> bfdFileList = bfdFileService.getFileByBfdId(bfdId);
				// 标记需要用印的资料清单文件
				if (CollectionUtils.isNotEmpty(bfdFileList)) {
					for (int j = 0; j < bfdFileList.size(); j++) {
						ContractPactRevVerF bfdFile = bfdFileList.get(j);
						if (bfdFile != null) {
							bfdFile.setIsUseSeal(1);
							bfdFileService.update(bfdFile);
						}
					}
				}
			}
		}
	}

	/**
	 * 获取待用印资料清单文件信息
	 * @param revVerId-主表主键
	 * @return 资料清单文件信息
	 */
	public List<Map<String, String>> getUseSealFile(String revVerId) {
		// 资料清单文件集合
		List<ContractPactRevVerF> bfdFileList = new ArrayList<ContractPactRevVerF>();
		if (StringUtils.isNotBlank(revVerId)) {
			bfdFileList = bfdFileService.getUseSealFile(revVerId);
		}
		// 资料清单文件相关信息
		List<Map<String, String>> li = new ArrayList<Map<String, String>>();
		if (CollectionUtils.isNotEmpty(bfdFileList)) {
			for (int i = 0; i < bfdFileList.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				ContractPactRevVerBf bfdEntity = bfdService.get(bfdFileList.get(i).getBfdId());// 资料清单对象
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
				map.put("moduleName", ContractPactRevVer.MODULE_ID);// 应用编号-申报准备
				map.put("busiType", ContractPactRevVer.BUSI_TYPE);// 附件上传业务一级分类
				li.add(map);
			}
			return li;
		}
		return null;
	}
}

