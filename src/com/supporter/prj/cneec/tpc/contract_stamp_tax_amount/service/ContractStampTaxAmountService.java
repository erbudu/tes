package com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractOrder;
import com.supporter.prj.cneec.tpc.contract_online.entity.ContractOnline;
import com.supporter.prj.cneec.tpc.contract_online.service.ContractOnlineService;
import com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.dao.ContractStampTaxAmountDao;
import com.supporter.prj.cneec.tpc.contract_stamp_tax_amount.entity.ContractStampTaxAmount;
import com.supporter.prj.cneec.tpc.derivative_contract_change.entity.DerivativeConChange;
import com.supporter.prj.cneec.tpc.derivative_contract_online.entity.DerivativeContractOnline;
import com.supporter.prj.cneec.tpc.derivative_contract_online.service.DerivativeContractOnlineService;
import com.supporter.prj.cneec.tpc.order_change.entity.ContractChOrder;
import com.supporter.prj.cneec.tpc.order_online.entity.OrderOnline;
import com.supporter.prj.cneec.tpc.order_online.service.OrderOnlineService;
import com.supporter.prj.cneec.tpc.prj_contract_table.entity.PrjContractTable;
import com.supporter.prj.cneec.tpc.prj_contract_table.service.PrjContractTableService;
import com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.entity.StampTaxItem;
import com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.service.StampTaxItemService;

/**
 * @Title: Service
 * @Description: 贸易印花税金额表.
 * @author LEGO
 * @date 2020-02-03 13:12:36
 * @version V1.0
 */
@Service
public class ContractStampTaxAmountService {

	@Autowired
	private ContractStampTaxAmountDao contractStampTaxAmountDao;
	
	@Autowired
	private StampTaxItemService itemService;
	
	@Autowired
	private OrderOnlineService onlineService;
	
	@Autowired
	private ContractOnlineService contractOnlineService;
	
	@Autowired
	private DerivativeContractOnlineService derivativeContractOnlineService;
	
	@Autowired
	private PrjContractTableService contractTableService;

