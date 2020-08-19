package com.supporter.prj.ppm.contract.sign.review_ver.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.contract.sign.review_ver.dao.ContractSignRevVerRpDao;
import com.supporter.prj.ppm.contract.sign.review_ver.entity.ContractSignRevVerRp;

/**
 * @Title: Service
 * @Description: 验证评审要点表.
 * @author YAN
 * @date 2019-09-09 10:46:31
 * @version V1.0
 */
@Service
public class ContractSignRevVerRpService {

	@Autowired
	private ContractSignRevVerRpDao contractSignRevVerRpDao;

	/**
	 * 根据主键获取验证评审要点表.
	 * 
	 * @param rpId 主键
	 * @return ContractSignRevVerRp
	 */
	public ContractSignRevVerRp get(String rpId) {
		return contractSignRevVerRpDao.get(rpId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< ContractSignRevVerRp > getGrid(UserProfile user, JqGrid jqGrid, ContractSignRevVerRp contractSignRevVerRp) {
		return contractSignRevVerRpDao.findPage(jqGrid, contractSignRevVerRp);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param rpId
	 * @return
	 */
	public ContractSignRevVerRp initEditOrViewPage(String rpId) {
		if (StringUtils.isBlank(rpId)) {// 新建
			ContractSignRevVerRp entity = new ContractSignRevVerRp();
			return entity;
		} else {// 编辑
			ContractSignRevVerRp entity = contractSignRevVerRpDao.get(rpId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param contractSignRevVerRp 实体类
	 * @return
	 */
	public ContractSignRevVerRp saveOrUpdate(UserProfile user, ContractSignRevVerRp contractSignRevVerRp) {
		if (StringUtils.isBlank(contractSignRevVerRp.getRpId())) {// 新建
			return this.save(user, contractSignRevVerRp);
		} else {// 编辑
			return this.update(user, contractSignRevVerRp);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param contractSignRevVerRp 实体类
	 * @return
	 */
	private ContractSignRevVerRp save(UserProfile user, ContractSignRevVerRp contractSignRevVerRp) {
		this.contractSignRevVerRpDao.save(contractSignRevVerRp);
		return contractSignRevVerRp;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param contractSignRevVerRp 实体类
	 * @return
	 */
	private ContractSignRevVerRp update(UserProfile user, ContractSignRevVerRp contractSignRevVerRp) {
		ContractSignRevVerRp contractSignRevVerRpDb = this.contractSignRevVerRpDao.get(contractSignRevVerRp.getRpId());
		if(contractSignRevVerRpDb == null) {
			return this.save(user, contractSignRevVerRp);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(contractSignRevVerRp, contractSignRevVerRpDb);
			this.contractSignRevVerRpDao.update(contractSignRevVerRpDb);
			return contractSignRevVerRpDb;
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
				ContractSignRevVerRp contractSignRevVerRpDb = this.contractSignRevVerRpDao.get(rpId);
				this.contractSignRevVerRpDao.delete(rpId);
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
		return this.contractSignRevVerRpDao.nameIsValid( rpId, rpName);
	}

}

