package com.supporter.prj.cneec.tpc.project_goods_bill.service;

import java.util.ArrayList;
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
import com.supporter.prj.cneec.tpc.order_change.entity.ContractChOrder;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderChangeGoods;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderGoods;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderOnline;
import com.supporter.prj.cneec.tpc.project_goods_bill.dao.ProjectGoodsBillDao;
import com.supporter.prj.cneec.tpc.project_goods_bill.entity.ProjectGoodsBill;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

/**
 * @Title: ProjectGoodsBillService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-11-08
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class ProjectGoodsBillService {

	@Autowired
	private ProjectGoodsBillDao projectGoodsBillDao;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, ProjectGoodsBill.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param projectGoodsBill
	 */
	public void getAuthCanExecute(UserProfile userProfile, ProjectGoodsBill projectGoodsBill) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, ProjectGoodsBill.MODULE_ID, projectGoodsBill.getBillId(), projectGoodsBill);
	}

	/**
	 * 获取项目货品清单对象集合
	 * @param user
	 * @param jqGrid
	 * @param projectGoodsBill
	 * @return
	 */
	public List<ProjectGoodsBill> getGrid(UserProfile user, JqGrid jqGrid, ProjectGoodsBill projectGoodsBill) {
		String authFilter = getAuthFilter(user);
		return this.projectGoodsBillDao.findPage(jqGrid, projectGoodsBill, authFilter);
	}

	/**
	 * 获取单个项目货品清单对象
	 * @param billId
	 * @return
	 */
	public ProjectGoodsBill get(String billId) {
		return this.projectGoodsBillDao.get(billId);
	}

	/**
	 * 新建项目货品清单对象
	 * @param user
	 * @return
	 */
	public ProjectGoodsBill newProjectGoodsBill(UserProfile user) {
		ProjectGoodsBill projectGoodsBill = new ProjectGoodsBill();
		loadProjectGoodsBill(projectGoodsBill, user);
		projectGoodsBill.setBillStatus(ProjectGoodsBill.EFFECT);
		return projectGoodsBill;
	}

	/**
	 * 添加基础信息
	 * @return
	 */
	public ProjectGoodsBill loadProjectGoodsBill(ProjectGoodsBill projectGoodsBill, UserProfile user) {
		projectGoodsBill.setCreatedBy(user.getName());
		projectGoodsBill.setCreatedById(user.getPersonId());
		projectGoodsBill.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		projectGoodsBill.setModifiedBy(user.getName());
		projectGoodsBill.setModifiedById(user.getPersonId());
		projectGoodsBill.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Dept dept = user.getDept();
		if (dept != null) {
			projectGoodsBill.setDeptName(dept.getName());
			projectGoodsBill.setDeptId(dept.getDeptId());
		}
		// 设置状态
		projectGoodsBill.setBillStatus(ProjectGoodsBill.EFFECT);
		return projectGoodsBill;
	}

	/**
	 * 为编辑或查看页面初始化对象
	 * @param billId
	 * @param user
	 * @return
	 */
	public ProjectGoodsBill initEditOrViewPage(String billId, UserProfile user) {
		ProjectGoodsBill projectGoodsBill;
		if (StringUtils.isBlank(billId)) {
			projectGoodsBill = newProjectGoodsBill(user);
			projectGoodsBill.setBillId(UUIDHex.newId());
			projectGoodsBill.setAdd(true);
		} else {
			projectGoodsBill = (ProjectGoodsBill) this.projectGoodsBillDao.get(billId);
			projectGoodsBill.setAdd(false);
		}
		return projectGoodsBill;
	}

	/**
	 * 销售合同上线完成后初始化项目货品清单
	 */
	public void saveProjectGoodsBillByOrderOnline(OrderOnline orderOnline) {
		List<ProjectGoodsBill> billList = new ArrayList<ProjectGoodsBill>();
		ProjectGoodsBill projectGoodsBill;
		List<OrderGoods> goodsList = orderOnline.getGoodsList();
		if (CollectionUtils.isNotEmpty(goodsList)) {
			for (OrderGoods goods : goodsList) {
				projectGoodsBill = new ProjectGoodsBill();
				try {
					// 复制销售合同上线信息至项目货品清单对象
					BeanUtils.copyProperties(projectGoodsBill, orderOnline);
					projectGoodsBill.setContractId(orderOnline.getOrderId());
					projectGoodsBill.setContractNo(orderOnline.getOrderNo());
					projectGoodsBill.setContractName(orderOnline.getOrderName());
					// 复制销售合同上线货品及服务信息至项目货品清单对象
					BeanUtils.copyProperties(projectGoodsBill, goods);
					// 设置独有属性
					projectGoodsBill.setGoodsSource(ProjectGoodsBill.GOODS_SOURCE_ORDER_ONLINE);
					// 设置状态
					projectGoodsBill.setBillStatus(ProjectGoodsBill.EFFECT);
					billList.add(projectGoodsBill);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 批量保存
			if (billList.size() > 0) {
				this.projectGoodsBillDao.save(billList);
			}
		}
	}

	/**
	 * 销售合同变更完成后初始化项目货品清单
	 */
	public void saveProjectGoodsBillByOrderChange(ContractChOrder contractChOrder) {
		// 删除销售合同上线时保存的项目货品信息
		this.projectGoodsBillDao.deleteByProperty("contractId", contractChOrder.getChId());
		List<ProjectGoodsBill> billList = new ArrayList<ProjectGoodsBill>();
		ProjectGoodsBill projectGoodsBill;
		List<OrderChangeGoods> goodsList = contractChOrder.getGoodsList();
		if (CollectionUtils.isNotEmpty(goodsList)) {
			for (OrderChangeGoods goods : goodsList) {
				projectGoodsBill = new ProjectGoodsBill();
				try {
					// 复制销售合同变更信息至项目货品清单对象
					BeanUtils.copyProperties(projectGoodsBill, contractChOrder);
					projectGoodsBill.setContractId(contractChOrder.getContractId());
					projectGoodsBill.setContractNo(contractChOrder.getContractNo());
					projectGoodsBill.setContractName(contractChOrder.getContractName());
					// 复制销售合同变更货品及服务信息至项目货品清单对象
					BeanUtils.copyProperties(projectGoodsBill, goods);
					// 设置独有属性
					projectGoodsBill.setGoodsSource(ProjectGoodsBill.GOODS_SOURCE_ORDER_CHANGE);
					// 设置状态
					projectGoodsBill.setBillStatus(ProjectGoodsBill.EFFECT);
					billList.add(projectGoodsBill);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 批量保存
			if (billList.size() > 0) {
				this.projectGoodsBillDao.save(billList);
			}
		}
	}

	/**
	 * 保存或修改对象
	 * @param user
	 * @param projectGoodsBill
	 * @param valueMap
	 * @return
	 */
	public ProjectGoodsBill saveOrUpdate(UserProfile user, ProjectGoodsBill projectGoodsBill, Map<String, Object> valueMap) {
		if (projectGoodsBill.getAdd()) {
			// 装配基础信息
			loadProjectGoodsBill(projectGoodsBill, user);
			this.projectGoodsBillDao.save(projectGoodsBill);
		} else {
			// 设置更新时间
			projectGoodsBill.setModifiedBy(user.getName());
			projectGoodsBill.setModifiedById(user.getPersonId());
			projectGoodsBill.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.projectGoodsBillDao.update(projectGoodsBill);
		}
		return projectGoodsBill;
	}

	/**
	 * 批量删除对象
	 * @param user
	 * @param billIds
	 */
	public void delete(UserProfile user, String billIds) {
		if (StringUtils.isNotBlank(billIds)) {
			ProjectGoodsBill projectGoodsBill;
			for (String billId : billIds.split(",")) {
				projectGoodsBill = this.get(billId);
				if (projectGoodsBill == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, projectGoodsBill);
				this.projectGoodsBillDao.delete(projectGoodsBill);
			}
		}
	}

}
