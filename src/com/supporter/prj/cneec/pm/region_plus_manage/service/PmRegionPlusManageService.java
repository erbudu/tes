package com.supporter.prj.cneec.pm.region_plus_manage.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pm.region_plus_manage.dao.PmRegionPlusManageDao;
import com.supporter.prj.cneec.pm.region_plus_manage.entity.PmRegionPlusManage;
import com.supporter.prj.cneec.tpc.util.RoleUtil;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.role.service.RoleMemberService;
import com.supporter.prj.eip.role.service.RoleService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

@Service
public class PmRegionPlusManageService {
	
	@Autowired
	private PmRegionPlusManageDao pmRegionPlusManageDao;
	
	@Autowired
	private PmRegionPlusManageSubtableService pmRegionPlusManageSubtableService;
	
	/**
	 * 根据主键id获得实体
	 * @param manageId
	 * @return
	 */
	public PmRegionPlusManage get(String manageId){
		return pmRegionPlusManageDao.get(manageId);
	}
	
	/**
	 * 进入新建或编辑页面需要加载的信息
	 * @param manageId
	 * @param user
	 * @return
	 */
	public PmRegionPlusManage initEditOrViewPage(String manageId, UserProfile user){
		if(StringUtils.isBlank(manageId)){//主键空白是新建
			PmRegionPlusManage plus = new PmRegionPlusManage();
			plus.setAreaInername(PmRegionPlusManage.FOREIGN);//默认设为国外市场，后期又去要在放开国内市场
			plus.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			if (user != null){
				plus.setCreatedBy(user.getName());
				plus.setCreatedById(user.getPersonId());
			}
			return plus;
		}else {
			return get(manageId);
		}
	}
	
	/**
	 * 分页查询数据
	 * @param jqGrid
	 * @param plus
	 * @return
	 */
	public List<PmRegionPlusManage> getGrid(JqGrid jqGrid, PmRegionPlusManage plus){
		return pmRegionPlusManageDao.findPage(jqGrid, plus);
	}
	
	/**
	 * 保存或更新
	 * @param user
	 * @param plus
	 * @return
	 */
	public PmRegionPlusManage saveOrUpdate(UserProfile user, PmRegionPlusManage plus){
		if (plus != null){
			plus.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			if (StringUtils.isBlank(plus.getManageId())){//主键空白是新建
				plus.setManageId(UUIDHex.newId());
				if (user != null){
					plus.setModifiedBy(user.getName());
					plus.setModifiedById(user.getPersonId());
				}
				this.pmRegionPlusManageDao.save(plus);
			}else {
				String manageId = plus.getManageId();
				PmRegionPlusManage oldPlus = this.get(manageId);
				if (!oldPlus.getAreaInername().equals(plus.getAreaInername())){//与原来保存的所属市场不一致
					//删除该区域+下的所有子表信息
					this.pmRegionPlusManageSubtableService.deleteAllSubtableByManageId(manageId);
				}
				this.pmRegionPlusManageDao.update(plus);
			}
		}
		return plus;
	}
	
	/**
	 * 删除
	 * @param manageId
	 */
	public void batchDel(String manageId){
		if (StringUtils.isNotBlank(manageId)){
			//删除该区域+下的所有子表信息
			this.pmRegionPlusManageSubtableService.deleteAllSubtableByManageId(manageId);
			//删除区域＋
			this.pmRegionPlusManageDao.delete(manageId);
		}
	}
	
	/**
	 * 根据用户返回可选择的区域+
	 * @param user
	 * @return
	 */
	public Map<String, String> getRedionPlusMap(UserProfile user){
		Map<String, String> map = new LinkedHashMap<String, String>();
//		boolean isManagePerson = this.pmRegionPlusManageDao.getPersonIsInRole("INFORMATIONCENTERADMINISTRATOR", user.getAccount().getAccountId());
//		String personId = user.getPersonId();
//		if ("1".equals(personId) || isManagePerson){
//			personId = "all";
//		}
//		List<PmRegionPlusManage> plusList = this.pmRegionPlusManageDao.getplusList(personId);
		//2019年5月23日改为返回全部
		List<PmRegionPlusManage> plusList = this.pmRegionPlusManageDao.getplusList("all");
		if (plusList != null){
			map.put("", "全部区域+");
			for (PmRegionPlusManage plus : plusList){
				map.put(plus.getManageId(), plus.getRegionPlusName());
			}
		}
		return map;
	}
}
