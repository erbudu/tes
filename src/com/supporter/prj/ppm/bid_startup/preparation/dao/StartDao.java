/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.ppm.bid_startup.preparation.entity.StartEntity;

/**
 *<p>Title: 投议标备案及许可->申报准备->启动申报持久层</p>
 *<p>Description:对应数据库操作，做数据的增删该查操作，由service层调用</p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年8月13日
 * 
 */
@Repository
public class StartDao extends MainDaoSupport<StartEntity, String>{
	
	public StartEntity findEntityByPrjId(String prjId) {
		StartEntity entity = this.findUniqueResult("prjId", prjId);
		return entity;
		
	}
	
}
