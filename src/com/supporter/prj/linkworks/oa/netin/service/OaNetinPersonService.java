package com.supporter.prj.linkworks.oa.netin.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.netin.dao.OaNetinPersonDao;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetin;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetinPerson;

@Service
@Transactional(TransManager.APP)
public class OaNetinPersonService {
	@Autowired
	private OaNetinPersonDao netinPersonDao;
	
	/**
	 * 根据主键id获得实体
	 * @param id
	 * @return
	 */
	public OaNetinPerson get(String id){
		return netinPersonDao.get(id);
	}
	
	/**
	 * 进入新建或编辑页面需要加载的信息
	 * @param netinId
	 * @param id
	 * @param user
	 * @return
	 */
	public OaNetinPerson initEditOrViewPage(String netinId, String id, UserProfile user){
		OaNetinPerson netinPerson = new OaNetinPerson();
		if (StringUtils.isBlank(id)){//新建
			netinPerson.setId(com.supporter.util.UUIDHex.newId());
			netinPerson.setNetinId(netinId);
			netinPerson.setIsNew(true);
		}else{//编辑
			netinPerson = netinPersonDao.get(id);
			netinPerson.setIsNew(false);
		}
		return netinPerson;
	}
	
	/**
	 * 获取人员列表
	 * @param jqGrid
	 * @param netin
	 * @return
	 */
	public List<OaNetinPerson> getGrid(JqGrid jqGrid, String netinId){
		return netinPersonDao.findPage(jqGrid, netinId);
	}
	
	/**
	 * 保存或更新
	 * @param netinPerson
	 * @return
	 */
	public OaNetinPerson saveOrUpdate(OaNetinPerson netinPerson){
		if (netinPerson != null){
			if (netinPerson.getIsNew()){
				this.netinPersonDao.save(netinPerson);
			}else{
				this.netinPersonDao.update(netinPerson);
			}
		}
		return netinPerson;
	}
	
	/**
	 * 删除人员信息
	 * @param netinPerson
	 */
	public void deleteNetinPerson(String id){
		if (StringUtils.isNotBlank(id)){
			this.netinPersonDao.delete(id);
		}
	}
	
	/**
	 * 删除入网申请单下所有人员信息
	 * @param netin
	 */
	public void deleteAllNetinPerson(String netinId){
		if (StringUtils.isNotBlank(netinId)){
			this.netinPersonDao.deletedInner(netinId);
		}
	}
	
	/**
	 * 判断维护单下离退休职工人数
	 * @param netinId
	 * @return
	 */
	public Integer getRetirementCount(String netinId){
		List<OaNetinPerson> list = this.netinPersonDao.getRetirementList(netinId);
		if (list.size() > 0){
			return list.size();
		}
		return 0;
	}
	
	/**
	 * 判断维护单下离退休职工人数
	 * @param netinId
	 * @return
	 */
	public Integer getNotRetirementCount(String netinId){
		List<OaNetinPerson> allPersonList = this.netinPersonDao.getNetinPerosnListByNetinId(netinId);
		List<OaNetinPerson> retirementList = this.netinPersonDao.getRetirementList(netinId);
		Integer allPerosnCount = allPersonList == null ? 0 : allPersonList.size();
		Integer retirementCount = retirementList == null ? 0 : retirementList.size();
		return allPerosnCount - retirementCount;
	}
	
	
	/**
	 * 获取维护单下外聘人员
	 * @param netinId
	 * @return
	 */
	public List<OaNetinPerson> getOutsidePerosn(String netinId){
		List<OaNetinPerson> list = this.netinPersonDao.getOutsidePersonList(netinId);
		if (list != null){
			return list;
		}
		return null;
	}
}
