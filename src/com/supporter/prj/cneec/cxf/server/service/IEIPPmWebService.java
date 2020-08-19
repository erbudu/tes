package com.supporter.prj.cneec.cxf.server.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * cxf PM服务接口(用于数据中心调用总部接口向EIP推送数据)
 * @author Administrator
 *
 */
@RequestMapping(value = "pm", produces = "text/plain;charset=utf-8;", consumes = "text/plain;charset=utf-8;")
@WebService(targetNamespace = "http://service.server.cxf.cneec.prj.supporter.com/")
public interface IEIPPmWebService {
	/**
	 * 用于简单测试
	 * @param msg 输入参数
	 * @return String
	 */
	String myTest(@WebParam(name = "msg") String msg);
	
	/**
	 * 采购信息回盘
	 * @param entityId 业务对象ID
	 * @return json格式的数据
	 */
	@WebMethod (operationName = "returnProcureContract")
	String returnProcureContract(@WebParam(name = "entityId") String entityId);
	
	/**
	 * 施工签证回盘
	 * @param entityId 业务对象ID
	 * @return json格式的数据
	 */
	@WebMethod (operationName = "returnEngineeVisa")
	String returnEngineeVisa(@WebParam(name = "entityId") String entityId);
	
	/**
	 * 合同结算回盘
	 * @param entityId 业务对象ID
	 * @return json格式的数据
	 */
	@WebMethod (operationName = "returnContractBalance")
	String returnContractBalance(@WebParam(name = "entityId") String entityId);
	
	/**
	 * 付款申请回盘
	 * @param entityId 业务对象ID
	 * @return json格式的数据
	 */
	@WebMethod (operationName = "returnPaymentApply")
	String returnPaymentApply(@WebParam(name = "entityId") String entityId);
	
	/**
	 * 资金拨付回盘
	 * @param entityId 业务对象ID
	 * @return json格式的数据
	 */
	@WebMethod (operationName = "returnFundAppropriation")
	String returnFundAppropriation(@WebParam(name = "entityId") String entityId);
	
	/**
	 * 推送采购信息
	 * @param jsonData json格式的数据
	 * @return boolean  true:成功   false：失败
	 */
	@WebMethod (operationName = "sendProcureContract")
	boolean sendProcureContract(@WebParam(name = "jsonData") String jsonData);
	
	/**
	 * 推送施工签证
	 * @param jsonData json格式的数据
	 * @return boolean  true:成功   false：失败
	 */
	@WebMethod (operationName = "sendEngineeVisa")
	boolean sendEngineeVisa(@WebParam(name = "jsonData") String jsonData);
	
	/**
	 * 推送合同结算
	 * @param jsonData json格式的数据
	 * @return boolean  true:成功   false：失败
	 */
	@WebMethod (operationName = "sendContractBalance")
	boolean sendContractBalance(@WebParam(name = "jsonData") String jsonData);
	
	/**
	 * 推送付款申请
	 * @param jsonData json格式的数据
	 * @return boolean  true:成功   false：失败
	 */
	@WebMethod (operationName = "sendPaymentApply")
	boolean sendPaymentApply(@WebParam(name = "jsonData") String jsonData);
	/**
	 * 推送付款申请实际支付
	 * @param jsonData json格式的数据
	 * @return boolean  true:成功   false：失败
	 */
	@WebMethod (operationName = "sendPaymentActual")
	boolean sendPaymentActual(@WebParam(name = "jsonData") String jsonData);
	
	/**
	 * 推送资金拨付
	 * @param jsonData json格式的数据
	 * @return boolean  true:成功   false：失败
	 */
	@WebMethod (operationName = "sendFundAppropriation")
	boolean sendFundAppropriation(@WebParam(name = "jsonData") String jsonData);
	
	/**
	 * 推送银行信息
	 * @param jsonData json格式的数据
	 * @return boolean  true:成功   false：失败
	 */
	@WebMethod (operationName = "sendBank")
	boolean sendBank(@WebParam(name = "jsonData") String jsonData);
	
	/**
	 * 推送设计变更
	 * @param jsonData json格式的数据
	 * @return boolean  true:成功   false：失败
	 */
	@WebMethod (operationName = "sendDesignChange")
	boolean sendDesignChange(@WebParam(name = "jsonData") String jsonData);
	
	/**
	 * 推送未报销现金
	 * @param jsonData json格式的数据
	 * @return boolean  true:成功   false：失败
	 */
	@WebMethod (operationName = "sendNotReimbursed")
	boolean sendNotReimbursed(@WebParam(name = "jsonData") String jsonData);
	
	/**
	 * 推送库存现金
	 * @param jsonData json格式的数据
	 * @return boolean  true:成功   false：失败
	 */
	@WebMethod (operationName = "sendStockCash")
	boolean sendStockCash(@WebParam(name = "jsonData") String jsonData);
	
	/**
	 * 推送交付施工
	 * @param jsonData json格式的数据
	 * @return boolean  true:成功   false：失败
	 */
	@WebMethod (operationName = "sendDeliveryConstruction")
	boolean sendDeliveryConstruction(@WebParam(name = "jsonData") String jsonData);

	/**
	 * 推送图纸交接
	 * @param jsonData
	 * @return boolean true:成功 false：失败
	 */
	@WebMethod(operationName = "sendDrawingLibrary")
	boolean sendDrawingLibrary(@WebParam(name = "jsonData") String jsonData);

	/**
	 * 推送图纸外审
	 * @param jsonData
	 * @return boolean true:成功 false：失败
	 */
	@WebMethod(operationName = "sendExternalDrawing")
	boolean sendExternalDrawing(@WebParam(name = "jsonData") String jsonData);

}
