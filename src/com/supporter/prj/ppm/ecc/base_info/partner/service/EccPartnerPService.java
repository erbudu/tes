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

import com.supporter.prj.ppm.ecc.base_info.partner.dao.EccPartnerPDao;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerP;

/**
 * @Title: Service
 * @Description: 出口管制项目合伙人合作伙伴.
 * @author YAN
 * @date 2019-08-16 18:34:21
 * @version V1.0
 */
@Service
public class EccPartnerPService {

	@Autowired
	private EccPartnerPDao eccPartnerPDao;

	/**
	 * 根据主键获取出口管制项目合伙人合作伙伴.
	 * 
	 * @param partnerPId 主键
	 * @return EccPartnerP
	 */
	public EccPartnerP get(String partnerPId) {
		return eccPartnerPDao.get(partnerPId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccPartnerP > getGrid(UserProfile user, JqGrid jqGrid, EccPartnerP eccPartnerP) {
		return eccPartnerPDao.findPage(jqGrid, eccPartnerP);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param partnerPId
	 * @return
	 */
	public EccPartnerP initEditOrViewPage(String partnerPId) {
		if (StringUtils.isBlank(partnerPId)) {// 新建
			EccPartnerP entity = new EccPartnerP();
			return entity;
		} else {// 编辑
			EccPartnerP entity = eccPartnerPDao.get(partnerPId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccPartnerP 实体类
	 * @return
	 */
	public EccPartnerP saveOrUpdate(UserProfile user, EccPartnerP eccPartnerP) {
		if (StringUtils.isBlank(eccPartnerP.getPartnerPId())) {// 新建
			return this.save(user, eccPartnerP);
		} else {// 编辑
			return this.update(user, eccPartnerP);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccPartnerP 实体类
	 * @return
	 */
	private EccPartnerP save(UserProfile user, EccPartnerP eccPartnerP) {
		this.eccPartnerPDao.save(eccPartnerP);
		return eccPartnerP;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccPartnerP 实体类
	 * @return
	 */
	private EccPartnerP update(UserProfile user, EccPartnerP eccPartnerP) {
		EccPartnerP eccPartnerPDb = this.eccPartnerPDao.get(eccPartnerP.getPartnerPId());
		if(eccPartnerPDb == null) {
			return this.save(user, eccPartnerP);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccPartnerP, eccPartnerPDb);
			this.eccPartnerPDao.update(eccPartnerPDb);
			return eccPartnerPDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param partnerPIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String partnerPIds) {
		if(StringUtils.isNotBlank(partnerPIds)) {
			for(String partnerPId : partnerPIds.split(",")) {
				EccPartnerP eccPartnerPDb = this.eccPartnerPDao.get(partnerPId);
				this.eccPartnerPDao.delete(eccPartnerPDb);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param partnerPId
	 * @param partnerPName
	 * @return
	 */
	public boolean nameIsValid(String partnerPId,String partnerPName) {
		return this.eccPartnerPDao.nameIsValid( partnerPId, partnerPName);
	}

	/**
	 * 根据合作伙伴id获取d对象
	 * @param partnerId
	 * @return
	 */
	public EccPartnerP findByPartnerId(String partnerId) {
		List <EccPartnerP> list = findListByPartnerId(partnerId);
		if (list!=null){
			return list.get(0);
		}
		EccPartnerP partner = new EccPartnerP();
		partner.setPartnerId(partnerId);
		return partner;
	}

	/**
	 * 根据合作伙伴id获取列表
	 * @param partnerId
	 * @return
	 */
	public  List<EccPartnerP> findListByPartnerId(String partnerId){
		List<EccPartnerP> list = this.eccPartnerPDao.findBy("partnerId",partnerId);
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
			List<EccPartnerP> list = findListByPartnerId(partnerId);
			if (list!=null){
				this.eccPartnerPDao.delete(list);
			}
		}
	}
}

