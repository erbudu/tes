package com.supporter.prj.eip.code_share.code.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;

import com.supporter.prj.cneec.cxf.JsonDateValueProcessor;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip.code_share.code.dao.EntityProjectDao;
import com.supporter.prj.eip.code_share.code.dao.EntitySalesContractDao;
import com.supporter.prj.eip.code_share.code.entity.EntityProject;
import com.supporter.prj.eip.code_share.code.entity.EntitySalesContract;
import com.supporter.prj.eip.transaction.TransManager;

/**
 * @Title: 销售合同
 * @Description: CS_ENTITY_SALES_CONTRACT.
 * @author Administrator
 * @date 2019-07-17 16:46:39
 * @version V1.0
 *
 */
@Service
public class EntitySalesContractService {
	@Autowired
	private EntitySalesContractDao salesContractDao;
	@Autowired
	private EntityProjectDao projectDao;
	
	private static final int ERROR_RESULT_1 = 1;
	private static final int ERROR_RESULT_2 = 2;
	private static final int ERROR_RESULT_3 = 3;
	private static final int ERROR_RESULT_4 = 4;

	private static final int MIN_LEN_SALES_CONTRACT_NO = 15;

	/**
	 * 推送销售合同 (此处不用加事务)
	 * @param json 销售合同JSON串
	 * @return 如果成功返回0, 其他为失败
	 */
	public int sendSalesContract(String json) {
		if (StringUtils.isBlank(json)) {
			return ERROR_RESULT_1;
		}
		/**
		 * JSON结构说明： { prjId:"项目ID", prjName:"项目名称", contractId:"销售合同ID",
		 * contractName:"销售合同名称", contractNo:"销售合同编码", prjLib: "项目库",
		 * //项目库:ENGINEE-工程项目; TRADE-贸易项目 sourceFrom:"数据来源业务系统" }
		 * 
		 */

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(json, jsonConfig);

		String prjLib = CommonUtil.trim(jsonObj.getString("prjLib"));
		if (StringUtils.isBlank(prjLib)) {
			return ERROR_RESULT_2;
		}
		String contractId = CommonUtil.trim(jsonObj.getString("contractId"));
		if (StringUtils.isBlank(contractId)) {
			return ERROR_RESULT_3;
		}
		String contractNo = jsonObj.getString("contractNo");
		if (StringUtils.isBlank(contractNo)) {
			return ERROR_RESULT_4;
		}

		String prjId = CommonUtil.trim(jsonObj.getString("prjId"));
		String prjRecId = prjLib + "_" + prjId;
		EntityProject prj = projectDao.get(prjRecId);

		EntitySalesContract entity = salesContractDao.getContractInLibById(prjLib, contractId);
		boolean isNew = false;
		if (entity == null) {
			isNew = true;
			entity = new EntitySalesContract();
			entity.setContractRecId(prjLib + "_" + contractId);
		}
		entity.setContractId(contractId);
		entity.setContractName(jsonObj.getString("contractName"));
		entity.setContractNo(contractNo);
		entity.setCreatedDate(new Date());
		entity.setDeptCode(getDeptCodeFromContractNo(contractNo));
		entity.setPrjCode(getPrjCodeFromContractNo(contractNo));
		entity.setPrjId(prjId);
		entity.setPrjLib(prjLib);
		entity.setPrjName(jsonObj.getString("prjName"));
		if (prj != null) {
			entity.setPrjRecId(prj.getPrjRecId());
			if (StringUtils.isBlank(entity.getPrjName())) {
				entity.setPrjName(prj.getPrjName());
			}
		}
		entity.setSourceFrom(jsonObj.getString("sourceFrom"));
		if (isNew) {
			salesContractDao.save(entity);
		} else {
			salesContractDao.update(entity);
		}

		return 0;
	}

