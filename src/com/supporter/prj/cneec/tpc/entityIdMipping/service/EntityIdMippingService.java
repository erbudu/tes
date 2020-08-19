package com.supporter.prj.cneec.tpc.entityIdMipping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.cneec.tpc.entityIdMipping.dao.EntityIdMippingDao;
import com.supporter.prj.cneec.tpc.entityIdMipping.entity.EntityIdMipping;

/**
 * @Title: Service
 * @Description: 贸易主键映射表.
 * @date 2019-07-16 17:04:35
 * @version V1.0
 */
@Service
public class EntityIdMippingService {

	@Autowired
	private EntityIdMippingDao entityIdMippingDao;

	/**
	 * 根据主键获取贸易主键映射表.
	 * 
	 * @param tpcEntityId 主键
	 * @return EntityIdMipping
	 */
	public EntityIdMipping get(String tpcEntityId) {
		return entityIdMippingDao.get(tpcEntityId);
	}
	
	/**
	 * 创建主键映射
	 * @param tpcEntityId
	 * @param moduleName
	 * @return
	 */
	public EntityIdMipping createEntityIdMipping(String tpcEntityId, String moduleName) {
		if (StringUtils.isNotBlank(tpcEntityId)) {
			EntityIdMipping entity = this.get(tpcEntityId);
			if (entity == null) {
				entity = new EntityIdMipping();
				entity.setTpcEntityId(tpcEntityId);
				entity.setEntityId(initEntityId());
				entity.setModuleName(moduleName);
				this.entityIdMippingDao.save(entity);
			}
			return entity;
		}
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "创建主键映射失败，tpcEntityId为空！");
		return null;
	}
	
	/**
	 * 初始化entityId
	 * @return
	 */
	public long initEntityId() {
		long maxEntityId = this.entityIdMippingDao.getMaxEntityId();
		if (maxEntityId != 0L) {
			maxEntityId += 1;
		}else {
			maxEntityId = 9900000001L;
		}
		return maxEntityId;
	}

}

