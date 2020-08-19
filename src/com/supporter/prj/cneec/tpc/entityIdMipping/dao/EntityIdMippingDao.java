package com.supporter.prj.cneec.tpc.entityIdMipping.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.cneec.tpc.entityIdMipping.entity.EntityIdMipping;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;

/**
 * @Title: Entity
 * @Description: 贸易主键映射表.
 * @date 2019-07-16 17:04:35
 * @version V1.0
 */
@Repository
public class EntityIdMippingDao extends MainDaoSupport<EntityIdMipping, String> {

	/**
	 * 获取最大的entityId
	 * @return
	 */
	public long getMaxEntityId() {
		long maxId = 0L;
		String ls_sql = "select max(entityId) from " + EntityIdMipping.class.getName();
		List<Long> list = this.find(ls_sql);
		if (list.size() > 0 && list != null) {
			if (list.get(0) != null) {
				maxId = list.get(0);
			}
		}
		return maxId;
	}

}

