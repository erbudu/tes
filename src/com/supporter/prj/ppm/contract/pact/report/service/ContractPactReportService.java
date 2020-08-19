package com.supporter.prj.ppm.contract.pact.report.service;

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
import com.supporter.prj.ppm.contract.pact.report.constant.ContractPactReportConstant;
import com.supporter.prj.ppm.contract.pact.report.dao.ContractPactReportDao;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReport;
import com.supporter.prj.ppm.contract.pact.report.entity.ContractPactReportBfd;
import com.supporter.prj.ppm.contract.pact.review.service.ContractPactReviewService;
import com.supporter.prj.ppm.contract.pact.review_ver.service.ContractPactRevVerService;
import com.supporter.prj.ppm.ecc.project_ecc.service.EccService;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;

@Service
@Transactional(TransManager.APP)
public class ContractPactReportService {

	@Autowired
	private ContractPactReportDao dao;
	@Autowired
	private ContractPactReportBfdService bfdService;
	@Autowired
	private ContractPactReportBfdFService bfdFileService;
	@Autowired
	private ContractPactReviewService reviewService;
	@Autowired
	private ContractPactRevVerService revVerService;
	@Autowired
	private BaseInfoService baseInfoService;
	@Autowired
	private EccService eccService;

	/**
	 * 获取合同及协议报审列表
	 * @param jqGrid 列表
	 * @param report 实体对象
	 * @param user 当前登录用户
	 * @return 合同及协议报审列表
	 */
	public List<ContractPactReport> getGrid(JqGrid jqGrid, ContractPactReport report, UserProfile user) {
		return dao.findPage(jqGrid, report, user);
	}

	/**
	 * 获取某项目下已备案完成的报审列表,且报审的合同及协议类型为项目开发合作协议下子项
	 * @param jqGrid 列表
	 * @param report 实体对象
	 * @param user 当前登录用户
	 * @param keyword 模糊查询关键字
	 * @return 合同及协议报审列表
	 */
	public List<ContractPactReport> getBeianPassRepGrid(JqGrid jqGrid, ContractPactReport report, UserProfile user, String keyword) {
		return dao.getBeianPassRepGrid(jqGrid, report, user, keyword);
	}

	/**
	 * 获取某项目下已备案完成的报审列表
	 * @param jqGrid 列表
	 * @param report 实体对象
	 * @param user 当前登录用户
	 * @param keyword 模糊查询关键字
	 * @return 合同及协议报审列表
	 */
	public List<ContractPactReport> getBeianPassRepGridSimp(JqGrid jqGrid, ContractPactReport report, UserProfile user, String keyword) {
		return dao.getBeianPassRepGridSimp(jqGrid, report, user, keyword);
	}

	/**
	 * 获取某项目下已备案完成的报审对象，且该报审处于生效(审批完成)状态
	 * @param prjId 项目信息栏中选中项目的id
	 * @return 报审对象集合
	 */
	public List<ContractPactReport> getBeianPassReports(String prjId) {
		return dao.getBeianPassReports(prjId);
	}

	/**
	 *  进入新建或编辑或查看页面需要加载的信息.
	 * @param id 主键
	 * @param user 当前登录用户
	 * @param prjId 项目信息栏中选中项目的id
	 * @return 实体对象
	 */
	public ContractPactReport initEditOrViewPage(String reportId, UserProfile user, String prjId) {
		ContractPactReport report = new ContractPactReport();
		// 初始化主表基本信息
		if (StringUtils.isBlank(reportId)) {//新建
			report.setReportId(com.supporter.util.UUIDHex.newId());
			// report.setReportNo("");
			report.setIsNew(true);
			report.setStatus(ContractPactReport.StatusCodeTable.DRAFT);
			report.setCreatorId(user.getPersonId());
			report.setCreator(user.getName());
			report.setCreationDate(new Date());
			// 获取左侧项目信息栏选中项目的信息
			Prj prj = baseInfoService.PrjInformation(prjId);
			report.setPrjId(prj.getPrjId());
			report.setPrjNo(prj.getPrjNo());
			report.setPrjNameZh(prj.getPrjCName());
			report.setPrjNameEn(prj.getPrjEName());
			// 联系人默认当前登录人
			report.setContactId(user.getPersonId());
			report.setContactName(user.getName());
			// 测试赋值
			report.setReportName("报审名称为...");
			// 根据码表初始化资料清单
			List<ContractPactReportBfd> bfds = new ArrayList<ContractPactReportBfd>();
			List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems(ContractPactReportConstant.BFD_CODETABLE_NAME);
			for (int i = 0; i < items.size(); i++) {
				ContractPactReportBfd bfd = new ContractPactReportBfd();
				bfd.setBfdId(com.supporter.util.UUIDHex.newId());
				bfd.setReportId(report.getReportId());
				bfd.setFileTypeId(items.get(i).getItemId());
				bfd.setFileTypeName(items.get(i).getItemValue());
				bfds.add(bfd);
			}
			report.setBfds(bfds);
		} else {//编辑
			report = dao.get(reportId);
			report.setIsNew(false);
			report.setModifierId(user.getPersonId());
			report.setModifier(user.getName());
			report.setModificationDate(new Date());
			// 获取某报审对应的资料清单
			List<ContractPactReportBfd> bfds = new ArrayList<ContractPactReportBfd>();
			bfds = bfdService.getListByPactReportId(reportId);
			report.setBfds(bfds);
		}
		return report;
	}

