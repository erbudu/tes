package com.supporter.prj.eip.module.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip.module.entity.ModuleHandler;
import com.supporter.prj.eip.module.entity.ModulePage;
import com.supporter.prj.eip.module.util.MessageFormat;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.module.entity.IModule;
import com.supporter.prj.eip_service.module.entity.IModuleHandler;

/**   
 * @Title: Entity
 * @Description: 服务实现类.
 * @author gongjiwen
 * @date 2016-12-21 10:26:27
 * @version V1.0   
 *
 */
@Repository
public class ModuleHandlerDao extends MainDaoSupport< ModuleHandler, String > {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param moduleIds 模块ids
	 * @return
	 */
	public List< ModuleHandler > findPage(JqGrid jqGrid, ModuleHandler moduleHandler, Set<String> moduleIds) {
		String handlerName = moduleHandler.getHandlerName ();
		if (StringUtils.isNotBlank(handlerName)) {
			jqGrid.addHqlFilter(
					"handlerName like ? or displayNameZh like ? or displayNameEn like ? or displayName3 like ? or displayName4 like ?"
					+ " or synonymZh like ? or synonymEn like ? or synonym3 like ? or synonym4 like ?",
					"%" + handlerName+ "%", "%" + handlerName+ "%", "%" + handlerName+ "%", "%" + handlerName+ "%", "%" + handlerName+ "%",
					"%" + handlerName+ "%", "%" + handlerName+ "%", "%" + handlerName+ "%", "%" + handlerName+ "%");
		}
		if (CollectionUtils.isNotEmpty(moduleIds)) {
			StringBuffer sb = new StringBuffer();
			int i = 1;
			for(String moduleId : moduleIds){
				if(i < moduleIds.size()){
					sb.append("moduleId = ? or ");
				}else{
					sb.append("moduleId = ?");
				}
				i ++;
			}
			jqGrid.addHqlFilter(sb.toString(), moduleIds.toArray());
		}
		return this.retrievePage(jqGrid);
	}
	/**
	 * 判断名字是否重复
	 * 
	 * @param handlerId
	 * @param handlerName
	 * @return
	 */
	public boolean checkNameIsValid(String moduleId, String handlerId,String handlerName){
		String hql = null;
		List retList = null;
		if(StringUtils.isBlank(handlerId)){//新建时
			hql = "from " + ModuleHandler.class.getName() + " where  handlerName = ? and moduleId = ?";
			retList = this.retrieve(hql, handlerName, moduleId);
		}else{//编辑时
			hql = "from " + ModuleHandler.class.getName() + " where handlerId != ? and handlerName = ? and moduleId";
			retList = this.retrieve(hql, handlerId, handlerName, moduleId);
		}
		if(CollectionUtils.isEmpty(retList)){
			return true;
		}
		return false;
	}
	public ModuleHandler getByName(String moduleId, String handlerName){
		String hql = "from " + ModuleHandler.class.getName() + " where moduleId = ? and handlerName = ?";
		List < ModuleHandler > list = this.find(hql, moduleId, handlerName);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}
	
	
	public List< String > getHandlerIds(String moduleId) {
		StringBuffer hql = new StringBuffer();
		hql.append("select handlerId from ").append(ModuleHandler.class.getName());
		
		hql.append(" where moduleId = ?");
		return this.find(hql.toString(), moduleId);
	}
	
	public IModuleHandler getHandler(String moduleId,
			String handlerDisplayOrSynonymName) {
		String hql = "from " + ModuleHandler.class.getName() + " where moduleId = ? and (handlerName = ? or displayNameZh = ? or displayNameEn = ?"
				+ " or displayName3 = ? or displayName4 = ? or synonymZh like ? or synonymEn like ? or synonym3 like ? or synonym4 like ?)";
		List< ModuleHandler > list = this.find(hql, moduleId, handlerDisplayOrSynonymName, handlerDisplayOrSynonymName, handlerDisplayOrSynonymName,handlerDisplayOrSynonymName, handlerDisplayOrSynonymName,
				"%" + handlerDisplayOrSynonymName + "%", "%" + handlerDisplayOrSynonymName + "%", "%" + handlerDisplayOrSynonymName + "%", "%" + handlerDisplayOrSynonymName + "%" );
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		if(list.size() > 1){
			EIPService.getLogService(ModuleConstant.LOG_TYPE_DEBUG).error(MessageFormat.format("找到moduleId={},handlerDisplayOrSynonymName={}的记录数为[]条！", moduleId,
					handlerDisplayOrSynonymName, list.size()));
		}
		return list.get(0);
	}
}

