package com.supporter.prj.ppm.ecc.base_info.partner.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.base_info.partner.dao.EccPartnerFsoDao;
import com.supporter.prj.ppm.ecc.base_info.partner.entity.EccPartnerFso;

/**
 * @Title: Service
 * @Description: 出口管制项目合同伙伴办事处.
 * @author YAN
 * @date 2019-08-16 18:34:19
 * @version V1.0
 */
@Service
public class EccPartnerFsoService {

	@Autowired
	private EccPartnerFsoDao eccPartnerFsoDao;

	/**
	 * 根据主键获取出口管制项目合同伙伴办事处.
	 * 
	 * @param fsoId 主键
	 * @return EccPartnerFso
	 */
	public EccPartnerFso get(String fsoId) {
		return eccPartnerFsoDao.get(fsoId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccPartnerFso > getGrid(UserProfile user, JqGrid jqGrid, EccPartnerFso eccPartnerFso) {
		return eccPartnerFsoDao.findPage(jqGrid, eccPartnerFso);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param fsoId
	 * @return
	 */
	public EccPartnerFso initEditOrViewPage(String fsoId) {
		if (StringUtils.isBlank(fsoId)) {// 新建
			EccPartnerFso entity = new EccPartnerFso();
			return entity;
		} else {// 编辑
			EccPartnerFso entity = eccPartnerFsoDao.get(fsoId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccPartnerFso 实体类
	 * @return
	 */
	public EccPartnerFso saveOrUpdate(UserProfile user, EccPartnerFso eccPartnerFso) {
		if (StringUtils.isBlank(eccPartnerFso.getFsoId())) {// 新建
			return this.save(user, eccPartnerFso);
		} else {// 编辑
			return this.update(user, eccPartnerFso);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccPartnerFso 实体类
	 * @return
	 */
	private EccPartnerFso save(UserProfile user, EccPartnerFso eccPartnerFso) {
		this.eccPartnerFsoDao.save(eccPartnerFso);
		return eccPartnerFso;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccPartnerFso 实体类
	 * @return
	 */
	private EccPartnerFso update(UserProfile user, EccPartnerFso eccPartnerFso) {
		EccPartnerFso eccPartnerFsoDb = this.eccPartnerFsoDao.get(eccPartnerFso.getFsoId());
		if(eccPartnerFsoDb == null) {
			return this.save(user, eccPartnerFso);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccPartnerFso, eccPartnerFsoDb);
			this.eccPartnerFsoDao.update(eccPartnerFsoDb);
			return eccPartnerFsoDb;
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
				EccPartnerFso eccPartnerFsoDb = this.eccPartnerFsoDao.get(fsoId);
				this.eccPartnerFsoDao.delete(fsoId);
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
		return this.eccPartnerFsoDao.nameIsValid( fsoId, fsoName);
	}
	/**
	 * 根据合作伙伴id获取d对象
	 * @param partnerId
	 * @return
	 */
    public EccPartnerFso findByPartnerId(String partnerId) {
		List <EccPartnerFso> list = findListByPartnerId(partnerId);
		if (list!=null){
			return list.get(0);
		}
		EccPartnerFso fso = new  EccPartnerFso();
		fso.setPartnerId(partnerId);
		return fso;
    }

	/**
	 * 根据合作伙伴id获取列表
	 * @param partnerId
	 * @return
	 */
    public  List<EccPartnerFso> findListByPartnerId(String partnerId){
		List<EccPartnerFso> list = this.eccPartnerFsoDao.findBy("partnerId",partnerId);
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
			List<EccPartnerFso> list = findListByPartnerId(partnerId);
			if (list!=null){
				this.eccPartnerFsoDao.delete(list);
			}
		}
	}
}

