package com.supporter.prj.eip.code_share.code.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * cxf 编码共享服务接口
 * @author Administrator
 *
 */
@RequestMapping(value = "codeShare", produces = "text/plain;charset=utf-8;", consumes = "text/plain;charset=utf-8;")
@WebService(targetNamespace = "http://service.code.code_share.eip.prj.supporter.com/")
public interface ICodeShareService {
	/**
	 * 用于简单测试
	 * @param msg 输入参数
	 * @return String
	 */
	String myTest(@WebParam(name = "msg") String msg);

	/**
	 * 推送项目信息
	 * @param json 项目信息JSON串
	 * @return 如果成功返回0, 其他为失败
	 */
	@WebMethod (operationName = "sendPrj")
	int sendPrj(@WebParam(name = "json") String json);
	
	/**
	 * 推送销售合同
	 * @param json 销售合同JSON串
	 * @return 如果成功返回0, 其他为失败
	 */
	@WebMethod (operationName = "sendSalesContract")
	int sendSalesContract(@WebParam(name = "json") String json);
	
	/**
	 *  获取一般分包合同编码
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @return 一般分包合同编码
	 */
	@WebMethod (operationName = "getSubContractNo")
	String getSubContractNo(@WebParam(name = "prjLib") String prjLib,
			@WebParam(name = "saleContractIdOrNo") String saleContractIdOrNo);
	
	/**
	 * 释放一般分包合同编码，将编码还回编码共享池，供其他合同调用
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param code 被释放的一般分包合同编码
	 * @return 如果成功返回0, 其他为失败
	 */
	@WebMethod (operationName = "fireSubContractNo")
	int fireSubContractNo(@WebParam(name = "prjLib") String prjLib,
			@WebParam(name = "saleContractIdOrNo") String saleContractIdOrNo,
			@WebParam(name = "code") String code);
	
	/**
	 * 获取一般分包合同-附属合同编码
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param subContractNo 一般分包合同编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @return 附属合同编码
	 */
	@WebMethod (operationName = "getSubContractAttachNo")
	String getSubContractAttachNo(@WebParam(name = "prjLib") String prjLib,
			@WebParam(name = "saleContractIdOrNo") String saleContractIdOrNo,
			@WebParam(name = "subContractNo") String subContractNo);
	
	/**
	 * 释放附属合同编码，将编码还回编码共享池，供其他合同调用
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param subContractNo 一般分包合同编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param code 被释放的附属合同编码
	 * @return 如果成功返回0, 其他为失败
	 */
	@WebMethod (operationName = "fireSubContractAttachNo")
	int fireSubContractAttachNo(@WebParam(name = "prjLib") String prjLib,
			@WebParam(name = "saleContractIdOrNo") String saleContractIdOrNo,
			@WebParam(name = "subContractNo") String subContractNo,
			@WebParam(name = "code") String code);
	
	/**
	 *  获取备品备件采购合同编码
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @return 备品备件采购合同编码
	 */
	@WebMethod (operationName = "getSparePartsContractNo")
	String getSparePartsContractNo(@WebParam(name = "prjLib") String prjLib,
			@WebParam(name = "saleContractIdOrNo") String saleContractIdOrNo);
	
	/**
	 * 释放备品备件采购合同编码，将编码还回编码共享池，供其他合同调用
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param code 被释放的备品备件采购合同编码
	 * @return 如果成功返回0, 其他为失败
	 */
	@WebMethod (operationName = "fireSparePartsContractNo")
	int fireSparePartsContractNo(@WebParam(name = "prjLib") String prjLib, 
			@WebParam(name = "saleContractIdOrNo") String saleContractIdOrNo,
			@WebParam(name = "code") String code);
	
	/**
	 *  获取零星采购合同编码
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @return 零星采购合同编码
	 */
	@WebMethod (operationName = "getSporadicContractNo")
	String getSporadicContractNo(@WebParam(name = "prjLib") String prjLib,
			@WebParam(name = "saleContractIdOrNo") String saleContractIdOrNo);
	
	/**
	 * 释放零星采购合同编码，将编码还回编码共享池，供其他合同调用
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param code 被释放的零星采购合同编码
	 * @return 如果成功返回0, 其他为失败
	 */
	@WebMethod (operationName = "fireSporadicContractNo")
	int fireSporadicContractNo(@WebParam(name = "prjLib") String prjLib,
			@WebParam(name = "saleContractIdOrNo") String saleContractIdOrNo,
			@WebParam(name = "code") String code);
	
	
}
