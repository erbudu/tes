package com.supporter.prj.cneec.tpc.invoice_no_contract.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.invoice_no_contract.constant.InvoiceNoContractConstant;
import com.supporter.prj.cneec.tpc.invoice_no_contract.dao.InvoiceNoContractRecDao;
import com.supporter.prj.cneec.tpc.invoice_no_contract.entity.InvoiceNoContractRecEntity;
import com.supporter.prj.cneec.tpc.prj_settlement.entity.PrjSettlement;
import com.supporter.prj.cneec.tpc.prj_settlement.service.PrjSettlementService;
import com.supporter.prj.cneec.tpc.tariff_vat_payment.entity.TariffVatPayment;
import com.supporter.prj.cneec.tpc.tariff_vat_payment.service.TariffVatPaymentService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.UUIDHex;

/**
 * 非合同付款收发票明细表Service
 * InvoiceNoContractRecService
 * @author CHENHAO
 *
 */

@Service
public class InvoiceNoContractRecService {

	@Autowired
	InvoiceNoContractRecDao dao;
	
	@Autowired
	InvoiceNoContractService service;
	
	@Autowired
	TariffVatPaymentService tariffService;
	
	@Autowired
	PrjSettlementService settlementService;
	
	/**
	 * 获取分页的明细表
	 * @param jqGrid
	 * @param invoiceId
	 * @return
	 */
	public List<InvoiceNoContractRecEntity> getGrid(String invoiceId) {
		
		if(StringUtils.isBlank(invoiceId)) {
			return null;
		}
		List<InvoiceNoContractRecEntity> list = dao.findBy("invoiceId", invoiceId);
		//用于判断该单子是来自哪个功能的
		for(InvoiceNoContractRecEntity entity : list) {
			TariffVatPayment tariff = tariffService.get(entity.getPaymentId());
			entity.setPaymentType(tariff != null ? InvoiceNoContractConstant.TARIFF : InvoiceNoContractConstant.SETTLEMENT); 
		}
		return list;
	}

	/**
	 * 返回固定格式的单号
	 * @return
	 */
	public List<Map<String, Object>> getPaymentNo(String invoiceId, String recordId, String prjId){
		if(StringUtils.isBlank(prjId) || StringUtils.isBlank(recordId) || StringUtils.isBlank(invoiceId)) {
			return null;
		}
		List<String> paymentIds = dao.getPaymentNo(invoiceId, recordId, prjId);
		//分别获取两个功能完成审批的可用单子
		List<PrjSettlement> settlements = settlementService.getComplete(prjId, paymentIds);
		List<TariffVatPayment> tariffs = tariffService.getComplete(prjId, paymentIds);
		
		//用于返回固定格式的单号
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> settlementMap = new HashMap<String, Object>();
		List<Map<String, String>> settlementGroup = new ArrayList<Map<String, String>>();
		Map<String, Object> tariffMap = new HashMap<String, Object>();
		List<Map<String, String>> tariffGroup = new ArrayList<Map<String, String>>();
		
		
		//将单号分别存入各自的list
		for(PrjSettlement settlement : settlements) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", settlement.getSettlementId());
			map.put("name", settlement.getSettlementNo());
			settlementGroup.add(map);
		}
		for(TariffVatPayment tariff : tariffs) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", tariff.getPaymentId());
			map.put("name", tariff.getPaymentNo());
			tariffGroup.add(map);
		}
		//将单号存入result
		settlementMap.put("label", InvoiceNoContractConstant.SETTLEMENT);
		settlementMap.put("group", settlementGroup);
		tariffMap.put("label", InvoiceNoContractConstant.TARIFF);
		tariffMap.put("group", tariffGroup);
		result.add(settlementMap);
		result.add(tariffMap);
		
		return result;
	}
	/**
	 * 初始化页面数据
	 * @param paymentNo
	 * @param noType
	 * @return
	 */
	public InvoiceNoContractRecEntity initPage(InvoiceNoContractRecEntity entity) {
		
		//新建时执行
		if(StringUtils.isBlank(entity.getRecordId())) {
			entity.setRecordId(UUIDHex.newId());
			entity.setActualPaymentAmount(0.0);
			entity.setSumReceivedAmount(0.0);
			entity.setPaymentAmount(0.0);
			entity.setIsEndUp(0);
			return entity;
		}
		//如果是进入编辑或查看页面时执行
		if(StringUtils.isBlank(entity.getPaymentId())) {
			//查看页面不需要再次更改累计金额和付款状态
			if(entity.getIsView()) {
				return dao.get(entity.getRecordId());
			}
			entity = dao.get(entity.getRecordId());
		}else {
			//根据单号查询
			InvoiceNoContractRecEntity result = dao.getByPaymentId(entity.getPaymentId(), entity.getInvoiceId());
			//如果是空的话就将申请金额和实际付款金额修改为该单号的金额
			if(result == null) {
				//如果是贸易进口税和增值税
				if(entity.getPaymentType().equals(InvoiceNoContractConstant.TARIFF)) {
					TariffVatPayment tariff = tariffService.get(entity.getPaymentId());
					entity.setPaymentAmount(tariff.getPaymentAmount());
					entity.setActualPaymentAmount(tariff.getPaymentAmount());
				//如果是费用支付
				}else if(entity.getPaymentType().equals(InvoiceNoContractConstant.SETTLEMENT)) {
					PrjSettlement settlement = settlementService.get(entity.getPaymentId());
					entity.setPaymentAmount(settlement.getSettlementAmount());
					entity.setActualPaymentAmount(settlement.getRealSettlementAmount());
				}
				//如果可以根据单号查询到信息
			}else {
				entity = result;	
			}
		} 
		//更改累计金额为当前累计金额
		Double sum = dao.getSumAmount(entity.getPaymentId(), entity.getRecordId());
		entity.setSumReceivedAmount(sum == null ? 0.0 : sum);
		//更改付款状态
		if(entity.getApInvoiceAmount() != null) {
			entity.setIsEndUp((entity.getSumReceivedAmount() + entity.getApInvoiceAmount() < entity.getActualPaymentAmount()) ? InvoiceNoContractConstant.NO_END_UP : InvoiceNoContractConstant.END_UP);
		}
		return entity;		
	}

	/**
	 * 删除详情单
	 * @param recordId
	 * @return
	 */
	public OperResult<InvoiceNoContractRecEntity> delete(String recordId) {
		if(StringUtils.isNotBlank(recordId)) {
			InvoiceNoContractRecEntity entity = dao.get(recordId);
			if(entity != null) {
				dao.delete(entity);
				return OperResult.succeed(null, "删除成功", entity);
			}
		}
		return null;
	}
	/**
	 * 保存或修改详单信息
	 * @param entity
	 * @return
	 */

	public OperResult<InvoiceNoContractRecEntity> saveOrUpdate(UserProfile user, InvoiceNoContractRecEntity entity) {
		
		if(dao.get(entity.getRecordId()) == null) {
			dao.save(entity);
		}else {
			dao.update(entity);
		}
		
		return OperResult.succeed(null, "保存成功", entity);
	}

	/**
	 * 根据外键删除
	 * @param invoiceId
	 */
	public void deleteByInvoiceId(String invoiceId) {
		if(StringUtils.isNotBlank(invoiceId)) {
			List<InvoiceNoContractRecEntity> list = dao.findBy("invoiceId", invoiceId);
			if(list.size() > 0) {
				dao.delete(list);
			}
		}
	}
}
