package com.supporter.prj.eip.code_share.code.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip.code_share.code.constant.ContractConstant;
import com.supporter.prj.eip.code_share.code.dao.CodeContractNoDao;
import com.supporter.prj.eip.code_share.code.dao.EntitySalesContractDao;
import com.supporter.prj.eip.code_share.code.entity.CodeContractNo;
import com.supporter.prj.eip.code_share.code.entity.EntitySalesContract;
import com.supporter.prj.eip.code_share.service.CsSerialNumberBusinessService;
import com.supporter.prj.eip.logon.entity.UserProfileImpl;
import com.supporter.prj.eip.transaction.TransManager;

/**
 * @Title: 合同编号
 * @Description: CS_CODE_CONTRACT_NO.
 * @author Administrator
 * @date 2019-07-17 16:46:44
 * @version V1.0
 *
 */
@Service
public class CodeContractNoService {
	@Autowired
	private CodeContractNoDao codeContractNoDao;
	@Autowired
	private EntitySalesContractDao salesContractDao;
	@Autowired
	private CsSerialNumberBusinessService numberService;

	/**
	 * 根据主键获取CS_CODE_CONTRACT_NO.
	 * 
	 * @param codeId 主键
	 * @return CodeContractNo
	 */
	public CodeContractNo get(String codeId) {
		return codeContractNoDao.get(codeId);
	}

