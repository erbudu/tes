package com.supporter.prj.cneec.tpc.purchase_demand.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.purchase_demand.dao.PurchaseDemandCustomDao;
import com.supporter.prj.cneec.tpc.purchase_demand.dao.PurchaseDemandDetailDao;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemand;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemandCustom;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemandDetail;
import com.supporter.prj.cneec.tpc.purchase_demand.entity.PurchaseDemandOrder;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.eip_service.security.entity.UserProfile;

@Service
public class PurchaseDemandOrderService {

	@Autowired
	private PurchaseDemandCustomDao purchaseDemandCustomDao;
	@Autowired
	private PurchaseDemandDetailDao purchaseDemandDetailDao;

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
	 * 拼装订单信息
	 * @param listPage
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	private List<PurchaseDemandOrder> refinePurchaseDemandOrder(ListPage<PurchaseDemandOrder> listPage, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		List<PurchaseDemandOrder> refinedList = listPage.getRows();
		List<PurchaseDemandCustom> customList = this.purchaseDemandCustomDao.getList(parameters, authFilter);
		// 是否展开（默认折叠）
		boolean expand = Boolean.valueOf(String.valueOf(parameters.get("expand")));
		PurchaseDemandOrder order;
		for (PurchaseDemandCustom custom : customList) {
			order = new PurchaseDemandOrder();
			order.setDetailId(custom.getRecordId());
			order.setRecordId(custom.getRecordId());
			order.setItemName(custom.getCustomerName());
			order.setLevel(0);
			order.setLeaf(false);
			order.setExpanded(expand);
			refinedList.add(order);
			// 获取订单货物/服务明细
			parameters.put("recordId", custom.getRecordId());
			List<PurchaseDemandDetail> detailList = this.purchaseDemandDetailDao.getList(parameters, authFilter);
			if (CollectionUtils.isNotEmpty(detailList)) {
				for (PurchaseDemandDetail detail : detailList) {
					order = new PurchaseDemandOrder();
					BeanUtils.copyProperties(order, detail);
					order.setParent(custom.getRecordId());
					refinedList.add(order);
				}
			} else {
				// 明细为空时清除该客户
				refinedList.remove(order);
			}
			listPage.setRowCount(listPage.getRowCount() + detailList.size() + 1);
		}
		if (listPage.getRowCount() > 0) {
			listPage.setRowCount(listPage.getRowCount() + 1);
		}
		return refinedList;
	}

	/**
	 * 获取订单信息
	 * @param page
	 * @param pageSize
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public ListPage<PurchaseDemandOrder> getOrderListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		ListPage<PurchaseDemandOrder> listPage = new ListPage<PurchaseDemandOrder>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);

		List<PurchaseDemandOrder> refinedList = refinePurchaseDemandOrder(listPage, parameters, userProfile);
		listPage.setRows(refinedList);
		return listPage;
	}

	/**
	 * 获取订单信息
	 * @param page
	 * @param pageSize
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public ListPage<PurchaseDemandOrder> getPurchaseDemandOrderListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<PurchaseDemandOrder> listPage = new ListPage<PurchaseDemandOrder>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);

		List<PurchaseDemandOrder> refinedList = new ArrayList<PurchaseDemandOrder>();

		ListPage<PurchaseDemandCustom> customs = new ListPage<PurchaseDemandCustom>();
		customs.setPageNo(page);
		customs.setPageSize(pageSize);
		customs.setAutoCount(true);
		customs = this.purchaseDemandCustomDao.getListPage(customs, parameters, authFilter);
		List<PurchaseDemandCustom> customList = customs.getRows();
		// 是否展开（默认折叠）
		boolean expand = Boolean.valueOf(String.valueOf(parameters.get("expand")));
		PurchaseDemandOrder order;
		for (PurchaseDemandCustom custom : customList) {
			// list中记录等于需求记录时不再添加
			if (refinedList.size() == pageSize) {
				break;
			}
			order = new PurchaseDemandOrder();
			order.setDetailId(custom.getRecordId());
			order.setRecordId(custom.getRecordId());
			order.setItemName(custom.getCustomerName());
			order.setLevel(0);
			order.setLeaf(false);
			order.setExpanded(expand);
			refinedList.add(order);
			// 获取订单货物/服务明细
			parameters.put("recordId", custom.getRecordId());
			ListPage<PurchaseDemandDetail> details = new ListPage<PurchaseDemandDetail>();
			details.setPageNo(page);
			details.setPageSize(pageSize - refinedList.size());// 可添加从表记录只能为总可添加记录减去已添加记录
			details.setAutoCount(true);
			details = this.purchaseDemandDetailDao.getListPage(details, parameters, authFilter);
			List<PurchaseDemandDetail> detailList = details.getRows();
			if (CollectionUtils.isNotEmpty(detailList)) {
				for (PurchaseDemandDetail detail : detailList) {
					order = new PurchaseDemandOrder();
					BeanUtils.copyProperties(order, detail);
					order.setParent(custom.getRecordId());
					refinedList.add(order);
				}
			} else {
				// 明细为空时清除该客户
				refinedList.remove(order);
			}
			listPage.setRowCount(listPage.getRowCount() + detailList.size() + 1);
		}
		if (listPage.getRowCount() > 0) {
			listPage.setRowCount(listPage.getRowCount() + 1);
		}
		listPage.setRows(refinedList);
		return listPage;
	}

}
