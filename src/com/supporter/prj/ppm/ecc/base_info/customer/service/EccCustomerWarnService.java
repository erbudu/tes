package com.supporter.prj.ppm.ecc.base_info.customer.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerFso;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerPartner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.base_info.customer.dao.EccCustomerWarnDao;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerWarn;

/**
 * @Title: Service
 * @Description: 出口管制客户警告.
 * @author YAN
 * @date 2019-08-16 18:30:30
 * @version V1.0
 */
@Service
public class EccCustomerWarnService {

	@Autowired
	private EccCustomerWarnDao eccCustomerWarnDao;

	/**
	 * 根据主键获取出口管制客户警告.
	 * 
	 * @param warnId 主键
	 * @return EccCustomerWarn
	 */
	public EccCustomerWarn get(String warnId) {
		return eccCustomerWarnDao.get(warnId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccCustomerWarn > getGrid(UserProfile user, JqGrid jqGrid, EccCustomerWarn eccCustomerWarn) {
		return eccCustomerWarnDao.findPage(jqGrid, eccCustomerWarn);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param warnId
	 * @return
	 */
	public EccCustomerWarn initEditOrViewPage(String warnId) {
		if (StringUtils.isBlank(warnId)) {// 新建
			EccCustomerWarn entity = new EccCustomerWarn();
			return entity;
		} else {// 编辑
			EccCustomerWarn entity = eccCustomerWarnDao.get(warnId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccCustomerWarn 实体类
	 * @return
	 */
	public EccCustomerWarn saveOrUpdate(UserProfile user, EccCustomerWarn eccCustomerWarn) {
		if (StringUtils.isBlank(eccCustomerWarn.getWarnId())) {// 新建
			return this.save(user, eccCustomerWarn);
		} else {// 编辑
			return this.update(user, eccCustomerWarn);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccCustomerWarn 实体类
	 * @return
	 */
	private EccCustomerWarn save(UserProfile user, EccCustomerWarn eccCustomerWarn) {
		this.eccCustomerWarnDao.save(eccCustomerWarn);
		return eccCustomerWarn;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccCustomerWarn 实体类
	 * @return
	 */
	private EccCustomerWarn update(UserProfile user, EccCustomerWarn eccCustomerWarn) {
		EccCustomerWarn eccCustomerWarnDb = this.eccCustomerWarnDao.get(eccCustomerWarn.getWarnId());
		if(eccCustomerWarnDb == null) {
			return this.save(user, eccCustomerWarn);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccCustomerWarn, eccCustomerWarnDb);
			this.eccCustomerWarnDao.update(eccCustomerWarnDb);
			return eccCustomerWarnDb;
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
				EccCustomerWarn eccCustomerWarnDb = this.eccCustomerWarnDao.get(warnId);
				this.eccCustomerWarnDao.delete(warnId);
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
		return this.eccCustomerWarnDao.nameIsValid( warnId, warnName);
	}
	/**
	 * 根据客户id查询
	 * @param customerId
	 * @return
	 */
	public EccCustomerWarn findByCustomerId(String customerId) {
		List<EccCustomerWarn> list = findListByCustomerId(customerId);
		if (list!=null){
			return list.get(0);
		}else {
			EccCustomerWarn warn = new EccCustomerWarn();
			warn.setCustomerId(customerId);
			return  warn;
		}
	}

	/**
	 * 根据客户id删除
	 * @param customerId
	 */
	public void deleteByCustomerId(String customerId) {
		List<EccCustomerWarn> list = findListByCustomerId(customerId);
		if (list!=null){
			this.eccCustomerWarnDao.delete(list);
		}
	}

	public List<EccCustomerWarn> findListByCustomerId(String customerId){
		List<EccCustomerWarn> list = this.eccCustomerWarnDao.findBy("customerId",customerId);
		if (list!=null&&list.size()>0){
			return list;
		}else {
			return null;
		}
	}
}

