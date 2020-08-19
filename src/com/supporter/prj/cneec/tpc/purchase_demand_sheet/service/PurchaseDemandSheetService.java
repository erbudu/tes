package com.supporter.prj.cneec.tpc.purchase_demand_sheet.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemand;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemandCustom;
import com.supporter.prj.cneec.tpc.purchase_demand.service.PurchaseDemandService;
import com.supporter.prj.cneec.tpc.purchase_demand_sheet.dao.PurchaseDemandSheetDao;
import com.supporter.prj.cneec.tpc.purchase_demand_sheet.entity.PurchaseDemandSheet;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: PurchaseDemandSheetService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2018-04-10
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class PurchaseDemandSheetService {

	@Autowired
	private PurchaseDemandSheetDao purchaseDemandSheetDao;
	@Autowired
	private PurchaseDemandService purchaseDemandService;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, PurchaseDemandSheet.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param purchaseDemandSheet
	 */
	public void getAuthCanExecute(UserProfile userProfile, PurchaseDemandSheet purchaseDemandSheet) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, PurchaseDemandSheet.MODULE_ID, purchaseDemandSheet.getSheetId(), purchaseDemandSheet);
	}

	/**
	 * 获取客户采购需求选择对象集合
	 * @param user
	 * @param jqGrid
	 * @param purchaseDemandSheet
	 * @return
	 */
	public List<PurchaseDemandSheet> getGrid(UserProfile user, JqGrid jqGrid, PurchaseDemandSheet purchaseDemandSheet) {
		String authFilter = getAuthFilter(user);
		return this.purchaseDemandSheetDao.findPage(jqGrid, purchaseDemandSheet, authFilter);
	}

	/**
	 * 选择列表记录
	 * @param page
	 * @param pageSize
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public ListPage<PurchaseDemandSheet> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<PurchaseDemandSheet> listPage = new ListPage<PurchaseDemandSheet>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.purchaseDemandSheetDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

	/**
	 * 获取单个客户采购需求选择对象
	 * @param sheetId
	 * @return
	 */
	public PurchaseDemandSheet get(String sheetId) {
		return this.purchaseDemandSheetDao.get(sheetId);
	}

	/**
	 * 获取客户采购需求选择对象集合
	 * @param sheetId
	 * @return
	 */
	public List<PurchaseDemandSheet> getListBySheetIds(String sheetIds) {
		List<String> ids =  Arrays.asList(sheetIds.split(","));
		return this.purchaseDemandSheetDao.get(ids);
	}

	/**
	 * 根据包ID获取客户采购需求选择对象集合
	 * @param recordIds
	 * @return
	 */
	public List<PurchaseDemandSheet> getListByRecordIds(String recordIds) {
		return this.purchaseDemandSheetDao.getListByFields("recordId", recordIds);
	}

	/**
	 * 根据参数过滤对象
	 * @param sheetIds
	 * @return
	 */
	public List<PurchaseDemandSheet> getList(Map<String, Object> params) {
		return this.purchaseDemandSheetDao.find(params);
	}

	/**
	 * 根据客户采购需求单创建选择单
	 * @return
	 */
	public void createPurchaseDemandSheetByDemandId(String demandId) {
		PurchaseDemand purchaseDemand = purchaseDemandService.get(demandId);
		if (purchaseDemand != null) {
			createPurchaseDemandSheetByPurchaseDemand(purchaseDemand);
		}
	}

	/**
	 * 根据客户采购需求单创建选择单
	 * @return
	 */
	public void createPurchaseDemandSheetByPurchaseDemand(PurchaseDemand purchaseDemand) {
		List<PurchaseDemandSheet> sheetList = new ArrayList<PurchaseDemandSheet>();
		PurchaseDemandSheet purchaseDemandSheet;
		try {
			List<PurchaseDemandCustom> customList = this.purchaseDemandService.getCustomList(purchaseDemand.getDemandId());
			if (customList != null) {
				// 单一客户与（customList.size为1）单一供方区别只在于客户采购需求单下客户是否唯一
				for (PurchaseDemandCustom demandCustom : customList) {
					purchaseDemandSheet = new PurchaseDemandSheet();
					BeanUtils.copyProperties(purchaseDemandSheet, purchaseDemand);
					BeanUtils.copyProperties(purchaseDemandSheet, demandCustom);
					purchaseDemandSheet.setReviewStatus(PurchaseDemandSheet.NO_REVIEW);
					sheetList.add(purchaseDemandSheet);
				}
			}
		} catch (Exception e) {
			System.err.println("createPurchaseDemandSheetByPurchaseDemand:" + e.getMessage());
		}
		if (sheetList.size() > 0) {
			this.purchaseDemandSheetDao.save(sheetList);
		}
	}

	/**
	 * 批量修改选择单评审状态
	 * @param sheetIds
	 */
	public void updateReviewStatusByRecordIds(String recordIds, int reviewStatus) {
		Map<String, Object> values = new LinkedHashMap<String, Object>();
		values.put("reviewStatus", reviewStatus);
		this.purchaseDemandSheetDao.updateReview(values, null, recordIds);
	}

	/**
	 * 批量修改选择单评审状态
	 * @param sheetIds
	 */
	public void updateReviewStatusBySheetIds(String sheetIds, int reviewStatus) {
		Map<String, Object> values = new LinkedHashMap<String, Object>();
		values.put("reviewStatus", reviewStatus);
		this.purchaseDemandSheetDao.updateReview(values, sheetIds, null);
	}

	/**
	 * 批量修改选择单评审状态(完成时)
	 * @param sheetIds
	 */
	public void updateReviewStatusReviewedByRecordIds(String recordIds, String reviewConclusion) {
		Map<String, Object> values = new LinkedHashMap<String, Object>();
		values.put("reviewStatus", PurchaseDemandSheet.REVIEWED);
		values.put("reviewConclusion", reviewConclusion);
		this.purchaseDemandSheetDao.updateReview(values, null, recordIds);
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param sheetIds
	 */
	public void delete(UserProfile user, String sheetIds) {
		if (StringUtils.isNotBlank(sheetIds)) {
			PurchaseDemandSheet purchaseDemandSheet;
			for (String sheetId : sheetIds.split(",")) {
				purchaseDemandSheet = this.get(sheetId);
				if (purchaseDemandSheet == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, purchaseDemandSheet);
				this.purchaseDemandSheetDao.delete(purchaseDemandSheet);
			}
		}
	}

}