	/**
	 * 从销售合同编号中获取部门代码
	 * 
	 * @param salesContractNo 销售合同编号
	 * @return String
	 */
	private String getDeptCodeFromContractNo(String salesContractNo) {
		// salesContractNo : CNEEC2015GXSRA003
		if (StringUtils.isBlank(salesContractNo) || salesContractNo.length() < MIN_LEN_SALES_CONTRACT_NO) {
			return "";
		}
		// return GX
		final int startIndex = 9;
		final int endIndex = 11;
		return salesContractNo.substring(startIndex, endIndex);
	}

	/**
	 * 从销售合同编号中获取项目代码
	 * 
	 * @param salesContractNo
	 *            销售合同编号
	 * @return String
	 */
	private String getPrjCodeFromContractNo(String salesContractNo) {
		// salesContractNo : CNEEC2015GXSRA003 CNEEC2015GXSRA003e002
		if (StringUtils.isBlank(salesContractNo) || salesContractNo.length() < MIN_LEN_SALES_CONTRACT_NO) {
			return "";
		}
		// return SRA003
		final int startIndex = 11;
		int endIndex = 0;
		//final int contractOrderNumberLen = 3;
		int eIndex = salesContractNo.lastIndexOf("e");
		if (eIndex > MIN_LEN_SALES_CONTRACT_NO) {
			//endIndex = eIndex - contractOrderNumberLen;
			endIndex = eIndex;
		} else {
			//endIndex = salesContractNo.length() - contractOrderNumberLen;
			endIndex = salesContractNo.length();
		}

		return salesContractNo.substring(startIndex, endIndex);
	}

	/**
	 * 根据主键获取CS_ENTITY_SALES_CONTRACT.
	 * 
	 * @param contractRecId
	 *            主键
	 * @return EntitySalesContract
	 */
	public EntitySalesContract get(String contractRecId) {
		return salesContractDao.get(contractRecId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户
	 * @param jqGrid 表格
	 * @param entitySalesContract 销售合同
	 * @return List< EntitySalesContract >
	 */
	public List<EntitySalesContract> getGrid(UserProfile user, JqGrid jqGrid, EntitySalesContract entitySalesContract) {
		return salesContractDao.findPage(user, jqGrid, entitySalesContract);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param contractRecId
	 * @return
	 */
	public EntitySalesContract initEditOrViewPage(String contractRecId) {
		if (StringUtils.isBlank(contractRecId)) {
			EntitySalesContract entity = new EntitySalesContract();
			return entity;
		} else {
			EntitySalesContract entity = salesContractDao.get(contractRecId);
			if (entity != null) {

			}
			return entity;
		}
	}

	/**
	 * 保存.
	 * 
	 * @param user
	 *            用户信息
	 * @param entitySalesContract
	 *            实体类
	 * @return
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public OperResult<EntitySalesContract> save(UserProfile user, EntitySalesContract entitySalesContract) {
		// 主键
		String contractRecId = entitySalesContract.getContractRecId();

		// 保存数据库
		this.salesContractDao.save(entitySalesContract);

		return OperResult.succeed("success", null, entitySalesContract);
	}

	/**
	 * 删除.
	 * 
	 * @param user
	 *            用户信息
	 * @param contractRecIds
	 *            主键集合，多个以逗号分隔
	 */
	@Transactional(transactionManager = TransManager.APP, rollbackFor = Exception.class)
	public OperResult<?> delete(UserProfile user, String contractRecIds) {
		if (StringUtils.isNotBlank(contractRecIds)) {
			for (String contractRecId : contractRecIds.split(",")) {
				EntitySalesContract entitySalesContractDb = this.salesContractDao.get(contractRecId);
				if (entitySalesContractDb == null) {
					continue;
				}
				// 删除记录
				this.salesContractDao.delete(contractRecId);
			}
		}
		return OperResult.succeed("success", null, null);
	}
	
	/**
	 * 根据项目库和销售合同ID获取销售合同对象
	 * @param prjLib 项目库
	 * @param saleContractId 销售合同ID
	 * @return EntitySalesContract
	 */
	public EntitySalesContract getContractInLibById(String prjLib, String saleContractId) {
		String recId = CommonUtil.trim(prjLib)  + "_" + CommonUtil.trim(saleContractId);
		EntitySalesContract contract = this.get(recId);
		return contract;
	}
}