	/**
	 *  报审编号生成规则
	 * @return 报审编号
	 */
	public String generateReportNo() {
		List<ContractPactReport> list = dao.generateReportNo();
		Date date = new Date();
		SimpleDateFormat regx = new SimpleDateFormat("yyyy");
		if (CollectionUtils.isEmpty(list)) {
			return "报审" + regx.format(date) + "年第000号";
		} else {
			String no = list.get(0).getReportNo();
			int maxNo = Integer.parseInt(no.substring(no.lastIndexOf("第") + 1, no.lastIndexOf("第") + 4)) + 1;
			String maxNoStr = String.format("%03d", maxNo);
			return "报审" + regx.format(date) + "年第" + maxNoStr + "号";
		}
	}

	/**
	 * 保存
	 * @param report 实体对象
	 * @param user 当前登录用户
	 * @return 保存后的实体对象
	 */
	public ContractPactReport saveOrUpdate(ContractPactReport report, UserProfile user) {
		if (report == null) {
			return null;
		}
		ContractPactReport ret = null;
		// 设置合同及协议名称为报审单名称
		report.setReportName(report.getContractPactName());
		if (report.getIsNew()) {// 新建
			// 保存报审
			dao.save(report);
			// 保存资料清单
			bfdService.saveBdf(report.getBfds());
			// 保存资料清单文件
			bfdFileService.saveBfdFile(report.getBfds());
			ret = report;
		} else {
			// 保存报审
			dao.update(report);
			// 保存资料清单文件
			bfdFileService.saveBfdFile(report.getBfds());
			ret = report;
		}
		return ret;
	}

	/**
	 * 设置提交人等信息
	 * @param reportId 报审主键
	 * @param user 当前登录用户
	 */
	public void valid(String reportId, UserProfile user) {
		if (StringUtils.isNotBlank(reportId)) {
			ContractPactReport report = dao.get(reportId);
			report.setSubmitterId(user.getPersonId());
			report.setSubmitter(user.getName());
			report.setSubmissionDate(new Date());
			dao.update(report);
		}
	}

	/**
	 * 删除
	 * @param reportId 主键
	 */
	public void delete(String reportId) {
		// 删除主表
		ContractPactReport report = dao.get(reportId);
		dao.delete(report);
		// 删除资料清单
		bfdService.delete(reportId);
		// 删除资料清单文件
		bfdFileService.delBdfFileByReportId(reportId);
	}

	/**
	 * 根据主键获取实体对象
	 * @param id 模块主键
	 * @return 实体对象
	 */
	public ContractPactReport get(String id) {
		return dao.get(id);
	}

	/**
	 * 流程处理类更新
	 * @param pactReport
	 * @return
	 */
	public ContractPactReport update(ContractPactReport pactReport) {
		if (pactReport != null) {
			dao.update(pactReport);
		}
		return pactReport;
	}

