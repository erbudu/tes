package com.supporter.prj.ppm.ecc.base_info.owner.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.base_info.owner.dao.EccOwnerFsoDao;
import com.supporter.prj.ppm.ecc.base_info.owner.entity.EccOwnerFso;

/**
 * @Title: Service
 * @Description: 出口管制主办事处.
 * @author YAN
 * @date 2019-08-16 18:39:16
 * @version V1.0
 */
@Service
public class EccOwnerFsoService {

	@Autowired
	private EccOwnerFsoDao eccOwnerFsoDao;

	/**
	 * 根据主键获取出口管制主办事处.
	 * 
	 * @param fsoId 主键
	 * @return EccOwnerFso
	 */
	public EccOwnerFso get(String fsoId) {
		return eccOwnerFsoDao.get(fsoId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccOwnerFso > getGrid(UserProfile user, JqGrid jqGrid, EccOwnerFso eccOwnerFso) {
		return eccOwnerFsoDao.findPage(jqGrid, eccOwnerFso);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param fsoId
	 * @return
	 */
	public EccOwnerFso initEditOrViewPage(String fsoId) {
		if (StringUtils.isBlank(fsoId)) {// 新建
			EccOwnerFso entity = new EccOwnerFso();
			return entity;
		} else {// 编辑
			EccOwnerFso entity = eccOwnerFsoDao.get(fsoId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccOwnerFso 实体类
	 * @return
	 */
	public EccOwnerFso saveOrUpdate(UserProfile user, EccOwnerFso eccOwnerFso) {
		if (StringUtils.isBlank(eccOwnerFso.getFsoId())) {// 新建
			return this.save(user, eccOwnerFso);
		} else {// 编辑
			return this.update(user, eccOwnerFso);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccOwnerFso 实体类
	 * @return
	 */
	private EccOwnerFso save(UserProfile user, EccOwnerFso eccOwnerFso) {
		this.eccOwnerFsoDao.save(eccOwnerFso);
		return eccOwnerFso;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccOwnerFso 实体类
	 * @return
	 */
	private EccOwnerFso update(UserProfile user, EccOwnerFso eccOwnerFso) {
		EccOwnerFso eccOwnerFsoDb = this.eccOwnerFsoDao.get(eccOwnerFso.getFsoId());
		if(eccOwnerFsoDb == null) {
			return this.save(user, eccOwnerFso);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccOwnerFso, eccOwnerFsoDb);
			this.eccOwnerFsoDao.update(eccOwnerFsoDb);
			return eccOwnerFsoDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param fsoIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String fsoIds) {
		if(StringUtils.isNotBlank(fsoIds)) {
			for(String fsoId : fsoIds.split(",")) {
				EccOwnerFso eccOwnerFsoDb = this.eccOwnerFsoDao.get(fsoId);
				this.eccOwnerFsoDao.delete(fsoId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param fsoId
	 * @param fsoName
	 * @return
	 */
	public boolean nameIsValid(String fsoId,String fsoName) {
		return this.eccOwnerFsoDao.nameIsValid( fsoId, fsoName);
	}

    public EccOwnerFso findByOwnerId(String ownerId) {
		List<EccOwnerFso> list = this.findListByOwnerId(ownerId);
		if (list!=null){
			return list.get(0);
		}else{
			EccOwnerFso fso = new EccOwnerFso();
			fso.setOwnerId(ownerId);
			return fso;
		}
    }

    public List<EccOwnerFso> findListByOwnerId(String ownerId){
		List<EccOwnerFso> list = this.eccOwnerFsoDao.findBy("ownerId",ownerId);
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
			List<EccOwnerFso> list = findListByOwnerId(ownerId);
			if (list!=null){
				this.eccOwnerFsoDao.delete(list);
			}
		}
	}
}

