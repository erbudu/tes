package com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;

import com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.dao.StampTaxItemDao;
import com.supporter.prj.cneec.tpc.tpc_stamp_tax_item.entity.StampTaxItem;

/**
 * @Title: Service
 * @Description: 贸易印花税税目表.
 * @author LEGO
 * @date 2020-02-03 13:26:24
 * @version V1.0
 */
@Service
public class StampTaxItemService {

	@Autowired
	private StampTaxItemDao stampTaxItemDao;

	/**
	 * 根据主键获取贸易印花税税目表.
	 * 
	 * @param itemId 主键
	 * @return StampTaxItem
	 */
	public StampTaxItem get(String itemId) {
		return stampTaxItemDao.get(itemId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< StampTaxItem > getGrid(UserProfile user, JqGrid jqGrid, StampTaxItem stampTaxItem) {
		return stampTaxItemDao.findPage(jqGrid, stampTaxItem);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param itemId
	 * @return
	 */
	public StampTaxItem initEditOrViewPage(String itemId) {
		if (StringUtils.isBlank(itemId)) {// 新建
			StampTaxItem entity = new StampTaxItem();
			entity.setAdd(true);
			return entity;
		} else {// 编辑
			StampTaxItem entity =this.get(itemId);
			entity.setAdd(false);
			return entity;
		}
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param stampTaxItem 实体类
	 * @return
	 */
	public StampTaxItem saveOrUpdate(UserProfile user, StampTaxItem stampTaxItem) {
		if (stampTaxItem != null) {
			if (stampTaxItem.getAdd()) {// 新建
				stampTaxItem.setItemId(com.supporter.util.UUIDHex.newId());
				return this.save(stampTaxItem);
			} else {// 编辑
				return this.update(stampTaxItem);
			}
		}
		return stampTaxItem;
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param stampTaxItem 实体类
	 * @return
	 */
	private StampTaxItem save(StampTaxItem stampTaxItem) {
		this.stampTaxItemDao.save(stampTaxItem);
		return stampTaxItem;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param stampTaxItem 实体类
	 * @return
	 */
	private StampTaxItem update(StampTaxItem stampTaxItem) {
		StampTaxItem stampTaxItemDb = this.stampTaxItemDao.get(stampTaxItem.getItemId());
		if(stampTaxItemDb == null) {
			return this.save(stampTaxItem);
		}else{
			this.stampTaxItemDao.update(stampTaxItem);
			return stampTaxItem;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param itemIds 主键集合，多个以逗号分隔
	 */
	public void delete(String itemIds) {
		if(StringUtils.isNotBlank(itemIds)) {
			for(String itemId : itemIds.split(",")) {
				this.stampTaxItemDao.delete(itemId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param itemId
	 * @param itemName
	 * @return
	 */
	public boolean nameIsValid(String itemId,String taxItemName) {
		return this.stampTaxItemDao.nameIsValid( itemId, taxItemName);
	}
	
	/**
	 * 获取印花税税目map，合同选择印花税用
	 * @return
	 */
	public Map<String, String> getstampTaxItemMap(){
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<StampTaxItem> list = this.stampTaxItemDao.getAllStampTaxItemList();
		if (list != null && list.size() > 0) {
			for (StampTaxItem item : list) {
				map.put(item.getItemId(), item.getTaxItemName());
			}
		}
		return map;
	}

}

