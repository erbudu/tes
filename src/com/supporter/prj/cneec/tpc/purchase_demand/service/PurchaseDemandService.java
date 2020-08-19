package com.supporter.prj.cneec.tpc.purchase_demand.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.purchase_demand.dao.PurchaseDemandCustomDao;
import com.supporter.prj.cneec.tpc.purchase_demand.dao.PurchaseDemandDao;
import com.supporter.prj.cneec.tpc.purchase_demand.dao.PurchaseDemandDetailDao;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemand;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemandCustom;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemandDetail;
import com.supporter.prj.cneec.tpc.purchase_demand_sheet.service.PurchaseDemandSheetService;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.cneec.tpc.util.TpcConstant;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: PurchaseDemandService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-09-06
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class PurchaseDemandService {

	@Autowired
	private PurchaseDemandDao purchaseDemandDao;
	@Autowired
	private PurchaseDemandCustomDao purchaseDemandCustomDao;
	@Autowired
	private PurchaseDemandDetailDao purchaseDemandDetailDao;

	@Autowired
	private PurchaseDemandSheetService purchaseDemandSheetService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, PurchaseDemand.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param purchaseDemand
	 */
	public void getAuthCanExecute(UserProfile userProfile, PurchaseDemand purchaseDemand) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, PurchaseDemand.MODULE_ID, purchaseDemand.getDemandId(), purchaseDemand);
	}

	/**
	 * 获取客户采购需求单对象集合
	 * @param user
	 * @param jqGrid
	 * @param purchaseDemand
	 * @return
	 */
	public List<PurchaseDemand> getGrid(UserProfile user, JqGrid jqGrid, PurchaseDemand purchaseDemand) {
		String authFilter = getAuthFilter(user);
		return this.purchaseDemandDao.findPage(jqGrid, purchaseDemand, authFilter);
	}

	/**
	 * 获取客户从表对象集合
	 * @param user
	 * @param jqGrid
	 * @param demandId 需求单ID
	 * @param recordId 客户表ID
	 * @return
	 */
	public List<PurchaseDemandDetail> getDetailGrid(UserProfile user, JqGrid jqGrid, String demandId, String recordId) {
		return this.purchaseDemandDetailDao.findPage(jqGrid, demandId, recordId);
	}

	/**
	 * 获取单个客户采购需求单对象
	 * @param demandId
	 * @return
	 */
	public PurchaseDemand get(String demandId) {
		return this.purchaseDemandDao.get(demandId);
	}

	/**
	 * 根据采购需求单ID获取对象集合
	 * @param demandIds
	 * @return
	 */
	public List<PurchaseDemand> getPurchaseDemandListByDemandIds(String demandIds) {
		List<PurchaseDemand> purchaseDemandList = null;
		if (StringUtils.isNotBlank(demandIds)) {
			purchaseDemandList = this.purchaseDemandDao.get(Arrays.asList(demandIds.split(",")));
			for (PurchaseDemand demand : purchaseDemandList) {
				demand.setCustomList(getCustomList(demand.getDemandId()));
			}
		}
		return purchaseDemandList;
	}
	/**
	 * 获取所有客户表
	 * @param purchaseDemand
	 * @return
	 */
	public List<PurchaseDemandCustom> getCustomList(String demandId) {
		List<PurchaseDemandCustom> customList = null;
		if (StringUtils.isNotBlank(demandId)) {
			customList = this.purchaseDemandCustomDao.getCustomListByDemandId(demandId);
		}
		return customList;
	}

	/**
	 * 获取客户下明细
	 * @param recordId
	 * @return
	 */
	public List<PurchaseDemandDetail> getDetailList(String recordId) {
		List<PurchaseDemandDetail> detailList = null;
		if (StringUtils.isNotBlank(recordId)) {
			detailList = this.purchaseDemandDetailDao.getDetailListByRecordId(recordId);
		}
		return detailList;
	}

	/**
	 * 新建客户采购需求单对象
	 * @param user
	 * @return
	 */
	public PurchaseDemand newPurchaseDemand(Map<String, Object> valueMap, UserProfile user) {
		PurchaseDemand purchaseDemand = new PurchaseDemand();
		loadPurchaseDemand(purchaseDemand, valueMap, user);
		purchaseDemand.setSwfStatus(PurchaseDemand.DRAFT);
		// 新建时如果本项目是单一客户时给主表设置客户信息
		if (valueMap != null && valueMap.containsKey("singleCategoryCode")) {
			String singleCategoryCode = valueMap.get("singleCategoryCode").toString();
			if (singleCategoryCode.equals(TpcConstant.SINGLE_CUSTOMER)) {
				String projectId = valueMap.get("projectId").toString();
				// 获取已有客户采购需求单
				List<PurchaseDemand> demandList = this.purchaseDemandDao.findBy("projectId", projectId);
				if (demandList.size() > 0) {
					PurchaseDemand demand = demandList.get(0);
					purchaseDemand.setCustomerId(demand.getCustomerId());
					purchaseDemand.setCustomerName(demand.getCustomerName());
				}
			}
		}
		return purchaseDemand;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public PurchaseDemand loadPurchaseDemand(PurchaseDemand purchaseDemand, Map<String, Object> valueMap, UserProfile user) {
		purchaseDemand.setCreatedBy(user.getName());
		purchaseDemand.setCreatedById(user.getPersonId());
		purchaseDemand.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		purchaseDemand.setModifiedBy(user.getName());
		purchaseDemand.setModifiedById(user.getPersonId());
		purchaseDemand.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			purchaseDemand.setDeptName(dept.getName());
			purchaseDemand.setDeptId(dept.getDeptId());
		}
		// 设置状态
		purchaseDemand.setSwfStatus(PurchaseDemand.DRAFT);
		return purchaseDemand;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param demandId
	 * @param user
	 * @return
	 */
	public PurchaseDemand initEditOrViewPage(String demandId, Map<String, Object> valueMap, UserProfile user) {
		PurchaseDemand purchaseDemand;
		if (StringUtils.isBlank(demandId)) {
			purchaseDemand = newPurchaseDemand(valueMap, user);
			purchaseDemand.setDemandId(UUIDHex.newId());
			purchaseDemand.setAdd(true);
		} else {
			purchaseDemand = (PurchaseDemand) this.purchaseDemandDao.get(demandId);
			purchaseDemand.setAdd(false);
			List<PurchaseDemandCustom> customList = this.getCustomList(demandId);
			purchaseDemand.setCustomList(customList);
		}
		return purchaseDemand;
	}

	/**
	 * 拷贝对象
	 * @param user
	 * @param demandIds
	 * @return
	 */
	public boolean copy(UserProfile user, String demandIds, Map<String, Object> valueMap) {
		if (StringUtils.isNotBlank(demandIds)) {
			String projectNo = (String) valueMap.get("projectNo");
			if (projectNo == null) {
				return false;
			}
			PurchaseDemand saveDemand = null;
			PurchaseDemandCustom saveCustom = null;
			PurchaseDemandDetail saveDetail = null;
			PurchaseDemand purchaseDemand;
			List<PurchaseDemandCustom> customList;
			List<PurchaseDemandDetail> detailList;
			try {
				for (String demandId : demandIds.split(",")) {
					saveDemand = new PurchaseDemand();
					purchaseDemand = this.get(demandId);
					BeanUtils.copyProperties(saveDemand, purchaseDemand);
					saveDemand.setDemandId(UUIDHex.newId());
					loadPurchaseDemand(saveDemand, valueMap, user);
					saveDemand.setBatchNo(generatorBatchNo(purchaseDemand.getProjectId(), projectNo));
					saveDemand.setSwfStatus(PurchaseDemand.DRAFT);
					this.purchaseDemandDao.save(saveDemand);
					customList = this.getCustomList(demandId);
					for (PurchaseDemandCustom custom : customList) {
						saveCustom = new PurchaseDemandCustom();
						BeanUtils.copyProperties(saveCustom, custom);
						saveCustom.setDemandId(saveDemand.getDemandId());
						this.purchaseDemandCustomDao.save(saveCustom);
						detailList = this.getDetailList(custom.getRecordId());
						for (PurchaseDemandDetail detail : detailList) {
							saveDetail = new PurchaseDemandDetail();
							BeanUtils.copyProperties(saveDetail, detail);
							saveDetail.setDemandId(saveDemand.getDemandId());
							saveDetail.setRecordId(saveCustom.getRecordId());
							this.purchaseDemandDetailDao.save(saveDetail);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("PurchaseDemandService copy Error :" + e.getMessage());
				return false;
			}
		}
		return true;
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param purchaseDemand
	 * @param valueMap
	 * @return
	 */
	public PurchaseDemand saveOrUpdate(UserProfile user, PurchaseDemand purchaseDemand, Map<String, Object> valueMap) {
		if (purchaseDemand.getAdd()) {
			// 第一次保存生成编号
			if (StringUtils.isBlank(purchaseDemand.getBatchNo())) {
				purchaseDemand.setBatchNo(generatorBatchNo(purchaseDemand.getProjectId(), valueMap.get("projectNo").toString()));
			}
			// 装配基础信息
			loadPurchaseDemand(purchaseDemand, valueMap, user);
			// 单一客户时设置客户信息至主表
			String singleCategoryCode = CommonUtil.trim(purchaseDemand.getSingleCategoryCode());
			if (singleCategoryCode.equals(TpcConstant.SINGLE_CUSTOMER) && purchaseDemand.getCustomList() != null) {
				PurchaseDemandCustom custom = purchaseDemand.getCustomList().get(0);
				purchaseDemand.setCustomerId(custom.getCustomerId());
				purchaseDemand.setCustomerName(custom.getCustomerName());
			}
			this.purchaseDemandDao.save(purchaseDemand);
		} else {
			// 设置更新时间
			purchaseDemand.setModifiedBy(user.getName());
			purchaseDemand.setModifiedById(user.getPersonId());
			purchaseDemand.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			deleteCustoms(purchaseDemand.getDelRecordIds());
			this.purchaseDemandDao.update(purchaseDemand);
		}
		// 保存或更新客户数据
		List<PurchaseDemandCustom> customList = purchaseDemand.getCustomList();
		if(CollectionUtils.isNotEmpty(customList)){
			saveOrUpdateCustomList(purchaseDemand, customList);
		}
		return purchaseDemand;
	}

	/**
	 * 保存或更新从表数据
	 * @param demandId 主表ID
	 * @param detailList 要保存的从表集合
	 */
	public void saveOrUpdateCustomList(PurchaseDemand purchaseDemand, List<PurchaseDemandCustom> customList) {
		for (PurchaseDemandCustom custom : customList) {
			custom.setDemandId(purchaseDemand.getDemandId());
			if (StringUtils.isBlank(custom.getRecordId()) || custom.getAdd()) {
				this.purchaseDemandCustomDao.save(custom);
			} else {
				deleteDetails(custom.getDelDetailIds());
				this.purchaseDemandCustomDao.update(custom);
			}
			List<PurchaseDemandDetail> detailList = custom.getDetailList();
			if (CollectionUtils.isNotEmpty(detailList)) {
				saveOrUpdateDetailList(custom, detailList);
			}
		}
	}

	/**
	 * 保存或更新从表数据
	 * @param demandId 主表ID
	 * @param detailList 要保存的从表集合
	 */
	public void saveOrUpdateDetailList(PurchaseDemandCustom custom, List<PurchaseDemandDetail> detailList) {
		for (PurchaseDemandDetail detail : detailList) {
			if (StringUtils.isBlank(detail.getDetailId())) {
				detail.setDemandId(custom.getDemandId());
				detail.setRecordId(custom.getRecordId());
				this.purchaseDemandDetailDao.save(detail);
			} else {
				this.purchaseDemandDetailDao.update(detail);
			}
		}
	}

	/**
	 * 保存提交
	 * @param user
	 * @param purchaseDemand
	 * @param valueMap
	 * @return
	 */
	public PurchaseDemand commit(UserProfile user, PurchaseDemand purchaseDemand, Map<String, Object> valueMap) {
		saveOrUpdate(user, purchaseDemand, valueMap);
		// 需求单不需要审批直接完成
		purchaseDemand.setSwfStatus(PurchaseDemand.COMPLETED);
		this.purchaseDemandDao.update(purchaseDemand);
		// 对外报价评审时待评审完成写入
		if (!purchaseDemand.isExternalReview()) {
			this.purchaseDemandSheetService.createPurchaseDemandSheetByPurchaseDemand(purchaseDemand);
		}
		// 记录日志
		//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + purchaseDemand + "}", null, null);
		return purchaseDemand;
	}

	/**
	 * 生成批次号
	 * @return
	 */
	public synchronized String generatorBatchNo(String projectId, String projectNo) {
		String batchNo = null;
		String NoHead = projectNo.substring(10);
		Integer count = this.purchaseDemandDao.getObjectCountByPropValue("projectId", projectId);
		String NoEnd = String.format("%03d", (count + 1));
		batchNo = NoHead + "-P" + NoEnd;
		return batchNo;
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param demandIds
	 */
	public void delete(UserProfile user, String demandIds) {
		if (StringUtils.isNotBlank(demandIds)) {
			PurchaseDemand purchaseDemand;
			for (String demandId : demandIds.split(",")) {
				purchaseDemand = this.get(demandId);
				if (purchaseDemand == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, purchaseDemand);
				// 删除客户表
				this.purchaseDemandCustomDao.deleteByProperty("demandId", demandId);
				// 删除客户明细表
				this.purchaseDemandDetailDao.deleteByProperty("demandId", demandId);
				// 删除主表
				this.purchaseDemandDao.delete(purchaseDemand);
			}
		}
	}

	/**
	 * 删除客户信息
	 * @param user
	 * @param demandIds
	 */
	public void deleteCustoms(String delCustomIds) {
		if (StringUtils.isNotBlank(delCustomIds)) {
			for (String recordId : delCustomIds.split(",")) {
				// 删除客户表
				this.purchaseDemandCustomDao.delete(recordId);
				// 删除客户明细表
				this.purchaseDemandDetailDao.deleteByProperty("recordId", recordId);
			}
		}
	}

	/**
	 * 删除客户明细
	 * @param delDetailIds
	 */
	public void deleteDetails(String delDetailIds) {
		if (StringUtils.isNotBlank(delDetailIds)) {
			for (String detailId : delDetailIds.split(",")) {
				// 删除从表
				this.purchaseDemandDetailDao.deleteByProperty("detailId", detailId);
			}
		}
	}

	/** 以下是选择客户采购需求方法 **/

	private List<PurchaseDemand> refinePurchaseDemand(List<PurchaseDemand> demandList) {
		if (CollectionUtils.isNotEmpty(demandList)) {
			List<PurchaseDemand> refinedLst = new ArrayList<PurchaseDemand>();
			for (PurchaseDemand demand : demandList) {
				refinedLst.add(demand);
			}
			return refinedLst;
		}
		return demandList;
	}

	public ListPage<PurchaseDemand> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<PurchaseDemand> listPage = new ListPage<PurchaseDemand>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.purchaseDemandDao.getListPage(listPage, parameters, authFilter);
		List<PurchaseDemand> refinedLst = refinePurchaseDemand(listPage.getRows());
		listPage.setRows(refinedLst);
		return listPage;
	}

}
