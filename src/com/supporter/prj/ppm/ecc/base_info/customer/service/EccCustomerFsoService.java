package com.supporter.prj.ppm.ecc.base_info.customer.service;

import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.base_info.customer.dao.EccCustomerFsoDao;
import com.supporter.prj.ppm.ecc.base_info.customer.entity.EccCustomerFso;

/**
 * @Title: Service
 * @Description: 出口管制客户办事处.
 * @author YAN
 * @date 2019-08-16 18:30:28
 * @version V1.0
 */
@Service
public class EccCustomerFsoService {

	@Autowired
	private EccCustomerFsoDao eccCustomerFsoDao;

	/**
	 * 根据主键获取出口管制客户办事处.
	 * 
	 * @param fsoId 主键
	 * @return EccCustomerFso
	 */
	public EccCustomerFso get(String fsoId) {
		return eccCustomerFsoDao.get(fsoId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccCustomerFso > getGrid(UserProfile user, JqGrid jqGrid, EccCustomerFso eccCustomerFso) {
		return eccCustomerFsoDao.findPage(jqGrid, eccCustomerFso);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param fsoId
	 * @return
	 */
	public EccCustomerFso initEditOrViewPage(String fsoId) {
		if (StringUtils.isBlank(fsoId)) {// 新建
			EccCustomerFso entity = new EccCustomerFso();
			return entity;
		} else {// 编辑
			EccCustomerFso entity = eccCustomerFsoDao.get(fsoId);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccCustomerFso 实体类
	 * @return
	 */
	public EccCustomerFso saveOrUpdate(UserProfile user, EccCustomerFso eccCustomerFso) {
		if (StringUtils.isBlank(eccCustomerFso.getFsoId())) {// 新建
			return this.save(user, eccCustomerFso);
		} else {// 编辑
			return this.update(user, eccCustomerFso);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccCustomerFso 实体类
	 * @return
	 */
	private EccCustomerFso save(UserProfile user, EccCustomerFso eccCustomerFso) {
		this.eccCustomerFsoDao.save(eccCustomerFso);
		return eccCustomerFso;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccCustomerFso 实体类
	 * @return
	 */
	private EccCustomerFso update(UserProfile user, EccCustomerFso eccCustomerFso) {
		EccCustomerFso eccCustomerFsoDb = this.eccCustomerFsoDao.get(eccCustomerFso.getFsoId());
		if(eccCustomerFsoDb == null) {
			return this.save(user, eccCustomerFso);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccCustomerFso, eccCustomerFsoDb);
			this.eccCustomerFsoDao.update(eccCustomerFsoDb);
			return eccCustomerFsoDb;
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
				EccCustomerFso eccCustomerFsoDb = this.eccCustomerFsoDao.get(fsoId);
				this.eccCustomerFsoDao.delete(fsoId);
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
		return this.eccCustomerFsoDao.nameIsValid( fsoId, fsoName);
	}

	/**
	 * 根据客户id查询 对应的所在地
	 * @param customerId
	 * @return
	 */
    public EccCustomerFso findByCustomerId(String customerId) {
    	List<EccCustomerFso> list = findListByCustomerId(customerId);
    	if (list!=null){
    		return list.get(0);
		}else {
    		EccCustomerFso fso = new EccCustomerFso();
    		fso.setCustomerId(customerId);
    		return  fso;
		}
    }

	/**
	 * 根据客户id删除
	 * @param customerId
	 */
	public void deleteByCustomerId(String customerId) {
		List<EccCustomerFso> list = findListByCustomerId(customerId);
		if (list!=null){
			this.eccCustomerFsoDao.delete(list);
		}
	}

	public List<EccCustomerFso> findListByCustomerId(String customerId){
		List<EccCustomerFso> list = this.eccCustomerFsoDao.findBy("customerId",customerId);
		if (list!=null&&list.size()>0){
			return list;
		}else {
			return null;
		}
	}
}

