package com.supporter.prj.ppm.contract.sign.filing.service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.sign.filing.dao.ContractEffectConditionDao;
import com.supporter.prj.ppm.contract.sign.filing.dao.ContractEffectConditionDao;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractEffectCondition;
import com.supporter.prj.ppm.contract.sign.filing.entity.ContractEffectCondition;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title: Service
 * @Description: PPM_CONTRACT_FILING_BFD_F.
 * @author YAN
 * @date 2019-09-17 11:37:26
 * @version V1.0
 */
@Service
public class ContractEffectConditionService {

	@Autowired
	private ContractEffectConditionDao contractEffectConditionDao;

	/**
	 * 根据主键获取PPM_CONTRACT_FILING_BFD_F.
	 * 
	 * @param recordId 主键
	 * @return ContractEffectCondition
	 */
	public ContractEffectCondition get(String conditionId) {
		return contractEffectConditionDao.get(conditionId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<ContractEffectCondition> getGrid(UserProfile user, JqGrid jqGrid, ContractEffectCondition contractEffectCondition) {
		return contractEffectConditionDao.findPage(jqGrid, contractEffectCondition);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param conditionId
	 * @return
	 */
	public ContractEffectCondition initEditOrViewPage(String conditionId) {
		if (StringUtils.isBlank(conditionId)) {// 新建
			ContractEffectCondition entity = new ContractEffectCondition();
			return entity;
		} else {// 编辑
			ContractEffectCondition entity = contractEffectConditionDao.get(conditionId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractEffectCondition 实体类
	 * @return
	 */
	public ContractEffectCondition saveOrUpdate(UserProfile user, ContractEffectCondition contractEffectCondition) {
		if (StringUtils.isBlank(contractEffectCondition.getConditionId())) {// 新建
			return this.save(user, contractEffectCondition);
		} else {// 编辑
			return this.update(user, contractEffectCondition);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractEffectCondition 实体类
	 * @return
	 */
	private ContractEffectCondition save(UserProfile user, ContractEffectCondition contractEffectCondition) {
		this.contractEffectConditionDao.save(contractEffectCondition);
		return contractEffectCondition;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractEffectCondition 实体类
	 * @return
	 */
	private ContractEffectCondition update(UserProfile user, ContractEffectCondition contractEffectCondition) {
		ContractEffectCondition contractEffectConditionDb = this.contractEffectConditionDao.get(contractEffectCondition.getConditionId());
		if(contractEffectConditionDb == null) {
			return this.save(user, contractEffectCondition);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractEffectCondition, contractEffectConditionDb);
			this.contractEffectConditionDao.update(contractEffectConditionDb);
			return contractEffectConditionDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param conditionIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String conditionIds) {
		if(StringUtils.isNotBlank(conditionIds)) {
			for(String conditionId : conditionIds.split(",")) {
				ContractEffectCondition contractEffectConditionDb = this.contractEffectConditionDao.get(conditionId);
				this.contractEffectConditionDao.delete(conditionId);
			}
		}
	}
	public void deleteByPrjId(String prjId) {
		this.contractEffectConditionDao.deleteByProperty("prjId",prjId);
	}
}