	/**
	 * 分页表格展示数据.
	 * @param user 用户
	 * @param jqGrid 表格
	 * @param codeContractNo 编号记录
	 * @return List<CodeContractNo>
	 */
	public List<CodeContractNo> getGrid(UserProfile user, JqGrid jqGrid, CodeContractNo codeContractNo) {
		return codeContractNoDao.findPage(user, jqGrid, codeContractNo);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * @param codeId 编号记录ID
	 * @return CodeContractNo
	 */
	public CodeContractNo initEditOrViewPage(String codeId) {
		if (StringUtils.isBlank(codeId)) {
			CodeContractNo entity = new CodeContractNo();
			return entity;
		} else {
			CodeContractNo entity = codeContractNoDao.get(codeId);
			if (entity != null) {

			}
			return entity;
		}
	}

	/**
	 * 保存.
	 * @param user 用户
	 * @param codeContractNo 编号记录对象
	 * @return OperResult<CodeContractNo>
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public OperResult<CodeContractNo> saveOrUpdate(UserProfile user, CodeContractNo codeContractNo) {
		// 主键
		String codeId = codeContractNo.getCodeId();

		// 保存数据库
		this.codeContractNoDao.save(codeContractNo);

		return OperResult.succeed("success", null, codeContractNo);
	}

	/**
	 * 删除.
	 * 
	 * @param user 用户信息
	 * @param codeIds 主键集合，多个以逗号分隔
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public OperResult<?> delete(UserProfile user, String codeIds) {
		if (StringUtils.isNotBlank(codeIds)) {
			for (String codeId : codeIds.split(",")) {
				CodeContractNo codeContractNoDb = this.codeContractNoDao.get(codeId);
				if (codeContractNoDb == null) {
					continue;
				}

				// 删除记录
				this.codeContractNoDao.delete(codeId);
			}
		}
		return OperResult.succeed("success", null, null);
	}
	
	/**
	 *  获取一般分包合同编码
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @return 一般分包合同编码
	 */
	@Transactional(transactionManager = TransManager.APP)
	public synchronized String getSubContractNo(String prjLib, String saleContractIdOrNo) {
		if (StringUtils.isBlank(saleContractIdOrNo) || StringUtils.isBlank(prjLib)) {
			return "";
		}
		
		Map <String, Object> map = new HashMap <String, Object>();
		map.put("codeType", CodeContractNo.CodeType.SUB_CONTRACT);
		map.put("initCount", ContractConstant.SUB_CONTRACT_COUNT_INIT);
		map.put("minCount", ContractConstant.SUB_CONTRACT_COUNT_MIN);
		map.put("growCount", ContractConstant.SUB_CONTRACT_COUNT_GROW);
		map.put("moduleId", ContractConstant.MODULE_ID_CONTRACT);
		map.put("category2", ContractConstant.CATEGORY2_SUB_CONTRACT);
		
		//需要返回的编号
		String returnCode = this.getCode(prjLib, saleContractIdOrNo, map);
		
		return returnCode;
	}
	
	/**
	 * 新建合同编号记录
	 * @param codeType 编号类型
	 * @param contract 销售合同对象
	 * @return CodeContractNo
	 */
	private CodeContractNo newCodeContractNo(int codeType, EntitySalesContract contract) {
		CodeContractNo rec = new CodeContractNo();
		rec.setCodeLocked(false);
		rec.setCodeType(codeType);
		rec.setContractId(contract.getContractId());
		rec.setContractNo(contract.getContractNo());
		rec.setCreatedDate(new Date());
		//rec.setDisplayOrder(0);
		rec.setPrjId(contract.getPrjId());
		rec.setPrjLib(contract.getPrjLib());
		//rec.setShareCode("");
		return rec;
	}

	/**
	 * 释放一般分包合同编码，将编码还回编码共享池，供其他合同调用
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param code 被释放的一般分包合同编码
	 * @return 如果成功返回0, 其他为失败
	 */
	@Transactional(transactionManager = TransManager.APP)
	public int fireSubContractNo(String prjLib, String saleContractIdOrNo, String code) {
		CodeContractNo rec = codeContractNoDao.getCodeContractNoFair(prjLib, saleContractIdOrNo,
				CodeContractNo.CodeType.SUB_CONTRACT, code);
		if (rec != null) {
			rec.setCodeLocked(false);
			codeContractNoDao.update(rec);
		}
		return 0;
	}

	/**
	 * 获取一般分包合同-附属合同编码
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param subContractNo 一般分包合同编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @return 附属合同编码
	 */
	public synchronized String getSubContractAttachNo(String prjLib, String saleContractIdOrNo, String subContractNo) {
		if (StringUtils.isBlank(subContractNo) || StringUtils.isBlank(prjLib)) {
			return "";
		}
		
		//如果一般分包合同为空，则下面不建立子编号，返回空
		CodeContractNo subContractCodeRec = codeContractNoDao.getCodeContractNoByShareCode(prjLib, saleContractIdOrNo,
				CodeContractNo.CodeType.SUB_CONTRACT, subContractNo);
		if (subContractCodeRec == null) {
			return "";
		}
		
		Map <String, Object> map = new HashMap <String, Object>();
		map.put("codeType", CodeContractNo.CodeType.SUB_CONTRACT_ATTACH);
		map.put("initCount", ContractConstant.SUB_ATTACH_COUNT_INIT);
		map.put("minCount", ContractConstant.SUB_ATTACH_COUNT_MIN);
		map.put("growCount", ContractConstant.SUB_ATTACH_COUNT_GROW);
		map.put("moduleId", ContractConstant.MODULE_ID_CONTRACT);
		map.put("category2", ContractConstant.CATEGORY2_SUB_ATTACH);
		
		//需要返回的编号
		String returnCode = this.getSubNo(prjLib, saleContractIdOrNo, subContractCodeRec, map);
		
		return returnCode;
	}

	/**
	 * 释放附属合同编码，将编码还回编码共享池，供其他合同调用
	 * @param subContractNo 一般分包合同编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param code 被释放的附属合同编码
	 * @return 如果成功返回0, 其他为失败
	 */
	public int fireSubContractAttachNo(String prjLib, String saleContractIdOrNo, String subContractNo, String code) {
		CodeContractNo rec = codeContractNoDao.getCodeContractNoFair(prjLib, saleContractIdOrNo, subContractNo,
				CodeContractNo.CodeType.SUB_CONTRACT_ATTACH, code);
		if (rec != null) {
			rec.setCodeLocked(false);
			codeContractNoDao.update(rec);
		}
		return 0;
	}

	/**
	 *  获取备品备件采购合同编码
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @return 备品备件采购合同编码
	 */
	public synchronized String getSparePartsContractNo(String prjLib, String saleContractIdOrNo) {
		if (StringUtils.isBlank(saleContractIdOrNo) || StringUtils.isBlank(prjLib)) {
			return "";
		}
		EntitySalesContract contract = salesContractDao.getContractByIdOrNo(prjLib, saleContractIdOrNo);
		if (contract == null) {
			return "";
		}
		
		Map <String, Object> map = new HashMap <String, Object>();
		map.put("codeType", CodeContractNo.CodeType.SPARE_PARTS_CONTRACT);
		map.put("initCount", ContractConstant.SPARE_PARTS_COUNT_INIT);
		map.put("minCount", ContractConstant.SPARE_PARTS_COUNT_MIN);
		map.put("growCount", ContractConstant.SPARE_PARTS_COUNT_GROW);
		map.put("moduleId", ContractConstant.MODULE_ID_CONTRACT);
		map.put("category2", ContractConstant.CATEGORY2_SPARE_PARTS);
		
		//需要返回的编号
		String returnCode = this.getCode(prjLib, saleContractIdOrNo, map);
		
		return returnCode;
	}

	/**
	 * 释放备品备件采购合同编码，将编码还回编码共享池，供其他合同调用
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param code 被释放的备品备件采购合同编码
	 * @return 如果成功返回0, 其他为失败
	 */
	public int fireSparePartsContractNo(String prjLib, String saleContractIdOrNo, String code) {
		CodeContractNo rec = codeContractNoDao.getCodeContractNoFair(prjLib, saleContractIdOrNo,
				CodeContractNo.CodeType.SPARE_PARTS_CONTRACT, code);
		if (rec != null) {
			rec.setCodeLocked(false);
			codeContractNoDao.update(rec);
		}
		return 0;
	}

	/**
	 *  获取零星采购合同编码
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @return 零星采购合同编码
	 */
	public synchronized String getSporadicContractNo(String prjLib, String saleContractIdOrNo) {
		if (StringUtils.isBlank(saleContractIdOrNo) || StringUtils.isBlank(prjLib)) {
			return "";
		}
		EntitySalesContract contract = salesContractDao.getContractByIdOrNo(prjLib, saleContractIdOrNo);
		if (contract == null) {
			return "";
		}
		
		Map <String, Object> map = new HashMap <String, Object>();
		map.put("codeType", CodeContractNo.CodeType.SPORADIC_CONTRACT);
		map.put("initCount", ContractConstant.SPORADIC_COUNT_INIT);
		map.put("minCount", ContractConstant.SPORADIC_COUNT_MIN);
		map.put("growCount", ContractConstant.SPORADIC_COUNT_GROW);
		map.put("moduleId", ContractConstant.MODULE_ID_CONTRACT);
		map.put("category2", ContractConstant.CATEGORY2_SPORADIC);
		
		//需要返回的编号
		String returnCode = this.getCode(prjLib, saleContractIdOrNo, map);
		
		return returnCode;
	}

	/**
	 * 释放零星采购合同编码，将编码还回编码共享池，供其他合同调用
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param code 被释放的零星采购合同编码
	 * @return 如果成功返回0, 其他为失败
	 */
	public int fireSporadicContractNo(String prjLib, String saleContractIdOrNo, String code) {
		CodeContractNo rec = codeContractNoDao.getCodeContractNoFair(prjLib, saleContractIdOrNo,
				CodeContractNo.CodeType.SPORADIC_CONTRACT, code);
		if (rec != null) {
			rec.setCodeLocked(false);
			codeContractNoDao.update(rec);
		}
		return 0;
	}
	
	/**
	 * 获取编号的通用方法
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param map 参数
	 * @return String
	 */
	private String getCode(String prjLib, String saleContractIdOrNo, Map <String, Object> map) {
		EntitySalesContract contract = salesContractDao.getContractByIdOrNo(prjLib, saleContractIdOrNo);
		if (contract == null) {
			return "";
		}
		
		String returnCode = ""; //需要返回的编号
		
		int codeType = (Integer) map.get("codeType");
		int initCount = (Integer) map.get("initCount");
		int minCount = (Integer) map.get("minCount");
		int growCount = (Integer) map.get("growCount");
		String moduleId = (String) map.get("moduleId");
		String category2 = (String) map.get("category2");
		
		List <CodeContractNo> list = codeContractNoDao.getUnLockedNoList(prjLib, saleContractIdOrNo, codeType);
		int maxDisplayOrder = codeContractNoDao.getMaxCodeDisplayOrder(prjLib, saleContractIdOrNo, codeType);
		
		if (CollectionUtils.isEmpty(list)) {
			//初始化编号池
			List <CodeContractNo> newList = new ArrayList<CodeContractNo>();
			for (int i = 0; i < initCount; i++) {
				CodeContractNo rec = newCodeContractNo(codeType, contract);
				//setDisplayOrder
				rec.setDisplayOrder(maxDisplayOrder + i + 1);
				
				//setShareCode
				UserProfile user = new UserProfileImpl();
				String code = numberService.generateNumber(0, 0, moduleId, category2, contract, user);
				rec.setShareCode(code);
				newList.add(rec);
			}
			returnCode = newList.get(0).getShareCode();
			newList.get(0).setCodeLocked(true);
			codeContractNoDao.save(newList);
		}
		
		if (CollectionUtils.isNotEmpty(list)) {
			if (list.size() > minCount) {
				returnCode = list.get(0).getShareCode();
				list.get(0).setCodeLocked(true);
				codeContractNoDao.update(list.get(0));
			} else {
				//扩展编号池
				List <CodeContractNo> newList = new ArrayList<CodeContractNo>();
				for (int i = 0; i < growCount; i++) {
					CodeContractNo rec = newCodeContractNo(codeType, contract);
					//setDisplayOrder
					rec.setDisplayOrder(maxDisplayOrder + i + 1);
					
					//setShareCode
					UserProfile user = new UserProfileImpl();
					String code = numberService.generateNumber(0, 0, moduleId, category2, contract, user);
					rec.setShareCode(code);
					newList.add(rec);
				}
				codeContractNoDao.save(newList);
				
				returnCode = list.get(0).getShareCode();
				list.get(0).setCodeLocked(true);
				codeContractNoDao.update(list.get(0));
			}
		}
		return returnCode;
	}
	
	/**
	 * 获取子编号的通用方法
	 * @param prjLib 项目库  ENGINEE-工程项目; TRADE-贸易项目
	 * @param saleContractIdOrNo 销售合同ID或编码
	 * @param parentRec 父编号
	 * @param map 参数
	 * @return String
	 */
	private String getSubNo(String prjLib, String saleContractIdOrNo, CodeContractNo parentRec, Map <String, Object> map) {
		EntitySalesContract contract = salesContractDao.getContractByIdOrNo(prjLib, saleContractIdOrNo);
		if (contract == null) {
			return "";
		}
		
		int codeType = (Integer) map.get("codeType");
		int initCount = (Integer) map.get("initCount");
		int minCount = (Integer) map.get("minCount");
		int growCount = (Integer) map.get("growCount");
		String moduleId = (String) map.get("moduleId");
		String category2 = (String) map.get("category2");
		
		String returnCode = ""; //需要返回的编号
		
		List <CodeContractNo> list = codeContractNoDao.getUnLockedNoList(prjLib, saleContractIdOrNo, parentRec.getShareCode(), codeType);
		int maxDisplayOrder = codeContractNoDao.getMaxCodeDisplayOrder(prjLib, saleContractIdOrNo, parentRec.getShareCode(), codeType);
		
		if (CollectionUtils.isEmpty(list)) {
			//初始化编号池
			List <CodeContractNo> newList = new ArrayList<CodeContractNo>();
			for (int i = 0; i < initCount; i++) {
				CodeContractNo rec = newCodeContractNo(codeType, contract);
				rec.setParentCode(parentRec.getShareCode());
				//setDisplayOrder
				rec.setDisplayOrder(maxDisplayOrder + i + 1);
				
				//setShareCode
				UserProfile user = new UserProfileImpl();
				String code = numberService.generateNumber(0, 0, moduleId, category2, parentRec, user);
				rec.setShareCode(code);
				newList.add(rec);
			}
			returnCode = newList.get(0).getShareCode();
			newList.get(0).setCodeLocked(true);
			codeContractNoDao.save(newList);
		}
		
		if (CollectionUtils.isNotEmpty(list)) {
			if (list.size() > minCount) {
				returnCode = list.get(0).getShareCode();
				list.get(0).setCodeLocked(true);
				codeContractNoDao.update(list.get(0));
			} else {
				//扩展编号池
				List <CodeContractNo> newList = new ArrayList<CodeContractNo>();
				for (int i = 0; i < growCount; i++) {
					CodeContractNo rec = newCodeContractNo(codeType, contract);
					rec.setParentCode(parentRec.getShareCode());
					//setDisplayOrder
					rec.setDisplayOrder(maxDisplayOrder + i + 1);
					
					//setShareCode
					UserProfile user = new UserProfileImpl();
					String code = numberService.generateNumber(0, 0, moduleId, category2, parentRec, user);
					rec.setShareCode(code);
					newList.add(rec);
				}
				codeContractNoDao.save(newList);
				
				returnCode = list.get(0).getShareCode();
				list.get(0).setCodeLocked(true);
				codeContractNoDao.update(list.get(0));
			}
		}
		return returnCode;
		
	}
	
	/**
	 * 强制锁定
	 * @param contractRecId 记录ID
	 */
	public void forceLock(String contractRecId) {
		CodeContractNo rec = codeContractNoDao.get(contractRecId);
		if (rec != null && !rec.isCodeLocked()) {
			rec.setCodeLocked(true);
			codeContractNoDao.update(rec);
		}
	}
	
	/**
	 * 强制解锁
	 * @param contractRecId 记录ID
	 */
	public void fireLock(String contractRecId) {
		CodeContractNo rec = codeContractNoDao.get(contractRecId);
		if (rec != null && rec.isCodeLocked()) {
			rec.setCodeLocked(false);
			codeContractNoDao.update(rec);
		}
	}

}
