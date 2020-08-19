package com.supporter.prj.ppm.ecc.base_info.owner.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomer;
import com.supporter.prj.ppm.ecc.base_info.owner.dao.EccOwnerFsoDao;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwnerFso;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwnerPartner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.base_info.owner.dao.EccOwnerDao;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwner;

/**
 * @Title: Service
 * @Description: 出口管制客户.
 * @author YAN
 * @date 2019-08-16 18:39:15
 * @version V1.0
 */
@Service
public class EccOwnerService {

	@Autowired
	private EccOwnerDao eccOwnerDao;
	@Autowired
	private EccOwnerFsoService fsoService;
	@Autowired
	private EccOwnerPartnerService partnerService;
	/**
	 * 根据主键获取出口管制客户.
	 * 
	 * @param ownerId 主键
	 * @return EccOwner
	 */
	public EccOwner get(String ownerId) {
		return eccOwnerDao.get(ownerId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccOwner > getGrid(UserProfile user, JqGrid jqGrid, EccOwner eccOwner) {
		return eccOwnerDao.findPage(jqGrid, eccOwner);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param ownerId
	 * @return
	 */
	public EccOwner initEditOrViewPage(String ownerId) {
		EccOwner entity = null;
		if (StringUtils.isBlank(ownerId)) {// 新建
			entity = new EccOwner();
			ownerId = com.supporter.util.UUIDHex.newId();
			entity.setOwnerId(ownerId);
		} else {// 编辑
			entity = eccOwnerDao.get(ownerId);
			entity = initEditOrViewPage(ownerId,entity);
		}
		return entity;
	}
	public EccOwner initEditOrViewPage(String ownerId,EccOwner entity) {
		if (entity==null){
			entity = new EccOwner();
			entity.setOwnerId(ownerId);
		}
		EccOwnerFso fso = this.fsoService.findByOwnerId(ownerId);
		EccOwnerPartner partner = this.partnerService.findByOwnerId(ownerId);
		entity.setFso(fso);
		entity.setPartner(partner);
		return entity;
	}
	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccOwner 实体类
	 * @return
	 */
	public EccOwner saveOrUpdate(UserProfile user, EccOwner eccOwner) {
		if (StringUtils.isBlank(eccOwner.getOwnerId())) {// 新建
			return this.save(user, eccOwner);
		} else {// 编辑
			return this.update(user, eccOwner);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccOwner 实体类
	 * @return
	 */
	private EccOwner save(UserProfile user, EccOwner eccOwner) {
		this.eccOwnerDao.save(eccOwner);
		return eccOwner;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccOwner 实体类
	 * @return
	 */
	private EccOwner update(UserProfile user, EccOwner eccOwner) {
		EccOwner eccOwnerDb = this.eccOwnerDao.get(eccOwner.getOwnerId());
		if(eccOwnerDb == null) {
			this.save(user, eccOwner);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccOwner, eccOwnerDb);
			this.eccOwnerDao.update(eccOwnerDb);
		}
		//保存或更新从表
		EccOwnerFso fso = eccOwner.getFso();
		EccOwnerPartner partner = eccOwner.getPartner();
		fso = this.fsoService.saveOrUpdate(user,fso);
		partner = this.partnerService.saveOrUpdate(user,partner);
		eccOwner.setPartner(partner);
		eccOwner.setFso(fso);
		return eccOwner;
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param ownerIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String ownerIds) {
		if(StringUtils.isNotBlank(ownerIds)) {
			for(String ownerId : ownerIds.split(",")) {
				EccOwner eccOwnerDb = this.eccOwnerDao.get(ownerId);
				this.eccOwnerDao.delete(ownerId);
				//删除从表
				this.partnerService.deleteByOwnerId(ownerId);
				this.fsoService.deleteByOwnerId(ownerId);
			}
		}
	}
	/**
	 * 全部删除
	 * @param user
	 * @param eccId
	 */
	public void batchDelAll(UserProfile user, String eccId) {
		List<EccOwner> entitys = this.eccOwnerDao.findBy("eccId",eccId);
		if (entitys!=null&&entitys.size()>0){
			for (EccOwner c:entitys
			) {
				delete(user,c.getOwnerId());
			}
		}

	}
	/**
	 * 判断名字是否重复
	 * 
	 * @param ownerId
	 * @param ownerName
	 * @return
	 */
	public boolean nameIsValid(String ownerId,String ownerName) {
		return this.eccOwnerDao.nameIsValid( ownerId, ownerName);
	}

}

