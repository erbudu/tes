package com.supporter.prj.ppm.ecc.base_info.owner.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwnerFso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.base_info.owner.dao.EccOwnerPartnerDao;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwnerPartner;

/**
 * @Title: Service
 * @Description: 出口管制客户的合伙人.
 * @author YAN
 * @date 2019-08-16 18:39:17
 * @version V1.0
 */
@Service
public class EccOwnerPartnerService {

	@Autowired
	private EccOwnerPartnerDao eccOwnerPartnerDao;

	/**
	 * 根据主键获取出口管制客户的合伙人.
	 * 
	 * @param ownerPartnerId 主键
	 * @return EccOwnerPartner
	 */
	public EccOwnerPartner get(String ownerPartnerId) {
		return eccOwnerPartnerDao.get(ownerPartnerId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccOwnerPartner > getGrid(UserProfile user, JqGrid jqGrid, EccOwnerPartner eccOwnerPartner) {
		return eccOwnerPartnerDao.findPage(jqGrid, eccOwnerPartner);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param ownerPartnerId
	 * @return
	 */
	public EccOwnerPartner initEditOrViewPage(String ownerPartnerId) {
		if (StringUtils.isBlank(ownerPartnerId)) {// 新建
			EccOwnerPartner entity = new EccOwnerPartner();
			return entity;
		} else {// 编辑
			EccOwnerPartner entity = eccOwnerPartnerDao.get(ownerPartnerId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccOwnerPartner 实体类
	 * @return
	 */
	public EccOwnerPartner saveOrUpdate(UserProfile user, EccOwnerPartner eccOwnerPartner) {
		if (StringUtils.isBlank(eccOwnerPartner.getOwnerPartnerId())) {// 新建
			return this.save(user, eccOwnerPartner);
		} else {// 编辑
			return this.update(user, eccOwnerPartner);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccOwnerPartner 实体类
	 * @return
	 */
	private EccOwnerPartner save(UserProfile user, EccOwnerPartner eccOwnerPartner) {
		this.eccOwnerPartnerDao.save(eccOwnerPartner);
		return eccOwnerPartner;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccOwnerPartner 实体类
	 * @return
	 */
	private EccOwnerPartner update(UserProfile user, EccOwnerPartner eccOwnerPartner) {
		EccOwnerPartner eccOwnerPartnerDb = this.eccOwnerPartnerDao.get(eccOwnerPartner.getOwnerPartnerId());
		if(eccOwnerPartnerDb == null) {
			return this.save(user, eccOwnerPartner);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccOwnerPartner, eccOwnerPartnerDb);
			this.eccOwnerPartnerDao.update(eccOwnerPartnerDb);
			return eccOwnerPartnerDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param ownerPartnerIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String ownerPartnerIds) {
		if(StringUtils.isNotBlank(ownerPartnerIds)) {
			for(String ownerPartnerId : ownerPartnerIds.split(",")) {
				EccOwnerPartner eccOwnerPartnerDb = this.eccOwnerPartnerDao.get(ownerPartnerId);
				this.eccOwnerPartnerDao.delete(ownerPartnerId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param ownerPartnerId
	 * @param ownerPartnerName
	 * @return
	 */
	public boolean nameIsValid(String ownerPartnerId,String ownerPartnerName) {
		return this.eccOwnerPartnerDao.nameIsValid( ownerPartnerId, ownerPartnerName);
	}

	public EccOwnerPartner findByOwnerId(String ownerId) {
		List<EccOwnerPartner> list = this.findListByOwnerId(ownerId);
		if (list!=null){
			return list.get(0);
		}else{
			EccOwnerPartner fso = new EccOwnerPartner();
			fso.setOwnerId(ownerId);
			return fso;
		}
	}

	public List<EccOwnerPartner> findListByOwnerId(String ownerId){
		List<EccOwnerPartner> list = this.eccOwnerPartnerDao.findBy("ownerId",ownerId);
		if (list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	/**
	 * 根据用户id删除
	 *
	 * @param user 用户信息
	 * @param fsoIds 主键集合，多个以逗号分隔
	 */
	public void deleteByOwnerId(String ownerId) {
		if(StringUtils.isNotBlank(ownerId)) {
			List<EccOwnerPartner> list = findListByOwnerId(ownerId);
			if (list!=null){
				this.eccOwnerPartnerDao.delete(list);
			}
		}
	}
}

