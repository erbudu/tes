package com.supporter.prj.eip.module.cache;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.module.constant.ModuleConstant;
import com.supporter.prj.eip.module.dao.ModuleDomainObjectAttrDao;
import com.supporter.prj.eip.module.entity.ModuleDomainObjectAttr;
import com.supporter.prj.eip_service.module.entity.IDomainObjectAttr;

public class ModuleAttrCacheManager extends AbstractCacheManager< ModuleDomainObjectAttr >{
	private static final String CACHE_KEY_PREFIX = ModuleConstant.CACHE_KEY_PREFIX + "ATTR";
	
	private String attrId;
	private String domainObjectId;
	private String displayOrSynonymName;
	public ModuleAttrCacheManager(String attrId){
		super(attrId);
		this.attrId = attrId;
	}
	public ModuleAttrCacheManager(String domainObjectId, String displayOrSynonymName){
		super(domainObjectId, displayOrSynonymName);
		this.domainObjectId = domainObjectId;
		this.displayOrSynonymName = displayOrSynonymName;
	}
	@Override
	public String getCacheKeyPrefix() {
		return CACHE_KEY_PREFIX;
	}

	@Override
	public int getCacheExpiryTime() {
		return ModuleConstant.CACHE_TIME_48H;
	}

	@Override
	public ModuleDomainObjectAttr getFromDb() {
		if(StringUtils.isNotBlank(attrId)){
			ModuleDomainObjectAttr entity = SpringContextHolder.getBean(ModuleDomainObjectAttrDao.class).get(attrId);
			return entity;
		}else{
			if(StringUtils.isNotBlank(domainObjectId) && StringUtils.isNotBlank(displayOrSynonymName)){
				IDomainObjectAttr attr = SpringContextHolder.getBean(ModuleDomainObjectAttrDao.class).
						getDomainObjectAttr(domainObjectId, displayOrSynonymName);
				return (ModuleDomainObjectAttr) attr;
			}
		}
		return null;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}
	public String getDomainObjectId() {
		return domainObjectId;
	}
	public void setDomainObjectId(String domainObjectId) {
		this.domainObjectId = domainObjectId;
	}
	public String getDisplayOrSynonymName() {
		return displayOrSynonymName;
	}
	public void setDisplayOrSynonymName(String displayOrSynonymName) {
		this.displayOrSynonymName = displayOrSynonymName;
	}
	
	
	
}
