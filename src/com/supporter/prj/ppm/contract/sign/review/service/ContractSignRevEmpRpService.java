package com.supporter.prj.ppm.contract.sign.review.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.sign.review.dao.ContractSignRevEmpRpDao;
import com.supporter.prj.ppm.contract.sign.review.entity.ContractSignRevEmpRp;

/**
 * @Title: Service
 * @Description: 评审人员的评审要点.
 * @author YAN
 * @date 2019-09-06 18:35:31
 * @version V1.0
 */
@Service
public class ContractSignRevEmpRpService {

	@Autowired
	private ContractSignRevEmpRpDao contractSignRevEmpRpDao;

	/**
	 * 根据主键获取评审人员的评审要点.
	 * 
	 * @param rpId 主键
	 * @return ContractSignRevEmpRp
	 */
	public ContractSignRevEmpRp get(String rpId) {
		return contractSignRevEmpRpDao.get(rpId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractSignRevEmpRp > getGrid(UserProfile user, JqGrid jqGrid, ContractSignRevEmpRp contractSignRevEmpRp) {
		return contractSignRevEmpRpDao.findPage(jqGrid, contractSignRevEmpRp);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param rpId
	 * @return
	 */
	public ContractSignRevEmpRp initEditOrViewPage(String rpId) {
		if (StringUtils.isBlank(rpId)) {// 新建
			ContractSignRevEmpRp entity = new ContractSignRevEmpRp();
			return entity;
		} else {// 编辑
			ContractSignRevEmpRp entity = contractSignRevEmpRpDao.get(rpId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractSignRevEmpRp 实体类
	 * @return
	 */
	public ContractSignRevEmpRp saveOrUpdate(UserProfile user, ContractSignRevEmpRp contractSignRevEmpRp) {
		if (StringUtils.isBlank(contractSignRevEmpRp.getRpId())) {// 新建
			return this.save(user, contractSignRevEmpRp);
		} else {// 编辑
			return this.update(user, contractSignRevEmpRp);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractSignRevEmpRp 实体类
	 * @return
	 */
	private ContractSignRevEmpRp save(UserProfile user, ContractSignRevEmpRp contractSignRevEmpRp) {
		this.contractSignRevEmpRpDao.save(contractSignRevEmpRp);
		return contractSignRevEmpRp;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractSignRevEmpRp 实体类
	 * @return
	 */
	private ContractSignRevEmpRp update(UserProfile user, ContractSignRevEmpRp contractSignRevEmpRp) {
		ContractSignRevEmpRp contractSignRevEmpRpDb = this.contractSignRevEmpRpDao.get(contractSignRevEmpRp.getRpId());
		if(contractSignRevEmpRpDb == null) {
			return this.save(user, contractSignRevEmpRp);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractSignRevEmpRp, contractSignRevEmpRpDb);
			this.contractSignRevEmpRpDao.update(contractSignRevEmpRpDb);
			return contractSignRevEmpRpDb;
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
				ContractSignRevEmpRp contractSignRevEmpRpDb = this.contractSignRevEmpRpDao.get(rpId);
				this.contractSignRevEmpRpDao.delete(rpId);
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
		return this.contractSignRevEmpRpDao.nameIsValid( rpId, rpName);
	}

}

