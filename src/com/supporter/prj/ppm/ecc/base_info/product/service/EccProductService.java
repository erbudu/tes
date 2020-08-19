package com.supporter.prj.ppm.ecc.base_info.product.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.base_info.product.dao.EccProductDao;
import com.supporter.prj.ppm.ecc.base_info.product.entity.EccProduct;

import javax.swing.text.html.parser.Entity;

/**
 * @Title: Service
 * @Description: 出口管制产品.
 * @author YAN
 * @date 2019-08-19 15:22:21
 * @version V1.0
 */
@Service
public class EccProductService {

	@Autowired
	private EccProductDao eccProductDao;

	/**
	 * 根据主键获取出口管制产品.
	 * 
	 * @param productId 主键
	 * @return EccProduct
	 */
	public EccProduct get(String productId) {
		return eccProductDao.get(productId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccProduct > getGrid(UserProfile user, JqGrid jqGrid, EccProduct eccProduct) {
		return eccProductDao.findPage(jqGrid, eccProduct);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param productId
	 * @return
	 */
	public EccProduct initEditOrViewPage(String productId) {
		if (StringUtils.isBlank(productId)) {// 新建
			EccProduct entity = new EccProduct();
			entity.setProductId(com.supporter.util.UUIDHex.newId());
			return entity;
		} else {// 编辑
			EccProduct entity = eccProductDao.get(productId);
			entity = initEditOrViewPage(productId,entity);
			return entity;
		}
	}
	public EccProduct initEditOrViewPage(String productId,EccProduct entity) {
		if (entity==null){
			entity = new EccProduct();
			entity.setProductId(productId);
		}
		return  entity;
	}
	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccProduct 实体类
	 * @return
	 */
	public EccProduct saveOrUpdate(UserProfile user, EccProduct eccProduct) {
		if (StringUtils.isBlank(eccProduct.getProductId())) {// 新建
			return this.save(user, eccProduct);
		} else {// 编辑
			return this.update(user, eccProduct);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccProduct 实体类
	 * @return
	 */
	private EccProduct save(UserProfile user, EccProduct eccProduct) {
		this.eccProductDao.save(eccProduct);
		return eccProduct;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccProduct 实体类
	 * @return
	 */
	private EccProduct update(UserProfile user, EccProduct eccProduct) {
		EccProduct eccProductDb = this.eccProductDao.get(eccProduct.getProductId());
		if(eccProductDb == null) {
			return this.save(user, eccProduct);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccProduct, eccProductDb);
			this.eccProductDao.update(eccProductDb);
			return eccProductDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param productIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String productIds) {
		if(StringUtils.isNotBlank(productIds)) {
			for(String productId : productIds.split(",")) {
				EccProduct eccProductDb = this.eccProductDao.get(productId);
				this.eccProductDao.delete(productId);
			}
		}
	}
	/**
	 * 全部删除
	 * @param user
	 * @param eccId
	 */
	public void batchDelAll(UserProfile user, String eccId) {
		this.eccProductDao.deleteByProperty("eccId",eccId);
	}
	/**
	 * 判断名字是否重复
	 * 
	 * @param productId
	 * @param productName
	 * @return
	 */
	public boolean nameIsValid(String productId,String productName) {
		return this.eccProductDao.nameIsValid( productId, productName);
	}

}

