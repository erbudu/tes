package com.supporter.prj.ppm.ecc.base_info.customer.service;

import java.util.List;


import com.supporter.prj.eip.module.util.ModuleUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;


import com.supporter.prj.ppm.ecc.base_info.customer.dao.EccCustomerPartnerDao;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerPartner;

/**
 * @Title: Service
 * @Description: 出口管制客户的合伙人.
 * @author YAN
 * @date 2019-08-16 18:30:29
 * @version V1.0
 */
@Service
public class EccCustomerPartnerService {

	@Autowired
	private EccCustomerPartnerDao eccCustomerPartnerDao;

	/**
	 * 根据主键获取出口管制客户的合伙人.
	 * 
	 * @param customerId 主键
	 * @return EccCustomerPartner
	 */
	public EccCustomerPartner get(String customerId) {
		return eccCustomerPartnerDao.get(customerId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccCustomerPartner > getGrid(UserProfile user, JqGrid jqGrid, EccCustomerPartner eccCustomerPartner) {
		return eccCustomerPartnerDao.findPage(jqGrid, eccCustomerPartner);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param customerId
	 * @return
	 */
	public EccCustomerPartner initEditOrViewPage(String customerId) {
		if (StringUtils.isBlank(customerId)) {// 新建
			EccCustomerPartner entity = new EccCustomerPartner();
			return entity;
		} else {// 编辑
			EccCustomerPartner entity = eccCustomerPartnerDao.get(customerId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccCustomerPartner 实体类
	 * @return
	 */
	public EccCustomerPartner saveOrUpdate(UserProfile user, EccCustomerPartner eccCustomerPartner) {
		if (StringUtils.isBlank(eccCustomerPartner.getCustomerId())) {// 新建
			return this.save(user, eccCustomerPartner);
		} else {// 编辑
			return this.update(user, eccCustomerPartner);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccCustomerPartner 实体类
	 * @return
	 */
	private EccCustomerPartner save(UserProfile user, EccCustomerPartner eccCustomerPartner) {
		this.eccCustomerPartnerDao.save(eccCustomerPartner);
		return eccCustomerPartner;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccCustomerPartner 实体类
	 * @return
	 */
	private EccCustomerPartner update(UserProfile user, EccCustomerPartner eccCustomerPartner) {
		EccCustomerPartner eccCustomerPartnerDb = this.eccCustomerPartnerDao.get(eccCustomerPartner.getCustomerId());
		if(eccCustomerPartnerDb == null) {
			return this.save(user, eccCustomerPartner);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccCustomerPartner, eccCustomerPartnerDb);
			this.eccCustomerPartnerDao.update(eccCustomerPartnerDb);
			return eccCustomerPartnerDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param customerIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String customerIds) {
		if(StringUtils.isNotBlank(customerIds)) {
			for(String customerId : customerIds.split(",")) {
				EccCustomerPartner eccCustomerPartnerDb = this.eccCustomerPartnerDao.get(customerId);
				this.eccCustomerPartnerDao.delete(customerId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param customerId
	 * @param customerName
	 * @return
	 */
	public boolean nameIsValid(String customerId,String customerName) {
		return this.eccCustomerPartnerDao.nameIsValid( customerId, customerName);
	}
	/**
	 * 根据客户id查询
	 * @param customerId
	 * @return
	 */
	public EccCustomerPartner findByCustomerId(String customerId) {
		List<EccCustomerPartner> list = findListByCustomerId(customerId);
		if (list!=null){
			return list.get(0);
		}else {
			EccCustomerPartner partner = new EccCustomerPartner();
			partner.setCustomerId(customerId);
			return  partner;
		}
	}
	/**
	 * 根据客户id删除
	 * @param customerId
	 */
	public void deleteByCustomerId(String customerId) {
		List<EccCustomerPartner> list = findListByCustomerId(customerId);
		if (list!=null){
			this.eccCustomerPartnerDao.delete(list);
		}
	}

	public List<EccCustomerPartner> findListByCustomerId(String customerId){
		List<EccCustomerPartner> list = this.eccCustomerPartnerDao.findBy("customerId",customerId);
		if (list!=null&&list.size()>0){
			return list;
		}else {
			return null;
		}
	}
}

