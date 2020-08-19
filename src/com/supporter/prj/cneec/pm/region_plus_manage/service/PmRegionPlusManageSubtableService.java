package com.supporter.prj.cneec.pm.region_plus_manage.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.pm.region_plus_manage.dao.PmRegionPlusManageSubtableDao;
import com.supporter.prj.cneec.pm.region_plus_manage.entity.PmRegionPlusManageSubtable;
import com.supporter.prj.cneec.tpc.custom.entity.Custom;
import com.supporter.prj.cneec.tpc.util.CheckBoxVO;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.util.UUIDHex;

@Service
@Transactional(TransManager.APP)
public class PmRegionPlusManageSubtableService {
	@Autowired
	private PmRegionPlusManageSubtableDao pmRegionPlusManageSubtableDao;
	
	/**
	 * 根据主键id获得实体
	 * @param subtableId
	 * @return
	 */
	public PmRegionPlusManageSubtable get(String subtableId){
		return this.pmRegionPlusManageSubtableDao.get(subtableId);
	}
	
	/**
	 * 删除区域＋下所有子表信息
	 * @param manageId
	 */
	public void deleteAllSubtableByManageId(String manageId){
		if (StringUtils.isNotBlank(manageId)){
			List<PmRegionPlusManageSubtable> subList = this.pmRegionPlusManageSubtableDao.getSubtableListByManageId(manageId);
			if (subList != null){
				this.pmRegionPlusManageSubtableDao.delete(subList);
			}
		}
	}
	
	/**
	 * 根据所属市场获取大洲或地区map
	 * @param areaInername
	 * @return
	 */
	public Map<String, String> getAreaMap(String areaInername){
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<Object[]> areaList = this.pmRegionPlusManageSubtableDao.getAreaList(areaInername);
		if (areaList != null){
			for (Object[] obj : areaList){
				map.put(obj[0].toString(), obj[1].toString());
			}
		}
		return map;
	}
	
	/**
	 * 获取大洲或地区下的国家省份date
	 * @param manageId
	 * @param areaId
	 * @return
	 */
	public List<CheckBoxVO> getAreaItemData(String manageId, String areaId){
		List<CheckBoxVO> list = new ArrayList<CheckBoxVO>();
		List<Object[]> areaItemList = this.pmRegionPlusManageSubtableDao.getAreaItemList(areaId);
		List<String> areaIdList = this.pmRegionPlusManageSubtableDao.getAreaIdListByManageId(manageId);
		if (areaItemList != null){
			for (Object[] obj : areaItemList){
				String id = areaId + "_" + obj[0].toString();
				String name = areaId + "_item";
				String value = obj[0].toString();
				String describe = obj[1].toString();
				boolean cheched = areaIdList != null ? areaIdList.contains(value) : false;
				CheckBoxVO vo = new CheckBoxVO(id, name, value, describe, cheched);
				list.add(vo);
			}
		}
		return list;
	}
	
	/**
	 * 保存选择的国家或地区
	 * @param manageId
	 * @param areaItems
	 */
	public void saveAreaItems(String manageId, String areaItems){
		//先删除原有的子表记录
		List<PmRegionPlusManageSubtable> subList = this.pmRegionPlusManageSubtableDao.getSubtableListByManageId(manageId);
		if (subList != null){
			this.pmRegionPlusManageSubtableDao.delete(subList);
		}
		//再保存新的子表记录
		if (StringUtils.isNotBlank(areaItems)){
			String[] areaItemArr = areaItems.split(",");
			for (int i = 0; i < areaItemArr.length; i++){
				String[] areaItem = areaItemArr[i].split("_");
				PmRegionPlusManageSubtable sub = new PmRegionPlusManageSubtable();
				sub.setSubtableId(UUIDHex.newId());
				sub.setManageId(manageId);
				sub.setAreaId(areaItem[0]);
				sub.setAreaName(areaItem[2]);
				sub.setAreaItemId(areaItem[1]);
				sub.setAreaItemName(areaItem[3]);
				this.pmRegionPlusManageSubtableDao.save(sub);
			}
		}
	}
	
	/**
	 * 根据区域+管理ID获取下属国家map
	 * @param manageId
	 * @return
	 */
	public Map<String, String> getAreaItemsMap(String manageId){
		List<PmRegionPlusManageSubtable> subList = this.pmRegionPlusManageSubtableDao.getSubtableListByManageId(manageId);
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (subList != null){
			map.put("", "全部");
			for (PmRegionPlusManageSubtable sub : subList){
				String areaItemId = sub.getAreaItemId();
				String areaItemName = sub.getAreaItemName();
				map.put(areaItemId, areaItemName);
			}
		}
		return map;
	}
}
