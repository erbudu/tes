package com.supporter.prj.eip.code_share.code.service;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: 编码共享服务实现类
 * @author Administrator
 * @date 2019-07-17 16:46:42
 * @version V1.0
 *
 */
@Service("codeShareServiceImpl")
@WebService(endpointInterface = "com.supporter.prj.eip.code_share.code.service.ICodeShareService")
public class CodeShareServiceImpl implements ICodeShareService {
	@Autowired
	private CodeContractNoService codeContractNoService;
	@Autowired
	private EntityProjectService enityProjectService;
	@Autowired
	private EntitySalesContractService entitySalesContractService;

	@Override
	public String myTest(String msg) {
		return "测试成功：" + msg;
	}

	/**
	 * 推送项目信息
	 * @param json 项目信息JSON串
	 * @return 如果成功返回0, 其他为失败
	 */
	@Override
	public int sendPrj(String json) {
		return enityProjectService.sendPrj(json);
	}

	/**
	 * 推送销售合同
	 * @param json 销售合同JSON串
	 * @return 如果成功返回0, 其他为失败
	 */
	@Override
	public int sendSalesContract(String json) {
		return entitySalesContractService.sendSalesContract(json);
	}

	/**
	 *  获取一般分包合同编码
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @return 一般分包合同编码
	 */
	@Override
	public String getSubContractNo(String prjLib, String saleContractIdOrNo) {
		return codeContractNoService.getSubContractNo(prjLib, saleContractIdOrNo);
	}

	/**
	 * 释放一般分包合同编码，将编码还回编码共享池，供其他合同调用
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param code 被释放的一般分包合同编码
	 * @return 如果成功返回0, 其他为失败
	 */
	@Override
	public int fireSubContractNo(String prjLib, String saleContractIdOrNo, String code) {
		return codeContractNoService.fireSubContractNo(prjLib, saleContractIdOrNo, code);
	}

	/**
	 * 获取一般分包合同-附属合同编码
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param subContractNo 一般分包合同编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @return 附属合同编码
	 */
	@Override
	public String getSubContractAttachNo(String prjLib, String saleContractIdOrNo,
			String subContractNo) {
		return codeContractNoService.getSubContractAttachNo(prjLib, saleContractIdOrNo, subContractNo);
	}

	/**
	 * 释放附属合同编码，将编码还回编码共享池，供其他合同调用
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param subContractNo 一般分包合同编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param code 被释放的附属合同编码
	 * @return 如果成功返回0, 其他为失败
	 */
	@Override
	public int fireSubContractAttachNo(String prjLib, String saleContractIdOrNo,
			String subContractNo, String code) {
		return codeContractNoService.fireSubContractAttachNo(prjLib, saleContractIdOrNo, subContractNo, code);
	}

	/**
	 *  获取备品备件采购合同编码
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @return 备品备件采购合同编码
	 */
	@Override
	public String getSparePartsContractNo(String prjLib, String saleContractIdOrNo) {
		return codeContractNoService.getSparePartsContractNo(prjLib, saleContractIdOrNo);
	}

	/**
	 * 释放备品备件采购合同编码，将编码还回编码共享池，供其他合同调用
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param code 被释放的备品备件采购合同编码
	 * @return 如果成功返回0, 其他为失败
	 */
	@Override
	public int fireSparePartsContractNo(String prjLib, String saleContractIdOrNo, String code) {
		return codeContractNoService.fireSparePartsContractNo(prjLib, saleContractIdOrNo, code);
	}

	/**
	 *  获取零星采购合同编码
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @return 零星采购合同编码
	 */
	@Override
	public String getSporadicContractNo(String prjLib, String saleContractIdOrNo) {
		return codeContractNoService.getSporadicContractNo(prjLib, saleContractIdOrNo);
	}

	/**
	 * 释放零星采购合同编码，将编码还回编码共享池，供其他合同调用
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param code 被释放的零星采购合同编码
	 * @return 如果成功返回0, 其他为失败
	 */
	@Override
	public int fireSporadicContractNo(String prjLib, String saleContractIdOrNo, String code) {
		return codeContractNoService.fireSporadicContractNo(prjLib, saleContractIdOrNo, code);
	}
	

}
