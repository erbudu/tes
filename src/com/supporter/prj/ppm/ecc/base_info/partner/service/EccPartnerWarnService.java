package com.supporter.prj.ppm.ecc.base_info.partner.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerFso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.base_info.partner.dao.EccPartnerWarnDao;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerWarn;

/**
 * @Title: Service
 * @Description: 出口管制合伙人警告.
 * @author YAN
 * @date 2019-08-16 18:34:22
 * @version V1.0
 */
@Service
public class EccPartnerWarnService {

	@Autowired
	private EccPartnerWarnDao eccPartnerWarnDao;

	/**
	 * 根据主键获取出口管制合伙人警告.
	 * 
	 * @param warnId 主键
	 * @return EccPartnerWarn
	 */
	public EccPartnerWarn get(String warnId) {
		return eccPartnerWarnDao.get(warnId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccPartnerWarn > getGrid(UserProfile user, JqGrid jqGrid, EccPartnerWarn eccPartnerWarn) {
		return eccPartnerWarnDao.findPage(jqGrid, eccPartnerWarn);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param warnId
	 * @return
	 */
	public EccPartnerWarn initEditOrViewPage(String warnId) {
		if (StringUtils.isBlank(warnId)) {// 新建
			EccPartnerWarn entity = new EccPartnerWarn();
			return entity;
		} else {// 编辑
			EccPartnerWarn entity = eccPartnerWarnDao.get(warnId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccPartnerWarn 实体类
	 * @return
	 */
	public EccPartnerWarn saveOrUpdate(UserProfile user, EccPartnerWarn eccPartnerWarn) {
		if (StringUtils.isBlank(eccPartnerWarn.getWarnId())) {// 新建
			return this.save(user, eccPartnerWarn);
		} else {// 编辑
			return this.update(user, eccPartnerWarn);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccPartnerWarn 实体类
	 * @return
	 */
	private EccPartnerWarn save(UserProfile user, EccPartnerWarn eccPartnerWarn) {
		this.eccPartnerWarnDao.save(eccPartnerWarn);
		return eccPartnerWarn;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccPartnerWarn 实体类
	 * @return
	 */
	private EccPartnerWarn update(UserProfile user, EccPartnerWarn eccPartnerWarn) {
		EccPartnerWarn eccPartnerWarnDb = this.eccPartnerWarnDao.get(eccPartnerWarn.getWarnId());
		if(eccPartnerWarnDb == null) {
			return this.save(user, eccPartnerWarn);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccPartnerWarn, eccPartnerWarnDb);
			this.eccPartnerWarnDao.update(eccPartnerWarnDb);
			return eccPartnerWarnDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param warnIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String warnIds) {
		if(StringUtils.isNotBlank(warnIds)) {
			for(String warnId : warnIds.split(",")) {
				EccPartnerWarn eccPartnerWarnDb = this.eccPartnerWarnDao.get(warnId);
				this.eccPartnerWarnDao.delete(warnId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param warnId
	 * @param warnName
	 * @return
	 */
	public boolean nameIsValid(String warnId,String warnName) {
		return this.eccPartnerWarnDao.nameIsValid( warnId, warnName);
	}

	/**
	 * 根据合作伙伴id获取d对象
	 * @param partnerId
	 * @return
	 */
	public EccPartnerWarn findByPartnerId(String partnerId) {
		List <EccPartnerWarn> list = findListByPartnerId(partnerId);
		if (list!=null){
			return list.get(0);
		}
		EccPartnerWarn warn = new EccPartnerWarn();
		warn.setPartnerId(partnerId);
		return warn;
	}

	/**
	 * 根据合作伙伴id获取列表
	 * @param partnerId
	 * @return
	 */
	public  List<EccPartnerWarn> findListByPartnerId(String partnerId){
		List<EccPartnerWarn> list = this.eccPartnerWarnDao.findBy("partnerId",partnerId);
		if (list!=null&&list.size()>0){
			return  list;
		}else {
			return null;
		}
	}

	/**
	 * 按客户id删除
	 *
	 * @param partnerId 客户id
	 */
	public void deleteByPartnerId(String partnerId) {
		if(StringUtils.isNotBlank(partnerId)) {
			List<EccPartnerWarn> list = findListByPartnerId(partnerId);
			if (list!=null){
				this.eccPartnerWarnDao.delete(list);
			}
		}
	}
}

