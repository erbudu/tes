package com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.service;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.dao.PrePrjManagerFilingDao;
import com.supporter.prj.cneec.pc.pre_prj_manager.pre_prj_info_filing.entity.PrePrjManagerFiling;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: Service
 * @Description: 功能模块表
 * @author tiansen
 * 
 */
@Service
public class PrePrjManagerFilingService {
	@Autowired
	private PrePrjManagerFilingDao filingDao;
	
	/**
	 * 分页查询
	 * @param user
	 * @param jqGrid
	 * @param filing
	 * @return
	 */
	public List<PrePrjManagerFiling> getGrid(UserProfile user, JqGrid jqGrid, PrePrjManagerFiling filing){
		return filingDao.findPage(user, jqGrid, filing);
	}
	
	/**
	 * 根据id获取对象
	 * @param filingId
	 * @return
	 */
	public PrePrjManagerFiling get(String filingId){
		return filingDao.get(filingId);
	}
	
	/**
	 * 初始化备案信息
	 * @param filingId
	 * @param user
	 * @return
	 */
	public PrePrjManagerFiling initEditOrViewPage(String filingId,UserProfile user){
		PrePrjManagerFiling filing =null;
		if(StringUtils.isBlank(filingId)){
			filing = newFiling(user);
		}else{
			filing =get(filingId);
		}
		return filing;
	}
	/**
	 * 新建初始化备案信息
	 * @param user
	 * @return
	 */
	public PrePrjManagerFiling newFiling(UserProfile user){
		PrePrjManagerFiling filing = new PrePrjManagerFiling();
		filing.setAdd(true);
		filing.setFilingId(com.supporter.util.UUIDHex.newId());
		filing.setCreatedBy(user.getName());
		filing.setCountryId(user.getPersonId());
		filing.setFollowingManId(user.getPersonId());
		filing.setFollowingMan(user.getName());
		Dept dept = user.getDept();
		if(dept!=null){
			filing.setDeptId(dept.getDeptId());
			filing.setDeptName(dept.getName());
		}
		return filing;
	}
	
	/**
	 * 保存或更细实体类
	 * @param filing
	 * @param user
	 * @return
	 */
	public PrePrjManagerFiling saveOrUpdate(PrePrjManagerFiling filing,UserProfile user){
		if(filing.getAdd()){
			filingDao.save(filing);
		}else{
			filingDao.update(filing);
		}
		return filing;
	}
	
	/**
	 * 删除
	 * @param filingIds
	 * @param user
	 */
	public void delete (String filingIds,UserProfile user){
		if(StringUtils.isNotBlank(filingIds)){
			for (String filingId : filingIds.split(",")) {
				filingDao.delete(filingId);
			}
		}
	}

	/**
	 * 获取区域下拉框
	 * @param projectId
	 * @return
	 */
	public Map<String, String> getAreaCodetable() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("AREA_F");
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}
	
	/**
	 * 获取省市下拉框
	 * @param projectId
	 * @return
	 */
	public Map<String, String> getAreaItemtable(String value) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("PROVINCE");
		if (list != null && list.size() > 0 && value != null) {
			for (IComCodeTableItem item : list) {
				if(item.getExtField1().equals(value)) {
					map.put(item.getItemId(), item.getDisplayName());
				}
			}
		}
		return map;
	}

	/**
	 * 获取国家码表
	 * @return
	 */
	public Map<String, String> getCountrytable() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("AREA_F");
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}

	/**
	 * 获取项目领域码表
	 * @return
	 */
	public Map<String, String> getprjAreatable() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getCodeTableItems("AREA_F");
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}

	/**
	 * 根据id获取
	 * @param filingIds
	 * @param user
	 * @return
	 */
	public List<PrePrjManagerFiling> getFilingByIds(String filingIds, UserProfile user) {
		if(StringUtils.isNotBlank(filingIds)) {
			for (String filingId : filingIds.split(",")) {
				List<PrePrjManagerFiling> list = new ArrayList<PrePrjManagerFiling>() ;
				PrePrjManagerFiling entity  =get(filingId);
				list.add(entity);
				return list;
						
			}
		}
		return null;
	}
}
