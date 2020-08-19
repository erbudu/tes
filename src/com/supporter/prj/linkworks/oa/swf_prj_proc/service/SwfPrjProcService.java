package com.supporter.prj.linkworks.oa.swf_prj_proc.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.linkworks.oa.swf_prj_proc.dao.SwfPrjProcDao;
import com.supporter.prj.linkworks.oa.swf_prj_proc.entity.SwfPrjProc;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

@Service
public class SwfPrjProcService {
	@Autowired
	private SwfPrjProcDao swfPrjProcDao;
	
	/**
	 * 根据主键ID获取实体
	 * @param procId
	 * @return
	 */
	public SwfPrjProc get(String procId){
		return swfPrjProcDao.get(procId);
	}
	
	/**
	 * 进入新建或编辑页面需要加载的信息
	 * @param procId
	 * @param user
	 * @return
	 */
	public SwfPrjProc initEditOrViewPage(String procId, UserProfile user){
		if (StringUtils.isBlank(procId)){
			SwfPrjProc swfPrjProc = new SwfPrjProc();
			swfPrjProc.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			if (user != null){
				swfPrjProc.setCreatedBy(user.getName());
				swfPrjProc.setCreatedById(user.getPersonId());
			}
			return swfPrjProc;
		}else {
			return this.get(procId);
		}
	}
	
	/**
	 * 分页查询数据
	 * @param jqGrid
	 * @param swfPrjProc
	 * @return
	 */
	public List<SwfPrjProc> getGrid(JqGrid jqGrid, SwfPrjProc swfPrjProc){
		return this.swfPrjProcDao.findPage(jqGrid, swfPrjProc);
	}
	
	/**
	 * 保存或更新
	 * @param user
	 * @param swfPrjProc
	 * @return
	 */
	public SwfPrjProc saveOrUpdate(UserProfile user, SwfPrjProc swfPrjProc){
		if (swfPrjProc != null){
			swfPrjProc.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			if (user != null){
				swfPrjProc.setModifiedBy(user.getName());
				swfPrjProc.setModifiedById(user.getPersonId());
			}
			if (StringUtils.isBlank(swfPrjProc.getProcId())){
				swfPrjProc.setProcId(com.supporter.util.UUIDHex.newId());
				this.swfPrjProcDao.save(swfPrjProc);
			}else {
				this.swfPrjProcDao.update(swfPrjProc);
			}
		}
		return swfPrjProc;
	}
	
	/**
	 * 删除
	 * @param ProcId
	 */
	public void batchDel(String ProcId){
		if(StringUtils.isNotBlank(ProcId)){
			this.swfPrjProcDao.delete(ProcId);
		}
	}
	
	/**
	 * 获取应用平台MAP
	 * @return
	 */
	public Map<String, String> getAppPlatformMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取国内/国外下地区
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems("APP_BUSINESS", "");
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}
	
	/**
	 * 获取业务名称MAP
	 * @param appPlatformCede
	 * @return
	 */
	public Map<String, String> getBusinessMap(String appPlatformCede) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 取国内/国外下地区
		List<IComCodeTableItem> list = EIPService.getComCodeTableService().getSubItems("APP_BUSINESS", appPlatformCede);
		if (list != null && list.size() > 0) {
			for (IComCodeTableItem item : list) {
				map.put(item.getItemId(), item.getDisplayName());
			}
		}
		return map;
	}
}