	/**
	 * 根据主键获取贸易印花税金额表.
	 * 
	 * @param stampAmountId 主键
	 * @return ContractStampTaxAmount
	 */
	public ContractStampTaxAmount get(String stampAmountId) {
		return contractStampTaxAmountDao.get(stampAmountId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractStampTaxAmount > getGrid(UserProfile user, JqGrid jqGrid, ContractStampTaxAmount contractStampTaxAmount) {
		return contractStampTaxAmountDao.findPage(jqGrid, contractStampTaxAmount);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param stampAmountId
	 * @return
	 */
	public ContractStampTaxAmount initEditOrViewPage(String stampAmountId) {
		if (StringUtils.isBlank(stampAmountId)) {// 新建
			ContractStampTaxAmount entity = new ContractStampTaxAmount();
			return entity;
		} else {// 编辑
			ContractStampTaxAmount entity = contractStampTaxAmountDao.get(stampAmountId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractStampTaxAmount 实体类
	 * @return
	 */
	public ContractStampTaxAmount saveOrUpdate(UserProfile user, ContractStampTaxAmount contractStampTaxAmount) {
		if (StringUtils.isBlank(contractStampTaxAmount.getStampAmountId())) {// 新建
			return this.save(user, contractStampTaxAmount);
		} else {// 编辑
			return this.update(user, contractStampTaxAmount);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractStampTaxAmount 实体类
	 * @return
	 */
	private ContractStampTaxAmount save(UserProfile user, ContractStampTaxAmount contractStampTaxAmount) {
		this.contractStampTaxAmountDao.save(contractStampTaxAmount);
		return contractStampTaxAmount;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractStampTaxAmount 实体类
	 * @return
	 */
	private ContractStampTaxAmount update(UserProfile user, ContractStampTaxAmount contractStampTaxAmount) {
		ContractStampTaxAmount contractStampTaxAmountDb = this.contractStampTaxAmountDao.get(contractStampTaxAmount.getStampAmountId());
		if(contractStampTaxAmountDb == null) {
			return this.save(user, contractStampTaxAmount);
		}else{
			this.contractStampTaxAmountDao.update(contractStampTaxAmount);
			return contractStampTaxAmount;
		}
		
	}
	
	/**
	 * 创建销售合同印花税金额
	 * @param order
	 * @return
	 */
	public ContractStampTaxAmount createOrderStampTaxAmount(OrderOnline order, PrjContractTable table) {
		ContractStampTaxAmount amount = new ContractStampTaxAmount();
		amount.setConfirmDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		amount.setConfirmId(order.getOrderId());
		amount.setContractAmount(order.getContractRmbAmount());
		amount.setContractId(table.getContractId());
		amount.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		amount.setStampTaxItemId(order.getTaxItemId());
		double rate = 0;
		StampTaxItem item = itemService.get(amount.getStampTaxItemId());
		if(item != null) rate = item.getTaxRate();
		amount.setStampAmount(BigDecimal.valueOf(amount.getContractAmount()).multiply(BigDecimal.valueOf(rate)).doubleValue());
		this.save(null, amount);
		return amount;
	}
	
	/**
	 * 通过销售合同上线创建印花税金额
	 * @param orderId
	 * @param taxItemId
	 * @return
	 */
	public boolean createOrderStampTaxAmountByOnline(String orderId, String taxItemId) {
		boolean result = false;
		if (StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(taxItemId)) {
			StampTaxItem item = this.itemService.get(taxItemId);
			OrderOnline order = this.onlineService.get(orderId);
			if (item != null && order != null) {
				order.setTaxItemId(taxItemId);
				order.setTaxItemName(item.getTaxItemName());
				this.onlineService.update(order);
				
				PrjContractTable table = this.contractTableService.get(order.getOrderId());
				if (table != null) {
					table.setTaxItemId(taxItemId);
					table.setTaxItemName(item.getTaxItemName());
					this.contractTableService.update(table);
					
					double rate = item.getTaxRate();
					if (rate > 0) {
						ContractStampTaxAmount amount = new ContractStampTaxAmount();
						amount.setConfirmDate(table.getCreatedDate());
						amount.setConfirmId(order.getOrderId());
						amount.setContractAmount(order.getContractRmbAmount());
						amount.setContractId(table.getContractId());
						amount.setCreatedDate(table.getCreatedDate());
						amount.setStampTaxItemId(order.getTaxItemId());
						amount.setStampAmount(BigDecimal.valueOf(amount.getContractAmount()).multiply(BigDecimal.valueOf(rate)).doubleValue());
						this.save(null, amount);
					}
					result = true;
				}else {
					System.out.println("获取合同失败！");
				}
			}else {
				System.out.println("印花税税目或上线为空！");
			}
		}else {
			System.out.println("无效的参数！");
		}
		return result;
	}
	
	/**
	 * 创建采购合同印花税金额
	 * @param contract
	 * @return
	 */
	public ContractStampTaxAmount createContractStampTaxAmount(ContractOnline contract, PrjContractTable table) {
		ContractStampTaxAmount amount = new ContractStampTaxAmount();
		amount.setConfirmDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		amount.setConfirmId(contract.getContractId());
		amount.setContractAmount(contract.getContractRmbAmount());
		amount.setContractId(table.getContractId());
		amount.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		amount.setStampTaxItemId(contract.getTaxItemId());
		double rate = 0;
		StampTaxItem item = itemService.get(amount.getStampTaxItemId());
		if(item != null) rate = item.getTaxRate();
		amount.setStampAmount(BigDecimal.valueOf(amount.getContractAmount()).multiply(BigDecimal.valueOf(rate)).doubleValue());
		this.save(null, amount);
		return amount;
	}
	
	/**
	 * 通过采购合同上线创建印花税金额
	 * @param contractId
	 * @param taxItemId
	 * @return
	 */
	public boolean createContractStampTaxAmountByOnline(String contractId, String taxItemId) {
		boolean result = false;
		if (StringUtils.isNotBlank(contractId) && StringUtils.isNotBlank(taxItemId)) {
			StampTaxItem item = this.itemService.get(taxItemId);
			ContractOnline contract = this.contractOnlineService.get(contractId);
			if (item != null && contract != null) {
				contract.setTaxItemId(taxItemId);
				contract.setTaxItemName(item.getTaxItemName());
				this.contractOnlineService.update(contract);
				
				PrjContractTable table = this.contractTableService.get(contract.getContractId());
				if (table != null) {
					table.setTaxItemId(taxItemId);
					table.setTaxItemName(item.getTaxItemName());
					this.contractTableService.update(table);
					
					double rate = item.getTaxRate();
					if (rate > 0) {
						ContractStampTaxAmount amount = new ContractStampTaxAmount();
						amount.setConfirmDate(table.getCreatedDate());
						amount.setConfirmId(contract.getContractId());
						amount.setContractAmount(contract.getContractRmbAmount());
						amount.setContractId(table.getContractId());
						amount.setCreatedDate(table.getCreatedDate());
						amount.setStampTaxItemId(contract.getTaxItemId());
						amount.setStampAmount(BigDecimal.valueOf(amount.getContractAmount()).multiply(BigDecimal.valueOf(rate)).doubleValue());
						this.save(null, amount);
					}
					result = true;
				}else {
					System.out.println("获取合同失败！");
				}
			}else {
				System.out.println("印花税税目或上线为空！");
			}
		}else {
			System.out.println("无效的参数！");
		}
		return result;
	}
	
	/**
	 * 创建衍生合同印花税金额
	 * @param derivative
	 * @return
	 */
	public ContractStampTaxAmount createDerivativeStampTaxAmount(DerivativeContractOnline derivative, PrjContractTable table) {
		ContractStampTaxAmount amount = new ContractStampTaxAmount();
		amount.setConfirmDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		amount.setConfirmId(derivative.getContractId());
		amount.setContractAmount(derivative.getContractRmbAmount());
		amount.setContractId(table.getContractId());
		amount.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		amount.setStampTaxItemId(derivative.getTaxItemId());
		double rate = 0;
		StampTaxItem item = itemService.get(amount.getStampTaxItemId());
		if(item != null) rate = item.getTaxRate();
		amount.setStampAmount(BigDecimal.valueOf(amount.getContractAmount()).multiply(BigDecimal.valueOf(rate)).doubleValue());
		this.save(null, amount);
		return amount;
	}
	
	/**
	 * 通过衍生合同上线创建印花税金额
	 * @param orderId
	 * @param taxItemId
	 * @return
	 */
	public boolean createDerivativeStampTaxAmountByOnline(String contractId, String taxItemId) {
		boolean result = false;
		if (StringUtils.isNotBlank(contractId) && StringUtils.isNotBlank(taxItemId)) {
			StampTaxItem item = this.itemService.get(taxItemId);
			DerivativeContractOnline derivative = this.derivativeContractOnlineService.get(contractId);
			if (item != null && derivative != null) {
				derivative.setTaxItemId(taxItemId);
				derivative.setTaxItemName(item.getTaxItemName());
				this.derivativeContractOnlineService.update(derivative);
				
				PrjContractTable table = this.contractTableService.get(derivative.getContractId());
				if (table != null) {
					table.setTaxItemId(taxItemId);
					table.setTaxItemName(item.getTaxItemName());
					this.contractTableService.update(table);
					
					double rate = item.getTaxRate();
					if (rate > 0) {
						ContractStampTaxAmount amount = new ContractStampTaxAmount();
						amount.setConfirmDate(table.getCreatedDate());
						amount.setConfirmId(derivative.getContractId());
						amount.setContractAmount(derivative.getContractRmbAmount());
						amount.setContractId(table.getContractId());
						amount.setCreatedDate(table.getCreatedDate());
						amount.setStampTaxItemId(derivative.getTaxItemId());
						amount.setStampAmount(BigDecimal.valueOf(amount.getContractAmount()).multiply(BigDecimal.valueOf(rate)).doubleValue());
						this.save(null, amount);
					}
					result = true;
				}else {
					System.out.println("获取合同失败！");
				}
			}else {
				System.out.println("印花税税目或上线为空！");
			}
		}else {
			System.out.println("无效的参数！");
		}
		return result;
	}
	
	/**
	 * 销售合同变更上线完成后创建印花税金额
	 * @param contractChOrder
	 * @param table
	 * @return
	 */
	public ContractStampTaxAmount completedOrderChange(ContractChOrder contractChOrder ,PrjContractTable table) {
		double afterAmount = contractChOrder.getContractRmbAmount();//获取变更后金额
		double beforAmount = table.getContractRmbAmount();//获取变更前金额
		double diffAmount = BigDecimal.valueOf(afterAmount).subtract(BigDecimal.valueOf(beforAmount)).doubleValue();
		System.out.println("afterAmount:"+afterAmount);
		System.out.println("beforAmount:"+beforAmount);
		System.out.println("diffAmount:"+diffAmount);
		
		if(diffAmount <= 0) return null;//减额或金额不变的直接退出
		
		ContractStampTaxAmount amount = new ContractStampTaxAmount();
		amount.setContractId(table.getContractId());
		amount.setChangeId(contractChOrder.getChangeId());
		amount.setConfirmDate(CommonUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		amount.setStampTaxItemId(table.getTaxItemId());
		amount.setCreatedDate(CommonUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		StampTaxItem item = null;
		double rate = 0;
		if(StringUtils.isNotBlank(amount.getStampTaxItemId())){
			item = itemService.get(amount.getStampTaxItemId());
			if(item != null) rate = item.getTaxRate();
		}
		amount.setContractAmount(diffAmount);
		amount.setStampAmount(BigDecimal.valueOf(diffAmount).multiply(BigDecimal.valueOf(rate)).doubleValue());
		this.save(null, amount);
		return amount;
	}
	
	/**
	 * 采购合同变更上线完成后创建印花税金额
	 * @param contractChOrder
	 * @param table
	 * @return
	 */
	public ContractStampTaxAmount completedContractChange(ContractOrder contractChOrder, PrjContractTable table) {
		double afterAmount = contractChOrder.getContractRmbAmount();//获取变更后金额
		double beforAmount = table.getContractRmbAmount();//获取变更前金额
		double diffAmount = BigDecimal.valueOf(afterAmount).subtract(BigDecimal.valueOf(beforAmount)).doubleValue();
		System.out.println("afterAmount:"+afterAmount);
		System.out.println("beforAmount:"+beforAmount);
		System.out.println("diffAmount:"+diffAmount);
		
		if(diffAmount <= 0) return null;//减额或金额不变的直接退出
		
		ContractStampTaxAmount amount = new ContractStampTaxAmount();
		amount.setContractId(table.getContractId());
		amount.setChangeId(contractChOrder.getChangeId());
		amount.setConfirmDate(CommonUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		amount.setStampTaxItemId(table.getTaxItemId());
		amount.setCreatedDate(CommonUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		StampTaxItem item = null;
		double rate = 0;
		if(StringUtils.isNotBlank(amount.getStampTaxItemId())){
			item = itemService.get(amount.getStampTaxItemId());
			if(item != null) rate = item.getTaxRate();
		}
		amount.setContractAmount(diffAmount);
		amount.setStampAmount(BigDecimal.valueOf(diffAmount).multiply(BigDecimal.valueOf(rate)).doubleValue());
		this.save(null, amount);
		return amount;
	}
	
	/**
	 * 衍生合同变更完成后创建印花税金额
	 * @param contractChOrder
	 * @param table
	 * @return
	 */
	public ContractStampTaxAmount completedDerivativeChange(DerivativeConChange contractChOrder ,PrjContractTable table) {
		double afterAmount = contractChOrder.getContractRmbAmount();//获取变更后金额
		double beforAmount = table.getContractRmbAmount();//获取变更前金额
		double diffAmount = BigDecimal.valueOf(afterAmount).subtract(BigDecimal.valueOf(beforAmount)).doubleValue();
		System.out.println("afterAmount:"+afterAmount);
		System.out.println("beforAmount:"+beforAmount);
		System.out.println("diffAmount:"+diffAmount);
		
		if(diffAmount <= 0) return null;//减额或金额不变的直接退出
		
		ContractStampTaxAmount amount = new ContractStampTaxAmount();
		amount.setContractId(table.getContractId());
		amount.setChangeId(contractChOrder.getChangeId());
		amount.setConfirmDate(CommonUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		amount.setStampTaxItemId(table.getTaxItemId());
		amount.setCreatedDate(CommonUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
		StampTaxItem item = null;
		double rate = 0;
		if(StringUtils.isNotBlank(amount.getStampTaxItemId())){
			item = itemService.get(amount.getStampTaxItemId());
			if(item != null) rate = item.getTaxRate();
		}
		amount.setContractAmount(diffAmount);
		amount.setStampAmount(BigDecimal.valueOf(diffAmount).multiply(BigDecimal.valueOf(rate)).doubleValue());
		this.save(null, amount);
		return amount;
	}

}