	/**
	 * 获取某项目下所有审批完成的合同及协议报审
	 * @param prjId 项目id
	 * @return Map<报审id, 报审编号>
	 */
	public Map<String, String> getAllCompleteReport(String prjId) {
		List<ContractPactReport> list = dao.getAllCompleteReport(prjId);
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).getReportId(), list.get(i).getReportNo());
		}
		return map;
	}

	/**
	 * 获取某项目下所有审批完成的合同及协议报审
	 * @param prjId 项目id
	 * @return Map<报审id, 报审编号_报审名称>
	 */
	public Map<String, String> getAllCompleteReportPlus(String prjId) {
		List<ContractPactReport> list = dao.getAllCompleteReport(prjId);
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).getReportId(), list.get(i).getReportNo() + "_" + list.get(i).getReportName());
		}
		return map;
	}

	/**
	 * 根据主键获取报审对象
	 * @param reportId 报审主键
	 * @return 实体对象
	 */
	public ContractPactReport getReport(String reportId) {
		ContractPactReport report = dao.get(reportId);
		return report;
	}

	/**
	 * 获取某项目下所有评审模块的评审结论为通过
	 * +评审结论为有条件通过并评审验证通过的报审
	 * @return Map<报审ID,报审编号>
	 */
	public Map<String, String> getAllPassReport(String prjId) {
		Map<String, String> map = new HashMap<String, String>();
		// 获取评审通过的报审ID
		List<String> list = reviewService.getReviewPassReport(prjId);
		// 获取评审结论有条件通过并评审验证通过的报审
		List<String> listTwo = revVerService.getRevVerPassReport(prjId);
		list.addAll(listTwo);
		// 获取所有审批通过的报审
		if(CollectionUtils.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				ContractPactReport report = dao.get(list.get(i));
				map.put(report.getReportId(), report.getReportNo() + "_" + report.getReportName());
			}
		}
		return map;
	}

	/**
	 * 检查所选项目是否可以启动合同及协议的签订
	 * 启动合同及协议的条件为项目初始登录完成并出口管制审查完毕
	 * @param prjId 项目id
	 * @return true-可以启动，false-不可以启动
	 */
	public boolean checkPrjStatus(String prjId) {
		Prj prj = baseInfoService.PrjInformation(prjId);
		// 判断项目是否初始化登陆完成
		Integer prjStatus = prj.getStatus();
		if (prjStatus == 1 || prjStatus == 2) {// 初始化登陆完成
			// 判断项目是否需要进行出口管制确认
			if (prj.getIsEccConfirm()) {// 需要进行出口管制确认
				if (eccService.prjIsEccComplete(prjId)) {// 出口管制审查完毕
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 初始化合同及协议类型下拉选选项组
	 */
	public List<Object> getContractPactType() {
		List<Object> list = new ArrayList<Object>();
		//获取合同及协议类型码表
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_CONTRACT_PACT_TYPE");
		//分离码表项中的一级码表与二级码表，减少运算
		List<IComCodeTableItem> itemRoot = new ArrayList<IComCodeTableItem>();//一级码表
		List<IComCodeTableItem> itemLeaf = new ArrayList<IComCodeTableItem>();//一级码表下的二级码表
		if (CollectionUtils.isNotEmpty(items)) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getParentItemId() == null || items.get(i).getParentItemId() == "") {//一级码表
					itemRoot.add(items.get(i));
				}else {//二级码表
					itemLeaf.add(items.get(i));
				}
			}
		}
		//初始化合同及协议类型下拉选-选项组数据
		if (CollectionUtils.isNotEmpty(itemRoot)) {//一级码表
			Map<String, Object> map;
			for (int i = 0; i < itemRoot.size(); i++) {
				map = new HashMap<String, Object>();
				map.put("label", itemRoot.get(i).getItemValue());
				if (CollectionUtils.isNotEmpty(itemLeaf)) {//二级码表
					List<Object> lista = new ArrayList<Object>();
					for (int j = 0; j < itemLeaf.size(); j++) {
						// 判断二级码表是否为一级码表的子项
						if (itemLeaf.get(j).getParentItemId().equals(itemRoot.get(i).getItemId())) {// 是
							Map<String, String> mapa = new HashMap<String, String>();
							mapa.put("id", itemLeaf.get(j).getItemId());
							mapa.put("name", itemLeaf.get(j).getItemValue());
							lista.add(mapa);
						}
						map.put("group", lista);
					}
				}
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 设置合同及协议报审状态为失效
	 * @param reportIds 主键
	 */
	public void invalidReport(String reportIds) {
		// 遍历报审将其设置为失效状态
		String[] arr = reportIds.split(",");
		for (int i = 0; i < arr.length; i++) {
			String reportId = arr[i];
			if (StringUtils.isNotBlank(reportId)) {
				ContractPactReport entity = dao.get(reportId);
				if (entity != null) {
					entity.setStatus(ContractPactReport.StatusCodeTable.INVALID);
					dao.update(entity);
				}
			}
		}
	}

}

