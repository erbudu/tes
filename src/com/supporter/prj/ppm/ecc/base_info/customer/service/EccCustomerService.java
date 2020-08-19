package com.supporter.prj.ppm.ecc.base_info.customer.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerFso;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerPartner;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerWarn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.base_info.customer.dao.EccCustomerDao;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomer;

/**
 * @Title: Service
 * @Description: 出口管制客户.
 * @author YAN
 * @date 2019-08-16 18:30:26
 * @version V1.0
 */
@Service
public class EccCustomerService {

	@Autowired
	private EccCustomerDao eccCustomerDao;
	@Autowired
	private EccCustomerFsoService fsoService;
	@Autowired
	private EccCustomerPartnerService partnerService;
	@Autowired
	private EccCustomerWarnService warnService;

	/**
	 * 根据主键获取出口管制客户.
	 * 
	 * @param customerId 主键
	 * @return EccCustomer
	 */
	public EccCustomer get(String customerId) {
		return eccCustomerDao.get(customerId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccCustomer > getGrid(UserProfile user, JqGrid jqGrid, EccCustomer eccCustomer) {

		return eccCustomerDao.findPage(jqGrid, eccCustomer);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param customerId
	 * @return
	 */
	public EccCustomer initEditOrViewPage(String customerId) {
		EccCustomer entity = null;
		if (StringUtils.isBlank(customerId)) {// 新建
			entity = new EccCustomer();
			customerId = com.supporter.util.UUIDHex.newId();
			entity.setCustomerId(customerId);
		} else {// 编辑
			entity = eccCustomerDao.get(customerId);
		}
		entity = initEditOrViewPage(customerId,entity);
		return entity;
	}
	public EccCustomer initEditOrViewPage(String customerId,EccCustomer entity) {
		if (entity==null){
			entity = new EccCustomer();
			entity.setCustomerId(customerId);
		}
		//获取客户所在地对象
		EccCustomerFso fso = fsoService.findByCustomerId(customerId);
		//后去客户合作伙伴对象
		EccCustomerPartner partner = partnerService.findByCustomerId(customerId);
		//获取客户警告对象
		EccCustomerWarn warn = warnService.findByCustomerId(customerId);
		entity.setFso(fso);
		entity.setPartner(partner);
		entity.setWarn(warn);
		return entity;
	}
	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccCustomer 实体类
	 * @return
	 */
	public EccCustomer saveOrUpdate(UserProfile user, EccCustomer eccCustomer) {
		if (StringUtils.isBlank(eccCustomer.getCustomerId())) {// 新建
			return this.save(user, eccCustomer);
		} else {// 编辑
			return this.update(user, eccCustomer);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccCustomer 实体类
	 * @return
	 */
	private EccCustomer save(UserProfile user, EccCustomer eccCustomer) {
		this.eccCustomerDao.save(eccCustomer);
		return eccCustomer;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccCustomer 实体类
	 * @return
	 */
	private EccCustomer update(UserProfile user, EccCustomer eccCustomer) {
		EccCustomer eccCustomerDb = this.eccCustomerDao.get(eccCustomer.getCustomerId());
		if(eccCustomerDb == null) {
			this.save(user, eccCustomer);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccCustomer, eccCustomerDb);
			this.eccCustomerDao.update(eccCustomerDb);
		}
		//保存或更新 客户所在地、客户警告、合作伙伴信息
		EccCustomerFso fso = eccCustomer.getFso();
		//后去客户合作伙伴对象
		EccCustomerPartner partner = eccCustomer.getPartner();
		//获取客户警告对象
		EccCustomerWarn warn = eccCustomer.getWarn();

		fso = this.fsoService.saveOrUpdate(user,fso);
		partner = this.partnerService.saveOrUpdate(user,partner);
		warn = this.warnService.saveOrUpdate(user,warn);
		eccCustomer.setFso(fso);
		eccCustomer.setPartner(partner);
		eccCustomer.setWarn(warn);
		return eccCustomer;
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
				EccCustomer eccCustomerDb = this.eccCustomerDao.get(customerId);
				this.eccCustomerDao.delete(eccCustomerDb);
				//删除从表
				this.fsoService.deleteByCustomerId(customerId);
				this.partnerService.deleteByCustomerId(customerId);
				this.warnService.deleteByCustomerId(customerId);
			}
		}
	}

	/**
	 * 全部删除
	 * @param user
	 * @param eccId
	 */
	public void batchDelAll(UserProfile user, String eccId) {
		List<EccCustomer> customers = this.eccCustomerDao.findBy("eccId",eccId);
		if (customers!=null&&customers.size()>0){
			for (EccCustomer c:customers
				 ) {
				delete(user,c.getCustomerId());
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
		return this.eccCustomerDao.nameIsValid( customerId, customerName);
	}

}

