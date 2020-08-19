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
import com.supporter.prj.eip.module.entity.ModuleDomainObject;
import com.supporter.prj.eip.module.entity.ModuleDomainObjectAttr;
import com.supporter.prj.eip.module.util.MessageFormat;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.module.entity.IDomainObjectAttr;

/**   
 * @Title: Entity
 * @Description: 业务对象属性.
 * @author gongjiwen
 * @date 2016-12-19 15:28:11
 * @version V1.0   
 *
 */
@Repository
public class ModuleDomainObjectAttrDao extends MainDaoSupport<ModuleDomainObjectAttr, String> {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param moduleIds 模块ids
	 * @return
	 */
	public List<ModuleDomainObjectAttr> findPage(JqGrid jqGrid,
			ModuleDomainObjectAttr moduleDomainObjectAttr,
			Set<String> moduleIds) {
		String attrName = moduleDomainObjectAttr.getAttrName ();
		if (StringUtils.isNotBlank(attrName)) {
			jqGrid.addHqlFilter(
					"attrName like ? or displayNameZh like ? or displayNameEn like ? or displayName3 like ? or displayName4 like ?"
					+ " or synonymZh like ? or synonymEn like ? or synonym3 like ? or synonym4 like ?",
					"%" + attrName+ "%", "%" + attrName+ "%", "%" + attrName+ "%", "%" + attrName+ "%", "%" + attrName+ "%",
					"%" + attrName+ "%", "%" + attrName+ "%", "%" + attrName+ "%", "%" + attrName+ "%");
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
		if(StringUtils.isNotBlank(moduleDomainObjectAttr.getDomainObjectId())){
			jqGrid.addEq("domainObjectId", moduleDomainObjectAttr.getDomainObjectId());
		}
		if (StringUtils.isNotBlank(moduleDomainObjectAttr.getTableId())) {
			jqGrid.addEq("tableId", moduleDomainObjectAttr.getTableId());
		}
		if (moduleDomainObjectAttr.getDataType() > 0) {
			jqGrid.addEq("dataType", moduleDomainObjectAttr.getDataType());
		}
		if(StringUtils.isNotBlank(moduleDomainObjectAttr.getDataTypes())){
			String[] types = moduleDomainObjectAttr.getDataTypes().split(",");
			List<Integer> dataTypes = new ArrayList<Integer>();
			StringBuffer sb = new StringBuffer();
			int i = 1;
			for(String type : types){
				dataTypes.add(Integer.parseInt(type));
				if(i < types.length){
					sb.append("dataType = ? or ");
				}else{
					sb.append("dataType = ?");
				}
				i ++;
			}
			jqGrid.addHqlFilter(sb.toString(), dataTypes.toArray());
		}
		if(jqGrid.getSortProperties().size() == 0){
			jqGrid.addSortPropertyAsc("tableId");
			jqGrid.addSortPropertyAsc("attrName");
		}
		return this.retrievePage(jqGrid);
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param attrId
	 * @param attrName
	 * @return
	 */
	public boolean checkNameIsValid(String domainObjectId, String attrId, String attrName) {
		String hql = null;
		List retList = null;
		if (StringUtils.isBlank(attrId)) {// 新建时
			hql = "from " + ModuleDomainObjectAttr.class.getName() + " where  attrName = ? and domainObjectId = ?";
			retList = this.retrieve(hql, attrName, domainObjectId);
		} else {// 编辑时
			hql = "from " + ModuleDomainObjectAttr.class.getName() + " where attrId != ? and attrName = ? and domainObjectId = ?";
			retList = this.retrieve(hql, attrId, attrName, domainObjectId);
		}
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}


	public int deleteByTableId(String tableId) {
		String hql = "delete from " + ModuleDomainObjectAttr.class.getName() + " where tableId = ?";
		return this.execUpdate(hql, tableId);
	}
	
	public ModuleDomainObjectAttr getDomainObjectAttrByDomainObjectIdAndAttrName(String domainObjectId, String attrName) {
		String hql = "from " + ModuleDomainObjectAttr.class.getName() + " where attrName = ? and domainObjectId = ?";
		List< ModuleDomainObjectAttr > list = this.find(hql, attrName, domainObjectId);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	public IDomainObjectAttr getDomainObjectAttr(String domainObjectId,
			String attrDisplayOrSynonymName) {
		// TODO Auto-generated method stub
		String hql = "from " + ModuleDomainObjectAttr.class.getName() + " where domainObjectId = ? and (attrName = ? or displayNameZh = ? or displayNameEn = ?"
				+ " or displayName3 = ? or displayName4 = ? or synonymZh like ? or synonymEn like ? or synonym3 like ? or synonym4 like ?)";
		List< ModuleDomainObjectAttr > list = this.find(hql, domainObjectId, 
				attrDisplayOrSynonymName, attrDisplayOrSynonymName, attrDisplayOrSynonymName,attrDisplayOrSynonymName, attrDisplayOrSynonymName,
				 "%" + attrDisplayOrSynonymName + "%", "%" + attrDisplayOrSynonymName + "%", "%" + attrDisplayOrSynonymName + "%", "%" + attrDisplayOrSynonymName + "%");
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		if(list.size() > 1){
			EIPService.getLogService(ModuleConstant.LOG_TYPE_DEBUG).error(MessageFormat.format("找到domainObjectId={},attrDisplayOrSynonymName={}的记录数为[]条！", domainObjectId,
					attrDisplayOrSynonymName, list.size()));
		}
		return list.get(0);
	}
	
	public List<String> getDomainObjectAttrIdsByTableId(String tableId){
		String hql = "select attrId from " + ModuleDomainObjectAttr.class.getName() + " where tableId = ?";
		return this.find(hql, tableId);
	}
	
	public List<String> getDomainObjectAttrIdsByDomainObjectId(String domainObjectId){
		String hql = "select attrId from " + ModuleDomainObjectAttr.class.getName() + " where domainObjectId = ?";
		return this.find(hql, domainObjectId);
	}
}
