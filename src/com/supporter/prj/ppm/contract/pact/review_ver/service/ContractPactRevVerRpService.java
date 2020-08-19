package com.supporter.prj.ppm.contract.pact.review_ver.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.pact.review_ver.dao.ContractPactRevVerRpDao;
import com.supporter.prj.ppm.contract.pact.review_ver.entity.ContractPactRevVerRp;

@Service
@Transactional(TransManager.APP)
public class ContractPactRevVerRpService {

	@Autowired
	private ContractPactRevVerRpDao contractPactRevVerRpDao;

	/**
	 * 根据主键获取PPM_CONTRACT_PACT_REV_VER_RP.
	 * 
	 * @param rpId 主键
	 * @return ContractPactRevVerRp
	 */
	public ContractPactRevVerRp get(String rpId) {
		return contractPactRevVerRpDao.get(rpId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractPactRevVerRp > getGrid(UserProfile user, JqGrid jqGrid, ContractPactRevVerRp contractPactRevVerRp) {
		return contractPactRevVerRpDao.findPage(jqGrid, contractPactRevVerRp);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param rpId
	 * @return
	 */
	public ContractPactRevVerRp initEditOrViewPage(String rpId) {
		if (StringUtils.isBlank(rpId)) {// 新建
			ContractPactRevVerRp entity = new ContractPactRevVerRp();
			return entity;
		} else {// 编辑
			ContractPactRevVerRp entity = contractPactRevVerRpDao.get(rpId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractPactRevVerRp 实体类
	 * @return
	 */
	public ContractPactRevVerRp saveOrUpdate(UserProfile user, ContractPactRevVerRp contractPactRevVerRp) {
		if (StringUtils.isBlank(contractPactRevVerRp.getRpId())) {// 新建
			return this.save(user, contractPactRevVerRp);
		} else {// 编辑
			return this.update(user, contractPactRevVerRp);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractPactRevVerRp 实体类
	 * @return
	 */
	private ContractPactRevVerRp save(UserProfile user, ContractPactRevVerRp contractPactRevVerRp) {
		this.contractPactRevVerRpDao.save(contractPactRevVerRp);
		return contractPactRevVerRp;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractPactRevVerRp 实体类
	 * @return
	 */
	private ContractPactRevVerRp update(UserProfile user, ContractPactRevVerRp contractPactRevVerRp) {
		ContractPactRevVerRp contractPactRevVerRpDb = this.contractPactRevVerRpDao.get(contractPactRevVerRp.getRpId());
		if(contractPactRevVerRpDb == null) {
			return this.save(user, contractPactRevVerRp);
		}else{
			this.contractPactRevVerRpDao.update(contractPactRevVerRpDb);
			return contractPactRevVerRpDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param rpIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String rpIds) {
		if(StringUtils.isNotBlank(rpIds)) {
			for(String rpId : rpIds.split(",")) {
				ContractPactRevVerRp contractPactRevVerRpDb = this.contractPactRevVerRpDao.get(rpId);
				this.contractPactRevVerRpDao.delete(rpId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param rpId
	 * @param rpName
	 * @return
	 */
	public boolean nameIsValid(String rpId,String rpName) {
		return this.contractPactRevVerRpDao.nameIsValid( rpId, rpName);
	}

}

