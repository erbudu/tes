package com.supporter.prj.ppm.contract.pact.beian.service;

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
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.pact.beian.dao.ContractPactBeianDao;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeian;
import com.supporter.prj.ppm.contract.pact.beian.entity.ContractPactBeianBfd;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReport;
import com.supporter.prj.ppm.contract.pact.report.service.ContractPactReportService;
import com.supporter.prj.ppm.contract.pact.seal_bfd.constant.ContractPactPublishConstant;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

@Service
@Transactional(TransManager.APP)
public class ContractPactBeianService {

	@Autowired
	private ContractPactBeianDao dao;
	@Autowired
	private ContractPactBeianBfdService bfdService;
	@Autowired
	private ContractPactBeianBfdFService bfdFileService;
	@Autowired
	private ContractPactReportService reportService;
	@Autowired
	private BaseInfoService baseInfoService;

	/**
	 * 分页表格展示数据.
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<ContractPactBeian> getGrid(UserProfile user, JqGrid jqGrid, ContractPactBeian entity) {
		return dao.findPage(jqGrid, entity);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * @param user
	 * @param recordId
	 * @param prjId 
	 * @return
	 */
	public ContractPactBeian initEditOrViewPage(String recordId, String prjId, UserProfile user) {
		ContractPactBeian entity;
		if (StringUtils.isBlank(recordId)) {// 新建
			entity = new ContractPactBeian();
			entity.setRecordId(com.supporter.util.UUIDHex.newId());
			entity.setIsNew(true);
			entity.setStatus(ContractPactBeian.StatusCodeTable.DRAFT);
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
			List<ContractPactBeianBfd> bfds = new ArrayList<ContractPactBeianBfd>();
			List<IComCodeTableItem> items = EIPService.getComCodeTableService()
				.getCodeTableItems(ContractPactPublishConstant.BFD_CODETABLE_NAME);
			for (int i = 0; i < items.size(); i++) {
				// 只显示合同及协议的资料清单
				if ("协议及合同文本".equals(items.get(i).getItemValue())) {
					ContractPactBeianBfd bfd = new ContractPactBeianBfd();
					bfd.setBfdId(com.supporter.util.UUIDHex.newId());
					bfd.setRecordId(entity.getRecordId());
					bfd.setFileTypeId(items.get(i).getItemId());
					bfd.setFileTypeName(items.get(i).getItemValue());
					bfds.add(bfd);
				}
			}
			entity.setBfds(bfds);
			return entity;
		} else {// 编辑
			entity = dao.get(recordId);
			entity.setIsNew(false);
			entity.setModifierId(user.getPersonId());
			entity.setModifier(user.getName());
			entity.setModificationDate(new Date());
			// 获取某协议出版对应的资料清单
			List<ContractPactBeianBfd> bfds = new ArrayList<ContractPactBeianBfd>();
			bfds = bfdService.getListByrecordId(recordId);
			entity.setBfds(bfds);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * @param user 用户信息
	 * @param ContractPactBeian 实体类
	 * @return
	 */
	public ContractPactBeian saveOrUpdate(UserProfile user, ContractPactBeian entity) {
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
	 * @param recordId 评审验证主键
	 */
	public void delete(String recordId) {
		// 删除评审验证
		dao.delete(recordId);
		// 删除资料清单
		bfdService.delete(recordId);
		// 删除资料清单文件
		bfdFileService.delBdfFileByRevId(recordId);
	}

	/**
	 * 根据主键获取实体对象
	 * @param id 模块主键
	 * @return 实体对象
	 */
	public ContractPactBeian get(String id) {
		return dao.get(id);
	}

	/**
	 * 流程处理类更新实体对象
	 * @param pactReview
	 * @return
	 */
	public ContractPactBeian update(ContractPactBeian pactPublish) {
		if (pactPublish != null) {
			dao.update(pactPublish);
		}
		return pactPublish;
	}

	/**
	 * 提交
	 * @param recordId 评审验证主键
	 * @return  操作结果
	 * @param user 当前登录用户
	 */
	public void valid(String recordId, UserProfile user) {
		ContractPactBeian entity = dao.get(recordId);
		if (entity != null) {
			entity.setSubmitterId(user.getPersonId());
			entity.setSubmitter(user.getName());
			entity.setSubmissionDate(new Date());
			dao.update(entity);
		}
	}

	/**
	 * 编号生成规则
	 * @return 备案编号
	 */
	public String generateBeianNo() {
		List<ContractPactBeian> list = dao.getMaxBeianNo();
		Date date = new Date();
		SimpleDateFormat regx = new SimpleDateFormat("yyyy");
		if (CollectionUtils.isEmpty(list)) {
			return "协备字" + regx.format(date) + "年第000号";
		} else {
			String no = list.get(0).getRecordNo();
			int maxNo = Integer.parseInt(no.substring(no.lastIndexOf("第") + 1, no.lastIndexOf("第") + 4)) + 1;
			String maxNoStr = String.format("%03d", maxNo);
			return "协备字" + regx.format(date) + "年第" + maxNoStr + "号";
		}
	}

	/**
	 * 获取某项目下所有通过/有条件通过并验证通过的报审，且该报审未备案过
	 * @param prjId 项目id
	 * @return Map<报审id, 报审编号>
	 */
	public Map<String, String> getAllPassReport(String prjId) {
		// 获取所有通过/有条件通过的报审
		Map<String, String> map = reportService.getAllPassReport(prjId);
		// 获取所有已备案的报审
		List<String> list = dao.getAllReportId();
		// 去掉已备案的报审
		for (int i = 0; i < list.size(); i++) {
			map.remove(list.get(i));
		}
		return map;
	}

	/**
	 * 备案编辑操作下，获取该条备案当前关联的报审单
	 * @param reportId 报审单id
	 * @return Map<报审id, 报审编号>
	 */
	public Map<String, String> getAllPassReportPlus(String reportId) {
		Map<String, String> map = new HashMap<String, String>();
		ContractPactReport report = reportService.getReport(reportId);
		if (report != null) {
			map.put(reportId, report.getReportNo() + "_" + report.getReportName());
		}
		return map;
	}

	/**
	 *  获取某项目下已备案完成的报审id
	 * @param prjId 项目id
	 * @return 报审id集合
	 */
	public List<String> getBeianPassRepId(String prjId) {
		List<String> repIdList = dao.getBeianPassRepId(prjId);
		return repIdList;
	}

}

