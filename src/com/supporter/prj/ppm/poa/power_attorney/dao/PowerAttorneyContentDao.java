package com.supporter.prj.ppm.poa.power_attorney.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.ppm.poa.power_attorney.entity.PowerAttorneyContent;

/**   
 * @Title: Dao
 * @Description: 
 * @author GuoXiansheng
 * @date 2019-09-25 14:00:07
 * @version V1.0   
 *
 */
@Repository
public class PowerAttorneyContentDao extends MainDaoSupport < PowerAttorneyContent, String > {
	
	/**
	 * 按授权书id查询对应授权书的内容对象信息
	 * @param laId
	 * @return
	 */
	public PowerAttorneyContent selectPowerAttorneyContentByLaId(String laId){
		PowerAttorneyContent powerAttorneyContent = this.get(laId);
		return powerAttorneyContent;
	}
	/**
	 * 获取所有授权书内容列表
	 * @return
	 */
	public List<PowerAttorneyContent> selectPowerAttorneyContentALL(){
		String hql = "from PowerAttorneyContent ";
		List<PowerAttorneyContent> list = this.find(hql);
		return list;
	}
}
